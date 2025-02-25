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
package me.eccentric_nz.TARDIS.chemistry.creative;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.chemistry.lab.Lab;
import me.eccentric_nz.TARDIS.chemistry.lab.LabBuilder;
import me.eccentric_nz.TARDIS.chemistry.product.Product;
import me.eccentric_nz.TARDIS.chemistry.product.ProductBuilder;
import me.eccentric_nz.TARDIS.custommodeldata.GUIChemistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ProductsCreativeInventory {

    private final TARDIS plugin;
    private final ItemStack[] menu;

    public ProductsCreativeInventory(TARDIS plugin) {
        this.plugin = plugin;
        menu = getItemStack();
    }

    private ItemStack[] getItemStack() {
        ItemStack[] stack = new ItemStack[54];
        int i = 0;
        for (Product entry : Product.values()) {
            if (i > 52) {
                break;
            }
            ItemStack is = ProductBuilder.getProduct(entry);
            stack[i] = is;
            if (i % 9 == 7) {
                i += 2;
            } else {
                i++;
            }
        }
        for (Lab entry : Lab.values()) {
            if (i > 52) {
                break;
            }
            ItemStack is = LabBuilder.getLabProduct(entry);
            stack[i] = is;
            if (i % 9 == 7) {
                i += 2;
            } else {
                i++;
            }
        }
        // elements
        ItemStack elements = new ItemStack(GUIChemistry.ELEMENTS.getMaterial(), 1);
        ItemMeta eim = elements.getItemMeta();
        eim.setDisplayName(ChatColor.RESET + "Elements");
        eim.setCustomModelData(GUIChemistry.ELEMENTS.getCustomModelData());
        elements.setItemMeta(eim);
        stack[35] = elements;
        // compounds
        ItemStack compounds = new ItemStack(GUIChemistry.COMPOUNDS.getMaterial(), 1);
        ItemMeta cim = compounds.getItemMeta();
        cim.setDisplayName(ChatColor.RESET + "Compounds");
        cim.setCustomModelData(GUIChemistry.COMPOUNDS.getCustomModelData());
        compounds.setItemMeta(cim);
        stack[44] = compounds;
        // close
        ItemStack close = new ItemStack(Material.BOWL, 1);
        ItemMeta close_im = close.getItemMeta();
        close_im.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_CLOSE"));
        close_im.setCustomModelData(GUIChemistry.CLOSE.getCustomModelData());
        close.setItemMeta(close_im);
        stack[53] = close;
        return stack;
    }

    public ItemStack[] getMenu() {
        return menu;
    }
}
