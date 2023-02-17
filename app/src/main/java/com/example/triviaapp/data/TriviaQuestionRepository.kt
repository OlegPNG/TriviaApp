package com.example.triviaapp.data

import com.example.triviaapp.model.Result
import com.example.triviaapp.network.TriviaApiService

interface TriviaQuestionRepository {

    suspend fun getTriviaQuestions(): List<Result>
}

class DefaultTriviaQuestionRepository(
    private val triviaApiService: TriviaApiService
) : TriviaQuestionRepository {

    override suspend fun getTriviaQuestions(): List<Result> {
        val response = triviaApiService.getQuestions()
        return response.results
    }
}