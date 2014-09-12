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
	


	public boolean installDB(String database, String user, String password, String sqlFile) {
		try {
			msg = new StringBuffer();
			
			if(!readFile(sqlFile)) 
				return false;
			
			//String url = "jdbc:mysql://localhost/" + database;
 			String url = "jdbc:mysql://localhost/";  
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			stm.execute("Create Database " + database);
			stm.execute("Use " + database);
			
			
			while(nextSQL())
				stm.executeUpdate(getSQL());		
			
			stm.close();
			con.close();
			
		} catch (Exception e) {
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
			msg.append("Error Reading File - " + er.getMessage());
			return false;
		}
		return true;
	}
	

}
