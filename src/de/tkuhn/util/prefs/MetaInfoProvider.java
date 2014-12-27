/*
 * MetaInfoProvider.java
 *
 * Created on 22. Mai 2003, 19:56
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

import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * This class describes Objects that can provide MetaInfo and Resources for
 * given Preferences. It also implements singleton semantics for creating
 * instances.
 * 
 * @author Tilmann Kuhn
 */
public abstract class MetaInfoProvider {

	/**
	 * Return the MetaInfo object corresponding to the given Preferences.
	 * 
	 * @param prefs
	 *            the Preferences to retrieve MetaInformations for.
	 * @return the corresponding MetaInfo
	 */
	public abstract MetaInfo getMetaInfo(Preferences prefs);

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
	public abstract ResourceBundle getResources(Preferences prefs);

	/** Holds the singleton instance */
	private static MetaInfoProvider provider = null;

	/**
	 * Returns the MetaInfoProvider. Or tries to get one by calling init(null)
	 * if none is present.
	 * 
	 * @return the MetaInfoProvider.
	 */
	public static MetaInfoProvider getInstance() {
		return provider;
	} // end getInstance

	/**
	 * Initializes the Singleton to be an Object of the given class-name and
	 * returns the instance. Does it's job only if not allready initialized.
	 * 
	 * @param className
	 *            the name of the class to be instantiated.
	 * @return the MetaInfoProvider.
	 * @throws MetaInfoProviderInitializationException
	 *             if there is a problem like class not found etc. Look at the
	 *             cause for details.
	 */
	public static MetaInfoProvider init(String className)
			throws MetaInfoProviderInitializationException {
		if (provider == null) {
			if (className == null) {
				throw new MetaInfoProviderInitializationException(
						"Null is not a legal class name!");
			}
			try {
				provider = (MetaInfoProvider) Thread.currentThread()
						.getContextClassLoader().loadClass(className)
						.newInstance();
			} catch (ClassNotFoundException e) {
				throw new MetaInfoProviderInitializationException(
						"Could not find MetaInfoProvider: " + className, e);
			} catch (InstantiationException e) {
				throw new MetaInfoProviderInitializationException(
						"Could not instantiate MetaInfoProvider: " + className,
						e);
			} catch (IllegalAccessException e) {
				throw new MetaInfoProviderInitializationException(
						"Could not access MetaInfoProvider: " + className, e);
			} catch (ClassCastException e) {
				throw new MetaInfoProviderInitializationException("Class "
						+ className + " is not of type MetaInfoProvider", e);
			}
		}
		return provider;
	} // end init

	/**
	 * Initializes the Singleton to be the given Object if not allready
	 * initialized.
	 * 
	 * @param provider
	 *            the initial singleton instance
	 */
	public static void init(MetaInfoProvider provider) {
		if (MetaInfoProvider.provider == null) {
			if (provider != null) {
				MetaInfoProvider.provider = provider;
			}
		}
	} // end init

}
