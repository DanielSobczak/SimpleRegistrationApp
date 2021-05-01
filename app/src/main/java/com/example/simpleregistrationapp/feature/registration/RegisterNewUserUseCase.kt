package com.example.simpleregistrationapp.feature.registration

import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.domain.user.UserStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

sealed class RegistrationResult {
    object Loading : RegistrationResult()
    object Success : RegistrationResult()
    data class UnhandledError(val exception: Exception) : RegistrationResult()

}

interface RegisterNewUserUseCase {
    fun registerNewUser(user: User): Flow<RegistrationResult>
}

class RegisterNewUserUseCaseImpl @Inject constructor(
    private val userStorage: UserStorage
) : RegisterNewUserUseCase {

    override fun registerNewUser(user: User): Flow<RegistrationResult> = flow {
        emit(RegistrationResult.Loading)
        try {
            userStorage.insert(user)
            emit(RegistrationResult.Success)
        } catch (exception: Exception) {
            emit(RegistrationResult.UnhandledError(exception))
        }
    }.flowOn(Dispatchers.Default)

}
