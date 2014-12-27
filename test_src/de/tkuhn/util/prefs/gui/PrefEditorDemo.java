/*
 * PrefEditorDemo.java
 *
 * Created on 28. Juli 2003, 19:27
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import de.tkuhn.util.prefs.MetaInfoProvider;
import de.tkuhn.util.prefs.PrefsMetaInfoProvider;

/**
 * This is the implementation of the PrefsMeta editor demo application.
 * 
 * @author tilmann
 * 
 */
public class PrefEditorDemo {

	private static final String PREFS_NODE_PATH = "/de/tkuhn/util/prefs/demo/prefs";
	private static final String PREFS_META_NODE_PATH = "/de/tkuhn/util/prefs/demo/meta";
	private static final String PREFS_META_BUNDLE_NAME = "de/tkuhn/util/prefs/gui/demoPrefsMeta";
	private static final String PREFS_XML = "de/tkuhn/util/prefs/gui/demoPrefs.xml";
	private static final String PREFS_META_XML = "de/tkuhn/util/prefs/gui/demoPrefsMeta.xml";

	private Preferences prefs;
	private PrefEditor editor;

	/**
	 * Initializes the preferences to be shown to the user
	 * 
	 * @throws Exception
	 */
	private void initPrefs() throws Exception {

		if (!Preferences.userRoot().nodeExists(PREFS_NODE_PATH)) {
			importFromXml(PREFS_XML);
		}

		prefs = Preferences.userRoot().node(PREFS_NODE_PATH);
	}

	/**
	 * Initializes the meta information of the preferences shown to the user
	 */
	private void initPrefsMeta() throws Exception {
		if (!Preferences.userRoot().nodeExists(PREFS_META_NODE_PATH)) {
			importFromXml(PREFS_META_XML);
		}

		Preferences prefsMeta = Preferences.userRoot().node(
				PREFS_META_NODE_PATH);

		ResourceBundle prefsMetaResources = ResourceBundle
				.getBundle(PREFS_META_BUNDLE_NAME);

		PrefsMetaInfoProvider prefsMetaInfoProvider = new PrefsMetaInfoProvider(
				prefsMeta, prefsMetaResources);

		MetaInfoProvider.init(prefsMetaInfoProvider);
	}

	/**
	 * Imports preferences from a system resource xml file
	 * 
	 * @param systemResourceName
	 *            the name of the system resource
	 */
	private static void importFromXml(String systemResourceName)
			throws FileNotFoundException, IOException,
			InvalidPreferencesFormatException {
		InputStream in = ClassLoader
				.getSystemResourceAsStream(systemResourceName);
		try {
			Preferences.importPreferences(in);
		} finally {
			in.close();
		}
	}

	/**
	 * Sets the look and feel of swing to the system look and feel if possible
	 */
	private void initLNF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Use standard LNF
		}
	}

	/**
	 * Shows a welcome dialog with message and version number retrieved from the
	 * preferences
	 */
	private void showWelcomeDialog() {
		String welcomeMessage = prefs.get("WelcomeText",
				":-( Here should have been a welcome message.");
		float version = prefs.getFloat("Version", 0);
		welcomeMessage = String.format(welcomeMessage, version);
		JOptionPane.showMessageDialog(editor, welcomeMessage);
	}

	/**
	 * Starts the demo application showing a preferences editor in the given
	 * mode
	 * 
	 * @param editorMode
	 *            the mode to open the editor with
	 */
	public void showDemoPrefEditor(int editorMode) throws Exception {
		initPrefs();
		initPrefsMeta();
		initLNF();
		editor = new PrefEditor(prefs, editorMode);
		editor.setVisible(true);
		showWelcomeDialog();
	}

}
