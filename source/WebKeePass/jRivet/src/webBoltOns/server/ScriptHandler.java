package webBoltOns.server;

/*
 * $Id: ScriptHandler.java,v 1.1 2007/04/20 19:37:15 paujones2005 Exp $ $Name:  $
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



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Stack;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import webBoltOns.client.MenuItem;
import webBoltOns.client.WindowItem;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;

@SuppressWarnings("deprecation")

public class ScriptHandler extends HandlerBase {
	private DataAccess dataAccess;
	private String handlerMode;
	private int hl = 0, par_hl = 0, acsLvl = 1;
	private String title, scrpt, mthd, clss,
			hght, wdth, refesh, docType, icn;
	private Stack<String> stck = new Stack<String>();
	private DataSet tbl = new DataSet();
	private UserSecurityManager usrScrtyMngr;
	private Vector<String> vf = new Vector<String>();
	private boolean editMode = false;
	private String user = "", scmbl = "";

	public ScriptHandler(DataAccess da, UserSecurityManager usm, String m, boolean e) {
		stck.push("0");
		dataAccess = da;
		usrScrtyMngr = usm;
		handlerMode = m;
		editMode = e;
	}

	// Called when character data is found inside an element
	public void characters(char[] ch, int start, int length)
			throws SAXException {

	}

	// Called when when the end of element is encountered
	public void endElement(String name) throws SAXException {
		if (!name.equals("SCRIPT")) {
			stck.pop();
		}
	}


	
	private String formatAttributeValue(String type, String key, AttributeList attrs) {
		
		String attributeValue = attrs.getValue(key);
		if (attributeValue != null) {
			return attributeValue;
		} else {
			if (type.equals("[Integer/]"))
				return "0";
			else if (type.equals("[DataType/]"))
				return "CHR";
			else if (type.equals("[Flag/]"))
				return "N";
			else if (type.equals("[Boolean/]"))
				return "False";
			else
				return "";
		}
	}

	
	private String buildAttribute(String type, String key, 	AttributeList attrs) {
	 return key + "='" + formatAttributeValue(type,key,attrs) +"' ";
	}
	
	public boolean isValidXMLType(String type){
		if(docType.equals(""))
			return true;
		if(type.equals("[SCREEN_SCRIPT/]") && docType.equals("Window"))
			return true;
		if(type.equals("[MENU_SCRIPT/]") && docType.equals("Menu"))
			return true;
		return false;
	}
	
	
	// Simple Accessor for the DataSet of parsed values
	public DataSet getMenuTable() {
		tbl.put(WindowItem.SCREEN_OBJECTS, vf);
		tbl.put(WindowItem.TOTAL_OBJECTS, Integer.toString(hl));
		tbl.put(WindowItem.HEIGHT, hght);
		tbl.put(WindowItem.WIDTH, wdth);
		tbl.putStringField(MenuItem.CLASSNAME, clss);
		tbl.putStringField(MenuItem.SCRIPTNAME, scrpt);
		tbl.putStringField(MenuItem.BANNER_TITLE, dataAccess.getBannerTitle());
		tbl.putStringField(MenuItem.METHODNAME, mthd);
		tbl.putStringField(WindowItem.SCREEN_TITLE, title); 
		tbl.putStringField("[logoTitle/]", dataAccess.getWindowTitle());
		return tbl;
	}

	
	// Simple Accessor for the DataSet of parsed values
	public DataSet getScriptTable() {		
		tbl.put(WindowItem.SCREEN_OBJECTS, vf);
		tbl.putIntegerField(WindowItem.TOTAL_OBJECTS, hl);
		tbl.putStringField(WindowItem.SCREEN_TITLE, title);
		tbl.put(WindowItem.HEIGHT, hght);
		tbl.putStringField(WindowItem.WIDTH, wdth);
		tbl.putStringField(WindowItem.CLASSNAME, clss);
		tbl.putStringField(WindowItem.SCRIPTNAME, scrpt);
		tbl.putStringField(WindowItem.METHOD, mthd);
		tbl.putStringField(WindowItem.AUTO_REFRESH, refesh);
		tbl.putStringField(WindowItem.ICON, icn);
		tbl.putStringField(WindowItem.BANNER_TITLE, dataAccess .getBannerTitle());
		tbl.putStringField("[logoTitle/]", dataAccess.getWindowTitle());
		tbl.putIntegerField("[ScriptAccessLevel/]", acsLvl);
		return tbl;
	}

	
	// Simple Accessor for the DataSet of parsed values
	public void setTable(DataSet table) {
		this.tbl = table;
	}

	// Called when a new element is encountered
	public void startElement(String tag, AttributeList attrs)
			throws SAXException {
			String iconFileName = formatAttributeValue("", WindowItem.ICON, attrs);
			String scriptLink = formatAttributeValue("", WindowItem.LINK, attrs);
			String linkList = formatAttributeValue("",WindowItem.LIST_TABLE, attrs);
			String linkListField = formatAttributeValue("", WindowItem.LIST_TABLE_FIELD, attrs);
			String linkListDesc = formatAttributeValue("", WindowItem.LIST_TABLE_DESCRIPTION, attrs);
			String linkListSelection = formatAttributeValue("", WindowItem.LIST_TABLE_SELECTION, attrs);
			hl++;
			
			par_hl = Integer.parseInt(stck.peek().toString());     
			if (tag.equals("SCRIPT")) 
				setScriptAttributes(attrs);
			else if (tag.equals("BarButton")) 
				setBarButtonAttributes(attrs);
			else { 
				int itemAccessLevel;
				if(usrScrtyMngr != null && dataAccess.isSecurityManagerON())
					itemAccessLevel = usrScrtyMngr.grantItemAccess(scriptLink, dataAccess);			
				else 
					itemAccessLevel = 3;
		
				StringBuffer object = new StringBuffer("[ ");
				if (handlerMode.equals("[SCREEN_SCRIPT/]")) {
					object.append(WindowItem.OBJECT_NAME + "='" + tag + "' ");
					object.append(buildAttribute("", WindowItem.FIELDNAME, attrs));
					object.append(buildAttribute("", WindowItem.DESCRIPTION, attrs));
					object.append(buildAttribute("", WindowItem.METHODCLASS, attrs));
					object.append(buildAttribute("", WindowItem.METHOD, attrs));
					object.append(buildAttribute("", WindowItem.PARAMETER, attrs));
					object.append(buildAttribute("", WindowItem.COMMIT_CLASS, attrs));
					object.append(buildAttribute("", WindowItem.COMMIT_METHOD, attrs));

                    object.append(buildAttribute("[Integer/]", WindowItem.LENGTH, attrs));
					object.append(buildAttribute("[Integer/]", WindowItem.HEIGHT, attrs));
					object.append(buildAttribute("[Integer/]", WindowItem.WIDTH, attrs));
					object.append(buildAttribute("[Integer/]", WindowItem.DECIMALS, attrs));
					object.append(buildAttribute("[Flag/]", WindowItem.PROTECTED, attrs));
					object.append(buildAttribute("[DataType/]", WindowItem.DATATYPE, attrs));
					object.append(buildAttribute("[Integer/]", WindowItem.DATALENGTH, attrs));
					object.append(buildAttribute("", WindowItem.EDITMASK, attrs));
					object.append(buildAttribute("", WindowItem.POSITION, attrs));
					object.append(buildAttribute("", WindowItem.ALIGNMENT, attrs));
					object.append(buildAttribute("[Flag/]", WindowItem.ENCRYPT, attrs));
					object.append(buildAttribute("", WindowItem.COMPONENT_ORIENTATION, attrs)); 
					object.append(buildAttribute("", WindowItem.DEFAULT_VALUE, attrs));   		
					object.append(buildAttribute("[Flag/]", WindowItem.HIDDEN, attrs)); 		
					object.append(buildAttribute("[Flag/]", WindowItem.ZERO_FILL, attrs));   		
					
					object.append(WindowItem.LIST_TABLE + "='" + linkList + "' ");
					object.append(WindowItem.LIST_TABLE_FIELD + "='" + linkListField + "' ");
					object.append(WindowItem.LIST_TABLE_DESCRIPTION + "='" + linkListDesc + "' ");
					object.append(WindowItem.LIST_TABLE_SELECTION + "='" + linkListSelection + "' ");

					object.append(WindowItem.LINK + "='" + scriptLink + "' ");
					object.append(WindowItem.ICON + "='" + iconFileName + "' ");
					
					object.append(WindowItem.OBJECT_HL + "='" + hl + "' " );
					object.append(WindowItem.PARENT_HL + "='" + par_hl + "' ");
					object.append(WindowItem.ACCESS_LEVEL + "='" + itemAccessLevel + "'/]");

				} else if (handlerMode.equals("[MENU_SCRIPT/]")) {

					object.append(MenuItem.OBJECT_NAME + "='" + tag + "' ");
					object.append(buildAttribute("", MenuItem.DESCRIPTION, attrs));
					object.append(buildAttribute("", MenuItem.METHODNAME, attrs));
					object.append(buildAttribute("", MenuItem.METHODCLASS, attrs));
					object.append(buildAttribute("", MenuItem.QUICKLINK, attrs));
					
					object.append(MenuItem.LINK + "='" + scriptLink + "'  ");
					object.append(MenuItem.ICON + "='" + iconFileName + "'  ");
					object.append(MenuItem.OBJECT_HL + "='" + hl + "'  ");
					object.append( MenuItem.PARENT_HL + "='" + par_hl + "' " );
					object.append(MenuItem.ACCESS_LEVEL + "='" + itemAccessLevel + "'/]");
				}
				vf.addElement(object.toString());
				stck.push(Integer.toString(hl));
				if(!editMode && !linkList.equals(""))
					buildLinkedList(linkList, linkListField, 
							linkListDesc, iconFileName, linkListSelection);
			}
		}
	
	
	
	
	    private void buildLinkedList(String linkListTable, String linkField, 
	    		                            String linkDesc, String linkIcon, String linkSelection ){
			
	    	Statement qry = null;
	    	ResultSet resultSet;
			try {
				qry = dataAccess.execConnectReadOnly();
				String sql = "Select " + linkField;
				
				if(!linkDesc.equals(""))
					sql += " , " + linkDesc;				
				
				if(!linkIcon.equals(""))
					sql += " , " + linkIcon;
			
				sql += " From " + linkListTable + " ";
				
				if(!linkSelection.equals("") ) {
					linkSelection = linkSelection.replaceAll(":", "'");
					linkSelection = linkSelection.replaceAll("User", user).trim();
					linkSelection = linkSelection.replaceAll("Scmbl", scmbl.trim()); 
					sql += " Where " + linkSelection + " ";					
				}
				
				resultSet = qry.executeQuery(sql);
				par_hl = hl;
				
				while (resultSet.next()) {
					String item = "", desc ="", icon="";
					item = (String) DataAccess.getFieldValue(resultSet,linkField,"CHR");
					if(!linkDesc.equals(""))
						desc = (String) DataAccess.getFieldValue(resultSet,linkDesc,"CHR");
					if(!linkIcon.equals(""))
						icon = (String) DataAccess.getFieldValue(resultSet,linkIcon,"CHR");
					
					
					hl++;
					StringBuffer object = new StringBuffer("[");
					object.append(WindowItem.OBJECT_NAME + "='" + WindowItem.COMBOITEM_OBJECT + "' ");
					object.append(WindowItem.DESCRIPTION + "='" + desc + "' ");
					object.append(WindowItem.PROTECTED + "='N' ");
					object.append(WindowItem.DATATYPE + "='CHR' ");
					object.append(WindowItem.DEFAULT_VALUE + "='"+ item + "' ");
					object.append(WindowItem.ICON + "='" + icon + "' ");
					object.append(WindowItem.OBJECT_HL + "='" + hl + "' " );
					object.append(WindowItem.PARENT_HL + "='" + par_hl + "' ");
					object.append(WindowItem.ACCESS_LEVEL + "='3'/]");
										
					vf.addElement(object.toString());						
				}
				resultSet.close();
				} catch (Exception er) {
					dataAccess.logMessage(" *ScriptJandler.getLinkedList*  --  " + er ); 
				} finally {
					dataAccess.execClose(qry);
				}
	    }
	
		private void 	setScriptAttributes(AttributeList attrs) {
			scrpt = formatAttributeValue("", WindowItem.SCRIPTNAME, attrs);
			clss = formatAttributeValue("", WindowItem.CLASSNAME, attrs);
			title = formatAttributeValue("", WindowItem.DESCRIPTION, attrs);
			hght = formatAttributeValue("[Integer/]", WindowItem.HEIGHT, attrs);
			wdth = formatAttributeValue("[Integer/]", WindowItem.WIDTH, attrs);
			mthd = formatAttributeValue("", WindowItem.METHOD,attrs);
			refesh = formatAttributeValue("[Boolean/]", WindowItem.AUTO_REFRESH, attrs);
			icn = formatAttributeValue("", WindowItem.ICON,attrs);
			docType = formatAttributeValue("",WindowItem.DOCUMENT_TYPE, attrs);
			
			if(usrScrtyMngr != null &&  dataAccess.isSecurityManagerON())
				acsLvl = usrScrtyMngr.grantScriptAccess(scrpt, dataAccess );
			else 
				acsLvl = 3;
		}
		
		
		
		private void 	setBarButtonAttributes(AttributeList attrs) {
			String type = attrs.getValue(WindowItem.TYPE);
			tbl.put("[" + type + "_Icon/]", formatAttributeValue("", "ICON", attrs));
			tbl.put("[" + type + "_Method/]", formatAttributeValue("",WindowItem.METHOD, attrs));
			tbl.put("[" + type + "_Desc/]", formatAttributeValue("",WindowItem.DESCRIPTION, attrs));
			tbl.put("[" + type + "_MethodClass/]", formatAttributeValue("",WindowItem.METHODCLASS, attrs));
			tbl.put("[" + type + "_Link/]", formatAttributeValue("", WindowItem.LINK, attrs));
			
			stck.push(Integer.toString(hl));
		}
 	 
		
	 	public void loadScript(String script, DataSet ds) throws   ParserConfigurationException, 
	 	                  SAXException, IOException {
			
			user = ds.getUser(); 
			scmbl = ds.getScmbl(); 
			File file = new File(script);
			FileReader reader = new FileReader(file);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			sp.parse(new InputSource(reader), this);
	 	}
 }
		
		
		
		
 	