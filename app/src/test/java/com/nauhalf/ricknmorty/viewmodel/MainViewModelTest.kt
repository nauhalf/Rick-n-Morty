package com.nauhalf.ricknmorty.viewmodel

import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.domain.character.usecase.FetchAllCharacterUseCase
import com.nauhalf.ricknmorty.util.MainDispatcherRule
import com.nauhalf.ricknmorty.util.generateDummyCharacter
import com.nauhalf.ricknmorty.view.main.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    val mainDispatcher = MainDispatcherRule()

    @MockK
    lateinit var fetchAllCharacterUseCase: FetchAllCharacterUseCase

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(fetchAllCharacterUseCase)
    }

    @Test
    fun mainViewModel_loadAllCharacters() = runTest {
        // given
        val data = (1..20).map {
            generateDummyCharacter(it)
        }
        coEvery {
            fetchAllCharacterUseCase.invoke()
        } returns flow {
            emit(RickMortyResponse.Success(data = data))
        }

        // when
        viewModel.loadAllCharacter()
        advanceUntilIdle()
        // then
        coVerify { fetchAllCharacterUseCase.invoke() }
        assertTrue(viewModel.character.first() is RickMortyResponse.Success)
    }
}
