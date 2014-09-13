package webBoltOns.client.components.componentRules;

/*
 * $Id: MenuRules.java,v 1.1 2007/04/20 19:37:28 paujones2005 Exp $ $Name:  $
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

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import webBoltOns.client.MenuItem;

public class MenuRules {

	public final JLabel[] propertyLabel = { 
			new JLabel("Menu/Option : "),
			new JLabel("Description: "),
			new JLabel("XML/HTTP Link: "),
			new JLabel("Quick Link: "),
			new JLabel("Quick Link Image: ")};

	public final Component[] propertyComponents = {
			new JTextField(), // Component
			new JTextField(), // 01 Label Description:
			new JTextField(), // 02 Link
			new JComboBox(), // 03 Quick
			new JTextField(), // 03 Image
	};

	public final String[] propertyTypes = { 
			MenuItem.OBJECT_NAME,
			MenuItem.DESCRIPTION, 
			MenuItem.LINK,
			MenuItem.QUICKLINK,
			MenuItem.ICON,
			};

	public final String[] screenComponents = { MenuItem.SUB_MENU_OBJECT,
			"Script", MenuItem.DOCUMENT_TO_LOAD_OBJECT, };

	public Hashtable<String, String[]> propertyRules = new Hashtable<String, String[]>();

	public MenuRules() {
		propertyRules.put(MenuItem.SUB_MENU_OBJECT, new String[] { "T", // Component
				"T", // 01 Label Description:
				"T", // LINK
				"T", // Quick Link
				"F", // Image
		});
		propertyRules.put("Script", new String[] { "T", // Component
				"T", // 01 Label Description:
				"T", // LINK
				"T", // Quick Link
				"T", // Image
		});
		propertyRules.put(MenuItem.DOCUMENT_TO_LOAD_OBJECT, new String[] {
				"T", // Component
				"T", // 01 Label Description:
				"T", // LINK
				"T", // Quick Link
				"T", // Image
		});
	}
}