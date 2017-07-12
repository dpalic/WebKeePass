package webBoltOns.client;

/*
 * $Id: MenuFrame.java,v 1.1 2007/04/20 19:37:14 paujones2005 Exp $ $Name:  $
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

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.RepaintManager;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

 
import webBoltOns.AppletConnector;
import webBoltOns.client.components.CButton;
import webBoltOns.client.components.CDialog;
import webBoltOns.client.components.componentRules.ComManager;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowParm;
import webBoltOns.client.components.layoutManagers.StackedFlowLayout;
import webBoltOns.dataContol.DataSet;

/**
 * <CODE>MenuFrame</CODE> will display a tree and panel with the contents of
 * the jRivet menu file.
 * 
 */

public class MenuFrame extends JPanel implements ComManager, ClipboardOwner,  MouseListener, KeyListener, ActionListener {

	class CControlTab extends Component implements Icon {

		private static final long serialVersionUID = 8706313181632510734L;

		private int height;

		private int width;

		private int x_pos;

		private int y_pos;

		private WindowFrame accs;

		
		public boolean ent_R = false;

		public boolean ent_L = false;

		public boolean linkTab = false;

		public CControlTab(WindowFrame windowAccess) {
			accs = windowAccess;
			if (accs != null) {
				width = 45;
				linkTab = false;
			} else {
				width = 23;
				linkTab = true;
			}
			height = 18;
		}

		
		public Rectangle getBounds() {
			return new Rectangle(x_pos, y_pos, width, height);
		}

		public int getIconHeight() {
			return height;
		}

		public int getIconWidth() {
			return width;
		}

		public WindowFrame getWindowAccess() {
			return accs;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			this.x_pos = x;
			this.y_pos = y;

			Color col = g.getColor();

			g.setColor(Color.BLACK);
			int y_p = y + 2;
			int x_b = x + 18;
			int y_b = y_p;

			if (ent_L)
				g.setColor(new Color(250, 190, 250));
			else
				g.setColor(Color.BLACK);

			if (!linkTab) {
				g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
				g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);

				g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
				g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);

				g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
				g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
			} 

			
			if (!linkTab) {
				if (ent_R)
					g.setColor(new Color(250, 190, 250));
				else
					g.setColor(Color.BLACK);

				g.drawLine(x_b, y_b + 3, x_b + 12, y_b + 3);
				g.drawLine(x_b, y_b + 4, x_b + 12, y_b + 4);

				g.drawLine(x_b, y_b + 3, x_b, y_b + 9);
				g.drawLine(x_b, y_b + 9, x_b + 12, y_b + 9);
				g.drawLine(x_b + 12, y_b + 3, x_b + 12, y_b + 9);
			}

			g.setColor(col);
		}

	}

	class CTabbedUI extends MetalTabbedPaneUI {

		CTabbedUI() {
			super();
			setBackground(Color.BLACK);
		}

		protected void paintTabBackground(Graphics g, int tabPlacement,
				int tabIndex, int x, int y, int w, int h, boolean isSelected) {

			Graphics2D d1 = (Graphics2D) g;
			Color c = null;

			if (isSelected)
				c = cntr.tbColor;
			else
				c = Color.lightGray;

			GradientPaint gp = new GradientPaint(0.011F, 0.98F, c, 1.0F, 1.0f,Color.WHITE, true);
			d1.setPaint(gp);

			if (isSelected) {
				d1.fillRoundRect(x - 1, y - 1, w + 2, h, 7, 15);
				d1.setColor(c);
				d1.drawRoundRect(x - 1, y - 1, w + 2, h, 7, 15);
			} else {
				d1.fillRect(x, y, w, h);
				d1.setColor(Color.DARK_GRAY);
				d1.drawLine(x, y + 6, x, y + h);
				d1.drawLine(x + w, y + 6, x + w, y + h);
				d1.drawLine(x, y + h - 1, x + w, y + h - 1);
			}
		}

		protected void paintFocusIndicator(Graphics g, int tabPlacement,
				Rectangle[] rects, int tabIndex, Rectangle iconRect,
				Rectangle textRect, boolean isSelected) {
		}

		protected void paintHighlightBelowTab() {}
		protected void paintLeftTabBorder(int tabIndex, Graphics g, int x,
				int y, int w, int h, int btm, int rght, boolean isSelected) {}
		protected void paintRightTabBorder(int tabIndex, Graphics g, int x,
				int y, int w, int h, int btm, int rght, boolean isSelected) {}
		protected void paintTopTabBorder(int tabIndex, Graphics g, int x,
				int y, int w, int h, int btm, int rght, boolean isSelected) {}
		protected void paintBottomTabBorder(int tabIndex, Graphics g, int x,
				int y, int w, int h, int btm, int rght, boolean isSelected) {}
		protected void paintTabBorder(Graphics g, int tabPlacement,
				int tabIndex, int x, int y, int w, int h, boolean isSelected) {}
	}


	class CTabbedControlPane extends JTabbedPane implements MouseListener,
			MouseMotionListener {

		private static final long serialVersionUID = -968966024228434622L;
		public CTabbedControlPane() {
			super();
			CTabbedUI ui = new CTabbedUI();
			setUI(ui);
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public void addLinkTab(String title, CControlTab ct, Component comp) {
			addTab(title, ct, comp);
		}
		
		
		public void addTab(String title, Component component, Icon extraIcon) {
			super.addTab(title, null, component);
		}

		public void mouseClicked(MouseEvent e) {
			int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());

			if (tabNumber > -1 && ((CControlTab) getIconAt(tabNumber)) != null) {
				CControlTab ct = ((CControlTab) getIconAt(tabNumber));
				int x = ct.getBounds().x;
				int y = ct.getBounds().y;
				Rectangle rect = new Rectangle();
				rect.setBounds(x, y, 16, 32);
				
				if (!ct.linkTab && rect.contains(e.getX(), e.getY())) {
					MenuFrame.this.deleteTab(((CControlTab) getIconAt(tabNumber)).getWindowAccess());
					return;
				}

				
				rect.setBounds(x + 16, y, 16, 32);
				if (!ct.linkTab && rect.contains(e.getX(), e.getY())) {				
					MenuFrame.this.removeTab(((CControlTab) getIconAt(tabNumber)).getWindowAccess());
					return;
				}
				

			}

			if (e.getClickCount() > 1) {
				setSplit();
				return;
			}

		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		
		public void mouseExited(MouseEvent e) {
			for(int z=1; z < getTabCount(); z++) {
				if (((CControlTab) getIconAt(z)) != null) {
					CControlTab c = (CControlTab) getIconAt(z);
					c.ent_L = false;
					c.ent_R = false;
					c.paintIcon(null, getGraphics(), c.getBounds().x,  c.getBounds().y);
				}	
			}
		}
			
		public void focusMenu() {
				horSplit.setDividerLocation((int) imgPnl.getPreferredSize().getWidth() - 10);
				menuTree.grabFocus();
		}

		
		public void mouseMoved(MouseEvent e) {
			int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());

			if (tabNumber < 0 || ((CControlTab) getIconAt(tabNumber)) == null)
				return;
			CControlTab c = (CControlTab) getIconAt(tabNumber);

			int x = c.getBounds().x;
			int y = c.getBounds().y;
			Rectangle rect = new Rectangle();
			rect.setBounds(x + 4, y + 6, 10, 11);
			if (rect.contains(e.getX(), e.getY() + 5))
				c.ent_L = true;
			else
				c.ent_L = false;

			rect.setBounds(x + 20, y + 6, 10, 11);
			if (rect.contains(e.getX(), e.getY()))
				c.ent_R = true;
			else
				c.ent_R = false;

			c.paintIcon(null, getGraphics(), x, y);
		}
		
		
		public void deleteAll () {
			setSelectedIndex(0);
	        int tabCount = getTabCount();
	        while (tabCount-- > 1) {
	            WindowFrame w = ((CControlTab) getIconAt(tabCount)).getWindowAccess();
	            if(w != null) {
	         		wf.remove(w);
	            	w.getMainFrame().dispatchEvent(new WindowEvent(w.getMainFrame(), WindowEvent.WINDOW_CLOSING));
	            	w.getMainFrame().dispose();
	                w = null;
	            } 
	            removeTabAt(tabCount);
	        }
			
	        Enumeration we = wf.elements();
	        while (we.hasMoreElements()) {
	        	WindowFrame w = (WindowFrame) we.nextElement();
            	w.getMainFrame().dispatchEvent(new WindowEvent(w.getMainFrame(), WindowEvent.WINDOW_CLOSING));
            	w.getMainFrame().dispose();
                w = null;
	        }
	  
			setFocusable(false);
		}
			
	}

	/**
	 * 
	 * <CODE>MenuRenderer</CODE> Tree cell Renderer for the Menu Tree
	 * 
	 */
	class MenuRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = -6171746720200691957L;

		ImageIcon dcIcon, scIcon;

		public MenuRenderer() {
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);

			Object object = ((DefaultMutableTreeNode) value).getUserObject();
			MenuItem menuComponent = (MenuItem) object;

			if (leaf) {
				setText(menuComponent.getDescription());
				scIcon = new ImageIcon(cntr.menuScriptIcon.getImage(), menuComponent.getDescription());
				dcIcon = new ImageIcon(cntr.menuDocumentIcon.getImage(), menuComponent.getDescription());

				if (menuComponent.getObjectName().equals("Script"))
					setIcon(scIcon);
				else
					setIcon(dcIcon);
			}
			return this;

		}
	}

	/**
	 * 
	 * <CODE>OptRenderer</CODE> Tree cell Renderer for the Menu Tree
	 * 
	 */
	class OptRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 6242543953157505157L;

		public OptRenderer() {

		}

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);

			Object object = ((DefaultMutableTreeNode) value).getUserObject();
			JLabel menuOption = (JLabel) object;

			if (menuOption != null)
				setText(menuOption.getText());

			return this;

		}

	}

	private static final long serialVersionUID = -7676303172426404655L;
	
	
	private  String adminScpMth[] = { null, null, null, null, null, "getMenuLink", null };
	
	private  String adminScpOp[] = { "UserAdmin110", "UserAdmin130", 
			"UserAdmin100", "UserAdmin120", "UserAdmin150",
			"UserAdmin160", "UserAdmin190"};

	private  String adminTxtOp[] = { "UAD0110", "UAD0130",
			"UAD0100", "UAD0120", "UAD0150", "UAD0160",
			"UAD0190" };

	private String startup = "KeePass006";
	
	private AppletConnector cntr;

	private String bt;
	
	private Clipboard clpbrd;
	
	private SecurityManager security;

	private JButton bgClrBtn, tbFntClrBtn, tbClrBtn, crsClrBtn;

	private JPanel crdPnl;

	private JSplitPane horSplit;

	private JLabel imgPnl, banner,d2;

	private MenuItem mnCmpTr[];

	private DataSet mnuDS;

	private MenuRenderer mnRndr;

	private JTree menuTree, optionTree;

	private Vector<String> mnec = new Vector<String>();
	
	private Vector<WindowFrame> wf = new Vector<WindowFrame>(); 

	private OptRenderer optRndr;

	private int qcolumn = 0;
	
	private JPanel qckPnl, rghtPnl, leftPanel, bnrBar, topRight, main0;
	
	private JProgressBar cpyBar;

	private JComboBox srvrLgs, combomode;

	private JLabel stdFtNm, hdeFtNm, bTitle, cpyL, robotL;

	private CTabbedControlPane mainTab = new CTabbedControlPane();

	private String totCnectns = "5";

	private int totObj = 0;

	private JButton infoT ,slpter, logot;

	private JDialog admFrm;
	
	private JPasswordField pw1, pw2, pw3;
	
	private Thread timer; 
		
	private boolean endTimer = false;
	
	public MenuFrame(AppletConnector a) {
		cntr = a;
	}

	/**
	 * <h2><code>addToTreeNode</code></h2>
	 * 
	 * <p>
	 * Adds a node to the menu tree. The menu tree is rendered within the left
	 * window panel in the MenuFrame object
	 * </p>
	 * 
	 * 
	 * @param int
	 *            parent_hl - Parent Node Index
	 * @param int
	 *            hl - Current Node Index
	 * @param String
	 *            objects - Menu Node obect description
	 */
	private void addToTreeNode(int parent_hl, int hl, String objects) {
		DefaultMutableTreeNode submenu = new DefaultMutableTreeNode(mnCmpTr[hl]);

		mnCmpTr[hl].setTreeNode(submenu);
		mnCmpTr[parent_hl].getTreeNode().add(mnCmpTr[hl].getTreeNode());

		if (mnCmpTr[hl].getQuickLink()) {
			JButton qlink;
			if(mnCmpTr[hl].getIconName() == null || mnCmpTr[hl].getIconName().equals(""))
					qlink = new JButton(mnCmpTr[hl].getDescription(), cntr.quickLinkIcon);
			else
					qlink = new JButton(mnCmpTr[hl].getDescription(),cntr.getImageIcon(mnCmpTr[hl].getIconName()));
			
			qlink.setName(Integer.toString(hl));
			qlink.setBorder(null);
			char n = getNextButtonMnemonic(mnCmpTr[hl].getDescription());
			if (n != ' ')
				qlink.setMnemonic(n);
			if (mnCmpTr[hl].getObjectName().equals("Document")) {
				qlink.setIcon(cntr.documentIcon);
			}
			qlink.addActionListener(this);
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
			qckPnl.add(qlink, gridLocation);
		}
	}

	public int getMenuHeight() {
		  return mainTab.getHeight() - (mainTab.getTabRunCount() * 19)  + topRight.getHeight() - 20 ;
		
	}

	
	
	public void copyToClipTimer(String clipName, String clipVal) {		
			if (cpyBar.isVisible() && timer != null) {
 		 		endTimer = true;
 		 		while(timer.isAlive()); 
			}
			
			timer = new Thread()  {			 
             public void run() {
         	   try {
         		cpyBar.setValue(10);
         		cpyL.setVisible(true);
         		cpyBar.setVisible(true);
    			endTimer = false;     		
         		 for(int s=10; s > 0 & !endTimer; s--){
         			 cpyBar.setValue(s);
         			 Thread.sleep(650);
         		 }	 
         		 
         	   } catch (InterruptedException e) {
            		  Thread.currentThread().interrupt();
         	   
         	   } finally {
         		   cpyBar.setValue(0);
         		   clpbrd.setContents(new StringSelection(""), MenuFrame.this);
         		   cpyL.setVisible(false);
         		   cpyBar.setVisible(false);
            	 
         	   }
             }
			};
			
			clpbrd.setContents(new StringSelection(clipVal), MenuFrame.this);
			cpyL.setText(clipName + " -- " + cntr.getMsgText("TXT0055"));
			
			timer.start();	 
	}
	
	
 
	
	public void lostOwnership(Clipboard arg0, Transferable arg1) { }

	
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
	
	
	private JPanel buildAbout() {
		final JPanel card1 = new JPanel(new StackedFlowLayout(StackedFlowLayout.TOP, 25, 25));
		
		
		card1.add(createTitle(cntr.getMsgText("OSS0001") ));
		
		card1.add(new JLabel(cntr.logoImagePanel));
		JLabel b1 = new JLabel(cntr.getMsgText("OSS0002") );
		b1.setHorizontalAlignment(JLabel.CENTER);
		card1.add(b1);
		
		card1.add(new JLabel(cntr.getImageIcon("oss.jpg")));
		JLabel d1 = new JLabel("Copyright: 2008, 2009, 2010 ");
		d1.setHorizontalAlignment(JLabel.CENTER);
		card1.add(d1);
;
		d2 = new JLabel("<html><font color=\"blue\"><u>www.ossfree.net</u></font></html>");
		d2.setHorizontalAlignment(JLabel.CENTER);
		d2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		d2.setName("oss");
		d2.addMouseListener(this);
		card1.add(d2);
		return card1;

	}

	/**
	 * <h2><code>buildAdminTools</code></h2>
	 * 
	 * <p>
	 * Creates admin tools within the admin console window
	 * </p>
	 * 
	 * @return JPanel - the Admin Tools Panel
	 * 
	 */
	private JPanel buildAdminTools(DefaultMutableTreeNode adminTree, JComboBox combomode) {

		Border border;
		TitledBorder titledBorder;
		JPanel adminTools = new JPanel(new StackedFlowLayout());
		JButton adminButton[] = new JButton[adminScpOp.length];

		JPanel infoPanel = new JPanel(new GridFlowLayout(15, 5));
		border = BorderFactory.createLineBorder(new Color(0, 0, 150), 1);
		titledBorder = new TitledBorder(border, cntr.getMsgText("TXT0044"));
		titledBorder.setTitleFont(cntr.standardFont);
		titledBorder.setTitleColor(new Color(0, 0, 150));
		infoPanel.setBorder(titledBorder);

		infoPanel.add(new JLabel(cntr.getMsgText("TXT0045")), new GridFlowParm(true, 0));
		infoPanel.add(new JLabel(totCnectns),new GridFlowParm(false, 1));
		infoPanel.add(new JLabel(cntr.getMsgText("TXT0046")), new GridFlowParm(false, 2));
		infoPanel.add(new JLabel(Integer.toString(totObj)),new GridFlowParm(false, 3));
		infoPanel.add(new JLabel(cntr.getMsgText("TXT0047")), new GridFlowParm(true, 0));
		infoPanel.add(new JLabel(cntr.menuScript),new GridFlowParm(false, 1));

		infoPanel.add(new JLabel(cntr.getMsgText("TXT0048")),new GridFlowParm(false, 2));
		infoPanel.add(new JLabel(cntr.dateFormat),new GridFlowParm(false, 3));

		infoPanel.add(new JLabel(cntr.getMsgText("TXT0049")),new GridFlowParm(true, 0));
		infoPanel.add(combomode, new GridFlowParm(false, 1));


		final JPanel logsPanel = new JPanel(new GridFlowLayout(15, 5));
		border = BorderFactory.createLineBorder(new Color(0, 0, 150), 1);
		titledBorder = new TitledBorder(border, cntr.getMsgText("TXT0050"));
		titledBorder.setTitleFont(cntr.standardFont);
		titledBorder.setTitleColor(new Color(0, 0, 150));
		logsPanel.setBorder(titledBorder);

		srvrLgs = new JComboBox();
		srvrLgs.setFont(cntr.standardFont);
		srvrLgs.setPreferredSize(new Dimension((5 * cntr.headerFont.getSize() + 140), 19));
		DataSet logs = new DataSet();
		logs.putStringField(WindowItem.ACTION, WindowItem.SEARCH);
		logs.putStringField(WindowItem.CLASSNAME,"webBoltOns.server.DevelopmentTools");
		logs.putStringField(WindowItem.METHOD, "getServerLogFileList");
		logs = cntr.postServerRequestImed(logs);

		Enumeration eLogFiles = logs.getTableVector("ServerLogFiles").elements();
		while (eLogFiles.hasMoreElements())
			srvrLgs.addItem((String) eLogFiles.nextElement());

		JButton logButton = new JButton(cntr.getMsgText("TXT0051"),cntr.quickLinkIcon);
		logButton.setBorder(null);
		logButton.setActionCommand("logs");
		logButton.addActionListener(this);
		logsPanel.add(srvrLgs, new GridFlowParm(true, 0));
		logsPanel.add(logButton, new GridFlowParm(false, 1));

		final JPanel adminPanel = new JPanel(new GridFlowLayout(10, 10));
		border = BorderFactory.createLineBorder(new Color(0, 0, 150), 1);
		titledBorder = new TitledBorder(border, cntr.getMsgText("TXT0052"));
		titledBorder.setTitleFont(cntr.standardFont);
		titledBorder.setTitleColor(new Color(0, 0, 150));
		adminPanel.setBorder(titledBorder);

		for (int b = 0; b < adminScpOp.length; b++) {
			final int x = b;
			adminButton[x] = new JButton(cntr.getMsgText(adminTxtOp[x]), cntr.quickLinkIcon);
			adminButton[x].setBorder(null);
			DefaultMutableTreeNode adminNode = new DefaultMutableTreeNode(new JLabel(cntr.getMsgText(adminTxtOp[x])));
			adminTree.add(adminNode);
			 
			adminButton[x].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adminPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					loadAdminScript(adminScpOp[x], adminScpMth[x], "");
					adminPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			});
		}

		adminPanel.add(adminButton[0], new GridFlowParm(true, 0));
		adminPanel.add(adminButton[1], new GridFlowParm(false, 1));
		adminPanel.add(adminButton[2], new GridFlowParm(false, 2));
		adminPanel.add(adminButton[3], new GridFlowParm(true, 0));
		adminPanel.add(adminButton[4], new GridFlowParm(false, 1));
		adminPanel.add(adminButton[5], new GridFlowParm(false, 2));
		adminPanel.add(adminButton[6], new GridFlowParm(true, 0));

		adminTools.add(infoPanel, null);
		adminTools.add(logsPanel, null);
		adminTools.add(adminPanel, null);
		JPanel card4 = new JPanel(new GridFlowLayout(5, 5));
		card4.setName("Card 4");
		card4.add(createTitle("Administrative Tasks"),
				new GridFlowParm(true, 0));

		card4.add(adminTools, new GridFlowParm(true, 0));

		return card4;
	}

	/**
	 * <h2><code>buildColorButtons</code></h2>
	 * 
	 * <p>
	 * Create the Colour Selection buttons on the admin console
	 * </p>
	 */
	private void buildColorButtons() {
	
		bgClrBtn = new JButton();
		bgClrBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bgClrBtn.setBackground(cntr.bgColor);
		bgClrBtn.setPreferredSize(new Dimension(40, 15));
		bgClrBtn.setActionCommand("bgClrBtn");
		bgClrBtn.addActionListener(this);
		tbFntClrBtn = new JButton();
		tbFntClrBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		tbFntClrBtn.setBackground(cntr.tbFontColor);
		tbFntClrBtn.setPreferredSize(new Dimension(40, 15));
		tbFntClrBtn.setActionCommand("tbFntClrBtn");
		tbFntClrBtn.addActionListener(this);
		tbClrBtn = new JButton();
		tbClrBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		tbClrBtn.setBackground(cntr.tbColor);
		tbClrBtn.setPreferredSize(new Dimension(40, 15));
		tbClrBtn.setActionCommand("tbClrBtn");
		tbClrBtn.addActionListener(this);
		crsClrBtn = new JButton();
		crsClrBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		crsClrBtn.setBackground(cntr.crsColor);
		crsClrBtn.setPreferredSize(new Dimension(40, 15));
		crsClrBtn.setActionCommand("crsClrBtn");
		crsClrBtn.addActionListener(this);
		stdFtNm = new JLabel();
		stdFtNm.addMouseListener(this);
		hdeFtNm = new JLabel();
		hdeFtNm.addMouseListener(this);

	}

	/**
	 * <h2><code>buildDisplayMenuObject</code></h2>
	 * 
	 * <p>
	 * Get the XML Menu script from the server and draw the menu
	 * </p>
	 * 
	 * @param DataSet
	 *            menu - The menu object
	 * 
	 */
	private void buildMainMenu(DataSet menu) {
		// get the screen layout from the server
		bt = menu.getStringField(WindowItem.BANNER_TITLE);
		qckPnl = new JPanel(new GridFlowLayout(40, 40));
		imgPnl = new JLabel(cntr.banner);
		imgPnl.setPreferredSize(new Dimension(cntr.banner.getIconWidth() + 10, cntr.banner.getIconHeight() + 10));

		// draw the Menu
		Vector vf = (Vector) menu.get(MenuItem.SCREEN_OBJECTS);
		Enumeration eObjects = vf.elements();
		totObj = Integer.parseInt(menu.get(MenuItem.TOTAL_OBJECTS).toString());

		mnCmpTr = new MenuItem[totObj + 1];
		for (int i = 0; i < totObj + 1; i++)
			mnCmpTr[i] = new MenuItem();

		mnCmpTr[0].setTreeNode(new DefaultMutableTreeNode());

		String objStr = null;
		while (eObjects.hasMoreElements()) {
			objStr = (String) eObjects.nextElement();
			int objectAccessLevel = Integer.parseInt(DataSet.parseProperty(
					MenuItem.ACCESS_LEVEL, objStr));
			int parent_hl = Integer.parseInt(DataSet.parseProperty(
					MenuItem.PARENT_HL, objStr));
			int object_hl = Integer.parseInt(DataSet.parseProperty(
					MenuItem.OBJECT_HL, objStr));

			if (objectAccessLevel > 1
					&& mnCmpTr[parent_hl].getTreeNode() != null) {
				mnCmpTr[object_hl].setObjectProperties(objStr);
				addToTreeNode(parent_hl, object_hl, objStr);
			}
		}

	 
		leftPanel = new JPanel(new BorderLayout());
		mnRndr = new MenuRenderer();

		menuTree = new JTree(mnCmpTr[0].getTreeNode());
		ToolTipManager.sharedInstance().registerComponent(menuTree);
		menuTree.setRootVisible(false);
		menuTree.setShowsRootHandles(true);
		menuTree.putClientProperty("JTree.lineStyle", "Angled");
		menuTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		mnRndr.setLeafIcon(new ImageIcon(cntr.scriptIcon.getImage()));
		mnRndr.setClosedIcon(new ImageIcon(cntr.menuClosedIcon.getImage()));
		mnRndr.setOpenIcon(new ImageIcon(cntr.menuOpenIcon.getImage()));
		menuTree.setCellRenderer(mnRndr);
		TreePath treepath = menuTree.getSelectionPath();
		menuTree.expandPath(treepath);
		menuTree.updateUI();
		menuTree.setSelectionPath(treepath);
		menuTree.setRowHeight(20);
		menuTree.setName("menu");
		menuTree.addKeyListener(this);	
		menuTree.addMouseListener(this);
		
		JPanel topLeft = new JPanel(new BorderLayout());
		topLeft.add(imgPnl, BorderLayout.NORTH);

		infoT = cntr.buildFancyButton("", "logo.gif", cntr.getMsgText("TXT0053"), ' ');
		infoT.setActionCommand("infoT");
		infoT.addActionListener(this);

		logot = cntr.buildFancyButton("", "logout.gif", cntr.getMsgText("TXT0054"), ' ');
		logot.setActionCommand("logOut");
		logot.addActionListener(this);
		
		if (checkSecurity()) clpbrd = cntr.getToolkit().getSystemClipboard();
	
		robotL = new JLabel("");
		cpyL = new JLabel(cntr.getMsgText("TXT0055"));
		cpyL.setFont(new java.awt.Font("Arial", 1, 8));
		cpyBar = new JProgressBar(0, 10);
		cpyBar.setPreferredSize(new Dimension(50, 10));
		cpyBar.setStringPainted(false);

		robotL.setVisible(true);
		cpyL.setVisible(false);
		cpyBar.setVisible(false);	
	    	
		final JPanel controls = new JPanel();
		controls.add(robotL);
		controls.add(cpyL);
		controls.add(cpyBar);
		controls.add(logot);
		controls.add(infoT);
		
		slpter = cntr.buildFancyButton("   ", "left.gif", cntr.getMsgText("TXT0056") , ' ');
		slpter.setActionCommand("slpter");
		slpter.addActionListener(this);
		
		leftPanel.add(BorderLayout.NORTH, topLeft);
		JScrollPane treeScrollpane = new JScrollPane(menuTree);
		treeScrollpane.setBorder(null);
		leftPanel.add(BorderLayout.CENTER, treeScrollpane);

		bnrBar = new JPanel(new BorderLayout());
		banner = new JLabel(bt);		
		banner.setFont(new java.awt.Font("Dialog", 1, 15));
	
		bnrBar.add(BorderLayout.NORTH, banner);
		main0 = new JPanel(new BorderLayout());
		main0.add(bnrBar, BorderLayout.NORTH);
		main0.add(qckPnl, BorderLayout.CENTER);
		mainTab.addLinkTab("Quick Links ",  new CControlTab(null), new JScrollPane(main0));
		topRight = new JPanel(new BorderLayout());
		topRight.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		topRight.add(BorderLayout.WEST, slpter);
		topRight.add(BorderLayout.EAST, controls);
	
		rghtPnl = new JPanel(new BorderLayout());
		rghtPnl.add(BorderLayout.NORTH, topRight);
		rghtPnl.add(BorderLayout.CENTER, mainTab);
		
		bTitle = createTitle(cntr.getMsgText("TXT0057") 
				+ cntr.user + "-"
				+ cntr.userDesc + "      " + cntr.getMsgText("TXT0058")
 				+ (new SimpleDateFormat(cntr.dateFormat)).format(new Date()),
				 Color.BLACK, BorderFactory.createEmptyBorder(2, 2, 2, 2)  ,new Font("Dialog", Font.BOLD, 14));	
		
		topRight.add(BorderLayout.CENTER, bTitle);
		
		horSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);		
		horSplit.setLeftComponent(leftPanel);
		horSplit.setRightComponent(rghtPnl);
		horSplit.setDividerSize(3);
		horSplit.setResizeWeight(0);
		horSplit.setDividerLocation(0);

		setName("MenuFrame");
		setLayout(new BorderLayout());
		add(horSplit, BorderLayout.CENTER);
		horSplit.setDividerLocation((int) imgPnl.getPreferredSize().getWidth() - 10);
		setVisible(true);
		menuTree.grabFocus();

		((JComponent) this).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2" );
		((JComponent) this).getActionMap().put( "F2", 
		   new AbstractAction() {
			private static final long serialVersionUID = 214005275668399814L;
			public void actionPerformed( ActionEvent ae ){
				mainTab.focusMenu();
			}
		});
	
		
		if(startup != "") loadScript(MenuItem.SCRIPT_TO_RUN_OBJECT, "KeePass006", 3);
	}
	
	
	public void displayRobot(boolean t) { 
		if(t)
			robotL.setIcon(cntr.getImageIcon("run.jpg"));
		else
			robotL.setIcon(null);
	}
		
	
 
	public void setSplit() {
		if (horSplit.getDividerLocation() > 10) {
			horSplit.setDividerLocation(0);
			slpter.setIcon(cntr.getImageIcon("right.gif"));
		} else {
			horSplit.setDividerLocation((int) imgPnl.getPreferredSize().getWidth() - 10);
			slpter.setIcon(cntr.getImageIcon("left.gif"));
	
		}
	}
	
	
	/**
	 * <h2><code>buildUserTools</code></h2>
	 * 
	 * <p>
	 * Creates user tools within the admin console window
	 * </p>
	 * 
	 * @return JPanel - the User Tools Panel
	 * 
	 */
	private JPanel buildUserTools() {
		final JPanel crd3 = new JPanel(new GridFlowLayout(5, 5));
		crd3.setName("Card 3");
		crd3.add(createTitle(cntr.getMsgText("TXT0059")), new GridFlowParm(true, 0));
		crd3.add(createTitle(cntr.getMsgText("TXT0060")), new GridFlowParm(true,0));
		crd3.add(createTitle(cntr.getMsgText("TXT0061")),	new GridFlowParm(false, 2));
		crd3.add(new JLabel(cntr.getMsgText("TXT0062")), new GridFlowParm(true, 0));
		crd3.add(bgClrBtn, new GridFlowParm(false, 1));
		crd3.add(new JLabel(cntr.getMsgText("TXT0063")),new GridFlowParm(false, 2));
		crd3.add(stdFtNm, new GridFlowParm(false, 3));
		crd3.add(new JLabel(cntr.getMsgText("TXT0064")),new GridFlowParm(true, 0));
		crd3.add(tbFntClrBtn, new GridFlowParm(false, 1));
		crd3.add(new JLabel(cntr.getMsgText("TXT0065")),new GridFlowParm(false, 2));
		crd3.add(hdeFtNm, new GridFlowParm(false, 3));
		crd3.add(new JLabel(cntr.getMsgText("TXT0066")), new GridFlowParm(true, 0));
		crd3.add(tbClrBtn, new GridFlowParm(false, 1));
		crd3.add(new JLabel(cntr.getMsgText("TXT0067")), new GridFlowParm(true, 0));
		CButton saveTheme = new CButton(cntr.getMsgText("TXT0068"));
		saveTheme.setActionCommand("saveTheme");
		saveTheme.addActionListener(this);
		crd3.add(crsClrBtn, new GridFlowParm(false, 1));
		crd3.add(saveTheme, new GridFlowParm(true, 2));
		return crd3;
	}

	
	/**
	 * <h2><code>buildUserTools</code></h2>
	 * 
	 * <p>
	 * Creates user tools within the admin console window
	 * </p>
	 * 
	 * @return JPanel - the User Tools Panel
	 * 
	 */
	private JPanel buildChngPswrd() {
		pw1 = new JPasswordField(20);
		pw2 = new JPasswordField(20);
		pw3 = new JPasswordField(20);
		
		final JPanel crd4 = new JPanel(new GridFlowLayout(10, 12));
		crd4.setName("Card 4");
		crd4.add(createTitle(cntr.getMsgText("TXT0069")), new GridFlowParm(true, 0));
		crd4.add(new JLabel("         " + cntr.getMsgText("TXT0070")), new GridFlowParm(true, 0));
		crd4.add(pw1, new GridFlowParm(false, 1));
		crd4.add(new JLabel("         " + cntr.getMsgText("TXT0071")),new GridFlowParm(true, 0));
		crd4.add(pw2, new GridFlowParm(false, 1));
		crd4.add(new JLabel("         " + cntr.getMsgText("TXT0072")),new GridFlowParm(true, 0));
		crd4.add(pw3, new GridFlowParm(false, 1));
		
		CButton chg = new CButton(cntr.getMsgText("TXT0073"));
		chg.setActionCommand("ChngPswrd");
		chg.addActionListener(this);
		crd4.add(chg, new GridFlowParm(true, 1));
		return crd4;
	
	}
	/**
	 * <h2><code>createTitle</code></h2>
	 * 
	 * <p>
	 * Creates a title label
	 * </p>
	 * 
	 * @param String
	 *            title - the description displayed in the label
	 * 
	 * @return - JLabel - the created label
	 */
	
	protected JLabel createTitle(String t ) {
		return createTitle(t, Color.BLUE, BorderFactory.createEmptyBorder(5, 5, 10, 5), new Font("Futura XBlkIt BT", 0, 18));
	}
	
	
	protected JLabel createTitle(String t, Color c, Border b, Font f) {
		JLabel sectionTitle = new JLabel();
		sectionTitle.setFont(f);
		sectionTitle.setAlignmentY((float) 1.5);
		sectionTitle.setVerifyInputWhenFocusTarget(true);
		sectionTitle.setHorizontalAlignment(SwingConstants.LEADING);
		sectionTitle.setHorizontalTextPosition(SwingConstants.TRAILING);
		sectionTitle.setForeground(c);
		sectionTitle.setText(t);
		sectionTitle.setBorder(b);
		return sectionTitle;
	}

	/**
	 * <h2><code>displayAdminConsole</code></h2>
	 * 
	 * <p>
	 * Creates the admin console window
	 * </p>
	 * 
	 * @param ActionEvent
	 *            action
	 * 
	 */
	public void displayAdminConsole() {
		final Object[] modes = { "HTTP", "RMI", "SOCKET" };
		admFrm = new JDialog(cntr.getAppletFrame(),false);
		final JSplitPane optionSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		optionSplit.setFocusable(false);
		combomode = new JComboBox(modes);

		combomode.setFont(cntr.standardFont);
		combomode.setPreferredSize(new Dimension((5 * cntr.headerFont.getSize() + 25), 19));
		combomode.setSelectedItem(cntr.getComMode());

		stdFtNm.setFont(cntr.standardFont);
		stdFtNm.setText("<html><font color=\"blue\"><u>"
				+ cntr.standardFont.getFamily() 	+ "</u></font></html>");
		stdFtNm.setCursor(new Cursor(Cursor.HAND_CURSOR));

		hdeFtNm.setFont(cntr.headerFont);
		hdeFtNm.setText("<html><font color=\"blue\"><u>"
						+ cntr.headerFont.getFamily()
						+ "</u></font></html>");
		hdeFtNm.setCursor(new Cursor(Cursor.HAND_CURSOR));

		crdPnl = new JPanel();
		crdPnl.setLayout(new CardLayout());
		crdPnl.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 10), BorderFactory.createBevelBorder(1)));
		DefaultMutableTreeNode optionNode = new DefaultMutableTreeNode();
		DefaultMutableTreeNode adminNode = new DefaultMutableTreeNode(new JLabel(cntr.getMsgText("TXT0074")));
		
		optionNode.add(new DefaultMutableTreeNode(new JLabel(cntr.getMsgText("TXT0075"))));
		if(cntr.securityManager && ! cntr.ldap)
			optionNode.add(new DefaultMutableTreeNode(new JLabel(cntr.getMsgText("TXT0076"))));
		if (cntr.deskTopThemeAccess)
			optionNode.add(new DefaultMutableTreeNode(new JLabel(cntr.getMsgText("TXT0077"))));
		if (cntr.administratorAccess)
			optionNode.add(adminNode);
		
		optionTree = new JTree(optionNode);
		optionTree.setRootVisible(false);
		optionTree.setShowsRootHandles(true);
		optionTree.putClientProperty("JTree.lineStyle", "Angled");
		optionTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		optRndr = new OptRenderer();
		optRndr.setLeafIcon(new ImageIcon(cntr.menuClosedIcon.getImage()));
		optRndr.setClosedIcon(new ImageIcon(
				cntr.menuClosedIcon.getImage()));
		optRndr.setOpenIcon(new ImageIcon(cntr.menuOpenIcon.getImage()));

		optionTree.setCellRenderer(optRndr);
		TreePath optionpath = optionTree.getSelectionPath();
		optionTree.expandPath(optionpath);
		optionTree.updateUI();
		optionTree.setSelectionPath(optionpath);
		optionTree.setRowHeight(20);
		optionTree.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 10), BorderFactory
				.createBevelBorder(1)));
    
		optionTree.addMouseListener(this);

		optionSplit.setLeftComponent(optionTree);

		JPanel north = new JPanel(new BorderLayout());
		north.add(BorderLayout.WEST, createTitle(""));
		JLabel jRivet = new JLabel(cntr.logoImageIcon);
		jRivet.setVerticalAlignment(JLabel.TOP);
		north.add(BorderLayout.EAST, jRivet);

		crdPnl.add(buildAbout(), cntr.getMsgText("TXT0075"));
		crdPnl.add(buildUserTools(),  cntr.getMsgText("TXT0127"));
		crdPnl.add(buildAdminTools(adminNode, combomode), cntr.getMsgText("TXT0074"));
		if(cntr.securityManager && ! cntr.ldap)
			crdPnl.add(buildChngPswrd(), cntr.getMsgText("TXT0069"));
		
		optionSplit.setRightComponent(crdPnl);
		optionSplit.setDividerSize(3);
		optionSplit.setResizeWeight(0);
		optionSplit.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 10), BorderFactory
				.createBevelBorder(1)));

		JPanel south = new JPanel(new FlowLayout());
		CButton ok = new CButton(cntr.getMsgText("TXT0001"));
		ok.setMnemonic('O');
		ok.setActionCommand("admMode");
		ok.addActionListener(this);
		south.add(ok);

		JPanel main = new JPanel(new BorderLayout());
		main.add(BorderLayout.NORTH, north);
		main.add(BorderLayout.CENTER, optionSplit);
		main.add(BorderLayout.SOUTH, south);

		optionTree.setBackground(cntr.bgColor);
		optRndr.setBackgroundNonSelectionColor(cntr.bgColor);

		admFrm.getContentPane().add(main);
		admFrm.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		admFrm.setLocation((d.width - admFrm.getSize().width) / 2,
				(d.height - admFrm.getSize().height) / 2);
		admFrm.setTitle("Tools");
		admFrm.setVisible(true);
	}

	/**
	 * <h2><code>fireAdminButtonPerformed</code></h2>
	 * 
	 * <p>
	 * Executed when the user selects a button on the admin console
	 * </p>
	 * 
	 * @param String
	 *            mode - Mode passed from the action listener
	 * 
	 */
	private void fireAdminButtonPerformed(String mode) {
		if (mode != null && !mode.equals("")) {
			cntr.setComMode(mode);
		}
	}

	/**
	 * <h2><code>fireNodeSelected</code></h2>
	 * 
	 * <p>
	 * Executed when the user selects a new node in the menu tree
	 * </p>
	 * 
	 * @param JTree
	 *            tree - The Menu Tree
	 * 
	 */
	private void fireNodeSelected(JTree tree) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		if (node != null) {
			Object nodeInfo = node.getUserObject();
			if (node.isLeaf()) {
				MenuItem menuitem = (MenuItem) nodeInfo;
				loadScript(menuitem.getObjectName(), menuitem.getScriptToRun(),
						menuitem.getUserAccessLevel());
			}
		}
	}

	/**
	 * <h2><code>getApplet</code></h2>
	 * 
	 * <p>
	 * Returns the applet object
	 * </p>
	 * 
	 * @return - Applet - Applet Connector
	 * 
	 */
	public Applet getApplet() {
		return cntr;
	}

	/**
	 * <h2><code>getNextButtonMnemonic</code></h2>
	 * 
	 * <p>
	 * gets the next button Mnemonic
	 * </p>
	 * 
	 * @param String
	 *            buttonText
	 * @return char - the button Mnemonic
	 */
	private char getNextButtonMnemonic(String buttonText) {
		char[] bt = buttonText.toUpperCase().toCharArray();
		for (int x = 0; x < bt.length; x++) {
			if (!mnec.contains(Character.toString(bt[x])) && bt[x] != ' ') {
				mnec.add(Character.toString(bt[x]));
				return bt[x];
			}
		}
		return ' ';
	}

	/**
	 * <h2><code>getWindow</code></h2>
	 * 
	 * <p>
	 * Returns the menu data set object used to create the menu object
	 * </p>
	 * 
	 * @return - DataSet - Menu Data Set
	 * 
	 */
	public DataSet getWindow() {
		return mnuDS;
	}

	/**
	 * <h2><code>loadAdminScript</code></h2>
	 * 
	 * <p>
	 * Send a request to the server for an Admin window XML script If found by
	 * the server, create and render the new window
	 * </p>
	 * 
	 * @param String
	 *            scriptToRun - Name of the XML Script
	 * @param String
	 *            initMethod - Script Init Method
	 * @param String
	 *            logfile - The Log File.
	 * 
	 */
	private void loadAdminScript(String scriptToRun, String initMethod,
			String logfile) {
		DataSet scrnSt = new DataSet();
		scrnSt.putStringField("[SCREEN_SCRIPT/]", scriptToRun);
		scrnSt.putIntegerField("[ScriptAccessLevel/]", 3);
		final WindowFrame adminWindow = new WindowFrame(cntr);
		adminWindow.setWindow(this, scrnSt);
		adminWindow.showWindow();
		if (initMethod != null) {
			adminWindow.getDataSet().putStringField("MenuXML", cntr.menuScript);
			adminWindow.getDataSet().putStringField("LogFileName", logfile);
			adminWindow.startWindowFrame(scrnSt, initMethod);
		}
	}

	/**
	 * <h2><code>loadScript</code></h2>
	 * 
	 * <p>
	 * Send a request to the server for a window XML script If found by the
	 * server, create and render the new window
	 * </p>
	 * 
	 * @param String
	 *            documentType - The document type to load
	 * @param String
	 *            scriptToRun - Name of the XML Script
	 * @param int
	 *            userAccessLevel - The user access level
	 * 
	 */
	private void loadScript(String documentType, String scriptToRun,
			int userAccessLevel) {
		if (documentType.equals(MenuItem.SCRIPT_TO_RUN_OBJECT)) {
			qckPnl.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			DataSet dataSet = new DataSet();
			dataSet.putStringField("[SCREEN_SCRIPT/]", scriptToRun);
			dataSet.putIntegerField("[ScriptAccessLevel/]", userAccessLevel);
			WindowFrame window = new WindowFrame(cntr);
			window.setWindow(this, dataSet);
			addTab(window);
			qckPnl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else if (documentType.equals(MenuItem.DOCUMENT_TO_LOAD_OBJECT)) {
			cntr.showWebDocument(scriptToRun);
		}
	}

	/**
	 * <h2><code>addTab</code></h2>
	 * 
	 * <p>
	 * Adds a new tab to the main tabbed pane
	 * </p>
	 * 
	 * 
	 */
 	protected void addTab(WindowFrame w) {
 		wf.add(w);
		mainTab.addTab(w.getTitle(), new CControlTab(w), w.getWindowPane());
		mainTab.setSelectedIndex(mainTab.getComponentCount() - 1);
		w.hideWindow();
		w.findFocus();
	}
 
 	
	/**
	 * <h2><code>closeTab</code></h2>
	 * 
	 * 
	 */
 	protected void deleteTab(WindowFrame w) {
 		mainTab.remove(w.getWindowPane());
 		wf.remove(w);
 		w.getMainFrame().dispatchEvent(new WindowEvent(w.getMainFrame(), WindowEvent.WINDOW_CLOSING));
    	w.getMainFrame().dispose();
 		w = null;
	}
	
 	
	/**
	 * <h2><code>removeTab</code></h2>
	 * 
	 * 
	 */
 	protected void removeTab(WindowFrame window) {
 		mainTab.remove(window.getWindowPane());
 		window.showWindow();
	}
 	
 	/**
	 * <h2><code>setLookAndFeel</code></h2>
	 * 
	 * <p>
	 * Sets the Applet client 'look and feel;
	 * </p>
	 * 
	 */
	private void setLookAndFeel() {
		try {
			RepaintManager.currentManager(this).setDoubleBufferingEnabled(true);
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			UIManager.put("Tree.expandedIcon", cntr.menuExpandIcon);
			UIManager.put("Tree.collapsedIcon",cntr.menuCollapseIcon);
			UIManager.put("Tree.selectionBorderColor", Color.darkGray);
			UIManager.put("Tree.rightChildIndent", new Integer(8));
			UIManager.put("Tree..background", cntr.bgColor);
			UIManager.put("ScrollBar.thumb", cntr.bgColor);
			UIManager.put("ScrollBar.background", cntr.bgColor);
			UIManager.put("ScrollBar.width", new Integer(14));
			UIManager.put("Panel.background", cntr.bgColor);
			UIManager.put("TabbedPane.background", cntr.bgColor);
			UIManager.put("ToolBar.background", cntr.bgColor);
			UIManager.put("Button.background", cntr.bgColor);
			UIManager.put("OptionPane.background", cntr.bgColor);
			UIManager.put("TextField.caretBlinkRate", new Integer(0));
			UIManager.put("ColorChooser.background", cntr.bgColor);
			UIManager.put("Slider.background", cntr.bgColor);
			//UIManager.put("PopupMenu.background", cntr.bgColor);
			UIManager.put("PopupMenu.background", Color.WHITE);
			UIManager.put("MenuItem.background", Color.WHITE);
			//UIManager.put("Table.background",Color.WHITE);
 			UIManager.put("Table.background", cntr.bgColor);
			UIManager.put("Viewport.background", cntr.bgColor);
			UIManager.put("RadioButton.background", cntr.bgColor);
			UIManager.put("CheckBox.background", cntr.bgColor);
			UIManager.put("ComboBox.background", Color.WHITE);
			UIManager.put("Button.border", null);

			if (menuTree != null) {
				menuTree.setBackground(cntr.bgColor);
				mnRndr.setBackgroundNonSelectionColor(cntr.bgColor);
			}

			if (optionTree != null) {
				optionTree.setBackground(cntr.bgColor);
				optRndr.setBackgroundNonSelectionColor(cntr.bgColor);
			}

			if (qckPnl != null) {
				qckPnl.setBackground(cntr.bgColor);
				Component c[] = qckPnl.getComponents();
				for (int ci = 0; ci < c.length; ci++)
					c[ci].setBackground(cntr.bgColor);
				infoT.setBackground(cntr.bgColor);
				topRight.setBackground(cntr.bgColor);
				imgPnl.setBackground(cntr.bgColor);
				bnrBar.setBackground(cntr.tbColor);
				banner.setForeground(Color.WHITE);
			}

		} catch (Exception e) {}
		
	}
	
	
	
	private void updateTheme() {
		CDialog optionCnf = new CDialog(cntr.getAppletFrame(), cntr);
		DataSet updThm = new DataSet();
		updThm.putStringField(WindowItem.METHODCLASS,"webBoltOns.server.UserSecurityManager");
		updThm.putStringField(WindowItem.CLASSNAME,"webBoltOns.server.UserSecurityManager");
		updThm.putStringField(WindowItem.METHOD, "updUserTheme");
		updThm.putStringField(WindowItem.ACTION, "EDIT");

		updThm.putStringField("UserID", cntr.user);
		updThm.putIntegerField("BackGroundColorRed", bgClrBtn.getBackground().getRed());
		updThm.putIntegerField("BackGroundColorBlue",bgClrBtn.getBackground().getBlue());
		updThm.putIntegerField("BackGroundColorGreen",bgClrBtn.getBackground().getGreen());
		updThm.putIntegerField("TitleBarColorRed", tbClrBtn.getBackground().getRed());
		updThm.putIntegerField("TitleBarColorBlue", tbClrBtn.getBackground().getBlue());
		updThm.putIntegerField("TitleBarColorGreen", tbClrBtn.getBackground().getGreen());
		updThm.putIntegerField("TitleBarFontColorRed", tbFntClrBtn.getBackground().getRed());
		updThm.putIntegerField("TitleBarFontColorBlue", tbFntClrBtn.getBackground().getBlue());
		updThm.putIntegerField("TitleBarFontColorGreen", tbFntClrBtn.getBackground().getGreen());
		updThm.putIntegerField("WindowTitleColorRed", 0);
		updThm.putIntegerField("WindowTitleColorBlue", 0);
		updThm.putIntegerField("WindowTitleColorGreen", 0);
		updThm.putIntegerField("WindowTitleFontColorRed", 0);
		updThm.putIntegerField("WindowTitleFontColorBlue", 0);
		updThm.putIntegerField("WindowTitleFontColorGreen", 0);
		updThm.putIntegerField("CursorColorRed", crsClrBtn.getBackground().getRed());
		updThm.putIntegerField("CursorColorBlue", crsClrBtn.getBackground().getBlue());
		updThm.putIntegerField("CursorColorGreen", crsClrBtn.getBackground().getGreen());
		updThm.putStringField("HeaderFontName", hdeFtNm.getFont().getFamily().trim());
		updThm.putIntegerField("HeaderFontSize", hdeFtNm.getFont().getSize());
		updThm.putBooleanField("HeaderFontItalic", hdeFtNm.getFont().isItalic());
		updThm.putBooleanField("HeaderFontBold", hdeFtNm.getFont().isBold());
		updThm.putStringField("RegularFontName", stdFtNm.getFont().getFamily());
		updThm.putIntegerField("RegularFontSize", stdFtNm.getFont().getSize());
		updThm.putBooleanField("RegularFontItalic",stdFtNm.getFont().isItalic());
		updThm.putBooleanField("RegularFontBold", stdFtNm.getFont().isBold());
		
		updThm = cntr.postServerRequestImed(updThm);
		cntr.postServerRequestImed(updThm);			
		optionCnf.showRequestCompleteDialog("Theme Updated", null);
		
		cntr.bgColor = bgClrBtn.getBackground();
		cntr.crsColor = crsClrBtn.getBackground();
		cntr.tbColor = tbClrBtn.getBackground();
		cntr.tbFontColor = tbFntClrBtn.getBackground();
		cntr.headerFont = hdeFtNm.getFont();
		cntr.standardFont = stdFtNm.getFont();
		
		setLookAndFeel();
	}
	
	
	/**
	 * <h2><code>setMenuMain</code></h2>
	 * 
	 * <p>
	 * Sets the menu and creates the user menu
	 * </p>
	 * 
	 * @param DataSet
	 *            menu - Menu object
	 */
	public void setMenuMain(DataSet menu) {
		mnuDS = menu;
		mnuDS.putStringField(MenuItem.ACTION, MenuItem.GET_MENU);
		mnuDS.putStringField("[MENU_SCRIPT/]", cntr.menuScript);
		mnuDS = cntr.postServerRequestImed(mnuDS);
		cntr.setMessageLiterals((Hashtable) mnuDS.get("[A_Literals/]"));
		mnuDS.remove("[A_Literals/]");
		
		
		if (mnuDS != null) {
			try {
				setLookAndFeel();
				buildColorButtons();
				buildMainMenu(mnuDS);
				setLookAndFeel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 

	
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(menuTree) && e.getClickCount() == 2) {
			leftPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			fireNodeSelected(menuTree);
			leftPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
		
		} else  if (e.getSource().equals(stdFtNm)) {	
			 CDialog co = new CDialog(cntr.getAppletFrame(), cntr);
			 cntr.standardFont = co.showFontPickerDialog(cntr.standardFont);
			 stdFtNm.setFont(cntr.standardFont);
			 stdFtNm.setText("<html><font color=\"blue\"><u>"
							+ cntr.standardFont.getFamily()
							+ "</u></font></html>");
			 hdeFtNm.setCursor(new Cursor(Cursor.HAND_CURSOR));
		 
			 	
			
		} else  if (e.getSource().equals(hdeFtNm)) {	 
			CDialog co = new CDialog(cntr.getAppletFrame(), cntr);
			cntr.headerFont = co.showFontPickerDialog(cntr.headerFont);
			hdeFtNm.setFont(cntr.headerFont);
			hdeFtNm.setText("<html><font color=\"blue\"><u>"
					            + cntr.headerFont.getFamily()
								+ "</u></font></html>");
			hdeFtNm.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
			
		} else  if (e.getSource().equals(d2)) {	
			cntr.showWebDocument("http://ossfree.net");
		
					
		} else  if (e.getSource().equals(optionTree)) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) optionTree.getLastSelectedPathComponent();
			if (node != null && e.getClickCount() == 2) {
				Object nodeInfo = node.getUserObject();
				String menuitem = ((JLabel) nodeInfo).getText();
				((CardLayout) crdPnl.getLayout()).show(crdPnl, menuitem);
				for (int m = 0; m < adminTxtOp.length; m++) {
					if (menuitem.equals(cntr.getMsgText(adminTxtOp[m])) ) {
						optionTree.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						((CardLayout) crdPnl.getLayout()).show(crdPnl, "Start Administrative Tasks");
						loadAdminScript(adminScpOp[m],adminScpMth[m], "");
						optionTree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				}

			}
		}

	}
	


	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(menuTree) && e.getKeyCode() == 10) {
			mnRndr.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) menuTree
					.getLastSelectedPathComponent();
			if (!node.isLeaf()) {
				if (menuTree.isCollapsed(menuTree.getLeadSelectionRow())) {
					menuTree.expandRow(menuTree.getLeadSelectionRow());
				} else {
					menuTree.collapseRow(menuTree.getLeadSelectionRow());
				}
			} else {
				fireNodeSelected(menuTree);
				mnRndr.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		
	}

	
	public void actionPerformed(ActionEvent e) {
	
		if (cntr.checkTimeout()) return;
		
		final CDialog co = new CDialog(cntr.getAppletFrame(), cntr);
		
		String cmd = e.getActionCommand();
		int hl = DataSet.checkInteger(((JButton) e.getSource()).getName());
		if(hl > 0) 
			loadScript(mnCmpTr[hl].getObjectName(), mnCmpTr[hl].getScriptToRun(), mnCmpTr[hl].getUserAccessLevel());		
		
		else if(cmd.equals("logs"))
			loadAdminScript("UserAdmin170", "getServerLogFile",(String) srvrLgs.getSelectedItem());

		else if(cmd.equals("bgClrBtn")) 
			bgClrBtn.setBackground(co.showColorPickerDialog(bgClrBtn.getBackground()));
	
		else if(cmd.equals("tbFntClrBtn")) 
			tbFntClrBtn.setBackground(co.showColorPickerDialog(tbFntClrBtn.getBackground()));
	
		else if(cmd.equals("tbClrBtn")) 
			tbClrBtn.setBackground(co.showColorPickerDialog(tbClrBtn.getBackground()));
	
		else if(cmd.equals("crsClrBtn")) 
			crsClrBtn.setBackground(co.showColorPickerDialog(crsClrBtn.getBackground()));
		
		else if(cmd.equals("saveTheme"))
			updateTheme();	

		else if(cmd.equals("ChngPswrd"))
			cntr.changeMyPassword(pw1, pw2, pw3);

		else if(cmd.equals("admMode")) {
			fireAdminButtonPerformed(combomode.getSelectedItem().toString());
			admFrm.dispose();
	
		} else if(cmd.equals("slpter")) {
			setSplit();

		} else if(cmd.equals("infoT")) {	
			doInfoT();
	
		} else if(cmd.equals("logOut") && co.showLogOutCnfrm(this )) { 
			doLogout();
			
		}
	}
	
	
	
	public void doLogout() {
		mainTab.deleteAll();
		cntr.execLogout();		
	}
	
	
	public void doInfoT() {
		infoT.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		displayAdminConsole();
		infoT.setCursor(new Cursor(Cursor.HAND_CURSOR));		
	}
	
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void fireServerRepsone(ActionEvent source, DataSet request, DataSet respose) { }



}