package webBoltOns.client.components.componentRules;

/*
 * $Id: AutoDocument.java,v 1.1 2007/04/20 19:37:14 paujones2005 Exp $ $Name:  $
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



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

public class AutoDocument extends PlainDocument {

	private static final long serialVersionUID = -545931822871991994L;
	private JComboBox comboBox;
	private ComboBoxModel model;
	private JTextComponent editor;
	private boolean selecting=false, hdePUOnLstF, hitBckSp=false,  hitBkSpSel, isEditing = false;
	KeyListener editorKeyLstnr;
	FocusListener editorFcsLstnr;

	public AutoDocument(){}
	
	public void setControl(final JComboBox comboBox) {
	    this.comboBox = comboBox;
	    comboBox.setEditable(true);
	    model = comboBox.getModel();
	    comboBox.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (!selecting) highlightCompletedText(0);
	      }
	    });
	    comboBox.addPropertyChangeListener(new PropertyChangeListener() {
	      public void propertyChange(PropertyChangeEvent e) {
	        if (e.getPropertyName().equals("editor")) configureEditor((ComboBoxEditor) e.getNewValue());
	        if (e.getPropertyName().equals("model")) model = (ComboBoxModel) e.getNewValue();
	      }
	    });
	    editorKeyLstnr = new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        if (comboBox.isDisplayable()) comboBox.setPopupVisible(true);
	        hitBckSp=false;
	        switch (e.getKeyCode()) {
	          case KeyEvent.VK_BACK_SPACE : hitBckSp=true;
	          hitBkSpSel=editor.getSelectionStart()!=editor.getSelectionEnd();
	          break;
	          case KeyEvent.VK_DELETE : e.consume();
	          comboBox.getToolkit().beep();
	          break;
	        }
	      }
	    };

	    hdePUOnLstF=System.getProperty("java.version").startsWith("1.5");
	    editorFcsLstnr = new FocusAdapter() {
	      public void focusGained(FocusEvent e) {
	        highlightCompletedText(0);
	      }
	      public void focusLost(FocusEvent e) {
	    	 highlightCompletedText(AutoDocument.this.getLength());
	        if (hdePUOnLstF) comboBox.setPopupVisible(false);
	      }
	    };
	    configureEditor(comboBox.getEditor());
	    Object selected = comboBox.getSelectedItem();
	    if (selected!=null) setText(selected.toString());
	    highlightCompletedText(0);
	  }

	  void configureEditor(ComboBoxEditor newEditor) {
	    if (editor != null) {
	      editor.removeKeyListener(editorKeyLstnr);
	      editor.removeFocusListener(editorFcsLstnr);
	    }

	    if (newEditor != null) {
	      editor = (JTextComponent) newEditor.getEditorComponent();
	      editor.addKeyListener(editorKeyLstnr);
	      editor.addFocusListener(editorFcsLstnr);
	      editor.setDocument(this);
	    }
	  }


	  public void remove(int offs, int len) throws BadLocationException {
	    if (selecting) return;
	    if (hitBckSp) {
	      if (offs>0) {
	        if (hitBkSpSel) offs--;
	      } else {
	        comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
	      }
	      highlightCompletedText(offs);
	    } else {
	      super.remove(offs, len);
	    }
	  }


	  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
	  
	    if (selecting) return;
	 
	    super.insertString(offs, str, a);

	    Object item = lookupItem(getText(0, getLength()));
	  
	      if (item != null) {
	        setSelectedItem(item);
	        setText(item.toString());
	      } else {
	        item = comboBox.getSelectedItem();
	        offs = offs-str.length();
	        comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
	      }
	      highlightCompletedText(offs+str.length());
	     
	  }

	  private void setText(String text) {
	    try {
	      // remove all text and insert the completed string
	      super.remove(0, getLength());
	      super.insertString(0, text, null);
	    } catch (BadLocationException e) {
	      throw new RuntimeException(e.toString());
	    }
	  }

	  private void highlightCompletedText(int start) {
	    editor.setCaretPosition(getLength());
	    editor.moveCaretPosition(start);
	  }

	  private void setSelectedItem(Object item) {
		selecting = true;
		Object curent =  model.getSelectedItem();
		if(curent != null && curent.toString().equals(item.toString())) 	
				isEditing = false;
		else
				isEditing = true;	
		
	    selecting = true;
	    model.setSelectedItem(item);
	    selecting = false;
	  }

	  private Object lookupItem(String pattern) {
	    Object selectedItem = model.getSelectedItem();
	    if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
	      return selectedItem;
	    } else {		
	      for (int i=0, n=model.getSize(); i < n; i++) {
	        Object currentItem = model.getElementAt(i);
	        if (currentItem != null && startsWithIgnoreCase(currentItem.toString(), pattern)) {
	          return currentItem;
	        }
	      }
	    }	
	    return null;
	  }
	  
	  
	  public boolean isEditing() { return isEditing; }

	  // checks if str1 starts with str2 - ignores case
	  private boolean startsWithIgnoreCase(String str1, String str2) {
	    return str1.toUpperCase().startsWith(str2.toUpperCase());
	  }}