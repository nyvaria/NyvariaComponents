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

import com.onarandombox.MultiverseCore.MultiverseCore;
import net.nyvaria.component.wrapper.NyvariaPlugin;
import org.bukkit.plugin.Plugin;

/**
 * @author Paul Thompson
 */
public class MultiverseHook {
    private static final String PLUGIN_NAME = "Multiverse-Core";

    private static NyvariaPlugin plugin = null;
    private static MultiverseCore multiverseCore = null;

    private MultiverseHook() {
        // Prevent instantiation
    }

    public static boolean enable(NyvariaPlugin plugin) {
        MultiverseHook.plugin = plugin;

        // Try to hook Multiverse-Core
        Plugin multiversePlugin = MultiverseHook.plugin.getServer().getPluginManager().getPlugin(PLUGIN_NAME);

        if (multiversePlugin != null) {
            MultiverseHook.plugin.log(String.format("%1$s detected: %2$s", PLUGIN_NAME, multiversePlugin.getDescription().getVersion()));
            multiverseCore = (MultiverseCore) multiversePlugin;
        }

        return isHooked();
    }

    public static void disable() {
        multiverseCore = null;
        plugin = null;
    }

    public static boolean isHooked() {
        return (multiverseCore != null);
    }

    public static String getWorldAlias(String name) {
        if (isHooked()) {
            return multiverseCore.getMVWorldManager().getMVWorld(name).getAlias();
        }
        return null;
    }
}
