package com.nauhalf.ricknmorty.data.episode.api.repository

import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.data.episode.api.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    /** Fetch Episode of [id] from Rick & Morty Api Service Episode Collection **/
    fun fetchEpisode(id: Int): Flow<RickMortyResponse<Episode>>
}
