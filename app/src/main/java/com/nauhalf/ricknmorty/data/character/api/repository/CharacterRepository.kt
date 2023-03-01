package com.nauhalf.ricknmorty.data.character.api.repository

import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.data.character.api.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    /** Fetch all characters from Rick & Morty API Character Collection **/
    fun fetchAllCharacters(): Flow<RickMortyResponse<List<Character>>>

    /** Fetch single characters of [id] from Rick & Morty API Character Collection **/
    fun fetchCharacter(id: Int): Flow<RickMortyResponse<Character>>
}
