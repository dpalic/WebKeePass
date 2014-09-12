package webBoltOns.client.components.layoutManagers;

/*
 * $Id: StackedFlowLayout.java,v 1.1 2007/04/20 19:37:30 paujones2005 Exp $ $Name:  $
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


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.Serializable;

public class StackedFlowLayout extends FlowLayout implements Serializable {

	private static final long serialVersionUID = -8290702395521516645L;
	public static final int BOTTOM = 2;
	public static final int MIDDLE = 1;
	public static final int TOP = 0;

	boolean hfill;
	int hgap;
	boolean vfill;
	int vgap;

	public StackedFlowLayout() {
		this(TOP, 5, 5, true, false);
	}

	public StackedFlowLayout(boolean hfill, boolean vfill) {
		this(TOP, 5, 5, hfill, vfill);
	}

	public StackedFlowLayout(int align) {
		this(align, 5, 5, true, false);
	}

	
	public StackedFlowLayout(int align, int hgap, int vgap) {
		this(align, hgap, vgap, true, false);
	}

	public StackedFlowLayout(int align, boolean hfill, boolean vfill) {
		this(align, 5, 5, hfill, vfill);
	}

	public StackedFlowLayout(int align, int hgap, int vgap, boolean hfill,
			boolean vfill) {
		setAlignment(align);
		this.hgap = hgap;
		this.vgap = vgap;
		this.hfill = hfill;
		this.vfill = vfill;
	}
	
	public StackedFlowLayout(int hgap, int vgap) {
		this(TOP, hgap, vgap, true, true);
	}
	
	public int getHgap() {
		return hgap;
	}

	public boolean getHorizontalFill() {
		return hfill;
	}

	public boolean getVerticalFill() {
		return vfill;
	}

	public int getVgap() {
		return vgap;
	}

	public void layoutContainer(Container target) {
		Insets insets = target.getInsets();
		int maxheight = target.getSize().height
				- (insets.top + insets.bottom + vgap * 2);
		int maxwidth = target.getSize().width
				- (insets.left + insets.right + hgap * 2);
		int numcomp = target.getComponentCount();
		int x = insets.left + hgap;
		int y = 0;
		int colw = 0;
		int start = 0;
		for (int i = 0; i < numcomp; i++) {
			Component m = target.getComponent(i);
			if (!m.isVisible())
				continue;
			Dimension d = m.getPreferredSize();
			if (vfill && i == numcomp - 1)
				d.height = Math.max(maxheight - y, m.getPreferredSize().height);
			if (hfill) {
				m.setSize(maxwidth, d.height);
				d.width = maxwidth;
			} else {
				m.setSize(d.width, d.height);
			}
			if (y + d.height > maxheight) {
				placeObjects(target, x, insets.top + vgap, colw, maxheight - y,
						start, i);
				y = d.height;
				x += hgap + colw;
				colw = d.width;
				start = i;
				continue;
			}
			if (y > 0)
				y += vgap;
			y += d.height;
			colw = Math.max(colw, d.width);
		}

		placeObjects(target, x, insets.top + vgap, colw, maxheight - y, start,
				numcomp);
	}

	public Dimension minimumLayoutSize(Container target) {
		Dimension tarsiz = new Dimension(0, 0);
		for (int i = 0; i < target.getComponentCount(); i++) {
			Component m = target.getComponent(i);
			if (!m.isVisible())
				continue;
			Dimension d = m.getMinimumSize();
			tarsiz.width = Math.max(tarsiz.width, d.width);
			if (i > 0)
				tarsiz.height += vgap;
			tarsiz.height += d.height;
		}

		Insets insets = target.getInsets();
		tarsiz.width += insets.left + insets.right + hgap * 2;
		tarsiz.height += insets.top + insets.bottom + vgap * 2;
		return tarsiz;
	}

	private void placeObjects(Container target, int x, int y, int width,
			int height, int first, int last) {
		int align = getAlignment();
		if (align == 1)
			y += height / 2;
		if (align == 2)
			y += height;
		for (int i = first; i < last; i++) {
			Component m = target.getComponent(i);
			Dimension md = m.getSize();
			if (m.isVisible()) {
				int px = x + (width - md.width) / 2;
				m.setLocation(px, y);
				y += vgap + md.height;
			}
		}

	}

	public Dimension preferredLayoutSize(Container target) {
		Dimension tSize = new Dimension(0, 0);
		for (int i = 0; i < target.getComponentCount(); i++) {
			Component m = target.getComponent(i);
			if (!m.isVisible())
				continue;
			Dimension d = m.getPreferredSize();
			tSize.width = Math.max(tSize.width, d.width);
			if (i > 0)
				tSize.height += vgap;
			tSize.height += d.height;
		}
		Insets insets = target.getInsets();
		tSize.width += insets.left + insets.right + hgap * 2;
		tSize.height += insets.top + insets.bottom + vgap * 2;
		return tSize;
	}

	public void setHgap(int hgap) {
		super.setHgap(hgap);
		this.hgap = hgap;
	}

	public void setHorizontalFill(boolean hfill) {
		this.hfill = hfill;
	}

	public void setVerticalFill(boolean vfill) {
		this.vfill = vfill;
	}

	public void setVgap(int vgap) {
		super.setVgap(vgap);
		this.vgap = vgap;
	}

}

