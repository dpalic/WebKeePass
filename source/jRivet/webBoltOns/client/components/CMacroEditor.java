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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.clientUtil.PasswordGntr;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CMacroEditor extends JTextField implements StandardComponentLayout,
                                     ActionListener, KeyListener {
	 
	 
	private static final long serialVersionUID = -3343059845815975451L;
	private final static String CpyMacro = "[CpyMacro/]";
	private final static String ShwMacro = "[ShwMacro/]"; 
	 
	protected AppletConnector cnct;
	protected String commitValue;
	protected WindowItem comp;
	 
	
	private JPanel fieldPanel;
	protected JButton shwMc, cpyMc;
	protected WindowFrame mFrm;
	protected JLabel textBoxLabel;
	private JPopupMenu popup;
	
	public CMacroEditor() {}
	
	
	public void actionPerformed(ActionEvent ae) {
	  
		  if(ae.getActionCommand().equals(CpyMacro)) {		
			commitEditing(new String (getText()));
			mFrm.getMenuObject().copyToClipTimer(commitValue);
		 } else if(ae.getActionCommand().equals(ShwMacro)) {	
			 popup.show(shwMc, 5 , shwMc.getHeight());
		 } else  {
			 commitEditing(new String (getText()) + ae.getActionCommand()  );
			 popup.setVisible(false);
		 }	
	}
	

	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		mFrm = mainFrame;
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		fieldPanel = new JPanel();
		fieldPanel.setLayout(new BorderLayout());
		fieldPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(165, 163, 151)));

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
		fieldPanel.add(this, BorderLayout.WEST);
		
		shwMc = cnct.buildFancyButton(null, "clip.gif", "Macro Fields", 'm' );
		shwMc.setActionCommand(ShwMacro);
		shwMc.addActionListener(this);
		fieldPanel.add(shwMc, BorderLayout.CENTER);
		
		cpyMc = cnct.buildFancyButton(null, "clip.gif", "Copy/Execute", 'm' );
		cpyMc.setActionCommand(CpyMacro);
		cpyMc.addActionListener(this);
		fieldPanel.add(cpyMc, BorderLayout.EAST);
 
		popup = new JPopupMenu();
		
		JButton pb;
		pb = cnct.buildFancyButton(" Add User Id", "clip.gif", "User Name", ' ');
		pb.setActionCommand("[UserID]");
		pb.addActionListener(this);
		popup.add(pb);
		
		pb = cnct.buildFancyButton(" Add Password", "clip.gif", "Password", ' ');
		pb.setActionCommand("[PW]");
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(" Tab ->", "clip.gif", "Tab ->", ' ');
		pb.setActionCommand("[TB->]");
		pb.addActionListener(this);
		popup.add(pb);
		
		pb = cnct.buildFancyButton("  <- Back Tab", "clip.gif", "<- Back Tab", ' ');
		pb.setActionCommand("[<-TB]");
		pb.addActionListener(this);
		popup.add(pb);
		
		pb = cnct.buildFancyButton(" Enter Key", "clip.gif", "Enter Key", ' ');
		pb.setActionCommand("[TB->]");
		pb.addActionListener(this);
		popup.add(pb);

		addKeyListener(this);
		
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(textBoxLabel, 4);
			((CPanelContainer) parentItem.getComponentObject()).addRight(fieldPanel, 5);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(textBoxLabel, 2);
			((CPanelContainer) parentItem.getComponentObject()).addRight(fieldPanel, 3);
		} else if (comp.getPosition().equals(WindowItem.LEFT)) {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(textBoxLabel, 0);
			((CPanelContainer) parentItem.getComponentObject()).addRight(fieldPanel, 1);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addc(textBoxLabel);
			((CPanelContainer) parentItem.getComponentObject()).addc(fieldPanel);
		}
	}

	public void clearComponent(String defaultValue) {
		commitEditing(null);
		updateUI();
	}
	
	protected void commitEditing(String value) {
		if (value != null)
			setText(value);
		else
			setText(null);
		
		commitValue = new String (getText());		
		 
	}

	
	public String getSelectedComponentItem() { return null; }
 
	public void initializeComponentUI() {}
	

	public boolean locateCursor() {return false; }

	
	public void populateComponent(String action, String editorName, DataSet dataSet) {
		commitEditing(cnct.decrypt(dataSet.getStringField(editorName)));
	}


	public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {
		commitEditing(new String (getText()));		
		dataSet.putStringField(editorName, cnct.encrypt(commitValue));
		return dataSet;
	}

	
	public String getString () {
		commitEditing(new String (getText()));		
		return commitValue;
	}

	protected void revertEditing() {
		if (commitValue != null)
			setText(commitValue);
		else
			setText(null);
	}
	
	public void keyReleased(KeyEvent arg0) {}
	public void setProperty(String propertyName, String propertyValue) {} 
	public void setValid(boolean invalid) { }
	public boolean validateComponent(String action, String editorName) {return true; }
	public void keyPressed(KeyEvent arg0) { }
	public void keyTyped(KeyEvent arg0) { }
	 
}
