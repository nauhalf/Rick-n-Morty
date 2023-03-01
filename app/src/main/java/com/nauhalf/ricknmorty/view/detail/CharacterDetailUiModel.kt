package com.nauhalf.ricknmorty.view.detail

import androidx.annotation.StringRes

sealed class CharacterDetailUiModel {
    @get:StringRes
    abstract val title: Int

    data class Information(
        override val title: Int,
        val value: String,
    ) : CharacterDetailUiModel()

    data class Separator(override val title: Int) : CharacterDetailUiModel()
}
