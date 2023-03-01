package com.nauhalf.ricknmorty.data.character.api.model

data class Character(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: String = "",
    val location: String = "",
    val image: String = "",
    val episode: List<String> = listOf(),
    val createdAt: String = "",
)
