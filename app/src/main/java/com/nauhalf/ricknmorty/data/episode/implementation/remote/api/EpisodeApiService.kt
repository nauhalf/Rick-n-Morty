package com.nauhalf.ricknmorty.data.episode.implementation.remote.api

import com.nauhalf.ricknmorty.data.episode.implementation.remote.response.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeApiService {
    @GET("/api/episode/{id}")
    suspend fun fetchEpisode(@Path("id") id: Int): EpisodeResponse
}
