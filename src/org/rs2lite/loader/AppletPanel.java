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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Represents a JPanel that allows us to show a nice loading screen until we have an applet object
 * 
 * @author Nikki
 *
 */
@SuppressWarnings("serial")
public class AppletPanel extends JPanel {
	
	/**
	 * The text to show while loading..
	 */
	private static final String LOADING_TEXT = "Loading, please wait...";
	
	/**
	 * The RS2 Applet
	 */
	private Applet applet;
	
	/**
	 * Create a new Rs2Panel that will contain the applet
	 */
	public AppletPanel() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(765, 503));
	}
	
	/**
	 * Paint the overlay until the applet is added
	 */
	public void paintOverlay() {
		Graphics g = getGraphics();
		if(g == null)
			return;
		if(applet == null || !applet.isShowing()) {
			FontMetrics metrics = g.getFontMetrics();
			
			g.setColor(Color.WHITE);
			
			g.drawString(LOADING_TEXT, (this.getWidth()/2) - (metrics.stringWidth(LOADING_TEXT)/2), this.getHeight()/2);
		}
	}
	
	/**
	 * Set the applet
	 * @param applet
	 * 			
	 */
	public void setApplet(Applet applet) {
		this.applet = applet;
	}
	
	/**
	 * Check if this panel has a valid applet
	 * @return
	 * 		True if applet != null and is showing
	 */
	public boolean hasApplet() {
		return applet != null && applet.isShowing();
	}
}
