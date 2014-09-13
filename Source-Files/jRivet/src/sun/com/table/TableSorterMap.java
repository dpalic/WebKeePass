package sun.com.table;

/*
* Copyright 1997, 1998 by Sun Microsystems, Inc.,
* 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
* All rights reserved.
*
* This software is the confidential and proprietary information
* of Sun Microsystems, Inc. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with Sun.
*/
 
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableSorterMap extends AbstractTableModel implements
		TableModelListener {

	private static final long serialVersionUID = -8399594953731541803L;
	protected TableModel model;

	public TableModel getModel() {
		return model;
	}

	public void setModel(TableModel model) {
		this.model = model;
		model.addTableModelListener(this);
	}

	public Object getValueAt(int aRow, int aColumn) {
		return model.getValueAt(aRow, aColumn);
	}

	public void setValueAt(Object aValue, int aRow, int aColumn) {
		model.setValueAt(aValue, aRow, aColumn);
	}

	public int getRowCount() {
		return (model == null) ? 0 : model.getRowCount();
	}

	public int getColumnCount() {
		return (model == null) ? 0 : model.getColumnCount();
	}

	public String getColumnName(int aColumn) {
		return model.getColumnName(aColumn);

	}

	public Class getColumnClass(int aColumn) {
		return model.getColumnClass(aColumn);
	}

	public boolean isCellEditable(int row, int column) {
		return model.isCellEditable(row, column);
	}

	public void tableChanged(TableModelEvent e) {
		fireTableChanged(e);
	}
}