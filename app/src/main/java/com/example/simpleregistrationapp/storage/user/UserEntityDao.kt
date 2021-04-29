package com.example.simpleregistrationapp.storage.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserEntityDao {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>

    @Insert
    fun insert(user: UserEntity)

    @Query("DELETE FROM userEntity")
    fun deleteAll()
}
