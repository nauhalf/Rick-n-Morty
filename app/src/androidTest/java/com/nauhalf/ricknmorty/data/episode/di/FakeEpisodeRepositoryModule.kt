package com.nauhalf.ricknmorty.data.episode.di

import com.google.gson.Gson
import com.nauhalf.ricknmorty.core.coroutine.IoDispatcher
import com.nauhalf.ricknmorty.data.episode.api.repository.EpisodeRepository
import com.nauhalf.ricknmorty.data.episode.implementation.repository.FakeEpisodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [EpisodeRepositoryModule::class]
)
object FakeEpisodeRepositoryModule {
    @Provides
    @Singleton
    fun provideEpisodeRepository(
        gson: Gson,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): EpisodeRepository {
        return FakeEpisodeRepositoryImpl(
            gson = gson,
            ioDispatcher = ioDispatcher
        )
    }
}
