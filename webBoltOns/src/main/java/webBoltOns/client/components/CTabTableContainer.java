package webBoltOns.client.components;

/*
 * $Id: CTabTableContainer.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import sun.com.table.TableColumnSorter;



import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowParm;
import webBoltOns.dataContol.DataSet;

public class CTabTableContainer extends JPanel implements StandardComponentLayout {

	private static final long serialVersionUID = -3568800542933754796L;

	protected AppletConnector cnct;

	protected Hashtable tabs;

	protected JTabbedPane tabtable;

	protected Vector vtabs = new Vector();

	protected WindowItem wc;

	protected WindowFrame accs;

	protected WindowItem comp;

	public CTabTableContainer() {}

	public void addTab(CTableContainer table, String desc, String iconName) {
		vtabs.add(table);
		ImageIcon tabIcon = null;
		if(!iconName.equals("")) tabIcon = cnct.getImageIcon(iconName);
		tabtable.addTab(desc, tabIcon, table);
	}

	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		
		accs = mainFrame;
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		
		tabtable = new JTabbedPane();
		tabtable.setFont(cnct.headerFont);
		tabtable.setDoubleBuffered(true);
		this.setLayout(new GridFlowLayout(0, 0));
		add(tabtable, new GridFlowParm(GridFlowParm.NEXT_ROW, 0));
		setName(Integer.toString(comp.getObjectHL()));
		
		if(comp.getOrientation().equals(WindowItem.BOTTOM))
			tabtable.setTabPlacement(JTabbedPane.BOTTOM);
		else
			tabtable.setTabPlacement(JTabbedPane.TOP);
		
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 4);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(this, 2);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(this, 0);
		}
	}

	public void clearComponent(String defaultValue) {
	}
	
	public String getString() {
		return null;
	}
	
	public void fireIconsCleared() {
		Enumeration etabs = vtabs.elements();
		while (etabs.hasMoreElements()) {
			CTableContainer t = (CTableContainer) etabs.nextElement();
			TableColumnModel columnModel = t.getJTable().getColumnModel();
			for (int ci = 0; ci < columnModel.getColumnCount(); ci++) {
				TableColumn col = columnModel.getColumn(ci);
		
			 CTableColumn.CTableHeaderRenderer hd = (CTableColumn.CTableHeaderRenderer) col
						.getHeaderRenderer();
				hd.setIcon(null);
			}

		}
	}

	public void fireRowChanged(CTableContainer table, int row) {
		Enumeration etabs = vtabs.elements();
		while (etabs.hasMoreElements()) {
			CTableContainer t = (CTableContainer) etabs.nextElement();
			if (t != table) {
				t.setRowChanged(row);
			}
		}
	}
  	
	
	public boolean locateCursor() {
		return false;
	}
  	
	public void fireSelectionChanged(CTableContainer table, int sp, int sr[]) {
		Enumeration etabs = vtabs.elements();
		while (etabs.hasMoreElements()) {
			CTableContainer t = (CTableContainer) etabs.nextElement();
			if (t != table)
				t.setSelection(sp, sr);
		}

	}

	public void fireTableSorting(TableColumnSorter sorter, int i, int j) {
		Enumeration etabs = vtabs.elements();
		while (etabs.hasMoreElements()) {
			CTableContainer t = ((CTableContainer) etabs.nextElement());
			TableColumnSorter s = t.getTableSorter();
			if (s != sorter) {
				s.setTableChanged();
				s.swap(i, j);
			}
		}
	}

	public String getSelectedComponentItem() {
		return null;
	}

	public void initializeComponentUI() {}
	
	public void populateComponent(String action, String tableName, DataSet dataSet) { }

	public DataSet populateDataSet(String action, String editorName,
			DataSet dataSet) {
		return dataSet;
	}

	public void requestTabFocus(CTableContainer tab, JTable table) {
		tabtable.setSelectedComponent(tab);
		table.grabFocus();
	}

	public boolean validateComponent(String action, String editorName) {return true;}
	public void setValid(boolean invalid) {}
	public void setProperty(String propertyName, String propertyValue) {}

}