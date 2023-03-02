package com.nauhalf.ricknmorty.data.episode.implementation.repository

import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.core.base.response.toError
import com.nauhalf.ricknmorty.data.episode.api.model.Episode
import com.nauhalf.ricknmorty.data.episode.api.repository.EpisodeRepository
import com.nauhalf.ricknmorty.data.episode.implementation.mapper.toEpisode
import com.nauhalf.ricknmorty.data.episode.implementation.remote.api.EpisodeApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EpisodeRepositoryImpl(
    private val episodeApiService: EpisodeApiService,
    private val ioDispatcher: CoroutineDispatcher,
) : EpisodeRepository {
    override fun fetchEpisode(id: Int): Flow<RickMortyResponse<Episode>> = flow {
        try {
            // call API fetchEpisode of id episode
            val data = episodeApiService.fetchEpisode(id = id)
            // emit the result to success with mapped response to Episode data class
            emit(RickMortyResponse.Success(data = data.toEpisode()))
        } catch (e: Exception) {
            // when it throw an error, catch the exception and emit mapped Error from the exception
            emit(e.toError())
        }
        // run the process on Dispatcher.IO
    }.flowOn(ioDispatcher)
}
