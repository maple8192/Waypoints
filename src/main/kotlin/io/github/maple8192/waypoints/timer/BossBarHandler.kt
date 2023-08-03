package io.github.maple8192.waypoints.timer

import io.github.maple8192.waypoints.Waypoints
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class BossBarHandler : BukkitRunnable() {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val guides = Waypoints.handler.getGuides(player.uniqueId)
            for (guide in guides.entries) {
                val loc = Waypoints.handler.getWaypoint(player.uniqueId, guide.key) ?: continue
                guide.value.setTitle(if (loc.world == player.world) { "${loc.distance(player.location).toInt()} m" } else { "Not in same world" })
            }
        }
    }
}