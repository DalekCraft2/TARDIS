/*
 * Copyright (C) 2018 eccentric_nz
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
package me.eccentric_nz.TARDIS.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * TabCompleter for /tardisbind command
 */
public class TARDISBindTabComplete extends TARDISCompleter implements TabCompleter {

    private final List<String> ROOT_SUBS = ImmutableList.of("save", "cmd", "player", "area", "biome", "remove", "update", "chameleon", "transmat");
    private final ImmutableList<String> T1_SUBS = ImmutableList.of("hide", "rebuild", "home", "cave", "make_her_blue");
    private final ImmutableList<String> CHAM_SUBS = ImmutableList.of("off", "adapt", "invisible");

    public TARDISBindTabComplete() {
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        // Remember that we can return null to default to online player name matching
        String lastArg = args[args.length - 1];
        if (args.length <= 1) {
            return partial(args[0], ROOT_SUBS);
        } else if (args.length == 2) {
            String sub = args[0];
            switch (sub) {
                case "player":
                    return null;
                case "cmd":
                    return partial(lastArg, T1_SUBS);
                case "chameleon":
                    return partial(lastArg, CHAM_SUBS);
            }
        }
        return ImmutableList.of();
    }
}
