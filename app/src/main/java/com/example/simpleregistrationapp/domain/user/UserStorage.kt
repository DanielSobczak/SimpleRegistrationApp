package com.example.simpleregistrationapp.domain.user

interface UserStorage {
    fun insert(user: User)
    fun getAll(): List<User>
    fun deleteAll()
}
