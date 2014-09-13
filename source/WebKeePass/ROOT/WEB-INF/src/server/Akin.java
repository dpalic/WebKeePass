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

public class Akin {

	public Akin() { }

	private Crypt crpt = new Crypt();

	/**/
	public DataSet getAKin(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (!dataAccss.executeQuery(
					"Select * From wkpPasswordAKin Where AKinID =  "
							+ dataSet.getIntegerField("KinID")
							+ " And PasswordID = '"
							+ dataSet.getIntegerField("PasswordID")
							+ "' And y1 = '" + dataSet.getScmbl() + "' ",
					"wkpPasswordAKin", dataSet))

				dataSet.addMessage("Record Not found", "30", "FirstName", null);

		} catch (Exception exception) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	public DataSet initAkinList(DataSet dataSet, DataAccess dataAccss) {
		dataSet = crpt.getCryptDesc(dataSet, dataAccss);
		if (dataSet.getBooleanField("wkpPasswordCrypt"))
			return getAkinList(dataSet, dataAccss);
		else
			return dataSet;

	}

	public DataSet getAkinList(DataSet dataSet, DataAccess dataAccss) {
		try {
			String[] tb = new String[] { "AKinID", "x2", "t6", "w3", "v4", "r9", "u5"};
			dataSet.putTableVector("Table1", dataAccss.executeVectorQuery(
					"Select AKinID, x2, t6, w3, v4, r9, u5 From wkpPasswordAKin Where  PasswordID = '"
							+ dataSet.getStringField("PasswordID")
							+ "' And y1 = '" + dataSet.getScmbl() + "' ", tb));
		} catch (DBSchemaException e) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	public DataSet getAkinListByPwd(DataSet dataSet, DataAccess dataAccss) {
		try {
			String[] tb = new String[] { "PasswordID", "a0", "b9", "AKinID",
					"x2", "w3", "v4", "u5" };
			dataSet.putTableVector("Table1",dataAccss.executeVectorQuery(
											"SELECT a.PasswordID, a0, b9, AKinID,x2, w3, v4, u5 From wkpPasswordAKin a inner join wkpPasswordCrypt c  "
													+ "on a.PasswordID = c.PasswordID where y1 = '"
													+ dataSet.getScmbl()
													+ "' Order By a.PasswordID",
											tb));
		} catch (DBSchemaException e) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	public DataSet getShareList(DataSet dataSet, DataAccess dataAccss) {

		Statement sql = null;
		ResultSet rs = null;
		Vector<String[]> v = new Vector<String[]>();
		try {

			dataAccss.executeQuery(
					"Select GroupID From jrUsers  Where UserID = '"
							+ dataSet.getUser() + "' ",
					new String[] { "GroupID" }, dataSet);

			String qry = "Select a.PasswordID, a.w3, a.v4, a.x2,  a.t6, a.r9, b.a0, b.j1, b.b9 from wkpPasswordAKin a inner join wkpPasswordCrypt b "
					+ " on a.PasswordID = b.PasswordID Where a.x2 ='" + dataSet.getScmbl()
					+ "' Or  a.x2 = '" + dataAccss.encrypt(dataSet.getStringField("GroupID"))
					+ "' Or  a.t6 in ( Select cc from wkpBundle Where gg = '"+ dataSet.getScmbl()
					   + "' And hh = '" + dataAccss.encrypt("[Bundle-Access/]") 
					   + "' And ee = '" + dataAccss.encrypt("true") +"' ) "; 

			sql = dataAccss.execConnectReadOnly();
			rs = sql.executeQuery(qry);
			while (rs.next()) {
				boolean active = DataSet.checkBoolean(dataAccss.decrypt(rs.getString("v4")));
				int exp =  DataSet.checkInteger(DataSet.formatDateField(
											dataAccss.decrypt(rs.getString("r9")), "yyyyMMdd")  );
				int today  = DataSet.checkInteger(new SimpleDateFormat("yyyyMMdd").format(new Date()));
				if(exp > 0 && exp < today) active = false;
				
				if (active) {
					String[] row = new String[7];
					row[0] = rs.getString("PasswordID");
					row[1] = rs.getString("j1");
					row[2] = rs.getString("t6");
					row[3] = rs.getString("x2");
					row[4] = rs.getString("w3");
					row[5] = rs.getString("b9");
					row[6] = rs.getString("a0");
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

	public boolean isNewAkin(String aKinID, String pwID, String y1,
			DataAccess dataAccss) {
		try {
			return !dataAccss.executeQuery(
					"Select AKinID From wkpPasswordAKin Where AKinID = "
							+ aKinID + " And PasswordID = '" + pwID
							+ "' And y1 = '" + y1 + "' ", "", null);

		} catch (Exception e) {
			return true;
		}
	}

	/**/
	public DataSet deleteAkin(DataSet dataSet, DataAccess dtAccss) {
		PreparedStatement updQry = dtAccss.execPreparedConnect(
				"Delete From wkpPasswordAKin Where AKinID = ?  And PasswordID = '"
						+ dataSet.getIntegerField("PasswordID")
						+ "' And y1 = '"
						+ dataSet.getScmbl() + "' ");

		Enumeration<?> table = dataSet.getTableVector("Table1").elements();
		dataSet.remove("Table1");

		try {
			while (table.hasMoreElements()) {
				String[] row = (String[]) table.nextElement();
				if (row[0] != null && !row[0].equals("")) {
					updQry.setInt(1, DataSet.checkInteger(row[0]));
					updQry.executeUpdate();
				}
			}

			dataSet.addMessage("LIT0006");
		} catch (SQLException e) { 
			dataSet.addMessage("SVR0001");
		} finally {
			dtAccss.execClose(updQry);
		}
		return dataSet;
	}

	
	
	public boolean editAkinList(DataSet dtSet, DataAccess dtAccss)
			throws DBSchemaException {
	 
		dtSet = crpt.getCryptDesc(dtSet, dtAccss);
		if (!dtSet.getBooleanField("wkpPasswordCrypt")) return false;

		Enumeration<?> table = dtSet.getTableVector("Table1").elements();
		while (table.hasMoreElements()) {
			String[] row = (String[]) table.nextElement();
			String ug = dtAccss.decrypt(row[1]);
			if (row[1] != null && !row[1].equals("")
					&& !dtAccss.executeQuery(
							"Select UserID From jrUsers Where UserID = '" + ug + "' ", "", null)
					&& !dtAccss.executeQuery(
							"Select GroupID From jrUserGroups  Where GroupID = '" + ug + "' ", "", null)) {

				dtSet.addMessage("WKP0001");
				return false;
			}

			if (row[2] != null && !row[2].equals("") && editBnd(row[2], dtAccss)){
				dtSet.addMessage("WKP0003");
				return false;
			}
			
			
			if (row[2] != null && !row[2].equals("")
					&& !dtAccss.executeQuery(
							"Select BundleID From wkpBundle Where aa = '" + row[2] + "' ", "", null)) {
				dtSet.addMessage("WKP0003");
				return false;
			}

		}
		return true;
	}

	/**/
	public DataSet updAkinList(DataSet dtSet, DataAccess dtAccss) {

		PreparedStatement insQry = dtAccss.execPreparedConnect(
				"Insert Into wkpPasswordAKin (AKinID, PasswordID, y1, x2, t6, w3, v4, r9, u5) "
						+ " Values ( ?, '"
					    + dtSet.getStringField("PasswordID")
						+ "', '" + dtSet.getScmbl() + "', ?, ?, ?, ?, ?, ? )");

		PreparedStatement updQry = dtAccss.execPreparedConnect(
				"Update wkpPasswordAKin SET x2 = ?,t6 = ?, w3 = ?, v4 = ?, r9 = ?, u5 = ? "
						+ " Where AKinID = ? And PasswordID = '"
						+ dtSet.getStringField("PasswordID")
						+ "' And y1 = '"
						+ dtSet.getScmbl() + "' ");

		try {
			if (!editAkinList(dtSet, dtAccss)) 	return dtSet;

			Enumeration<?> table = dtSet.getTableVector("Table1").elements();
			dtSet.remove("Table1");

			while (table.hasMoreElements()) {
				String[] row = (String[]) table.nextElement(); 
				
				if ((row[1] != null && !row[1].equals(""))
						|| (row[2] != null && !row[2].equals(""))) {
					if (row[4] == null)
						row[4] = dtAccss.encrypt("[TOP/]");
					if (isNewAkin(row[0], dtSet.getStringField("PasswordID"),
							dtSet.getScmbl(), dtAccss)) {
						insQry.setInt(1, new Sequences().getNextSequences(
								"wkpPasswordAKin", "AKinID", dtAccss));
						insQry.setString(2, row[1]);
						insQry.setString(3, row[2]);
						insQry.setString(4, row[3]);
						insQry.setString(5, row[4]);
						insQry.setString(6, row[5]);
						insQry.setString(7,row[6]);
						insQry.executeUpdate();
					} else {
						updQry.setString(1, row[1]);
						updQry.setString(2, row[2]);
						updQry.setString(3, row[3]);
						updQry.setString(4, row[4]);
						updQry.setString(5, row[5]);
						updQry.setString(6, row[6]);
						updQry.setInt(7, DataSet.checkInteger(row[0]));
						updQry.executeUpdate();
					}

				}
			}

			dtSet.addMessage("LIT0004");
		} catch (Exception e) {
			dtSet.addMessage("SVR0001");
		} finally {
			dtAccss.execClose(insQry);
			dtAccss.execClose(updQry);
		}
		return dtSet;
	}

	
	
	public DataSet getMySharesList(DataSet dataSet, DataAccess dataAccss) {
		Vector<Object[]> crypt = new Vector<Object[]>();
		ResultSet resultSet;
		Statement qry = dataAccss.execConnectReadOnly();

		try {
			String sql = "Select KeeperID, KeeperDesc, CreateDate, KeeperIcon "
					+ " From wkpGroups where PrntID = 0 And j1 ='[Share-Group/]' ";
			resultSet = qry.executeQuery(sql);
			while (resultSet.next()) {
				Object[] row = new Object[10];
				row[0] = new Boolean(false);
				row[1] = Integer.toString(resultSet.getInt("KeeperID"));
				row[2] = resultSet.getString("KeeperDesc");
				row[3] = resultSet.getString("CreateDate");
				row[4] = resultSet.getString("KeeperIcon");
				crypt.addElement(row);
			}
			resultSet.close();

			dataAccss.executeQuery("Select GroupID From jrUsers  Where UserID = '"
							+ dataSet.getUser() + "' ",
					new String[] { "GroupID" }, dataSet);

			sql = "Select a.PasswordID, a.w3, a.v4, a.r9, b.a0, b.b9, b.j1 from wkpPasswordAKin a inner join wkpPasswordCrypt b "
					+ " on a.PasswordID = b.PasswordID Where (a.x2 ='"
					+ dataSet.getScmbl()
					+ "' Or  a.x2 = '"
					+ dataAccss.encrypt(dataSet.getStringField("GroupID"))
					+ "' Or  a.t6 in ( Select cc from wkpBundle Where gg = '"+ dataSet.getScmbl()
					   + "' And hh = '" + dataAccss.encrypt("[Bundle-Access/]") 
					   + "' And ee = '" + dataAccss.encrypt("true") 
					+ "' ) ) And a.u5 = '" + dataAccss.encrypt("[TOP/]") + "' ";

			resultSet = qry.executeQuery(sql);

			while (resultSet.next()) {
				boolean active = DataSet.checkBoolean(dataAccss.decrypt(resultSet.getString("v4")));
				int exp =  DataSet.checkInteger(DataSet.formatDateField(
											dataAccss.decrypt(resultSet.getString("r9")), "yyyyMMdd")  );
				int today  = DataSet.checkInteger(new SimpleDateFormat("yyyyMMdd").format(new Date()));
				if(exp > 0 && exp < today) active = false;
				if(active) {
				Object[] row = new Object[10];
					row[0] = new Boolean(true);
					row[1] = "0";
					row[2] = "     Web-KeePass Password";
					row[3] = "";
					row[4] = "dot.gif";
					row[5] = resultSet.getString("PasswordID");
					row[6] = resultSet.getString("a0");
					row[7] = resultSet.getString("b9");
					row[8] = resultSet.getString("j1");
					row[9] = resultSet.getString("w3");
					crypt.addElement(row);
				}
			}

			dataSet.put("Table1", crypt);

		} catch (Exception exception) {
			dataSet.addMessage("SVR0001");
		} finally {
			dataAccss.execClose(qry);
		}
		return dataSet;
	}

	public DataSet expandMySharesList(DataSet dataSet, DataAccess dataAccss) {
		Vector<Object[]> crypt = new Vector<Object[]>();
		ResultSet resultSet;
		Statement qry = dataAccss.execConnectReadOnly();
		int keeper = DataSet.checkInteger(dataSet.getTreeItemToExpand());
		if (keeper == 0)
			return dataSet;

		try {
			resultSet = qry.executeQuery("Select KeeperID, KeeperDesc, CreateDate, KeeperIcon "
							+ " From wkpGroups where PrntID = "
							+ keeper
							+ " And j1 ='[Share-Group/]' ");
			while (resultSet.next()) {
				Object[] row = new Object[10];
				row[0] = new Boolean(false);
				row[1] = Integer.toString(resultSet.getInt("KeeperID"));
				row[2] = resultSet.getString("KeeperDesc");
				row[3] = resultSet.getString("CreateDate");
				row[4] = resultSet.getString("KeeperIcon");
				crypt.addElement(row);
			}
			resultSet.close();

			resultSet = qry.executeQuery("Select a.PasswordID, a.w3, a.v4, a.r9, b.a0, b.b9, b.j1 from wkpPasswordAKin a inner join wkpPasswordCrypt b "
							+ " on a.PasswordID = b.PasswordID Where (a.x2 ='"
							+ dataSet.getScmbl()
							+ "' Or  a.x2 = '"
							+ dataAccss.encrypt(dataSet.getStringField("GroupID"))
							+ "' Or  a.t6 in ( Select cc from wkpBundle Where gg = '"+ dataSet.getScmbl()
								+ "' And hh = '" + dataAccss.encrypt("[Bundle-Access/]") 
								+ "' And ee = '" + dataAccss.encrypt("true") 
							+ "' ) ) And a.u5 = '"
							+ dataAccss.encrypt("" + keeper) + "' ");

			while (resultSet.next()) {
				boolean active = DataSet.checkBoolean(dataAccss.decrypt(resultSet.getString("v4")));
				int exp =  DataSet.checkInteger(DataSet.formatDateField(
											dataAccss.decrypt(resultSet.getString("r9")), "yyyyMMdd")  );
				int today  = DataSet.checkInteger(new SimpleDateFormat("yyyyMMdd").format(new Date()));
				if(exp > 0 && exp < today) active = false;
				if(active) {
				Object[] row = new Object[10];
					row[0] = new Boolean(true);
					row[1] = "0";
					row[2] = "     Web-KeePass Password";
					row[3] = "";
					row[4] = "dot.gif";
					row[5] = resultSet.getString("PasswordID");
					row[6] = resultSet.getString("a0");
					row[7] = resultSet.getString("b9");
					row[8] = resultSet.getString("j1");
					row[9] = resultSet.getString("w3");
					crypt.addElement(row);
				}
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
	public DataSet getBndList(DataSet dataSet, DataAccess dataAccss) {
		try {
			String[] tb = new String[] { "BundleID", "aa", "bb", "cc" };
			String hh = dataAccss.encrypt("[Bundle-Group/]");
			dataSet.putTableVector("Table1", dataAccss.executeVectorQuery(
					"Select BundleID, aa, bb, cc From wkpBundle Where hh = '" + hh + "' " , tb));
		} catch (DBSchemaException e) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public boolean isNewBnd(String bnd,String hh, DataAccess dataAccss) {
		try { ;
			return !dataAccss.executeQuery(
					"Select BundleID From wkpBundle Where BundleID = " + bnd + 
					           " And hh = '" + hh + "' ", "", null);
		} catch (Exception e) {
			return true;
		}
	}

	/**/
	public DataSet updBndList(DataSet dtSet, DataAccess dtAccss) {

		String hh = dtAccss.encrypt("[Bundle-Group/]");
		PreparedStatement insQry = dtAccss.execPreparedConnect(
				"Insert Into wkpBundle (BundleID, aa, bb, cc, hh) Values ( ?,?,?,?, '" + hh +  "' ) ");

		PreparedStatement updQry = dtAccss.execPreparedConnect(
				"Update wkpBundle SET aa = ?, bb = ?, cc = ? Where BundleID = ? And hh = '" + hh + "' ");

		try {
			Enumeration<?> table = dtSet.getTableVector("Table1").elements();
			dtSet.remove("Table1");
			while (table.hasMoreElements()) {
				String[] row = (String[]) table.nextElement();
				if (row[1] != null && !row[1].equals("")) {
					if (isNewBnd(row[0], hh,  dtAccss)) {
						insQry.setInt(1, new Sequences().getNextSequences(
								"wkpBundle", "BundleID", dtAccss));
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

	
	
	public boolean editBnd(String aa,  DataAccess dataAccss) {
		try { 
			return ! dataAccss.executeQuery(
					"Select BundleID From wkpBundle Where aa= '" + aa + 
					"' And hh = '" + dataAccss.encrypt("[Bundle-Group/]") + 
					"' And cc = '" + dataAccss.encrypt("true") + "' ", "", null);
		} catch (Exception e) {
			return true;
		}
	}
	
	
	/**/
	public DataSet delBndList(DataSet dtSet, DataAccess dtAccss) {
		PreparedStatement updQry = dtAccss.execPreparedConnect("delete wkpBundle Where BundleID = ?");
		try {
			Enumeration<?> table = dtSet.getTableVector("Table1").elements();
			dtSet.remove("Table1");
			while (table.hasMoreElements()) {
				String[] row = (String[]) table.nextElement();
				if (row[0] != null && !row[0].equals("")) {
						updQry.setInt(1, DataSet.checkInteger(row[0]));
						updQry.executeUpdate();
				}
			}
			dtSet.addMessage("LIT0006");
		} catch (Exception e) {
			dtAccss.logMessage(" akin -- " + e);
			dtSet.addMessage("SVR0001");
		} finally {
			dtAccss.execClose(updQry);
		}
		return dtSet;
	}

}
