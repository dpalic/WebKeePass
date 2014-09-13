package webBoltOns.server.reportWriter;

/*
 * $Id: GenericReport.java,v 1.1 2007/04/20 19:37:19 paujones2005 Exp $ $Name:  $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;
 
 /**
  * <h1>GenericReport</h1>
  * 
  * <p>
  *    the jRivet Framework Server-side PDF Report Generator -
  * </p>
  * 
  * 
  */
 public class GenericReport  {
	 
	 	public DataAccess dataAccess;
		public StringBuffer sql;
		public GenericServlet gs;
		
		public static final long serialVersionUID = 3411892708301107850L;
		public  String jRivetVersion = "jRivet Framework 4.0 - www.ossfree.net";
		
		public void buildEmptyPage(ServletRequest request, ServletResponse response) {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(" <html> <head>  <title>Report Not Created</title>  </head>");
			out.println("<body> <h2><font face=\"Arial\">No details found for report query:</font></h2>");
			
			if (sql != null)
				out.println("<p><font face=\"Courier New\">" + sql + "</font></p>");

			out.println("</body> </html>");
		} catch (IOException e) {
			gs.log("IOException: " + e);
		}
	}
	 
	
		
	public void init(DataAccess ds, GenericServlet sc){
		 dataAccess = ds;
	     gs = sc;
    }
	
	/**
	 * <h2><code>buildErrorPage</code></h2>
	 * 
	 * <p>
	 *  Create an Error static HTMP page to report to the user that 
	 *  executed SQL query ended in error.       
	 * </p>
	 * 
	 * @param   ServletRequest request  - the HTTP request
	 * @param   ServletResponse response - the HTTP respones
	 * 
	 */	
	public void buildErrorPage(ServletRequest request,  ServletResponse response,
			 String  errorDetails) {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(" <html> <head>  <title>Error Loading Report</title>  </head>");
			out.println("<body> <h2><font face=\"Arial\">An Error Was Encountered Loading Your Report.</font></h2>");
			out.println("<p><font face=\"Arial\">Please Contact Systems Administrator</font></p>");
			out.println("<p><font face=\"Arial\"> ** - " + errorDetails + "</font></p>");
			
			
			if (sql != null) {
				out.println("<p><font face=\"Arial\">Report query: </font></p>");
				out.println("<p><font face=\"Courier New\">" + sql
						+ "</font></p>");
			}

			out.println("</body> </html>");
		} catch (IOException e) {
			gs.log("IOException: " + e);
		}
	}
	
	
	
	
	public void buildPDFReportPage(ServletRequest request,  ServletResponse response, byte [] buffer){
		try {
		response.setContentType("application/pdf");
		response.setContentLength(buffer.length);
		ServletOutputStream out = response.getOutputStream();
		out.write(buffer, 0, buffer.length);
		out.flush();
		out.close();
		} catch (IOException e) {
			gs.log("IOException: " + e);
		}
	}

	
	
	public void reportService(ServletRequest request, ServletResponse response) {
		String rpt = request.getParameter("DocumentID").trim();
		if (rpt != null)
			buildReport(request, response, rpt);
		else
			buildErrorPage(request, response, " --  Report Not Loaded --");
	}
	
	
	
	
	private void buildReport(ServletRequest request, ServletResponse response, String id) {
		Statement qry = null;
		try {

			qry = dataAccess.execConnectReadOnly();
			ResultSet rs = qry.executeQuery("Select DocumentPath from documents where DocumentID = " + id);

			if (rs.next()) {
				String path = rs.getString("DocumentPath");

				File file = new File(path);

				byte[] buffer = new byte[(int) file.length()];
				InputStream imprt = new FileInputStream(file);
				imprt.read(buffer);
				imprt.close();

				if(path.toLowerCase().contains(".pdf"))
					response.setContentType("application/pdf");
				else if(path.toLowerCase().contains(".xls"))
					response.setContentType("application/vnd.ms-excel");
				else if(path.toLowerCase().contains(".msg"))
					response.setContentType("application/vnd.ms-outlook");
				else if(path.toLowerCase().contains(".ppt"))
					response.setContentType("application/vnd.ms-powerpoint");
				else if(path.toLowerCase().contains(".pps"))
					response.setContentType("application/vnd.ms-powerpoint");
				else if(path.toLowerCase().contains(".rtf"))
					response.setContentType("application/rtf");
				else if(path.toLowerCase().contains(".rtx"))
					response.setContentType("text/richtext");
				else if(path.toLowerCase().contains(".doc"))
					response.setContentType("application/msword");
				else if(path.toLowerCase().contains(".wri"))
					response.setContentType("application/mswrite");
				else if(path.toLowerCase().contains(".xml"))
					response.setContentType("text/xml");
				else if(path.toLowerCase().contains(".bmp"))
					response.setContentType("image/bmp");
				else if(path.toLowerCase().contains(".gif"))
					response.setContentType("image/gif");
				else if(path.toLowerCase().contains(".jpg"))
					response.setContentType("image/jpeg");
				else if(path.toLowerCase().contains(".jpeg"))
					response.setContentType("image/jpeg");
				else if(path.toLowerCase().contains(".jpe"))
					response.setContentType("image/jpeg");				
				else if(path.toLowerCase().contains(".htm"))
					response.setContentType("text/html");
				else
					response.setContentType("text/plain");
				
				response.setContentLength(buffer.length);
				ServletOutputStream out = response.getOutputStream();
				out.write(buffer, 0, buffer.length);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			gs.log("Exception: " + e);
			buildErrorPage(request, response, " --  Report Not Loaded -- \n " + e.getMessage());
		}  
	}

	
	
	
	/**
	 * <h2><code>formatField</code></h2>
	 * 
	 * <p>
	 *  Formats a value into its printed form.    
	 * </p>
	 * 
	 * @param  String value - The Value to be printed
	 * @param  String dataType - The Data Type to be printed. 
	 *  <p>INT - An integer</p>
	 *  <p>DAT - A Date Value</p>
	 *  <p>FLT - A 'float' value</p>
	 *     
	 * @param  int decimals - number of decimal places (used with FLT)
	 * 
	 * @return   String - Formated Value
	 */
	public String formatField(String value, String dataType, int decimals) {
		if (value == null)
			value = "";
		if (dataType.equals("INT"))
			return DataSet.formatIntegerField(value, "0");
		else if ((dataType.equals("FLT")))
			return DataSet.formatDoubleField(value, DataSet
					.createFloatMask(decimals));
		else if (dataType.equals("DAT"))
			return DataSet.formatDateField(value, "dd MMM yyyy");
		else
			return value;
	}

	
	
	public  static StringBuffer mergeTagValues(StringBuffer sql, String tag, String value) {
		String searchTag = "[" + tag.trim() + "/]";
		boolean merging = true;
		while (merging) {
			int a = sql.indexOf(searchTag);
			if (a != -1)
				sql.replace(a, a + searchTag.length(), value);
			else
				merging = false;
		}
		return sql;
	}

}
