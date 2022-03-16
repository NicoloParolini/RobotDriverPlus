package com.livingcode.test.robotdriverplus

import android.app.Application
import android.bluetooth.BluetoothManager
import android.content.res.Resources
import android.hardware.input.InputManager
import com.livingcode.test.robotdriverplus.domain.configuration.Configurator
import com.livingcode.test.robotdriverplus.domain.controller.ControllerHandler
import com.livingcode.test.robotdriverplus.domain.controller.ControllerListener
import com.livingcode.test.robotdriverplus.domain.controller.ControllerStorage
import com.livingcode.test.robotdriverplus.domain.driver.RobotDriver
import com.livingcode.test.robotdriverplus.domain.robot.RobotConnector
import com.livingcode.test.robotdriverplus.ui.navigation.FlowBackStack
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import timber.log.Timber

class RobotApplication : Application() {
    private val koinModule = module {
        single { androidContext().resources }
        single { FlowBackStack() }
        single { Configurator() }
        single { RobotDriver() }
        single { ControllerListener(get(), androidContext().getSystemService(InputManager::class.java)) }
        single { ControllerStorage() }
        single { RobotConnector(androidContext().getSystemService(BluetoothManager::class.java)) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RobotApplication)
            modules(koinModule)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}