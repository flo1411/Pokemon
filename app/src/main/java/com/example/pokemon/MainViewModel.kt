package com.example.pokemon

import androidx.lifecycle.ViewModel
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.Pokemon
import com.example.pokemon.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _users = MutableStateFlow<ApiState<List<Pokemon>>>(ApiState.Loading)
    val users = _users.asStateFlow()


}