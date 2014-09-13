package webBoltOns.client.components;


/*
 * $Id: CTableColumn.java,v 1.1 2007/04/20 19:37:20 paujones2005 Exp $ $Name:  $
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


import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.CComboBoxField.ComboItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class CTableColumn extends JComponent implements StandardComponentLayout  {

	private static final long serialVersionUID = 1400113917490861574L;

	private WindowItem comp;
	private AppletConnector cnct;
	private WindowFrame frm;

	int cellnumber = 0;

	private CTableCellEditor tblCellEd;
	private CTableCellRenderer tblCellRndr;
	private CTableHeaderRenderer colHdrRndr;
	
	private CTableContainer table;
	public CTableColumn() {}
	
	
	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		table = (CTableContainer) parentItem.getComponentObject(); 
		frm = mainFrame;
		
	  	   // 1 - Add a Text Box Object
		if (thisItem.getObjectName().equals(WindowItem.TABLE_TEXTFIELD_OBJECT)) {
			final CTextBoxField textFieldColumn = new CTextBoxField();
			thisItem.setComponentObject(textFieldColumn);
			textFieldColumn.buildComponent(comp, thisItem, mainFrame);

			//  2 - Add a Check Box Object
		} else if (thisItem.getObjectName().equals(WindowItem.TABLE_CHECKBOX_OBJECT)) {
			final JCheckBox checkBox = new JCheckBox("");
			thisItem.setComponentObject(checkBox);
			thisItem.setDataType("BOL");
			
			
			// 3- Add A Combo Box Object
		} else if (thisItem.getObjectName().equals(WindowItem.TABLE_COMBOBOX_OBJECT)) {
			final CComboBoxField comboBox = new CComboBoxField();		
			comboBox.buildComponent(comp, thisItem, mainFrame);
			thisItem.setComponentObject(comboBox);
			thisItem.setDataType("CMB");
				
			
				// 4- Add A Spinner Object
		} else if (thisItem.getObjectName().equals(WindowItem.TABLE_SPINNER_OBJECT)) {
			thisItem.setDataType("INT");
			JSpinner spinner = new JSpinner();
			thisItem.setComponentObject(spinner);
				
				// 5- Add An Image Object 
		} else if (thisItem.getObjectName().equals(WindowItem.TABLE_IMAGE_OBJECT  )) {
			thisItem.setDataType("IMG");
			thisItem.setProtected(true);
			JTextField imageField = new JTextField();
			imageField.setEditable(false);
			imageField.setFocusable(false);
			thisItem.setComponentObject(imageField);
			
			// 5- Add An PWS Object 
		} else if (thisItem.getObjectName().equals(WindowItem.TABLE_PWD_OBJECT  )) {
			thisItem.setDataType("PWD");
			thisItem.setProtected(true);
			JTextField imageField = new JTextField();
			imageField.setEditable(false);
			imageField.setFocusable(false);
			thisItem.setComponentObject(imageField);
		
			// 5- Add a nav Bar
		} else if (thisItem.getObjectName().equals(WindowItem.TABLE_NAVROW_OBJECT  )) {
			thisItem.setDataType("NAV");
			thisItem.setProtected(true);
			thisItem.setComponentObject(new CTableRowNavBar(thisItem));
		}
		
		tblCellRndr = new CTableCellRenderer(comp.getComponentObject());
		tblCellEd = new CTableCellEditor(comp.getComponentObject()); 
		tblCellRndr.initRenderer();
		colHdrRndr = new CTableHeaderRenderer();
		
		if(thisItem.isProtected())
			colHdrRndr.setForeground(Color.BLACK);
		else if(!thisItem.getLink().equals("")  
				|| thisItem.getDataType().equals("DAT") ||
					thisItem.getDataType().equals("FLT"))
			colHdrRndr.setForeground(Color.BLUE);
		else	
			colHdrRndr.setForeground(Color.BLACK);
		
		if(comp.getComponentObject() != null)
			comp.getComponentObject().setBackground(Color.WHITE);
		
		comp.setLength(comp.getLength() * 15);
		tblCellRndr.setHorizontalAlignment(comp.getCellAlignment());
		
		if (comp.isProtected()) 
			tblCellEd.setClickCountToStart(99);
		 else 	
			tblCellEd.setClickCountToStart(1);

		((CTableContainer) parentItem.getComponentObject()).addColumn(this);
	}
		
	
	public Class getCellClass() {
	 	if(comp.getObjectName().equals(WindowItem.TABLE_CHECKBOX_OBJECT))
	 		return (Boolean.class); 
	 	else 
			return comp.getComponentObject().getClass();
	}
	
	
	public CTableContainer getTable() {
		return table;
	}
	
	public WindowItem getAppletComponent() {
		return comp;
	}

	public int getCellNumber() {
		return cellnumber;
	}

	public void setCellNumber(int cellnumber) {
		this.cellnumber = cellnumber;
	}

	public CTableCellEditor getCellEditor() {
		return tblCellEd;
	}

	public CTableCellRenderer getCellRenderer() {
		return tblCellRndr;
	}

	public CTableHeaderRenderer getHeaderRenderer() {
		return colHdrRndr;
	}

	public void setHeaderRendererIcon(Icon icon) {
		colHdrRndr.setIcon(icon);
	}

	public void setHeaderRendererTips(String tips) {
		colHdrRndr.setToolTipText(tips);
	}

	public void setHeaderRendererFont(Font font) {
		colHdrRndr.setFont(font);
	}

	public String initNewLine() {
		if(comp.getDataType().equals("INT") || 
				comp.getDataType().equals("FLT"))
			return "0";
		else if(comp.getDataType().equals("BOL"))
			return "false";
		else
			return "";
	}
	
	public void setTreeColumn() {
		tblCellRndr = null;
		tblCellEd = null; 
	}
  	
	
	public boolean locateCursor() {
		return false;
	}
  	
	
	public  void tableColumnPressed(WindowFrame mFrm, JTable tableView,  KeyEvent keyEvent, 
			          final int column, final int row ) {
 
		final int k = keyEvent.getKeyCode();
       
		if(row == tableView.getRowCount() -1 && k == 40) {
			mFrm.doNewLineClick();
			keyEvent.consume();
			return;
		}	
			
		if(comp.isProtected())  
        	return;
       
		if (keyEvent.getModifiers() != 8
				&& ((k > 95 && k < 106) || (k > 43 && k < 57)
						|| (k > 64 && k < 90) || (k == 222) || (k == 192))) {

			boolean selected = false;
			if(comp.getComponentObject()  instanceof CTextBoxField ) 
				selected = ((CTextBoxField) comp.getComponentObject()).isSelected();
			
			if (!mFrm.isVaildCharacter(keyEvent, comp.getObjectHL(), selected)) 
				return;

			tableView.editCellAt(row, column);
			if(comp.getComponentObject()  instanceof CTextBoxField ) {
				((CTextBoxField) comp.getComponentObject()).requestFocus();
				((CTextBoxField) comp.getComponentObject()).setText(null);
			
			} else	if(comp.getComponentObject()  instanceof CComboBoxField ) {
				((CComboBoxField ) comp.getComponentObject()).requestFocus();
			}
		  	
		}
	}
 
	
	public  void setCellFocus(JTable tableView, int row, int column) {
		tableView.scrollRectToVisible(tableView.getCellRect(row, 0, true));		
		tableView.setRowSelectionInterval(row, row);
		tableView.setColumnSelectionInterval(column,column);
		tableView.editCellAt(row, column);
		if(comp.getComponentObject()  instanceof CTextBoxField ) {
			((CTextBoxField) comp.getComponentObject()).requestFocus();
			((CTextBoxField) comp.getComponentObject()).setText(null);
	
		} else	if(comp.getComponentObject()  instanceof CComboBoxField ) {
			CComboBoxField  c = (CComboBoxField) comp.getComponentObject();
			c.requestFocus();
		}
	}


	//****************************************************************************************
	protected class CTableRowNavBar extends JPanel  {
	
		private static final long serialVersionUID = 7413021481590051465L;
		private Hashtable oh = new Hashtable();
		
		public CTableRowNavBar(WindowItem navRow) {
			super();
			setLayout(new FlowLayout(0));
			table.setNavBar(this);
		}	
		
		
		public JLabel addOption(JMenuItem opt) {
			JLabel ol = new JLabel(cnct.getImageIcon("MNode3.gif"));		
			add(ol);
			oh.put(ol, opt);
			return ol;
		}
		
		
		public JLabel addOption(String image) {
			JLabel ol = new JLabel(cnct.getImageIcon(image));		
			add(ol);
			oh.put(ol, image);
			return ol;
		}
		
		
		public void fireNavAction(MouseEvent e){
			Enumeration en = oh.keys();
			while(en.hasMoreElements()){
				JLabel ol = (JLabel) en.nextElement();		
				Rectangle r1 = ol.getBounds();
				if(r1.contains(e.getX(), r1.getCenterY()))
					if(oh.get(ol) instanceof JMenuItem)
						((JMenuItem)oh.get(ol)).doClick();
					else 
						frm.actionRunDoc(table.getDocID());
			}
		}		
		
		
		public void fireNavAction(){
			frm.actionRunDoc(table.getDocID());
		}
		
	
		public void fireNavMoved(MouseEvent e){
			Enumeration en = oh.keys();
			while(en.hasMoreElements()){
				JLabel ol = (JLabel) en.nextElement();		
				Rectangle r1 = ol.getBounds();
				if(r1.contains(e.getX(), r1.getCenterY()))
					if(oh.get(ol) instanceof JMenuItem)
						setToolTipText(((JMenuItem)oh.get(ol)).getText());
					else
						setToolTipText(((String)oh.get(ol)));
			}
		}
		
	}
 
	
	
	
	
	
	//****************************************************************************************
	class CTableCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 3402882650408759449L;
		protected String dataType = "CHR";
		
		public CTableCellRenderer(final JComponent component) {}
		
		
		public void initRenderer() {
			dataType = comp.getDataType();;
			 
			if(comp.isProtected())
				setBackground(new Color(252, 252, 252));
			else if(!comp.getLink().equals("") ||  dataType.equals("DAT")|| dataType.equals("CMB"))
				setBackground(new Color(250,250,255));
			else
				setBackground(cnct.bgColor);
		}

		
	    public Component getTableCellRendererComponent(JTable table, Object value, 
	    				boolean isSelected, boolean hasFocus, int row, int col) {
	    	
	    	if(dataType.equals("NAV"))
	    		return comp.getComponentObject();
	    	
    		return super.getTableCellRendererComponent(table,  value, isSelected,  hasFocus,  row,  col); 
	    	
	      }
	
		protected void setValue(Object value) {
			if(dataType.equals("BOL") || dataType.equals("NAV")) {
			 return; 
			
			} else if (dataType.equals("IMG")) {
				setText(null);
				setIcon(cnct.getImageIcon((String) value));
				setHorizontalAlignment(JTextField.CENTER);


			} else if (dataType.equals("PWD")) {
				setText(null);
				setIcon(cnct.getImageIcon("idots.gif"));
				setHorizontalAlignment(JTextField.CENTER);

				
			} else if(value  instanceof String ) {
				setIcon(null);
				if(dataType.equals("CMB")) 
					formatText( ((CComboBoxField) comp.getComponentObject()).getKeyValue((String) value));
				else
					formatText((String) value);
			
			} else {
				setIcon(null);
				setText(null);
			}
		}		

		
 	private void formatText(String value) {
			String dataType = comp.getDataType();
			int len = comp.getDataLength();
			try{
				if (dataType.equals("INT")) 
					super.setText(DataSet.formatIntegerField(value, comp.getEditMask()));
				else if ((dataType.equals("FLT"))) 
					super.setText(DataSet.formatDoubleField(value, comp.getEditMask()));
				else if (dataType.equals("DAT")) 
					super.setText(DataSet.formatDateField(value, cnct.dateFormat));
				else if (dataType.equals("TIM")) 
					super.setText(DataSet.formatTimeField(value, "kk:mm:ss"));
				else 
					super.setText(DataSet.formatCharField(value,  comp.getEditMask()));
				
			} catch (NumberFormatException e) {
				super.setText(null);
			}
 	} 	
	
	}

	
	
	class CTableCellEditor extends AbstractCellEditor implements TableCellEditor,  FocusListener {
		
		protected class EditorDelegate implements ActionListener, ItemListener, Serializable {

			private static final long serialVersionUID = 7842446339894698115L;
				protected Object value;

				public Object getCellEditorValue() {
					return value;
				}

				public void setValue(Object value) {
					this.value = value;
				}

				public boolean isCellEditable(EventObject anEvent) {
					if(comp.isProtected()) {  			 
						return false;
					}	if (anEvent instanceof MouseEvent) {
						return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
					}
					return true;
				}

				public boolean shouldSelectCell(EventObject e) {
					return true;
				}

				public boolean startCellEditing(EventObject e) {
					return true;
				}

				public boolean stopCellEditing() {
					fireEditingStopped();
					return true;
				}

				public void cancelCellEditing() {
					fireEditingCanceled();
				}

				public void actionPerformed(ActionEvent e) {
					CTableCellEditor.this.stopCellEditing();
				}

				public void itemStateChanged(ItemEvent e) {
					CTableCellEditor.this.stopCellEditing();
				}
			}
		
		
		private static final long serialVersionUID = 5527326520100450264L;
		protected JComponent editorComponent;
		protected EditorDelegate delegate;
		protected int clickCountToStart = 1;

		public CTableCellEditor (final JComponent component) {
			if(component != null) {
				setClickCountToStart(1);
				component.addFocusListener(this);
			}
			
			if (component instanceof CTextBoxField)
				setTextBoxEditor((CTextBoxField) component);
			else if (component instanceof JCheckBox)
				setCheckBoxEditor((JCheckBox) component);
			else if (component instanceof CComboBoxField)
				setComboBoxEditor((CComboBoxField) component);
			else if (component instanceof JSpinner)
				setSpinnerEditor((JSpinner) component);
			else
				delegate = null;
		}
		
				
		private void setTextBoxEditor(final CTextBoxField textField) {
			editorComponent = textField;
			delegate = new EditorDelegate() {

				private static final long serialVersionUID = 1450748269941425282L;

				public void setValue(Object aValue) {
					if(aValue != null)	
						textField.setText(aValue.toString());
					else
						textField.setText(null);
				}

				public Object getCellEditorValue() {
					return textField.getText();
				}

				public boolean shouldSelectCell(EventObject anEvent) {
					textField.selectAll();
					return true;
				}
				
				public boolean stopCellEditing() {
						return super.stopCellEditing();
				}
			};
			textField.addActionListener(delegate);
		}

		private void setCheckBoxEditor(final JCheckBox checkBox) {
			editorComponent = checkBox;
			delegate = new EditorDelegate() {
				private static final long serialVersionUID = 1L;

				public void setValue(Object value) {
					boolean selected = false;
					if (value instanceof Boolean) {
						selected = ((Boolean) value).booleanValue();
					} else if (value instanceof String) {
						selected = value.equals("true");
					}
					checkBox.setSelected(selected);
				}

				public Object getCellEditorValue() {
					return Boolean.valueOf(checkBox.isSelected());
				}
			};
			checkBox.addActionListener(delegate);
		}

		private void setComboBoxEditor(final CComboBoxField comboBox) {
			editorComponent = comboBox;
			comboBox.putClientProperty("JComboBox.isTableCellEditor",Boolean.TRUE);

			delegate = new EditorDelegate() {
				private static final long serialVersionUID = -2255619450561506966L;
				public void setValue(Object value) {
					comboBox.commitEditing((String) value);
				}

				
				public Object getCellEditorValue() {
					ComboItem item = (ComboItem) comboBox.getSelectedItem();
					if(item != null)
						return item.getKey();
					else
						return null;
				}

				
				public boolean isCellEditable(EventObject anEvent) {
					return true;
				}
	
				public boolean shouldSelectCell(EventObject anEvent) {
	 				if (anEvent instanceof MouseEvent) {
						MouseEvent e = (MouseEvent) anEvent;
						return e.getID() != MouseEvent.MOUSE_DRAGGED;
					}
					return true;
				}

				
				public boolean stopCellEditing() {	
					if (comboBox.isEditable()) 
						comboBox.actionPerformed(new ActionEvent(CTableCellEditor.this, 0, ""));
					return super.stopCellEditing();
					 	
				}
			};
			
			
			comboBox.addActionListener(delegate);
		}

		
		private void setSpinnerEditor(final JSpinner spinner) {
			editorComponent = spinner;
			spinner.getEditor().setEnabled(true);
			spinner.setEditor(new JSpinner.NumberEditor(spinner, "#########"));
			JComponent editor = spinner.getEditor();
			JFormattedTextField spinnerField = ((JSpinner.NumberEditor) editor).getTextField();
			spinnerField.setHorizontalAlignment(JTextField.LEFT);
			spinnerField.setEditable(true);

			delegate = new EditorDelegate() {

				private static final long serialVersionUID = 1L;

				public void setValue(Object value) {
					if (DataSet.isInteger((String) value))
						spinner.setValue(Integer.valueOf((String) value));
					else
						spinner.setValue(Integer.valueOf("0"));
				}

				public Object getCellEditorValue() {
					return spinner.getValue().toString();
				}

				public boolean shouldSelectCell(EventObject anEvent) {
					if (anEvent instanceof MouseEvent) {
						MouseEvent e = (MouseEvent) anEvent;
						return e.getID() != MouseEvent.MOUSE_DRAGGED;
					}
					return true;
				}

				public boolean stopCellEditing() {
					try {
						((JSpinner.NumberEditor) spinner.getEditor()).commitEdit();
					} catch (java.text.ParseException pe) {
						spinner.setValue(new Integer(0));
					}
					return super.stopCellEditing();
				}
			};

			spinnerField.addActionListener(delegate);
		}

		public Component getComponent() {
			return editorComponent;
		}

		public void setClickCountToStart(int count) {
			clickCountToStart = count;
		}

		public int getClickCountToStart() {
			return clickCountToStart;
		}

		public Object getCellEditorValue() {
			return delegate.getCellEditorValue();
		}

		public boolean isCellEditable(EventObject e) {
			if(delegate == null) 
				return false;
			else
				return delegate.isCellEditable(e);
		}

		public boolean shouldSelectCell(EventObject e) {
			if(delegate == null) 
				return false;
			else
				return delegate.shouldSelectCell(e);
		}
		
		public boolean stopCellEditing() {
			return delegate.stopCellEditing();
		}

		public void cancelCellEditing() {
			delegate.cancelCellEditing();
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			if(delegate != null)
				delegate.setValue(value);
			return editorComponent;
		}



	public void focusGained(FocusEvent e) {
		editorComponent.setBackground(cnct.crsColor);
	}


	public void focusLost(FocusEvent e) {
		editorComponent.setBackground(Color.white);
	}

	}

	public class CTableHeaderRenderer extends JLabel implements TableCellRenderer, Serializable {

		private static final long serialVersionUID = 72875375579075491L;

		public CTableHeaderRenderer() {
			super();
			setOpaque(false);
			setBorder(BorderFactory.createRaisedBevelBorder());
			setHorizontalAlignment(0);
		}

		public void setForeground(Color c) {
			super.setForeground(c);
		}

		public void setBackground(Color c) {
			 super.setBackground(c);
		}

		public void updateUI() {
			super.updateUI();
			setBackground(null);
		}

		public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setFont(table.getFont());
			setValue(value);
			return this;
		}

		public boolean isOpaque() {
			Color back = getBackground();
			Component p = getParent();
			if (p != null) {
				p = p.getParent();
			}
			// p should now be the JTable.
			boolean colorMatch = (back != null) && (p != null)
					&& back.equals(p.getBackground()) && p.isOpaque();
			return !colorMatch && super.isOpaque();
		}

		public void validate() {
		}

		public void revalidate() {
		}

		public void repaint(long tm, int x, int y, int width, int height) {
		}

		public void repaint(Rectangle r) {
		}

		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			if (propertyName == "text") {
				super.firePropertyChange(propertyName, oldValue, newValue);
			}
		}

		public void firePropertyChange(String propertyName, boolean oldValue,
				boolean newValue) {
		}

		protected void setValue(Object value) {
			setText((value == null) ? "" : value.toString());
		}

		//public class UIResource extends CTableHeaderRenderer implements
		//		javax.swing.plaf.UIResource {
		//	private static final long serialVersionUID = 8195473242861516149L;
		//}

	}

	public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {return dataSet;}
	public String getSelectedComponentItem() {return null;}
	public void populateComponent(String action, String editorName, DataSet dataSet) {}
	public void clearComponent(String dv) {}
	public void initializeComponentUI() {}
	public void setToolTipText(String tipText) {}
	public boolean validateComponent(String action, String editorName) {return true;}
	public void setValid(boolean invalid) {}
	public void setProperty(String propertyName, String propertyValue) {}

	public String getString() {
		return null;
	}

}