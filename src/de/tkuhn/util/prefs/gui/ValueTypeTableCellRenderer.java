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

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import de.tkuhn.util.prefs.MetaInfo;
import de.tkuhn.util.prefs.MetaInfoProvider;

/**
 * A table cell renderer for the preference's value in meta preferences mode
 * 
 * @author Tilmann Kuhn
 */
public class ValueTypeTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	protected static final String ERROR_RETRIEVE = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/ValueTableCellRenderer")
			.getString("error.retrieve");

	private class BooleanRenderer extends JCheckBox implements
			TableCellRenderer {

		private static final long serialVersionUID = 1L;

		public BooleanRenderer() {
			super();
			setHorizontalAlignment(JLabel.CENTER);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}
			setSelected((value != null && ((Boolean) value).booleanValue()));
			return this;
		}
	}

	private TableCellRenderer booleanRenderer;

	/** Creates a new instance of KeyCellRenderer */
	public ValueTypeTableCellRenderer() {
		booleanRenderer = new BooleanRenderer();
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

		// This should always hold
		if (value instanceof PrefMapping) {

			PrefMapping mapping = (PrefMapping) value;

			// Usually use the mapping name
			String key = mapping.getKey();

			MetaInfoProvider metaInfoProvider = MetaInfoProvider.getInstance();

			// Are there meta informations?
			if (metaInfoProvider != null) {

				MetaInfo info = metaInfoProvider
						.getMetaInfo(mapping.getPrefs());

				if (info.getType(key) == MetaInfo.TYPE_boolean)
					return booleanRenderer.getTableCellRendererComponent(
							table,
							new Boolean(mapping.getPrefs().getBoolean(key,
									false)), isSelected, hasFocus, row, column);

				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				setText(mapping.getPrefs()
						.get(mapping.getKey(), ERROR_RETRIEVE));

				return this;

			}
			throw new IllegalArgumentException("No MetaInfoProvider found!");

		} else if (value instanceof Boolean) {
			return booleanRenderer.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
		}
		throw new IllegalArgumentException("This is not a PrefMapping!");

	}

}
