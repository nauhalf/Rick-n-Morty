package com.nauhalf.ricknmorty.core.network.module

import com.nauhalf.ricknmorty.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkProvider(): NetworkProvider {
        return NetworkProvider(BuildConfig.RickMortyBaseUrl)
    }

    @Provides
    @Singleton
    fun provideRetrofit(networkProvider: NetworkProvider): Retrofit {
        return networkProvider.createRetrofit()
    }
}
