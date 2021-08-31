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
import me.eccentric_nz.TARDIS.custommodeldata.GUIChameleonHelp;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

/**
 * @author eccentric_nz
 */
class TARDISChameleonHelpGUI {

    private final TARDIS plugin;
    private final ItemStack[] help;

    TARDISChameleonHelpGUI(TARDIS plugin) {
        this.plugin = plugin;
        help = getItemStack();
    }

    private ItemStack[] getItemStack() {

        // back
        ItemStack back = new ItemStack(Material.ARROW, 1);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("BACK_CONSTRUCT"));
        back.setItemMeta(backMeta);
        // help
        ItemStack info = new ItemStack(Material.BOWL, 1);
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("INFO"));
        List<String> infoLore = plugin.getChameleonGuis().getStringList("INFO_HELP_1");
        for (int i = 0; i < infoLore.size(); i++) {
            infoLore.set(i, ChatColor.GRAY + infoLore.get(i));
        }
        infoMeta.setLore(infoLore);
        infoMeta.setCustomModelData(GUIChameleonHelp.INFO_HELP_1.getCustomModelData());
        info.setItemMeta(infoMeta);
        // help
        ItemStack info2 = new ItemStack(Material.BOWL, 1);
        ItemMeta info2Meta = info2.getItemMeta();
        info2Meta.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("INFO"));
        List<String> info2Lore = plugin.getChameleonGuis().getStringList("INFO_HELP_2");
        for (int i = 0; i < info2Lore.size(); i++) {
            info2Lore.set(i, ChatColor.GRAY + info2Lore.get(i));
        }
        info2Meta.setLore(info2Lore);
        info2Meta.setCustomModelData(GUIChameleonHelp.INFO_HELP_2.getCustomModelData());
        info2.setItemMeta(info2Meta);
        // one
        ItemStack column1 = new ItemStack(Material.BOWL, 1);
        ItemMeta column1Meta = column1.getItemMeta();
        column1Meta.setDisplayName(ChatColor.RESET + "1");
        column1Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_L_FRONT")));
        column1Meta.setCustomModelData(GUIChameleonHelp.COL_L_FRONT.getCustomModelData());
        column1.setItemMeta(column1Meta);
        // two
        ItemStack column2 = new ItemStack(Material.BOWL, 1);
        ItemMeta column2Meta = column2.getItemMeta();
        column2Meta.setDisplayName(ChatColor.RESET + "2");
        column2Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_L_MIDDLE")));
        column2Meta.setCustomModelData(GUIChameleonHelp.COL_L_MIDDLE.getCustomModelData());
        column2.setItemMeta(column2Meta);
        // three
        ItemStack column3 = new ItemStack(Material.BOWL, 1);
        ItemMeta column3Meta = column3.getItemMeta();
        column3Meta.setDisplayName(ChatColor.RESET + "3");
        column3Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_L_BACK")));
        column3Meta.setCustomModelData(GUIChameleonHelp.COL_L_BACK.getCustomModelData());
        column3.setItemMeta(column3Meta);
        // four
        ItemStack column4 = new ItemStack(Material.BOWL, 1);
        ItemMeta column4Meta = column4.getItemMeta();
        column4Meta.setDisplayName(ChatColor.RESET + "4");
        column4Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_B_MIDDLE")));
        column4Meta.setCustomModelData(GUIChameleonHelp.COL_B_MIDDLE.getCustomModelData());
        column4.setItemMeta(column4Meta);
        // five
        ItemStack column5 = new ItemStack(Material.BOWL, 1);
        ItemMeta column5Meta = column5.getItemMeta();
        column5Meta.setDisplayName(ChatColor.RESET + "5");
        column5Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_R_BACK")));
        column5Meta.setCustomModelData(GUIChameleonHelp.COL_R_BACK.getCustomModelData());
        column5.setItemMeta(column5Meta);
        // six
        ItemStack column6 = new ItemStack(Material.BOWL, 1);
        ItemMeta column6Meta = column6.getItemMeta();
        column6Meta.setDisplayName(ChatColor.RESET + "6");
        column6Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_R_MIDDLE")));
        column6Meta.setCustomModelData(GUIChameleonHelp.COL_R_MIDDLE.getCustomModelData());
        column6.setItemMeta(column6Meta);
        // seven
        ItemStack column7 = new ItemStack(Material.BOWL, 1);
        ItemMeta column7Meta = column7.getItemMeta();
        column7Meta.setDisplayName(ChatColor.RESET + "7");
        column7Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_R_FRONT")));
        column7Meta.setCustomModelData(GUIChameleonHelp.COL_R_FRONT.getCustomModelData());
        column7.setItemMeta(column7Meta);
        // eight
        ItemStack column8 = new ItemStack(Material.BOWL, 1);
        ItemMeta column8Meta = column8.getItemMeta();
        column8Meta.setDisplayName(ChatColor.RESET + "8");
        column8Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_F_MIDDLE")));
        column8Meta.setCustomModelData(GUIChameleonHelp.COL_F_MIDDLE.getCustomModelData());
        column8.setItemMeta(column8Meta);
        // nine
        ItemStack column9 = new ItemStack(Material.BOWL, 1);
        ItemMeta column9Meta = column9.getItemMeta();
        column9Meta.setDisplayName(ChatColor.RESET + "9");
        column9Meta.setLore(Collections.singletonList(ChatColor.GRAY + plugin.getChameleonGuis().getString("COL_C_LAMP")));
        column9Meta.setCustomModelData(GUIChameleonHelp.COL_C_LAMP.getCustomModelData());
        column9.setItemMeta(column9Meta);
        // grid
        ItemStack gridInfo = new ItemStack(Material.BOWL, 1);
        ItemMeta gridInfoMeta = gridInfo.getItemMeta();
        gridInfoMeta.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("INFO"));
        List<String> gridInfoLore = plugin.getChameleonGuis().getStringList("INFO_HELP_3");
        for (int i = 0; i < gridInfoLore.size(); i++) {
            gridInfoLore.set(i, ChatColor.GRAY + gridInfoLore.get(i));
        }
        gridInfoMeta.setLore(gridInfoLore);
        gridInfoMeta.setCustomModelData(GUIChameleonHelp.INFO_HELP_3.getCustomModelData());
        gridInfo.setItemMeta(gridInfoMeta);
        // column
        ItemStack columnInfo = new ItemStack(Material.BOWL, 1);
        ItemMeta columnInfoMeta = columnInfo.getItemMeta();
        columnInfoMeta.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("INFO"));
        List<String> columnInfoLore = plugin.getChameleonGuis().getStringList("INFO_HELP_4");
        for (int i = 0; i < columnInfoLore.size(); i++) {
            columnInfoLore.set(i, ChatColor.GRAY + columnInfoLore.get(i));
        }
        columnInfoMeta.setLore(columnInfoLore);
        columnInfoMeta.setCustomModelData(GUIChameleonHelp.INFO_HELP_4.getCustomModelData());
        columnInfo.setItemMeta(columnInfoMeta);
        // example
        ItemStack exampleButton = new ItemStack(Material.BOWL, 1);
        ItemMeta exampleButtonMeta = exampleButton.getItemMeta();
        exampleButtonMeta.setDisplayName(ChatColor.RESET + plugin.getChameleonGuis().getString("VIEW_TEMP"));
        exampleButtonMeta.setCustomModelData(GUIChameleonHelp.VIEW_TEMP.getCustomModelData());
        exampleButton.setItemMeta(exampleButtonMeta);
        // one
        ItemStack row1 = new ItemStack(Material.BOWL, 1);
        ItemMeta row1Meta = row1.getItemMeta();
        row1Meta.setDisplayName(ChatColor.RESET + "1");
        row1Meta.setCustomModelData(GUIChameleonHelp.ROW_1.getCustomModelData());
        row1.setItemMeta(row1Meta);
        // two
        ItemStack row2 = new ItemStack(Material.BOWL, 1);
        ItemMeta row2Meta = row2.getItemMeta();
        row2Meta.setDisplayName(ChatColor.RESET + "2");
        row2Meta.setCustomModelData(GUIChameleonHelp.ROW_2.getCustomModelData());
        row2.setItemMeta(row2Meta);
        // three
        ItemStack row3 = new ItemStack(Material.BOWL, 1);
        ItemMeta row3Meta = row3.getItemMeta();
        row3Meta.setDisplayName(ChatColor.RESET + "3");
        row3Meta.setCustomModelData(GUIChameleonHelp.ROW_3.getCustomModelData());
        row3.setItemMeta(row3Meta);
        // four
        ItemStack row4 = new ItemStack(Material.BOWL, 1);
        ItemMeta row4Meta = row4.getItemMeta();
        row4Meta.setDisplayName(ChatColor.RESET + "4");
        row4Meta.setCustomModelData(GUIChameleonHelp.ROW_4.getCustomModelData());
        row4.setItemMeta(row4Meta);

        return new ItemStack[]{
                back, null, null, info, info2, null, null, null, null,
                null, null, null, null, null, null, null, columnInfo, null,
                null, gridInfo, null, null, null, null, null, row4, null,
                column3, column4, column5, null, null, null, null, row3, null,
                column2, column9, column6, null, exampleButton, null, null, row2, null,
                column1, column8, column7, null, null, null, null, row1, null
        };
    }

    public ItemStack[] getHelp() {
        return help;
    }
}
