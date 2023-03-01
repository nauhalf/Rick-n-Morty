package com.nauhalf.ricknmorty.data.character.implementation.remote.response

import com.google.gson.annotations.SerializedName

data class AllCharacterResponse(
    @SerializedName("results")
    val results: List<CharacterResponse>?,
)
