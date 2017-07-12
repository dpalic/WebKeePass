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

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.clientUtil.AutoType;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowParm;
import webBoltOns.dataContol.DataSet;

public class CMacroEditor extends JTextField 
				implements StandardComponentLayout, ActionListener {
	 
	 
	private static final long serialVersionUID = -3343059845815975451L;
	private final static String CpyMacro = "[CpyMacro/]";
	private final static String ClrMacro = "[ClrMacro/]";

	private final static String Delay = "[Delay-";
	private final static String Tab   = "[Tab  ]";
	private final static String TabBc = "[TabBc]";
	private final static String Home  = "[Home ]";
	private final static String Enter = "[Enter]";
	
	private final static String GoWeb = "[GoWeb]";
	private final static String GoAlt = "[GoAlt]";
	private final static String GoPgm = "[GoPgm]";


	private final static String Password = "[Password]";
	private final static String UserId = "[UserId]";
	private final static String PasswordId = "[PasswordId]";
	private final static String EmailId = "[Email]";

	private JButton play;
	private JButton clear;
	private JLabel playing;
	
	protected AppletConnector cnct;
	protected String commitValue;
	protected WindowItem comp;

	private JPanel fieldPanel;
	protected WindowFrame mFrm;
	protected JLabel textBoxLabel;
 

	public CMacroEditor() {}
 
	
	public void actionPerformed(ActionEvent ae) {

		  if(ae.getActionCommand().equals(CpyMacro)) {
			commitEditing(new String (getText()));
	
			play.setEnabled(false);
			clear.setEnabled(false);
			playing.setVisible(true);
			mFrm.getMenuObject().displayRobot(true);
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
 
					AutoType rr = new AutoType();				
					rr.autoTypeRobort(commitValue, mFrm,
										mFrm.getString("b9"), 
										mFrm.getString("c8"),
										mFrm.getString("PasswordID"),  
										mFrm.getString("m8"),
										mFrm.getString("n7"),
										mFrm.getString("i2"),
										mFrm.getString("p5"));
					 
					playing.setVisible(false);
					play.setEnabled(true);	 
					clear.setEnabled(true);
					mFrm.getMenuObject().displayRobot(false);
				}});
			

		 } else if(ae.getActionCommand().equals(ClrMacro)) {
			 setText("");
			 commitEditing("");
		 } else  {
			 commitEditing(new String (getText()) + ae.getActionCommand());
		 }	

	}
	

	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		mFrm = mainFrame;
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		fieldPanel = new JPanel();
		fieldPanel.setLayout(new BorderLayout());
		fieldPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(165, 165, 165)));
		
		setName(Integer.toString(comp.getObjectHL()));
		setFont(cnct.standardFont);
		setBackground(Color.WHITE);
		setEditable(true);
		
		textBoxLabel = new JLabel(comp.getDescription());
		textBoxLabel.setFont(cnct.headerFont);

		//setHorizontalAlignment(comp.getCellAlignment());
		setColumns(comp.getLength());
		setSelectionColor(Color.cyan);
		setFont(cnct.standardFont);
		textBoxLabel.setFont(cnct.headerFont);
		comp.setComponentObject(this);
		fieldPanel.add(this, BorderLayout.CENTER);
		
		JToolBar tb = new JToolBar();
		tb.setBorder(BorderFactory.createLoweredBevelBorder());
		
		clear = cnct.buildFancyButton(cnct.getMsgText("TXT0131"), "clip.gif", cnct.getMsgText("TXT0131"), 'c' );
		clear.setActionCommand(ClrMacro);
		clear.addActionListener(this);
		tb.add(clear, null);
		tb.add(new JToolBar.Separator());

		
		play  = cnct.buildFancyButton(cnct.getMsgText("TXT0132"), "clip.gif", cnct.getMsgText("TXT0132"), 'r' );
		play.setActionCommand(CpyMacro);
		play.addActionListener(this);
		tb.add(play, null);
		tb.add(new JToolBar.Separator());		
		fieldPanel.add(tb, BorderLayout.NORTH);
		
		playing = new JLabel(cnct.getImageIcon("run.jpg"));
		playing.setVisible(false);
		tb.add(playing, null);
		tb.setPreferredSize(new Dimension(180, 26));

		JPanel p1 = new JPanel(new GridFlowLayout());
		JButton pb;
				
		//0
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0133"), "ePreview.gif", cnct.getMsgText("TXT0133"), ' ');
		pb.setActionCommand(UserId);
		pb.addActionListener(this);
		p1.add(pb, new GridFlowParm(true, 0));
		//1
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0137"), "iNext.gif", cnct.getMsgText("TXT0137"), ' ');
		pb.setActionCommand(Tab);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 1));
		//2
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0138"), "Mdot.gif", cnct.getMsgText("TXT0137"), ' ');
		pb.setActionCommand(Delay + "02]");
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 2));
		//3
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0143"), "script.gif", cnct.getMsgText("TXT0143"), ' ');
		pb.setActionCommand(GoWeb);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 3));

				
		//0
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0134"), "ePreview.gif", cnct.getMsgText("TXT0134"), ' ');
		pb.setActionCommand(PasswordId);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(true, 0));
		//1
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0146"), "iNext.gif", cnct.getMsgText("TXT0146"), ' ');
		pb.setActionCommand(TabBc);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 1));
		//2
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0139"), "Mdot.gif", cnct.getMsgText("TXT0139"), ' ');
		pb.setActionCommand(Delay + "05]");
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 2));
		//3
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0145"), "script.gif", cnct.getMsgText("TXT0145"), ' ');
		pb.setActionCommand(GoPgm);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 3));

		
		//0
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0135"), "ePreview.gif", cnct.getMsgText("TXT0135"), ' ');
		pb.setActionCommand(Password);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(true, 0));
		//1
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0142"), "iNext.gif", cnct.getMsgText("TXT0142"), ' ');
		pb.setActionCommand(Enter);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 1));
		//2
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0140"), "Mdot.gif", cnct.getMsgText("TXT0140"), ' ');
		pb.setActionCommand(Delay + "10]");
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 2));
		//3
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0144"), "script.gif",cnct.getMsgText("TXT0144") , ' ');
		pb.setActionCommand(GoAlt);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 3));
		
		//0
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0136"), "ePreview.gif", cnct.getMsgText("TXT0136"), ' ');
		pb.setActionCommand(EmailId);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(true, 0));
 		//1
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0147"), "iNext.gif", cnct.getMsgText("TXT0147"), ' ');
		pb.setActionCommand(Home);
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 1));
		//2
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0141"), "Mdot.gif", cnct.getMsgText("TXT0141"), ' ');
		pb.setActionCommand(Delay + "20]");
		pb.addActionListener(this);
		p1.add(pb,new GridFlowParm(false, 2));
		//3

	
		 
		fieldPanel.add(p1, BorderLayout.SOUTH);
		
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
			((CPanelContainer) parentItem.getComponentObject()).addc(playing);
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
	public void mouseEntered(MouseEvent arg0) {}
 

	
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
	
	public void setProperty(String propertyName, String propertyValue) {} 
	public void setValid(boolean invalid) { }
	public boolean validateComponent(String action, String editorName) {return true; }

}
