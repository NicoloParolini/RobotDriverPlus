package com.livingcode.test.robotdriverplus.domain.robot

class Instructions(c0: ByteArray?) {
    val buffers = Array<ByteArray?>(2) {
        ByteArray(
            14
        )
    }

    init {
        buffers[0] = c0
    }

    companion object {
        const val MOTOR_A: Byte = 0
        const val MOTOR_B: Byte = 1
        const val MOTOR_C: Byte = 2
        fun backward(motor: Byte): ByteArray {
            val buffer = ByteArray(14)
            buffer[0] = (14 - 2).toByte() // length lsb
            buffer[1] = 0 // length msb
            buffer[2] = 0 // direct command (with response)
            buffer[3] = 0x04 // set output state
            buffer[4] = motor // motor (A:0, B:1, C:2)
            buffer[5] = 100 // speed range (-100 : 100)
            buffer[6] = (1 + 2).toByte() // mode (MOTOR_ON)
            buffer[7] = 0
            buffer[8] = 0
            buffer[9] = 0x20 // run state (RUNNING)
            buffer[10] = 0
            buffer[11] = 0
            buffer[12] = 0
            buffer[13] = 0
            return buffer
        }

        fun forward(motor: Byte): ByteArray {
            val buffer = ByteArray(14)
            buffer[0] = (14 - 2).toByte() // length lsb
            buffer[1] = 0 // length msb
            buffer[2] = 0 // direct command (with response)
            buffer[3] = 0x04 // set output state
            buffer[4] = motor // motor (A:0, B:1, C:2)
            buffer[5] = -100 // speed range (-100 : 100)
            buffer[6] = (1 + 2).toByte() // mode (MOTOR_ON)
            buffer[7] = 0
            buffer[8] = 0
            buffer[9] = 0x20 // run state (RUNNING)
            buffer[10] = 0
            buffer[11] = 0
            buffer[12] = 0
            buffer[13] = 0
            return buffer
        }

        fun brake(motor: Byte): ByteArray {
            val buffer = ByteArray(14)
            buffer[0] = (14 - 2).toByte() // length lsb
            buffer[1] = 0 // length msb
            buffer[2] = 0 // direct command (with response)
            buffer[3] = 0x04 // set output state
            buffer[4] = motor // motor (A:0, B:1, C:2)
            buffer[5] = 0 // speed range (-100 : 100)
            buffer[6] = 2 // mode (MOTOR_ON)
            buffer[7] = -100
            buffer[8] = 0
            buffer[9] = 0x20 // run state (RUNNING)
            buffer[10] = 0
            buffer[11] = 0
            buffer[12] = 0
            buffer[13] = 0
            return buffer
        }
    }
}