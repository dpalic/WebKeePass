package webBoltOns.client.components.componentRules;

/*
 * $Id: ReportRules.java,v 1.1 2008/05/20 02:28:17 paujones2005 Exp $ $Name:  $
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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import webBoltOns.server.reportWriter.ReportColumn;

public class ReportRules {

	public final JLabel[] propertyLabel = { 
			new JLabel("Column Field : "),
			new JLabel("Description: "),
			new JLabel("Data Length: "),
			new JLabel("Decimals: "),
			new JLabel("Alignment: "),
			new JLabel("Data Type: "),
			new JLabel("Sub-Totaled: "),
			new JLabel("Maximum: "),
			new JLabel("Minimum: "),
			new JLabel("Average: "),
			new JLabel("Count: "),	
			new JLabel("Level Break: "),
			new JLabel("Encrypted: ")};
	
	public final Component[] propertyComponents = {
			new JTextField(), // Column Field
			new JTextField(), // 01 Description
			new JSpinner(),   // 02 Data Length
			new JSpinner(),   // 03 Decimals
			new JComboBox(),  // 04 Alignment
			new JComboBox(),  // 05 Data Type
			new JComboBox(),  // 06 Subtotaled
			new JComboBox(),  // 07 Max
			new JComboBox(),  // 08 Min
			new JComboBox(),  // 09 Average
			new JComboBox(),  // 10 Count
			new JSpinner(),   // 11 Level Break
			new JComboBox(),  // 12 Encrypted
	};

	public final String[] propertyTypes = { 
			ReportColumn.FIELDNAME,
			ReportColumn.DESCRIPTION,
			ReportColumn.LENGTH,
			ReportColumn.DECIMALS,
			ReportColumn.ALIGNMENT,
			ReportColumn.DATATYPE,
			ReportColumn.SUB_TOTAL,
			ReportColumn.SUB_MAXIMUM,
			ReportColumn.SUB_MINIMUM,
			ReportColumn.SUB_AVERAGE,
			ReportColumn.SUB_COUNT,
			ReportColumn.LEVELBREAK, 
			ReportColumn.ENCRYPTED};

	public final String[] screenComponents = {"COLUMN"};
	Hashtable<String, String[]> propertyRules = new Hashtable<String, String[]>();

	public ReportRules() {
		propertyRules.put("COLUMN", new String[] {
				"T", // Column Field
				"T", // 01 Description
				"T", // 02 Data Length
				"T", // 03 Decimals
				"T", // 04 Alignment
				"T", // 05 Data Type
				"T", // 06 Subtotaled
				"T", // 07 Max
				"T", // 08 Min
				"T", // 09 Average
				"T", // 10 Count
				"T", // 11 Level Break
				"T", // 12 ENCRYPTED
				
		});
	}
}