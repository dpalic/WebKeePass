package installers;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseInstaller {

	Connection con;
	Statement stm;
	PreparedStatement ps;
	ResultSet rs;
	StringBuffer msg;
	StringBuffer buffer;
	String qry = 
		"INSERT INTO `jrUsers` (`UserID`,`GroupID`,`UserDescription`,`Name`,`Address1`,`Address2`,`Address3`,`Address4`,`Phone1`," +
		"`Phone2`,`Fax`,`Email`,`WebSite`,`Notes`,`CreateDate`,`ActiveDate`,`InActiveDate`,`LastChangeDate`,`ActiveUser`," +
		"`BackGroundColorRed`,`BackGroundColorBlue`,`BackGroundColorGreen`,`TitleBarColorRed`,`TitleBarColorBlue`,`TitleBarColorGreen`," +
		"`TitleBarFontColorRed`,`TitleBarFontColorBlue`,`TitleBarFontColorGreen`,`WindowTitleColorRed`,`WindowTitleColorBlue`," +
		"`WindowTitleColorGreen`,`WindowTitleFontColorRed`,`WindowTitleFontColorBlue`,`WindowTitleFontColorGreen`,`CursorColorRed`," +
		"`CursorColorBlue`,`CursorColorGreen`,`HeaderFontName`,`HeaderFontSize`,`HeaderFontItalic`,`HeaderFontBold`,`RegularFontName`," +
		"`RegularFontSize`,`RegularFontItalic`,`RegularFontBold`,`CopyAccess`,`PrintAccess`,`Password`) VALUES  " +
        " ( ?, ?, ?, ?, '','','','','',NULL,'','','',NULL,NULL,NULL,'','20060706',1,255,255,255,100,255,100,250,250,250,0,0,0,0,0,0,250,220,250," +
        "'Arial',11,0,1,'Arial',11,0,0,0,0,?) ";
 
	
	public boolean installDB(String database, String userDB, String pswdDB, String sqlFile, 
									String userA, String pswdA, String userS, String pswdS) {
		try {
			msg = new StringBuffer();
			
			if(!readFile(sqlFile)) 
				return false;
			

 			String url = "jdbc:mysql://localhost/";  
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, userDB, pswdDB);
			stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			try {
				stm.execute("Drop Database " + database);
			} catch (Exception e) { }
			
			stm.execute("Create Database " + database);
			stm.execute("Use " + database);
			
			while(nextSQL()) stm.executeUpdate(getSQL());		
		
			ps = con.prepareStatement(qry);
			ps.setString(1, userA);
			ps.setString(2, "Admins");
			ps.setString(3, "Administrator / Root");
			ps.setString(4, "Administrator / Root");
			ps.setString(5, pswdA);
			ps.executeUpdate();
			
			ps.setString(1, userS);
			ps.setString(2, "Users");
			ps.setString(3, "User 1");
			ps.setString(4, "User 1");
			ps.setString(5, pswdS);
			ps.executeUpdate();
			
			stm.close();
			ps.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("  * Database error:" + e.getMessage() + " \n");
			return false;
		}
		return true;
	}

	public String getMessages() {
		return msg.toString();
	}

	


	
	private boolean nextSQL(){
		if(buffer.indexOf(";") != -1)
			return true;
		else		
			return false;
	}
	
	
	private  String  getSQL() {		
		int e = buffer.indexOf(";");
		String sql = buffer.substring(0, e+1);
		buffer.delete(0, e+1);
 		return sql;
	}
	
		
	private boolean readFile(String fileNmae) {
		buffer = new StringBuffer();
		msg  = new StringBuffer();
	    try {
	    	Reader file = new FileReader(fileNmae);	
	    	char[] b = new char[128];
	    	int n;
	    	while ((n = file.read(b)) > 0)
	    		buffer.append(b,0,n);
	    	file.close();
	    	
		} catch (Exception er) {
			er.printStackTrace();
			msg.append("Error Reading File - " + er.getMessage());
			return false;
		}
		return true;
	}
	
	


}
