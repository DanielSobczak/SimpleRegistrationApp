package com.example.simpleregistrationapp.feature.confirmation

import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.feature.utils.dummyUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
class GetUserUseCaseImplTest {

    private lateinit var sut: GetUserUseCase
    private val testDispatcher = TestCoroutineDispatcher()
    private val mockUserStorage = mock<UserStorage>()

    @Before
    fun init() {
        sut = GetUserUseCaseImpl(
            mockUserStorage,
            testDispatcher
        )
    }

    @Test
    fun obtainingExistingUser() = testDispatcher.runBlockingTest {
        given(mockUserStorage.getAll()).willReturn(listOf(dummyUser))
        val emits = sut.getRegisteredUser().toList()
        assertThat(emits).containsExactly(
            FetchUserResult.Loading,
            FetchUserResult.Success(dummyUser)
        )
    }

    @Test
    fun obtainingUserFromEmptyStorage() = testDispatcher.runBlockingTest {
        given(mockUserStorage.getAll()).willReturn(emptyList())
        val emits = sut.getRegisteredUser().toList()
        assertThat(emits).containsExactly(
            FetchUserResult.Loading,
            FetchUserResult.NoUserFound
        )
    }
}
