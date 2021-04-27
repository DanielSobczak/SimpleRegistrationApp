package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksViewModel
import com.example.simpleregistrationapp.feature.registration.ValidationResponse.ValidationError
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegistrationViewModel(initialState: RegistrationState) :
    MavericksViewModel<RegistrationState>(initialState) {

    private val formatter = SimpleDateFormat("dd-MM-yyyy")
    private val userValidator = UserValidator()

    fun updateName(name: String) = setState { copy(name = name) }
    fun updateEmail(email: String) = setState { copy(email = email) }
    fun updateDate(date: Date) =
        setState {
            copy(
                dateOfBirth = date,
                formattedDateOfBirth = formatter.format(date),
            )
        }

    fun onRegisterClicked() {
        withState { state ->
            when (val validate = userValidator.validate(state.toRequest())) {
                ValidationResponse.PassedValidation -> navigateToConfirmationScreen()
                is ValidationResponse.ValidationFailed -> {
                    setState {
                        copy(formErrors = validate.errors)
                    }
                }
            }
        }
    }

    private fun navigateToConfirmationScreen() {
        TODO("implement navigation")
    }
}

private fun RegistrationState.toRequest() = RegistrationRequest(
    this.name,
    this.email,
    this.dateOfBirth
)

data class RegistrationRequest(
    val name: String = "",
    val email: String = "",
    val dateOfBirth: Date? = null,
)

class UserValidator {

    fun validate(request: RegistrationRequest): ValidationResponse {
        val results = ArrayList<ValidationError>()
        var shouldFocus = true
        if (request.name.trim().isEmpty()) {
            results.add(ValidationError.MissingName(shouldFocus))
            shouldFocus = false
        }

        if (request.email.trim().isEmpty()) {
            results.add(ValidationError.MissingEmail(shouldFocus))
            shouldFocus = false
        }

        if (request.dateOfBirth == null) {
            results.add(ValidationError.MissingDateOfBirth(shouldFocus))
        }

        return if (results.isEmpty()) {
            ValidationResponse.PassedValidation
        } else {
            ValidationResponse.ValidationFailed(results)
        }
    }

}

sealed class ValidationResponse {
    object PassedValidation : ValidationResponse()
    data class ValidationFailed(
        val errors: List<ValidationError>
    ) : ValidationResponse()

    sealed class ValidationError(open val shouldFocus: Boolean) {
        data class MissingName(override val shouldFocus: Boolean) : ValidationError(shouldFocus)
        data class MissingEmail(override val shouldFocus: Boolean) : ValidationError(shouldFocus)
        data class IncorrectEmailFormat(override val shouldFocus: Boolean) :
            ValidationError(shouldFocus)

        data class MissingDateOfBirth(override val shouldFocus: Boolean) :
            ValidationError(shouldFocus)

    }
}
