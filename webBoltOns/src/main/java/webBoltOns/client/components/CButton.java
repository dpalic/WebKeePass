package webBoltOns.client.components;

/*
 * $Id: CButton.java,v 1.1 2007/04/20 19:37:21 paujones2005 Exp $ $Name:  $
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
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class CButton extends JButton{

	private static final long serialVersionUID = 3557416440342682523L;

	CButton() {
		super();
		setUI(new CButtonUI());
	};

	public CButton(String text) {
		super(text);
		setUI(new CButtonUI());
		  
	};

	CButton(String text, Icon icon) {
		super(text,icon );
		setUI(new CButtonUI());
	}
	
	/**
	 * UI for buttons in <b>Substance</b> look and feel.
	 * 
	 * @author P. Jones 
	 */
	
}

  class CButtonUI extends MetalButtonUI {

	  private final static Border defaultBorder = 
	     CButtonBorder.getButtonBorder();
	  private Border savedBorder;
	  protected static CButtonUI buttonUI;

	  public static ComponentUI createUI (JComponent c) {
	    if (buttonUI == null) {
	      buttonUI = new CButtonUI();
	    }
	    return buttonUI;
	  }

	  public void installUI (JComponent c) {
	    super.installUI (c);
	    savedBorder = c.getBorder();
	    c.setBorder (defaultBorder);
	    
	  }

	  public void uninstallUI (JComponent c) {
	    if (c.getBorder() == defaultBorder)
	      c.setBorder(savedBorder);
	    super.uninstallUI (c);
	  }

	  protected void paintButtonPressed(
	   Graphics g, AbstractButton b) { }
	
	    protected void paintFocus(Graphics g, AbstractButton b,
				      Rectangle viewRect, Rectangle textRect, Rectangle iconRect){
	    	
	    }

 }

  class CButtonBorder extends AbstractBorder {
	
	private static final long serialVersionUID = 5009938510565240479L;
	private static Border buttonBorder =
	     new CButtonBorder();

	  public static Border getButtonBorder() {
	    return buttonBorder;
	  }

	  public void paintBorder (Component c, Graphics g, 
	      int x, int y, int width, int height) {
	    boolean pressed = false;
	    boolean focused = false;

	   
	    if (c instanceof AbstractButton) {
	      AbstractButton b = (AbstractButton)c;
	      ButtonModel bm = b.getModel();

	      pressed = bm.isPressed();
	      focused = (pressed && bm.isArmed()) || 
	                (b.isFocusPainted() && b.hasFocus());
	    
	    g.drawRoundRect(x,y, width -3 ,height -3, 7, 7);
	    if(focused) {
	    	g.setColor(Color.LIGHT_GRAY);
		    g.drawRoundRect(x+1,y+1, width-5 ,height-5, 7, 7);	    	
	    	}
	    }
	  }
	  
	  public Insets getBorderInsets (Component c) {
	    return new Insets (2,36,4, 36);
	  }
	}



