package com.nauhalf.ricknmorty.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.domain.character.usecase.FetchCharacterUseCase
import com.nauhalf.ricknmorty.domain.episode.usecase.FetchEpisodeUseCase
import com.nauhalf.ricknmorty.util.MainDispatcherRule
import com.nauhalf.ricknmorty.util.generateDummyCharacter
import com.nauhalf.ricknmorty.util.generateDummyEpisode
import com.nauhalf.ricknmorty.view.detail.CharacterDetailUiModel
import com.nauhalf.ricknmorty.view.detail.DetailViewModel
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
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    val mainDispatcher = MainDispatcherRule()

    @MockK
    lateinit var fetchCharacterUseCase: FetchCharacterUseCase

    @MockK
    lateinit var fetchEpisodeUseCase: FetchEpisodeUseCase

    @MockK(relaxUnitFun = true)
    lateinit var savedStateHandle: SavedStateHandle

    lateinit var viewModel: DetailViewModel

    companion object {
        const val ID = 1
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery {
            savedStateHandle.get<Int>(any())
        } returns ID

        viewModel = DetailViewModel(
            fetchCharacterUseCase = fetchCharacterUseCase,
            fetchEpisodeUseCase = fetchEpisodeUseCase,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun detailViewModel_loadCharacters() = runTest {
        // given
        val slot = slot<Int>()

        coEvery {
            fetchCharacterUseCase.invoke(capture(slot))
        } returns flow {
            emit(RickMortyResponse.Success(data = generateDummyCharacter(ID)))
        }

        // when
        viewModel.loadEpisode(ID)
        advanceUntilIdle()

        // then
        coVerify { fetchCharacterUseCase.invoke(slot.captured) }
        assertTrue(viewModel.detailCharacter.first() is RickMortyResponse.Success)
        val resultData = (viewModel.detailCharacter.first() as RickMortyResponse.Success).data

        assertEquals(resultData.id, slot.captured)
    }

    @Test
    fun detailViewModel_refresh() = runTest {
        // given
        val slot = slot<Int>()

        coEvery {
            fetchCharacterUseCase.invoke(capture(slot))
        } returns flow {
            emit(RickMortyResponse.Success(data = generateDummyCharacter(ID)))
        }

        // when
        viewModel.refresh()
        advanceUntilIdle()

        // then
        coVerify { fetchCharacterUseCase.invoke(slot.captured) }
    }

    @Test
    fun detailViewModel_loadEpisode() = runTest {
        // given
        val data = generateDummyEpisode(1)
        val slot = slot<Int>()
        coEvery {
            fetchEpisodeUseCase.invoke(capture(slot))
        } returns flow {
            emit(RickMortyResponse.Success(data = data))
        }
        // when
        viewModel.loadEpisode(1)
        advanceUntilIdle()

        // then
        coVerify { fetchEpisodeUseCase.invoke(slot.captured) }
        assertTrue(viewModel.detailFirstEpisode.first() is RickMortyResponse.Success)
        val resultData = (viewModel.detailFirstEpisode.first() as RickMortyResponse.Success).data

        assertEquals(resultData.name, data.name)
        assertEquals(resultData.id, slot.captured)
    }

    @Test
    fun detailViewModel_setBiographicalList() = runTest {
        // given
        val dummyBiographical = listOf<CharacterDetailUiModel>(
            CharacterDetailUiModel.Information(
                title = 1,
                value = "Alive"
            )
        )

        // when
        viewModel.setBiographicalList(dummyBiographical)
        advanceUntilIdle()
        // then
        assertTrue(
            viewModel.detailList.first().find {
                it is CharacterDetailUiModel.Information && it.value.contains(
                    "alive",
                    ignoreCase = true
                )
            } != null
        )
    }

    @Test
    fun detailViewModel_setMetaList() = runTest {
        // given
        val dummyMetaInformation = listOf<CharacterDetailUiModel>(
            CharacterDetailUiModel.Information(
                title = 1,
                value = "1 Episode(s)"
            )
        )

        // when
        viewModel.setMetaList(dummyMetaInformation)
        advanceUntilIdle()
        // then
        assertTrue(
            viewModel.detailList.first().find {
                it is CharacterDetailUiModel.Information && it.value.contains(
                    "episode",
                    ignoreCase = true
                )
            } != null
        )
    }
}
