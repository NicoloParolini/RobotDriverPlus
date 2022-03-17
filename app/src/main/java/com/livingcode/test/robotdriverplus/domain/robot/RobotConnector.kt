package com.livingcode.test.robotdriverplus.domain.robot

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import com.livingcode.test.robotdriverplus.domain.configuration.MotorCommand
import com.livingcode.test.robotdriverplus.domain.configuration.MotorId
import com.livingcode.test.robotdriverplus.ui.models.DomainResult
import com.livingcode.test.robotdriverplus.ui.models.Errors
import com.livingcode.test.robotdriverplus.ui.models.Robot
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import java.util.*

class RobotConnector(btManager: BluetoothManager) {
    private val btAdapter: BluetoothAdapter = btManager.adapter
    private val connectedRobots: MutableMap<String, BluetoothSocket> = mutableMapOf()

    @SuppressLint("MissingPermission")
    fun scanForRobots(): List<Robot> {
        return btAdapter.bondedDevices.map { dev ->
            Robot(name = dev.name, macAddress = dev.address, connected = false)
        }.sortedBy { it.name }
    }

    @SuppressLint("MissingPermission")
    fun connectRobot(robot: Robot): DomainResult<Robot> {
        return try {
            val socket = btAdapter.getRemoteDevice(robot.macAddress)
                ?.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
            socket?.let {
                socket.connect()
                connectedRobots[robot.macAddress] = socket
                DomainResult(data = robot.copy(connected = true))
            } ?: DomainResult(error = Errors.ERROR_NO_SOCKET)
        } catch (ex: IOException) {
            DomainResult(error = Errors.ERROR_CONNECTION_FAILED)
        }
    }

    fun disconnectRobot(robot: Robot): DomainResult<Robot> {
        return try {
            connectedRobots[robot.macAddress]?.let {
                it.inputStream.close()
                it.outputStream.close()
                it.close()
                DomainResult(data = robot.copy(connected = false))
            } ?: DomainResult(
                error = Errors.ERROR_NOT_CONNECTED,
                data = robot.copy(connected = false)
            )
        } catch (ex: IOException) {
            DomainResult(error = Errors.ERROR_CLOSE_FAILED, data = robot.copy(connected = true))
        }
    }

    fun commandRobot(robot: Robot, motor: Motors, command: MotorCommand) {
        Timber.v("Command $robot $motor $command")
        connectedRobots[robot.macAddress]?.let {socket ->
            val cmd: Instructions? = when (command) {
                MotorCommand.FWD -> Instructions(Instructions.forward(motor.byteId))
                MotorCommand.BACK -> Instructions(Instructions.backward(motor.byteId))
                MotorCommand.COAST -> Instructions(Instructions.brake(motor.byteId))
                MotorCommand.BRAKE -> Instructions(Instructions.brake(motor.byteId))
                MotorCommand.NONE -> null
            }
            cmd?.let {
                writeBuffers(cmd.buffers, socket) }
        }
    }

    private fun writeBuffers(buffers: Array<ByteArray?>, socket: BluetoothSocket) {
        Timber.v("TEEEST Sending buffers")
        try {
            for (cursor in buffers.indices) {
                buffers[cursor]?.let { socket.outputStream.write(it) }
            }
            socket.outputStream.flush()
        } catch (e: IOException) {
            Timber.e("Robot disconnected")
        }
    }
}