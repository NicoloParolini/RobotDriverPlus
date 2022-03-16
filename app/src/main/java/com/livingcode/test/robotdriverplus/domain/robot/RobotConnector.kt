package com.livingcode.test.robotdriverplus.domain.robot

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.net.MacAddress
import androidx.core.app.ActivityCompat
import com.livingcode.test.robotdriverplus.StateFlowContainer
import com.livingcode.test.robotdriverplus.ui.models.DomainResult
import com.livingcode.test.robotdriverplus.ui.models.Errors
import com.livingcode.test.robotdriverplus.ui.models.Robot
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
            } ?: DomainResult(error = Errors.ERROR_NOT_CONNECTED, data = robot.copy(connected = false))
        } catch (ex: IOException) {
            DomainResult(error = Errors.ERROR_CLOSE_FAILED, data = robot.copy(connected = true))
        }
    }
}