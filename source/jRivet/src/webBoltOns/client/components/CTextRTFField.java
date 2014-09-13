package webBoltOns.client.components;

/*
 * $Id: CTextRTFField.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleContext;
import javax.swing.text.rtf.RTFEditorKit;

import webBoltOns.client.WindowFrame;
import webBoltOns.client.WindowItem;
import webBoltOns.client.clientUtil.SimpleFileFilter;
import webBoltOns.client.components.componentRules.StandardComponentLayout;
import webBoltOns.dataContol.DataSet;

public class CTextRTFField extends CTextHTMLField implements
		StandardComponentLayout, ActionListener {

	// An action that opens an existing file
	class ImportAction extends AbstractAction {

		private static final long serialVersionUID = -6461801625701678965L;

		public ImportAction() {
			super("Import Document");
		}

		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new SimpleFileFilter("rtf", "RTF Documents"));
			chooser.setLocation(0, 0);
			if (chooser.showOpenDialog(mFrm.getWindowFrame()) != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooser.getSelectedFile();
			if (file == null)
				return;

			FileReader reader = null;
			try {
				reader = new FileReader(file);				
				doc = new DefaultStyledDocument(context);
				rtf.read(reader, doc, 0);
				mainT.setDocument(doc);
				mFrm.doEditClick();
			} catch (IOException ex) {
				dialog = new CDialog(mFrm.getWindowFrame(), cnct);
				dialog.showWarnDialog(cnct.getMsgText("TXT0105"), CTextRTFField.this);
			} catch (BadLocationException e) {
				dialog = new CDialog(mFrm.getWindowFrame(), cnct);
				dialog.showWarnDialog(cnct.getMsgText("TXT0105"), CTextRTFField.this);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException x) {}
				}
			}
		}
	}

	// An action that saves the document to a file
	class ExportAction extends AbstractAction {

		private static final long serialVersionUID = -6461801625701678966L;

		public ExportAction() {
			super("Export Document");
		}

		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new SimpleFileFilter("rtf", "RTF Documents"));
			if (chooser.showSaveDialog(mFrm.getWindowFrame()) != JFileChooser.APPROVE_OPTION ||  
										chooser.getSelectedFile().getAbsolutePath() == null) return;
			
			String f = chooser.getSelectedFile().getAbsolutePath();
			File file = null;
			OutputStream out = null;
			if(f.toLowerCase().endsWith(".rtf")) 
				file = new File(f);
			else 
				file = new File(f+".rtf");
		
			try {
				out = new FileOutputStream(file);
				rtf.write(out, doc, 0, doc.getLength());
			} catch (IOException ex) {
				dialog = new CDialog(mFrm.getWindowFrame(), cnct);
				dialog.showWarnDialog(cnct.getMsgText("TXT0106"), CTextRTFField.this);
			} catch (BadLocationException e) {
				dialog = new CDialog(mFrm.getWindowFrame(), cnct);
				dialog.showWarnDialog(cnct.getMsgText("TXT0106"), CTextRTFField.this);
		} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException x) {
					}
				}
			}
		}
	}

	private static final long serialVersionUID = 2054577206355596248L;
	
	private RTFEditorKit rtf;

	private StyleContext context;

	private DefaultStyledDocument doc;

	public CTextRTFField() {}

	public void buildComponent(WindowItem parentItem, WindowItem thisItem, WindowFrame mainFrame) {
		super.buildComponent(parentItem, thisItem, mainFrame);
	}
	
	
	public void initializeComponentUI() {
		toolBar = buildToolBar();
		add(toolBar, BorderLayout.NORTH);
		rtf = new RTFEditorKit();
		context = new StyleContext();
		doc = new DefaultStyledDocument(context);
		mainT.setMonitorDocumentType(doc, rtf);
		rtf = new RTFEditorKit();
		context = new StyleContext();
		doc = new DefaultStyledDocument(context);
		mainT.setMonitorDocumentType(doc, rtf);
	}
	
	
	private JToolBar buildToolBar() {
		JButton mnu;
		JToolBar tb = new JToolBar();

		mnu = cnct.buildFancyButton(cnct.getMsgText("TXT0101"), "eImport.gif", null, ' ');
		mnu.addActionListener(new ImportAction());
		tb.add(mnu, null);
		tb.add(new JToolBar.Separator());
		
		mnu = cnct.buildFancyButton(cnct.getMsgText("TXT0125"), "eExport.gif", null, ' ');
		mnu.addActionListener(new ExportAction());
		tb.add(mnu, null);
		tb.add(new JToolBar.Separator());
		
		mnu = cnct.buildFancyButton(cnct.getMsgText("TXT0121"), "eCut.gif", null, ' ');
		mnu.addActionListener(mainT.getActionMap().get(DefaultEditorKit.cutAction));
		tb.add(mnu, null);
		tb.add(new JToolBar.Separator());
		
		mnu = cnct.buildFancyButton(cnct.getMsgText("TXT0122"), "eCopy.gif", null, ' ');
		mnu.addActionListener(mainT.getActionMap().get(DefaultEditorKit.copyAction));
		tb.add(mnu, null);
		tb.add(new JToolBar.Separator());
		
		mnu = cnct.buildFancyButton(cnct.getMsgText("TXT0123"), "ePaste.gif", null, ' ');
		mnu.addActionListener(mainT.getActionMap().get(DefaultEditorKit.pasteAction));
		tb.add(mnu, null);
		tb.add(new JToolBar.Separator());
		
		mnu = cnct.buildFancyButton(cnct.getMsgText("TXT0061"), "eFont.gif", null, ' ');
		mnu.setActionCommand("font");
		mnu.addActionListener(this);
		tb.add(mnu, null);
		tb.add(new JToolBar.Separator());
		
		mnu = cnct.buildFancyButton(cnct.getMsgText("TXT0126"), "eFind.gif", null, ' ');
		mnu.setActionCommand("find");
		mnu.addActionListener(this);
		tb.add(mnu, null);
		tb.add(new JToolBar.Separator());
		
		tb.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(new Color(235, 235, 235), 10), BorderFactory
				.createLoweredBevelBorder()));

		return tb;
	}

	
	protected void setFontAttributeSet(AttributeSet attr) {
		int xStart = mainT.getSelectionStart();
		int xFinish = mainT.getSelectionEnd();
		 if (!mainT.hasFocus()) {
		 	xStart = mainT.getMonitorStart();
		 	xFinish = mainT.getMonitorFinish();
	 	}

		if (xStart != xFinish) {
			doc.setCharacterAttributes(xStart, xFinish - xStart, attr, false);

		} else {
			MutableAttributeSet inputAttributes = rtf.getInputAttributes();
			inputAttributes.addAttributes(attr);
		}
	}

	
	
	public void clearComponent(String defaultValue) {
		doc = new DefaultStyledDocument(context);
		mainT.setDocument(doc);
	}

	
	public DataSet populateDataSet(String action, String editorName,
			DataSet dataSet) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			rtf.write(out, doc, 0, doc.getLength());
			byte [] bytes =  out.toByteArray();
			dataSet.putStreamArray(editorName, bytes);	
		} catch (IOException ex) {
			dataSet.putStreamArray(editorName, null);	
		} catch (BadLocationException e) {
			dataSet.putStreamArray(editorName, null);				
		}
			return dataSet;
	}

	public void populateComponent(String action, String editorName,
			DataSet dataSet) {
		try {
			byte [] bytes = dataSet.getStreamArray(editorName);
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			doc = new DefaultStyledDocument(context);
			rtf.read(in, doc, 0);
			mainT.setDocument(doc);
			setScrollPosition(0);
		} catch (NullPointerException e) {
			setText(null);	
		} catch (IOException e) {
			setText(null);
		} catch (BadLocationException e) {
			setText(null);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("font")) {
			int p = mainT.getCaretPosition();
			dialog = new CDialog(mFrm.getWindowFrame(), cnct);
			SimpleAttributeSet attr = dialog.showFontPickerDialog(doc
					.getCharacterElement(p).getAttributes());
			if (attr != null)
				setFontAttributeSet(attr);
			mainT.grabFocus();
	
		} else if(cmd.equals("find")) {
			dialog = new CDialog(mFrm.getWindowFrame(), cnct);
			dialog.showFindTextDialog(mainT);
			 if(!mainT.isRequestFocusEnabled()) {
				 mainT.setRequestFocusEnabled(true); 
			 }	
			
		}
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				mainT.requestFocusInWindow();
			}} );
	}
}
