package webBoltOns.client;

/*
 * $Id: MenuItem.java,v 1.1 2007/04/20 19:37:14 paujones2005 Exp $ $Name:  $
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


import javax.swing.tree.DefaultMutableTreeNode;
import webBoltOns.dataContol.DataSet;

/**
 * <CODE>MenuItem</CODE> 
 * 
 * <p> A menu component object </p>
 */

public class MenuItem extends WindowItem {

	public static final String CLASSNAME = "CLASS_NAME";
	public static final String DELETE_USER_DOCUMENTS = "DELETE_USER_DOCUMENTS";
	public static final String DOCUMENT_TO_LOAD_OBJECT = "Document";
	public static final String GET_ICONS = "GET_ICONS";
	public static final String GET_USER_DOCUMENTS = "GET_USER_DOCUMENTS";
	public static final String MENU_OBJECT = "Menu";
	public static final String METHODCLASS = "METHOD_CLASS";
	public static final String METHODNAME = "METHOD";
	public static final String OBJECT_NAME = "OBJECT_NAME";
	public static final String QUICKLINK = "QUICK_LINK";
	public static final String SCRIPT_TO_RUN_OBJECT = "Script";
	public static final String SCRIPTNAME = "SCRIPT_NAME";
	public static final String SUB_MENU_OBJECT = "SubMenu";
	private String menu = "";
	private boolean quicklink;
	private String scriptToRun = "";
	private DefaultMutableTreeNode treeNode;

	public MenuItem() {
	}
	
	/**
	 * <h2><code>getMenu</code></h2>
	 * @return String - the menu item 
	 */
	public String getMenu() {
		return menu;
	}
	
	
	/**
	 * <h2><code>getQuickLink</code></h2>
	 * @return String - Menu quick link flag 
	 */
	public boolean getQuickLink() {
		return quicklink;
	}
	
	
	/**
	 * <h2><code>getScriptToRun</code></h2>
	 * @return String - XML Script name to load 
	 */
	public String getScriptToRun() {
		return scriptToRun;
	}
	
	
	/**
	 * <h2><code>DefaultMutableTreeNode</code></h2>
	 * @return DefaultMutableTreeNode - menu item tree node 
	 */
	public DefaultMutableTreeNode getTreeNode() {
		return treeNode;
	}
	
	
	/**
	 * <h2><code>setMenu</code></h2>
	 * @param String mn - Menu Item 
	 */
	public void setMenu(String mn) {
		menu = mn;
	}
	
	
	/**
	 * <h2><code>setQuickLink</code></h2>
	 * @param boolean - sets the quick link flag 
	 */
	public void setQuickLink(boolean ql) {
		quicklink = ql;
	}
	
	
	/**
	 * <h2><code>setScriptToRun</code></h2>
	 * @param String - sets the XML script name 
	 */
	public void setScriptToRun(String mn) {
		scriptToRun = mn;
	}

	
	/**
	 * <h2><code>setTreeNode</code></h2>
	 * @param DefaultMutableTreeNode tn - Sets the Menu Tree Node   
	 */
	public void setTreeNode(DefaultMutableTreeNode tn) {
		treeNode = tn;
	}
	
	
	/**
	 * <h2><code>toString</code></h2>
	 * @return String - Returns menu description   
	 */
	public String toString() {
		return super.getDescription();
	}
	
	
	/**
	 * <h2><code>setObjectProperties</code></h2>
	 * 
	 *  <pre> Sets all object properties for the menu item </pre>
	 *  
	 * @param String object -  Menu properties
	 *
	 */
	public void  setObjectProperties(String object) {
		setObjectName(DataSet.parseProperty(OBJECT_NAME, object));
		setDescription(DataSet.parseProperty(DESCRIPTION, object));
		setScriptToRun(DataSet.parseProperty(LINK,object));
		setIconName(DataSet.parseProperty(ICON,object));
		setParentHL(Integer.parseInt(DataSet.parseProperty(PARENT_HL, object)));
        setUserAccessLevel(Integer.parseInt(DataSet.parseProperty(ACCESS_LEVEL, object)));
		if (DataSet.parseProperty(MenuItem.QUICKLINK, object).equals(
				"TRUE")) {
			setQuickLink(true);
		} else {
			setQuickLink(false);
		}
		setObjectHL(Integer.parseInt(DataSet.parseProperty(OBJECT_HL, object)));
	}
}
