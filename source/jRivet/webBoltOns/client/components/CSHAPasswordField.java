package webBoltOns.client.components;

/*
 * $Id: CPasswordField.java,v 1.1 2007/04/20 19:37:20 paujones2005 Exp $ $Name:  $
 *
 * Copyright  2004, 2005, 2006  www.jrivet.com
 * 
 *   @author P. Jones  
 * 	 @version 2.060719
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'jRivet Framework - Java Solutions for Enterprise Applications'.
 *
 * The Initial Developer of the Original Code is Paul Jones. Portions created by
 *  the Initial Developer are Copyright (C) 2004, 2005, 2006  by Paul Jones.
 *
 *  **All Rights Reserved **.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the "GNU LIBRARY GENERAL PUBLIC LICENSE"), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 *
 * http://www.jRivet.com/download/
 */

import java.awt.Color;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CSHAPasswordField extends JPasswordField implements
		StandardComponentLayout {

	private static final long serialVersionUID = -3343059845815975451L;
	protected Hashtable keys;
	protected JLabel textBoxLabel;
	protected WindowItem comp;
	protected WindowFrame mFrm;
	protected AppletConnector cnct;
	 
	
	public CSHAPasswordField() {}

	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		mFrm = mainFrame;
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		keys = new Hashtable();
		setName(Integer.toString(comp.getObjectHL()));
		setFont(cnct.standardFont);
		setBackground(Color.WHITE);
		
		textBoxLabel = new JLabel(comp.getDescription());
		textBoxLabel.setFont(cnct.headerFont);

		setHorizontalAlignment(comp.getCellAlignment());
		setColumns(comp.getLength());
		setSelectionColor(Color.cyan);
		setFont(cnct.standardFont);
		textBoxLabel.setFont(cnct.headerFont);
		comp.setComponentObject(this);
		
		if(comp.isHidden())
			setEchoChar('-');
		else 
			setEchoChar((char)0);

		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(textBoxLabel, 4);
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 5);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(textBoxLabel, 2);
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 3);
		} else if (comp.getPosition().equals(WindowItem.LEFT)) {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(textBoxLabel, 0);
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 1);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addc(textBoxLabel);
			((CPanelContainer) parentItem.getComponentObject()).addc(this);
		}
	}
	


	public DataSet populateDataSet(String action, String editorName,
			DataSet dataSet) {
			try {
				dataSet.putStringField(editorName,  sha1(new String(getPassword())));
			} catch (NoSuchAlgorithmException e) {
				dataSet.putStringField(editorName,  null);
			}

		return dataSet;
	}

	
	public void populateComponent(String action, String editorName, DataSet dataSet) {
		setText(null);	
	}

	public void clearComponent(String defaultValue) {
		setText(null);	
	}
 
	public void initializeComponentUI() {}
	public String getString() {return null;}
	public String getSelectedComponentItem() { return null; }
	public boolean validateComponent(String action, String editorName) {return true; }
	public boolean locateCursor() {return false; }
	public void setProperty(String propertyName, String propertyValue) {}
	public void setValid(boolean invalid) {}
	  
	public static String sha1(String data) throws NoSuchAlgorithmException {
		    MessageDigest md = MessageDigest.getInstance("SHA");
		    String hex = "";
		    md.update(data.getBytes());
		    byte[] digest = md.digest();
		    for (int i=0; i<digest.length; i++) {
		      String h = Integer.toHexString(digest[i]);
		      if (h.length() == 1) h = "0" + h;
		      h = h.substring(h.length()-2);
		      hex += h;
		    }
		    return hex;
	}
}
