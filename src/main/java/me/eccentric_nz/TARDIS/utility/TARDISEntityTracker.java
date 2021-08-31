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
import me.eccentric_nz.TARDIS.control.TARDISScanner;
import me.eccentric_nz.tardischunkgenerator.disguise.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class TARDISEntityTracker {

    private final TARDIS plugin;

    public TARDISEntityTracker(TARDIS plugin) {
        this.plugin = plugin;
    }

    public void addNPCs(Location exterior, Location interior, UUID uuid) {
        List<Entity> entities = TARDISScanner.getNearbyEntities(exterior, 6);
        List<UUID> npcids = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                Location location = entity.getLocation();
                // determine relative location
                double relx = location.getX() - exterior.getX();
                double rely = location.getY() - exterior.getY();
                double relz = location.getZ() - exterior.getZ();
                double adjx = interior.getX() + relx;
                double adjy = interior.getY() + rely;
                double adjz = interior.getZ() + relz;
                Location l = new Location(interior.getWorld(), adjx, adjy, adjz);
                l.setYaw(location.getYaw());
                l.setPitch(location.getPitch());
                // create NPC
                plugin.setTardisSpawn(true);
                ArmorStand stand = (ArmorStand) interior.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
                npcids.add(stand.getUniqueId());
                Object[] options = null;
                switch (entity.getType()) {
                    case CAT -> options = new Object[]{((Cat) entity).getCatType(), ((Tameable) entity).isTamed(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case PANDA -> options = new Object[]{GENE.getFromPandaGene(((Panda) entity).getMainGene()), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case DONKEY, MULE -> options = new Object[]{((ChestedHorse) entity).isCarryingChest(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case PIG -> options = new Object[]{((Pig) entity).hasSaddle(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case CREEPER -> options = new Object[]{((Creeper) entity).isPowered()};
                    case ENDERMAN -> options = new Object[]{((Enderman) entity).getCarriedBlock() != null};
                    case SHEEP -> options = new Object[]{((Sheep) entity).getColor(), ((Tameable) entity).isTamed(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case WOLF -> options = new Object[]{((Wolf) entity).getCollarColor(), ((Tameable) entity).isTamed(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case HORSE -> options = new Object[]{((Horse) entity).getColor(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case LLAMA -> options = new Object[]{((Llama) entity).getColor(), ((Llama) entity).getInventory().getDecor() != null, AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case OCELOT -> options = new Object[]{((Tameable) entity).isTamed(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case PARROT -> options = new Object[]{((Parrot) entity).getVariant(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case RABBIT -> options = new Object[]{((Rabbit) entity).getRabbitType(), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case VILLAGER -> options = new Object[]{PROFESSION.getFromVillagerProfession(((Villager) entity).getProfession()), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case ZOMBIE_VILLAGER -> options = new Object[]{PROFESSION.getFromVillagerProfession(((ZombieVillager) entity).getVillagerProfession()), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case SLIME, MAGMA_CUBE -> options = new Object[]{((Slime) entity).getSize()};
                    case COW, TURTLE, ZOMBIE, BEE -> options = new Object[]{AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case SNOWMAN -> options = new Object[]{((Snowman) entity).isDerp()};
                    case PUFFERFISH -> options = new Object[]{((PufferFish) entity).getPuffState()};
                    case TROPICAL_FISH -> options = new Object[]{((TropicalFish) entity).getPattern()};
                    case MUSHROOM_COW -> options = new Object[]{MUSHROOM_COW.getFromMushroomCowType(((MushroomCow) entity).getVariant()), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    case FOX -> options = new Object[]{FOX.getFromFoxType(((Fox) entity).getFoxType()), AGE.getFromBoolean(!((Ageable) entity).isAdult())};
                    default -> {
                    }
                }
                plugin.getTardisHelper().disguiseArmourStand(stand, entity.getType(), options);
            }
        }
        if (npcids.size() > 0) {
            plugin.getTrackerKeeper().getRenderedNPCs().put(uuid, npcids);
        }
    }

    public void removeNPCs(UUID uuid) {
        plugin.getTrackerKeeper().getRenderedNPCs().get(uuid).forEach((i) -> {
            Entity npc = Bukkit.getEntity(i);
            if (npc != null) {
                plugin.getTardisHelper().undisguiseArmourStand((ArmorStand) npc);
                npc.remove();
            }
        });
        plugin.getTrackerKeeper().getRenderedNPCs().remove(uuid);
    }
}
