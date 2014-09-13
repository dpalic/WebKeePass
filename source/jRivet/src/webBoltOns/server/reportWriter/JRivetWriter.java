package webBoltOns.server.reportWriter;

/*
 * $Id: JRivetWriter.java,v 1.1 2007/04/20 19:37:20 paujones2005 Exp $ $Name:  $
 *
 *   Copyright  2004, 2005, 2006  www.jrivet.com  -   All Rights Reserved 
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
 * The Initial Developer of the Original Code is Paul Jones.
 * 
 *  Copyright (C) 2004, 2005, 2006  by Paul Jones.
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
 * 
 */

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfOutline;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
 
 /**
  * <h1>GenericReport</h1>
  * 
  * <p>
  *    the jRivet Framework Server-side PDF Report Generator -
  * </p>
  * 
  * 
  */
 public class JRivetWriter extends GenericReport {
	 
		private PdfContentByte contentByte;
		private Document document;
		private boolean newpage = true;
		private ByteArrayOutputStream outputStream;
		private int pageLength = 25;
		private PdfPTable reportBody;
		private Object[] reportColumns;
		private PdfOutline root;
		private int rowCount, totalRows;
		private ReportAccumulator topA, bottomA;
		private final Color totalColor = new Color(250, 250, 250), detailColor = Color.WHITE;
		private PdfWriter writer;
		private static final long serialVersionUID = 3411892708301107850L;
		
	 /**
	  * <h2><code>accumulateReportTotals</code></h2>
	  * 
	  * <p>
	  *  accumulate all report totals, 
	  *  calculate averages, minimums and maximums    
	  * </p>
	  * 
	  * 
	  * @param   String [] record - The record to accumulate
	  * 
	  */	
	 private void accumulateReportTotals(String record[]) {
		double totals[] = new double[reportColumns.length];
		for (int c = 0; c < reportColumns.length; c++) {
			ReportColumn column = (ReportColumn) reportColumns[c];

			if (column.isSubTotaled() || column.isSubMaximum()
					|| column.isSubMinumum() || column.isSubAveraged())
				totals[c] = Double.parseDouble(record[c]);
		}
		bottomA.accumulateTotals(totals);
	}

	 
	 /**
	  * <h2><code>buildBlankLine</code></h2>
	  * 
	  * <p>
	  *  build a 'blank' line     
	  * </p>
	  */	
	private void buildBlankLine() throws DocumentException {

		if (++rowCount < pageLength) {
			PdfPCell cell;
			for (int c = 0; c < reportColumns.length; c++) {
				cell = new PdfPCell(new Paragraph(" ", FontFactory.getFont(
						FontFactory.HELVETICA, 12, Font.NORMAL)));
				cell.setBorder(0);
				cell.setBackgroundColor(totalColor);
				reportBody.addCell(cell);

			}
		}
	}

	 /**
	  * <h2><code>buildDetilLine</code></h2>
	  * 
	  * <p>
	  *  create and print a detail line on the report    
	  * </p>
	  * 
	  * 
	  * @param   String [] record - report record to print
	  * 
	  */
	private void buildDetilLine(String[] record) throws DocumentException {
		newpage = isNewPage(++rowCount);
		PdfPCell cell;

		if (record != null) {
			for (int c = 0; c < reportColumns.length; c++) {
				ReportColumn column = (ReportColumn) reportColumns[c];
				String value = " ";

				if (record != null) {
					if (column.getLevelBreak() > 0)
						value = column.getAccumulator().getPrintValue(newpage);
					else
						value = record[c];
				}

				cell = new PdfPCell(new Paragraph(value, FontFactory.getFont(
						FontFactory.HELVETICA, 12, Font.NORMAL)));

				cell.setBorder(0);
				cell.setNoWrap(true);

				cell.setBackgroundColor(detailColor);

				if (column.getAlignment().equals(ReportColumn.LEFT))
					cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				else if (column.getAlignment().equals(ReportColumn.RIGHT))
					cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				else
					cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

				reportBody.addCell(cell);

			}
		}
	}
	
	
	/**
	 * <h2><code>buildReport</code></h2>
	 * 
	 * <p>
	 *  Create the PDF report        
	 * </p>
	 * 
	 * @param   ServletRequest request  - the HTTP request
	 * @param   ServletResponse response - the HTTP respones
	 * 
	 */	
	private void buildReport(ServletRequest request, ServletResponse response, DataSet reportTable) {
		try {
			
			rowCount = 999;
			totalRows = 0;
			document = new Document(PageSize.LEGAL.rotate());
			document.setMargins(8.0f ,8.0f, 8.0f, 18.0f);

			outputStream = new ByteArrayOutputStream();
			writer = PdfWriter.getInstance(document, outputStream);
			writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
			buildReportTitles(reportTable);

			document.open();

			contentByte = writer.getDirectContent();
			root = new PdfOutline(contentByte.getRootOutline(),
					new PdfDestination(PdfDestination.FIT), "Report");

			topA.setOutline(root);

			ResultSet resultSet;
			Statement statement = dataAccess.execConnectReadOnly();

			sql = new StringBuffer(reportTable
					.getStringField(ReportColumn.SQL_QUERY));

			sql = DataSet.removeFormat(sql, "\n");
			sql = DataSet.removeFormat(sql, "\t");

			Enumeration parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String p = (String) parameters.nextElement();
				String v = request.getParameter(p);
				if (!p.equals("ReportScript") && 
						           !p.equals("")  && !v.equals(""))
					sql = mergeTagValues(sql, p, v);
			}

			resultSet = statement.executeQuery(sql.toString().trim());
			String[] record = new String[reportColumns.length];

			while (resultSet.next()) {
				for (int c = 0; c < reportColumns.length; c++) {
					ReportColumn column = (ReportColumn) reportColumns[c];
					String value = (String) DataAccess.getFieldValue(resultSet,
							column.getFeildName(), column.getDataType());
					record[c] = formatField(value, column.getDataType(), column
							.getDecimals());
				}

				topA.recursiveLevelBreaks(record);
				buildDetilLine(record);
				accumulateReportTotals(record);
				totalRows++;
			}

			if (totalRows == 0) {
				buildEmptyPage(request, response);
				
				
			} else {
				topA.fireGrandTotalBreak();
				resultSet.close();
				dataAccess.execClose(statement);
				document.add(reportBody);
				document.close();
				buildPDFReportPage(request, response, outputStream.toByteArray());
			}

		} catch (DocumentException e) {
			gs.log("** DocumentException: " + e);
			buildErrorPage(request, response, e.toString());
		} catch (IOException e) {
			gs.log("** IOException: " + e);
			buildErrorPage(request, response, e.toString());
		} catch (Exception e) {
			gs.log("** Exception: " + e);
			buildErrorPage(request, response, e.toString());
		}
		
	}
	
	/**
	 * <h2><code>buildReportTitles</code></h2>
	 * 
	 * <p>
	 *  Create the report headings        
	 * </p>
	 * 
	 * @param   DataSet reportTable - the report data object
	 * 
	 */	
	private void buildReportTitles(DataSet reportTable)
			throws DocumentException, BadElementException,
			MalformedURLException, IOException {

		Paragraph title = new Paragraph();

		title.add(Image.getInstance(dataAccess.getImagePath()
				+ "reportLogo.gif"));
		title.add(new Chunk(new SimpleDateFormat("                           "
				+ "hh:mm:ss - dd MMM yyyy").format(new Date()), FontFactory
				.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));

		title.add(new Chunk("           "
				+ reportTable.getStringField(ReportColumn.REPORT_TITLE),
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD)));

		HeaderFooter header = new HeaderFooter(title, false);
		header.setBorder(0);

		HeaderFooter footer = new HeaderFooter(new Phrase("page:", FontFactory
				.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)), true);
		footer.setAlignment(HeaderFooter.ALIGN_CENTER);

		document.setHeader(header);
		document.setFooter(footer);

		reportColumns = reportTable.getTableVector(ReportColumn.REPORT_DETAILS)
				.toArray();

		topA = new ReportAccumulator(this, -1, reportColumns.length);
		bottomA = topA;

		reportBody = new PdfPTable(reportColumns.length);
		reportBody.setTotalWidth(1.100f);
		reportBody.setHeaderRows(1);
		
		
		float cW[] = new float[reportColumns.length];

		for (int c = 0; c < reportColumns.length; c++) {
			ReportColumn column = (ReportColumn) reportColumns[c];
			if (column.getLevelBreak() > 0) {
				ReportAccumulator r = new ReportAccumulator(this, c,
						reportColumns.length);
				bottomA.setChildAccumulator(r);
				r.setParentAccumulator(bottomA);
				column.setAccumulator(r);
				bottomA = r;
			}

			PdfPCell hdr = new PdfPCell(new Paragraph(column.getDescription(),
					FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));

			hdr.setBorder(Rectangle.BOTTOM);
			if (column.getAlignment().equals(ReportColumn.LEFT))
				hdr.setHorizontalAlignment(Cell.ALIGN_LEFT);
			else if (column.getAlignment().equals(ReportColumn.RIGHT))
				hdr.setHorizontalAlignment(Cell.ALIGN_RIGHT);
			else
				hdr.setHorizontalAlignment(Cell.ALIGN_CENTER);

			reportBody.addCell(hdr);
			cW[c] = (float) column.getLength();
		}
		reportBody.setWidths(cW);
	}

	/**
	 * <h2><code>buildTotalLine</code></h2>
	 * 
	 * <p>
	 *  Create printed total lines        
	 * </p>
	 * 
	 * @param   String[] record - A report total line
	 * 
	 */		
	private void buildTotalLine(String[] record) throws DocumentException {
		++rowCount;
		PdfPCell cell;

		if (record != null) {
			for (int c = 0; c < reportColumns.length; c++) {
				ReportColumn column = (ReportColumn) reportColumns[c];
				String value = record[c];

				cell = new PdfPCell(new Paragraph(value, FontFactory.getFont(
						FontFactory.HELVETICA, 12, Font.NORMAL)));

				cell.setBorder(0);
				cell.setNoWrap(true);

				cell.setBackgroundColor(totalColor);

				if (column.getAlignment().equals(ReportColumn.LEFT))
					cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				else if (column.getAlignment().equals(ReportColumn.RIGHT))
					cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				else
					cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

				reportBody.addCell(cell);

			}
		}
	}

	/**
	 * <h2><code>fireNewSection</code></h2>
	 * 
	 * <p>
	 *  Executed by <code>ReportAccumulator</code> when a new section is 
	 *  detected      
	 * </p>
	 * 
	 * @param   ReportAccumulator ac - Source creating the new section. 
	 * @param   String newValue - the value of the new section.
	 * 
	 */		
	public void fireNewSection(ReportAccumulator ac, String newValue) {
		PdfOutline outLine = new PdfOutline(ac.getParentAccumulator()
				.getOutline(), new PdfDestination(PdfDestination.FITH, 50),
				newValue);
		outLine.setOpen(false);
		ac.setOutline(outLine);
	}

	
	/**
	 * <h2><code>fireTotalLine</code></h2>
	 * 
	 * <p>
	 *  Executed by <code>ReportAccumulator</code> when a total line 
	 *  needs to be printed on the report.  
	 * </p>
	 * 
	 * @param   ReportAccumulator ac - Source of the total line. 
	 * 
	 */		
	public void fireTotalLine(ReportAccumulator ac) throws DocumentException {
		int cl = ac.getColumnNumber();
		ReportColumn clc;
		if (cl > -1) 
			clc = (ReportColumn) reportColumns[cl];
		else
			clc = null;
		
		String[] cntsRecord = new String[reportColumns.length];
		String[] totsRecord = new String[reportColumns.length];
		double[] tots = ac.getTotals();
		String[] maxsRecord = new String[reportColumns.length];
		double[] maxs = ac.getMaxs();
		String[] minsRecord = new String[reportColumns.length];
		double[] mins = ac.getMins();
		String[] avrsRecord = new String[reportColumns.length];
		double[] avrs = ac.getAvrs();
		int counts = ac.getCounters();
		boolean sb = false, mx = false, mn = false, av = false;

		for (int c = 0; c < reportColumns.length; c++) {
			ReportColumn column = (ReportColumn) reportColumns[c];

			
			if (c == 0 && cl == -1) {
			
				totsRecord[c] = " Grand-total:";
				maxsRecord[c] = " Maximum:";
				minsRecord[c] = " Minimum:";
				avrsRecord[c] = " Average:";
			
			} else	if (c == cl) {
				cntsRecord[c] = "     Count: " + +counts;
				 
				totsRecord[c] = "     Sub-total:";
				maxsRecord[c] = "     Maximum:";
				minsRecord[c] = "     Minimum:";
				avrsRecord[c] = "     Average:";

			} else {
				
				cntsRecord[c] = " ";

				if (column.isSubTotaled()) {
					totsRecord[c] = formatField(Double.toString(tots[c]),
							column.getDataType(), column.getDecimals());
					sb = true;
				} else {
					totsRecord[c] = " ";
				}

				if (column.isSubMaximum()) {
					maxsRecord[c] = formatField(Double.toString(maxs[c]),
							column.getDataType(), column.getDecimals());
					mx = true;
				} else {
					maxsRecord[c] = " ";
				}

				if (column.isSubMinumum()) {
					minsRecord[c] = formatField(Double.toString(mins[c]),
							column.getDataType(), column.getDecimals());
					mn = true;
				} else {
					minsRecord[c] = " ";
				}

				if (column.isSubAveraged()) {
					avrsRecord[c] = formatField(Double.toString(avrs[c]),
							column.getDataType(), column.getDecimals());
					av = true;
				} else {
					avrsRecord[c] = " ";
				}

			}
		}
		if (clc != null && clc.isSubCounted())
			buildTotalLine(cntsRecord);
		if (sb)
			buildTotalLine(totsRecord);
		if (mx)
			buildTotalLine(maxsRecord);
		if (mn)
			buildTotalLine(minsRecord);
		if (av)
			buildTotalLine(avrsRecord);
		buildBlankLine();
	}




	/**
	 * <h2><code>getReportScript</code></h2>
	 * 
	 * <p>
	 *  Request and retrieve the report XML from the server.  
	 *  Execute the SQL query and build the report when the XML is returned    
	 * </p>
	 * 
	 * @param  String scripName - Name of the XML containing the report script
	 *
	 * @return   DataSet - DataSet with the report layout
	 * 
	 */
	private DataSet getReportScript(String scripName) {
		DataSet reportTable;

		try {
			String script = dataAccess.getMenuPath() + scripName + ".xml";

			FileReader reader = new FileReader(new File(script));
			SAXParser sp = SAXParserFactory.newInstance().newSAXParser();
			ReportHandler handler = new ReportHandler();
			sp.parse(new InputSource(reader), handler);
			reportTable = handler.getReportTable();

		} catch (FileNotFoundException e) {
			gs.log("** File Not Found Exception : " + e);
			return null;
		} catch (ParserConfigurationException e) {
			gs.log("** Parser Configuration Exception : " + e);
			return null;
		} catch (SAXException e) {
			gs.log("** SAX Exception : " + e);
			return null;
		} catch (IOException e) {
			gs.log("** IO Exception[ : " + e);
			return null;
		}

		return reportTable;
	}


	/**
	 * <h2><code>isNewPage</code></h2>
	 * 
	 * <p>
	 *  Method to test for a page break.  When a page break occurs 
	 *  the contents of the table are added to the document and 
	 *  the table is cleared out.    
	 * </p>
	 * 
	 * @param  int rowcount - the current report page line number
	 * @throws DocumentException
	 * @return - boolean - true when a new page is printed
	 * 
	 */	
	private boolean isNewPage(int rowcount) throws DocumentException {
		if (rowcount < pageLength)
			return false;
		
		document.add(reportBody);
		reportBody.deleteBodyRows();
		document.newPage();
		rowCount = 1;
		return true;
	}
	
	
	/**
	 * <h2><code>service</code></h2>
	 * 
	 * <p>
	 *   Proces Http Service and create report.    
	 * </p>
	 * 
	 * @param  ServletRequest request
	 * @param ServletResponse response
	 * 
	 */	
	public void  reportService(ServletRequest request, ServletResponse response) {

		DataSet reportTable = getReportScript(
		 		           request.getParameter("Lng").trim() + 
			        	   System.getProperty("file.separator") +
                           request.getParameter(ReportColumn.REPORT_SCRIPT).trim());
		
		if (reportTable != null)
			 buildReport(request, response, reportTable);
		else
			buildErrorPage(request, response, " -- Report Script Not Loaded --");

	}



}
