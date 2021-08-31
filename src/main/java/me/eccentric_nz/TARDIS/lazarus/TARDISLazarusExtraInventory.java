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
package me.eccentric_nz.TARDIS.lazarus;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.custommodeldata.GUIGeneticManipulator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Genetic Manipulation Device was invented by Professor Richard Lazarus. The machine would turn anyone inside
 * decades younger, but the process contained one side effect: genes that evolution rejected and left dormant would be
 * unlocked, transforming the human into a giant skeletal scorpion-like beast that fed off the lifeforce of living
 * creatures.
 *
 * @author eccentric_nz
 */
class TARDISLazarusExtraInventory { // TODO Make a page button for this so it is more easily accessible. Also, consolidate each GUI with a separate "page 2" class into a single class each.

    private final ItemStack[] terminal;
    private final TARDIS plugin;
    private final List<Material> disguises = new ArrayList<>();

    TARDISLazarusExtraInventory(TARDIS plugin) {
        this.plugin = plugin;
        disguises.add(Material.COD_SPAWN_EGG);
        disguises.add(Material.PUFFERFISH_SPAWN_EGG);
        disguises.add(Material.SALMON_SPAWN_EGG);
        disguises.add(Material.TROPICAL_FISH_SPAWN_EGG);
        disguises.add(Material.GHAST_SPAWN_EGG);
        disguises.add(Material.PHANTOM_SPAWN_EGG);
        disguises.add(Material.VINDICATOR_SPAWN_EGG);
        disguises.add(Material.PILLAGER_SPAWN_EGG);
        disguises.add(Material.RAVAGER_SPAWN_EGG);
        disguises.add(Material.TRADER_LLAMA_SPAWN_EGG);
        disguises.add(Material.WANDERING_TRADER_SPAWN_EGG);
        disguises.add(Material.BEE_SPAWN_EGG);
        disguises.add(Material.ZOMBIE_HORSE_SPAWN_EGG);
        disguises.add(Material.SKELETON_HORSE_SPAWN_EGG);
        disguises.add(Material.PIGLIN_SPAWN_EGG);
        disguises.add(Material.PIGLIN_BRUTE_SPAWN_EGG);
        disguises.add(Material.STRIDER_SPAWN_EGG);
        disguises.add(Material.HOGLIN_SPAWN_EGG);
        disguises.add(Material.ZOGLIN_SPAWN_EGG);
        // 1.17
        disguises.add(Material.AXOLOTL_SPAWN_EGG); // add colours to TCG
        disguises.add(Material.GOAT_SPAWN_EGG);
        disguises.add(Material.GLOW_SQUID_SPAWN_EGG);
        terminal = getItemStack();
    }

    /**
     * Constructs an inventory for the Temporal Locator GUI.
     *
     * @return an Array of itemStacks (an inventory)
     */
    private ItemStack[] getItemStack() {
        ItemStack[] eggs = new ItemStack[54];
        int i = 0;
        // put herobrine
        ItemStack herobrine = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta herobrineMeta = herobrine.getItemMeta();
        herobrineMeta.setDisplayName(ChatColor.RESET + "HEROBRINE");
        herobrine.setItemMeta(herobrineMeta);
        eggs[i] = herobrine;
        i++;
        // golems
        // put iron golem
        ItemStack ironGolem = new ItemStack(Material.IRON_BLOCK, 1);
        ItemMeta ironGolemMeta = ironGolem.getItemMeta();
        ironGolemMeta.setDisplayName(ChatColor.RESET + "IRON_GOLEM");
        ironGolem.setItemMeta(ironGolemMeta);
        eggs[i] = ironGolem;
        i++;
        // put snow golem
        // TODO Snow Golem is already in the first page, correct? Remove this one (This could be done whilst consolidating multi-page GUI classes).
        ItemStack snowGolem = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta snowGolemMeta = snowGolem.getItemMeta();
        snowGolemMeta.setDisplayName(ChatColor.RESET + "SNOW_GOLEM");
        snowGolem.setItemMeta(snowGolemMeta);
        eggs[i] = snowGolem;
        i++;
        // put wither
        ItemStack wither = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
        ItemMeta witherMeta = wither.getItemMeta();
        witherMeta.setDisplayName(ChatColor.RESET + "WITHER");
        wither.setItemMeta(witherMeta);
        eggs[i] = wither;
        i++;
        // put illusioner
        ItemStack illusioner = new ItemStack(Material.BOW, 1);
        ItemMeta illusionerMeta = illusioner.getItemMeta();
        illusionerMeta.setDisplayName(ChatColor.RESET + "ILLUSIONER");
        illusioner.setItemMeta(illusionerMeta);
        eggs[i] = illusioner;
        i++;
        // fish / ghast / phantom / illagers / dead horses / traders
        for (Material material : disguises) {
            ItemStack egg = new ItemStack(material, 1);
            ItemMeta eggMeta = egg.getItemMeta();
            eggMeta.setDisplayName(ChatColor.RESET + material.toString().replace("_SPAWN_EGG", ""));
            egg.setItemMeta(eggMeta);
            eggs[i] = egg;
            i++;
        }
        // if TARDISWeepingAngels is enabled angels, cybermen and ice warriors will be available
        if (plugin.checkTWA()) {
            ItemStack weepingAngel = new ItemStack(Material.BRICK, 1);
            ItemMeta weepingAngelMeta = weepingAngel.getItemMeta();
            weepingAngelMeta.setDisplayName(ChatColor.RESET + "WEEPING_ANGEL");
            weepingAngelMeta.setCustomModelData(GUIGeneticManipulator.ANGEL.getCustomModelData());
            weepingAngel.setItemMeta(weepingAngelMeta);
            eggs[i] = weepingAngel;
            i++;
            ItemStack cyberman = new ItemStack(Material.IRON_INGOT, 1);
            ItemMeta cybermanMeta = cyberman.getItemMeta();
            cybermanMeta.setDisplayName(ChatColor.RESET + "CYBERMAN");
            cybermanMeta.setCustomModelData(GUIGeneticManipulator.CYBERMAN.getCustomModelData());
            cyberman.setItemMeta(cybermanMeta);
            eggs[i] = cyberman;
            i++;
            ItemStack dalek = new ItemStack(Material.SLIME_BALL, 1);
            ItemMeta dalekMeta = dalek.getItemMeta();
            dalekMeta.setDisplayName(ChatColor.RESET + "DALEK");
            dalekMeta.setCustomModelData(GUIGeneticManipulator.DALEK.getCustomModelData());
            dalek.setItemMeta(dalekMeta);
            eggs[i] = dalek;
            i++;
            ItemStack iceWarrior = new ItemStack(Material.SNOWBALL, 1);
            ItemMeta iceWarriorMeta = iceWarrior.getItemMeta();
            iceWarriorMeta.setDisplayName(ChatColor.RESET + "ICE_WARRIOR");
            iceWarriorMeta.setCustomModelData(GUIGeneticManipulator.ICE_WARRIOR.getCustomModelData());
            iceWarrior.setItemMeta(iceWarriorMeta);
            eggs[i] = iceWarrior;
            i++;
            ItemStack emptyChild = new ItemStack(Material.SUGAR, 1);
            ItemMeta emptyChildMeta = emptyChild.getItemMeta();
            emptyChildMeta.setDisplayName(ChatColor.RESET + "EMPTY_CHILD");
            emptyChildMeta.setCustomModelData(GUIGeneticManipulator.EMPTY_CHILD.getCustomModelData());
            emptyChild.setItemMeta(emptyChildMeta);
            eggs[i] = emptyChild;
            i++;
            ItemStack judoon = new ItemStack(Material.YELLOW_DYE, 1);
            ItemMeta judoonMeta = judoon.getItemMeta();
            judoonMeta.setDisplayName(ChatColor.RESET + "JUDOON");
            judoonMeta.setCustomModelData(GUIGeneticManipulator.JUDOON.getCustomModelData());
            judoon.setItemMeta(judoonMeta);
            eggs[i] = judoon;
            i++;
            ItemStack k9 = new ItemStack(Material.BONE, 1);
            ItemMeta k9Meta = k9.getItemMeta();
            k9Meta.setDisplayName(ChatColor.RESET + "K9");
            k9Meta.setCustomModelData(GUIGeneticManipulator.K9.getCustomModelData());
            k9.setItemMeta(k9Meta);
            eggs[i] = k9;
            i++;
            ItemStack ood = new ItemStack(Material.ROTTEN_FLESH, 1);
            ItemMeta oodMeta = ood.getItemMeta();
            oodMeta.setDisplayName(ChatColor.RESET + "OOD");
            oodMeta.setCustomModelData(GUIGeneticManipulator.OOD.getCustomModelData());
            ood.setItemMeta(oodMeta);
            eggs[i] = ood;
            i++;
            ItemStack silent = new ItemStack(Material.END_STONE, 1);
            ItemMeta silentMeta = silent.getItemMeta();
            silentMeta.setDisplayName(ChatColor.RESET + "SILENT");
            silentMeta.setCustomModelData(GUIGeneticManipulator.SILENT.getCustomModelData());
            silent.setItemMeta(silentMeta);
            eggs[i] = silent;
            i++;
            ItemStack silurian = new ItemStack(Material.FEATHER, 1);
            ItemMeta silurianMeta = silurian.getItemMeta();
            silurianMeta.setDisplayName(ChatColor.RESET + "SILURIAN");
            silurianMeta.setCustomModelData(GUIGeneticManipulator.SILURIAN.getCustomModelData());
            silurian.setItemMeta(silurianMeta);
            eggs[i] = silurian;
            i++;
            ItemStack sontaran = new ItemStack(Material.POTATO, 1);
            ItemMeta sontaranMeta = sontaran.getItemMeta();
            sontaranMeta.setDisplayName(ChatColor.RESET + "SONTARAN");
            sontaranMeta.setCustomModelData(GUIGeneticManipulator.SONTARAN.getCustomModelData());
            sontaran.setItemMeta(sontaranMeta);
            eggs[i] = sontaran;
            i++;
            ItemStack strax = new ItemStack(Material.BAKED_POTATO, 1);
            ItemMeta straxMeta = strax.getItemMeta();
            straxMeta.setDisplayName(ChatColor.RESET + "STRAX");
            straxMeta.setCustomModelData(GUIGeneticManipulator.STRAX.getCustomModelData());
            strax.setItemMeta(straxMeta);
            eggs[i] = strax;
            i++;
            ItemStack toclafane = new ItemStack(Material.GUNPOWDER, 1);
            ItemMeta toclafaneMeta = toclafane.getItemMeta();
            toclafaneMeta.setDisplayName(ChatColor.RESET + "TOCLAFANE");
            toclafaneMeta.setCustomModelData(GUIGeneticManipulator.TOCLAFANE.getCustomModelData());
            toclafane.setItemMeta(toclafaneMeta);
            eggs[i] = toclafane;
            i++;
            ItemStack vashtaNerada = new ItemStack(Material.BOOK, 1);
            ItemMeta vashtaNeradaMeta = vashtaNerada.getItemMeta();
            vashtaNeradaMeta.setDisplayName(ChatColor.RESET + "VASHTA_NERADA");
            vashtaNeradaMeta.setCustomModelData(GUIGeneticManipulator.VASHTA_NERADA.getCustomModelData());
            vashtaNerada.setItemMeta(vashtaNeradaMeta);
            eggs[i] = vashtaNerada;
            i++;
            ItemStack zygon = new ItemStack(Material.PAINTING, 1);
            ItemMeta zygonMeta = zygon.getItemMeta();
            zygonMeta.setDisplayName(ChatColor.RESET + "ZYGON");
            zygonMeta.setCustomModelData(GUIGeneticManipulator.ZYGON.getCustomModelData());
            zygon.setItemMeta(zygonMeta);
            eggs[i] = zygon;
        }
        // add options
        ItemStack master = new ItemStack(Material.COMPARATOR, 1);
        ItemMeta masterMeta = master.getItemMeta();
        masterMeta.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_MASTER"));
        masterMeta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getLanguage().getString("SET_OFF")));
        masterMeta.setCustomModelData(GUIGeneticManipulator.BUTTON_MASTER.getCustomModelData());
        master.setItemMeta(masterMeta);
        eggs[45] = master;
        ItemStack ageButton = new ItemStack(Material.HOPPER, 1);
        ItemMeta ageButtonMeta = ageButton.getItemMeta();
        ageButtonMeta.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_AGE"));
        ageButtonMeta.setLore(Collections.singletonList(ChatColor.GRAY + "ADULT"));
        ageButtonMeta.setCustomModelData(GUIGeneticManipulator.BUTTON_AGE.getCustomModelData());
        ageButton.setItemMeta(ageButtonMeta);
        eggs[47] = ageButton;
        ItemStack typeButton = new ItemStack(Material.CYAN_DYE, 1);
        ItemMeta typeButtonMeta = typeButton.getItemMeta();
        typeButtonMeta.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_TYPE"));
        typeButtonMeta.setLore(Collections.singletonList(ChatColor.GRAY + "WHITE"));
        typeButtonMeta.setCustomModelData(GUIGeneticManipulator.BUTTON_TYPE.getCustomModelData());
        typeButton.setItemMeta(typeButtonMeta);
        eggs[48] = typeButton;
        ItemStack optionsButton = new ItemStack(Material.LEAD, 1);
        ItemMeta optionsButtonMeta = optionsButton.getItemMeta();
        optionsButtonMeta.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_OPTS"));
        optionsButtonMeta.setLore(Collections.singletonList(ChatColor.GRAY + "FALSE"));
        optionsButtonMeta.setCustomModelData(GUIGeneticManipulator.BUTTON_OPTS.getCustomModelData());
        optionsButton.setItemMeta(optionsButtonMeta);
        eggs[49] = optionsButton;
        // add buttons
        ItemStack restoreButton = new ItemStack(Material.APPLE, 1);
        ItemMeta restoreButtonMeta = restoreButton.getItemMeta();
        restoreButtonMeta.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_RESTORE"));
        restoreButtonMeta.setCustomModelData(GUIGeneticManipulator.BUTTON_RESTORE.getCustomModelData());
        restoreButton.setItemMeta(restoreButtonMeta);
        eggs[51] = restoreButton;
        // set
        ItemStack modifyButton = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta modifyButtonMeta = modifyButton.getItemMeta();
        modifyButtonMeta.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_DNA"));
        modifyButtonMeta.setCustomModelData(GUIGeneticManipulator.BUTTON_DNA.getCustomModelData());
        modifyButton.setItemMeta(modifyButtonMeta);
        eggs[52] = modifyButton;
        ItemStack cancelButton = new ItemStack(Material.BOWL, 1);
        ItemMeta cancelButtonMeta = cancelButton.getItemMeta();
        cancelButtonMeta.setDisplayName(ChatColor.RESET + plugin.getLanguage().getString("BUTTON_CANCEL"));
        cancelButtonMeta.setCustomModelData(GUIGeneticManipulator.BUTTON_CANCEL.getCustomModelData());
        cancelButton.setItemMeta(cancelButtonMeta);
        eggs[53] = cancelButton;

        return eggs;
    }

    public ItemStack[] getTerminal() {
        return terminal;
    }
}
