package org.eclipse.paho.android.service.evtbus;

import org.eclipse.paho.android.service.Status;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttEvent {
    Object source;
    String clientHandle;
    Status status;

    public MqttEvent(Object source, String clientHandle) {
        this.source = source;
        this.clientHandle = clientHandle;
        this.status = status;
    }

    public Object getSource() {
        return source;
    }

    public String getClientHandle() {
        return clientHandle;
    }

    public Status getStatus() {
        return status;
    }
}
