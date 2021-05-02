package com.example.simpleregistrationapp.feature.registration.validation

import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationError

class NameInputValidator {
    fun validate(input: String): ValidationError? {
        return when {
            input.isBlank() -> ValidationError.EmptyField
            input.length < 2 -> ValidationError.InvalidFormat
            else -> null
        }
    }
}
