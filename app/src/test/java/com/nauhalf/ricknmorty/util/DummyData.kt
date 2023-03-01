package com.nauhalf.ricknmorty.util

import com.nauhalf.ricknmorty.data.character.api.model.Character
import com.nauhalf.ricknmorty.data.episode.api.model.Episode

fun generateDummyCharacter(id: Int): Character {
    return Character(
        id = id,
        name = "Character $id",
        status = "Alive",
        type = "",
        gender = "Male",
        origin = "Earth",
        location = "Earth",
        image = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg",
        episode = (1..5).map {
            "https://rickandmortyapi.com/api/character/avatar/$it.jpeg"
        },
    )
}

fun generateDummyEpisode(id: Int): Episode {
    return Episode(
        id = id,
        name = "Episode $id",
    )
}
