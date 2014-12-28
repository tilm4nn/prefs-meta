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

/**
 * This TableModel implementation allows users to edit Preferences with attached
 * MetaInfo. The MetaInfo is taken into account for type-checking and denial of
 * editing capabilities.
 * 
 * @author Tilmann Kuhn
 */
public class AdminPrefTableModel extends MetaPrefTableModel implements
		TableModel {

	private static final long serialVersionUID = 1L;

	protected static final String COL_EDIT_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/AdminPrefTableModel")
			.getString("column.edit.name");

	/**
	 * Creates a new instance of UserPrefTableModel
	 * 
	 * @param prefs
	 *            the preferences this TableModel will reflect.
	 */
	public AdminPrefTableModel(Preferences prefs) {
		super(prefs, true);
	}

	/**
	 * Creates a new instance of UserPrefTableModel
	 * 
	 * @param prefs
	 *            the preferences this TableModel will reflect.
	 * @param allowNew
	 *            creation of new Mappings is allowed
	 */
	public AdminPrefTableModel(Preferences prefs, boolean allowNew) {
		super(prefs, true, allowNew);
	}

	/**
	 * Creates a new instance of UserPrefTableModel
	 * 
	 * @param prefs
	 *            the preferences this TableModel will reflect.
	 * @param allowNew
	 *            creation of new Mappings is allowed
	 * @param useResNames
	 * 	          set to {@code true} if only the resource
	 * names should be rendered as captions
	 */
	public AdminPrefTableModel(Preferences prefs, boolean allowNew,
			boolean useResNames) {
		super(prefs, true, allowNew, useResNames);
	}

	public int getColumnCount() {
		return 4;
	}

	public Class<?> getColumnClass(int column) {
		if (column == 3)
			return Boolean.class;
		return super.getColumnClass(column);
	}

	public String getColumnName(int column) {
		if (column == 3)
			return COL_EDIT_NAME;
		return super.getColumnName(column);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= getRowCount() || rowIndex < 0)
			return null;
		if (columnIndex == 3)
			return new Boolean(meta.isUserEditable(getKeyForRow(rowIndex)));
		return super.getValueAt(rowIndex, columnIndex);
	}

	public boolean isCellEditable(int row, int column) {
		return column != 0;
	}

	public void setValueAt(Object obj, int row, int column) {
		if (column == 3 && row < getRowCount() && row >= 0) {
			meta.setUserEditable(getKeyForRow(row),
					((Boolean) obj).booleanValue());
			fireTableDataChanged();
		}
		if (column == 2 && row < getRowCount() && row >= 0) {
			meta.setType(getKeyForRow(row), ((Integer) obj).intValue());
			fireTableDataChanged();
		} else {
			super.setValueAt(obj, row, column);
		}
	}

	/**
	 * Set the CellEditor of the tree suiting to this model
	 * 
	 * @param table
	 *            the table to set the CellEditor for
	 */
	public void registerCellEditors(JTable table) {
		super.registerCellEditors(table);
		table.getColumn(COL_TYPE_NAME).setCellEditor(new TypeTableCellEditor());
	}

}
