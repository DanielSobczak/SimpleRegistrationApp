package com.example.simpleregistrationapp.di

import android.app.Application
import androidx.room.Room
import com.example.simpleregistrationapp.storage.RoomDatabase
import com.example.simpleregistrationapp.storage.user.UserEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): RoomDatabase {
        return Room.databaseBuilder(
            app,
            RoomDatabase::class.java, "main-database"
        ).build()
    }


    @Provides
    fun provideUserDao(roomDatabase: RoomDatabase): UserEntityDao {
        return roomDatabase.userDao()
    }

}

