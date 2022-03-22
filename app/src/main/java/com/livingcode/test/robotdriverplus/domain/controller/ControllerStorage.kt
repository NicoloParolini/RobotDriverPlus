package com.livingcode.test.robotdriverplus.domain.controller

import com.livingcode.test.robotdriverplus.ui.models.Controller

class ControllerStorage {
    private val cache : MutableMap<String, Controller> = mutableMapOf()

    fun connect(controller: Controller) {
        cache[controller.name] = controller
    }

    fun remove(controller: Controller) {
        cache.remove(controller.name)
    }

    fun getControllers() : List<Controller>{
        return cache.values.toList()
    }

    fun getController(id : String) : Controller? {
        return cache[id]
    }
}