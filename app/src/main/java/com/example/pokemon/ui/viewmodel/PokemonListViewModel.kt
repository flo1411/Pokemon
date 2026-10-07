package com.example.pokemon.ui.viewmodel

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.ErrorCodes
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _pokemon = MutableStateFlow<ApiState<PokemonResponse>>(ApiState.Loading)
    private val query = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val pokemon: StateFlow<ApiState<PokemonResponse>> = combine(
        _pokemon,
        query.debounce { text -> if (text.length < 3) 0L else 300L }.distinctUntilChanged()
    ) { state, queryText ->
        filterByQuery(state, queryText)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ApiState.Loading
    )

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    fun searchPokemon(query: String) {
        this.query.value = query
    }


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
        if (append) _isLoadingMore.emit(true)
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

    private fun filterByQuery(
        state: ApiState<PokemonResponse>,
        queryText: String
    ): ApiState<PokemonResponse> {
        if (queryText.length < 3 || state !is ApiState.Success) return state
        val filtered = state.value.pokemon.orEmpty().filter { pokemon ->
            pokemon.name.orEmpty().contains(queryText, ignoreCase = true)
        }
        return state.copy(value = state.value.copy(pokemon = filtered))
    }
}