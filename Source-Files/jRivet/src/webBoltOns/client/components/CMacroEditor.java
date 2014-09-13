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

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import sun.swing.SwingUtilities2;

import com.sun.java.swing.SwingUtilities3;

import webBoltOns.AppletConnector;
import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CMacroEditor extends JTextField implements StandardComponentLayout,
                                     ActionListener {
	 
	 
	private static final long serialVersionUID = -3343059845815975451L;
	private final static String CpyMacro = "[CpyMacro/]";
	private final static String ShwMacro = "[ShwMacro/]"; 
	private final static String ClrMacro = "[ClrMacro/]";

	private final static String Delay = "[Delay-";
	private final static String Tab   = "[Tab  ]";
	private final static String TabBc = "[TabBc]";
	private final static String Home  = "[Home ]";
	private final static String Enter = "[Enter]";
	
	private final static String GoWeb = "[GoWeb]";
	private final static String GoAlt = "[GoAlt]";
	private final static String GoPgm = "[GoPgm]";


	private final static String Password = "[Password]";
	private final static String UserId = "[UserId]";
	private final static String PasswordId = "[PasswordId]";
	private final static String EmailId = "[Email]";

	private JButton play;
	private JButton clear;
	private JButton edit;
	
	private JLabel playing;
	
	protected AppletConnector cnct;
	protected String commitValue;
	protected WindowItem comp;

	private JPanel fieldPanel;
	protected WindowFrame mFrm;
	protected JLabel textBoxLabel;
	private JPopupMenu popup;

	public CMacroEditor() {}


	public void actionPerformed(ActionEvent ae) {

		  if(ae.getActionCommand().equals(CpyMacro)) {
			commitEditing(new String (getText()));
	
			play.setEnabled(false);
			edit.setEnabled(false);
			clear.setEnabled(false);
			playing.setVisible(true);
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					RobotRunner rr = new RobotRunner();				
					rr.runRobot(commitValue);
					playing.setVisible(false);
					play.setEnabled(true);	 
					edit.setEnabled(true);
					clear.setEnabled(true);
				}});
			
		  } else if(ae.getActionCommand().equals(ShwMacro)) {
			 JButton shwMc = (JButton) ae.getSource();
			 popup.show(shwMc, 5 , shwMc.getHeight());
		 } else if(ae.getActionCommand().equals(ClrMacro)) {
			 setText("");
			 commitEditing("");
		 } else  {
			 commitEditing(new String (getText()) + ae.getActionCommand());
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
		setEditable(false);
		
		textBoxLabel = new JLabel(comp.getDescription());
		textBoxLabel.setFont(cnct.headerFont);

		//setHorizontalAlignment(comp.getCellAlignment());
		setColumns(comp.getLength());
		setSelectionColor(Color.cyan);
		setFont(cnct.standardFont);
		textBoxLabel.setFont(cnct.headerFont);
		comp.setComponentObject(this);
		fieldPanel.add(this, BorderLayout.CENTER);
		
		JToolBar tb = new JToolBar();
		tb.setBorder(BorderFactory.createLoweredBevelBorder());
		
		
		edit = cnct.buildFancyButton(cnct.getMsgText("TXT0130"), "clip.gif", cnct.getMsgText("TXT0130"), 'e' );
		edit.setActionCommand(ShwMacro);
		edit.addActionListener(this);
		tb.add(edit, null);
		tb.add(new JToolBar.Separator());
		
		clear = cnct.buildFancyButton(cnct.getMsgText("TXT0131"), "clip.gif", cnct.getMsgText("TXT0131"), 'c' );
		clear.setActionCommand(ClrMacro);
		clear.addActionListener(this);
		tb.add(clear, null);
		tb.add(new JToolBar.Separator());

		
		play  = cnct.buildFancyButton(cnct.getMsgText("TXT0132"), "clip.gif", cnct.getMsgText("TXT0132"), 'r' );
		play.setActionCommand(CpyMacro);
		play.addActionListener(this);
		tb.add(play, null);
		tb.add(new JToolBar.Separator());		
		fieldPanel.add(tb, BorderLayout.SOUTH);
		
		playing = new JLabel(cnct.getImageIcon("run.jpg"));
		playing.setVisible(false);
		tb.add(playing, null);
		
		tb.setPreferredSize(new Dimension(180, 26));
		
		
		popup = new JPopupMenu();
		
		
		JButton pb;
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0133"), "ePreview.gif", cnct.getMsgText("TXT0133"), ' ');
		pb.setActionCommand(UserId);
		pb.addActionListener(this);
		popup.add(pb);
		
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0134"), "ePreview.gif", cnct.getMsgText("TXT0134"), ' ');
		pb.setActionCommand(PasswordId);
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0135"), "ePreview.gif", cnct.getMsgText("TXT0135"), ' ');
		pb.setActionCommand(Password);
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0136"), "ePreview.gif", cnct.getMsgText("TXT0136"), ' ');
		pb.setActionCommand(EmailId);
		pb.addActionListener(this);
		popup.add(pb);

		
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0137"), "iNext.gif", cnct.getMsgText("TXT0137"), ' ');
		pb.setActionCommand(Tab);
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0146"), "iNext.gif", cnct.getMsgText("TXT0146"), ' ');
		pb.setActionCommand(TabBc);
		pb.addActionListener(this);
		popup.add(pb);	
		
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0147"), "iNext.gif", cnct.getMsgText("TXT0147"), ' ');
		pb.setActionCommand(Home);
		pb.addActionListener(this);
		popup.add(pb);
		
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0142"), "iNext.gif", cnct.getMsgText("TXT0142"), ' ');
		pb.setActionCommand(Enter);
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0138"), "Mdot.gif", cnct.getMsgText("TXT0137"), ' ');
		pb.setActionCommand(Delay + "02]");
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0139"), "Mdot.gif", cnct.getMsgText("TXT0139"), ' ');
		pb.setActionCommand(Delay + "05]");
		pb.addActionListener(this);
		popup.add(pb);
		
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0140"), "Mdot.gif", cnct.getMsgText("TXT0140"), ' ');
		pb.setActionCommand(Delay + "10]");
		pb.addActionListener(this);
		popup.add(pb);
		
		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0141"), "Mdot.gif", cnct.getMsgText("TXT0141"), ' ');
		pb.setActionCommand(Delay + "20]");
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0143"), "script.gif", cnct.getMsgText("TXT0143"), ' ');
		pb.setActionCommand(GoWeb);
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0144"), "script.gif",cnct.getMsgText("TXT0144") , ' ');
		pb.setActionCommand(GoAlt);
		pb.addActionListener(this);
		popup.add(pb);

		pb = cnct.buildFancyButton(cnct.getMsgText("TXT0145"), "script.gif", cnct.getMsgText("TXT0145"), ' ');
		pb.setActionCommand(GoPgm);
		pb.addActionListener(this);
		popup.add(pb);
		
		
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
			((CPanelContainer) parentItem.getComponentObject()).addc(playing);
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
		
		commitValue = new String (getText());		
		 
	}

	
	public String getSelectedComponentItem() { return null; }
 
	public void initializeComponentUI() {}
	

	public boolean locateCursor() {return false; }

	
	public void populateComponent(String action, String editorName, DataSet dataSet) {
		commitEditing(cnct.decrypt(dataSet.getStringField(editorName)));
	}


	public DataSet populateDataSet(String action, String editorName, DataSet dataSet) {
		commitEditing(new String (getText()));		
		dataSet.putStringField(editorName, cnct.encrypt(commitValue));
		return dataSet;
	}

	
	public String getString () {
		commitEditing(new String (getText()));		
		return commitValue;
	}

	protected void revertEditing() {
		if (commitValue != null)
			setText(commitValue);
		else
			setText(null);
	}
	
	public void setProperty(String propertyName, String propertyValue) {} 
	public void setValid(boolean invalid) { }
	public boolean validateComponent(String action, String editorName) {return true; }
	 
	

	class RobotRunner {

		private Robot robot;

		public RobotRunner()  {
			try {
				this.robot = new Robot();
			} catch ( AWTException e) {
				e.printStackTrace();	
			}

		}


		public void runRobot(String  i) {
			
			
			final int notFound = 999999999;
			
			int	d = notFound, t = notFound, e = notFound, b = notFound, w = notFound, 
			    a = notFound, p = notFound, r = notFound, h = notFound;;

			i = i.replace(UserId,     mFrm.getString("b9"));
			i = i.replace(Password,   mFrm.getString("c8"));
			i = i.replace(PasswordId, mFrm.getString("PasswordID"));
			i = i.replace(EmailId,    mFrm.getString("m8"));
			
			if (i.indexOf(Delay)   != -1)  d = i.indexOf(Delay);  
			if (i.indexOf(Tab)     != -1)  t = i.indexOf(Tab);
			if (i.indexOf(TabBc)   != -1)  r = i.indexOf(TabBc);			
			if (i.indexOf(Enter)   != -1)  e = i.indexOf(Enter);
			if (i.indexOf(Home)    != -1)  h = i.indexOf(Home);
			if (i.indexOf(GoWeb)   != -1)  w = i.indexOf(GoWeb);
			if (i.indexOf(GoAlt)   != -1)  a = i.indexOf(GoAlt);
			if (i.indexOf(GoPgm)   != -1)  p = i.indexOf(GoPgm);

			
			// do Delay
			if(d != notFound  &&  d<t  &&  d<e  &&  d<w  &&  d<a  &&  d<p   &&   d<r  &&  d<h) {
				if(d > 0) type(i.substring(0, d));
				delay(i.substring(d+7, d+9));
				runRobot(i.substring(d+10, i.length()));
				return;
			}
			
			
			// do tab
			if(t != notFound && t<e  &&  t<w  &&  t<a  &&  t<p  && t<r  &&  t<h) {
				if(t > 0) type(i.substring(0, t));
				System.out.println(Tab);
				doType(KeyEvent.VK_TAB);
				runRobot(i.substring(t+7,i.length()));
				return;
			}
			
			
			// do enter
			if(e != notFound  &&  e<w  &&  e<a  &&  e<p  &&  e<r  &&  e<h) {
				if(e > 0) type(i.substring(0, e));
				System.out.println(Enter);
				doType(KeyEvent.VK_ENTER);
				runRobot(i.substring(e+7,i.length()));
				return;	
			}
			

			// do Web Site
			if(w != notFound && w<a  &&  w<p  && w<r  &&  w<h) {
				if(w > 0) type(i.substring(0, w));
				System.out.println(GoWeb);
				String document = mFrm.getString("n7");
				if(! document.startsWith("http"))
					cnct.showWebDocument("http://" + document);
				else
					cnct.showWebDocument(document);				

				runRobot(i.substring(w+7, i.length()));
				return;	
			}

			
			// do Alt Site
			if(a != notFound && a<p  && a<r  &&   a<h) {
				if(a > 0) type(i.substring(0, a));
				System.out.println(GoAlt);
				String document = mFrm.getString("i2");
				if(! document.startsWith("http"))
					cnct.showWebDocument("http://" + document);
				else
					cnct.showWebDocument(document);				

				runRobot(i.substring(a+7, i.length()));
				return;	
			}


			// do Pgm
			if(p != notFound  && p<r  &&  p<h) {
				if(p > 0) type(i.substring(0, p));
				System.out.println(GoPgm);
				mFrm.runProgram(mFrm.getString("p5"));
				runRobot(i.substring(p+7, i.length()));
				return;	
			}

			// do back tab
			if(r != notFound  && r<h) {
				if(r > 0) type(i.substring(0, r));
				doType(KeyEvent.VK_SHIFT, KeyEvent.VK_TAB);
				runRobot(i.substring(r+7, i.length()));
				return;	
			}

			
			// do home
			if(h != notFound) {
				if(h > 0) type(i.substring(0, h));
				doType(KeyEvent.VK_HOME);
				runRobot(i.substring(h+7, i.length()));
				return;	
			}
			
			//just type it!
			type(i);
		}
			
						
		public void type(CharSequence characters) {	
			System.out.println("chr");
			int length = characters.length();
			for (int i = 0; i < length; i++) {
				char character = characters.charAt(i);
				type(character);
			}
		}
		
		
		public void delay(String d) {
			System.out.println("delay-"+ d);
			robot.delay((Integer.parseInt(d) * 1000));
		}

		
		public void type(char character) {

			switch (character) {
			case 'a': doType(KeyEvent.VK_A); break;
			case 'b': doType(KeyEvent.VK_B); break;
			case 'c': doType(KeyEvent.VK_C); break;
			case 'd': doType(KeyEvent.VK_D); break;
			case 'e': doType(KeyEvent.VK_E); break;
			case 'f': doType(KeyEvent.VK_F); break;
			case 'g': doType(KeyEvent.VK_G); break;
			case 'h': doType(KeyEvent.VK_H); break;
			case 'i': doType(KeyEvent.VK_I); break;
			case 'j': doType(KeyEvent.VK_J); break;
			case 'k': doType(KeyEvent.VK_K); break;
			case 'l': doType(KeyEvent.VK_L); break;
			case 'm': doType(KeyEvent.VK_M); break;
			case 'n': doType(KeyEvent.VK_N); break;
			case 'o': doType(KeyEvent.VK_O); break;
			case 'p': doType(KeyEvent.VK_P); break;
			case 'q': doType(KeyEvent.VK_Q); break;
			case 'r': doType(KeyEvent.VK_R); break;
			case 's': doType(KeyEvent.VK_S); break;
			case 't': doType(KeyEvent.VK_T); break;
			case 'u': doType(KeyEvent.VK_U); break;
			case 'v': doType(KeyEvent.VK_V); break;
			case 'w': doType(KeyEvent.VK_W); break;
			case 'x': doType(KeyEvent.VK_X); break;
			case 'y': doType(KeyEvent.VK_Y); break;
			case 'z': doType(KeyEvent.VK_Z); break;
			
			case 'A': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_A); break;
			case 'B': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_B); break;
			case 'C': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_C); break;
			case 'D': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_D); break;
			case 'E': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_E); break;
			case 'F': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_F); break;
			case 'G': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_G); break;
			case 'H': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_H); break;
			case 'I': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_I); break;
			case 'J': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_J); break;
			case 'K': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_K); break;
			case 'L': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_L); break;
			case 'M': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_M); break;
			case 'N': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_N); break;
			case 'O': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_O); break;
			case 'P': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_P); break;
			case 'Q': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Q); break;
			case 'R': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_R); break;
			case 'S': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_S); break;
			case 'T': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_T); break;
			case 'U': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_U); break;
			case 'V': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_V); break;
			case 'W': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_W); break;
			case 'X': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_X); break;
			case 'Y': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Y); break;
			case 'Z': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Z); break;
			
			case '`': doType(KeyEvent.VK_BACK_QUOTE); break;
			case '0': doType(KeyEvent.VK_0); break;
			case '1': doType(KeyEvent.VK_1); break;
			case '2': doType(KeyEvent.VK_2); break;
			case '3': doType(KeyEvent.VK_3); break;
			case '4': doType(KeyEvent.VK_4); break;
			case '5': doType(KeyEvent.VK_5); break;
			case '6': doType(KeyEvent.VK_6); break;
			case '7': doType(KeyEvent.VK_7); break;
			case '8': doType(KeyEvent.VK_8); break;
			case '9': doType(KeyEvent.VK_9); break;
			case '-': doType(KeyEvent.VK_MINUS); break;
			case '=': doType(KeyEvent.VK_EQUALS); break;
			
			case '~': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE); break;
			case '!': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_1);  break;
			case '@': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_2);  break;
			case '#': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_3);  break;
			case '$': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_4);  break;
			case '%': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_5);  break;
			case '^': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_6);  break;
			case '&': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_7);  break;
			case '*': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_8);  break;
			case '(': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_9);  break;
			case ')': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_0);  break;
			case '_': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_MINUS);  break;
			case '+': doType(KeyEvent.VK_SHIFT, KeyEvent.VK_EQUALS); break;
			
			case '\t': doType(KeyEvent.VK_TAB); break;			
			case '\n': doType(KeyEvent.VK_ENTER); break;
			case '[':  doType(KeyEvent.VK_OPEN_BRACKET); break;
			case ']':  doType(KeyEvent.VK_CLOSE_BRACKET); break;
			
			case '\\': doType(KeyEvent.VK_BACK_SLASH); break;
			case '{':  doType(KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET); break;
			case '}':  doType(KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET); break;
			case '|':  doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH); break;
			case ';':  doType(KeyEvent.VK_SEMICOLON); break;
			case ':':  doType(KeyEvent.VK_SHIFT,KeyEvent.VK_SEMICOLON); break;
			case '\'': doType(KeyEvent.VK_QUOTE); break;
			case '"':  doType(KeyEvent.VK_SHIFT, KeyEvent.VK_QUOTE); break;
			case ',':  doType(KeyEvent.VK_COMMA); break;
			case '<':  doType(KeyEvent.VK_SHIFT, KeyEvent.VK_COMMA); break;
			case '.':  doType(KeyEvent.VK_PERIOD); break;
			case '>':  doType(KeyEvent.VK_SHIFT, KeyEvent.VK_PERIOD); break;
			case '/':  doType(KeyEvent.VK_SLASH); break;
			case '?':  doType(KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH); break;
			case ' ':  doType(KeyEvent.VK_SPACE); break;
			
			default:
				throw new IllegalArgumentException("Cannot type character " + character);
			}
		}

		private void doType(int... keyCodes) {
			doType(keyCodes, 0, keyCodes.length);
		}

		private void doType(int[] keyCodes, int offset, int length) {
			if (length == 0) {
				return;
			}
			robot.keyPress(keyCodes[offset]);
			doType(keyCodes, offset + 1, length - 1);
			robot.keyRelease(keyCodes[offset]);
		}

	}

}
