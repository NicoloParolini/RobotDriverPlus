package com.livingcode.test.robotdriverplus.domain.controller

import com.livingcode.test.robotdriverplus.models.Robot

data class Controller(val name : String, val descriptor : String, val id : Int, val robots : List<Robot> = listOf())