package com.example.simpleregistrationapp.feature.registration.validation

import com.example.simpleregistrationapp.feature.registration.RegistrationRequest
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationError
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationFailed
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

private val correctRequest = RegistrationRequest(
    name = "Foo",
    email = "foo@bar.com",
    dateOfBirth = Date()
)

class UserValidatorTest {

    @Test
    fun givenAllEmptyFields() {
        val emptyRequest = RegistrationRequest()
        val sut = UserValidator()
        val result = sut.validate(emptyRequest)
        assertThat(result).isEqualTo(
            ValidationFailed(
                nameError = ValidationError.EmptyField(true),
                emailError = ValidationError.EmptyField(false),
                dateOfBirthError = ValidationError.EmptyField(false)
            )
        )
    }

    @Test
    fun givenProperFields() {
        val sut = UserValidator()
        val result = sut.validate(correctRequest)
        assertThat(result).isEqualTo(
            ValidationResponse.PassedValidation
        )
    }

    @Test
    fun givenEmptyName() {
        val sut = UserValidator()
        val result = sut.validate(correctRequest.copy(name = ""))
        assertThat(result).isEqualTo(
            ValidationFailed(
                nameError = ValidationError.EmptyField(true),
                emailError = null,
                dateOfBirthError = null
            )
        )
    }

    @Test
    fun givenNameWithOnlyOneCharacter() {
        val sut = UserValidator()
        val result = sut.validate(correctRequest.copy(name = "a"))
        assertThat(result).isEqualTo(
            ValidationFailed(
                nameError = ValidationError.InvalidFormat(true),
                emailError = null,
                dateOfBirthError = null
            )
        )
    }

    @Test
    fun givenEmptyEmail() {
        val sut = UserValidator()
        val result = sut.validate(correctRequest.copy(email = ""))
        assertThat(result).isEqualTo(
            ValidationFailed(
                nameError = null,
                emailError = ValidationError.EmptyField(true),
                dateOfBirthError = null
            )
        )
    }

    @Test
    fun givenIncorrectEmailFormat() {
        val sut = UserValidator()
        val result = sut.validate(correctRequest.copy(email = "foo.com"))
        assertThat(result).isEqualTo(
            ValidationFailed(
                nameError = null,
                emailError = ValidationError.InvalidFormat(true),
                dateOfBirthError = null
            )
        )
    }

    @Test
    fun givenEmptyDateOfBirth() {
        val sut = UserValidator()
        val result = sut.validate(correctRequest.copy(dateOfBirth = null))
        assertThat(result).isEqualTo(
            ValidationFailed(
                nameError = null,
                emailError = null,
                dateOfBirthError = ValidationError.EmptyField(true)
            )
        )
    }


}
