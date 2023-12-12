package com.livingcode.test.robotdriverplus.ui.navigation

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FlowStep(private val putOnStack: Boolean = true) : KoinComponent {
    private val backStack: FlowBackStack by inject()
    private var action: suspend (FlowResult) -> FlowResult = { FlowResult.RESULT_OK }
    private val nextSteps: MutableMap<FlowResult, FlowStep> = mutableMapOf()
    private var label: String = "Not set"

    fun addNextStep(condition: FlowResult, step: FlowStep): FlowStep {
        nextSteps[condition] = step
        return this
    }

    fun setAction(action: suspend (FlowResult) -> FlowResult): FlowStep {
        this.action = action
        return this
    }

    fun setLabel(label: String): FlowStep {
        this.label = label
        return this
    }

    fun useDefaultAction(
        viewModelConstructor: (FlowResult) -> FlowViewModel,
        screen: Screens,
        navigator: Navigator
    ): FlowStep {
        this.setAction {
            val vm = viewModelConstructor(it)
            navigator.navigate(Screen(screen, vm))
            return@setAction vm.proceed()
        }
        return this
    }

    suspend fun execute(prevResult: FlowResult): FlowResult {
        backStack.currentStep?.let { currentStep ->
            if (!nextSteps.containsKey(FlowResult.RESULT_BACK)) {
                addNextStep(FlowResult.RESULT_BACK, currentStep)
            }
        }

        if (putOnStack) {
            backStack.currentStep = this
        }

        val result = action(prevResult)
        return (nextSteps[result] ?: nextSteps[FlowResult.RESULT_ELSE])?.execute(result)
            ?: FlowResult.RESULT_OK
    }
}