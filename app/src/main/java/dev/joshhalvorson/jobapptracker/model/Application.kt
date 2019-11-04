package dev.joshhalvorson.jobapptracker.model

import java.io.Serializable

data class Application(
    val company: String,
    val dateApplied: String,
    val moveAlong: Boolean,
    val response: Boolean
) : Serializable