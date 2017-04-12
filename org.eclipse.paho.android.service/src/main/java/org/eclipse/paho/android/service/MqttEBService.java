/*******************************************************************************
 * Copyright (c) 1999, 2016 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *   James Sutton - isOnline Null Pointer (bug 473775)
 */
package org.eclipse.paho.android.service;

import android.annotation.SuppressLint;
import android.os.Bundle;

import org.eclipse.paho.android.service.evtbus.MqttEventBus;
import org.eclipse.paho.android.service.evtbus.MqttRawEvent;

/**
 * <p>
 * The android service which interfaces with an MQTT client implementation
 * </p>
 * <p>
 * The main API of MqttService is intended to pretty much mirror the
 * IMqttAsyncClient with appropriate adjustments for the Android environment.<br>
 * These adjustments usually consist of adding two parameters to each method :-
 * </p>
 * <ul>
 * <li>invocationContext - a string passed from the application to identify the
 * context of the operation (mainly included for support of the javascript API
 * implementation)</li>
 * <li>activityToken - a string passed from the Activity to relate back to a
 * callback method or other context-specific data</li>
 * </ul>
 * <p>
 * To support multiple client connections, the bulk of the MQTT work is
 * delegated to MqttConnection objects. These are identified by "client
 * handle" strings, which is how the Activity, and the higher-level APIs refer
 * to them.
 * </p>
 * <p>
 * Activities using this service are expected to start it and bind to it using
 * the BIND_AUTO_CREATE flag. The life cycle of this service is based on this
 * approach.
 * </p>
 * <p>
 * Operations are highly asynchronous - in most cases results are returned to
 * the Activity by broadcasting one (or occasionally more) appropriate Intents,
 * which the Activity is expected to register a listener for.<br>
 * The Intents have an Action of
 * {@link MqttServiceConstants#CALLBACK_TO_ACTIVITY
 * MqttServiceConstants.CALLBACK_TO_ACTIVITY} which allows the Activity to
 * register a listener with an appropriate IntentFilter.<br>
 * Further data is provided by "Extra Data" in the Intent, as follows :-
 * </p>
 * <table border="1" summary="">
 * <tr>
 * <th align="left">Name</th>
 * <th align="left">Data Type</th>
 * <th align="left">Value</th>
 * <th align="left">Operations used for</th>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_CLIENT_HANDLE
 * MqttServiceConstants.CALLBACK_CLIENT_HANDLE}</td>
 * <td align="left" valign="top">String</td>
 * <td align="left" valign="top">The clientHandle identifying the client which
 * initiated this operation</td>
 * <td align="left" valign="top">All operations</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">{@link MqttServiceConstants#CALLBACK_STATUS
 * MqttServiceConstants.CALLBACK_STATUS}</td>
 * <td align="left" valign="top">Serializable</td>
 * <td align="left" valign="top">An {@link Status} value indicating success or
 * otherwise of the operation</td>
 * <td align="left" valign="top">All operations</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_ACTIVITY_TOKEN
 * MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN}</td>
 * <td align="left" valign="top">String</td>
 * <td align="left" valign="top">the activityToken passed into the operation</td>
 * <td align="left" valign="top">All operations</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_INVOCATION_CONTEXT
 * MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT}</td>
 * <td align="left" valign="top">String</td>
 * <td align="left" valign="top">the invocationContext passed into the operation
 * </td>
 * <td align="left" valign="top">All operations</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">{@link MqttServiceConstants#CALLBACK_ACTION
 * MqttServiceConstants.CALLBACK_ACTION}</td>
 * <td align="left" valign="top">String</td>
 * <td align="left" valign="top">one of
 * <table summary="">
 * <tr>
 * <td align="left" valign="top"> {@link MqttServiceConstants#SEND_ACTION
 * MqttServiceConstants.SEND_ACTION}</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#UNSUBSCRIBE_ACTION
 * MqttServiceConstants.UNSUBSCRIBE_ACTION}</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top"> {@link MqttServiceConstants#SUBSCRIBE_ACTION
 * MqttServiceConstants.SUBSCRIBE_ACTION}</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top"> {@link MqttServiceConstants#DISCONNECT_ACTION
 * MqttServiceConstants.DISCONNECT_ACTION}</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top"> {@link MqttServiceConstants#CONNECT_ACTION
 * MqttServiceConstants.CONNECT_ACTION}</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#MESSAGE_ARRIVED_ACTION
 * MqttServiceConstants.MESSAGE_ARRIVED_ACTION}</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#MESSAGE_DELIVERED_ACTION
 * MqttServiceConstants.MESSAGE_DELIVERED_ACTION}</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#ON_CONNECTION_LOST_ACTION
 * MqttServiceConstants.ON_CONNECTION_LOST_ACTION}</td>
 * </tr>
 * </table>
 * </td>
 * <td align="left" valign="top">All operations</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_ERROR_MESSAGE
 * MqttServiceConstants.CALLBACK_ERROR_MESSAGE}
 * <td align="left" valign="top">String</td>
 * <td align="left" valign="top">A suitable error message (taken from the
 * relevant exception where possible)</td>
 * <td align="left" valign="top">All failing operations</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_ERROR_NUMBER
 * MqttServiceConstants.CALLBACK_ERROR_NUMBER}
 * <td align="left" valign="top">int</td>
 * <td align="left" valign="top">A suitable error code (taken from the relevant
 * exception where possible)</td>
 * <td align="left" valign="top">All failing operations</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_EXCEPTION_STACK
 * MqttServiceConstants.CALLBACK_EXCEPTION_STACK}</td>
 * <td align="left" valign="top">String</td>
 * <td align="left" valign="top">The stacktrace of the failing call</td>
 * <td align="left" valign="top">The Connection Lost event</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_MESSAGE_ID
 * MqttServiceConstants.CALLBACK_MESSAGE_ID}</td>
 * <td align="left" valign="top">String</td>
 * <td align="left" valign="top">The identifier for the message in the message
 * store, used by the Activity to acknowledge the arrival of the message, so
 * that the service may remove it from the store</td>
 * <td align="left" valign="top">The Message Arrived event</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_DESTINATION_NAME
 * MqttServiceConstants.CALLBACK_DESTINATION_NAME}
 * <td align="left" valign="top">String</td>
 * <td align="left" valign="top">The topic on which the message was received</td>
 * <td align="left" valign="top">The Message Arrived event</td>
 * </tr>
 * <tr>
 * <td align="left" valign="top">
 * {@link MqttServiceConstants#CALLBACK_MESSAGE_PARCEL
 * MqttServiceConstants.CALLBACK_MESSAGE_PARCEL}</td>
 * <td align="left" valign="top">Parcelable</td>
 * <td align="left" valign="top">The new message encapsulated in Android
 * Parcelable format as a {@link ParcelableMqttMessage}</td>
 * <td align="left" valign="top">The Message Arrived event</td>
 * </tr>
 * </table>
 */
@SuppressLint("Registered")
public class MqttEBService extends MqttService implements MqttTraceHandler {

    public MqttEBService() {
        super();
    }

    /**
     * pass data back to the Activity, by building a suitable Intent object and
     * broadcasting it
     *
     * @param clientHandle source of the data
     * @param status       OK or Error
     * @param dataBundle   the data to be passed
     */
    @Override
    void callbackToActivity(String clientHandle, Status status,
                            Bundle dataBundle) {
        MqttEventBus.getRaw().post(new MqttRawEvent(this, clientHandle, status, dataBundle));
    }

}
