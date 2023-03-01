package com.nauhalf.ricknmorty.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.data.character.api.repository.CharacterRepository
import com.nauhalf.ricknmorty.domain.character.usecase.FetchCharacterUseCase
import com.nauhalf.ricknmorty.util.generateDummyCharacter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
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
class FetchCharacterUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var characterRepository: CharacterRepository

    private lateinit var useCase: FetchCharacterUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FetchCharacterUseCase(characterRepository)
    }

    @Test
    fun fetchCharacterUseCase_invoke() = runTest {
        // given
        val data = generateDummyCharacter(1)
        val slot = slot<Int>()
        coEvery {
            characterRepository.fetchCharacter(capture(slot))
        } returns flow {
            emit(RickMortyResponse.Success(data = data))
        }

        // when
        val result = useCase.invoke(1)
        // then
        coVerify {
            characterRepository.fetchCharacter(slot.captured)
        }
        assertTrue(result.first() is RickMortyResponse.Success)
        val resultData = (result.first() as RickMortyResponse.Success).data

        assertEquals(resultData, data)
        assertEquals(resultData.id, slot.captured)

    }

}
