package com.nauhalf.ricknmorty.data.episode.di

import com.nauhalf.ricknmorty.core.coroutine.IoDispatcher
import com.nauhalf.ricknmorty.data.episode.api.repository.EpisodeRepository
import com.nauhalf.ricknmorty.data.episode.implementation.remote.api.EpisodeApiService
import com.nauhalf.ricknmorty.data.episode.implementation.repository.EpisodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EpisodeRepositoryModule {

    @Provides
    @Singleton
    fun providesEpisodeApiService(
        retrofit: Retrofit,
    ): EpisodeApiService {
        return retrofit.create(EpisodeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesEpisodeRepository(
        episodeApiService: EpisodeApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): EpisodeRepository {
        return EpisodeRepositoryImpl(
            episodeApiService,
            ioDispatcher
        )
    }
}
