package com.example.simpleregistrationapp.feature.registration.validation

import com.example.simpleregistrationapp.feature.registration.RegistrationRequest
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.*

class UserValidator {

    fun validate(request: RegistrationRequest): ValidationResponse {
        var nameError: ValidationError? = null
        var emailError: ValidationError? = null
        var dateOfBirthError: ValidationError? = null
        var shouldFocus = true
        if (request.name.trim().isEmpty()) {
            nameError = ValidationError.EmptyField(shouldFocus)
            shouldFocus = false
        }

        if (request.email.trim().isEmpty()) {
            emailError = ValidationError.EmptyField(shouldFocus)
            shouldFocus = false
        } else if (request.email.length < 2) {
            emailError = ValidationError.InvalidFormat(shouldFocus)
            shouldFocus = false
        }

        if (request.dateOfBirth == null) {
            dateOfBirthError = ValidationError.EmptyField(shouldFocus)
        }

        return if (listOfNotNull(nameError, emailError, dateOfBirthError).isEmpty()) {
            PassedValidation
        } else {
            ValidationFailed(nameError, emailError, dateOfBirthError)
        }
    }
}


