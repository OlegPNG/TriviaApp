package com.example.triviaapp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaapp.R
import com.example.triviaapp.ui.RadioButtons

@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    triviaUiState: TriviaUiState,
    triviaApiState: TriviaApiState,
    onNextButtonClick: () ->  Unit
) {

    when(triviaApiState) {
        is TriviaApiState.Loading -> LoadingScreen(modifier)
        is TriviaApiState.Success -> ResultScreen(triviaUiState, modifier, onNextButtonClick)
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
/*
@Composable
fun ResultScreen(triviaUiState: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical=100.dp)
        ) {
            Text(
                text = triviaUiState,
                fontSize = 48.sp
            )
        }
    }
}*/

@Composable
fun ResultScreen(
    triviaUiState: TriviaUiState,
    modifier: Modifier = Modifier,
    onNextButtonClick: () -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        triviaUiState.toastMessage.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(24.dp)
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
            modifier = modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            RadioButtons(
                radioOptions = (triviaUiState.answerChoices as MutableList<String>),
                selectedOption = triviaUiState.selectedOption,
                modifier = Modifier.fillMaxWidth(),
                onSelected = {
                    triviaUiState.selectedOption.value = it
                    Log.d("QuestionScreen", triviaUiState.selectedOption.value)
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
        Column(
            modifier = Modifier.fillMaxWidth().weight(0.1f),
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
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
    }
}