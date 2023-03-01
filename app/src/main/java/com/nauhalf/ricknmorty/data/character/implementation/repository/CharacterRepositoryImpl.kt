package com.nauhalf.ricknmorty.data.character.implementation.repository

import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.core.base.response.toError
import com.nauhalf.ricknmorty.data.character.api.model.Character
import com.nauhalf.ricknmorty.data.character.api.repository.CharacterRepository
import com.nauhalf.ricknmorty.data.character.implementation.mapper.toCharacter
import com.nauhalf.ricknmorty.data.character.implementation.remote.api.CharacterApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepositoryImpl(
    private val characterApiService: CharacterApiService,
    private val ioDispatcher: CoroutineDispatcher,
) : CharacterRepository {
    override fun fetchAllCharacters(): Flow<RickMortyResponse<List<Character>>> = flow {
        try {
            val data = characterApiService.fetchAllCharacters()
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
            val data = characterApiService.fetchCharacter(id)
            emit(RickMortyResponse.Success(data = data.toCharacter()))
        } catch (e: Exception) {
            emit(e.toError())
        }
    }.flowOn(ioDispatcher)
}
