package io.github.maple8192.waypoints

import io.github.maple8192.waypoints.commands.*
import io.github.maple8192.waypoints.timer.BossBarHandler
import io.github.maple8192.waypoints.waypoint.WaypointHandler
import org.bukkit.plugin.java.JavaPlugin

class Waypoints : JavaPlugin() {
    override fun onEnable() {
        getCommand("setwaypoint")?.setExecutor(SetWaypoint())
        getCommand("removewaypoint")?.setExecutor(RemoveWaypoint())
        getCommand("waypoints")?.setExecutor(WaypointList())
        getCommand("go")?.setExecutor(ShowGuide())
        getCommand("cancel")?.setExecutor(CancelGuide())

        BossBarHandler().runTaskTimer(this, 0, 1)
    }

    companion object {
        val handler = WaypointHandler()
    }
}