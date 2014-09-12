package webBoltOns.server.servletUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
@SuppressWarnings("deprecation")
/*
 * $Id: HttpMessage.java,v 1.1 2007/04/20 19:37:18 paujones2005 Exp $ $Name:  $
 *
 * Copyright  2004, 2005, 2006  www.jrivet.com
 * 
 *   @author P. Jones 
 * 	 @version 2.060719
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'jRivet Framework - Java Solutions for Enterprise Applications'.
 *
 * The Initial Developer of the Original Code is Paul Jones. Portions created by
 *  the Initial Developer are Copyright (C) 2004, 2005, 2006  by Paul Jones.
 *
 *  **All Rights Reserved **.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the "GNU LIBRARY GENERAL PUBLIC LICENSE"), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 *
 * http://www.jRivet.com/download/
 */


public class HttpMessage {
	Hashtable<String, String> headers = null;

	URL servlet = null;

	/**
	 * Constructs a new HttpMessage that can be used to communicate with the
	 * servlet at the specified URL.
	 * 
	 * @param servlet
	 *            the server resource (typically a servlet) with which to
	 *            communicate
	 */
	public HttpMessage(URL servlet) {
		this.servlet = servlet;
	}

	/**
	 * Performs a GET request to the servlet, with no query string.
	 * 
	 * @return an InputStream to read the response
	 * @exception IOException
	 *                if an I/O error occurs
	 */
	public InputStream sendGetMessage() throws IOException {
		return sendGetMessage(null);
	}

	/**
	 * Performs a GET request to the servlet, building a query string from the
	 * supplied properties list.
	 * 
	 * @param args
	 *            the properties list from which to build a query string
	 * @return an InputStream to read the response
	 * @exception IOException
	 *                if an I/O error occurs
	 */
	public InputStream sendGetMessage(Properties args) throws IOException {
		String argString = ""; // default

		if (args != null) {
			argString = "?" + toEncodedString(args);
		}
		URL url = new URL(servlet.toExternalForm() + argString);

		// Turn off caching
		URLConnection con = url.openConnection();
		con.setUseCaches(false);

		// Send headers
		sendHeaders(con);

		return con.getInputStream();
	}

	// Send the contents of the headers hashtable to the server
	private void sendHeaders(URLConnection con) {
		if (headers != null) {
			Enumeration enumer = headers.keys();
			while (enumer.hasMoreElements()) {
				String name = (String) enumer.nextElement();
				String value = (String) headers.get(name);
				con.setRequestProperty(name, value);
			}
		}
	}

	/**
	 * Performs a POST request to the servlet, with no query string.
	 * 
	 * @return an InputStream to read the response
	 * @exception IOException
	 *                if an I/O error occurs
	 */
	public InputStream sendPostMessage() throws IOException {
		return sendPostMessage(null);
	}

	/**
	 * Performs a POST request to the servlet, building post data from the
	 * supplied properties list.
	 * 
	 * @param args
	 *            the properties list from which to build the post data
	 * @return an InputStream to read the response
	 * @exception IOException
	 *                if an I/O error occurs
	 */
	public InputStream sendPostMessage(Properties args) throws IOException {
		String argString = ""; // default
		if (args != null) {
			argString = toEncodedString(args); // notice no "?"
		}

		URLConnection con = servlet.openConnection();

		// Prepare for both input and output
		con.setDoInput(true);
		con.setDoOutput(true);

		// Turn off caching
		con.setUseCaches(false);

		// Work around a Netscape bug
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		// Send headers
		sendHeaders(con);

		// Write the arguments as post data
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(argString);
		out.flush();
		out.close();

		return con.getInputStream();
	}

	/**
	 * Performs a POST request to the servlet, uploading a serialized object.
	 * <p>
	 * The servlet can receive the object in its <tt>doPost()</tt> method like
	 * this:
	 * 
	 * <pre>
	 * ObjectInputStream objin = new ObjectInputStream(req.getInputStream());Object obj = objin.readObject();
	 *   
	 *  
	 * </pre>
	 * 
	 * The type of the uploaded object can be determined through introspection.
	 * 
	 * @param obj
	 *            the serializable object to upload
	 * @return an InputStream to read the response
	 * @exception IOException
	 *                if an I/O error occurs
	 */
	public InputStream sendPostMessage(Serializable obj) throws IOException {
		URLConnection con = servlet.openConnection();

		// Prepare for both input and output
		con.setDoInput(true);
		con.setDoOutput(true);

		// Turn off caching
		con.setUseCaches(false);

		// Set the content type to be application/x-java-serialized-object
		con.setRequestProperty("Content-Type",
				"application/x-java-serialized-object");

		// Send headers
		sendHeaders(con);

		// Write the serialized object as post data
		ObjectOutputStream out = new ObjectOutputStream(con.getOutputStream());
		out.writeObject(obj);
		out.flush();
		out.close();

		return con.getInputStream();
	}

	/**
	 * Sets the authorization information for the request (using BASIC
	 * authentication via the HTTP Authorization header). The authorization
	 * persists across multiple requests.
	 * 
	 * @param name
	 *            the user name
	 * @param name
	 *            the user password
	 */
	public void setAuthorization(String name, String password) {
		String authorization = name + ":" + password;
		setHeader("Authorization", "Basic " + authorization);
	}

	/**
	 * Sets a request cookie with the given name and value. The cookie persists
	 * across multiple requests. The caller is responsible for ensuring there
	 * are no illegal characters in the name and value.
	 * 
	 * @param name
	 *            the header name
	 * @param value
	 *            the header value
	 */
	public void setCookie(String name, String value) {
		if (headers == null) {
			headers = new Hashtable<String, String>();
		}
		String existingCookies = (String) headers.get("Cookie");
		if (existingCookies == null) {
			setHeader("Cookie", name + "=" + value);
		} else {
			setHeader("Cookie", existingCookies + "; " + name + "=" + value);
		}
	}

	/**
	 * Sets a request header with the given name and value. The header persists
	 * across multiple requests. The caller is responsible for ensuring there
	 * are no illegal characters in the name and value.
	 * 
	 * @param name
	 *            the header name
	 * @param value
	 *            the header value
	 */
	public void setHeader(String name, String value) {
		if (headers == null) {
			headers = new Hashtable<String, String>();
		}
		headers.put(name, value);
	}

	/*
	 * Converts a properties list to a URL-encoded query string
	 */
	private String toEncodedString(Properties args) {
		StringBuffer buf = new StringBuffer();
		Enumeration names = args.propertyNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = args.getProperty(name);
			buf
					.append(URLEncoder.encode(name) + "="
							+ URLEncoder.encode(value));
			if (names.hasMoreElements()) {
				buf.append("&");
			}
		}
		return buf.toString();
	}
}