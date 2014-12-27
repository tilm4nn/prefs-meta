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

/**
 * This TableModel implementation allows users to edit Preferences with attched
 * MetaInfo. The MetaInfo is taken into account for type-checking and denial of
 * editing capabilities.
 * 
 * @author Tilmann Kuhn
 */
public class DeveloperPrefTableModel extends AdminPrefTableModel implements
		TableModel {

	private static final long serialVersionUID = 1L;
	protected static final String COL_NAME_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/DeveloperPrefTableModel")
			.getString("column.name.name");
	protected static final String COL_SDESC_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/DeveloperPrefTableModel")
			.getString("column.sdesc.name");
	protected static final String COL_LDESC_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/DeveloperPrefTableModel")
			.getString("column.ldesc.name");

	/**
	 * Creates a new instance of UserPrefTableModel
	 * 
	 * @param prefs
	 *            the preferences this TableModel will reflect.
	 */
	public DeveloperPrefTableModel(Preferences prefs) {
		super(prefs, true, false);
	}

	public int getColumnCount() {
		return 7;
	}

	public Class<?> getColumnClass(int column) {
		if (column >= 4)
			return PrefMapping.class;
		else
			return super.getColumnClass(column);
	}

	public String getColumnName(int column) {
		if (4 == column)
			return COL_NAME_NAME;
		if (5 == column)
			return COL_SDESC_NAME;
		if (6 == column)
			return COL_LDESC_NAME;
		return super.getColumnName(column);
	}

	public boolean isCellEditable(int row, int column) {
		return column != 0;
	}

	public void setValueAt(Object obj, int row, int column) {
		if (row < getRowCount() && row >= 0) {
			switch (column) {
			case 4:
				meta.setMappingInfo(getKeyForRow(row), MetaInfo.KEY_NAME,
						(String) obj);
				fireTableDataChanged();
				return;
			case 5:
				meta.setMappingInfo(getKeyForRow(row), MetaInfo.KEY_SHORTDESC,
						(String) obj);
				fireTableDataChanged();
				return;
			case 6:
				meta.setMappingInfo(getKeyForRow(row), MetaInfo.KEY_LONGDESC,
						(String) obj);
				fireTableDataChanged();
				return;
			}
		}
		super.setValueAt(obj, row, column);
	}

	public void registerCellRenderers(JTable table) {
		super.registerCellRenderers(table);
		table.getColumn(COL_NAME_NAME).setCellRenderer(
				new MappingMetaTableCellRenderer(MetaInfo.KEY_NAME));
		table.getColumn(COL_SDESC_NAME).setCellRenderer(
				new MappingMetaTableCellRenderer(MetaInfo.KEY_SHORTDESC));
		table.getColumn(COL_LDESC_NAME).setCellRenderer(
				new MappingMetaTableCellRenderer(MetaInfo.KEY_LONGDESC));
	}

	/**
	 * Set the CellEditor of the tree suiting to this model
	 * 
	 * @param table
	 *            the table to set the CellEditor for
	 */
	public void registerCellEditors(JTable table) {
		super.registerCellEditors(table);
		table.getColumn(COL_NAME_NAME).setCellEditor(
				new MappingMetaTableCellEditor(MetaInfo.KEY_NAME));
		table.getColumn(COL_SDESC_NAME).setCellEditor(
				new MappingMetaTableCellEditor(MetaInfo.KEY_SHORTDESC));
		table.getColumn(COL_LDESC_NAME).setCellEditor(
				new MappingMetaTableCellEditor(MetaInfo.KEY_LONGDESC));
	}

}
