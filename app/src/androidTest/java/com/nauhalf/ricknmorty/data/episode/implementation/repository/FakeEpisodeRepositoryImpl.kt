package com.nauhalf.ricknmorty.data.episode.implementation.repository

import com.google.gson.Gson
import com.nauhalf.ricknmorty.core.base.response.HttpError
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.core.base.response.toError
import com.nauhalf.ricknmorty.data.episode.api.model.Episode
import com.nauhalf.ricknmorty.data.episode.api.repository.EpisodeRepository
import com.nauhalf.ricknmorty.data.episode.implementation.mapper.toEpisode
import com.nauhalf.ricknmorty.data.episode.implementation.remote.response.EpisodeResponse
import com.nauhalf.ricknmorty.util.JsonUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.FileNotFoundException

class FakeEpisodeRepositoryImpl(
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher,
) : EpisodeRepository {
    override fun fetchEpisode(id: Int): Flow<RickMortyResponse<Episode>> = flow {
        try {
            val json = JsonUtil.getJsonContent("episode_$id.json")
            when (val episode = gson.fromJson(json, EpisodeResponse::class.java)) {
                null -> emit(RickMortyResponse.Empty)
                else -> emit(RickMortyResponse.Success(data = episode.toEpisode()))
            }
        } catch (e: FileNotFoundException) {
            emit(
                HttpError(
                    message = "Episode not found",
                    code = 404,
                    data = null
                )
            )
        } catch (e: Exception) {
            emit(e.toError())
        }
    }.flowOn(ioDispatcher)
}
