package com.example.perfumeshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perfumeshop.data.SettingsRepository
import com.example.perfumeshop.navigation.AppNavigation
import com.example.perfumeshop.ui.theme.PerfumeShopTheme
import com.example.perfumeshop.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsRepository = SettingsRepository(applicationContext)

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModel.factory(settingsRepository)
            )
            val darkMode by settingsViewModel.darkMode.collectAsStateWithLifecycle()

            PerfumeShopTheme(darkTheme = darkMode) {
                AppNavigation(
                    darkMode = darkMode,
                    onDarkModeChange = settingsViewModel::setDarkMode
                )
            }
        }
    }
}