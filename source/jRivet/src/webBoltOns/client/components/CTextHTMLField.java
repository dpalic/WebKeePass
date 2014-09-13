package webBoltOns.client.components;

/*
 * $Id: CTextHTMLField.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.EditorKit;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CTextHTMLField extends JPanel implements StandardComponentLayout,	 
												ActionListener {

	private static final long serialVersionUID = 1064767542094191962L;
	protected TextMonitor mainT;
	protected TextMonitor srcT;
	protected CardLayout card;
	protected WindowItem comp;
	protected AppletConnector cnct;
	protected WindowFrame mFrm;
	protected JToolBar toolBar;
	protected CDialog dialog;
	protected JPanel body;
	private boolean source = false;
	private JScrollPane manP;
	private JScrollPane srcP;
	
	
	public CTextHTMLField() {}

	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		mFrm = mainFrame;
		card = new CardLayout();
		body = new JPanel(card);
		

		mainT = new TextMonitor();
		mainT.setFont(cnct.standardFont);		
		manP = new JScrollPane(mainT);				 
		manP.setPreferredSize(new Dimension(thisItem.getWidth() * 20, thisItem.getHeight() * 20));
		manP.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(thisItem.getDescription()),BorderFactory.createEmptyBorder(5, 5, 5, 5)),
				manP.getBorder()));
		body.add(manP, "Main");
		
		if (!comp.getDataType().equals("HTM")) {  
			mainT.setContentType("text/plain");
			srcT = null;
			srcP = null;					
		}  else  { 
			mainT.setContentType("text/html");
			srcT = new TextMonitor();
			srcT.setFont(cnct.standardFont);
			srcT.setContentType("text/plain");
			srcT.setBackground(Color.lightGray);	
			srcP = new JScrollPane(srcT);				 
			srcP.setPreferredSize(new Dimension(thisItem.getWidth() * 20, thisItem.getHeight() * 20));
			srcP.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(thisItem.getDescription()),BorderFactory.createEmptyBorder(5, 5, 5, 5)),
				srcP.getBorder()));	
			body.add(srcP, "Source");
		}
				
		setLayout(new BorderLayout());
		add(body, BorderLayout.CENTER);
		setName(Integer.toString(comp.getObjectHL()));
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 4);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 2);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(this, 0);
		}

		if (comp.isProtected()) {
			setIsEditable(false);
			setFocusable(false);
			setBackground(new Color(225, 225, 225));
		}
	}

	private JToolBar buildToolBar() {
		JButton menuItem;
		JToolBar tb = new JToolBar();
		
		menuItem = cnct.buildFancyButton(cnct.getMsgText("TXT0121"), "eCut.gif", null, ' ');
		menuItem.addActionListener(mainT.getActionMap().get(DefaultEditorKit.cutAction));
		tb.add(menuItem, null);
		tb.add(new JToolBar.Separator());
		
		menuItem = cnct.buildFancyButton(cnct.getMsgText("TXT0122"), "eCopy.gif", null, ' ');
		menuItem.addActionListener(mainT.getActionMap().get(DefaultEditorKit.copyAction));
		tb.add(menuItem, null);
		tb.add(new JToolBar.Separator());
		
		menuItem = cnct.buildFancyButton(cnct.getMsgText("TXT0123"), "ePaste.gif", null, ' ');
		menuItem.addActionListener(mainT.getActionMap().get(DefaultEditorKit.pasteAction));
		tb.add(menuItem, null);
		tb.add(new JToolBar.Separator());
		
		if(comp.getDataType().equals("HTM")) {
			menuItem = cnct.buildFancyButton(cnct.getMsgText("TXT0124"), "eSource.gif", null, ' ');
			menuItem.addActionListener(this);
			tb.add(menuItem, null);
			tb.add(new JToolBar.Separator());
		}
		
		
		tb.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));

		return tb;
	}
	
	
	private void toggleSource() {
		if(source) {
			mainT.setText(srcT.getText());	
			srcT.setEditable(false);
			mainT.setEditable(true);
			card.show(body,"Main");
			source = false;
		} else { 
			srcT.setText(mainT.getText());
			mainT.setEditable(false);
			srcT.setEditable(true);
			card.show(body,"Source");
			source = true;
		}
		setScrollPosition(0);
	}
 
	public String getString() {
		return  getText();
	}
	
	
	public void setToolTipText(String tip) {
		mainT.setToolTipText(tip);
		if(srcT != null)
			srcT.setToolTipText(tip);
	}

	public void setIsEditable(boolean edit) {
		mainT.setEditable(edit);
	}

	public void setText(String text) {
		mainT.setText(text);
		if(srcT != null)
			srcT.setText(text); 

	}

	public String getText() {
		if(srcT != null && source) 
			toggleSource();
		
		return mainT.getText();	
	}

	public void setScrollPosition(int position) {
		mainT.setCaretPosition(position);
	}

	public DataSet populateDataSet(String action, String editorName,
			DataSet dataSet) {
		dataSet.putStringField(editorName, getText());
		return dataSet;
	}

	public String getSelectedComponentItem() {
		return getText();
	}

	public void populateComponent(String action, String editorName, DataSet dataSet) {
		setText(null);
		setText(dataSet.getStringField(editorName));
		setScrollPosition(0);
	}

	public void clearComponent(String defaultValue) {
		setText(defaultValue);
	}

	public void initializeComponentUI() {
		toolBar = buildToolBar();
		add(toolBar, BorderLayout.NORTH);

	}

	public boolean validateComponent(String action, String editorName) {
		return true;
	}

	public void setProperty(String propertyName, String propertyValue) {}

	

	class TextMonitor extends JTextPane  {
		private static final long serialVersionUID = 7642878086874787221L;
		private int m_xStart = -1;
		private int m_xFinish = -1;
		
		TextMonitor(){
			super();
			addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					if (m_xStart >= 0 && m_xFinish >= 0)
						if (getCaretPosition() == m_xStart) {
							setCaretPosition(m_xFinish);
							moveCaretPosition(m_xStart);
						} else {
							select(m_xStart, m_xFinish);
						}
				}

				public void focusLost(FocusEvent e) {
					setMonitor();
				}
			});
		}
		
		public void setMonitor() {
			m_xStart = getSelectionStart();
			m_xFinish = getSelectionEnd();
		}		
		
		public int getMonitorStart() {
			return m_xStart;
		}
		
		public int getMonitorFinish() {
			return m_xFinish;
		}
		
		public void setMonitorDocumentType(DefaultStyledDocument doc, EditorKit ek){
			setDocument(doc);
			setEditorKit(ek);
		}
		
		public void setMonitor(int sx, int fx) {
			m_xStart = sx;
			m_xFinish = fx;
		}
		
	}
  	
	
	public void setValid(boolean invalid) {}
	
	public boolean locateCursor() {
		return false;
	}

	public void actionPerformed(ActionEvent e) {
		toggleSource();
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				mainT.requestFocusInWindow();
			}} );	
	}
	
 }