package webBoltOns.server.reportWriter;

/*
 * $Id: ReportColumn.java,v 1.1 2007/04/20 19:37:19 paujones2005 Exp $ $Name:  $
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

public class ReportColumn implements java.io.Serializable {

	private static final long serialVersionUID = -7665680171706338838L;
	public static final String REPORT_DETAILS = "[Report Details/]";
	public static final String SQL_QUERY = "[Sql Query/]";
	public static final String REPORT_SCRIPT = "ReportScript";
	public static final String PAGE_LENGTH = "PageLenght";
	public static final String REPORT_TITLE = "DESCRIPTION";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String FIELDNAME = "FIELDNAME";
	public static final String LENGTH = "LENGTH";
	public static final String DECIMALS = "DECIMALS";
	public static final String DATATYPE = "DATATYPE";
	public static final String POSITION = "POSITION";
	public static final String DOCUMENT_TYPE = "DOCUMENT_TYPE";

	public static final String SUB_TOTAL = "SUB_TOTAL";
	public static final String SUB_MAXIMUM = "SUB_MAXIMUM";
	public static final String SUB_MINIMUM  = "SUB_MINIMUM";
	public static final String SUB_COUNT = "SUB_COUNT";
	public static final String SUB_AVERAGE = "SUB_AVERAGE";
	
	public static final String LEVELBREAK = "LEVELBREAK";
	public static final String ALIGNMENT = "ALIGNMENT";
	public static final String RIGHT = "Right";
	public static final String CENTER = "Center";
	public static final String LEFT = "Left";

	
	private String description = "", fieldname = "", datatype = "",
			alignment = "";

	private int length = 0, decimals = 0, controlLevelBreak = 0;

	boolean subTotaled = false, 
		    subMaximum = false,
			subMinimum = false,
			subCount = false,
			subAverage = false;

	private ReportAccumulator accumulator;

	public ReportColumn() {
	}

	public String getDescription() {
		return description;
	}

	public String getFeildName() {
		return fieldname;
	}

	public String getDataType() {
		return datatype;
	}

	public String getAlignment() {
		return alignment;
	}

	public boolean isSubTotaled() {
		return subTotaled;
	}

	public boolean isSubMaximum() {
		return subMaximum;
	}
	
	public boolean isSubMinumum() {
		return subMinimum;
	}
	
	public boolean isSubCounted() {
		return subCount;
	}
	
	public boolean isSubAveraged() {
		return subAverage;
	}
	
	public int getLength() {
		return length;
	}

	public int getDecimals() {
		return decimals;
	}

	public int getLevelBreak() {
		return controlLevelBreak;
	}

	public void setDescription(String s) {
		description = s;
	}

	public void setFeildName(String s) {
		fieldname = s;
	}

	public void setDataType(String s) {
		datatype = s;
	}

	public void setAlignment(String s) {
		alignment = s;
	}

	public void setSubTotaled(String s) {
		if (s.equals("Y"))
			subTotaled = true;
		else
			subTotaled = false;
	}

	public void setSubMaximum(String s) {
		if (s.equals("Y"))
			subMaximum = true;
		else
			subMaximum = false;
	}

	public void setSubMinimum(String s) {
		if (s.equals("Y"))
			subMinimum = true;
		else
			subMinimum = false;
	}

	public void setSubCounted(String s) {
		if (s.equals("Y"))
			subCount = true;
		else
			subCount = false;
	}
	
	public void setSubAverage(String s) {
		if (s.equals("Y"))
			subAverage = true;
		else
			subAverage = false;
	}
	
	public void setLength(String s) {
		length = Integer.parseInt(s);
	}

	public void setDecimals(String s) {
		decimals = Integer.parseInt(s);
	}

	public void setLevelBreak(String s) {
		controlLevelBreak = Integer.parseInt(s);
	}

	public ReportAccumulator getAccumulator() {
		return accumulator;
	}

	public void setAccumulator(ReportAccumulator a) {
		accumulator = a;
	}
}
