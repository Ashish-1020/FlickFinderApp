package com.example.flickfinder.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.flickfinder.ui.home.HomeScreen
import com.example.flickfinder.ui.home.HomeViewModel





import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flickfinder.ui.details.Detail


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUpNavGraph(navController: NavHostController, viewModel: HomeViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, viewModel)
        }

        composable("details/{releaseId}") { backStackEntry ->
            val releaseId = backStackEntry.arguments?.getString("releaseId")?.toInt() ?: 0
            Detail(viewModel, releaseId)
        }
    }
}