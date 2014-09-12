package webBoltOns.client.components;

/*
 * $Id: CMenuExplorerContainer.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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

import java.util.Enumeration;
import java.util.Vector;

import webBoltOns.client.MenuItem;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;



public class CMenuExplorerContainer extends CTreeTableContainer  
                                       implements StandardComponentLayout {

	private static final long serialVersionUID = -4039467577038954520L;

	private Vector menuData;

	private Hashtable pointers;

	public CMenuExplorerContainer() {}

	
	
	public Vector getMenuRows(String parentRow) {
		Vector<Object[]> rows = new Vector<Object[]>();
		Enumeration eObjects = menuData.elements();

		String parentHL;
		if (parentRow.equals("[Top/]"))
			parentHL = "0";
		else
			parentHL = (String) pointers.get(parentRow);

		while (eObjects.hasMoreElements()) {
			String objectAttributeString = (String) eObjects.nextElement();
			if (DataSet.parseProperty(MenuItem.PARENT_HL,
					objectAttributeString).equals(parentHL)) {

				Object[] rowObject = new Object[5];
				if (DataSet.parseProperty(MenuItem.OBJECT_NAME,
						objectAttributeString).equals("SubMenu"))
					rowObject[0] = Boolean.FALSE;
				else
					rowObject[0] = Boolean.TRUE;
				rowObject[1] = DataSet.parseProperty(
						MenuItem.DESCRIPTION, objectAttributeString);
				rowObject[2] = DataSet.parseProperty(MenuItem.LINK,
						objectAttributeString);
				rowObject[3] = DataSet.parseProperty(
						MenuItem.OBJECT_NAME, objectAttributeString);
				int access = Integer.parseInt(DataSet.parseProperty(
						MenuItem.ACCESS_LEVEL, objectAttributeString));
				if (access == 3)
					rowObject[4] = "Inquiry / Update";
				else if ((access == 2))
					rowObject[4] = "Inquiry Only";
				else
					rowObject[4] = "No Access";
				rows.add(rowObject);
			}
		}
		return rows;
	}

	public DataSet populateDataSet(String action, String field, DataSet dataSet) {
		Enumeration tableData = super.getTreeDataRows().elements();
		Vector<String[]> tableSet = new Vector<String[]>();
		while (tableData.hasMoreElements()) {
			Object[] objectRow = (Object[]) tableData.nextElement();
			String[] stringRow = new String[4];
			stringRow[0] = (String) objectRow[1];
			stringRow[1] = (String) objectRow[2];
			stringRow[2] = (String) objectRow[3];
			if (((String) objectRow[4]).equals("Inquiry / Update"))
				stringRow[3] = "3";
			else if (((String) objectRow[4]).equals("Inquiry Only"))
				stringRow[3] = "2";
			else
				stringRow[3] = "1";

			tableSet.add(stringRow);
		}
		dataSet.putTableVector("[MenuDataTop/]", tableSet);
		return dataSet;
	}

  	
	public boolean locateCursor() {
		return false;
	}
  	
	public void populateComponent(String action, String tableName, DataSet menuSet) {
		menuData = (Vector) menuSet.get(MenuItem.SCREEN_OBJECTS);
		if (menuData != null) {
			if (action == null
					|| (!action.equals(WindowItem.EDIT_RECORD) && !action
							.equals(WindowItem.DELETE_RECORD))) {
				Enumeration eObjects = menuData.elements();
				pointers = new Hashtable();
				while (eObjects.hasMoreElements()) {
					String objectAttributeString = (String) eObjects
							.nextElement();
					pointers.put(DataSet.parseProperty(
							MenuItem.DESCRIPTION, objectAttributeString),
							DataSet.parseProperty(MenuItem.OBJECT_HL,
									objectAttributeString));
				}
				super.expandTable(getMenuRows("[Top/]"));
			}
		}
	}
}