package io.github.maple8192.waypoints.waypoint

import org.bukkit.Location
import java.util.*

class WaypointHandler {
    private val waypoints: MutableMap<UUID, MutableMap<String, Location>> = mutableMapOf()

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
}