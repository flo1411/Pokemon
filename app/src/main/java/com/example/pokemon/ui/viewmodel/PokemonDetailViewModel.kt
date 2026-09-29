package com.example.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.ErrorCodes
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonDetailResponse
import com.example.pokemon.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _pokemon = MutableStateFlow<ApiState<PokemonDetailResponse>>(ApiState.Loading)
    val pokemon = _pokemon.asStateFlow()

    suspend fun getPokemonDetails(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getPokemonDetails(pokemon.url)
                _pokemon.emit(ApiState.Success(response))
            } catch (e: Exception) {
                _pokemon.emit(
                    ApiState.Failure(
                        isNetworkError = false,
                        errorCode = ErrorCodes.HTTP,
                        errorBody = e.cause
                    )
                )
            }
        }
    }
}