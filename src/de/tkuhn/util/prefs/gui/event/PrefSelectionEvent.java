/*
 * PrefSelectionEvent.java
 *
 * Created on 14. Mai 2003, 22:28
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

package de.tkuhn.util.prefs.gui.event;

import java.util.EventObject;
import java.util.prefs.Preferences;

/**
 * This event is issued if a new Preferences node is being selected.
 * 
 * @author Tilmann Kuhn
 */
public class PrefSelectionEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private Preferences prefs;

	/**
	 * Creates a new instance of PrefSelectionEvent
	 * 
	 * @param source
	 *            the object that issued this event
	 * @param selection
	 *            the Preferences that are selected
	 */
	public PrefSelectionEvent(Object source, Preferences selection) {
		super(source);
		prefs = selection;
	}

	/**
	 * Get the selected Preferences node.
	 * 
	 * @return the Preferences that are selected
	 */
	public Preferences getPreferences() {
		return prefs;
	}

}
