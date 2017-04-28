package org.eclipse.paho.android.service.evtbus;

import org.eclipse.paho.android.service.Status;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttConnectCompleteEvent extends MqttEvent {
    boolean isReconnect;
    String serverURI;

    public MqttConnectCompleteEvent(Object source, String clientHandle, boolean isReconnect, String serverURI) {
        super(source, clientHandle);
        this.isReconnect = isReconnect;
        this.serverURI = serverURI;
    }

    public boolean isReconnect() {
        return isReconnect;
    }

    public String getServerURI() {
        return serverURI;
    }

    @Override
    public String toString() {
        return "MqttConnectCompleteEvent{" +
                "isReconnect=" + isReconnect +
                ", serverURI='" + serverURI + '\'' +
                "} " + super.toString();
    }
}
