package com.nauhalf.ricknmorty.data.episode.implementation.remote.response

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode")
    val episode: String?,
)
