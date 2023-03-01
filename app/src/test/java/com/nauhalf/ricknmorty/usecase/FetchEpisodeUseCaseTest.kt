package com.nauhalf.ricknmorty.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.data.episode.api.repository.EpisodeRepository
import com.nauhalf.ricknmorty.domain.episode.usecase.FetchEpisodeUseCase
import com.nauhalf.ricknmorty.util.generateDummyEpisode
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
class FetchEpisodeUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var episodeRepository: EpisodeRepository

    private lateinit var useCase: FetchEpisodeUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FetchEpisodeUseCase(episodeRepository)
    }

    @Test
    fun fetchEpisodeUseCase_invoke() = runTest {
        // given
        val data = generateDummyEpisode(1)
        val slot = slot<Int>()
        coEvery {
            episodeRepository.fetchEpisode(capture(slot))
        } returns flow {
            emit(RickMortyResponse.Success(data = data))
        }

        // when
        val result = useCase.invoke(1)
        // then
        coVerify {
            episodeRepository.fetchEpisode(slot.captured)
        }
        assertTrue(result.first() is RickMortyResponse.Success)
        val resultData = (result.first() as RickMortyResponse.Success).data

        assertEquals(resultData, data)
        assertEquals(resultData.id, slot.captured)

    }

}
