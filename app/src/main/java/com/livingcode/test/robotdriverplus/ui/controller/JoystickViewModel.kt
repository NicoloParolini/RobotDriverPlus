package com.livingcode.test.robotdriverplus.ui.controller

import android.text.Layout
import com.livingcode.test.robotdriverplus.R
import com.livingcode.test.robotdriverplus.StateFlowContainer

class JoystickViewModel(val name : String, private val onSelect : (Directions) -> Unit) {
    fun onSelect(direction : Directions) {
        onSelect(direction)
    }

    enum class Directions(val iconResource : Int) {
        UP(R.drawable.icon_up),
        DOWN(R.drawable.icon_down),
        LEFT(R.drawable.icon_left),
        RIGHT(R.drawable.icon_right),
        UNUSED(0)
    }
}