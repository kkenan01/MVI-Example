package com.kenankaric.mviexample

/**
 * State class will represent everything that can be presented on the screen,
 * ie. displaying its state.
 *
 * Usually you'd have following:
 * 1. isLoading: Boolean
 * 2. success: SomeType
 * 3. failure: Throwable/String/Etc. Usually you'd have this as a single event or you can isolate
 * this into different state class. Idea is to show error once, which won't happen if for instance
 * configuration change happens (you rotate the screen).
 * 4. Everything else you'll need for your screen to operate as it should.
 *
 * Here our "success" state is just our name updated. Poor naming convention however it reflects
 * on what will be updated. This is small example so it's good to go.
 */
data class MainActivityState(
    val name: String = ""
)