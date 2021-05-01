package com.example.simpleregistrationapp.storage.user

import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.domain.user.UserStorage
import javax.inject.Inject

class RoomUserStorage @Inject constructor(
    private val userDao: UserEntityDao,
) : UserStorage {

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
