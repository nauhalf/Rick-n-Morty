package com.nauhalf.ricknmorty.core.base.response

sealed class RickMortyResponse<out T> private constructor(){
    object Loading : RickMortyResponse<Nothing>()
    class Success<T>(
        val data: T,
        val meta: Map<String, Any?> = mapOf(),
    ) : RickMortyResponse<T>()

    open class Error(
        open val message: String,
        val meta: Map<String, Any?> = mapOf(),
    ) : RickMortyResponse<Nothing>()

    object Empty : RickMortyResponse<Nothing>()
}
