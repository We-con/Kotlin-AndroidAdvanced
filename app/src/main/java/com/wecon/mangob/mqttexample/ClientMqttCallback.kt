package com.wecon.mangob.mqttexample

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttMessage

/**
 * Created by mangob on 2017. 9. 4..
 */
interface ClientMqttCallback {

    fun connectComplete(reconnect: Boolean, serverURI: String?)

    fun messageArrived(topic: String?, message: MqttMessage?)

    fun connectionLost(cause: Throwable?)

    fun deliveryComplete(token: IMqttDeliveryToken?)

    fun onConnectionSuccess(asyncActionToken: IMqttToken?)

    fun onConnectionFailure(asyncActionToken: IMqttToken?, exception: Throwable?)
}