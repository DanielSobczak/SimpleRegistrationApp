package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.AssistedViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.hiltMavericksViewModelFactory
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse
import com.example.simpleregistrationapp.feature.utils.LoadingState
import com.example.simpleregistrationapp.utils.LOCAL_DATE_FORMAT
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class RegistrationViewModel @AssistedInject constructor(
    @Assisted initialState: RegistrationState,
    private val registerNewUserUseCase: RegisterNewUserUseCase
) :
    MavericksViewModel<RegistrationState>(initialState) {

    private val dateFormatter = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT)
    private val sideEffectsFlow = MutableSharedFlow<RegistrationSideEffects>()
    val sideEffectsFlowReceiver = sideEffectsFlow.asSharedFlow()

    fun updateName(name: String) = setState { copy(name = name, nameError = null) }
    fun updateEmail(email: String) = setState { copy(email = email, emailError = null) }
    fun updateDate(date: LocalDate) =
        setState {
            copy(
                dateOfBirth = date,
                dateOfBirthError = null,
                formattedDateOfBirth = date.format(dateFormatter),
            )
        }

    fun onRegisterClicked() {
        registerUser()
    }

    private fun registerUser() {
        withState { state ->
            viewModelScope.launch {
                registerNewUserUseCase
                    .registerNewUser(state.toRequest())
                    .collect {
                        reduceRegistrationResult(it)
                    }
            }
        }
    }

    private fun RegistrationState.toRequest() = RegistrationRequest(
        this.name,
        this.email,
        this.dateOfBirth
    )

    private fun reduceRegistrationResult(registrationResult: RegistrationResult) {
        when (registrationResult) {
            RegistrationResult.Loading -> setState {
                copy(loadingState = LoadingState.Loading)
            }
            RegistrationResult.Success -> {
                setState { copy(loadingState = LoadingState.Ready) }
                navigateToConfirmationScreen()
            }
            is RegistrationResult.UnhandledError -> setState {
                copy(loadingState = LoadingState.Error)
            }
            is RegistrationResult.InvalidFields -> setState {
                stateForValidationError(registrationResult.validationError)
            }
        }
    }

    private fun RegistrationState.stateForValidationError(
        validationError: ValidationResponse.ValidationFailed
    ): RegistrationState = copy(
        nameError = validationError.nameError?.asNameError(),
        emailError = validationError.emailError?.asEmailError(),
        dateOfBirthError = validationError.dateOfBirthError?.asDateError(),
        loadingState = LoadingState.Error
    )

    private fun navigateToConfirmationScreen() {
        viewModelScope.launch {
            sideEffectsFlow.emit(RegistrationSideEffects.OpenConfirmationScreen)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<RegistrationViewModel, RegistrationState> {
        override fun create(state: RegistrationState): RegistrationViewModel
    }

    companion object :
        MavericksViewModelFactory<RegistrationViewModel, RegistrationState> by hiltMavericksViewModelFactory()
}

private fun ValidationResponse.ValidationError.asNameError(): String {
    return when (this) {
        ValidationResponse.ValidationError.EmptyField -> "Missing username"
        ValidationResponse.ValidationError.InvalidFormat -> "Username format invalid"
    }
}

private fun ValidationResponse.ValidationError.asEmailError(): String {
    return when (this) {
        ValidationResponse.ValidationError.EmptyField -> "Missing email"
        ValidationResponse.ValidationError.InvalidFormat -> "Email format invalid"
    }
}

private fun ValidationResponse.ValidationError.asDateError(): String {
    return when (this) {
        ValidationResponse.ValidationError.EmptyField -> "Missing date of birth"
        ValidationResponse.ValidationError.InvalidFormat -> "Date format is not valid"
    }
}


