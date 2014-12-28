/*
 * PrefsTreeModel.java
 *
 * Created on 14. Mai 2003, 17:51
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
import java.util.Collection;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.tkuhn.swing.actions.ActionManager;
import de.tkuhn.swing.tree.AbstractTreeModel;

/**
 * This is a <code>TreeModel</code> showing a tree of <code>Preferences</code>
 * nodes. It also allows insertion and deletion of node.
 * 
 * @author Tilmann Kuhn
 */
public class PrefTreeModel extends AbstractTreeModel implements TreeModel,
		ActionListener {

	/** Flag to turn on and off debug messages */
	private static final boolean debug = true;

	/* values used for the NEW_Node Action */
	private static final String NEW_NODE_COMMAND = "NEW_Node";
	private static final String NEW_NODE_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/PrefTreeModel").getString(
					"action.new.name");
	private static final String NEW_NODE_SDESC = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/PrefTreeModel").getString(
					"action.new.shortdesc");
	private static final KeyStroke NEW_NODE_KEY = KeyStroke.getKeyStroke(
			KeyEvent.VK_INSERT, 0);

	/* values used for the DEL_Node Action */
	private static final String DEL_NODE_COMMAND = "DEL_Node";
	private static final String DEL_NODE_NAME = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/PrefTreeModel").getString(
					"action.delete.name");
	private static final String DEL_NODE_SDESC = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/PrefTreeModel").getString(
					"action.delete.shortdesc");
	private static final KeyStroke DEL_NODE_KEY = KeyStroke.getKeyStroke(
			KeyEvent.VK_DELETE, 0);

	/** Error string for error on backing source */
	private static final String ERROR_BACKING = java.util.ResourceBundle
			.getBundle("de/tkuhn/util/prefs/gui/PrefTreeModel").getString(
					"error.backingstore");

	/** Root node of the tree to be displayed */
	private Preferences root;

	/** May Preferences be Modified? */
	private boolean modifyMode;

	/** Manager used to keep track of Actions */
	private ActionManager actionManager = new ActionManager();

	/**
	 * Creates a new instance of PrefsTreeModel.
	 * 
	 * @param prefs
	 *            the root node for tree display
	 * @param modifyMode
	 *            determines if the Tree may be modified
	 */
	public PrefTreeModel(Preferences prefs, boolean modifyMode) {
		super();
		setPrefs(prefs);
		this.modifyMode = modifyMode;

		// Initialize the Actions
		initActions();
	}

	/** Initialize the Actions that have impact on the Model */
	private void initActions() {
		if (modifyMode) {
			actionManager.addActionListener(this);

			Action action;
			action = actionManager.newAction(NEW_NODE_COMMAND);
			action.putValue(Action.ACCELERATOR_KEY, NEW_NODE_KEY);
			action.putValue(Action.NAME, NEW_NODE_NAME);
			action.putValue(Action.SHORT_DESCRIPTION, NEW_NODE_SDESC);

			action = actionManager.newAction(DEL_NODE_COMMAND);
			action.putValue(Action.ACCELERATOR_KEY, DEL_NODE_KEY);
			action.putValue(Action.NAME, DEL_NODE_NAME);
			action.putValue(Action.SHORT_DESCRIPTION, DEL_NODE_SDESC);
		}
	}

	/**
	 * Setter for the property prefs which is the root node of the tree display.
	 * 
	 * @param prefs
	 *            the new value for the property prefs.
	 */
	public void setPrefs(Preferences prefs) {
		if (root != prefs) {
			root = prefs;
			fireTreeStructureChanged(new TreeModelEvent(this,
					new TreePath(root)));
		}
	}

	/**
	 * Returns the child of <code>parent</code> at index <code>index</code> in
	 * the parent's child array. <code>parent</code> must be a node previously
	 * obtained from this data source. This should not return <code>null</code>
	 * if <code>index</code> is a valid index for <code>parent</code> (that is
	 * <code>index &gt;= 0 &amp;&amp;
	 * index &lt; getChildCount(parent</code>)).
	 * 
	 * @param parent
	 *            a node in the tree, obtained from this data source
	 * @return the child of <code>parent</code> at index <code>index</code>
	 */
	public Object getChild(Object parent, int index) {

		if (debug) {
			assert parent != null;
			assert parent instanceof Preferences;
		}

		try {
			// Return the indexth child node of the Preferences
			Preferences prefs = (Preferences) parent;
			String[] names = prefs.childrenNames();
			if (index < names.length) {

				if (debug)
					assert names[index] != null;

				return prefs.node(names[index]);
			}

		} catch (BackingStoreException e) {
			e.printStackTrace(System.err);
			return ERROR_BACKING;
		}
		return null;
	}

	/**
	 * Returns the number of children of <code>parent</code>. Returns 0 if the
	 * node is a leaf or if it has no children. <code>parent</code> must be a
	 * node previously obtained from this data source.
	 * 
	 * @param parent
	 *            a node in the tree, obtained from this data source
	 * @return the number of children of the node <code>parent</code>
	 */
	public int getChildCount(Object parent) {

		if (debug) {
			assert parent != null;
			assert (parent instanceof Preferences);
		}

		Preferences prefs = (Preferences) parent;
		try {
			return prefs.childrenNames().length;

		} catch (BackingStoreException e) {

			e.printStackTrace(System.err);
		}
		return 0;
	}

	/**
	 * Returns the index of child in parent. If <code>parent</code> is
	 * <code>null</code> or <code>child</code> is <code>null</code>, returns -1.
	 * 
	 * @param parent
	 *            a note in the tree, obtained from this data source
	 * @param child
	 *            the node we are interested in
	 * @return the index of the child in the parent, or -1 if either
	 *         <code>child</code> or <code>parent</code> are <code>null</code>
	 */
	public int getIndexOfChild(Object parent, Object child) {

		if (parent == null || child == null)
			return -1;

		try {
			// Just iterate through the child names and return the right index
			Preferences prefs = (Preferences) parent;
			String name;
			String[] names;
			name = ((Preferences) child).name();
			names = prefs.childrenNames();
			for (int i = 0; i < names.length; i++)
				if (name.equals(names[i]))
					return i;

		} catch (BackingStoreException e) {
			e.printStackTrace(System.err);
		}
		return -1;
	}

	/**
	 * Returns the root of the tree. Returns <code>null</code> only if the tree
	 * has no nodes.
	 * 
	 * @return the root of the tree
	 */
	public Object getRoot() {
		return root;
	}

	/**
	 * Gets invoked when any Action of this Model takes place. You should not
	 * have to call this yourself.
	 * 
	 * @param ae
	 *            the ActionEvent to be processed
	 */
	public void actionPerformed(ActionEvent ae) {

		JOptionPane.showMessageDialog(null, "Not implemented yet");
	}

	/**
	 * Get all Actions that have impact on the TreeModel.
	 * 
	 * @return a Collection containing the Actions
	 */
	public Collection<Action> getActions() {
		return actionManager.getActions();
	}

	/**
	 * Returns <code>true</code> if <code>node</code> is a leaf. It is possible
	 * for this method to return <code>false</code> even if <code>node</code>
	 * has no children. A directory in a file system, for example, may contain
	 * no files; the node representing the directory is not a leaf, but it also
	 * has no children.
	 * 
	 * @param node
	 *            a node in the tree, obtained from this data source
	 * @return true if <code>node</code> is a leaf
	 */
	public boolean isLeaf(Object node) {
		return false;
	}

}
