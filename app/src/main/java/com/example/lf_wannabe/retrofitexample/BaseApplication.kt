package com.example.lf_wannabe.retrofitexample

import android.app.Application

/**
 * Created by lf_wannabe on 06/09/2017.
 */
class BaseApplication: Application() {
    companion object {
        var customService: CustomService = CustomService.retrofit.create(CustomService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
    }


}