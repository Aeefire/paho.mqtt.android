package org.eclipse.paho.android.service.evtbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttEventBus extends EventBus{


    /** Log tag, apps may override it. */
    public static String TAG = "Mqtt"+EventBus.TAG;

    static volatile MqttEventBus defaultInstance;
    static volatile MqttEventBus rawInstance;


    public static MqttEventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (EventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new MqttEventBus();
                }
            }
        }
        return defaultInstance;
    }

    public static MqttEventBus getRaw(){
        if (rawInstance == null) {
            synchronized (EventBus.class) {
                if (rawInstance == null) {
                    rawInstance = new MqttEventBus();
                }
            }
        }
        return defaultInstance;
    }

    public MqttEventBus(){
        super();
    }
}
