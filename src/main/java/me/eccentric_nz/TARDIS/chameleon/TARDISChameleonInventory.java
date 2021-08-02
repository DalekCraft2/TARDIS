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
package me.eccentric_nz.TARDIS.chameleon;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.custommodeldata.GUIChameleon;
import me.eccentric_nz.TARDIS.enumeration.Adaption;
import me.eccentric_nz.TARDIS.enumeration.PRESET;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * A TARDIS with a functioning chameleon circuit can appear as almost anything desired. The owner can program the
 * circuit to make it assume a specific shape. If no appearance is specified, the TARDIS automatically choses its own
 * shape. When a TARDIS materialises in a new location, within the first nanosecond of landing, its chameleon circuit
 * analyses the surrounding area, calculates a twelve-dimensional data map of all objects within a thousand-mile radius
 * and then determines which outer shell will best blend in with the environment. According to the Eleventh Doctor, the
 * TARDIS would perform these functions, but then disguise itself as a 1960s era police box anyway.
 *
 * @author eccentric_nz
 */
public class TARDISChameleonInventory {

    private final ItemStack[] terminal;
    private final TARDIS plugin;
    private final Adaption adapt;
    private final PRESET preset;
    private final ItemStack on;
    private final ItemStack off;

    public TARDISChameleonInventory(TARDIS plugin, Adaption adapt, PRESET preset) {
        this.plugin = plugin;
        this.adapt = adapt;
        this.preset = preset;
        on = new ItemStack(Material.LIME_WOOL, 1);
        off = new ItemStack(Material.LIGHT_GRAY_CARPET, 1);
        terminal = getItemStack();
    }

    /**
     * Constructs an inventory for the Chameleon Circuit GUI.
     *
     * @return an Array of itemStacks (an inventory)
     */
    private ItemStack[] getItemStack() {

        // Apply now
        ItemStack apply = new ItemStack(Material.COMPARATOR, 1);
        ItemMeta now = apply.getItemMeta();
        now.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("APPLY"));
        List<String> applyLore = plugin.getChameleonGuis().getStringList("APPLY_LORE");
        for (int i = 0; i < applyLore.size(); i++) {
            applyLore.set(i, ChatColor.GRAY + applyLore.get(i));
        }
        now.setLore(applyLore);
        now.setCustomModelData(GUIChameleon.BUTTON_APPLY.getCustomModelData());
        apply.setItemMeta(now);
        // Disabled
        ItemStack dis = new ItemStack(Material.BOWL, 1);
        ItemMeta abled = dis.getItemMeta();
        abled.setDisplayName(ChatColor.RESET + "Chameleon Circuit");
        List<String> disabledLore = plugin.getChameleonGuis().getStringList("DISABLED_LORE");
        for (int i = 0; i < disabledLore.size(); i++) {
            disabledLore.set(i, ChatColor.GRAY + disabledLore.get(i));
        }
        abled.setLore(disabledLore);
        abled.setCustomModelData(GUIChameleon.BUTTON_CHAMELEON.getCustomModelData());
        dis.setItemMeta(abled);
        // Adaptive
        ItemStack adap = new ItemStack(Material.BOWL, 1);
        ItemMeta tive = adap.getItemMeta();
        tive.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("ADAPT"));
        List<String> adaptiveLore = plugin.getChameleonGuis().getStringList("ADAPT_LORE");
        for (int i = 0; i < adaptiveLore.size(); i++) {
            adaptiveLore.set(i, ChatColor.GRAY + adaptiveLore.get(i));
        }
        tive.setLore(adaptiveLore);
        tive.setCustomModelData(GUIChameleon.BUTTON_ADAPT.getCustomModelData());
        adap.setItemMeta(tive);
        // Invisible
        ItemStack invis;
        if (plugin.getConfig().getBoolean("allow.invisibility")) {
            invis = new ItemStack(Material.BOWL, 1);
            ItemMeta ible = invis.getItemMeta();
            ible.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("INVISIBLE"));
            List<String> invisibleLore = plugin.getChameleonGuis().getStringList("INVISIBLE_LORE");
            for (int i = 0; i < invisibleLore.size(); i++) {
                invisibleLore.set(i, ChatColor.GRAY + invisibleLore.get(i));
            }
            if (plugin.getConfig().getBoolean("circuits.damage")) {
                invisibleLore.add(ChatColor.GRAY + plugin.getLanguage().getString("INVISIBILITY_LORE_1"));
                invisibleLore.add(ChatColor.GRAY + plugin.getLanguage().getString("INVISIBILITY_LORE_2"));
            }
            ible.setLore(invisibleLore);
            ible.setCustomModelData(GUIChameleon.BUTTON_INVISIBLE.getCustomModelData());
            invis.setItemMeta(ible);
        } else {
            invis = null;
        }
        // Shorted out
        ItemStack shor = new ItemStack(Material.BOWL, 1);
        ItemMeta tout = shor.getItemMeta();
        tout.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("SHORT"));
        List<String> shortedLore = plugin.getChameleonGuis().getStringList("SHORT_LORE");
        for (int i = 0; i < shortedLore.size(); i++) {
            shortedLore.set(i, ChatColor.GRAY + shortedLore.get(i));
        }
        tout.setLore(shortedLore);
        tout.setCustomModelData(GUIChameleon.BUTTON_SHORT.getCustomModelData());
        shor.setItemMeta(tout);
        // construction GUI
        ItemStack cons = new ItemStack(Material.BOWL, 1);
        ItemMeta truct = cons.getItemMeta();
        truct.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("CONSTRUCT"));
        List<String> constructLore = plugin.getChameleonGuis().getStringList("CONSTRUCT_LORE");
        for (int i = 0; i < constructLore.size(); i++) {
            constructLore.set(i, ChatColor.GRAY + constructLore.get(i));
        }
        truct.setLore(constructLore);
        truct.setCustomModelData(GUIChameleon.BUTTON_CONSTRUCT.getCustomModelData());
        cons.setItemMeta(truct);
        // Disabled radio button
        boolean isFactoryOff = preset.equals(PRESET.FACTORY) && adapt.equals(Adaption.OFF);
        ItemStack fac = isFactoryOff ? on.clone() : off.clone();
        ItemMeta tory = fac.getItemMeta();
        String donoff = isFactoryOff ? ChatColor.RED + plugin.getLanguage().getString("DISABLED") : ChatColor.GREEN + plugin.getLanguage().getString("SET_ON");
        tory.setDisplayName(donoff);
        fac.setItemMeta(tory);
        // Adaptive radio button
        ItemStack biome = (adapt.equals(Adaption.OFF)) ? off.clone() : on.clone();
        ItemMeta block = biome.getItemMeta();
        block.setDisplayName(adapt.getColour() + adapt.toString());
        biome.setItemMeta(block);
        // Invisible radio button
        ItemStack not;
        if (plugin.getConfig().getBoolean("allow.invisibility")) {
            not = (preset.equals(PRESET.INVISIBLE)) ? on.clone() : off.clone();
            ItemMeta blue = not.getItemMeta();
            String ionoff = (preset.equals(PRESET.INVISIBLE)) ? ChatColor.GREEN + plugin.getLanguage().getString("SET_ON") : ChatColor.RED + plugin.getLanguage().getString("SET_OFF");
            blue.setDisplayName(ionoff);
            not.setItemMeta(blue);
        } else {
            not = null;
        }
        // Shorted out radio button
        boolean isNotFactoryInvisibleOrConstruct = !preset.equals(PRESET.INVISIBLE) && !preset.equals(PRESET.FACTORY) && !preset.equals(PRESET.CONSTRUCT);
        ItemStack pre = isNotFactoryInvisibleOrConstruct ? on.clone() : off.clone();
        ItemMeta set = pre.getItemMeta();
        String sonoff = isNotFactoryInvisibleOrConstruct ? ChatColor.GREEN + preset.toString() : ChatColor.RED + plugin.getLanguage().getString("SET_OFF");
        set.setDisplayName(sonoff);
        pre.setItemMeta(set);
        // Construct radio button
        ItemStack bui = (preset.equals(PRESET.CONSTRUCT)) ? on.clone() : off.clone();
        ItemMeta lder = bui.getItemMeta();
        String conoff = (preset.equals(PRESET.CONSTRUCT)) ? ChatColor.GREEN + plugin.getLanguage().getString("SET_ON") : ChatColor.RED + plugin.getLanguage().getString("SET_OFF");
        lder.setDisplayName(conoff);
        bui.setItemMeta(lder);
        // Cancel / close
        ItemStack close = new ItemStack(Material.BOWL, 1);
        ItemMeta can = close.getItemMeta();
        can.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_CLOSE"));
        can.setCustomModelData(GUIChameleon.BUTTON_CLOSE.getCustomModelData());
        close.setItemMeta(can);

        return new ItemStack[]{apply, null, null, null, null, null, null, null, null, null, null, dis, adap, invis, shor, cons, null, null, null, null, fac, biome, not, pre, bui, null, close};
    }

    public ItemStack[] getMenu() {
        return terminal;
    }
}
