/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.TARDIS.commands.tardis;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.utility.TARDISUpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;

/**
 * @author eccentric_nz
 */
class TARDISVersionCommand {

    private final TARDIS plugin;

    TARDISVersionCommand(TARDIS plugin) {
        this.plugin = plugin;
    }

    boolean displayVersion(CommandSender sender) {
        String messagePrefix = plugin.getMessagePrefix();
        List<String> hooks = plugin.getDescription().getSoftDepend();
        String tardisversion = plugin.getDescription().getVersion();
        String chunkversion = plugin.getPM().getPlugin("TARDISChunkGenerator").getDescription().getVersion();
        String cb = Bukkit.getVersion();
        // send server and TARDIS versions
        sender.sendMessage(messagePrefix + "Server version: " + ChatColor.AQUA + cb);
        sender.sendMessage(messagePrefix + "TARDIS version: " + ChatColor.AQUA + tardisversion);
        sender.sendMessage(messagePrefix + "TARDISChunkGenerator version: " + ChatColor.AQUA + chunkversion);
        // send dependent plugin versions
        for (Plugin hook : plugin.getPM().getPlugins()) {
            PluginDescriptionFile desc = hook.getDescription();
            String name = desc.getName();
            String version = desc.getVersion();
            if (hooks.contains(name)) {
                sender.sendMessage(messagePrefix + name + " version: " + ChatColor.AQUA + version);
            }
        }
        // check for new TARDIS build
        if (sender.isOp()) {
            sender.sendMessage(messagePrefix + "Checking for new TARDIS builds...");
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new TARDISUpdateChecker(plugin, sender));
        }
        return true;
    }
}
