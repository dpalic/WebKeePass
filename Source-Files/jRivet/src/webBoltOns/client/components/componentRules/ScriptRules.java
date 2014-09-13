package webBoltOns.client.components.componentRules;

/*
 * $Id: ScriptRules.java,v 1.1 2007/04/20 19:37:28 paujones2005 Exp $ $Name:  $
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
import javax.swing.JSpinner;
import javax.swing.JTextField;

import webBoltOns.client.WindowItem;

public  class ScriptRules {
	
	public final JLabel[] propertyLabel = {
			new JLabel("Component:            "), 
			new JLabel("Action Type: "),
			new JLabel("Label Description: "), 
			new JLabel("Field Name: "),
			new JLabel("Position:"), 
			new JLabel("Data Type: "),
			new JLabel("Data Length: "), 
			new JLabel("Decmails: "),
			new JLabel("Data Alignment: "), 
			new JLabel("Output Only : "),
			new JLabel("Edit Mask"), 
			new JLabel("Length: "),
			new JLabel("Height: "), 
			new JLabel("Width: "),
			new JLabel("Icon File Name: "), 
			new JLabel("Script Link: "),
			new JLabel("Link/Method: "), 
			new JLabel("Link Class: "),
			new JLabel("Link Parameter:"),
			new JLabel("List Table Name: "),
			new JLabel("List Table Field: "), 
			new JLabel("List Table Desc: "),
			new JLabel("List Selection: "),
			new JLabel("Orientation: "),
			new JLabel("Default Value/Key: "),
			new JLabel("Hidden/Protected: "),
			new JLabel("Zero Fill Field: "),
			new JLabel("Com/Method: "),
			new JLabel("Com Class: "),
			new JLabel("Encrypted: "),
	};
	
	public final Component[] propertyComponents = {
			new JTextField(), // Component
			new JComboBox(), //  01 Type
			new JTextField(), // 02 Label Description:
			new JTextField(), // 03 Field Name
			new JComboBox(), //  04 Position
			new JComboBox(), //  05 Data Type
			new JSpinner(), //   06 Data Length
			new JSpinner(), //   07 Decmails
			new JComboBox(), //  08 Data Aligment
			new JComboBox(), //  09 Input/output
			new JTextField(), // 10 Edit Mask
			new JSpinner(), //   11 Length
			new JSpinner(), //   12 Height
			new JSpinner(), //   13 Width
			new JTextField(), // 14 Icon File Name:
			new JTextField(), // 15 Script Link
			new JTextField(), // 16 Link Method:
			new JTextField(), // 17 Link Class:
			new JTextField(), // 18 Paramter Name::
			new JTextField(), // 19 List Table Name:
			new JTextField(), // 20 List Field Name:
			new JTextField(), // 21 List Desc Name:
			new JTextField(), // 22 List Selection :
			new JComboBox(), //  23 Component_Orientation: 
			new JTextField(), // 24 Default Value:
			new JComboBox(), //  25 Hidden:
			new JComboBox(), //  26 Zero Fill Value: 
			new JTextField(), // 27 Commitment Method:
			new JTextField(), // 28 Commitment Class :
			new JComboBox(), //  29 Encrypted:
	};

	public final static String[] propertyTypes = {
		    WindowItem.OBJECT_NAME,
			WindowItem.TYPE, WindowItem.DESCRIPTION,
			WindowItem.FIELDNAME, WindowItem.POSITION,
			WindowItem.DATATYPE, WindowItem.DATALENGTH,
			WindowItem.DECIMALS, WindowItem.ALIGNMENT,
			WindowItem.PROTECTED, WindowItem.EDITMASK,
			WindowItem.LENGTH, WindowItem.HEIGHT,
			WindowItem.WIDTH, WindowItem.ICON, WindowItem.LINK,
			WindowItem.METHOD, WindowItem.METHODCLASS,
			WindowItem.PARAMETER, WindowItem.LIST_TABLE,
			WindowItem.LIST_TABLE_FIELD, WindowItem.LIST_TABLE_DESCRIPTION,
			WindowItem.LIST_TABLE_SELECTION,WindowItem.COMPONENT_ORIENTATION,
			WindowItem.DEFAULT_VALUE,	WindowItem.HIDDEN,	WindowItem.ZERO_FILL,
			WindowItem.COMMIT_METHOD,	WindowItem.COMMIT_CLASS, WindowItem.ENCRYPT
			};

	public final static String[] propertyInitValues = {
			"", 	// 0 -WindowItem.OBJECT_NAME,
			"", 	// 1 - WindowItem.TYPE,
			"", 	// 2 - WindowItem.DESCRIPTION,
			"", 	// 3 - WindowItem.FIELDNAME,
			"Left", // 4 - WindowItem.POSITION,
			"CHR", 	// 5 - WindowItem.DATATYPE,
			"0", 	// 6 - WindowItem.DATALENGTH,
			"0", 	// 7 - WindowItem.DECIMALS,
			"Left", // 8 - WindowItem.ALIGNMENT,
			"N", 	// 9 - WindowItem.PROTECTED,
			"", 	//10 - WindowItem.EDITMASK,
			"0", 	//11 - WindowItem.LENGTH,
			"0", 	//12 - WindowItem.HEIGHT,
			"0", 	//13 - WindowItem.WIDTH,
			"", 	//14 - WindowItem.ICON,
			"", 	//15 - WindowItem.LINK,
			"", 	//16 - WindowItem.METHODNAME,
			"", 	//17 - WindowItem.METHODCLASS
			"", 	//18 - WindowItem.PARAMETER
			"", 	//19 - WindowItem.LIST_TABLE
			"",	 	//20 - WindowItem.LIST_TABLE_FLD
			"", 	//21 - WindowItem.LIST_TABLE_DSC
			"", 	//22 - WindowItem.LIST_TABLE_SEL
			"Top", 	//23 - WindowItem.COMPONENT_ORIENTATION
			"", 	//24 - WindowItem.DEFAULT_VALUE
			"N", 	//25 - WindowItem.HIDDEN,
			"N", 	//26 - WindowItem.ZERO_FILL_VALUE,
			"", 	//27 - Commitment Method,
			"", 	//28 - Commitment Class,
			"N", 	//29 - WindowItem.ENCRYPT_VALUE
	
	};

	public final static String[] barButtonTypes = { "CLEAR", "FIND", "EXPAND", "EDIT",
			"DELETE", "PREV", "NEXT", "MORE", "PRINT", "POST", "THREAD",
			"PREVIEW", "REPORT", "NEW-LINE", "DEL-LINE", "POST-LINE" };

	public final static String[] screenComponents = {
			WindowItem.TEXT_LABEL,
			WindowItem.TEXT_FIELD_OBJECT,
			WindowItem.SEARCH_FIELD_OBJECT,
			WindowItem.SPINNER_OBJECT,
			WindowItem.TEXT_AREA_OBJECT,
			WindowItem.TEXT_RTF_AREA_OBJECT,
			WindowItem.COMBOBOX_OBJECT, 
			WindowItem.COMBOITEM_OBJECT,
			WindowItem.LISTBOX_OBJECT,
			WindowItem.CHECKBOX_OBJECT, 
			"BarButton",
			WindowItem.IMAGE_FIELD, 
			WindowItem.IMAGE_BOX_OBJECT,
			WindowItem.MEDIA_BOX_OBJECT,
			WindowItem.RADIO_BUTTON_OBJECT,
			WindowItem.BUTTON_OBJECT,
			WindowItem.TABLE_CHECKBOX_OBJECT,
			WindowItem.TABLE_COMBOBOX_OBJECT,
			WindowItem.TABLE_SPINNER_OBJECT,
			WindowItem.TABLE_TEXTFIELD_OBJECT,
			WindowItem.TABLE_IMAGE_OBJECT,
			WindowItem.TABLE_PWD_OBJECT,
			WindowItem.TABLE_NAVROW_OBJECT,
			WindowItem.FILE_PROMPT,
			WindowItem.PASSWORD_FIELD_OBJECT,
			WindowItem.PASSWORD_EDITOR_OBJECT,
			WindowItem.MACRO_EDITOR,			
			};

	public final static String[] screenContainers = {
			"ButtonBar",
			WindowItem.PANEL_OBJECT, 
			WindowItem.TABGROUP_OBJECT,
			WindowItem.TABITEM_OBJECT,
			WindowItem.TAB_TABLE_OBJECT,
			WindowItem.TABLE_OBJECT,
			WindowItem.TABLE_DOC_MNGR_OBJECT,
			WindowItem.TREE_TABLE_OBJECT,
			WindowItem.RADIO_BUTTON_GROUP_OBJECT,
			WindowItem.MENU_EXPLORER_OBJECT,
			WindowItem.SCRIPT_EDITOR_OBJECT,
			WindowItem.SCRIPT_DESIGNER_OBJECT,
			WindowItem.REPORT_EDITOR_OBJECT,
			WindowItem.MENU_EDITOR_OBJECT
			};

	public final  Hashtable<String, String> compClass = new Hashtable<String, String>(); 

	public final  Hashtable<String, String[]> propertyRules = new Hashtable<String, String[]>();
		
	
	public ScriptRules() {
		compClass.put(WindowItem.TEXT_LABEL, "webBoltOns.client.components.CTextLabel");
		compClass.put(WindowItem.TEXT_FIELD_OBJECT, "webBoltOns.client.components.CTextBoxField");
		compClass.put(WindowItem.MACRO_EDITOR, "webBoltOns.client.components.CMacroEditor");
		compClass.put(WindowItem.SEARCH_FIELD_OBJECT, "webBoltOns.client.components.CSearchTextField");
		compClass.put(WindowItem.SPINNER_OBJECT, "webBoltOns.client.components.CSpinnerField");
		compClass.put(WindowItem.TEXT_AREA_OBJECT, "webBoltOns.client.components.CTextHTMLField");
		compClass.put(WindowItem.TEXT_RTF_AREA_OBJECT, "webBoltOns.client.components.CTextRTFField");
		compClass.put(WindowItem.COMBOBOX_OBJECT, "webBoltOns.client.components.CComboBoxField");    
		compClass.put(WindowItem.LISTBOX_OBJECT, "webBoltOns.client.components.CListBoxField");
		compClass.put(WindowItem.IMAGE_FIELD, "webBoltOns.client.components.CImageBoxField");
		compClass.put(WindowItem.FILE_PROMPT, "webBoltOns.client.components.CFilePrompt");
		compClass.put(WindowItem.PASSWORD_FIELD_OBJECT, "webBoltOns.client.components.CSHAPasswordField");
		compClass.put(WindowItem.PASSWORD_EDITOR_OBJECT, "webBoltOns.client.components.CPasswordEditor");
		
		compClass.put(WindowItem.IMAGE_BOX_OBJECT, "webBoltOns.client.components.CImageBoxField");
        compClass.put(WindowItem.CHECKBOX_OBJECT, "webBoltOns.client.components.CCheckBoxField"); 
         
		compClass.put(WindowItem.PANEL_OBJECT, "webBoltOns.client.components.CPanelContainer");
		compClass.put(WindowItem.TABGROUP_OBJECT,"webBoltOns.client.components.CTabContainer");
		compClass.put(WindowItem.TABITEM_OBJECT, "webBoltOns.client.components.CPanelContainer");
		compClass.put(WindowItem.TAB_TABLE_OBJECT, "webBoltOns.client.components.CTabTableContainer");
		compClass.put(WindowItem.TABITEM_OBJECT, "webBoltOns.client.components.CPanelContainer");
		
		compClass.put(WindowItem.TABLE_OBJECT,          "webBoltOns.client.components.CTableContainer");
		compClass.put(WindowItem.TREE_TABLE_OBJECT,     "webBoltOns.client.components.CTreeTableContainer");
		compClass.put(WindowItem.RADIO_BUTTON_GROUP_OBJECT, "webBoltOns.client.components.CRadioGroupContainer");
		compClass.put(WindowItem.MENU_EXPLORER_OBJECT,  "webBoltOns.client.components.CMenuExplorerContainer");
		compClass.put(WindowItem.SCRIPT_EDITOR_OBJECT,  "webBoltOns.client.components.CScriptEditorContainer");
		compClass.put(WindowItem.REPORT_EDITOR_OBJECT,  "webBoltOns.client.components.CReportEditorContainer");
		compClass.put(WindowItem.MENU_EDITOR_OBJECT,    "webBoltOns.client.components.CMenuEditorContainer");			
		compClass.put(WindowItem.TABLE_DOC_MNGR_OBJECT, "webBoltOns.client.components.CDocManager");
		compClass.put(WindowItem.MENU_EDITOR_OBJECT,    "webBoltOns.client.components.CMenuEditorContainer"); 		
		
		compClass.put(WindowItem.TABLE_CHECKBOX_OBJECT,  "webBoltOns.client.components.CTableColumn");
		compClass.put(WindowItem.TABLE_COMBOBOX_OBJECT,  "webBoltOns.client.components.CTableColumn");
		compClass.put(WindowItem.TABLE_SPINNER_OBJECT,   "webBoltOns.client.components.CTableColumn");
		compClass.put(WindowItem.TABLE_IMAGE_OBJECT,     "webBoltOns.client.components.CTableColumn");
		compClass.put(WindowItem.TABLE_PWD_OBJECT,       "webBoltOns.client.components.CTableColumn");
		compClass.put(WindowItem.TABLE_TEXTFIELD_OBJECT, "webBoltOns.client.components.CTableColumn");
		compClass.put(WindowItem.TABLE_NAVROW_OBJECT,    "webBoltOns.client.components.CTableColumn");
		
		propertyRules.put(WindowItem.TEXT_LABEL, new String[] {
				"T", // 00 Component
				"F", // 01 Type
				"T", // 02 Label Description:
				"T", // 03 Field Name
				"T", // 04 Position
				"F", // 05 Data Type
				"F", // 06 Data Length
				"F", // 07 Decmails
				"F", // 08 Data Aligment
				"F", // 09 Input/output
				"F", // 10 Edit Mask
				"F", // 11 Length
				"F", // 12 Height
				"F", // 13 Width
				"F", // 14 Icon File Name:
				"F", // 15 Script Link
				"F", // 16 Link Method:
				"F", // 17 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation 
				"F", // 24 Default Value 
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method
				"F", // 28 Commit Class		
				"F", // 29 Encrypted	
		});		
		propertyRules.put(WindowItem.TEXT_FIELD_OBJECT, new String[] {
				"T", // 00 Component
				"F", // 01 Type
				"T", // 02 Label Description:
				"T", // 03 Field Name
				"T", // 04 Position
				"T", // 05 Data Type
				"T", // 06 Data Length
				"T", // 07 Decmails
				"T", // 08 Data Aligment
				"T", // 09 Input/output
				"T", // 10 Edit Mask
				"T", // 11 Length
				"F", // 12 Height
				"F", // 13 Width
				"F", // 14 Icon File Name:
				"T", // 15 Script Link
				"T", // 16 Link Method:
				"T", // 17 Link Class:
				"T", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation 
				"T", // 24 Default Value 
				"T", // 25 Hidden Object 
				"T", // 26 Zero Fill Value
				"T", // 27 Commit Method
				"T", // 28 Commit Class		
				"T", // 29 Encrypted	
		});		
		propertyRules.put(WindowItem.MACRO_EDITOR, new String[] {
				"T", // 00 Component
				"F", // 01 Type
				"T", // 02 Label Description:
				"T", // 03 Field Name
				"T", // 04 Position
				"T", // 05 Data Type
				"T", // 06 Data Length
				"T", // 07 Decmails
				"T", // 08 Data Aligment
				"T", // 09 Input/output
				"T", // 10 Edit Mask
				"T", // 11 Length
				"F", // 12 Height
				"F", // 13 Width
				"F", // 14 Icon File Name:
				"T", // 15 Script Link
				"T", // 16 Link Method:
				"T", // 17 Link Class:
				"T", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation 
				"T", // 24 Default Value 
				"T", // 25 Hidden Object 
				"T", // 26 Zero Fill Value
				"T", // 27 Commit Method
				"T", // 28 Commit Class		
				"T", // 29 Encrypted	
		});
		propertyRules.put(WindowItem.SEARCH_FIELD_OBJECT, new String[] {
				"T", // 00 Component
				"F", // 01 Type
				"T", // 02 Label Description:
				"T", // 03Field Name
				"T", // 04 Position
				"T", // 05 Data Type
				"T", // 06 Data Length
				"T", // 07 Decmails
				"T", // 08 Data Aligment
				"T", // 09 Input/output
				"T", // 10 Edit Mask
				"T", // 11 Length
				"F", // 12 Height
				"F", // 13 Width
				"F", // 14 Icon File Name:
				"T", // 15 Script Link
				"T", // 16 Link Method:
				"T", // 17 Link Class:
				"T", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation 
				"T", // 24 Default Value
				"F", // 25 Hidden Object
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method 
				"F", // 28 Commit Class
				"T", // 29 Encrypted	
		});
		propertyRules.put(WindowItem.PASSWORD_FIELD_OBJECT, new String[] {
				"T", // 00 Component
				"F", // 01 Type
				"T", // 02 Label Description:
				"T", // 03 Field Name
				"T", // 04 Position
				"F", // 05 Data Type
				"T", // 06 Data Length
				"F", // 07 Decmails
				"T", // 08 Data Aligment
				"F", // 09 Input/output
				"F", // 10 Edit Mask
				"T", // 11 Length
				"F", // 12 Height
				"F", // 13 Width
				"F", // 14 Icon File Name:
				"F", // 15 Script Link
				"F", // 16 Link Method:
				"F", // 17 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation 
				"F", // 24 Default Value 
				"T", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method
				"F", // 28 Commit Class	
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.PASSWORD_EDITOR_OBJECT, new String[] {
				"T", // 00 Component
				"F", // 01 Type
				"T", // 02 Label Description:
				"T", // 03 Field Name
				"T", // 04 Position
				"F", // 05 Data Type
				"T", // 06 Data Length
				"F", // 07 Decmails
				"T", // 08 Data Aligment
				"F", // 09 Input/output
				"F", // 10 Edit Mask
				"T", // 11 Length
				"F", // 12 Height
				"F", // 13 Width
				"F", // 14 Icon File Name:
				"F", // 15 Script Link
				"F", // 16 Link Method:
				"F", // 17 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation 
				"F", // 24 Default Value 
				"T", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method
				"F", // 28 Commit Class	
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.SPINNER_OBJECT, new String[] {
				"T", // 00Component
				"F", // 01 Type
				"T", // 02 Label Description:
				"T", // 03 Field Name
				"T", // 04 Position
				"F", // 05 Data Type
				"T", // 06 Data Length
				"F", // 07 Decmails
				"T", // 08 Data Aligment
				"F", // 09 Input/output
				"F", // 10 Edit Mask
				"T", // 11 Length
				"F", // 12 Height
				"F", // 13 Width
				"F", // 14 Icon File Name:
				"F", // 15 Script Link
				"F", // 16 Link Method:
				"F", // 17 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation 
				"T", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method 
				"F", // 28 Commit Class
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TEXT_AREA_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"T", // 04 Data Type
				"F", // 05 Data Length
				"F", // 06 Decmails
				"F", // 07 Data Aligment
				"T", // 08 Input/output
				"F", // 09 Edit Mask
				"F", // 10 Length
				"T", // 11 Height
				"T", // 12 Width
				"F", // 13 Icon File Name:
				"F", // 14 Script Link
				"F", // 15 Link Method:
				"F", // 16 Link Class:
				"F", // 17 Paramter Name
				"F", // 18 List Table:
				"F", // 19 List Field Name:
				"F", // 20 List Field Desc:
				"F", // 21 List Selection
				"F", // 23 Component Orientation 
				"T", // 24 Default Value 
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method 
				"F", // 28 Commit CLASS
				"T", // 28 Encrypted
		});
		propertyRules.put(WindowItem.TEXT_RTF_AREA_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 05 Data Length
				"F", // 06 Decmails
				"F", // 07 Data Aligment
				"T", // 08 Input/output
				"F", // 09 Edit Mask
				"F", // 10 Length
				"T", // 11 Height
				"T", // 12 Width
				"F", // 13 Icon File Name:
				"F", // 14 Script Link
				"F", // 15 Link Method:
				"F", // 16 Link Class:
				"F", // 17 Paramter Name
				"F", // 18 List Table:
				"F", // 19 List Field Name:
				"F", // 20 List Field Desc:
				"F", // 21 List Selection
				"F", // 23 Component Orientation 
				"T", // 24 Default Value 
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method 
				"F", // 28 Commit Class
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.LISTBOX_OBJECT, new String[] { 
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"T", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"T", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"T", // 19 List Table:
				"T", // 20 List Field Name:
				"T", // 21 List Field Desc:
				"T", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value 
				"F", // 25 Hidden Object
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method 
				"F", // 28 Commit Class
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.COMBOBOX_OBJECT, new String[] { 
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"T", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"T", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"T", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"T", // 19 List Table:
				"T", // 20 List Field Name:
				"T", // 21 List Field Desc:
				"T", // 22 List Selection
				"F", // 23 Component Orientation 
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Method 
				"F", // 28 Commit Class
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.COMBOITEM_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description
				"F", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"T", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"T", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.CHECKBOX_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put("BarButton", new String[] {
				"T", // Component
				"T", // 00 Type
				"T", // 01 Label Description:
				"F", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"T", // 12 Icon File Name:
				"T", // 13 Script Link
				"T", // 14 Link Method:
				"F", // 15 Link Class
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.BUTTON_OBJECT, new  String[] { 
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"T", // 12 Icon File Name:
				"T", // 13 Script Link
				"T", // 14 Link Method:
				"T", // 15 Link Class:
				"T", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.IMAGE_FIELD, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"T", // 13 Script Link
				"T", // 14 Link Method:
				"T", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});

		propertyRules.put(WindowItem.IMAGE_BOX_OBJECT, new String[] { 
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"T", // 07 Input/output
				"F", // 08 Edit Mask
				"T", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.MEDIA_BOX_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"T", // 07 Input/output
				"F", // 08 Edit Mask
				"T", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.RADIO_BUTTON_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"T", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABLE_CHECKBOX_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABLE_NAVROW_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"T", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABLE_COMBOBOX_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"T", // 06 Data Aligment
				"T", // 07 Input/output
				"F", // 08 Edit Mask
				"T", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"T", // 19 List Table:
				"T", // 20 List Field Name:
				"T", // 21 List Field Desc:
				"T", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABLE_SPINNER_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"T", // 06 Data Length
				"F", // 05 Decmails
				"T", // 06 Data Aligment
				"T", // 07 Input/output
				"F", // 07 Edit Mask
				"T", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABLE_IMAGE_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 07 Edit Mask
				"T", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABLE_PWD_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 07 Edit Mask
				"T", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABLE_TEXTFIELD_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"T", // 04 Data Type
				"T", // 06 Data Length
				"T", // 05 Decmails
				"T", // 06 Data Aligment
				"T", // 07 Input/output
				"T", // 08 Edit Mask
				"T", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"T", // 13 Script Link
				"T", // 14 Link Method:
				"T", // 15 Link Class:
				"T", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"T", // 26 Zero Fill Value
				"Y", // 27 Commit Class 
				"Y", // 28 Commit Method
				"T", // 29 Encrypted
		});
		propertyRules.put("ButtonBar", new String[] {
				"T", // Component
				"F", // 00 Type
				"F", // 01 Label Description:
				"F", // 02 Field Name
				"F", // 03 Position
				"F", // 06 Data Length
				"F", // 04 Data Type
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 22 List Field Desc:
				"F", // 21 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.PANEL_OBJECT, new String[] {
				"T", //  Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 22 List Field Desc:
				"F", // 21 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"T", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABGROUP_OBJECT, new String[] { 
				"T", // Component
				"F", // 00 Type
				"F", // 01 Label Description:
				"F", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"T", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABITEM_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"F", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"T", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});

		propertyRules.put(WindowItem.TAB_TABLE_OBJECT, new String[] { 
				"T", //  Component
				"F", // 00 Type
				"F", // 01 Label Description:
				"F", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"T", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		
		propertyRules.put(WindowItem.TABLE_DOC_MNGR_OBJECT, new String[] { 
				"T", //  Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"T", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"T", // 27 Commit Class 
				"T", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TABLE_OBJECT, new String[] { 
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"T", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.TREE_TABLE_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"T", // 14 Link Method:
				"F", // 15 Link Class:}
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.RADIO_BUTTON_GROUP_OBJECT,new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"T", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 21 List Selection
				"F", // 23 Component Orientation 
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"T", // 29 Encrypted
			});
		propertyRules.put(WindowItem.MENU_EXPLORER_OBJECT, new String[] {
				"T", // Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Length
				"F", // 04 Position
				"F", // 05 Data Type
				"F", // 06 Data Length
				"F", // 06 Decmails
				"F", // 07 Data Aligment
				"F", // 08 Input/output
				"F", // 09 Edit Mask
				"T", // 10 Height
				"T", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 22 List Field Desc:
				"F", // 21 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.SCRIPT_EDITOR_OBJECT, new String[] {
				"T", //Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 22 List Field Desc:
				"F", // 21 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.SCRIPT_DESIGNER_OBJECT, new String[] {
				"T", //Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 22 List Field Desc:
				"F", // 21 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.MENU_EDITOR_OBJECT, new String[] {
				"T", //Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});
		propertyRules.put(WindowItem.REPORT_EDITOR_OBJECT, new String[] {
				"T", //Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"F", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"T", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"F", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object 
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});

		propertyRules.put(WindowItem.FILE_PROMPT, new String[] {
				"T", //Component
				"F", // 00 Type
				"T", // 01 Label Description:
				"T", // 02 Field Name
				"T", // 03 Position
				"F", // 04 Data Type
				"F", // 06 Data Length
				"F", // 05 Decmails
				"F", // 06 Data Aligment
				"F", // 07 Input/output
				"F", // 08 Edit Mask
				"F", // 09 Length
				"F", // 10 Height
				"F", // 11 Width
				"F", // 12 Icon File Name:
				"F", // 13 Script Link
				"T", // 14 Link Method:
				"F", // 15 Link Class:
				"F", // 18 Paramter Name
				"F", // 19 List Table:
				"F", // 20 List Field Name:
				"F", // 21 List Field Desc:
				"F", // 22 List Selection
				"F", // 23 Component Orientation
				"F", // 24 Default Value
				"F", // 25 Hidden Object
				"F", // 26 Zero Fill Value
				"F", // 27 Commit Class 
				"F", // 28 Commit Method
				"F", // 29 Encrypted
		});	
	}
}

