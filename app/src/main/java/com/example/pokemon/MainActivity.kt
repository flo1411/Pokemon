package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.model.ApiState
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PokemonList(viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PokemonList(viewModel: MainViewModel, modifier: Modifier) {
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
}

@Composable
fun SetupPokemonList(response: PokemonResponse, modifier: Modifier) {
    LazyColumn(
        modifier.padding(
            8.dp
        )
    ) {
        items(
            response.pokemon.orEmpty(),
            key = { item -> item.name ?: (1..10000).random() }) { pokemonItem ->
            PokemonCard(pokemonItem)
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon) {
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
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = pokemon.name.orEmpty(),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
            AsyncImage(
                //model = pokemon.url,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.url)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokemonTheme {
        Greeting("Android")
    }
}