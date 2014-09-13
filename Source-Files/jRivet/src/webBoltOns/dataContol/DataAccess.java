package webBoltOns.dataContol;

/*
 * $Id: DataAccess.java,v 1.2 2007/04/21 18:31:50 paujones2005 Exp $ $Name:  $
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import javax.crypto.SecretKey;
import javax.naming.Context;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import oracle.jdbc.driver.OracleResultSet;

import org.xml.sax.InputSource;

import webBoltOns.ServletConnector;
import webBoltOns.client.clientUtil.CipherString;
import webBoltOns.server.UserSecurityManager;

/**
 * <h1>DataAccess</h1>
 * 
 * <p>
 * the jRivet Framework (Server-side)
 * </p>
 * 
 * <p>
 * Create a JBDC database connection pool based on values passed from the
 * ConfigHandler object.   
 * </p>
 * 
 */

public class DataAccess {

	private boolean sMnger, jMnger;
	
	private SchemaHandler schema;
	
	
	private DataSet standardTheme = new DataSet();

	private int debuglevel = 0;

	private ServletConnector servletConnector;

	private String configPath, keyPath;
	
	private DataSet eOpts = new DataSet();

    public int registryPort, socketPort;
    
    private CipherString cipher;

	public DataAccessConnectionPool pool = new DataAccessConnectionPool();

	private String windowTitle, bannertitle, connectType, scriptdir,
			imagedir, logsdir, menuDocument, serverDateFormat, docdir;
	
	private String emailServer, emailFromAddress, emailUser, emailPassword;

	public static final String INT_VALUE = "INT", CHR_VALUE = "CHR",
			FLT_VALUE = "FLT", DAT_VALUE = "DAT", BOL_VALUE = "BOL",
			BLB_VALUE = "BLB";

	public final static int LIKE = 0, STARTS_WITH = 1, ENDS_WITH = 2,
			LESS_THAN = 3, GREATER_THAN = 4, EQUAL_TO = 5, NOT_EQUAL_TO = 6;

	public DataAccess(String configPath, String keyPath, ServletConnector servletConnector) {
		this.servletConnector = servletConnector;
		this.configPath = configPath;
		this.keyPath = keyPath;
	}

	
	/**
	 * <h2><code>addToArgumentList</code></h2>
	 * 
	 * <p>
	 * Adds the string <code>nextArg</code> to a comma delimited Argument
	 * List. Returns the comma delimited list
	 * </p>
	 * 
	 * @param String
	 *            argList
	 * @param String
	 *            nextArg
	 * 
	 * @return The comma delimited list as a string
	 */
	public static String addToArgumentList(String argList, String nextArg) {
		if (argList == null || argList.trim().equals(""))
			argList += nextArg;
		else
			argList += ", " + nextArg;
		return argList;
	}

	/**
	 * <h2><code>addToArgumentList</code></h2>
	 * 
	 * <p>
	 * Adds the string array <code>nextArgList</code> to a comma delimited
	 * Argument List. Returns the comma delimited list
	 * </p>
	 * 
	 * @param String
	 *            argList
	 * @param String[]
	 *            nextArgList
	 * 
	 * @return The comma delimited list as a string
	 */
	public static String addToArgumentList(String argumentList,
			String nextArgList[]) {
		for (int x = 0; x < nextArgList.length; x++)
			if (argumentList.indexOf(nextArgList[x]) == -1)
				argumentList = addToArgumentList(argumentList, nextArgList[x]);
		return argumentList;
	}

	/**
	 * <h2><code>buildArgumentList</code></h2>
	 * 
	 * <p>
	 * builds a comma delimited Argument List based on the passed string array
	 * Returns the comma delimited list
	 * </p>
	 * 
	 * @param String[]
	 *            argList
	 * 
	 * @return The comma delimited list as a string
	 */
	public static String buildArgumentList(String argList[]) {
		String argumentList = "";
		for (int x = 0; x < argList.length; x++)
			if (argumentList.indexOf(argList[x]) == -1)
				argumentList = addToArgumentList(argumentList, argList[x]);
		return argumentList;
	}

	/**
	 * <h2><code>addToSearchWhereClause</code></h2>
	 * 
	 * <p>
	 * builds or adds to an SQL where clause based on the passed values as:
	 * </p>
	 * 
	 * @param String
	 *            sql - The as SQL string such as 'Select * from mytable'
	 * @param String
	 *            fieldName - Name of the table column such as 'mycolumn'
	 * @param String
	 *            fieldType - Field data type such as 'INT'
	 * @param String
	 *            whereValue - Value for the field such as '10'
	 * 
	 * @return sql - The sql String - 'Select * from mytable Where mycolumn =
	 *         10'
	 * 
	 */
	public static String addToSearchWhereClause(String sql, String fieldName,
			String fieldType, String whereValue) {
		if (whereValue != null && !whereValue.equals("")
				&& !whereValue.equals("0")) {
			if ((sql.toUpperCase().indexOf("WHERE ")) == -1)
				sql += " Where ";
			else
				sql += " And ";

			if (fieldType.equals("CHR"))
				sql += fieldName + " Like '" + whereValue.trim() + "%' ";
			else if (fieldType.equals("DAT"))
				sql += fieldName + " = '" + whereValue.trim() + "' ";
			else
				sql += fieldName + " = " + whereValue.trim() + " ";
		}
		return sql;
	}

	/**
	 * <h2><code>addToSearchWhereClause</code></h2>
	 * 
	 * <p>
	 * builds or adds to an SQL where clause based on the passed values as:
	 * </p>
	 * 
	 * @param String
	 *            sql - The as SQL string such as 'Select * from mytable'
	 * @param String
	 *            fieldName - Name of the table column such as 'mycolumn'
	 * @param SearchTextFieldDataSet -
	 *            The SearchDataSet value from the CSearchTextField object
	 * 
	 * @return sql - The sql String - 'Select * from mytable Where mycolumn =
	 *         10'
	 * 
	 */
	public static String addToSearchWhereClause(String sql, String fieldName,
			SearchTextFieldDataSet searchDataSet) {

		String whereValue = searchDataSet.getSearchValue().trim(), fieldType = searchDataSet
				.getSearchDataType(), dl = " ";
		int searchType = searchDataSet.getSearchType();

		if (whereValue != null && !whereValue.equals("")
				&& !whereValue.equals("0")) {
			if ((sql.toUpperCase().indexOf("WHERE ")) == -1)
				sql += " Where ";
			else
				sql += " And ";

			if (fieldType.equals("CHR") || fieldType.equals("DAT"))
				dl = "'";

			if (searchType == LIKE && dl.equals("'"))
				sql += fieldName + " Like '%" + whereValue.trim() + "%'";
			else if (searchType == STARTS_WITH && dl.equals("'"))
				sql += fieldName + " Like '" + whereValue.trim() + "%'";
			else if (searchType == ENDS_WITH && dl.equals("'"))
				sql += fieldName + " Like '%" + whereValue.trim() + "'";
			else if (searchType == NOT_EQUAL_TO)
				sql += fieldName + " <> " + dl + whereValue.trim() + dl;
			else if (searchType == LESS_THAN)
				sql += fieldName + " < " + dl + whereValue.trim() + dl;
			else if (searchType == GREATER_THAN)
				sql += fieldName + " > " + dl + whereValue.trim() + dl;
			else
				sql += fieldName + " = " + dl + whereValue.trim() + dl;

		}
		return sql;
	}

	/**
	 * <h2><code>execClose</code></h2>
	 * 
	 * <p>
	 * Closes the passed Sql statement object and returns the connection to the
	 * pool
	 * </p>
	 * 
	 * @param Statement
	 *           the sql statement
	 * @return true if the connection was placed into the pool - false on an SQL
	 *         error
	 * 
	 */
	public boolean execClose(Statement statement) {
		if (statement == null)
			return true;

		try {
			Connection connection = statement.getConnection();
			statement.close();
			pool.releaseConnection(connection);
			statement = null;
			connection = null;
			return true;
		} catch (SQLException err) {
			logMessage("Error closing connection: " + pool.getDriver() + " - "
					+ err.getMessage());

			return false;
		}
	}

	/**
	 * <h2><code>execClose</code></h2>
	 * 
	 * <p>
	 * Closes the passed Sql statement object and returns the connection to the
	 * pool
	 * </p>
	 * 
	 * @param PreparedStatement
	 *           the SQL statement
	 * @return true if the connection was placed into the pool - false on an SQL
	 *         error
	 * 
	 */
	public boolean execClose(PreparedStatement statement) {
		if (statement == null)
			return true;

		try {
			Connection connection = statement.getConnection();
			statement.close();
			pool.releaseConnection(connection);
			statement = null;
			connection = null;
			return true;
		} catch (SQLException err) {
			logMessage("Error closing connection: " + pool.getDriver() + " - "
					+ err.getMessage());

			return false;
		}
	}

	/**
	 * <h2><code>execConnectUpdate</code></h2>
	 * 
	 * <p>
	 * Gets a connection from the pool and creates an 'updatable' Statement
	 * object
	 * </p>
	 * 
	 * @return Statement
	 * 
	 */
	public Statement execConnectUpdate() {
		try {
			Connection connection = pool.getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			return statement;
		} catch (Exception err) {
			logMessage("Error opening connection: " + pool.getDriver() + " - "
					+ err.getMessage());
			return null;
		}
	}

	/**
	 * <h2><code>execConnectReadOnly</code></h2>
	 * 
	 * <p>
	 * Gets a connection from the pool and creates a 'read-only' Statement
	 * object
	 * </p>
	 * 
	 * @return Statement
	 * 
	 */
	public Statement execConnectReadOnly() {
		try {
			Connection connection = pool.getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
			return statement;
		} catch (Exception err) {
			logMessage("Error opening connection: " + pool.getDriver() + " - " + err.getMessage());
			err.printStackTrace();
			return null;
		}
	}

	/**
	 * <h2><code>execPreparedConnect</code></h2>
	 * 
	 * <p>
	 * Gets a connection from the pool and creates a PreparedStatement object
	 * </p>
	 * 
	 * @return PreparedStatement
	 * 
	 */
	public PreparedStatement execPreparedConnect(String preparedSQL) {
		try {
			Connection connection = pool.getConnection();
			PreparedStatement statement = connection
					.prepareStatement(preparedSQL);
			return statement;
		} catch (Exception err) {
			logMessage("Error opening connection: " + pool.getDriver() + " - " + err.getMessage());
			err.printStackTrace();
			return null;
		}
	}

	/**
	 * <h2><code>logMessage</code></h2>
	 * 
	 * <p>
	 * Logs the string passed to the serlvet log file
	 * </p>
	 * 
	 * @param String -
	 *            message - the string to place in the log
	 * 
	 */
	public void logMessage(String message) {
		if (servletConnector != null)
			servletConnector.log(message);
		else
			System.err.println(message);
	}

	/**
	 * <h2><code>getBannerTitle</code></h2>
	 * 
	 * <p>
	 * returns the banner title string (from ConfigFile1.XML)
	 * </p>
	 * 
	 * @return String - The title string
	 * 
	 */
	public String getBannerTitle() {
		return bannertitle;
	}

	/**
	 * <h2><code>getStandardMenu</code></h2>
	 * 
	 * <p>
	 * returns default standard menu file name (from ConfigFile1.XML)
	 * </p>
	 * 
	 * @return String - the menu name
	 * 
	 */
	public String getStandardMenu() {
		return menuDocument;
	}

	/**
	 * <h2><code>getConnectionType</code></h2>
	 * 
	 * <p>
	 * returns default conection type (from ConfigFile1.XML)
	 * </p>
	 * 
	 * @return String - the conection type
	 * 
	 */
	public String getConnectionType() {
		return connectType;
	}

	/**
	 * <h2><code>getDebugLevel</code></h2>
	 * 
	 * <p>
	 * returns debugging level (from ConfigFile1.XML)
	 * </p>
	 * 
	 * @return String - the debugging level
	 * 
	 */
	public int getDebugLevel() {
		return debuglevel;
	}

	/**
	 * <h2><code>getMenuPath</code></h2>
	 * 
	 * <p>
	 * returns script file path (from ConfigFile1.XML)
	 * </p>
	 * 
	 * @return String - the script file path
	 */
	public String getMenuPath() {
		return getScriptPath();
	}

	/**
	 * <h2><code>getFieldValue</code></h2>
	 * 
	 * <p>
	 * returns an object from a result set based on the following input parms:
	 * </p>
	 * 
	 * 
	 * @param ResultSet
	 *            resultSet - The result set from an SQL qry
	 * @param String
	 *            field - The table column in to extract from the result set
	 * @param String
	 *            type - the column type as: INT=Integer CHR=String BOL=Boolean
	 *            DAT=Date type FLT=double BLB=binary image stream
	 * 
	 * 
	 * 
	 * @return Object - requested value from the SQL result set
	 */
	public static Object getFieldValue(ResultSet resultSet, String field,
			String type) throws SQLException, IOException {

		if (type.equals("INT"))
			return Integer.toString(resultSet.getInt(field));
		else if (type.equals("CHR"))
			return resultSet.getString(field);
		else if (type.equals("BOL"))
			return Boolean.toString(resultSet.getBoolean(field));
		else if (type.equals("FLT"))
			return Double.toString(resultSet.getDouble(field));
		else if (type.equals("DAT"))
			return (resultSet.getString(field));
		else if (type.equals("BLB")) {
			if(resultSet instanceof  OracleResultSet) {
				Blob blb = resultSet.getBlob(field); 
				if(blb == null || blb.length() == 0 ) return null;
				else return blb.getBytes(1, (int) blb.length());
			} else	{
				return resultSet.getBytes(field);
			}
		} else
			return null;
	}

	/**
	 * <h2><code>setPreparedValue</code></h2>
	 * 
	 * <p>
	 * Sets a value in a Prepared Statement using the following parms
	 * </p>
	 * 
	 * 
	 * @param PreparedStatement -
	 *            Statement to set
	 * @param int
	 *            fieldIndex
	 * @param String
	 *            field - the table column name
	 * @param String
	 *            type - the column type as: INT=Integer CHR=String BOL=Boolean
	 *            DAT=Date type FLT=double BLB=binary image stream
	 * @param DataSet
	 *            values - Applet data set with the values from the screen
	 */
	public static void setPreparedValue(PreparedStatement preparedStatement,
			int fieldIndex, String field, String type, DataSet values)
			throws SQLException, IOException {
		
		if (type.equals("INT"))
			preparedStatement.setInt(fieldIndex, values.getIntegerField(field));
		else if (type.equals("CHR"))
			preparedStatement.setString(fieldIndex, values.getStringField(field));
		else if (type.equals("BOL"))
			preparedStatement.setBoolean(fieldIndex, values.getBooleanField(field));
		else if (type.equals("FLT"))
			preparedStatement.setDouble(fieldIndex, values.getDoubleField(field));
		else if (type.equals("DAT"))
			preparedStatement.setString(fieldIndex, values.getStringField(field));
		else if (type.equals("BLB")){
			byte [] b = values.getStreamArray(field);
			InputStream in = new ByteArrayInputStream(b);
			preparedStatement.setBinaryStream(fieldIndex, in , b.length );
		}
	
	
	}


	public String [] getTableColumns(String table) throws DBSchemaException {
		return schema.getTableColumns(table);
	}
	
	public String getDataType(String column) throws DBSchemaException {
		return schema.getDataType(column);
	}
	
	
	public String getDataType(String table, String column) throws DBSchemaException {
		return schema.getDataType(table, column);
	}
	
	
	public boolean executeQuery(String qry, String table, DataSet ds) throws DBSchemaException  {
		if(ds != null)
			return executeQuery(qry, schema.getTableColumns(table), ds);
		else
			return executeQuery(qry, new String [] {}, null);
	}
	
	
	public boolean executeQuery(String qry, String columns [], DataSet ds) throws DBSchemaException  {
		ResultSet rs;
		Statement stmt = execConnectReadOnly();
		boolean rtn = false;
		int c=0;
		try {
			rs = stmt.executeQuery(qry);
			if (rs.next()) {
				rtn = true;
				if(columns != null && ds != null ) {
					for (c = 0; c < columns.length; c++)  
						if(schema.isColumn(columns[c]))
							ds.put(columns[c], 
									getFieldValue(rs, columns[c], getDataType(columns[c])));
				}				 		
			}	
			
			if(debuglevel != 0)  logMessage(qry + " - " + ds.toString());
			
		} catch (Exception er) {
			logMessage("\n*** DataBase Execption  -> Executing SQL - " + qry );
			logMessage("   ------- DataSet --> " + ds.toString() );
			er.printStackTrace();
			throw new DBSchemaException();
		} finally {
			execClose(stmt);
		}
		return rtn;
	}
	
	
	public void executeInsertQuery(String table,  DataSet ds) throws DBSchemaException  {
		 executeInsertQuery(table, schema.getTableColumns(table), ds);
	}
	
	
	public void executeInsertQuery(String table, String columns [], DataSet ds) throws DBSchemaException  {
		PreparedStatement stmt = null;
		String fl = "", vl = "", qry = "";
		int cc=1, x=0;
		try {
			for (x = 0; x < columns.length; x++) {
				if(ds.containsKey(columns[x]) && schema.isColumn(columns[x])) {
					fl = addToArgumentList(fl, columns[x]);
					vl = addToArgumentList(vl, "?");
				}
			}
			
			qry = "Insert Into "+ table + " (" + fl + " ) Values (" + vl + ") ";
			stmt = execPreparedConnect(qry);
			
			for (x = 0; x < columns.length; x++) 
				if(ds.containsKey(columns[x]) && schema.isColumn(columns[x]))  
					setPreparedValue(stmt, cc++, columns[x], schema.getDataType(columns[x]), ds);
		
			stmt.executeUpdate();
			
			if(debuglevel != 0)  logMessage(qry + " - " + ds.toString());
				
		} catch (Exception er) {
			logMessage("\n*** DataBase Execption  -> Executing SQL -  " + qry );
			logMessage("   ------- DataSet --> " + ds.toString() );
			er.printStackTrace();
			throw new DBSchemaException();
		} finally {
			execClose(stmt);
		}
	}
	
	
	
	public void  executeUpdateQuery(String table,  DataSet ds, String whereClause) throws DBSchemaException  {
		executeUpdateQuery(table, schema.getTableColumns(table), ds, whereClause);	
	}
	
	
	public void executeUpdateQuery(String table, String columns [], DataSet ds, String whereClause) throws DBSchemaException  {
		PreparedStatement stmt = null;
		String vl = "", qry="";
		int cc=1, x=0;
		try {
			for (x = 0; x < columns.length; x++)
				if(whereClause.indexOf(columns[x]) == -1 && ds.containsKey(columns[x]) 
																&& schema.isColumn(columns[x])) 
					vl = addToArgumentList(vl,  columns[x] + " = ? ");
			
			qry = "Update " + table +" Set " + vl + whereClause;
			stmt = execPreparedConnect(qry);
			
			for (x = 0; x < columns.length; x++) 
				if(whereClause.indexOf(columns[x]) == -1 && ds.containsKey(columns[x])
																&& schema.isColumn(columns[x])) 
					setPreparedValue(stmt, cc++, columns[x], schema.getDataType(table,  columns[x]), ds);	    
			
			stmt.executeUpdate();
			
			if(debuglevel != 0)  logMessage(qry + " - " + ds.toString());
		
		} catch (Exception er) {
			logMessage("\n*** DataBase Execption  -> Executing SQL - " + qry );
			logMessage("   ------- DataSet --> " + ds.toString() );
			er.printStackTrace();
			throw new DBSchemaException();
		} finally {
			execClose(stmt);
		}	
	}
	
	
	public void executePreparedQuery(PreparedStatement stmt, String columns [], DataSet ds) throws DBSchemaException  {
		try {	
			for (int x = 0; x < columns.length; x++) 
				setPreparedValue(stmt, x+1, columns[x], schema.getDataType( columns[x]), ds);
			stmt.executeUpdate();			
		} catch (Exception er) {
			logMessage("\n*** DataBase Execption  -> PreparedStatement -  " + stmt.toString() );
			logMessage("   ------- DataSet --> " + ds.toString() );
			er.printStackTrace();
			throw new DBSchemaException();
		} 
	}
	
	
	public Vector  executeVectorQuery(String qry, String columns []) throws DBSchemaException  {
		Vector<Object[]> rv = new Vector<Object[]>();
		ResultSet rs;
		Statement stmt = execConnectReadOnly();
		try {
			rs = stmt.executeQuery(qry);
			while (rs.next()) {
				Object[] tableRow = new String[columns.length];
				for(int c = 0; c < tableRow.length; c++) {
					String column = columns[c];
					String type = getDataType(column);
					tableRow[c]= getFieldValue(rs, column, type);
				}		
				rv.addElement(tableRow);
			}
			
			if(debuglevel != 0)  logMessage(qry + " - " + rv.toString());
			
		} catch (Exception er) {
			logMessage("\n** DataBase Execption  -> Executing SQL -  " + qry );
			er.printStackTrace();
			throw new DBSchemaException();
		} finally {
			execClose(stmt);
		}
	return rv;
	}
	
	
	/**
	 * <h2><code>getStandardUserTheme</code></h2>
	 * 
	 * <p>
	 * returns a data set object with the standard user desktop theme
	 * </p> 
	 * 
	 * @return DataSet - with the standard theme
	 * 
	 */
	public DataSet getStandardUserTheme() {
		return standardTheme;
	}

	/**
	 * <h2><code>isSecurityManagerON</code></h2>
	 * 
	 * <p>returns status of the Security Manager</p>
	 * <p>	true =  Security Manager is loaded</p>
	 * <p>	false =  Security Manager is null</p>
	 * 
	 * @return boolean - the status
	 */
	public boolean isSecurityManagerON() {
		return sMnger;
	}

	/**
	 * <h2><code>isSecurityManagerON</code></h2>
	 * 
	 * <p>returns status of the Security Manager</p>
	 * <p>	true =  Security Manager is loaded</p>
	 * <p>	false =  Security Manager is null</p>
	 * 
	 * @return boolean - the status
	 */
	public boolean isJRivetSecurityON() {
		return jMnger;
	} 
	
	/**
	 * <h2><code>getImagePath</code></h2>
	 * 
	 * <p>returns the file path to the image directory</p>
	 * 
	 * @return string - image path
	 */
	public String getImagePath() {
		return imagedir;
	}



	/**
	 * <h2><code>getScriptPath</code></h2>
	 * 
	 * <p>returns the file path to the script directory</p>
	 * 
	 * @return string - script path
	 */	
	public String getScriptPath() {
		return scriptdir;
	}

	/**
	 * <h2><code>getDocumentPath</code></h2>
	 * 
	 * <p>returns the file path to the script directory</p>
	 * 
	 * @return string - script path
	 */	
	public String getDocPath() {
		return docdir;
	}
	
	/**
	 * <h2><code>getServerLogsPath</code></h2>
	 * 
	 * <p>returns the file path to the logs directory</p>
	 * 
	 * @return string - logs path
	 */	
	public String getServerLogsPath() {
		return logsdir;
	}

	/**
	 * <h2><code>getTotalConnections</code></h2>
	 * 
	 * <p>returns total number of connections in the database pool</p>
	 * 
	 * @return int - number of open JBDC connections
	 */	
	public int getTotalConnections() {
		return pool.getSize();
	}

	/**
	 * <h2><code>getWindowTitle</code></h2>
	 * 
	 * <p>returns applet window title</p>
	 * 
	 * @return String
	 */	
	public String getWindowTitle() {
		return windowTitle;
	}

	/**
	 * <h2><code>getServerDateFormat</code></h2>
	 * 
	 * <p>returns the date format used to populate date fields with tables</p>
	 * 
	 * @return String
	 */	
	public String getServerDateFormat() {
		return serverDateFormat;
	}

 
	
	/**
	 * <h2><code>getEmailServer</code></h2>
	 * 
	 * <p>returns name of the email server set in the config file </p>
	 * 
	 * @return String
	 */	
	public String getEmailServer() {
		return emailServer;
	}
	
	
	
	/**
	 * <h2><code>getEmailFromUser</code></h2>
	 * 
	 * <p>returns name of the email from  set in the config file </p>
	 * 
	 * @return String
	 */	
	public String getEmailFromUser() {
		return emailUser;
	}
	 
	
	/**
	 * <h2><code>getEmailFromPassword</code></h2>
	 * 
	 * <p>returns name of the email from  set in the config file </p>
	 * 
	 * @return String
	 */	
	public String getEmailFromPassword() {
		return emailPassword;
	}
	
	/**
	 * <h2><code>getEmailFromAddress</code></h2>
	 * 
	 * <p>returns name of the email from  set in the config file </p>
	 * 
	 * @return String
	 */	
	public String getEmailFromAddress() {
		return emailFromAddress;
	}	
	
	/**
	 * <h2><code>initDataAccess</code></h2>
	 * 
	 * <p>create the connection pool and sets a number of 
	 *  values needed by the server</p>
	 * 
	 */	
	public boolean initDataAccess() {
		 
		try {

			File file = new File(configPath);
			FileReader reader = new FileReader(file);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			ConfigHandler handler = new ConfigHandler();
			sp.parse(new InputSource(reader), handler);
			Hashtable cfgTable = handler.getTable();
				
			menuDocument = (String) cfgTable.get("MENU");
			windowTitle = (String) cfgTable.get("WINDOW_TITLE");
			bannertitle = (String) cfgTable.get("BANNER_TITLE");
			connectType = (String) cfgTable.get("CONNECT_TYPE");
			debuglevel = Integer.parseInt((String) cfgTable.get("DEBUG_LEVEL"));
			sMnger = true;
			jMnger = true;	
		 
	    	File keyFile = new File(keyPath);
	    	ObjectInputStream i = new ObjectInputStream(new FileInputStream(keyFile));
	    	Object key = i.readObject();
			cipher = new CipherString((SecretKey)key);
	    	i.close();			
						
			registryPort = Integer.parseInt((String) cfgTable.get("REGISTRY_PORT"));
			socketPort = Integer.parseInt((String) cfgTable.get("SOCKET_PORT"));

			scriptdir = (String) cfgTable.get("SCRIPT_DIRECTORY");
			logsdir = (String) cfgTable.get("LOG_DIRECTORY");
			imagedir = (String) cfgTable.get("IMAGE_DIRECTORY");
			docdir = (String) cfgTable.get("DOC_DIRECTORY");

			serverDateFormat = (String) cfgTable.get("SERVER_DATE_FORMAT");
 		 
			emailServer = (String) cfgTable.get("Email_Server");
			emailFromAddress = (String) cfgTable.get("Email_Address");
			emailUser = (String) cfgTable.get("Email_User");
			emailPassword = (String) cfgTable.get("Email_Password");

			schema = new SchemaHandler();
			schema.loadDBSchema((String) cfgTable.get("DATABASE_SCHEMA"), this);
				
		    eOpts = new DataSet();
			eOpts.putStringField("[optionQuestionIcon/]",  (String) cfgTable .get("optionQuestionIcon"));
			eOpts.putStringField("[optionMessageIcon/]", (String) cfgTable .get("optionMessageIcon"));
			eOpts.putStringField("[tableCopyIcon/]",(String) cfgTable.get("tableCopyIcon"));
			eOpts.putStringField("[tablePrintIcon/]", (String) cfgTable.get("tablePrintIcon"));
			eOpts.putStringField("[link_Icon/]", (String) cfgTable.get("link_Icon"));
			eOpts.putStringField("[post_Icon/]", (String) cfgTable.get("post_Icon"));
			eOpts.putStringField("[Error_Icon/]", (String) cfgTable.get("error_Icon"));
			eOpts.putStringField("[warn_Icon/]", (String) cfgTable.get("warn_Icon"));
			eOpts.putStringField("[message_Icon/]", (String) cfgTable.get("message_Icon"));
			eOpts.putStringField("[treeOpenIcon/]", (String) cfgTable.get("treeOpenIcon"));
			eOpts.putStringField("[treeClosedIcon/]", (String) cfgTable.get("treeClosedIcon"));
			eOpts.putStringField("[treeLeafIcon/]", (String) cfgTable.get("treeLeafIcon"));
			eOpts.putStringField("[webDocumentURLIcon/]",   (String) cfgTable.get("webDocumentURLIcon"));
			eOpts.putStringField("[emailDocumentURLIcon/]",(String) cfgTable.get("emailDocumentURLIcon"));
			eOpts.putStringField("[upMessageArrowIcon/]", (String) cfgTable.get("upMessageArrowIcon"));
			eOpts.putStringField("[downMessageArrowIcon/]",  (String) cfgTable.get("downMessageArrowIcon"));
			eOpts.putStringField("[menuBannerImage/]",  (String) cfgTable.get("menuBannerImage"));
			eOpts.putStringField("[menuOpenedIcon/]", (String) cfgTable.get("menuOpenedIcon"));
			eOpts.putStringField("[menuClosedIcon/]",(String) cfgTable.get("menuClosedIcon"));
			eOpts.putStringField("[menuScriptIcon/]", (String) cfgTable.get("menuScriptIcon"));
			eOpts.putStringField("[menuDocumentIcon/]", (String) cfgTable.get("menuDocumentIcon"));
			eOpts.putStringField("[menuScriptLinkIcon/]", (String) cfgTable.get("menuScriptLinkIcon"));
			eOpts.putStringField("[menuQuickLinkIcon/]", (String) cfgTable.get("menuQuickLinkIcon"));
			eOpts.putStringField("[menuExpandIcon/]", (String) cfgTable.get("menuExpandIcon"));
			eOpts.putStringField("[menuCollapseIcon/]", (String) cfgTable.get("menuCollapseIcon"));
			eOpts.putStringField("[logoPanel/]", "logoPanel.JPG");
			eOpts.putStringField("[logo/]", "logo.gif");
			eOpts.putStringField("[defaultConnectionType/]", getConnectionType());
			eOpts.putStringField("[totalOpeningConnections/]", Integer.toString(getTotalConnections()));
			eOpts.putStringField("[serverDateFormat/]", getServerDateFormat());
			eOpts.putStringField("[scriptAccessPath/]", getScriptPath());
			eOpts.putStringField("[imageAccessPath/]", getImagePath());
			eOpts.put("[StandardUserTheme/]", getStandardUserTheme());
			
			
			 
			eOpts.putBooleanField("LDAP", DataSet.checkBoolean((String) cfgTable.get("LDAP")));
			if(eOpts.getBooleanField("LDAP")) {
			  eOpts.putStringField(Context.INITIAL_CONTEXT_FACTORY, 
					(String) cfgTable.get(Context.INITIAL_CONTEXT_FACTORY));
			  eOpts.putStringField(Context.SECURITY_AUTHENTICATION, 
					(String) cfgTable.get(Context.SECURITY_AUTHENTICATION));
			  eOpts.putStringField(Context.SECURITY_PROTOCOL, 
					(String) cfgTable.get(Context.SECURITY_PROTOCOL));
			  eOpts.putStringField(Context.PROVIDER_URL, 
					(String) cfgTable.get(Context.PROVIDER_URL));
			  eOpts.putStringField(UserSecurityManager.PRINCIPAL_DN_PREFIX_OPT, 
					(String) cfgTable.get(UserSecurityManager.PRINCIPAL_DN_PREFIX_OPT));
			  eOpts.putStringField(UserSecurityManager.PRINCIPAL_DN_SUFFIX_OPT, 
					(String) cfgTable.get(UserSecurityManager.PRINCIPAL_DN_SUFFIX_OPT));
			  eOpts.putStringField(UserSecurityManager.MATCH_ON_USER_DN_OPT, 
					  (String) cfgTable.get(UserSecurityManager.MATCH_ON_USER_DN_OPT));
			  eOpts.putStringField("[LDAP_Group/]",(String) cfgTable.get("LDAP_Group"));
			  
			}
			
			eOpts.put("[iconTable/]", loadAllIcons());
			eOpts.put("[cipher_key/]", key);
			
			standardTheme.putStringField("[BackGroundColor/]", (String) cfgTable.get("BackGroundColor"));
			standardTheme.putStringField("[TitleBarColor/]", (String) cfgTable .get("TitleBarColor"));
			standardTheme.putStringField("[TitleBarFontColor/]", (String) cfgTable.get("TitleBarFontColor"));
			standardTheme.putStringField("[WindowTitleColor/]", (String) cfgTable.get("WindowTitleColor"));
			standardTheme.putStringField("[WindowTitleFontColor/]", (String) cfgTable.get("WindowTitleFontColor"));
			standardTheme.putStringField("[CursorColor/]", (String) cfgTable .get("CursorColor"));
			standardTheme.putStringField("[HeaderFont/]", (String) cfgTable .get("HeaderFont"));
			standardTheme.putStringField("[RegularFont/]", (String) cfgTable .get("RegularFont"));
			standardTheme.putStringField("DATE_FORMAT", (String) cfgTable .get("DATE_FORMAT"));
			
			pool.setDebugLevel(debuglevel);
			pool.setDriver((String) cfgTable.get("DRIVER"));
			pool.setURL((String) cfgTable.get("URL"));
			pool.setSize(Integer.parseInt((String) cfgTable .get("CONNECTIONS")));
			pool.setUsername((String) cfgTable.get("USERID"));
			pool.setPassword((String) cfgTable.get("PASSWORD"));
			pool.initializePool();

			return true;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;
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
	
	/**
	 * <h2><code>loadAllIcons</code></h2>
	 * 
	 * <p>
	 *  load all icon images to be passed to the client 
	 * </p>
	 *    
	 */
	private DataSet loadAllIcons() {
		DataSet eicons = new DataSet();
	
		try {
			String icons[] = new File(getImagePath()).list();
			for (int i = 0; i < icons.length; i++) 
				if (icons[i].toUpperCase().endsWith(".GIF")
						|| icons[i].toUpperCase().endsWith(".JPG")) {
				    File iconFile = new File(getImagePath() + icons[i]);
					byte [] iconImage = new byte[(int)iconFile.length()];
				    InputStream icon = new FileInputStream(iconFile);
					icon.read(iconImage);
					eicons.putIcon(icons[i].toUpperCase(),iconImage);
				}
			
		} catch (Exception er) {
			System.err.println(er);
		}
		return eicons;
	}
	
	
	
	public DataSet getEOpts() {
		return eOpts;
	}
	
	
}