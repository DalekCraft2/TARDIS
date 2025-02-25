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

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.enumeration.COMPASS;
import me.eccentric_nz.TARDIS.messaging.TARDISMessage;
import me.eccentric_nz.TARDIS.planets.TARDISBiome;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class TARDISStaticUtils {

    private static final UUID ZERO_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    /**
     * Get the direction a player is facing.
     *
     * @param p    the player
     * @param swap whether to swap the direction E &lt;-&gt; W, S &lt;-&gt; N
     * @return the direction the player is facing
     */
    public static String getPlayersDirection(Player p, boolean swap) {
        // get player direction
        String d = p.getFacing().toString();
        if (swap) {
            d = switch (p.getFacing()) {
                case EAST -> "WEST";
                case NORTH -> "SOUTH";
                case WEST -> "EAST";
                default -> "NORTH";
            };
        }
        return d;
    }

    /**
     * Get whether the biome is ocean.
     *
     * @param b the biome to check
     * @return true if it is ocean
     */
    public static boolean isOceanBiome(TARDISBiome b) {
        return switch (b.name()) {
            case "OCEAN", "COLD_OCEAN", "DEEP_COLD_OCEAN", "DEEP_FROZEN_OCEAN", "DEEP_LUKEWARM_OCEAN", "DEEP_OCEAN", "DEEP_WARM_OCEAN", "FROZEN_OCEAN", "LUKEWARM_OCEAN", "WARM_OCEAN" -> true;
            default -> false;
        };
    }

    /**
     * Get a human-readable time from server ticks.
     *
     * @param t the time in ticks
     * @return a string representation of the time
     */
    public static String getTime(long t) {
        if (t > 0 && t <= 2000) {
            return "early morning";
        }
        if (t > 2000 && t <= 3500) {
            return "mid morning";
        }
        if (t > 3500 && t <= 5500) {
            return "late morning";
        }
        if (t > 5500 && t <= 6500) {
            return "around noon";
        }
        if (t > 6500 && t <= 8000) {
            return "afternoon";
        }
        if (t > 8000 && t <= 10000) {
            return "mid afternoon";
        }
        if (t > 10000 && t <= 12000) {
            return "late afternoon";
        }
        if (t > 12000 && t <= 14000) {
            return "twilight";
        }
        if (t > 14000 && t <= 16000) {
            return "evening";
        }
        if (t > 16000 && t <= 17500) {
            return "late evening";
        }
        if (t > 17500 && t <= 18500) {
            return "around midnight";
        }
        if (t > 18500 && t <= 20000) {
            return "the small hours";
        }
        if (t > 20000 && t <= 22000) {
            return "the wee hours";
        } else {
            return "pre-dawn";
        }
    }

    /**
     * Checks whether a door is open.
     *
     * @param door the door block
     * @return true or false
     */
    public static boolean isDoorOpen(Block door) {
        Openable openable = (Openable) door.getBlockData();
        return openable.isOpen();
    }

    /**
     * Gets the column to set the Police box sign in if CTM is on in the player's preferences.
     *
     * @param d the direction of the Police Box
     * @return the column
     */
    public static int getCol(COMPASS d) {
        return switch (d) {
            case NORTH -> 6;
            case WEST -> 4;
            case SOUTH -> 2;
            default -> 0;
        };
    }

    /**
     * Sets the Chameleon Sign text or messages the player.
     *
     * @param loc  the location string retrieved from the database
     * @param line the line number to set
     * @param text the text to write
     * @param p    the player to message (if the Chameleon control is not a sign)
     */
    public static void setSign(String loc, int line, String text, Player p) {
        if (!loc.isEmpty()) {
            // get sign block so we can update it
            Location l = TARDISStaticLocationGetters.getLocationFromDB(loc);
            if (l != null) {
                Chunk chunk = l.getChunk();
                while (!chunk.isLoaded()) {
                    chunk.load();
                }
                Block cc = l.getBlock();
                if (Tag.SIGNS.isTagged(cc.getType())) {
                    Sign sign = (Sign) cc.getState();
                    sign.setLine(line, text);
                    sign.update();
                } else {
                    TARDISMessage.send(p, "CHAM", " " + text);
                }
            }
        }
    }

    /**
     * Gets the Chameleon Sign preset text.
     *
     * @param loc the location string retrieved from the database
     * @return the last line of the sign
     */
    public static String getLastLine(String loc) {
        // get sign block so we can read it
        String str = "";
        Location l = TARDISStaticLocationGetters.getLocationFromDB(loc);
        if (l != null) {
            Block cc = l.getBlock();
            if (Tag.SIGNS.isTagged(cc.getType())) {
                Sign sign = (Sign) cc.getState();
                str = sign.getLine(3);
            }
        }
        return str;
    }

    public static boolean isInfested(Material material) {
        return switch (material) {
            case INFESTED_CHISELED_STONE_BRICKS, INFESTED_COBBLESTONE, INFESTED_CRACKED_STONE_BRICKS, INFESTED_MOSSY_STONE_BRICKS, INFESTED_STONE, INFESTED_STONE_BRICKS -> true;
            default -> false;
        };
    }

    public static boolean isBanner(Material material) {
        return switch (material) {
            case BLACK_BANNER, BLUE_BANNER, BROWN_BANNER, CYAN_BANNER, GRAY_BANNER, GREEN_BANNER, LIGHT_BLUE_BANNER, LIGHT_GRAY_BANNER, LIME_BANNER, MAGENTA_BANNER, ORANGE_BANNER, PINK_BANNER, PURPLE_BANNER, RED_BANNER, WHITE_BANNER, YELLOW_BANNER, BLACK_WALL_BANNER, BLUE_WALL_BANNER, BROWN_WALL_BANNER, CYAN_WALL_BANNER, GRAY_WALL_BANNER, GREEN_WALL_BANNER, LIGHT_BLUE_WALL_BANNER, LIGHT_GRAY_WALL_BANNER, LIME_WALL_BANNER, MAGENTA_WALL_BANNER, ORANGE_WALL_BANNER, PINK_WALL_BANNER, PURPLE_WALL_BANNER, RED_WALL_BANNER, WHITE_WALL_BANNER, YELLOW_WALL_BANNER -> true;
            default -> false;
        };
    }

    public static String getNick(UUID uuid) {
        if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null) {
            Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
            User user = essentials.getUser(uuid);
            String prefix = essentials.getSettings().getNicknamePrefix();
            return ChatColor.stripColor(user.getNick(false)).replace(prefix, "");
        }
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            OfflinePlayer offlinePlayer = getOfflinePlayer(uuid);
            return (offlinePlayer != null) ? offlinePlayer.getName() : "Unknown";
        } else {
            return player.getName();
        }
    }

    public static String getNick(Player player) {
        if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null) {
            Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
            User user = essentials.getUser(player.getUniqueId());
            String prefix = essentials.getSettings().getNicknamePrefix();
            return ChatColor.stripColor(user.getNick(false)).replace(prefix, "");
        }
        return player.getName();
    }

    public static UUID getZERO_UUID() {
        return ZERO_UUID;
    }

    public static EulerAngle angleToEulerAngle(int degrees) {
        return angleToEulerAngle(((double) degrees) / 360 * Math.PI);
    }

    private static EulerAngle angleToEulerAngle(double radians) {
        double x = Math.cos(radians);
        double z = Math.sin(radians);
        return new EulerAngle(x, 0, z);
    }

    public static boolean isMusicDisk(ItemStack is) {
        return switch (is.getType()) {
            case MUSIC_DISC_BLOCKS, MUSIC_DISC_CAT, MUSIC_DISC_CHIRP, MUSIC_DISC_MALL, MUSIC_DISC_WAIT -> true;
            default -> false;
        };
    }

    /**
     * Checks whether a chunk is available to build a TARDIS in.
     *
     * @param w   the world the chunk is in.
     * @param x   the x coordinate of the chunk.
     * @param z   the z coordinate of the chunk.
     * @param wid the width of the schematic.
     * @param len the length of the schematic.
     * @return a list of Chunks.
     */
    public static List<Chunk> getChunks(World w, int x, int z, int wid, int len) {
        List<Chunk> chunks = new ArrayList<>();
        int cw = TARDISNumberParsers.roundUp(wid, 16);
        int cl = TARDISNumberParsers.roundUp(len, 16);
        // check all the chunks that will be used by the schematic
        for (int cx = 0; cx < cw; cx++) {
            for (int cz = 0; cz < cl; cz++) {
                Chunk chunk = w.getChunkAt((x + cx), (z + cz));
                chunks.add(chunk);
            }
        }
        return chunks;
    }

    /**
     * Gets the TARDISBiome of a location
     *
     * @param location the location to get the biome of
     * @return the biome at the location
     */
    public static TARDISBiome getBiomeAt(Location location) {
        // get biome
        String biomeKey = TARDIS.plugin.getTardisHelper().getBiomeKey(location);
        // convert to TARDISBiome
        String[] split = biomeKey.split(":");
        NamespacedKey key = new NamespacedKey(TARDIS.plugin, split[1]);
        return TARDISBiome.of(key);
    }

    /**
     * Gets an offline player
     *
     * @param name the player's name to lookup
     */
    public static OfflinePlayer getOfflinePlayer(String name) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }


    /**
     * Gets an offline player
     *
     * @param uuid the player's UUID to lookup
     */
    public static OfflinePlayer getOfflinePlayer(UUID uuid) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getUniqueId().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

}
