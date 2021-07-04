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
package me.eccentric_nz.TARDIS.schematic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.TARDISConstants;
import me.eccentric_nz.TARDIS.rooms.TARDISPainting;
import me.eccentric_nz.TARDIS.utility.TARDISBannerData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.eccentric_nz.TARDIS.schematic.TARDISBannerSetter.setBanners;

/**
 * @author eccentric_nz
 */
class TARDISSchematicPaster implements Runnable {

    private final TARDIS plugin;
    private final Player player;
    private final HashMap<Block, BlockData> postRedstoneTorches = new HashMap<>();
    private final HashMap<Block, TARDISBannerData> postBanners = new HashMap<>();
    private int task, length, relative, height, width, d, x, y, z;
    private int counter = 0;
    private double div = 1.0d;
    private World world;
    private JsonObject object;
    private JsonArray array;
    private boolean running = false;
    private BossBar bossBar;

    TARDISSchematicPaster(TARDIS plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        length = 0;
        relative = 0;
    }

    @Override
    public void run() {
        // initialise
        if (!running) {
            UUID uuid = player.getUniqueId();
            if (!plugin.getTrackerKeeper().getPastes().containsKey(uuid)) {
                player.sendMessage(plugin.getPluginName() + "No schematic loaded! " + ChatColor.GREEN + "/ts load [name]");
                plugin.getServer().getScheduler().cancelTask(task);
                task = -1;
                return;
            }
            object = plugin.getTrackerKeeper().getPastes().get(uuid);
            // get dimensions
            JsonObject dimensions = object.get("dimensions").getAsJsonObject();
            height = dimensions.get("height").getAsInt() - 1;
            width = dimensions.get("width").getAsInt();
            d = dimensions.get("length").getAsInt() - 1;
            div = (height + 1.0d) * width * (d + 1.0d);
            // get start location
            JsonObject relative = object.get("relative").getAsJsonObject();
            int relativeX = relative.get("x").getAsInt();
            int relativeY = relative.get("y").getAsInt();
            int relativeZ = relative.get("z").getAsInt();
            x = player.getLocation().getBlockX() - relativeX;
            y = player.getLocation().getBlockY() - relativeY;
            z = player.getLocation().getBlockZ() - relativeZ;
            world = player.getWorld();
            // get input array
            array = object.get("input").getAsJsonArray();
            bossBar = Bukkit.createBossBar("TARDIS Schematic Paste Progress", BarColor.WHITE, BarStyle.SOLID, TARDISConstants.EMPTY_ARRAY);
            bossBar.setProgress(0);
            bossBar.addPlayer(player);
            bossBar.setVisible(true);
            running = true;
        }
        if (length == height && relative == width - 1) {
            for (Map.Entry<Block, BlockData> map : postRedstoneTorches.entrySet()) {
                map.getKey().setBlockData(map.getValue());
                if (TARDIS.plugin.getBlockLogger().isLogging()) {
                    TARDIS.plugin.getBlockLogger().logPlacement(map.getKey());
                }
            }
            setBanners(postBanners);
            // paintings
            if (object.has("paintings")) {
                JsonArray paintings = (JsonArray) object.get("paintings");
                for (int i = 0; i < paintings.size(); i++) {
                    JsonObject painting = paintings.get(i).getAsJsonObject();
                    JsonObject relativeLocation = painting.get("rel_location").getAsJsonObject();
                    int paintingX = relativeLocation.get("x").getAsInt();
                    int paintingY = relativeLocation.get("y").getAsInt();
                    int paintingZ = relativeLocation.get("z").getAsInt();
                    Art art = Art.valueOf(painting.get("art").getAsString());
                    BlockFace facing = BlockFace.valueOf(painting.get("facing").getAsString());
                    Location paintingLocation = TARDISPainting.calculatePosition(art, facing, new Location(world, x + paintingX, y + paintingY, z + paintingZ));
                    try {
                        Painting paintingEntity = (Painting) world.spawnEntity(paintingLocation, EntityType.PAINTING);
                        paintingEntity.teleport(paintingLocation);
                        paintingEntity.setFacingDirection(facing, true);
                        paintingEntity.setArt(art, true);
                    } catch (IllegalArgumentException illegalArgumentException) {
                        plugin.debug("Invalid painting location!");
                    }
                }
            }
            plugin.getServer().getScheduler().cancelTask(task);
            task = -1;
            bossBar.setProgress(1);
            bossBar.setVisible(false);
            bossBar.removeAll();
        }
        // paste a column
        JsonArray level = (JsonArray) array.get(length);
        JsonArray row = (JsonArray) level.get(relative);
        for (int c = 0; c <= d; c++) {
            counter++;
            JsonObject column = row.get(c).getAsJsonObject();
            BlockData blockData = plugin.getServer().createBlockData(column.get("data").getAsString());
            Block block = world.getBlockAt(x + relative, y + length, z + c);
            if (!block.getType().isAir() && plugin.getBlockLogger().isLogging()) {
                plugin.getBlockLogger().logRemoval(block);
            }
            switch (blockData.getMaterial()) {
                case REDSTONE_TORCH -> postRedstoneTorches.put(block, blockData);
                case BLACK_BANNER, BLACK_WALL_BANNER, BLUE_BANNER, BLUE_WALL_BANNER, BROWN_BANNER, BROWN_WALL_BANNER, CYAN_BANNER, CYAN_WALL_BANNER, GRAY_BANNER, GRAY_WALL_BANNER, GREEN_BANNER, GREEN_WALL_BANNER, LIGHT_BLUE_BANNER, LIGHT_BLUE_WALL_BANNER, LIGHT_GRAY_BANNER, LIGHT_GRAY_WALL_BANNER, LIME_BANNER, LIME_WALL_BANNER, MAGENTA_BANNER, MAGENTA_WALL_BANNER, ORANGE_BANNER, ORANGE_WALL_BANNER, PINK_BANNER, PINK_WALL_BANNER, PURPLE_BANNER, PURPLE_WALL_BANNER, RED_BANNER, RED_WALL_BANNER, WHITE_BANNER, WHITE_WALL_BANNER, YELLOW_BANNER, YELLOW_WALL_BANNER -> {
                    JsonObject state = column.has("banner") ? column.get("banner").getAsJsonObject() : null;
                    if (state != null) {
                        TARDISBannerData tardisBannerData = new TARDISBannerData(blockData, state);
                        postBanners.put(block, tardisBannerData);
                    }
                }
                default -> {
                    block.setBlockData(blockData, true);
                    if (plugin.getBlockLogger().isLogging()) {
                        plugin.getBlockLogger().logPlacement(block);
                    }
                }
            }
            double progress = counter / div;
            bossBar.setProgress(progress);
            if (c == d && relative < width) {
                relative++;
            }
            if (c == d && relative == width && length < height) {
                relative = 0;
                length++;
            }
        }
    }

    public void setTask(int task) {
        this.task = task;
    }
}
