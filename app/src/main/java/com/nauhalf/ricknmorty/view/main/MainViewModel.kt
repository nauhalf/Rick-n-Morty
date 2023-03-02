package com.nauhalf.ricknmorty.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.data.character.api.model.Character
import com.nauhalf.ricknmorty.domain.character.usecase.FetchAllCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val fetchAllCharacterUseCase: FetchAllCharacterUseCase) :
    ViewModel() {
    private val _characters =
        MutableStateFlow<RickMortyResponse<List<Character>>>(RickMortyResponse.Empty)
    val character: StateFlow<RickMortyResponse<List<Character>>> = _characters
    private var job: Job? = null

    init {
        // call loadAllCharacter at the first time ViewModel created
        loadAllCharacter()
    }

    fun loadAllCharacter() {
        // cancel any process if there's a Job available
        job?.cancel()
        job = viewModelScope.launch {
            // run the use case with emitting the Loading at the first flow run
            fetchAllCharacterUseCase.invoke()
                .onStart {
                    emit(RickMortyResponse.Loading)
                }
                .collect {
                    // set emitted value to _characters StateFlow
                    _characters.value = it
                }
        }
    }
}
