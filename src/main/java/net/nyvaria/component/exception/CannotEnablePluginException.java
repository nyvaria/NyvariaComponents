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
package net.nyvaria.component.exception;

/**
 * @author Paul Thompson
 */
public class CannotEnablePluginException extends Exception {
    private static final long serialVersionUID = 1L;

    public CannotEnablePluginException() {

    }

    public CannotEnablePluginException(final Throwable cause) {
        super(cause);
    }

    public CannotEnablePluginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CannotEnablePluginException(final String message) {
        super(message);
    }
}
