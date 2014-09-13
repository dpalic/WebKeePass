package webBoltOns.client.components;

/*
 * $Id: CTreeTableContainer.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultTreeCellRenderer;

import sun.com.table.AbstractTreeTableModel;
import sun.com.table.TreeTable;
import sun.com.table.TreeTableModel;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CTreeTableContainer extends CTableContainer implements
		StandardComponentLayout, ActionListener, KeyListener, MouseListener {

	private static final long serialVersionUID = -8948143573170957964L;

	private CTreeTableModel dataModel;

	private CTableColumn[] tableColumns;

	private TreeTable tableView;

	public CTreeTableContainer() {}


	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("print")) {
			print.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			printTable();
			print.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		} else if (cmd.equals("copy")) {
			copy.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			copyTable();
			copy.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseReleased(final MouseEvent e) {
		if (mFrm != null && e.getClickCount() > 1
				&& e.getButton() == MouseEvent.BUTTON1) {
			mFrm.fireWindowReturning("", null);
			
		} else if(e.getButton() == MouseEvent.BUTTON3 && popUp != null) {
			 SwingUtilities.invokeLater(new Runnable() {
                 public void run() {
                	 int r = tableView.rowAtPoint(e.getPoint());
                	 if(r >= 0) tableView.setRowSelectionInterval(r, r);
                 	}
			 });
			popUp.show(e.getComponent(), e.getX(), e.getY());
		}
	
	}
	
	
	public void addColumn(CTableColumn c) {
		if(columns.isEmpty())
			c.setTreeColumn();
		
		columns.add(c);
	}
	
	
	public void clearComponent(String defaultValue) {
		dataModel = new CTreeTableModel(mFrm, comp,tableColumns, null);
		dataModel.setTree(this);
		if (dataModel != null) {
			tableView.setTreeTableModel(dataModel);
		}
		redrawTable();
	}
	
	public void commitEditing() {
		if (tableView.isEditing()) {
			tableView.getCellEditor().stopCellEditing();
		}
	}

	private void copyTable() {
		mFrm.copyToClipBoard(getTableDump());
	}

 
	public void expandTable(Vector itemToExpand) {
		dataModel = new CTreeTableModel(mFrm, comp, tableColumns, itemToExpand);
		dataModel.setTree(this);
		tableView.setTreeTableModel(dataModel);
		redrawTable();
	}


	public int getRowCount() {
		return tableView.getRowCount();
	}

	public void keyReleased(KeyEvent e) {
		int c = tableView.getSelectedColumn();
		int r = tableView.getSelectedRow();
		if (c != -1) {
			String cmp = tableColumns[c].getAppletComponent()
					.getObjectName();
			if (cmp.equals(WindowItem.TABLE_TEXTFIELD_OBJECT)
					&& e.getKeyCode() == 9) {
				tableView.editCellAt(r, c);
				((JTextField) tableColumns[c].getAppletComponent()
						.getComponentObject()).requestFocus();
			}

			if (mFrm != null && e.getKeyCode() == 83) {
				mFrm.fireWindowReturning("", null);
			}
		}
	}
	
	public Vector getSelectedRecords() {
		commitEditing();
		int rows[] = getSelectedRows();
		Vector selected = new Vector();
		if (rows.length > 0) {
			for (int x = 0; x < rows.length; x++) {
				String[] rec = new String[fieldDescription.length];
				for (int y = 0; y < fieldDescription.length; y++) {
					Object aValue = tableView.getValueAt(rows[x], y);
					if (tableColumns[y].getAppletComponent().getDataType().equals("BOL")) {
						
						if (((Boolean) aValue).booleanValue()) 
							rec[y] = "true";
						 else 
							rec[y] = "false";
						
					} else {
						
						if(cnct.strictEncoding && tableColumns[y].getAppletComponent().isEncrypted())
							rec[y] = cnct.encrypt((String) tableView.getValueAt(rows[x], y));
						else
							rec[y] = (String) tableView.getValueAt(rows[x], y);
						
						
					}
				}

				selected.addElement(rec);
			}

			return selected;
		} else {
			return null;
		}
	}

	public int[] getSelectedRows() {
		return tableView.getSelectedRows();
	}

	private String getTableDump() {
		StringBuffer dump = new StringBuffer();
		for (int c = 0; c < fieldDescription.length; c++) {
			dump.append(fieldDescription[c] + "\t");
		}
		dump.append("\n");
		for (int r = 0; r < getRowCount(); r++) {
			for (int c = 0; c < fieldDescription.length; c++) {
				Object value = tableView.getValueAt(r, c);
				if (value != null)
					dump.append(value.toString());
				dump.append("\t");
			}
			dump.append("\n");
		}
		return dump.toString();
	}

	public Vector getTreeDataRows() {
		Vector tableData = new Vector();
		tableData = dataModel.getTreeDataRows(null, tableData);
		return tableData;
	}

 
	public void initializeComponentUI() {
		Enumeration ecolumns = columns.elements();
		totcol = columns.size();
		tableColumns = new CTableColumn[totcol];
		fieldDescription = new String[totcol];
		fieldName = new String[totcol];
		fieldDataType = new String[totcol];
		fieldParameter = new String[totcol];
		curcol = 0;
		
		print = new JButton(cnct.tablePrintIcon);
		print.setToolTipText("Print Table");
		print.setActionCommand("print");
		print.setCursor(new Cursor(Cursor.HAND_CURSOR));
		print.addActionListener(this);

		copy = new JButton(cnct.tableCopyIcon);
		copy.setToolTipText("Copy");
		copy.setActionCommand("copy");
		copy.setCursor(new Cursor(Cursor.HAND_CURSOR));
		copy.addActionListener(this);

		while (ecolumns.hasMoreElements()) {
			tableColumns[curcol] = (CTableColumn) ecolumns.nextElement();
			fieldDescription[curcol] = tableColumns[curcol]
					.getAppletComponent().getDescription();
			fieldDataType[curcol] = tableColumns[curcol].getAppletComponent()
					.getDataType();
			fieldName[curcol] = tableColumns[curcol].getAppletComponent()
					.getFieldName();
			fieldParameter[curcol] = tableColumns[curcol].getAppletComponent()
					.getFieldParameterName();
			tbWidth += tableColumns[curcol].getAppletComponent().getLength() + 5;
			curcol++;
		}
		tbWidth += 10;

		dataModel = new CTreeTableModel(mFrm, comp,
				tableColumns, null);
		dataModel.setTree(this);

		tableView = new TreeTable();

		tableView.setTreeTableModel(dataModel);
		tableView.getTree().setBackground(Color.WHITE);
		tableView.setFont(cnct.standardFont);
		tableView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		redrawTable();
		scrollpane = new JScrollPane(tableView);
		scrollpane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createCompoundBorder(BorderFactory.createTitledBorder(""),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)),
				scrollpane.getBorder()));

		scrollpane.setPreferredSize(new Dimension(
				tableView.getPreferredSize().width + 40, tbHeight + 15));

		tableView.addMouseListener(this);
		tableView.addKeyListener(this);

		setLayout(new BorderLayout());
		tableView.setGridColor(new Color(255, 255, 255));
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		if (cnct.tableCopyAccess)
			scrollpane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, print);
		if (cnct.tablePrintAccess)
			scrollpane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, copy);
		add(scrollpane, BorderLayout.WEST);
	}

	public void populateComponent(String action, String editorName,
			DataSet dataSet) {
		if(dataSet.getTableVector(editorName) != null )
			expandTable(dataSet.getTableVector(editorName));
		else
			clearComponent(null);
	}

	public DataSet populateDataSet(String action, String editorName,
			DataSet dataSet) {
		return dataSet;
	}

	private void printTable() {
		try {
			tableView.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat(
					tableTitle), new MessageFormat("Page - {0}"));
		} catch (Exception er) {
		}
	}

	/***************************************************************************
	 * 
	 * Redraw the table data
	 *  
	 */
	private void redrawTable() {
		commitEditing();
		CTreeTableRenderer renderer = new CTreeTableRenderer();
		renderer.setLeafIcon(new ImageIcon(cnct.treeTableLeafIcon
				.getImage()));
		renderer.setClosedIcon(new ImageIcon(
				cnct.treeTableClosedIcon.getImage()));
		renderer.setOpenIcon(new ImageIcon(cnct.treeTableOpenIcon
				.getImage()));
		tableView.getTree().setCellRenderer(renderer);
		tableView.setRowMargin(2);

		for (int x = 0; x < totcol; x++) {
			TableColumn c = tableView.getColumn(tableColumns[x]
					.getAppletComponent().getDescription());
			tableColumns[x].getHeaderRenderer().setFont(
					cnct.headerFont);
			c.setHeaderRenderer(tableColumns[x].getHeaderRenderer());
			if (!tableColumns[x].getAppletComponent().getDataType().equals("BOL")) {
				c.setCellEditor(tableColumns[x].getCellEditor());
				c.setCellRenderer(tableColumns[x].getCellRenderer());
			}

			if (tableColumns[x].getAppletComponent().getLength() != 0) {
				c.setPreferredWidth(tableColumns[x].getAppletComponent()
						.getLength());
			}
		}
	}

	public void requestTableFocus() {
		tableView.requestFocus();
	}

	public void setToolTipText(String tipField, String tip) {
		for (int c = 0; c < fieldName.length; c++) {
			try {
				TableColumn col = tableView.getColumn(tableColumns[c]
						.getAppletComponent().getDescription());
				DefaultTableCellRenderer r = (DefaultTableCellRenderer) col
						.getHeaderRenderer();
				if (fieldName[c].equals(tipField)) {
					r.setToolTipText(tip);
					col.setHeaderRenderer(r);
				}
			} catch (Exception e) {
			}
		}
	}

	
}
 
class CTreeTableModel extends AbstractTreeTableModel implements TreeTableModel {

 
	class TableNode {

		private WindowItem appletComponent;

		private Object[] children;

		private int columns;

		private CTreeTableModel model;

		private TableNode parentNode;

		private CTableColumn[] tableColumns;

		private TableRow tableRow;

		private WindowFrame windowAccess;

		public TableNode(WindowFrame windowAccess,
				WindowItem windowComponent,
				CTableColumn[] tableColumns, CTreeTableModel model,
				TableNode parentNode, TableRow tableRow) {
			this.tableColumns = tableColumns;
			this.columns = tableColumns.length;
			this.tableRow = tableRow;
			this.parentNode = parentNode;
			this.appletComponent = windowComponent;
			this.windowAccess = windowAccess;
			this.model = model;
		}

		public TableNode(WindowFrame windowAccess,
				WindowItem windowComponent,
				CTableColumn[] tableColumns, CTreeTableModel model,
				Vector topItem) {
			this.parentNode = null;
			this.windowAccess = windowAccess;
			this.appletComponent = windowComponent;
			this.tableColumns = tableColumns;
			this.model = model;

			if (topItem != null) {
				Object[] rowObjects = topItem.toArray();
				children = new TableNode[rowObjects.length];
				for (int i = 0; i < rowObjects.length; i++) {
					TableRow childRow = new TableRow(
							formatRowlData((Object[]) rowObjects[i]), this,
							columns);
					children[i] = new TableNode(windowAccess, windowComponent,
							tableColumns, model, this, childRow);
				}
			}

		}

		public void clearChildren() {
			children = null;
		}

		private Object[] expandMenuExplorerChildren(String next) {
			Vector childvec = ((CMenuExplorerContainer) appletComponent
					.getComponentObject()).getMenuRows(next);
			Object[] rowObject = childvec.toArray();
			if (rowObject != null) {
				children = new TableNode[rowObject.length];
				for (int i = 0; i < rowObject.length; i++) {
					TableRow childRow = new TableRow(
							formatRowlData((Object[]) rowObject[i]), this,
							columns);
					children[i] = new TableNode(windowAccess, appletComponent,
							tableColumns, model, this, childRow);
				}
			}
			return children;
		}

		private Object[] expandTreeTableChildren(String next) {

			Vector childvec = windowAccess.treeExpand_actionPerformed(
					appletComponent.getMethod(), "EXPAND_TABLE",
					appletComponent.getFieldName(), next);

			Object[] rowObject = childvec.toArray();
			if (rowObject != null) {
				children = new TableNode[rowObject.length];
				for (int i = 0; i < rowObject.length; i++) {
					TableRow childRow = new TableRow(
							formatRowlData((Object[]) rowObject[i]), this,
							columns);
					children[i] = new TableNode(windowAccess, appletComponent,
							tableColumns, model, this, childRow);
				}
			}
			return children;
		}

	 
		public Object[] formatRowlData(Object[] rowObject) {
			for (int c = 2; c < rowObject.length; c++) {
				
				if(rowObject[c] != null && windowAccess.getAppletConnector().strictEncoding 
											&&  tableColumns[c - 1].getAppletComponent().isEncrypted())
					rowObject[c] = windowAccess.getAppletConnector().decrypt(rowObject[c].toString());
				
				if (tableColumns[c - 1].getAppletComponent().getDataType().equals("BOL")) {
					if (rowObject[c].getClass().toString().endsWith("String")) {
						if (((String) rowObject[c]).equals("true")) {
							rowObject[c]  = new Boolean(true);
						} else {
							rowObject[c]  = new Boolean(false);
						}
						
						 
					}

				}
			}
			return rowObject;
		}

		protected Object[] getChildren() {
			Object container = appletComponent.getComponentObject();
			String next;
			if (tableRow != null)
				next = (String) tableRow.getCell(0);
			else
				next = "[TOP/]";

			if (children != null)
				return children;
			else if (container instanceof CMenuExplorerContainer)
				return children = expandMenuExplorerChildren(next);
			else if (container instanceof CTreeTableContainer)
				return children = expandTreeTableChildren(next);
			else
				return null;
		}

		 

		public Object[] getCurrentChilren() {
			return children;
		}

		/**
		 * Returns the parent of the receiver.
		 */
		public TableNode getParent() {
			return parentNode;
		}

		/**
		 * Gets the path from the root to the receiver.
		 */
		public TableNode[] getPath() {
			return getPathToRoot(this, 0);
		}

		protected TableNode[] getPathToRoot(TableNode aNode, int depth) {
			TableNode[] retNodes;
			if (aNode == null) {
				if (depth == 0) {
					return null;
				} else {
					retNodes = new TableNode[depth];
				}
			} else {
				depth++;
				retNodes = getPathToRoot(aNode.getParent(), depth);
				retNodes[retNodes.length - depth] = aNode;
			}
			return retNodes;
		}

		public TableRow getRow() {
			return tableRow;
		}

		public CTreeTableContainer getTreeTable() {
			return treeTable;
		}

		public WindowFrame getWindowAccess() {
			return windowAccess;
		}

		/**
		 * Can be invoked when a node has changed, will create the appropriate
		 * event.
		 */
		public void nodeChanged() {
			TableNode parent = getParent();
			if (parent != null) {
				TableNode[] path = parent.getPath();
				int[] index = { getIndexOfChild(parent, this) };
				Object[] children = { this };
				fireTreeNodesChanged(CTreeTableModel.this, path, index,
						children);
			}
		}

		public void setRowValue(Object aValue, int column) {
			tableRow.setCell(aValue, column);
		}

		public String toString() {
			if (tableRow != null) {
				return (String) tableRow.getCell(0);
			} else {
				return "TOP";
			}
		}

	}

	/***************************************************************************
	 * Class: Table Row Desc:
	 **************************************************************************/
	class TableRow {
		int columns;

		boolean leaf;

		Object[] row;

		TableNode tableNode;

		public TableRow(Object[] row, TableNode tableNode, int colums) {
			this.leaf = ((Boolean) row[0]).booleanValue();
			this.tableNode = tableNode;
			this.row = row;
		}

		public Object getCell(int cell) {
			return row[cell + 1];
		}

		public Object[] getRow() {
			return row;
		}

		public boolean isLeaf() {
			return leaf;
		}

		public void setCell(Object value, int cell) {
			row[cell + 1] = value;
		}

		public void setLeaf(boolean leaf) {
			this.leaf = leaf;
		}
	}

	private Class[] cClass;

	private String[] cNames, cTypes;

	private TableNode root;

	private CTableColumn[] tableColumns;

	private CTreeTableContainer treeTable;

	public CTreeTableModel(WindowFrame windowAccess,
			WindowItem windowComponent,
			CTableColumn[] tableColumns, Vector topItem) {
		super(null);
		if (topItem != null) {
			super.setRoot(new TableNode(windowAccess, windowComponent,
					tableColumns, this, topItem));
		}
		root = (TableNode) getRoot();
		this.tableColumns = tableColumns;
	
	}

	public Object getChild(Object aNode, int i) {
		return getChildren(aNode)[i];
	}

	public int getChildCount(Object aNode) {
		Object[] children = getChildren(aNode);
		return (children == null) ? 0 : children.length;
	}

	public Object[] getChildren(Object aNode) {
		TableNode tableNode = ((TableNode) aNode);
		return tableNode.getChildren();
	}

	public Class getColumnClass(int column) {
		return cClass[column];
	}

	public int getColumnCount() {
		return cNames.length;
	}

	public String getColumnName(int column) {
		return cNames[column];
	}

	public String getColumnType(int column) {
		if (cTypes != null) {
			return cTypes[column];
		} else {
			return null;
		}
	}

	protected TableRow getRow(Object aNode) {
		TableNode tableNode = ((TableNode) aNode);
		return tableNode.getRow();
	}

	public Vector getTreeDataRows(TableNode aNode, Vector tableData) {
		if (aNode != null) {
			tableData.add(aNode.getRow().getRow());

		} else {
			aNode = root;
		}

		if (aNode != null) {
			Object[] cChildren = aNode.getCurrentChilren();
			if (cChildren != null)
				for (int i = 0; i < cChildren.length; i++) {
					getTreeDataRows((TableNode) cChildren[i], tableData);
				}
		}
		return tableData;
	}

	public Object getValueAt(Object aNode, int column) {
		TableRow row = getRow(aNode);
		if (row != null) {
			return row.getCell(column);
		} else {
			return null;
		}

	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public boolean isCellEditable(Object aNode, int column) {
		return true;
	}

	public boolean isLeaf(Object aNode) {
		if (((TableNode) aNode).getRow() != null) {
			return ((TableNode) aNode).getRow().isLeaf();
		} else {
			return false;
		}
	}

	public void setTree(CTreeTableContainer treeTable) {
		this.treeTable = treeTable;
		cNames = new String[tableColumns.length];
		cTypes = new String[tableColumns.length];
		cClass = new Class[tableColumns.length];
		for (int c = 0; c < tableColumns.length; c++) {
			WindowItem aComp = tableColumns[c].getAppletComponent();
			cNames[c] = aComp.getDescription();
			cTypes[c] = aComp.getDataType();
			if (cTypes[c].equals("BOL")) {
				cClass[c] = Boolean.class;
			} else {
				cClass[c] = String.class;
			}
		}
		cClass[0] = TreeTableModel.class;
	}

	public void setValueAt(Object aValue, Object aNode, int column) {

		((TableNode) aNode).setRowValue(aValue, column);
	}

}


class CTreeTableRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 7373403728897565298L;

	public CTreeTableRenderer() {
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		return this;
	}
}