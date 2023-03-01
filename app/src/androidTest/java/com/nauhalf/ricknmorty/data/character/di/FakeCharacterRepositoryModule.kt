package com.nauhalf.ricknmorty.data.character.di

import com.google.gson.Gson
import com.nauhalf.ricknmorty.core.coroutine.IoDispatcher
import com.nauhalf.ricknmorty.data.character.api.repository.CharacterRepository
import com.nauhalf.ricknmorty.data.character.implementation.repository.FakeCharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CharacterRepositoryModule::class]
)
object FakeCharacterRepositoryModule {
    @Provides
    @Singleton
    fun provideCharacterRepository(
        gson: Gson,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): CharacterRepository {
        return FakeCharacterRepositoryImpl(
            gson = gson,
            ioDispatcher = ioDispatcher
        )
    }
}
