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

import java.util.prefs.Preferences;

/**
 * A mapping that map preferences with their String key
 * 
 * @author Tilmann Kuhn
 */
public class PrefMapping {

	private Preferences prefs;

	private String key;

	/** Creates a new instance of PrefMapping */
	public PrefMapping() {
	}

	public PrefMapping(Preferences prefs, String key) {
		this.prefs = prefs;
		this.key = key;
	}

	/**
	 * Getter for property prefs.
	 * 
	 * @return Value of property prefs.
	 */
	public Preferences getPrefs() {
		return prefs;
	}

	/**
	 * Setter for property prefs.
	 * 
	 * @param prefs
	 *            New value of property prefs.
	 */
	public void setPrefs(Preferences prefs) {
		this.prefs = prefs;
	}

	/**
	 * Getter for property key.
	 * 
	 * @return Value of property key.
	 */
	public java.lang.String getKey() {
		return key;
	}

	/**
	 * Setter for property key.
	 * 
	 * @param key
	 *            New value of property key.
	 */
	public void setKey(java.lang.String key) {
		this.key = key;
	}

	public String toString() {
		return key;
	}

	public Object clone() {
		return new PrefMapping(prefs, key);
	}

}
