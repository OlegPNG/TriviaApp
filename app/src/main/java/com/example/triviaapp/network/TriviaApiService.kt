package com.example.triviaapp.network

import com.example.triviaapp.model.TriviaResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface TriviaApiService {
    @GET("api.php?amount=10&encode=base64&type=multiple")
    suspend fun getQuestions(): TriviaResponse
}

