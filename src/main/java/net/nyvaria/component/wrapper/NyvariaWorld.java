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

import net.nyvaria.component.hook.MultiverseHook;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author Paul Thompson
 */
public class NyvariaWorld {
    private NyvariaWorld() {
        // Prevent instantiation
    }

    /**
     * Static Methods
     */

    public static String getWorldAlias(String name) {
        String alias = name;

        if (MultiverseHook.isHooked()) {
            alias = MultiverseHook.getWorldAlias(name);
        }

        return alias;
    }

    public static String getWorldAlias(World world) {
        return getWorldAlias(world.getName());
    }

    public static String getWorldAlias(Location location) {
        return getWorldAlias(location.getWorld());
    }
}
