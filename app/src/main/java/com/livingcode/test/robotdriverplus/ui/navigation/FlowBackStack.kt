package com.livingcode.test.robotdriverplus.ui.navigation

class FlowBackStack {
    var currentViewModel : FlowViewModel? = null
    var currentStep : FlowStep? = null

    fun back(){
        currentViewModel?.onBack()
    }
}