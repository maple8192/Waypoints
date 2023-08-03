package io.github.maple8192.waypoints

import io.github.maple8192.waypoints.commands.*
import io.github.maple8192.waypoints.timer.BossBarHandler
import io.github.maple8192.waypoints.waypoint.WaypointHandler
import io.github.maple8192.waypoints.yml.WaypointData
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Waypoints : JavaPlugin() {
    private val repository = WaypointData(this)

    override fun onEnable() {
        handler = WaypointHandler(repository.load())

        getCommand("setwaypoint")?.setExecutor(SetWaypoint())
        getCommand("removewaypoint")?.setExecutor(RemoveWaypoint())
        getCommand("waypoints")?.setExecutor(WaypointList())
        getCommand("go")?.setExecutor(ShowGuide())
        getCommand("cancel")?.setExecutor(CancelGuide())

        BossBarHandler().runTaskTimer(this, 0, 1)
    }

    override fun onDisable() {
        Bukkit.getOnlinePlayers().forEach {
            handler.getGuides(it.uniqueId).forEach { guide ->
                handler.removeGuide(it.uniqueId, guide.key)
            }
        }
        repository.save(handler.getWaypointsAll())
    }

    companion object {
        lateinit var handler: WaypointHandler
    }
}