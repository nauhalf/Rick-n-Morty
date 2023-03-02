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
            // call API fetchAllCharacters
            val data = characterApiService.fetchAllCharacters()
            // if the result is null set emit value to Empty
            // else set the emit value to Success with mapped response to Character data class
            when (val result = data.results) {
                null -> emit(RickMortyResponse.Empty)
                else -> emit(RickMortyResponse.Success(result.map { it.toCharacter() }))
            }
        } catch (e: Exception) {
            // when it throw an error, catch the exception and emit mapped Error from the exception
            emit(e.toError())
        }
        // run the process on Dispatcher.IO
    }.flowOn(ioDispatcher)

    override fun fetchCharacter(id: Int): Flow<RickMortyResponse<Character>> = flow {
        try {
            // call API fetchCharacter of id character
            val data = characterApiService.fetchCharacter(id)
            // emit the result to success with mapped response to Character data class
            emit(RickMortyResponse.Success(data = data.toCharacter()))
        } catch (e: Exception) {
            // when it throw an error, catch the exception and emit mapped Error from the exception
            emit(e.toError())
        }
        // run the process on Dispatcher.IO
    }.flowOn(ioDispatcher)
}
