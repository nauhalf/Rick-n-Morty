package com.nauhalf.ricknmorty.domain.episode.usecase

import com.nauhalf.ricknmorty.data.episode.api.repository.EpisodeRepository
import javax.inject.Inject

class FetchEpisodeUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository,
) {
    /** Run the UseCase by using EpisodeRepository.fetchEpisode(:id) **/
    operator fun invoke(id: Int) = episodeRepository.fetchEpisode(id)
}
