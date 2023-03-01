package com.nauhalf.ricknmorty.data.character.di

import com.nauhalf.ricknmorty.core.coroutine.IoDispatcher
import com.nauhalf.ricknmorty.data.character.api.repository.CharacterRepository
import com.nauhalf.ricknmorty.data.character.implementation.remote.api.CharacterApiService
import com.nauhalf.ricknmorty.data.character.implementation.repository.CharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterRepositoryModule {
    @Provides
    @Singleton
    fun provideCharacterApiService(
        retrofit: Retrofit,
    ): CharacterApiService {
        return retrofit.create(CharacterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterApiService: CharacterApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            characterApiService,
            ioDispatcher
        )
    }
}
