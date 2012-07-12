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
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;
import java.util.HashMap;

/**
 * The applet stub used to feed params
 * 
 * @author Nikki
 *
 */
public class WebAppletStub implements AppletStub {
	
	/**
	 * The Applet this stub belongs to
	 */
	private Applet applet;
	
	/**
	 * The parameters parsed by WebAppletLoader
	 */
	private HashMap<String, String> parameters;

	/**
	 * The code base/document base
	 */
	private URL baseUrl;

	/**
	 * Construct a new WebAppletStub
	 * @param applet
	 * 			The loaded Applet
	 * @param baseUrl
	 * 			The base URL
	 * @param parameters
	 * 			Parameters for runtime
	 */
	public WebAppletStub(Applet applet, URL baseUrl, HashMap<String, String> parameters) {
		this.applet = applet;
		this.baseUrl = baseUrl;
		this.parameters = parameters;
	}

	@Override
	public boolean isActive() {
		return applet.isActive();
	}

	@Override
	public URL getDocumentBase() {
		return baseUrl;
	}

	@Override
	public URL getCodeBase() {
		return baseUrl;
	}

	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public AppletContext getAppletContext() {
		return null;
	}

	@Override
	public void appletResize(int width, int height) {
		
	}

}
