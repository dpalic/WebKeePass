package webBoltOns.client.components;

/*
 * $Id: CListBoxField.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem; 
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CListBoxField extends JPanel implements
						StandardComponentLayout, FocusListener, KeyListener {
	
	public class ListItem implements Serializable {
		String key, description, iconName;
		private static final long serialVersionUID = -3046738153957563157L;
		
		public ListItem(String k, String d, String i) {
			key = k;
			description = d;
			iconName = i;
		}

		public String getDescription() {
			return description;
		}

		public String getIconName() {
			return iconName;
		}

		public String getKey() {
			return key;
		}	
	}
	
	private static final long serialVersionUID = -6525955193141129804L;
	private WindowItem comp;
	private AppletConnector cnct;
	private String commitKey;
	private Hashtable<String, ListItem> keys;
	private JList list;
	private JScrollPane pane; 
	private DefaultComboBoxModel listModel;
	
	public CListBoxField() {}

	
	public void addListItem(String key, String description, String iconName) {

		if (key == null || key.equals(""))
			key = description;

		ListItem item = new ListItem(key, description, iconName);
		keys.put(key, item);
		listModel.addElement(item);
	}
	
	public String getString() {
		return null;
	}
	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		
		listModel = new DefaultComboBoxModel();
		list = new JList(listModel);
		setBackground(cnct.bgColor);
		setTitle(comp.getDescription());
		keys = new Hashtable<String, ListItem>();
		setName(Integer.toString(comp.getObjectHL()));
		setFont(cnct.standardFont);
		setBackground(Color.WHITE);
		list.setCellRenderer(new ImageCellRenderer());
		ListSelectionModel lsm = list.getSelectionModel();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addFocusListener(this);
		list.addKeyListener(this);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(comp.getHeight());
		list.setFixedCellWidth(comp.getWidth() * 10 );

		pane = new JScrollPane (list);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setBackground(cnct.bgColor);
		
		add(pane);

		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					this, 5);

		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					this, 3);

		} else if (comp.getPosition().equals(WindowItem.LEFT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(
					this, 1);

		} else {
			((CPanelContainer) parentItem.getComponentObject()).addc(this);
		}

	}

	public void clearComponent(String defaultValue) {
		list.setSelectedIndex(0);
		pane.getVerticalScrollBar().setValue(0);
		commitKey = null;
	}

	private void commitEditing(String key) {
		if (key != null)
			list.setSelectedValue(keys.get(key), true);
		commitKey = key;
	}

	public String getSelectedComponentItem() {
		return null;
	}

	public void initializeComponentUI() {
	}

	public void populateComponent(String action, String editor, DataSet ds) {
		if(cnct.strictEncoding && comp.isEncrypted())
			commitEditing(cnct.decrypt(ds.getStringField(editor)));
		else
			commitEditing(ds.getStringField(editor));
	}

	public DataSet populateDataSet(String action, String editor, DataSet ds) {
		ListItem item = (ListItem) list.getSelectedValue();
		if (item != null)
			if(cnct.strictEncoding && comp.isEncrypted())
				ds.putStringField(editor, cnct.encrypt(item.getKey()));
			else
		        ds.putStringField(editor, item.getKey());
		else
			ds.putStringField(editor, null);
		return ds;
	}

	private void revertEditing() {
		if (commitKey != null)
			list.setSelectedValue(keys.get(commitKey), true);
		else
			list.setSelectedIndex(0);
	}

	  public void setTitle(String title) {
	    if (!title.equals("") && !title.equals(null))  {
	      Border border = BorderFactory.createLineBorder(new Color(0, 0, 150), 1);
	      TitledBorder titledBorder = new TitledBorder(border, title);
	      titledBorder.setTitleFont(cnct.standardFont);
	      titledBorder.setTitleColor(new Color(0, 0, 150));
	      setBorder(titledBorder);
	    }
	    
	  }
	

	class ImageCellRenderer implements ListCellRenderer {

		public ImageCellRenderer() {
			renderer = new JLabel();
			renderer.setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if (value == null) {
				renderer.setText("");
				renderer.setIcon(null);
			} else {
				ListItem item = (ListItem) value;
				renderer.setText(item.getDescription());
				if (item.getIconName().equals(""))
					renderer.setIcon(null);
				else
					renderer.setIcon(cnct.getImageIcon(item
							.getIconName()));
			}
			renderer.setBackground(isSelected ? SystemColor.textHighlight
					: SystemColor.text);
			renderer.setForeground(isSelected ? SystemColor.textHighlightText
					: SystemColor.textText);
			return renderer;
		}

		private JLabel renderer;

	}
	
	

	public void focusLost(FocusEvent e) {
		ListItem item = (ListItem) list.getSelectedValue();
		if (item != null)
			commitKey = item.getKey();
		else
			commitKey = null;
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			ListItem item = (ListItem) list.getSelectedValue();
			if (item != null)
				commitKey = item.getKey();
			else
				commitKey = null;

		} else if (e.getKeyCode() == 27) {
			revertEditing();
			e.consume();
		}
		
	}

	public boolean validateComponent(String action, String editorName) {return true;}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void focusGained(FocusEvent e) {}
	public void setValid(boolean invalid) {}
	public boolean locateCursor() {return false;}
	public void setProperty(String propertyName, String propertyValue) {}
	
}
