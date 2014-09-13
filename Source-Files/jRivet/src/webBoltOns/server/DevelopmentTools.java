package webBoltOns.server;

/*
 * $Id: DevelopmentTools.java,v 1.1 2007/04/20 19:37:15 paujones2005 Exp $ $Name:  $
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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import webBoltOns.client.WindowItem;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;

import com.sun.xml.tree.XmlDocument;

public class DevelopmentTools {

 	public final static int NONE = 1;

	public final static int READ = 2;

	public final static int READ_WRITE = 3;

	protected String requesterUserName, requesterGroupName;

	public final String os = System.getProperty("os.name"),
	                    fs = System.getProperty("file.separator");   
    
	public DevelopmentTools() {
	}

    public DataSet getMenuScriptDetails(DataSet script, DataAccess dataAccess) {
    	script.putStringField("TypeID", "[MENU_SCRIPT/]");
    	script = getScriptDetails(script, dataAccess);
    	return script;
    }
	
    
    public DataSet getScreenScriptDetails(DataSet script, DataAccess dataAccess) {
    	script.putStringField("TypeID", "[SCREEN_SCRIPT/]");
    	script = getScriptDetails(script, dataAccess);
    	return script;
    }
	
 	public DataSet getScriptDetails(DataSet script, DataAccess dataAccess) {
		String scriptFileName = script.getStringField("ScriptID"),
			docType = script.getStringField("TypeID"),
			docPath;
		DataSet scriptTable;
		try {
			docPath = dataAccess.getScriptPath() + "ENG" + fs + scriptFileName + ".xml";
			File file = new File(docPath);
			FileReader reader = new FileReader(file);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			ScriptHandler handler = new ScriptHandler(dataAccess, null, docType, true);
			sp.parse(new InputSource(reader), handler);
						
			if (docType.equals("[SCREEN_SCRIPT/]") && handler.isValidXMLType(docType)) {	
				scriptTable = handler.getScriptTable();
				script.putStringField("TitleID", scriptTable.getStringField("[WINDOW_TITLE/]"));
				script.putStringField("BannerID", scriptTable.getStringField(WindowItem.BANNER_TITLE));
				script.putStringField("DescID", scriptTable.getStringField(WindowItem.SCREEN_TITLE));
				script.putStringField("ClassID", scriptTable.getStringField(WindowItem.CLASSNAME));
				script.putStringField("MethodID", scriptTable.getStringField(WindowItem.METHOD));		
				script.putIntegerField("Hght", scriptTable.getIntegerField(WindowItem.HEIGHT));
				script.putIntegerField("Wdth", scriptTable.getIntegerField(WindowItem.WIDTH));
				script.putBooleanField("aFresh", scriptTable.getBooleanField(WindowItem.AUTO_REFRESH));		
				script.putStringField("aIcon", scriptTable.getStringField(WindowItem.ICON));		
				script.put("SEditor", scriptTable);
			   
			} else if (docType.equals("[MENU_SCRIPT/]") && handler.isValidXMLType(docType)) {
				scriptTable = handler.getMenuTable();
				script.putStringField("TitleID", scriptTable.getStringField("[WINDOW_TITLE/]"));
				script.putStringField("BannerID", scriptTable.getStringField(WindowItem.BANNER_TITLE));
				script.putStringField("DescID", scriptTable.getStringField(WindowItem.SCREEN_TITLE));
				script.put("SEditor", scriptTable);

			} else {
				script.addMessage("Invalid Document Type", "30", "ScriptID", null);
				script.put("SEditor", null);
			}
					
			
		} catch (Exception e) {
			script.put("SEditor", null);
			script.addMessage("Script Not Found", "30", null, null);
		}
		return script;
	}
 	
 	 	

	
	public DataSet getScriptEditLink (DataSet script, DataAccess dataAccess) {
		script.putStringField("ScriptID", script.getStringField("SEditor"));
		script.putStringField("TypeID", "[SCREEN_SCRIPT/]");
        script = getScriptDetails(script, dataAccess);
	    return script;
	}
	
	public DataSet getMenuLink(DataSet script, DataAccess dataAccess) {
		script.putStringField("ScriptID", script.getStringField("MenuXML"));
		script.putStringField("TypeID", "[MENU_SCRIPT/]");
        script = getScriptDetails(script, dataAccess);
		return script;
	}
	
	public DataSet getScriptLink(DataSet script, DataAccess dataAccess) {
		script.putStringField("ScriptID", script.getStringField("MenuItem"));
		script.putStringField("TypeID", "[SCREEN_SCRIPT/]");
        script = getScriptDetails(script, dataAccess);
		return script;
	}
	
	 

	
	
			
	public DataSet saveScriptDetails(DataSet script, DataAccess dataAccess) {
			try {
			XmlDocument xml = new XmlDocument();
			Element root = xml.createElement("SCRIPT");
			String scriptname = script.getStringField("ScriptID");
			String type = script.getStringField("TypeID"); 
			String scriptpath = dataAccess.getScriptPath() + "ENG" + fs + scriptname + ".xml";
			String today = (new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));
			
            java.util.Hashtable<String, Element> keys = new java.util.Hashtable<String, Element>();
			 
           	xml.appendChild(xml.createComment(
           			       "\t **Script Builder 4.0 - www.ossfree.net *  ")); 
        	xml.appendChild(xml.createComment(
        	 		       "\t \t Script Name :  " + scriptname+  " " ));
           	xml.appendChild(xml.createComment(
           			       "\t \t Description :  " + script.getStringField("DescID") +  " " ));
           	if(type.equals("[SCREEN_SCRIPT/]")) {
           		xml.appendChild(xml.createComment(
           			       "\t \t Server Class :  " + script.getStringField("ClassID") +  " " ));
           		xml.appendChild(xml.createComment(
    			       "\t \t Initalization Method :  " + script.getStringField("MethodID") +   " " ));
           	}
           	xml.appendChild(xml.createComment(
        			       "\t \t File Path :  " + scriptpath +  " "));
        	xml.appendChild(xml.createComment(
        			       "\t \t Last Update :  " + today +  " "));
        	xml.appendChild(xml.createComment(
        			       "\t \t User Profile:  " + script.getUser()+  " " ));

           	setRootAttribute(root, "SCRIPT_NAME", scriptname);
        	
           	if(type.equals("[SCREEN_SCRIPT/]")) {
        		setReportAttribute(root, WindowItem.DOCUMENT_TYPE, "Window");
        		setRootAttribute(root, WindowItem.CLASSNAME, script.getStringField("ClassID"));
        		setRootAttribute(root, WindowItem.METHOD, script.getStringField("MethodID"));
        		setReportAttribute(root, WindowItem.AUTO_REFRESH, script.getStringField("aFresh"));
           		setReportAttribute(root, WindowItem.ICON, script.getStringField("aIcon"));
           	        		
           	} else {
        		setReportAttribute(root, WindowItem.DOCUMENT_TYPE, "Menu");
           	}	
           	
           	setRootAttribute(root, WindowItem.DESCRIPTION , script.getStringField("DescID"));
           	setRootAttribute(root, WindowItem.SCREEN_TITLE, script.getStringField("DescID"));
            setRootAttribute(root, WindowItem.WIDTH ,script.getStringField("Wdth"));
            setRootAttribute(root, WindowItem.HEIGHT, script.getStringField("Hght"));
            
            keys.put("1", root);
            
 			Enumeration  elements = script.getTableVector("[Path/]").elements();
 			while (elements.hasMoreElements()) {
			 String objectAttributeString = (String)elements.nextElement();			 
			 String objectHL = DataSet.parseProperty("HLO", objectAttributeString);
			 String parentHL = DataSet.parseProperty("HLP", objectAttributeString);
             Element screenComponent = xml.createElement(DataSet.parseProperty(
            		 WindowItem.OBJECT_NAME, objectAttributeString));             
            setComponentAttribute(screenComponent, WindowItem.TYPE, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.FIELDNAME, objectAttributeString);
            setComponentAttribute(screenComponent, WindowItem.DESCRIPTION, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.LENGTH,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.DECIMALS, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.HEIGHT,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.WIDTH,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.ICON,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.DATATYPE,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.DATALENGTH,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.EDITMASK,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.POSITION, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.PROTECTED, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.HIDDEN, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.ZERO_FILL, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.ENCRYPT, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.ALIGNMENT,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.DEFAULT_VALUE, objectAttributeString);
			setComponentAttribute(screenComponent, WindowItem.LINK,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.PARAMETER, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.METHODCLASS,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.METHOD, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.COMMIT_CLASS, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.COMMIT_METHOD,  objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.LIST_TABLE, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.LIST_TABLE_FIELD, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.LIST_TABLE_DESCRIPTION, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.LIST_TABLE_SELECTION, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.COMBOITEM_OBJECT, objectAttributeString);
         	setComponentAttribute(screenComponent, WindowItem.COMPONENT_ORIENTATION, objectAttributeString);         	
         	setComponentAttribute(screenComponent, "QUICK_LINK", objectAttributeString);
			  keys.put(objectHL, screenComponent);
			 ((Element) keys.get(parentHL)).appendChild(screenComponent);
 			}

 			 xml.appendChild(root);
 			 File xmlFile = new File(scriptpath);
 			 FileOutputStream xmlOut = new FileOutputStream(xmlFile); 
 			 xml.write(xmlOut);
 			 xmlOut.close();
 			 xml.write(System.out); 
							
		} catch (Exception e) {
			script.put("SEditor", null);
			dataAccess.logMessage(" *DevelopmentTools.saveScriptDetails*  --  " + e );
			script.addMessage("Script Not Found", "30", null, null);
		}
		return script;
	}
		
	

	
	
	public DataSet getScriptFileList(DataSet scriptList, DataAccess dataAccess) {
		Vector<Object> scripts = new Vector<Object>();
		try {
			String dir [] = new File(dataAccess.getScriptPath() + "ENG" + fs).list();
		    for (int x =0; x< dir.length; x++) {
		    	String rec[] = new String[3];
		    	
		    	File file = new File(dataAccess.getScriptPath() + "ENG" + fs + dir[x]);
		    	
		    	if(file.isFile()) {	
		    		 int e = file.getName().toLowerCase().indexOf(".xml");
		    		 if(e != -1) {
		    			rec[0] = file.getName().substring(0 , e);
		    			Date d = new Date (file.lastModified());
	
		    			rec[1] = d.toString(); 
		    			rec[2] = Long.toString(file.length());		    	
		    			scripts.add(rec);
		    		 }
		    	}
		    } 	
		} catch (Exception er) {
			scriptList.addMessage("No Sctiprs Found", "30", null, null);
		}
		scriptList.putTableVector("ScriptFiles", scripts);
		return scriptList;
	}
	
	
	
	public DataSet getServerLogFileList(DataSet logsList, DataAccess dataAccess) {
		Vector<Object> logs = new Vector<Object>();
		try {
			String dir [] = new File(dataAccess.getServerLogsPath()).list();
		    for (int x =0; x< dir.length; x++)
		    	logs.add(dir[x]);
		} catch (Exception er) {
			logsList.addMessage("log_dir Not Found", "30", null, null);
		}
		logsList.putTableVector("ServerLogFiles", logs);
		return logsList;
	}
	
	
	
	public DataSet getServerLogFile(DataSet log, DataAccess dataAccess) {
		try {
		String filename = dataAccess.getServerLogsPath()  + log.getStringField("LogFileName");
		Reader file = new FileReader(filename);	
		StringBuffer buffer = new StringBuffer();
		char[] b = new char[128];
		int n;
		while ((n = file.read(b)) > 0)
			buffer.append(b,0,n);
		
		log.putStringField("LogFile", buffer.toString());
		file.close();
		} catch (Exception er) {
			dataAccess.logMessage(" *DevelopmentTools.getServerLog*  --  " + er );
			log.addMessage("File Not Found", "30", null, null);
		}
		return log;
	}
	
	
	private void setRootAttribute(Element elmnt, String attrName, String attrValue) {
		if(attrValue != null && !attrValue.equals(""))
			elmnt.setAttribute(attrName, attrValue);
	}
	
	private void setComponentAttribute(Element elmnt, String attrName, String objectAttributeString) {
		String attrValue= DataSet.parseProperty(attrName,objectAttributeString);
		if(attrValue != null && !attrValue.equals(""))
			elmnt.setAttribute(attrName, attrValue);
	}	
	
	private void setReportAttribute(Element elmnt, String attrName, String attrValue) {
		if(attrValue != null && !attrValue.equals(""))
			elmnt.setAttribute(attrName, attrValue);
	}	

 
 

}

