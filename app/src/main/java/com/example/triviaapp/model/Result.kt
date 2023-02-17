package com.example.triviaapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val incorrect_answers: List<String>,
    val question: String,
    val type: String
)