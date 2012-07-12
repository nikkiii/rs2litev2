/**
 * RS2Lite, the open source Runescape Launcher
 * Copyright (C) 2012 Nikki <nikki@nikkii.us>
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
package org.rs2lite.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A utility to read from web pages
 * 
 * @author Nikki
 *
 */
public class WebUtil {
	/**
	 * Read from a page
	 * @param url
	 * 			The URL to read from
	 * @return
	 * 			The contents, with lines separated by \n
	 * @throws IOException
	 * 			If an error occurred
	 */
	public static String readPage(URL url) throws IOException {
		return readFromInput(url.openStream());
	}
	
	/**
	 * Read the contents of an input stream
	 * @param input
	 * 			The input stream to read from
	 * @return
	 * 			The contents, with lines separated by \n
	 * @throws IOException
	 * 			If an error occurred while reading
	 */
	public static String readFromInput(InputStream input) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		try {
			while(true) {
				String line = reader.readLine();
				if(line == null) {
					break;
				}
				
				builder.append(line).append("\n");
			}
		} finally {
			reader.close();
		}
		return builder.toString();
	}
}
