/*
 * MetaPrefTableModel.java
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

import java.util.prefs.Preferences;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import de.tkuhn.util.prefs.MetaInfo;
import de.tkuhn.util.prefs.MetaInfoProvider;

/**
 * This TableModel implementation allows users to edit Preferences with attached
 * MetaInfo. The MetaInfo is taken into account for type-checking and denial of
 * editing capabilities.
 * 
 * @author Tilmann Kuhn
 */
public class MetaPrefTableModel extends BasicPrefTableModel implements
		TableModel {

	private static final long serialVersionUID = 1L;

	protected static final String COL_TYPE_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/MetaPrefTableModel").getString(
					"column.type.name");

	protected MetaInfo meta;

	protected boolean useResNames = true;

	/**
	 * Creates a new instance of UserPrefTableModel
	 * 
	 * @param prefs
	 *            the preferences this TableModel will reflect.
	 * @param allowDelete
	 *            deletion of Mappings is allowed
	 */
	public MetaPrefTableModel(Preferences prefs, boolean allowDelete) {
		super(prefs, allowDelete, false);
	}

	/**
	 * Creates a new instance of UserPrefTableModel
	 * 
	 * @param prefs
	 *            the preferences this TableModel will reflect.
	 * @param allowDelete
	 *            deletion of Mappings is allowed
	 * @param allowNew
	 *            creation of new mappings is allowed
	 */
	public MetaPrefTableModel(Preferences prefs, boolean allowDelete,
			boolean allowNew) {
		super(prefs, allowDelete, allowNew);
	}

	/**
	 * Creates a new instance of UserPrefTableModel
	 * 
	 * @param prefs
	 *            the preferences this TableModel will reflect.
	 * @param allowDelete
	 *            deletion of Mappings is allowed
	 * @param allowNew
	 *            creation of new mappings is allowed
	 */
	public MetaPrefTableModel(Preferences prefs, boolean allowDelete,
			boolean allowNew, boolean useResNames) {
		super(prefs, allowDelete, allowNew);
		this.useResNames = useResNames;
	}

	/**
	 * Set a new References instance to be reflected by this TableModel
	 * 
	 * @param prefs
	 *            the new Preferences for this model.
	 */
	public void setPrefs(Preferences prefs) {
		meta = MetaInfoProvider.getInstance().getMetaInfo(prefs);
		super.setPrefs(prefs);
	}

	public int getColumnCount() {
		return 3;
	}

	public String getColumnName(int column) {
		if (column == 2)
			return COL_TYPE_NAME;
		return super.getColumnName(column);
	}

	public boolean isCellEditable(int row, int column) {
		return column == 1 && meta.isUserEditable(getKeyForRow(row));
	}

	public void setValueAt(Object obj, int row, int column) {
		if (column == 1 && row < getRowCount() && row >= 0) {

			String key = getKeyForRow(row);
			if (meta.getType(key) == MetaInfo.TYPE_boolean) {

				prefs.putBoolean(key, ((Boolean) obj).booleanValue());
				fireTableDataChanged();

			} else
				super.setValueAt(obj, row, column);
		} else
			super.setValueAt(obj, row, column);
	}

	/**
	 * Set the CellEditor of the tree suiting to this model
	 * 
	 * @param table
	 *            the table to set the CellEditor for
	 */
	public void registerCellEditors(JTable table) {
		super.registerCellEditors(table);
		table.getColumn(COL_VALUE_NAME).setCellEditor(
				new ValueTableCellEditor());
	}

	public void registerCellRenderers(JTable table) {
		super.registerCellRenderers(table);
		table.getColumn(COL_PROPERTY_NAME).setCellRenderer(
				new KeyResTableCellRenderer(useResNames));
		table.getColumn(COL_TYPE_NAME).setCellRenderer(
				new TypeTableCellRenderer());
		table.getColumn(COL_VALUE_NAME).setCellRenderer(
				new ValueTypeTableCellRenderer());
	}

}
