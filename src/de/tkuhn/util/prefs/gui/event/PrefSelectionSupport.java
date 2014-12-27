/*
 * PrefSelectionSupport.java
 *
 * Created on 14. Mai 2003, 22:34
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.prefs.Preferences;

/**
 * An Object helping to keep track of registered PrefSelectionListeners
 * 
 * @author Tilmann Kuhn
 */
public class PrefSelectionSupport implements PrefSelectionListener {

	private Collection<PrefSelectionListener> listeners;

	/** Creates a new instance of PrefSelectionSupport */
	public PrefSelectionSupport() {
		listeners = new HashSet<PrefSelectionListener>();
	}

	/**
	 * Adds a PrefSelectionListener to this support's list of listeners.
	 * 
	 * @param psl
	 *            the listener to be added
	 */
	public void addPrefSelectionListener(PrefSelectionListener psl) {
		listeners.add(psl);
	}

	/**
	 * Remove a PrefSelectionListener from this support't list of listeners.
	 * 
	 * @param psl
	 *            the listener to be removed
	 */
	public void removePrefSelectionListener(PrefSelectionListener psl) {
		listeners.remove(psl);
	}

	/**
	 * Issue a valueChanged at all registered PrefSelectionListeners.
	 * 
	 * @param pse
	 *            the PrefSelectionEvent to be transmitted.
	 */
	public void valueChanged(PrefSelectionEvent pse) {
		Iterator<PrefSelectionListener> iter = listeners.iterator();
		while (iter.hasNext())
			(iter.next()).valueChanged(pse);
	}

	/**
	 * Issue a valueChanged at all registered PrefSelectionListeners with a
	 * PrefSelectionEvent constructed with the given values.
	 * 
	 * @param source
	 *            the Object issuing the event
	 * @param prefs
	 *            the new Preferences node being selected
	 */
	public void fireValueChanged(Object source, Preferences prefs) {
		PrefSelectionEvent pse = new PrefSelectionEvent(source, prefs);
		valueChanged(pse);
	}

}
