package com.example.simpleregistrationapp.feature.confirmation

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.AssistedViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.hiltMavericksViewModelFactory
import com.example.simpleregistrationapp.feature.utils.LoadingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ConfirmationViewModel @AssistedInject constructor(
    @Assisted initialState: ConfirmationState,
    private val getUserUseCase: GetUserUseCase,
) :
    MavericksViewModel<ConfirmationState>(initialState) {

    init {
        viewModelScope.launch {
            getUserUseCase.getRegisteredUser().collect { result ->
                when (result) {
                    is FetchUserResult.UnhandledError -> setState { copy(loadingState = LoadingState.Error) }
                    FetchUserResult.Loading -> setState { copy(loadingState = LoadingState.Loading) }
                    FetchUserResult.NoUserFound -> setState { copy(loadingState = LoadingState.Error) }
                    is FetchUserResult.Success -> setState {
                        copy(
                            loadingState = LoadingState.Ready,
                            user = result.user
                        )
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ConfirmationViewModel, ConfirmationState> {
        override fun create(state: ConfirmationState): ConfirmationViewModel
    }

    companion object :
        MavericksViewModelFactory<ConfirmationViewModel, ConfirmationState> by hiltMavericksViewModelFactory()

}
