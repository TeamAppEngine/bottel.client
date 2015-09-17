package io.bottel.models.events;

/**
 * Created by Omid on 9/17/2015.
 */
public class OnConnectionStateChanged {
    public enum States {
        CONNECTED,
        DISCONNECTED,
        FAILED
    }


    private String message;
    private States state;

    public OnConnectionStateChanged(String message, States state) {
        this.message = message;
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }
}
