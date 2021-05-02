package com.example.simpleregistrationapp.feature.registration.validation

import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationError
import java.util.*

class DateInputValidator {

    fun validate(input: Date?): ValidationError? {
        return when (input) {
            null -> ValidationError.EmptyField
            else -> null
        }
    }
}
