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
import java.util.Enumeration;
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
		} catch (Exception exception) {
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
		} catch (Exception exception) {
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

		} catch (Exception exception) {
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

		} catch (Exception exception) {
			dataSet.addMessage("SVR0001");
		} finally {
			dataAccss.execClose(qry);
		}
		return dataSet;
	}

	/**/
	public DataSet getCryptSrch(DataSet dataSet, DataAccess dataAccss) {
		Vector<String[]> cstmrv1 = new Vector<String[]>();
		String[] tblCols = (String[]) dataSet.getTableColumnFields("Table1");
		ResultSet resultSet;
		Statement sqlStatement = dataAccss.execConnectReadOnly();

		try {
			String sql = "Select * From wkpPasswordCrypt Where j1 ='" + dataSet.getScmbl()
					+ "' ";

			resultSet = sqlStatement.executeQuery(sql);
			while (resultSet.next()) {
				String[] row1 = new String[tblCols.length];
				for (int x = 1; x < tblCols.length; x++)
					row1[x] = (String) DataAccess.getFieldValue(resultSet,
							tblCols[x], dataAccss.getDataType(tblCols[x]));

				cstmrv1.addElement(row1);
			}
			resultSet.close();
			dataSet.put("Table1", cstmrv1);

		} catch (Exception exception) {
			dataSet.addMessage("SVR0001");
		} finally {
			dataAccss.execClose(sqlStatement);
		}
		return dataSet;
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
			dtAccss.logMessage(" Adherent -- " + e);
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
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}
	
}
