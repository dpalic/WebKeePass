 package webBoltOns.client.components;

/*
 * $Id: CTableContainer.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import sun.com.table.TableColumnSorter;
import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.CTableColumn.CTableRowNavBar;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CTableContainer extends JPanel implements StandardComponentLayout, 
		ActionListener, KeyListener, MouseListener, AdjustmentListener, MouseMotionListener{

	private static final long serialVersionUID = -9089928189246377175L;
	protected AppletConnector cnct;
	protected Vector columns = new Vector();
	private CTableModel dataModel;
	protected String[] fieldDescription, fieldName, fieldDataType, fieldParameter;
	protected JScrollPane scrollpane;
	private TableColumnSorter sorter;
	protected CTableColumn[] tableColumns;
	protected String tableTitle = "";
	private JTable tableView;
	public CTabTableContainer tabTable;
	protected int totcol = 0, curcol = 0, tbWidth = 0, tbHeight = 0;
	protected WindowFrame mFrm;
	protected WindowItem comp;
	protected JButton print, copy;
	private CTableRowNavBar navBar;
	
	protected JPopupMenu popUp;	 
	
	public CTableContainer() {}
	
	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		mFrm = mainFrame;
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		tableTitle = thisItem.getDescription();
		setName(Integer.toString(comp.getObjectHL()));
		if( ! comp.getObjectName().equals(WindowItem.TABLE_DOC_MNGR_OBJECT)) {
			if(parentItem.getComponentObject()   instanceof CTabTableContainer) {
				tabTable = (CTabTableContainer) parentItem.getComponentObject();
				tabTable.addTab(this, thisItem.getDescription(), thisItem.getIconName());			
			} else {
				tabTable = null;
				if (comp.getPosition().equals(WindowItem.RIGHT)) 
					((CPanelContainer) parentItem.getComponentObject()).addRight(this, 4);
				else if (comp.getPosition().equals(WindowItem.CENTER)) 
					((CPanelContainer) parentItem.getComponentObject()).addRight(this, 2);
				else 
					((CPanelContainer) parentItem.getComponentObject()).addLeft(this, 0);
			}
		}
		if (thisItem.getHeight() == 0) 
			tbHeight = 300;
		else 
			tbHeight = thisItem.getHeight() * 20;
	}


	
	public void addPopUpButton(WindowItem btn) {
		if(popUp == null) { 
			popUp = new JPopupMenu();
			JLabel opt = new JLabel("  Options");
			popUp.add(opt);
			popUp.add(new JPopupMenu.Separator());
		}
		
		JMenuItem mnu = new JMenuItem(btn.getDescription());
		mnu.setIcon(cnct.getImageIcon("MNode3.gif"));
		mnu.setActionCommand(Integer.toString(btn.getObjectHL()));
		mnu.addActionListener(mFrm);
		popUp.add(mnu);
	}
	
	protected void setNavBar(CTableRowNavBar nb) {
		navBar = nb;
	}
	
	protected CTableRowNavBar getNavBar() {
		return navBar ;
	}
	
	protected void addFormattedRecord(String[] record) {
		Object[] rc = new Object[record.length];
		for (int c = 0; c < tableColumns.length; c++)  
			rc[c] = formatObject(record[c], c);
		dataModel.addRecord(rc);
	}


	public void addColumn(CTableColumn c) {
		columns.add(c);
	}
	
	public String getString() {
		return null;
	}
	
	public void setValid(boolean invalid) {
	}
	
	public boolean locateCursor() {
		return false;
	}
  	
	public void clearComponent(String defaultValue) {
		if (dataModel != null) {
			dataModel.clearTableData();
			rePaintTable();
		}
	}
	
 
	protected void commitEditing() {
		if (tableView != null && tableView.isEditing())  
			tableView.getCellEditor().stopCellEditing();
	}


	private void copyTable() {
		StringBuffer dump = new StringBuffer();
		for (int c = 0; c < fieldDescription.length; c++) {
			dump.append(fieldDescription[c] + "\t");
		}
		dump.append("\n");
		for (int r = 0; r < getRowCount(); r++) {
			for (int c = 0; c < fieldDescription.length; c++) {
				Object value = sorter.getValueAt(r, c);
				if (value != null)
					dump.append(value.toString());
					dump.append("\t");
				}
				dump.append("\n");
			}
 		mFrm.copyToClipBoard(dump.toString());
	}


	private Object formatObject(Object val, int col) {
		if(val == null) 
			return null;
		
		if(cnct.strictEncoding && tableColumns[col].getAppletComponent().isEncrypted())
			val =  cnct.decrypt(val.toString());
		
		if (tableColumns[col].getAppletComponent().getDataType().equals("BOL")) {
			if (val instanceof String) 
				if (((String) val).equals("true"))
					return new Boolean(true);
				else
					return new Boolean(false);
		}
		
		return val;
	}


	public Vector getChangedRecords(boolean nextChange) {
		commitEditing();
		Vector changed = new Vector();
		for (int r = 0; r < tableView.getRowCount(); r++) {
			if (dataModel.rowHasChanged(r, nextChange)) {
				changed.add(getStringRecordAt(r, true));
			}
		}
		return changed;
	}

	
	
	public String [] getEditingRow() {
		int editRow = tableView.getEditingRow();
		commitEditing();
		if(editRow != -1)
			return getStringRecordAt(editRow, true);
		else
			return null;
	}

	
	public void setEditingRow(String [] record) {		
		Object[] rc = new Object[record.length];
		for (int c = 0; c < tableColumns.length; c++)  
			rc[c] = formatObject(record[c],c);
		 		
		int r = tableView.getSelectedRow();
		int c = tableView.getSelectedColumn();
		if(r != -1 && c != -1){
			dataModel.changeRecord(rc, r);
			rePaintTable();
			tableColumns[c].setCellFocus(tableView,r,c);
		}
	}
	
	
	
	public String[] getFieldNames() {
		return fieldName;
	}

	public String[] getFieldParameters() {
		return fieldParameter;
	}

	public JTable getJTable() {
		return tableView;
	}


	public int getRowCount() {
		return tableView.getRowCount();
	}

	public String getSelectedComponentItem() {
		return null;
	}

	
	public Vector getSelectedRecords() {
		commitEditing();
		int row[] = getSelectedRowIndex();
		Vector selected = new Vector();
		if (row.length > 0) {
			for (int x = 0; x < row.length; x++) {
				selected.addElement(getStringRecordAt(row[x], true));
			}
			return selected;
		} else {
			return null;
		}
	}

	
	private int[] getSelectedRowIndex() {
		int[] r = tableView.getSelectedRows();
		int[] s = new int[r.length];
		for (int x = 0; x < r.length; x++) {
			s[x] = sorter.getRowIndex(r[x]);
		}
		return s;
	}

	
		
	private String[] getStringRecordAt(int row, boolean encode) {
			String[] rowString = new String[tableColumns.length];
			for (int col = 0; col < tableColumns.length; col++) {
				Object value = dataModel.getValueAt(row,col);
				if (value != null) {
					
					if(encode && cnct.strictEncoding && tableColumns[col].getAppletComponent().isEncrypted())
						rowString[col] = cnct.encrypt(value.toString());
					else if(tableColumns[col].getAppletComponent().getDataType().equals("DAT"))
						rowString[col] = DataSet.formatDateField(value.toString(), cnct.serverDateFormat);
					else if(tableColumns[col].getAppletComponent().getDataType().equals("TIM"))
						rowString[col] = DataSet.formatTimeField(value.toString(), "kkmmss");
					else	
						rowString[col] = value.toString();
				}
			}
			return rowString;
		}

	
	
	public TableColumnSorter getTableSorter() {
		return sorter;
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
		print.setToolTipText(cnct.getMsgText("TXT0119"));
		print.setCursor(new Cursor(Cursor.HAND_CURSOR));
		print.setActionCommand("print");
		print.addActionListener(this);

		copy = new JButton(cnct.tableCopyIcon);
		copy.setToolTipText(cnct.getMsgText("TXT0120"));
		copy.setCursor(new Cursor(Cursor.HAND_CURSOR));
		copy.setActionCommand("copy");
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
		
		if(navBar != null && popUp != null) {
			Component [] m = popUp.getComponents();
			for(int a = 0; a < m.length; a++) {
				if(m[a] instanceof JMenuItem) 
					navBar.addOption((JMenuItem) m[a]);
			}
		}
		
		dataModel = new CTableModel(fieldDescription, fieldDescription.length,  this);
		sorter = new TableColumnSorter(dataModel, tabTable, tableColumns,cnct);
		
		tableView = new JTable(sorter);
		sorter.addMouseListenerToHeaderInTable(tableView);
		tableView.setRowMargin(2);
		tableView.setRowHeight(20);
		tableView.setFont(cnct.standardFont);
		tableView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableView.getTableHeader().setFont(cnct.standardFont);
		rePaintTable();
		scrollpane = new JScrollPane(tableView);
		scrollpane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createCompoundBorder(BorderFactory.createTitledBorder(""),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)), scrollpane.getBorder())); 
		scrollpane.setPreferredSize(new Dimension(
				tableView.getPreferredSize().width + 40, tbHeight + 15));
		
		scrollpane.getVerticalScrollBar().addAdjustmentListener(this);
		tableView.addMouseListener(this);
		tableView.addMouseMotionListener(this);
		tableView.addKeyListener(this);
		
		setLayout(new BorderLayout());
		tableView.setGridColor(new Color(240, 240, 240));
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		if (cnct.tablePrintAccess)
			scrollpane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, print);
		if (cnct.tableCopyAccess)
			scrollpane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, copy);
	//	add(scrollpane, BorderLayout.CENTER);
	add(scrollpane);

	}

	
	public void populateComponent(String action, String tableName, DataSet dataSet) {
		
		if (action == null)		
			setTableData(dataSet.getTableVector(tableName));
		
		else if (action.equals(WindowItem.MORE_RECORD))  
			setMoreTableData(dataSet.getTableVector(tableName), false);
		
		else if(action.equals(WindowItem.TABLE_PROMPT))			
			setEditingRow(dataSet.getCurrentTableRow(tableName));		
		
		else if (action.equals(WindowItem.EDIT_RECORD)) 	
			return;
				
		else if (action.equals(WindowItem.DELETE_RECORD))
			return;
		
		else if (action.equals(WindowItem.POST_RECORD))
			return;
		
		else if (action.equals(WindowItem.STD_COMMIT))
			return;
		
		else	
			setTableData(dataSet.getTableVector(tableName));
	}


	public DataSet populateDataSet(String action, String tablename,
			DataSet dataSet) {
		
	dataSet.remove(tablename);
	if(getRowCount() > 0) { 
		if(action == null)
			dataSet.putTableVector(tablename, getSelectedRecords());
		
		else if (action.equals(WindowItem.NEWLINE))
			return dataSet;
			
		else if (action.equals(WindowItem.EDIT_RECORD))
			dataSet.putTableVector(tablename, getChangedRecords(false));	
						
		else if (action.equals(WindowItem.POST_RECORD))
			dataSet.putTableVector(tablename, getChangedRecords(true));	
			
		else if (action.equals(WindowItem.TABLE_PROMPT))	
			dataSet.putCurrentTableRow(tablename,  getEditingRow());
	 		
	 	else 
			dataSet.putTableVector(tablename, getSelectedRecords());

 	 } 	
	
	return dataSet;
	}


	
	
	private void printTable() {
		try {
			tableView.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat(
							tableTitle), new MessageFormat("Page - {0}"));
		} catch (Exception er) {}
	}


	private void rePaintTable() {
		commitEditing();
 		for (int x = 0; x < totcol; x++) {
			TableColumn c = tableView.getColumn(tableColumns[x]
					.getAppletComponent().getDescription());
			tableColumns[x].setHeaderRendererFont(cnct.headerFont);
			tableColumns[x].setHeaderRendererIcon(null);
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

	public boolean setEmptyLine(boolean requestfocus) {
		commitEditing();
		String[] rec = new String[tableColumns.length];
		for (int r = 0; r < tableColumns.length; r++) {
			rec[r] = tableColumns[r].initNewLine();
		}
		
		addFormattedRecord(rec);
		rePaintTable();
		int r = tableView.getRowCount()-1;
		if (r > -1) {
			for (int c = 0; c < tableColumns.length; c++) {
				if (!tableColumns[c].getAppletComponent().isProtected()) {
					tableColumns[c].setCellFocus(tableView, r, c);
			 		requestfocus = false;
			 		c = tableColumns.length;
				}
			}
		}
		return requestfocus;
	}

	
	public boolean setMoreTableData(Vector v, boolean requestfocus) {
		commitEditing();
		if (v != null) {
			Enumeration e = v.elements();
			while (e.hasMoreElements()) {
				String[] rec = (String[]) e.nextElement();
				addFormattedRecord(rec);
			}
		}
		rePaintTable();
		int r = tableView.getRowCount()-1;
		if (requestfocus) {
			for (int c = 0; c < tableColumns.length; c++) {
				if (!tableColumns[c].getAppletComponent().isProtected()) {
					tableColumns[c].setCellFocus(tableView,r,c);
					c = tableColumns.length;
					requestfocus = false;
				}
			}
		}
		return requestfocus;
	}


	public String getDocID() {
		String rw [] = getStringRecordAt(tableView.getSelectedRow(), false);
		if (rw == null) return "";
		for(int x=0; x < fieldName.length; x++)
			if(fieldName[x].equals("DocumentID")) return rw[x];
		return "";
	}
	

	public void setRowChanged(int row) {
		dataModel.setRowChanged(row);
	}


	public void setSelection(int sp, int sr[]) {
		scrollpane.getVerticalScrollBar().setValue(sp);
		if (sr != null) {
			int rows = sr.length;
			for (int r = 0; r < rows; r++) {
				if (r == 0) {
					tableView.getSelectionModel().setSelectionInterval(sr[0], sr[0]);
				} else {
					tableView.getSelectionModel().addSelectionInterval(sr[r], sr[r]);
				}
			}
		}
	}


	
	protected void setTableData(Vector v) {
		commitEditing();
		dataModel.clearTableData();
		if (v != null) {
			Enumeration e = v.elements();
			while (e.hasMoreElements()) {
				String[] rec = (String[]) e.nextElement();
				addFormattedRecord(rec);
			}
		}
		rePaintTable();
		if (getRowCount() != 0)
			setSelection(0, new int[] { 0, 0 });
	}

	
	public void setToolTipText(String tipField, String tip) {
		for (int c = 0; c < fieldName.length; c++) {
			try {
				if (fieldName[c].equals(tipField)) {
					tableColumns[c].setHeaderRendererTips(tip);
				}
			} catch (Exception e) {
			}
		}
	}
	
	
	public boolean validateComponent(String action, String editorName) {
		commitEditing();
		if(tableView == null)
			return true;
		
		for (int r = 0; r < tableView.getRowCount(); r++) {
			if (dataModel.rowHasChanged(r, true)) {
				String [] record = getStringRecordAt(r, false);
				 for(int c = 0;c < tableColumns.length; c++ ) {
					WindowItem i =  tableColumns[c].getAppletComponent();
 				 	if(!mFrm.isVaildValue(record[c], i.getObjectHL())) {
 				 		return false;
				 	}
				 }
			}
		}
		return true;
	}


	public void keyPressed(KeyEvent e) {
		int c = tableView.getSelectedColumn();
		int r = tableView.getSelectedRow();
		if (c != -1 && r != -1) 
			tableColumns[c].tableColumnPressed(mFrm, tableView, e, c, r);	
	}


	public void keyReleased(KeyEvent e) {
		if (tabTable != null) {
			tabTable.fireSelectionChanged(this, scrollpane
					.getVerticalScrollBar().getValue(), tableView
					.getSelectedRows());

		}
		if (tableView.getSelectedColumn() != -1) {
			if (mFrm != null && e.getKeyCode() == 83) {
				mFrm.fireWindowReturning("", null);
			}
		}
	}



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
		if (tabTable != null) {
			tabTable.fireSelectionChanged(this,
					scrollpane.getVerticalScrollBar().getValue(),
					tableView.getSelectedRows());
		}
	 
		
		if (mFrm != null && e.getClickCount() > 1 && e.getButton() == MouseEvent.BUTTON1) {		
			int r = tableView.rowAtPoint(e.getPoint());
			int c = tableView.columnAtPoint(e.getPoint());
        	if(c >= 0 && r >= 0)      	 
        		mFrm.fireWindowReturning(fieldDescription[c], tableView.getValueAt(r, c));
        	else
        		mFrm.fireWindowReturning("", null);
        		
		} else if(e.getButton() == MouseEvent.BUTTON3 && popUp != null) {
			 SwingUtilities.invokeLater(new Runnable() {
                 public void run() {
                	 int r = tableView.rowAtPoint(e.getPoint());
                	 if(r >= 0) tableView.setRowSelectionInterval(r, r);
                 	}
			 });
			popUp.show(e.getComponent(), e.getX(), e.getY());
		
		} else 	if(navBar != null) {
			 SwingUtilities.invokeLater(new Runnable() {
	                public void run() {			
	                	navBar.fireNavAction(e);
             	}
			 });	 
		}
	
		
		
	}


	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (tabTable != null) {
			tabTable.fireSelectionChanged(this, scrollpane
					.getVerticalScrollBar().getValue(), null);
		}
		
	}
	
	
	public void mouseClicked(MouseEvent e) {
	
	}
	
	
	public void hidePopUP() {
			popUp.setVisible(false);
	}
	
	public void mouseMoved(MouseEvent e) {
		if (navBar != null)
			navBar.fireNavMoved(e);
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void setProperty(String propertyName, String propertyValue) {}
	public void mouseDragged(MouseEvent e) {}
	
 }


class CTableModel extends AbstractTableModel implements TableModelListener {

	private static final long serialVersionUID = -8036228282594775893L;
	private Vector cache = new Vector();
	private Vector chg = new Vector();
	private int columnCount;
	private String[] columnNames;
	private CTableContainer thisTable;

	public CTableModel(String[] columnNames, int columnCount, CTableContainer thisTable) {
		this.columnCount = columnCount;
		this.columnNames = columnNames;
		this.thisTable = thisTable;
		cache = new Vector();
	}

	public void addRecord(Object[] rowObject) {
		cache.addElement(rowObject);
		chg.add(Boolean.FALSE);
		fireTableChanged(null);
	}

	public void changeRecord(Object[] rowObject, int row) {
		cache.set(row, rowObject);
		chg.set(row, Boolean.TRUE);
		fireTableChanged(null);
	}
	
	

	public void clearTableData() {
		cache.removeAllElements();
		chg.removeAllElements();
		fireTableChanged(null);
	}

	public Class getColumnClass(int c) {
		return thisTable.tableColumns[c].getCellClass();
	}

	public int getColumnCount() {
		return columnCount;
	}

	public String getColumnName(int i) {
		return columnNames[i];
	}


	public int getRowCount() {
		if (cache != null) {
			return cache.size();
		} else {
			return 0;
		}
	}

	public Object getValueAt(int row, int col) {
		Object[] rw = (Object[]) cache.elementAt(row);
		return rw[col];
	}

	
	public boolean isCellEditable(int row, int col) {
		return true;
	}


	public boolean rowHasChanged(int row) {
		return ((Boolean) chg.get(row)).booleanValue();
	}

	public boolean rowHasChanged(int row, boolean nextchange) {
		Boolean changed = (Boolean) (chg.get(row));
		if (!nextchange) {
			chg.setElementAt(Boolean.FALSE, row);
		}
		return changed.booleanValue();
	}

	public void setRowChanged(int row) {
		chg.setElementAt(Boolean.TRUE, row);
	}

	public void setValueAt(Object aValue, int row, int col) {
		Object[] rw = ((Object[]) cache.elementAt(row));
		rw[col] = aValue;
		cache.setElementAt(rw, row);
		setRowChanged(row);
		if (thisTable.tabTable != null) {
			thisTable.tabTable.fireRowChanged(thisTable, row);
		}
	}
		
	public void tableChanged() {
		fireTableChanged(null);
	}

	public void tableChanged(TableModelEvent e) {
		fireTableChanged(e);
	} 	
}