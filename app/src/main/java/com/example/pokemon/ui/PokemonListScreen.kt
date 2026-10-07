package com.example.pokemon.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.ui.theme.CardYellow
import com.example.pokemon.ui.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = viewModel(),
    onPokemonClicked: (Pokemon) -> Unit
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
                onPokemonClicked,
                viewModel
            )

            is ApiState.Failure -> Text("Failure")
        }
    }
}

@Composable
fun SetupPokemonList(
    response: PokemonResponse,
    modifier: Modifier,
    onPokemonClicked: (Pokemon) -> Unit,
    viewModel: PokemonListViewModel
) {
    val searchTextState = rememberTextFieldState()
    TextField(
        state = searchTextState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp,
            )
            .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(50.dp)),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        placeholder = { Text("Search") },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
        )
    )
    LaunchedEffect(searchTextState) {
        snapshotFlow {
            searchTextState.text.toString()
        }.collect { viewModel.searchPokemon(it) }
    }

    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 &&
                    lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            viewModel.loadMorePokemon()
        }
    }
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()

    LazyColumn(
        modifier.padding(
            8.dp
        ),
        state = listState
    ) {
        itemsIndexed(
            items = response.pokemon.orEmpty(),
            key = { _, item -> item.name.orEmpty() }
        ) { index, pokemonItem ->
            PokemonCard(pokemonItem, index, onPokemonClicked)
        }

        if (isLoadingMore) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon, index: Int, onPokemonClicked: (Pokemon) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(containerColor = CardYellow),
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(100.dp)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            )
            .clickable { onPokemonClicked(pokemon) }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "#" + (index + 1) + " " + pokemon.name.orEmpty(),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}