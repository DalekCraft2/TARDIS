/*
 * Copyright (C) 2018 eccentric_nz
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
package me.eccentric_nz.TARDIS.listeners;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.ResultSetDoors;
import me.eccentric_nz.TARDIS.database.ResultSetTravellers;
import me.eccentric_nz.TARDIS.enumeration.COMPASS;
import me.eccentric_nz.TARDIS.utility.TARDISEntityTracker;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import me.eccentric_nz.TARDIS.utility.TARDISNumberParsers;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author eccentric_nz
 */
public class TARDISRenderRoomListener implements Listener {

    private final TARDIS plugin;

    public TARDISRenderRoomListener(TARDIS plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getTrackerKeeper().getRenderRoomOccupants().contains(player.getUniqueId())) {
            event.setCancelled(true);
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                // tp the player back to the TARDIS console
                transmat(player);
                player.updateInventory();
            }
        }
    }

    public void transmat(Player p) {
        TARDISMessage.send(p, "TRANSMAT");
        // get the TARDIS the player is in
        HashMap<String, Object> wherep = new HashMap<>();
        wherep.put("uuid", p.getUniqueId().toString());
        ResultSetTravellers rst = new ResultSetTravellers(plugin, wherep, false);
        if (rst.resultSet()) {
            int id = rst.getTardis_id();
            HashMap<String, Object> whered = new HashMap<>();
            whered.put("tardis_id", id);
            whered.put("door_type", 1);
            ResultSetDoors rsd = new ResultSetDoors(plugin, whered, false);
            if (rsd.resultSet()) {
                COMPASS d = rsd.getDoor_direction();
                String doorLocStr = rsd.getDoor_location();
                String[] split = doorLocStr.split(":");
                String w = (split[0].equals("TARDIS_TimeVortex") ? "tardis_time_vortex" : split[0].toLowerCase(Locale.ENGLISH));
                World cw = plugin.getServer().getWorld(w);
                int cx = TARDISNumberParsers.parseInt(split[1]);
                int cy = TARDISNumberParsers.parseInt(split[2]);
                int cz = TARDISNumberParsers.parseInt(split[3]);
                Location tp_loc = new Location(cw, cx, cy, cz);
                int getx = tp_loc.getBlockX();
                int getz = tp_loc.getBlockZ();
                switch (d) {
                    case NORTH:
                        // z -ve
                        tp_loc.setX(getx + 0.5);
                        tp_loc.setZ(getz - 0.5);
                        break;
                    case EAST:
                        // x +ve
                        tp_loc.setX(getx + 1.5);
                        tp_loc.setZ(getz + 0.5);
                        break;
                    case SOUTH:
                        // z +ve
                        tp_loc.setX(getx + 0.5);
                        tp_loc.setZ(getz + 1.5);
                        break;
                    case WEST:
                        // x -ve
                        tp_loc.setX(getx - 0.5);
                        tp_loc.setZ(getz + 0.5);
                        break;
                }
                tp_loc.setPitch(p.getLocation().getPitch());
                tp_loc.setYaw(p.getLocation().getYaw());
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    p.playSound(tp_loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                    p.teleport(tp_loc);
                    plugin.getTrackerKeeper().getRenderRoomOccupants().remove(p.getUniqueId());
                    if (plugin.getTrackerKeeper().getRenderedNPCs().containsKey(p.getUniqueId()) && plugin.getPM().isPluginEnabled("Citizens")) {
                        new TARDISEntityTracker(plugin).removeNPCs(p.getUniqueId());
                    }
                }, 10L);
            } else {
                TARDISMessage.send(p, "TRANSMAT_NO_CONSOLE");
            }
        } else {
            TARDISMessage.send(p, "TRANSMAT_NO_TARDIS");
        }
    }
}
