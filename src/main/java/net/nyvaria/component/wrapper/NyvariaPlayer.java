/**
 * Copyright (c) 2013-2014
 * Paul Thompson <captbunzo@gmail.com> / Nyvaria <geeks@nyvaria.net>
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

/**
 *
 */
package net.nyvaria.component.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Thompson
 */
public class NyvariaPlayer {
    private Player player = null;
    private OfflinePlayer offlinePlayer = null;

    public NyvariaPlayer(Player player) {
        this.player = player;
        this.offlinePlayer = player;
    }

    public NyvariaPlayer(OfflinePlayer offlinePlayer) {
        this.player = offlinePlayer.getPlayer();
        this.offlinePlayer = offlinePlayer;
    }

    /**
     * Methods for returning stuff about this player
     */

    public Player getPlayer() {
        return player;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public NyvariaGroup getPrimaryGroup() {
        return NyvariaGroup.getPrimaryGroup(offlinePlayer);
    }

    public String getName() {
        return offlinePlayer.getName();
    }

    public String getWrappedName() {
        NyvariaGroup group = getPrimaryGroup();
        return group.getPrefix() + offlinePlayer.getName() + group.getSuffix();
    }

    /**
     * Static methods for wrapping this player name
     */

    public static String getWrappedName(Player player) {
        return new NyvariaPlayer(player).getWrappedName();
    }

    public static String getWrappedName(OfflinePlayer offlinePlayer) {
        return new NyvariaPlayer(offlinePlayer).getWrappedName();
    }

    /**
     * Static methods matching online or offline players
     */

    public static List<Player> matchPlayer(String partialName, CommandSender sender) {
        List<Player> matchedPlayers = Bukkit.getServer().matchPlayer(partialName);

        if (matchedPlayers.size() == 0) {
            sender.sendMessage(ChatColor.YELLOW + String.format("Cannot find a player online named %1$s", ChatColor.WHITE + partialName + ChatColor.YELLOW));
        } else if (matchedPlayers.size() > 1) {
            sender.sendMessage(ChatColor.YELLOW + String.format("%1$s matches more then one player online", ChatColor.WHITE + partialName + ChatColor.YELLOW));
        }

        return matchedPlayers;
    }

    public static List<OfflinePlayer> matchOfflinePlayer(String partialName) {
        List<OfflinePlayer> matchedPlayers = new ArrayList<OfflinePlayer>();

        for (OfflinePlayer offlinePlayer : Bukkit.getServer().getOfflinePlayers()) {
            if (partialName.equalsIgnoreCase(offlinePlayer.getName())) {
                // Exact match
                matchedPlayers.clear();
                matchedPlayers.add(offlinePlayer);
                break;
            }

            if (offlinePlayer.getName().toLowerCase().contains(partialName.toLowerCase())) {
                // Partial match
                matchedPlayers.add(offlinePlayer);
            }
        }

        return matchedPlayers;
    }

    public static List<OfflinePlayer> matchOfflinePlayer(String partialName, CommandSender sender) {
        List<OfflinePlayer> matchedOfflinePlayers = matchOfflinePlayer(partialName);

        if (matchedOfflinePlayers.size() == 0) {
            sender.sendMessage(ChatColor.YELLOW + String.format("Cannot find a player named %1$s", ChatColor.WHITE + partialName + ChatColor.YELLOW));
        } else if (matchedOfflinePlayers.size() > 1) {
            sender.sendMessage(ChatColor.YELLOW + String.format("%1$s matches more then one player", ChatColor.WHITE + partialName + ChatColor.YELLOW));
        }

        return matchedOfflinePlayers;
    }

}
