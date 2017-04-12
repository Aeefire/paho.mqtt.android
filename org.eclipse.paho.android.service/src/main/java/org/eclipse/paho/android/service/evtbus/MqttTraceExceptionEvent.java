package org.eclipse.paho.android.service.evtbus;

import org.eclipse.paho.android.service.Status;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttTraceExceptionEvent extends MqttTraceEvent {

    Exception exception;


    public MqttTraceExceptionEvent(Object source, String clientHandle, String tag, String message, Exception exception) {
        super(source, clientHandle, tag, message);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
