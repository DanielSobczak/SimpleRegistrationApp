package com.example.simpleregistrationapp.feature.registration.validation

import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationError

class EmailInputValidator {

    private val emailPattern =
        """[a-zA-Z0-9+._%\-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{0,25})+""".toRegex()

    fun validate(input: String): ValidationError? {
        return when {
            input.isBlank() -> ValidationError.EmptyField
            !hasValidEmailFormat(input) -> ValidationError.InvalidFormat
            else -> null
        }
    }

    private fun hasValidEmailFormat(input: String): Boolean {
        return emailPattern.matches(input)
    }
}
