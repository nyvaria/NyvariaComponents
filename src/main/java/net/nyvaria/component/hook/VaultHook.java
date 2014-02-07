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
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author Paul Thompson
 *
 */
public class VaultHook {
	private static final String  PLUGIN_NAME = "Vault";
	
	private static NyvariaPlugin plugin      = null;
	private static Vault         vault       = null;
	private static Permission    permissions = null;

	private VaultHook() {
		// Prevent instantiation
	}
	
	public static boolean enable(NyvariaPlugin plugin) {
		VaultHook.plugin = plugin;
		
		// Try to hook Vault
		Plugin vaultPlugin = VaultHook.plugin.getServer().getPluginManager().getPlugin(PLUGIN_NAME);

		if (vaultPlugin != null) {
			VaultHook.plugin.log("%1$s detected: %2$s", PLUGIN_NAME, vaultPlugin.getDescription().getVersion());
			vault = (Vault) vaultPlugin;
			VaultHook.setupPermissions();
		}

		return is_hooked();
	}

	public static void disable() {
		permissions = null;
		vault       = null;
		plugin      = null;
	}
	
	public static boolean is_hooked() {
		return (vault != null);
	}
	
	private static boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
		permissions = rsp.getProvider();
		return (permissions != null);
	}
	
	public static String getPrimaryGroup(Player player) {
		return permissions.getPrimaryGroup(player);
	}
	
	public static String getPrimaryGroup(OfflinePlayer offlinePlayer) {
		if (offlinePlayer.getPlayer() != null) {
			return getPrimaryGroup(offlinePlayer.getPlayer());
		}
		return permissions.getPrimaryGroup((String) null, offlinePlayer.getName());
	}
}
