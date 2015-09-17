package io.bottel.models.events;

/**
 * Created by Omid on 9/17/2015.
 */
public class OnCallStatusChanged {
    public enum CALL_STATUS {
        INCOMING_CALL, //  incoming call received.
        HANGED_UP,
        CONNECTED,
        DISCONNECTED,
        ACCEPTED, // call is accepted by the receipt
        STARTED,
        REJECTED,
        NO_ANSWER,
        UNKNOWN
    }

    private CALL_STATUS status;

    public OnCallStatusChanged() {
        this(CALL_STATUS.UNKNOWN);
    }

    public OnCallStatusChanged(CALL_STATUS status) {
        this.status = status;
    }

    public CALL_STATUS getStatus() {
        return status;
    }

    public void setStatus(CALL_STATUS status) {
        this.status = status;
    }
}
