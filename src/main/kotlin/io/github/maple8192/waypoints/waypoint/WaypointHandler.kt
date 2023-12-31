package io.github.maple8192.waypoints.waypoint

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import java.util.*

class WaypointHandler(fromConfig: Map<UUID, Map<String, Location>>) {
    private val waypoints: MutableMap<UUID, MutableMap<String, Location>> = fromConfig.map { it.key to it.value.toMutableMap() }.toMap().toMutableMap()
    private val guides: MutableMap<UUID, MutableMap<String, BossBar>> = mutableMapOf()

    fun addWaypoint(uuid: UUID, name: String, location: Location) {
        if (!waypoints.containsKey(uuid)) {
            waypoints[uuid] = mutableMapOf()
        }
        waypoints[uuid]?.put(name, location)
    }

    fun removeWaypoint(uuid: UUID, name: String) {
        if (!waypoints.containsKey(uuid)) return
        if (waypoints[uuid]?.containsKey(name) == false) return
        removeGuide(uuid, name)
        waypoints[uuid]?.remove(name)
    }

    fun getWaypoint(uuid: UUID, name: String): Location? {
        if (!waypoints.containsKey(uuid)) return null
        return waypoints[uuid]?.get(name)
    }

    fun getWaypoints(uuid: UUID): Map<String, Location> {
        if (!waypoints.containsKey(uuid)) return mapOf()
        return waypoints[uuid]!!.toMap()
    }

    fun getWaypointsAll(): Map<UUID, Map<String, Location>> {
        return waypoints.toMap()
    }

    fun addGuide(uuid: UUID, name: String) {
        if (!waypoints.containsKey(uuid)) return
        if (waypoints[uuid]?.get(name) == null) return
        if (guides[uuid]?.containsKey(name) == true) return

        if (!guides.containsKey(uuid)) guides[uuid] = mutableMapOf()
        guides[uuid]?.put(name, Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID).also { it.addPlayer(Bukkit.getPlayer(uuid) ?: return); it.isVisible = true })
    }

    fun getGuides(uuid: UUID): Map<String, BossBar> {
        if (!guides.containsKey(uuid)) return mapOf()
        return guides[uuid]!!.toMap()
    }

    fun removeGuide(uuid: UUID, name: String) {
        if (!(guides[uuid] ?: return).containsKey(name)) return
        guides[uuid]?.get(name)?.isVisible = false
        guides[uuid]?.remove(name)
    }
}