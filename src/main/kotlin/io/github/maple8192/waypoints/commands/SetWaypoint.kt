package io.github.maple8192.waypoints.commands

import io.github.maple8192.waypoints.Waypoints
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetWaypoint : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return true

        if (args?.size != 1) return true

        Waypoints.handler.addWaypoint(sender.uniqueId, args[0], sender.location)

        return true
    }
}