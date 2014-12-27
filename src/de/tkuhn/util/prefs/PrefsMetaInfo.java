/*
 * PrefsMetaInfo.java
 *
 * Created on 22. Mai 2003, 23:48
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

import java.util.prefs.Preferences;

/**
 * An implementation of MetaInfo using Preferences itself for storage of meta
 * information.
 * 
 * @author Tilmann Kuhn
 */
public class PrefsMetaInfo implements MetaInfo {

	private static final String NODE_PREFIX = "#NODE#";

	private Preferences meta;

	/**
	 * Creates a new instance of PrefsMetaInfo
	 * 
	 * @param metaNode
	 *            the Preferences node where meta information is stored.
	 */
	public PrefsMetaInfo(Preferences metaNode) {
		meta = metaNode;
	}

	/**
	 * Get the resource name of the node icon. This operation returns null if
	 * the name is not set or if the storage back-end is not reachable.
	 * 
	 * @return a String with the resource name or null
	 */
	/*
	 * public String getIconResource() { return meta.get(NODE_ICON_KEY,null); }
	 */

	/**
	 * Retrieve a piece of Information stored about the mapping identified by
	 * its key inside the node this MetaInfo describes mapped to the infoKey.
	 * This operation returns default if the mapping is not set or if the
	 * storage back-end is not reachable.
	 * 
	 * @param key
	 *            the key of the mapping data was stored about
	 * @param infoKey
	 *            the key the data was mapped to
	 * @param defaultInfo
	 *            the default to be returned on missing data
	 * @return the value of the mapping or the default
	 */
	public String getMappingInfo(String key, String infoKey, String defaultInfo) {
		return meta.get(key + "->" + infoKey, defaultInfo);
	}

	/**
	 * Retrieve a piece of Information stored about the node described by this
	 * MetaInfo under the given key. This operation returns default if the
	 * mapping is not set or if the storage back-end is not reachable.
	 * 
	 * @param infoKey
	 *            the key the data was mapped to
	 * @param defaultInfo
	 *            the default to be returned on missing data
	 * @return the value of the mapping or the default
	 */
	public String getNodeInfo(String infoKey, String defaultInfo) {
		return meta.get(NODE_PREFIX + "->" + infoKey, defaultInfo);
	}

	/**
	 * Get the type of the value mapped with the given key. This operation
	 * returns TYPE_undefined if the type is not set of if the storage back-end
	 * is not reachable.
	 * 
	 * @param key
	 *            the key of the mapping
	 * @return the type of the mapping as int
	 */
	public int getType(String key) {
		return meta.getInt(key + "->" + MetaInfo.KEY_TYPE,
				MetaInfo.TYPE_undefined);
	}

	/**
	 * Check if the value of the mapping with the given key is editable by users
	 * or by admins only. This operation returns false if the flag is not set or
	 * if the storage back-end is not reachable.
	 * 
	 * @param key
	 *            the key of the mapping
	 * @return true if users may edit, false otherwise
	 */
	public boolean isUserEditable(String key) {
		return meta.getBoolean(key + "->" + MetaInfo.KEY_USEREDIT, false);
	}

	/**
	 * Set the resource name of the node icon.
	 * 
	 * @param name
	 *            the name of the resource
	 */
	/*
	 * public void setIconResource(String name) { meta.put(NODE_ICON_KEY,name);
	 * }
	 */

	/**
	 * Store a piece of Information about the mapping identified by its key
	 * inside the node described by this MetaInfo. Map the data to the infoKey.
	 * 
	 * @param key
	 *            the key of the mapping to store data about
	 * @param infoKey
	 *            key to map the data with
	 * @param info
	 *            the data to be mapped
	 */
	public void setMappingInfo(String key, String infoKey, String info) {
		meta.put(key + "->" + infoKey, info);
	}

	/**
	 * Store a piece of Information about the node described by this MetaInfo
	 * under the given key.
	 * 
	 * @param infoKey
	 *            the key to map the data with
	 * @param info
	 *            the data to be mapped
	 */
	public void setNodeInfo(String infoKey, String info) {
		meta.put(NODE_PREFIX + "->" + infoKey, info);
	}

	/**
	 * Set the type of the value mapped with the given key. It is the callers
	 * due to check if the contents of the mapping obey this type!
	 * 
	 * @param key
	 *            the key of the mapping
	 * @param type
	 *            the new type of the mapping. Should be one of the predefined
	 *            types.
	 */
	public void setType(String key, int type) {
		meta.putInt(key + "->" + MetaInfo.KEY_TYPE, type);
	}

	/**
	 * Set if the value of the mapping with the given key should be editable by
	 * users or by admins only.
	 * 
	 * @param key
	 *            the key of the mapping
	 * @param editable
	 *            the new state: true: users may edit false: admins only
	 */
	public void setUserEditable(String key, boolean editable) {
		meta.putBoolean(key + "->" + MetaInfo.KEY_USEREDIT, editable);
	}

	public String toString() {
		return "PrefsMetaInfo located at: " + meta.toString();
	}

}
