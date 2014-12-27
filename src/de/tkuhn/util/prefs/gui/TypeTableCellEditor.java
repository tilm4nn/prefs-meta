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

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 * A cell editor for the type cell to select the preferences type using a combo
 * box
 * 
 * @author Tilmann Kuhn
 */
public class TypeTableCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;

	private static final String[] typeName = { "String", "int", "long",
			"double", "float", "boolean", "byte array" };

	/** Creates a new instance of PrefCellEditor */
	public TypeTableCellEditor() {
		super(new JComboBox(typeName));
	}

	@Override
	public Object getCellEditorValue() {
		return new Integer(
				((JComboBox) super.getComponent()).getSelectedIndex() + 1);
	}

}
