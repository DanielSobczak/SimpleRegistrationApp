package com.example.simpleregistrationapp.feature.registration

import java.util.*

data class RegistrationRequest(
    val name: String = "",
    val email: String = "",
    val dateOfBirth: Date? = null,
)
