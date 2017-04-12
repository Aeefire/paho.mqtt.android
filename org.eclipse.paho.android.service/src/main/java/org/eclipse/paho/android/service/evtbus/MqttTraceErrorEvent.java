package org.eclipse.paho.android.service.evtbus;

import org.eclipse.paho.android.service.Status;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttTraceErrorEvent extends MqttTraceEvent {
    public MqttTraceErrorEvent(Object source, String clientHandle, String tag, String message) {
        super(source, clientHandle, tag, message);
    }
}
