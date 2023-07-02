package com.example.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickmorty.ui.screens.CharactersScreen
import com.example.rickmorty.ui.screens.CharactersViewModel
import com.example.rickmorty.ui.theme.RickMortyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RickMortyTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          val navController = rememberNavController()

          NavHost(navController = navController, startDestination = "home") {
            composable("home") {
              Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Button(
                  onClick = {
                    navController.navigate("characters")
                  }
                ) {
                  Text("View Characters")
                }
              }
            }

            composable("characters") {
              val viewModel: CharactersViewModel by viewModels()
              val state = viewModel.characters.collectAsState()
              CharactersScreen(state = state)
            }

            composable("characterinfo/{id}") {

            }

            composable("episodes") {
              // TODO
            }
            composable("episodeinfo/{id}") {
              // TODO
            }
            composable("locations") {
              // TODO
            }
            composable("locationinfo/{id}") {
              // TODO
            }
          }
        }
      }
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
  RickMortyTheme {
    Greeting("Android")
  }
}