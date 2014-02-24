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
package net.nyvaria.component.hook;

import net.nyvaria.component.wrapper.NyvariaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.messaging.PluginMessageListenerRegistration;

import java.io.*;

/**
 * Created by Paul Thompson on 23/02/2014.
 * @author Paul Thompson
 */
public class BungeeHook implements Listener, PluginMessageListener{
    private static String CHANNEL = "BungeeCord";
    private static BungeeHook instance = null;

    private NyvariaPlugin plugin = null;
    private String serverName = null;

    private BungeeHook() {
        // Prevent instantiation
    }

    private BungeeHook(NyvariaPlugin plugin) {
        PluginMessageListenerRegistration pmlreg;

        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, BungeeHook.CHANNEL);
        pmlreg = plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, BungeeHook.CHANNEL, this);

        if (pmlreg == null) {
            plugin.log("BungeeHook :: pmlreg == null");
        } else if (pmlreg.isValid()) {
            plugin.log("BungeeHook :: pmlreg is valid");
            plugin.log("BungeeHook :: pmlreg.getChannel() = " + pmlreg.getChannel());
            plugin.log("BungeeHook :: pmlreg.getPlugin() :: Name = " + pmlreg.getPlugin().getName());
            plugin.log("BungeeHook :: pmlreg.getListener() :: Name = " + pmlreg.getListener().getClass().getName());
        } else {
            plugin.log("BungeeHook :: pmgreg is not valid");
        }
    }

    public static boolean enable(NyvariaPlugin plugin) {
        instance = new BungeeHook(plugin);
        return isHooked();
    }

    public static void disable() {
        instance = null;
    }

    public static boolean isHooked() {
        return (instance != null) && (instance.serverName != null);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        this.plugin.log("BungeeHook.onPluginMessageReceived :: channel = " + channel);
        if (!channel.equals(BungeeHook.CHANNEL)) {
            return;
        }

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
            String subchannel = in.readUTF();

            this.plugin.log("BungeeHook.onPluginMessageReceived :: subchannel = " + subchannel);
            if (subchannel.equals("GetServer")) {
                serverName = in.readUTF();
                this.plugin.log("BungeeHook.onPluginMessageReceived :: serverName = " + serverName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.plugin.log("BungeeHook.onPlayerJoin :: player = " + event.getPlayer().getName());

        if (!BungeeHook.isHooked()) {
            this.plugin.log("BungeeHook.onPlayerJoin :: BungeeHook.isHooked() = false");
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    sendGetServer(event.getPlayer());
                }
            }, 20);
        } else {
            this.plugin.log("BungeeHook.onPlayerJoin :: BungeeHook.isHooked() = true");
        }
    }

    private void sendGetServer(Player player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("GetServer");
            player.sendPluginMessage(plugin, BungeeHook.CHANNEL, b.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
