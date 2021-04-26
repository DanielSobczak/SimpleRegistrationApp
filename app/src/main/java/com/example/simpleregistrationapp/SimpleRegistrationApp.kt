package com.example.simpleregistrationapp

import android.app.Application
import com.airbnb.mvrx.Mavericks

class SimpleRegistrationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }

}
