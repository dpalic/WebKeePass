package webBoltOns.dataContol;

/*
 * $Id: ConfigHandler.java,v 1.1 2007/04/20 19:37:22 paujones2005 Exp $ $Name:  $
 *
 *   Copyright  2004, 2005, 2006  www.jrivet.com  -   All Rights Reserved 
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
 * The Initial Developer of the Original Code is Paul Jones.
 * 
 *  Copyright (C) 2004, 2005, 2006  by Paul Jones.
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
 * 
 */


import java.util.Hashtable;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;

/**
 * <h1>ConfigHandler</h1>
 * 
 * <p> the jRivet Framework (Server-side) </p>
 * 
 * <p>
 *   Reads the ConfigFile1.xml document - This XML document outlines
 *   the basic items needed to start the jRivet servlet (as follows).
 *   ConfigFile1.xml is most often found in the WEB-INF folder but it 
 *   can be placed in any location. WEB.XML will contains a servlet
 *   parm to point to the ConfigFile1 file directory.  See
 *   ConfigFile1.xml for more details:
 * </p>
 *  
 * <p> 1 - The Database JBDC driver & class name</p>
 * <p> 2 - The DataBase JBDC connection string </p>
 * <p> 3 - Total Database connections to pool </p>
 * <p> 4 - The Database user name and password </p>
 * <p> 5 - Servlet RMI and Socket ports </p>
 * <p> 6 - Company Name displayed in window title bar </p>
 * <p> 7 - File Directory for XML scripts and images </p>
 * <p> 8 - Debugging and security levels </p>
 * <p> 9 - Default user desk-top colors and icons </p>
 *
 *
 */
@SuppressWarnings("deprecation")

public class ConfigHandler extends HandlerBase {

	protected Hashtable<String, String> table = new Hashtable<String, String>();

	protected String currentElement, currentValue, defaultThemeObject;

	 /**
	  * <h2><code>setTable</code></h2>
	  * 
	  * <p>
	  *  Simple Accessor for the Hashtable of parsed values    
	  * </p>
	  * 
	  * 
	  * @param   Hashtable table
	  * 
	  */	
	public void setTable(Hashtable table) {
		this.table = table;
	}

	 /**
	  * <h2><code>getTable</code></h2>
	  * 
	  * <p>
	  *  Simple Accessor for the Hashtable of parsed values    
	  * </p>
	  * 
	  * 
	  * @return   Hashtable table
	  * 
	  */
	public Hashtable getTable() {
		return table;
	}


	 /**
	  * <h2><code>startElement</code></h2>
	  * 
	  * <p>
	  *  Called when a new element is encountered    
	  * </p>
	  * 
	  * @param  String tag
	  * @param  AttributeList attrs
	  */	
	public void startElement(String tag, AttributeList attrs)
			throws SAXException {

		if (tag.endsWith("Color")) {
			defaultThemeObject = "[ColorName='" + tag + "' Red='"
					+ attrs.getValue("Red") + "' Blue='"
					+ attrs.getValue("Blue") + "' Green='"
					+ attrs.getValue("Green") + "' /]";

		}

		if (tag.endsWith("Font")) {
			defaultThemeObject = "[FontName='" + tag + "' FontFamily='"
					+ attrs.getValue("FamilyName") + "' Size='"
					+ attrs.getValue("PointSize") + "' Italic='"
					+ attrs.getValue("Italics") + "' Bold='"
					+ attrs.getValue("Bolded") + "' /]";

		}
		// hold onto the new element tag, that will be placed in
		// our Hashtable when matching character data is encountered.
		currentElement = tag;
	}

	 /**
	  * <h2><code>characters</code></h2>
	  * 
	  * <p>
	  *  Called when character data is found inside an element    
	  * </p>
	  * 
	  * @param  char[] ch
	  * @param  int start
	  * @param 	  int length
	  */	
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		// create a String containing the characters
		// found in the element
		currentValue = new String(ch, start, length);
	}

	/**
	  * <h2><code>endElement/code></h2>
	  * 
	  * <p>
	  *  Called when when the end of element is encountered    
	  * </p>
	  * 
	  * @param  String name
	  */	
	public void endElement(String name) throws SAXException {
		if (currentElement.equals(name)) {
			if (name.equals("BackGroundColor") || name.equals("TitleBarColor")
					|| name.equals("TitleBarFontColor")
					|| name.equals("WindowTitleColor")
					|| name.equals("WindowTitleFontColor")
					|| name.equals("CursorColor") || name.equals("HeaderFont")
					|| name.equals("RegularFont"))
				table.put(currentElement, defaultThemeObject);
			else
				table.put(currentElement, currentValue);

		}
	}
}