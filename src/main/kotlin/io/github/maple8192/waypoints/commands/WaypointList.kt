package io.github.maple8192.waypoints.commands

import io.github.maple8192.waypoints.Waypoints
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class WaypointList : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return true

        val waypoints = Waypoints.handler.getWaypoints(sender.uniqueId)
        sender.sendMessage(Component.text("===== ").append(Component.text("Waypoints").color(NamedTextColor.AQUA)).append(Component.text(" =====")))
        for (wp in waypoints.toSortedMap().entries) {
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

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        return null
    }
}