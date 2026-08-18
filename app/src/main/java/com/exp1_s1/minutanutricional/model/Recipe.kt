package com.exp1_s1.minutanutricional.model

data class Recipe(
    val title: String,
    val day: String,
    val description: String,
    val ingredients: List<String>,
    val nutritionalRecommendation: String
)
