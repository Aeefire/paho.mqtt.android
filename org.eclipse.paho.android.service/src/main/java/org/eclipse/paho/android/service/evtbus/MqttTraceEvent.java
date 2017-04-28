package org.eclipse.paho.android.service.evtbus;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttTraceEvent extends MqttEvent {
    String tag;
    String message;

    public MqttTraceEvent(Object source, String clientHandle, String tag, String message) {
        super(source, clientHandle);
        this.tag = tag;
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MqttTraceEvent{" +
                "tag='" + tag + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
