package org.eclipse.paho.android.service.evtbus;

import org.eclipse.paho.android.service.Status;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttMessageArrivedEvent extends MqttEvent {
    String topic;
    MqttMessage message;

    public MqttMessageArrivedEvent(Object source, String clientHandle, String topic, MqttMessage message) {
        super(source, clientHandle);
        this.topic = topic;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public MqttMessage getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MqttMessageArrivedEvent{" +
                "topic='" + topic + '\'' +
                ", message=" + message +
                "} " + super.toString();
    }
}
