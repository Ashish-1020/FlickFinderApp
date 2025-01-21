package com.example.flickfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.flickfinder.ui.home.HomeScreen
import com.example.flickfinder.ui.home.HomeViewModel

import com.example.flickfinder.ui.theme.FlickFinderTheme
import com.example.flickfinder.utils.SetUpNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController= rememberNavController()
            val viewModel: HomeViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.loadReleases("api")
            }
            SetUpNavGraph(navController, viewModel)
        }
    }
}
