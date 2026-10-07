package com.example.pokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonDetailResponse
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.ui.theme.CardYellow
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
            is ApiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }
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
fun SetupPokemonDetailScreen(
    pokemonResponse: PokemonDetailResponse,
    modifier: Modifier,
    viewModel: PokemonDetailViewModel
) {
    Box(
        Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .padding(20.dp)
    )
    {
        val cardShape = RoundedCornerShape(15.dp)
        Box(
            Modifier
                .fillMaxSize()
                .border(1.dp, Color.DarkGray, cardShape)
                //.shadow(2.dp, cardShape)
                .clip(cardShape)
                .background(CardYellow)
        ) {}
    }
}
