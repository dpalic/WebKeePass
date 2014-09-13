package server;
/**
 * <p>Title: wkpPasswordAKin </p>
 * <p>Description:  </p>
 *
 * 
 * @author  [User/]
 * @version 1.0
 */


/*
Class Description:
  
*/


/*
Tables/Data :
  
*/



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import webBoltOns.dataContol.DBSchemaException;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;
import webBoltOns.server.UserSecurityManager;



public class Akin {


	
 public Akin() {}
 
 private TheCrypt crpt = new TheCrypt();
 //private UserSecurityManager usm = new UserSecurityManager();
 
 /**/
 public DataSet getAKin(DataSet dataSet, DataAccess dataAccss) {     
	 try {
    	  if(! dataAccss.executeQuery(
          		"Select * From wkpPasswordAKin Where AKinID =  " +  dataSet.getIntegerField("KinID") +
					" And PasswordID = "+ dataSet.getIntegerField("PasswordID") + 
	  			    " And y1 = '"+ dataSet.getScmbl()  +"' ", "wkpPasswordAKin" ,dataSet))
	  			    		
    		  dataSet.addMessage("Record Not found", "30", "FirstName", null);
      
    } catch (Exception exception) {
      dataSet.addMessage("Database Error - Please contact systems administrator", "30", null, null);
    }
    return dataSet;
  }
  
 
 public DataSet initAkinList(DataSet dataSet, DataAccess dataAccss) {      
	 dataSet = crpt.getCryptDesc(dataSet, dataAccss);
	 if(dataSet.getBooleanField("wkpPasswordCrypt"))		 
		 return getAkinList(dataSet,  dataAccss);
	  else 
		 return dataSet;
  
 }

 

  public DataSet getAkinList(DataSet dataSet, DataAccess dataAccss) {     
		try {
			String[] tb = new String[] {"AKinID","x2", "w3", "v4", "u5"};
			dataSet.putTableVector("Table1", dataAccss.executeVectorQuery(
					"Select * From wkpPasswordAKin Where  PasswordID =  "
							+ dataSet.getStringField("PasswordID")
							+ " And y1 = '" + dataSet.getScmbl() + "' ", tb));
		} catch (DBSchemaException e) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}
	

  
  
  public DataSet getShareList(DataSet dataSet, DataAccess dataAccss) {
	  
	  int today = DataSet.checkInteger(new SimpleDateFormat("yyyymmdd").format(new Date()));
	  Statement sql = null;
	  ResultSet rs = null;
	  Vector<String[]> v = new Vector<String[]>();
	  try {
		  
		  dataAccss.executeQuery(
  				"Select GroupID From jrUsers  Where UserID = '" + dataSet.getUser()  + 
  				"' " , new String[]{"GroupID"}, dataSet);
		  
		  
		  String qry = 
			  "Select a.PasswordID, a.w3, a.v4, a.x2, b.a0, b.j1, b.b9 from wkpPasswordAKin a inner join wkpPasswordCrypt b " +
			  " on a.PasswordID = b.PasswordID Where a.x2 ='" + dataSet.getScmbl() + 
			  "' Or  a.x2 = '" + dataAccss.encrypt(dataSet.getStringField("GroupID")) + "' ";
		  
		  sql = dataAccss.execConnectReadOnly();
		  rs = sql.executeQuery(qry);
		  while(rs.next()){
			boolean active   = DataSet.checkBoolean(dataAccss.decrypt(rs.getString("v4")));
			if(active) {
				String [] row= new String[6];
				row[0] = rs.getString("PasswordID");
				row[1] = rs.getString("j1");
				row[2] = rs.getString("x2");
				row[3] = rs.getString("w3");
				row[4] = rs.getString("b9");
				row[5] = rs.getString("a0");
				v.add(row);
			}
		  }
		  	
		  dataSet.putTableVector("Table1", v);
		  dataAccss.execClose(sql);
		  
	  } catch (Exception e) {
		  dataSet.addMessage("SVR0001");
	  }
		 
	  return dataSet;
	}

  
  
  
    public boolean isNewAkin(String aKinID, String pwID, String y1, DataAccess dataAccss){
     try {
    	 return !dataAccss.executeQuery(
     			"Select AKinID From wkpPasswordAKin Where AKinID = " + aKinID +
     			" And PasswordID = "+ pwID + 
  			    " And y1 = '"+ y1  +"' " ,"", null);
    	 
       } catch (Exception exception) {
    	   return true;
       }     
    }
        

   
    /**/
    public DataSet deleteAkin(DataSet dataSet, DataAccess dtAccss) {
  		PreparedStatement updQry = dtAccss.execPreparedConnect(
  				"Delete From wkpPasswordAKin Where AKinID = ?  And PasswordID = "+ dataSet.getIntegerField("PasswordID") + 
	  			    " And y1 = '"+ dataSet.getScmbl()  +"' ");
  		
  		Enumeration<?> table = dataSet.getTableVector("Table1").elements();
  		dataSet.remove("Table1");
  		
  		try {
  			while (table.hasMoreElements()) {
  				String[] row = (String[]) table.nextElement();
  				if (row[1] != null && !row[1].equals("")) {
  					updQry.setInt(1, DataSet.checkInteger(row[0]));
  					updQry.executeUpdate();
  				}
  			}
  			
  			dataSet.addMessage("LIT0006");
  		} catch (SQLException e) {
  			dtAccss.logMessage(" *Akin.deleteAkin* -- " + e);
  			dataSet.addMessage("SVR0001");
  		} finally {
  			dtAccss.execClose(updQry);
  		}
  		return dataSet;
  	}
   
    
    
    public boolean editAkinList(DataSet dtSet, DataAccess dtAccss) throws DBSchemaException {
    	dtSet = crpt.getCryptDesc(dtSet, dtAccss);
    	if(! dtSet.getBooleanField("wkpPasswordCrypt")) return false;
    	  
      	Enumeration<?> table = dtSet.getTableVector("Table1").elements();
      	while (table.hasMoreElements()) {
      		String[] row = (String[]) table.nextElement();
      		String ug = dtAccss.decrypt(row[1]);
      		if(row[1] != null && !row[0].equals("")
      				&& !dtAccss.executeQuery(
      				"Select UserID From jrUsers Where UserID = '" + ug + "' ", "", null)  && 
    			 !dtAccss.executeQuery(
    				"Select GroupID From jrUserGroups  Where GroupID = '" + ug  + "' " , "", null) ) {
     				 
    			 dtSet.addMessage("WKP0001");
    			 return false;
      		}
      	}    	
    	return true;
    }
    
    
    /**/
    public DataSet updAkinList(DataSet dtSet, DataAccess dtAccss) {

    	PreparedStatement insQry = dtAccss.execPreparedConnect(
    		  "Insert Into wkpPasswordAKin (AKinID, PasswordID, y1, x2, w3, v4) " +
  	  		  " Values ( ?, " + dtSet.getIntegerField("PasswordID") + ", '"  + dtSet.getScmbl() +"', ?, ?, ? )");
  	  
    	PreparedStatement updQry = dtAccss.execPreparedConnect(
  			  "Update wkpPasswordAKin SET x2 = ?, w3 = ?, v4 = ? " +
  			  " Where AKinID = ? And PasswordID = "+ dtSet.getIntegerField("PasswordID") + 
  			  " And y1 = '"  + dtSet.getScmbl() +"' ");
    	
    	try {	
        		if(!editAkinList(dtSet, dtAccss))  return dtSet;
      	  
    		Enumeration<?> table = dtSet.getTableVector("Table1").elements();
    		dtSet.remove("Table1");

  	  
  		  	while (table.hasMoreElements()) {
  		  		String[] row = (String[]) table.nextElement();

  				if (row[1] != null && !row[1].equals("")) {
  			
  					if (isNewAkin(row[0], dtSet.getStringField("PasswordID"), dtSet.getScmbl(), dtAccss)) {	
  						insQry.setInt(1, new Sequences().getNextSequences("wkpPasswordAKin", "AKinID", dtAccss));
  						insQry.setString(2, row[1]);
  						insQry.setString(3, row[2]);
  						insQry.setString(4, row[3]);
  						insQry.executeUpdate();
  					
  					} else {
  						updQry.setString(1, row[1]);
  						updQry.setString(2, row[2]);
  						updQry.setString(3, row[3]);
  						updQry.setInt(4, DataSet.checkInteger(row[0]));
  						updQry.executeUpdate();
  					}
  			
  				}
  		  	}
    	
  		  	dtSet.addMessage("LIT0004");
  		} catch (Exception e) {
  			dtAccss.logMessage(" *Topics.updateTopicsList* -- " + e);
  			dtSet.addMessage("SVR0001");
  		} finally {
  			dtAccss.execClose(insQry);
  			dtAccss.execClose(updQry);
  		}
  		return dtSet;
  	}
 
    
}
