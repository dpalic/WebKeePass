package server;

/**
 * <p>Title: PasswordCrypt </p>
 * <p>Description: The Password Crypt </p>
 *
 * 
 * @author  [User/]
 * @version 1.0
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import webBoltOns.dataContol.DBSchemaException;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;

public class Crypt {

	public Crypt() {
	}

	/**/
	public DataSet getCrypt(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (dataSet.containsKey("PasswordID") && dataAccss.executeQuery(
					"Select * From wkpPasswordCrypt Where PasswordID = '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl() + "' ",
					"wkpPasswordCrypt", dataSet)) {

				dataSet = addAccessItem(dataSet, dataAccss, "KeePass002");

			 	return getCrypHistoryList(dataSet, dataAccss);
			
			} else {
				dataSet.remove("Table1");
				dataSet.remove("PasswordID");
				dataSet.addMessage("LIT0001");
			}
		} catch (DBSchemaException e) {
			dataAccss.logMessage(" getCrypt -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	
	/**/
	public DataSet getSharedCrypt(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (dataSet.containsKey("PasswordID") && dataAccss.executeQuery(
					"Select * From wkpPasswordCrypt Where PasswordID =  '"
							+  dataSet.getStringField("PasswordID") 
							+ "' And  j1 = '" + dataSet.getStringField("j1") +"' ",
							 
							"wkpPasswordCrypt", dataSet)) {
				
				dataSet = addAccessItem(dataSet, dataAccss, "KeePass012");
				
			 	return getCrypHistoryList(dataSet, dataAccss);
			
			} else {
				dataSet.remove("Table1");
				dataSet.putIntegerField("PasswordID", 0);
				dataSet.addMessage("LIT0001");
			}
		} catch (DBSchemaException e) {
			dataAccss.logMessage(" getSharedCrypt -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}
	

	
	
	
	public DataSet getCryptDesc(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (dataAccss.executeQuery(
					"Select a0, b9, c8,  i2, j1  From wkpPasswordCrypt Where PasswordID = '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl() + "' ",			
							new String[] {"a0", "b9", "c8", "i2", "j1"}, dataSet)) {

				dataSet.putBooleanField("wkpPasswordCrypt", true);
				
			} else {
				dataSet.putBooleanField("wkpPasswordCrypt", false);
				dataSet.addMessage("WKP0002");
			}
			
		} catch (DBSchemaException e) {
			dataSet.putBooleanField("wkpPasswordCrypt", false);
			dataSet.addMessage("WKP0002");
		}
		return dataSet;
	}

	
	
	/**/
	public DataSet getCrypHistory(DataSet dataSet, DataAccess dataAccss) {
		try {
			if(  dataSet.getStringField("HistoryID") != null) {
 			  if (!dataAccss.executeQuery(
					"Select * From wkpCryptHistory Where HistoryID = "
							+ dataSet.getStringField("HistoryID")
							+ " And  PasswordID =  '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl() + "' ",
					"wkpCryptHistory", dataSet))

				dataSet.addMessage("LIT0001");
			}
		} catch (DBSchemaException e) {
			dataAccss.logMessage(" getCrypHistory -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet nxtCrypHistory(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (!dataAccss.executeQuery(
					"Select * From wkpCryptHistory Where HistoryID > "
							+ dataSet.getStringField("HistoryID")
							+ " And  PasswordID = '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl()
							+ "' Order By HistoryID ", "wkpCryptHistory",
					dataSet))

				dataSet.addMessage("LIT0002");
		} catch (DBSchemaException e) {
			dataAccss.logMessage(" nxtCrypHistory -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet prvCrypHistory(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (!dataAccss.executeQuery(
					"Select * From wkpCryptHistory Where HistoryID <  "
							+ dataSet.getStringField("HistoryID")
							+ " And  PasswordID = '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl()
							+ "' Order By HistoryID Desc ", "wkpCryptHistory",
					dataSet))

				dataSet.addMessage("LIT0003");
		} catch (DBSchemaException e) {
			dataAccss.logMessage(" prvCrypHistory -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet getCrypHistoryList(DataSet dataSet, DataAccess dataAccss) {
		try {
			String[] tb = new String[] { "HistoryID", "a0", "b9", "e6", "s2"  };
			dataSet.putTableVector("Table1", dataAccss.executeVectorQuery(
					"Select HistoryID, a0, b9, e6, s2 From wkpCryptHistory "
							+ " Where  PasswordID = '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl() + "' ", tb));

		} catch (DBSchemaException e) {
			dataAccss.logMessage(" getCrypHistoryList -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet restoreCrypt(DataSet dataSet, DataAccess dataAccss) {
		return updateCrypt(dataSet, dataAccss);
	}

	/**/
	public DataSet updCrypt(DataSet dataSet, DataAccess dataAccss) {

		dataSet.putStringField("s2", dataAccss.encrypt(dataSet.getUserIP()));
		dataSet.putStringField("t1", dataAccss.encrypt(dataSet.getRmtHost()));

		try {
			if (editCrypt(dataSet, dataAccss)) {
				if (isNewCrypt(dataSet, dataAccss))
					dataSet = insertCrypt(dataSet, dataAccss);
				else
					dataSet = updateCrypt(dataSet, dataAccss);

				dataSet.putIntegerField("HistoryID",
						new Sequences().getNextSequences("wkpCryptHistory", "HistoryID", dataAccss));
				dataAccss.executeInsertQuery("wkpCryptHistory", dataSet);
			}

		} catch (DBSchemaException e) {
			dataAccss.logMessage(" updCrypt -- " + e);
			dataSet.addMessage("SVR0001");
		}

		return dataSet;
	}

	/**/
	private boolean editCrypt(DataSet dataSet, DataAccess dataAccss) {
		
		if(dataSet.getStringField("PasswordID") == null || 
				dataSet.getStringField("PasswordID").equals("")) {	
			dataSet.addMessage("WKP0004","PassworID");
			return false;
		}
		
		return true;
	}

	/**/
	public boolean isNewCrypt(DataSet dataSet, DataAccess dataAccss) {
		try {
			return !dataAccss.executeQuery(
					"Select PasswordID From wkpPasswordCrypt Where PasswordID = '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl() + "' ", "", null);
		} catch (Exception e) {
			dataAccss.logMessage(" isNewCrypt -- " + e);
			dataSet.addMessage("SVR0001");
			return true;
		}
	}

	/**/
	private DataSet insertCrypt(DataSet dataSet, DataAccess dataAccss) {
		try {
			String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
			dataSet.putStringField("d7", dataAccss.encrypt(today));
			dataSet.putStringField("e6", dataAccss.encrypt(today));
			dataSet.putStringField("j1", dataSet.getScmbl());
			if (dataSet.get("Image") == null)
				dataSet.remove("Image");
			if (dataSet.get("Notes") == null)
				dataSet.remove("Notes");
 
			dataAccss.executeInsertQuery("wkpPasswordCrypt", dataSet);
			dataSet.addMessage("LIT0005");
		} catch (Exception e) {
			dataSet.addMessage("SVR0001");
		}

		return dataSet;
	}

	/**/
	private DataSet updateCrypt(DataSet dataSet, DataAccess dataAccss) {
		try {
			String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
			dataSet.putStringField("e6", dataAccss.encrypt(today));
			dataSet.putStringField("j1", dataSet.getScmbl());
			if (dataSet.get("Image") == null)
				dataSet.remove("Image");
			if (dataSet.get("Notes") == null)
				dataSet.remove("Notes");

			dataAccss.executeUpdateQuery("wkpPasswordCrypt", dataSet,
					"Where PasswordID = '"
							+ dataSet.getStringField("PasswordID")
							+ "'  And j1 = '" + dataSet.getScmbl() + "' ");
			dataSet.addMessage("LIT0004");
		} catch (Exception e) {
			dataAccss.logMessage(" updateCrypt -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet delCrypt(DataSet dataSet, DataAccess dataAccss) {
		Statement sql = dataAccss.execConnectUpdate();
		try {
			sql.executeUpdate("Delete from wkpPasswordCrypt Where PasswordID = '"
							+ dataSet.getStringField("PasswordID")
									+ "'  And j1 = '" + dataSet.getScmbl() + "' ");
			
			sql.executeUpdate("Delete from wkpCryptHistory Where PasswordID =  '"
					+ dataSet.getStringField("PasswordID") + "' ") ;
			
			sql.executeUpdate("Delete from wkpPasswordAKin Where PasswordID = '"
					+ dataSet.getStringField("PasswordID") + "' ");
			
			
			dataSet.addMessage("LIT0006");
		} catch (Exception e) {
			dataAccss.logMessage(" delCrypt -- " + e);
			dataSet.addMessage("SVR0001");
		} finally {
			dataAccss.execClose(sql);
		}
		return dataSet;
	}

	
	
	
	/**/
	public DataSet nxtCrypt(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (dataSet.containsKey("PasswordID") && dataAccss.executeQuery(
					"Select * From wkpPasswordCrypt Where PasswordID > '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl()
							+ "' Order By PasswordID ", "wkpPasswordCrypt",
					dataSet)) {

				dataSet = addAccessItem(dataSet, dataAccss, "KeePass002");
				
				return getCrypHistoryList(dataSet, dataAccss);
			} else {
				dataSet.addMessage("LIT0002");
			}
		} catch (DBSchemaException e) {
			dataAccss.logMessage(" nxtCrypt -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet prvCrypt(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (dataSet.containsKey("PasswordID") && dataAccss.executeQuery(
					"Select * From wkpPasswordCrypt Where PasswordID < '"
							+ dataSet.getStringField("PasswordID")
							+ "' And j1 = '" + dataSet.getScmbl()
							+ "' Order By PasswordID Desc ",
					"wkpPasswordCrypt", dataSet)) {

				dataSet = addAccessItem(dataSet, dataAccss, "KeePass002");
				
				return getCrypHistoryList(dataSet, dataAccss);
			} else {
				dataSet.addMessage("LIT0003");
			}
		} catch (DBSchemaException e) {
			dataAccss.logMessage(" prvCrypt -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	public DataSet getCryptList(DataSet dataSet, DataAccess dataAccss) {
		Vector<Object[]> crypt = new Vector<Object[]>();
		ResultSet resultSet;
		Statement qry = dataAccss.execConnectReadOnly();

		try {
			String sql = "Select KeeperID, KeeperDesc, CreateDate, KeeperIcon "
					+ " From wkpGroups where PrntID = 0 And j1 ='"
					+ dataSet.getScmbl() + "' ";
			resultSet = qry.executeQuery(sql);
			while (resultSet.next()) {
				Object[] row = new Object[8];
				row[0] = new Boolean(false);
				row[1] = Integer.toString(resultSet.getInt("KeeperID"));
				row[2] = resultSet.getString("KeeperDesc");
				row[3] = resultSet.getString("CreateDate");
				row[4] = resultSet.getString("KeeperIcon");
				crypt.addElement(row);
			}
			resultSet.close();

			sql = "Select PasswordID, a0, b9, d7, r3 from wkpPasswordCrypt  where KeeperID = 0 And j1 = '"
					+ dataSet.getScmbl() + "' ";

			resultSet = qry.executeQuery(sql);
			while (resultSet.next()) {
				Object[] row = new Object[8];
				row[0] = new Boolean(true);
				row[1] = "0";
				row[2] = "     Web-KeePass Password";
				row[3] = dataAccss.decrypt(resultSet.getString("d7"));
				row[4] = resultSet.getString("r3");
				row[5] = resultSet.getString("PasswordID");
				row[6] = resultSet.getString("a0");
				row[7] = resultSet.getString("b9");
				crypt.addElement(row);
			}
			dataSet.put("Table1", crypt);

		} catch (Exception e) {
			dataAccss.logMessage(" getCryptList -- " + e);
			dataSet.addMessage("SVR0001");
		} finally {
			dataAccss.execClose(qry);
		}
		return dataSet;
	}

	public DataSet expandCryptList(DataSet dataSet, DataAccess dataAccss) {
		Vector<Object[]> crypt = new Vector<Object[]>();
		ResultSet resultSet;
		Statement qry = dataAccss.execConnectReadOnly();
		int keeper = DataSet.checkInteger(dataSet.getTreeItemToExpand());
		if (keeper == 0)
			return dataSet;

		try {
			String sql = "Select KeeperID, KeeperDesc, CreateDate, KeeperIcon "
					+ " From wkpGroups where PrntID = " + keeper + " And j1 ='"
					+ dataSet.getScmbl() + "' ";
			resultSet = qry.executeQuery(sql);
			while (resultSet.next()) {
				Object[] row = new Object[8];
				row[0] = new Boolean(false);
				row[1] = Integer.toString(resultSet.getInt("KeeperID"));
				row[2] = resultSet.getString("KeeperDesc");
				row[3] = resultSet.getString("CreateDate");
				row[4] = resultSet.getString("KeeperIcon");

				crypt.addElement(row);
			}
			resultSet.close();

			sql = "Select PasswordID, a0, b9, d7, r3 from wkpPasswordCrypt  where KeeperID = "
					+ keeper + " And j1 = '" + dataSet.getScmbl() + "' ";

			resultSet = qry.executeQuery(sql);
			while (resultSet.next()) {
				Object[] row = new Object[8];
				row[0] = new Boolean(true);
				row[1] = "0";
				row[2] = "     Web-KeePass Password";
				row[3] = dataAccss.decrypt(resultSet.getString("d7"));
				row[4] = resultSet.getString("r3");
				row[5] = resultSet.getString("PasswordID");
				row[6] = resultSet.getString("a0");
				row[7] = resultSet.getString("b9");
				crypt.addElement(row);
			}
			dataSet.put("Table1", crypt);

		} catch (Exception e) {
			dataAccss.logMessage(" expandCryptList -- " + e);
			dataSet.addMessage("SVR0001");
		} finally {
			dataAccss.execClose(qry);
		}
		return dataSet;
	}

	/**/
	public DataSet getCryptSrch(DataSet dtSet, DataAccess dtAccs) {
		
		Vector<String[]> tbl1 = new Vector<String[]>();
		Vector<String[]> tbl2 = new Vector<String[]>();
		String[] cls1 = (String[]) dtSet.getTableColumnFields("Table1");
		String[] cls2 = (String[]) dtSet.getTableColumnFields("Table2");
		ResultSet rs;
		Statement stmt = dtAccs.execConnectReadOnly();

		try {
			String sql = "Select * From wkpPasswordCrypt Where j1 ='" + dtSet.getScmbl() + "' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				String[] row1 = new String[cls1.length];
				for (int x = 1; x < cls1.length; x++)
					row1[x] = (String) DataAccess.getFieldValue(rs,
							cls1[x], dtAccs.getDataType(cls1[x]));

				String[] row2 = new String[cls2.length];
				row2[0] = row1[1];
				for (int x = 1; x < cls2.length; x++)
					row2[x] = (String) DataAccess.getFieldValue(rs,
							cls2[x], dtAccs.getDataType(cls2[x]));

			    
				if (    (row1[1] == null || dtSet.getStringField("@pwdID") == null || dtSet.getStringField("@pwdID").equals("") 
						          || dtAccs.decrypt(row1[1]).contains(dtSet.getStringField("@pwdID") ))	
						      
					 &&  (row1[3] == null || dtSet.getStringField("@desc") == null || dtSet.getStringField("@desc").equals("") 
							      || dtAccs.decrypt(row1[3]).contains(dtSet.getStringField("@desc") ))
							      
				     &&  (row1[4] == null || dtSet.getStringField("@userName") == null || dtSet.getStringField("@userName").equals("") 
								  || dtAccs.decrypt(row1[4]).contains(dtSet.getStringField("@userName") )) ) {	
			    		
					tbl1.addElement(row1);
					tbl2.addElement(row2);
			    }
			}
			
			rs.close();
			dtSet.put("Table1", tbl1);
			dtSet.put("Table2", tbl2);

		} catch (Exception e) {
			dtAccs.logMessage(" getCryptSrch -- " + e);
			dtSet.addMessage("SVR0001");
		} finally {
			dtAccs.execClose(stmt);
		}
		return dtSet;
	}
	 
	
	
	public DataSet addAccessItem(DataSet dtSet, DataAccess dtAccss, String script) {
		PreparedStatement insQry = dtAccss.execPreparedConnect(
				"Insert Into wkpAccess (AccessID, aa, bb, cc, dd, ee, ff, gg) " +
				" Values (?, ?, ?, ?, ?, ?, ?, ? ) "); 
		try {
	
			insQry.setInt(1, new Sequences().getNextSequences("wkpAccess", "AccessID", dtAccss));
	 		insQry.setString(2, dtSet.getStringField("PasswordID"));
	 		insQry.setString(3, dtSet.getStringField("j1"));
	 		insQry.setString(4, dtSet.getScmbl());
	 		insQry.setString(5, dtAccss.encrypt(dtSet.getUserIP()));
	 		insQry.setString(6, dtAccss.encrypt(
	 				new SimpleDateFormat("yyyyMMdd").format(new Date()) ));
	 		insQry.setString(7, dtAccss.encrypt(new SimpleDateFormat("HH:mm:ss").format(new Date()) ));
	 		insQry.setString(8, dtAccss.encrypt(script));
			
	 		insQry.executeUpdate();
			 
		} catch (Exception e) {
			dtAccss.logMessage(" addAccessItem -- " + e);
			dtSet.addMessage("SVR0001");
		} finally {
			dtAccss.execClose(insQry);
		}

		return dtSet;
	}
	
	
	/**/
	public DataSet getAccesslog(DataSet dataSet, DataAccess dataAccss) {
		try {
			String[] tb = new String[] { "AccessID", "cc", "dd", "ee", "ff", "gg" };
			
			String sql =  " Select * From wkpAccess Where  aa = '" 
				+ dataSet.getStringField("PasswordID")
				+ "' And bb = '" + dataSet.getScmbl() + "' ";
			
			String cc = dataSet.getStringField("@cc");
			if(cc != null && !cc.equals(""))
				sql += " And cc = '" + dataAccss.encrypt(cc) + "' ";
	
			String ee = dataSet.getStringField("@ee");
			if(ee != null && !ee.equals(""))
				sql += " And ee = '" + dataAccss.encrypt(ee) + "' ";
			
			String dd = dataSet.getStringField("@dd");
			if(dd != null && !dd.equals(""))
				sql += " And dd = '" + dataAccss.encrypt(dd) + "' ";
			
			dataSet.putTableVector("Table1", dataAccss.executeVectorQuery(sql, tb));

		} catch (DBSchemaException e) {
			dataAccss.logMessage(" getAccesslog -- " + e);
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}
	
}
