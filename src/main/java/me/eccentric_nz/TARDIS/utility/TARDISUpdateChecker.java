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
package me.eccentric_nz.TARDIS.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.messaging.TARDISMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class TARDISUpdateChecker implements Runnable {

    private final TARDIS plugin;
    private final JsonParser jsonParser;
    private final CommandSender sender;

    public TARDISUpdateChecker(TARDIS plugin, CommandSender sender) {
        this.plugin = plugin;
        jsonParser = new JsonParser();
        this.sender = sender;
    }

    @Override
    public void run() {
        String version = plugin.getGeneralKeeper().getPluginYAML().getString("version");
        if (!version.contains("-")) {
            // Version has no "-SNAPSHOT" suffix
            // TODO Make one of the Jenkins properties contain the version number so releases can be checked as well.
            if (sender == null) {
                plugin.getLogger().log(Level.WARNING, "Build number is missing from plugin.yml!");
            } else {
                sender.sendMessage(plugin.getMessagePrefix() + "Build number is missing from plugin.yml!");
            }
            return;
        }
        if (version.indexOf("-") >= version.length()) {
            // Version has a hyphen but no build number
            if (sender == null) {
                plugin.getLogger().log(Level.WARNING, "Build number is missing from plugin.yml!");
            } else {
                sender.sendMessage(plugin.getMessagePrefix() + "Build number is missing from plugin.yml!");
            }
            return;
        }
        String build = version.split("-")[1];
        int buildNumber;
        try {
            buildNumber = Integer.parseInt(build);
        } catch (NumberFormatException e) {
            // Build number is not a number
            if (sender == null) {
                plugin.getLogger().log(Level.WARNING, "Build number is invalid!");
            } else {
                sender.sendMessage(plugin.getMessagePrefix() + "Build number is invalid!");
            }
            return;
        }
        JsonObject lastBuild = fetchLatestJenkinsBuild();
        if (lastBuild == null || !lastBuild.has("id")) {
            // couldn't get Jenkins info
            if (sender == null) {
                plugin.getLogger().log(Level.WARNING, "Failed to fetch latest build from Jenkins!");
            } else {
                sender.sendMessage(plugin.getMessagePrefix() + "Failed to fetch latest build from Jenkins!");
            }
            return;
        }
        int newBuildNumber = lastBuild.get("id").getAsInt();
        if (newBuildNumber < buildNumber) {
            // if new build number is older
            if (sender == null) {
                plugin.getLogger().log(Level.WARNING, "The latest build number is older than this version's!");
            } else {
                sender.sendMessage(plugin.getMessagePrefix() + "The latest build number is older than this version's!");
            }
            return;
        }
        plugin.setUpdateFound(true);
        plugin.setBuildNumber(buildNumber);
        plugin.setUpdateNumber(newBuildNumber);
        if (buildNumber == newBuildNumber) {
            if (sender == null) {
                plugin.getLogger().log(Level.INFO, "TARDIS is up-to-date!");
            } else {
                sender.sendMessage(plugin.getMessagePrefix() + "TARDIS is up-to-date!");
            }
        } else {
            if (sender == null) {
                plugin.getLogger().log(Level.WARNING, String.format(TARDISMessage.JENKINS_UPDATE_READY, buildNumber, newBuildNumber));
                plugin.getLogger().log(Level.WARNING, TARDISMessage.UPDATE_COMMAND);
            } else {
                sender.sendMessage(plugin.getMessagePrefix() + "TARDIS is " + (newBuildNumber - buildNumber) + " builds behind! Type " + ChatColor.AQUA + "/tadmin update_plugins" + ChatColor.RESET + " to update!");
            }
        }
    }

    /**
     * Fetches from jenkins, using the REST api the last snapshot build information
     */
    private JsonObject fetchLatestJenkinsBuild() {
        try {
            // We're connecting to TARDIS's Jenkins REST api
            URL url = new URL("http://tardisjenkins.duckdns.org:8080/job/TARDIS/lastSuccessfulBuild/api/json");
            // Create a connection
            URLConnection request = url.openConnection();
            request.setRequestProperty("User-Agent", "TARDISPlugin");
            request.connect();
            // Convert to a JSON object
            JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            return root.getAsJsonObject();
        } catch (Exception e) {
            plugin.debug("Failed to check for a snapshot update on TARDIS Jenkins.");
        }
        return null;
    }
}
