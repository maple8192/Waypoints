package io.github.maple8192.waypoints.commands

import io.github.maple8192.waypoints.Waypoints
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WaypointList : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return true

        val waypoints = Waypoints.handler.getWaypoints(sender.uniqueId)
        for (wp in waypoints.entries) {
            sender.sendMessage(
                Component.text("- ${wp.key}")
                    .append(Component.text("     "))
                    .append(Component.text(wp.value.world.name).color(NamedTextColor.GREEN))
                    .append(Component.text(" "))
                    .append(Component.text("(${wp.value.x.toInt()},${wp.value.y.toInt()},${wp.value.z.toInt()})").color(NamedTextColor.GRAY))
            )
        }

        return true
    }
}