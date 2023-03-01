package com.nauhalf.ricknmorty.util

val String.id: Int
    get() {
        return try {
            this.substring(this.lastIndexOf("/") + 1).toIntOrNull() ?: 0
        } catch (_: Exception) {
            0
        }
    }
