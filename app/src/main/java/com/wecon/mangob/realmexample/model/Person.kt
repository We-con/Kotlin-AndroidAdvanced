package com.wecon.mangob.realmexample.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Created by mangob on 2017. 8. 31..
 */
open class Person(
        @PrimaryKey
        var id: Long = 0,
        var name: String = "",
        var age: Int = 0,

        // Other objects in a one-to-one relation must also subclass RealmObject
        var dog: Dog? = null,

        // One-to-many relations is simply a RealmList of the objects which also subclass RealmObject
        var cats: RealmList<Cat> = RealmList(),

        // You can instruct Realm to ignore a field and not persist it.
        @Ignore
        var tempReference: Int = 0
) : RealmObject() {

}