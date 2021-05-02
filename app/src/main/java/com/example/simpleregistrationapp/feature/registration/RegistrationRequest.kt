package com.example.simpleregistrationapp.feature.registration

import org.threeten.bp.LocalDate

data class RegistrationRequest(
    val name: String = "",
    val email: String = "",
    val dateOfBirth: LocalDate? = null,
)
