package io.bottel.voip;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.zingaya.voximplant.VoxImplantCallback;
import com.zingaya.voximplant.VoxImplantClient;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import io.bottel.models.events.OnCallStatusChanged;
import io.bottel.models.events.OnConnectionStateChanged;
import io.bottel.services.CallReceiverService;
import io.bottel.utils.GUtils;
import io.bottel.utils.PresencePersistent;

/**
 * Created by Omid on 9/17/2015.
 */
public class VoxClient extends VOIPService implements VoxImplantCallback {

    private static final String MESSAGE_CONNECTED = "Already connected";

    VoxImplantClient client;
    private boolean isConnected;
    private String username;
    private String password;
    private String currentCallId;
    private Context context;

    @Override
    public void init(Context context) {
        super.init(context);
        client = VoxImplantClient.instance();
        client.setAndroidContext(context);

        client.setCallback(this);
        client.setCameraResolution(320, 240);
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void signOut() {
        client.closeConnection();
    }

    @Override
    public void loginAsync(Context context, String username, String password) {
        this.username = username;
        this.password = password;
        this.context = context;

        if (this.isConnected)
            client.login(username, password);
        else
            client.connect();
        // after onConnectionSuccess happens to automatically login
    }

    @Override
    public void loginAsync(String username, String password) {
        loginAsync(context, username, password);
    }

    @Override
    public void hangUp(Context context) {
        super.hangUp(context);

        if (currentCallId != null) {
            Map<String, String> map = new HashMap<>();
            map.put("hangup", "true");
            isHangedUp = true;
            client.disconnectCall(currentCallId, map);
            EventBus.getDefault().post(new OnCallStatusChanged(OnCallStatusChanged.CALL_STATUS.HANGED_UP));
        }
    }


    @Override
    public void connect(Context context) {
//        Toast.makeText(context, "connecting..", Toast.LENGTH_SHORT).show();

        if (!isConnected)
            client.connect();
        this.context = context;
    }

    @Override
    public void call(String receipt) {
        currentCallId = client.createCall(receipt, false, null); // (receiptId, videoEnabled, customData)
        Map<String, String> headers = new HashMap<>();
        toast("calling.. " + receipt);
        headers.put("X-DirectCall", "true");

        client.startCall(currentCallId, headers);
    }

    @Override
    public void hold() {
        client.setMute(true);
    }

    @Override
    public void answerCall() {
        client.answerCall(currentCallId);
    }

    @Override
    public void rejectCall() {
        client.declineCall(currentCallId);
//        PresencePersistent.setCurrentState(context, PresencePersistent.State.AVAILABLE);
    }

    @Override
    public void resume() {
        client.setMute(false);
    }


    /**
     * VoxImplantCallback listeners
     */

    @Override
    public void onConnectionSuccessful() {
        this.isConnected = true;
        String message = "connection established - voxclient";
        toast(message);

        EventBus.getDefault().post(new OnConnectionStateChanged(message, OnConnectionStateChanged.States.CONNECTED));

        // if username and password are available, try to login automatically
        if (!GUtils.isNullOrEmpty(this.username) && !GUtils.isNullOrEmpty(this.password))
            client.login(this.username, this.password);
    }

    @Override
    public void onConnectionFailedWithError(String message) {
        toast("some crap happened:" + message);
        if (message.equals(MESSAGE_CONNECTED))
            onConnectionSuccessful();
        else
            EventBus.getDefault().post(new OnConnectionStateChanged(message, OnConnectionStateChanged.States.FAILED));
    }

    @Override
    public void onConnectionClosed() {
        this.isConnected = false;
        toast("connection lost!");

    }

    @Override
    public void onMessageReceivedInCall(String s, String s1, Map<String, String> map) {

    }

    @Override
    public void onCallConnected(String s, Map<String, String> map) {
        toast("call connected");
        isOnCall = true;

        hold();

        PresencePersistent.setCurrentState(context, PresencePersistent.State.ON_CALL);
        EventBus.getDefault().post(new OnCallStatusChanged(OnCallStatusChanged.CALL_STATUS.CONNECTED));
    }

    @Override
    public void onCallRinging(String s, Map<String, String> map) {
        toast("call ringing..");
    }

    boolean isHangedUp = false;
    boolean isOnCall = false;


    @Override
    public boolean isOnCall() {
        return isOnCall;
    }

    @Override
    public void onCallDisconnected(String s, Map<String, String> map) {
        isOnCall = false;
        // if this is not the latest call, just ignore it.
        if (!s.equals(currentCallId))
            return;

        toast("call disconnected");

        if (!isHangedUp) {
            isHangedUp = false;
            EventBus.getDefault().post(new OnCallStatusChanged(OnCallStatusChanged.CALL_STATUS.DISCONNECTED));
            PresencePersistent.setCurrentState(context, PresencePersistent.State.AVAILABLE);
        } else isHangedUp = false;
    }

    @Override
    public void onLoginFailed(VoxImplantClient.LoginFailureReason loginFailureReason) {
        toast("Login failed! " + loginFailureReason.toString());
        EventBus.getDefault().post(new OnConnectionStateChanged("", OnConnectionStateChanged.States.FAILED));
    }

    @Override
    public void onLoginSuccessful(String displayName) {
        toast("Login succeeded.");
//        EventBus.getDefault().post(new OnLoginSuccessful());
//        this.listener.onLoggedIn(displayName);
    }

    @Override
    public void onIncomingCall(String callId, String from, String displayName, boolean videoCall, Map<String, String> headers) {
//        EventBus.getDefault().post(new OnCallStatusChanged(OnCallStatusChanged.CALL_STATUS.INCOMING_CALL));
//        client.answerCall(callId);

        toast("call received in voxclient " + from);
        currentCallId = callId;
//        EventBus.getDefault().post(new OnIncomingCallReceived(callId, from, displayName, videoCall, headers));

        Intent intent = new Intent(context, CallReceiverService.class);
        intent.putExtra(CallReceiverService.BUNDLE_CALLEE_EMAIL, displayName);
        context.startService(intent);
    }

    private void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSIPInfoReceivedInCall(String s, String s1, String s2, Map<String, String> map) {

    }

    @Override
    public void onCallFailed(String callId, int code, String reason, Map<String, String> headers) {
        currentCallId = null;
        Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCallAudioStarted(String s) {

    }
}
