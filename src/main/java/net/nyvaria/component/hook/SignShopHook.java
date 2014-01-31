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
import org.wargamer2010.signshop.SignShop;

/**
 * @author Paul Thompson
 *
 */
public class SignShopHook {
	private static JavaPlugin plugin = null;
	private static SignShop signshop = null;

	private SignShopHook() {
		// Disallow instantiation
	}

	public static boolean initialize(JavaPlugin plugin) {
		SignShopHook.plugin = plugin;

		// Try to hook Multiverse-Core
		Plugin signshopPlugin = SignShopHook.plugin.getServer().getPluginManager().getPlugin("SignShop");

		if (signshopPlugin != null) {
			SignShopHook.plugin.getLogger().log(Level.INFO, "SignShop detected:" + signshopPlugin.getDescription().getVersion());
			signshop = (SignShop) signshopPlugin;
		}

		return is_hooked();
	}

	public static boolean is_hooked() {
		return (signshop != null);
	}
}
