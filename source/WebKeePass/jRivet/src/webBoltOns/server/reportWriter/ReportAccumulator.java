package webBoltOns.server.reportWriter;

/*
 * $Id: ReportAccumulator.java,v 1.1 2007/04/20 19:37:19 paujones2005 Exp $ $Name:  $
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

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfOutline;


public class ReportAccumulator {

	String brkLevel, savValue;
	double [] brkTotals, brkMaxs, brkMins, brkAvrs;
	int brkCounters;
	ReportAccumulator child, parent;
	JRivetWriter reportWriter;
	int column, totalColumns;
	PdfOutline outline;
    boolean newitem = true;
    
	public ReportAccumulator(JRivetWriter reportWriter, int column, int totalColumns) {
		this.reportWriter = reportWriter;
        this.column = column; 
        this.totalColumns = totalColumns;
		brkTotals = new double[totalColumns];	
		brkMaxs = new double[totalColumns];	
		brkMins = new double[totalColumns];	
		brkAvrs = new double[totalColumns];	
		brkCounters = 0 ;
        savValue = "[*FirstTime/]";
	}
	

	public void setChildAccumulator(ReportAccumulator cr){
		child = cr;
	}

	public void setParentAccumulator(ReportAccumulator pr){
		parent = pr;
	}


	public ReportAccumulator getChildAccumulator(){
		return child;
	}

	public ReportAccumulator getParentAccumulator(){
		return parent;
	}
	
	public int getColumnNumber() {
		return column;
	}
	
	 
	public void recursiveLevelBreaks(String [] record) 
			throws DocumentException{
        getLevelBreak(record);
        if(child != null)
        	child.recursiveLevelBreaks(record);        	
            
	}
	
	
	protected boolean getLevelBreak(String [] record) throws DocumentException {		
		if(column == -1)
			return false;
		
		if(record[column].equals(savValue))
			return false;
	 
		fireLevelBreak(record);
		return true;
	}
	 	
	
	public  void fireLevelBreak(String [] record) throws DocumentException {

		if(record != null) {
			reportWriter.fireNewSection(this, record[column]);
			newitem = true;	
		}	
		
		if(child != null)
 			child.fireLevelBreak(record);		

		if(column != -1 ) {
			if(!savValue.equals("[*FirstTime/]")){
				if(brkCounters > 0)
					for(int c=0; c < totalColumns; c++)
						brkAvrs[c] = brkTotals[c] / brkCounters;

				reportWriter.fireTotalLine(this);
				parent.accumulateTotals(brkTotals);
			}
		
			for(int c=0; c < totalColumns; c++){
				brkTotals[c] = 0;
				brkMaxs[c] = 0;
				brkMins[c] = 0;
				brkAvrs[c] = 0;
			}
			brkCounters  = 0;
			
			
			if(record != null) 
				savValue = record[column];
		}		
	}
	

	public  void fireGrandTotalBreak() throws DocumentException {
		if(child != null)
 			child.fireLevelBreak(null);		

		if(brkCounters > 0)
			for(int c=0; c < totalColumns; c++)
				brkAvrs[c] = brkTotals[c] / brkCounters;

		reportWriter.fireTotalLine(this);
	}

		
	public String getSaveValue(){
		return savValue;
	}

	
	public String getPrintValue(boolean newpage) {
		String rtn = " ";
		if(newpage)  
			rtn =  savValue;
		
		if(newitem) {
			newitem = false;
			rtn =  savValue;
		} 	   	
		return rtn;
	}

	
	public void  accumulateTotals(double [] cTot){
 			brkCounters++;	
			for(int c=0; c < totalColumns; c++){
				brkTotals[c] += cTot[c];
				if(cTot[c] > brkMaxs[c])
					brkMaxs[c] = cTot[c]; 	
				if(cTot[c] < brkMins[c] || brkMins[c] == 0)
					brkMins[c] = cTot[c]; 			
			}
 	}
	
	
	public double [] getTotals(){
		return brkTotals;
	}

	
	public double [] getMaxs(){
		return brkMaxs;
	}

	
	public double [] getMins(){
		return brkMins;
	}

	public double [] getAvrs(){
		return brkAvrs;
	}

	
	public int getCounters(){
		return brkCounters;
	}

	public 	PdfOutline getOutline(){
		return outline;
	}

	public 	void setOutline(PdfOutline o){
		 outline = o;
	}
}

