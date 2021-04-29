package com.example.simpleregistrationapp.domain.user

import java.util.*

data class User(
    val name: String,
    val email: String,
    val dateOfBirth: Date,
)
