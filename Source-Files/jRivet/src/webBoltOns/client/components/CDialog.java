package webBoltOns.client.components;

/*
 * $Id: CDialog.java,v 1.1 2007/04/20 19:37:20 paujones2005 Exp $ $Name:  $
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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import webBoltOns.AppletConnector;
import webBoltOns.client.clientUtil.FrameBorder;
import webBoltOns.client.clientUtil.RdBorder;
import webBoltOns.client.components.CTextHTMLField.TextMonitor;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.client.components.layoutManagers.GridFlowLayout;
import webBoltOns.client.components.layoutManagers.GridFlowParm;
import webBoltOns.client.components.layoutManagers.StackedFlowLayout;
import webBoltOns.dataContol.DataSet;

public class CDialog extends JDialog implements ActionListener, KeyListener,
		WindowListener, MouseListener, ChangeListener, MouseMotionListener {

	private static final long serialVersionUID = 2523387830351067769L;
	private final String dp_dayText[] = { "Su", "Mo", "Tu", "We", "Th", "Fr",
			"Sa" };
	private final int dp_dom[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
			31 };
	private final String f_Sizes[] = { "8", "10", "11", "12", "14", "16", "18",
			"20", "24", "30", "36", "40", "48", "60", "72" };

	private final String dp_monthText[] = { "MTH0001", "MTH0002", "MTH0003",
			"MTH0004", "MTH0005", "MTH0006", "MTH0006", "MTH0008", "MTH0009", "MTH0010",
			"MTH0011", "MTH0012" };

	
	private final String cc_num[] = { "C", "MR", "M-", "M+", "7", "8", "9",
			"/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+" };
	
	
	private final String k_val[] = { "C", "*", "/", "+", "-", ".", "=", "=",
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

	private final int k_key[] = {
			KeyEvent.VK_C, 106, 111, 107, 109, KeyEvent.VK_PERIOD, KeyEvent.VK_ENTER, KeyEvent.VK_EQUALS, 
			KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_3, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9,
			KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, 
			KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9,};
	
	private AppletConnector cnct;
	private ButtonGroup bg;
	private JCheckBox isChkCase, isChkWord, isBld, isItlc, isUndrlne, isST, isSS;
	private JSpinner year;
	private JLabel wkday[];
	private int yy, mm, dd;
	private JTextPane ff_SampleArea;
	private JTextField mtday[] = new JTextField[42], cckey[] = new JTextField[20];
	private JRadioButton s_rdDown, s_rdUp;
	private String rtnDate = "";
	private boolean rtnBln;
	private JComboBox months, fntNmCh, fntSzCh;
	private JButton lt, rt, td, fnd, rplc, rplcAll, fColor;
	private TextMonitor textArea;
	private JTextField fndT, rplcT, un;
	private CDialog wrng;
	private SimpleAttributeSet outAttrbts;
	private DataSet login;
	private JPasswordField pw;

	private JTextField calc;
	private String scalc1, scalc2;
	private double dReg1, dReg2, dMem;
	private String sOperator;
	private boolean isFixReg;
	private JComponent cmp;
	private JButton exit;

	public CDialog(Frame f, AppletConnector appletConnector) {
		super(f, true);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		cnct = appletConnector;
		addWindowListener(this);
		addMouseMotionListener(this);
		
		exit = cnct.buildFancyButton(null, "eDelete.gif", "Close",' ');
		exit.setActionCommand("N");
		exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		exit.addActionListener(this);
	}

	
	
	private void displayDialog(JComponent c, JPanel main) {
		
		if(c == null)
			main.setBorder(BorderFactory.createCompoundBorder(new RdBorder(),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		else
			main.setBorder(BorderFactory.createCompoundBorder(new FrameBorder(),
					BorderFactory.createEmptyBorder(0, 0, 5, 5)));
			
		getContentPane().add(main);
		pack();
		
		Point p = getComponentPoint(c);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		if (p == null) {
			setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);
		} else {
			int x = p.x + 17; 
			int y = p.y + c.getHeight() + 1;
			if(x + getWidth() >= d.width) x = d.width - getWidth() - 5;
			if(y + getHeight() >= d.height) y = d.height - getHeight() - 25;
			setLocation(x, y);
		}
		cmp = c;
		setVisible(true);
	}

 
	
    public  void dispose(){
    	super.dispose();
    	if(cmp != null && cmp instanceof StandardComponentLayout) {
    		SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					cmp.requestFocus();
				}} );	
    	}
    }

    
	private Point getComponentPoint(Component c){
		try {
			return c.getLocationOnScreen();
		} catch (Exception e) {
			return null;	
		}
	}
	
	
	private int findNext(boolean doReplace, boolean matchCase,
			boolean searchUp, boolean matchWords, final TextMonitor textArea,
			JTextField find, JTextField replace, boolean showWarnings) {

		int pos = textArea.getCaretPosition();
		int searchIndex = -1;
		String searchData = "";
		String key = "";
		String replaceData = "";
		try {
			Document searchDoc = textArea.getDocument();
			if (searchUp)
				searchData = searchDoc.getText(0, pos);
			else
				searchData = searchDoc.getText(pos, searchDoc.getLength() - pos);
			searchIndex = pos;
		} catch (BadLocationException e) {
			return -1;
		}

		if (searchData.equals(""))
			return -1;

		key = find.getText();
		if (key.equals(""))
			return -1;

		if (!matchCase) {
			searchData = searchData.toLowerCase();
			key = key.toLowerCase();
		}

		if (matchWords) {
			for (int k = 0; k < Utils.WORD_SEPARATORS.length; k++) {
				if (key.indexOf(Utils.WORD_SEPARATORS[k]) >= 0)
					return -1;
			}
		}

		if (doReplace) {
			replaceData = replace.getText();
			if (replaceData == null || replaceData.equals(""))
				return -1;
		}

		int xStart = -1;
		int xFinish = -1;
		while (true) {
			if (searchUp)
				xStart = searchData.lastIndexOf(key, pos - 1);
			else
				xStart = searchData.indexOf(key, pos - searchIndex);

			if (xStart < 0) {
				return 0;
			}

			xFinish = xStart + key.length();
			if (matchWords) {
				boolean s1 = xStart > 0;
				boolean b1 = s1
						&& !Utils.isSeparator(searchData.charAt(xStart - 1));
				boolean s2 = xFinish < searchData.length();
				boolean b2 = s2
						&& !Utils.isSeparator(searchData.charAt(xFinish));
				if (b1 || b2) {
					if (searchUp && s1) {
						pos = xStart;
						continue;
					}
					if (!searchUp && s2) {
						pos = xFinish;
						continue;
					}

					if (showWarnings)
						return 0;
				}
			}
			break;
		}

		if (!searchUp) {
			xStart += searchIndex;
			xFinish += searchIndex;
		}

		if (doReplace) {
			setSelection(textArea, xStart, xFinish, searchUp);
			textArea.replaceSelection(replaceData);
			setSelection(textArea, xStart, xStart + replaceData.length(),
					searchUp);
			searchIndex = -1;
		} else {
			textArea.setMonitor(xStart, xFinish);
			if (searchUp)
				textArea.setCaretPosition(xStart);
			else
				textArea.setCaretPosition(xFinish);

		}
		
		return 1;
	}

	
	
	public Color showColorPickerDialog(Color color) {
		 
		getContentPane().removeAll();
		final JColorChooser colorChooser = new JColorChooser(color);
		JPanel north = new JPanel(new BorderLayout());
		north.add(BorderLayout.WEST, new JLabel(
							"<html><p><font color=blue size=+1>   " + cnct.getMsgText("TXT0002") + "   </font></html>"));

		north.add(BorderLayout.EAST, exit);
		JPanel center = new JPanel(new GridFlowLayout(10, 10));

		center.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		center.add(colorChooser, new GridFlowParm(true, 0));
		JPanel south = new JPanel(new FlowLayout());

		CButton ok = new CButton(cnct.getMsgText("TXT0001"));
		ok.setMnemonic('O');
		ok.setActionCommand("Y");
		ok.addActionListener(this);
		ok.addKeyListener(this);

		south.add(ok);
		JPanel main = new JPanel(new BorderLayout());
		main.add(BorderLayout.NORTH, north);
		main.add(BorderLayout.CENTER, center);
		main.add(BorderLayout.SOUTH, south);

		displayDialog(null, main);

		if (rtnBln)
			return colorChooser.getColor();
		else
			return color;
	}

	public String showDatePickerDialog(String prmdte, JComponent c) {

		getContentPane().removeAll();
		String yymmdd = new SimpleDateFormat("yyyyMMdd").format(new Date());

		if (DataSet.isDate(prmdte))
			yymmdd = DataSet.formatDateField(prmdte, "yyyyMMdd");
		
		rtnDate = yymmdd;

		yy = Integer.parseInt(yymmdd.substring(0, 4));
		mm = Integer.parseInt(yymmdd.substring(4, 6));
		dd = Integer.parseInt(yymmdd.substring(6, 8));
		lt = cnct.buildFancyButton(null, "left.gif", null, ' ');
		rt = cnct.buildFancyButton(null, "right.gif", null, ' ');
		td = cnct.buildFancyButton(cnct.getMsgText("TXT0004"), null, null, ' ');

		JPanel tPan = new JPanel(new BorderLayout());
		tPan.add(BorderLayout.WEST, new JLabel(
				"<html><p><font color=blue size=+1>   " + cnct.getMsgText("TXT0003") + "   </font></html>"));
		tPan.add(BorderLayout.EAST, exit);
		JPanel dtePan = new JPanel(new GridFlowLayout(1, 1));
		dtePan.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(Color.lightGray), BorderFactory
				.createEmptyBorder(10, 10, 10, 10)));

		wkday = new JLabel[7];
		months = new JComboBox();
		months.setBackground(Color.white);
		months.setPreferredSize(new Dimension(100, 19));

		year = new JSpinner(new SpinnerNumberModel(1000, 1000, 9999, 1));
		year.setValue(new Integer(yy));
		year.setEditor(new JSpinner.NumberEditor(year, "####"));

		for (int m = 0; m < 12; m++)
			months.addItem(cnct.getMsgText(dp_monthText[m]));
		months.setSelectedIndex(mm - 1);

		months.setActionCommand("date");
		months.addActionListener(this);
		year.setName("date");
		year.addChangeListener(this);
		rt.setActionCommand("date+");
		rt.addActionListener(this);
		lt.setActionCommand("date-");
		lt.addActionListener(this);
		td.setActionCommand("date=");
		td.addActionListener(this);

		for (int d = 0; d < 7; d++) {
			wkday[d] = new JLabel();
			wkday[d].setText(dp_dayText[d]);
			wkday[d].setForeground(Color.BLUE);
			wkday[d].setHorizontalAlignment(JLabel.CENTER);
			if (d == 0)
				dtePan.add(wkday[d], new GridFlowParm(true, 1));
			else
				dtePan.add(wkday[d], new GridFlowParm(false, d + 1));
		}

		for (int d = 0; d < 42; d++) {
			mtday[d] = new JTextField(2);
			mtday[d].setEditable(false);
			mtday[d].setBackground(Color.white);
			mtday[d].setHorizontalAlignment(JTextField.RIGHT);
			mtday[d].setCursor(new Cursor(Cursor.HAND_CURSOR));
			int tab = (d % 7) + 1;
			if (tab == 1)
				dtePan.add(mtday[d], new GridFlowParm(true, 1));
			else
				dtePan.add(mtday[d], new GridFlowParm(false, tab));
			mtday[d].setName(Integer.toString(d));
			mtday[d].addMouseListener(this);
		}

		repaintDate(yy, mm - 1, dd);
		JPanel chsrPan = new JPanel(new FlowLayout());

		chsrPan.add(lt);
		chsrPan.add(months);
		chsrPan.add(year);
		chsrPan.add(rt);

		JPanel main = new JPanel(new StackedFlowLayout());
		main.add(tPan);
		main.add(chsrPan);
		main.add(dtePan);
		main.add(td);
		displayDialog(c, main);
		if (rtnBln)
			return rtnDate;
		else 
			return DataSet.formatDateField(prmdte, "yyyyMMdd");
	}

	public String showCalcualtorDialog(String prm, JComponent c) {
		JPanel main = new JPanel(new BorderLayout(0, 0));
		JPanel north = new JPanel(new BorderLayout(5, 15));
		north.add(BorderLayout.WEST, new JLabel(
								"<html><p><font color=blue size=+1>" + cnct.getMsgText("TXT0005") + "</font></html>"));
		north.add(BorderLayout.EAST, exit);

		calc = new JTextField(15);
		calc.setText(Double.toString((DataSet.checkDouble(prm)) ));
		calc.setForeground(Color.darkGray);
		calc.setEditable(false);
		JPanel dsp = new JPanel(new  FlowLayout(FlowLayout.LEFT, 2 , 10));
		dsp.add(calc);
		JButton rtn = cnct.buildFancyButton("", "eExport.gif", cnct.getMsgText("TXT0006"), ' ');
		rtn.setActionCommand("Y");
		rtn.setName("Y");
		rtn.addActionListener(this);
		dsp.add(rtn);
		north.add(BorderLayout.SOUTH, dsp);
		
		JPanel pKey = new JPanel(new GridFlowLayout());
		for (int d = 0; d < 20; d++) {
			cckey[d] = new JTextField(3);
			cckey[d].setText(cc_num[d]);
			cckey[d].setActionCommand("calc");
			cckey[d].setName("calc");
			cckey[d].setEditable(false);
			cckey[d].setHorizontalAlignment(JTextField.CENTER);
			cckey[d].setBackground(Color.white);
			cckey[d].setCursor(new Cursor(Cursor.HAND_CURSOR));
						
			int tab = (d % 4) + 1;
			if (tab == 1)
				pKey.add(cckey[d], new GridFlowParm(true, 1));
			else
				pKey.add(cckey[d], new GridFlowParm(false, tab));
			
			cckey[d].addMouseListener(this);
		
		}
		
		for(int d=0; d< k_key.length; d++){
			final String val = k_val[d];
 			main.getInputMap(
 					JComponent.WHEN_IN_FOCUSED_WINDOW).put(
					   KeyStroke.getKeyStroke(k_key[d], 0), val);
	
 			main.getActionMap().put(k_val[d],  new AbstractAction() {
				public void actionPerformed( ActionEvent ae ){
					calcAction(val);
				}
			});
		}


		
	
		main.add(BorderLayout.NORTH, north);
		main.add(BorderLayout.CENTER, pKey);
		
		dReg1 = 0.0d;
		dReg2 = 0.0d;
		dMem = 0.0d;
		sOperator = "";
		isFixReg = true;
		
		addKeyListener(this);
		
		displayDialog(c, main);
		if(rtnBln)
			return calc.getText().trim();
		else
			return prm;
	
	}

	public Font showFontPickerDialog(Font inFont) {
		SimpleAttributeSet a = new SimpleAttributeSet();
		StyleConstants.setFontFamily(a, inFont.getFamily());
		StyleConstants.setFontSize(a, inFont.getSize());

		a = showFontPickerDialog(a);
		if (a != null) {
			String f = StyleConstants.getFontFamily(a).toString();
			int s = StyleConstants.getFontSize(a);
			if (inFont.isBold())
				inFont = new Font(f, Font.BOLD, s);
			else
				inFont = new Font(f, Font.PLAIN, s);
		}

		return inFont;
	}

	public SimpleAttributeSet showFontPickerDialog(AttributeSet inAttributes) {

		getContentPane().removeAll();
		fntNmCh = new JComboBox();
		fntSzCh = new JComboBox();
		outAttrbts = new SimpleAttributeSet();
		isBld = new JCheckBox("Bold");
		isItlc = new JCheckBox("Italic");
		isUndrlne = new JCheckBox("Underline");
		isST = new JCheckBox("Strikethrough");
		isSS = new JCheckBox("SubScript");
		fColor = new JButton();
		rtnBln = false;

		String fontList[];

		fontList = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();

		for (int i = 0; i < fontList.length; i++)
			fntNmCh.addItem(fontList[i]);
		fntNmCh.setSelectedItem(StyleConstants.getFontFamily(inAttributes)
				.toString());
		fntNmCh.setActionCommand("font+");
		fntNmCh.addActionListener(this);

		for (int i = 0; i < f_Sizes.length; i++)
			fntSzCh.addItem(f_Sizes[i]);

		fntSzCh.setSelectedItem(Integer.toString(StyleConstants
				.getFontSize(inAttributes)));
		fntSzCh.setActionCommand("font+");
		fntSzCh.addActionListener(this);

		isItlc.setSelected(StyleConstants.isItalic(inAttributes));
		isItlc.setActionCommand("font+");
		isItlc.addActionListener(this);

		isBld.setSelected(StyleConstants.isBold(inAttributes));
		isBld.setActionCommand("font+");
		isBld.addActionListener(this);

		isUndrlne.setSelected(StyleConstants.isUnderline(inAttributes));
		isUndrlne.setActionCommand("font+");
		isUndrlne.addActionListener(this);

		isST.setSelected(StyleConstants.isStrikeThrough(inAttributes));
		isST.setActionCommand("font+");
		isST.addActionListener(this);

		isSS.setSelected(StyleConstants.isSubscript(inAttributes));
		isSS.setActionCommand("font+");
		isSS.addActionListener(this);

		fColor.setCursor(new Cursor(Cursor.HAND_CURSOR));
		fColor.setBackground(StyleConstants.getForeground(inAttributes));
		fColor.setPreferredSize(new Dimension(50, 15));
		fColor.setActionCommand("fontc");
		fColor.addActionListener(this);

		JPanel stylePanel = new JPanel(new GridFlowLayout(5, 5));

		stylePanel.add(isBld, new GridFlowParm(true, 1));
		stylePanel.add(isItlc, new GridFlowParm(false, 2));
		stylePanel.add(isST, new GridFlowParm(true, 1));
		stylePanel.add(isSS, (new GridFlowParm(false, 2)));
		stylePanel.add(isUndrlne, new GridFlowParm(true, 1));

		JPanel colourPanel = new JPanel();

		colourPanel.add(new JLabel(cnct.getMsgText("TXT0008")));
		colourPanel.add(fColor);

		JPanel sizePane = new JPanel();
		sizePane.add(fntNmCh);
		sizePane.add(fntSzCh);

		JPanel north = new JPanel(new BorderLayout());
		north.add(BorderLayout.WEST, new JLabel(
				"<html><p><font color=blue size=+1>   " + cnct.getMsgText("TXT0007") + "   </font></html>"));
		north.add(BorderLayout.EAST, exit);

		ff_SampleArea = new JTextPane();
		ff_SampleArea.setText( cnct.getMsgText("TXT0009") );
		previewFancyFont(outAttrbts, fntNmCh.getSelectedItem().toString(),
				Integer.parseInt(fntSzCh.getSelectedItem().toString()), isBld
						.isSelected(), isItlc.isSelected(), isST.isSelected(),
				isSS.isSelected(), isUndrlne.isSelected(), fColor
						.getBackground());

		ff_SampleArea.setPreferredSize(new Dimension(130, 120));
		ff_SampleArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(cnct.getMsgText("TXT0010") ), BorderFactory
						.createEmptyBorder(10, 10, 10, 10)));

		JPanel south = new JPanel(new FlowLayout());

		CButton ok = new CButton(cnct.getMsgText("TXT0001"));
		ok.setActionCommand("Y");
		ok.setName("Y");
		ok.setMnemonic('O');
		ok.addActionListener(this);
		south.add(ok);

		JPanel main = new JPanel(new StackedFlowLayout());
		main.add(north);
		main.add(sizePane);
		main.add(stylePanel);
		main.add(colourPanel);
		main.add(ff_SampleArea);
		main.add(south);
		displayDialog(null, main);

		if (rtnBln)
			return outAttrbts;
		else
			return null;
	}

	public boolean showDeleteCnfrm(JComponent c) {
		getContentPane().removeAll();
		JPanel nrth = new JPanel(new BorderLayout());
		nrth.add(BorderLayout.WEST,	new JLabel(
								"<html><p><font color=blue size=+1>  " + cnct.getMsgText("TXT0011") + "   </font></html>"));
		nrth.add(BorderLayout.EAST, exit);
		JPanel center = new JPanel(new GridFlowLayout(10, 10));
		center.add(new JLabel(cnct.optionQuestionIcon), new GridFlowParm(true, 0));
		center.add(new JLabel(cnct.getMsgText("TXT0011")), new GridFlowParm(false, 1));
		center.setPreferredSize(new Dimension(275, 60));
		JPanel sth = new JPanel(new FlowLayout());

		CButton ok = new CButton(cnct.getMsgText("TXT0013"));
		ok.setActionCommand("Y");
		ok.setName("Y");
		ok.setMnemonic('Y');
		ok.addActionListener(this);
		ok.addKeyListener(this);

		CButton cancel = new CButton(cnct.getMsgText("TXT0014"));
		cancel.setActionCommand("N");
		cancel.setName("N");
		cancel.setMnemonic('N');
		cancel.addActionListener(this);
		cancel.addKeyListener(this);

		sth.add(ok);
		sth.add(cancel);

		JPanel main = new JPanel(new BorderLayout());
		main.setBorder(new LineBorder(Color.BLACK));
		main.add(BorderLayout.NORTH, nrth);
		main.add(BorderLayout.CENTER, center);
		main.add(BorderLayout.SOUTH, sth);

		ok.requestFocus();
		displayDialog(c, main);

		if (c != null && !rtnBln)
			c.setCursor(new Cursor(Cursor.HAND_CURSOR));

		return rtnBln;

	}
	
	public boolean showTimeOutCnfrm(Component c) {
		getContentPane().removeAll();
		JPanel nrth = new JPanel(new BorderLayout());
		nrth.add(BorderLayout.WEST,new JLabel(
								"<html><p><font color=blue size=+1>  " + cnct.getMsgText("TXT0015") + "  </font></html>"));
		nrth.add(BorderLayout.EAST, exit);
		JPanel center = new JPanel(new GridFlowLayout(10, 10));
		center.add(new JLabel(cnct.optionMessageIcon), new GridFlowParm(true, 0));
		center.add(new JLabel(cnct.getMsgText("TXT0016")), new GridFlowParm(false, 1));
		center.setPreferredSize(new Dimension(275, 60));
		JPanel sth = new JPanel(new FlowLayout());

		CButton ok = new CButton(cnct.getMsgText("TXT0017"));
		ok.setActionCommand("Y");
		ok.setName("Y");
		ok.setMnemonic('Y');
		ok.addActionListener(this);
		ok.addKeyListener(this);
		sth.add(ok);

		JPanel main = new JPanel(new BorderLayout());
		main.add(BorderLayout.NORTH, nrth);
		main.add(BorderLayout.CENTER, center);
		main.add(BorderLayout.SOUTH, sth);

		displayDialog(null, main);

		if (c != null && !rtnBln) c.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		dispose();
		return rtnBln;
	}

	public boolean showLogOutCnfrm(Component c) {
		getContentPane().removeAll();
		JPanel nrth = new JPanel(new BorderLayout());
		nrth.add(BorderLayout.WEST,new JLabel(
								"<html><p><font color=blue size=+1>  " + cnct.getMsgText("TXT0018") + "  </font></html>"));
		nrth.add(BorderLayout.EAST, exit);
		JPanel center = new JPanel(new GridFlowLayout(10, 10));
		center.add(new JLabel(cnct.optionQuestionIcon),
				new GridFlowParm(true, 0));
		center.add(new JLabel(cnct.getMsgText("TXT0019")),
				new GridFlowParm(false, 1));
		center.setPreferredSize(new Dimension(275, 60));
		JPanel sth = new JPanel(new FlowLayout());

		CButton ok = new CButton(cnct.getMsgText("TXT0013"));
		ok.setActionCommand("Y");
		ok.setName("Y");
		ok.setMnemonic('Y');
		ok.addActionListener(this);
		ok.addKeyListener(this);

		CButton cancel = new CButton(cnct.getMsgText("TXT0014"));
		cancel.setActionCommand("N");
		cancel.setName("N");
		cancel.setMnemonic('N');
		cancel.addActionListener(this);
		cancel.addKeyListener(this);

		sth.add(ok);
		sth.add(cancel);

		JPanel main = new JPanel(new BorderLayout());
		main.add(BorderLayout.NORTH, nrth);
		main.add(BorderLayout.CENTER, center);
		main.add(BorderLayout.SOUTH, sth);

		displayDialog(null, main);

		if (c != null && !rtnBln) c.setCursor(new Cursor(Cursor.HAND_CURSOR));
		 
		dispose();
		return rtnBln;

	}

	private boolean calcAction(String arg) {
		//
		// numeric key input
		//
		if ("C".equals(arg)) {
			dReg1 = 0.0d;
			dReg2 = 0.0d;
			sOperator = "";
			calc.setText("0");
			isFixReg = true;
		} else if (("0".equals(arg)) | ("1".equals(arg)) | ("2".equals(arg))
				| ("3".equals(arg)) | ("4".equals(arg)) | ("5".equals(arg))
				| ("6".equals(arg)) | ("7".equals(arg)) | ("8".equals(arg))
				| ("9".equals(arg)) | (".".equals(arg))) {
			if (isFixReg)
				scalc2 = (String) arg;
			else
				scalc2 = calc.getText() + arg;
			calc.setText(scalc2);
			isFixReg = false;
		}
		//
		// operations
		//
		else if (("+".equals(arg)) | ("-".equals(arg)) | ("*".equals(arg))
				| ("/".equals(arg)) | ("=".equals(arg))) {
			scalc1 = calc.getText();
			dReg2 = (Double.valueOf(scalc1)).doubleValue();
			dReg1 = calcCalc(sOperator, dReg1, dReg2);
			Double dTemp = new Double(dReg1);
			scalc2 = dTemp.toString();
			calc.setText(scalc2);
			sOperator = (String) arg;
			isFixReg = true;
		}
		//
		// memory read operation
		//
		else if ("MR".equals(arg)) {
			Double dTemp = new Double(dMem);
			scalc2 = dTemp.toString();
			calc.setText(scalc2);
			sOperator = "";
			isFixReg = true;
		}
		//
		// memory add operation
		//
		else if ("M+".equals(arg)) {
			scalc1 = calc.getText();
			dReg2 = (Double.valueOf(scalc1)).doubleValue();
			dReg1 = calcCalc(sOperator, dReg1, dReg2);
			Double dTemp = new Double(dReg1);
			scalc2 = dTemp.toString();
			calc.setText(scalc2);
			dMem = dMem + dReg1;
			sOperator = "";
			isFixReg = true;
		}
		//
		// memory sub operation
		//
		else if ("M-".equals(arg)) {
			scalc1 = calc.getText();
			dReg2 = (Double.valueOf(scalc1)).doubleValue();
			dReg1 = calcCalc(sOperator, dReg1, dReg2);
			Double dTemp = new Double(dReg1);
			scalc2 = dTemp.toString();
			calc.setText(scalc2);
			dMem = dMem - dReg1;
			sOperator = "";
			isFixReg = true;
		}
		return true;
	}

	private double calcCalc(String sOperator, double dReg1, double dReg2) {
		if ("+".equals(sOperator))
			dReg1 = dReg1 + dReg2;
		else if ("-".equals(sOperator))
			dReg1 = dReg1 - dReg2;
		else if ("*".equals(sOperator))
			dReg1 = dReg1 * dReg2;
		else if ("/".equals(sOperator))
			dReg1 = dReg1 / dReg2;
		else
			dReg1 = dReg2;
		return dReg1;
	}

	
	
	private SimpleAttributeSet previewFancyFont(SimpleAttributeSet attributes,
			String fontNameChoice, int fontSizeChoice, boolean isBold,
			boolean isItalic, boolean isST, boolean isSubScript,
			boolean isUnderline, Color fColor) {
		
		
		StyleConstants.setFontFamily(attributes, fontNameChoice);
		StyleConstants.setFontSize(attributes, fontSizeChoice);
		StyleConstants.setBold(attributes, isBold);
		StyleConstants.setItalic(attributes, isItalic);
		StyleConstants.setUnderline(attributes, isUnderline);
		StyleConstants.setStrikeThrough(attributes, isST);
		StyleConstants.setSubscript(attributes, isSubScript);
		StyleConstants.setForeground(attributes, fColor);
		DefaultStyledDocument doc = (DefaultStyledDocument) ff_SampleArea
		.getStyledDocument();
		doc.setCharacterAttributes(0, 11, attributes, false);
		return attributes;
	}

	private void repaintDate(int yy, int mm, int dd) {
		GregorianCalendar calendar = new GregorianCalendar(yy, mm, 1);
		int leadGap = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int daysInMonth = dp_dom[mm];
		if (calendar.isLeapYear(calendar.get(Calendar.YEAR)) && mm == 1) {
			daysInMonth++;
		}
		for (int d = 1; d <= 42; d++) {
			if (d - leadGap == dd) {
				mtday[d - 1].setBackground(SystemColor.gray);
				mtday[d - 1].setForeground(Color.white);
			} else {
				mtday[d - 1].setBackground(SystemColor.white);
				mtday[d - 1].setForeground(Color.black);
			}

			if (d > leadGap && d <= daysInMonth + leadGap) {
				mtday[d - 1].setText(Integer.toString(d - leadGap));
				mtday[d - 1].setVisible(true);
			} else {
				mtday[d - 1].setText("");
				mtday[d - 1].setVisible(false);
			}
		}
	}

	private void setSelection(TextMonitor textArea, int xStart, int xFinish,
			boolean moveUp) {
		if (moveUp) {
			textArea.setCaretPosition(xFinish);
			textArea.moveCaretPosition(xStart);
		} else {
			textArea.select(xStart, xFinish);
		}
	}

	public void showFindTextDialog(final TextMonitor ta) {
		getContentPane().removeAll();
		wrng = new CDialog(null, cnct);
		fndT = new JTextField(25);
		rplcT = new JTextField(25);
		textArea = ta;
		textArea.setMonitor();
		int s = textArea.getMonitorStart();
		int f = textArea.getMonitorFinish();
		if (s < f && s != -1) {
			try {
				fndT.setText(textArea.getDocument().getText(s, f - s));
			} catch (BadLocationException e) {
			}
		}

		isChkWord = new JCheckBox(cnct.getMsgText("TXT0021"));
		isChkWord.setMnemonic('w');
		ButtonModel m_modelWord = isChkWord.getModel();

		bg = new ButtonGroup();
		s_rdUp = new JRadioButton("Search up");
		s_rdUp.setMnemonic('u');
		ButtonModel m_modelUp = s_rdUp.getModel();
		bg.add(s_rdUp);

		isChkCase = new JCheckBox(cnct.getMsgText("TXT0020"));
		isChkCase.setMnemonic('c');
		ButtonModel m_modelCase = isChkCase.getModel();

		s_rdDown = new JRadioButton(cnct.getMsgText("TXT0022"), true);
		s_rdDown.setMnemonic('d');
		ButtonModel m_modelDown = s_rdDown.getModel();
		bg.add(s_rdDown);

		// "Replace" panel
		JPanel po = new JPanel(new GridLayout(2, 2, 8, 2));
		po.setBorder(new TitledBorder(new EtchedBorder(), cnct.getMsgText("TXT0023") ));
		JPanel p2 = new JPanel(new BorderLayout());
		JPanel pc2 = new JPanel(new BorderLayout());
		JPanel pc = new JPanel(new GridFlowLayout(10, 10));
		pc.setBorder(new EmptyBorder(8, 5, 8, 0));
		pc.add(new JLabel("Find:"), new GridFlowParm(true, 1));
		// find2.setDocument(m_docFind);
		pc.add(fndT, new GridFlowParm(false, 2));

		pc.add(new JLabel(cnct.getMsgText("TXT0024")), new GridFlowParm(true, 1));
		pc.add(rplcT, new GridFlowParm(false, 2));

		pc2.add(pc, BorderLayout.CENTER);
		po = new JPanel(new GridLayout(2, 2, 8, 2));
		po.setBorder(new TitledBorder(new EtchedBorder(), cnct.getMsgText("TXT0024")));
		isChkWord = new JCheckBox(cnct.getMsgText("TXT0025") );
		isChkWord.setMnemonic('w');

		isChkWord.setModel(m_modelWord);
		po.add(isChkWord);

		bg = new ButtonGroup();
		s_rdUp = new JRadioButton(cnct.getMsgText("TXT0026"));
		s_rdUp.setMnemonic('u');
		s_rdUp.setModel(m_modelUp);
		bg.add(s_rdUp);
		po.add(s_rdUp);

		isChkCase = new JCheckBox(cnct.getMsgText("TXT0020"));
		isChkCase.setMnemonic('c');
		isChkCase.setModel(m_modelCase);
		po.add(isChkCase);

		s_rdDown = new JRadioButton(cnct.getMsgText("TXT0022"), true);
		s_rdDown.setMnemonic('d');
		s_rdDown.setModel(m_modelDown);
		bg.add(s_rdDown);
		po.add(s_rdDown);
		pc2.add(po, BorderLayout.SOUTH);
		p2.add(pc2, BorderLayout.CENTER);

		JPanel p02 = new JPanel(new FlowLayout());
		JPanel p = new JPanel(new GridFlowLayout(15, 15));
		fnd = cnct.buildFancyButton(cnct.getMsgText("TXT0097"), "ifind.gif", cnct.getMsgText("TXT0027"), 'f');
		fnd.setActionCommand("find");
		fnd.addActionListener(this);
		p.add(fnd, new GridFlowParm(true, 1));

		rplc = cnct.buildFancyButton(cnct.getMsgText("TXT0024"), "iReplace.gif", cnct.getMsgText("TXT0028"), 'r');
		rplc.setActionCommand("replace");
		rplc.addActionListener(this);
		p.add(rplc, new GridFlowParm(true, 1));
		rplcAll = cnct.buildFancyButton(cnct.getMsgText("TXT0029"), "iReplace.gif", cnct.getMsgText("TXT0029"), 'a');
		rplcAll.setActionCommand("replaceAll");
		rplcAll.addActionListener(this);
		p.add(rplcAll, new GridFlowParm(true, 1));

		p02.add(p);
		p2.add(p02, BorderLayout.EAST);

		JPanel north = new JPanel(new BorderLayout());
		north.add(BorderLayout.WEST, new JLabel(
								"<html><p><font color=blue size=+1>" + cnct.getMsgText("TXT0030") + "</font></html>"));
		north.add(BorderLayout.EAST, exit);
		JPanel main = new JPanel(new BorderLayout());
		main.add(north, BorderLayout.NORTH);
		main.add(p2, BorderLayout.CENTER);
		displayDialog(null, main);

	}

	public void showInvalidConnectionDialog(String message) {
		showMessageDialog(cnct.getMsgText("TXT0031"), message, null);
	}

	public void showInvalidEntryDialog(String message, JComponent c) {
		showMessageDialog(cnct.getMsgText("TXT0032"), message, c);
	}

	public void showRequestCompleteDialog(String message, JComponent c) {
		showMessageDialog(cnct.getMsgText("TXT0033"), message, c);
	}

	public void showWarnDialog(String message, JComponent c) {
		showMessageDialog(cnct.getMsgText("TXT0034"), message, c);
	}

	public void showMessageDialog(String title, String message, JComponent c) {
		getContentPane().removeAll();
		JPanel north = new JPanel(new BorderLayout());
		north.add(BorderLayout.WEST,
				new JLabel("<html><p><font color=blue size=+1>" + title
						+ "</font></html>"));

		north.add(BorderLayout.EAST, exit);
		JPanel center = new JPanel(new GridFlowLayout(10, 10));
		center.add(new JLabel(cnct.optionMessageIcon),
				new GridFlowParm(true, 0));
		center.add(new JLabel(message), new GridFlowParm(false, 1));
		JPanel south = new JPanel(new FlowLayout());
		CButton ok = new CButton(cnct.getMsgText("TXT0001"));
		ok.setActionCommand("Y");
		ok.setName("Y");
		ok.setMnemonic('Y');
		ok.addActionListener(this);
		ok.addKeyListener(this);
		south.add(ok);

		JPanel main = new JPanel(new BorderLayout());
		main.add(BorderLayout.NORTH, north);
		main.add(BorderLayout.CENTER, center);
		main.add(BorderLayout.SOUTH, south);

		ok.requestFocus();
		displayDialog(c, main);
	}

	public DataSet showLoginDialog(final DataSet ln) {
		getContentPane().removeAll();
		login = ln;
		JPanel north = new JPanel(new BorderLayout());
		north.setBorder(BorderFactory.createEtchedBorder());
		north.add(BorderLayout.CENTER, new JLabel(cnct.banner));
		un = new JTextField(15);
		un.setName("username");
		un.setActionCommand("username");
		un.addActionListener(this);
		
		pw = new JPasswordField(15);
		pw.setName("login");
		pw.setActionCommand("login");
		pw.addActionListener(this);

		JPanel center = new JPanel(new GridFlowLayout(10, 10));
		center.add(new JLabel("User ID:"), new GridFlowParm(true,0));
		center.add(un, new GridFlowParm(false, 1));
		center.add(new JLabel("Password:"),new GridFlowParm(true, 0));
		center.add(pw, new GridFlowParm(false, 1));
		center.setPreferredSize(new Dimension(275, 60));
		JPanel south = new JPanel(new FlowLayout());

		CButton in = new CButton("Log In!");
		in.setActionCommand("login");
		in.setName("login");
		in.addActionListener(this);
		in.addKeyListener(this);

		CButton cn = new CButton("Cancel");
		cn.setActionCommand("N");
		cn.setName("N");
		cn.addActionListener(this);
		cn.addKeyListener(this);
		south.add(in);
		south.add(cn);
		JPanel main = new JPanel(new BorderLayout());
		main.add(BorderLayout.NORTH, north);
		main.add(BorderLayout.CENTER, center);
		main.add(BorderLayout.SOUTH, south);
		getContentPane().add(main);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				 un.requestFocusInWindow();
			}
		});
		
		displayDialog(null, main);
		if (rtnBln)
			return login;
		else
			return null;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equals("Y")) {
			rtnBln = true;
			dispose();

		} else if (cmd.equals("N")) {
			rtnBln = false;
			dispose();

		} else if (cmd.equals("date")) {
			Integer y = (Integer) year.getValue();
			int m = months.getSelectedIndex();
			repaintDate(y.intValue(), m, dd);

		} else if (cmd.equals("date+")) {
			int y = ((Integer) year.getValue()).intValue();
			int m = months.getSelectedIndex() + 1;
			if (m == 12) {
				y++;
				m = 0;
			}
			months.setSelectedIndex(m);
			year.setValue(new Integer(y));
			repaintDate(y, m, dd);

		} else if (cmd.equals("date-")) {
			int y = ((Integer) year.getValue()).intValue();
			int m = months.getSelectedIndex() - 1;
			if (m == -1) {
				y--;
				m = 11;
			}
			months.setSelectedIndex(m);
			year.setValue(new Integer(y));
			repaintDate(y, m, dd);

		} else if (cmd.equals("date=")) {
			rtnDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			rtnBln = true;
			dispose();

		} else if (cmd.equals("find")) {
			if (findNext(false, isChkCase.isSelected(), s_rdUp.isSelected(), 
			 		         isChkWord.isSelected(), textArea, fndT, null, true) < 1)
				wrng.showWarnDialog("Text String Not Found", null);
			else
				dispose();

			
		} else if (cmd.equals("replace")) {
			if (findNext(true, isChkCase.isSelected(), s_rdUp.isSelected(), 
				  	isChkWord.isSelected(), textArea, fndT,  rplcT, true) < 1)
				wrng.showWarnDialog("Replace String Not Found", null);

			
		} else if (cmd.equals("replaceAll")) {
			while (true) {
				int result = findNext(true, isChkCase.isSelected(), s_rdUp
						.isSelected(), isChkWord.isSelected(), textArea, fndT,
						rplcT, false);
				if (result < 0) {// error
					return;
				} else if (result == 0) // no more
					break;
			}

		} else if (cmd.equals("fontc")) {
			wrng = new CDialog(null, cnct);
			fColor.setBackground(wrng.showColorPickerDialog(fColor
					.getBackground()));
			previewFancyFont(outAttrbts, fntNmCh.getSelectedItem().toString(),
					Integer.parseInt(fntSzCh.getSelectedItem().toString()),
					isBld.isSelected(), isItlc.isSelected(), isST.isSelected(),
					isSS.isSelected(), isUndrlne.isSelected(), fColor
							.getBackground());

		} else if (cmd.equals("font+")) {
			previewFancyFont(outAttrbts, fntNmCh.getSelectedItem().toString(),
					Integer.parseInt(fntSzCh.getSelectedItem().toString()),
					isBld.isSelected(), isItlc.isSelected(), isST.isSelected(),
					isSS.isSelected(), isUndrlne.isSelected(), fColor
							.getBackground());
			
		} else if (cmd.equals("username") && un.getText() != null && !un.getText().equals("")) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {pw.requestFocusInWindow();}
			});
			
		} else if (cmd.equals("login")) {
			rtnBln = true;
			login.putStringField("[Login-UserName/]", un.getText());
			login.putStringField("[Login-Clear/]", new String(pw.getPassword()));
			try {
				login.putStringField("[Login-Password/]", CPasswordField
						.sha1(new String(pw.getPassword())));
			} catch (NoSuchAlgorithmException e1) {
				login.putStringField("[Login-Password/]", "");
			}
			dispose();

		}
	}

	
	
	public void keyPressed(KeyEvent e) {
		String cmd = ((JComponent) e.getSource()).getName();

		if (cmd.equals("Y") && e.getKeyCode() == 10) {
			rtnBln = true;
			dispose();
			return;

		} else if (cmd.equals("N") && e.getKeyCode() == 10) {
			rtnBln = false;
			dispose();
			return;

		} else if (cmd.equals("login") && e.getKeyCode() == 10) {
			rtnBln = true;
			login.putStringField("[Login-UserName/]", un.getText());
			try {
				login.putStringField("[Login-Password/]", CPasswordField.sha1(new String(pw.getPassword())));
			} catch (NoSuchAlgorithmException e1) {
				login.putStringField("[Login-Password/]", "");
			}
			dispose();
			return;
		}
		
		if(calc != null) calcAction(KeyEvent.getKeyText(e.getKeyCode()));
			
	}

	public void stateChanged(ChangeEvent e) {
		Integer y = (Integer) year.getValue();
		int m = months.getSelectedIndex();
		repaintDate(y.intValue(), m, dd);
	}

	public void mouseClicked(MouseEvent e) {
		String cmd = ((JComponent) e.getSource()).getName();
		
		if(cmd.equals("calc")) {
			calcAction( ((JTextField)e.getSource()).getText());	
			
		} else if (DataSet.isInteger(cmd)) {
			int ry = ((Integer) year.getValue()).intValue() * 10000;
			int rm = (months.getSelectedIndex() + 1) * 100;
			int rd = Integer.parseInt(mtday[DataSet.checkInteger(cmd)]
					.getText());
			rtnDate = Integer.toString(ry + rm + rd);
			rtnBln = true;
			dispose();
		}
	}

	public void windowClosed(WindowEvent e) {
		if (getParent() != null)
			getParent().requestFocus();
	}

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { }

	public void mousePressed(MouseEvent e) {  }

	public void mouseReleased(MouseEvent e) {}

	public void keyReleased(KeyEvent e) { }

	public void keyTyped(KeyEvent e) { }

	public void windowActivated(WindowEvent e) { }

	public void windowClosing(WindowEvent e) { }

	public void windowDeactivated(WindowEvent e) { }

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(13));
//		Point p = e.getLocationOnScreen();
//		setLocation(p.x - (getWidth() / 2),  p.y - 10 );	
	}

	public void mouseMoved(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

}
 
 
 class Utils {
	public static final char[] WORD_SEPARATORS = { ' ', '\t', '\n', '\r', '\f',
			'.', ',', ':', '-', '(', ')', '[', ']', '{', '}', '<', '>', '/',
			'|', '\\', '\'', '\"' };

	public static boolean isSeparator(char ch) {
		for (int k = 0; k < WORD_SEPARATORS.length; k++)
			if (ch == WORD_SEPARATORS[k])
				return true;
		return false;
	}
}
