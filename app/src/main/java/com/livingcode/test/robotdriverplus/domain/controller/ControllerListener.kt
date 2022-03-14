package com.livingcode.test.robotdriverplus.domain.controller

import android.hardware.input.InputManager
import android.view.InputDevice

class ControllerListener(
    private val storage: ControllerStorage,
    private val inputManager: InputManager
) : InputManager.InputDeviceListener {
    init {
        scan()
    }

    override fun onInputDeviceAdded(p0: Int) {
        getController(p0)?.let{
            storage.connect(it)
        }
    }

    override fun onInputDeviceRemoved(p0: Int) {
        getController(p0)?.let{
            storage.remove(it)
        }
    }

    override fun onInputDeviceChanged(p0: Int) {
        getController(p0)?.let{
            storage.connect(it)
        }
    }

    private fun scan() {
        inputManager.inputDeviceIds.forEach {
            onInputDeviceAdded(it)
        }
    }

    private fun getController(id: Int): Controller? {
        val device = inputManager.getInputDevice(id)
        return if (device.supportsSource(InputDevice.SOURCE_JOYSTICK)) Controller(
            name = device.name,
            id = id,
            descriptor = device.descriptor
        ) else null
    }
}