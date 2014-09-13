package webBoltOns;

/*
 * $Id: ReportWriter.java,v 1.1 2007/04/20 19:37:27 paujones2005 Exp $ $Name:  $
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

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import webBoltOns.dataContol.DataAccess;
import webBoltOns.server.reportWriter.GenericReport;
import webBoltOns.server.reportWriter.JRivetWriter;
import webBoltOns.server.reportWriter.JasperWriter;
 
 /**
  * <h1>ReportWriter</h1>
  * 
  * <p>
  *    the jRivet Framework Server-side PDF Report Generator -
  * </p>
  * 
  * 
  */
 public class ReportWriter extends GenericServlet {
	 
		private DataAccess dataAccess;
		private String imageFilePath, scriptFilePath;
		private static final long serialVersionUID = 3411892708301107850L;

		
	/**
	 * <h2><code>init</code></h2>
	 * 
	 * <p>
	 *  Initialize the reportwriter object    
	 * </p>
	 * 
	 * @param  ServletConfig config - the config object
	 * @throws ServletException
	 * 
	 */	
	public void init(ServletConfig config) throws ServletException {
		String configHome = config.getInitParameter("configHome");
		String configFile = "WebKeePassConf.xml";
		String keyFile  = "dataKeePass.key";
		
		if (configHome != null)
			dataAccess = new DataAccess(configHome + configFile, configHome + keyFile, null);
		else
			dataAccess = new DataAccess("/java/" + configFile, "/java/" + keyFile, null);
		
		if (dataAccess.initDataAccess()) {
			imageFilePath = dataAccess.getImagePath();
			scriptFilePath = dataAccess.getScriptPath();
			super.init(config);
			log("Server Starting" );
			log("Database pool opening" );
			log("Conf-Home : " + configHome);
			log("Conf-File : " + configFile);
			log("Script-Path : " + scriptFilePath);
			log("Image-Path : " + imageFilePath);
			log("Log-Path : " + dataAccess.getServerLogsPath());
			log("Menu-Path " + dataAccess.getMenuPath());
			log("Connection :" + dataAccess.getConnectionType());
		}
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
	public void service(ServletRequest request, ServletResponse response) {

		String docType = request.getParameter("ServiceDocType").trim();
		 
		if(docType.equals("jrivet")) {
			JRivetWriter jrw = new JRivetWriter();
			jrw.init(dataAccess, this);
		    jrw.reportService(request, response);
		
		} else if(docType.equals("jasper")) {
			JasperWriter jrw = new JasperWriter();
			jrw.init(dataAccess, this);
			jrw.reportService(request, response);
		
		} else if(docType.equals("docviewer")) {
			GenericReport gnr = new GenericReport();
			gnr.init(dataAccess, this);
			gnr.reportService(request, response);
		}
	}
	
}
