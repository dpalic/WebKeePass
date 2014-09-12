package webBoltOns.client.components;


/*
 * $Id: CReportEditorContainer.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.ReportRules;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayoutParameter;
import webBoltOns.dataContol.DataSet;
import webBoltOns.server.reportWriter.ReportColumn;

public class CReportEditorContainer extends JPanel implements
					StandardComponentLayout, KeyListener, ActionListener, FocusListener {

	private static final long serialVersionUID = -1172193154111946506L;
	private AppletConnector cnct;
	private JSplitPane hrzntlSpltPn;
	private ObjectRenderer objRndr;
	private JTree objTree;
	private JPanel rightPnl, leftPnl;
	private JPopupMenu popup;
	private JToolBar toolBar;
	private JScrollPane treeScrlPn;
	private ObjectTreeNode root;
	private WindowItem comp;
	private ObjectTreeNode lstSelNd;
	private ReportRules rptRls;
	private ObjectTreeNode paster;
	private JMenuItem psteAMenu;
	private JButton pasteAfter;

	
	public CReportEditorContainer() {}

	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame)  {
		
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		
		rptRls = new ReportRules();
		setName(Integer.toString(comp.getObjectHL()));
		root = new ObjectTreeNode();
		objTree = new JTree(root);
		toolBar = buildToolBar();
		setLayout(new BorderLayout());

		objTree.setRootVisible(false);
		objTree.setShowsRootHandles(true);
		objTree.putClientProperty("JTree.lineStyle", "Angled");
		objTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		rightPnl = buildPropertyPanel();
		rightPnl.setPreferredSize(new Dimension(410, comp.getHeight()));
		leftPnl = new JPanel(new BorderLayout());
		leftPnl.setPreferredSize(new Dimension(120, comp.getHeight()));

		objRndr = new ObjectRenderer();
		objTree.setCellRenderer(objRndr);
		objRndr.setBackgroundNonSelectionColor(cnct.bgColor);
		TreePath treepath = objTree.getSelectionPath();
		objTree.expandPath(treepath);

		objTree.setSelectionPath(treepath);
		objTree.setRowHeight(20);
		objTree.setBackground(cnct.bgColor);

		treeScrlPn = new JScrollPane(objTree);
		treeScrlPn.setBorder(null);
		leftPnl.add(BorderLayout.CENTER, treeScrlPn);

		hrzntlSpltPn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		hrzntlSpltPn.setLeftComponent(leftPnl);
		hrzntlSpltPn.setRightComponent(rightPnl);
		hrzntlSpltPn.setDividerSize(3);
		JPanel mainPanel = new JPanel();

		mainPanel.add(hrzntlSpltPn);
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));

		toolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));

		add(mainPanel, BorderLayout.CENTER);
		add(toolBar, BorderLayout.NORTH);

		objTree.addMouseListener(buildTreeMouseListener());
		objTree.setName("tree");
		objTree.addKeyListener(this);

		hrzntlSpltPn.setBorder(null);
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this,4);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this,2);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(this, 0);
		}
		objTree.updateUI();
	}

	private JPanel buildPropertyPanel() {
		JPanel propertyPanel = new JPanel(new GridFlowLayout());

		((JComboBox) rptRls.propertyComponents[4]).addItem("");
		((JComboBox) rptRls.propertyComponents[4])
				.addItem(WindowItem.LEFT);
		((JComboBox) rptRls.propertyComponents[4])
				.addItem(WindowItem.CENTER);
		((JComboBox) rptRls.propertyComponents[4])
				.addItem(WindowItem.RIGHT);

		((JComboBox) rptRls.propertyComponents[5]).addItem("INT");
		((JComboBox) rptRls.propertyComponents[5]).addItem("FLT");
		((JComboBox) rptRls.propertyComponents[5]).addItem("CHR");
		((JComboBox) rptRls.propertyComponents[5]).addItem("DAT");

		((JComboBox) rptRls.propertyComponents[6]).addItem("Y");
		((JComboBox) rptRls.propertyComponents[6]).addItem("N");

		((JComboBox) rptRls.propertyComponents[7]).addItem("Y");
		((JComboBox) rptRls.propertyComponents[7]).addItem("N");

		((JComboBox) rptRls.propertyComponents[8]).addItem("Y");
		((JComboBox) rptRls.propertyComponents[8]).addItem("N");

		((JComboBox) rptRls.propertyComponents[9]).addItem("Y");
		((JComboBox) rptRls.propertyComponents[9]).addItem("N");

		((JComboBox) rptRls.propertyComponents[10]).addItem("Y");
		((JComboBox) rptRls.propertyComponents[10]).addItem("N");

		for (int p = 0; p < rptRls.propertyLabel.length; p++) {
			final Component c = rptRls.propertyComponents[p];
			propertyPanel.add(rptRls.propertyLabel[p],new GridFlowLayoutParameter(true, 0));
			c.setPreferredSize(new Dimension(185, 19));
			c.setName("comp");
			c.addKeyListener(this);
			if (c instanceof JTextField)
				c.addFocusListener(this);
			propertyPanel.add(c, new GridFlowLayoutParameter(false, 1));
		}
		return propertyPanel;
	}

	/***************************************************************************
	 * 
	 * Clear the Screen.
	 * 
	 *  
	 */
	public void clearComponent(String defaultValue) {

		root.removeAllChildren();
		ObjectTreeNode nodeB = new ObjectTreeNode();
		ReportColumn c =new ReportColumn();
		nodeB.setUserObject(c);
		nodeB.setAttributeColumn(c);
		root.add(nodeB);
		objTree.updateUI();
		for (int p = 0; p < rptRls.propertyTypes.length; p++) {
			rptRls.propertyLabel[p].setVisible(false);
			rptRls.propertyComponents[p].setVisible(false);
			if (rptRls.propertyComponents[p] instanceof JTextField) {
				((JTextField) rptRls.propertyComponents[p])
						.setText("");
			} else if (rptRls.propertyComponents[p] instanceof JComboBox) {
				((JComboBox) rptRls.propertyComponents[p])
						.setSelectedItem("");
			}
		}

	}

	public boolean validateComponent(String action, String editorName) {
		return true;
	}

	/***************************************************************************
	 * 
	 * Populate the screen
	 * 
	 *  
	 */
	public void populateComponent(String action, String editorName,
			DataSet dataSet) {
		DataSet script = (DataSet) dataSet.get(editorName);

		if (script != null)
			loadTree(script);
	}

	private void loadTree(DataSet script) {
		final Vector vf = script.getTableVector(ReportColumn.REPORT_DETAILS);
		final Enumeration eObjects = vf.elements();
		clearComponent(null);
		root.removeAllChildren();
		while (eObjects.hasMoreElements()) {
			ReportColumn rColumn = (ReportColumn) eObjects.nextElement();
			ObjectTreeNode node = new ObjectTreeNode();
			node.setAttributeColumn(rColumn);
			node.setUserObject(rColumn);
			root.add(node);
		}
		objTree.setVisible(true);
		rightPnl.setVisible(true);
		objTree.updateUI();
		rightPnl.updateUI();
		objTree.grabFocus();
		hrzntlSpltPn.setDividerLocation(220);
	}

	public DataSet populateDataSet(String action, String editorName,
			DataSet dataSet) {
		dataSet.putStringField("TypeID", "[REPORT_SCRIPT/]");
		commitEditing((ObjectTreeNode) objTree
				.getLastSelectedPathComponent());
		Vector path = new Vector();
		path = getObjectAttributePath(path, root);
		if (!path.isEmpty())
			dataSet.putTableVector("[Path/]", path);
		return dataSet;
	}

	public String getSelectedComponentItem() {
		if (lstSelNd != null)
			return lstSelNd.getAttributeColumn().getFeildName();
		else
			return null;
	}

	public Vector<ReportColumn> getObjectAttributePath(Vector<ReportColumn> path, ObjectTreeNode node) {
		path.add(node.getAttributeColumn());
		ObjectTreeNode children[] = node.getChildren();
		if (children != null)
			for (int c = 0; c < children.length; c++) {
				path = getObjectAttributePath(path, children[c]);
			}
		return path;
	}

	private void commitEditing(ObjectTreeNode selectedTreeNode) {

		for (int p = 1; p < rptRls.propertyTypes.length; p++) {
			if (rptRls.propertyComponents[p] instanceof JSpinner) {
				try {
					((JSpinner.NumberEditor) ((JSpinner) rptRls.propertyComponents[p])
							.getEditor()).commitEdit();
				} catch (java.text.ParseException pe) {
				}
			}
		}

		ReportColumn rc = new ReportColumn();
		rc.setFeildName(((JTextField) rptRls.propertyComponents[0])
				.getText());
		rc.setDescription(((JTextField) rptRls.propertyComponents[1])
				.getText());
		rc.setLength(((JSpinner) rptRls.propertyComponents[2])
				.getValue().toString());
		rc.setDecimals(((JSpinner) rptRls.propertyComponents[3])
				.getValue().toString());
		rc.setAlignment(((JComboBox) rptRls.propertyComponents[4])
				.getSelectedItem().toString());
		rc.setDataType(((JComboBox) rptRls.propertyComponents[5])
				.getSelectedItem().toString());
		rc.setSubTotaled(((JComboBox) rptRls.propertyComponents[6])
				.getSelectedItem().toString().trim());
		rc.setSubMaximum(((JComboBox) rptRls.propertyComponents[7])
				.getSelectedItem().toString().trim());
		rc.setSubMinimum(((JComboBox) rptRls.propertyComponents[8])
				.getSelectedItem().toString().trim());
		rc.setSubAverage(((JComboBox) rptRls.propertyComponents[9])
				.getSelectedItem().toString().trim());
		rc.setSubCounted(((JComboBox) rptRls.propertyComponents[10])
				.getSelectedItem().toString().trim());
		rc.setLevelBreak(((JSpinner) rptRls.propertyComponents[11])
				.getValue().toString());

		if (lstSelNd != null)
			lstSelNd.setAttributeColumn(rc);
		lstSelNd = selectedTreeNode;
		objTree.updateUI();
	}

	private void fireNodeSelected(ObjectTreeNode selectedTreeNode) {

		if (selectedTreeNode != null) {
			toolBar.setEnabled(true);

			ReportColumn rc = selectedTreeNode.getAttributeColumn();
			commitEditing(selectedTreeNode);

			((JTextField) rptRls.propertyComponents[0]).setText(rc
					.getFeildName());
			((JTextField) rptRls.propertyComponents[1]).setText(rc
					.getDescription());
			((JSpinner) rptRls.propertyComponents[2])
					.setValue(new Integer(rc.getLength()));
			((JSpinner) rptRls.propertyComponents[3])
					.setValue(new Integer(rc.getDecimals()));
			((JComboBox) rptRls.propertyComponents[4])
					.setSelectedItem(rc.getAlignment());
			((JComboBox) rptRls.propertyComponents[5])
					.setSelectedItem(rc.getDataType());

			if (rc.isSubTotaled())
				((JComboBox) rptRls.propertyComponents[6])
						.setSelectedItem("Y");
			else
				((JComboBox) rptRls.propertyComponents[6])
						.setSelectedItem("N");

			if (rc.isSubMaximum())
				((JComboBox) rptRls.propertyComponents[7])
						.setSelectedItem("Y");
			else
				((JComboBox) rptRls.propertyComponents[7])
						.setSelectedItem("N");

			if (rc.isSubMinumum())
				((JComboBox) rptRls.propertyComponents[8])
						.setSelectedItem("Y");
			else
				((JComboBox) rptRls.propertyComponents[8])
						.setSelectedItem("N");

			if (rc.isSubAveraged())
				((JComboBox) rptRls.propertyComponents[9])
						.setSelectedItem("Y");
			else
				((JComboBox) rptRls.propertyComponents[9])
						.setSelectedItem("N");

			if (rc.isSubCounted())
				((JComboBox) rptRls.propertyComponents[10])
						.setSelectedItem("Y");
			else
				((JComboBox) rptRls.propertyComponents[10])
						.setSelectedItem("N");

			((JSpinner) rptRls.propertyComponents[11])
					.setValue(new Integer(rc.getLevelBreak()));

			for (int p = 0; p < rptRls.propertyTypes.length; p++) {
				rptRls.propertyLabel[p].setVisible(true);
				rptRls.propertyComponents[p].setVisible(true);
			}
		}
		updateUI();
	}

	private void cutObjectTreeNode(ObjectTreeNode nodeToRemove) {
		if (nodeToRemove != null) {
			paster = nodeToRemove;
			ObjectTreeNode parent = (ObjectTreeNode) nodeToRemove.getParent();
			if (parent != null) {
				parent.remove(nodeToRemove);
			}
			objTree.updateUI();
			psteAMenu.setEnabled(true);
			pasteAfter.setEnabled(true);
		}
	}

	private void copyObjectTreeNode(ObjectTreeNode nodeToCopy) {
		if (nodeToCopy != null) {
			paster = (ObjectTreeNode) nodeToCopy.clone();
			objTree.updateUI();
			psteAMenu.setEnabled(true);
			pasteAfter.setEnabled(true);
		}
	}

	private void moveUpObjectTreeNode(ObjectTreeNode nodeToMove) {
		if (nodeToMove != null) {
			ObjectTreeNode parent = (ObjectTreeNode) nodeToMove.getParent();
			int index = parent.getIndex(nodeToMove);
			if (index > 0) {
				parent.insert(nodeToMove, index - 1);
			}
			objTree.updateUI();
		}
	}

	private void moveDownObjectTreeNode(ObjectTreeNode nodeToMove) {
		if (nodeToMove != null) {
			ObjectTreeNode parent = (ObjectTreeNode) nodeToMove.getParent();
			int index = parent.getIndex(nodeToMove);
			if (parent.getChildCount() > index + 1) {
				parent.insert(nodeToMove, index + 1);
			}
			objTree.updateUI();
		}
	}

	public MouseListener buildTreeMouseListener() {
		JMenuItem mnu;
		//Create the popup menu.
		popup = new JPopupMenu();
		mnu = new JMenuItem("Cut Item");
		mnu.setIcon(cnct.getImageIcon("ecut.gif"));
		mnu.setActionCommand("cut");
		mnu.addActionListener(this);
		popup.add(mnu);

		mnu = new JMenuItem("Copy Item");
		mnu.setIcon(cnct.getImageIcon("ecopy.gif"));
		mnu.setActionCommand("copy");
		mnu.addActionListener(this);
		popup.add(mnu);

		psteAMenu = new JMenuItem("Paste After");
		psteAMenu.setActionCommand("pastafter");
		psteAMenu.setIcon(cnct.getImageIcon("ePaste.gif"));
		psteAMenu.addActionListener(this);
		popup.add(psteAMenu);
		psteAMenu.setEnabled(false);

		popup.addSeparator();

		mnu = new JMenuItem("Move Up");
		mnu.setIcon(cnct.getImageIcon("eup.gif"));
		mnu.setActionCommand("up");
		mnu.addActionListener(this);
		popup.add(mnu);

		mnu = new JMenuItem("Move Down");
		mnu.setIcon(cnct.getImageIcon("edown.gif"));
		mnu.setActionCommand("down");
		mnu.addActionListener(this);
		popup.add(mnu);

		popup.addSeparator();

		MouseListener popupListener = new TreeMouseListener(popup);
		return popupListener;
	}

	private JToolBar buildToolBar() {
		JButton mnu;
		JToolBar tb = new JToolBar();

		mnu = cnct.buildFancyButton("Cut  ", "ecut.gif", null, ' ');
		mnu.setActionCommand("cut");
		mnu.addActionListener(this);
		tb.add(mnu, null);

		mnu = cnct.buildFancyButton("Copy ", "ecopy.gif", null, ' ');
		mnu.setActionCommand("copy");
		mnu.addActionListener(this);
		tb.add(mnu, null);

		pasteAfter = cnct.buildFancyButton("Paste After ", "epaste.gif", null, ' ');
		pasteAfter.setActionCommand("pastafter");
		pasteAfter.addActionListener(this);
		tb.add(pasteAfter);
		pasteAfter.setEnabled(false);

		mnu = cnct.buildFancyButton("Up  ", "eup.gif", null, ' ');
		mnu.setActionCommand("up");
		mnu.addActionListener(this);
		tb.add(mnu);

		mnu = cnct.buildFancyButton("Down  ", "edown.gif", null, ' ');
		mnu.setActionCommand("down");
		mnu.addActionListener(this);
		tb.add(mnu);

		mnu = cnct.buildFancyButton("Insert Aft  ", "eInsertAfter.gif", null, ' ');
		mnu.setActionCommand("insfter");
		mnu.addActionListener(this);

		tb.add(mnu);

		return tb;
	}


	private void pasteObjectTreeNode(ObjectTreeNode referenceNode, String action) {

		if (paster != null) {
			ObjectTreeNode parent = (ObjectTreeNode) referenceNode.getParent();
			parent.insert(paster, parent.getIndex(referenceNode) + 1);
		}
		objTree.updateUI();
		pasteAfter.setEnabled(false);
		psteAMenu.setEnabled(false);
	}

	private void insertObjectTreeNode(ObjectTreeNode selectedNode,
			Object popUpMenu, String action) {

		if (selectedNode != null) {
			ReportColumn newColumn = new ReportColumn();
			ObjectTreeNode newNode = new ObjectTreeNode();
			newNode.setAttributeColumn(newColumn);
			newNode.setUserObject(newColumn);
			ObjectTreeNode parent = (ObjectTreeNode) selectedNode.getParent();
			parent.insert(newNode, parent.getIndex(selectedNode) + 1);
			objTree.updateUI();
		}
	}

	public void initializeComponentUI() {
	}

	class TreeMouseListener extends MouseAdapter {
		JPopupMenu popup;

		TreeMouseListener(JPopupMenu popupMenu) {
			popup = popupMenu;
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				leftPnl.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				fireNodeSelected((ObjectTreeNode) objTree
						.getLastSelectedPathComponent());
				leftPnl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	class OptRenderer extends DefaultTreeCellRenderer {


		private static final long serialVersionUID = -7325794743668391278L;

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

	class ObjectRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = 7965044528993781037L;

		ImageIcon dcIcon;

		ImageIcon scIcon;

		public ObjectRenderer() {
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);

			ReportColumn rc = ((ObjectTreeNode) value).getAttributeColumn();

			if (rc != null) {
				setText("Column :" + rc.getFeildName());
			}
			if (cnct != null) {
				setIcon(cnct.menuDocumentIcon);
			}
			return this;

		}
	}

	class ObjectTreeNode extends DefaultMutableTreeNode {

		private static final long serialVersionUID = 5271136616260988190L;
		private ReportColumn rColomn;

		public void setAttributeColumn(ReportColumn c) {
			rColomn = c;
		}

		public ReportColumn getAttributeColumn() {
			return rColomn;
		}

		public ObjectTreeNode[] getChildren() {
			if (isLeaf()) {
				return null;
			} else {
				ObjectTreeNode[] children = new ObjectTreeNode[getChildCount()];
				for (int c = 0; c < getChildCount(); c++)
					children[c] = (ObjectTreeNode) getChildAt(c);
				return children;
			}
		}

	}



	public void keyReleased(KeyEvent e) {
		String cmd = ((Component)e.getSource()).getName();
		
		if (cmd.equals("tree") && objTree.getLastSelectedPathComponent() != null)
			fireNodeSelected((ObjectTreeNode) objTree.getLastSelectedPathComponent());
		
		
		else if(cmd.equals("comp") && objTree.getLastSelectedPathComponent() != null
				&& e.getKeyCode() == 10) 
			commitEditing((ObjectTreeNode) objTree .getLastSelectedPathComponent());
		
	}

	


	public void focusGained(FocusEvent e) {
		JTextField c = ((JTextField)e.getSource());
		c.selectAll();
		c.setBackground(cnct.crsColor);
	}

	public void focusLost(FocusEvent e) {
		JTextField c = ((JTextField)e.getSource());
		c.setBackground(Color.white);
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();		
		if(cmd.equals("cut"))
			cutObjectTreeNode((ObjectTreeNode) objTree.getLastSelectedPathComponent());
		else if(cmd.equals("copy"))
			copyObjectTreeNode((ObjectTreeNode) objTree.getLastSelectedPathComponent());
		else if(cmd.equals("up"))
			moveUpObjectTreeNode((ObjectTreeNode) objTree.getLastSelectedPathComponent());
		else if(cmd.equals("down"))
			moveDownObjectTreeNode((ObjectTreeNode) objTree.getLastSelectedPathComponent());
		else if(cmd.equals("pastafter"))
			pasteObjectTreeNode((ObjectTreeNode) objTree.getLastSelectedPathComponent(), "[Insert/]");
		else if(cmd.equals("insfter"))
			insertObjectTreeNode((ObjectTreeNode) objTree.getLastSelectedPathComponent(), e.getSource(), "[Insert/]");
	
	}
	
	
	
	public void setProperty(String propertyName, String propertyValue) {}
	public void setValid(boolean invalid) {}	
	public boolean locateCursor() {return false;}
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	
}