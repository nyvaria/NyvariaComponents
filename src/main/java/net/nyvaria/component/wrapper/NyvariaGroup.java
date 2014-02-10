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

import net.nyvaria.component.hook.VaultHook;
import net.nyvaria.component.hook.ZPermissionsHook;
import net.nyvaria.component.util.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 */
public class NyvariaGroup {
    public static final String DEFAULT_GROUP_NAME   = null;
    public static final String DEFAULT_GROUP_PREFIX = "";
    public static final String DEFAULT_GROUP_SUFFIX = "";

    private String name;
    private String label;

    public NyvariaGroup(String name) {
        this.name   = name;
        this.label  = NyvariaGroup.getLabel(name);
    }

    /**
     * Getters
     */

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getPrefix() {
        return NyvariaGroup.getPrefix(getName());
    }

    public String getSuffix() {
        return NyvariaGroup.getSuffix(getName());
    }

    public String getWrappedName() {
        return getPrefix() + getName() + getSuffix();
    }

    public String getWrappedLabel() {
        return getPrefix() + getLabel() + getSuffix();
    }

    /**
     * Static methods for getting information about a group
     */

    public static String getLabel(String name) {
        return StringUtils.splitCamelCase(name);
    }

    public static String getPrefix(String name) {
        if (ZPermissionsHook.isHooked()) {
            return ZPermissionsHook.getGroupPrefix(name);
        }
        return NyvariaGroup.DEFAULT_GROUP_PREFIX;
    }

    public static String getSuffix(String name) {
        if (ZPermissionsHook.isHooked()) {
            return ZPermissionsHook.getGroupSuffix(name);
        }
        return NyvariaGroup.DEFAULT_GROUP_SUFFIX;
    }

    public static String getWrappedName(String name) {
        return getPrefix(name) + name + getSuffix(name);
    }

    public static String getWrappedLabel(String name) {
        return getPrefix(name) + getLabel(name) + getSuffix(name);
    }

    /**
     * Static methods for retrieving the primary group for a player
     */

    public static NyvariaGroup getPrimaryGroup(Player player) {
        return getPrimaryGroup(player, DEFAULT_GROUP_NAME);
    }

    public static NyvariaGroup getPrimaryGroup(Player player, String defaultGroupName) {
        String groupName = getPrimaryGroupName(player, defaultGroupName);
        return groupName == null ? null : new NyvariaGroup(groupName);
    }

    public static NyvariaGroup getPrimaryGroup(OfflinePlayer offlinePlayer) {
        return getPrimaryGroup(offlinePlayer, DEFAULT_GROUP_NAME);
    }

    public static NyvariaGroup getPrimaryGroup(OfflinePlayer offlinePlayer, String defaultGroupName) {
        String groupName = getPrimaryGroupName(offlinePlayer, defaultGroupName);
        return groupName == null ? null : new NyvariaGroup(groupName);
    }

    /**
     * Static methods for retrieving the name of the primary group for a player
     */

    public static String getPrimaryGroupName(Player player) {
        return getPrimaryGroupName(player, DEFAULT_GROUP_NAME);
    }

    public static String getPrimaryGroupName(Player player, String defaultGroupName) {
        if (VaultHook.isHooked()) {
            return VaultHook.getPrimaryGroup(player);
        }
        return defaultGroupName;
    }

    public static String getPrimaryGroupName(OfflinePlayer offlinePlayer) {
        return getPrimaryGroupName(offlinePlayer, DEFAULT_GROUP_NAME);
    }

    public static String getPrimaryGroupName(OfflinePlayer offlinePlayer, String defaultGroupName) {
        if (VaultHook.isHooked()) {
            return VaultHook.getPrimaryGroup(offlinePlayer);
        }
        return defaultGroupName;
    }
}
