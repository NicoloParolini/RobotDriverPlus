package com.livingcode.test.robotdriverplus.ui.navigation

abstract class FlowCoordinator {
    abstract val startStep : FlowStep

    suspend fun startFlow(input: FlowResult): FlowResult {
        return startStep.execute(input)
    }
}