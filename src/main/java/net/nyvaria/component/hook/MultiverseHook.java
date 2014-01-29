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

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.onarandombox.MultiverseCore.MultiverseCore;

/**
 * @author Paul Thompson
 *
 */
public class MultiverseHook {
	private static JavaPlugin plugin = null;
	private static MultiverseCore multiverseCore = null;

	private MultiverseHook() {
		// Disallow instantiation
	}

	public static boolean initialise(JavaPlugin plugin) {
		MultiverseHook.plugin = plugin;

		// Try to hook Multiverse-Core
		Plugin multiversePlugin = MultiverseHook.plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");

		if (multiversePlugin != null) {
			MultiverseHook.plugin.getLogger().log(Level.INFO, "Multiverse-Core detected:" + multiversePlugin.getDescription().getVersion());
			multiverseCore = (MultiverseCore) multiversePlugin;
		}

		return is_hooked();
	}

	public static boolean is_hooked() {
		return (multiverseCore != null);
	}

	public static String getWorldAlias(String name) {
		if (multiverseCore != null) {
			return multiverseCore.getMVWorldManager().getMVWorld(name).getAlias();
		}
		return null;
	}
}
