package com.wecon.mangob.kotsonexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.salomonbrys.kotson.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creating JsonObject
        val obj: JsonObject = jsonObject(
                "name" to "IU",
                "age" to 25,
                "job" to "Singer"
        )

        // Creating JsonArray
        val arr: JsonArray = jsonArray("Palette", "사랑이 잘", "밤편지")

        // Creating Sample JsonObject
        val iu = jsonObject(
                "name" to "IU",
                "age" to 25,
                "job" to "Singer",
                "album" to jsonArray("Palette", "사랑이 잘", "밤편지")
        )

        // Creating Sample Person Object
        var me: Person = Person(
                "Mangob",
                26,
                "Student",
                ArrayList<String>().apply { add("EcoFarm") }
        )

        text_json_object.text = obj.toString()
        text_json_array.text = me.toString()
        text_json_to_person.text = Gson().fromJson<Person>(iu).toString()
        text_person_to_json.text = Gson().toJson(me)
    }

}
