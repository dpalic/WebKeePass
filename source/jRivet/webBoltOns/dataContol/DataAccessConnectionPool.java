package webBoltOns.dataContol;

/*
 * $Id: DataAccessConnectionPool.java,v 1.1 2007/04/20 19:37:22 paujones2005 Exp $ $Name:  $
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


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
@SuppressWarnings("deprecation")
public class DataAccessConnectionPool {

	private String driver = new String("");
	private String password = new String("");
	private Vector<DataAccessConnection> pool = null;
	private int size = 0, debublevel = 0, stale = 60 * 2, timeout = 5;
	private String url = new String("");
	private String username = new String("");

	public DataAccessConnectionPool() {

	}

	/**
	 * <h2><code>getConnection</code></h2>
	 * 
	 * <p>
	 * Adds the DataAccessConnection to the pool
	 * </p>
	 * 
	 * @param DataAccessConnection value 
	 */
	private void addConnection(DataAccessConnection value) {
		if (pool == null)  
			pool = new Vector<DataAccessConnection>(size);

		pool.addElement(value);
	}

	/**
	 * <h2><code>createConnection</code></h2>
	 * 
	 * <p>
	 * creates a new Connection
	 * </p>
	 * 
	 * @return Connection 
	 */
	private Connection createConnection() throws Exception {
		Connection con = null;
		if(!username.equals("") && !password.equals(""))
			con = DriverManager.getConnection(url, username, password);
		else
			con = DriverManager.getConnection(url);
		
		return con;
	}

	/**
	 * <h2><code>emptyPool</code></h2>
	 * 
	 * <p>
	 * Empty all connections from the pool
	 * </p>
	 * 
	 */
	public synchronized void emptyPool() {
		for (int x = 0; x < pool.size(); x++) {
			if (debublevel > 0) {
				System.err.println("Closing JDBC Connection " + x);
			}
			
			DataAccessConnection pcon = (DataAccessConnection) pool.elementAt(x);

			if (!pcon.inUse()) {
				pcon.close();
			} else {
				try {
					java.lang.Thread.sleep(30000);
					pcon.close();
				} catch (InterruptedException ie) {
					System.err.println(ie.getMessage());
				}
			}
		}
	}


	/**
	 * <h2><code>getConnection()</code></h2>
	 * 
	 * <p>
	 * returns a connecttion for the dataaccess object
	 * </p>
	 * 
	 * @return Connection
	 * 
	 */
	public synchronized Connection getConnection() throws Exception {
		DataAccessConnection pcon = null;
		for (int x = 0; x < pool.size(); x++) {
			pcon = (DataAccessConnection) pool.elementAt(x);
			
			if(pcon.isStaleConnection(stale) ||
				(pcon.inUse() && pcon.isStaleConnection(timeout)) ) {
				System.err.println("Re-opening Stale Connection " + x);
				pcon.close();
				Connection con = createConnection();
				pcon = new DataAccessConnection(con);
				pcon.setInUse(false);
				pool.setElementAt(pcon,x);
			}
						
			
			if (!pcon.inUse() ) {
				pcon.setInUse(true);
				if (debublevel > 0) {
					System.err.println("Opening Connection " + x);
				}
				return pcon.getConnection();
			}
		}

		try {
			Connection con = createConnection();
			pcon = new DataAccessConnection(con);
			pcon.setInUse(true);
			pool.addElement(pcon);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pcon.getConnection();
	}


	/**
	 * <h2><code>getDriver</code></h2>
	 * 
	 * <p>
	 * returns the driver name
	 * </p>
	 * 
	 * @return String - Driver Name
	 * 
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * <h2><code>getPassword</code></h2>
	 * 
	 * <p>
	 * returns the database password
	 * </p>
	 * 
	 * @return String -  
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <h2><code>getSize</code></h2>
	 * 
	 * <p>
	 * returns the size of the pool in numnber of open connections
	 * </p>
	 * 
	 * @return int 
	 * 
	 */
	public int getSize() {
		return size;
	}

	/**
	 * <h2><code>getURL</code></h2>
	 * 
	 * <p>
	 * returns the database URL
	 * </p>
	 * 
	 * @return String 
	 * 
	 */
	public String getURL() {
		return url;
	}

	/**
	 * <h2><code>getUserName</code></h2>
	 * 
	 * <p>
	 * returns the database User Name
	 * </p>
	 * 
	 * @return String 
	 * 
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * <h2><code>initializePool</code></h2>
	 * 
	 * <p>
	 *   Sets up the pool
	 * </p>
	 */
	public synchronized void initializePool() throws Exception {
		if (driver == null) {
			throw new Exception("No Driver Name Specified!");
		}
		if (url == null) {

			throw new Exception("No URL Specified!");
		}
		if (size < 1) {
			throw new Exception("Pool size is less than 1!");
		}
		try {
			Class.forName(driver);
			for (int x = 0; x < size; x++) {
				if (debublevel > 0) {
					System.err.println("Opening JDBC Connection " + x);

				}
				Connection con = createConnection();
				if (con != null) {
					DataAccessConnection pcon = new DataAccessConnection(con);
					addConnection(pcon);
				}
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.err.println(cnfe.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * <h2><code>releaseConnection</code></h2>
	 * 
	 * <p>
	 *   returns a connection to a  pool
	 * </p>
	 *
	 * @param Connection - the returning connection
	 */
	public synchronized void releaseConnection(Connection con) {

		// find the DataAccessConnection Object
		for (int x = 0; x < pool.size(); x++) {
			DataAccessConnection pcon = (DataAccessConnection) pool.elementAt(x);

			// Check for correct Connection
			if (pcon.getConnection() == con) {
				if (debublevel > 0) {
					System.err.println("Releasing Connection " + x);
				}
				pcon.setInUse(false);

				break;
			}
		}
	}

	/**
	 * <h2><code>setDebugLevel</code></h2>
	 * 
	 * <p>
	 *   Sets the debugger level
	 * </p>
	 *
	 * @param int -
	 */
	public void setDebugLevel(int level) {
		this.debublevel = level;
	}


	/**
	 * <h2><code>setDriver</code></h2>
	 * 
	 * <p>
	 * Set the value of the JDBC Driver
	 * </p>
	 *
	 * @param String  - Driver Name
	 */
	public void setDriver(String value) {
		if (value != null)  
			driver = value;
	}

	/**
	 * <h2><code>setPassword</code></h2>
	 * 
	 * <p>
	 * Set the database password
	 * </p>
	 *
	 * @param String  - password
	 */
	public void setPassword(String value) { 
		if (value != null) 
			password = value;	
	}

	/**
	 * <h2><code>setSize</code></h2>
	 * 
	 * <p>
	 * Set the initial number of connections
	 * </p>
	 *
	 * @param int - size
	 */
	public void setSize(int value) {
		if (value > 1)  
			size = value;
		 
	}

	
	/**
	 * <h2><code>setTimeOut</code></h2>
	 * 
	 * <p>
	 * Sets the timeout value for a connection
	 * </p>
	 *
	 * @param int - size
	 */
	public void setTimeOut(int value) {
		if (value > 1)  
			timeout = value;
		 
	}

	
	/**
	 * <h2><code>setTimeOut</code></h2>
	 * 
	 * <p>
	 * Sets the Stale timeout value for a connection
	 * </p>
	 *
	 * @param int - size
	 */
	public void setStaleTimeOut(int value) {
		if (value > 1)  
			stale = value;
		 
	}	
	
	/**
	 * <h2><code>setURL</code></h2>
	 * 
	 * <p>
	 * Set the URL Pointing to the Datasource
	 * </p>
	 *
	 * @param String - Database URL
	 */
	public void setURL(String value) {
		if (value != null)  
			url = value;
	}
		
	/**
	 * <h2><code>setURL</code></h2>
	 * 
	 * <p>
	 * Set the username
	 * </p>
	 *
	 * @param String - Database password
	 */
	public void setUsername(String value) {
		if (value != null) 
			username = value;
	}
}