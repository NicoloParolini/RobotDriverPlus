package com.livingcode.test.robotdriverplus.domain.configuration

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConfigurationStorage(private val sharedPreferences: SharedPreferences) {
    private val configCache: MutableMap<String, Configuration> = mutableMapOf()
    private val configLabel = "configurations"
    fun saveConfiguration(configuration: Configuration) {
        configCache[configuration.controller] = configuration
        val json = Gson().toJson(configCache.mapValues { config ->
            config.value.toJsonConfiguration()
        })
        sharedPreferences.edit().putString(configLabel, json).apply()
    }

    fun loadConfigurationMap(): Map<String, JsonConfiguration> {
        sharedPreferences.getString(configLabel, null)?.let { json ->
            return Gson().fromJson(json, object :
                TypeToken<Map<String, JsonConfiguration>>() {}.type)
        }
        return mapOf()
    }

    private fun Configuration.toJsonConfiguration(): JsonConfiguration {
        val jsonCommands = mutableListOf<JsonCommand>()
        commands.forEach { button ->
            button.value.forEach { motorId ->
                jsonCommands.add(
                    JsonCommand(
                        button = button.key,
                        robot = motorId.key.robot,
                        motor = motorId.key.motor,
                        command = motorId.value
                    )
                )
            }
        }
        return JsonConfiguration(
            controller = controller,
            commands = jsonCommands
        )
    }
}