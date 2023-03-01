package com.nauhalf.ricknmorty.util

import java.io.InputStreamReader

object JsonUtil {
    fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}
