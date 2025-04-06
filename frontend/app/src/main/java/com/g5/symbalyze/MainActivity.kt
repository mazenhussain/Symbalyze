package com.g5.symbalyze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.g5.symbalyze.api.identifySymbol
import com.g5.symbalyze.model.SymbolResponse
import com.g5.symbalyze.ui.screens.DrawInputScreen
import com.g5.symbalyze.ui.screens.TypeInputScreen
import com.g5.symbalyze.ui.screens.HomeScreen
import com.g5.symbalyze.ui.screens.*
import com.g5.symbalyze.ui.theme.SymbalyzeTheme
import java.util.UUID

// main navigation
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SymbalyzeTheme {
                Scaffold() { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) { 
                        AppNavigation()
                    }
                }
            }
        }
    }
}

// list of all screens
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("draw") { DrawInputScreen(navController) }
        composable("type") { TypeInputScreen(navController) }
        composable("image") { ImageInputScreen(navController) }
        composable("result") { ResultsDisplayScreen(navController) }
    }
}