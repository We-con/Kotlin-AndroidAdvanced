package com.wecon.mangob.realmexample.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

/**
 * Created by mangob on 2017. 8. 31..
 */
open class Dog : RealmObject() {
    var name: String? = null
    @LinkingObjects("dog")
    val owners: RealmResults<Person>? = null
}