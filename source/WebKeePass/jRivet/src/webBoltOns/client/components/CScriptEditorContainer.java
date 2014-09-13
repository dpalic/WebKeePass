package webBoltOns.client.components;


/*
 * $Id: CScriptEditorContainer.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import webBoltOns.client.components.componentRules.ScriptRules;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowParm;
import webBoltOns.dataContol.DataSet;

public class CScriptEditorContainer extends JPanel implements StandardComponentLayout, 
			KeyListener, FocusListener, ActionListener {

	
	 protected class ObjectTreeNode extends DefaultMutableTreeNode {

			private static final long serialVersionUID = -4584477642639473333L;
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
	 
	 
	private static final long serialVersionUID = -8137762082645292525L;
	private int objectHL= 0;
	private AppletConnector  cnct;
	private JSplitPane hrzntlSpltPn;
	private ObjectRenderer objectRenderer;
	private JTree objectTree;
	private JPanel rightPanel, leftPanel;
	private JScrollPane treeScrollpane;
	private ObjectTreeNode root;
	private WindowItem comp;
	private ObjectTreeNode lastSelectedNode;
	private ScriptRules sRules;
	private JToolBar toolBar ;
	private JPopupMenu insertAfterPopup, insertChildPopup; 
	private JButton insertAfter, insertChild;
	private JMenuItem[] mnCmpInsrtChld, mnuInsrtChld,
						mnuInsrtAftr, mnuConInsrtAftr,
						tlComInsertChd, tlCntnrInsrtChld,
						tlComInsrtAftr, tlCntrInsrtAftr;

	private ObjectTreeNode paster;

	private JMenuItem pasteAfterMenu, pasteChildMenu;
	private JButton pasteAfter, pasteChild;


	public CScriptEditorContainer() {}

	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();		
		sRules = new ScriptRules();
		
		mnCmpInsrtChld = new JMenuItem [ScriptRules.screenComponents.length];
		mnuInsrtAftr = new JMenuItem [ScriptRules.screenComponents.length];
		tlComInsertChd = new JMenuItem [ScriptRules.screenComponents.length];
		tlComInsrtAftr = new JMenuItem [ScriptRules.screenComponents.length];
		
		for(int m=0; m < ScriptRules.screenComponents.length; m++) {
			mnCmpInsrtChld[m] = new JMenuItem(ScriptRules.screenComponents[m]);
	  	   	mnuInsrtAftr[m] = new JMenuItem(ScriptRules.screenComponents[m]);
	  	    tlComInsertChd[m] = new JMenuItem(ScriptRules.screenComponents[m]);
	  	    tlComInsertChd[m].setIcon(cnct.menuDocumentIcon);
	  	    tlComInsrtAftr[m] = new JMenuItem(ScriptRules.screenComponents[m]);
	  	    tlComInsrtAftr[m].setIcon(cnct.menuDocumentIcon);
		}
		
		mnuInsrtChld = new JMenuItem [ScriptRules.screenContainers.length];
		mnuConInsrtAftr = new JMenuItem [ScriptRules.screenContainers.length];
		tlCntnrInsrtChld = new JMenuItem [ScriptRules.screenContainers.length];
		tlCntrInsrtAftr = new JMenuItem [ScriptRules.screenContainers.length];
		for(int m=0; m < ScriptRules.screenContainers.length; m++) {
			mnuInsrtChld[m] = new JMenuItem(ScriptRules.screenContainers[m]);
	  	   	mnuConInsrtAftr[m] = new JMenuItem(ScriptRules.screenContainers[m]);
	  	    tlCntnrInsrtChld[m] = new JMenuItem(ScriptRules.screenContainers[m]);
	  	    tlCntnrInsrtChld[m].setIcon(cnct.menuScriptIcon);
	  	   	tlCntrInsrtAftr[m] = new JMenuItem(ScriptRules.screenContainers[m]);
	  	    tlCntrInsrtAftr[m].setIcon(cnct.menuScriptIcon);
		}
		setName(Integer.toString(comp.getObjectHL()));
		root = new ObjectTreeNode();
		objectTree = new JTree(root);
		toolBar = buildToolBar();
		objectTree.setRootVisible(false);
		objectTree.setShowsRootHandles(true);
		objectTree.putClientProperty("JTree.lineStyle", "Angled");
		objectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		rightPanel = buildPropertyPanel();
		rightPanel.setPreferredSize(new Dimension(410, comp.getHeight()));

		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(180 , comp.getHeight()));

		objectRenderer = new ObjectRenderer();
		objectTree.setCellRenderer(objectRenderer);
		TreePath treepath = objectTree.getSelectionPath();
		objectTree.expandPath(treepath);
		objectRenderer.setBackgroundNonSelectionColor(cnct.bgColor);
		objectTree.setBackground(cnct.bgColor);
		objectTree.setSelectionPath(treepath);
		objectTree.setRowHeight(20);
		treeScrollpane = new JScrollPane(objectTree);
		treeScrollpane.setBorder(null);
		leftPanel.add(BorderLayout.CENTER, treeScrollpane);

		hrzntlSpltPn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		hrzntlSpltPn.setLeftComponent(leftPanel);
		hrzntlSpltPn.setRightComponent(rightPanel);
		hrzntlSpltPn.setDividerSize(3);
		hrzntlSpltPn.setResizeWeight(0);
		
        JPanel mainPanel = new JPanel();
        mainPanel.add(hrzntlSpltPn);
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));
		toolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));
		
		setLayout(new BorderLayout());
		
		add(mainPanel, BorderLayout.CENTER);
		add(toolBar, BorderLayout.NORTH);
		
		objectTree.addMouseListener(buildTreeMouseListener());
		objectTree.setName("objectTree");
		objectTree.addKeyListener(this);
		hrzntlSpltPn.setBorder(null);
		
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			 ((CPanelContainer) parentItem.getComponentObject()).addRight(this, 4);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			 ((CPanelContainer) parentItem.getComponentObject()).addRight(this,2);
		} else {
			 ((CPanelContainer) parentItem.getComponentObject()).addLeft(this, 0);
		}
	}

	public String getString() {
		return null;
	}
	

	protected JPanel buildPropertyPanel() {
		JPanel propertyPanel = new JPanel(new GridFlowLayout(5,3));
	     
		((JTextField) sRules.propertyComponents[0]).setEditable(false);
		((JTextField) sRules.propertyComponents[0]).setFocusable(false);
		((JTextField) sRules.propertyComponents[0]).setBackground(new Color(225, 225,225)); 
		
		((JComboBox) sRules.propertyComponents[1]).addItem("");
		for(int b=0;b < ScriptRules.barButtonTypes.length; b++)
			((JComboBox) sRules.propertyComponents[1]).addItem(
					ScriptRules.barButtonTypes[b]);
		
		((JComboBox) sRules.propertyComponents[4]).addItem("");
		((JComboBox) sRules.propertyComponents[4]).addItem(WindowItem.LEFT);
		((JComboBox) sRules.propertyComponents[4]).addItem(WindowItem.LEFT_FILL);
		((JComboBox) sRules.propertyComponents[4]).addItem(WindowItem.CENTER);
		((JComboBox) sRules.propertyComponents[4]).addItem(WindowItem.CENTER_FILL);
		((JComboBox) sRules.propertyComponents[4]).addItem(WindowItem.RIGHT);
		((JComboBox) sRules.propertyComponents[4]).addItem(WindowItem.RIGHT_FILL);
		
		((JComboBox) sRules.propertyComponents[5]).addItem("INT");
		((JComboBox) sRules.propertyComponents[5]).addItem("FLT");
		((JComboBox) sRules.propertyComponents[5]).addItem("CHR");
		((JComboBox) sRules.propertyComponents[5]).addItem("DAT");
		((JComboBox) sRules.propertyComponents[5]).addItem("TIM");
		((JComboBox) sRules.propertyComponents[5]).addItem("BOL");
		((JComboBox) sRules.propertyComponents[5]).addItem("BLB");
		((JComboBox) sRules.propertyComponents[5]).addItem("IMG");
		((JComboBox) sRules.propertyComponents[5]).addItem("HTM");
		
		((JComboBox) sRules.propertyComponents[8]).addItem("");
		((JComboBox) sRules.propertyComponents[8]).addItem("Left");
		((JComboBox) sRules.propertyComponents[8]).addItem("Center");
		((JComboBox) sRules.propertyComponents[8]).addItem("Right");

		((JComboBox) sRules.propertyComponents[9]).addItem("Y");
		((JComboBox) sRules.propertyComponents[9]).addItem("N");

		((JComboBox) sRules.propertyComponents[23]).addItem(WindowItem.TOP);
		((JComboBox) sRules.propertyComponents[23]).addItem(WindowItem.BOTTOM);
		
		((JComboBox) sRules.propertyComponents[25]).addItem("Y");
		((JComboBox) sRules.propertyComponents[25]).addItem("N");

		((JComboBox) sRules.propertyComponents[26]).addItem("Y");
		((JComboBox) sRules.propertyComponents[26]).addItem("N");
		
		((JComboBox) sRules.propertyComponents[29]).addItem("Y");
		((JComboBox) sRules.propertyComponents[29]).addItem("N");

		for (int p = 0; p < sRules.propertyLabel.length; p++) {
			final Component c = sRules.propertyComponents[p];
			propertyPanel.add(sRules.propertyLabel[p], new GridFlowParm(true, 0));
			c.setPreferredSize(new Dimension(215, 18));
		    c.setName("comp");
			c.addKeyListener(this);
			if(c instanceof JSpinner) {
				JComponent editor = ((JSpinner)c).getEditor();	 
				((JSpinner.DefaultEditor) editor).getTextField().addFocusListener(this); 
			}

			if(c instanceof JTextField) 		
				c.addFocusListener(this);
			
			propertyPanel.add(c, new GridFlowParm(false, 1));
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
		nodeB.setAttributeString( " [OBJECT_NAME='ButtonBar' /]");
		nodeB.setUserObject( " [OBJECT_NAME='ButtonBar' /]");
		root.add(nodeB);
		
		ObjectTreeNode nodeP = new ObjectTreeNode();
		nodeP.setAttributeString(" [OBJECT_NAME='Panel' /]");
		nodeP.setUserObject(" [OBJECT_NAME='Panel' /]");
		root.add(nodeP);
		
		
		objectTree.updateUI();
		for (int p = 0; p < ScriptRules.propertyTypes.length; p++) {
			sRules.propertyLabel[p].setVisible(false);
			sRules.propertyComponents[p].setVisible(false);
			if (sRules.propertyComponents[p] instanceof JTextField) {
					((JTextField) sRules.propertyComponents[p]).setText("");

				} else if (sRules.propertyComponents[p] instanceof JComboBox) {
					((JComboBox) sRules.propertyComponents[p]).setSelectedItem("");

				} else if (sRules.propertyComponents[p] instanceof JSpinner) {
					((JSpinner) sRules.propertyComponents[p]).setValue(new Integer(0));
				}	
			}
	}
	
	
	public boolean validateComponent(String action, String editorName) {return true;}

	/***************************************************************************
	 * 
	 * Populate the screen
	 * 
	 *  
	 */
	public void populateComponent(String action, String editorName, DataSet dataSet) {
		DataSet script = (DataSet) dataSet.get(editorName); 
		if (script != null)
		     loadTree(script);
	}

	protected void loadTree(DataSet script) {
		final Vector vf = script.getTableVector(WindowItem.SCREEN_OBJECTS);
		final Enumeration eObjects = vf.elements();
		clearComponent(null);
		root.removeAllChildren();
		Hashtable<String, ObjectTreeNode> screenObjects = new Hashtable<String, ObjectTreeNode>();
		screenObjects.put("0", root);
		
		while (eObjects.hasMoreElements()) {
			StringBuffer attributeList = new StringBuffer();
			String attributeString = (String) eObjects.nextElement();
			String selectedItemType = DataSet.parseProperty("OBJECT_NAME", attributeString);
			String parent_hl = DataSet.parseProperty(
					WindowItem.PARENT_HL, attributeString);
			String object_hl = DataSet.parseProperty(
					WindowItem.OBJECT_HL, attributeString);
			String [] rules = (String []) sRules.propertyRules.get(selectedItemType);
			
			for (int p = 0; p < ScriptRules.propertyTypes.length; p++) {
				if(rules[p].equals("T")) {
					attributeList.append( " " + ScriptRules.propertyTypes[p] + "='" + DataSet
					.parseProperty(ScriptRules.propertyTypes[p], attributeString) +"' "); 
				}
			}			
			ObjectTreeNode node = new ObjectTreeNode();	
			node.setAttributeString(attributeList.toString());
			node.setUserObject(attributeList.toString());
			screenObjects.put(object_hl, node);
				((ObjectTreeNode) screenObjects.get(parent_hl)).add(node);				
		}
       
		for(int b=0; b < ScriptRules.barButtonTypes.length; b++)			
		buildBarButton(ScriptRules.barButtonTypes[b] , script, 
									   ((ObjectTreeNode) screenObjects.get("2")));	
		
		objectTree.updateUI();
		hrzntlSpltPn.setDividerLocation(220);
		objectTree.grabFocus();
	}

	
	private void buildBarButton(String buttonType, DataSet script,
			ObjectTreeNode parent){
		String desc = "[" + buttonType +"_Desc/]";
		String method = "[" + buttonType + "_Method/]";
		String icon = "[" + buttonType + "_Icon/]";
		String link = "[" + buttonType + "_Link/]";
		String mClass ="[" + buttonType +  "_MethodClass/]";
 		
		if (script.getStringField(desc) != null &&
				!script.getStringField(desc).equals("")) {
			ObjectTreeNode buttonNode = new ObjectTreeNode();
			String 	objectAttributeString =	
				"  OBJECT_NAME='BarButton' " +
				"  TYPE='" + buttonType + 
				"' DESCRIPTION='" + script.getStringField(desc) +
				"' METHOD='" + script.getStringField(method) +
				"' LINK='" + script.getStringField(link) +
				"' METHOD_CLASS='"+ script.getStringField(mClass) +
				"' ICON='" + script.getStringField(icon) + "'";
			buttonNode.setAttributeString(objectAttributeString);
			buttonNode.setUserObject(objectAttributeString);
            parent.add(buttonNode);				
		}
	}
	
	public String getSelectedComponentItem() {
		if(lastSelectedNode != null)
			return DataSet.parseProperty(WindowItem.FIELDNAME,  lastSelectedNode.getAttributeString());
		else 
			return null;
	}

	public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {
		dataSet.putStringField("TypeID", "[SCREEN_SCRIPT/]");
		commitEditing((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		Vector path = new Vector ();
		objectHL = 1;
		path = getObjectAttributePath(path, root, objectHL);
		if(!path.isEmpty())
			dataSet.putTableVector("[Path/]", path);		
		return dataSet;
	}
	
	
	public Vector<Object> getObjectAttributePath (Vector<Object> path, ObjectTreeNode node,
			int pHL) {
    	String objectAttributeString = node.getAttributeString();
    	if (objectAttributeString != null ) {
    		objectHL++;
    		path.add("[" + "HLP"  + "='" + pHL + "'  HLO='"  + objectHL  + "' -Saving_As-> /]"
						+ objectAttributeString ) ;
    	}	
    	ObjectTreeNode children [] = node.getChildren();
    	int p = objectHL;
    	if(children != null)
    		for(int c=0; c< children.length; c++) {
    			path = getObjectAttributePath(path,  children[c], p);
    	    }	
    	return path;
    }
	
	
	public Vector<Object> getObjectAttributePathPreview (Vector<Object> path, ObjectTreeNode node,
			int parentHL) {
    	String objectAttributeString = node.getAttributeString();
    	if (objectAttributeString != null ) {
    		objectHL++;
    		path.add("[ " + WindowItem.PARENT_HL + "='" + parentHL + "' " 
    				      + WindowItem.OBJECT_HL + "='" + objectHL  +  "' "
    				      + MenuItem.ACCESS_LEVEL + "='3' "
						  + objectAttributeString + "  /]") ;
    	}	
    	ObjectTreeNode children [] = node.getChildren();
    	int p = objectHL;
    	if(children != null)
    		for(int c=0; c< children.length; c++) {
    			path = getObjectAttributePathPreview(path, 
    					children[c], p);
    	    }	
    	return path;
    }
	
	
	protected void commitEditing(ObjectTreeNode selectedTreeNode) {
		String attributes[] = new String[ScriptRules.propertyTypes.length];
		StringBuffer attributelist = new StringBuffer();
		attributelist.append( " " + ScriptRules.propertyTypes[0] + "='" + 
		                ((JTextField) sRules.propertyComponents[0]).getText() + "'" );
																							
		for (int p = 1; p < ScriptRules.propertyTypes.length; p++) {
			if(sRules.propertyComponents[p].isVisible() ){
				if (sRules.propertyComponents[p] instanceof JTextField) {
					attributes[p] = ((JTextField) sRules.propertyComponents[p]).getText();
			
				} else if (sRules.propertyComponents[p] instanceof JComboBox) {
					attributes[p] = ((JComboBox) sRules.propertyComponents[p]).getSelectedItem().toString();

				} else if (sRules.propertyComponents[p] instanceof JSpinner) {
					try {
					((JSpinner.NumberEditor) ((JSpinner) sRules.propertyComponents[p]).getEditor()).commitEdit();
					} catch (java.text.ParseException pe) {}
					attributes[p] = ((JSpinner) sRules.propertyComponents[p]).getValue().toString();
				}
				attributelist.append(" " + ScriptRules.propertyTypes[p] + "='" + attributes[p] + "'");
			}		
		}
		
		if (lastSelectedNode != null)
			lastSelectedNode.setAttributeString(attributelist.toString());
		lastSelectedNode = selectedTreeNode;
		objectTree.updateUI();
	}


	protected void fireNodeSelected(ObjectTreeNode selectedTreeNode) {

		if (selectedTreeNode != null) {		
			String attributeString = selectedTreeNode.getAttributeString();
			String selectedItemType = DataSet.parseProperty("OBJECT_NAME",
					attributeString);
			
			if(!selectedItemType.equals("")) {
				commitEditing(selectedTreeNode);
				String [] rules = (String [])  sRules.propertyRules.get(selectedItemType);
			
				for (int p = 0; p < ScriptRules.propertyTypes.length; p++) {
					if(rules[p].equals("F")) {
						sRules.propertyLabel[p].setVisible(false);
						sRules.propertyComponents[p].setVisible(false);
					} else {	
						sRules.propertyLabel[p].setVisible(true);
						sRules.propertyComponents[p].setVisible(true);
						if (sRules.propertyComponents[p] instanceof JTextField) {
							((JTextField) sRules.propertyComponents[p]).setText(DataSet
									.parseProperty(ScriptRules.propertyTypes[p], attributeString));

						} else if (sRules.propertyComponents[p] instanceof JComboBox) {
							((JComboBox) sRules.propertyComponents[p])
							.setSelectedItem(DataSet.parseProperty(
									ScriptRules.propertyTypes[p], attributeString));

						} else if (sRules.propertyComponents[p] instanceof JSpinner) {
							((JSpinner) sRules.propertyComponents[p]).setValue(new Integer(
									DataSet.checkInteger(DataSet.parseProperty(
											ScriptRules.propertyTypes[p], attributeString))));
						}	
					}
				}
			}
		}
		rightPanel.updateUI();
	}

	
	private void cutObjectTreeNode(ObjectTreeNode nodeToRemove) {
	  if(nodeToRemove != null ){
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
		if(nodeToCopy != null){
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
		
		final JPopupMenu popup = new JPopupMenu();
			  
		JMenuItem mnu;
				
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

		pasteChildMenu = new JMenuItem("Paste Child");
		pasteChildMenu.setIcon(cnct.getImageIcon("ePasteChild.gif"));
		pasteChildMenu.setActionCommand("pasteChild");
		pasteChildMenu.addActionListener(this);
		popup.add(pasteChildMenu);
		pasteChildMenu.setEnabled(false);
		

		pasteAfterMenu = new JMenuItem("Paste After");
		pasteAfterMenu.setIcon(cnct.getImageIcon("ePaste.gif"));	
		pasteAfterMenu.setActionCommand("pasteAfter");
		pasteAfterMenu.addActionListener(this);
		popup.add(pasteAfterMenu);
		pasteAfterMenu.setEnabled(false);


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

		popup.add(buildInsertChildMenu());
		popup.add(buildInsertAfterMenu());
 
		return  new TreeMouseListener(popup);
	}

	private JToolBar buildToolBar() {
		
		final JToolBar tb = new JToolBar();
		JButton mnu;

		mnu =  cnct.buildFancyButton("Cut ", "ecut.gif", null, ' ');
		mnu.setActionCommand("cut");
		mnu.addActionListener(this);
		tb.add(mnu, null);
		
		mnu =  cnct.buildFancyButton("Copy ", "ecopy.gif", null, ' ');
		mnu.setActionCommand("copy");
		mnu.addActionListener(this);
		tb.add(mnu, null);

		pasteAfter = cnct.buildFancyButton("Paste After ", "epaste.gif", null, ' ');
		pasteAfter.setActionCommand("pasteAfter");
		pasteAfter.addActionListener(this);
		tb.add(pasteAfter);
		pasteAfter.setEnabled(false);
		
		pasteChild =  cnct.buildFancyButton("Paste Child ", "ePasteChild.gif", null, ' ');
		pasteChild.setActionCommand("pasteChild");
		pasteChild.addActionListener(this);
		tb.add(pasteChild);
		pasteChild.setEnabled(false);
		
		mnu =  cnct.buildFancyButton("Up ", "eup.gif", null, ' ');
		mnu.setActionCommand("up");
		mnu.addActionListener(this);
		tb.add(mnu);

		mnu = cnct.buildFancyButton("Down ", "edown.gif", null, ' ');;
		mnu.setActionCommand("down");
		mnu.addActionListener(this);
		tb.add(mnu);

		insertAfter = cnct.buildFancyButton("Insert After ", "eInsertAfter.gif", null, ' ');
		final JMenu insertAfterPopup1 = new JMenu("Components");
		insertAfterPopup1.setIcon(cnct.getImageIcon("eComponent.gif"));
		for (int sm = 0; sm < tlComInsrtAftr.length; sm++) {
			tlComInsrtAftr[sm].setActionCommand("insertAfter");
			tlComInsrtAftr[sm].addActionListener(this);
			insertAfterPopup1.add(tlComInsrtAftr[sm]);
		}		
		JMenu insertAfterPopup2 = new JMenu("Containers");
		insertAfterPopup2.setIcon(cnct.getImageIcon("eContainer.gif"));
		for (int sm = 0; sm < tlCntrInsrtAftr.length; sm++) {
			tlCntrInsrtAftr[sm].setActionCommand("insertAfter");
			tlCntrInsrtAftr[sm].addActionListener(this);
			insertAfterPopup2.add(tlCntrInsrtAftr[sm]);
		}		
		
		insertAfterPopup = new JPopupMenu();
		insertAfterPopup.add(insertAfterPopup1);
		insertAfterPopup.add(insertAfterPopup2);
		insertAfter.setActionCommand("insertAfterPopup");
		insertAfter.addActionListener(this);		
		tb.add(insertAfter);
		
		insertChild = cnct.buildFancyButton("Insert Child ", "eInsertChild.gif", null, ' ');;
		final JMenu insertChildPopup1 = new JMenu("Components");
		insertChildPopup1.setIcon(cnct.getImageIcon("eComponent.gif"));
		for (int sm = 0; sm < tlComInsertChd.length; sm++) {
			tlComInsertChd[sm].setActionCommand("insertChild");
			tlComInsertChd[sm].addActionListener(this);
			insertChildPopup1.add(tlComInsertChd[sm]);
		}		
		
		final JMenu insertChildPopup2 = new JMenu("Containers");
		insertChildPopup2.setIcon(cnct.getImageIcon("eContainer.gif"));
		for (int sm = 0; sm < tlCntnrInsrtChld.length; sm++) {
			tlCntnrInsrtChld[sm].setActionCommand("insertChild");
			tlCntnrInsrtChld[sm].addActionListener(this);
			insertChildPopup2.add(tlCntnrInsrtChld[sm]);
		}	

		insertChildPopup = new JPopupMenu();
		insertChildPopup.add(insertChildPopup1);
		insertChildPopup.add(insertChildPopup2);
		insertChild.setActionCommand("insertChildPopup");
		insertChild.addActionListener(this);			
		tb.add(insertChild);
		return tb;
	}

	
	private JMenu buildInsertChildMenu() {
		JMenu menu = new JMenu("Insert Child Object");
		menu.setIcon(cnct.getImageIcon("eInsertChild.gif"));
		JMenu submenu = new JMenu("Contaner Object");
		submenu.setIcon(cnct.getImageIcon("eContainer.gif"));
		for (int sm = 0; sm < mnuInsrtChld.length; sm++) {
			mnuInsrtChld[sm].setActionCommand("insertChild");
			mnuInsrtChld[sm].addActionListener(this);
			submenu.add(mnuInsrtChld[sm]);
		}
		menu.add(submenu);

		submenu = new JMenu("Data Element Object");
		submenu.setIcon(cnct.getImageIcon("eComponent.gif"));
		for (int sm = 0; sm < mnCmpInsrtChld.length; sm++) {
			mnCmpInsrtChld[sm].setActionCommand("insertChild");
			mnCmpInsrtChld[sm].addActionListener(this);
			submenu.add(mnCmpInsrtChld[sm]);
		}

		menu.add(submenu);
		return menu;
	}

	private JMenu buildInsertAfterMenu() {
		JMenu menu = new JMenu("Insert After Object");
		menu.setIcon(cnct.getImageIcon("eInsertAfter.gif"));
		JMenu submenu = new JMenu("Contaner Object");
		submenu.setIcon(cnct.getImageIcon("eContainer.gif"));
		for (int sm = 0; sm < mnuConInsrtAftr.length; sm++) {
			mnuConInsrtAftr[sm].setActionCommand("insertAfter");
			mnuConInsrtAftr[sm].addActionListener(this);
			submenu.add(mnuConInsrtAftr[sm]);
		}
		menu.add(submenu);

		submenu = new JMenu("Data Element Object");
		submenu.setIcon(cnct.getImageIcon("eComponent.gif"));
		for (int sm = 0; sm < mnuInsrtAftr.length; sm++) {
			mnuInsrtAftr[sm].setActionCommand("insertAfter");
			mnuInsrtAftr[sm].addActionListener(this);
			submenu.add(mnuInsrtAftr[sm]);
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
		StringBuffer attributelist = new StringBuffer();
		String item = ((JMenuItem) popUpMenu).getText();
		attributelist.append(" " + ScriptRules.propertyTypes[0] + "='" + item + "'");
		
		for(int i=1;i<ScriptRules.propertyTypes.length; i++)
			attributelist.append(" " + ScriptRules.propertyTypes[i] + "='" + 
					ScriptRules.propertyInitValues[i] + "'");
		
		ObjectTreeNode newNode = new ObjectTreeNode();
		newNode.setAttributeString(attributelist.toString());
		newNode.setUserObject(attributelist.toString());

		if (action.equals("[Child/]")) {
			selectedNode.add(newNode);
		} else {
			ObjectTreeNode parent = (ObjectTreeNode) selectedNode.getParent();
			if(parent != null)
				parent.insert(newNode, parent.getIndex(selectedNode) + 1 );
		}

		objectTree.updateUI();
	}
	
     public void initializeComponentUI () {}
	  
	 protected class TreeMouseListener extends MouseAdapter {
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

	 protected class OptRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = -7759340743311085456L;

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

	 protected class ObjectRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = -3821854337514820557L;

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
			
				if(DataSet.parseProperty("OBJECT_NAME", object).equals(
					                       WindowItem.BAR_BUTTON_OBJECT)
				   || DataSet.parseProperty("OBJECT_NAME", object).equals(
		                       WindowItem.TABITEM_OBJECT)
				   || DataSet.parseProperty("OBJECT_NAME", object).equals(
				   		       WindowItem.PANEL_OBJECT)
				   || DataSet.parseProperty("OBJECT_NAME", object).equals(
				   			   WindowItem.BUTTON_OBJECT)) {
					
					setText(DataSet.parseProperty("OBJECT_NAME", object)
					+ " : "
					+ DataSet.parseProperty(WindowItem.DESCRIPTION,
							object));
					setIcon(cnct.menuDocumentIcon);
				} else { 		
					setText(DataSet.parseProperty("OBJECT_NAME", object)
					+ " : "
					+ DataSet.parseProperty(WindowItem.FIELDNAME,
							object));
					setIcon(cnct.menuScriptIcon);	
				}	

			}

			return this;

		}
	}



	 public void setProperty(String propertyName, String propertyValue) {}
	 public void setValid(boolean invalid) {} 	
	 public boolean locateCursor() {
			return false;
	 }





	public void keyReleased(KeyEvent e) {
		String cmd = ( (JComponent)e.getSource()).getName();
		if(cmd.equals("objectTree") && objectTree.getLastSelectedPathComponent() != null) 
				fireNodeSelected((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
	
		else if(cmd.equals("comp") &&  objectTree.getLastSelectedPathComponent() != null 
									&& e.getKeyCode() == 10) 
			commitEditing((ObjectTreeNode) objectTree. getLastSelectedPathComponent());
	}



	public void focusGained(FocusEvent e) {
		JTextField c = ((JTextField) e.getSource());
		c.selectAll();
		c.setBackground(cnct.crsColor);
	}


	public void focusLost(FocusEvent e) {
		JTextField c = ((JTextField) e.getSource());
		c.setBackground(Color.white);		
	}


	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd.equals("insertChild"))
			insertObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent(), 
										e.getSource(), "[Child/]");
		
		else if(cmd.equals("insertAfter")) 
			insertObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent(),
						e.getSource(), "[Insert/]");
			
		else if(cmd.equals("cut")) 
			cutObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		
		else if(cmd.equals("copy")) 
			copyObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		
		else if(cmd.equals("pasteAfter")) 
			pasteObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent(), "[Insert/]");
	
		else if(cmd.equals("pasteChild")) 
			pasteObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent(), "[Child/]");
	
		else if(cmd.equals("up")) 
			moveUpObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		
		else if(cmd.equals("down")) 
			moveDownObjectTreeNode((ObjectTreeNode) objectTree.getLastSelectedPathComponent());
		
		else if(cmd.equals("insertAfterPopup")) 
			insertAfterPopup.show(insertAfter, 15 , insertAfter.getHeight() );		
		
		else if(cmd.equals("insertChildPopup"))
			insertChildPopup.show(insertChild, 15 , insertChild.getHeight() );	
		
	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	  	
	 
 }

