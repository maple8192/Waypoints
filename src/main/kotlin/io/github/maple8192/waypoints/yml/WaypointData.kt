package io.github.maple8192.waypoints.yml

import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.logging.Level

class WaypointData(private val plugin: Plugin) {
    private var config: FileConfiguration? = null
    private val fileName = "waypoints.yml"

    fun load(): Map<UUID, Map<String, Location>> {
        saveDefaultConfig()
        if (config != null) {
            reloadConfig()
        }
        config = getConfig()

        val waypoints = mutableMapOf<UUID, MutableMap<String, Location>>()
        for (uuid in config!!.getConfigurationSection("")!!.getKeys(false)) {
            val u = UUID.fromString(config!!.getString(uuid) ?: continue)
            waypoints[u] = mutableMapOf()
            for (name in (config!!.getConfigurationSection(uuid) ?: continue).getKeys(false)) {
                waypoints[u]?.put(name, config!!.getLocation("$uuid.$name") ?: continue)
            }
        }

        return waypoints
    }

    private fun saveDefaultConfig() {
        if (!File(plugin.dataFolder, fileName).exists()) {
            plugin.saveResource(fileName, false)
        }
    }

    private fun reloadConfig() {
        config = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, fileName))

        val stream = plugin.getResource(fileName) ?: return

        (config as YamlConfiguration).setDefaults(YamlConfiguration.loadConfiguration(InputStreamReader(stream, StandardCharsets.UTF_8)))
    }

    private fun getConfig(): FileConfiguration {
        if (config == null) {
            reloadConfig()
        }
        return config!!
    }

    private fun saveConfig() {
        if (config == null) {
            return
        }
        try {
            getConfig().save(File(plugin.dataFolder, fileName))
        } catch (ex: IOException) {
            plugin.logger.log(Level.SEVERE, "Could not save to $fileName", ex)
        }
    }
}