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

import java.io.IOException;
import java.util.logging.Level;

import net.nyvaria.component.wrapper.NyvariaPlugin;

import org.mcstats.Metrics;

/**
 * @author Paul Thompson
 *
 */
public class MetricsHook {
	private static NyvariaPlugin plugin  = null;
	private static Metrics       metrics = null;
	
	private static final String METRICS_URL_PREFIX = "http://mcstats.org/plugin/";
	private static       String METRICS_URL = null;
	
	private MetricsHook() {
		// Prevent instantiation
	}
	
	public static boolean initialize(NyvariaPlugin plugin) {
		MetricsHook.plugin = plugin;
		MetricsHook.METRICS_URL = METRICS_URL_PREFIX + plugin.getName();
		
		if (!MetricsHook.plugin.getConfig().getBoolean("use-metrics")) {
			MetricsHook.plugin.log(Level.INFO, "Skipping metrics");
		} else {
			try {
				MetricsHook.run();
				MetricsHook.plugin.log(String.format("Metrics started: %1$s", MetricsHook.METRICS_URL));
			} catch (IOException e) {
				MetricsHook.plugin.log(Level.WARNING, "Failed to start metrics");
				e.printStackTrace();
			}
		}
		
		return is_hooked();
	}
	
	public static boolean is_hooked() {
		return (metrics != null);
	}
	
	private static void run() throws IOException {
		metrics = new Metrics(plugin);
		metrics.start();
	}
}
