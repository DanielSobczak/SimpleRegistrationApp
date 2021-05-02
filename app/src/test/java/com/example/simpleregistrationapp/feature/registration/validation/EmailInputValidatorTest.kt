package com.example.simpleregistrationapp.feature.registration.validation

import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationError
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class EmailInputValidatorTest {

    private val sut = EmailInputValidator()

    @Test
    fun givenEmptyEmailAddressReturnsError() {
        val validationError = sut.validate("")
        assertThat(validationError).isEqualTo(ValidationError.EmptyField)
    }

    @Test
    fun givenEmailAddressWithoutAtReturnsError() {
        assertIsNotValidFormat("foobar.com")
    }

    @Test
    fun givenEmailAddressWithMultipleAtReturnsError() {
        assertIsNotValidFormat("foo@bar@bar.com")
    }

    @Test
    fun givenEmailAddressWithSpecialCharactersReturnsError() {
        assertIsNotValidFormat("""a"b(c)d,e:f;g<h>i[j\k]l@example.com""")
    }

    @Test
    fun givenEmailAddressWithIncorrectDomainReturnsError() {
        assertIsNotValidFormat("foo@bar")
        assertIsNotValidFormat("foo@.com")
        assertIsNotValidFormat("foo@b.a")
    }

    @ParameterizedTest(name = "[{index}] given {0} should be valid")
    @ValueSource(strings = ["f@b.co", "foo@bar.com", "f@longlongaddres.com"])
    fun givenProperEmailAddressIsValid(email: String) {
        assertIsValid(email)
    }

    private fun assertIsValid(email: String) {
        val validationError = sut.validate(email)
        assertThat(validationError).isNull()
    }

    private fun assertIsNotValidFormat(email: String) {
        val validationError = sut.validate(email)
        assertThat(validationError).isEqualTo(ValidationError.InvalidFormat)
    }

}
