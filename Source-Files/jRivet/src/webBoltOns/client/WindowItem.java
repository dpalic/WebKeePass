package webBoltOns.client;

import javax.swing.JComponent;
import javax.swing.JLabel;

import webBoltOns.dataContol.DataSet;

/*
 * $Id: WindowItem.java,v 1.1 2007/04/20 19:37:14 paujones2005 Exp $ $Name:  $
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

public class WindowItem {
	public static final String ACCESS_LEVEL = "ACCESS_LEVEL";
	public static final String ACTION = "ACTION";
	public static final String ALIGNMENT = "ALIGNMENT";
	public static final String AUTO_REFRESH = "AUTO_REFRESH";
	public static final String BANNER_TITLE = "Banner_Title";
	public static final String BAR_BUTTON_OBJECT = "BarButton";
	public static final String BOTTOM = "Bottom";
	public static final String BUTTON_OBJECT = "Button";
	public static final String CENTER = "Center";
	public static final String CENTER_FILL = "Center Fill";
	public static final String CHECKBOX_OBJECT = "CheckBox";
	public static final String CLASSNAME = "CLASS_NAME";
	public static final String CLEAR = "CLEAR";
	public static final String COMBOBOX_OBJECT = "ComboBox";
	public static final String COMBOITEM_OBJECT = "ComboItem";
	public static final String COMMIT_CLASS = "COMMIT_CLASS";
	public static final String COMMIT_METHOD = "COMMIT_METHOD";
	public static final String COMPONENT_ORIENTATION = "COMPONENT_ORIENTATION";
	public static final String DATALENGTH = "DATA_LEN";
	public static final String DATATYPE = "DATA_TYPE";
	public static final String DECIMALS = "DECIMALS";
	public static final String DEFAULT_VALUE = "DEFAULT_VALUE";
	public static final String DELETE_LINE = "DELETE_LINE";
	public static final String DELETE_RECORD = "DELETE_RECORD";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String DOCUMENT_TYPE = "DOCUMENT_TYPE";
	public static final String EDIT_RECORD = "EDIT_RECORD";
	public static final String EDITMASK = "EDITMASK";
	public static final String ENCRYPT = "ENCRYPT";
	public static final String EXPAND_RECORD = "EXPAND_RECORD";
	public static final String FIELDNAME = "FIELDNAME";
	public static final String FILE_PROMPT = "FilePrompt";
	public static final String FIND_RECORD = "FIND_RECORD";
	public static final String FINDLINK = "FINDLINK";
	public static final String GET_BATCH = "EXECBATCH";
	public static final String GET_EMAIL = "EMAIL";
	public static final String GET_MENU = "GET_MENU";
	public static final String GET_RUN = "RUNPGM";
	public static final String GET_SCRIPT = "GET_SCRIPT";
	public static final String GET_URL = "URL";
	
	public static final String CLIP_FIELD = "CLIP_FIELD";
	
	public static final String POST_FILE_UPLOAD = "FILE_UPLOAD";
	
	public static final String HEIGHT = "HEIGHT";
	public static final String HIDDEN = "HIDDEN";
	public static final String ICON = "ICON";
	public static final String IMAGE_BOX_OBJECT = "ImageBox";
	public static final String IMAGE_FIELD = "ImageField";
	public static final String TEXT_LABEL = "TextLabel";
	public static final String LEFT = "Left";
	public static final String LEFT_FILL = "Left Fill";
	public static final String LENGTH = "LENGTH";
	public static final String LINK = "LINK";
	public static final String LIST_TABLE = "LIST_TABLE_NAME";
	public static final String LIST_TABLE_DESCRIPTION = "LIST_TABLE_DESCRIPTION";
	public static final String LIST_TABLE_FIELD = "LIST_TABLE_FIELD";
	public static final String LIST_TABLE_SELECTION = "LIST_TABLE_SELECTTION";
	public static final String TABLE_NAVROW_OBJECT = "TableNavRow";
	public static final String LISTBOX_OBJECT = "ListBox";
	public static final String MEDIA_BOX_OBJECT = "MediaBox";
	public static final String MENU_EDITOR_OBJECT = "MenuEditor";
	public static final String MENU_EXPLORER_OBJECT = "MenuExplorer";
	public static final String MACRO_EDITOR = "MacroEditor";
	public static final String MESSAGE = "MESSAGE";
	public static final String METHOD = "METHOD";
	public static final String METHODCLASS = "METHOD_CLASS";
	public static final String MORE_RECORD = "MORE_RECORD";
	public static final String NEW_LINE = "NEW_LINE";
	public static final String NEWLINE = "NEWLINE";
	public static final String NEXT_RECORD = "NEXT_RECORD";
	public static final String OBJECT_HL = "OBJECT_HL";
	public static final String OBJECT_NAME = "OBJECT_NAME";
	public static final String PANEL_OBJECT = "Panel";
	public static final String PARAMETER = "PARAMETER";
	public static final String PARENT_HL = "PARENT_HL";
	public static final String PASSWORD_FIELD_OBJECT = "PasswordField";
	public static final String PASSWORD_EDITOR_OBJECT = "PasswordEditor";
	public static final String POSITION = "POSITION";
	public static final String POST_LINE = "POST_LINE";
	public static final String POST_RECORD = "POST_RECORD";
	public static final String PREV_RECORD = "PREV_RECORD";
	public static final String PRINT_RECORD = "PRINT_RECORD";
	public static final String PROTECTED = "PROTECTED";
	public static final String RADIO_BUTTON_GROUP_OBJECT = "RadioButtonGroup";
	public static final String RADIO_BUTTON_OBJECT = "RadioButton";
	public static final String REPORT_EDITOR_OBJECT = "ReportEditor";
	public static final String RIGHT = "Right";
	public static final String RIGHT_FILL = "Right Fill";
	public static final String SCREEN_OBJECTS = "Screen_Objects";
	public static final String SCREEN_TITLE = "Screen_Title";
	public static final String SCRIPT_DESIGNER_OBJECT = "ScriptDesigner";
	public static final String SCRIPT_EDITOR_OBJECT = "ScriptEditor";
	public static final String SCRIPTNAME = "SCRIPT_NAME";
	public static final String SEARCH = "SEARCH";
	public static final String SEARCH_FIELD_OBJECT = "SearchTextField";
	public static final String SPINNER_OBJECT = "SpinnerField";
	public static final String STD_COMMIT = "STD_COMMIT";
	public static final String STD_PROMPT = "STD_PROMPT";
	public static final String TAB_TABLE_OBJECT = "TabTable";
	public static final String TABGROUP_OBJECT = "TabGroup";
	public static final String TABITEM_OBJECT = "TabItem";
	public static final String TABLE_CHECKBOX_OBJECT = "TableCheckBox";
	public static final String TABLE_COMBOBOX_OBJECT = "TableComboBox";
	public static final String TABLE_IMAGE_OBJECT = "TableImageField";
	public static final String TABLE_PWD_OBJECT = "TablePwdField";
	public static final String TABLE_OBJECT = "Table";
	public static final String TABLE_PROMPT = "TABLE_PROMPT";
	public static final String TABLE_SPINNER_OBJECT = "TableSpinnerField";
	public static final String TABLE_TEXTFIELD_OBJECT = "TableTextField";
	public static final String TABLE_DOC_MNGR_OBJECT = "DocumentManagerTable";
	public static final String TEXT_AREA_OBJECT = "TextArea";
	public static final String TEXT_FIELD_OBJECT = "TextField";
	public static final String TEXT_RTF_AREA_OBJECT = "TextRTFArea";
	public static final String TOP = "Top";
	public static final String TOTAL_OBJECTS = "Total_Objects";
	public static final String TREE_TABLE_OBJECT = "TreeTable";
	public static final String TYPE = "TYPE";
	public static final String WIDTH = "WIDTH";
	public static final String ZERO_FILL = "ZERO_FILL";
	
	protected String classForName;
	protected JComponent componentObject;

	protected String description, screenposition, editmask, datatype, link,
		findlink, method, iconFileName, fieldParameterName, methodClass,
		fieldname, objectname, componentOrientation, defaultValue,
			commitMethod, commitClass;

	protected String headings[];

	protected int length, decimals, height, width, datalength, cellalignment;

	protected int parent_hl, object_hl, grantedAccessLevel;

	protected boolean protect, hidden, zeroFill, encrypt;

	public WindowItem() { }

	public int getCellAlignment() {
		return cellalignment;
	}

	public String getClassForName() {
		return classForName;
	}

	public String getCommitClass() {
		return commitClass;
	}

	public String getCommitMethod() {
		return commitMethod;
	}

	public JComponent getComponentObject() {
		return componentObject;
	}

	public int getDataLength() {
		return datalength;
	}

	public String getDataType() {
		return datatype;
	}

	public int getDecimals() {
		return decimals;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getDescription() {
		return description;
	}

	public String getEditMask() {
		return editmask;
	}

	public String getFieldName() {
		return fieldname;
	}

	public String getFieldParameterName() {
		return fieldParameterName;
	}

	public String getFindLink() {
		return findlink;
	}

	public String[] getHeadings() {
		return headings;
	}

	public int getHeight() {
		return height;
	}

	public String getIconName() {
		return iconFileName;
	}

	public int getLength() {
		return length;
	}

	public String getLink() {
		if (grantedAccessLevel > 1)
			return link;
		else
			return "";

	}

	public String getMethod() {
		return method;
	}

	public String getMethodClass() {
		return methodClass;
	}

	public int getObjectHL() {
		return object_hl;
	}

	public String getObjectName() {
		return objectname;
	}

	public String getOrientation() {
		return componentOrientation;
	}

	public int getParentHL() {
		return parent_hl;
	}

	public String getPosition() {
		return screenposition;
	}

	public int getUserAccessLevel() {
		return grantedAccessLevel;

	}

	public int getWidth() {
		return width;
	}

	public boolean isEncrypted() {
		return encrypt;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean isProtected() {
		return protect;
	}

	public boolean isZeroFilled() {
		return zeroFill;
	}

	public void setCellAlignment(int ps) {
		cellalignment = ps;
	}

	public void setClassForName(String cls) {
		classForName = cls;
	}

	public void setCommitClass(String cc) {
		commitClass = cc;
	}

	public void setCommitMethod(String cm) {
		commitMethod = cm;
	}

	public void setComponentObject(JComponent cm) {
		componentObject = cm;
	}

	public void setDataLength(int ln) {
		datalength = ln;
	}

	public void setDataType(String dt) {
		datatype = dt;
	}

	public void setDecimals(int d) {
		decimals = d;
	}

	public void setDefaultValue(String dt) {
		defaultValue = dt;
	}

	public void setDescription(String ds) {
		description = ds;
	}

	public void setEditMask(String ed) {
		editmask = ed;
	}

	public void setEncrypted(boolean en) {
		encrypt = en;
	}

	public void setFeildParameter(String pn) {
		fieldParameterName = pn;
	}

	public void setFieldName(String fn) {
		fieldname = fn;
	}

	public void setFindLink(String lk) {
		findlink = lk;
	}

	public void setHeadings(String[] hd) {
		headings = hd;
	}

	public void setHeight(int h) {
		height = h;
	}

	public void setHidden(boolean h) {
		hidden = h;
	}

	public void setIconName(String ic) {
		iconFileName = ic;
	}

	public void setLength(int ln) {
		length = ln;
	}

	public void setLink(String lk) {
		link = lk;
	}

	public void setMethod(String mt) {
		method = mt;
	}

	public void setMethodClass(String mtc) {
		methodClass = mtc;
	}

	public void setObjectHL(int hl) {
		object_hl = hl;
	}

	public void setObjectName(String on) {
		objectname = on;
	}


	public void setOrientation(String co) {
		componentOrientation = co;
	}

	public void setParentHL(int hl) {
		parent_hl = hl;
	}

	public void setPostion(String ps) {
		screenposition = ps;
	}

	public void setProtected(boolean p) {
		protect = p;
	}

	public void setUserAccessLevel(int grantedAccessLevel) {
		this.grantedAccessLevel = grantedAccessLevel;
	}

	public void setWidth(int w) {
		width = w;
	}

	public void setZeroFilled(boolean zf) {
		zeroFill = zf;
	}

	public String toString() {
		return description;
	}
	public void setObjectProperties(String objPrm) {
		setObjectName(DataSet.parseProperty(OBJECT_NAME, objPrm));
		setParentHL(Integer.parseInt(DataSet.parseProperty(PARENT_HL, objPrm)));
		setObjectHL(Integer.parseInt(DataSet.parseProperty(OBJECT_HL, objPrm)));
		setUserAccessLevel(Integer.parseInt(DataSet.parseProperty(ACCESS_LEVEL, 	objPrm)));
		setDescription(DataSet.parseProperty(DESCRIPTION, objPrm));
		setMethod(DataSet.parseProperty(METHOD, objPrm));
		setMethodClass(DataSet.parseProperty(METHODCLASS, objPrm));
		setFieldName(DataSet.parseProperty(FIELDNAME, objPrm));
		setLength(DataSet.checkInteger(DataSet.parseProperty(LENGTH, objPrm)));
		setDecimals(DataSet.checkInteger(DataSet .parseProperty(DECIMALS, objPrm)));
		setHeight(DataSet.checkInteger(DataSet.parseProperty(HEIGHT, objPrm)));
		setWidth(DataSet.checkInteger(DataSet.parseProperty(WIDTH, objPrm)));
		setIconName(DataSet.parseProperty(ICON, objPrm));
		setLink(DataSet.parseProperty(LINK, objPrm));
		setFeildParameter(DataSet.parseProperty(WindowItem.PARAMETER, objPrm));
		setEditMask(DataSet.parseProperty(EDITMASK, objPrm));
		setPostion(DataSet.parseProperty(WindowItem.POSITION, objPrm));
		setOrientation(DataSet.parseProperty(WindowItem.COMPONENT_ORIENTATION, objPrm));
		setDefaultValue(DataSet.parseProperty(WindowItem.DEFAULT_VALUE, objPrm));
		setCommitMethod(DataSet.parseProperty(WindowItem.COMMIT_METHOD, objPrm));
		setCommitClass(DataSet.parseProperty(WindowItem.COMMIT_CLASS, objPrm));
		String dType = DataSet.parseProperty(DATATYPE, objPrm);
		String dLength = DataSet.parseProperty(DATALENGTH, objPrm);

		if (dType.equals(""))
			setDataType("CHR");
		else
			setDataType(dType.substring(0, 3));

		if (dLength.equals("") || dLength.equals("0"))
			setDataLength(50);
		else
			setDataLength(Integer.parseInt(dLength));

		if (DataSet.parseProperty(PROTECTED, objPrm).equals("Y"))
			setProtected(true);
		else
			setProtected(false);

		if (DataSet.parseProperty(HIDDEN, objPrm).equals("Y"))
			setHidden(true);
		else
			setHidden(false);

		if (DataSet.parseProperty(ZERO_FILL, objPrm).equals("Y"))
			setZeroFilled(true);
		else
			setZeroFilled(false);
		
		if (DataSet.parseProperty(ENCRYPT, objPrm).equals("Y"))
			setEncrypted(true);
		else
			setEncrypted(false);
		
		if (DataSet.parseProperty(ALIGNMENT, objPrm).equals(RIGHT))
			setCellAlignment(JLabel.RIGHT);
		else if (DataSet.parseProperty(ALIGNMENT, objPrm).equals(
				WindowItem.CENTER))
			setCellAlignment(JLabel.CENTER);
		else
			setCellAlignment(JLabel.LEFT);
	}

}