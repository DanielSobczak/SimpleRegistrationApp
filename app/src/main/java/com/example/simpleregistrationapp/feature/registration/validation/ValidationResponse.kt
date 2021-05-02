package com.example.simpleregistrationapp.feature.registration.validation

sealed class ValidationResponse {
    object PassedValidation : ValidationResponse()
    data class ValidationFailed(
        val nameError: ValidationError? = null,
        val emailError: ValidationError? = null,
        val dateOfBirthError: ValidationError? = null,
    ) : ValidationResponse()

    sealed class ValidationError(open val shouldFocus: Boolean) {
        data class EmptyField(override val shouldFocus: Boolean) : ValidationError(shouldFocus)
        data class InvalidFormat(override val shouldFocus: Boolean) : ValidationError(shouldFocus)
    }
}
