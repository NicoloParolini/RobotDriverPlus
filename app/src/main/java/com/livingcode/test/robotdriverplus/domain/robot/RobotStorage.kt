package com.livingcode.test.robotdriverplus.domain.robot

import com.livingcode.test.robotdriverplus.ui.models.Robot

class RobotStorage(private val robotConnector: RobotConnector) {
    private val robotsCache : MutableMap<String, Robot> = mutableMapOf()

    init {
        robotConnector.scanForRobots().forEach {robot ->
            saveRobot(robot)
        }
    }

    fun saveRobot(robot : Robot){
        robotsCache[robot.macAddress] = robot
    }

    fun getRobots() : List<Robot> {
        return robotsCache.values.toList()
    }

    fun getRobot(macAddress : String) : Robot? {
        return robotsCache[macAddress]
    }
}