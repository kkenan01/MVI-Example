package com.kenankaric.mviexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    /**
     * Similar to [LiveData]/[MutableLiveData], we have [StateFlow]/[MutableStateFlow].
     *
     * It needs to have a default value, so we use our [MainActivityState] for it.
     */
    private val _name: MutableStateFlow<MainActivityState> = MutableStateFlow(MainActivityState())

    /**
     * We don't want to expose mutable version of this state, since we don't want anything from
     * the outside to change it.
     * So we just provide **read-only** version of it.
     */
    val name: StateFlow<MainActivityState> = _name.asStateFlow()

    /**
     * init{ ... } block will run immediately after [MainViewModel] has been created.
     * We use this opportunity to setup our initial UI state with whatever we want.
     *
     * In this case, it's about setting text to the screen with empty name.
     */
    init {
        onIntent(MainActivityIntent.EmptyName)
    }

    /**
     * Good practice to work with states is to think of it like this:
     * You get old state, update it with new one, re-emit new state.
     *
     * In the method bellow we call ".update" extension function which returns to us old state.
     * Then by calling copy, we say that we only want some values (in this case single value) to
     * update and it will after that automatically re-emit as a new state.
     *
     * Calling copy ensures that data flows in one way, called unidirectional data flow which we want.
     * Ie. only update data here and UI will pick it up and render it on the screen.
     */
    private fun updateName(newName: String) {
        _name.update { oldState ->
            oldState.copy(name = newName)
        }
    }

    private fun updateWithNoName() {
        _name.update { oldState ->
            oldState.copy(name = AppConst.EMPTY_NAME)
        }
    }

    /**
     * One way to solve on how to work with intents/events is to expose public method
     * which will accept all the intents/events you defined in [MainActivityIntent].
     *
     * This way all your logic will be kept private, UI will only know to trigger something,
     * and something will come back. Essentially keeping the [ViewModel] as a black box.
     */
    fun onIntent(event: MainActivityIntent) {
        when (event) {
            MainActivityIntent.EmptyName -> updateWithNoName()
            is MainActivityIntent.NameInput -> updateName(event.input)
        }
    }
}