package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksState
import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.feature.utils.LoadingState
import java.util.*

data class RegistrationState(
    val formattedDateOfBirth: String = "",
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val dateOfBirth: Date? = null,
    val dateOfBirthError: String? = null,
    val loadingState: LoadingState = LoadingState.Ready
    // val formErrors: List<ValidationResponse.ValidationError> = emptyList(),
) : MavericksState {

    fun mapToUser(): User = User(
        name,
        email,
        dateOfBirth!!
    )

}


