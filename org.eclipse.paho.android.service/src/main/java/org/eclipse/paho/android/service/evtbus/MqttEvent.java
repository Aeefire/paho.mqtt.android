package org.eclipse.paho.android.service.evtbus;

import org.eclipse.paho.android.service.Status;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttEvent {
    Object source;
    String clientHandle;

    public MqttEvent(Object source, String clientHandle) {
        this.source = source;
        this.clientHandle = clientHandle;
    }

    public Object getSource() {
        return source;
    }

    public String getClientHandle() {
        return clientHandle;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName()+"{" +
                "source=" + source +
                ", clientHandle='" + clientHandle + '\'' +
                '}';
    }
}
