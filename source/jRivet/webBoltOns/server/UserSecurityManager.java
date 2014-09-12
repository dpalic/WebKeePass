package webBoltOns.server;

/*
 * $Id: UserSecurityManager.java,v 1.2 2007/04/21 18:31:50 paujones2005 Exp $ $Name:  $
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

import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import webBoltOns.client.WindowItem;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;
import webBoltOns.dataContol.DBSchemaException;

public class UserSecurityManager {

	public final static int NONE = 1;
	public final static int READ = 2;
	public final static int READ_WRITE = 3;
	protected  String reqUserID, reqDesc, reqGroup;

	public UserSecurityManager() { }
	

	
	
	/* */
	private String buildColorAttr(String color, ResultSet resultSet)
			throws SQLException {
		 return "[ColorName='" + color + "'  Red='"
				+ resultSet.getInt(color + "Red") + "'  Blue='"
				+ resultSet.getInt(color + "Blue") + "'  Green='"
				+ resultSet.getInt(color + "Green") + "' /]";
	}

	
	
	/* */
	private String buildFontAttr(String fonts, ResultSet resultSet)
			throws SQLException {
		return "[FontName='" + fonts + "'  FontFamily='"
				+ resultSet.getString(fonts + "Name") + "'  Size='"
				+ resultSet.getInt(fonts + "Size") + "'  Italic='"
				+ resultSet.getBoolean(fonts + "Italic") + "'  Bold='"
				+ resultSet.getBoolean(fonts + "Bold") + "' /]";
	}

	
	
	/* */
	public DataSet getNotes(DataSet userGroup, DataAccess dataAccess) {
		String groupID = userGroup.getStringField("GroupID");
		String userID = userGroup.getStringField("UserID");
		String qry;
		try {
			if (userID == null || userID.equals(""))
				qry = "Select Notes From jrUserGroups Where GroupID = '" + groupID + "'";
			else
				qry = "Select Notes From jrUsers  Where GroupID = '"+ groupID + "'  And UserID = '" + userID + "'";

			if (!dataAccess.executeQuery(qry, new String[] {"Notes"}, userGroup))
				   userGroup.addMessage("Notes Not Found", "10", null, null);
			} catch (Exception er) {}
		return userGroup;
	}

	
	/* */
	public DataSet confirmPassword(DataSet login, DataAccess dataAccess) {
		try {
			String qry = "Select Password From jrUsers Where UserID = '" + 
			                                    login.getStringField("[Login-UserName/]") +"' ";
			
			if(dataAccess.executeQuery(qry, new String[] {"Password"} , login)
					&&  login.getStringField("Password").equals(login.getStringField("[Login-Password/]" ))) {
				login.putStringField("[UserManagerStatus/]", "Grant_Access");
				login.setUser(login.getStringField("[Login-UserName/]"));
				login.putStringField("[cKey/]", dataAccess.getCKey());
			} else {
				login.putStringField("[UserManagerStatus/]", "Prompt_Access");
				login.setUser("");
				login.putStringField("[cKey/]","");
			}
			
		} catch (Exception er) {}	
		login.remove("[Login-UserName/]");
		login.remove("[Login-Password/]");
		return login;
	}
	
	
	
	/* */
	public DataSet setRequesterAccess(DataSet dataSet, DataAccess dataAccess) {
		setRequesterGroupID(dataSet.getUser(), dataAccess);
		dataSet.setUser(reqUserID, reqDesc, reqGroup);
		return dataSet;
	}	
	
	
	
	/* */
	public DataSet setRequesterGroupAccess(DataSet dataSet, DataAccess dataAccess) {
		dataSet = setRequesterAccess(dataSet, dataAccess);
		try {
			String qry = 
					"Select GroupID, MenuXML, DateFormat, BUnit, Administrator, DeskTopTheme, TipAccess, "
					+ "CopyAccess, PrintAccess, AccessMethod  From jrUserGroups Where GroupID = '"
					+ reqGroup + "'";
			
			if (dataAccess.executeQuery(qry, 
					new String [] { "MenuXML", "DateFormat", "BUnit", "Administrator", "DeskTopTheme", 
									"TipAccess","CopyAccess", "PrintAccess", "AccessMethod"  }, dataSet)) {
				dataSet.setUserGroup(dataSet.getStringField("GroupID"));
				dataSet.putStringField("[BUnit/]", dataSet.getStringField("BUnit"));
				dataSet.putStringField("[MENU_SCRIPT/]", dataSet.getStringField("MenuXML"));
				dataSet.putStringField("[ConnectionType/]", dataSet.getStringField("AccessMethod"));
				dataSet.putStringField("[DateFormat/]", dataSet.getStringField("DateFormat"));
				dataSet.putBooleanField("[Administrator/]", dataSet.getBooleanField("Administrator"));
				dataSet.putBooleanField("[DeskTopTheme/]", dataSet.getBooleanField("DeskTopTheme"));
				dataSet.putBooleanField("[TipAccess/]", dataSet.getBooleanField("TipAccess"));
				dataSet.putBooleanField("[CopyAccess/]", dataSet.getBooleanField("CopyAccess"));
				dataSet.putBooleanField("[PrintAccess/]", dataSet.getBooleanField("PrintAccess"));
				dataSet.putStringField("[UserManagerStatus/]", "Grant_Access");
			} else {
				dataSet.putStringField("[UserManagerStatus/]", "No_Access");		
			}
		
		} catch (Exception er) {}
	 return dataSet;
	}

	
	/* */
	private void setRequesterGroupID(String userID, DataAccess dataAccess) {			
		reqUserID = "";
		reqGroup  = "";
		reqDesc   = "";
		DataSet ds = new DataSet();
		String qry ="Select GroupID, UserDescription From jrUsers Where userID = '" + userID + "' ";
		try {
			if (dataAccess.executeQuery(qry, 
					   new String [] {"GroupID", "UserDescription"}, ds)) {
				reqGroup = ds.getStringField("GroupID");
				reqDesc = ds.getStringField("UserDescription");
				reqUserID = userID;
			}
		} catch (Exception er) {}
	}

	
	
	/* */
	public DataSet getUser(DataSet user, DataAccess dataAccess) {
		try {
			if(!dataAccess.executeQuery(
						"Select * From jrUsers  Where UserID = '"+ user.getStringField("UserID") + "'",
						"jrUsers", user)) user.addMessage("User Not Found", "30", "UserID", null);
		
			user.putBooleanField("UpdPW",false);
			user.remove("Password");	
		
		} catch (DBSchemaException e) {}
		return user;
	}

	
	
	/* */	
	public DataSet getUserGroup(DataSet userGroup, DataAccess dataAccess) {
			try {
				if (dataAccess.executeQuery("Select * From jrUserGroups  Where GroupID = '"+ userGroup.getStringField("GroupID") + "'",
														"jrUserGroups", userGroup)) {
					
					reqGroup = userGroup.getStringField("GroupID");
					String script = dataAccess.getScriptPath() + userGroup.getStringField("MenuXML") + ".xml";
					File file = new File(script);
					FileReader reader = new FileReader(file);
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					ScriptHandler handler = new ScriptHandler(dataAccess, this, "[MENU_SCRIPT/]", true);
					sp.parse(new InputSource(reader), handler);
					DataSet cfgTable = handler.getMenuTable();
					userGroup.put(WindowItem.SCREEN_OBJECTS, cfgTable.get(WindowItem.SCREEN_OBJECTS));
					userGroup.put(WindowItem.TOTAL_OBJECTS, cfgTable.get(WindowItem.TOTAL_OBJECTS));
				} else {
					userGroup.addMessage("Group Not Found", "30", "GroupID", null);
				}
			} catch (Exception er) {}
		
		return userGroup;
	}

	
	
	
	/* */
	public DataSet getUserGroupList(DataSet groupSet, DataAccess dataAccess) {
		String desc = groupSet.getStringField("@Description");
		String menu = groupSet.getStringField("@MenuXML");

		String sql = "Select GroupID, GroupDescription, MenuXML, ActiveGroup, "
		  		     + "Administrator, DeskTopTheme, TipAccess from jrUserGroups ";
		sql = DataAccess.addToSearchWhereClause(sql, "GroupDescription", "CHR", desc );
		sql = DataAccess.addToSearchWhereClause(sql, "MenuXML", "CHR", menu);
		
		try {
			groupSet.putTableVector("UserGroup",  dataAccess.executeVectorQuery(sql, 
					  	new String[] {"GroupID", "GroupDescription","MenuXML", "ActiveGroup", 
										"Administrator","DeskTopTheme", "TipAccess" } ));

		} catch (Exception er) {}
		return groupSet;
	}

	
	
	
	/* */
	public DataSet getUserList(DataSet user, DataAccess dataAccess) {
		String desc = user.getStringField("@Description");
		String name = user.getStringField("@Name");
		String group = user.getStringField("@Group");
		String sql = "Select UserID, UserDescription, Name, GroupID, "
				+ "ActiveUser  from jrUsers ";
		sql = DataAccess.addToSearchWhereClause(sql, "UserDescription", "CHR", desc );
		sql = DataAccess.addToSearchWhereClause(sql, "Name", "CHR", name );
		sql = DataAccess.addToSearchWhereClause(sql, "GroupID", "CHR", group );

		try {
			user.putTableVector("User", dataAccess.executeVectorQuery(sql, 
					new String[] { "UserID", "UserDescription","Name", "GroupID", "ActiveUser" } ));
				 
			} catch (Exception er) {}
		return user;
	}

	
	/* */	
	public DataSet getUserTheme(DataSet users, DataAccess dataAccess) {
		String userID = users.getUser();
		ResultSet resultSet;
		String sql;
		Statement qry = dataAccess.execConnectReadOnly();
			try {
				sql = "Select  BackGroundColorRed, BackGroundColorBlue, BackGroundColorGreen, "
						+ " TitleBarColorRed, TitleBarColorBlue, TitleBarColorGreen,"
						+ " TitleBarFontColorRed, TitleBarFontColorBlue, TitleBarFontColorGreen, "
						+ " WindowTitleColorRed, WindowTitleColorBlue, WindowTitleColorGreen, "
						+ " WindowTitleFontColorRed, WindowTitleFontColorBlue, WindowTitleFontColorGreen, "
						+ " CursorColorRed, CursorColorBlue, CursorColorGreen, "
						+ " HeaderFontName, HeaderFontSize, HeaderFontItalic, HeaderFontBold, "
						+ " RegularFontName, RegularFontSize, RegularFontItalic, RegularFontBold "
						+ "  From jrUsers  Where  UserID ='" + userID + "'";

				resultSet = qry.executeQuery(sql);
				if (resultSet.next()) {
					users.putStringField("[BackGroundColor/]", buildColorAttr("BackGroundColor", resultSet));
					users.putStringField("[TitleBarColor/]", buildColorAttr("TitleBarColor", resultSet));
					users.putStringField("[TitleBarFontColor/]",buildColorAttr("TitleBarFontColor", resultSet));
					users.putStringField("[WindowTitleColor/]", buildColorAttr("WindowTitleColor", resultSet));
					users.putStringField("[WindowTitleFontColor/]",buildColorAttr("WindowTitleFontColor", resultSet));
					users.putStringField("[CursorColor/]", buildColorAttr("CursorColor", resultSet));
					users.putStringField("[HeaderFont/]", buildFontAttr("HeaderFont", resultSet));
					users.putStringField("[RegularFont/]", buildFontAttr("RegularFont", resultSet));
				}
				resultSet.close();
			} catch (Exception er) {
				dataAccess.logMessage(" *UserSecurityManager.getUserTheme*  --  " + er );
				users.addMessage("SVR0001");
			} finally {
				dataAccess.execClose(qry);
			}
		return users;
	}


	
	/* */	
	public int grantItemAccess(String item, DataAccess dataAccess ) {
		int accesslevel = READ_WRITE;
		if (item == null || item.equals(""))
			return accesslevel;
		ResultSet resultSet;
		Statement qry = dataAccess.execConnectReadOnly();
			try {
				resultSet = qry.executeQuery(
						"Select ItemAccessLevel From jrGroupAccess Where GroupID  = '"
						+ reqGroup + "' And MenuItem  = '" + item + "'");
				if (resultSet.next())
					accesslevel = resultSet.getInt("ItemAccessLevel");
				resultSet.close();
			} catch (Exception er) {
				dataAccess.logMessage(" *UserSecurityManager.grantItemAccess*  --  " + er );
			} finally {
				dataAccess.execClose(qry);
			}
		return accesslevel;
	}

	
	/* */		
	public int grantScriptAccess(String item, DataAccess dataAccess) {
		return grantItemAccess(item, dataAccess);
	}

	
	
	/* */		
	private boolean isNewGroupAccess(String groupID, String menuItem, DataAccess dataAccess) {
		try {	
			return !dataAccess.executeQuery(
				"Select GroupID From jrGroupAccess Where GroupID = '"+ groupID + 
					"' AND MenuItem = '" + menuItem + "'" , "", null );
		} catch (DBSchemaException er) {
			return true;
		}
	}


	
	/* */		
	private boolean isNewUser(String userID, DataAccess dataAccess) {
		try {
			return !dataAccess.executeQuery(
							"Select UserID From jrUsers Where UserID = '"
							+ userID + "'", "", null);
		} catch (DBSchemaException e) {
			return true;
		}
	}

	
	/* */
	public DataSet changeMyPassword(DataSet pw, DataAccess dataAccess) {
	
		String old = pw.getStringField("[old/]");
		String new1 = pw.getStringField("[new-1/]");
		String new2 = pw.getStringField("[new-2/]");
 
		if(!new1.equals(new2) ) 
			return pw.putStringField("[response/]", "New password not the same");
		
		String qry = "Select Password From jrUsers Where UserID = '" +  pw.getUser() +"' ";

		try {
			if(! dataAccess.executeQuery(qry, new String[] {"Password"} , pw) || 
					                         ! pw.getStringField("Password").equals(old)) 
				return pw.putStringField("[response/]", "Current password is not correct");
		
		pw.putStringField("Password", new1);
		dataAccess.executeUpdateQuery("jrUsers", new String[] {"Password"}, pw, "Where UserID = '" +  pw.getUser() +"' ");
		
		
		} catch (DBSchemaException e) {		
			return pw.putStringField("[response/]", "Password cannot be changed");
		}		
		
		pw.remove("[old/]");
		pw.remove("[new-1/]");
		pw.remove("[new-2/]");
		pw.remove("Password");
		return pw.putStringField("[response/]", "Password Updated");
	}
	
	
	
	/* */	
	public boolean isNewUserGroup(DataSet userGroup, DataAccess dataAccess) {
		try {
			return !dataAccess.executeQuery(
						"Select GroupID From jrUserGroups  Where GroupID = '"
						+ userGroup.getStringField("GroupID") + "'" , "", null);
		} catch (Exception er) {
			return true; 
		}
	}


	
	/* */	
	public DataSet nxtUser(DataSet user, DataAccess dataAccess) {
		try {
			if(!dataAccess.executeQuery(
				"Select * From jrUsers  Where UserID > '"+ user.getStringField("UserID") + 
				"' Order By UserID",
				"jrUsers", user)) user.addMessage("LIT0002");
			 
			user.putBooleanField("UpdPW",false);
			user.remove("Password");	
		
		} catch (DBSchemaException e) {}
		return user;
	}

	
	
	/* */	
	public DataSet nxtUserGroup(DataSet userGroup, DataAccess dataAccess) { 
		try {
			if(dataAccess.executeQuery("Select GroupID From jrUserGroups  Where GroupID > '"
				+ userGroup.getStringField("GroupID") + "' Order By GroupID",
				new String[] {"GroupID"}, userGroup)) 
					getUserGroup(userGroup, dataAccess);
			else 
					userGroup.addMessage("LIT0002");
						 
			} catch (Exception er) {
		}
		return userGroup;
	}

	
	
	/* */		
	public DataSet prvUser(DataSet user, DataAccess dataAccess) {
		try {
			if(!dataAccess.executeQuery(
				"Select * From jrUsers  Where UserID < '"+ user.getStringField("UserID") + "' Order By UserID Desc",
				"jrUsers", user )) user.addMessage("LIT0003");
			 
			user.putBooleanField("UpdPW",false);
			user.remove("Password");	
		
		} catch (DBSchemaException e) {}
		return user;
	}

	
	
	/* */		
	public DataSet prvUserGroup(DataSet userGroup, DataAccess dataAccess) {
		try {
			if(dataAccess.executeQuery("Select GroupID From jrUserGroups  Where GroupID < '"
				+ userGroup.getStringField("GroupID") + "' Order By GroupID Desc",
				new String[] {"GroupID"}, userGroup)) 
					getUserGroup(userGroup, dataAccess);
			else 
					userGroup.addMessage("LIT0003");
						 
			} catch (Exception er) {
		}
		return userGroup;
	}

	
	/* */	
	private DataSet updGroupAccessDetails(DataSet groupAccess, DataAccess dataAccess) {
		String groupID = groupAccess.getStringField("GroupID");
		String[] rowObject = null;
		String updateFields = "";
		String updateValues = "";
		String insertExpressions = "";
		PreparedStatement sqlIStatement = null;
		PreparedStatement sqlUStatement = null;
		try {
			updateFields = DataAccess.addToArgumentList(updateFields, "GroupID");
			updateValues = DataAccess.addToArgumentList(updateValues, "?");
			updateFields = DataAccess.addToArgumentList(updateFields,"GroupItemDescription");
			updateValues = DataAccess.addToArgumentList(updateValues, "?");
			updateFields = DataAccess.addToArgumentList(updateFields, "MenuItem");
			updateValues = DataAccess.addToArgumentList(updateValues, "?");
			updateFields = DataAccess.addToArgumentList(updateFields,"ItemAccessLevel");
			updateValues = DataAccess.addToArgumentList(updateValues, "?");
			sqlIStatement = dataAccess.execPreparedConnect("INSERT INTO jrGroupAccess ("
							+ updateFields + " ) VALUES (" + updateValues + ")");
			
			insertExpressions = DataAccess.addToArgumentList(insertExpressions, "ItemAccessLevel = ? ");
			sqlUStatement = dataAccess.execPreparedConnect("UPDATE jrGroupAccess SET "
							+ insertExpressions
							+ " WHERE GroupID = ? And  MenuItem = ?");

			Enumeration groupAccessLevels = groupAccess.getTableVector("[MenuDataTop/]").elements();

			while (groupAccessLevels.hasMoreElements()) {
				rowObject = (String[]) groupAccessLevels.nextElement();

				if (isNewGroupAccess(groupID, rowObject[1], dataAccess)) {
					sqlIStatement.setString(1, groupID);
					sqlIStatement.setString(2, rowObject[0]);
					sqlIStatement.setString(3, rowObject[1]);
					sqlIStatement.setInt(4, DataSet.checkInteger(rowObject[3]));
					sqlIStatement.executeUpdate();
				} else {
					sqlUStatement.setInt(1, DataSet.checkInteger(rowObject[3]));
					sqlUStatement.setString(2, groupID);
					sqlUStatement.setString(3, rowObject[1]);
					sqlUStatement.executeUpdate();
				}
			}

		} catch (SQLException ex) {
			dataAccess.logMessage(" *UserSecurityManager.updGroupAccessDetails*  --  " + ex );
			groupAccess.addMessage("SVR0001");
		} finally {
			dataAccess.execClose(sqlIStatement);
			dataAccess.execClose(sqlUStatement);
		}
		return groupAccess;
	}

	
	/* */	
	public DataSet updNotes(DataSet userGroup, DataAccess dataAccess) {
		String groupID = userGroup.getStringField("GroupID");
		String userID = userGroup.getStringField("UserID");
		PreparedStatement qry = null;
			try {
				if (userID == null || userID.equals(""))
					qry = dataAccess.execPreparedConnect(
							"UPDATE jrUserGroups SET Notes = ?  Where GroupID = ?");
				else
					qry = dataAccess.execPreparedConnect(
							"UPDATE jrUsers SET Notes =  ? Where GroupID = ? And UserID = ?");

				qry.setString(1, userGroup.getStringField("Notes"));
				qry.setString(2, groupID);
				if (userID != null && !userID.equals(""))
					qry.setString(3, userID);
				qry.executeUpdate();
				userGroup.addMessage("Note Updated", "10", null, null);

			} catch (Exception er) {
				dataAccess.logMessage(" *UserSecurityManager.updNotes*  --  " + er );
				userGroup.addMessage("SVR0001");
			} finally {
				dataAccess.execClose(qry);
			}
		return userGroup;
	}

	
	
	/* */	
	public DataSet updUser(DataSet user, DataAccess dataAccess) {
		if(isValidUser(user,dataAccess))
			if(isNewUser(user.getStringField("UserID"), dataAccess))
				insertIntoUser(user,dataAccess);
			else		
				updateIntoUser(user,dataAccess);

		return user;
	}	
	
	
	/* */		
	private boolean isValidUser (DataSet user, DataAccess dataAccess) {
		if(isNewUserGroup(user,dataAccess)) {
		   user.addMessage("Invalid Group ID","30","GroupID",null);
			return false;
		}	
		
		
		return true;
	}
	
	
	/* */	
	private boolean isValidGroup (DataSet group, DataAccess dataAccess) {
		boolean r = true;
		if(group.getStringField("AccessMethod") == null) {
			group.addMessage("Invalid Server Access","30","AccessMethod",null);
			r =  false;
		}	
				
		if(group.getStringField("DateFormat").equals("")) {
			group.addMessage("Invalid Date Format-- (ie: 'dd-MMM-yyyy' or 'MM/dd/yyyy') ","30","DateFormat",null);
			r =  false;
		}	

		
		try {
			String script = dataAccess.getScriptPath() + group.getStringField("MenuXML") + ".xml";
			File file = new File(script);	
			FileReader reader = new FileReader(file);
			SAXParserFactory spf = SAXParserFactory.newInstance();	
			SAXParser sp = spf.newSAXParser();
			ScriptHandler handler = new ScriptHandler(dataAccess, null, "[MENU_SCRIPT/]", true);
			sp.parse(new InputSource(reader), handler);
			if(!handler.isValidXMLType("[MENU_SCRIPT/]")) {
				group.addMessage("Invalid Menu XML File","30","MenuXML",null);
				r =  false;
			}	
		} catch (Exception e) {
			group.addMessage("Invalid Menu XML File","30","MenuXML",null);
			r =  false;
		}
		return r;
	}
	
	
	
	/* */		
	private DataSet updateIntoUser(DataSet user, DataAccess dataAccess) {		
		String userID = user.getStringField("UserID");
		try {
			if(!user.getBooleanField("UpdPW") || user.getStringField("Password").equals(""))
					user.remove("Password");
			
			user.remove("UpdPW");
			dataAccess.executeUpdateQuery("jrUsers", user, "Where UserID = '" + userID + "'");
			
				user.addMessage("User Updated", "10", null, null);
			} catch (Exception er) {
				user.addMessage("SVR0001");
			}
		return user;
	}


	
	/* */		
	private DataSet insertIntoUser(DataSet user, DataAccess dataAccess) {
			user =  initNewUserTheme(user);
			try {
				dataAccess.executeInsertQuery("jrUsers", 
						dataAccess.getTableColumns("jrUsers-Extended"),  user);
				user.addMessage("User Updated", "10", null, null);
			} catch (Exception er) {
				user.addMessage("SVR0001");
			}
			return user;
		}

	
	/* */		
	private DataSet initNewUserTheme(DataSet user) {
		user.putIntegerField("BackGroundColorRed", 255);
		user.putIntegerField("BackGroundColorBlue", 255);
		user.putIntegerField("BackGroundColorGreen", 255);
		user.putIntegerField("TitleBarColorRed", 100);
		user.putIntegerField("TitleBarColorBlue", 255);
		user.putIntegerField("TitleBarColorGreen", 100);
		user.putIntegerField("TitleBarFontColorRed", 250);
		user.putIntegerField("TitleBarFontColorBlue", 250);
		user.putIntegerField("TitleBarFontColorGreen", 250);
		user.putIntegerField("WindowTitleColorRed", 235);
		user.putIntegerField("WindowTitleColorBlue", 235);
		user.putIntegerField("WindowTitleColorGreen", 235);
		user.putIntegerField("WindowTitleFontColorRed", 0);
		user.putIntegerField("WindowTitleFontColorBlue", 0);
		user.putIntegerField("WindowTitleFontColorGreen", 0);
		user.putIntegerField("CursorColorRed",250);
		user.putIntegerField("CursorColorBlue",220);
		user.putIntegerField("CursorColorGreen",250);
		
		user.putStringField("HeaderFontName","Arial");
		user.putIntegerField("HeaderFontSize", 11);
		user.putBooleanField("HeaderFontItalic", false);
		user.putBooleanField("HeaderFontBold", true);	
		user.putStringField("RegularFontName", "Arial");
		user.putIntegerField("RegularFontSize", 11);
		user.putBooleanField("RegularFontItalic", false);
		user.putBooleanField("RegularFontBold", false);

		return user;
	}
	
	
	/* */		
	public DataSet updUserGroup(DataSet userGroup, DataAccess dataAccess) {
	  if(isValidGroup(userGroup,dataAccess))
		if (isNewUserGroup(userGroup, dataAccess))
			insertIntoUserGroup(userGroup, dataAccess);
		else	
			updateIntoUserGroup(userGroup, dataAccess);
	return userGroup;	
	}
		
	
	
	/* */		
	private DataSet insertIntoUserGroup(DataSet userGroup, DataAccess dataAccess) {
		try {		
			dataAccess.executeInsertQuery("jrUserGroups",userGroup.getUpdateableFields(), userGroup);
			updGroupAccessDetails(userGroup, dataAccess);
			userGroup.addMessage("Group Updated", "10", null, null);
			} catch (Exception er) {
				userGroup.addMessage("*Data Base Error", "30", null, null);
			}
		return userGroup;
	}	
	
	
	/* */		
	private DataSet updateIntoUserGroup(DataSet userGroup, DataAccess dataAccess) {
		String groupID = userGroup.getStringField("GroupID");
		try  {
			dataAccess.executeUpdateQuery("jrUserGroups", userGroup.getUpdateableFields(),
						userGroup,  " Where groupID = '" + groupID + "'");
			
			updGroupAccessDetails(userGroup, dataAccess); 
			userGroup.addMessage("User Group Updated", "10", null, null);
			} catch (Exception er) {
				userGroup.addMessage("SVR0001");
			}
		return userGroup;
	}

	public DataSet updUserTheme(DataSet users, DataAccess dataAccess) {
		String userID = users.getStringField("UserID");
		String sql;
		Statement qry = dataAccess.execConnectUpdate();
			try {
				sql = "UPDATE jrUsers Set"
						+ " BackGroundColorRed ="
						+ users.getIntegerField("BackGroundColorRed")
						+ ", BackGroundColorBlue ="
						+ users.getIntegerField("BackGroundColorBlue")
						+ ", BackGroundColorGreen ="
						+ users.getIntegerField("BackGroundColorGreen")
						+ ", TitleBarColorRed ="
						+ users.getIntegerField("TitleBarColorRed")
						+ ", TitleBarColorBlue  ="
						+ users.getIntegerField("TitleBarColorBlue")
						+ ", TitleBarColorGreen ="
						+ users.getIntegerField("TitleBarColorGreen")
						+ ", TitleBarFontColorRed ="
						+ users.getIntegerField("TitleBarFontColorRed")
						+ ", TitleBarFontColorBlue ="
						+ users.getIntegerField("TitleBarFontColorBlue")
						+ ", TitleBarFontColorGreen ="
						+ users.getIntegerField("TitleBarFontColorGreen")
						+ ", WindowTitleColorRed ="
						+ users.getIntegerField("WindowTitleColorRed")
						+ ", WindowTitleColorBlue ="
						+ users.getIntegerField("WindowTitleColorBlue")
						+ ", WindowTitleColorGreen ="
						+ users.getIntegerField("WindowTitleColorGreen")
						+ ", WindowTitleFontColorRed ="
						+ users.getIntegerField("WindowTitleFontColorRed")
						+ ", WindowTitleFontColorBlue ="
						+ users.getIntegerField("WindowTitleFontColorBlue")
						+ ", WindowTitleFontColorGreen ="
						+ users.getIntegerField("WindowTitleFontColorGreen")
						+ ", CursorColorRed ="
						+ users.getIntegerField("CursorColorRed")
						+ ", CursorColorBlue ="
						+ users.getIntegerField("CursorColorBlue")
						+ ", CursorColorGreen ="
						+ users.getIntegerField("CursorColorGreen")
						+ ", HeaderFontName ='"
						+ users.getStringField("HeaderFontName")
						+ "', HeaderFontSize ="
						+ users.getIntegerField("HeaderFontSize")
						+ ", HeaderFontItalic ="
						+ DataSet.booleanToInt(users
								.getBooleanField("HeaderFontItalic"))
						+ ", HeaderFontBold ="
						+ DataSet.booleanToInt(users
								.getBooleanField("HeaderFontBold"))
						+ ", RegularFontName ='"
						+ users.getStringField("RegularFontName")
						+ "', RegularFontSize ="
						+ users.getIntegerField("RegularFontSize")
						+ ", RegularFontItalic ="
						+ DataSet.booleanToInt(users
								.getBooleanField("RegularFontItalic"))
						+ ", RegularFontBold ="
						+ DataSet.booleanToInt(users
								.getBooleanField("RegularFontBold"))
						+ " Where  UserID ='" + userID + "'";

				qry.executeUpdate(sql);
				qry.close();
				users.addMessage("User Theme Updated", "10", null, null);
			} catch (Exception er) {
				dataAccess.logMessage(" *UserSecurityManager.updUserTheme*  --  " + er );
				users.addMessage("SVR0001");
			} finally {
				dataAccess.execClose(qry);
			}
		return users;
	}	
	
 
 
}

