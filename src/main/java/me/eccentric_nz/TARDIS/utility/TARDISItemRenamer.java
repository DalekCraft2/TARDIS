/*
 * Copyright (C) 2012 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.TARDIS.utility;

import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author eccentric_nz
 */
public class TARDISItemRenamer {

    private final ItemStack itemStack;

    public TARDISItemRenamer(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Sets the name of the held item to the specified string. Also adds some
     * lore, which I don't actually know how to get to display.
     */
    public void setName(String name, boolean setlore) {
        ItemMeta im = this.itemStack.getItemMeta();
        im.setDisplayName(name);
        if (setlore) {
            ArrayList<String> lore = new ArrayList<String>();
            lore.add("Enter and exit your TARDIS");
            im.setLore(lore);
        }
        this.itemStack.setItemMeta(im);
    }
}