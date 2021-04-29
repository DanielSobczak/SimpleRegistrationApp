package com.example.simpleregistrationapp.storage.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.feature.registration.ValidationResponse
import java.util.*

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val email: String,
    val dateOfBirth: Date,
) {
    fun toDomain() = User(name, email, dateOfBirth)
}


fun User.toEntity() = UserEntity(name = name, email = email, dateOfBirth = dateOfBirth)

