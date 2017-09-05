package com.wecon.mangob.mqttexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttMessage

/**
 * Created by mangob on 2017. 9. 4..
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mqtt = MqttHandler(this)
        mqtt.addCallback(object: ClientMqttCallback{
            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                if(reconnect) showStatus(">> Reconnected to : ${serverURI}")
                else showStatus(">> Connected to : ${serverURI}")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                showStatus("Incoming message : " + message)
            }

            override fun connectionLost(cause: Throwable?) {
                showStatus(">> Connection Lost")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                showStatus(">> Delivery Complete : ${token?.message}")
            }

            override fun onConnectionSuccess(asyncActionToken: IMqttToken?) {
                showStatus(">> Connection Success")

                mqtt.subacribe("/test/mangob", object: IMqttActionListener{
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        showStatus("subscribe success")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        showStatus("subscribe failure")
                    }
                })
            }

            override fun onConnectionFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                showStatus("connection failure")
            }
        })
        mqtt.connect()

        fab.setOnClickListener {
            mqtt?.publish("/test/mangob", "Hello mqtt", object: IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    showStatus("publish success")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    showStatus("publish failure")
                }
            })
        }
    }

    private fun showStatus(txt: String) {
        container.addView(TextView(this).apply { text = txt })
    }



}
