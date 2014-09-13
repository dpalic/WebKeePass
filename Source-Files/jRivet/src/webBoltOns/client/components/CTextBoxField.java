package webBoltOns.client.components;

/*
 * $Id: CTextBoxField.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CTextBoxField extends JTextField 
   implements StandardComponentLayout, KeyListener, FocusListener, MouseListener {

	private static final long serialVersionUID = -3343059845815975451L;
	protected Hashtable<Object, Object> keys;
	protected JLabel textBoxLabel;
	protected WindowItem comp;
	protected WindowFrame mFrm;
	protected AppletConnector cnct;
	protected String commitValue;
	protected String dataType;
	protected boolean validValue = true;
	protected ImageIcon image; 

	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		mFrm = mainFrame;
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		dataType = comp.getDataType();
		keys = new Hashtable<Object, Object>();
		setName(Integer.toString(comp.getObjectHL()));
		setFont(cnct.standardFont);
		setBackground(Color.WHITE);
		image = cnct.getImageIcon("err.gif");
		setDoubleBuffered(true);
		
		if (parentItem.getComponentObject() instanceof CPanelContainer)
			buildPanelTextBox(parentItem);
		else
			buildTableTextBox();
	}
	
	
		
	protected void commitEditing(String value) {
		if (value != null)
			setText(value);
		else
			setText(null);

		commitValue = getText();
		
	}
	

	protected void revertEditing() {
		if (commitValue != null)
			setText(commitValue);
		else
			setText(null);
		
		validValue = true;
	
	}

	
	/**
	 * <h2><code>setText</code></h2>
	 * 
 	 * 	
	 * Format a text field
	 * 
	 * @param String value - the string to be formatted
	 * @param int hl - object tree index
	 * 
	 * @return the formatted text string
	 *  
	 */
	public void setText(String value) {
		if(value == null || value.equals("")) {
			super.setText(null);
			return;
		}
		
		String editmask = comp.getEditMask();
		int len = comp.getDataLength();
		
		if (dataType.equals("INT")) { 
			super.setText(DataSet.formatIntegerField(value, editmask));
			return;
			
		} else if ((dataType.equals("FLT"))) {
			super.setText(DataSet.formatDoubleField(value, editmask));
			return;
			
		} else if (dataType.equals("DAT")) {
			super.setText(DataSet.formatDateField(value, cnct.dateFormat));
			return;

		} else if (dataType.equals("TIM")) {
			super.setText(DataSet.formatTimeField(value, "kk:mm:ss"));
			return;
			
		} else {
			
			if(comp.isZeroFilled())  
				super.setText(DataSet.zeroFillField(value, len));
			else
				super.setText(DataSet.formatCharField(value, editmask));	
		}	
	}

	
	public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {
		commitEditing(getText());
		String value = getText();
		if (value != null && !value.equals("")) {
			
			if(cnct.strictEncoding && comp.isEncrypted())
				dataSet.putStringField(editorName, cnct.encrypt(value));
			else if (dataType.equals("DAT"))
				dataSet.putStringField(editorName, DataSet.formatDateField(value, cnct.serverDateFormat));
			else if (dataType.equals("TIM"))
				dataSet.putStringField(editorName, DataSet.formatTimeField(value, "kkmmss"));
			else	
				dataSet.putStringField(editorName, value);

			
		} else {
			dataSet.put(editorName, "");
		}

		return dataSet;
	}


	
	public String getString() {
		commitEditing(getText());
		if (commitValue != null) return commitValue; 
		return "";
	}
	
 	
	
	public void populateComponent(String action, String editorName, DataSet dataSet) {
		validValue = true;
		if(cnct.strictEncoding && comp.isEncrypted())
			commitEditing(cnct.decrypt(dataSet.getStringField(editorName)));
		else
			commitEditing(dataSet.getStringField(editorName));
	}

	public void clearComponent(String defaultValue) {
		validValue = true;
		if(defaultValue != null && !defaultValue.equals("") )
			commitEditing(defaultValue);
		else if (dataType.equals("INT") || dataType.equals("FLT"))
			commitEditing("0");
		else
			commitEditing(null);
		updateUI();
	}

	
	private void buildPanelTextBox(WindowItem parentComponent) {
		textBoxLabel = new JLabel(comp.getDescription());
		textBoxLabel.setFont(cnct.headerFont);

		// 1a-integer format
		if (dataType.equals("INT")) {
			if (comp.getEditMask().equals(""))
				comp.setEditMask("0");
			// 1a-float format
		} else if (dataType.equals("FLT")) {
			if (comp.getEditMask().equals(""))
				comp.setEditMask(DataSet
						.createFloatMask(comp.getDecimals()));
		}

		setHorizontalAlignment(comp.getCellAlignment());
		setColumns(comp.getLength());
		setSelectionColor(Color.cyan);
		setFont(cnct.standardFont);
		textBoxLabel.setFont(cnct.headerFont);
		comp.setComponentObject(this);
		
		if (comp.isProtected()) {
			setEditable(false);
			setFocusable(false);
			setBackground(new Color(245, 245, 245));
		} else {
			addKeyListener(this);
			addFocusListener(this);
			if (!comp.getLink().equals("") || comp.getDataType().equals("DAT") || comp.getDataType().equals("FLT")) {
				textBoxLabel.setText(("<html><font color=\"blue\"><u>"
						                  + comp.getDescription() + "</u></font></html>"));
				textBoxLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				textBoxLabel.addMouseListener(this);
			}
		}
		
		
		if (comp.isHidden()) {
			setVisible(false);
			textBoxLabel.setVisible(false);
		} else {
			setVisible(true);
			textBoxLabel.setVisible(true);	
		}
		
		
		// now place the field on the panel
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentComponent.getComponentObject()).addRight(textBoxLabel, 4);
			((CPanelContainer) parentComponent.getComponentObject()).addRight(this, 5);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentComponent.getComponentObject()).addRight(textBoxLabel, 2);
			((CPanelContainer) parentComponent.getComponentObject()).addRight(this, 3);
		} else if (comp.getPosition().equals(WindowItem.LEFT)) {
			((CPanelContainer) parentComponent.getComponentObject()).addLeft(textBoxLabel, 0);
			((CPanelContainer) parentComponent.getComponentObject()).addRight(this, 1);
		} else {
			((CPanelContainer) parentComponent.getComponentObject()).addc(textBoxLabel);
			((CPanelContainer) parentComponent.getComponentObject()).addc(this);
		}
	}

	private void buildTableTextBox() {
		
		textBoxLabel = null;
		setName(Integer.toString(comp.getObjectHL()));

		// 1a-integer format
		if (dataType.equals("INT")) {
			comp.setEditMask("0");
			// 1a-float format
		} else if (dataType.equals("FLT")) {
			comp.setEditMask(DataSet.createFloatMask(comp.getDecimals()));
		}

		setHorizontalAlignment(comp.getCellAlignment());
		setColumns(comp.getLength());
		setSelectionColor(Color.cyan);
		setFont(cnct.standardFont);
		comp.setComponentObject(this);

		// is the text field editable?
		if (!comp.isProtected()) {
			addKeyListener(this);
			if (!comp.getLink().equals("") || dataType.equals("DAT") || dataType.equals("FLT")) 
				addMouseListener(this);
			
		}
	}

	
	public void initializeComponentUI() {}

	
	public boolean isSelected(){
		if(getSelectedText() == null)
			return false;
		else if(getText() == null)
			return false;
		else if (!getSelectedText().equals(getText()))
			return false;
		else return true;
	}
	
	public boolean isValid() {
		 
		return validValue;
	}
	
	public String getSelectedComponentItem() {
		return (String) getText();
	}

	
	public boolean validateComponent(String action, String editorName) {
		 
		if(textBoxLabel != null)
			validValue =  mFrm.isVaildValue(getText(), comp.getObjectHL());
		if(!validValue) repaint();
			 
		return validValue;
	}


	public void setProperty(String propertyName, String propertyValue) {

		if (propertyName.equals(WindowItem.PROTECTED)) {
			if (propertyValue.equals("false")) {
				setEditable(true);
				setFocusable(true);
				setBackground(Color.WHITE);
			} else {
				setEditable(false);
				setFocusable(false);
				setBackground(new Color(245, 245, 245));
			}
		} else if(propertyName.equals(WindowItem.HIDDEN)) {
			if (propertyValue.equals("false")) {
				setVisible(true);
				textBoxLabel.setVisible(true);
			} else {
				setVisible(false);
				textBoxLabel.setVisible(false);	
			}
		}
		updateUI();
	}
	
	
	
	public boolean locateCursor() {
		return true;
	}

	public void setValid(boolean valid) {
		if(validValue != valid) repaint();
		this.validValue = valid;
	}

	
	public  void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	 	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	 	if(image != null && !validValue) {
			int x = 0; 	
			if(this.getHorizontalAlignment() == JTextField.LEFT)
				x = g.getClipBounds().width - image.getIconWidth() - 2;	
				g2.drawImage(image.getImage(),
			   		 x , 0, image.getIconWidth(),
					 	    image.getIconHeight(), 
					 	    image.getImageObserver());
		}
	}

	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {	 
			commitEditing(getText());
			 
		} else if (e.getKeyCode() == 27) {
			return;
		} else if (e.getModifiers() == 0 && e.getKeyCode() == 115) {
			if (!comp.getLink().equals("")) {
				if (textBoxLabel != null)
					textBoxLabel.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				mFrm.actionScriptLinkPerformed(comp.getLink(), comp.getFieldName(), comp.getObjectHL());
			
				if (textBoxLabel != null)
					textBoxLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

			} else if (dataType.equals("DAT")) {
				CDialog dp = new CDialog(mFrm.getWindowFrame(), cnct);
				commitEditing(dp.showDatePickerDialog(getText(), this));
			} else if (dataType.equals("FLT")) {
				CDialog dp = new CDialog(mFrm.getWindowFrame(), cnct);
				commitEditing(dp.showCalcualtorDialog(getText(), CTextBoxField.this));
			}
		}
		
	}

	
	public void keyTyped(KeyEvent e) {
		mFrm.isVaildCharacter(e, comp.getObjectHL(), isSelected());
	}



	public void focusGained(FocusEvent e) {
		selectAll();
		setBackground(cnct.crsColor);
		mFrm.lfEnable = true;
		validValue = true;
	}



	public void focusLost(FocusEvent e) {
		setBackground(Color.white);
		select(0, 0);
		if (getText() != null) {
			commitEditing(getText());
		}	
	
		mFrm.actionCommit(comp.getObjectHL());
	}



	public void mouseClicked(MouseEvent e) {
	
		if(textBoxLabel != null) 
			textBoxLabel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		else if (e.getButton() != 3)
			return;
		
		if(dataType.equals("DAT")) {
			CDialog dp = new CDialog(mFrm.getWindowFrame(), cnct);
			commitEditing(dp.showDatePickerDialog(getText(), this));
		} else if(!comp.getLink().equals("")) { 
				mFrm.actionScriptLinkPerformed(comp.getLink(), 
								comp.getFieldName(), comp.getObjectHL());
		} else if(dataType.equals("FLT")) {
			CDialog dp = new CDialog(mFrm.getWindowFrame(), cnct);
			commitEditing(dp.showCalcualtorDialog(getText(), CTextBoxField.this));
		}	
		
		if(textBoxLabel != null) 	
			textBoxLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	 

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}	
	}
	
	

