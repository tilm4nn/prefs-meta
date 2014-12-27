/*
 * PrefEditorPanel.java
 *
 * Created on 14. Mai 2003, 21:43
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

import de.tkuhn.util.prefs.gui.event.PrefSelectionEvent;
import de.tkuhn.util.prefs.gui.event.PrefSelectionListener;

/**
 * This is a panel containing a Preferences editor that can operate in different
 * modes and contains a Preferences node tree and a table for value editing. It
 * can be used to display, edit, develop and administer either normal
 * preferences or preferences enriched with meta information.
 * 
 * @author Tilmann Kuhn
 */
public class PrefEditorPanel extends JPanel implements PrefSelectionListener {

	private static final long serialVersionUID = 1L;

	/* Editor modes for normal preferences */
	public static final int BASIC_DEFAULT = 0;
	public static final int BASIC_ALLOW_DELETE = 1;
	public static final int BASIC_ADMIN = 2;
	public static final int BASIC_DEVELOPER = 3;

	/* Editor modes for preferences with meta information */
	public static final int META_DEFAULT = 100;
	public static final int META_ALLOW_DELETE = 101;
	public static final int META_ADMIN = 102;
	public static final int META_DEVELOPER = 103;

	/** Creates new form PrefEditorPanel */
	public PrefEditorPanel() {
		initComponents();

		prefTreePanel.addPrefSelectionListener(this);
	}

	/**
	 * Set the root Preferences node for display in tree and table.
	 * 
	 * @param prefs
	 *            the new root node
	 */
	public void setRoot(Preferences prefs) {
		prefTablePanel.setPrefs(prefs);
		prefTreePanel.setRoot(prefs);
	}

	/**
	 * Set the mode this editor will work in defined by PrefEditor. This must be
	 * set before setting the first root node.
	 * 
	 * @param mode
	 *            the mode to be used
	 */
	public void setMode(int mode) {
		prefTreePanel.setMode(mode);
		prefTablePanel.setMode(mode);
	}

	/**
	 * Set actions that should be rendered inside the panel.
	 * 
	 * @param actions
	 *            the actions that will be displayed
	 */
	public void setActions(Collection<Action> actions) {
		prefTablePanel.setActions(actions);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		jSplitPane = new javax.swing.JSplitPane();
		prefTreePanel = new de.tkuhn.util.prefs.gui.PrefTreePanel();
		prefTablePanel = new de.tkuhn.util.prefs.gui.PrefTablePanel();

		setLayout(new java.awt.BorderLayout());

		jSplitPane.setDividerLocation(200);
		prefTreePanel.setFont(new java.awt.Font("Dialog", 0, 11));
		prefTreePanel.setPreferredSize(new java.awt.Dimension(77, 363));
		jSplitPane.setLeftComponent(prefTreePanel);

		prefTablePanel.setFont(new java.awt.Font("Dialog", 0, 11));
		prefTablePanel.setPreferredSize(new java.awt.Dimension(550, 400));
		jSplitPane.setRightComponent(prefTablePanel);

		add(jSplitPane, java.awt.BorderLayout.CENTER);

	}// GEN-END:initComponents

	/**
	 * Recieves PrefSelectionEvents from the tree and forwards them to the
	 * table. You should not need to call this direktly.
	 * 
	 * @param pse
	 *            the PrefSelectionEvent to be forwarded
	 */
	public void valueChanged(PrefSelectionEvent pse) {
		prefTablePanel.setPrefs(pse.getPreferences());
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private de.tkuhn.util.prefs.gui.PrefTablePanel prefTablePanel;
	private javax.swing.JSplitPane jSplitPane;
	private de.tkuhn.util.prefs.gui.PrefTreePanel prefTreePanel;
	// End of variables declaration//GEN-END:variables

}
