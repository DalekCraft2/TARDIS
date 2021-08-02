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
package me.eccentric_nz.TARDIS.chemistry.product;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.custommodeldata.GUIChemistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ProductInventory {

    private final ItemStack[] menu;
    private final TARDIS plugin;

    public ProductInventory(TARDIS plugin) {
        this.plugin = plugin;
        menu = getItemStack();
    }

    private ItemStack[] getItemStack() {
        ItemStack[] stack = new ItemStack[27];
        // info
        ItemStack info = new ItemStack(Material.BOWL, 1);
        ItemMeta info_im = info.getItemMeta();
        info_im.setDisplayName(ChatColor.RESET + "Info");
        info_im.setLore(Arrays.asList(ChatColor.GRAY + "Combine elements and compounds", ChatColor.GRAY + "to create sparklers, balloons,", ChatColor.GRAY + "lamps, and glow sticks.", ChatColor.GRAY + "To see a product formula", ChatColor.GRAY + "use the " + ChatColor.GREEN + "/tardischemistry formula" + ChatColor.GRAY + " command.", ChatColor.GRAY + "Place items like you would", ChatColor.GRAY + "in a crafting table", ChatColor.GRAY + "in the 9 left slots."));
        info_im.setCustomModelData(GUIChemistry.INFO.getCustomModelData());
        info.setItemMeta(info_im);
        stack[8] = info;
        // craft recipe
        ItemStack craft = new ItemStack(Material.BOWL, 1);
        ItemMeta craft_im = craft.getItemMeta();
        craft_im.setDisplayName(ChatColor.RESET + "Craft");
        craft_im.setCustomModelData(GUIChemistry.CRAFT.getCustomModelData());
        craft.setItemMeta(craft_im);
        stack[17] = craft;
        // close
        ItemStack close = new ItemStack(Material.BOWL, 1);
        ItemMeta close_im = close.getItemMeta();
        close_im.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_CLOSE"));
        close_im.setCustomModelData(GUIChemistry.CLOSE.getCustomModelData());
        close.setItemMeta(close_im);
        stack[26] = close;
        return stack;
    }

    public ItemStack[] getMenu() {
        return menu;
    }
}
