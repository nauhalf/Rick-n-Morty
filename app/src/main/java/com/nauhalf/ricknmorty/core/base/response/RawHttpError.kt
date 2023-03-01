package com.nauhalf.ricknmorty.core.base.response

import com.google.gson.annotations.SerializedName

data class RawHttpError(
    @SerializedName("error")
    val error: String?,
)
