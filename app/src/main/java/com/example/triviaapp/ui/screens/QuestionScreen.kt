package com.example.triviaapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaapp.R
import com.example.triviaapp.ui.RadioButtons
import kotlinx.coroutines.delay


@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    triviaUiState: TriviaUiState,
    triviaApiState: TriviaApiState,
    onNextButtonClick: () ->  Unit
) {

    when(triviaApiState) {
        is TriviaApiState.Loading -> LoadingScreen(modifier)
        is TriviaApiState.Success -> if( triviaUiState.transitionVisible.value ) {
            AnimateTransitionScreen(triviaUiState = triviaUiState)
        } else {
            DisplayQuestionScreen(triviaUiState, modifier, onNextButtonClick)
        }
        is TriviaApiState.Error -> ErrorScreen(modifier)
    }

}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun DisplayQuestionScreen(
    triviaUiState: TriviaUiState,
    modifier: Modifier = Modifier,
    onNextButtonClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        /*Spacer(modifier = Modifier.weight(0.1f))*/
        Text(
            text = triviaUiState.question!!.question,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            RadioButtons(
                radioOptions = (triviaUiState.answerChoices as MutableList<String>),
                selectedOption = triviaUiState.selectedOption,
                modifier = Modifier.fillMaxWidth(),
                onSelected = {
                    triviaUiState.selectedOption.value = it
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNextButtonClick
            ) {
                Text(
                    "Next",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun TransitionScreen(modifier: Modifier, correctAnswers: Int, text: String) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text,
                fontSize = 36.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(text = "$correctAnswers/10")
        }

    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
    }
}

@Composable
fun AnimateTransitionScreen(
    triviaUiState: TriviaUiState
) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val density = LocalDensity.current
    AnimatedVisibility(
        visibleState = state,
        enter = slideInHorizontally {
            with(density) { -300.dp.roundToPx()}
        } + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally {
            200
        } + fadeOut()
    ) {
        TransitionScreen(modifier = Modifier, correctAnswers = triviaUiState.correctAnswers.value, text = triviaUiState.previousResult.value)
    }
    LaunchedEffect(Unit) {
        delay(2000)
        //triviaUiState.transitionVisible.value = false
        state.targetState = false
        delay(300)
        triviaUiState.transitionVisible.value = false
    }
}