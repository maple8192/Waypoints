package io.github.maple8192.waypoints.waypoint

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import java.util.*

class WaypointHandler {
    private val waypoints: MutableMap<UUID, MutableMap<String, Location>> = mutableMapOf()
    private val guides: MutableMap<UUID, MutableMap<String, BossBar>> = mutableMapOf()

    fun addWaypoint(uuid: UUID, name: String, location: Location) {
        if (!waypoints.containsKey(uuid)) {
            waypoints[uuid] = mutableMapOf()
        }
        waypoints[uuid]?.put(name, location)
    }

    fun getWaypoint(uuid: UUID, name: String): Location? {
        if (!waypoints.containsKey(uuid)) return null
        return waypoints[uuid]?.get(name)
    }

    fun getWaypoints(uuid: UUID): Map<String, Location> {
        if (!waypoints.containsKey(uuid)) return mapOf()
        return waypoints[uuid]!!.toMap()
    }

    fun addGuide(uuid: UUID, name: String) {
        if (!waypoints.containsKey(uuid)) return
        if (waypoints[uuid]?.get(name) == null) return
        if (guides[uuid]?.containsKey(name) == true) return

        if (!guides.containsKey(uuid)) guides[uuid] = mutableMapOf()
        guides[uuid]?.put(name, Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID).also { it.addPlayer(Bukkit.getPlayer(uuid) ?: return); it.isVisible = true })
    }
}