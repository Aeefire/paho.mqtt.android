package org.eclipse.paho.android.service.evtbus;

import android.os.Bundle;

import org.eclipse.paho.android.service.Status;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttRawEvent extends MqttEvent {
    Bundle data;

    public MqttRawEvent(Object source, String clientHandle, Status status, Bundle data) {
        super(source, clientHandle);
        this.data = data;
    }

    public Bundle getData() {
        return data;
    }
}
