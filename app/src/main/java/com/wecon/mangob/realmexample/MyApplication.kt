package com.wecon.mangob.realmexample

import android.app.Application
import io.realm.Realm

/**
 * Created by mangob on 2017. 8. 31..
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}