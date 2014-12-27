/*
 * BasicPrefTableModel.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import de.tkuhn.swing.actions.ActionManager;

/**
 * This TableModel implementation provides editing capabilities for Preferences
 * objects.
 * 
 * @author Tilmann Kuhn
 */
public class BasicPrefTableModel extends PrefTableModel implements TableModel,
		ActionListener {

	private static final long serialVersionUID = 1L;

	protected static final String NEW_PREF_COMMAND = "NEW_Pref";
	protected static final String NEW_PREF_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/BasicPrefTableModel")
			.getString("action.new.name");
	protected static final String NEW_PREF_SDESC = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/BasicPrefTableModel")
			.getString("action.new.shortdesc");
	protected static final KeyStroke NEW_PREF_KEY = KeyStroke.getKeyStroke(
			KeyEvent.VK_INSERT, 0);
	protected static final String DEL_PREF_COMMAND = "DEL_Pref";
	protected static final String DEL_PREF_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/BasicPrefTableModel")
			.getString("action.delete.name");
	protected static final String DEL_PREF_SDESC = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/BasicPrefTableModel")
			.getString("action.delete.shortdesc");
	protected static final KeyStroke DEL_PREF_KEY = KeyStroke.getKeyStroke(
			KeyEvent.VK_DELETE, 0);

	protected static final String COL_PROPERTY_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/BasicPrefTableModel")
			.getString("column.property.name");
	protected static final String COL_VALUE_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/BasicPrefTableModel")
			.getString("column.value.name");

	protected static final String REQUEST_MAPPING_KEY = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/BasicPrefTableModel")
			.getString("request.mapping.key");

	protected Preferences prefs = null;
	protected boolean allowDelete = false;
	protected boolean allowNew = false;

	protected ActionManager actionManager = new ActionManager();

	protected List<PrefMapping> mappings;

	/**
	 * Creates a new instance of UserPrefTableModel
	 * 
	 * @param prefs
	 *            the Preferences reflected by this TableModel
	 * @param allowDelete
	 *            is it allowed to delete prefs?
	 * @param allowNew
	 *            is it allowed to create new prefs?
	 */
	public BasicPrefTableModel(Preferences prefs, boolean allowDelete,
			boolean allowNew) {
		this.allowDelete = allowDelete;
		this.allowNew = allowNew;
		setPrefs(prefs);
		initActions();
	}

	protected void initActions() {
		actionManager.addActionListener(this);
		Action action;
		if (allowNew) {
			action = actionManager.newAction(NEW_PREF_COMMAND);
			action.putValue(Action.ACCELERATOR_KEY, NEW_PREF_KEY);
			action.putValue(Action.NAME, NEW_PREF_NAME);
			action.putValue(Action.SHORT_DESCRIPTION, NEW_PREF_SDESC);
		}
		if (allowDelete) {
			action = actionManager.newAction(DEL_PREF_COMMAND);
			action.putValue(Action.ACCELERATOR_KEY, DEL_PREF_KEY);
			action.putValue(Action.NAME, DEL_PREF_NAME);
			action.putValue(Action.SHORT_DESCRIPTION, DEL_PREF_SDESC);
		}
	}

	/**
	 * Set a new References instnce to be reflected by this TableModel
	 * 
	 * @param prefs
	 *            the new Preferences for this model.
	 */
	public void setPrefs(Preferences prefs) {
		if (mappings != null)
			fireTableRowsDeleted(0, mappings.size() - 1);
		this.prefs = prefs;
		try {
			String[] key = prefs.keys();
			mappings = new ArrayList<PrefMapping>(key.length);
			for (int i = 0; i < key.length; i++) {
				mappings.add(new PrefMapping(prefs, key[i]));
			}
		} catch (BackingStoreException e) {
			IllegalArgumentException ile = new IllegalArgumentException(
					"Could not retrieve keys!");
			ile.initCause(e);
			throw ile;
		}
		fireTableRowsInserted(0, mappings.size() - 1);
	}

	public Class<?> getColumnClass(int param) {
		return PrefMapping.class;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnName(int param) {
		switch (param) {
		case 0:
			return COL_PROPERTY_NAME;
		case 1:
			return COL_VALUE_NAME;
		}
		return null;
	}

	public int getRowCount() {
		return mappings.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		if (rowIndex >= getRowCount() || rowIndex < 0
				|| columnIndex >= getColumnCount() || columnIndex < 0)
			return null;

		return mappings.get(rowIndex);
	}

	public boolean isCellEditable(int row, int column) {
		return column == 1;
	}

	public void setValueAt(Object obj, int row, int column) {
		if (column == 1 && row < getRowCount() && row >= 0) {
			prefs.put(getKeyForRow(row), (String) obj);
			fireTableDataChanged();
		}
	}

	/**
	 * Return a Collection of Actions that can be used to manipulate the Model
	 * 
	 * @return the Collection of Actions
	 */
	public Collection<Action> getActions() {
		return actionManager.getActions();
	}

	public void actionPerformed(ActionEvent ae) {
		if (NEW_PREF_COMMAND.equals(ae.getActionCommand())) {
			String name = JOptionPane.showInputDialog(REQUEST_MAPPING_KEY);
			if (name != null) {
				prefs.put(name, "");
				setPrefs(prefs);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Not implemented yet");
		}
	}

	/**
	 * Get the Preferences instance reflected by this TableModel
	 * 
	 * @return the preferences reflected by this Model
	 */
	public Preferences getPrefs() {
		return prefs;
	}

	public void registerCellRenderers(JTable table) {
		table.getColumn(COL_PROPERTY_NAME).setCellRenderer(
				new KeyTableCellRenderer());
		table.getColumn(COL_VALUE_NAME).setCellRenderer(
				new ValueTableCellRenderer());
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

	protected String getKeyForRow(int row) {
		return ((PrefMapping) mappings.get(row)).getKey();
	}

}
