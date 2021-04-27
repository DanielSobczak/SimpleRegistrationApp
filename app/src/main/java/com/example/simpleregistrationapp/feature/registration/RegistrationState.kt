package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksState
import java.util.*

data class RegistrationState(
    val formattedDateOfBirth: String = "",
    val name: String = "",
    val email: String = "",
    val dateOfBirth: Date? = null,
    val formErrors: List<ValidationResponse.ValidationError> = emptyList(),
) : MavericksState
