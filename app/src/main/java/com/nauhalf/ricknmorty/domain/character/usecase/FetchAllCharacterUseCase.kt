package com.nauhalf.ricknmorty.domain.character.usecase

import com.nauhalf.ricknmorty.data.character.api.repository.CharacterRepository
import javax.inject.Inject

class FetchAllCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    /** Run the UseCase by using CharacterRepository.fetchAllCharacters() **/
    operator fun invoke() = characterRepository.fetchAllCharacters()
}
