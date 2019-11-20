package dev.joshhalvorson.jobapptracker.model

import java.io.Serializable

data class Application(
    val company: String,
    val dateApplied: String,
    val moveAlong: Boolean,
    val response: Boolean,
    val firstInterview: Boolean,
    val secondInterview: Boolean,
    val thirdInterview: Boolean,
    val offer: Boolean
) : Serializable