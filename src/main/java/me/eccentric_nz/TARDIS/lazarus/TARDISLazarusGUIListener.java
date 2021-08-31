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
import me.eccentric_nz.TARDIS.api.event.TARDISGeneticManipulatorDisguiseEvent;
import me.eccentric_nz.TARDIS.api.event.TARDISGeneticManipulatorUndisguiseEvent;
import me.eccentric_nz.TARDIS.blueprints.TARDISPermission;
import me.eccentric_nz.TARDIS.listeners.TARDISMenuListener;
import me.eccentric_nz.TARDIS.messaging.TARDISMessage;
import me.eccentric_nz.TARDIS.utility.TARDISNumberParsers;
import me.eccentric_nz.TARDIS.utility.TARDISSounds;
import me.eccentric_nz.tardischunkgenerator.disguise.*;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.entity.Cat.Type;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * @author eccentric_nz
 */
public class TARDISLazarusGUIListener extends TARDISMenuListener implements Listener {

    private final TARDIS plugin;
    private final HashMap<UUID, Boolean> snowmen = new HashMap<>();
    private final HashMap<UUID, Integer> axolotls = new HashMap<>();
    private final HashMap<UUID, Integer> cats = new HashMap<>();
    private final HashMap<UUID, Integer> foxes = new HashMap<>();
    private final HashMap<UUID, Integer> genes = new HashMap<>();
    private final HashMap<UUID, Integer> horses = new HashMap<>();
    private final HashMap<UUID, Integer> llamas = new HashMap<>();
    private final HashMap<UUID, Integer> moos = new HashMap<>();
    private final HashMap<UUID, Integer> parrots = new HashMap<>();
    private final HashMap<UUID, Integer> professions = new HashMap<>();
    private final HashMap<UUID, Integer> puffers = new HashMap<>();
    private final HashMap<UUID, Integer> rabbits = new HashMap<>();
    private final HashMap<UUID, Integer> sheep = new HashMap<>();
    private final HashMap<UUID, Integer> slimes = new HashMap<>();
    private final HashMap<UUID, Integer> tropics = new HashMap<>();
    private final HashMap<UUID, String> disguises = new HashMap<>();
    private final List<Integer> slimeSizes = Arrays.asList(1, 2, 4);
    private final List<Integer> pufferStates = Arrays.asList(0, 1, 2);
    private final List<String> twaMonsters = Arrays.asList("WEEPING ANGEL", "CYBERMAN", "DALEK", "EMPTY CHILD", "ICE WARRIOR", "JUDOON", "K9", "OOD", "SILENT", "SILURIAN", "SONTARAN", "STRAX", "TOCLAFANE", "VASHTA NERADA", "ZYGON");
    private final List<String> twaHelmets = Arrays.asList("Weeping Angel Head", "Cyberman Head", "Dalek Head", "Empty Child Head", "Ice Warrior Head", "Judoon Head", "K9 Head", "Ood Head", "Silent Head", "Silurian Head", "Sontaran Head", "Strax Head", "Toclafane", "Vashta Nerada Head", "Zygon Head");

    public TARDISLazarusGUIListener(TARDIS plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    /**
     * Listens for player clicking inside an inventory. If the inventory is a TARDIS GUI, then the click is processed
     * accordingly.
     *
     * @param event a player clicking an inventory slot
     */
    @EventHandler(ignoreCancelled = true)
    public void onLazarusClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        String name = view.getTitle();
        if (name.equals(ChatColor.DARK_RED + "Genetic Manipulator")) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            Player player = (Player) event.getWhoClicked();
            UUID uuid = player.getUniqueId();
            Block block = plugin.getTrackerKeeper().getLazarus().get(uuid);
            if (block == null) {
                return;
            }
            int maxSlot = 45;
            if (slot >= 0 && slot < maxSlot) {
                // get selection
                ItemStack itemStack = view.getItem(slot);
                if (itemStack != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    // remember selection
                    String display = ChatColor.stripColor(itemMeta.getDisplayName());
                    if (twaMonsters.contains(display) && !plugin.checkTWA()) {
                        itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Genetic modification not available!"));
                        itemStack.setItemMeta(itemMeta);
                    } else {
                        if (display.equals("HEROBRINE")) {
                            display = "PLAYER";
                        }
                        disguises.put(uuid, display);
                        setSlotFourtyEight(view, display, uuid);
                    }
                } else {
                    disguises.put(uuid, "PLAYER");
                }
            }
            if (slot == 45) { // The Master Switch : ON | OFF
                ItemStack itemStack = view.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (TARDISPermission.hasPermission(player, "tardis.themaster")) {
                    if (plugin.getTrackerKeeper().getImmortalityGate().equals("")) {
                        boolean isOff = ChatColor.stripColor(itemMeta.getLore().get(0)).equals(plugin.getLanguage().getString("SET_OFF"));
                        String onOff = isOff ? plugin.getLanguage().getString("SET_ON") : plugin.getLanguage().getString("SET_OFF");
                        itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + onOff));
                        int customModelData = isOff ? 2 : 3;
                        itemMeta.setCustomModelData(customModelData);
                    } else {
                        itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "The Master Race is already", ChatColor.GRAY + "set to " + plugin.getTrackerKeeper().getImmortalityGate() + "!", ChatColor.GRAY + "Try again later."));
                    }
                } else {
                    itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "You do not have permission", ChatColor.GRAY + "to be The Master!"));
                }
                itemStack.setItemMeta(itemMeta);
            }
            if (slot == 47) { // adult / baby
                ItemStack itemStack = view.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                String onOff = (ChatColor.stripColor(itemMeta.getLore().get(0)).equals("ADULT")) ? "BABY" : "ADULT";
                itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + onOff));
                itemStack.setItemMeta(itemMeta);
            }
            if (slot == 48) { // type / colour
                if (disguises.containsKey(uuid)) {
                    setSlotFourtyEight(view, disguises.get(uuid), uuid);
                }
            }
            if (slot == 49) { // Tamed / Flying / Blazing / Powered / Beaming / Aggressive / Decorated / Chest carrying : TRUE | FALSE
                ItemStack itemStack = view.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> lore = itemMeta.getLore();
                int position = lore.size() - 1;
                String trueFalse = (ChatColor.stripColor(lore.get(position)).equals("FALSE")) ? ChatColor.GREEN + "TRUE" : ChatColor.RED + "FALSE";
                lore.set(position, ChatColor.GRAY + trueFalse);
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
            }
            if (slot == 51) { //remove disguise
                plugin.getTrackerKeeper().getGeneticManipulation().add(uuid);
                close(player);
                // animate the manipulator walls
                TARDISLazarusRunnable runnable = new TARDISLazarusRunnable(plugin, block);
                int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, runnable, 6L, 6L);
                runnable.setTaskID(taskId);
                TARDISSounds.playTARDISSound(player.getLocation(), "lazarus_machine");
                // undisguise the player
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (twaMonsters.contains(disguises.get(uuid))) {
                        twaOff(player);
                    } else if (plugin.isDisguisesOnServer()) {
                        TARDISLazarusLibs.removeDisguise(player);
                    } else {
                        TARDISLazarusDisguise.removeDisguise(player);
                    }
                    TARDISMessage.send(player, "GENETICS_RESTORED");
                    plugin.getPM().callEvent(new TARDISGeneticManipulatorUndisguiseEvent(player));
                }, 80L);
                // open the door
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    openDoor(block);
                    untrack(uuid, true);
                    plugin.getTrackerKeeper().getGeneticallyModified().remove(uuid);
                }, 100L);
            }
            if (slot == 52) { // add disguise
                plugin.getTrackerKeeper().getGeneticManipulation().add(uuid);
                close(player);
                // animate the manipulator walls
                TARDISLazarusRunnable runnable = new TARDISLazarusRunnable(plugin, block);
                int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, runnable, 6L, 6L);
                runnable.setTaskID(taskId);
                TARDISSounds.playTARDISSound(player.getLocation(), "lazarus_machine");
                // disguise the player
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (plugin.isDisguisesOnServer()) {
                        TARDISLazarusLibs.removeDisguise(player);
                    } else {
                        TARDISLazarusDisguise.removeDisguise(player);
                    }
                    if (isReversedPolarity(view)) {
                        plugin.getTrackerKeeper().setImmortalityGate(player.getName());
                        if (plugin.isDisguisesOnServer()) {
                            TARDISLazarusLibs.runImmortalityGate(player);
                        } else {
                            TARDISLazarusDisguise.runImmortalityGate(player);
                        }
                        plugin.getServer().broadcastMessage(plugin.getMessagePrefix() + "The Master (aka " + player.getName() + ") has cloned his genetic template to all players. Behold the Master Race!");
                        plugin.getPM().callEvent(new TARDISGeneticManipulatorDisguiseEvent(player, player.getName()));
                        // schedule a delayed task to remove the disguise
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            plugin.getServer().getOnlinePlayers().forEach((p) -> {
                                if (plugin.isDisguisesOnServer()) {
                                    TARDISLazarusLibs.removeDisguise(p);
                                } else {
                                    TARDISLazarusDisguise.removeDisguise(p);
                                }
                            });
                            plugin.getServer().broadcastMessage(plugin.getMessagePrefix() + "Lord Rassilon has reset the Master Race back to human form.");
                            plugin.getTrackerKeeper().setImmortalityGate("");
                            plugin.getPM().callEvent(new TARDISGeneticManipulatorUndisguiseEvent(player));
                        }, 3600L);
                    } else if (disguises.containsKey(uuid)) {
                        String disguise = disguises.get(uuid);
                        // undisguise first
                        twaOff(player);
                        if (twaMonsters.contains(disguise)) {
                            if (disguise.equals("WEEPING ANGEL")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise WEEPING_ANGEL on " + player.getUniqueId());
                            }
                            if (disguise.equals("CYBERMAN")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise CYBERMAN on " + player.getUniqueId());
                            }
                            if (disguise.equals("DALEK")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise DALEK on " + player.getUniqueId());
                            }
                            if (disguise.equals("EMPTY CHILD")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise EMPTY_CHILD on " + player.getUniqueId());
                            }
                            if (disguise.equals("ICE WARRIOR")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise ICE_WARRIOR on " + player.getUniqueId());
                            }
                            if (disguise.equals("JUDOON")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise JUDOON on " + player.getUniqueId());
                            }
                            if (disguise.equals("K9")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise K9 on " + player.getUniqueId());
                            }
                            if (disguise.equals("OOD")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise OOD on " + player.getUniqueId());
                            }
                            if (disguise.equals("SILENT")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise SILENT on " + player.getUniqueId());
                            }
                            if (disguise.equals("SILURIAN")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise SILURIAN on " + player.getUniqueId());
                            }
                            if (disguise.equals("SONTARAN")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise SONTARAN on " + player.getUniqueId());
                            }
                            if (disguise.equals("STRAX")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise STRAX on " + player.getUniqueId());
                            }
                            if (disguise.equals("TOCLAFANE")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise TOCLAFANE on " + player.getUniqueId());
                            }
                            if (disguise.equals("VASHTA NERADA")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise VASHTA on " + player.getUniqueId());
                            }
                            if (disguise.equals("ZYGON")) {
                                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise ZYGON on " + player.getUniqueId());
                            }
                        } else {
                            EntityType disguiseType = EntityType.valueOf(disguise);
                            Object[] options = null;
                            switch (disguiseType) {
                                case AXOLOTL:
                                    if (!plugin.isDisguisesOnServer()) {
                                        options = new Object[]{getAxolotlVariant(view), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case CAT:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getCatType(view), getBoolean(view), getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{getCatType(view), getBoolean(view), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case PANDA:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getGene(view), false, getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{GENE.getFromPandaGene(getGene(view)), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case DONKEY:
                                case MULE:
                                case PIG:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, null, getBoolean(view), (!getBoolean(view) && getBaby(view))).createDisguise();
                                    } else {
                                        options = new Object[]{getBoolean(view), AGE.getFromBoolean(!getBoolean(view) && getBaby(view))};
                                    }
                                    break;
                                case PILLAGER:
                                case BAT:
                                case CREEPER:
                                case ENDERMAN:
                                case BLAZE:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, null, getBoolean(view), false).createDisguise();
                                    } else {
                                        options = new Object[]{getBoolean(view)};
                                    }
                                    break;
                                case SHEEP:
                                case WOLF:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getColor(view), getBoolean(view), getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{getColor(view), getBoolean(view), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case HORSE:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getHorseColor(view), false, getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{getHorseColor(view), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case LLAMA:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getLlamaColor(view), getBoolean(view), (!getBoolean(view) && getBaby(view))).createDisguise();
                                    } else {
                                        options = new Object[]{getLlamaColor(view), getBoolean(view), AGE.getFromBoolean(!getBoolean(view) && getBaby(view))};
                                    }
                                    break;
                                case OCELOT:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, null, getBoolean(view), getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{getBoolean(view), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case PARROT:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getParrotVariant(view), false, getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{getParrotVariant(view), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case RABBIT:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getRabbitType(view), getBoolean(view), getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{getRabbitType(view), getBoolean(view), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case VILLAGER:
                                case ZOMBIE_VILLAGER:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getProfession(view), false, getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{PROFESSION.getFromVillagerProfession(getProfession(view)), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case SLIME:
                                case MAGMA_CUBE:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getSlimeSize(view), false, false).createDisguise();
                                    } else {
                                        options = new Object[]{getSlimeSize(view)};
                                    }
                                    break;
                                case COW:
                                case TURTLE:
                                case ZOMBIE:
                                case BEE:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, null, false, getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case SNOWMAN:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, snowmen.get(uuid), false, false).createDisguise();
                                    } else {
                                        options = new Object[]{snowmen.get(uuid)};
                                    }
                                    break;
                                case PUFFERFISH:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, puffers.get(uuid), false, false).createDisguise();
                                    } else {
                                        options = new Object[]{puffers.get(uuid)};
                                    }
                                    break;
                                case TROPICAL_FISH:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, TropicalFish.Pattern.values()[tropics.get(uuid)], false, false).createDisguise();
                                    } else {
                                        options = new Object[]{TropicalFish.Pattern.values()[tropics.get(uuid)]};
                                    }
                                    break;
                                case MUSHROOM_COW:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getCowVariant(view), false, getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{MUSHROOM_COW.getFromMushroomCowType(getCowVariant(view)), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                case FOX:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, getFoxType(view), false, getBaby(view)).createDisguise();
                                    } else {
                                        options = new Object[]{FOX.getFromFoxType(getFoxType(view)), AGE.getFromBoolean(getBaby(view))};
                                    }
                                    break;
                                default:
                                    if (plugin.isDisguisesOnServer()) {
                                        new TARDISLazarusLibs(player, disguise, null, false, false).createDisguise();
                                    }
                                    break;
                            }
                            if (!plugin.isDisguisesOnServer()) {
                                new TARDISLazarusDisguise(plugin, player, disguiseType, options).createDisguise();
                            }
                        }
                        TARDISMessage.send(player, "GENETICS_MODIFIED", disguise);
                        plugin.getPM().callEvent(new TARDISGeneticManipulatorDisguiseEvent(player, disguise));
                    }
                }, 80L);
                // open the door
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    openDoor(block);
                    untrack(uuid, false);
                    plugin.getTrackerKeeper().getGeneticallyModified().add(uuid);
                }, 100L);
            }
            if (slot == 53) {
                close(player);
                openDoor(block);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onLazarusClose(InventoryCloseEvent event) {
        String name = event.getView().getTitle();
        UUID uuid = event.getPlayer().getUniqueId();
        if (name.equals(ChatColor.DARK_RED + "Genetic Manipulator") && !plugin.getTrackerKeeper().getGeneticManipulation().contains(uuid)) {
            Block block = plugin.getTrackerKeeper().getLazarus().get(event.getPlayer().getUniqueId());
            if (block.getRelative(BlockFace.SOUTH).getType().equals(Material.COBBLESTONE_WALL)) {
                block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
                block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(Material.AIR);
            }
            untrack(uuid, false);
        }
    }

    private void untrack(UUID uuid, boolean remove) {
        // stop tracking player
        plugin.getTrackerKeeper().getLazarus().remove(uuid);
        if (remove) {
            disguises.remove(uuid);
        }
        sheep.remove(uuid);
        horses.remove(uuid);
        cats.remove(uuid);
        professions.remove(uuid);
        slimes.remove(uuid);
        plugin.getTrackerKeeper().getGeneticManipulation().remove(uuid);
    }

    private void openDoor(Block block) {
        block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
        block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(Material.AIR);
    }

    private void setSlotFourtyEight(InventoryView view, String disguise, UUID uuid) {
        String type = null;
        int option;
        switch (ChatColor.stripColor(disguise)) {
            case "AXOLOTL" -> {
                if (axolotls.containsKey(uuid)) {
                    option = (axolotls.get(uuid) + 1 < 5) ? axolotls.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Axolotl.Variant.values()[option].toString();
                axolotls.put(uuid, option);
            }
            case "SNOW_GOLEM" -> {
                boolean derp;
                if (snowmen.containsKey(uuid)) {
                    derp = !snowmen.get(uuid);
                } else {
                    derp = true;
                }
                snowmen.put(uuid, derp);
                type = (derp) ? "Pumpkin head" : "Derp face";
            }
            case "SHEEP", "WOLF" -> {
                if (sheep.containsKey(uuid)) {
                    option = (sheep.get(uuid) + 1 < 16) ? sheep.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = DyeColor.values()[option].toString();
                sheep.put(uuid, option);
            }
            case "HORSE" -> {
                if (horses.containsKey(uuid)) {
                    option = (horses.get(uuid) + 1 < 7) ? horses.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Horse.Color.values()[option].toString();
                horses.put(uuid, option);
            }
            case "LLAMA" -> {
                if (llamas.containsKey(uuid)) {
                    option = (llamas.get(uuid) + 1 < 4) ? llamas.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Llama.Color.values()[option].toString();
                llamas.put(uuid, option);
            }
            case "CAT" -> {
                if (cats.containsKey(uuid)) {
                    option = (cats.get(uuid) + 1 < 11) ? cats.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Type.values()[option].toString();
                cats.put(uuid, option);
            }
            case "FOX" -> {
                if (foxes.containsKey(uuid)) {
                    option = (foxes.get(uuid) + 1 < 2) ? foxes.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Fox.Type.values()[option].toString();
                foxes.put(uuid, option);
            }
            case "RABBIT" -> {
                if (rabbits.containsKey(uuid)) {
                    option = (rabbits.get(uuid) + 1 < 7) ? rabbits.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Rabbit.Type.values()[option].toString();
                rabbits.put(uuid, option);
            }
            case "PARROT" -> {
                if (parrots.containsKey(uuid)) {
                    option = (parrots.get(uuid) + 1 < 5) ? parrots.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Parrot.Variant.values()[option].toString();
                parrots.put(uuid, option);
            }
            case "VILLAGER", "ZOMBIE_VILLAGER" -> {
                if (professions.containsKey(uuid)) {
                    option = (professions.get(uuid) + 1 < Profession.values().length) ? professions.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Profession.values()[option].toString();
                professions.put(uuid, option);
            }
            case "SLIME", "MAGMA_CUBE" -> {
                if (slimes.containsKey(uuid)) {
                    option = (slimes.get(uuid) + 1 < 3) ? slimes.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = slimeSizes.get(option).toString();
                slimes.put(uuid, option);
            }
            case "MUSHROOM_COW" -> {
                if (moos.containsKey(uuid)) {
                    option = (moos.get(uuid) + 1 < 2) ? moos.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = MushroomCow.Variant.values()[option].toString();
                moos.put(uuid, option);
            }
            case "PUFFERFISH" -> {
                if (puffers.containsKey(uuid)) {
                    option = (puffers.get(uuid) + 1 < 3) ? puffers.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = pufferStates.get(option).toString();
                puffers.put(uuid, option);
            }
            case "TROPICAL_FISH" -> {
                if (tropics.containsKey(uuid)) {
                    option = (tropics.get(uuid) + 1 < 12) ? tropics.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = TropicalFish.Pattern.values()[option].toString();
                tropics.put(uuid, option);
            }
            case "PANDA" -> {
                if (genes.containsKey(uuid)) {
                    option = (genes.get(uuid) + 1 < 7) ? genes.get(uuid) + 1 : 0;
                } else {
                    option = 0;
                }
                type = Panda.Gene.values()[option].toString();
                genes.put(uuid, option);
            }
            default -> {
            }
        }
        if (type != null) {
            ItemStack itemStack = view.getItem(48);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + type));
            itemStack.setItemMeta(itemMeta);
        }
    }

    private boolean isReversedPolarity(InventoryView view) {
        ItemStack itemStack = view.getItem(45);
        ItemMeta itemMeta = itemStack.getItemMeta();
        return ChatColor.stripColor(itemMeta.getLore().get(0)).equals(plugin.getLanguage().getString("SET_ON"));
    }

    private DyeColor getColor(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return DyeColor.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return DyeColor.WHITE;
        }
    }

    private Horse.Color getHorseColor(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Horse.Color.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return Horse.Color.WHITE;
        }
    }

    private MushroomCow.Variant getCowVariant(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return MushroomCow.Variant.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return MushroomCow.Variant.RED;
        }
    }

    private Llama.Color getLlamaColor(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Llama.Color.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return org.bukkit.entity.Llama.Color.CREAMY;
        }
    }

    private Axolotl.Variant getAxolotlVariant(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Axolotl.Variant.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return Axolotl.Variant.WILD;
        }
    }

    private Type getCatType(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Type.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return Type.TABBY;
        }
    }

    private Fox.Type getFoxType(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Fox.Type.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return Fox.Type.RED;
        }
    }

    private Panda.Gene getGene(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Panda.Gene.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return Panda.Gene.NORMAL;
        }
    }

    private Parrot.Variant getParrotVariant(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Parrot.Variant.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return Parrot.Variant.GRAY;
        }
    }

    private Rabbit.Type getRabbitType(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Rabbit.Type.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return Rabbit.Type.BROWN;
        }
    }

    private Profession getProfession(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        try {
            return Profession.valueOf(ChatColor.stripColor(itemMeta.getLore().get(0)));
        } catch (IllegalArgumentException e) {
            return Profession.FARMER;
        }
    }

    private int getSlimeSize(InventoryView view) {
        ItemStack itemStack = view.getItem(48);
        ItemMeta itemMeta = itemStack.getItemMeta();
        int size = TARDISNumberParsers.parseInt(ChatColor.stripColor(itemMeta.getLore().get(0)));
        return (size == 0) ? 2 : size;
    }

    private boolean getBaby(InventoryView view) {
        ItemStack itemStack = view.getItem(47);
        ItemMeta itemMeta = itemStack.getItemMeta();
        return ChatColor.stripColor(itemMeta.getLore().get(0)).equals("BABY");
    }

    private boolean getBoolean(InventoryView view) {
        ItemStack itemStack = view.getItem(49);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        int position = lore.size() - 1;
        return ChatColor.stripColor(lore.get(position)).equals("TRUE");
    }

    private void twaOff(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName()) {
            String helmetName = ChatColor.stripColor(helmet.getItemMeta().getDisplayName());
            if (twaHelmets.contains(helmetName)) {
                plugin.getServer().dispatchCommand(plugin.getConsole(), "twa disguise WEEPING_ANGEL off " + player.getUniqueId());
            }
        }
    }
}
