package com.livingcode.test.robotdriverplus

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateFlowContainer<T>(initialValue: T) {
    private val innerFlow: MutableStateFlow<T> = MutableStateFlow(initialValue)
    val flow = innerFlow.asStateFlow()

    fun setValue(value: T) {
        innerFlow.value = value
    }
}