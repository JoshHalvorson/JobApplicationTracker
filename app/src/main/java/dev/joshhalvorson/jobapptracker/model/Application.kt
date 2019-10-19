package dev.joshhalvorson.jobapptracker.model

class Application(
    private val company: String,
    private val dateApplied: String,
    private val moveAlong: Boolean,
    private val response: Boolean
)