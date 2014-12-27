/*
 * PrefEditor.java
 *
 * Created on 29. Mai 2003, 19:17
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
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * This JFrame implements a preferences editor based on the PrefEditorPanel
 * 
 * @author Tilmann Kuhn
 */
public class PrefEditor extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	// private static final String CLOSE_COMMAND = "CLOSE";
	// private static final String CLOSE_NAME = java.util.ResourceBundle
	// .getBundle("de/tkuhn/util/prefs/gui/PrefEditor").getString(
	// "action.close.name");
	// private static final String CLOSE_SDESC = java.util.ResourceBundle
	// .getBundle("de/tkuhn/util/prefs/gui/PrefEditor").getString(
	// "action.close.shortdesc");

	private int mode = PrefEditorPanel.BASIC_DEFAULT;

	// private ActionManager actionManager = new ActionManager();

	/** Creates new form PrefEditor */
	public PrefEditor() {
		initComponents();
		initActions();
	}

	public PrefEditor(Preferences root) {
		this();
		setRoot(root);
	}

	public PrefEditor(Preferences root, int mode) {
		this();
		setMode(mode);
		setRoot(root);
	}

	private void initActions() {
		// actionManager.addActionListener(this);
		//
		// Action action;
		// action = actionManager.newAction(CLOSE_COMMAND);
		// action.putValue(Action.NAME, CLOSE_NAME);
		// action.putValue(Action.SHORT_DESCRIPTION, CLOSE_SDESC);
		//
		// prefEditorPanel.setActions(actionManager.getActions());
	}

	public void setRoot(Preferences root) {
		prefEditorPanel.setMode(mode);
		prefEditorPanel.setRoot(root);
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		prefEditorPanel = new de.tkuhn.util.prefs.gui.PrefEditorPanel();

		setTitle(java.util.ResourceBundle.getBundle(
				"de/tkuhn/util/prefs/gui/PrefEditor").getString("window.title"));
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm(evt);
			}
		});

		prefEditorPanel.setFont(new java.awt.Font("Dialog", 0, 11));
		prefEditorPanel.setMinimumSize(new java.awt.Dimension(103, 54));
		prefEditorPanel.setPreferredSize(new java.awt.Dimension(1000, 500));
		getContentPane().add(prefEditorPanel, java.awt.BorderLayout.CENTER);

		pack();
	}// GEN-END:initComponents

	/** Exit the Application */
	private void exitForm(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_exitForm
		dispose();
	}// GEN-LAST:event_exitForm

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Use standard LNF
		}
		new PrefEditor(Preferences.userRoot(), PrefEditorPanel.META_DEVELOPER)
				.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// if (CLOSE_COMMAND.equals(ae.getActionCommand())) {
		// dispose();
		// }
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private de.tkuhn.util.prefs.gui.PrefEditorPanel prefEditorPanel;
	// End of variables declaration//GEN-END:variables

}