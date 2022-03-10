package com.livingcode.test.robotdriverplus.ui.controller

class ControlledRobotViewModel(
    val label: String,
    val onSelect: (String, String, MotorSelectorViewModel.MotorDirection) -> Unit
) {
    val motors = mapOf(
        "A" to MotorSelectorViewModel("A") { motor, direction ->
            onSelect(
                label,
                motor,
                direction
            )
        },
        "B" to MotorSelectorViewModel("B") { motor, direction ->
            onSelect(
                label,
                motor,
                direction
            )
        },
        "C" to MotorSelectorViewModel("C") { motor, direction -> onSelect(label, motor, direction) }
    )
}