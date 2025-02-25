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

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.enumeration.Advancement;

import java.io.*;
import java.util.logging.Level;

/**
 * @author eccentric_nz
 */
public class TARDISChecker {

    private final TARDIS plugin;

    public TARDISChecker(TARDIS plugin) {
        this.plugin = plugin;
    }

    public static boolean hasDimension(String dimension) {
        boolean exists = true;
        File container = TARDIS.plugin.getServer().getWorldContainer();
        String s_world = TARDIS.plugin.getServer().getWorlds().get(0).getName();
        String dataPacksRoot = container.getAbsolutePath() + File.separator + s_world + File.separator + "datapacks" + File.separator;
        // check if directories exist
        String dimensionRoot = dataPacksRoot + dimension + File.separator + "data" + File.separator + "tardis" + File.separator;
        File dimensionDir = new File(dimensionRoot + "dimension");
        File dimensionTypeDir = new File(dimensionRoot + "dimension_type");
        File worldGenDir = new File(dimensionRoot + "worldgen");
        File biomeDir = new File(dimensionRoot + "worldgen" + File.separator + "biome");
        File featureDir = new File(dimensionRoot + "worldgen" + File.separator + "configured_feature");
        if (!dimensionDir.exists()) {
            dimensionDir.mkdirs();
        }
        if (!dimensionTypeDir.exists()) {
            dimensionTypeDir.mkdirs();
        }
        if (!worldGenDir.exists()) {
            worldGenDir.mkdirs();
        }
        if (!biomeDir.exists()) {
            biomeDir.mkdirs();
        }
        if (!featureDir.exists()) {
            featureDir.mkdirs();
        }
        // copy files to directory
        File dimFile = new File(dimensionDir, dimension + ".json");
        if (!dimFile.exists()) {
            exists = false;
            copy("datapacks/" + dimension + "/data/tardis/dimension/" + dimension + ".json", dimFile);
        }
        File dimTypeFile = new File(dimensionTypeDir, dimension + ".json");
        if (!dimTypeFile.exists()) {
            exists = false;
            copy("datapacks/" + dimension + "/data/tardis/dimension_type/" + dimension + ".json", dimTypeFile);
        }
        switch (dimension) {
            case "skaro":
                File tree = new File(featureDir, "skaro_tree.json");
                File desert = new File(biomeDir, "skaro_desert.json");
                File hills = new File(biomeDir, "skaro_hills.json");
                File lakes = new File(biomeDir, "skaro_lakes.json");
                if (!tree.exists()) {
                    exists = false;
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/configured_feature/skaro_tree.json", tree);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/skaro_desert.json", desert);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/skaro_hills.json", hills);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/skaro_lakes.json", lakes);
                }
                break;
            case "gallifrey":
                File plant = new File(featureDir, "gallifrey_tree.json");
                File grass = new File(featureDir, "gallifrey_grass.json");
                File badlands = new File(biomeDir, "gallifrey_badlands.json");
                File plateau = new File(biomeDir, "gallifrey_plateau.json");
                File eroded = new File(biomeDir, "gallifrey_eroded.json");
                if (!plant.exists()) {
                    exists = false;
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/configured_feature/gallifrey_tree.json", plant);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/configured_feature/gallifrey_grass.json", grass);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/gallifrey_badlands.json", badlands);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/gallifrey_plateau.json", plateau);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/gallifrey_eroded.json", eroded);
                }
                break;
            default:
                // nothing to do
                break;
        }
        String dataPacksMeta = dataPacksRoot + dimension;
        File mcmeta = new File(dataPacksMeta, "pack.mcmeta");
        if (!mcmeta.exists()) {
            exists = false;
            copy("datapacks/" + dimension + "/pack.mcmeta", mcmeta);
        }
        return exists;
    }

    public static void updateDimension(String dimension) {
        File container = TARDIS.plugin.getServer().getWorldContainer();
        String s_world = TARDIS.plugin.getServer().getWorlds().get(0).getName();
        String dataPacksRoot = container.getAbsolutePath() + File.separator + s_world + File.separator + "datapacks" + File.separator;
        // check if directories exist
        String dimensionRoot = dataPacksRoot + dimension + File.separator + "data" + File.separator + "tardis" + File.separator;
        File dimensionDir = new File(dimensionRoot + "dimension");
        if (dimensionDir.exists()) {
            File dimensionTypeDir = new File(dimensionRoot + "dimension_type");
            File biomeDir = new File(dimensionRoot + "worldgen" + File.separator + "biome");
            File featureDir = new File(dimensionRoot + "worldgen" + File.separator + "configured_feature");
            // overwrite files
            File dimFile = new File(dimensionDir, dimension + ".json");
            copy("datapacks/" + dimension + "/data/tardis/dimension/" + dimension + ".json", dimFile);
            File dimTypeFile = new File(dimensionTypeDir, dimension + ".json");
            copy("datapacks/" + dimension + "/data/tardis/dimension_type/" + dimension + ".json", dimTypeFile);
            File metaFile = new File(dataPacksRoot + dimension, "pack.mcmeta");
            copy("datapacks/" + dimension + "/pack.mcmeta", metaFile);
            switch (dimension) {
                case "skaro":
                    File tree = new File(featureDir, "skaro_tree.json");
                    File desert = new File(biomeDir, "skaro_desert.json");
                    File hills = new File(biomeDir, "skaro_hills.json");
                    File lakes = new File(biomeDir, "skaro_lakes.json");
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/configured_feature/skaro_tree.json", tree);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/skaro_desert.json", desert);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/skaro_hills.json", hills);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/skaro_lakes.json", lakes);
                    break;
                case "gallifrey":
                    File plant = new File(featureDir, "gallifrey_tree.json");
                    File grass = new File(featureDir, "gallifrey_grass.json");
                    File badlands = new File(biomeDir, "gallifrey_badlands.json");
                    File plateau = new File(biomeDir, "gallifrey_plateau.json");
                    File eroded = new File(biomeDir, "gallifrey_eroded.json");
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/configured_feature/gallifrey_tree.json", plant);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/configured_feature/gallifrey_grass.json", grass);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/gallifrey_badlands.json", badlands);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/gallifrey_plateau.json", plateau);
                    copy("datapacks/" + dimension + "/data/tardis/worldgen/biome/gallifrey_eroded.json", eroded);
                    break;
                default:
                    // nothing to do
                    break;
            }
        }
    }

    public static void copy(String filename, File file) {
        InputStream in = null;
        try {
            in = TARDIS.plugin.getResource(filename);
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (IOException io) {
                TARDIS.plugin.getLogger().log(java.util.logging.Level.SEVERE, "Checker: Could not save the file (" + file + ").");
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    TARDIS.plugin.getLogger().log(java.util.logging.Level.SEVERE, "Checker: Could not close the output stream.");
                }
            }
        } catch (FileNotFoundException e) {
            TARDIS.plugin.getLogger().log(java.util.logging.Level.SEVERE, "Checker: File not found: " + filename);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    TARDIS.plugin.getLogger().log(java.util.logging.Level.SEVERE, "Checker: Could not close the input stream.");
                }
            }
        }
    }

    public void checkAdvancements() {
        // get server's main world folder
        // is there a worlds container?
        File container = plugin.getServer().getWorldContainer();
        String s_world = plugin.getServer().getWorlds().get(0).getName();
        // check if directories exist
        String dataPacksRoot = container.getAbsolutePath() + File.separator + s_world + File.separator + "datapacks" + File.separator + "tardis" + File.separator + "data" + File.separator + "tardis" + File.separator + "advancements";
        File tardisDir = new File(dataPacksRoot);
        if (!tardisDir.exists()) {
            plugin.getLogger().log(Level.INFO, plugin.getLanguage().getString("ADVANCEMENT_DIRECTORIES"));
            tardisDir.mkdirs();
        }
        for (Advancement advancement : Advancement.values()) {
            String json = advancement.getConfigName() + ".json";
            File jfile = new File(dataPacksRoot, json);
            if (!jfile.exists()) {
                plugin.getLogger().log(Level.SEVERE, String.format(plugin.getLanguage().getString("ADVANCEMENT_NOT_FOUND"), json));
                plugin.getLogger().log(Level.INFO, String.format(plugin.getLanguage().getString("ADVANCEMENT_COPYING"), json));
                copy(json, jfile);
            }
        }
        String dataPacksMeta = container.getAbsolutePath() + File.separator + s_world + File.separator + "datapacks" + File.separator + "tardis";
        File mcmeta = new File(dataPacksMeta, "pack.mcmeta");
        if (!mcmeta.exists()) {
            plugin.getLogger().log(Level.SEVERE, String.format(plugin.getLanguage().getString("ADVANCEMENT_NOT_FOUND"), "pack.mcmeta"));
            plugin.getLogger().log(Level.INFO, String.format(plugin.getLanguage().getString("ADVANCEMENT_COPYING"), "pack.mcmeta"));
            copy("pack.mcmeta", mcmeta);
        }
    }
}
