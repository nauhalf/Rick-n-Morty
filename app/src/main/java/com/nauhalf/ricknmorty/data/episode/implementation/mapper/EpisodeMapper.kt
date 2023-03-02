package com.nauhalf.ricknmorty.data.episode.implementation.mapper

import com.nauhalf.ricknmorty.data.episode.api.model.Episode
import com.nauhalf.ricknmorty.data.episode.implementation.remote.response.EpisodeResponse
/** Extension function to map EpisodeResponse data class to Episode data class **/
fun EpisodeResponse.toEpisode(): Episode {
    return Episode(
        id = id ?: 0,
        name = name.orEmpty(),
        airDate = airDate.orEmpty(),
        episode = episode.orEmpty()
    )
}
