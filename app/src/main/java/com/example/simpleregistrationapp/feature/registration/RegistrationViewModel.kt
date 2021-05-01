package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.AssistedViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.hiltMavericksViewModelFactory
import com.example.simpleregistrationapp.feature.registration.ValidationResponse.ValidationError
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegistrationViewModel @AssistedInject constructor(
    @Assisted initialState: RegistrationState,
    private val registerNewUserUseCase: RegisterNewUserUseCase
) :
    MavericksViewModel<RegistrationState>(initialState) {

    private val formatter = SimpleDateFormat("dd-MM-yyyy")
    private val userValidator = UserValidator()
    private val sideEffectsFlow = MutableSharedFlow<RegistrationSideEffects>()
    val sideEffectsFlowReceiver = sideEffectsFlow.asSharedFlow()

    fun updateName(name: String) = setState { copy(name = name) }
    fun updateEmail(email: String) = setState { copy(email = email) }
    fun updateDate(date: Date) =
        setState {
            copy(
                dateOfBirth = date,
                formattedDateOfBirth = formatter.format(date),
            )
        }

    fun onRegisterClicked() {
        withState { state ->
            when (val validate = userValidator.validate(state.toRequest())) {
                ValidationResponse.PassedValidation -> registerUser()
                is ValidationResponse.ValidationFailed -> {
                    setState {
                        copy(formErrors = validate.errors)
                    }
                }
            }
        }
    }

    private fun registerUser() {
        withState {
            viewModelScope.launch {
                registerNewUserUseCase.registerNewUser(it.mapToUser()).collect {
                    when (it) {
                        RegistrationResult.Loading -> {
                        }
                        RegistrationResult.Success -> {
                            navigateToConfirmationScreen()
                        }
                        is RegistrationResult.UnhandledError -> {
                        }
                    }
                }
            }
        }
    }

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

private fun RegistrationState.toRequest() = RegistrationRequest(
    this.name,
    this.email,
    this.dateOfBirth
)

data class RegistrationRequest(
    val name: String = "",
    val email: String = "",
    val dateOfBirth: Date? = null,
)

class UserValidator {

    fun validate(request: RegistrationRequest): ValidationResponse {
        val results = ArrayList<ValidationError>()
        var shouldFocus = true
        if (request.name.trim().isEmpty()) {
            results.add(ValidationError.MissingName(shouldFocus))
            shouldFocus = false
        }

        if (request.email.trim().isEmpty()) {
            results.add(ValidationError.MissingEmail(shouldFocus))
            shouldFocus = false
        }

        if (request.dateOfBirth == null) {
            results.add(ValidationError.MissingDateOfBirth(shouldFocus))
        }

        return if (results.isEmpty()) {
            ValidationResponse.PassedValidation
        } else {
            ValidationResponse.ValidationFailed(results)
        }
    }

}

sealed class ValidationResponse {
    object PassedValidation : ValidationResponse()
    data class ValidationFailed(
        val errors: List<ValidationError>
    ) : ValidationResponse()

    sealed class ValidationError(open val shouldFocus: Boolean) {
        data class MissingName(override val shouldFocus: Boolean) : ValidationError(shouldFocus)
        data class MissingEmail(override val shouldFocus: Boolean) : ValidationError(shouldFocus)
        data class IncorrectEmailFormat(override val shouldFocus: Boolean) :
            ValidationError(shouldFocus)

        data class MissingDateOfBirth(override val shouldFocus: Boolean) :
            ValidationError(shouldFocus)

    }
}
