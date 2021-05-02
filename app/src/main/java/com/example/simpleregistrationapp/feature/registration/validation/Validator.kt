package com.example.simpleregistrationapp.feature.registration.validation

import com.example.simpleregistrationapp.feature.registration.RegistrationRequest
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.PassedValidation
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationFailed
import javax.inject.Inject

class UserValidator @Inject constructor() {
    private val nameValidator = NameInputValidator()
    private val dateValidator = DateInputValidator()
    private val emailValidator = EmailInputValidator()

    fun validate(request: RegistrationRequest): ValidationResponse {
        val nameError = nameValidator.validate(request.name)
        val emailError = emailValidator.validate(request.email)
        val dateOfBirthError = dateValidator.validate(request.dateOfBirth)

        return if (listOfNotNull(nameError, emailError, dateOfBirthError).isEmpty()) {
            PassedValidation
        } else {
            ValidationFailed(nameError, emailError, dateOfBirthError)
        }
    }
}
