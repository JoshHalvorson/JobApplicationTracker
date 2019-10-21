package dev.joshhalvorson.jobapptracker.model

data class Application(
    val company: String,
    val dateApplied: String,
    val moveAlong: Boolean,
    val response: Boolean
)