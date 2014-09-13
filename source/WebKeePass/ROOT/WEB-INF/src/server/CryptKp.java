package server;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import webBoltOns.dataContol.DBSchemaException;
import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;

public class CryptKp {

	public CryptKp() {
	}

	/**/
	public DataSet newCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		dataSet.putIntegerField("PrntID", dataSet.getIntegerField("KeeperID"));
		dataSet.putIntegerField("KeeperID", 0);
		dataSet.putStringField("KeeperDesc", "");
		dataSet.putStringField("KeeperIcon", "");
		dataSet.putStringField("CreateDate", "");
		dataSet.putStringField("LastUpdate", "");
		return dataSet;
	}

	/**/
	public DataSet getCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (!dataAccss.executeQuery(
					"Select * From wkpGroups Where KeeperID = "
							+ dataSet.getIntegerField("KeeperID")
							+ " And j1 = '" + dataSet.getScmbl() + "' ",
					"wkpGroups", dataSet));

				
		} catch (DBSchemaException e) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet updCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		if (editCryptKeeper(dataSet, dataAccss))
			if (isNewCryptKeeper(dataSet, dataAccss))
				dataSet = insertCryptKeeper(dataSet, dataAccss);
			else
				dataSet = updateCryptKeeper(dataSet, dataAccss);
		return dataSet;
	}

	private boolean editCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		if(dataSet.getStringField("KeeperID").equals(dataSet.getStringField("PrntID"))) {
				dataSet.addMessage("LIT0007");
				return false;
		}				
		return true;
	}

	public boolean isNewCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		try {
			return !dataAccss.executeQuery(
					"Select KeeperID From wkpGroups Where KeeperID = "
							+ dataSet.getIntegerField("KeeperID")
							+ " And j1 = '" + dataSet.getScmbl() + "' ", "",
					null);
		} catch (Exception exception) {
			return true;
		}
	}

	private DataSet insertCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		try {
			dataSet.putStringField("CreateDate", 
					new SimpleDateFormat("yyyyMMdd").format(new Date()) );
			
			dataSet.putStringField("LastUpdate",  
					new SimpleDateFormat("yyyyMMdd").format(new Date()) );
			dataSet.putStringField("j1", dataSet.getScmbl());
			if (dataSet.getIntegerField("KeeperID") == 0)
				dataSet.putIntegerField("KeeperID", new Sequences()
						.getNextSequences("wkpGroups", "KeeperID", dataAccss));

			dataAccss.executeInsertQuery("wkpGroups", dataSet);
			dataSet.addMessage("Record Added", "10", null, null);
		} catch (Exception exception) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	private DataSet updateCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		try {

			dataSet.putStringField("LastUpdate",
					new SimpleDateFormat("yyyyMMdd").format(new Date()));
			dataSet.putStringField("j1", dataSet.getScmbl());
			dataAccss.executeUpdateQuery("wkpGroups", dataSet,
					"Where KeeperID = " + dataSet.getIntegerField("KeeperID")
							+ " And j1 = '" + dataSet.getScmbl() + "' ");

			dataSet.addMessage("Record Updated", "10", null, null);
		} catch (Exception exception) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet delCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		Statement sql = dataAccss.execConnectUpdate();
		try {
			sql.executeUpdate("Delete from wkpGroups Where KeeperID = "
							+ dataSet.getIntegerField("KeeperID" + " And j1 = '"
									+ dataSet.getScmbl() + "' "));
			dataSet.addMessage("Record Deleted", "10", null, null);
		} catch (Exception exception) {
			dataSet.addMessage(
					"Database Error - Please contact systems administrator",
					"30", null, null);
		} finally {
			dataAccss.execClose(sql);
		}
		return dataSet;
	}

	/**/
	public DataSet nxtCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (!dataAccss.executeQuery(
					"Select * From wkpGroups Where KeeperID > "
							+ dataSet.getIntegerField("KeeperID")
							+ " And j1 = '" + dataSet.getScmbl()
							+ "' Order By KeeperID ", "wkpGroups", dataSet))

				dataSet.addMessage("LIT0002");
		} catch (DBSchemaException e) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet prvCryptKeeper(DataSet dataSet, DataAccess dataAccss) {
		try {
			if (!dataAccss
					.executeQuery("Select * From wkpGroups Where KeeperID < "
							+ dataSet.getIntegerField("KeeperID")
							+ " And j1 = '" + dataSet.getScmbl()
							+ "' Order By KeeperID Desc ", "wkpGroups", dataSet))

				dataSet.addMessage("LIT0003");
		} catch (DBSchemaException e) {
			dataSet.addMessage("SVR0001");
		}
		return dataSet;
	}

	/**/
	public DataSet getwkpGroupsList(DataSet dataSet, DataAccess dataAccss) {
		Vector<String[]> cstmrv1 = new Vector<String[]>();
		String keyField1 = (String) dataSet.get("@GroupID");
		String[] tblCols = (String[]) dataSet.getTableColumnFields("Table1");
		ResultSet resultSet;
		Statement sqlStatement = dataAccss.execConnectReadOnly();

		String sql = "Select " + DataAccess.buildArgumentList(tblCols)
				+ "  From wkpGroups Where j1 = '" + dataSet.getScmbl() + "' ";
		
		sql = DataAccess.addToSearchWhereClause(sql, "GroupID", "CHR", keyField1);

		try {
			resultSet = sqlStatement.executeQuery(sql);
			while (resultSet.next()) {
				String[] row1 = new String[tblCols.length];
				for (int x = 0; x < tblCols.length; x++)
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

}
