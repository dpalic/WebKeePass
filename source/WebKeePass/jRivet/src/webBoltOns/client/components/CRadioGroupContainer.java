package webBoltOns.client.components;

/*
 * $Id: CRadioGroupContainer.java,v 1.1 2007/04/20 19:37:20 paujones2005 Exp $ $Name:  $
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
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowParm;
import webBoltOns.dataContol.DataSet;

public class CRadioGroupContainer extends JPanel implements
		StandardComponentLayout {

	private static final long serialVersionUID = -6417739072856852776L;
	private ButtonGroup buttongroup;
	private ButtonModel top = null;
	private Hashtable<String, ButtonModel> model;
	private	WindowItem comp;
	private AppletConnector cnct;
	private GridFlowLayout l = new GridFlowLayout(1, 1);
 

	public CRadioGroupContainer() {}

	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		
		setDoubleBuffered(true);
		buttongroup = new ButtonGroup();
		setLayout(l);
		Border border = BorderFactory.createLineBorder(new Color(0, 0, 150), 1);
		TitledBorder titledBorder = new TitledBorder(border, thisItem.getDescription());
		titledBorder.setTitleFont(cnct.standardFont);
		titledBorder.setTitleColor(new Color(0, 0, 150));
		setBorder(titledBorder);
		model = new Hashtable<String, ButtonModel>();
		
		setName(Integer.toString(comp.getObjectHL()));
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					this, 4);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					this, 2);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(
					this, 0);
		}
	}

	public void addRadioButton(WindowItem wi) {
	
		JRadioButton button = new JRadioButton(wi.getDescription());
		String description = wi.getDescription();
		String key = wi.getDefaultValue();
		
		if (key == null || key.equals(""))
			key = description;
		
		button.setText(description);
		button.setFont(cnct.standardFont);
		button.setActionCommand(key);
		model.put(key, button.getModel());

		buttongroup.add(button);
		super.add(button,  new GridFlowParm(true, 0));
		if (top == null) {
			top = button.getModel();

		}
	}

	public String getSelectedButton() {
		return buttongroup.getSelection().getActionCommand();
	}

	public void setSelectedButton(String value) {
		if (value != null) {
			buttongroup.setSelected((ButtonModel) model.get(value), true);
		}
	}

	public DataSet populateDataSet(String action, String editorName,
			DataSet dataSet) {
		dataSet.putStringField(editorName, getSelectedButton());
		return dataSet;
	}

	public String getSelectedComponentItem() {
		return buttongroup.getSelection().getActionCommand();
	}

	public void populateComponent(String action, String editorName,	DataSet dataSet) {
		String value = dataSet.getStringField(editorName);
		if (value != null) {
			buttongroup.setSelected((ButtonModel) model.get(value), true);
		}
	}

	public void clearComponent(String defaultValue) {
		buttongroup.setSelected(top, true);
	}

	public void initializeComponentUI() {}
	public boolean validateComponent(String action, String editorName) {return true;}
	public boolean locateCursor() {return false;}
	public String getString() {return null;}
	public void setValid(boolean invalid) {}
	public void setProperty(String propertyName, String propertyValue) {}

}
