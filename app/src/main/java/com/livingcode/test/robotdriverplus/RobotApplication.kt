package com.livingcode.test.robotdriverplus

import android.app.Application
import android.content.res.Resources
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
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RobotApplication)
            modules(koinModule)
        }
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}