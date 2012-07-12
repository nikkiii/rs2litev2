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
package org.rs2lite.loader;

import java.applet.Applet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rs2lite.util.Utils;
import org.rs2lite.util.WebUtil;

/**
 * Loads an applet from a page, copying all parameters
 * 
 * @author Nikki
 *
 */
public class WebAppletLoader {
	
	/**
	 * The web page URL
	 */
	private URL url;
	
	/**
	 * The base URL
	 * - http://test.com/test/test becomes http://test.com/test/, etc
	 */
	private URL baseUrl;
	
	/**
	 * The jar file URL
	 */
	private URL archiveUrl;
	
	/**
	 * The main class name
	 */
	private String className;
	
	/**
	 * The parameters used to launch
	 */
	private HashMap<String, String> params = new HashMap<String, String>();
	
	/**
	 * Construct a new web applet loader
	 * @param url
	 * 			The main page URL
	 */
	public WebAppletLoader(URL url) {
		this.url = url;
	}
	
	/**
	 * Construct a new web applet loader with the URL being a string
	 * @param string
	 * 			The main page string to convert to a URL
	 * @throws MalformedURLException
	 * 			If the URL is invalid
	 */
	public WebAppletLoader(String string) throws MalformedURLException {
		this(new URL(string));
	}

	/**
	 * Load the applet with the following steps
	 * - Construct a base url (Described above)
	 * - Read the main applet page
	 * - Parse all <param> tags
	 * - Parse the archive file
	 * - Find the name of the main class and set it
	 * @throws IOException
	 */
	public void load() throws IOException {
		baseUrl = new URL(url.toString().substring(0, url.toString().lastIndexOf('/')));
		String contents = WebUtil.readPage(url);
		Pattern pattern = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");
		Matcher matcher = pattern.matcher(contents);
		while (matcher.find()) {
			String param_name = Utils.trim(matcher.group(1), '\"');
			String param_value = Utils.trim(matcher.group(2), '\"');
			params.put(param_name, param_value);
		}
		if(params.containsKey("haveie6")) {
			params.remove("haveie6");
		}
		pattern = Pattern.compile("archive=(.*?)\\.jar");
		matcher = pattern.matcher(contents);
		if(!matcher.find()) {
			throw new RuntimeException("Could not find the archive!");
		}
		archiveUrl = new URL(baseUrl + "/" + Utils.trim(matcher.group(1), '"') + ".jar");
		pattern = Pattern.compile("code=([\"a-zA-Z0-9\\.]+)");
		matcher = pattern.matcher(contents);
		if(!matcher.find()) {
			throw new RuntimeException("Could not find the main class!");
		}
		className = Utils.trim(matcher.group(1), '"');
		className = className.substring(0, className.lastIndexOf('.'));
	}
	
	/**
	 * Construct a new applet from the loaded parameters
	 * @return
	 * 		The newly constructed applet
	 * @throws AppletLoaderException
	 * 		If an exception occurred while loading
	 */
	public Applet newApplet() throws AppletLoaderException {
		if(archiveUrl == null) {
			throw new IllegalArgumentException("No applet url!");
		}
		try {
			URLClassLoader classLoader = new URLClassLoader(new URL[] {archiveUrl});
			Applet app = (Applet) classLoader.loadClass(className).newInstance();
			app.setStub(new WebAppletStub(app, baseUrl, params));
			app.setVisible(true);
			app.init();
			app.start();
			return app;
		} catch(Exception e) {
			throw new AppletLoaderException(e);
		}
	}
}
