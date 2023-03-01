package com.nauhalf.ricknmorty.data.character.implementation.repository

import com.google.gson.Gson
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.core.base.response.toError
import com.nauhalf.ricknmorty.data.character.api.model.Character
import com.nauhalf.ricknmorty.data.character.api.repository.CharacterRepository
import com.nauhalf.ricknmorty.data.character.implementation.mapper.toCharacter
import com.nauhalf.ricknmorty.data.character.implementation.remote.response.AllCharacterResponse
import com.nauhalf.ricknmorty.util.JsonUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeCharacterRepositoryImpl(
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher,
) :
    CharacterRepository {
    override fun fetchAllCharacters(): Flow<RickMortyResponse<List<Character>>> = flow {
        try {
            val data = gson.fromJson(
                JsonUtil.getJsonContent("all_character.json"),
                AllCharacterResponse::class.java
            )
            when (val result = data.results) {
                null -> emit(RickMortyResponse.Empty)
                else -> emit(RickMortyResponse.Success(result.map { it.toCharacter() }))
            }
        } catch (e: Exception) {
            emit(e.toError())
        }
    }.flowOn(ioDispatcher)

    override fun fetchCharacter(id: Int): Flow<RickMortyResponse<Character>> = flow {
        try {
            val data = gson.fromJson(
                JsonUtil.getJsonContent("all_character.json"),
                AllCharacterResponse::class.java
            )
            when (val character = data.results?.firstOrNull { it.id == id }) {
                null -> emit(RickMortyResponse.Empty)
                else -> emit(RickMortyResponse.Success(character.toCharacter()))
            }
        } catch (e: Exception) {
            emit(e.toError())
        }
    }.flowOn(ioDispatcher)
}
