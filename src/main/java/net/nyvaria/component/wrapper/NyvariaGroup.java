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

import net.nyvaria.component.hook.ZPermissionsHook;
import net.nyvaria.component.util.StringUtils;

/**
 * @author Paul Thompson
 */
public class NyvariaGroup {
    private String name;
    private String label;
    private String prefix;
    private String suffix;

    public NyvariaGroup(String name) {
        this.name = name;
        this.label = NyvariaGroup.getLabel(name);
        this.prefix = NyvariaGroup.getPrefix(name);
        this.suffix = NyvariaGroup.getSuffix(name);
    }

    /**
     * Static Methods
     */

    public static String getLabel(String name) {
        return StringUtils.splitCamelCase(name);
    }

    public static String getPrefix(String name) {
        if (ZPermissionsHook.is_hooked()) {
            return ZPermissionsHook.getGroupPrefix(name);
        }
        return "";
    }

    public static String getSuffix(String name) {
        if (ZPermissionsHook.is_hooked()) {
            return ZPermissionsHook.getGroupSuffix(name);
        }
        return "";
    }

    public static String getWrappedName(String name) {
        return getPrefix(name) + name + getSuffix(name);
    }

    public static String getWrappedLabel(String name) {
        return getPrefix(name) + getLabel(name) + getSuffix(name);
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
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getWrappedName() {
        return getPrefix() + getName() + getSuffix();
    }

    public String getWrappedLabel() {
        return getPrefix() + getLabel() + getSuffix();
    }
}
