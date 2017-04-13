package org.eclipse.paho.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;

import org.eclipse.paho.android.service.evtbus.MqttConnectCompleteEvent;
import org.eclipse.paho.android.service.evtbus.MqttConnectionLostEvent;
import org.eclipse.paho.android.service.evtbus.MqttDeliveryCompleteEvent;
import org.eclipse.paho.android.service.evtbus.MqttEventBus;
import org.eclipse.paho.android.service.evtbus.MqttMessageArrivedEvent;
import org.eclipse.paho.android.service.evtbus.MqttRawEvent;
import org.eclipse.paho.android.service.evtbus.MqttTraceDebugEvent;
import org.eclipse.paho.android.service.evtbus.MqttTraceErrorEvent;
import org.eclipse.paho.android.service.evtbus.MqttTraceExceptionEvent;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by schartmueller on 4/12/2017.
 */

public class MqttAndroidEBClient extends MqttAndroidClient {

    public MqttAndroidEBClient(Context context, String serverURI, String clientId) {
        super(context, serverURI, clientId);
        MqttAndroidClient.SERVICE_NAME = "org.eclipse.paho.android.service.MqttEBService";
    }

    public MqttAndroidEBClient(Context ctx, String serverURI, String clientId, Ack ackType) {
        super(ctx, serverURI, clientId, ackType);
        MqttAndroidClient.SERVICE_NAME = "org.eclipse.paho.android.service.MqttEBService";
    }

    public MqttAndroidEBClient(Context ctx, String serverURI, String clientId, MqttClientPersistence persistence) {
        super(ctx, serverURI, clientId, persistence);
        MqttAndroidClient.SERVICE_NAME = "org.eclipse.paho.android.service.MqttEBService";
    }

    public MqttAndroidEBClient(Context context, String serverURI, String clientId, MqttClientPersistence persistence, Ack ackType) {
        super(context, serverURI, clientId, persistence, ackType);
        MqttAndroidClient.SERVICE_NAME = "org.eclipse.paho.android.service.MqttEBService";
    }

    @Override
    public void registerResources(Context context) {
        registerReceiver(null);
    }

    @Override
    public void setCallback(MqttCallback callback) {
        throw new UnsupportedOperationException("You cannot add a listener here. You must use EventBus!");
    }

    @Override
    public void setTraceCallback(MqttTraceHandler traceCallback) {
        throw new UnsupportedOperationException("You cannot add a listener here. You must use EventBus!");
    }

    @Override
    void registerReceiver(BroadcastReceiver receiver) {
        MqttEventBus.getRaw().register(this);
        super.setCallback(mqttCallbackExtended);
        super.setTraceCallback(mqttTraceHandler);
        receiverRegistered = true;
    }

    @Override
    public void unregisterResources() {
        MqttEventBus.getRaw().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onRawMqttEvent(MqttRawEvent evt) {
        // this is the "onReceive"

        Bundle data = evt.getData();
        String action = data.getString(MqttServiceConstants.CALLBACK_ACTION);

        if (MqttServiceConstants.CONNECT_ACTION.equals(action)) {
            connectAction(data);
        } else if (MqttServiceConstants.CONNECT_EXTENDED_ACTION.equals(action)) {
            connectExtendedAction(data);
        } else if (MqttServiceConstants.MESSAGE_ARRIVED_ACTION.equals(action)) {
            messageArrivedAction(data);
        } else if (MqttServiceConstants.SUBSCRIBE_ACTION.equals(action)) {
            subscribeAction(data);
        } else if (MqttServiceConstants.UNSUBSCRIBE_ACTION.equals(action)) {
            unSubscribeAction(data);
        } else if (MqttServiceConstants.SEND_ACTION.equals(action)) {
            sendAction(data);
        } else if (MqttServiceConstants.MESSAGE_DELIVERED_ACTION.equals(action)) {
            messageDeliveredAction(data);
        } else if (MqttServiceConstants.ON_CONNECTION_LOST_ACTION
                .equals(action)) {
            connectionLostAction(data);
        } else if (MqttServiceConstants.DISCONNECT_ACTION.equals(action)) {
            disconnected(data);
        } else if (MqttServiceConstants.TRACE_ACTION.equals(action)) {
            traceAction(data);
        } else {
            mqttService.traceError(MqttService.TAG, "Callback action doesn't exist.");
        }
    }

    private MqttCallbackExtended mqttCallbackExtended = new MqttCallbackExtended() {
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
            MqttEventBus.getDefault().post(new MqttConnectCompleteEvent(MqttAndroidEBClient.this, getClientHandle(), reconnect, serverURI));
        }

        @Override
        public void connectionLost(Throwable cause) {
            MqttEventBus.getDefault().post(new MqttConnectionLostEvent(MqttAndroidEBClient.this, getClientHandle(), cause));
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            MqttEventBus.getDefault().post(new MqttMessageArrivedEvent(MqttAndroidEBClient.this, getClientHandle(), topic, message));
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            MqttEventBus.getDefault().post(new MqttDeliveryCompleteEvent(MqttAndroidEBClient.this, getClientHandle(), token));
        }
    };


    private MqttTraceHandler mqttTraceHandler = new MqttTraceHandler() {
        @Override
        public void traceDebug(String tag, String message) {
            MqttEventBus.getDefault().post(new MqttTraceDebugEvent(MqttAndroidEBClient.this, getClientHandle(), tag, message));
        }

        @Override
        public void traceError(String tag, String message) {
            MqttEventBus.getDefault().post(new MqttTraceErrorEvent(MqttAndroidEBClient.this, getClientHandle(), tag, message));

        }

        @Override
        public void traceException(String tag, String message, Exception e) {
            MqttEventBus.getDefault().post(new MqttTraceExceptionEvent(MqttAndroidEBClient.this, getClientHandle(), tag, message, e));

        }
    };

}
