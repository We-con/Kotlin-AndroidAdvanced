package com.wecon.mangob.kotsonexample

/**
 * Created by mangob on 2017. 9. 5..
 */
data class Person (
        var name: String = "IU",
        var age: Int = 25,
        var job: String = "Singer",
        var album: ArrayList<String> = ArrayList<String>().apply {
            add("pallete")
            add("사랑이 잘")
            add("밤편지")
        }
)