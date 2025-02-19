package com.example.pokemon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.ui.viewmodel.PokemonViewModel

@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        /*
        val pokemonState = viewModel.pokemon.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchPokemon()
        }

        when (pokemonState.value) {
            is ApiState.Loading -> CircularProgressIndicator(modifier = modifier)
            is ApiState.Success -> SetupPokemonList(
                (pokemonState.value as ApiState.Success<PokemonResponse>).value,
                modifier
            )

            is ApiState.Failure -> Text("Failure")
        }

         */
    }
}
