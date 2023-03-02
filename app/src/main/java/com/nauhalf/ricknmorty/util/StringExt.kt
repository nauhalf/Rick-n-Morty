package com.nauhalf.ricknmorty.util

/** Get the id number from url
 * example :
 * URL = https://rickandmortyapi.com/api/character/1
 * ID = 1
 * **/
val String.id: Int
    get() {
        return try {
            this.substring(this.lastIndexOf("/") + 1).toIntOrNull() ?: 0
        } catch (_: Exception) {
            0
        }
    }
