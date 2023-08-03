package io.github.maple8192.waypoints.commands

import io.github.maple8192.waypoints.Waypoints
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class CancelGuide : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return true

        if (args.isNullOrEmpty()) {
            val guides = Waypoints.handler.getGuides(sender.uniqueId)
            for (guide in guides.entries) {
                Waypoints.handler.removeGuide(sender.uniqueId, guide.key)
            }
        } else {
            Waypoints.handler.removeGuide(sender.uniqueId, args[0])
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        return null
    }
}