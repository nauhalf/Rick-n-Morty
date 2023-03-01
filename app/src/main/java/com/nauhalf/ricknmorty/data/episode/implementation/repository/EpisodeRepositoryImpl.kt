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
            val data = episodeApiService.fetchEpisode(id = id)
            emit(RickMortyResponse.Success(data = data.toEpisode()))
        } catch (e: Exception) {
            emit(e.toError())
        }
    }.flowOn(ioDispatcher)
}
