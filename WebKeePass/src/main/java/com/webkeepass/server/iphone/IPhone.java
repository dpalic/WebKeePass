package com.webkeepass.server.iphone;


import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import webBoltOns.client.components.CSHAPasswordField;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;
import webBoltOns.server.UserSecurityManager;


public class IPhone extends GenericServlet {

	private static final long serialVersionUID = 6451145079860741535L;

	private String imageFilePath, scriptFilePath;

	private PrintWriter out;

	private UserSecurityManager userSecurityManager  = null;
	
	private DataAccess dataAccess = null;
	
	private String page = null;

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
			log("Server Starting");
			log("Database pool opening");
			log("Conf-Home : " + configHome);
			log("Conf-File : " + configFile);
			log("Script-Path : " + scriptFilePath);
			log("Image-Path : " + imageFilePath);
			log("Log-Path : " + dataAccess.getServerLogsPath());
			log("Menu-Path " + dataAccess.getMenuPath());
			log("Connection :" + dataAccess.getConnectionType());
			dataAccess.logMessage(".....Opening SecurityManager "); 
			userSecurityManager = new UserSecurityManager();
			userSecurityManager.setLdapLogin(dataAccess.getEOpts().getBooleanField("LDAP"));
		}
	}


	public void service(ServletRequest request, ServletResponse response)  {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			DataSet	id = new DataSet();
			id.setUser(request.getParameter("id"));			
			id.setScmbl(dataAccess.encrypt(id.getUser()));
			id.setUserIP(request.getRemoteAddr());
			id.setRmtHost(request.getRemoteHost());
			id.putStringField("[Login-UserName/]", id.getUser());
			id.putStringField("[Login-Password/]", CSHAPasswordField.sha1(request.getParameter("pw")));
			id =  userSecurityManager.confirmPassword(id ,dataAccess);
			if(id.getUser().equals(""))	
				throw new SecurityException();
			buildIApp(id.getScmbl());
	
		} catch (Exception e) {
			log(e.getLocalizedMessage());
			buildLoginIn();	
		}
	}


	public void buildLoginIn() {	
		page = null;
		out.println(
		  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"    \n"
		  + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">    \n"
		  + "<html xmlns=\"http://www.w3.org/1999/xhtml\">    \n"
		  + "<head>  \n"
		  + "<title>Web KeePass</title>  \n"
		  + "<style type=\"text/css\">   \n"
		  + "body {   \n"
		  + "  margin: 0;  \n"
		  + "  border-bottom: 1px solid #E0E0E0;  \n"
		  + "  padding: 0;  \n"
		  + "  background: url(iPhoneArrow.png) no-repeat right center;  \n"
		  + "  font-size: 20px;  \n"
		  + "  font-weight: bold;  \n"
		  + "}  \n"
		  + "h1 {   \n"
		  + " box-sizing: border-box;  \n"
		  + " margin: 0;  \n"
		  + " padding: 10px;  \n"
		  + " line-height: 20px;  \n"
		  + " font-size: 20px;  \n"
		  + " font-weight: bold;  \n"
		  + " text-align: center;  \n"
		  + " text-shadow: rgba(0, 0, 0, 0.6) 0px -1px 0;  \n"
		  + " text-overflow: ellipsis;  \n"
		  + " color: #FFFFFF;  \n"
		  + " background: url(iPhoneToolbar.png) #6d84a2 repeat-x;  \n"
		  + " border-bottom: 1px solid #2d3642;  \n"
		  + "}  \n"
		  + ".login {   \n"
		  + " position: absolute;   \n"
		  + " top: 8px;  \n"
		  + " right: 6px;  \n"
		  + " -webkit-border-image: url(iPhoneButton.png) 0 5 0 5;  \n"
		  + " -webkit-border-radius: 0;  \n"
		  + " border-width: 0 0px 0 0px;  \n"
		  + " padding: 0;  \n"
		  + " height: 28px;  \n"
		  + " line-height: 28px;  \n"
		  + " font-size: 12px;  \n"
		  + " font-weight: bold;  \n"
		  + " color: #FFFFFF;  \n"
		  + " text-shadow: rgba(0, 0, 0, 0.6) 0px -1px 0;  \n"
		  + " text-decoration: none;  \n"
		  + " background: none;  \n"
		  + "}  \n"
		  + "</style>   \n"
		  + "<meta name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">   \n"
		  + "</head>   \n"
		  + "<body>   \n"
		  + "<h1 id=\"pageTitle\"> iWeb Keepass </h1>  \n"
		  + "<ul id=\"passwords\" title=\"iWeb KeePass\" selected=\"true\">   \n"
		  + "<form method=\"post\" action=\"iphone\">   \n"
		  +	"<input type=\"Submit\" value=\"Log-in\" class=\"login\"> \n  "
		  + "  <table style=\"text-align: left; width: 1%;\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">  <tbody> \n "
		  + "  <tr><td><text>User: </text></td><td> <input name=\"id\" value=\"\" size=\"20\" type=\"text\"></td></tr>   \n "
		  + "  <tr><td><text>Password: </text> </td><td> <input name=\"pw\" value=\"\" size=\"20\" type=\"password\"> </form> </td></tr>  \n "
		  + " </tbody></table>  \n  </form></ul>   \n" 
		  + " </body>   \n "
		  + " </html>   \n");	
	}

	
	public void buildIApp(String pg) throws Exception {
		page = pg;
		
		out.println(
				  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"  \n"
				+ "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">   \n"
				+ " <html xmlns=\"http://www.w3.org/1999/xhtml\">  \n"
				+ " <head> \n   <title>Web KeePass</title>  \n"
				+ "  <meta name=\"viewport\" content=\"width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\"/>  \n"
				+ "  <style type=\"text/css\" media=\"screen\">@import \"../iphone/iphonenav.css\";</style>   \n"
				+ "  <script type=\"application/x-javascript\" src=\"../iphone/iphonenav.js\"></script>   \n"
				+ "  </head> \n   <body> \n    <h1 id=\"pageTitle\">  </h1>  \n"
				+ "   <input type=\"button\" class=\"logout\" value=\"Log-out\" onclick=\"window.location.href='iphone'\"> \n  "				
				+ "   <a id=\"homeButton\" class=\"link\" href=\"#passwords\"> My Passwords </a>   \n"
				+ "   <ul id=\"passwords\" title=\" iWeb Keepass \" selected=\"true\">");

		ResultSet resultSet;
		Statement qry = dataAccess.execConnectReadOnly();
		Vector <ListItem>  tags = new Vector<ListItem>();
		try { 
			resultSet = qry.executeQuery("Select KeeperID, KeeperDesc From wkpGroups where PrntID = 0 And j1 ='" + page + "' ");
			while (resultSet.next()) {
				ListItem p = new ListItem(
						resultSet.getInt("KeeperID"), 
						resultSet.getString("KeeperDesc"));

				out.println("     <li> <a href=\"#Ctgry--"+ p.getID() +"\"> " + p.getCategory() + "</a> </li>   ");
				tags.addElement(p);
			}

			resultSet.close();

			resultSet = qry.executeQuery("Select * from wkpPasswordCrypt  where KeeperID = 0 And j1 = '" + page + "' ");

			while (resultSet.next()) {
				ListItem p = new ListItem(
						resultSet.getString("PasswordID"),
						resultSet.getString("a0"),
						resultSet.getString("o6"), 
						resultSet.getString("b9"), 
						resultSet.getString("c8"),
						resultSet.getString("e6"), 
						resultSet.getString("f5"),
						resultSet.getString("g4"),
						resultSet.getString("n7"),
						resultSet.getString("i2"),
						resultSet.getString("k0"),
						resultSet.getString("m8")); 

				out.println("     <li> <a href=\"#Pswrd--"+ p.getID() +"\"> " + p.getDesc() + "</a> </li> ");
				tags.addElement(p);
			}

			out.println("    </ul>  \n   ");

			resultSet.close();
			
			if(!tags.isEmpty()) expandLevel(tags.elements());

			out.println("   </body>  \n </html>");

		} catch (Exception e) {
			throw e; 
		} finally {
			dataAccess.execClose(qry);
		}

		return;
	}



	private void expandLevel(Enumeration<ListItem> tags) throws Exception {
		while (tags.hasMoreElements()) {
			ListItem p = tags.nextElement(); 
			if(p.isPassword())
				expandPwd(p);
			else
				expandCat(p);
		}	
	}



	private void expandPwd(ListItem p) {
		out.println(
				  "    <div id=\"Pswrd--" + p.getID() + "\" class=\"panel\" title=\"Details\">  \n"
				+ "     <h2>"+p.getDesc()+"</h2>  \n"
			    + "     <table style=\"text-align: left; width: 1%;\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">  <tbody> \n "
				+ "       <tr><td><h3> User ID:</h3></td><td><h3>" + p.getUser() + "</h3></td></tr>  \n"
				+ "       <tr><td><h3> Password:</h3></td><td><h3>" + p.getPwrd() + "</h3></td></tr>  \n"
				+ "       <tr><td><h3> Email:</h3></td><td><h3>" + p.getEmail() + "</h3></td></tr> \n"
				+ "       <tr><td><h3> Web Site:</h3></td><td><h3>" + p.getWebSite() + "</h3></td></tr>  \n"
				+ "       <tr><td><h3> Exp Date</h3></td><td><h3>" + p.getExpireDate() + "</h3></td></tr>  \n"
				+ "     </tbody></table>  \n" 
				+ "    </div>");
	}


	public void expandCat(ListItem p) throws Exception {
		ResultSet resultSet;
		Statement qry = dataAccess.execConnectReadOnly();
		int keeper = DataSet.checkInteger(p.getID());
		if (keeper == 0) return;

		out.println("    <ul id=\"Ctgry--" + p.getID() + "\" title=\"" + p.getCategory() +"\" >   ");

		Vector <ListItem>  tags = new Vector<ListItem>();
		try {
			String sql = "Select KeeperID, KeeperDesc From wkpGroups where PrntID = " + keeper + " And j1 ='" + page + "' ";
			resultSet = qry.executeQuery(sql);
			while (resultSet.next()) {
				ListItem c = new ListItem(
						resultSet.getInt("KeeperID"), 
						resultSet.getString("KeeperDesc"));

				out.println("     <li> <a href=\"#Ctgry--"+ c.getID() +"\"> " + c.getCategory() + "</a> </li>   ");
				tags.addElement(c);
			}

			resultSet.close();

			sql = "Select * from wkpPasswordCrypt  where KeeperID = "
				+ keeper + " And j1 = '" + page + "' ";
			
			resultSet = qry.executeQuery(sql);
			while (resultSet.next()) {
				ListItem c = new ListItem(
						resultSet.getString("PasswordID"),
						resultSet.getString("a0"),
						resultSet.getString("o6"), 
						resultSet.getString("b9"), 
						resultSet.getString("c8"),
						resultSet.getString("e6"),
						resultSet.getString("f5"),
						resultSet.getString("g4"),
						resultSet.getString("n7"),
						resultSet.getString("i2"),
						resultSet.getString("k0"),
						resultSet.getString("m8")); 

				out.println("     <li> <a href=\"#Pswrd--"+ c.getID() +"\"> " + c.getDesc() + "</a> </li> ");
				tags.addElement(c);
			}

			out.println("     </ul>  ");
			resultSet.close();
			
			if(!tags.isEmpty()) expandLevel(tags.elements());

		} catch (Exception e) {
			throw e;
		} finally {
			dataAccess.execClose(qry);
		}
		return;

	}




	public 	class ListItem {

		public final static int PASSWRD = 1;
		public final static int CATGRY= 2;
		int type;
		private String id = null;
		private String dsc = null;;
		private String dsc2 = null;
		private String username = null;
		private String password = null;
		private String lastUpdate = null;
		private String expiresOn = null;
		private String expires = null;
		private String webSite = null;
		private String altWebSite = null;
		private String supportSite = null;
		private String email = null;

		
		ListItem (int i, String d) {
			id = Integer.toString(i);
			dsc = d;
			type = CATGRY;
		}


		ListItem (String PasswordID, String a0, String o6, String b9, 
				String c8, String e6, String f7, String g4, String n7, 
				String i2, String k0, String m8 ) {

			id = PasswordID;
			dsc = a0;
			dsc2 = o6;
			username = b9;
			password = c8;
			lastUpdate = e6;
			expiresOn = f7;
			expires= g4;
			webSite = n7;
			altWebSite = i2;
			supportSite = k0;
			email = m8;
			type = PASSWRD;
		}

		public boolean isPassword() {
			if (type == PASSWRD)
				return true;
			else
				return false;
		}

		public String getID()  {return id;}
		public String getCategory()  {return dsc;}
		public String getPlanID()  {return checkNullSting(id);}
		public String getDesc() {return checkNullSting(dsc);}
		public String getDesc2() {return checkNullSting(dsc2);}
		public String getUser() {return checkNullSting(username);}
		public String getPwrd() {return checkNullSting(password);}
		public String getLastUpdateDate() {return checkNullSting(lastUpdate);}
		public String getExpireDate() {return checkNullSting(expiresOn);}
		public String getExpire() {return checkNullSting(expires);}
		public String getWebSite() {return checkNullSting(webSite);}
		public String getAltWebSite() {return checkNullSting(altWebSite);}
		public String getSupportWebSite() {return checkNullSting(supportSite);}
		public String getEmail() {return checkNullSting(email);}

		public String checkNullSting(String s) {
			String r = dataAccess.decrypt(s);
			if(r != null ) return r;
			else return "";			
		}
	   
	}

}
