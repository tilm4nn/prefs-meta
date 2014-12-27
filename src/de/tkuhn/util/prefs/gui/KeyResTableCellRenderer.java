/*
 * KeyCellRenderer.java
 *
 * Created on 27. Juli 2003, 21:04
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

import java.awt.Component;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.tkuhn.util.prefs.MetaInfo;
import de.tkuhn.util.prefs.MetaInfoProvider;

/**
 * A table cell renderer that renders the preference's key next to the property
 * name obtained from resource files or meta information.
 * 
 * @author Tilmann Kuhn
 */
public class KeyResTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	boolean useResNames;

	/** Creates a new instance of KeyCellRenderer */
	public KeyResTableCellRenderer(boolean useResNames) {
		this.useResNames = useResNames;
	}

	/**
	 * 
	 * Returns the default table cell renderer.
	 * 
	 * @param table
	 *            the <code>JTable</code>
	 * @param value
	 *            the value to assign to the cell at <code>[row, column]</code>
	 * @param isSelected
	 *            true if cell is selected
	 * @param hasFocus
	 *            true if cell has focus
	 * @param row
	 *            the row of the cell to render
	 * @param column
	 *            the column of the cell to render
	 * @return the default table cell renderer
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		// This should always hold
		if (value instanceof PrefMapping) {

			PrefMapping mapping = (PrefMapping) value;

			// Usually use the mapping name
			String name = mapping.getKey();

			String key;

			MetaInfoProvider metaInfoProvider = MetaInfoProvider.getInstance();

			// Are there meta informations?
			if (metaInfoProvider != null) {

				MetaInfo info = metaInfoProvider
						.getMetaInfo(mapping.getPrefs());

				// Retrieve a key for Resources or a name
				key = info.getMappingInfo(name, MetaInfo.KEY_NAME, null);

				ResourceBundle prefsTexts = metaInfoProvider
						.getResources(mapping.getPrefs());

				// Are there message resources?
				if (prefsTexts == null) {

					// No: Then use the retrieved name
					if (key != null)
						name = key;
				} else {

					String key2 = key != null ? key : mapping.getPrefs()
							.absolutePath()
							+ "/"
							+ name
							+ "->"
							+ MetaInfo.KEY_NAME;
					try {
						// try to obtain a message for the key
						name = prefsTexts.getString(key2);

					} catch (MissingResourceException e) {

						if (key == null) {
							name = mapping.getKey();
						} else {
							name = key;
						}
					}
				}
			}
			if (useResNames) {
				setText(name);
			} else {
				setText(mapping.getKey() + " (" + name + ")");
			}
		} else
			throw new IllegalArgumentException("This is not a PrefMapping!");

		return this;
	}

}
