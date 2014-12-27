/*
 * MetaInfoProviderImpl.java
 *
 * Created on 22. Mai 2003, 20:40
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

package de.tkuhn.util.prefs;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * A MetaInfoProvider for the PrefsMetaInfo implementation of MetaInfo. This
 * implementation uses Preferences to store meta information in. The nodes used
 * are selected in the subtrees under the nodes given to the constructor by
 * evaluating a Preference's absolute path.
 * 
 * @author Tilmann Kuhn
 */
public class PrefsMetaInfoProvider extends MetaInfoProvider {

	/* PrefsMeta nodes are computed relative to these root nodes */
	private Preferences sysRoot = null;
	private Preferences userRoot = null;

	/* Soft-caches for the PrefsMetaInfo objects */
	private Map<String, SoftReference<PrefsMetaInfo>> userCache = new HashMap<String, SoftReference<PrefsMetaInfo>>();
	private Map<String, SoftReference<PrefsMetaInfo>> sysCache = new HashMap<String, SoftReference<PrefsMetaInfo>>();

	/* Resources according to the prefs */
	private ResourceBundle sysResources = null;
	private ResourceBundle userResources = null;

	/**
	 * Creates a new instance of PrefsMetaInfoProvider
	 * 
	 * @param sysRoot
	 *            the root node to store system meta informations in.
	 * @param userRoot
	 *            the root node to store user meta informations in.
	 */
	public PrefsMetaInfoProvider(Preferences sysRoot, Preferences userRoot) {
		this.sysRoot = sysRoot;
		this.userRoot = userRoot;
	}

	/**
	 * Creates a new instance of PrefsMetaInfoProvider
	 * 
	 * @param sysRoot
	 *            the root node to store system meta informations in.
	 * @param userRoot
	 *            the root node to store user meta informations in.
	 * @param sysResources
	 *            Resources according to the System Preferences.
	 * @param userResources
	 *            Resources according to the User Preferences.
	 */
	public PrefsMetaInfoProvider(Preferences sysRoot, Preferences userRoot,
			ResourceBundle sysResources, ResourceBundle userResources) {
		this.sysRoot = sysRoot;
		this.userRoot = userRoot;
		setUserResources(userResources);
		setSysResources(sysResources);
	}

	/**
	 * Creates a new instance of PrefsMetaInfoProvider
	 * 
	 * @param userRoot
	 *            the root node to store user meta informations in.
	 * @param userResources
	 *            Resources according to the User Preferences.
	 */
	public PrefsMetaInfoProvider(Preferences userRoot,
			ResourceBundle userResources) {
		this.userRoot = userRoot;
		setUserResources(userResources);
	}

	/**
	 * Return the MetaInfo object corresponding to the given Preferences.
	 * 
	 * @param prefs
	 *            the Preferences to retrieve MetaInformations for.
	 * @return the corresponding MetaInfo
	 */
	public MetaInfo getMetaInfo(Preferences prefs) {

		if (prefs == null)
			throw new IllegalArgumentException(
					"null is not a lega Preferences object!");
		// Cut off the slash
		String path = prefs.absolutePath().substring(1);

		// Select the right components
		Map<String, SoftReference<PrefsMetaInfo>> cache;
		Preferences root;
		if (prefs.isUserNode()) {
			cache = userCache;
			root = userRoot;
			if (root == null)
				throw new IllegalArgumentException(
						"No meta informations can be retrieved for user nodes!");
		} else {
			cache = sysCache;
			root = sysRoot;
			if (root == null)
				throw new IllegalArgumentException(
						"No meta informations can be retrieved for system nodes!");
		}

		// Check the cache
		SoftReference<PrefsMetaInfo> ref = cache.get(path);
		PrefsMetaInfo info = ref != null ? ref.get() : null;
		if (info != null)
			return info;

		// Create new instance
		info = new PrefsMetaInfo(root.node(path));
		cache.put(path, new SoftReference<PrefsMetaInfo>(info));

		return info;
	}

	/**
	 * Return the Resource (eg internationalized Strings) that should be used
	 * together with the prefs. In this Resources may be stored the Strings for
	 * the keys present in MetaInfo. May return null if there are no Resources
	 * attached.
	 * 
	 * @param prefs
	 *            the Preferences to return Resources for.
	 * @return the ResourceBundle.
	 */
	public ResourceBundle getResources(Preferences prefs) {
		if (prefs.isUserNode())
			return userResources;
		else
			return sysResources;
	}

	public String toString() {
		return "PrefsMetaInfoProvicer with\nUserRoot: " + userRoot.toString()
				+ "\nSystemRoot: " + sysRoot.toString() + "\n";
	}

	/**
	 * Getter for property sysResources.
	 * 
	 * @return Value of property sysResources.
	 */
	public ResourceBundle getSysResources() {
		return sysResources;
	}

	/**
	 * Setter for property sysResources.
	 * 
	 * @param sysResources
	 *            New value of property sysResources.
	 */
	public void setSysResources(ResourceBundle sysResources) {
		this.sysResources = sysResources;
	}

	/**
	 * Getter for property userResources.
	 * 
	 * @return Value of property userResources.
	 */
	public ResourceBundle getUserResources() {
		return userResources;
	}

	/**
	 * Setter for property userResources.
	 * 
	 * @param userResources
	 *            New value of property userResources.
	 */
	public void setUserResources(ResourceBundle userResources) {
		this.userResources = userResources;
	}

}
