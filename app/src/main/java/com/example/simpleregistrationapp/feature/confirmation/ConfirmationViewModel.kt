package com.example.simpleregistrationapp.feature.confirmation

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.AssistedViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.hiltMavericksViewModelFactory
import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.feature.utils.LoadingState
import com.example.simpleregistrationapp.storage.user.RoomUserStorage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ConfirmationViewModel @AssistedInject constructor(
    @Assisted initialState: ConfirmationState,
    private val roomUserStorage: RoomUserStorage,
) :
    MavericksViewModel<ConfirmationState>(initialState) {

    private val repository = DummyUserRepository()

    init {
        viewModelScope.launch {
            val user = withContext(Dispatchers.Default) {
                roomUserStorage.getAll().first()
            }
            repository.fetchUser().collect { result ->

                when (result) {
                    is FetchUserResult.Fail -> setState { copy(loadingState = LoadingState.Error) }
                    FetchUserResult.Loading -> setState { copy(loadingState = LoadingState.Loading) }
                    FetchUserResult.NoUserFound -> setState { copy(loadingState = LoadingState.Error) }
                    is FetchUserResult.Success -> setState {
                        copy(
                            loadingState = LoadingState.Ready,
                            user = user
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

interface UserRepository {
    suspend fun fetchUser(): Flow<FetchUserResult>
}

class DummyUserRepository : UserRepository {

    override suspend fun fetchUser(): Flow<FetchUserResult> {
        return flow<FetchUserResult> {
            emit(FetchUserResult.Loading)
            delay(5000)
            emit(FetchUserResult.Success(User("Daniel", "foofoo@bar.com", Date())))
        }
    }

}


sealed class FetchUserResult {
    object Loading : FetchUserResult()
    object NoUserFound : FetchUserResult()
    data class Success(val user: User) : FetchUserResult()
    data class Fail(val exception: Exception) : FetchUserResult()
}

