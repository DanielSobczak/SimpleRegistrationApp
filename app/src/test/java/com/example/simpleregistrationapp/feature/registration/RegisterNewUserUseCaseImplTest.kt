package com.example.simpleregistrationapp.feature.registration

import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.feature.registration.validation.UserValidator
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse
import com.example.simpleregistrationapp.feature.utils.dummyUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class RegisterNewUserUseCaseImplTest {

    private lateinit var sut: RegisterNewUserUseCase
    private val testDispatcher = TestCoroutineDispatcher()
    private val mockUserStorage = mock<UserStorage>()

    @Before
    fun init() {
        sut = RegisterNewUserUseCaseImpl(
            mockUserStorage,
            UserValidator(),
            testDispatcher
        )
    }

    @Test
    fun registeringNewUserWithAllFieldsValid_willEmitSuccess() =
        testDispatcher.runBlockingTest {
            val emits = sut.registerNewUser(dummyUser).toList()
            assertThat(emits).containsExactly(
                RegistrationResult.Loading,
                RegistrationResult.Success
            )
        }

    @Test
    fun givenDatabaseError_WhenRegisteringNewUserWithAllFieldsValid_willEmitError() =
        testDispatcher.runBlockingTest {
            val databaseException = RuntimeException()
            given(mockUserStorage.insert(dummyUser)).willThrow(databaseException)
            val emits = sut.registerNewUser(dummyUser).toList()
            assertThat(emits).containsExactly(
                RegistrationResult.Loading,
                RegistrationResult.UnhandledError(databaseException)
            )
        }

    @Test
    fun whenRegisteringNewUserWithMissingMail_willEmitValidationError() =
        testDispatcher.runBlockingTest {
            val emits = sut.registerNewUser(dummyUser.copy(email = "")).toList()
            assertThat(emits).containsExactly(
                RegistrationResult.Loading,
                RegistrationResult.InvalidFields(
                    ValidationResponse.ValidationFailed(
                        emailError = ValidationResponse.ValidationError.EmptyField
                    )
                )
            )
        }
}
