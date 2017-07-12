package webBoltOns.client.components;

/*
 * $Id: CComboBoxField.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem; 
import webBoltOns.client.components.componentRules.AutoDocument;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CComboBoxField extends JComboBox implements StandardComponentLayout {
	private static final long serialVersionUID = -6525955193141129804L;
	private Hashtable<String, ComboItem> keys;
	private JLabel comboLabel;
	private WindowItem comp;
	private AppletConnector cnct;
	private String commitKey;
	protected boolean validValue = true;
	protected ImageIcon image; 
	
	
	public CComboBoxField() {}

	public void addListItem(String key, String description, String iconName) {

		if (key == null || key.equals(""))
			key = description;
		
		ComboItem item = new ComboItem(key, description, iconName);
		keys.put(key,item);
		addItem(item);
	}

	public void commitEditing(String key) {
		if (key != null)
			setSelectedItem(keys.get(key));
		commitKey = key;
	}

	private void revertEditing() {
		if (commitKey != null)
			setSelectedItem(keys.get(commitKey));
		else
			setSelectedIndex(0);

		validValue = true;
	}

	
	
	public DataSet populateDataSet(String action, String editor,DataSet ds) {
			ComboItem item  = (ComboItem) getSelectedItem();
			if(item != null && item instanceof ComboItem)
				if(cnct.strictEncoding && comp.isEncrypted())
					ds.putStringField(editor, cnct.encrypt(item.getKey()));
				else
					ds.putStringField(editor, item.getKey());
			else 
				ds.putStringField(editor, null);
			return ds;
	}

	public void populateComponent(String action, String editor, DataSet ds) {
		if(cnct.strictEncoding && comp.isEncrypted())
			commitEditing(cnct.decrypt(ds.getStringField(editor)));
		else
			commitEditing(ds.getStringField(editor));
	}

	public void clearComponent(String defaultValue) {
		setSelectedItem(null);
		validValue = true;
		commitKey = null;
		updateUI();
	}
	

	
	public String getKeyValue(String key) {
		if(keys == null)
			return null;
		
		try {
			return ((ComboItem) keys.get(key)).description;
		} catch (NullPointerException e) {
			return null;
		}
		
 	}

	public String getString() {return null;}
	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		image = cnct.getImageIcon("err.gif");
		
		keys = new Hashtable<String, ComboItem>();
		setName(Integer.toString(comp.getObjectHL()));
		setFont(cnct.standardFont);
		setBackground(Color.WHITE);
		setRenderer(new ImageCellRenderer());
		
		addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent f) {
				setBackground(cnct.crsColor);
				validValue = true;
			}

			public void focusLost(FocusEvent f) {
				setBackground(Color.WHITE);
				ComboItem item = (ComboItem)getSelectedItem();
				if(item != null)
					commitKey = item.getKey();
				else
					commitKey = null;
			}
		});
		addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {		
					ComboItem item = (ComboItem)getSelectedItem();
					if(item != null)
						commitKey = item.getKey();
					else
						commitKey = null;
	
				} else if (e.getKeyCode() == 27) {
					revertEditing();
					e.consume();
				}				
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		if (parentItem.getComponentObject() instanceof CPanelContainer) {
			comboLabel = new JLabel(comp.getDescription());
			comboLabel.setFont(cnct.headerFont);
			new AutoDocument().setControl(this);
			
			setPreferredSize(new Dimension((comp.getLength()
					* cnct.headerFont.getSize() + 25), 19));

			if (comp.getPosition().equals(WindowItem.RIGHT)) {
				((CPanelContainer) parentItem.getComponentObject())
						.addRight(comboLabel, 4);
				((CPanelContainer) parentItem.getComponentObject())
						.addRight(this, 5);

			} else if (comp.getPosition().equals(
					WindowItem.CENTER)) {
				((CPanelContainer) parentItem.getComponentObject())
						.addRight(comboLabel, 2);
				((CPanelContainer) parentItem.getComponentObject())
						.addRight(this, 3);

			} else if (comp.getPosition().equals(
					WindowItem.LEFT)) {
				((CPanelContainer) parentItem.getComponentObject())
						.addLeft(comboLabel, 0);
				((CPanelContainer) parentItem.getComponentObject())
						.addRight(this, 1);

			} else {
				((CPanelContainer) parentItem.getComponentObject())
						.addc(comboLabel);
				((CPanelContainer) parentItem.getComponentObject())
						.addc(this);
			}
		}
	}


	public String getSelectedComponentItem() {
		return (String) getSelectedItem();
	}

	public boolean validateComponent(String action, String editorName) {
		updateUI();
		return true;
	}

	public class ComboItem implements Serializable {

		private static final long serialVersionUID = -3046738153957563157L;

		String key, description, iconName;

		public ComboItem(String k, String d, String i) {
			key = k;
			description = d;
			iconName = i;
		}

		public String getKey() {
			return key;
		}

		public String getDescription() {
			return description;
		}

		public String getIconName() {
			return iconName;
		}
		
		public String toString() {
			return  description;
		}
	
	}

	
	class ImageCellRenderer implements ListCellRenderer {
		
		private JLabel renderer;

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
				ComboItem item = (ComboItem)value;
				renderer.setText(item.getDescription());
				if(item.getIconName().equals(""))
					renderer.setIcon(null);
				else
					renderer.setIcon(cnct.getImageIcon(item.getIconName()));
			}
		 	
			if(isSelected)			
				renderer.setBackground( SystemColor.lightGray);
			else
				renderer.setBackground( SystemColor.text);
			
			return renderer;
		}

	}

	public void initializeComponentUI() {}
	
	public void setProperty(String propertyName, String propertyvalue) {}

	public boolean locateCursor() {
		//grabFocus();
		return true;
	}
	
	public void setValid(boolean invalid) {
		this.validValue = invalid;
	}

	
	public  void paint (Graphics g) {
		super.paint(g);
		if(image != null && !validValue) {
			int x =g.getClipBounds().width - image.getIconWidth() - 20;	
			   	g.drawImage(image.getImage(),
			   		 x , 0, image.getIconWidth(),
					 	    image.getIconHeight(), 
					 	    image.getImageObserver());
		}
	}

}
