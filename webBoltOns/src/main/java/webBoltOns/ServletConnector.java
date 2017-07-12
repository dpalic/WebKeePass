package webBoltOns;

/*
 * $Id: ServletConnector.java,v 1.1 2007/04/20 19:37:27 paujones2005 Exp $ $Name:  $
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

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import webBoltOns.client.MenuItem;
import webBoltOns.client.WindowItem;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;
import webBoltOns.dataContol.LiteralsHandler;
import webBoltOns.server.ScriptHandler;
import webBoltOns.server.ServletInterface;
import webBoltOns.server.UserSecurityManager;
import webBoltOns.server.servletUtil.DaemonRemoteServlet;
import webBoltOns.server.servletUtil.NativeExecutable;
 
 /**
  * <h1>ServletConnector</h1>
  * 
  * <p>
  * the jRivet Framework Server-side Servlet Processor 
  * </p>
  * 
  * 
  */
 public class ServletConnector extends DaemonRemoteServlet implements
		ServletInterface {

	private static final long serialVersionUID = 6293520332728376851L;
	private DataAccess dataAccess;
	protected UserSecurityManager userSecurityManager;
	private final String jRivetVersion = "    --jRivet Framework - version: v 3.060506 2006/05/06   08:15:39 jrivet.com-- ";
	private final String fs = System.getProperty("file.separator");
		
	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {}
	
	/**
	 * <h2><code>doPost</code></h2>
	 * 
	 * <p>
	 * Initiate communication between the client and the server. Receive the
	 * <code>DataSet</code> object from the client.  Process the request and 
	 * send the respones back to the client
	 * 
	 * The <code>DataSet</code> object passes all values to and from the
	 * server.
	 * 
	 * 
	 * 
	 * Communication back is performed via RMI, HTTP or Raw Socket modes based on a
	 * flag set in the applet
	 * </p>
	 * 
	 * 
	 *  @param HttpServletRequest req - The http request
	 *  @param HttpServletResponse res - The http respone
	 *	@throws ServletException, IOException
	 * 
	 * @see AppletConnetor
	 */

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		DataSet dataSet;
		ObjectInputStream objin = new ObjectInputStream(req.getInputStream());

		try {
			dataSet = (DataSet) objin.readObject();

			String action = dataSet.getServerAction();

			dataSet.setUserIP(req.getRemoteAddr());
			dataSet.setRmtHost(req.getRemoteHost());
			
			if (action.equals(MenuItem.GET_ICONS))
				dataSet = getIcons(dataSet);	
			else if(action.equals("USER_LOGIN") )  
				dataSet = getUserSignOn(dataSet, req);
			else if (action.equals(WindowItem.GET_SCRIPT))   
				dataSet = getScript(dataSet);
			else if (action.equals(WindowItem.GET_MENU))  
				dataSet = getMenu(dataSet);
			else if (action.equals(WindowItem.GET_BATCH))  
				dataSet = execBatch(dataSet);
			else  
				dataSet = getAction(dataSet);
			 

			ObjectOutputStream out = new ObjectOutputStream(res
					.getOutputStream());

			out.writeObject(dataSet);

		} catch (IOException e) {
			log("doPost IOException : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			log("doPost ClassNotFoundException : " + e.getMessage());
		}
	}
	
	
	/**
	 * <h2><code>execBatch</code></h2>
	 * 
	 * <p>
	 * run a batch command on the server
	 * </p>
	 * 
	 * 
	 *  @param DataSet dataSet - the Data Set object containing the request
	 *  @return DataSet - Processed response.
	 *  @deprecated 1.050726
	 */
	@Deprecated
	public DataSet execBatch(DataSet dataSet) {
 		  String cmd = dataSet.getStringField(WindowItem.METHOD);
		  NativeExecutable.startAction(cmd, "", true);
		  return dataSet;
	}
	
	
	/**
	 * <h2><code>getAction</code></h2>
	 * 
	 * <p>
	 *  Executes a Java Class and Method based on the client request
	 * </p>
	 * 
	 * 
	 *  @param DataSet dataSet - the Data Set object containing the request
	 *  @return DataSet - Processed response.
	 */
	public DataSet getAction(DataSet dataSet) {
		
		String classForName = dataSet.getStringField(WindowItem.CLASSNAME), 
		       methodForName = dataSet.getMethodName();

		Class<?> cls;
		try {
			cls = Class.forName(classForName);
			Class partypes[] = new Class[2];
			partypes[0] = dataSet.getClass();
			partypes[1] = dataAccess.getClass();
			Method meth = cls.getMethod(methodForName, partypes);
			Object methodObject;
			if (dataSet.getSecurityManagerStatus() && 
					    classForName.equals("webBoltOns.server.UserSecurityManager"))
				methodObject = userSecurityManager;
			else
				methodObject = cls.newInstance();

			Object arglist[] = new Object[2];
			arglist[0] = dataSet;
			arglist[1] = dataAccess;
			Object retobj = meth.invoke(methodObject, arglist);
			dataSet = (DataSet) retobj;	

		} catch (ClassNotFoundException e) {
			log("Class Not Found: " + e.getMessage());
			dataSet.addMessage("SVR0004");

		} catch (SecurityException e) {
			log("Security Exception: " + e.getMessage());
			dataSet.addMessage("SVR0005");

		} catch (NoSuchMethodException e) {
			log("No Such Method Exception: " + e.getMessage());
			dataSet.addMessage("SVR0006");

		} catch (InstantiationException e) {
			log("Instantiation Exception: " + e.getMessage());
			dataSet.addMessage("SVR0007");

		} catch (IllegalAccessException e) {
			log("Illegal Access Exception: " + e.getMessage());
			dataSet.addMessage("SVR0008");

		} catch (IllegalArgumentException e) {
			log("Illegal Argument Exception: " + e.getMessage());
			dataSet.addMessage("SVR0008");

		} catch (InvocationTargetException e) {
			log("Invocation Target Exception: " + e.getMessage());
			dataSet.addMessage("SVR0009");
		}
		return dataSet;
	}
	
	/**
	 * <h2><code>getMenu</code></h2>
	 * 
	 * <p>
	 *  Load a menu definition XML document and return it to the client
	 * </p>
	 * 
	 * 
	 *  @param DataSet dataSet - the Data Set object containing get menu request
	 *  @return DataSet - Processed response containing the menu script.
	 */
	public DataSet getMenu(DataSet dataSet) {
	
		if (dataSet.getSecurityManagerStatus()) 
			dataSet =  userSecurityManager.setRequesterAccess(dataSet, dataAccess);
		
		try {
			
			String script = dataAccess.getMenuPath() + dataSet.getLang() + fs + dataSet.getStringField("[MENU_SCRIPT/]") + ".xml";
			ScriptHandler hndl = new ScriptHandler(dataAccess, userSecurityManager,  "[MENU_SCRIPT/]", false);
			hndl.loadScript(script, dataSet);
			DataSet mnu = hndl.getMenuTable(); 

			script = dataAccess.getMenuPath() + dataSet.getLang() + fs + "A_Literals.xml";
			LiteralsHandler literals = new LiteralsHandler(); 
 			literals.loadAllLiterals(script, dataAccess);
 			mnu.put("[A_Literals/]", literals.getTable());
			
			mnu.setUser(dataSet.getUser(),dataSet.getUserDescription(), dataSet.getUserGroup());
			return mnu;

		} catch (FileNotFoundException e) {
			log("Menu File Not Found -- " +  dataAccess.getMenuPath()
					+ dataSet.getStringField("[MENU_SCRIPT/]") + ".xml");
			return null;
		} catch (ParserConfigurationException e) {
			log("Menu File XML Parse Error");
			return null;
		} catch (SAXException e) {
			log("Menu File XML Parse Error");
			return null;
		} catch (IOException e) {
			log("Menu File I/O Error");
			return null;
		}
	}
	
	/**
	 * <h2><code>getScript</code></h2>
	 * 
	 * <p>
	 *  Load a window definition XML document and return it to the client
	 * </p>
	 * 
	 * 
	 *  @param DataSet dataSet - the Data Set object containing get window request
	 *  @return DataSet - Processed response containing the window script.
	 */
	public DataSet getScript(DataSet dataSet) {
		
		if (dataSet.getSecurityManagerStatus()) 
			dataSet =  userSecurityManager.setRequesterAccess(dataSet, dataAccess);
		
		String script = dataAccess.getScriptPath() + dataSet.getLang() + fs +  dataSet.getStringField("[SCREEN_SCRIPT/]") + ".xml";
		
		try {
			ScriptHandler hndl = new ScriptHandler(dataAccess, userSecurityManager, "[SCREEN_SCRIPT/]", false);
			hndl.loadScript(script, dataSet); 
			DataSet frme = hndl.getScriptTable();
			frme.setUser(dataSet.getUser(),dataSet.getUserDescription(), dataSet.getUserGroup()); 
			return frme;

			
		} catch (FileNotFoundException e) {
			log("Script File Not Found: " + e.getMessage());
			dataSet.addMessage("SVR0015");
			return null;
		} catch (ParserConfigurationException e) {
			log("XML Parse Error: " + e.getMessage());
			dataSet.addMessage("SVR0016");
			return null;
		} catch (SAXException e) {
			log("XML Parse Error: " + e.getMessage());
			dataSet.addMessage("SVR0016");
			return null;
		} catch (IOException e) {
			log("XML File I/O Error: " + e.getMessage());
			dataSet.addMessage("SVR0017");
			return null;
		}
	}
	
	
	/**
	 * <h2><code>handleClient</code></h2>
	 * 
	 * <p>
	 *  Start socket services for raw socket communications 
	 * </p>
	 * 
	 * 
	 *  @param Socket Socket client - The client socket object 
	 */
	@Override
	public void handleClient(Socket client) {
		new Connection(this, client).start();
	}
	
	
	
	
	/**
	 * <h2><code>init</code></h2>
	 * 
	 * <p>
	 *  Initialize servlet services.  Opens a Data Access Object for database 
	 *  communications. Starts RMI and socket services  
	 * </p>
	 * 
	 *  @param ServletConfig config 
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		String configHome = config.getInitParameter("configHome");
		String configFile = "WebKeePassConf.xml";
		String keyFile  = "dataKeePass.key";
		
		if (configHome != null)
			dataAccess = new DataAccess(configHome + configFile, configHome + keyFile, this);
		else
			//dataAccess = new DataAccess("C:\\java\\Projects\\WKP\\" + configFile, 
			//							"C:\\java\\Projects\\WKP\\" + keyFile, this);

			dataAccess = new DataAccess("/home/pjones/Projects/JavaProjects/WebKeepass/wkp/conf/" + configFile, 
										"/home/pjones/Projects/JavaProjects/WebKeepass/wkp/conf/" + keyFile, this);

			
		if (dataAccess.initDataAccess()) { 
			super.init(config, dataAccess.registryPort, dataAccess.socketPort);
			dataAccess.logMessage(jRivetVersion);
			dataAccess.logMessage(".Server Starting");
			dataAccess.logMessage("..Database pool opening" );
			dataAccess.logMessage("..Conf-Home : " + configHome);
			dataAccess.logMessage("..Conf-File : " + configFile);
			dataAccess.logMessage("..Script-Path : " + dataAccess.getScriptPath());
			dataAccess.logMessage("..Image-Path : " + dataAccess.getImagePath());
			dataAccess.logMessage("..Log-Path : " + dataAccess.getServerLogsPath());
			dataAccess.logMessage("..Menu-Path " + dataAccess.getMenuPath());
			dataAccess.logMessage("..Connection :" + dataAccess.getConnectionType());
			dataAccess.logMessage("..Opening SecurityManager ");
			userSecurityManager = new UserSecurityManager();
			userSecurityManager.setLdapLogin(dataAccess.getEOpts().getBooleanField("LDAP")); 
			dataAccess.logMessage("..SecurityManager open");
			dataAccess.logMessage("..Get all Image Icons");
			dataAccess.logMessage("..Icon Table Loaded");
			dataAccess.logMessage("..Server Started");
		}
	}
	
	/**
	 * <h2><code>getUserEnvironment</code></h2>
	 * 
	 * <p>
	 *  Gets the client environment data for the current user and 
	 *  returns it to the client 
	 * </p>
	 * 
	 *  @param DataSet - dataSet - The request environment object 
	 *  @return DataSet - Environment data
	 *  
	 */
	private DataSet getUserSignOn(DataSet dataSet, HttpServletRequest req) {
		if (dataAccess.isSecurityManagerON()) {
		   if(dataAccess.isJRivetSecurityON()) {
			   dataSet =  userSecurityManager.confirmPassword(dataSet ,dataAccess); 
			   if(dataSet.getUser().equals(""))
				   return dataSet;
		
			   dataSet = userSecurityManager.setRequesterGroupAccess(dataSet,dataAccess);  		   
			   dataSet = userSecurityManager.getUserTheme(dataSet, dataAccess);
			   
		   } else {
			   dataSet.setUser(req.getRemoteUser());
		   } 
 
		} 
		return dataSet;
	}

	
	
	private DataSet getIcons(DataSet dataSet){
		dataSet.put("[Environment_Options/]", dataAccess.getEOpts());
		dataSet.put("[Standards/]", dataAccess.getStandardUserTheme());
		dataSet.setSecurityManagerStatus(dataAccess.isSecurityManagerON());
		return dataSet;
	}
	
 }

 

 
 

class Connection extends Thread {

	Socket client;

	ServletConnector servlet;

	Connection(ServletConnector servlet, Socket client) {
		this.servlet = servlet;
		this.client = client;
		setPriority(NORM_PRIORITY - 1);
	}

	@Override
	public void run() {
		try {
			// Read the first line sent by the client
			ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(client.getInputStream()));

			DataSet dataSet = null;
			Object obj = in.readObject();
			dataSet = (DataSet) obj;
			String action = dataSet.get(WindowItem.ACTION).toString();
			
			dataSet.setUserIP("Socket-Server");
			dataSet.setRmtHost("Socket-Server");
			
			if (action.equals(WindowItem.GET_SCRIPT)) {
				dataSet = servlet.getScript(dataSet);
			} else if (action.equals(WindowItem.GET_MENU)) {
				dataSet = servlet.getMenu(dataSet);
			} else if (action.equals(WindowItem.GET_BATCH)) {
				dataSet = servlet.execBatch(dataSet);
			} else {
				dataSet = servlet.getAction(dataSet);
			}

			ObjectOutputStream out = new ObjectOutputStream(client
					.getOutputStream());
			out.writeObject(dataSet);
			out.close();

			// Be sure to close the connection
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
			servlet.getServletContext().log(
					"Exception while handling client request :"
							+ e.getMessage());
		}
	}
} 