package server;
/**
 * <p>Title: Sequences</p>
 * <p>Description: </p>
 *
 * 
 * @author  [User/]
 * @version 1.0
 */


/*
Class Description:
TableName  
*/


/*
Tables/Data Definition:
  
*/



 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import webBoltOns.dataContol.DataAccess; 

public class Sequences {
	
 public Sequences() {}
 
 /**/
 
 public int getNextSequences(String tableName, String tableID, DataAccess dtAccss) {     
  
     ResultSet resultSet;
     Statement sqlStatement = dtAccss.execConnectReadOnly();
     int seq = 0;
      try {
         	resultSet = sqlStatement.executeQuery(
         			"Select seq from wkpSeqCrypt Where TableName = '" + tableName + "'");
         	if (resultSet.next()) {
         		seq = (resultSet.getInt("seq")) + 1;
         		seq = CheckTable(tableName, tableID, seq, dtAccss);
         		updateIntoSequences(tableName, seq, dtAccss);
         	} else {
         		seq = 1;
         		seq = CheckTable(tableName, tableID, seq, dtAccss);         		
         		insertIntoSequences(tableName, seq, dtAccss);
         	}
         	
         		resultSet.close();	
      } catch (Exception exception) {
      dtAccss.logMessage(" *Sequences.getNextSequences* -- " + exception);
    } finally {
      dtAccss.execClose(sqlStatement);
    }
  return seq;
  }

 
 
 public int getNextSequences (String seqID, DataAccess dtAccss) {     
	  
     ResultSet resultSet;
     Statement sqlStatement = dtAccss.execConnectReadOnly();
     int seq = 0;
      try {
         	resultSet = sqlStatement.executeQuery(
         			"Select seq from wkpSeqCrypt Where TableName = '" + seqID + "'");
         	if (resultSet.next()) {
         		seq = (resultSet.getInt("seq")) + 1;
         		updateIntoSequences(seqID, seq, dtAccss);
         	} else {
         		seq = 1;
         		insertIntoSequences(seqID, seq, dtAccss);
         	}
         	
         		resultSet.close();	
      } catch (Exception exception) {
      dtAccss.logMessage(" *Sequences.getNextSequences* -- " + exception);
    } finally {
      dtAccss.execClose(sqlStatement);
    }
  return seq;
  }

 
 private int CheckTable(String tableName, String tableID, int seq,  DataAccess dtAccss) {     
     ResultSet resultSet;
     Statement sqlStatement = dtAccss.execConnectReadOnly();
      try {
         	resultSet = sqlStatement.executeQuery(
         			"Select "+ tableID + " From "+ tableName + 
         			" Where " + tableID + " >= " + seq + " Order By " + tableID);
         	while (resultSet.next() && seq == resultSet.getInt(tableID))  seq++;
         	resultSet.close();	
      } catch (Exception exception) {
      dtAccss.logMessage(" *Sequences.getwkpSeqCrypt* -- " + exception);
    } finally {
      dtAccss.execClose(sqlStatement);
    }
  return seq;
  }

 
 
 private void insertIntoSequences(String tableName, int seq , DataAccess dtAccss) {
  	PreparedStatement sqlUpdateStatement = null;
  	try {
         sqlUpdateStatement = dtAccss.execPreparedConnect(
                   "Insert Into wkpSeqCrypt (TableName, Seq ) Values (?, ?)");
         sqlUpdateStatement.setString(1,tableName);
         sqlUpdateStatement.setInt(2, seq);
         sqlUpdateStatement.executeUpdate();

         } catch (Exception exception) {
            dtAccss.logMessage(" *Sequences.insertIntoSequences* -- " + exception);
         } finally {
           dtAccss.execClose(sqlUpdateStatement);
         }
     }
                      

  private void updateIntoSequences(String tableName, int seq, DataAccess dtAccss) {
  	PreparedStatement sqlUpdateStatement = null;
  	try {
  		sqlUpdateStatement = dtAccss.execPreparedConnect(
  				"Update wkpSeqCrypt SET Seq = ?  Where TableName = ?");
        sqlUpdateStatement.setInt(1, seq);
        sqlUpdateStatement.setString(2,tableName);
        sqlUpdateStatement.executeUpdate();
  	} catch (Exception exception) {
  		dtAccss.logMessage(" *Sequences.updateInto.wkpSeqCrypt* -- " + exception);
  	} finally {
  		dtAccss.execClose(sqlUpdateStatement);
  	}
  }


}