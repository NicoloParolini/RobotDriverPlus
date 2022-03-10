package com.livingcode.test.robotdriverplus.ui.navigation

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class FlowViewModel : ViewModel(), KoinComponent {
    private val backStack : FlowBackStack by inject()
    private var proceed : Continuation<FlowResult>? = null

    suspend fun proceed() : FlowResult = suspendCoroutine {
        backStack.currentViewModel = this
        proceed = it
    }

    private fun back() {
        proceed?.resume(FlowResult.RESULT_BACK)
    }

    open fun onBack() {
        back()
    }

    fun next(result: FlowResult){
        proceed?.resume(result)
    }
}