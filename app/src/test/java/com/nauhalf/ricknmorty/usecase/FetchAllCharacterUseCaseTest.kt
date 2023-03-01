package com.nauhalf.ricknmorty.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.data.character.api.repository.CharacterRepository
import com.nauhalf.ricknmorty.domain.character.usecase.FetchAllCharacterUseCase
import com.nauhalf.ricknmorty.util.generateDummyCharacter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchAllCharacterUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var characterRepository: CharacterRepository

    private lateinit var useCase: FetchAllCharacterUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FetchAllCharacterUseCase(characterRepository)
    }

    @Test
    fun fetchAllCharacterUseCase_invoke() = runTest {
        // given
        val data = (1..20).map {
            generateDummyCharacter(it)
        }
        coEvery {
            characterRepository.fetchAllCharacters()
        } returns flow {
            emit(RickMortyResponse.Success(data = data))
        }

        // when
        val result = useCase.invoke()

        // then
        coVerify {
            characterRepository.fetchAllCharacters()
        }
        assertTrue(result.first() is RickMortyResponse.Success)
        assertEquals((result.first() as RickMortyResponse.Success).data, data)

    }

}
