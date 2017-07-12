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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;

import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.clientUtil.RdBorder;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowParm;
import webBoltOns.dataContol.DataSet;


public class CDocManager  extends CTableContainer implements StandardComponentLayout {
	
  private static final long serialVersionUID = -194633502812488008L;
  private ImportAction ia = new ImportAction();
  private ViewAction   va = new ViewAction();
  private DeleteAction de = new  DeleteAction();
  private byte [] buf = null;
  JTabbedPane tabs = null;
  JPanel icons = null;
  int qcolumn = 0;
  public CDocManager() {}

  
  private class FileTransfer extends TransferHandler {
		
	     private static final long serialVersionUID = -6477920788967158786L;
	     private static final String URI_LIST = "uri-list";

				public boolean importData(JComponent comp, Transferable t) {
			      // Make sure we have the right starting points
			      if (!(comp instanceof JPanel ) ) return false;

			      List<File> files = null;
					 
					try {
						
						for (DataFlavor flavor : t.getTransferDataFlavors()) {
							Object obj = t.getTransferData(flavor);
							
							// windows
							if (DataFlavor.javaFileListFlavor.equals(flavor)) {
								files = (List<File>)t.getTransferData(flavor);	
			 
							// kde
							} else if (URI_LIST.equals(flavor.getSubType()) && obj instanceof String ) {
								String urilist = (String)obj;
								Scanner scanner = new Scanner(urilist.trim());
								
								files = new LinkedList<File>();
								
								while (scanner.hasNextLine()) {					
									files.add(new File(new URI(scanner.nextLine()) ) );
								}
			 
							}
							
						}
						
						if(files != null) {
							Iterator<File> i = files.iterator();
							while(i.hasNext()) ia.importFile(i.next());	
						}
					
					} catch (Exception e) {
						return false;
					}		
					return true;
			    }
			    
				
			    // We only support file lists on FSTrees...
			    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
					for (DataFlavor flavor : transferFlavors) {
						
						// windows
						if (flavor.equals(DataFlavor.javaFileListFlavor)) {
							return true;
					
						// linix
						} else if (URI_LIST.equals( flavor.getSubType() )) {				
							return true;				
						}
					}
					return false;
			    }
		 
		 }
  
  private class ViewAction extends AbstractAction {
	  public void actionPerformed(ActionEvent e) {
		  getNavBar().fireNavAction();
	  }
  }

  
  private class ImportAction extends AbstractAction {

	  private static final long serialVersionUID = -6461801625701678965L;

	  public ImportAction() {
		  super("Import Image");
	  }


	  public void actionPerformed(ActionEvent ev) {
		  JFileChooser chooser = new JFileChooser();			
		  chooser.setLocation(0, 0);
		  if (chooser.showOpenDialog(mFrm.getWindowFrame()) != JFileChooser.APPROVE_OPTION)
			  return;
		  File file = chooser.getSelectedFile();
		  if (file != null) importFile(file);
	  }



	  public void importFile (File file) {
		  InputStream imprt = null; 
		  try {
			  buf = new byte[(int)file.length()];
			  imprt = new FileInputStream(file);
			  imprt.read(buf);
			  if(buf != null && buf.length > 0 )
				  mFrm.actionFileUpLoad(comp.getObjectHL(), file.getName(),  buf);

		  } catch (Exception ex) {
			  new CDialog(mFrm.getWindowFrame(), cnct).showWarnDialog(cnct.getMsgText("TXT0098"), null);
		  } finally {
			  buf = null;
			  if (imprt != null) {
				  try {
					  imprt.close();
				  } catch (IOException x) {
				  }	
			  }
		  }
	  }

  }


  private class DeleteAction extends AbstractAction {

	  private static final long serialVersionUID = -6461801625701678965L;

	  public DeleteAction() {
		  super("Delete Image");
		  }

	  public void actionPerformed(ActionEvent ev) {
		  System.out.println("delete action --> " + getDocID());
		  DataSet ds = new  DataSet();
		  ds.putStringField("[Delete_Doc_Action/]",  getDocID());
		  ds.putStringField(WindowItem.METHOD, comp.getMethod());		
		  mFrm.actionSendServerNow(ds);
		  new CDialog(mFrm.getWindowFrame(), cnct).showWarnDialog(cnct.getMsgText("LIT0006"), null);
		  mFrm.doFindClick();
	  } 
  
  }


  
  
  private class Fileicon extends JLabel implements MouseListener {
	  String rec [] = null;	
	
	  public Fileicon() {}
	  
	  public Fileicon(String r[]) {
		  rec = r;
		  setText(rec[2]);
		  setToolTipText(rec[3]);
		  setIcon(cnct.getImageIcon("WTE1C.jpg"));
		  addMouseListener(this);
		  setCursor(new Cursor(Cursor.HAND_CURSOR));
	  }

	public void mouseReleased(MouseEvent e) {
		mFrm.actionRunDoc(rec[1]);
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) { 
	
	} 
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
  
	}
  
  
  
  public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
	 
	  super.buildComponent(parentItem, thisItem, mainFrame);
	  JPanel pn = new JPanel(new BorderLayout());
	  icons = new JPanel(new GridFlowLayout(40,40));
	  
	  JScrollPane isp = new JScrollPane(icons);
	  isp.setBorder(BorderFactory.createCompoundBorder(new RdBorder(Color.LIGHT_GRAY),
								BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
	  isp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	  isp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	  isp.setPreferredSize(new Dimension(tbWidth + 40, tbHeight + 15));
	  
	  tabs = new JTabbedPane();
	  tabs.addTab(cnct.getMsgText("TXT0099"), cnct.getImageIcon("dot.gif"), this);
	  tabs.addTab(cnct.getMsgText("TXT0100"), cnct.getImageIcon("dot.gif"), isp);
	  tabs.setTabPlacement(JTabbedPane.BOTTOM);
	  
	  pn.add(buildToolBar(), BorderLayout.NORTH);
	  pn.add(tabs, BorderLayout.CENTER);
	  
	  if (comp.getPosition().equals(WindowItem.RIGHT)) 
			((CPanelContainer) parentItem.getComponentObject()).addRight(pn, 4);
	  else if (comp.getPosition().equals(WindowItem.CENTER)) 
			((CPanelContainer) parentItem.getComponentObject()).addRight(pn, 2);
	   else 
			((CPanelContainer) parentItem.getComponentObject()).addLeft(pn, 0);
	  
	  FileTransfer dnd = new FileTransfer(); 
	  setTransferHandler(dnd);
	  icons.setTransferHandler(dnd);
	  
  }

  
  
  public void initializeComponentUI() {
	  super.initializeComponentUI();
	  if(getNavBar() != null)
		  getNavBar().addOption("MNode3.gif");
  }
  
  
  public void clearComponent(String d) {
	  qcolumn = 0;
	  icons.removeAll();
	  icons.validate();
	  icons.repaint();
	  super.clearComponent(d);  
   }
  
  
  
	public void populateComponent(String at, String tb, DataSet ds) {
		qcolumn = 0;
		icons.removeAll();		
		Enumeration e = ds.getTableVector(tb).elements();
		while (e.hasMoreElements()) {
			GridFlowParm gridLocation = null;
			qcolumn++;
			if (qcolumn == 1) {
				gridLocation = new GridFlowParm(true, 1);
			} else if (qcolumn == 2) {
				gridLocation = new GridFlowParm(false, 2);
			} else {
				gridLocation = new GridFlowParm(false, 3);
				qcolumn = 0;
			}
			icons.add(new Fileicon((String [])e.nextElement()) , gridLocation);
		}
		icons.validate();
		icons.repaint();
		super.populateComponent(at, tb, ds); 
	}
	
	
   private JToolBar buildToolBar() {
		JButton menuItem;
		JToolBar tb = new JToolBar();

		menuItem = super.cnct.buildFancyButton(cnct.getMsgText("TXT0101"), "eImport.gif", null, ' ');
		menuItem.addActionListener(ia);
		tb.add(menuItem, null);
		tb.add(new JToolBar.Separator());
		
		menuItem = super.cnct.buildFancyButton(cnct.getMsgText("TXT0102"), "eExport.gif", null, ' ');
		menuItem.addActionListener(va);
		tb.add(menuItem, null);
		tb.add(new JToolBar.Separator());
		
		menuItem = super.cnct.buildFancyButton(cnct.getMsgText("TXT0103"), "eDelete.gif", null, ' ');
		menuItem.addActionListener(de);
//		tb.add(menuItem, null);
		 		
		tb.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));

		return tb;
	}
		

  
}



