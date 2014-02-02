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

import java.util.logging.Level;

import net.nyvaria.component.wrapper.NyvariaPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.tyrannyofheaven.bukkit.zPermissions.ZPermissionsPlugin;
import org.tyrannyofheaven.bukkit.zPermissions.ZPermissionsService;

/**
 * @author Paul Thompson
 *
 */
public class ZPermissionsHook {
	private static final String   PLUGIN_NAME = "zPermissions";
	
	private static NyvariaPlugin       plugin        = null;
	private static ZPermissionsPlugin  zperms        = null;
	private static ZPermissionsService zpermsService = null;

	private ZPermissionsHook() {
		// Prevent instantiation
	}

	public static boolean initialize(NyvariaPlugin plugin) {
		ZPermissionsHook.plugin = plugin;

		// Try to hook zPermissions
		Plugin zpermsPlugin = ZPermissionsHook.plugin.getServer().getPluginManager().getPlugin(PLUGIN_NAME);
		
		if (zpermsPlugin != null) {
			zperms = (ZPermissionsPlugin) zpermsPlugin;
			
			try {
				zpermsService = Bukkit.getServicesManager().load(ZPermissionsService.class);
			} catch (NoClassDefFoundError e) {
				ZPermissionsHook.plugin.log(Level.WARNING, "ZPermissionsService class not found - zPerms support disabled!");
				zperms        = null;
				zpermsService = null;
				
			} finally {
				if (zpermsService == null) {
					ZPermissionsHook.plugin.log(Level.WARNING, "ZPermissionsService instance unexepectedly null after loading - zPerms support disabled!");
				}
			}
		}
		
		if (zpermsService == null) {
			zperms = null;
			return false;
		}
		
		ZPermissionsHook.plugin.log(String.format("%1$s detected: %2$s", PLUGIN_NAME, zperms.getDescription().getVersion()));
		return true;
	}
	
	public static boolean is_hooked() {
		return (zpermsService != null);
	}
	
	public static String getGroupPrefix(String name) {
		String prefix = null;
		
		if (is_hooked()) {
			try {
				prefix = zpermsService.getGroupMetadata(name, "prefix", String.class);
				prefix = ChatColor.translateAlternateColorCodes('&', prefix);
			} catch (Exception e) {
				prefix = null;
			}
		}
		
		return (prefix != null ? prefix : "");
	}

	public static String getGroupSuffix(String name) {
		String suffix = null;
		
		if (is_hooked()) {
			try {
				suffix = zpermsService.getGroupMetadata(name, "suffix", String.class);
				suffix = ChatColor.translateAlternateColorCodes('&', suffix);
			} catch (Exception e) {
				suffix = null;
			}
		}
		
		return (suffix != null ? suffix : "");
	}
}
