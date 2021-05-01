package com.example.simpleregistrationapp.feature.confirmation

import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.domain.user.UserStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

sealed class FetchUserResult {
    object Loading : FetchUserResult()
    object NoUserFound : FetchUserResult()
    data class Success(val user: User) : FetchUserResult()
    data class UnhandledError(val exception: Exception) : FetchUserResult()
}

interface GetUserUseCase {
    fun getRegisteredUser(): Flow<FetchUserResult>
}

class GetUserUseCaseImpl @Inject constructor(
    private val userStorage: UserStorage,
    private val coroutineDispatcher: CoroutineDispatcher
) : GetUserUseCase {

    override fun getRegisteredUser(): Flow<FetchUserResult> = flow {
        emit(FetchUserResult.Loading)
        val user = userStorage.getAll().lastOrNull()
        if (user != null) {
            emit(FetchUserResult.Success(user))
        } else {
            emit(FetchUserResult.NoUserFound)
        }
    }.flowOn(coroutineDispatcher)

}
