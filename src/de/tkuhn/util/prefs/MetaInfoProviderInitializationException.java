/*
 * MetaInfoProviderInitializationException.java
 *
 * Created on 22. Mai 2003, 20:14
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

/**
 * This Exception is thrown if there is a problem during initialization of
 * MetaInfoProvider
 * 
 * @author Tilmann Kuhn
 */
public class MetaInfoProviderInitializationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new MetaInfoProviderInitializationException
	 */
	public MetaInfoProviderInitializationException() {
	}

	/**
	 * Creates a new MetaInfoProviderInitializationException with an attached
	 * message.
	 * 
	 * @param msg
	 *            the message to be attached to the Exception
	 */
	public MetaInfoProviderInitializationException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new MetaInfoProviderInitializationException supplied with a
	 * message and a throwable responsible for this exception
	 * 
	 * @param msg
	 *            The details message for the Exception
	 * @param cause
	 *            The cause why this exception was thrown
	 */
	public MetaInfoProviderInitializationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
