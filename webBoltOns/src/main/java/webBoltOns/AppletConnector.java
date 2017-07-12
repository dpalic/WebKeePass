package webBoltOns;

/*
 * $Id: AppletConnector.java,v 1.1 2007/04/20 19:37:27 paujones2005 Exp $ $Name:  $
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
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

import javax.crypto.SecretKey;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;
import webBoltOns.client.MenuFrame;
import webBoltOns.client.MenuItem;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.CDialog;
import webBoltOns.client.components.CSHAPasswordField;
import webBoltOns.client.components.componentRules.ComManager;
import webBoltOns.client.components.componentRules.MenuRules; 
import webBoltOns.client.components.componentRules.ScriptRules;
import webBoltOns.dataContol.CipherString;
import webBoltOns.dataContol.DataSet; 
import webBoltOns.server.ServletInterface;
import webBoltOns.server.servletUtil.HttpMessage;

/** 
 * <h1>AppletConnector</h1>
 * 
 * <p>
 * the jRivet Framework client-side Applet -
 * </p>
 * 
 */

public class AppletConnector extends JApplet {
 
	
	public Color bgColor = Color.white, tbFontColor = Color.BLUE,
			tbColor = Color.cyan, wtFontColor = Color.white,
			wtColor = Color.gray, crsColor = Color.yellow;
	
	public String fontType, fontSize, totalConnections, dateFormat;
	public Font headerFont = new java.awt.Font("Arial", 1, 11), standardFont = new java.awt.Font("Arial", 0, 11);
	public boolean isStandalone = false, administratorAccess = false, deskTopThemeAccess = false, tipAccess = false,
			   tableCopyAccess = false, tablePrintAccess = false, securityManager = false,  strictEncoding= true, ldap = false;
			
	public String jRivetVersion = " - jRivet Framework - version: v 4.0 - www.ossfree.net ";
	public String menuScript, servletURL, reportURL, user,  userDesc, serverDateFormat;
	public double timeout = -1, lastuse = 0;
	public String lang = "ENG";
	public ImageIcon upIcon, downIcon, menuClosedIcon, menuOpenIcon,
			scriptIcon, documentIcon, banner, quickLinkIcon, linkButtonIcon,
			errorIcon, tableCopyIcon, tablePrintIcon, messageIcon, warnIcon,
			webEmailIcon, webDocumentIcon, postButtonIcon, treeTableOpenIcon,
			optionQuestionIcon, optionMessageIcon, treeTableClosedIcon,
			treeTableLeafIcon, menuExpandIcon, menuCollapseIcon,
			menuScriptIcon, menuDocumentIcon, logoImageIcon, logoImagePanel;
	
	 
	private static JFrame frame;
	private static final long serialVersionUID = 7491806255617902205L;
	private static final int SOCKET_PORT = 1100,REGISTRY_PORT = Registry.REGISTRY_PORT;
	private MenuFrame mMnu;
	private String com_mode;
	private DataSet environmentIcons, response;
	private int registryPort, socketPort;
	private ScriptRules sRules = new ScriptRules();
	private MenuRules mRules = new MenuRules(); 	
	private CipherString cipher;
	private Hashtable  literals = new Hashtable();
	public AppletConnector() {}

	/**
	 * <p>
	 * Main method - to execute the framework outside of an applet container
	 * </p>
	 * 
	 * @param -none
	 */
	public static void main(String[] args) {
	 
		AppletConnector applet = new AppletConnector();
		applet.isStandalone = true;
		frame = new JFrame();
		frame.setTitle("AppletMenu");
		frame.setName("AppletMenu");
		frame.getContentPane().add(applet);
		applet.init();
		applet.start();
		 
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setLocation(0, 0);
		frame.setVisible(true);

	}
	
	
	/**
	 * <h2><code>postServerRequestLazy</code></h2>
	 * 
	 * <p>
	 * Initiate communication between the client and the server.  Transfer the
	 * <code>DataSet</code> object to and from the server. The <code>DataSet</code> 
	 *  object passes all values to and from the server.
	 * 
	 * Communication is performed via RMI, HTTP or Raw Socket modes based on a
	 * flag set in the applet
	 * </p>
	 * 
	 * 
	 * @param values
	 *            to pass to the server
	 * @return Values returnes from the server
	 * 
	 * @see ServletConnetor
	 */
	 	public void postServerRequestLazy(final ComManager mngr,  final ActionEvent source, 
	 							             final DataSet request) { 
	 		request.setUser(user);
	 		request.setScmbl(encrypt(user));
	 		request.setLang(lang);
	 		
	 		Thread worker = new Thread() {
	 			public void run() {
	 				response = null;
	 				if (com_mode.equals("HTTP"))  
	 					response = getHttpObject(request);
	 				else if (com_mode.equals("RMI"))  
	 					response = getRMIObject(request);
	 				else if (com_mode.equals("SOCKET"))  
	 					response = getSocketObject(request);
	 
        	
	 				 SwingUtilities.invokeLater(new Runnable() {
                         public void run() {
                        	 if (response == null) {
                        		 CDialog networkError = new CDialog(null, AppletConnector.this);
                        		 networkError.showInvalidConnectionDialog(" Server/Connection Reset- Try Again Later...");
                        	 }          
                        	 
                        	 showWebStatus("Done");
                        	 mngr.fireServerRepsone(source, request, response);	
                         }
                     });
	 				}
	 			};
	 			worker.start(); 	   
	 	}
	 	
	 	
	/**
	 * <h2><code>postSeverResponseImed</code></h2>
	 * 
	 * <p>
	 * Initiate communication between the client and the server.  Transfer the
	 * <code>DataSet</code> object to and from the server. The <code>DataSet</code> 
	 *  object passes all values to and from the server.
	 * 
	 * Communication is performed via RMI, HTTP or Raw Socket modes based on a
	 * flag set in the applet
	 * </p>
	 * 
	 * 
	 * @param values
	 *            to pass to the server
	 * @return Values returnes from the server
	 * 
	 * @see ServletConnetor
	 */
	public DataSet postServerRequestImed(DataSet dataSet) {
		dataSet.setUser(user);
		dataSet.setScmbl(encrypt(user));
		dataSet.setLang(lang);
		
		if (com_mode.equals("HTTP"))  
			dataSet = getHttpObject(dataSet);
		  else if (com_mode.equals("RMI"))  
			dataSet = getRMIObject(dataSet);
		  else if (com_mode.equals("SOCKET"))  
			dataSet = getSocketObject(dataSet);
		 
		if (dataSet == null) {
			CDialog networkError = new CDialog(null, this);
			networkError.showInvalidConnectionDialog(getMsgText("TXT0038"));
		}
		return dataSet;
	}

	
	/**
	 * <h2><code>buildFancyButton</code></h2>
	 * 
	 * <p>
	 *  Creates a fancy button type of the applet
	 * </p>
	 * 
	 * @param -
	 *            requested icon file name
	 * @return - the image icon
	 * 
	 */
	public JButton buildFancyButton(String buttonName, String imageName, String toolTip, char mNemonic ) {
		JButton button = new JButton(buttonName, getImageIcon(imageName));
		button.setFocusable(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setToolTipText(toolTip);
		button.setFont(new java.awt.Font("Arial", 1, 12) );
		if (mNemonic != ' ') 
			button.setMnemonic(mNemonic);		
		return button;
	}
	
	/**
	 * <h2><code>destroy</code></h2>
	 * <p>
	 * Empty <code>destroy</code> method
	 * </p>
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * <h2><code>getAppletFrame</code></h2>
	 * 
	 * <p>
	 * Returns the content <code>Frame</code> object used to render the menu
	 * tree.
	 * </p>
	 * 
	 * @return The frame the menu is renderered within
	 * 
	 */
	public Frame getAppletFrame() {
		if (isStandalone)
			return frame;
		else {
			return (Frame) SwingUtilities.windowForComponent(this);
		}
	}

	
	public ScriptRules getScriptRules() {
		return sRules;
	}
	
	public MenuRules getMenuRules(){
		return mRules;
	}
	

	
	
	/**
	 * <h2><code>getAppletInfo</code></h2>
	 * 
	 * <p>
	 * Returns a String with the current jRivet Framework Version
	 * </p>
	 * 
	 * @return jRivet - Applet Version
	 * 
	 */
	public String getAppletInfo() {
		return jRivetVersion;
	}

	/**
	 * <h2><code>getParameter</code></h2>
	 * 
	 * <p>
	 * Parses and sets the following parameters. Parameters are passed in the
	 * underlying HTML document the applet was executed. When run as a
	 * standalone application, Applet parameters are set as follows:
	 * </p>
	 * 
	 * <p>localHost - yes</p>
	 * <p>servletURL - webBoltOns.ServletConnector</p>
	 * <p>reportURL - webBoltOns.ReportWriter</p>
	 * <p>registryPort</p>
	 * <p>socketPort</p>
	 * 
	 * @param ParameterKey
	 * @return Parameter Value
	 * 
	 */
	public String getParameter(String key) {
		if (isStandalone) {
			if (key.equals("localHost"))
				return "yes";
			else if (key.equals("servletURL"))
				return "webBoltOns.ServletConnector";
			else if (key.equals("reportURL"))
				return "webBoltOns.ReportWriter";
			else if (key.equals("registryPort"))
				return Integer.toString(REGISTRY_PORT);
			else if (key.equals("socketPort"))
				return Integer.toString(SOCKET_PORT);
		}
		return super.getParameter(key);
	}

	
	
	public URL getCodeBase()  {
		if(isStandalone)
			try {
				return  new URL("http://localhost:8100/servlet/");
			} catch (MalformedURLException e) {
				return null;
			}
		else
			return super.getCodeBase();
		
	}
	
	
	public void closeBrowse() {	
		 JSObject.getWindow(this).call("closeWin", null);
	}
	
	/**
	 * <h2><code>getComMode</code></h2>
	 * 
	 * <p>
	 * Returns the currect Communication mode the applet is using. Communication
	 * modes are:
	 * </p>
	 * 
	 * <p>RMI</p>
	 * <p>RAW Socket</p>
	 * <p>Http (Default mode)</p>
	 * 
	 * @return Communication Mode
	 * 
	 */
	public String getComMode() {
		return com_mode;
	}

	/**
	 * <h2><code>getHttpObject</code></h2>
	 * 
	 * <p>
	 * Communication with the server using the Default HTTP mode
	 * </p>
	 * 
	 * @param -
	 *            a request and data object passed to the server for processing
	 * @return - returned data from the server
	 * 
	 */
	private DataSet getHttpObject(DataSet outDataSet) {
		try {
			URL url = new URL(servletURL);
			HttpMessage msg = new HttpMessage(url);
			InputStream in = msg.sendPostMessage(outDataSet);
			ObjectInputStream result = new ObjectInputStream(in);
			// Read the object from the stream
			Object obj = result.readObject();

			DataSet inDataSet = (DataSet) obj;
			// Return the hash response
			return inDataSet;
		} catch (Exception e) {
			e.printStackTrace();
			outDataSet.put(WindowItem.MESSAGE, getMsgText("TXT0039"));
			return null;
		}
	}

	/**
	 * <h2><code>getImageIcon</code></h2>
	 * 
	 * <p>
	 * Get an icon image from the applet environment data
	 * </p>
	 * 
	 * @param -
	 *            requested icon file name
	 * @return - the image icon
	 * 
	 */
	public ImageIcon getImageIcon(String iconName) {
		if (iconName == null)
			return null;
		ImageIcon imageIcon = environmentIcons.getIcon(iconName.toUpperCase());
		if (imageIcon == null)
			imageIcon = environmentIcons.getIcon("DEFAULT.GIF");
		return imageIcon;

	}

	
	/**
	 * <h2><code>getRegistryHost</code></h2>
	 * 6
	 * <p>
	 * Gets the RMI registry host name
	 * </p>
	 * 
	 * @return - the registry host name
	 * 
	 */
	private String getRegistryHost() {
		if (isStandalone)
			return "localhost";
		else
			return getCodeBase().getHost();
	}

	/**
	 * <h2><code>getRegistryName</code></h2>
	 * 
	 * <p>
	 * Gets the RMI registry servlet class name
	 * </p>
	 * 
	 * @return - the registry servlet class name
	 * 
	 */
	private String getRegistryName() {
		return "webBoltOns.ServletConnector";
	}

	/**
	 * <h2><code>getRegistryPort</code></h2>
	 * 
	 * <p>
	 * Gets the RMI registry Port Number
	 * </p>
	 * 
	 * @return - the registry port number
	 * 
	 */
	private int getRegistryPort() {
		return registryPort;
	}

	/**
	 * <h2><code>getRMIObject</code></h2>
	 * 
	 * <p>
	 * Communication with the server using the RMI mode
	 * </p>
	 * 
	 * @param -
	 *            a request and data object passed to the server for processing
	 * @return - returned data from the server
	 * 
	 */
	private DataSet getRMIObject(DataSet outDataSet) {
		try {
			Registry registry = LocateRegistry.getRegistry(getRegistryHost(),
					getRegistryPort());
			ServletInterface server = (ServletInterface) registry
					.lookup(getRegistryName());

			String action = outDataSet.get(WindowItem.ACTION).toString();
			outDataSet.setUserIP(getRegistryHost());
			outDataSet.setRmtHost(getRegistryHost());
			
			if (action.equals(WindowItem.GET_SCRIPT)) {
				return server.getScript(outDataSet);
			} else if (action.equals(WindowItem.GET_MENU)) {
				return server.getMenu(outDataSet);
			} else if (action.equals(WindowItem.GET_BATCH)) {
				return server.execBatch(outDataSet);
			} else {
				return server.getAction(outDataSet);
			}

		} catch (ClassCastException e) {
			System.out.println("Retrieved object was not a Correct: "
					+ e.getMessage());
			return null;
		} catch (NotBoundException e) {
			System.out.println(getRegistryName() + " not bound: " + e.getMessage());
			return null;
		} catch (RemoteException e) {
			System.out.println("Hit remote exception: " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.out.println("Problem getting Object reference: "
					+ e.getClass().getName() + ": " + e.getMessage());
			return null;
		}

	}

	/**
	 * <h2><code>getSocketObject</code></h2>
	 * 
	 * <p>
	 * Communication with the server using the Raw Socket mode
	 * </p>
	 * 
	 * @param -
	 *            a request and data object passed to the server for processing
	 * @return - returned data from the server
	 * 
	 */
	private DataSet getSocketObject(DataSet outDataSet) {
		InputStream in = null;
		OutputStream out = null;
		try {
			// Establish a socket connection with the servlet
			String host = getRegistryHost();
			Socket socket = new Socket(host, getSocketPort());
			out = socket.getOutputStream();
			ObjectOutputStream request = new ObjectOutputStream(out);
			request.writeObject(outDataSet);
			request.flush();
			in = socket.getInputStream();
			ObjectInputStream result = new ObjectInputStream(
					new BufferedInputStream(in));
			Object obj = result.readObject();
			DataSet inhash = (DataSet) obj;
			return inhash;
		} catch (Exception e) {
			e.printStackTrace();
			outDataSet.put(WindowItem.MESSAGE, getMsgText("TXT0039"));
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	/**
	 * <h2><code>getSocketPort</code></h2>
	 * 
	 * <p>
	 * Gets the Socket Port Number
	 * </p>
	 * 
	 * @return - the Socket Port Number
	 * 
	 */
	public int getSocketPort() {
		return socketPort;
	}


	public void init() {
	}

	public void init(String x) {
		init();
	}

	/**
	 * <h2><code>setComMode</code></h2>
	 * 
	 * <p>
	 * sets Communication mode the applet is using. Communication modes are:
	 * </p>
	 * 
	 * <p>RMI</p>
	 * <p>RAW Socket</p>
	 * <p>Http (Default mode)</p>
	 * 
	 * @param CommunicationMode
	 */
	public void setComMode(String cm) {
		com_mode = cm;
	}

	/**
	 * <h2><code>showWebDocument</code></h2>
	 * 
	 * <p>
	 * Display a new browser window.
	 * </p>
	 * 
	 * @param document -
	 *            the URL of the document to display in the new window
	 * 
	 */
	public void showWebDocument(String document) {
		showWebStatus("Opening webBoltOns Server.ServletConnetor:" + document);
			if(isStandalone) {
				System.out.println(document);
			} else {		
				try {
					getAppletContext().showDocument(new URL(document), "_blank");

				} catch (MalformedURLException ex) {
					System.err.println("MalformedURLException -- " + ex);
				}
			}
		showWebStatus("Done");
	}

	/**
	 * <h2><code>showWebStatus</code></h2>
	 * 
	 * <p>
	 * Update the browser diaplay status bar
	 * </p>
	 * 
	 * @param status -
	 *            The texts to display
	 * 
	 */
	public void showWebStatus(String status) {
		if (!isStandalone) {
			getAppletContext().showStatus(status);
		}

	}
	
	
	public void changeMyPassword(JPasswordField opwf, JPasswordField npwf1, JPasswordField npwf2) {
		try {
			CDialog cp = new CDialog(null, this);
			System.out.println(getMsgText("TXT0040"));
			String opw = new String(opwf.getPassword());
			String npw1 = new String(npwf1.getPassword());
			String npw2 = new String(npwf2.getPassword());
			
			if(npw1.equals("") || npw2.equals("") || opw.equals("")) {
				cp.showMessageDialog(getMsgText("TXT0041"), getMsgText("TXT0042"), null);
				return;
			}
			
			if(npw1.length() < 5 || npw2.length() < 5) {
				cp.showMessageDialog(getMsgText("TXT0041"), getMsgText("TXT0043"), null);
				return;
			}
				
			
			DataSet ds = new DataSet();
			ds.putStringField("[old/]",    CSHAPasswordField.sha1(opw));
			ds.putStringField("[new-1/]",  CSHAPasswordField.sha1(npw1));
			ds.putStringField("[new-2/]",  CSHAPasswordField.sha1(npw2));
			
			ds.put(MenuItem.ACTION, "CHANGE_PASSWORD");
			ds.putStringField(WindowItem.CLASSNAME, "webBoltOns.server.UserSecurityManager");
			ds.putStringField(WindowItem.METHOD, "changeMyPassword");	
			ds = postServerRequestImed(ds);
			
			cp.showMessageDialog("Change My Password", ds.getStringField("[response/]"), null);
		
			opwf.setText(null);
			npwf1.setText(null);
			npwf2.setText(null);
			
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Password Not Changed");
			}
	}
	/**
	 * <h2><code>start</code></h2>
	 * 
	 * <p>
	 * Load the applet environment and start the client.
	 * </p>
	 * 
	 */
	public void start() {

		com_mode = "HTTP";
		socketPort = Integer.parseInt(getParameter("socketPort"));
		registryPort = Integer.parseInt(getParameter("registryPort"));
		servletURL = getCodeBase() + getParameter("servletURL");
		reportURL = getCodeBase() + getParameter("reportURL");
		 
		System.out.println("Servlet URL : " + servletURL);
		System.out.println("Report URL :  " + reportURL);
		System.out.println("Socket Port : " + socketPort);
		System.out.println("RMI Port :    " + registryPort);

		lastuse = new java.util.Date().getTime();
		timeout = -1;

		DataSet strtUp = new DataSet();
		System.out.println("Loading Environment...  please wait");
		strtUp.put(MenuItem.ACTION, MenuItem.GET_ICONS);
		strtUp = getHttpObject(strtUp);
		setIcons(strtUp);	
		
		
		strtUp.remove("[Environment_Options/]");
		try{
			if (strtUp.getSecurityManagerStatus())
				loadUserEnvironment(strtUp);
			else	 
				loadGuestEnvironment(strtUp);
		
			strtUp.remove("[Standards/]");
			System.out.println("Environment Loaded ...");		
			System.out.println("Loading Menu  " + servletURL);			
			
			mMnu = new MenuFrame(this);
			mMnu.setMenuMain(strtUp);
			getContentPane().add(mMnu);
			getContentPane().doLayout();
			getContentPane().setVisible(true);
		
		} catch (Exception e) {
			System.out.println("Invalid User!......");
			frame = null;
			closeBrowse();
		}  
	}
	
	
	public String encrypt(String s) {
		try {
			return cipher.encrypt(s);
		} catch (Exception e) {
			return null;
		}
	}

	
	public String decrypt(String s) {
		try {
			return cipher.decrypt(s);
		} catch (Exception e) {
			return null;
		}
	}

	public void execLogout()	{
		getContentPane().removeAll();
		getContentPane().setVisible(false);
		mMnu = null;
		start();
	}

	
    public String [] getMessage(String id) {
    	if (! literals.containsKey(id))
    		return new String [] {id, "", "", ""};
    	
    	return (String[]) literals.get(id); 
    }

  
    public String  getMsgText(String id) {
       	if (! literals.containsKey(id))
       	   return id;
       	
    	String [] mt = (String[]) literals.get(id);
    	return mt[0];
  
    }

    
    public void setMessageLiterals(Hashtable l) {
     literals = l;	
    }
    
	private void setIcons(DataSet dataSet) {			
			DataSet eOptions = (DataSet) dataSet.get("[Environment_Options/]");
			
			cipher = new CipherString((SecretKey) eOptions.get("[cipher_key/]") );
			eOptions.remove("[cipher_key/]");
			
			environmentIcons     = (DataSet) eOptions.get("[iconTable/]");
			serverDateFormat     = eOptions.getStringField("[serverDateFormat/]");	
			banner               = getImageIcon(eOptions.getStringField("[menuBannerImage/]"));
			quickLinkIcon        = getImageIcon(eOptions.getStringField("[menuQuickLinkIcon/]"));
			menuOpenIcon         = getImageIcon(eOptions.getStringField("[menuOpenedIcon/]"));
			menuClosedIcon       = getImageIcon(eOptions.getStringField("[menuClosedIcon/]"));
			menuScriptIcon       = getImageIcon(eOptions.getStringField("[menuScriptIcon/]"));
			menuDocumentIcon     = getImageIcon(eOptions.getStringField("[menuDocumentIcon/]"));
			scriptIcon           = getImageIcon(eOptions.getStringField("[menuScriptLinkIcon/]"));
			documentIcon         = getImageIcon(eOptions.getStringField("[webDocumentURLIcon/]"));
			upIcon               = getImageIcon(eOptions.getStringField("[upMessageArrowIcon/]"));
			downIcon             = getImageIcon(eOptions.getStringField("[downMessageArrowIcon/]"));
			linkButtonIcon       = getImageIcon(eOptions.getStringField("[link_Icon/]"));
			postButtonIcon       = getImageIcon(eOptions.getStringField("[post_Icon/]"));
			errorIcon            = getImageIcon(eOptions.getStringField("[Error_Icon/]"));
			warnIcon             = getImageIcon(eOptions.getStringField("[warn_Icon/]"));
			messageIcon          = getImageIcon(eOptions.getStringField("[message_Icon/]"));
			treeTableOpenIcon    = getImageIcon(eOptions.getStringField("[treeOpenIcon/]"));
			treeTableClosedIcon  = getImageIcon(eOptions.getStringField("[treeClosedIcon/]"));
			treeTableLeafIcon    = getImageIcon(eOptions.getStringField("[treeLeafIcon/]"));
			webEmailIcon         = getImageIcon(eOptions.getStringField("[emailDocumentURLIcon/]"));
			webDocumentIcon      = getImageIcon(eOptions.getStringField("[webDocumentURLIcon/]"));
			tableCopyIcon        = getImageIcon(eOptions.getStringField("[tableCopyIcon/]"));
			tablePrintIcon       = getImageIcon(eOptions.getStringField("[tablePrintIcon/]"));
			optionQuestionIcon   = getImageIcon(eOptions.getStringField("[optionQuestionIcon/]"));
			optionMessageIcon    = getImageIcon(eOptions.getStringField("[optionMessageIcon/]"));
			menuExpandIcon       = getImageIcon(eOptions.getStringField("[menuExpandIcon/]"));
			menuCollapseIcon     = getImageIcon(eOptions.getStringField("[menuCollapseIcon/]"));
			logoImageIcon        = getImageIcon(eOptions.getStringField("[logo/]"));
			logoImagePanel       = getImageIcon(eOptions.getStringField("[logoPanel/]"));			
	} 
	
	
	private void loadGuestEnvironment(DataSet ds) throws Exception {
		securityManager = false;
		ldap = false;
		System.out.println("UserSecurityManager: OFF");
		setEnvironment((DataSet)ds.get("[Standards/]"));
		user = "GENERAL";
		userDesc = "General User";
		timeout = -1;
		lang = "ENG";
		dateFormat = "dd MMM yyyy ";
		menuScript = "jRMenu";
		administratorAccess = true;
		deskTopThemeAccess = true;
		tipAccess = true;
		tableCopyAccess = true;
		tablePrintAccess = true;
		//cipher = new CipherString();
		setComMode("HTTP");
		
	}
	
	
	
	private void loadUserEnvironment(DataSet ds) throws Exception {
		
		System.out.println("UserSecurityManager: ON");
		securityManager = true;
		
		if(ldap) System.out.println("Using LDAP-Auth");
		ds.remove("[Login-UserName/]");
		ds.remove("[Login-Password/]");
		ds.remove("[Login-Clear/]");
		
		ds.put(MenuItem.ACTION, "USER_LOGIN");
		ds = getHttpObject(ds);
		
		while (ds != null && ds.getStringField("[UserManagerStatus/]").equals("Prompt_Access")) {
			System.out.println("Request User Login......");
			CDialog login = new CDialog(null, this);
			ds = login.showLoginDialog(ds);
			if(ds != null) {
				ds.put(MenuItem.ACTION, "USER_LOGIN");
				ds = getHttpObject(ds);
			}
		}
		
		if(ds == null || !ds.getStringField("[UserManagerStatus/]").equals("Grant_Access")){
			CDialog networkError = new CDialog(null, this);
			networkError.showInvalidConnectionDialog(" Login Cancel - Connection Reset " );
			throw new InvalidUserException();
		}
		
		setEnvironment(ds);
		ldap = ds.getBooleanField("LDAP");
		user = ds.getUser();
		userDesc = ds.getUserDescription();
		lang = ds.getStringField("[Language/]");
	 
		menuScript = ds.getStringField("[MENU_SCRIPT/]");
		administratorAccess = ds.getBooleanField("[Administrator/]");
		deskTopThemeAccess = ds.getBooleanField("[DeskTopTheme/]");
		tipAccess = ds.getBooleanField("[TipAccess/]");
		tableCopyAccess = ds.getBooleanField("[CopyAccess/]");
		tablePrintAccess = ds.getBooleanField("[PrintAccess/]");
		setComMode(ds.getStringField("[ConnectionType/]"));
		dateFormat = ds.getStringField("[DateFormat/]");
		
		if(ds.getDoubleField("[TimeOut/]") > 0 )	
		   timeout  = ds.getDoubleField("[TimeOut/]");
		else
		   timeout = -1;		
		
		lastuse = new java.util.Date().getTime();

	}
	

	public String getLanguage() { return lang;}
	
	private void setEnvironment(DataSet dataSet) {
						 
			String bgC = dataSet.getStringField("[BackGroundColor/]");
			String tbC = dataSet.getStringField("[TitleBarColor/]");
			String tbfC = dataSet.getStringField("[TitleBarFontColor/]");
			String wtC = dataSet.getStringField("[WindowTitleColor/]");
			String wtfC = dataSet.getStringField("[WindowTitleFontColor/]");
			String crsC = dataSet.getStringField("[CursorColor/]");
			String hdrF = dataSet.getStringField("[HeaderFont/]");
			String regF = dataSet.getStringField("[RegularFont/]");
			
			bgColor = new Color(DataSet.checkInteger(DataSet.parseProperty(
					"Red", bgC)), DataSet.checkInteger(DataSet.parseProperty(
					"Green", bgC)), DataSet.checkInteger(DataSet.parseProperty(
					"Blue", bgC)));

			tbColor = new Color(DataSet.checkInteger(DataSet.parseProperty(
					"Red", tbC)), DataSet.checkInteger(DataSet.parseProperty(
					"Green", tbC)), DataSet.checkInteger(DataSet.parseProperty(
					"Blue", tbC)));

			tbFontColor = new Color(DataSet.checkInteger(DataSet.parseProperty(
					"Red", tbfC)), DataSet.checkInteger(DataSet.parseProperty(
					"Green", tbfC)), DataSet.checkInteger(DataSet
					.parseProperty("Blue", tbfC)));

			wtColor = new Color(DataSet.checkInteger(DataSet.parseProperty(
					"Red", wtC)), DataSet.checkInteger(DataSet.parseProperty(
					"Green", wtC)), DataSet.checkInteger(DataSet.parseProperty(
					"Blue", wtC)));

			wtFontColor = new Color(DataSet.checkInteger(DataSet.parseProperty(
					"Red", wtfC)), DataSet.checkInteger(DataSet.parseProperty(
					"Green", wtfC)), DataSet.checkInteger(DataSet
					.parseProperty("Blue", wtfC)));

			crsColor = new Color(DataSet.checkInteger(DataSet.parseProperty(
					"Red", crsC)), DataSet.checkInteger(DataSet.parseProperty(
					"Green", crsC)), DataSet.checkInteger(DataSet
					.parseProperty("Blue", crsC)));

			String fontname = DataSet.parseProperty("FontFamily", regF).trim();
			int fontsize = DataSet.checkInteger(DataSet.parseProperty("Size",regF));
			standardFont = new Font(fontname, Font.PLAIN, fontsize);
			fontname = DataSet.parseProperty("FontFamily", hdrF).trim();
			fontsize = Integer.parseInt(DataSet.parseProperty("Size", hdrF));
			headerFont = new java.awt.Font(fontname, Font.BOLD, fontsize);
	}

	
	
	public void stop() {
		
	}
	
	
	public boolean checkTimeout() {
		
		if(timeout > 0) {
			if( ((new java.util.Date().getTime()) - lastuse ) /  (1000 * 60)  > timeout) {
				timeout = -1;
				lastuse = new java.util.Date().getTime();
				CDialog co = new CDialog(getAppletFrame(), this);
				co.showTimeOutCnfrm(this);
				mMnu.doLogout();
				return true;
				
			} else {
				lastuse = new java.util.Date().getTime();			
			}
		}
		
		return false;
	}

	
	class InvalidUserException extends Exception {
		public InvalidUserException() {
			super("Invalid UserID");
		}

		private static final long serialVersionUID = -6375455227311536961L;
	}


}