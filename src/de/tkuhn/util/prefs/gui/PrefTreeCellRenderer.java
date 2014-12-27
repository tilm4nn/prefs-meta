/*
 * PrefsTreeCellRenderer.java
 *
 * Created on 14. Mai 2003, 19:10
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
import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import de.tkuhn.util.prefs.MetaInfo;
import de.tkuhn.util.prefs.MetaInfoProvider;

/**
 * A tree cell renderer that renders the preference's node's key and name
 * retrieved from meta information and resources.
 * 
 * @author Tilmann Kuhn
 */
public class PrefTreeCellRenderer extends DefaultTreeCellRenderer implements
		Serializable {

	private static final long serialVersionUID = 1L;

	/** Use Resource names for nodes */
	private boolean useResNames = true;

	/** Creates a new instance of PrefsTreeCellRenderer */
	public PrefTreeCellRenderer(boolean useResNames) {
		this.useResNames = useResNames;
	}

	/**
	 * Sets the value of the current tree cell to <code>value</code>. If
	 * <code>selected</code> is true, the cell will be drawn as if selected. If
	 * <code>expanded</code> is true the node is currently expanded and if
	 * <code>leaf</code> is true the node represets a leaf and if
	 * <code>hasFocus</code> is true the node currently has focus.
	 * <code>tree</code> is the <code>JTree</code> the receiver is being
	 * configured for. Returns the <code>Component</code> that the renderer uses
	 * to draw the value.
	 * 
	 * @return the <code>Component</code> that the renderer uses to draw the
	 *         value
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		// This should always hold
		if (value instanceof Preferences) {

			Preferences prefs = (Preferences) value;

			// Usualy use the node name
			String name = prefs.name();

			String key;

			MetaInfoProvider metaInfoProvider = MetaInfoProvider.getInstance();

			// Are there meta informations?
			if (metaInfoProvider != null) {

				MetaInfo info = metaInfoProvider.getMetaInfo(prefs);

				// Retrieve a key for Resources or a name
				key = info.getNodeInfo(MetaInfo.KEY_NAME, null);

				ResourceBundle prefsTexts = metaInfoProvider
						.getResources(prefs);

				// Are there message resources?
				if (prefsTexts == null) {

					// No: Then use the retrieved name
					if (key != null)
						name = key;
				} else {

					String key2 = key != null ? key : prefs.absolutePath()
							+ "->" + MetaInfo.KEY_NAME;
					try {
						// try to obtain a message for the key
						name = prefsTexts.getString(key2);

					} catch (MissingResourceException e) {

						if (key == null) {
							name = prefs.name();
						} else {
							name = key;
						}
					}
				}
			}
			if (useResNames) {
				setText(name);
			} else {
				setText(prefs.name() + " (" + name + ")");
			}
		}

		return this;
	}

}
