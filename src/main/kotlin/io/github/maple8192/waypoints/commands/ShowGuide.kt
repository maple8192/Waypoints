package io.github.maple8192.waypoints.commands

import io.github.maple8192.waypoints.Waypoints
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class ShowGuide : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return true

        if (args?.size != 1) return true

        Waypoints.handler.addGuide(sender.uniqueId, args[0])

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        if (sender !is Player) return null

        if (args?.size == 1) {
            val guides = Waypoints.handler.getGuides(sender.uniqueId).keys
            return Waypoints.handler.getWaypoints(sender.uniqueId).keys.filter { !guides.contains(it) }.filter { it.startsWith(args[0]) }.toMutableList()
        }

        return null
    }
}