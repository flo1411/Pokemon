package com.example.pokemon.ui

import androidx.compose.foundation.clickable
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
fun PokemonListScreen(
    viewModel: PokemonViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onPokemonClicked: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val pokemonState = viewModel.pokemon.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchPokemon()
        }

        when (pokemonState.value) {
            is ApiState.Loading -> CircularProgressIndicator(modifier = modifier)
            is ApiState.Success -> SetupPokemonList(
                (pokemonState.value as ApiState.Success<PokemonResponse>).value,
                modifier,
                onPokemonClicked
            )

            is ApiState.Failure -> Text("Failure")
        }
    }
}

@Composable
fun SetupPokemonList(response: PokemonResponse, modifier: Modifier, onPokemonClicked: (String) -> Unit) {
    LazyColumn(
        modifier.padding(
            8.dp
        )
    ) {
        items(
            response.pokemon.orEmpty(),
            key = { item -> item.name ?: (1..10000).random() }) { pokemonItem ->
            PokemonCard(pokemonItem, onPokemonClicked)
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon, onPokemonClicked: (String) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD4C86E)),
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(100.dp)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            )
            .clickable { onPokemonClicked.invoke(pokemon.name.orEmpty()) }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = pokemon.name.orEmpty(),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}