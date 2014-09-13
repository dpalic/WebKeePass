package webBoltOns.client.components;

/*
 * $Id: CFilePrompt.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CFilePrompt extends JPanel implements StandardComponentLayout {

	private static final long serialVersionUID = 558782774497365986L;
	private WindowItem comp;
	private AppletConnector cnct;
	private WindowFrame mFrme;
	private JTextField prompt = new JTextField();
	public CFilePrompt() {}
 	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) { 
		comp = thisItem;
		mFrme = mainFrame;
		cnct = mainFrame.getAppletConnector();
	
		JLabel lb = new JLabel(thisItem.getDescription());
		lb.setFont(cnct.headerFont);
		prompt = new JTextField(25);
		CButton b = new CButton(cnct.getMsgText("TXT0104"));
		add(lb);
		add(prompt);
		add(b);
		
		b.addActionListener(new BrowseAction());

		setName(Integer.toString(comp.getObjectHL()));
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 4);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 2);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(this, 0);
		}

	}


	public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {
		 String path = prompt.getText();
		 if(path != null && !path.equals("") && comp.getMethod().equals("ImportFile")) {	
			CDialog dialog = new CDialog(mFrme.getWindowFrame(), cnct);	
			InputStream in = null;
			byte [] buf = null;
			try {
				File file = new File(path);
				buf = new byte[(int)file.length()];
				in = new FileInputStream(file);
				in.read(buf);

			} catch (IOException ex) {
				dialog.showWarnDialog(cnct.getMsgText("TXT0105"), prompt);
			} finally {
				if (in != null) {
				try {
					in.close();
					dataSet.putStreamArray(editorName, buf);
					} catch (IOException x) {
					}
				}
			}
		}
	 
	 return dataSet;
	}

	public String getSelectedComponentItem() {
		return null;
	}

	
	public String getString() {
		return null;
	}
	
	public boolean locateCursor() {
		prompt.grabFocus();
		return true;
	}
	
	public void setValid(boolean invalid) {
	}
	
	public void populateComponent(String action, String editorName, DataSet dataSet) {
		String path = prompt.getText();
		if(path != null && !path.equals("") && comp.getMethod().equals("ExportFile")) {		
			OutputStream out = null;
			File file = new File(path);
			CDialog dialog = new CDialog(mFrme.getWindowFrame(), cnct);
			try {
				out = new FileOutputStream(file);
				out.write(dataSet.getStreamArray(editorName));
			
			} catch (IOException e) {
				dialog.showWarnDialog(cnct.getMsgText("TXT0106"), prompt);
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException x) {
				}
			}	
		}
	}

	public void clearComponent(String defaultValue) {
		prompt.setText(null);
	}

	public void initializeComponentUI() {}

	public boolean validateComponent(String action, String editorName) {
		return true;
	}

	public void setProperty(String propertyName, String propertyValue) {}


	public void setToolTipText(String tip) {}
	// An action that opens an existing file
	
	
	class BrowseAction extends AbstractAction {

		private static final long serialVersionUID = -6461801657601643325L;

		public BrowseAction() {
			super("Browse...");
		}
		
		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
				
			chooser.setLocation(0, 0);			

			if (chooser.showOpenDialog(mFrme.getWindowFrame()) != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooser.getSelectedFile();
			if (file == null)
				return;
			
			prompt.setText(file.getAbsolutePath());
			}
	}
	
	 
}
