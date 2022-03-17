package com.livingcode.test.robotdriverplus.domain.robot

enum class Motors(val id : String, val byteId : Byte) {
    A("A", Instructions.MOTOR_A), B("B", Instructions.MOTOR_B), C("C", Instructions.MOTOR_C)
}