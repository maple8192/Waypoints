package io.github.maple8192.waypoints.timer

import io.github.maple8192.waypoints.Waypoints
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class BossBarHandler : BukkitRunnable() {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val guides = Waypoints.handler.getGuides(player.uniqueId)
            for (guide in guides.entries) {
                guide.value.addPlayer(player)
                guide.value.isVisible = true
                val loc = Waypoints.handler.getWaypoint(player.uniqueId, guide.key) ?: continue
                guide.value.setTitle(
                    if (loc.world == player.world) {
                        val name = if (guide.key.length > 8) { guide.key.substring(0..6) + "..." } else { guide.key }
                        val location = "(${loc.x.roundToInt()},${loc.y.roundToInt()},${loc.z.roundToInt()})"
                        val direction = StringBuilder().also {
                            val destVec = loc.toVector().subtract(player.location.toVector()).also { vec -> vec.y = 0.0 }
                            val playerVec = Vector(-sin(player.location.yaw * 3.14159265 / 180.0), 0.0, cos(player.location.yaw * 3.14159265 / 180.0))
                            val angle = destVec.angle(playerVec) * 180.0 / 3.14159265
                            val angleF = if (angle.isNaN()) 0.0001 else angle
                            val mul = destVec.z * playerVec.x - destVec.x * playerVec.z
                            val diff = (mul / abs(mul + 0.000001) * angleF / 5.0).roundToInt()

                            it.append(if (diff < -18) { "${ChatColor.RED}<${ChatColor.RESET} " } else { "  " })
                            for (i in -18..18) {
                                it.append(if ((diff - i) % 6 == 0) {
                                    if (diff - i == 0) {
                                        "${ChatColor.RED}|${ChatColor.RESET} "
                                    } else if (diff - i == 18) {
                                        "${ChatColor.AQUA}>${ChatColor.RESET} "
                                    } else if (diff - i == -18) {
                                        "${ChatColor.AQUA}<${ChatColor.RESET} "
                                    } else if (diff - i == 36 || diff - i == -36) {
                                        "${ChatColor.AQUA}=${ChatColor.RESET} "
                                    } else {
                                        "| "
                                    }
                                } else { ": " })
                            }
                            it.append(if (diff > 18) { "${ChatColor.RED}>" } else { " " })
                        }.toString()
                        val upDown = if (loc.y.roundToInt() > player.location.y.roundToInt()) { "^" } else if (loc.y.roundToInt() < player.location.y.roundToInt()) { "v" } else { "=" }
                        val distance = loc.distance(player.location).toInt()
                        "${ChatColor.GREEN}$name ${ChatColor.GRAY}${location} ${ChatColor.RESET}${direction} ${ChatColor.GREEN}${upDown} ${ChatColor.AQUA}${distance}m"
                    } else {
                        "${ChatColor.RED}Not in same world${ChatColor.RESET}(${ChatColor.GRAY}${loc.world.name}${ChatColor.RESET})"
                    }
                )
            }
        }
    }
}