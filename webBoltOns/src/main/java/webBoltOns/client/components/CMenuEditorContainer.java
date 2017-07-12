package webBoltOns.client.components;

/*
 * $Id: CMenuEditorContainer.java,v 1.1 2007/04/20 19:37:20 paujones2005 Exp $ $Name:  $
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
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
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
import webBoltOns.client.MenuItem;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.MenuRules;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowParm;
import webBoltOns.dataContol.DataSet;

public class CMenuEditorContainer extends JPanel 
	                  implements StandardComponentLayout, ActionListener, KeyListener, FocusListener {
	private static final long serialVersionUID = 2661220928479271296L;
	private int objectHL= 0;
	private AppletConnector cnec;
	private JSplitPane horizontalSplitPane;
	private ObjectRenderer objectRenderer;
	private JTree objectTree;
	private JPanel rightPanel, leftPanel;
	private JPopupMenu popup;
	private  JToolBar toolBar ;
	private JScrollPane treeScrollpane;
	private ObjectTreeNode root;
	private WindowItem comp;
	private ObjectTreeNode lastSelectedNode;
	private MenuRules scriptEditRules;
	private JButton insertAfter, insertChild; 
	private JPopupMenu insertAfterPopup, insertChildPopup;
	private JMenuItem[] mnCompInsrtChld, 
						mnCmpInsrtAftr,
						tlCmpInsrtChld, 
						tlCmpInsrtAftr;

	private ObjectTreeNode paster;
	private  JMenuItem pasteAfterMenu, pasteChildMenu;
	private JButton pasteAfter, pasteChild;

	
 
	public CMenuEditorContainer() {}

	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		
		comp = thisItem;
		cnec = mainFrame.getAppletConnector();	
		scriptEditRules = new MenuRules();
		
		setDoubleBuffered(true);
		mnCompInsrtChld = new JMenuItem [scriptEditRules.screenComponents.length];
		mnCmpInsrtAftr = new JMenuItem [scriptEditRules.screenComponents.length];
		tlCmpInsrtChld = new JMenuItem [scriptEditRules.screenComponents.length];
		tlCmpInsrtAftr = new JMenuItem [scriptEditRules.screenComponents.length];
		
		for(int m=0; m < scriptEditRules.screenComponents.length; m++) {
			mnCompInsrtChld[m] = new JMenuItem(scriptEditRules.screenComponents[m]);
	  	   	mnCmpInsrtAftr [m] = new JMenuItem(scriptEditRules.screenComponents[m]);
	  		tlCmpInsrtChld[m] = new JMenuItem(scriptEditRules.screenComponents[m]);
	  		tlCmpInsrtChld[m].setIcon(cnec.menuScriptIcon);
	  	   	tlCmpInsrtAftr [m] = new JMenuItem(scriptEditRules.screenComponents[m]);
	  	    tlCmpInsrtAftr [m].setIcon(cnec.menuScriptIcon);
		}

		
		setName(Integer.toString(comp.getObjectHL()));
		root = new ObjectTreeNode();
		objectTree = new JTree(root);
        toolBar = buildToolBar();
        setLayout(new BorderLayout());
    
		objectTree.setRootVisible(false);
		objectTree.setShowsRootHandles(true);
		objectTree.putClientProperty("JTree.lineStyle", "Angled");
		objectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
		rightPanel = buildPropertyPanel();
		rightPanel.setPreferredSize(new Dimension(410, comp.getHeight()));
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(120, comp.getHeight()));

		objectRenderer = new ObjectRenderer();
		objectTree.setCellRenderer(objectRenderer);
		objectRenderer.setBackgroundNonSelectionColor(cnec.bgColor);
		TreePath treepath = objectTree.getSelectionPath();
		objectTree.expandPath(treepath);

		objectTree.setSelectionPath(treepath);
		objectTree.setRowHeight(20);
		objectTree.setBackground(cnec.bgColor);
		
		treeScrollpane = new JScrollPane(objectTree);
		treeScrollpane.setBorder(null);
		leftPanel.add(BorderLayout.CENTER, treeScrollpane);

		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		horizontalSplitPane.setLeftComponent(leftPanel);
		horizontalSplitPane.setRightComponent(rightPanel);
		horizontalSplitPane.setDividerSize(3);
        JPanel mainPanel = new JPanel();
        
        mainPanel.add(horizontalSplitPane);
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));
		
	
		toolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));
	
		add(mainPanel, BorderLayout.CENTER);
		add(toolBar, BorderLayout.NORTH);
		
		objectTree.addMouseListener(buildTreeMouseListener());
		objectTree.setName("objectTree");
		objectTree.addKeyListener(this);

		horizontalSplitPane.setBorder(null);
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 4);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this,2);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(this, 0);
		}
		objectTree.updateUI();
	}

	private JPanel buildPropertyPanel() {
		JPanel propertyPanel = new JPanel(new GridFlowLayout());

		((JTextField) scriptEditRules.propertyComponents[0]).setEditable(false);
		((JTextField) scriptEditRules.propertyComponents[0]).setFocusable(false);
		((JTextField) scriptEditRules.propertyComponents[0]).setBackground(new Color(225, 225, 225));

		((JComboBox) scriptEditRules.propertyComponents[3]).addItem("FALSE");
		((JComboBox) scriptEditRules.propertyComponents[3]).addItem("TRUE");

		for (int p = 0; p < scriptEditRules.propertyLabel.length; p++) {
			final Component c = scriptEditRules.propertyComponents[p];
			propertyPanel.add(scriptEditRules.propertyLabel[p], new GridFlowParm(true, 0));
			c.setPreferredSize(new Dimension(185, 19));
			c.setName("comp");
			c.addKeyListener(this);
			if(c instanceof JTextField) 		
				c.addFocusListener(this);
			propertyPanel.add(c, new GridFlowParm(false, 1));
		}
		return propertyPanel;
	}

 
	public void clearComponent(String defaultValue) {
		root.removeAllChildren();
		ObjectTreeNode nodeB = new ObjectTreeNode();
		nodeB.setAttributeString( " [OBJECT_NAME='SubMenu' /]");
		nodeB.setUserObject( " [OBJECT_NAME='SubMenu' /]");
		root.add(nodeB);
		objectTree.updateUI();
		for (int p = 0; p < scriptEditRules.propertyTypes.length; p++) {	
			scriptEditRules.propertyLabel[p].setEnabled(false);
			scriptEditRules.propertyComponents[p].setVisible(false);
			if (scriptEditRules.propertyComponents[p] instanceof JTextField) {
					((JTextField) scriptEditRules.propertyComponents[p]).setText("");
				} else if (scriptEditRules.propertyComponents[p] instanceof JComboBox) {
					((JComboBox) scriptEditRules.propertyComponents[p]).setSelectedItem("");
			}
		}
	}

	public boolean validateComponent(String action, String editorName) {return true;}
	public String getString() {return null;}
	
	public void populateComponent(String action, String editorName,
			DataSet dataSet) {
		DataSet script = (DataSet) dataSet.get(editorName);
		
		if (script != null)
			loadTree(script);
	}

	private void loadTree(DataSet script) {
		 
		final Vector vf = script.getTableVector(MenuItem.SCREEN_OBJECTS);
		final Enumeration eObjects = vf.elements();
		//String objectAttributeString;
		clearComponent(null);
		root.removeAllChildren();
		Hashtable<String, ObjectTreeNode> screenObjects = new Hashtable<String, ObjectTreeNode>();
		screenObjects.put("0", root);
		
		while (eObjects.hasMoreElements()) {
			StringBuffer attributeList = new StringBuffer(); 
			String attributeString = (String) eObjects.nextElement();
			String selectedItemType = DataSet.parseProperty("OBJECT_NAME",
						attributeString);
			String parent_hl = DataSet.parseProperty(
					WindowItem.PARENT_HL, attributeString);
			String object_hl = DataSet.parseProperty(
					WindowItem.OBJECT_HL, attributeString);
			String [] rules = (String []) scriptEditRules.propertyRules.get(selectedItemType);
			
			for (int p = 0; p < scriptEditRules.propertyTypes.length; p++) {
				if(rules[p].equals("T")) {
					attributeList.append(" " + scriptEditRules.propertyTypes[p] + "='" + DataSet
					.parseProperty(scriptEditRules.propertyTypes[p], attributeString) +"' "); 
				}
			}			
	
			ObjectTreeNode node = new ObjectTreeNode();	
			node.setAttributeString(attributeList.toString());
			node.setUserObject(attributeList.toString());
			screenObjects.put(object_hl, node);
				((ObjectTreeNode) screenObjects.get(parent_hl)).add(node);				
		}
		objectTree.setVisible(true);
		rightPanel.setVisible(true);
		objectTree.updateUI();
		rightPanel.updateUI();
		objectTree.grabFocus();
		horizontalSplitPane.setDividerLocation(220);
	}

	
	
	public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {
		dataSet.putStringField("TypeID", "[MENU_SCRIPT/]"); 
		commitEditing((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		Vector path = new Vector ();
		objectHL = 1;
		path = getObjectAttributePath(path, root, objectHL);
		if(!path.isEmpty())
			dataSet.putTableVector("[Path/]", path);		
		return dataSet;
	}
	

	public String getSelectedComponentItem() {
		if(lastSelectedNode != null)
			return DataSet.parseProperty(MenuItem.LINK,  lastSelectedNode.getAttributeString());
		else 
			return null;
	}

	
	public Vector<Object> getObjectAttributePath (Vector<Object> path, ObjectTreeNode node,
			int parentHL) {
    	String objectAttributeString = node.getAttributeString();
    	if (objectAttributeString != null ) {
    		objectHL++;
    		path.add("[" + "HLP"  + "='" + parentHL + "'  HLO='"  + objectHL  + "'/]-->"
						+ objectAttributeString ) ;
    	}	
    	ObjectTreeNode children [] = node.getChildren();
    	int p = objectHL;
    	if(children != null)
    		for(int c=0; c< children.length; c++) {
    			path = getObjectAttributePath(path, 
    					children[c], p);
    	    }	
    	return path;
    }
	
	
	private void commitEditing(ObjectTreeNode selectedTreeNode) {
		String attributes[] = new String[scriptEditRules.propertyTypes.length];
		StringBuffer attributelist = new StringBuffer();
		attributelist.append(" " + scriptEditRules.propertyTypes[0] + "='" + 
		                ((JTextField) scriptEditRules.propertyComponents[0]).getText() + "'" );
																							
		for (int p = 1; p < scriptEditRules.propertyTypes.length; p++) {
			if(scriptEditRules.propertyComponents[p].isVisible() ){
				if (scriptEditRules.propertyComponents[p] instanceof JTextField) {
					attributes[p] = ((JTextField) scriptEditRules.propertyComponents[p]).getText();
			
				} else if (scriptEditRules.propertyComponents[p] instanceof JComboBox) {
					attributes[p] = ((JComboBox) scriptEditRules.propertyComponents[p])
					.getSelectedItem().toString();

				} else if (scriptEditRules.propertyComponents[p] instanceof JSpinner) {
					try {
					((JSpinner.NumberEditor) ((JSpinner) scriptEditRules.propertyComponents[p])
							.getEditor()).commitEdit();
					} catch (java.text.ParseException pe) {}
					attributes[p] = ((JSpinner) scriptEditRules.propertyComponents[p]).getValue()
						.toString();
				}
				attributelist.append(" " + scriptEditRules.propertyTypes[p] + "='" + attributes[p] + "'");
			}		
		}
		
		if (lastSelectedNode != null)
			lastSelectedNode.setAttributeString(attributelist.toString());
		lastSelectedNode = selectedTreeNode;
	 	objectTree.updateUI();
	}


	private void fireNodeSelected(ObjectTreeNode selectedTreeNode) {

		if (selectedTreeNode != null) {
		    toolBar.setEnabled(true);
			String attributeString = selectedTreeNode.getAttributeString();
			String selectedItemType = DataSet.parseProperty("OBJECT_NAME",
					attributeString);
			
			if(!selectedItemType.equals("")) {
				commitEditing(selectedTreeNode);
				String [] rules = (String [])  scriptEditRules.propertyRules.get(selectedItemType);
			
				for (int p = 0; p < scriptEditRules.propertyTypes.length; p++) {
					if(rules[p].equals("F")) {
						scriptEditRules.propertyLabel[p].setEnabled(false);
						scriptEditRules.propertyComponents[p].setVisible(false);
					} else {
						scriptEditRules.propertyLabel[p].setEnabled(true);
						scriptEditRules.propertyComponents[p].setVisible(true);
						if (scriptEditRules.propertyComponents[p] instanceof JTextField) {
							((JTextField) scriptEditRules.propertyComponents[p]).setText(DataSet
									.parseProperty(scriptEditRules.propertyTypes[p], attributeString));

						} else if (scriptEditRules.propertyComponents[p] instanceof JComboBox) {
							((JComboBox) scriptEditRules.propertyComponents[p])
							.setSelectedItem(DataSet.parseProperty(
									scriptEditRules.propertyTypes[p], attributeString));

						} else if (scriptEditRules.propertyComponents[p] instanceof JSpinner) {
							((JSpinner) scriptEditRules.propertyComponents[p]).setValue(new Integer(
									DataSet.checkInteger(DataSet.parseProperty(
											scriptEditRules.propertyTypes[p], attributeString))));
						}	
					}
				}
			}
		}
		updateUI();
	}

	
	private void cutObjectTreeNode(ObjectTreeNode nodeToRemove) {
	 if(nodeToRemove != null) {	
		paster = nodeToRemove;
		ObjectTreeNode parent = (ObjectTreeNode) nodeToRemove.getParent();
		if (parent != null) {
			parent.remove(nodeToRemove);
		}
		objectTree.updateUI();
		pasteAfterMenu.setEnabled(true);
		pasteChildMenu.setEnabled(true);
		pasteAfter.setEnabled(true);
		pasteChild.setEnabled(true);
	 }
	}

	
	private void copyObjectTreeNode(ObjectTreeNode nodeToCopy) {
	   if(nodeToCopy != null) {	
		paster = (ObjectTreeNode)nodeToCopy.clone();
		objectTree.updateUI();
		pasteAfterMenu.setEnabled(true);
		pasteChildMenu.setEnabled(true);
		pasteAfter.setEnabled(true);
		pasteChild.setEnabled(true);
	   } 	
	}

	
	private void moveUpObjectTreeNode(ObjectTreeNode nodeToMove) {
		ObjectTreeNode parent = (ObjectTreeNode) nodeToMove.getParent();
		int index = parent.getIndex(nodeToMove);
		if (index > 0) {
			parent.insert(nodeToMove, index - 1);
		}
		objectTree.updateUI();
	}

	private void moveDownObjectTreeNode(ObjectTreeNode nodeToMove) {
		ObjectTreeNode parent = (ObjectTreeNode) nodeToMove.getParent();
		int index = parent.getIndex(nodeToMove);
		if (parent.getChildCount() > index + 1) {
			parent.insert(nodeToMove, index + 1);
		}
		objectTree.updateUI();
	}


	
	
	public MouseListener buildTreeMouseListener() {
		JMenuItem mnu;
		//Create the popup menu.
		popup = new JPopupMenu();
		mnu = new JMenuItem("Cut Item");
		mnu.setActionCommand("cut");
		mnu.setIcon(cnec.getImageIcon("ecut.gif"));
		mnu.addActionListener(this);
		popup.add(mnu);
		
		mnu = new JMenuItem("Copy Item");
		mnu.setActionCommand("copy");
		mnu.setIcon(cnec.getImageIcon("ecopy.gif"));
		mnu.addActionListener(this);
		popup.add(mnu);
		
		pasteChildMenu = new JMenuItem("Paste as Child");
		pasteChildMenu.setIcon(cnec.getImageIcon("ePasteChild.gif"));
		pasteChildMenu.setActionCommand("pasteChild");
		pasteChildMenu.addActionListener(this);
		popup.add(pasteChildMenu);
		pasteChildMenu.setEnabled(false);
		
		pasteAfterMenu = new JMenuItem("Paste After");
		pasteAfterMenu.setIcon(cnec.getImageIcon("ePaste.gif"));
		pasteAfterMenu.setActionCommand("pasteAfter");
		pasteAfterMenu.addActionListener(this);
		popup.add(pasteAfterMenu);
		pasteAfterMenu.setEnabled(false);

		popup.addSeparator();
		
		mnu = new JMenuItem("Move Up");
		mnu.setIcon(cnec.getImageIcon("eup.gif"));
		mnu.setActionCommand("up");
		mnu.addActionListener(this);
		popup.add(mnu);

		mnu = new JMenuItem("Move Down");
		mnu.setIcon(cnec.getImageIcon("edown.gif"));
		mnu.setActionCommand("down");
		mnu.addActionListener(this);
		popup.add(mnu);

		popup.addSeparator();

		popup.add(buildInsertChildMenu());
		popup.add(buildInsertAfterMenu());

		return new TreeMouseListener(popup);
		
	}
	
	
	
	
	private JToolBar buildToolBar() {
		JButton mnu;
		JToolBar tb = new JToolBar();
		mnu = cnec.buildFancyButton("Cut ", "ecut.gif" ,null, ' ');
		mnu.setActionCommand("cut");
		mnu.addActionListener(this);
		tb.add(mnu, null);
		
		mnu = cnec.buildFancyButton("Copy ", "ecopy.gif" ,null, ' ');
		mnu.setActionCommand("copy");
		mnu.addActionListener(this);
		tb.add(mnu, null);

		pasteAfter = cnec.buildFancyButton("Paste After ", "epaste.gif" ,null, ' ');
		pasteAfter.setActionCommand("pasteAfter");
		pasteAfter.addActionListener(this);
		tb.add(pasteAfter);
		pasteAfter.setEnabled(false);
		
		pasteChild = cnec.buildFancyButton("Paste Child ", "epastechild.gif" ,null, ' ');
		pasteChild.setActionCommand("pasteChild");
		pasteChild.addActionListener(this);
		tb.add(pasteChild);
		pasteChild.setEnabled(false);
				
		mnu = cnec.buildFancyButton("Up ", "eup.gif" ,null, ' ');
		mnu.setActionCommand("up");
		mnu.addActionListener(this);
		tb.add(mnu);

		mnu = cnec.buildFancyButton("Down ", "edown.gif" ,null, ' ');
		mnu.setActionCommand("down");
		mnu.addActionListener(this);
		tb.add(mnu);

		insertAfter = cnec.buildFancyButton("Insert After ", "einsertafter.gif" ,null, ' '); 
		insertAfter.setMnemonic('i');
		insertAfterPopup = new JPopupMenu();
		for (int sm = 0; sm < tlCmpInsrtAftr.length; sm++) {
			tlCmpInsrtAftr[sm].setActionCommand("insertAfter");
			tlCmpInsrtAftr[sm].addActionListener(this);
			insertAfterPopup.add(tlCmpInsrtAftr[sm]);
		}		
		
		insertAfter.setActionCommand("insertAfterPopup");
		insertAfter.addActionListener(this);		
		tb.add(insertAfter);

		
		insertChild = cnec.buildFancyButton("Insert Child ", "einsertchild.gif" ,null, ' ');
		insertChildPopup = new JPopupMenu();
		for (int sm = 0; sm < tlCmpInsrtChld.length; sm++) {
			tlCmpInsrtChld[sm].setActionCommand("insertChild");
			tlCmpInsrtChld[sm].addActionListener(this);
			insertChildPopup.add(tlCmpInsrtChld[sm]);
		}		
		insertChild.setActionCommand("insertChildPopup");
		insertChild.addActionListener(this);		
		tb.add(insertChild);	
		tb.setEnabled(false);
		return tb;
	}
	
	
	private JMenu buildInsertChildMenu() {
		JMenu menu = new JMenu("Insert Child Object");
		menu.setIcon(cnec.getImageIcon("eInsertChild.gif"));
		JMenu submenu = new JMenu("Menu Object");
		for (int sm = 0; sm < mnCompInsrtChld.length; sm++) {
			mnCompInsrtChld[sm].setActionCommand("insertChild");
			mnCompInsrtChld[sm].addActionListener(this);
			submenu.add(mnCompInsrtChld[sm]);
		}
		menu.add(submenu);
		return menu;
	}

	private JMenu buildInsertAfterMenu() {
		JMenu menu = new JMenu("Insert After Object");
		menu.setIcon(cnec.getImageIcon("eInsertAfter.gif"));
		JMenu  submenu = new JMenu("Menu Object");
		for (int sm = 0; sm < mnCmpInsrtAftr.length; sm++) {
			mnCmpInsrtAftr[sm].setActionCommand("insertAfter");
			mnCmpInsrtAftr[sm].addActionListener(this);
			submenu.add(mnCmpInsrtAftr[sm]);
		}
		menu.add(submenu);
		return menu;
	}

	private void pasteObjectTreeNode(ObjectTreeNode referenceNode, String action) {
		
		if (paster != null) {
		
			if (action.equals("[Child/]")) {  	
				referenceNode.add(paster);
		 } else  {
				ObjectTreeNode parent = (ObjectTreeNode) referenceNode.getParent();
				parent.insert(paster, parent.getIndex(referenceNode) + 1);
			}	
		}
		objectTree.updateUI();
		pasteAfter.setEnabled(false);
		pasteChild.setEnabled(false);
		pasteAfterMenu.setEnabled(false);
		pasteChildMenu.setEnabled(false);
	}
	
	
	private void insertObjectTreeNode(ObjectTreeNode selectedNode,
			Object popUpMenu, String action) {
		String item = ((JMenuItem) popUpMenu).getText();
		String attributeString = "   " + scriptEditRules.propertyTypes[0] + "='" + item + "' "
				+ scriptEditRules.propertyTypes[1] + "='' " + scriptEditRules.propertyTypes[2] + "='' "
				+ scriptEditRules.propertyTypes[3] + "='FALSE' " ;

		ObjectTreeNode newNode = new ObjectTreeNode();
		newNode.setAttributeString(attributeString);
		newNode.setUserObject(attributeString);

		if (action.equals("[Child/]")) {
			selectedNode.add(newNode);
		} else {
			ObjectTreeNode parent = (ObjectTreeNode) selectedNode.getParent();
			parent.insert(newNode, parent.getIndex(selectedNode) + 1 );
		}

		objectTree.updateUI();
	}
	
	
  public void initializeComponentUI () {}
	  
	class TreeMouseListener extends MouseAdapter {
		JPopupMenu popup;

		TreeMouseListener(JPopupMenu popupMenu) {
			popup = popupMenu;
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				leftPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				fireNodeSelected((ObjectTreeNode) objectTree
						.getLastSelectedPathComponent());
				leftPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
		private static final long serialVersionUID = 5161992262981136764L;

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
		private static final long serialVersionUID = -2978310535646487064L;

		ImageIcon dcIcon;

		ImageIcon scIcon;

		public ObjectRenderer() {
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);

			String object = ((ObjectTreeNode) value).getAttributeString();
			if (object != null) {
				setText(DataSet.parseProperty("OBJECT_NAME", object)
						+ " : "
						+ DataSet.parseProperty(MenuItem.DESCRIPTION,
								object));
			}
			if (cnec != null){
				setIcon(cnec.menuDocumentIcon);
			} 
			return this;

		}
	}

	class ObjectTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 6154583926251126482L;
		private String objectAttributeString;

		public void setAttributeString(String attributeString) {
			objectAttributeString = attributeString;

		}

		public String getAttributeString() {
			return objectAttributeString;
		}
		
		
		public ObjectTreeNode [] getChildren() {
			if(isLeaf()) {
				return null;
			} else {
				ObjectTreeNode [] children = 
					new ObjectTreeNode[getChildCount()];
				for(int c=0; c < getChildCount();c++ )
					children[c] = (ObjectTreeNode) getChildAt(c);	
				return children;
			}
		}		

	}


	public void keyReleased(KeyEvent e) {
		String cmd = ((JComponent)e.getSource()).getName();
		
		if(cmd.equals("objectTree") && objectTree.getLastSelectedPathComponent() != null) 
			fireNodeSelected((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		
	
		else if(cmd.equals("comp") && objectTree.getLastSelectedPathComponent() != null 
																	&& e.getKeyCode() == 10) 
			commitEditing((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
	
	}


	public void focusGained(FocusEvent e) {
		JTextField c = (JTextField)e.getSource();
		c.selectAll();
		c.setBackground(cnec.crsColor);
	}


	public void focusLost(FocusEvent e) {
		JTextField c = (JTextField)e.getSource();
		c.setBackground(Color.white);
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd.equals("insertAfter"))
			insertObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent(),
					e.getSource(), "[Insert/]");
	
		else if(cmd.equals("insertChild"))
			insertObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent(), 
					e.getSource(), "[Child/]");
		
		else if(cmd.equals("cut"))
			cutObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		
		else if(cmd.equals("copy"))
			copyObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		
		else if(cmd.equals("pasteAfter"))
			pasteObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent(), "[Insert/]");
		
		else if(cmd.equals("pasteChild"))
			pasteObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent(), "[Child/]");

		else if(cmd.equals("up"))
			moveUpObjectTreeNode((ObjectTreeNode) objectTree .getLastSelectedPathComponent());
		
		else if(cmd.equals("down"))
			moveDownObjectTreeNode((ObjectTreeNode) objectTree .getLastSelectedPathComponent());

		else if(cmd.equals("insertAfterPopup"))	
			insertAfterPopup.show(insertAfter, 15 , insertAfter.getHeight() );		 
		
		else if(cmd.equals("insertChildPopup"))	
        	insertChildPopup.show(insertChild, 15 , insertChild.getHeight() );

	}
	
	public void setProperty(String propertyName, String propertyValue) {}
	public boolean locateCursor() {return false;}
	public void setValid(boolean invalid) {}
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
}