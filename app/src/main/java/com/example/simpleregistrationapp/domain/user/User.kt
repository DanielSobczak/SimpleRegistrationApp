package com.example.simpleregistrationapp.domain.user

import org.threeten.bp.LocalDate

data class User(
    val name: String,
    val email: String,
    val dateOfBirth: LocalDate,
)
