package com.example.triviaapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.triviaapp.ui.screens.TriviaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.triviaapp.R
import com.example.triviaapp.ui.screens.HomeScreen
import com.example.triviaapp.ui.screens.QuestionScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost

@Composable
fun TriviaApp(modifier: Modifier = Modifier, viewModel: TriviaViewModel, navController: NavHostController) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            val triviaViewModel: TriviaViewModel = viewModel
            val triviaUiState = triviaViewModel.triviaUiState.collectAsState()
            val triviaApiState = triviaViewModel.triviaApiState
            NavHost(
                modifier = Modifier,
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        onNavigateToQuestion = { navController.navigate("question") }
                    )
                }
                composable("question") {
                    QuestionScreen(
                        triviaUiState = triviaUiState.value,
                        triviaApiState = triviaApiState.value,
                        onNextButtonClick = {
                            triviaViewModel.updateQuestion(triviaUiState.value.selectedOption.value == triviaUiState.value.question?.correct_answer)
                        }
                    )
                }
            }
        }
    }
}