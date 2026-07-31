package com.example.pokemon.ui.viewmodel

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.ErrorCodes
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _pokemon = MutableStateFlow<ApiState<PokemonResponse>>(ApiState.Loading)
    val pokemon = _pokemon.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    fun fetchPokemon() {
        viewModelScope.launch {
            loadPage()
        }
    }

    fun loadMorePokemon() {
        val current = _pokemon.value
        if (current !is ApiState.Success) return
        if (_isLoadingMore.value) return
        val next = current.value.next ?: return
        if (next.isEmpty()) return

        val uri = next.toUri()
        val limit = uri.getQueryParameter("limit")?.toIntOrNull() ?: return
        val offset = uri.getQueryParameter("offset")?.toIntOrNull() ?: return
        viewModelScope.launch {
            loadPage(limit, offset, append = true)
        }
    }

    private suspend fun loadPage(limit: Int? = null, offset: Int? = null, append: Boolean = false) {
        if (append)_isLoadingMore.emit(true)
        else _pokemon.value = ApiState.Loading

        try {
            val response = apiService.getPokemon(limit, offset)
            val merged = if (append) {
                val existing = (_pokemon.value as? ApiState.Success)?.value
                response.copy(
                    pokemon = existing?.pokemon.orEmpty() + response.pokemon.orEmpty()
                )
            } else {
                response
            }
            _pokemon.value = ApiState.Success(merged)
        } catch (e: Exception) {
            if (!append) {
                _pokemon.value = ApiState.Failure(
                    isNetworkError = false,
                    errorCode = ErrorCodes.HTTP,
                    errorBody = e.cause
                )
            }
            // on append failure: keep current Success list, optionally expose a toast/flag later
        } finally {
            _isLoadingMore.emit(false)
        }
    }
}