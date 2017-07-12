package com.webkeepass.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import webBoltOns.dataContol.DataAccess;
import webBoltOns.dataContol.DataSet;

import com.sun.xml.tree.XmlDocument;



public class Doc {

	public Doc() { }
	
	
	class XmlDoc extends XmlDocument {
		public  void setAttribute(Element elmnt, String attrName, String attrValue) {
			if(attrValue != null && !attrValue.equals(""))
				elmnt.setAttribute(attrName, attrValue);
		}	 	
	}
	
	
	public DataSet importDoc (DataSet dtSet, DataAccess dtAccss) {
		PreparedStatement qry = null;
		String id = dtAccss.decrypt(dtSet.getStringField("PasswordID"));
		if(id != null && !id.equals("")) {
			try {
				qry = dtAccss.execPreparedConnect(
				    "Insert into documents (DocumentID, DocumentKey, DocumentFile, DocumentPath, DateAdded, UserID) Values (?, ?, ?, ?, ?, ?)");
				String key = "wp" + dtSet.getUser() + id;
				String path = dtAccss.getDocPath() + key + "_" + dtSet.getStringField("[Import_File/]"); 

				qry.setInt(1, new Sequences().getNextSequences("documents", "DocumentID", dtAccss));		
				qry.setString(2, key);
				qry.setString(3, dtSet.getStringField("[Import_File/]"));
				qry.setString(4, path);
				qry.setString(5, new SimpleDateFormat("yyyyMMdd").format(new Date()));
				qry.setString(6, dtSet.getUser());		
				qry.executeUpdate();

				File file = new File(path);
				FileOutputStream out = new FileOutputStream(file); 
				out.write(dtSet.getStreamArray("[Import_Data/]"));
				out.close();

			}  catch (Exception e) {
				dtAccss.logMessage(" importDoc -- " + e);
				dtSet.addMessage("SVR0001");
			} finally {
				dtAccss.execClose(qry);
			}
		}

		return dtSet;	
	}


	
	public DataSet getDocList (DataSet dtSet, DataAccess dtAccss) {
		String key = "wp" + dtSet.getUser() +  dtAccss.decrypt(dtSet.getStringField("PasswordID"));
		
		Vector<String[]> cstmrv1 = new Vector<String[]>();
	    ResultSet rs;
	    Statement qry = dtAccss.execConnectReadOnly();;
	    String sql = "Select * from documents where DocumentKey = '" + key + "' ";
	    
	    try {
	      rs = qry.executeQuery(sql);
	      while (rs.next()) {
	    	File file = new File(rs.getString("DocumentPath"));   
	    	if(file.isFile()) {  
	    		String[] tabelRow1 = new String[5];
	    		tabelRow1[0] = "Pdf.gif";
	    		tabelRow1[1] = Integer.toString(rs.getInt("DocumentID"));
	    		tabelRow1[2] = rs.getString("DocumentFile");
	    		tabelRow1[3] = rs.getString("DocumentDesc");
	    		tabelRow1[4] = rs.getString("DateAdded");
	    		cstmrv1.addElement(tabelRow1);
	    	}
	      }
	      rs.close();
	      dtSet.put("DocMngr", cstmrv1);

	    } catch (Exception e) {
	         dtAccss.logMessage(" getDocList -- " + e);
	         dtSet.addMessage("SVR0001");
	    } finally {
	      	dtAccss.execClose(qry);
	    }
	  return dtSet;	
	}
	
	
	public DataSet updDocList (DataSet dtSet, DataAccess dtAccss) {
		PreparedStatement qry = null;
		try {
			qry = dtAccss.execPreparedConnect("Update documents Set DocumentDesc = ? Where DocumentID = ?");
			Enumeration<?> expRow = dtSet.getTableVector("DocMngr").elements();
			while (expRow.hasMoreElements()) {
				String[] rw = (String[]) expRow.nextElement();
				qry.setString(1, rw[3]);
				qry.setString(2,rw[1]);
				qry.executeUpdate();
			}
 	
		}  catch (Exception e) {
		        dtAccss.logMessage(" updDocList -- " + e);
		        dtSet.addMessage("SVR0001");
	     } finally {
		     	dtAccss.execClose(qry);
	    }
		
	   return dtSet;	
	}

	
	
//---------------------------------------------------------------------------------------------------	
 	
	public DataSet exportXML( DataSet exprt, DataAccess dataAccess) {
		ResultSet rs;
		Statement qry = dataAccess.execConnectReadOnly();
		try {
			XmlDoc xml = new XmlDoc();
			Element root = xml.createElement("pwlist");
			rs = qry.executeQuery("Select * from wkpPasswordCrypt Where j1 = '" + exprt.getScmbl() + "' ");
			while (rs.next()) {
				Element pwentry = xml.createElement("pwentry");

				Element group = xml.createElement("group");
				group.setAttribute("tree", "General");
				group.appendChild(xml.createTextNode("Web-KeePass"));
				pwentry.appendChild(group);

				Element e = xml.createElement("title");
				e.appendChild(xml.createTextNode(dataAccess.decrypt(rs.getString("PasswordID"))) );
				pwentry.appendChild(e);

			    e = xml.createElement("username");
				e.appendChild(xml.createTextNode(dataAccess.decrypt(rs.getString("b9"))) );
				pwentry.appendChild(e);

			    e = xml.createElement("url");
				e.appendChild(xml.createTextNode(dataAccess.decrypt(rs.getString("n7"))) );
				pwentry.appendChild(e);
	 
			    e = xml.createElement("password");
				e.appendChild(xml.createTextNode(dataAccess.decrypt(rs.getString("c8"))) );
				pwentry.appendChild(e);				
				root.appendChild(pwentry);
				
			    e = xml.createElement("notes");
				e.appendChild(xml.createTextNode(
						dataAccess.decrypt(rs.getString("a0")) + "\n " +
						dataAccess.decrypt(rs.getString("o6")) ));
				pwentry.appendChild(e);				
				root.appendChild(pwentry);

			    e = xml.createElement("creationtime");
				e.appendChild(xml.createTextNode(
						dataAccess.decrypt(rs.getString("d7")) + "T01:00:00"));
				pwentry.appendChild(e);				
	 						
				root.appendChild(pwentry);
			}

			rs.close();
			xml.appendChild(root);
	
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			xml.write(o);
			exprt.putStreamArray("exportPath", o.toByteArray());
			exprt.addMessage("LIT0008");

		} catch (Exception e) {
			dataAccess.logMessage(" exportXML -- " + e);
			exprt.addMessage("SVR0001");

		} finally {
			dataAccess.execClose(qry);
		}
		 
		exprt.putStringField("importPath","");
		return exprt;
	}
	
	

	public DataSet importXML(DataSet imprt, DataAccess dataAccess) {

		Crypt crypt = new Crypt();
		try {
			ByteArrayInputStream buf = new ByteArrayInputStream(imprt.getStreamArray("importPath"));
			if(buf != null) {
				DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
				dBF.setIgnoringComments(true);  
				DocumentBuilder builder =  dBF.newDocumentBuilder();
				Document doc =  builder.parse(buf);
				Element root = doc.getDocumentElement();

				NodeList pwentry = root.getElementsByTagName("pwentry");	

				for (int i = 0; i < pwentry.getLength(); i++) {

					Element e = (Element) pwentry.item(i);
					String s;

					NodeList n = e.getElementsByTagName("title");
					if(n.item(0).hasChildNodes()) {
						s = n.item(0).getFirstChild().getNodeValue();
						imprt.putStringField("PasswordID", dataAccess.encrypt(s));
					}

					n = e.getElementsByTagName("username");
					if(n.item(0).hasChildNodes()) {
						s = n.item(0).getFirstChild().getNodeValue();
						imprt.putStringField("b9", dataAccess.encrypt(s));
					}

					n = e.getElementsByTagName("url");
					if(n.item(0).hasChildNodes()) {
						s = n.item(0).getFirstChild().getNodeValue();
						imprt.putStringField("n7", dataAccess.encrypt(s));
					}

					n = e.getElementsByTagName("password");
					if(n.item(0).hasChildNodes()) {
						s = n.item(0).getFirstChild().getNodeValue();
						imprt.putStringField("c8", dataAccess.encrypt(s));
					}

					crypt.updCrypt(imprt, dataAccess);
				}				
			}
		} catch (Exception e) {
			imprt.put("SEditor", null);
			imprt.addMessage("LIT0010");
		}
		imprt.putStringField("exportPath","");
		return imprt;
	}



}
