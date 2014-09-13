package webBoltOns.client;

/*
 * $Id: WindowFrame.java,v 1.1 2007/04/20 19:37:14 paujones2005 Exp $ $Name:  $
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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import webBoltOns.AppletConnector;
import webBoltOns.client.MenuFrame.CTabbedControlPane;
import webBoltOns.client.components.CButton;
import webBoltOns.client.components.CComboBoxField;
import webBoltOns.client.components.CDialog;
import webBoltOns.client.components.CListBoxField;
import webBoltOns.client.components.CPanelContainer;
import webBoltOns.client.components.CRadioGroupContainer;
import webBoltOns.client.components.CSpinnerField;
import webBoltOns.client.components.CTableContainer;
import webBoltOns.client.components.CTextBoxField;
import webBoltOns.client.components.CTreeTableContainer;
import webBoltOns.client.components.componentRules.ComManager;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayoutParameter;
import webBoltOns.client.components.layoutManagers.StackedFlowLayout;
import webBoltOns.dataContol.DataSet;

public class WindowFrame implements ClipboardOwner, ComManager, ActionListener {

	private AppletConnector cntr = null;
	private WindowFrame calngFrm = null;
	private String clsNme, clrMthd, fndMthd, expMthd,
			edtMthd, delMthd, prvMthd, nxtMthd, prtMthd,
			initMthd, pstMthd, mreMthd, linMthd, delLinMthd, 
			pLinMthd, prvwMthd, btchMthd, rptXML, rtnField;

	private JButton clear = null, find = null, edit = null, delete = null, post = null, 
			prev = null, next = null, expand = null, print = null, deleteline = null, postline = null,
			more = null, newline = null, btchThrd = null, preview = null, 
			report = null, info = null, dock = null, exMsg = null;

	private Clipboard clpbrd;
	private DataSet dSet;
	private MenuFrame mMnu;
	private JScrollPane mnPn, msgPn;
	private JPanel msgPnl, botPnl, topPnl, mPnl;
	private Vector<String> mnncs = new Vector<String>();
	private WindowItem cmpntTree[];
	private String scrptNme, scrnTtle;
	private SecurityManager security;
	private boolean srvtips = false, atRfrsh = false;
	private JSplitPane mainSpltPn;
	private JFrame mFrm;
	private int height=200, width= 200; 
	protected int totObj = 0, rtFldLvl = 0, msgGap = 65, itmAcsLvl = 1;
	private JDialog infoWndw;
	private JCheckBox tips; 
	public boolean lfEnable = true;
	
	
	/**
	 * <h2><code>WindowFrame</code></h2>
	 * @return String - Menu quick link flag 
	 */
	public WindowFrame(AppletConnector a) {
		 cntr = a;
	}

	
	/**
	 * <h2><code>actionButtonPerformed</code></h2>
	 * @param	int hl	 
	 *
	 * <p> called when a user buttons is selected 
	 */	
	private void actionButtonPerformed(int hl, ActionEvent e) {
		// -   Web Document
		if (cmpntTree[hl].getMethod().equals(WindowItem.GET_URL)) {
			actionWebDoc(hl);
		// -   Email Document
		} else if (cmpntTree[hl].getMethod().equals(WindowItem.GET_EMAIL)) {
			actionEmailDoc(hl);
			// -   Email Document
		} else if (cmpntTree[hl].getMethod().equals(WindowItem.CLIP_FIELD)) {
			actionTempClip(hl);
			
		} else if (cmpntTree[hl].getMethod().equals(WindowItem.NEW_LINE)) {
			actionNewTableLine();
			
		// -   Script Link
		} else if (!cmpntTree[hl].getLink().equals("")) {
			DataSet scrnSt = new DataSet();
			scrnSt.putStringField("[SCREEN_SCRIPT/]", cmpntTree[hl].getLink());
			scrnSt.putIntegerField("[ScriptAccessLevel/]", cmpntTree[hl].getUserAccessLevel());
			final WindowFrame aa = new WindowFrame(cntr);
			scrnSt = aa.setWindow(scrnSt, this, "", 0);
			aa.showWindow();
			scrnSt = getSelectedTableRows(scrnSt);
			scrnSt = getSelectedTextFields(scrnSt);
			aa.startWindowFrame(scrnSt, cmpntTree[hl].getMethod());
			mFrm.addWindowListener(new WindowListener(){
				public void windowActivated(WindowEvent arg0) {}
				public void windowClosed(WindowEvent arg0) {}
				public void windowClosing(WindowEvent arg0) {
					if(aa.mFrm != null){
						aa.mFrm.dispatchEvent(arg0);
						aa.mFrm.dispose();
						aa.mFrm = null;
				 	}
				}
				public void windowDeactivated(WindowEvent arg0) {}
				public void windowDeiconified(WindowEvent arg0) {}
				public void windowIconified(WindowEvent arg0) {}
				public void windowOpened(WindowEvent arg0) {}});
			
			
		// -  webBoltOnsServer Method
		} else if (!cmpntTree[hl].getMethod().equals("")) {
			
			if(cmpntTree[hl].getMethodClass().equals(""))
				actionPerformedLazy(clsNme,cmpntTree[hl].getMethod(), e);
			else	
				actionPerformedLazy(cmpntTree[hl].getMethodClass(),cmpntTree[hl].getMethod(), e);
		}

		if(e != null)
			((JComponent)e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * <h2><code>actionClear</code></h2>
	 * 
	 * @param String clrMthd
	 * @param String action
	 * 
	 * <p>clear action - revove all field content</p>
	 */
	private void actionClear() {
		
		msgPnl.removeAll();
		msgPnl.setBackground(cntr.bgColor);
		
		for (int x = 0; x <= totObj; x++) {
			if (cmpntTree[x].getComponentObject() != null && cmpntTree[x].getComponentObject() 
					                         instanceof StandardComponentLayout)
				((StandardComponentLayout) cmpntTree[x]
						.getComponentObject()).clearComponent(cmpntTree[x].getDefaultValue());
		}
		
		
		if (clrMthd != null && !clrMthd.equals("")) {
			dSet.put(WindowItem.METHOD, clrMthd);
			dSet.put(WindowItem.ACTION, WindowItem.CLEAR);
			postServerRequestImed();
		}
		
		if(clear != null) clear.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * <h2><code>actionCommit</code></h2>
	 * 
 	 * 	
	 * get any parms passed back fom a child frame
	 *  
	 */
	public void actionCommit(int clevel) {
		String cMethod = cmpntTree[clevel].getCommitMethod();
		String cClass = cmpntTree[clevel].getCommitClass();				

		if (lfEnable && !cMethod.equals("")) {
			sendWindowToTable(WindowItem.STD_COMMIT);
			dSet.putStringField(WindowItem.METHOD, cMethod);
			if(!cClass.equals(""))
				dSet.putStringField(WindowItem.CLASSNAME, cClass);	
			
			dSet.putStringField(WindowItem.ACTION,  WindowItem.STD_COMMIT);
			postServerRequestImed();
			sendTableToWindow(WindowItem.STD_COMMIT);
		}			
		lfEnable = true;
	}
	
	
	 
	public DataSet actionCompRequest(DataSet componentData) {
		cntr.showWebStatus("Opening webBoltOnsServer.ServletConnetor");
		componentData.putStringField(WindowItem.CLASSNAME, clsNme);
		componentData = cntr.postServerRequestImed(componentData);
		cntr.showWebStatus("Done");
		return componentData;
	}
	
	/**
	 * <h2><code>actionEmailDoc</code></h2>
	 *
	 * <p> display an email container in a new frame </p>
	 *  
	 */
	private void actionEmailDoc(int hl) {
		if (!cmpntTree[hl].getLink().equals("")) {
			cntr.showWebDocument(cmpntTree[hl].getLink());
		
		} else {
			for (int c = 0; c < cmpntTree.length; c++) {
				if (cmpntTree[c] == null || cmpntTree[c].getFieldName() == null)  
					continue;
				 
				if (cmpntTree[c].getFieldName().equals(cmpntTree[hl].getFieldName()) ){
					cntr.showWebDocument("mailto:" + ((StandardComponentLayout) cmpntTree[c].getComponentObject()).getString());
					c = cmpntTree.length;
				}
			}
		}
	}

	
	
	private void actionTempClip(int hl) {
		for (int c = 0; c < cmpntTree.length; c++) {
			if (cmpntTree[c] == null || cmpntTree[c].getFieldName() == null ||
					cmpntTree[c].getComponentObject() == null)  continue;
				
			if (cmpntTree[c].getFieldName().equals(cmpntTree[hl].getFieldName())) {
				mMnu.copyToClipTimer( ((StandardComponentLayout) cmpntTree[c].getComponentObject()).getString());
					c = cmpntTree.length;
				}
			}
		
		}
	
	
	
	
	/**
	 * <h2><code>fireComponentServerRequest</code></h2>
	 *
	 */	
	public DataSet actionItemRequest(DataSet componentData) {
		cntr.showWebStatus("Opening webBoltOnsServer.ServletConnetor");
		componentData.putStringField(WindowItem.CLASSNAME, clsNme);
		componentData = cntr.postServerRequestImed(componentData);
		cntr.showWebStatus("Done");
		return componentData;
	}

	
	
	private void actionNewTableLine() {
		String object, field;
		if (linMthd != null && !linMthd.equals("")) {
			sendWindowToTable(WindowItem.NEWLINE);
			dSet.put(WindowItem.METHOD, linMthd);
			dSet.put(WindowItem.ACTION, WindowItem.NEWLINE);
			dSet.putStringField(WindowItem.CLASSNAME, clsNme);
			postServerRequestImed();
		}

		boolean reqFcs = true;
		for (int x = 0; x <= totObj; x++) {
			object = cmpntTree[x].getObjectName();
			field = cmpntTree[x].getFieldName();
			if (object != null) {
				if (object.equals(WindowItem.TABLE_OBJECT)  && !cmpntTree[x].isProtected()) {
					if (linMthd != null && !linMthd.equals("")) {
						reqFcs = ((CTableContainer) cmpntTree[x].getComponentObject()).setMoreTableData(
								(Vector) dSet.get(field.toString()),reqFcs);
					} else {
						reqFcs = ((CTableContainer) cmpntTree[x].getComponentObject()).setEmptyLine(reqFcs);
					}
				}
			}
		}
		
		if(newline != null) newline.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}


	
	
	public void actionPerformed(ActionEvent e) {
			JComponent sce = ((JComponent)e.getSource()); 
			sce.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			String cmd = e.getActionCommand();
			CDialog optCfm = new CDialog(getWindowFrame(), cntr);
			
			if(DataSet.checkInteger(cmd) > 0 ) {
				if(validFrm(sce)) 
					actionButtonPerformed(DataSet.checkInteger(cmd), e);		
				sce.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			} else if (cmd.equals("*about*")) {		
				mMnu.displayAdminConsole();
				infoWndw.dispose();
				sce.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			} else if (cmd.equals("*edit*")) {						
				loadAdminScript("UserAdmin150", "getScriptLink", scrptNme);
				infoWndw.dispose();
				sce.setCursor(new Cursor(Cursor.HAND_CURSOR));
								
			} else if (cmd.equals("*ok*")) {										
				infoWindowActionPerformed(tips);
				infoWndw.dispose();
				sce.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			} else if (cmd.equals("*cancel*")) { 										
				infoWndw.dispose();		
				sce.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
	        } else if (cmd.equals("*info*")) {
				displayInfoWindow();	
				sce.setCursor(new Cursor(Cursor.HAND_CURSOR));

	        } else if (cmd.equals("*dock*")) {
				mMnu.addTab(WindowFrame.this);
				mMnu.validate();
				mFrm.requestFocus();
				sce.setCursor(new Cursor(Cursor.HAND_CURSOR));
	         
	        } else if (cmd.equals("*exMsg*")) {
	        	setExpand();
	        	sce.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
	        	
	        } else if(cmd.equals(WindowItem.CLEAR))	{	
				actionClear();
			
	        } else if(cmd.equals(WindowItem.FIND_RECORD)) {		
				actionPerformedLazy(clsNme, fndMthd, e);
			
			} else if(cmd.equals(WindowItem.EXPAND_RECORD) && validFrm(sce)) {
				actionPerformedLazy(clsNme, expMthd,  e);
			
			} else if(cmd.equals(WindowItem.EDIT_RECORD) && validFrm(sce)) { 
				actionPerformedLazy(clsNme, edtMthd, e);
			
			} else if(cmd.equals(WindowItem.DELETE_RECORD) && validFrm(sce) 
					&& optCfm.showDeleteCnfrm(sce)) { 
				actionPerformedLazy(clsNme, delMthd, e);
			
			} else if(cmd.equals(WindowItem.PREV_RECORD))	{
				actionPerformedLazy(clsNme, prvMthd,e);
			
			} else if(cmd.equals(WindowItem.NEXT_RECORD))	{
				actionPerformedLazy(clsNme, nxtMthd, e);
			
			} else if(cmd.equals(WindowItem.MORE_RECORD) && validFrm(sce)) {
				actionPerformedLazy(clsNme, mreMthd, e);
			
			} else if(cmd.equals(WindowItem.PRINT_RECORD) && validFrm(sce)) {
				actionPerformedLazy(clsNme, prtMthd, e);		
			
			} else if(cmd.equals(WindowItem.POST_RECORD) && validFrm(sce)) {
				actionPerformedLazy(clsNme, pstMthd, e);
			
			} else if(cmd.equals(WindowItem.GET_BATCH) && validFrm(sce)) {
				actionPerformedLazy(clsNme, btchMthd, e);
			
			} else if(cmd.equals(WindowItem.NEW_LINE) && validFrm(sce)) {
				actionNewTableLine();
			
			} else if(cmd.equals(WindowItem.DELETE_LINE) && validFrm(sce)  && 
							optCfm.showDeleteCnfrm(sce) ) {
				actionPerformedLazy(clsNme, delLinMthd, e);
			
			} else if(cmd.equals(WindowItem.POST_LINE) && validFrm(sce)) {
				actionPerformedLazy(clsNme, pLinMthd, e);
			
			} else if(cmd.equals("PREVIEW"))  {
				actionScriptPreview();
			
			} else if(cmd.equals("REPORT") && validFrm(sce)) {
				actionRunRpt(rptXML);
			
			}
	
	}		
	
	private void actionPerformedImed(String methodclass, String method, String action) {	
		cntr.showWebStatus("Opening webBoltOnsServer.ServletConnetor:" + method);
		sendWindowToTable(action);
		dSet.putStringField(WindowItem.CLASSNAME, methodclass);
		dSet.putStringField(WindowItem.METHOD, method);
		dSet.putStringField(WindowItem.ACTION, action);
		postServerRequestImed();
		if(!action.equals("PREVIEW") && !action.equals("POST")) 
			     sendTableToWindow(action);
		cntr.showWebStatus("Done");
		dSet.putStringField(WindowItem.CLASSNAME, clsNme);
		mPnl.validate();
	}

	/**
	 * <h2><code>actionPerformedImed</code></h2>
	 *
	 *  - Remote servewr actions
	 *  
	 */
	private void actionPerformedLazy(String mthdcls, String mthd, ActionEvent event) {	
		cntr.showWebStatus("Opening webBoltOnsServer.ServletConnetor:" + mthd);
		sendWindowToTable(event.getActionCommand());
		dSet.putStringField(WindowItem.CLASSNAME, mthdcls);
		dSet.putStringField(WindowItem.METHOD, mthd);
		dSet.putStringField(WindowItem.ACTION, event.getActionCommand());
		postServerRequestLazy(event);
	}
	
	
	/**
	 * <h2><code>actionRunRpt</code></h2>
	 * @param	String rptXML - the report name to display 
	 *
	 * <p> displayes a report</p> 
	 */	
	private void actionRunRpt(String rpt) {
		String  d = cntr.reportURL;
		if(rpt.toUpperCase().endsWith(".JASPER"))
			d += "?JasperFile="   + rpt +  "&ServiceDocType=jasper" ;
		else
			d += "?ReportScript=" + rpt +  "&ServiceDocType=jrivet" ;
		
		for (int x = 0; x <= totObj; x++) {
			String o = cmpntTree[x].getObjectName(),
			       f = cmpntTree[x].getFieldName(),
			       t = cmpntTree[x].getDataType();
			
			if (o != null && f != null  && o.equals(WindowItem.TEXT_FIELD_OBJECT)) {
				String v = ((CTextBoxField) cmpntTree[x].getComponentObject()).getSelectedComponentItem();
				
				if(v.equals("")) v = " ";
				
				if(t.equals("DAT") && DataSet.isDate(v));
					v =  DataSet.formatDateField(v, cntr.serverDateFormat);
				
				d += "&" + f + "=" + v;
			}	
		}				
		
		cntr.showWebDocument(d);
		if(report != null) report.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}

	
	private void actionScriptPreview() {
		preview.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		actionPerformedImed(clsNme, prvwMthd, "PREVIEW");
		if(dSet.getStringField("[ScriptPreview/]").equals("true")) {
			final WindowFrame aa = new WindowFrame(cntr);
			aa.setEditPreviewWindow(dSet);
		}
		
		if(preview != null)  preview.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

		
	/**
	 * <h2><code>link_actionPerformed</code></h2>
	 *
	 *  - script link action  
	 */	
	public void actionScriptLinkPerformed(String link, String rtnfield, int hl) {
		lfEnable = false;
		DataSet scrnSt = new DataSet();
		scrnSt.put("[SCREEN_SCRIPT/]", link);
		scrnSt.putIntegerField("[ScriptAccessLevel/]", cmpntTree[hl].getUserAccessLevel());
	
		final WindowFrame linkAccess = new WindowFrame(cntr);
		linkAccess.setWindow(scrnSt, this, rtnfield, hl);	
		linkAccess.showWindow();
		mFrm.addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowClosing(WindowEvent arg0) {
				if(linkAccess.mFrm != null) {
					linkAccess.mFrm.dispatchEvent(arg0);
					linkAccess.mFrm.dispose();
					linkAccess.mFrm = null;
				}
			}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent e) {}});
	}
	
	
	/**
	 * <h2><code>actionWebDoc</code></h2>
	 *
	 * <p> Display web documants </p>
	 *  
	 */
	private void actionWebDoc(int hl) {
		String document = "";
		if (!cmpntTree[hl].getLink().equals("")) {
			document = cmpntTree[hl].getLink();
		} else {
			for (int c = 0; c < cmpntTree.length; c++) {
				if (cmpntTree[c] == null || cmpntTree[c].getFieldName() == null)  
					continue;
				 
				
				if (cmpntTree[c].getFieldName().equals(cmpntTree[hl].getFieldName())
						&& cmpntTree[c].getObjectName().equals(WindowItem.TEXT_FIELD_OBJECT)) {
					document = "http://" + ((StandardComponentLayout) cmpntTree[c].getComponentObject()).getString();
					c = cmpntTree.length;
				}
			}
		}
		
		if (!document.equals("") && !document.equals("http://")) {
			cntr.showWebDocument(document);
		}
	
	}

	
	
	/**
	 * <h2><code>addToPanelObject</code></h2>
	 * @param int parent_hl 
	 * @param int hl
	 * @param String objects
	 *
 	 */
	private void addToPanelObject(int parent_hl, String objects, DataSet script) {
		final int hl = Integer.parseInt(DataSet.parseProperty(WindowItem.OBJECT_HL, objects));
		cmpntTree[hl].setObjectProperties(objects);    
		StandardComponentLayout stdCmp = null;
		try {
			String cls = (String) cntr.getScriptRules().compClass.get(cmpntTree[hl].getObjectName());
			if(cls != null) {
				cmpntTree[hl].setClassForName(cls);
				stdCmp = (StandardComponentLayout) Class.forName(cls).newInstance();
				cmpntTree[hl].setComponentObject((JComponent)stdCmp);
				((StandardComponentLayout) stdCmp).buildComponent(cmpntTree[parent_hl], cmpntTree[hl], this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	/**
	 * <h2><code>buildNavBar</code></h2>
	 * @param DataSet - Button Data 
	 * @return JPanel
	 *
 	 */
	private JPanel buildNavBar(DataSet script) {
	
		final  JToolBar toolBar = new JToolBar();
		toolBar.setForeground(Color.WHITE);
		toolBar.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
	
		if (script.get("[CLEAR_Desc/]") != null && !script.get("[CLEAR_Desc/]").equals("")) {
			clrMthd = script.getStringField("[CLEAR_Method/]");
			clear = cntr.buildFancyButton(script.getStringField("[CLEAR_Desc/]"),
					script.getStringField("[CLEAR_Icon/]"), "webBoltOnsServer Method: " + clrMthd, 
					getNextButtonMnemonic(script.getStringField("[CLEAR_Desc/]")));
			clear.setActionCommand(WindowItem.CLEAR);
			clear.addActionListener(this);
			toolBar.add(clear, null);
			toolBar.addSeparator();
		}

		if (script.get("[FIND_Method/]") != null && !script.get("[FIND_Method/]").equals("")) {
			fndMthd = script.getStringField("[FIND_Method/]");
			find = cntr.buildFancyButton(script.getStringField("[FIND_Desc/]"),
					script.getStringField("[FIND_Icon/]"),"webBoltOnsServer Method: " + fndMthd, 
					getNextButtonMnemonic(script.getStringField("[FIND_Desc/]")));
			find.setActionCommand(WindowItem.FIND_RECORD);
			find.addActionListener(this);
			toolBar.add(find, null);
			toolBar.addSeparator();
		}

		if (script.get("[EXPAND_Method/]") != null && !script.get("[EXPAND_Method/]").equals("")) {
			expMthd = script.getStringField("[EXPAND_Method/]");
			expand = cntr.buildFancyButton(script.getStringField("[EXPAND_Desc/]"),
					script.getStringField("[EXPAND_Icon/]"), "webBoltOnsServer Method: " + expMthd, 
					getNextButtonMnemonic(script.getStringField("[EXPAND_Desc/]")));
			expand.setActionCommand(WindowItem.EXPAND_RECORD);
			expand.addActionListener(this);
			toolBar.add(expand, null);
			toolBar.addSeparator();
		}

		if (itmAcsLvl > 2 && script.get("[EDIT_Method/]") != null && !script.get("[EDIT_Method/]").equals("")) {
			edtMthd = script.getStringField("[EDIT_Method/]");
			edit = cntr.buildFancyButton(script.getStringField("[EDIT_Desc/]"),
					script.getStringField("[EDIT_Icon/]"),"webBoltOnsServer Method: " + edtMthd, 
					getNextButtonMnemonic(script.getStringField("[EDIT_Desc/]")));
			edit.setActionCommand(WindowItem.EDIT_RECORD);
			edit.addActionListener(this);
			toolBar.add(edit, null);
			toolBar.addSeparator();
		}

		
		if (itmAcsLvl > 2 && script.get("[DELETE_Method/]") != null && !script.get("[DELETE_Method/]").equals("")) {
			delMthd = script.getStringField("[DELETE_Method/]");
			delete = cntr.buildFancyButton(script.getStringField("[DELETE_Desc/]"),
					 script.getStringField("[DELETE_Icon/]"), "webBoltOnsServer Method: " + delMthd, 
					 getNextButtonMnemonic(script.getStringField("[DELETE_Desc/]")));
			delete.setActionCommand(WindowItem.DELETE_RECORD);
			delete.addActionListener(this);
			toolBar.add(delete, null);
			toolBar.addSeparator();
		}
		
		
		if (script.get("[PREV_Method/]") != null && !script.get("[PREV_Method/]").equals("")) {
			prvMthd = script.getStringField("[PREV_Method/]");
			prev =  cntr.buildFancyButton(script.getStringField("[PREV_Desc/]"),
					script.getStringField("[PREV_Icon/]"),"webBoltOnsServer Method: " + prvwMthd, 
					 getNextButtonMnemonic(script.getStringField("[PREV_Desc/]")));
			prev.setActionCommand(WindowItem.PREV_RECORD);
			prev.addActionListener(this);
			toolBar.add(prev, null);
			toolBar.addSeparator();
		}


		if (script.get("[NEXT_Method/]") != null && !script.get("[NEXT_Method/]").equals("")) {
			nxtMthd = script.getStringField("[NEXT_Method/]");
			next =  cntr.buildFancyButton(script.getStringField("[NEXT_Desc/]"),
					script.getStringField("[NEXT_Icon/]"), "webBoltOnsServer Method: " + nxtMthd, 
					 getNextButtonMnemonic(script.getStringField("[NEXT_Desc/]")));
			next.setActionCommand(WindowItem.NEXT_RECORD);
			next.addActionListener(this);
			toolBar.add(next, null);
			toolBar.addSeparator();
		}

		
		if (script.get("[MORE_Method/]") != null && !script.get("[MORE_Method/]").equals("")) {
			mreMthd = script.getStringField("[MORE_Method/]");
			more = cntr.buildFancyButton(script.getStringField("[MORE_Desc/]"),
					script.getStringField("[MORE_Icon/]"), "webBoltOnsServer Method: " + mreMthd,
					 getNextButtonMnemonic(script.getStringField("[MORE_Desc/]")));
			more.setActionCommand(WindowItem.MORE_RECORD);
			more.addActionListener(this);
			toolBar.add(more, null);
			toolBar.addSeparator();
		}

		if (script.get("[PRINT_Method/]") != null && !script.get("[PRINT_Method/]").equals("")) {
			prtMthd = script.getStringField("[PRINT_Method/]");
			print =  cntr.buildFancyButton(script.getStringField("[PRINT_Desc/]"),
					script.getStringField("[PRINT_Icon/]"), "webBoltOnsServer Method: " + prtMthd,
					 getNextButtonMnemonic(script.getStringField("[PRINT_Desc/]")));
			print.setActionCommand(WindowItem.PRINT_RECORD);
			print.addActionListener(this);
			toolBar.add(print, null);
			toolBar.addSeparator();
		}

		if (itmAcsLvl > 2 && script.get("[POST_Method/]") != null && !script.get("[POST_Method/]").equals("")) {
			pstMthd = script.getStringField("[POST_Method/]");
			post =  cntr.buildFancyButton(script.getStringField("[POST_Desc/]"),
					script.getStringField("[POST_Icon/]"), "webBoltOnsServer Method: " + pstMthd,
					 getNextButtonMnemonic(script.getStringField("[POST_Desc/]")));
			post.setActionCommand(WindowItem.POST_RECORD);
			post.addActionListener(this);
			toolBar.add(post, null);
			toolBar.addSeparator();
		}
		
		if (itmAcsLvl > 2 && script.get("[THREAD_Method/]") != null && !script.get("[THREAD_Method/]").equals("")) {
			btchMthd = script.getStringField("[THREAD_Method/]");
			btchThrd = cntr.buildFancyButton(script.getStringField("[THREAD_Desc/]"),
					script.getStringField("[THREAD_Icon/]"), "webBoltOnsServer Method: " + btchMthd,
					 getNextButtonMnemonic(script.getStringField("[THREAD_Desc/]")));
			btchThrd.setActionCommand(WindowItem.GET_BATCH);
			btchThrd.addActionListener(this);
			toolBar.add(btchThrd, null);
			toolBar.addSeparator();
		}

		if (itmAcsLvl > 2 && script.get("[NEW-LINE_Desc/]") != null && !script.get("[NEW-LINE_Desc/]").equals("")) {
			linMthd = script.getStringField("[NEW-LINE_Method/]");
			newline = cntr.buildFancyButton(script.getStringField("[NEW-LINE_Desc/]"),
					script.getStringField("[NEW-LINE_Icon/]"), "webBoltOnsServer Method: " + linMthd,
					 getNextButtonMnemonic(script.getStringField("[NEW-LINE_Desc/]")));
			newline.setActionCommand(WindowItem.NEW_LINE);
			newline.addActionListener(this);
			toolBar.add(newline, null);
			toolBar.addSeparator();
		}
		
		if (itmAcsLvl > 2 && script.get("[DEL-LINE_Method/]") != null && !script.get("[DEL-LINE_Method/]").equals("")) {
			delLinMthd = script.getStringField("[DEL-LINE_Method/]");
			deleteline = cntr.buildFancyButton(script.getStringField("[DEL-LINE_Desc/]"),
					 script.getStringField("[DEL-LINE_Icon/]"), "webBoltOnsServer Method: " + delLinMthd, 
					 getNextButtonMnemonic(script.getStringField("[DEL-LINE_Desc/]")));
			deleteline.setActionCommand(WindowItem.DELETE_LINE);
			deleteline.addActionListener(this);
			toolBar.add(deleteline, null);
			toolBar.addSeparator();
		}
		
		
		if (itmAcsLvl > 2 && script.get("[POST-LINE_Method/]") != null && !script.get("[POST-LINE_Method/]").equals("")) {
			pLinMthd = script.getStringField("[POST-LINE_Method/]");
			postline = cntr.buildFancyButton(script.getStringField("[POST-LINE_Desc/]"),
					 script.getStringField("[POST-LINE_Icon/]"), "webBoltOnsServer Method: " + delLinMthd, 
					 getNextButtonMnemonic(script.getStringField("[POST-LINE_Desc/]")));
			postline.setActionCommand(WindowItem.POST_LINE);
			postline.addActionListener(this);
			toolBar.add(postline, null);
			toolBar.addSeparator();
		}
		
		if (script.get("[PREVIEW_Method/]") != null && !script.get("[PREVIEW_Method/]").equals("")) {
			prvwMthd = script.getStringField("[PREVIEW_Method/]");
			preview = cntr.buildFancyButton(script.getStringField("[PREVIEW_Desc/]"),
					script.getStringField("[PREVIEW_Icon/]"), "webBoltOnsServer Method: " + prvwMthd,
					 getNextButtonMnemonic(script.getStringField("[PREVIEW_Desc/]")));
			preview.setActionCommand("PREVIEW");
			preview.addActionListener(this);
			toolBar.add(preview, null);
			toolBar.addSeparator();
		}
		
		if (script.get("[REPORT_Link/]") != null && !script.get("[REPORT_Link/]").equals("")) {
			rptXML = script.getStringField("[REPORT_Link/]");
			report = cntr.buildFancyButton(script.getStringField("[REPORT_Desc/]"),
					script.getStringField("[REPORT_Icon/]"), "webBoltOnsServer Method: " + rptXML,
					 getNextButtonMnemonic(script.getStringField("[REPORT_Desc/]")));
			report.setActionCommand("REPORT");
			report.addActionListener(this);
			toolBar.add(report, null);
			toolBar.addSeparator();
		}
		

	    JPanel buttonBar = new JPanel();
	    buttonBar.add(BorderLayout.WEST, toolBar);
		toolBar.setName("Nav-Bar");
		return buttonBar;
	}


	
	/**
	 * <h2><code>buildButtonObject</code></h2>
	 * @param   int parent_hl 
	 * @param  int hl 
	 * @param	DataSet script
	 *
 	 */
	private void buildButtonObject(final int parent_hl, final int hl, final DataSet script) {
		JButton button = new JButton();
		String link = cmpntTree[hl].getLink();
		String method = cmpntTree[hl].getMethod();
		String popUp = cmpntTree[hl].getFieldParameterName();

		cmpntTree[hl].setComponentObject(button);
		button.setText(cmpntTree[hl].getDescription());
		button.setName(Integer.toString(hl));
		
		if(!popUp.equals("")) {
			for (int c = 0; c < cmpntTree.length; c++) {
				if (cmpntTree[c] == null || cmpntTree[c].getFieldName() == null)  
					continue;
				 		
				if (cmpntTree[c].getFieldName().equals(popUp))  
							( (CTableContainer)	cmpntTree[c].getComponentObject() )
													.addPopUpButton(cmpntTree[hl]);
			}
		}
		
		if (!cmpntTree[hl].getIconName().equals("")) {
			button.setIcon(cntr.getImageIcon(cmpntTree[hl].getIconName()));
		} else if (method.equals("URL")) {
			button.setIcon(cntr.webDocumentIcon);
		} else if (method.equals("EMAIL")) {
			button.setIcon(cntr.webDocumentIcon);
		} else if ((!link.equals(""))) {
			button.setIcon(cntr.linkButtonIcon);
		} else {
			button.setIcon(cntr.postButtonIcon);
		}

		button.setFocusable(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setActionCommand(Integer.toString(hl));
		if ((!link.equals("")) || (method != null && !method.equals(""))) {
			char quickkey = getNextButtonMnemonic(cmpntTree[hl].getDescription());
			if (quickkey != ' ') {
				button.setMnemonic(quickkey);
			}
			button.addActionListener(this);
		}

		if (cmpntTree[hl].getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) cmpntTree[parent_hl].getComponentObject()).addRight(button, 5);
		} else if (cmpntTree[hl].getPosition().equals(
				WindowItem.CENTER)) {
			((CPanelContainer) cmpntTree[parent_hl].getComponentObject()).addRight(button, 3);
		} else if (cmpntTree[hl].getPosition().equals(
				WindowItem.LEFT)) {
			((CPanelContainer) cmpntTree[parent_hl].getComponentObject()).addLeft(button, 1);
		} else {
			((CPanelContainer) cmpntTree[parent_hl].getComponentObject()).addc(button);
		}
	}

	
	/**
	 * <h2><code>buildDisplayFrameObject</code></h2>
	 * @param  DataSet - script - the display details
	 *
 	 * <p> This method read the script from the server and will render the screen,
	 * set action listeners and call inz methods </p> 
	 * 
	 */
	public void buildMainPanel(DataSet script) {
		// get the screen layout from the server

		scrnTtle = script.getStringField(WindowItem.SCREEN_TITLE);
		clsNme = script.getStringField(WindowItem.CLASSNAME);
		scrptNme = script.getStringField(WindowItem.SCRIPTNAME);
		initMthd = script.getStringField(WindowItem.METHOD);
		atRfrsh = script.getBooleanField(WindowItem.AUTO_REFRESH);
		
		dSet = new DataSet();
		dSet.putStringField(WindowItem.SCREEN_TITLE, scrnTtle);
		dSet.putStringField(WindowItem.CLASSNAME, clsNme);
		dSet.putStringField(WindowItem.SCRIPTNAME, scrptNme);
		dSet.putStringField(WindowItem.METHOD, initMthd);
		dSet.put(WindowItem.TOTAL_OBJECTS, script.get(WindowItem.TOTAL_OBJECTS));
		String action = script.getStringField(WindowItem.ACTION);

		if (action != null)
			dSet.put(WindowItem.ACTION, script.get(WindowItem.ACTION));

		if (!cntr.isStandalone && !checkSecurity())
			msgGap = 90;

		info = cntr.buildFancyButton("","logo.gif","Script Details",' ');
		info.setCursor(new Cursor(Cursor.HAND_CURSOR));
		info.setActionCommand("*info*");
		info.addActionListener(this);

		dock = cntr.buildFancyButton(null, "dock.gif", "Dock Window", ' ' );
		dock.setActionCommand("*dock*");
		dock.addActionListener(this);	
		dock.setVisible(false);
		
		
		// Add The Message Line
		msgPnl = new JPanel(new StackedFlowLayout());
		botPnl = new JPanel(new BorderLayout());
		botPnl.add(msgPnl, BorderLayout.CENTER);

		exMsg = new JButton();
		exMsg.setBorder(null);
		exMsg.setIcon(cntr.upIcon);
		exMsg.setFocusable(false);
		exMsg.setVerticalAlignment(1);
		exMsg.setCursor(new Cursor(Cursor.HAND_CURSOR));
		exMsg.setActionCommand("*exMsg*");
		exMsg.addActionListener(this);
		
		botPnl.add(exMsg, BorderLayout.EAST);
		msgPn = new JScrollPane(botPnl);
		msgPn.setPreferredSize(new Dimension(10, 40));
		msgPn .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// draw the screen
		final Enumeration eObjects = 
			script.getTableVector(WindowItem.SCREEN_OBJECTS).elements();
		totObj = script.getIntegerField(WindowItem.TOTAL_OBJECTS);

		cmpntTree = new WindowItem[totObj + 1];
		for (int i = 0; i < totObj + 1; i++)
			cmpntTree[i] = new WindowItem();

		
		CPanelContainer tp = new CPanelContainer();
		cmpntTree[0].setComponentObject(tp);
		cmpntTree[0].setObjectName(WindowItem.PANEL_OBJECT);
		tp.buildComponent(null, null, this);

		String  objectAttributeString;
		while (eObjects.hasMoreElements()) {
			objectAttributeString = (String) eObjects.nextElement();
			final int parent_hl = Integer.parseInt(DataSet.parseProperty(
					WindowItem.PARENT_HL,  objectAttributeString));
			final int object_hl = Integer.parseInt(DataSet.parseProperty(
					WindowItem.OBJECT_HL,  objectAttributeString));

			if (cmpntTree[parent_hl].getComponentObject() != null) {
				buildWindowItem(parent_hl, object_hl, objectAttributeString, script);
			}
		}

		final JPanel controls = new JPanel();
		controls.add(dock);
		controls.add(info);
		
		
		JLabel bannerLabel = new JLabel(scrnTtle);

		bannerLabel.setFont(new java.awt.Font("Dialog", 1, 15));
		bannerLabel.setForeground(cntr.tbFontColor);
		final JPanel bannerPanel = new JPanel(new BorderLayout());
		bannerPanel.setBackground(cntr.tbColor);
		bannerPanel.add(BorderLayout.SOUTH, bannerLabel);

		// create the buttons
		final JPanel buttonbar = buildNavBar(script);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(BorderLayout.WEST, buttonbar);
		buttonPanel.add(BorderLayout.EAST, controls);

		topPnl = new JPanel();
		topPnl.setLayout(new BorderLayout(5,5));
		topPnl.add(BorderLayout.NORTH, bannerPanel);
		topPnl.add(BorderLayout.SOUTH, buttonPanel);
				
		mPnl = new JPanel();
		mPnl.setLayout(new BorderLayout());
		mPnl.add(BorderLayout.NORTH, topPnl);
		mPnl.add(BorderLayout.CENTER,((CPanelContainer) cmpntTree[0].getComponentObject()));
		mnPn = new JScrollPane(mPnl);
		
		mainSpltPn = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainSpltPn.setTopComponent(mnPn);
		mainSpltPn.setBottomComponent(msgPn);
		
		mainSpltPn.setDividerSize(1);
		mainSpltPn.setResizeWeight(1);
		mainSpltPn.setFocusable(false);

		
		//draw components 
		for (int x = 0; x < cmpntTree.length; x++) 
			if(cmpntTree[x].getComponentObject() 
               instanceof StandardComponentLayout) 
				((StandardComponentLayout) cmpntTree[x].
						getComponentObject()).initializeComponentUI();
			
		mainSpltPn.setDoubleBuffered(true);
		mainSpltPn.validate();

		mPnl.add(BorderLayout.CENTER,((CPanelContainer) cmpntTree[0].getComponentObject()));
 		
 		((JComponent) mainSpltPn).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "F3" );
		((JComponent) mainSpltPn).getActionMap().put( "F3",  new AbstractAction() {
			public void actionPerformed( ActionEvent ae ){
			 	if(mFrm.isActive() && calngFrm != null)
			      	calngFrm.doFindClick();
			 	
				if(!dock.isVisible()) 
					mMnu.deleteTab(WindowFrame.this);
				else
					mFrm.dispose();
			}
		});

		((JComponent) mainSpltPn).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1" );
		((JComponent) mainSpltPn).getActionMap().put( "F1",  new AbstractAction() {
			public void actionPerformed( ActionEvent ae ){
				if(dock.isVisible()) 
					dock.doClick();
				else
					mMnu.removeTab(WindowFrame.this);
			}
		});

		
		((JComponent) mainSpltPn).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "F5" );
		((JComponent) mainSpltPn).getActionMap().put( "F5",  new AbstractAction() {
			private static final long serialVersionUID = 214005275668399814L;
			public void actionPerformed( ActionEvent ae ){
				exMsg.doClick();
			}
		});
		
	}
	
	
	/**
	 * <h2><code>buildMessageLine</code></h2>
	 *
	 */	
	private void buildMessageLine (String text, String type, String level, String field) {			
				JLabel messageLine = new JLabel();
				if (level.equals("30")) {
					msgPnl.setBackground(Color.YELLOW);
					messageLine.setText(text);
					messageLine.setIcon(cntr.errorIcon);
					messageLine.setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else if (level.equals("20")) {
					messageLine.setText(text);
					messageLine.setIcon(cntr.warnIcon);

				} else {
					messageLine.setText(text);
					messageLine.setIcon(cntr.messageIcon);
				}

				if (field != null) {
					
					for (int x = 0; x < cmpntTree.length; x++) {
						WindowItem i = cmpntTree[x];
						if (cmpntTree[x].getFieldName() == null)
							continue;
						
						if (i.getFieldName().equals(field))
							if (i.getComponentObject()  instanceof StandardComponentLayout) {
								((StandardComponentLayout)i.getComponentObject()).setValid(false);
								final int y = x; 
								messageLine.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										unWndFcs(y);
									}
								});
							}
					}
				}
				msgPnl.add(messageLine, null);
			}	

	
	/**
	 * <h2><code>buildMessagePopup</code></h2>
	 *
	 */	
	private void buildMessagePopup (String text, String field) {			
		 		CDialog optCfm = new CDialog(getWindowFrame(), cntr);
				mFrm.getToolkit().beep();
				if(field != null)
					optCfm.showMessageDialog(field, text, null);
				else			
					optCfm.showWaringDialog(text, null);
	}

	
	private void setExpand() {
		int sMax = mFrm.getHeight() - msgGap;
		if(!dock.isVisible())
		  sMax = mMnu.getMenuHeight() + 15 - msgGap ;
		
		if (mainSpltPn.getDividerLocation() <= sMax - msgGap) {
			mainSpltPn.setDividerLocation(sMax);
			exMsg.setIcon(cntr.upIcon);
		
		} else {
			if(dock.isVisible())
				mainSpltPn.setDividerLocation(sMax - msgGap);
			else
				mainSpltPn.setDividerLocation(sMax - msgGap);
			
			exMsg.setIcon(cntr.downIcon);
		}
	}
	
	
	/**
	 * <h2><code>buildObjectControl</code></h2>
	 * @param   int parent_hl
	 * @param	int object_hl 
	 * @param   String objectAttributeString
	 * @param   DataSet script	  
 	 */
	private void buildWindowItem(final int p_hl, final int hl,
			final String objectAttributeString, DataSet script) {

		cmpntTree[hl].setObjectProperties(objectAttributeString);

		 if (cmpntTree[hl].getObjectName().equals(WindowItem.BUTTON_OBJECT) &&  
				 cmpntTree[hl].getUserAccessLevel() > 1) 
			 buildButtonObject(p_hl, hl, script);
		
		 
		  else if  (cmpntTree[p_hl].getObjectName().equals(WindowItem.COMBOBOX_OBJECT) 
				        || cmpntTree[p_hl].getObjectName().equals(WindowItem.TABLE_COMBOBOX_OBJECT))  
			((CComboBoxField) cmpntTree[p_hl].getComponentObject()).addListItem(cmpntTree[hl].getDefaultValue(), 
						cmpntTree[hl].getDescription(), cmpntTree[hl].getIconName());

	 
		  else if (cmpntTree[p_hl].getObjectName().equals(WindowItem.LISTBOX_OBJECT))  
			  ((CListBoxField) cmpntTree[p_hl].getComponentObject()).addListItem(cmpntTree[hl].
					  getFieldName(),cmpntTree[hl].getDescription(), cmpntTree[hl].getIconName());

		 else if (cmpntTree[p_hl].getObjectName().equals(WindowItem.RADIO_BUTTON_GROUP_OBJECT)) 
				((CRadioGroupContainer) cmpntTree[p_hl].getComponentObject()).addRadioButton(cmpntTree[hl]);

		  else 
			addToPanelObject(p_hl, objectAttributeString, script);
	
	}
	
	
	/**
	 * <h2><code>checkSecurity</code></h2>
	 * <p> test system security manager </p>
	 *  
	 */
	public boolean checkSecurity() {
		try {
			security = System.getSecurityManager();
			if (security != null) {
				security.checkSystemClipboardAccess();
			}
			return true;
		} catch (SecurityException se) {
			return false;
		}
	}

	
	/**
	 * <h2><code>copyToClipBoard</code></h2>
	 * 
	 * @param String clipData
	 * 
	 * <p>copies the contents in clipData to the system clip board</p>
	 * 
	 */
	public void copyToClipBoard(String clipData) {
		try {
			StringSelection contents = new StringSelection(clipData);
			clpbrd.setContents(contents, WindowFrame.this);
		} catch (Exception er) {
		}
	}

	
	/**
	 * <h2><code>displayInfoWindow</code></h2>
	 * 
 	 * 
	 */
	private void displayInfoWindow() {

		infoWndw = new JDialog(getWindowFrame(),false);
		infoWndw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		final JPanel north = new JPanel(new BorderLayout());
		north.add(BorderLayout.WEST, mMnu.createTitle("Script Details"));
				
		final CButton about = new CButton("Admin" );
		about.setCursor(new Cursor(Cursor.HAND_CURSOR));
		about.setActionCommand("*about*");
		about.addActionListener(this);

		final JPanel center = new JPanel(new GridFlowLayout(10, 10));
		center.add(new JLabel("Script Name:"), new GridFlowLayoutParameter(true, 0));
		center.add(new JLabel(scrptNme), new GridFlowLayoutParameter(false, 1));
		center.add(new JLabel("   "), new GridFlowLayoutParameter(false, 2));
		center.add(new JLabel("Command Keys:"), new GridFlowLayoutParameter(false, 3));
		
		center.add(new JLabel("     Screen Title:"), new GridFlowLayoutParameter(true, 0));
		center.add(new JLabel(scrnTtle), new GridFlowLayoutParameter(false, 1));
		center.add(new JLabel("     F1=Dock/Undock Window"), new GridFlowLayoutParameter(false, 3));

		
		center.add(new JLabel("     Servlet Class:"), new GridFlowLayoutParameter(true,0));
		center.add(new JLabel(clsNme), new GridFlowLayoutParameter(false, 1));
		center.add(new JLabel("     F2=Focus/Display Menu"), new GridFlowLayoutParameter(false, 3));
				
		center.add(new JLabel("      Width:"), new GridFlowLayoutParameter(true, 0));
		center.add(new JLabel(Integer.toString(mFrm.getWidth())),new GridFlowLayoutParameter(false, 1));
		center.add(new JLabel("     F3=Close/Exit Script"), new GridFlowLayoutParameter(false, 3));
		
		center.add(new JLabel("      Height:"), new GridFlowLayoutParameter(true, 0));
		center.add(new JLabel(Integer.toString(mFrm.getHeight())),new GridFlowLayoutParameter(false, 1));
		center.add(new JLabel("     F4=Prompt"), new GridFlowLayoutParameter(false, 3));
		
		
		center.add(new JLabel("      Objects:"), new GridFlowLayoutParameter(true, 0));
		center.add(new JLabel(Integer.toString(totObj)),new GridFlowLayoutParameter(false, 1));
		center.add(new JLabel("     F5=Expand Messages"), new GridFlowLayoutParameter(false, 3));

		
		tips = new JCheckBox();
		tips.setSelected(srvtips);
		if (cntr.tipAccess) {
			center.add(new JLabel("Show Script Tips:"), new GridFlowLayoutParameter(true,0));
			center.add(tips, new GridFlowLayoutParameter(false, 1));
		}
	
		
		final JPanel south = new JPanel(new FlowLayout());
		if (cntr.administratorAccess) {
			final CButton edit = new CButton("Edit Script");
			edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
			edit.setActionCommand("*edit*");
			edit.addActionListener(this);
			center.add(edit, new GridFlowLayoutParameter(true, 3));
		}
		center.add(about, new GridFlowLayoutParameter(true, 3));

		final CButton ok = new CButton("Ok");
		ok.setMnemonic('O');
		ok.setActionCommand("*ok*");
		ok.addActionListener(this);

		final CButton cancel = new CButton("Cancel");
		cancel.setMnemonic('C');
		cancel.setActionCommand("*cancel*");
		cancel.addActionListener(this);

		south.add(ok);
		south.add(cancel);

		final JPanel main = new JPanel(new BorderLayout());
		main.add(BorderLayout.NORTH, north);
		main.add(BorderLayout.CENTER, center);
		main.add(BorderLayout.SOUTH, south);

		infoWndw.getContentPane().add(main);
		infoWndw.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		infoWndw.setLocation((d.width - infoWndw.getSize().width) / 2,
				(d.height - infoWndw.getSize().height) / 2);
		ok.requestFocus();
		infoWndw.setVisible(true);
	}

	
	
	/**
	 * <h2><code>doEditClick</code></h2>
	 *
	 * <p>prefrom a window refresh</p> 
	 *  
	 */
	public void doEditClick(){
		if( edit != null) {
			edit.doClick();
			return;
		}	

		if(post != null) 
			post.doClick();
	}
	
	
	/**
	 * <h2><code>doFindClick</code></h2>
	 *
	 * <p>prefrom a window refresh</p> 
	 *  
	 */
	public void doFindClick(){
		if(atRfrsh && find != null)
			find.doClick();
	}
	

	
	/**
	 * <h2><code>doNewLineClick</code></h2>
	 * @param ActionEvent action
	 * 
	 *   called by the client to when the user requestes		
	 *   a new table line
	 * 
	 */	

	public void doNewLineClick() {
		if(newline != null)
			newline.doClick();
	}

	
	public void fireServerRepsone(ActionEvent source, DataSet request, DataSet response) {
					
		if(response != null)  {
			String cmd = source.getActionCommand();
			CDialog optCfm = new CDialog(getWindowFrame(), cntr);
			dSet =	response; 
			if (more != null) more.setEnabled(dSet.getMoreButtonStatus());
			
			updateObjectProperty(dSet);
			if(!updateMessagePanel(dSet)) { 
				mFrm.getToolkit().beep();
				optCfm.showRequestCompleteDialog("Request Not Complete: Review Errors Below", 
						((JComponent)source.getSource()));
			} else {
				sendTableToWindow(cmd); 
				if(cmd.equals(WindowItem.POST_RECORD) || cmd.equals(WindowItem.POST_LINE)  
														||	cmd.equals(WindowItem.DELETE_LINE)) { 
					optCfm.showRequestCompleteDialog("Server Request Completed", 
							((JComponent)source.getSource()));
					if(find != null) find.doClick();
			 }
			 dSet.putStringField(WindowItem.CLASSNAME, clsNme);
			 mPnl.validate();
			} 
			
			if(source != null)
				((JComponent)source.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		}
	}
	
 
	
	/**
	 * <h2><code>fireWindowReturned</code></h2>
	 * 
 	 * 	
	 * get any parms passed back fom a child frame
	 *  
	 */
	public void fireWindowReturned(DataSet rDataSet, String rField, int rHL) {

		String rValue, rAction, rMethod, rClass; 
		if(!cmpntTree[rHL].getFieldParameterName().equals(""))  
			rField = cmpntTree[rHL].getFieldParameterName();
		
			
		rValue = rDataSet.getStringField(rField);
		if (cmpntTree[rHL].getFieldName() != null ) {
			 
				rMethod = cmpntTree[rHL].getMethod();
				rClass = cmpntTree[rHL].getMethodClass();
				
				if (!rMethod.equals("")) {
					if(cmpntTree[rHL].getObjectName().equals( 
						WindowItem.TABLE_TEXTFIELD_OBJECT))  
						rAction = WindowItem.TABLE_PROMPT;
					else
						rAction =  WindowItem.STD_PROMPT; 
				
					sendWindowToTable(rAction);
					dSet.putStringField(cmpntTree[rHL].getFieldName(), rValue);
					dSet.putStringField(WindowItem.METHOD, rMethod);
					if(!rClass.equals(""))
						dSet.putStringField(WindowItem.CLASSNAME, rClass);						
					dSet.putStringField(WindowItem.ACTION, WindowItem.FIND_RECORD);
					postServerRequestImed();
					sendTableToWindow(rAction);
				}
				((CTextBoxField) cmpntTree[rHL].getComponentObject()).setText(rValue);
				
 
		}
	 	unWndFcs(rHL);
		dSet.putStringField(WindowItem.CLASSNAME, clsNme);
	}	
	

	
	/**
	 * <h2><code>fireWindowReturning</code></h2>
	 * 
 	 * 	
	 * Pass any parms back to parent frame
	 *  
	 */
	public void fireWindowReturning() {
		if (calngFrm != null && !rtnField.equals("") && dock.isVisible()) {
			DataSet returnDataSet = new DataSet();
			returnDataSet = getSelectedTableRows(returnDataSet);
			calngFrm.fireWindowReturned(returnDataSet, rtnField, rtFldLvl);
			mFrm.dispose();
			calngFrm.mFrm.requestFocus();
		}
	}

	public AppletConnector getAppletConnector() {
		return cntr;
	}

	
	/**
	 * <h2><code>getDataSet</code></h2>
	 * @return DataSet -  
	 */
	public DataSet getDataSet() {
		return dSet;
	}
	
	
	public Frame getMainFrame() {
			return mFrm;
	}



	public MenuFrame getMenuObject() {
		return   mMnu;
	}


	/**
	 * <h2><code>getNextButtonMnemonic</code></h2>
	 * 
	 * Get the next button mnemonic
	 * 
	 *  @param String buttonText - this is the text displayed in 
	 *   the button
	 *   
	 *  @return char - the mnmonic for the button
	 *   
	 */
	private char getNextButtonMnemonic(String buttonText) {
		char[] bt = buttonText.toUpperCase().toCharArray();
		for (int x = 0; x < bt.length; x++) {
			if (!mnncs.contains(Character.toString(bt[x])) && bt[x] != ' ') {
				mnncs.add(Character.toString(bt[x]));
				return bt[x];
			}
		}
		return ' ';
	}

	/**
	 * <h2><code>getSelectedTableRows</code></h2>
	 * 
	 *  @return dataset - Dataset /w selected table rows
	 *  
	 */
	private DataSet getSelectedTableRows(DataSet hs) {
		//String object, datatype;
		Object windowComponentObject;  
		for (int x = 0; x <= totObj; x++) {
			windowComponentObject = cmpntTree[x].getComponentObject();
			if (windowComponentObject != null && 
					  windowComponentObject  instanceof CTableContainer ) {
		 
				String fieldNames[] = ((CTableContainer) cmpntTree[x]
						             .getComponentObject()).getFieldNames();
			 	     
				String fieldParameters [] =  ((CTableContainer) cmpntTree[x]
									 .getComponentObject()).getFieldParameters();
				
				Vector selected = new Vector();
				selected = ((CTableContainer) cmpntTree[x]
						.getComponentObject()).getSelectedRecords();
				if (selected != null) {
					if (!selected.isEmpty()) {
						String[] row = (String[]) selected.elementAt(0);
						for (int r = 0; r < fieldNames.length; r++) {
							if (row[r] != null) {
							  if(!fieldParameters[r].equals("") )
							  	 	hs.putStringField(fieldParameters[r], row[r]);
								 else
								 	hs.putStringField(fieldNames[r], row[r]);
							}
						}
					}
				}
			} 
		}
		return hs;
	}

	/**
	 * <h2><code>getSelectedTextFields</code></h2>
	 * 
	 * @return DataSet - dataset /w textfield data
	 * 
	 */
	private DataSet getSelectedTextFields(DataSet textFields) {
		for (int x = 0; x <= totObj; x++) {
			String object = cmpntTree[x].getObjectName(),
				   field = cmpntTree[x].getFieldName();
				  
			if(cmpntTree[x].getFieldParameterName() != null &&
						!cmpntTree[x].getFieldParameterName().equals("")) 
				 	field = cmpntTree[x].getFieldParameterName();

			
			if (object != null && field != null &&
					object.equals(WindowItem.TEXT_FIELD_OBJECT))
				textFields.put(field, ((JTextField) cmpntTree[x]
						.getComponentObject()).getText());
			 
		}
		return textFields;
	}
	
		
	
	/**
	 * <h2><code>getTitle</code></h2>
	 * 
	 * @return String - The title of the screen!
	 */
	public  String getTitle() {
		return scrnTtle;
	}

	
	/**
	 * <h2><code>getUnprotectedFields</code></h2>
	 * 
	 */	
	private void getUnprotectedFields() {
		Vector<String> updateObjects = new Vector<String>();
		for (int x = 0; x < cmpntTree.length; x++) {
			if (cmpntTree[x].getObjectName() == null
					|| cmpntTree[x].isProtected()) {
				continue;
			} else if (cmpntTree[x].getObjectName().equals(
					WindowItem.TABLE_OBJECT)) {
				String[] columnnames = ((CTableContainer) cmpntTree[x]
						.getComponentObject()).getFieldNames();
				dSet.put("[" + cmpntTree[x].getFieldName()
						+ "_COLNAMES/]", columnnames);
			} else if (cmpntTree[x].getObjectName().equals(
					WindowItem.CHECKBOX_OBJECT)
					|| cmpntTree[x].getObjectName().equals(
							WindowItem.COMBOBOX_OBJECT)
					|| cmpntTree[x].getObjectName().equals(
							WindowItem.LISTBOX_OBJECT)												
				    || cmpntTree[x].getObjectName().equals(
							WindowItem.RADIO_BUTTON_GROUP_OBJECT)
					|| cmpntTree[x].getObjectName().equals(
							WindowItem.TEXT_AREA_OBJECT)
					|| cmpntTree[x].getObjectName().equals(
							WindowItem.SPINNER_OBJECT)
					|| cmpntTree[x].getObjectName().equals(
							WindowItem.TEXT_FIELD_OBJECT)) {
				updateObjects.add(cmpntTree[x].getFieldName());
			}
		}

		Object[] updFldO = updateObjects.toArray();
		String[] updFlds = new String[updFldO.length];
		for (int x = 0; x < updFldO.length; x++) {
			updFlds[x] = (String) updFldO[x];
		}
		dSet.put("[updateFieldNames/]", (String[]) updFlds);
	}

	/**
	 * <h2><code>getWindowFrame</code></h2>
	 * @return JFrame - Returns the display frame object  
	 */
	public Frame getWindowFrame() {
		if (dock.isVisible())
			return mFrm;
		else 
			return cntr.getAppletFrame();
	}
 

	/**
	 * <h2><code>getWindowPane</code></h2>
	 * @return JSplitPane - Returns the display split pane object  
	 */	
	public JSplitPane getWindowPane() {
		return  mainSpltPn;
	}


 

	/**
	 * <h2><code>getWindowTree</code></h2>
	 * @return WnidowComponent - Returns component tree s  
	 */	
	public WindowItem[] getWindowTree() {
		return   cmpntTree;
	}

	/**
	 * <h2><code>hideWindow</code></h2>
	 *
	 * <p>hide window </p> 
	 *  
	 */
	public void hideWindow() {
		if(dock != null) 
			dock.setVisible(false);
		mainSpltPn.setDividerLocation(mMnu.getMenuHeight() + 15 - msgGap);
		exMsg.setIcon(cntr.upIcon);
		mainSpltPn.validate();
		mFrm.setVisible(false);
	}

	

	/**
	 * <h2><code>infoWindowActionPerformed</code></h2>
	 * 
	 */		
	private void infoWindowActionPerformed(JCheckBox tips) {
		if (tips.isSelected()) {
			showServerTips();
		} else {
			showNoServerTips();
		}
	}



	/**
	 * <h2><code>isVaildCharacter</code></h2>
	 *
	 * @return - true if character is valid
	 *  
	 */
	 public boolean isVaildCharacter(KeyEvent e, int hl, boolean allSelected ) {
		CDialog optCfm = new CDialog(getWindowFrame(), cntr);
		Component cmp = cmpntTree[hl].getComponentObject();
		
		final char nchr = e.getKeyChar();
		final String dataType = cmpntTree[hl].getDataType();
		final int datalength = cmpntTree[hl].getDataLength();
		int currentLenght = -1;
		
		if(cmp instanceof CTextBoxField)
			currentLenght = ((CTextBoxField)cmpntTree[hl]
											.getComponentObject()).getText().length();
			
		if(cmp instanceof CSpinnerField)
			currentLenght = (( CSpinnerField)cmpntTree[hl]
											.getComponentObject()).getSelectedComponentItem().length();
		
		if(cmp instanceof StandardComponentLayout)
			((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(true);
		
		
		if (e.getModifiers() < 3
				&& !(nchr == KeyEvent.VK_BACK_SPACE
						|| nchr == KeyEvent.VK_DELETE
						|| nchr == KeyEvent.VK_COPY
						|| nchr == KeyEvent.VK_PASTE
						|| nchr == KeyEvent.VK_LEFT
						|| nchr == KeyEvent.VK_RIGHT
						|| nchr == KeyEvent.VK_ESCAPE
						|| nchr == KeyEvent.VK_BEGIN
						|| nchr == KeyEvent.VK_END
						|| nchr == KeyEvent.VK_ENTER)) {

			if (!allSelected && currentLenght == datalength ) {
				((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(false);
				mFrm.getToolkit().beep();
				optCfm.showInvalidEntryDialog("<html><p>" + cmpntTree[hl].getDescription()
						+ " Max Length: " + datalength + "</html>", cmp);
				e.consume();
				((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(true);
				return false;
			}

			if (dataType.equals("INT")) {
				if (!(Character.isDigit(nchr) || nchr == KeyEvent.VK_MINUS)) {
					((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(false);
					mFrm.getToolkit().beep();
					optCfm.showInvalidEntryDialog("<html><p>" + cmpntTree[hl].getDescription()
							+ " must be numeric </html>", cmp);
					e.consume();
					((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(true);
					return false;
				}
			}

			if (dataType.equals("FLT")) {
				if (!(Character.isDigit(nchr) || nchr == KeyEvent.VK_PERIOD 
						|| nchr == KeyEvent.VK_COMMA  || nchr == KeyEvent.VK_MINUS)) {
					((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(false);
					mFrm.getToolkit().beep();
					optCfm.showInvalidEntryDialog("<html><p>"+ cmpntTree[hl].getDescription()
							+ " must be numeric </html>", cmp);
					e.consume();
					((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(true);
					return false;
				}
			}
		}
		return true;
	}
	

	/**
	 * <h2><code>isVaildValue</code></h2>
	 *
	 * @return - true if character is valid
	 *  
	 * validate character
	 *  
	 */
	
	  public boolean isVaildValue(String value, int hl) {
		CDialog optCfm = new CDialog(getWindowFrame(), cntr);
		JComponent cmp = cmpntTree[hl].getComponentObject();
		String dataType = cmpntTree[hl].getDataType();
		if(value == null) value = "";
		
		if (value == null || value.equals("")) 
			if (dataType.equals("INT") || dataType.equals("FLT"))
				value = "0";

		if (dataType.equals("INT") && !DataSet.isInteger(value)) {
			((StandardComponentLayout) cmp).setValid(false);
			mFrm.getToolkit().beep();
			optCfm.showInvalidEntryDialog("<html><p>" + cmpntTree[hl].getDescription()
					+ " must be numeric </html>", cmp);
			((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(true);
			return false;

		} else if (dataType.equals("FLT") && !DataSet.isDouble(value)) {
			((StandardComponentLayout)cmp).setValid(false);
			mFrm.getToolkit().beep();
			optCfm.showInvalidEntryDialog("<html><p>" + cmpntTree[hl].getDescription()
					+ " must be numeric </html>", cmp);
			((StandardComponentLayout)cmp).setValid(true);
			return false;

		} else if (dataType.equals("DAT") && !value.equals("") &&!DataSet.isDate(value)) {
			((StandardComponentLayout)cmp).setValid(false);
			mFrm.getToolkit().beep();
			optCfm.showInvalidEntryDialog("<html><p>"+ cmpntTree[hl].getDescription()
					+ " must be a valid date </html>", cmp);
			((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(true);
			return false;
		
		} else if (dataType.equals("TIM") && !value.equals("") &&!DataSet.isTime(value)) {
			((StandardComponentLayout)cmp).setValid(false);
			mFrm.getToolkit().beep();
			optCfm.showInvalidEntryDialog("<html><p>" + cmpntTree[hl].getDescription()
					+ " must be a valid Time as (hh:mm:ss) </html>", cmp);
			((StandardComponentLayout)cmpntTree[hl].getComponentObject()).setValid(true);
			return false;
		
		} else
			return true;
	}

	  
	/**
	 * <h2><code>loadAdminScript</code></h2>
	 * 
 	 * 
	 */
	private void loadAdminScript (String scriptToRun, String initMethod, String scriptName) {
		DataSet scrnSt = new DataSet();
		scrnSt.putStringField("[SCREEN_SCRIPT/]", scriptToRun);
		scrnSt.putIntegerField("[ScriptAccessLevel/]", 3);		
		final WindowFrame adminWindow = new WindowFrame(cntr);
   		scrnSt = adminWindow.setWindow(scrnSt, this, "", 0);
   		adminWindow.showWindow();
		if(initMethod != null) {
			scrnSt.putStringField("MenuXML", cntr.menuScript);
			scrnSt.putStringField("MenuItem", scriptName);
			adminWindow.startWindowFrame(scrnSt, initMethod);
		}
	}

	
	public void locateCursor(){	}
	public void lostOwnership(Clipboard clip, Transferable transferable) {}
		
	
	/**
	 * <h2><code>postServerRequestImed</code></h2>
	 * <p> Request action from the server</p>
	 * 
	 *  @return status from the server.
	 *  
	 */	
	private boolean postServerRequestImed() {
		getUnprotectedFields();
		dSet.clearMessagePump();
		dSet.clearObjectPropertyPump();
		if (more != null && more.isEnabled())
			dSet.setMoreButtonStatus(true);
		else
			dSet.setMoreButtonStatus(false);

		dSet = cntr.postServerRequestImed(dSet);
		updateObjectProperty(dSet);
		
		if (more != null)
			more.setEnabled(dSet.getMoreButtonStatus());

		return updateMessagePanel(dSet);
	}	
	
	
	private void postServerRequestLazy(ActionEvent src) {
		getUnprotectedFields();
		dSet.clearMessagePump();
		dSet.clearObjectPropertyPump();
		if (more != null && more.isEnabled())
			dSet.setMoreButtonStatus(true);
		else
			dSet.setMoreButtonStatus(false);

		cntr.postServerRequestLazy(this, src, dSet);
	}
	

	/**
	 * <h2><code>preProcessParameters</code></h2>
	 *
	 *   Load the Parameters from the Applet Table
	 *  
	 */
	private DataSet preProcessParameters(DataSet parameterDataSet) {
		
		for (int x = 0; x <= totObj; x++) {
			String object = cmpntTree[x].getObjectName(),
			       field = cmpntTree[x].getFieldName(),
	               fieldParameter =  cmpntTree[x].getFieldParameterName();
			 
			if (object != null && field != null && fieldParameter != null ) { 
				Object value = parameterDataSet.get(fieldParameter);   
				if(value != null && !value.equals("")) {
					parameterDataSet.put(field, value);
					parameterDataSet.remove(fieldParameter);
				}
			}
		}
		return parameterDataSet;
	}
	
	
	/**
	 * <h2><code>sendTableToWindow</code></h2>
	 *
	 * Load the Screen from the Applet Table
	 *  
	 */
	private void sendTableToWindow(String action) {
		for (int x = 0; x <= totObj; x++) {
			String object = cmpntTree[x].getObjectName(),
			       field = cmpntTree[x].getFieldName();
	
			if (object != null && cmpntTree[x].getComponentObject() 
                                      instanceof StandardComponentLayout)  
				((StandardComponentLayout) cmpntTree[x].getComponentObject()).populateComponent(action, field, dSet);
		   
		}
	}
	
	/**
	 * <h2><code>sendWindowToTable</code></h2>
	 *
	 * Load the dSet from the Screen
	 *  
	 */
	private void sendWindowToTable(String action) {
		String object, field;

		for (int x = 0; x <= totObj; x++) {
			object = cmpntTree[x].getObjectName();
			field = cmpntTree[x].getFieldName();
			if (object != null && cmpntTree[x].getComponentObject() 
                    instanceof StandardComponentLayout)  
				dSet = ((StandardComponentLayout) cmpntTree[x].getComponentObject())
				                       .populateDataSet(action, field, dSet);
		}
	}

	
	/**
	 * <h2><code>setDesignPreviewWindow</code></h2>
	 *
	 *  
	 */
	public JPanel setDesignPreviewWindow(DataSet script) {
		if (script != null) {
			itmAcsLvl = 3;
			script.put(WindowItem.ACTION, WindowItem.GET_SCRIPT);
					buildMainPanel(script);
					mPnl.setPreferredSize(new Dimension(
							script.getIntegerField(WindowItem.WIDTH), 
							script.getIntegerField(WindowItem.HEIGHT)));
					
			return mPnl;		
		} else {
			return null;
		}
		
	}


	/**
	 * <h2><code>setEditPreviewWindow</code></h2>
	 *
	 *  
	 */
	public void setEditPreviewWindow(DataSet script) {
		if (script != null) {
			mFrm = new JFrame();
			if (checkSecurity())
				clpbrd = mFrm.getToolkit().getSystemClipboard();
			mFrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			itmAcsLvl = 3;
			script.put(WindowItem.ACTION, WindowItem.GET_SCRIPT);
					buildMainPanel(script);
					mFrm.setName("WindowAccesPreview");
					mFrm.setTitle(scrnTtle);
					mFrm.getContentPane().setLayout(new BorderLayout());
					mFrm.getContentPane().add(mainSpltPn, BorderLayout.CENTER);	
					mFrm.pack();
					mFrm.setSize(script.getIntegerField(WindowItem.WIDTH), 
							script.getIntegerField(WindowItem.HEIGHT));

					Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
					mFrm.setLocation((d.width - mFrm.getSize().width) / 2,
							(d.height - mFrm.getSize().height) / 2);
			
					mFrm.setVisible(true);
		}		
	}


	/**
	 * <h2><code>setWindow</code></h2>
	 *
	 * <p>set window size and call create window method</p> 
	 *  
	 */
	public DataSet setWindow(DataSet script, WindowFrame cw, String returnfield, int returnlevel) {
		this.calngFrm = cw;
		this.rtnField = returnfield;
		this.rtFldLvl = returnlevel;
		setWindow(cw.getMenuObject(), script);
		if(calngFrm != null)
		mFrm.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {
			 	if(mFrm.isActive())	calngFrm.doFindClick();
			 	calngFrm.mFrm.requestFocus();
			}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});
		return dSet;
	}


	/**
	 * <h2><code>setWindow</code></h2>
	 *
	 * <p>set window size and call create window method</p> 
	 *  
	 */
	public void setWindow(final MenuFrame menuAccess, DataSet script) {
		this.mMnu = menuAccess;
		mFrm = new JFrame();
		if (checkSecurity())
			clpbrd = mFrm.getToolkit().getSystemClipboard();
	
		mFrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		cntr.showWebStatus("Opening webBoltOnsServer.ServletConnetor:"+ script);		
		itmAcsLvl = script.getIntegerField("[ScriptAccessLevel/]");
		script.put(WindowItem.ACTION, WindowItem.GET_SCRIPT);
		script = cntr.postServerRequestImed(script);
		if (script != null) {
				buildMainPanel(script);
				mFrm.setName("WindowFrame");
				mFrm.setTitle(scrnTtle);
				height = script.getIntegerField(WindowItem.WIDTH);
				width = script.getIntegerField(WindowItem.HEIGHT);
				mFrm.setSize(height, width);			
				startWindowFrame(dSet, null);				
		}		
		
		cntr.showWebStatus("Done");
	}


	/**
	 * <h2><code>showNoServerTips</code></h2>
	 *
	 * <p> remove the 'server tips' </p>
	 *  
	 */
	private void showNoServerTips() {
		srvtips = false;
		for (int x = 0; x < cmpntTree.length; x++) {
			if (cmpntTree[x].getComponentObject() == null) {
				continue;
			}
			String parentObject = cmpntTree[cmpntTree[x]
					.getParentHL()].getObjectName();
			if (cmpntTree[x].getObjectName().equals(
					WindowItem.BUTTON_OBJECT)) {
				((JButton) cmpntTree[x].getComponentObject())
						.setToolTipText(null);

			} else if (cmpntTree[x].getObjectName().equals(
					WindowItem.RADIO_BUTTON_OBJECT)) {
				((JRadioButton) cmpntTree[x].getComponentObject())
						.setToolTipText(null);

			} else if (cmpntTree[x].getObjectName().equals(
					WindowItem.TABLE_TEXTFIELD_OBJECT)
					|| cmpntTree[x].getObjectName().equals(
							WindowItem.TABLE_COMBOBOX_OBJECT)) {
				int table = cmpntTree[x].getParentHL();
				String fld = cmpntTree[x].getFieldName();
				if (parentObject.equals(WindowItem.TABLE_OBJECT)) {
					((CTableContainer) cmpntTree[table]
							.getComponentObject()).setToolTipText(fld, null);
				} else {
					((CTreeTableContainer) cmpntTree[table]
							.getComponentObject()).setToolTipText(fld, null);
				}

			} else {
				JComponent compObject = ((JComponent) cmpntTree[x]
						.getComponentObject());
				compObject.setToolTipText(null);
			}
		}
	}
	
	
	/**
	 * <h2><code>showServerTips</code></h2>
	 *
	 * <p> Show Servlet Tips </p>
	 *  
	 */
	private void showServerTips() {
		srvtips = true;
		for (int x = 0; x < cmpntTree.length; x++) {
			if (cmpntTree[x].getComponentObject() == null) {
				continue;
			}
			String parentObject = cmpntTree[cmpntTree[x]
					.getParentHL()].getObjectName();
			if (cmpntTree[x].getObjectName().equals(
					WindowItem.BUTTON_OBJECT)) {
				if (!cmpntTree[x].getLink().equals("")) {
					((JButton) cmpntTree[x].getComponentObject())
							.setToolTipText("webBoltOnsServer Link: "
									+ cmpntTree[x].getLink());
				} else if (!cmpntTree[x].getMethod().equals("")) {
					((JButton) cmpntTree[x].getComponentObject())
							.setToolTipText("webBoltOnsServer Method: "
									+ cmpntTree[x].getMethod());
				}

			} else if (cmpntTree[x].getObjectName().equals(
					WindowItem.RADIO_BUTTON_OBJECT)) {
				String fld = cmpntTree[cmpntTree[x]
						.getParentHL()].getFieldName();
				String val = cmpntTree[x].getDescription();
				((JRadioButton) cmpntTree[x].getComponentObject())
						.setToolTipText(fld + "=\"" + val + "\"");

			} else if (cmpntTree[x].getObjectName().equals(
					WindowItem.TABLE_TEXTFIELD_OBJECT)
					|| cmpntTree[x].getObjectName().equals(
							WindowItem.TABLE_COMBOBOX_OBJECT)) {
				int table = cmpntTree[x].getParentHL();
				String fld = cmpntTree[x].getFieldName();
				if (parentObject.equals(WindowItem.TABLE_OBJECT)) {
					((CTableContainer) cmpntTree[table]
							.getComponentObject()).setToolTipText(fld, fld);
				} else {
					((CTreeTableContainer) cmpntTree[table]
							.getComponentObject()).setToolTipText(fld, fld);
				}

			} else {

				JComponent compObject = ((JComponent) cmpntTree[x]
						.getComponentObject());
				compObject.setToolTipText(cmpntTree[x].getFieldName());
			}
		}
	}

	

	/**
	 * <h2><code>showWindow</code></h2>
	 *
	 * <p>Display the window</p> 
	 *  
	 */
	public void showWindow() {
		mFrm.getContentPane().setLayout(new BorderLayout());
		mFrm.getContentPane().add(mainSpltPn, BorderLayout.CENTER);	
		mFrm.pack();
		mFrm.getContentPane().validate();
		mFrm.setResizable(false);
		dock.setVisible(true);
		mFrm.setSize(height, width);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		mFrm.setLocation((d.width - mFrm.getSize().width) / 2,
				(d.height - mFrm.getSize().height) / 2);
		mFrm.setVisible(true);
	
		mainSpltPn.setDividerLocation(mFrm.getHeight() - msgGap);
		exMsg.setIcon(cntr.upIcon);
	}
				

	
	
	/**
	 * <h2><code>fireWindowCalled</code></h2>
	 * 
 	 * 	
	 *  execute any init-Methods for the newly created frame
	 *  
	 */
	public void startWindowFrame(DataSet hs, String calledmethod) {
		actionClear();
		if (initMthd != null && !initMthd.equals("")) {
			dSet.put(WindowItem.METHOD, initMthd);
			dSet.put(WindowItem.ACTION, WindowItem.FIND_RECORD);
			preProcessParameters(dSet);
			postServerRequestImed();
			sendTableToWindow(null);
		} 

		if (calledmethod != null && !calledmethod.equals("")) {
			dSet.put(WindowItem.METHOD, calledmethod);
			dSet.put(WindowItem.ACTION, WindowItem.FIND_RECORD);
			preProcessParameters(hs);
			postServerRequestImed();
			sendTableToWindow(null);
		}
		locateCursor();
		if(clear != null) clear.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}	
	
	
	
	/**
	 * <h2><code>treeExpand_actionPerformed</code></h2>
	 *
	 */
	public Vector treeExpand_actionPerformed(String method, String action,
			String tableName, String parentItem) {
		cntr.showWebStatus("Opening webBoltOnsServer.ServletConnetor:" + method);
		dSet.putStringField("[Parent_Tree_Expand/]", parentItem);
		dSet.put(WindowItem.METHOD, method);
		dSet.put(WindowItem.ACTION, action);
		postServerRequestImed();
		cntr.showWebStatus("Done");
		return dSet.getTableVector(tableName);
	}


	/**
	 * <h2><code>unWndFcs</code></h2>
	 *
	 * <p> set focus and ensure the focused 
	 * field is not behind any tabs</p>
	 */
	private void unWndFcs(int hl) {
		int chl = cmpntTree[hl].getParentHL();
		int phl = cmpntTree[chl].getParentHL();
		((Component) cmpntTree[hl].getComponentObject())
				.requestFocus();
		while (phl > 0) {
			if (cmpntTree[phl].getObjectName().equals(WindowItem.TABGROUP_OBJECT))
				((JTabbedPane) cmpntTree[phl].getComponentObject()).setSelectedComponent(
						                     ((Component) cmpntTree[chl].getComponentObject()));
			chl = phl;
			phl = cmpntTree[chl].getParentHL();
		}
		((Component) cmpntTree[hl].getComponentObject()).requestFocus();
	}


	/**
	 * <h2><code>updateMessagePanel</code></h2>
	 *
	 */
	private boolean updateMessagePanel(DataSet dataSet) {
		msgPnl.removeAll();
		msgPnl.setBackground(cntr.bgColor);
		boolean lvl30 = true;
		if (!dataSet.isPumpClear()) {
			Enumeration messagesDetails = dataSet.getMessageList();
			while (messagesDetails.hasMoreElements()) {
				String text, field, level, type;
				String[] msg = (String[]) messagesDetails.nextElement();				
				
				if(msg.length == 2 && cntr.getMessage(msg[0]) != null) {
					field = msg[1];
					msg = cntr.getMessage(msg[0]);
					text = msg[0];
					level = msg[1];
					type = msg[2];
				} else {	
					text = msg[0];
					level = msg[1];
					field = msg[2];
					type = msg[3];
				}
			
				if (lvl30 &&  level.equals("30")) lvl30 = false;
				
				if (type != null  && type.equals("Pop-Up")) 
					buildMessagePopup(text, field);					
				else
					buildMessageLine(text, type, level, field);
				
			}
		}
		msgPn.validate();
		return lvl30;
	}


	/**
	 * <h2><code>updateObjectProperty</code></h2>
	 *
	 * <p> Adjust any screen props sent by the server </p>
	 *  
	 */
	private void updateObjectProperty(DataSet dataSet) {
		// adjust all object properties set by the server
		String field;
		if (!dataSet.isObjectPropertyPumpClear()) {
			Enumeration objectPropertyDetails = dataSet.getObjectPropertyList();
			while (objectPropertyDetails.hasMoreElements()) {
				String[] property = (String[]) objectPropertyDetails.nextElement();
				for (int x = 0; x <= totObj; x++) {
					field = cmpntTree[x].getFieldName();
					if (field != null  && field.equals(property[0]))
						if(cmpntTree[x].getComponentObject() instanceof StandardComponentLayout) 		
							((StandardComponentLayout)cmpntTree[x].getComponentObject()).setProperty(
									property[1], property[2]);
				}
			}
		}
	}


	/**
	 * <h2><code>validateWiondow</code></h2>
	 * 
	 * <p>Validate Window Components</p>
	 * 
	 *  @return - true - all are valid data types
	 *   		- false - invalid data
	 */
	private boolean validFrm(JComponent src) {		
		for (int x = 0; x <= totObj; x++) {
			if (cmpntTree[x].getComponentObject() != null && 
						cmpntTree[x].getComponentObject() instanceof StandardComponentLayout) {
					if(! ((StandardComponentLayout) cmpntTree[x].getComponentObject()). 
												validateComponent("Edit", cmpntTree[x].getFieldName())) {
						src.setCursor(new Cursor(Cursor.HAND_CURSOR));
						
						return false;
					}
				}
		}
		return true;
	}
}