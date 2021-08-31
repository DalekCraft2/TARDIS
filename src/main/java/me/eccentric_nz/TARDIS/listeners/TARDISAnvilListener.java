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
package me.eccentric_nz.TARDIS.listeners;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.messaging.TARDISMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

/**
 * @author eccentric_nz
 */
public class TARDISAnvilListener implements Listener {

    private final HashMap<String, Material> disallow = new HashMap<>();

    public TARDISAnvilListener(TARDIS plugin) {
        plugin.getRecipesConfig().getConfigurationSection("shaped").getKeys(false).forEach((r) -> {
            String[] result = plugin.getRecipesConfig().getString("shaped." + r + ".result").split(":");
            disallow.put(r, Material.valueOf(result[0]));
        });
        plugin.getRecipesConfig().getConfigurationSection("shapeless").getKeys(false).forEach((q) -> {
            String[] result = plugin.getRecipesConfig().getString("shapeless." + q + ".result").split(":");
            disallow.put(q, Material.valueOf(result[0]));
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(InventoryClickEvent event) {
        Inventory inv = event.getView().getTopInventory();
        if (inv instanceof AnvilInventory) {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getRawSlot();
            // slot 2 = result item slot
            if (slot == 2) {
                ItemStack currentItem = event.getCurrentItem();
                if (currentItem != null && currentItem.hasItemMeta()) {
                    ItemMeta itemMeta = currentItem.getItemMeta();
                    ItemStack itemStack1 = inv.getItem(0);
                    ItemStack itemStack2 = inv.getItem(1);
                    if (checkRepair(itemStack1, itemStack2) && itemMeta.hasDisplayName() && disallow.containsKey(itemMeta.getDisplayName()) && currentItem.getType() == disallow.get(itemMeta.getDisplayName())) {
                        TARDISMessage.send(player, "NO_RENAME");
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean checkRepair(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack2 == null) {
            return true;
        }
        if (!itemStack1.hasItemMeta() || !itemStack2.hasItemMeta()) {
            return true;
        }
        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        if (!itemMeta1.hasDisplayName() || !itemMeta2.hasDisplayName()) {
            return true;
        }
        String displayName1 = itemMeta1.getDisplayName();
        String displayName2 = itemMeta2.getDisplayName();
        return !displayName1.equals(displayName2);
    }
}
