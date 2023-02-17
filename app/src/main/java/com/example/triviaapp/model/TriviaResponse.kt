package com.example.triviaapp.model

import kotlinx.serialization.Serializable

@Serializable
data class TriviaResponse(
    val response_code: Int,
    val results: List<Result>
)