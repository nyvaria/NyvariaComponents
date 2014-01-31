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
package net.nyvaria.component.wrapper.cmd;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public abstract class NyvariaCommand implements CommandExecutor {
	public static boolean hasCommandPermission(CommandSender sender, String permission) {
		if (sender instanceof Player) {
			return NyvariaCommand.hasCommandPermission((Player) sender, permission);
		} else if (sender instanceof ConsoleCommandSender) {
			return NyvariaCommand.hasCommandPermission((ConsoleCommandSender) sender, permission);
		}
		return false;
	}
	
	public static boolean hasCommandPermission(Player player, String permission) {
		if (!player.hasPermission(permission)) {
			player.sendMessage("Unknown command. Type \"help\"for help...");
			return false;
		}
		return true;
	}
	
	public static boolean hasCommandPermission(ConsoleCommandSender console, String permission) {
		if (!console.hasPermission(permission)) {
			console.sendMessage("Unknown command. Type \"help\"for help....");
			return false;
		}
		return true;
	}
}
