package com.example.simpleregistrationapp

import android.app.Application
import androidx.room.Room
import com.airbnb.mvrx.Mavericks
import com.example.simpleregistrationapp.storage.RoomDatabase

class SimpleRegistrationApp : Application() {

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java, "main-database"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }

}
