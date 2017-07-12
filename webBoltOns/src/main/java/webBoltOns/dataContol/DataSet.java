package webBoltOns.dataContol;


/*
 * $Id: DataSet.java,v 1.1 2007/04/20 19:37:22 paujones2005 Exp $ $Name:  $
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.text.MaskFormatter;

import webBoltOns.client.WindowItem;

@SuppressWarnings("deprecation")

public class DataSet extends Hashtable implements java.io.Serializable {

	private static final long serialVersionUID = 7023272412628711598L;
	

	
	public static int booleanToInt(boolean value) {
		if (value)
			return 1;
		else
			return 0;
	}

	/**
	 * <h2><code>checkBoolean</code></h2>
	 * 
	 * The value passed is tested for the <code>String</code> "true" or
	 * "false" - returns a boolean value of the string.
	 * 
	 * 
	 * @param String
	 *            value - the string to be tested
	 * @return the boolean value of the string text string
	 * 
	 */
	public static boolean checkBoolean(String value) {
		if (value == null || value.equals("") || value.equals("false")) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * <h2><code>checkDouble</code></h2>
	 * 
	 * The value passed in is tested for a <code>double</code> value:
	 * <p>
	 * returns 0 if the string can't be converted to a double
	 * <p>
	 * else returns the double value converted from the text string
	 * 
	 * @param String
	 *            value - the string to be tested
	 * @return double - value of the string
	 * 
	 */
	public static double checkDouble(String value) {
		try {
			if (value != null && !value.equals(""))
				return Double.parseDouble(value);
			else
				return 0.0d;
		} catch (NumberFormatException er) {
			return 0.0d;
		}
	}
	/**
	 * <h2><code>checkInteger</code></h2>
	 * 
	 * The value passed in is tested for an <code>int</code> value:
	 * <p>
	 * returns 0 if the string can't be converted to an int
	 * <p>
	 * else returns the int value converted from the string
	 * 
	 * @param String -
	 *            valuethe string to be tested
	 * @return int - value of the string
	 * 
	 */
	public static int checkInteger(String vl) {
		try {
			if (vl != null && !vl.equals("")) {
				return Integer.parseInt(vl);
			} else {
				return 0;
			}
		} catch (NumberFormatException er) {
			return 0;
		}

	}

	public static String createFloatMask(int decs) {
		DecimalFormatSymbols sm = new DecimalFormatSymbols();
		String editmask = "0";
		if (decs > 0) {
			editmask += sm.getDecimalSeparator();
			for (int x = 1; x <= decs; x++) {
				editmask += "0";
			}
		}
		return editmask;
	}

	/**
	 * <h2><code>formatCharField</code></h2>
	 * 
	 * Formats the value passed in based on the passed edit mask
	 * 
	 * @return the formatted value.
	 */
	public static String formatCharField(String val, String msk) {
		if (msk != null && !msk.equals("")) {
			try {
				MaskFormatter charformat = new MaskFormatter(msk);
				charformat.setPlaceholder(" ");
				return (String) charformat.stringToValue(val);
			} catch (Exception e) {
			}
		}
		return val;
	}

	public static String formatDateField(String dv, String msk) {
		try {
			return parseDate(dv, msk);
		} catch (Exception e) {
			return dv;
		}
	}

	public static String formatDoubleField(String dv, String msk) {
		DecimalFormatSymbols sm = new DecimalFormatSymbols();
		DecimalFormat fm = new DecimalFormat();
		if (msk != null && !msk.equals(""))
			fm.applyPattern(msk);
		else
			fm.applyPattern("#" + sm.getDecimalSeparator() + "00");
		try {
			return fm.format(Double.parseDouble(dv));
		} catch (Exception e) {
			return dv;
		}

	}

	public static String formatIntegerField(String iv, String msk) {
		DecimalFormat fltformat = new DecimalFormat();
		if (msk != null && !msk.equals(""))
			fltformat.applyPattern(msk);
		else
			fltformat.applyPattern("0");

		try {
			return fltformat.format(Integer.parseInt(iv));
		} catch (Exception e) {
			return iv;
		}		
	}

	public static String formatTimeField(String dv,String msk) {
		try {
			return parseTime(dv, msk);
		} catch (Exception e) {
			return dv;
		}
	}

	/**
	 * Gets an exception's stack trace as a String
	 * 
	 * @param e
	 *            the exception
	 * @return the stack trace of the exception
	 */
	public static String getStackTraceAsString(Throwable t) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(bytes, true);
		t.printStackTrace(writer);
		return bytes.toString();
	}

	public static boolean isDate(String value) {
		try {
			parseDate(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isTime(String value) {
		try {
			parseTime(value, "kkmmss");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String parseDate(String dv) 
	    throws NumberFormatException, DateStringFormatException {
		return parseDate(dv, "yyyyMMdd");
	}

	public static String parseDate(String date, String msk) throws NumberFormatException, DateStringFormatException {
		if (msk == null || msk.equals("")) msk = "yyyyMMdd";
		String d = removeAllFormat(date);
		return new SimpleDateFormat(msk).format(
			   new GregorianCalendar(parseYear(d),
				          parseMonth(d) - 1, parseDay(d)).getTime());
	}

	public static int parseDay(String dateValue)
			throws DateStringFormatException, NumberFormatException {

		if (dateValue.length() < 8)
			throw new DateStringFormatException("Cannot Parse Day");

		char date[] = dateValue.toCharArray();
		StringBuffer dayB = new StringBuffer();

		if (!isInteger(dateValue)) {
			if (date[0] >= '0' && date[0] <= '9')
				dayB.append(date[0]);

			if (date[1] >= '0' && date[1] <= '9')
				dayB.append(date[1]);
			return Integer.parseInt(dayB.toString());

		} else {
			int datn = Integer.parseInt(dateValue);
			if (datn > 99991231 || datn < 01010000)
				throw new DateStringFormatException("Cannot Parse Day");
			else if (datn > 12319999)
				return Integer.parseInt(dateValue.substring(6, 8));
			else
				return Integer.parseInt(dateValue.substring(2, 4));
		}
	}

	public static int parseMonth(String dateValue)
			throws DateStringFormatException, NumberFormatException {

		if (dateValue.length() < 8)
			throw new DateStringFormatException("Cannot Parse Year");

		String[] mth = { "jan", "feb", "mar", "apr", "may", "jun", "jul",
				"aug", "sep", "oct", "nov", "dec" };

		if (!isInteger(dateValue)) {

			for (int m = 0; m < mth.length; m++)
				if (dateValue.toLowerCase().indexOf(mth[m]) != -1)
					return m + 1;

			return 0;
		} else {
			int datn = Integer.parseInt(dateValue);
			if (datn > 99991231 || datn < 01010000)
				throw new DateStringFormatException("Cannot Parse Month");
			else if (datn > 12319999)
				return Integer.parseInt(dateValue.substring(4, 6));
			else
				return Integer.parseInt(dateValue.substring(0, 2));
		}
	}

	/**
	 * <p>
	 * Parses a 'property; from a string as item='property'
	 * </p>
	 * 
	 * @param prop -
	 *            the name of the property
	 * 
	 * @param propstring -
	 *            string containing the list of props
	 * 
	 * @return value of the property
	 */
	public static String parseProperty(String prop, String propstring) {
		try {
			int a = 0, b = 0;
			prop.trim();
			a = propstring.indexOf(" " + prop + "='");

			if (a == -1)
				a = propstring.indexOf("[" + prop + "='");

			if (a == -1)
				a = propstring.indexOf("'" + prop + "='");

			if (a != -1) {
				a += prop.length() + 3;
				b = propstring.indexOf("'", a);
			}

			if (a != -1 && b != -1)
				return propstring.substring(a, b);
			else
				return "";

		} catch (NullPointerException np) {
			return "";
		}
	}

	/**
	 * <p>
	 * Parses a 'sub-property; from a string as item='property(sub-prop)'
	 * </p>
	 * 
	 * @param prop -
	 *            the name of the property
	 * 
	 * @param propstring -
	 *            string containing the list of props
	 * 
	 * @return value of the sub-prop
	 */
	public static String parseSubProperty(String SubProperty) {
		try {
			int a = 0, b = 0;
			SubProperty.trim();
			a = SubProperty.indexOf("(");
			if (a != -1) {
				a++;
				b = SubProperty.indexOf(")", a);
			}
			if (a != -1 && b != -1) {
				return SubProperty.substring(a, b);
			} else {
				return "";
			}
		} catch (NullPointerException np) {
			return "";
		}
	}

	public static String parseTime(String time, String msk)  throws ParseException {		
		if (msk == null || msk.equals("")) msk = "kkmmss";
		return new SimpleDateFormat(msk).format(
				new SimpleDateFormat("kkmmss").parse(removeAllFormat(time)));
  }

	public static int parseYear(String dateValue)
			throws DateStringFormatException, NumberFormatException {

		if (dateValue.length() < 8)
			throw new DateStringFormatException("Cannot Parse Year");

		if (!isInteger(dateValue)) {
			String year = dateValue.substring(dateValue.length() - 4, dateValue
					.length());
			return Integer.parseInt(year);
		} else {
			int datn = Integer.parseInt(dateValue);
			if (datn > 99991231 || datn < 01010000)
				throw new DateStringFormatException("Cannot Parse Year");
			else if (datn > 12319999)
				return Integer.parseInt(dateValue.substring(0, 4));
			else
				return Integer.parseInt(dateValue.substring(4, 8));
		}
	}

	public static String removeAllFormat(String value){
		StringBuffer b;
		if (value.length() < 17)
			b = new StringBuffer(value);
		else
			b = new StringBuffer(value.substring(0, 10));
		removeFormat(b, ".");
		removeFormat(b, ",");
		removeFormat(b, "-");
		removeFormat(b, "/");
		removeFormat(b, "\\");
		removeFormat(b, " ");
		removeFormat(b, "_");
		removeFormat(b, ":");
		return b.toString();
	}

	public static StringBuffer removeFormat(StringBuffer value, String tag) {
		int s = value.indexOf(tag);
		while (s != -1) {
			if (s != -1)
				value.delete(s, s + 1);
			s = value.indexOf(tag);
		}
		return value;
	}

	
	/**
	 * Sends the contents of the specified file to the output stream
	 * 
	 * @param filename
	 *            the file to send
	 * @param out
	 *            the output stream to write the file
	 * @exception FileNotFoundException
	 *                if the file does not exist
	 * @exception IOException
	 *                if an I/O error occurs
	 */
	public static void returnFile(String filename, OutputStream out)
			throws FileNotFoundException, IOException {
		// A FileInputStream is for bytes
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int bytesRead;
			while ((bytesRead = fis.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	/**
	 * Sends the contents of the specified URL to the output stream
	 * 
	 * @param URL
	 *            whose contents are to be sent
	 * @param out
	 *            the output stream to write the contents
	 * @exception IOException
	 *                if an I/O error occurs
	 */
	public static void returnURL(URL url, OutputStream out) throws IOException {
		InputStream in = url.openStream();
		byte[] buf = new byte[4 * 1024]; // 4K buffer
		int bytesRead;
		while ((bytesRead = in.read(buf)) != -1) {
			out.write(buf, 0, bytesRead);
		}
	}

	/**
	 * Sends the contents of the specified URL to the Writer (commonly either a
	 * PrintWriter or JspWriter)
	 * 
	 * @param URL
	 *            whose contents are to be sent
	 * @param out
	 *            the Writer to write the contents
	 * @exception IOException
	 *                if an I/O error occurs
	 */
	public static void returnURL(URL url, Writer out) throws IOException {
		// Determine the URL's content encoding
		URLConnection con = url.openConnection();
		con.connect();
		String encoding = con.getContentEncoding();

		// Construct a Reader appropriate for that encoding
		BufferedReader in = null;
		if (encoding == null) {
			in = new BufferedReader(new InputStreamReader(url.openStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(url.openStream(),
					encoding));
		}
		char[] buf = new char[4 * 1024]; // 4Kchar buffer
		int charsRead;
		while ((charsRead = in.read(buf)) != -1) {
			out.write(buf, 0, charsRead);
		}
	}

	/**
	 * Splits a String into pieces according to a delimiter.
	 * 
	 * @param str
	 *            the string to split
	 * @param delim
	 *            the delimiter
	 * @return an array of strings containing the pieces
	 */
	public static String[] split(String str, String delim) {
		// Use a Vector to hold the splittee strings
		Vector<String> v = new Vector<String>();

		// Use a StringTokenizer to do the splitting
		StringTokenizer tokenizer = new StringTokenizer(str, delim);
		while (tokenizer.hasMoreTokens()) {
			v.addElement(tokenizer.nextToken());
		}

		String[] ret = new String[v.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = v.elementAt(i);
		}

		return ret;
	}

	/**
	 * <h2><code>zeroFillField</code></h2>
	 * 
	 * Zero Fills a Character Field
	 * 
	 * inputs - "123", 10
	 * 
	 * returns "0000000123"
	 * 
	 * @return the formatted value.
	 */
	public static String zeroFillField(String value, int len) {
		if (value == null || value.equals("")) {
			return value;
		} else {
			int l = len - value.trim().length();
			String r = "";
			for (int x = 0; x < l; x++)
				r += "0";
			return r + value.trim();
		}
	}

	private Vector<String[]> message = new Vector<String[]>();

	private Vector<String[]> property = new Vector<String[]>();

	private boolean securityManagerONOFF = false, moreButtonActive = false;

	private String user, userGroup, userDesc, userScbml, rmtIP, rmtHost, lang;

	public DataSet() { }

	public DataSet addMessage(String messagesID) {
		message.add(new String[] {messagesID,  null});
		return this;
	}

	public DataSet addMessage(String messagesID, String fieldID) {
		message.add(new String[] {messagesID,  fieldID});
		return this;
	}

	/**
	 * <h2><code>addMessage</code></h2>
	 * 
	 * <p>
	 * Adds a message to the window message pump - messages are are displayed in
	 * the lower panel of the applet window. Message parms are as:
	 * </p>
	 * 
	 * @param String
	 *            messagesText - The text to diaplay in the applet message panel
	 * @param String
	 *            level - the level of the message: '10' = info '20' = warnings
	 *            '30' = errors
	 * @param String
	 *            messageID - The field in error with the applet - this will
	 *            position the applet cursor to the field in error
	 * @param String
	 *            errorDetails
	 * 
	 */
	public DataSet addMessage(String messagesText, String level, String fieldID,
			String errorDetails) {
		message.add(new String[] {messagesText, level, fieldID, errorDetails});
		return this;
	}

	/**
	 * <h2><code>clearMessagePump</code></h2>
	 * 
	 * <p>
	 * removes all messages from the applet message pump - messages are are
	 * displayed in the lower panel of the applet window.
	 * </p>
	 * 
	 * 
	 */
	public void clearMessagePump() {
		message.clear();
	}

	/**
	 * <h2><code>clearObjectPropertyPump</code></h2>
	 * 
	 * <p>
	 * clears out the applet object property pump
	 * </p>
	 * 
	 * 
	 */
	public void clearObjectPropertyPump() {
		property.clear();
	}

	/**
	 * <h2><code>get</code></h2>
	 * 
	 * <p>
	 * Returns an object based on a key - ie - Hashtable get(key)
	 * </p>
	 * 
	 * @param k -
	 *            Hash Table key
	 * @return - Object
	 * 
	 */
	@Override
	public Object get(Object k) {
		Object value = super.get(k);
		if (value == null) {
			return null;
		} else if (value instanceof NullValue) {
			return null;
		} else {
			return value;
		}
	}

	/**
	 * <h2><code>getBooleanField</code></h2>
	 * 
	 * <p>
	 * Returns a boolean value from the com-Hashtable
	 * </p>
	 * 
	 * @param key -
	 *            script field name
	 * @return - boolean - field value
	 * 
	 */
	public boolean getBooleanField(String fld) {
		String value = getStringField(fld);
		if (value != null && value.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <h2><code>getCurrentTableRow</code></h2>
	 * 
	 * <p>
	 * Returns a table value from the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * 
	 * @return - String [] - field value
	 * 
	 */
	public String[] getCurrentTableRow(String tb) {
		Object object = get(tb);
		if (object != null && object instanceof String[])
			return (String[]) object;
		else
			return null;
	}

	/**
	 * <h2><code>getDoubleField</code></h2>
	 * 
	 * <p>
	 * Returns a double value from the com-Hashtable
	 * </p>
	 * 
	 * @param key -
	 *            script field name
	 * @return - double - field value
	 * 
	 */
	public double getDoubleField(String fld) {
		return checkDouble(getStringField(fld));
	}

	/**
	 * <h2><code>getFileType</code></h2>
	 * 
	 * <p>
	 * Returns and parse a file name
	 * </p>
	 * 
	 * @param key -
	 *            File name
	 * @return - string
	 * 
	 */
	public String getFileType(String fld) {
		fld.trim();
		int a = fld.indexOf(".");
		if (a != -1) {
			return fld.substring(++a);
		} else {
			return "";
		}
	}

	/**
	 * <h2><code>getIcon</code></h2>
	 * 
	 * <p>
	 * Returns an ImageIcon value from the com-Hashtable
	 * </p>
	 * 
	 * @param key -
	 *            script field name
	 * @return - ImageIcon - field value
	 * 
	 */
	public ImageIcon getIcon(String icn) {
		byte[] object = (byte[]) get(icn);
		if (object != null)
			return new ImageIcon(object);
		else
			return null;
	}

	/**
	 * <h2><code>getIntegerField</code></h2>
	 * 
	 * <p>
	 * Returns an int value from the com-Hashtable
	 * </p>
	 * 
	 * @param key -
	 *            script field name
	 * @return - int - field value
	 * 
	 */
	public int getIntegerField(String fld) {
		return checkInteger(getStringField(fld));
	}

	/**
	 * <h2><code>getMessage</code></h2>
	 * 
	 * <p>
	 * Returns a message from the message pump - messages are are displayed in
	 * the lower panel of the applet window.
	 * </p>
	 * 
	 * @param int -
	 *            message index
	 * 
	 */
	public String[] getMessage(int msg) {
		return message.get(msg);
	}

	
    /**
	 * <h2><code>getMessageList</code></h2>
	 * 
	 * <p>
	 * Returns a message pump - messages are are displayed in the lower panel of
	 * the applet window.
	 * </p>
	 * 
	 * @return Enumeration - messages
	 * 
	 */
	public Enumeration getMessageList() {
		return message.elements();
	}
    
    public String getMethodName() {
		return getStringField(WindowItem.METHOD);
	}
    
	public boolean getMoreButtonStatus() {
		return moreButtonActive;
	}

	public String[] getObjectProperty(int propID) {
		return property.get(propID);
	}

	public Enumeration getObjectPropertyList() {
		return property.elements();
	}

	public String getScmbl() {
    	return userScbml; 
    }

	
	public String getUserIP() {
    	return rmtIP; 
    }

	public String getRmtHost() {
    	return rmtHost; 
    }

	/**
	 * <h2><code>getSearchTextField</code></h2>
	 * 
	 * <p>
	 * Returns a SearchTextFieldDataSet value from the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @return - SearchTextFieldDataSet - field value
	 * 
	 */
	public SearchTextFieldDataSet getSearchTextField(String fld) {
		Object object = get(fld);
		if (object != null && object instanceof SearchTextFieldDataSet)
			return (SearchTextFieldDataSet) object;
		else
			return null;
	}

	public boolean getSecurityManagerStatus() {
		return securityManagerONOFF;
	}

	public String getServerAction() {
		return getStringField(WindowItem.ACTION);
	}

	/**
	 * <h2><code>getStreamArray</code></h2>
	 * 
	 * <p>
	 * Returns an byte[] file stream
	 * </p>
	 * 
	 * @param key -
	 *            script field name
	 * @return - byte[] file stream - field value
	 * 
	 */
	public byte[] getStreamArray(String fld) {
		return (byte[]) get(fld);
	}

	/**
	 * <h2><code>getStringField</code></h2>
	 * 
	 * <p>
	 * Returns a String value from the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @return - String - field value
	 * 
	 */
	public String getStringField(String fld) {
		Object object = get(fld);
		if (object != null && object instanceof String)
			return (String) object;
		else
			return null;
	}

	/**
	 * <h2><code>getTableColumnFields</code></h2>
	 * 
	 * <p>
	 * Returns an array that are the column names of an applet table
	 * </p>
	 * 
	 * @param String -
	 *            table name
	 * @return - String[] - column names
	 * 
	 */
	public String[] getTableColumnFields(String tb) {
		return (String[]) get("[" + tb.trim() + "_COLNAMES/]");
	}

	

	/**
	 * <h2><code>getTableVector</code></h2>
	 * 
	 * <p>
	 * Returns a table value from the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @return - Vector - field value
	 * 
	 */
	public Vector getTableVector(String tb) {
		Object object = get(tb);
		if (object != null && object instanceof Vector)
			return (Vector) object;
		else
			return new Vector();
	}
	
	public String getTreeItemToExpand() {
		return getStringField("[Parent_Tree_Expand/]");
	}

	/**
	 * <h2><code>getUpdateableFields</code></h2>
	 * 
	 * <p>
	 * Returns a list of fields that are set for update in the applet
	 * </p>
	 * 
	 * @return - String[] - field list
	 * 
	 */
	public String[] getUpdateableFields() {
		Object value = get("[updateFieldNames/]");
		if (value instanceof String[])
			return (String[]) value;
		else
			return null;
	}

	
	/**
	 * <h2><code>getURLField</code></h2>
	 * 
	 * <p>
	 * Returns a URL value from the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @return - URL - field value
	 * 
	 */
	public URL getURLField(String fld) {
		URL url = (URL) super.get(fld);
		if (url != null) {
			return url;
		} else {
			return null;
		}
	}

	public String getLang() {
		return lang;
	}
	
	public String getUser() {
		return user;
	}

	public String getUserDescription() {
		return userDesc;
	}

	
	public String getUserGroup() {
		return userGroup;
	}
	
	
	public boolean isObjectPropertyPumpClear() {
		return property.isEmpty();
	}
	
	
	
	public boolean isPumpClear() {
		return message.isEmpty();
	}

	
	@Override
	public DataSet put(Object k, Object v) {
		if (v != null) 
			super.put(k, v);
	     else 
			super.put(k, new NullValue());
		
		return this;
	}
	
	
	/**
	 * <h2><code>putBooleanField</code></h2>
	 * 
	 * <p>
	 * places a boolean value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            boolean - field value
	 * 
	 */
	public DataSet putBooleanField(String k, boolean v) {
		put(k, Boolean.toString(v));
		return this;
	}

	
	/**
	 * <h2><code>putCurrentTableRow</code></h2>
	 * 
	 * <p>
	 * places a table value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            String[] tableData
	 * 
	 */
	public DataSet putCurrentTableRow(String k, String[] v) {
		put(k, v);
		return this;
	}

	
	/**
	 * <h2><code>putDoubleField</code></h2>
	 * 
	 * <p>
	 * places a double value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            double - field value
	 * 
	 */
	public DataSet putDoubleField(String k, double v) {
		put(k, Double.toString(v));
		return this;
	}

	/**
	 * <h2><code>putIconField</code></h2>
	 * 
	 * <p>
	 * places a ImageIcon value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            ImageIcon - field value
	 * 
	 */

	public DataSet putIcon(String k, byte[] v) {
		put(k, v);
		return this;
	}

	
	/**
	 * <h2><code>putIconField</code></h2>
	 * 
	 * <p>
	 * places a ImageIcon value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            ImageIcon - field value
	 * 
	 */

	public DataSet putIntegerField(String k, int v) {
		put(k, Integer.toString(v));
		return this;
	}
	
	
	/**
	 * <h2><code>putStreamArray</code></h2>
	 * 
	 * <p>
	 * places a ImageIcon value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            StreamArry - field value
	 * 
	 */
	public DataSet putStreamArray(String k, byte[] v) {
		put(k, v);
		return this;
	}

	/**
	 * <h2><code>putStringField</code></h2>
	 * 
	 * <p>
	 * places a String value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            String - field value
	 * 
	 */
	public DataSet putStringField(String k, String v) {
		put(k, v);
		return this;
	}

	/**
	 * <h2><code>putTableVector</code></h2>
	 * 
	 * <p>
	 * places a table value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            Vector - field value
	 * 
	 */
	public DataSet putTableVector(String k, Vector v) {
		put(k, v);
		return this;
	}

	/**
	 * <h2><code>putURLField</code></h2>
	 * 
	 * <p>
	 * places a URL value into the com-Hashtable
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            URL - field value
	 * 
	 */
	public DataSet putURLField(String k, URL v) {
		put(k, v);
		return this;
	}

	public DataSet setMoreButtonStatus(boolean status) {
		moreButtonActive = status;
		return this;
	}

	public DataSet setObjectProperty(String objectName, String propertyName,
			String propertyValue) {
		property.add(new String[] { objectName, propertyName, propertyValue });
		return this;
	}

	public DataSet setScmbl(String s) {
    	userScbml = s;
    	return this;
    }

	public void setSecurityManagerStatus(boolean sms) {
		securityManagerONOFF = sms;
	}

	/**
	 * <h2><code>setUser</code></h2>
	 * 
	 * <p>
	 * Set by the server to sets the user name and description
	 * </p>
	 * 
	 * @param String -
	 *            user - User name for this session
	 * @param String -
	 *            userDesc - User description
	 * 
	 */
	public void setUser(String u) {
			user = u;
	}

	public void setUserIP(String u) {
		rmtIP = u;
	}
	
	public void setRmtHost(String u) {
		rmtHost = u;
	}
	
	public void setLang(String l) {
		lang = l;
	}
	
	public void setUser(String u, String d, String g, String l) {
		user = u;
		userDesc = d;
		userGroup = g;
		lang = l;
    }
	
	public void setUser(String u, String d, String g) {
			user = u;
			userDesc = d;
			userGroup = g;		 
	}

	/**
	 * <h2><code>setUserGroup</code></h2>
	 * 
	 * <p>
	 * Set by the server to sets the user group
	 * </p>
	 * 
	 * @param String -
	 *            script field name
	 * @param -
	 *            URL - field value
	 * 
	 */
	public void setUserGroup(String userGroup) {
		if (userGroup != null && !userGroup.equals(""))
			this.userGroup = userGroup;
		else
			this.userGroup = "GENERAL";
	};

}

class DateStringFormatException extends Exception {

	private static final long serialVersionUID = -6375051267419566260L;

	public DateStringFormatException() {
	}

	public DateStringFormatException(String msg) {
		super("Date Format Error: " + msg);
	}
}

class NullValue implements java.io.Serializable {

	private static final long serialVersionUID = -6688269878317090586L;

	@Override
	public String toString() {
		return "class webBoltOns.dataContol.NullValue";
	}
}