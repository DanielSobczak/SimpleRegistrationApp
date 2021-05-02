package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.AssistedViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RegistrationViewModel @AssistedInject constructor(
    @Assisted initialState: RegistrationState,
    private val registerNewUserUseCase: RegisterNewUserUseCase
) :
    MavericksViewModel<RegistrationState>(initialState) {

    private val formatter = SimpleDateFormat("dd-MM-yyyy")
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
            registerUser()
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


