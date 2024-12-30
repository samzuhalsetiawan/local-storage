package com.samzuhalsetiawan.localstorage.sample.compose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.localstorage.sample.SampleApp
import com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example.PreferencesExampleScreen
import com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example.PreferencesExampleScreenNavigationEvent
import com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example.PreferencesExampleScreenViewModel
import com.samzuhalsetiawan.localstorage.sample.compose.theme.LocalStorageTheme
import kotlinx.serialization.Serializable

class MainComposeActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalStorageTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home
                ) {
                    composable<Screen.Home> {
                        HomeScreen(
                            onNavigateToPreferencesExample = {
                                navController.navigate(Screen.PreferencesExample)
                            }
                        )
                    }
                    composable<Screen.PreferencesExample> {
                        val viewModel = viewModel<PreferencesExampleScreenViewModel> {
                            PreferencesExampleScreenViewModel(
                                localStorage = (application as SampleApp).localStorage
                            )
                        }
                        PreferencesExampleScreen(
                            viewModel = viewModel,
                            onNavigationEvent = { navigationEvent ->
                                when (navigationEvent) {
                                    PreferencesExampleScreenNavigationEvent.NavigateBack -> {
                                        navController.navigateUp()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun HomeScreen(
    onNavigateToPreferencesExample: () -> Unit
) {
    Scaffold {  scaffoldPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    onNavigateToPreferencesExample()
                }
            ) {
                Text(text = "Preferences Example")
            }
        }
    }
}

sealed interface Screen {

    @Serializable
    data object Home: Screen

    @Serializable
    data object PreferencesExample: Screen
}