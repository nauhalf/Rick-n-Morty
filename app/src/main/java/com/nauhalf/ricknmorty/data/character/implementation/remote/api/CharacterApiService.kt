package com.nauhalf.ricknmorty.data.character.implementation.remote.api

import com.nauhalf.ricknmorty.data.character.implementation.remote.response.AllCharacterResponse
import com.nauhalf.ricknmorty.data.character.implementation.remote.response.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApiService {
    @GET("/api/character")
    suspend fun fetchAllCharacters(): AllCharacterResponse

    @GET("/api/character/{id}")
    suspend fun fetchCharacter(@Path("id") id: Int): CharacterResponse
}
