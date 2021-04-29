package com.example.simpleregistrationapp.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simpleregistrationapp.storage.user.UserEntity
import com.example.simpleregistrationapp.storage.user.UserEntityDao

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserEntityDao
}
