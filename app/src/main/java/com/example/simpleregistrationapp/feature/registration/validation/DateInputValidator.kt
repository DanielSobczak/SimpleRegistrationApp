package com.example.simpleregistrationapp.feature.registration.validation

import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationError
import org.threeten.bp.LocalDate

class DateInputValidator {

    fun validate(input: LocalDate?): ValidationError? {
        return when (input) {
            null -> ValidationError.EmptyField
            else -> null
        }
    }
}
