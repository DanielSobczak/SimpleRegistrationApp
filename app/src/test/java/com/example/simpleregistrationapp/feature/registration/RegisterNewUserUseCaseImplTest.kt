package com.example.simpleregistrationapp.feature.registration

import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.feature.utils.dummyUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
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
            testDispatcher
        )
    }

    @Test
    fun registeringNewUserWithAllValidData() = testDispatcher.runBlockingTest {
        val emits = sut.registerNewUser(dummyUser).toList()
        assertThat(emits).containsExactly(
            RegistrationResult.Loading,
            RegistrationResult.Success
        )
    }

}
