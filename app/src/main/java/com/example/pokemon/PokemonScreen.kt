package com.example.pokemon

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokemon.ui.PokemonDetailScreen
import com.example.pokemon.ui.PokemonListScreen
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.ui.viewmodel.PokemonViewModel

/**
 * enum values that represent the screens in the app
 */
enum class PokemonScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Detail(title = R.string.detail_page_name)
}

@Composable
fun PokemonApp(
    viewModel: PokemonViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = PokemonScreen.valueOf(
        backStackEntry?.destination?.route ?: PokemonScreen.Start.name
    )

    Scaffold(
        topBar = {
            PokemonAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PokemonScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = PokemonScreen.Start.name) {
                PokemonListScreen(
                    viewModel,
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    onPokemonClicked = { navController.navigate(PokemonScreen.Detail.name) })
            }
            composable(route = PokemonScreen.Detail.name) {
                PokemonDetailScreen(modifier = Modifier.fillMaxSize().padding(innerPadding))
            }
        }
    }
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonAppBar(
    currentScreen: PokemonScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            }
        }
    )
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