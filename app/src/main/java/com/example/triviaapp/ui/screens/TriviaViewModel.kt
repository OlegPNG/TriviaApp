package com.example.triviaapp.ui.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.triviaapp.TriviaApplication
import com.example.triviaapp.data.DefaultTriviaQuestionRepository
import com.example.triviaapp.data.TriviaQuestionRepository
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.triviaapp.model.Result
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*
import java.util.Base64.getDecoder

sealed interface TriviaApiState {
    /*data class Success(
        val questions: String
    ) : TriviaUiState*/
    data class Success(
        val questionList: List<Result>,
        val selectedOption: MutableState<String> = mutableStateOf(""),
        var question: Result,
        var currentQuestion: Int = 0
    ) : TriviaApiState
    object Error : TriviaApiState
    object Loading : TriviaApiState
}

data class TriviaUiState(
    val questionList: List<Result>? = null,
    val selectedOption: MutableState<String> = mutableStateOf(""),
    val question: Result? = null,
    val questionIndex: Int = 0,
    val answerChoices: MutableList<String>? = null,
    val correctAnswers: Int = 0
)


class TriviaViewModel(private val triviaQuestionRepository: TriviaQuestionRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    val triviaApiState: MutableState<TriviaApiState> = mutableStateOf(TriviaApiState.Loading)

    private val _triviaUiState = MutableStateFlow(TriviaUiState())
    val triviaUiState: StateFlow<TriviaUiState> = _triviaUiState.asStateFlow()


    //val question: Result by mutableStateOf(Result("","","",listOf(),"","")) //Creates empty question object

    //Calls api and initializes uiState on init
    init {
        getTriviaQuestions()
    }


    private fun getTriviaQuestions() {
        viewModelScope.launch {
            triviaApiState.value = try {
                val result = triviaQuestionRepository.getTriviaQuestions()
                val answerStrings: MutableList<String> = mutableListOf()
                for (string in result[0].incorrect_answers) {
                    answerStrings.add(decodeHtmlString(string))
                }
                val newQuestion = decodeQuestionObject(result[0])

                _triviaUiState.update { currentState ->
                    currentState.copy(
                        questionList = result,
                        question = newQuestion,
                        answerChoices = (newQuestion.incorrect_answers + listOf(newQuestion.correct_answer)).toMutableList()
                    )
                }
                _triviaUiState.value.answerChoices?.shuffle()
                TriviaApiState.Success(questionList = result, question = result[0])
            } catch(e: IOException) {
                TriviaApiState.Error
            }
        }
    }

    fun updateQuestion(correctAnswer: Boolean) {
        val updatedIndex = triviaUiState.value.questionIndex + 1
        var correctAnswers = triviaUiState.value.correctAnswers
        if (correctAnswer) {
            correctAnswers += 1
        }
        Log.d("TriviaViewModel",correctAnswers.toString())
        if(updatedIndex <= 9) {
            val newQuestion =
                decodeQuestionObject(_triviaUiState.value.questionList!![updatedIndex])
            _triviaUiState.update { currentState ->
                currentState.copy(
                    questionIndex = updatedIndex,
                    question = newQuestion,
                    correctAnswers = correctAnswers,
                    selectedOption = mutableStateOf(""),
                    answerChoices = try {
                        (newQuestion.incorrect_answers +
                                newQuestion.correct_answer).toMutableList()
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                        null
                    }
                )
            }
            _triviaUiState.value.answerChoices?.shuffle()
        }
    }

    private fun decodeHtmlString(string: String): String {
        val utf8CharArray = string.toByteArray(Charsets.UTF_8)
        return utf8CharArray.toString(Charsets.UTF_8)
    }

    private fun decodeBase64(string: String): String {
        return String(getDecoder().decode(string))
    }

    private fun decodeQuestionObject(question: Result): com.example.triviaapp.model.Result {

        val incorrectAnswers: MutableList<String> = mutableListOf()
        for(string in question.incorrect_answers) {
            incorrectAnswers.add(decodeBase64(string))
        }

        return Result(
            category = decodeBase64(question.category),
            question = decodeBase64(question.question),
            incorrect_answers = incorrectAnswers,
            correct_answer = decodeBase64(question.correct_answer),
            difficulty = decodeBase64(question.difficulty),
            type = decodeBase64(question.type)
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TriviaApplication)
                val triviaQuestionRepository = application.container.triviaQuestionRepository
                TriviaViewModel(triviaQuestionRepository = triviaQuestionRepository)
            }
        }
    }
}
