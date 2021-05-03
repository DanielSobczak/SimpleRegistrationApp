package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.withState
import com.example.simpleregistrationapp.feature.registration.RegistrationResult.InvalidFields
import com.example.simpleregistrationapp.feature.registration.RegistrationResult.Success
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationError
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationFailed
import com.example.simpleregistrationapp.feature.utils.LoadingState
import com.example.simpleregistrationapp.feature.utils.MavericksViewModelTestExtension
import com.example.simpleregistrationapp.feature.utils.validRegistrationRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.threeten.bp.LocalDate

@ExperimentalCoroutinesApi
@ExtendWith(MavericksViewModelTestExtension::class)
internal class RegistrationViewModelTest {
    private val mockRegisterUseCase = mock<RegisterNewUserUseCase>()
    private val dummyLiterals = RegistrationLiterals(
        emptyNameError = "empty name",
        invalidNameError = "invalid name",
        emptyEmailError = "empty email",
        invalidEmailError = "invalid email",
        emptyDateError = "empty date",
        invalidDateError = "invalid date"
    )

    @Test
    fun `given valid registration data When register clicked Then state is reduced with success`() =
        runBlockingTest {
            givenUseCaseReturns(Success)
            val initialState = RegistrationState(
                name = validRegistrationRequest.name,
                email = validRegistrationRequest.email,
                dateOfBirth = validRegistrationRequest.dateOfBirth
            )
            val viewModel = RegistrationViewModel(initialState, mockRegisterUseCase, dummyLiterals)
            viewModel.onRegisterClicked()
            viewModel.assertStateEquals(
                initialState.copy(
                    loadingState = LoadingState.Ready,
                )
            )
        }

    @Test
    fun `given empty name response When register clicked Then state is reduced with error`() =
        runBlockingTest {
            givenUseCaseReturns(InvalidFields(ValidationFailed(nameError = ValidationError.EmptyField)))
            val initialState = RegistrationState()
            val viewModel = RegistrationViewModel(initialState, mockRegisterUseCase, dummyLiterals)
            viewModel.onRegisterClicked()
            viewModel.assertStateEquals(
                initialState.copy(
                    loadingState = LoadingState.Error,
                    nameError = "empty name"
                )
            )
        }

    @Test
    fun `given empty email response When register clicked Then state is reduced with error`() =
        runBlockingTest {
            givenUseCaseReturns(InvalidFields(ValidationFailed(emailError = ValidationError.EmptyField)))
            val initialState = RegistrationState()
            val viewModel = RegistrationViewModel(initialState, mockRegisterUseCase, dummyLiterals)
            viewModel.onRegisterClicked()
            viewModel.assertStateEquals(
                initialState.copy(
                    loadingState = LoadingState.Error,
                    emailError = "empty email"
                )
            )
        }

    @Test
    fun `given empty date response When register clicked Then state is reduced with error`() =
        runBlockingTest {
            givenUseCaseReturns(InvalidFields(ValidationFailed(dateOfBirthError = ValidationError.EmptyField)))
            val initialState = RegistrationState()
            val viewModel = RegistrationViewModel(initialState, mockRegisterUseCase, dummyLiterals)
            viewModel.onRegisterClicked()
            viewModel.assertStateEquals(
                initialState.copy(
                    loadingState = LoadingState.Error,
                    dateOfBirthError = "empty date"
                )
            )
        }

    @Test
    fun `given state with name error When name is updated Then error is cleared`() =
        runBlockingTest {
            val initialState = RegistrationState(nameError = "error to clear")
            val viewModel = RegistrationViewModel(initialState, mockRegisterUseCase, dummyLiterals)
            viewModel.updateName("newName")
            viewModel.assertStateEquals(
                initialState.copy(
                    name = "newName",
                    nameError = null
                )
            )
        }

    @Test
    fun `given state with email error When email is updated Then error is cleared`() =
        runBlockingTest {
            val initialState = RegistrationState(emailError = "error to clear")
            val viewModel = RegistrationViewModel(initialState, mockRegisterUseCase, dummyLiterals)
            viewModel.updateEmail("new@email.com")
            viewModel.assertStateEquals(
                initialState.copy(
                    email = "new@email.com",
                    emailError = null
                )
            )
        }

    @Test
    fun `given state with date error When date is updated Then error is cleared`() =
        runBlockingTest {
            val newDateOfBirth = LocalDate.of(2000, 1, 1)
            val initialState = RegistrationState(dateOfBirthError = "error to clear")
            val viewModel = RegistrationViewModel(initialState, mockRegisterUseCase, dummyLiterals)
            viewModel.updateDate(newDateOfBirth)
            viewModel.assertStateEquals(
                initialState.copy(
                    formattedDateOfBirth = "01/01/2000",
                    dateOfBirth = newDateOfBirth,
                    dateOfBirthError = null
                )
            )
        }

    private fun givenUseCaseReturns(vararg result: RegistrationResult) {
        given(mockRegisterUseCase.registerNewUser(any())).willReturn(flowOf(*result))
    }
}

private fun <T : MavericksState> MavericksViewModel<T>.assertStateEquals(expected: T) {
    withState(this) {
        assertThat(it).isEqualTo(expected)
    }
}
