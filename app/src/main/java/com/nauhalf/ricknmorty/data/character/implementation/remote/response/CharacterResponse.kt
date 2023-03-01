package com.nauhalf.ricknmorty.data.character.implementation.remote.response

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("origin")
    val origin: OriginResponse?,
    @SerializedName("location")
    val location: LocationResponse?,
    @SerializedName("episode")
    val episode: List<String>?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
) {
    data class OriginResponse(
        @SerializedName("name")
        val name: String?,
    )

    data class LocationResponse(
        @SerializedName("name")
        val name: String?,
    )
}
