package com.example.simpleregistrationapp

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SimpleRegistrationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
        AndroidThreeTen.init(this);
    }

}
