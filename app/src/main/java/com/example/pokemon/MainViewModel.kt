package com.example.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.ErrorCodes
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _pokemon = MutableStateFlow<ApiState<PokemonResponse>>(ApiState.Loading)
    val pokemon = _pokemon.asStateFlow()

    fun fetchPokemon() {
        viewModelScope.launch {
            try {
                val response = apiService.getPokemon()
                _pokemon.value = ApiState.Success(response)
            } catch (e: Exception) {
                _pokemon.value = ApiState.Failure(false,
                    ErrorCodes.HTTP,
                    e.cause)
            }
        }
    }
}