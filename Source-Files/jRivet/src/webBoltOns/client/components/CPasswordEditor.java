package webBoltOns.client.components;

/*
 * $Id: CPasswordField.java,v 1.1 2007/04/20 19:37:20 paujones2005 Exp $ $Name:  $
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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.clientUtil.PasswordGntr;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CPasswordEditor extends JPasswordField implements StandardComponentLayout, 
                                     ActionListener, KeyListener {

	class PWAnalysis {
		protected int caps, ccaps, ccharacters, cdigits, characters;
		protected final int CRITERIA = 5;
		protected int csymbols, digits, lengthStr;
		protected String pwd;
		protected int strength, symbols;

		public PWAnalysis() {
			 csymbols    = 0;
			 ccharacters = 0;
			 ccaps       = 0;
			 cdigits     = 0;
			 symbols	 = 0;
			 characters  = 0;
			 caps        = 0;
			 digits      = 0;
			 lengthStr   = 0;
			 strength    = 0;
			 pwd         = "";
		}
 
		public int analyse(String password) {
			pwd	 = password;
			for(int i = 0; i < pwd.length(); i++)
			{
				if ( Character.isDigit(pwd.charAt(i)) )  
					cdigits++;
				 

				if ( Character.isLowerCase(pwd.charAt(i)))
					ccharacters++;
				  

				if ( Character.isUpperCase(pwd.charAt(i)) )
					ccaps++;
				
			}

			strength  = checkSymbols();
			strength += checkDigits();
			strength += checkCaps();
			strength += checkChars();
			strength += checkLength();
			return strength;
		}
		 
		protected int checkCaps() {
			int average = 0;

			if (pwd.length() > 0) 
				average = ((ccaps * 100) / pwd.length());
			 
			if (20 <= average) 
				caps = getMaxCriteriaPerShare();
			else if (ccaps > 0)
				caps = getMaxCriteriaPerShare() - 10;

			return caps;
		}
	 
		protected int checkChars() {
			int average = 0;
			if (pwd.length() > 0)		
				average = ((ccharacters * 100) / pwd.length());

			if (20 <= average)
				characters = getMaxCriteriaPerShare();
			else if (ccharacters > 0)				 
				characters = getMaxCriteriaPerShare() - 10;

			return characters;
		}
 
		protected int checkDigits() {
			int average = 0;
			if (pwd.length() > 0)
				average = ((cdigits * 100) / pwd.length());
			
			if (20 <= average)
				digits = getMaxCriteriaPerShare();
			
			else if (cdigits > 0)	
				digits = getMaxCriteriaPerShare() - 10;
				
			return digits;
		}
 
		
		protected int checkLength() {
			if ((pwd.length() >= 7) && (pwd.length() <= 25))
				lengthStr = getMaxCriteriaPerShare();
				
			return lengthStr;
		}

		 
		protected int checkSymbols() {
			int average   = 0;
			int from      = 0;
			char[] syms   = {'~', '!', '#', '%', '^', '(', ')', '_', '+', '-', '=', '{', '}', '|', '[', ']', ';', '<', '>', ',', '.', '/'};

			for(int i = 0; i < pwd.length(); i++){
				for(int j = 0; j < syms.length; j++){
					if (pwd.indexOf(syms[j], from)!=-1){
						from = pwd.indexOf(syms[j], from) + 1;
						csymbols++;
						break;
					}
				}
			}

			if (pwd.length() > 0) 
				average = ((csymbols * 100) / pwd.length());
			 

			if (10 <= average) 
				symbols = getMaxCriteriaPerShare();
			
			else if (csymbols > 0)
				symbols = getMaxCriteriaPerShare() - 10;
		
			return symbols;
		}

		public int getCapsStrength()       { return caps;		}

		public int getCharactersStrength() { return characters; }

		public int getDigitsStrength()     { return digits;     }

		public int getLengthStrength()     { return lengthStr;  }

		protected int getMaxCriteriaPerShare() { return (100 / CRITERIA); }
		 
		public int getPasswordStrength()   { return strength;   }
 
		public int getSymbolsStrength()    { return symbols;    }

	 
		public void reinitialize() {
			 csymbols    = 0;
			 ccharacters = 0;
			 ccaps       = 0;
			 cdigits     = 0;
			 symbols	 = 0;
			 characters  = 0;
			 caps        = 0;
			 digits      = 0;
			 lengthStr   = 0;
			 strength    = 0;
			 pwd         = "";
		}
	}
	
	
	private static final long serialVersionUID = -3343059845815975451L;
	
	private final static String CrtPW = "[CrtPW/]";
	private final static String GenPW = "[GenPW/]";
	private final static String ShowPW = "[ShowPW/]";
	private final static String ClipPW = "[ClipPW/]";
	
	private final static String LETTERS =  "PWD0001";
	private final static String LOWERCASE_LETTERS = "PWD0002";
	private final static String LOWERCASE_LETTERS_NUMBERS = "PWD0003";
	private final static String NUMBERS_LETTERS =  "PWD0004";
	private final static String PRINTABLE =  "PWD0005";
	private final static String UPPERCASE_LETTERS = "PWD0006";
	private final static String UPPERCASE_LETTERS_NUMBERS = "PWD0007";
	
	private ButtonGroup bgroup;
	protected AppletConnector cnct;
	protected String commitValue;
	protected WindowItem comp;
	private char echo;
	
	private PWAnalysis anlys = new PWAnalysis(); 
	private JProgressBar bar = new JProgressBar(0, 92);
	private JPanel fieldPanel;
	protected JButton genPW, shwPW, crtPW;
	private PasswordGntr gntr = new PasswordGntr(PasswordGntr.LETTERS);
	private JSpinner len;
	protected WindowFrame mFrm;
	private Hashtable<String, ButtonModel> model;
	private JPopupMenu popup;
	
	protected JLabel textBoxLabel;

	
	public CPasswordEditor() {}
	
	
	public void actionPerformed(ActionEvent ae) {
	  
		if(ae.getActionCommand().equals(ShowPW)) {
			if(getEchoChar() == echo)
				setEchoChar((char)0);
			else 
				setEchoChar(echo);
		
		} else if(ae.getActionCommand().equals(GenPW)) {
			popup.show(genPW, 5 , genPW.getHeight() );		 
		

		} else if(ae.getActionCommand().equals(ClipPW)) {		
			commitEditing(new String (getPassword()));
			mFrm.getMenuObject().copyToClipTimer("PssWrd", commitValue);

			
		} else if(ae.getActionCommand().equals(CrtPW)) {
			setEchoChar((char)0);
			
			if(bgroup.getSelection().getActionCommand().equals(NUMBERS_LETTERS))
				gntr.setAlphabet(PasswordGntr.NUMBERS_LETTERS);
			else if(bgroup.getSelection().getActionCommand().equals(LETTERS))
				gntr.setAlphabet(PasswordGntr.LETTERS);
			else if(bgroup.getSelection().getActionCommand().equals(LOWERCASE_LETTERS))
				gntr.setAlphabet(PasswordGntr.LOWERCASE_LETTERS);
			else if(bgroup.getSelection().getActionCommand().equals(LOWERCASE_LETTERS_NUMBERS))
				gntr.setAlphabet(PasswordGntr.LOWERCASE_LETTERS_NUMBERS);	
			else if(bgroup.getSelection().getActionCommand().equals(UPPERCASE_LETTERS))
				gntr.setAlphabet(PasswordGntr.UPPERCASE_LETTERS);
			else if(bgroup.getSelection().getActionCommand().equals(UPPERCASE_LETTERS_NUMBERS))
				gntr.setAlphabet(PasswordGntr.UPPERCASE_LETTERS_NUMBERS);
			else if(bgroup.getSelection().getActionCommand().equals(PRINTABLE))
				gntr.setAlphabet(PasswordGntr.PRINTABLE);
			
			commitEditing(gntr.getPass((Integer)len.getValue()));
			popup.setVisible(false);
		}	
			
	}
	

	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		mFrm = mainFrame;
		comp = thisItem;
		cnct = mainFrame.getAppletConnector();
		fieldPanel = new JPanel();
		fieldPanel.setLayout(new BorderLayout());
		fieldPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(165, 163, 151)));

		setName(Integer.toString(comp.getObjectHL()));
		setFont(cnct.standardFont);
		setBackground(Color.WHITE);
		
		textBoxLabel = new JLabel(comp.getDescription());
		textBoxLabel.setFont(cnct.headerFont);

		setHorizontalAlignment(comp.getCellAlignment());
		setColumns(comp.getLength());
		setSelectionColor(Color.cyan);
		setFont(cnct.standardFont);
		textBoxLabel.setFont(cnct.headerFont);
		comp.setComponentObject(this);
		fieldPanel.add(this, BorderLayout.WEST);
		
		shwPW = cnct.buildFancyButton(null, "idots.gif", cnct.getMsgText("TXT0110"), 'c' );
		shwPW.setActionCommand(ShowPW);
		shwPW.addActionListener(this);
		fieldPanel.add(shwPW, BorderLayout.CENTER);
		
		genPW = cnct.buildFancyButton(null, "key.gif", cnct.getMsgText("TXT0111"), 'p' );
		genPW.setActionCommand(GenPW);
		genPW.addActionListener(this);
		fieldPanel.add(genPW, BorderLayout.EAST);
		echo = getEchoChar();

		bar.setValue(0);
		bar.setStringPainted(true);
		fieldPanel.add(bar, BorderLayout.SOUTH);
		bgroup = new ButtonGroup();
		popup = new JPopupMenu();
		model = new Hashtable<String, ButtonModel>();	
		
		JRadioButton b;
		b = new JRadioButton(cnct.getMsgText(PRINTABLE));
		b.setActionCommand(PRINTABLE);
		model.put(PRINTABLE, b.getModel());
		bgroup.add(b); 
		popup.add(b);
	 
		
		b = new JRadioButton(cnct.getMsgText(NUMBERS_LETTERS));
		b.setActionCommand(NUMBERS_LETTERS);
		model.put(NUMBERS_LETTERS, b.getModel());
		bgroup.add(b);
		bgroup.setSelected(b.getModel(), true);
		popup.add(b); 
		
		b = new JRadioButton(cnct.getMsgText(LETTERS));
		b.setActionCommand(LETTERS);
		model.put(LETTERS, b.getModel());
		bgroup.add(b);
		popup.add(b);
		
		b = new JRadioButton(cnct.getMsgText(LOWERCASE_LETTERS_NUMBERS));
		b.setActionCommand(LOWERCASE_LETTERS_NUMBERS);
		model.put(LOWERCASE_LETTERS_NUMBERS, b.getModel());
		bgroup.add(b);
		popup.add(b);
		
		b = new JRadioButton(cnct.getMsgText(LOWERCASE_LETTERS));
		b.setActionCommand(LOWERCASE_LETTERS);
		model.put(LOWERCASE_LETTERS, b.getModel());
		bgroup.add(b);
		popup.add(b);
		

		b = new JRadioButton(cnct.getMsgText(UPPERCASE_LETTERS_NUMBERS));
		b.setActionCommand(UPPERCASE_LETTERS_NUMBERS);
		model.put(UPPERCASE_LETTERS_NUMBERS, b.getModel());
		bgroup.add(b);
		popup.add(b);
			
		
		b = new JRadioButton(cnct.getMsgText(UPPERCASE_LETTERS));
		b.setActionCommand(UPPERCASE_LETTERS);
		model.put(UPPERCASE_LETTERS, b.getModel());
		bgroup.add(b);
		popup.add(b);
 
				
 		len = new JSpinner(new SpinnerNumberModel(8, 4, 256, 1));
 		len.setPreferredSize(new Dimension(100, 18));
 		JPanel spn = new JPanel();
 		spn.add(new JLabel(" Length:    "));
 		spn.add(len);
		popup.add(spn);
 		
		crtPW = cnct.buildFancyButton(cnct.getMsgText("TXT0111"), "key.gif", cnct.getMsgText("TXT0111"), 'p' );
		crtPW.setActionCommand(CrtPW);
		crtPW.addActionListener(this);
		popup.add(crtPW);
		
		addKeyListener(this);
		
		if (comp.getPosition().equals(WindowItem.RIGHT)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(textBoxLabel, 4);
			((CPanelContainer) parentItem.getComponentObject()).addRight(fieldPanel, 5);
		} else if (comp.getPosition().equals(WindowItem.CENTER)) {
			((CPanelContainer) parentItem.getComponentObject()).addRight(textBoxLabel, 2);
			((CPanelContainer) parentItem.getComponentObject()).addRight(fieldPanel, 3);
		} else if (comp.getPosition().equals(WindowItem.LEFT)) {
			((CPanelContainer) parentItem.getComponentObject()).addLeft(textBoxLabel, 0);
			((CPanelContainer) parentItem.getComponentObject()).addRight(fieldPanel, 1);
		} else {
			((CPanelContainer) parentItem.getComponentObject()).addc(textBoxLabel);
			((CPanelContainer) parentItem.getComponentObject()).addc(fieldPanel);
		}
	}

	public void clearComponent(String defaultValue) {
		commitEditing(null);
		updateUI();
	}
	
	protected void commitEditing(String value) {
		if (value != null)
			setText(value);
		else
			setText(null);
		
		commitValue = new String (getPassword());		
		anlys.reinitialize();		
		bar.setValue(anlys.analyse(new String (getPassword())));
	}

	
	public String getSelectedComponentItem() { return null; }
 
	public void initializeComponentUI() {}
	

	public boolean locateCursor() {return false; }

	
	public void populateComponent(String action, String editorName, DataSet dataSet) {
		commitEditing(cnct.decrypt(dataSet.getStringField(editorName)));
	}


	public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {
		commitEditing(new String (getPassword()));		
		dataSet.putStringField(editorName, cnct.encrypt(commitValue));
		return dataSet;
	}

	
	public String getString () {
		commitEditing(new String (getPassword()));		
		if(commitValue != null) return commitValue;
		return "";
	}

	protected void revertEditing() {
		if (commitValue != null)
			setText(commitValue);
		else
			setText(null);
	}
	
	public void keyReleased(KeyEvent arg0) {
		anlys.reinitialize();		
		bar.setValue(anlys.analyse(new String (getPassword())));
	}
	
	public void setProperty(String propertyName, String propertyValue) {} 
	public void setValid(boolean invalid) { }
	public boolean validateComponent(String action, String editorName) {return true; }
	public void keyPressed(KeyEvent arg0) { }
	public void keyTyped(KeyEvent arg0) { }
	 
}
