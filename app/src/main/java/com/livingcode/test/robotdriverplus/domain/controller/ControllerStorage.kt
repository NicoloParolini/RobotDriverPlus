package com.livingcode.test.robotdriverplus.domain.controller

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
}