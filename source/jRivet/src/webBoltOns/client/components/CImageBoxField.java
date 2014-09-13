package webBoltOns.client.components;

/*
 * $Id: CImageBoxField.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.clientUtil.SimpleFileFilter;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;
import webBoltOns.dataContol.GIFEncoder;
import webBoltOns.dataContol.JPEGEncoder;


public class CImageBoxField  extends JPanel implements StandardComponentLayout {
	
	private static final long serialVersionUID = -194633502812488008L;
    private JScrollPane areaScrollPane;
    private JLabel imageLabel = new JLabel();
    private WindowItem comp;
    private AppletConnector cnect;
    private WindowFrame mFrm;
    private JToolBar toolBar;
	private ImportAction importAction = new ImportAction();
	private ExportAction exportAction = new ExportAction();
	

	
  public CImageBoxField() {}


  public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame){
  	
	
    comp = thisItem;
 	mFrm = mainFrame;
    cnect = mainFrame.getAppletConnector();

 	this.setLayout(new BorderLayout());  
  	setName(Integer.toString(comp.getObjectHL()));
	toolBar = buildToolBar();
	add(toolBar, BorderLayout.NORTH);
	
	areaScrollPane = new JScrollPane(imageLabel);
	areaScrollPane.setPreferredSize(new Dimension(
			comp.getLength() * 20,
	        comp.getHeight() * 20));
	areaScrollPane.setBorder(
	        BorderFactory.createCompoundBorder(
	        BorderFactory.createCompoundBorder(
	        BorderFactory.createTitledBorder(comp.getDescription()),
	        BorderFactory.createEmptyBorder(5, 5, 5, 5)),
	        areaScrollPane.getBorder()));
	add(areaScrollPane, BorderLayout.CENTER);
	    
	    
    if (comp.getPosition().equals(WindowItem.RIGHT)) {
     ( (CPanelContainer) parentItem.getComponentObject()).addRight(this, 4);
   } else if (comp.getPosition().equals(WindowItem.CENTER)) {
     ( (CPanelContainer) parentItem.getComponentObject()).addRight(this, 2);
   } else {
     ( (CPanelContainer) parentItem.getComponentObject()).addLeft(this, 0);
   }
  }

	private JToolBar buildToolBar() {
		JButton menuItem;
		JToolBar tb = new JToolBar();

		menuItem = cnect.buildFancyButton(cnect.getMsgText("TXT0107"), "eImport.gif", null, ' ');
		menuItem.addActionListener(importAction);
		tb.add(menuItem, null);
		tb.add(new JToolBar.Separator());
		menuItem = cnect.buildFancyButton(cnect.getMsgText("TXT0108"), "eExport.gif", null, ' ');
		menuItem.addActionListener(exportAction);
		tb.add(menuItem, null);
		
		tb.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));

		return tb;
	}
		
  public void populateComponent(String action, String field, DataSet dataSet) {
  	ImageIcon imagevalue = dataSet.getIcon(field);	
    if (imagevalue != null) {
        imageLabel.setIcon(imagevalue);
        
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
      } else {
        imageLabel.setIcon(null);
      }
  }
  
  public void clearComponent(String defaultValue) {
    imageLabel.setIcon(null);
  }
   
  public void setToolTipText(String tip) {
    areaScrollPane.setToolTipText(tip);
  }

  public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {
		if(imageLabel.getIcon() != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();	
			if(JPEGEncoder.encode(out,imageLabel)) {
				byte [] bytes =  out.toByteArray();
				dataSet.putStreamArray(editorName, bytes);	 
			} else {
				dataSet.putStreamArray(editorName, null);	 
			}
		} else {
			dataSet.putStreamArray(editorName, null);	
		}
	  return dataSet;
  }

  
  public String getSelectedComponentItem() {
	return null;
  }
  
  public String getString() {
	  return null;
  }
	
  public void initializeComponentUI () {}
  

  public boolean locateCursor() {
		return false;
  }

  public void setValid(boolean invalid) {
  }
  
  public boolean validateComponent(String action, String editorName) {return true;}

  public void setProperty(String propertyName, String propertyvalue) {}
  


	// An action that opens an existing file
	class ImportAction extends AbstractAction {

		private static final long serialVersionUID = -6461801625701678965L;

		public ImportAction() {
			super("Import Image");
		}

		
		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new SimpleFileFilter("gif", "gif Image"));
			chooser.addChoosableFileFilter(new SimpleFileFilter("jpg", "jpg Image"));			
			chooser.setLocation(0, 0);
			CDialog dialog = new CDialog(mFrm.getWindowFrame(), cnect);

			if (chooser.showOpenDialog(mFrm.getWindowFrame()) != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooser.getSelectedFile();
			if (file == null)
				return;
			InputStream icon = null; 
			try {
				byte [] iconImage = new byte[(int)file.length()];
			    icon = new FileInputStream(file);
				icon.read(iconImage);
				ImageIcon image = new ImageIcon(iconImage);
				imageLabel.setIcon(image);
				mFrm.doEditClick();
			} catch (IOException ex) {
				dialog.showWarnDialog(cnect.getMsgText("TXT0105"), imageLabel);
			} finally {
				if (icon != null) {
					try {
						icon.close();
					} catch (IOException x) {
					}
				}
			}
		}
	}
	
	 

	// An action that saves the document to a file
	class ExportAction extends AbstractAction {

		private static final long serialVersionUID = -6461801625701678966L;

		public ExportAction() {
			super("Export Image");
		}

		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new SimpleFileFilter("gif", "gif Image"));
			chooser.addChoosableFileFilter(new SimpleFileFilter("jpg", "jpg Image"));
			
			if (chooser.showSaveDialog(mFrm.getWindowFrame()) != JFileChooser.APPROVE_OPTION ||  
					chooser.getSelectedFile().getAbsolutePath() == null) return;
			
			String f = chooser.getSelectedFile().getAbsolutePath();
			File file = null;
			OutputStream out = null;
			CDialog dialog = new CDialog(mFrm.getWindowFrame(), cnect);			

			if(f.toLowerCase().endsWith("gif") || f.toLowerCase().endsWith("jpg")) 
				file = new File(f);
			else if(chooser.getFileFilter().getDescription().equals("gif Image")) 
				file = new File(f +".gif");
			else 
				file = new File(f +".jpg");
						
			try {
				if(file.getName().toLowerCase().endsWith(".gif")) {
					out = new FileOutputStream(file);
					if(!GIFEncoder.encode(out,imageLabel)) 
						dialog.showWarnDialog(cnect.getMsgText("TXT0106"), imageLabel);
				} else if (file.getName().toLowerCase().endsWith(".jpg")) {	
					out = new FileOutputStream(file);
					if(!JPEGEncoder.encode(out,imageLabel)) 
						dialog.showWarnDialog(cnect.getMsgText("TXT0106"), imageLabel);
				} else {
					dialog.showWarnDialog(cnect.getMsgText("TXT0196"), imageLabel);	
					return;
				}
				
				
			} catch (FileNotFoundException e) {
				dialog.showWarnDialog(cnect.getMsgText("TXT0106"), imageLabel);
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException x) {
				}
			}			
		}
	}

}
