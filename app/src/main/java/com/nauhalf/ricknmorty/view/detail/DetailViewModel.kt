package com.nauhalf.ricknmorty.view.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauhalf.ricknmorty.R
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.domain.character.usecase.FetchCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.nauhalf.ricknmorty.data.character.api.model.Character
import com.nauhalf.ricknmorty.data.episode.api.model.Episode
import com.nauhalf.ricknmorty.domain.episode.usecase.FetchEpisodeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fetchCharacterUseCase: FetchCharacterUseCase,
    private val fetchEpisodeUseCase: FetchEpisodeUseCase,
) : ViewModel() {
    private val idCharacter = savedStateHandle.get<Int>("id") ?: 0
    private val _detailCharacter =
        MutableStateFlow<RickMortyResponse<Character>>(RickMortyResponse.Empty)
    val detailCharacter: StateFlow<RickMortyResponse<Character>> = _detailCharacter

    private val _detailFirstEpisode =
        MutableStateFlow<RickMortyResponse<Episode>>(RickMortyResponse.Empty)
    val detailFirstEpisode: StateFlow<RickMortyResponse<Episode>> = _detailFirstEpisode

    private val _biographicalList = MutableStateFlow<List<CharacterDetailUiModel>>(listOf())
    private val _metaList = MutableStateFlow<List<CharacterDetailUiModel>>(listOf())
    val detailList = combine(_biographicalList, _metaList, _detailFirstEpisode) { bio, meta, epi ->
        val list = mutableListOf<CharacterDetailUiModel>()
        list.add(CharacterDetailUiModel.Separator(title = R.string.biographical_information))
        list.addAll(bio)
        list.add(CharacterDetailUiModel.Separator(title = R.string.meta_information))
        if (epi is RickMortyResponse.Success) {
            list.add(
                CharacterDetailUiModel.Information(
                    title = R.string.first_seen_in,
                    value = "${epi.data.name} (${epi.data.episode})"
                )
            )
        }
        list.addAll(meta)
        return@combine list
    }.stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    init {
        loadCharacter(idCharacter)
    }

    fun refresh(){
        loadCharacter(idCharacter)
    }

    private fun loadCharacter(id: Int) {
        viewModelScope.launch {
            fetchCharacterUseCase(id)
                .onStart {
                    emit(RickMortyResponse.Loading)
                }.collect {
                    _detailCharacter.value = it
                }
        }
    }

    fun loadEpisode(id: Int) {
        viewModelScope.launch {
            fetchEpisodeUseCase(id)
                .onStart {
                    emit(RickMortyResponse.Loading)
                }.collect {
                    _detailFirstEpisode.value = it
                }
        }
    }

    fun setBiographicalList(value: List<CharacterDetailUiModel>) {
        _biographicalList.value = value
    }


    fun setMetaList(value: List<CharacterDetailUiModel>) {
        _metaList.value = value
    }
}
