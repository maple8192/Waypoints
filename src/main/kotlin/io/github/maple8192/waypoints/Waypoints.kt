package io.github.maple8192.waypoints

import io.github.maple8192.waypoints.commands.CancelGuide
import io.github.maple8192.waypoints.commands.SetWaypoint
import io.github.maple8192.waypoints.commands.ShowGuide
import io.github.maple8192.waypoints.commands.WaypointList
import io.github.maple8192.waypoints.waypoint.WaypointHandler
import org.bukkit.plugin.java.JavaPlugin

class Waypoints : JavaPlugin() {
    override fun onEnable() {
        getCommand("setwaypoint")?.setExecutor(SetWaypoint())
        getCommand("waypoints")?.setExecutor(WaypointList())
        getCommand("go")?.setExecutor(ShowGuide())
        getCommand("cancel")?.setExecutor(CancelGuide())
    }

    companion object {
        val handler = WaypointHandler()
    }
}