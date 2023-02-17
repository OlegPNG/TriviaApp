package com.example.triviaapp.data

import com.example.triviaapp.network.TriviaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

interface AppContainer {

    val triviaQuestionRepository: TriviaQuestionRepository
}

class DefaultAppContainer : AppContainer {

    private val BASE_URL =
        "https://opentdb.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: TriviaApiService by lazy {
        retrofit.create(TriviaApiService::class.java)
    }

    override val triviaQuestionRepository: TriviaQuestionRepository by lazy {
        DefaultTriviaQuestionRepository(retrofitService)
    }
}