package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksState
import java.util.*

data class RegistrationState(
    val name: String = "",
    val email: String = "",
    val dateOfBirth: Date? = null,
    val formattedDateOfBirth: String = ""
) : MavericksState
