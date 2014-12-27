/*
 * PrefCellEditor.java
 *
 * Created on 27. Mai 2003, 23:10
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
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableColumnModel;

import de.tkuhn.util.prefs.MetaInfo;
import de.tkuhn.util.prefs.MetaInfoProvider;

/**
 * A table cell editor for the preference's value
 * 
 * @author Tilmann Kuhn
 */
public class ValueTableCellEditor extends DefaultCellEditor implements
		CellEditorListener {

	private static final long serialVersionUID = 1L;

	private int type;

	DefaultCellEditor booleanEditor;

	/** Creates a new instance of PrefCellEditor */
	public ValueTableCellEditor() {
		super(new JTextField());
		booleanEditor = new DefaultCellEditor(new JCheckBox());
		booleanEditor.addCellEditorListener(this);
		JCheckBox checkBox = (JCheckBox) booleanEditor.getComponent();
		checkBox.setHorizontalAlignment(JCheckBox.CENTER);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		if (value instanceof PrefMapping) {

			PrefMapping mapping = (PrefMapping) value;
			String key = mapping.getKey();
			type = getType(mapping);

			if (type == MetaInfo.TYPE_undefined)
				return null;

			if (type == MetaInfo.TYPE_boolean) {

				Boolean val = new Boolean(mapping.getPrefs().getBoolean(key,
						false));

				return booleanEditor.getTableCellEditorComponent(table, val,
						isSelected, row, column);
			}

			String val = mapping.getPrefs().get(key, "");
			return super.getTableCellEditorComponent(table, val, isSelected,
					row, column);
		}
		throw new IllegalArgumentException("This is not a PrefMapping!");
	}

	public boolean stopCellEditing() {
		if (MetaInfo.TYPE_boolean == type) {
			return booleanEditor.stopCellEditing();
		}

		String value = (String) super.getCellEditorValue();
		try {
			switch (type) {
			case MetaInfo.TYPE_int:
				Integer.parseInt(value);
				break;
			case MetaInfo.TYPE_long:
				Long.parseLong(value);
				break;
			case MetaInfo.TYPE_float:
				Float.parseFloat(value);
				break;
			case MetaInfo.TYPE_double:
				Double.parseDouble(value);
				break;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return super.stopCellEditing();
	}

	public Object getCellEditorValue() {
		if (MetaInfo.TYPE_boolean == type) {
			return booleanEditor.getCellEditorValue();
		}
		return this.delegate.getCellEditorValue();
	}

	public void cancelEditing() {
		if (MetaInfo.TYPE_boolean == type) {
			booleanEditor.cancelCellEditing();
		} else {
			this.delegate.cancelCellEditing();
		}
	}

	public boolean isCellEditable(EventObject anEvent) {

		int type = getType(anEvent);

		if (type == MetaInfo.TYPE_boolean)
			return booleanEditor.isCellEditable(anEvent);

		return this.delegate.isCellEditable(anEvent);
	}

	public boolean shouldSelectCell(EventObject anEvent) {

		int type = getType(anEvent);

		if (type == MetaInfo.TYPE_boolean)
			return booleanEditor.shouldSelectCell(anEvent);

		return this.delegate.shouldSelectCell(anEvent);
	}

	private int getType(PrefMapping mapping) {

		String key = mapping.getKey();
		MetaInfoProvider metaInfoProvider = MetaInfoProvider.getInstance();

		// Is there meta information?
		if (metaInfoProvider != null) {

			MetaInfo info = metaInfoProvider.getMetaInfo(mapping.getPrefs());
			return info.getType(key);
		}
		throw new IllegalArgumentException("No MetaInfoProvider found!");
	}

	private int getType(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			MouseEvent ev = (MouseEvent) anEvent;
			JTable table = (JTable) anEvent.getSource();
			TableColumnModel colModel = table.getColumnModel();
			int col = colModel.getColumnIndexAtX(ev.getX());
			int row = table.rowAtPoint(ev.getPoint());
			PrefMapping mapping = (PrefMapping) table.getValueAt(row, col);

			return getType(mapping);
		}
		return MetaInfo.TYPE_undefined;
	}

	public void editingCanceled(ChangeEvent ce) {
		fireEditingCanceled();
	}

	public void editingStopped(ChangeEvent ce) {
		fireEditingStopped();
	}

}
