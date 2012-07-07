package me.eccentric_nz.plugins.TARDIS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Schematic {

    private final TARDIS plugin;

    public Schematic(TARDIS plugin) {
        this.plugin = plugin;
    }
    private static String[][][] blocks;

    public static String[][][] schematic(File file) {

        // load data from csv file
        try {
            blocks = new String[8][11][11];

            BufferedReader bufRdr = new BufferedReader(new FileReader(file));
            String line;
            //read each line of text file
            for (int level = 0; level < 8; level++) {
                for (int row = 0; row < 11; row++) {
                    line = bufRdr.readLine();
                    String[] strArr = line.split(",");
                    System.arraycopy(strArr, 0, blocks[level][row], 0, 11);
                }
            }
            //System.out.println(blocks[2][5][5]);
        } catch (IOException io) {
            System.out.println("Could not read csv file");
        }
        return blocks;
    }

    public void buildInnerTARDIS(String[][][] s, Player p, Location l, Constants.COMPASS d) {
        int level, row, col, id, x, y, z, startx, starty = 15, startz, resetx, resetz, cx = 0, cy = 0, cz = 0, rid = 0, multiplier = 1, tx = 0, ty = 0, tz = 0;
        byte data = 0x0;
        short damage = 0;
        List<String> torches = new ArrayList<String>();
        String tmp, replacedBlocks = "";
        World world = l.getWorld();
        // calculate startx, starty, startz
        // getStartLocation(Location loc, Constants.COMPASS dir)
        int gsl[] = getStartLocation(l, d);
        startx = gsl[0];
        resetx = gsl[1];
        startz = gsl[2];
        resetz = gsl[3];
        x = gsl[4];
        z = gsl[5];

        for (level = 0; level < 8; level++) {
            for (row = 0; row < 11; row++) {
                for (col = 0; col < 11; col++) {
                    tmp = s[level][row][col];
                    if (!tmp.equals("-")) {
                        if (tmp.contains(":")) {
                            String[] iddata = tmp.split(":");
                            id = Integer.parseInt(iddata[0]);
                            if (iddata[1].equals("~")) {
                                // determine data bit from direction (d) and block type
                                if (id == 76 && row == 1) { // 1st redstone torch
                                    switch (d) {
                                        case NORTH:
                                            data = 0x4;
                                            break;
                                        case EAST:
                                            data = 0x1;
                                            break;
                                        case SOUTH:
                                            data = 0x3;
                                            break;
                                        case WEST:
                                            data = 0x2;
                                            break;
                                    }
                                }
                                if (id == 76 && row == 3) { // 2nd redstone torch
                                    switch (d) {
                                        case NORTH:
                                            data = 0x1;
                                            break;
                                        case EAST:
                                            data = 0x3;
                                            break;
                                        case SOUTH:
                                            data = 0x2;
                                            break;
                                        case WEST:
                                            data = 0x4;
                                            break;
                                    }
                                }
                                if (id == 93 && col == 2 && level == 1) { // repeaters facing towards door
                                    switch (d) {
                                        case NORTH:
                                            data = 0x4;
                                            break;
                                        case EAST:
                                            data = 0x5;
                                            break;
                                        case SOUTH:
                                            data = 0x6;
                                            break;
                                        case WEST:
                                            data = 0x7;
                                            break;
                                    }
                                }
                                if (id == 93 && col == 3 && level == 1) { // repeaters facing away from door
                                    switch (d) {
                                        case NORTH:
                                            data = 0x6;
                                            break;
                                        case EAST:
                                            data = 0x7;
                                            break;
                                        case SOUTH:
                                            data = 0x4;
                                            break;
                                        case WEST:
                                            data = 0x5;
                                            break;
                                    }
                                }
                                if (id == 54) { // chest
                                    switch (d) {
                                        case NORTH:
                                        case EAST:
                                            data = 0x3;
                                            break;
                                        case SOUTH:
                                        case WEST:
                                            data = 0x2;
                                            break;
                                    }
                                    // remember the location of this chest
                                    plugin.timelords.set(p.getName() + ".chest", world.getName() + ":" + startx + ":" + starty + ":" + startz);
                                }
                                if (id == 61) { // furnace
                                    switch (d) {
                                        case NORTH:
                                        case WEST:
                                            data = 0x2;
                                            break;
                                        case SOUTH:
                                        case EAST:
                                            data = 0x3;
                                            break;
                                    }
                                }
                                if (id == 93 && row == 3 && col == 5 && level == 5) { // redstone repeater facing towards door
                                    switch (d) {
                                        case NORTH:
                                            data = 0x2;
                                            break;
                                        case EAST:
                                            data = 0x3;
                                            break;
                                        case SOUTH:
                                            data = 0x0;
                                            break;
                                        case WEST:
                                            data = 0x1;
                                            break;
                                    }
                                }
                                if (id == 93 && row == 5 && col == 3 && level == 5) { // redstone repeater facing right from door
                                    switch (d) {
                                        case NORTH:
                                            data = 0x1;
                                            break;
                                        case EAST:
                                            data = 0x2;
                                            break;
                                        case SOUTH:
                                            data = 0x3;
                                            break;
                                        case WEST:
                                            data = 0x0;
                                            break;
                                    }
                                }
                                if (id == 93 && row == 5 && col == 7 && level == 5) { // redstone repeater facing left from door
                                    switch (d) {
                                        case NORTH:
                                            data = 0x3;
                                            break;
                                        case EAST:
                                            data = 0x0;
                                            break;
                                        case SOUTH:
                                            data = 0x1;
                                            break;
                                        case WEST:
                                            data = 0x2;
                                            break;
                                    }
                                }
                                if (id == 93 && row == 7 && col == 5 && level == 5) { // redstone repeater facing away from door
                                    switch (d) {
                                        case NORTH:
                                            data = 0x0;
                                            break;
                                        case EAST:
                                            data = 0x1;
                                            break;
                                        case SOUTH:
                                            data = 0x2;
                                            break;
                                        case WEST:
                                            data = 0x3;
                                            break;
                                    }
                                }
                                if (id == 71) { // iron door bottom
                                    switch (d) {
                                        case NORTH:
                                            data = 0x1;
                                            break;
                                        case EAST:
                                            data = 0x2;
                                            break;
                                        case SOUTH:
                                            data = 0x3;
                                            break;
                                        case WEST:
                                            data = 0x0;
                                            break;
                                    }
                                }
                                if (id == 50) { // torch set to air and remember location
                                    id = 0;
                                    data = 0x0;
                                    torches.add(startx + ":" + starty + ":" + startz);
                                    System.out.println(torches);
                                }
                            } else {
                                data = Byte.parseByte(iddata[1]);
                            }
                        } else {
                            id = Integer.parseInt(tmp);
                            data = 0x0;
                        }
                        if (plugin.config.getBoolean("bonus_chest") == Boolean.valueOf("true")) {
                            // get block at location
                            Location replaceLoc = new Location(world, startx, starty, startz);
                            int replacedMaterialId = replaceLoc.getBlock().getTypeId();
                            replacedBlocks += replacedMaterialId + ":";
                        }
                        //setBlock(World w, int x, int y, int z, int m, byte d)
                        Constants.setBlock(world, startx, starty, startz, id, data);
                    }
                    switch (d) {
                        case NORTH:
                        case SOUTH:
                            startx += x;
                            break;
                        case EAST:
                        case WEST:
                            startz += z;
                            break;
                    }
                }
                switch (d) {
                    case NORTH:
                    case SOUTH:
                        startx = resetx;
                        startz += z;
                        break;
                    case EAST:
                    case WEST:
                        startz = resetz;
                        startx += x;
                        break;
                }
            }
            switch (d) {
                case NORTH:
                case SOUTH:
                    startz = resetz;
                    break;
                case EAST:
                case WEST:
                    startx = resetx;
                    break;
            }
            starty += 1;
        }
        // reinstate torches
        for (String t_locs : torches) {
            String[] t_data = t_locs.split(":");
            try {
                tx = Integer.parseInt(t_data[0]);
                ty = Integer.parseInt(t_data[1]);
                tz = Integer.parseInt(t_data[2]);
            } catch (NumberFormatException n) {
                System.err.println("Could not convert to number");
            }
            Constants.setBlock(world, tx, ty, tz, 50, (byte) 5);
        }
        if (plugin.config.getBoolean("bonus_chest") == Boolean.valueOf("true")) {
            replacedBlocks = replacedBlocks.substring(0, replacedBlocks.length() - 1);
            String[] replaceddata = replacedBlocks.split(":");
            // get saved chest location
            String saved_chestloc = plugin.timelords.getString(p.getName() + ".chest");
            String[] cdata = saved_chestloc.split(":");
            World cw = plugin.getServer().getWorld(cdata[0]);
            try {
                cx = Integer.parseInt(cdata[1]);
                cy = Integer.parseInt(cdata[2]);
                cz = Integer.parseInt(cdata[3]);
            } catch (NumberFormatException n) {
                System.err.println("Could not convert to number");
            }
            Location chest_loc = new Location(cw, cx, cy, cz);
            Block bonus_chest = chest_loc.getBlock();
            Chest chest = (Chest) bonus_chest.getState();
            Inventory chestInv = chest.getInventory();
            for (String i : replaceddata) {
                try {
                    rid = Integer.parseInt(i);
                } catch (NumberFormatException n) {
                    System.err.println("Could not convert to number");
                }
                switch (rid) {
                    case 16: // coal
                        rid = 263;
                        break;
                    case 21: // lapis
                        rid = 351;
                        multiplier = 4;
                        damage = 4;
                        break;
                    case 56: // diamond
                        rid = 264;
                        break;
                    case 73: // redstone
                        rid = 331;
                        multiplier = 4;
                        break;
                }
                chestInv.addItem(new ItemStack(rid, multiplier, damage));
                multiplier = 1; // reset multiplier
                damage = 0; // reset damage
            }
        }
    }

    public void destroyTARDIS(Player p, Location l, Constants.COMPASS d) {
        int cx = 0, cy = 0, cz = 0;
        // remove chest contents first
        String saved_chestloc = plugin.timelords.getString(p.getName() + ".chest");
        String[] cdata = saved_chestloc.split(":");
        World cw = plugin.getServer().getWorld(cdata[0]);
        try {
            cx = Integer.parseInt(cdata[1]);
            cy = Integer.parseInt(cdata[2]);
            cz = Integer.parseInt(cdata[3]);
        } catch (NumberFormatException n) {
            System.err.println("Could not convert to number");
        }
        Location chest_loc = new Location(cw, cx, cy, cz);
        Block bonus_chest = chest_loc.getBlock();
        Chest chest = (Chest) bonus_chest.getState();
        Inventory chestInv = chest.getInventory();
        chestInv.clear();
        // should probably clear furnace too
        // inner TARDIS
        int level, row, col, x, y, z, startx, starty = 15, startz, resetx, resetz;
        World world = l.getWorld();
        // calculate startx, starty, startz
        int gsl[] = getStartLocation(l, d);
        startx = gsl[0];
        resetx = gsl[1];
        startz = gsl[2];
        resetz = gsl[3];
        x = gsl[4];
        z = gsl[5];
        for (level = 0; level < 8; level++) {
            for (row = 0; row < 11; row++) {
                for (col = 0; col < 11; col++) {
                    // set the block to stone
                    Block b = world.getBlockAt(startx, starty, startz);
                    Material m = b.getType();
                    if (m != Material.CHEST && m != Material.FURNACE) {
                        Constants.setBlock(world, startx, starty, startz, 1, (byte) 0);
                    }
                    switch (d) {
                        case NORTH:
                        case SOUTH:
                            startx += x;
                            break;
                        case EAST:
                        case WEST:
                            startz += z;
                            break;
                    }
                }
                switch (d) {
                    case NORTH:
                    case SOUTH:
                        startx = resetx;
                        startz += z;
                        break;
                    case EAST:
                    case WEST:
                        startz = resetz;
                        startx += x;
                        break;
                }
            }
            switch (d) {
                case NORTH:
                case SOUTH:
                    startz = resetz;
                    break;
                case EAST:
                case WEST:
                    startx = resetx;
                    break;
            }
            starty += 1;
        }
        // remove bluebox
        int sbx = l.getBlockX() - 1;
        int rbx = sbx;
        int sby = l.getBlockY() - 2;
        int sbz = l.getBlockZ() - 1;
        int rbz = sbz;
        for (int yy = 0; yy < 4; yy++) {
            for (int xx = 0; xx < 3; xx++) {
                for (int zz = 0; zz < 3; zz++) {
                    Constants.setBlock(world, sbx, sby, sbz, 0, (byte) 0);
                    sbx++;
                }
                sbx = rbx;
                sbz++;
            }
            sbz = rbz;
            sby++;
        }
        // remove player from timelords
        String configPath = p.getName();
        plugin.timelords.set(configPath, null);
        try {
            plugin.timelords.save(plugin.timelordsfile);
        } catch (IOException io) {
            System.err.println(Constants.MY_PLUGIN_NAME + " Could not save timelords file!");
        }
    }
    private static int[] startLoc = new int[6];

    public int[] getStartLocation(Location loc, Constants.COMPASS dir) {
        switch (dir) {
            case NORTH:
                startLoc[0] = loc.getBlockX() - 5;
                startLoc[1] = startLoc[0];
                startLoc[2] = loc.getBlockZ() - 1;
                startLoc[3] = startLoc[2];
                startLoc[4] = 1;
                startLoc[5] = 1;
                break;
            case EAST:
                startLoc[0] = loc.getBlockX() + 1;
                startLoc[1] = startLoc[0];
                startLoc[2] = loc.getBlockZ() - 5;
                startLoc[3] = startLoc[2];
                startLoc[4] = -1;
                startLoc[5] = 1;
                break;
            case SOUTH:
                startLoc[0] = loc.getBlockX() + 5;
                startLoc[1] = startLoc[0];
                startLoc[2] = loc.getBlockZ() + 1;
                startLoc[3] = startLoc[2];
                startLoc[4] = -1;
                startLoc[5] = -1;
                break;
            case WEST:
                startLoc[0] = loc.getBlockX() - 1;
                startLoc[1] = startLoc[0];
                startLoc[2] = loc.getBlockZ() + 5;
                startLoc[3] = startLoc[2];
                startLoc[4] = 1;
                startLoc[5] = -1;
                break;
        }
        return startLoc;
    }
}