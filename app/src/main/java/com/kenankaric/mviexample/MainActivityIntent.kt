package com.kenankaric.mviexample

/**
 * This can be called either Intent/Event.
 * Sometimes you have both for different purposes.
 *
 * Idea of such a class is to define everything that user can perform on the screen as a form of intent
 * (input, button click, etc).
 *
 * This way we're restricting UI to only send intents/events to wherever while keeping all the logic
 * that will execute as a black box ie. hidden.
 */
sealed class MainActivityIntent {
    object EmptyName : MainActivityIntent()
    data class NameInput(val input: String) : MainActivityIntent()
}
