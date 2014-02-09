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
import org.bukkit.plugin.Plugin;
import org.wargamer2010.signshop.SignShop;

/**
 * @author Paul Thompson
 */
public class SignShopHook {
    private static final String PLUGIN_NAME = "SignShop";

    private static NyvariaPlugin plugin = null;
    private static SignShop signshop = null;

    private SignShopHook() {
        // Prevent instantiation
    }

    public static boolean enable(NyvariaPlugin plugin) {
        SignShopHook.plugin = plugin;

        // Try to hook SignShop
        Plugin signshopPlugin = SignShopHook.plugin.getServer().getPluginManager().getPlugin(PLUGIN_NAME);

        if (signshopPlugin != null) {
            SignShopHook.plugin.log(String.format("%1$s detected: %2$s", PLUGIN_NAME, signshopPlugin.getDescription().getVersion()));
            signshop = (SignShop) signshopPlugin;
        }

        return isHooked();
    }

    public static void disable() {
        signshop = null;
        plugin = null;
    }

    public static boolean isHooked() {
        return (signshop != null);
    }
}
