package server;

 

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Enumeration;

import webBoltOns.dataContol.DBSchemaException;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;
import webBoltOns.server.UserSecurityManager;

public class Adherent extends UserSecurityManager {

	public Adherent() { }
	
	public DataSet getUser(DataSet dtSet, DataAccess dtAccss) {
		dtSet = super.getUser(dtSet, dtAccss);
		return getAdherentList(dtSet, dtAccss);
		
	}

	public DataSet nxtUser(DataSet dtSet, DataAccess dtAccss) {
		dtSet = super.nxtUser(dtSet, dtAccss);
		return getAdherentList(dtSet, dtAccss);
	}

	
	public DataSet prvUser(DataSet dtSet, DataAccess dtAccss) {
		dtSet = super.prvUser(dtSet, dtAccss);
		return getAdherentList(dtSet, dtAccss);
	}
		
	
	public DataSet deleteUser(DataSet dtSet, DataAccess dtAccss) {
		dtSet = super.deleteUser(dtSet, dtAccss);
		Statement sql = dtAccss.execConnectUpdate();
		try {
			String hh = dtAccss.encrypt("[Bundle-Access/]");
			String gg = dtAccss.encrypt(dtSet.getStringField("UserID"));			 
			sql.executeUpdate(
					"Delete From wkpBundle Where gg = '" + gg + "' And hh = '" + hh + "' ");
 
		} catch (Exception exception) {
			 
		} finally {
			dtAccss.execClose(sql);
		}
		return dtSet;
		 
	}
		
	
	/* */		
	private boolean isValidUser (DataSet dtSet, DataAccess dataAccess) {
		if(isNewUserGroup(dtSet,dataAccess)) {
			dtSet.addMessage("Invalid Group ID","30","GroupID",null);
			return false;
		}	
		Enumeration<?> table = dtSet.getTableVector("Table1").elements();
		while (table.hasMoreElements()) {
			String[] row = (String[]) table.nextElement();
			if (row[0] != null && !row[0].equals("") 
					      && new Akin().editBnd(row[0], dataAccess)) {
				dtSet.addMessage("WKP0003");
				return false;
			}
		}		
		return true;
	}
	
	
	/* */
	public DataSet updUser(DataSet dtSet, DataAccess dtAccss) {
		if( ! isValidUser(dtSet,dtAccss)) return dtSet;
		
		dtSet = super.updUser(dtSet, dtAccss);
		String hh = dtAccss.encrypt("[Bundle-Access/]");
		String gg = dtAccss.encrypt(dtSet.getStringField("UserID"));
		
		PreparedStatement insQry = dtAccss.execPreparedConnect(
				"Insert Into wkpBundle (BundleID, cc, dd, ee, gg, hh) " +
				" Values ( ?,?,?,?,'" + gg + "','" + hh + "') ");
		PreparedStatement updQry = dtAccss.execPreparedConnect(
				"Update wkpBundle SET dd = ?, ee = ? " +
				" Where cc = ? And gg = '" + gg + "' And hh = '" + hh + "' ");

		try {
			Enumeration<?> table = dtSet.getTableVector("Table1").elements();
			dtSet.remove("Table1");
			while (table.hasMoreElements()) {
				String[] row = (String[]) table.nextElement();
				if (row[0] != null && !row[0].equals("")) {
					if (isNewBnd(row[0], gg, hh, dtAccss)) {
						insQry.setInt(1, new Sequences().getNextSequences(
								"wkpBundle", "BundleID", dtAccss));
						insQry.setString(2, row[0]);
						insQry.setString(3, row[1]);
						insQry.setString(4, row[2]);
						insQry.executeUpdate();
					} else {
						updQry.setString(1, row[1]);
						updQry.setString(2, row[2]);
						updQry.setString(3, row[0]);
						updQry.executeUpdate();
					}
				}
			}

			dtSet.addMessage("LIT0004");
		} catch (Exception e) {
			dtAccss.logMessage(" updUser -- " + e);
			dtSet.addMessage("SVR0001");
		} finally {
			dtAccss.execClose(insQry);
			dtAccss.execClose(updQry);
		}
		return dtSet;
	}

	
	
	/* */
	public DataSet deleteAdherent(DataSet dtSet, DataAccess dtAccss) {
		String hh = dtAccss.encrypt("[Bundle-Access/]");
		String gg = dtAccss.encrypt(dtSet.getStringField("UserID"));
		PreparedStatement updQry = dtAccss.execPreparedConnect(
				"Delete from wkpBundle Where cc = ? And gg = '" + gg + "' And hh = '" + hh + "' ");
		try {
			Enumeration<?> table = dtSet.getTableVector("Table1").elements();
			dtSet.remove("Table1");
			while (table.hasMoreElements()) {
				String[] row = (String[]) table.nextElement();
				if (row[0] != null && !row[0].equals("")) {
						updQry.setString(1, row[0]);
						updQry.executeUpdate();
				}
			}

			dtSet.addMessage("LIT0004");
		} catch (Exception e) {
			dtAccss.logMessage(" deleteAdherent -- " + e);
			dtSet.addMessage("SVR0001");
		} finally {
			dtAccss.execClose(updQry);
		}
		return dtSet;
	}

	
	
	/* */
	private boolean isNewBnd(String cc, String gg, String hh, DataAccess dataAccss) {
		try { 
			return !dataAccss.executeQuery(
					"Select BundleID From wkpBundle Where cc = '" + cc + 
					   "' And gg = '" + gg + "' And hh = '" + hh + "' ", "", null);
		} catch (Exception e) {
			return true;
		}
	}

	
	/* */
	public DataSet getAdherentList(DataSet dtSet, DataAccess dtAccss) {
			try {
				String hh = dtAccss.encrypt("[Bundle-Access/]");
				String gg = dtAccss.encrypt(dtSet.getStringField("UserID"));				
				String[] tb = new String[] { "cc", "dd", "ee" };
				dtSet.putTableVector("Table1", dtAccss.executeVectorQuery(
							"Select BundleID, cc, dd, ee From wkpBundle " +
							" Where  gg = '" + gg + "' And hh = '" + hh + "' " , tb));
				} catch (DBSchemaException e) {
					dtAccss.logMessage(" getAdherentList -- " + e);
					dtSet.addMessage("SVR0001");
				}
				return dtSet;
			}
	
  }
