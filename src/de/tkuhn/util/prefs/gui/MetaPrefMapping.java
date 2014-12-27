/*
 * PrefMapping.java
 *
 * Created on 27. Juli 2003, 21:45
 * 
 * The MIT License
 *
 * Copyright (C) 2003 
 * Tilmann Kuhn           Gildestr. 34
 * http://www.tkuhn.de    76149 Karlsruhe
 * prefsmeta@tkuhn.de     Germany
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.tkuhn.util.prefs.gui;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import de.tkuhn.util.prefs.MetaInfo;

/**
 * A mapping that maps preferences together with their meta information and
 * resources
 * 
 * @author Tilmann Kuhn
 */
public class MetaPrefMapping extends PrefMapping {

	private MetaInfo info;

	private ResourceBundle resources;

	/** Creates a new instance of PrefMapping */
	public MetaPrefMapping() {
	}

	public MetaPrefMapping(Preferences prefs, String key, MetaInfo info,
			ResourceBundle resources) {
		super(prefs, key);
		this.info = info;
		this.resources = resources;
	}

	public Object clone() {
		return new MetaPrefMapping(getPrefs(), getKey(), info, resources);
	}

	/**
	 * Getter for property info.
	 * 
	 * @return Value of property info.
	 */
	public MetaInfo getInfo() {
		return info;
	}

	/**
	 * Setter for property info.
	 * 
	 * @param info
	 *            New value of property info.
	 */
	public void setInfo(MetaInfo info) {
		this.info = info;
	}

	/**
	 * Getter for property resources.
	 * 
	 * @return Value of property resources.
	 */
	public ResourceBundle getResources() {
		return resources;
	}

	/**
	 * Setter for property resources.
	 * 
	 * @param resources
	 *            New value of property resources.
	 */
	public void setResources(ResourceBundle resources) {
		this.resources = resources;
	}

}
