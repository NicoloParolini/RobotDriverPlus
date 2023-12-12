package com.livingcode.test.robotdriverplus.domain.controller

import android.content.SharedPreferences
import com.livingcode.test.robotdriverplus.ui.models.Controller

class ControllerStorage(private val prefs: SharedPreferences) {
    private val cache: MutableMap<String, Controller> = mutableMapOf()

    fun connect(controller: Controller) {
        val name = prefs.getString(controller.descriptor, null)
        val namedController = controller.copy(name = name.let { name } ?: controller.name)
        if (!cache.containsKey(namedController.descriptor)) {
            saveController(namedController)
        }
    }

    fun remove(controller: Controller) {
        cache.remove(controller.descriptor)
    }

    fun getControllers(): List<Controller> {
        return cache.values.toList()
    }

    fun getController(id: String): Controller? {
        return cache[id]
    }

    fun change(controller: Controller) {
        saveController(controller)
    }

    private fun saveController(controller: Controller) {
        cache[controller.descriptor] = controller
        prefs.edit().putString(controller.descriptor, controller.name).apply()
    }
}