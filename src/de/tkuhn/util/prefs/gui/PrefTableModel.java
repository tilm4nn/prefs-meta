/*
 * PrefTableModel.java
 *
 * Created on 14. Mai 2003, 20:55
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

import java.util.Collection;
import java.util.prefs.Preferences;

import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Extends the AbstractTableModel with an operation setPrefs to set the
 * Preferences instance this model reflects.
 * 
 * @author Tilmann Kuhn
 */
public abstract class PrefTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	/**
	 * Set a new References instance to be reflected by this TableModel
	 * 
	 * @param prefs
	 *            the new Preferences for this model.
	 */
	public abstract void setPrefs(Preferences prefs);

	/**
	 * Get the Preferences instance reflected by this TableModel
	 * 
	 * @return the preferences reflected by this Model
	 */
	public abstract Preferences getPrefs();

	/**
	 * Return a Collection of Actions that can be used to manipulate the Model
	 * 
	 * @return the Collection of Actions
	 */
	public abstract Collection<Action> getActions();

	/**
	 * Register the CellEditors of the tree suiting to this model
	 * 
	 * @param table
	 *            the table to set the CellEditor for
	 */
	public void registerCellEditors(JTable table) {
	}

	/**
	 * Register the CellRenderers at the tree suiting this models elements.
	 * 
	 * @param table
	 *            the table to set the Renderers for
	 */
	public void registerCellRenderers(JTable table) {
	}

}
