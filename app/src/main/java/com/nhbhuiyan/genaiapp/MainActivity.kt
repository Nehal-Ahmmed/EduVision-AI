// MainActivity.kt (updated)
package com.nhbhuiyan.genaiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nhbhuiyan.genaiapp.presentation.screens.MainScreen
import com.nhbhuiyan.genaiapp.presentation.viewmodels.MainViewModel
import com.nhbhuiyan.genaiapp.ui.theme.GenAIAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nhbhuiyan.genaiapp.presentation.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenAIAppTheme {
                EduVisionApp()
            }
        }
    }
}

@Composable
fun EduVisionApp() {
    val navController = rememberNavController()
    val viewModel = MainViewModel()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("diagram_upload") {
            DiagramUploadScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("quiz_generator") {
            QuizGeneratorScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("visualize_concept") {
            VisualizeConceptScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("study_history") {
            StudyHistoryScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("result_screen") {
            ResultScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}