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
package me.eccentric_nz.TARDIS.travel;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.TARDISConstants;
import me.eccentric_nz.TARDIS.builders.TARDISInteriorPostioning;
import me.eccentric_nz.TARDIS.custommodeldata.GUISaves;
import me.eccentric_nz.TARDIS.database.resultset.ResultSetDestinations;
import me.eccentric_nz.TARDIS.database.resultset.ResultSetHomeLocation;
import me.eccentric_nz.TARDIS.database.resultset.ResultSetTardisID;
import me.eccentric_nz.TARDIS.enumeration.WorldManager;
import me.eccentric_nz.TARDIS.planets.TARDISAliasResolver;
import me.eccentric_nz.TARDIS.utility.TARDISNumberParsers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * The "Hollywood" sign was among the Earth cultural items the Threshold stole and moved to the town of Wormwood on the
 * Moon. The moon was later destroyed; the sign likely was also.
 *
 * @author eccentric_nz
 */
public class TARDISSaveSignInventory {

    private final TARDIS plugin;
    private final ItemStack[] terminal;
    private final List<Integer> slots = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90));
    private final int id;
    private final Player player;

    public TARDISSaveSignInventory(TARDIS plugin, int id, Player player) {
        this.plugin = plugin;
        this.id = id;
        this.player = player;
        terminal = getItemStack();
    }

    /**
     * Constructs an inventory for the Save Sign GUI.
     *
     * @return an Array of itemStacks (an inventory)
     */
    private ItemStack[] getItemStack() {
        HashMap<Integer, ItemStack> dests = new HashMap<>();
        // home stack
        ItemStack his = new ItemStack(TARDISConstants.GUI_IDS.get(0), 1);
        ItemMeta him = his.getItemMeta();
        List<String> hlore = new ArrayList<>();
        HashMap<String, Object> wherehl = new HashMap<>();
        wherehl.put("tardis_id", id);
        ResultSetHomeLocation rsh = new ResultSetHomeLocation(plugin, wherehl);
        if (rsh.resultSet()) {
            him.setDisplayName(ChatColor.RESET + "Home");
            hlore.add(ChatColor.GRAY + rsh.getWorld().getName());
            hlore.add(ChatColor.GRAY + "" + rsh.getX());
            hlore.add(ChatColor.GRAY + "" + rsh.getY());
            hlore.add(ChatColor.GRAY + "" + rsh.getZ());
            hlore.add(ChatColor.GRAY + rsh.getDirection().toString());
            hlore.add(ChatColor.GRAY + ((rsh.isSubmarine()) ? "true" : "false"));
            if (!rsh.getPreset().isEmpty()) {
                hlore.add(ChatColor.GRAY + rsh.getPreset());
            }
        } else {
            hlore.add(ChatColor.GRAY + "Not found!");
        }
        him.setLore(hlore);
        his.setItemMeta(him);
        ItemStack[] stack = new ItemStack[54];
        stack[0] = his;
        // saved destinations
        HashMap<String, Object> did = new HashMap<>();
        did.put("tardis_id", id);
        ResultSetDestinations rsd = new ResultSetDestinations(plugin, did, true);
        boolean hasSecondPage = false;
        int i = 1;
        int highest = -1;
        ArrayList<HashMap<String, String>> data;
        if (rsd.resultSet()) {
            data = rsd.getData();
            // cycle through saves
            for (HashMap<String, String> map : data) {
                if (map.get("type").equals("0")) {
                    int slot;
                    if (!map.get("slot").equals("-1")) {
                        slot = TARDISNumberParsers.parseInt(map.get("slot"));
                        if (slot > highest) {
                            highest = slot;
                        }
                    } else {
                        slot = slots.get(0);
                    }
                    slots.remove(Integer.valueOf(slot));
                    if (slot < 45) {
                        Material material;
                        if (map.get("icon").isEmpty()) {
                            material = TARDISConstants.GUI_IDS.get(i);
                        } else {
                            try {
                                material = Material.valueOf(map.get("icon"));
                            } catch (IllegalArgumentException e) {
                                material = TARDISConstants.GUI_IDS.get(i);
                            }
                        }
                        ItemStack is = new ItemStack(material, 1);
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName(ChatColor.RESET + map.get("dest_name"));
                        List<String> lore = new ArrayList<>();
                        String world = (plugin.getWorldManager().equals(WorldManager.MULTIVERSE)) ? plugin.getMVHelper().getAlias(map.get("world")) : TARDISAliasResolver.getWorldAlias(map.get("world"));
                        lore.add(world);
                        lore.add(map.get("x"));
                        lore.add(map.get("y"));
                        lore.add(map.get("z"));
                        lore.add(map.get("direction"));
                        lore.add((map.get("submarine").equals("1")) ? "true" : "false");
                        if (!map.get("preset").isEmpty()) {
                            lore.add(map.get("preset"));
                        }
                        im.setLore(lore);
                        is.setItemMeta(im);
                        dests.put(slot, is);
                        i++;
                    }
                }
            }
            hasSecondPage = data.size() > 44 || highest > 44;
            for (int s = 1; s < 45; s++) {
                stack[s] = dests.getOrDefault(s, null);
            }
        }
        // add button to allow rearranging saves
        ItemStack tool = new ItemStack(Material.ARROW, 1);
        ItemMeta rearrange = tool.getItemMeta();
        rearrange.setDisplayName(ChatColor.RESET + "Rearrange saves");
        rearrange.setCustomModelData(GUISaves.REARRANGE_SAVES.getCustomModelData());
        tool.setItemMeta(rearrange);
        // add button to allow deleting saves
        ItemStack bucket = new ItemStack(Material.BUCKET, 1);
        ItemMeta delete = bucket.getItemMeta();
        delete.setDisplayName(ChatColor.RESET + "Delete save");
        delete.setCustomModelData(GUISaves.DELETE_SAVE.getCustomModelData());
        bucket.setItemMeta(delete);
        ItemStack next = null;
        if (hasSecondPage) {
            // add button to go to next page
            next = new ItemStack(GUISaves.GO_TO_PAGE_2.getMaterial(), 1);
            ItemMeta page = next.getItemMeta();
            page.setDisplayName(ChatColor.RESET + GUISaves.GO_TO_PAGE_2.getName());
            page.setCustomModelData(GUISaves.GO_TO_PAGE_2.getCustomModelData());
            next.setItemMeta(page);
        }
        ItemStack own = null;
        // is it this player's TARDIS?
        ResultSetTardisID rstid = new ResultSetTardisID(plugin);
        if (rstid.fromUUID(player.getUniqueId().toString())) {
            // add button to view own saves (if in another player's TARDIS)
            if (rstid.getTardis_id() != id) {
                own = new ItemStack(GUISaves.LOAD_MY_SAVES.getMaterial(), 1);
                ItemMeta saves = own.getItemMeta();
                saves.setDisplayName(ChatColor.RESET + GUISaves.LOAD_MY_SAVES.getName());
                saves.setCustomModelData(GUISaves.LOAD_MY_SAVES.getCustomModelData());
                own.setItemMeta(saves);
            } else {
                // get TARDIS id of TARDIS player is in as they may have switched using the 'load my saves' button
                int tid = TARDISInteriorPostioning.getTARDISIdFromLocation(player.getLocation());
                if (tid != id) {
                    own = new ItemStack(GUISaves.LOAD_SAVES_FROM_THIS_TARDIS.getMaterial(), 1);
                    ItemMeta saves = own.getItemMeta();
                    saves.setDisplayName(ChatColor.RESET + GUISaves.LOAD_SAVES_FROM_THIS_TARDIS.getName());
                    saves.setCustomModelData(GUISaves.LOAD_SAVES_FROM_THIS_TARDIS.getCustomModelData());
                    own.setItemMeta(saves);
                }
            }
        }
        // add button to load TARDIS areas
        ItemStack map = new ItemStack(Material.MAP, 1);
        ItemMeta switchto = map.getItemMeta();
        switchto.setDisplayName(ChatColor.RESET + "Load TARDIS areas");
        switchto.setCustomModelData(GUISaves.LOAD_TARDIS_AREAS.getCustomModelData());
        map.setItemMeta(switchto);
        for (int m = 45; m < 54; m++) {
            switch (m) {
                case 45 -> stack[m] = tool;
                case 47 -> stack[m] = bucket;
                case 49 -> stack[m] = own;
                case 51 -> stack[m] = next;
                case 53 -> stack[m] = map;
                default -> stack[m] = null;
            }
        }
        return stack;
    }

    public ItemStack[] getTerminal() {
        return terminal;
    }
}
