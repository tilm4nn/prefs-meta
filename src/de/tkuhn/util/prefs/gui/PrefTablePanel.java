/*
 * PrefTabelPanel.java
 *
 * Created on 14. Mai 2003, 20:42
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
import javax.swing.JPanel;

import de.tkuhn.swing.actions.ActionUtils;

/**
 * A Panel containing a table showing informations about Preferences.
 * 
 * @author Tilmann Kuhn
 */
public class PrefTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/** The model used for the table */
	private PrefTableModel tableModel = null;

	/** The Preferences node displayed in the table */
	private Preferences prefs = null;

	/** Use developer mode */
	private boolean devel = false;

	/** Use admin mode */
	private boolean admin = false;

	/** Use mata informations mode */
	private boolean meta = false;

	/** allow delete of mappings */
	private boolean delete = false;

	/** allow creation of new mappings */
	private boolean allowNew = false;

	/** Actions that will be shown in this panel */
	private Collection<Action> actions = null;

	/** Creates new form PrefTabelPanel */
	public PrefTablePanel() {
		initComponents();
	}

	/**
	 * Set a new Preferences node to be displayed in the table.
	 * 
	 * @param prefs
	 *            the new node to be displayed
	 */
	public void setPrefs(Preferences prefs) {
		if (prefs != null) {
			if (this.prefs == null) {

				prefTable.setStatePreserving(false);

				// if necessary create a table model
				createTableModel(prefs);
				prefTable.setModel(tableModel);
				tableModel.registerCellRenderers(prefTable);
				tableModel.registerCellEditors(prefTable);
				// tableModel.addTableModelListener(this);
				// initialize actions
				prefTable.balanceColumns();
				initActions();

			} else if (this.prefs != prefs) {

				tableModel.setPrefs(prefs);

			}
			this.prefs = prefs;

		}
	}

	/** Initialize actions shown in this panel */
	private void initActions() {

		// Add all actinons of the model
		ActionUtils.addAll(toolBar, tableModel.getActions());

		// Add all actions of the actions property
		if (actions != null)
			ActionUtils.addAll(toolBar, actions);

		// Register the keybindings
		ActionUtils.registerAllKeys(prefTable, tableModel.getActions());
	}

	/**
	 * Create a table model for the given Preferences node.
	 * 
	 * @param pfers
	 *            the Preferences node to be displayed
	 */
	private void createTableModel(Preferences prefs) {

		if (meta) {
			if (devel)
				tableModel = new DeveloperPrefTableModel(prefs);
			else if (admin)
				tableModel = new AdminPrefTableModel(prefs);
			else
				tableModel = new MetaPrefTableModel(prefs, delete);
		} else {
			tableModel = new BasicPrefTableModel(prefs, delete, allowNew);
		}
	}

	/**
	 * Set the mode the table will work in defined by PrefEditor.
	 * 
	 * @param mode
	 *            the mode to be used
	 */
	public void setMode(int mode) {
		if (tableModel != null)
			throw new IllegalStateException(
					"Must set mode before preferences node!");
		switch (mode) {
		case PrefEditorPanel.META_DEFAULT:
			meta = true;
			delete = false;
			admin = false;
			allowNew = false;
			devel = false;
			break;
		case PrefEditorPanel.META_ALLOW_DELETE:
			meta = true;
			delete = true;
			admin = false;
			allowNew = false;
			devel = false;
			break;
		case PrefEditorPanel.META_DEVELOPER:
			devel = true;
			meta = true;
			delete = true;
			admin = true;
			allowNew = true;
			break;
		case PrefEditorPanel.META_ADMIN:
			meta = true;
			delete = true;
			admin = true;
			allowNew = false;
			devel = false;
			break;
		case PrefEditorPanel.BASIC_DEFAULT:
			meta = false;
			delete = false;
			admin = false;
			allowNew = false;
			devel = false;
			break;
		case PrefEditorPanel.BASIC_ALLOW_DELETE:
			meta = false;
			delete = true;
			admin = false;
			allowNew = false;
			devel = false;
			break;
		case PrefEditorPanel.BASIC_DEVELOPER:
			devel = true;
			meta = false;
			delete = true;
			admin = true;
			allowNew = true;
			break;
		case PrefEditorPanel.BASIC_ADMIN:
			meta = false;
			delete = true;
			admin = true;
			allowNew = false;
			devel = false;
			break;
		default:
			throw new IllegalArgumentException("No legal mode key given!");
		}
	}

	/**
	 * Setter for the property actions. These Actions will be shown in this
	 * panel.
	 * 
	 * @param actions
	 *            the new value for the property actions.
	 */
	public void setActions(Collection<Action> actions) {
		this.actions = actions;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		jScrollPane = new javax.swing.JScrollPane();
		prefTable = new de.tkuhn.swing.table.JUserFriendlyTable();
		toolBar = new javax.swing.JToolBar();

		setLayout(new java.awt.BorderLayout());

		prefTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		jScrollPane.setViewportView(prefTable);

		add(jScrollPane, java.awt.BorderLayout.CENTER);

		add(toolBar, java.awt.BorderLayout.SOUTH);

	}// GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private de.tkuhn.swing.table.JUserFriendlyTable prefTable;
	private javax.swing.JToolBar toolBar;
	private javax.swing.JScrollPane jScrollPane;
	// End of variables declaration//GEN-END:variables

}
