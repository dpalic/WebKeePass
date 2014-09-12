package webBoltOns.server.reportWriter;

/*
 * $Id: ReportHandler.java,v 1.1 2007/04/20 19:37:19 paujones2005 Exp $ $Name:  $
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


import java.util.Vector;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;

import webBoltOns.client.WindowItem;
import webBoltOns.dataContol.DataSet;
@SuppressWarnings("deprecation")
public class ReportHandler extends HandlerBase {

	private String currentValue="";

	private String reportTitle, docType;;
	
	private DataSet table = new DataSet();

	private Vector<ReportColumn> vf = new Vector<ReportColumn>();

	private int totalColumns = 0;

	public ReportHandler() {
	}

	// Called when character data is found inside an element
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		currentValue += new String(ch, start, length);
	}

	// Called when when the end of element is encountered
	public void endElement(String name) throws SAXException {
		if(name.equals("SqlQuery"))
			table.put(ReportColumn.SQL_QUERY, currentValue);
		
	}

	// Simple Accessor for the DataSet of parsed values
	public DataSet getReportTable() {
		table.putStringField(ReportColumn.REPORT_TITLE, reportTitle);
		table.putTableVector(ReportColumn.REPORT_DETAILS, vf);
		return table;
	}

	// Simple Accessor for the DataSet of parsed values
	public void setTable(DataSet table) {
		this.table = table;
	}
	
	
	public boolean isValidXMLType(String type){
		if(docType.equals(""))
			return true;
		if(type.equals("[REPORT_SCRIPT/]") && docType.equals("Report"))
			return true;
		return false;
	}

	private String formatAttributeValue(String type, String key,
			AttributeList attrs) {
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

	// Called when a new element is encountered
	public void startElement(String tag, AttributeList attrs)
			throws SAXException {
		currentValue="";
		if (tag.equals("SCRIPT")) 
			setScriptAttributes(attrs);
		else if(tag.equals("Column"))
			setColumnAttributes(attrs);
	}	
		
	private void setScriptAttributes(AttributeList attrs) {		
	  reportTitle = formatAttributeValue("",  ReportColumn.REPORT_TITLE, attrs);
	  docType = formatAttributeValue("",WindowItem.DOCUMENT_TYPE, attrs);
	}
  
	
	
	private void setColumnAttributes(AttributeList attrs) {		
		ReportColumn rc = new ReportColumn();
		rc.setDescription(formatAttributeValue("", ReportColumn.DESCRIPTION,attrs));
		rc.setFeildName(formatAttributeValue("", ReportColumn.FIELDNAME,attrs));
		rc.setDataType(formatAttributeValue("", ReportColumn.DATATYPE,attrs));
		rc.setLength(formatAttributeValue("[Integer/]", ReportColumn.LENGTH,attrs));
		rc.setLevelBreak(formatAttributeValue("[Integer/]", ReportColumn.LEVELBREAK,attrs));
		rc.setAlignment(formatAttributeValue("", ReportColumn.ALIGNMENT,attrs));
		rc.setSubTotaled(formatAttributeValue("[Boolean/]",ReportColumn.SUB_TOTAL, attrs));
		rc.setSubMaximum(formatAttributeValue("[Boolean/]",ReportColumn.SUB_MAXIMUM, attrs));
		rc.setSubMinimum(formatAttributeValue("[Boolean/]",ReportColumn.SUB_MINIMUM, attrs));
		rc.setSubAverage(formatAttributeValue("[Boolean/]",ReportColumn.SUB_AVERAGE, attrs));
		rc.setSubCounted(formatAttributeValue("[Boolean/]",ReportColumn.SUB_COUNT, attrs));
		rc.setDecimals(formatAttributeValue("[Integer/]", ReportColumn.DECIMALS,attrs));
		vf.addElement(rc);
		totalColumns++;
	}
}