package org.eclipse.paho.android.service.evtbus;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttDeliveryCompleteEvent extends MqttEvent {

    IMqttDeliveryToken token;

    public MqttDeliveryCompleteEvent(Object source, String clientHandle, IMqttDeliveryToken token) {
        super(source, clientHandle);
        this.token = token;
    }

    public IMqttDeliveryToken getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "MqttDeliveryCompleteEvent{" +
                "token=" + token +
                "} " + super.toString();
    }
}
