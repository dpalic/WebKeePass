package webBoltOns.client.components;

/*
 * $Id: CSearchTextField.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.swing.AbstractSpinnerModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;
import webBoltOns.dataContol.SearchTextFieldDataSet;

public class CSearchTextField extends CTextBoxField implements
		StandardComponentLayout, FocusListener, KeyListener, MouseListener {
	
	
	class IconEditor extends JLabel implements ChangeListener {
		private static final long serialVersionUID = -7828312165251238767L;

		public IconEditor(JSpinner spinner) {
			setOpaque(true);
			IconListModel myModel = (IconListModel) (spinner.getModel());
			setIcon((ImageIcon) myModel.getValue());
			spinner.addChangeListener(this);
			updateToolTipText(spinner);
		}

		protected void updateToolTipText(JSpinner spinner) {
			// Define our own tool tip text.
			IconListModel myModel = (IconListModel) (spinner.getModel());
			setToolTipText(((ImageIcon) myModel.getValue()).getDescription());
		}

		public void stateChanged(ChangeEvent e) {
			JSpinner spinner = (JSpinner) (e.getSource());
			IconListModel myModel = (IconListModel) (spinner.getModel());
			setIcon((ImageIcon) myModel.getValue());
			updateToolTipText(spinner);
		}

		public int getIndex() {
			IconListModel myModel = (IconListModel) (searchSelector.getModel());
			return myModel.index;
		}

		public void setState(int state) {
			IconListModel myModel = (IconListModel) (searchSelector.getModel());

			if (state == 40)
				myModel.setValue(myModel.getNextValue());

			else if (state == 38)
				myModel.setValue(myModel.getPreviousValue());

			else
				myModel.setValue(myModel.getValueAt(state));

			setIcon((ImageIcon) myModel.getValue());
			updateToolTipText(searchSelector);
		}

	}


	class IconListModel extends AbstractSpinnerModel implements Serializable {

		private static final long serialVersionUID = -3138454667631202233L;

		private List list;

		private int index;

	
		public IconListModel(List values) {
			if (values == null || values.size() == 0) {
				throw new IllegalArgumentException(
						"SpinnerListModel(List) expects non-null non-empty List");
			}
			this.list = values;
			this.index = 0;
		}

	
		public int getIndex() {
			return index;
		}

	
		public IconListModel(Object[] values) {
			if (values == null || values.length == 0) {
				throw new IllegalArgumentException(
						"SpinnerListModel(Object[]) expects non-null non-empty Object[]");
			}
			this.list = Arrays.asList(values);
			this.index = 0;
		}

	
		public IconListModel() {
			this(new Object[] { "empty" });
		}

	
		public List getList() {
			return list;
		}


		public void setList(List list) {
			if ((list == null) || (list.size() == 0)) {
				throw new IllegalArgumentException("invalid list");
			}
			if (!list.equals(this.list)) {
				this.list = list;
				index = 0;
				fireStateChanged();
			}
		}

	
		public Object getValue() {
			return list.get(index);
		}

	
		public Object getValueAt(int i) {
			if (i > list.size() || i < 0)
				return null;
			else
				return list.get(i);
		}

	
		public void setValue(Object elt) {
			int index = list.indexOf(elt);
			if (index == -1) {
				throw new IllegalArgumentException("invalid sequence element");
			} else if (index != this.index) {
				this.index = index;
				fireStateChanged();
			}
		}


		public Object getNextValue() {
			return (index >= (list.size() - 1)) ? list.get(0) : list
					.get(index + 1);
		}

	
		public Object getPreviousValue() {
			return (index <= 0) ? list.get(list.size() - 1) : list
					.get(index - 1);
		}

	
		Object findNextMatch(String substring) {
			int max = list.size();

			if (max == 0) {
				return null;
			}
			int counter = index;

			do {
				Object value = list.get(counter);
				String string = value.toString();

				if (string != null && string.startsWith(substring)) {
					return value;
				}
				counter = (counter + 1) % max;
			} while (counter != index);
			return null;
		}
	}
	
	
	private static final long serialVersionUID = 2994132588528581413L;

	private JLabel textBoxLabel;

	private JSpinner searchSelector;

	private JPanel fieldPanel;

	public CSearchTextField() {}

	public DataSet populateDataSet(String action, String editorName,
			DataSet dataSet) {

		String value = getText();

		if (value == null) {
			dataSet.put(editorName, null);

		} else {
			
			if(cnct.strictEncoding && comp.isEncrypted())
				value = cnct.encrypt(value);
			else if (dataType.equals("DAT"))
				value = DataSet.formatDateField(value,cnct.serverDateFormat);

			dataSet.put(editorName, new SearchTextFieldDataSet(value, dataType,
					((IconListModel) searchSelector.getModel()).getIndex()));

		}
		return dataSet;
	}

	public void populateComponent(String action, String editorName,
			DataSet dataSet) {
	}

	public void clearComponent(String defaultValue) {
		super.clearComponent(defaultValue);
		((IconEditor) searchSelector.getEditor()).setState(0);		
	}

	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {		
		mFrm = mainFrame;
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		dataType = comp.getDataType();
		keys = new Hashtable();
		setName(Integer.toString(comp.getObjectHL()));
		setFont(cnct.standardFont);
		setBackground(Color.WHITE);

		final String link = comp.getLink();
		textBoxLabel = new JLabel(comp.getDescription());
		textBoxLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		textBoxLabel.setFont(cnct.headerFont);
		textBoxLabel.setVerticalAlignment(JLabel.CENTER);
		setName(Integer.toString(comp.getObjectHL()));
		setHorizontalAlignment(comp.getCellAlignment());
		setColumns(comp.getLength());
		setSelectionColor(Color.cyan);
		setFont(cnct.standardFont);
		textBoxLabel.setFont(cnct.headerFont);
		comp.setComponentObject(this);
		setBackground(Color.WHITE);

		ImageIcon a = new ImageIcon(cnct.getImageIcon("like.gif")
				.getImage());
		a.setDescription(comp.getDescription() + cnct.getMsgText("TXT0112"));

		ImageIcon b = new ImageIcon(cnct.getImageIcon(
				"startswith.gif").getImage());
		b.setDescription(comp.getDescription() + cnct.getMsgText("TXT0113"));

		ImageIcon c = new ImageIcon(cnct
				.getImageIcon("endswith.gif").getImage());
		c.setDescription(comp.getDescription() + cnct.getMsgText("TXT0114"));

		ImageIcon d = new ImageIcon(cnct.getImageIcon("lt.gif")
				.getImage());
		d.setDescription(comp.getDescription() + cnct.getMsgText("TXT0115"));

		ImageIcon e = new ImageIcon(cnct.getImageIcon("gt.gif")
				.getImage());
		e.setDescription(comp.getDescription() + cnct.getMsgText("TXT0116"));

		ImageIcon f = new ImageIcon(cnct.getImageIcon("eq.gif")
				.getImage());
		f.setDescription(comp.getDescription() + cnct.getMsgText("TXT0117"));

		ImageIcon g = new ImageIcon(cnct.getImageIcon("ne.gif")
				.getImage());
		g.setDescription(comp.getDescription() + cnct.getMsgText("TXT0118"));

		ImageIcon[] searchIcons = { a, b, c, d, e, f, g };
		IconListModel searchOptions = new IconListModel(searchIcons);

		searchSelector = new JSpinner(searchOptions);
		searchSelector.setEditor(new IconEditor(searchSelector));
		searchSelector.setPreferredSize(new Dimension(
				(cnct.headerFont.getSize() + 22), 17));

		fieldPanel = new JPanel();
		fieldPanel.setLayout(new BorderLayout());
		fieldPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151)));

		fieldPanel.add(searchSelector, BorderLayout.WEST);
		fieldPanel.add(this, BorderLayout.CENTER);

		// 1a-integer format
		if (dataType.equals("INT")) {
			comp.setEditMask("0");
			// 1a-float format
		} else if (dataType.equals("FLT")) {
			comp.setEditMask(DataSet.createFloatMask(comp
					.getDecimals()));
		}

		addKeyListener(this);
		addFocusListener(this);

		// add the script link and make it look blue!
		if (!link.equals("")) {
			textBoxLabel.setText(("<html><font color=\"blue\"><u>"
					+ comp.getDescription() + "</u></font></html>"));
			textBoxLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			textBoxLabel.addMouseListener(this);
		}

		// now place the field on the panel
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					textBoxLabel, 4);
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					fieldPanel, 5);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					textBoxLabel, 2);
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					fieldPanel, 3);
		} else if (comp.getPosition().equals(WindowItem.LEFT)) {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(
					textBoxLabel, 0);
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					fieldPanel, 1);
		} else {
			((CPanelContainer) parentItem.getComponentObject())
					.addc(textBoxLabel);
			((CPanelContainer) parentItem.getComponentObject())
					.addc(fieldPanel);
		}
	}



	public void focusGained(FocusEvent e) {
		selectAll();
		setBackground(cnct.crsColor);
	}

	public void focusLost(FocusEvent e) {
		setBackground(Color.white);
		select(0, 0);
		if (getText() != null)
			commitEditing(getText());
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			commitEditing(getText());
		} else if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
			IconEditor i = (IconEditor) searchSelector.getEditor();
			i.setState(e.getKeyCode());
			e.consume();
		} else if (e.getKeyCode() == 27) {
			revertEditing();
			e.consume();
		} else if (e.getModifiers() == 0 && e.getKeyCode() == 115) {
			if (!comp.getLink().equals("")) {
				textBoxLabel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				mFrm.actionScriptLinkPerformed(comp.getLink(), comp.getFieldName(),comp.getObjectHL());
				textBoxLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (dataType.equals("DAT")) {
				CDialog datePicker = new CDialog(mFrm.getWindowFrame(), cnct);
				String oldDate = getText();
				String newdate = datePicker.showDatePickerDialog(oldDate, searchSelector );
				commitEditing(newdate);
			}
		}
		
	}

	

	public void keyTyped(KeyEvent e) {
		mFrm.isVaildCharacter(e, comp.getObjectHL(), isSelected());
	}

	public void mouseClicked(MouseEvent e) {
		textBoxLabel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		mFrm.actionScriptLinkPerformed(comp.getLink(), comp.getFieldName(), comp.getObjectHL());
		textBoxLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public void mouseEntered(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
