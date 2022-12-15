package com.kenankaric.mviexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.kenankaric.mviexample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    /**
     * Using [ActivityMainBinding] we're providing safe access to our UI components
     * by view binding.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * We are lazily injecting our [MainViewModel].
     * Meaning, it'll only be called when it's actually needed, so it doesn't waste resources.
     */
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Here we're inflating our layout.
         * [binding] magically knows which layout belongs to which activity/fragment.
         */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        updateGreetingMessage()
    }

    private fun setupUI() {
        /**
         * Since [StateFlow] and [MutableStateFlow] are coroutines, then need to be ran within some
         * scope; here we launch it within the scope of current lifecycle.
         *
         * Both collect their values. Here we get back our state back.
         * In one instance our state (triggred by the init block in the view model) will return
         * to us "state.name" with empty string which will update UI.
         *
         * Then when user starts to type, we trigger new intent/event which will be called and our
         * newly updated state will return actual name typed in into the input field.
         *
         * Note: Be aware, such coroutines are not lifecycle aware so you need to explicitly define
         * when they will be triggered ie manually bind them to some lifecycle event so they're not
         * collected forever.
         * This is small example so it's not needed (since it's for purposes of demonstrating it work).
         */
        lifecycleScope.launch {
            viewModel.name.collect { state ->
                binding.lblGreetingMessage.text = getString(R.string.greeting_message, state.name)
            }
        }
    }

    private fun updateGreetingMessage() {
        binding.etUsername.addTextChangedListener { text: Editable? ->
            /**
             * Calling such intent in the exposed method ensures that we have (from the UI perspective)
             * a restricted access to what's going on; since we know we're triggering something
             * with some data; but don't know what's happening internally.
             */
            viewModel.onIntent(MainActivityIntent.NameInput(text.toString()))
        }
    }
}