package com.wecon.mangob.mqttexample

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

/**
 * Created by mangob on 2017. 9. 4..
 */
class MqttHandler(val context: Context) {

    private var client: MqttAndroidClient ?= null

    private val SERVER_URL = "tcp://iot.eclipse.org:1883"
    private val CLIENT_ID = "mangob"
    private var connected = false

    private var mqttOptions = MqttConnectOptions().apply {
        isAutomaticReconnect = true
        isCleanSession = false
        connectionTimeout = 30
        keepAliveInterval = 60

    }

    private var disconnectedBufferOptions = DisconnectedBufferOptions().apply {
        isBufferEnabled = true
        bufferSize = 100
        isPersistBuffer = false
        isDeleteOldestMessages = false
    }

    private var mqttCallback: MqttCallbackExtended = object: MqttCallbackExtended {
        override fun connectComplete(reconnect: Boolean, serverURI: String?) {
            for(callback in callbackList) callback.connectComplete(reconnect, serverURI)
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            for(callback in callbackList) callback.messageArrived(topic, message)
        }

        override fun connectionLost(cause: Throwable?) {
            connected = false
            for(callback in callbackList) callback.connectionLost(cause)
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            for(callback in callbackList) callback.deliveryComplete(token)
        }
    }

    private var actionCallback: IMqttActionListener = object: IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            connected = true
            for(callback in callbackList) callback.onConnectionSuccess(asyncActionToken)
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            connected = false
            for(callback in callbackList) callback.onConnectionFailure(asyncActionToken, exception)
        }
    }


    private var callbackList = ArrayList<ClientMqttCallback>()

    fun connect() {
        if(isConnected()) return

        try {
            client = MqttAndroidClient(context, SERVER_URL, CLIENT_ID)
            client?.apply {
                setCallback(mqttCallback)
                connect(mqttOptions, context, actionCallback)
            }
        } catch (ex: MqttException) {

        }
    }

    fun disconnect() {
        if(!isConnected()) return

        try {
            client?.disconnect(context, actionCallback)
        } catch (ex: MqttException) {

        }
    }

    fun subacribe(topic: String, listener: IMqttActionListener) {
        if(!isConnected()) return

        try {
            client?.subscribe(topic, 0, context, listener)
        } catch (ex: MqttException) {

        }
    }

    fun unsubscribe(topic: String, listener: IMqttActionListener) {
        if(!isConnected()) return

        try {
            client?.unsubscribe(topic, context, listener)
        } catch (ex: MqttException) {

        }
    }

    fun publish(topic: String, message: String, listener: IMqttActionListener) {
        if(!isConnected()) return

        try {
            client?.publish(topic, MqttMessage(message.toByteArray()), context, listener)
        } catch (ex: MqttException) {

        }
    }

    fun isConnected(): Boolean {
        client?.apply { return true }; return false
    }

    fun addCallback(callback: ClientMqttCallback) {
        callbackList.add(callback)
    }

}