package com.nauhalf.ricknmorty.data.character.implementation.mapper

import com.nauhalf.ricknmorty.data.character.implementation.remote.response.CharacterResponse
import com.nauhalf.ricknmorty.data.character.api.model.Character

fun CharacterResponse.toCharacter(): Character {
    return Character(
        id = id ?: 0,
        name = name.orEmpty(),
        status = status.orEmpty(),
        species = species.orEmpty(),
        type = type.orEmpty(),
        gender = gender.orEmpty(),
        origin = origin?.name.orEmpty(),
        location = location?.name.orEmpty(),
        episode = episode ?: listOf(),
        image = image.orEmpty(),
        createdAt = createdAt.orEmpty()
    )
}
