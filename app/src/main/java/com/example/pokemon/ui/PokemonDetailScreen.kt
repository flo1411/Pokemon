package com.example.pokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonDetailResponse
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.ui.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    viewModel: PokemonDetailViewModel
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val pokemonState = viewModel.pokemon.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getPokemonDetails(pokemon)
        }

        when (pokemonState.value) {
            is ApiState.Loading -> CircularProgressIndicator(modifier = modifier)
            is ApiState.Success -> SetupPokemonDetailScreen(
                (pokemonState.value as ApiState.Success<PokemonDetailResponse>).value,
                modifier,
                viewModel
            )

            is ApiState.Failure -> Text("Failure")
        }

    }
}

@Composable
fun SetupPokemonDetailScreen(pokemonResponse: PokemonDetailResponse, modifier: Modifier, viewModel: PokemonDetailViewModel) {
    Box(
        Modifier
            .background(Color(0xFFD4C86E))
            .fillMaxSize()
    ) {}}
