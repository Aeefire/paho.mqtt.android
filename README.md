# Eventbus-Fork of Eclipse's Paho Android Service

This is a fork of Eclipse's official Mqtt Paho Android Service library. It includes an additional Service implementation and minor changes to eclipse's code-base **to make use of [GreenRobot's EventBus](https://github.com/greenrobot/EventBus) instead of an Android Service with BroadcastReceivers**.
Using EventBus allows for easier integration and skips the overhead of using Intents and Broadcasts.

*NOTE*: This is, by far, not the cleanest solution. Also, there are no automated tests for the EventBus mechanism included and eclipse's build logic is still included (although not used).

# To Use:
1. Fork this library or include it with gradle using jitpack.
2. Add the **AndroidEBService** to your application's Android Manifest
3. To receive events of the various types ( see the [**evtbus package**](https://github.com/Aeefire/paho.mqtt.android/tree/master/org.eclipse.paho.android.service/src/main/java/org/eclipse/paho/android/service/evtbus) for all types of events) register using the context and MqttEventBus:
```
// e.g. in onCreate() of your activity
MqttEventBus.getDefault().register(this)

// and to receive the event itself, typical for greenrobot's EventBus, just
public void someMethod(MqttEvent evt){
	// do something with it
}

```
# PullRequests
I gladly look at  (and merge) every pullrequest you send me, if it includes reasonable changes, cleanups, restructuring etc.

## Features
|                     |                    |   |                      |                    |
|---------------------|--------------------|---|----------------------|--------------------|
| MQTT 3.1            | :heavy_check_mark: |   | Automatic Reconnect  | :heavy_check_mark: |
| MQTT 3.1.1          | :heavy_check_mark: |   | Offline Buffering    | :heavy_check_mark: |
| LWT                 | :heavy_check_mark: |   | WebSocket Support    | :heavy_check_mark: |
| SSL / TLS           | :heavy_check_mark: |   | Standard TCP Support | :heavy_check_mark: |
| Message Persistence | :heavy_check_mark: |   |


## Links

- Project Website: [https://www.eclipse.org/paho](https://www.eclipse.org/paho)
- Eclipse Project Information: [https://projects.eclipse.org/projects/iot.paho](https://projects.eclipse.org/projects/iot.paho)
- Paho Android Client Page: [https://www.eclipse.org/paho/clients/android/](https://www.eclipse.org/paho/clients/android/)
- GitHub: [https://github.com/eclipse/paho.mqtt.android](https://github.com/eclipse/paho.mqtt.android)
- Twitter: [@eclipsepaho](https://twitter.com/eclipsepaho)
- Issues: [https://github.com/eclipse/paho.mqtt.android/issues](https://github.com/eclipse/paho.mqtt.android/issues)
- Mailing-list: [https://dev.eclipse.org/mailman/listinfo/paho-dev](https://dev.eclipse.org/mailman/listinfo/paho-dev)

