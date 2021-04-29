package com.example.simpleregistrationapp.storage.user

import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.storage.RoomDatabase

class RoomUserStorage(roomDatabase: RoomDatabase) : UserStorage {

    private val userDao = roomDatabase.userDao()

    override fun insert(user: User) {
        userDao.insert(user.toEntity())
    }

    override fun getAll(): List<User> {
        return userDao.getAll().map { it.toDomain() }
    }

    override fun deleteAll() {
        userDao.deleteAll()
    }
}
