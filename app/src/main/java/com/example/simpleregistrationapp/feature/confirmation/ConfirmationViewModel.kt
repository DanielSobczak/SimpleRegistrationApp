package com.example.simpleregistrationapp.feature.confirmation

import com.airbnb.mvrx.MavericksViewModel
import com.example.simpleregistrationapp.feature.utils.LoadingState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

class ConfirmationViewModel(initialState: ConfirmationState) :
    MavericksViewModel<ConfirmationState>(initialState) {

    private val repository = DummyUserRepository()

    init {
        viewModelScope.launch {
            repository.fetchUser().collect { result ->

                when (result) {
                    is FetchUserResult.Fail -> setState { copy(loadingState = LoadingState.Error) }
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

data class User(
    val name: String,
    val email: String,
    val dateOfBirth: Date,
)

