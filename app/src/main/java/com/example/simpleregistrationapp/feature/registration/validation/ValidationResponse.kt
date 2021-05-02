package com.example.simpleregistrationapp.feature.registration.validation

sealed class ValidationResponse {
    object PassedValidation : ValidationResponse()
    data class ValidationFailed(
        val nameError: ValidationError? = null,
        val emailError: ValidationError? = null,
        val dateOfBirthError: ValidationError? = null
    ) : ValidationResponse()

    sealed class ValidationError {
        object EmptyField : ValidationError()
        object InvalidFormat : ValidationError()
    }
}
