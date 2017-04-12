package org.eclipse.paho.android.service.evtbus;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttConnectionLostEvent extends MqttEvent {


    Throwable cause;

    public MqttConnectionLostEvent(Object source, String clientHandle, Throwable cause) {
        super(source, clientHandle);
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }
}
