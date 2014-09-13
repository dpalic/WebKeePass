package webBoltOns.client.components.layoutManagers;

/*
 * $Id: GridFlowLayout.java,v 1.1 2007/04/20 19:37:30 paujones2005 Exp $ $Name:  $
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
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;

public class GridFlowLayout implements LayoutManager2, Serializable {
	private static final long serialVersionUID = -4793834200957414643L;
	private Hashtable<Dimension, Dimension> cellSizes = new Hashtable<Dimension, Dimension>();
	private HashMap<String, Component> componentTable = new HashMap<String, Component>();
	private HashMap<Object, ComponentConstraint> constraintsTable = new HashMap<Object, ComponentConstraint>();
	public int height = 0, width = 0;
	private int minWidth = 0, minHeight = 0;
	private int preferredWidth = 0, preferredHeight = 0;
	private int row = 0, col = 0, curCol = 0, compIndex = 0;
	private int vgap, hgap;

	public GridFlowLayout() {
		this(5, 5);
	}

	public GridFlowLayout(int v, int h) {
		vgap = v;
		hgap = h;

	}

	public void addLayoutComponent(Component component, Object constraints) {
		synchronized (component.getTreeLock()) {
			boolean gotoNextRow = false, fillCell = false;
			if (constraints != null) {
				gotoNextRow = ((GridFlowParm) constraints).getRow();
				curCol = ((GridFlowParm) constraints).getTabs() + 1;
				fillCell = ((GridFlowParm) constraints).getFillCell();
			}

			double orow = 0, ocol = 0;
			double compWidth = component.getPreferredSize().getWidth(), 
			      compHeight = component.getPreferredSize().getHeight();

			if (gotoNextRow) 
				row++;
			
			if (curCol > col) 
				col = curCol;
			
			compIndex++;
			Dimension cr = new Dimension(curCol, row);
			Dimension location = (Dimension) cellSizes.get(cr);

			if (location == null) {
				Dimension newLocation = new Dimension();
				newLocation.setSize(compWidth + vgap, compHeight + hgap);
				cellSizes.put(cr, newLocation);
			} else {
				ocol = location.getWidth() + 1;
				orow = 0;
				location.setSize(location.getWidth() + compWidth + vgap, location.getHeight());
				cellSizes.put(cr, location);
			}

			ComponentConstraint componentConstraint = new ComponentConstraint(component);
			componentConstraint.setOffset(ocol, orow);
			componentConstraint.setCurrentSize(compWidth, compHeight);
			componentConstraint.setCellLocation(curCol, row);
			componentConstraint.setIndex(compIndex);
			componentConstraint.setFillCell(fillCell);
			
			constraintsTable.put((Object) component, componentConstraint);
			componentTable.put(Integer.toString(compIndex), component);
			getColumnPositions();
			getRowPositions();

		}
	}

	/* Required by LayoutManager. */
	public void addLayoutComponent(String name, Component comp) { }


	public Dimension currentLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			return new Dimension(width, height);
		}
	}

	private double[] getColumnPositions() {
		double columnWidths[] = new double[col+1];
		double accumColumnWidth = 0;
		for (int c = 0; c < col; c++) {
			double maxColumnWidth = 0;
			for (int r = 0; r < row; r++) {
				Dimension size = (Dimension) cellSizes.get(new Dimension(
						(c + 1), (r + 1)));
				if (size != null) {
					if (maxColumnWidth < size.getWidth()) {
						maxColumnWidth = size.getWidth();
					}
				}
			}
			columnWidths[c] = accumColumnWidth;
			accumColumnWidth += maxColumnWidth;
		}
		columnWidths[col] = accumColumnWidth;		
		preferredWidth = (int) accumColumnWidth;
		return columnWidths;
	}
		
	private double[] getRowPositions() {
		double rowHeight[] = new double[row+1];
		double accumRowHeight = 0;
		for (int r = 0; r < row; r++) {
			double maxRowHeight = 0;
			for (int c = 0; c < col; c++) {
				Dimension size = (Dimension) cellSizes.get(new Dimension((c + 1), (r + 1)));
				if (size != null) {
					if (maxRowHeight < size.getHeight()) {
						maxRowHeight = size.getHeight();
					}
				}
			}
			rowHeight[r] = accumRowHeight;
			accumRowHeight += maxRowHeight;
		}
		rowHeight[row] = accumRowHeight;
		preferredHeight = (int) accumRowHeight;
		return rowHeight;
	}
	
	
	public float getLayoutAlignmentX(Container target) {
		return 0f;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0f;
	}

	public Dimension getLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			return new Dimension(preferredWidth, preferredHeight);
		}
	}



	public void invalidateLayout(Container target) {}

	
	/* Required by LayoutManager. */
	/*
	 * This is called when the panel is first displayed, and every time its size
	 * changes. Note: You CAN'T assume preferredLayoutSize or minimumLayoutSize
	 * will be called -- in the case of applets, at least, they probably won't
	 * be.
	 */

	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			solveGrid(parent);
			double columnPosition[] = getColumnPositions();
			double rowPosition[] = getRowPositions();
			Insets insets = parent.getInsets();

			int maxWidth = parent.getSize().width - (insets.left + insets.right);
			if (width < maxWidth) {
				width = maxWidth;

			}
			int maxHeight = parent.getSize().height
					- (insets.top + insets.bottom);
			if (height < maxHeight) {
				height = maxHeight;

			}
			Component comps[] = parent.getComponents();
			for (int i = comps.length - 1; i >= 0; i--) {
				Component component = comps[i];
				ComponentConstraint componentConstraint = (ComponentConstraint) constraintsTable
						.get(component);
				if (componentConstraint != null ) {
					Dimension d = component.getPreferredSize();
					Dimension location = componentConstraint.getCellLocation();
					if (location != null) {
						Dimension offset = componentConstraint.getOffset();
						int r = (int) location.getHeight();
						int c = (int) location.getWidth();
						
						int absoluteX = insets.left
								+ (int) columnPosition[c - 1]
								+ (int) offset.getWidth() + hgap;						
						
						int absoluteY = insets.top + (int) rowPosition[r - 1]
								+ (int) offset.getHeight() + vgap;
						
						int absoluteWidth  = (int) columnPosition[c] - absoluteX - hgap;
						int absoluteHeight = (int) rowPosition[r]    - absoluteY - vgap;
					           
						if(!componentConstraint.getFillCell()){	
							absoluteWidth = d.width;
							absoluteHeight = d.height;
						}
						
						component.setBounds(absoluteX, absoluteY, absoluteWidth, absoluteHeight);
					}
				}
			}
		}
	}

	public Dimension maximumLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		}
	}

	/* Required by LayoutManager. */
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		//Always add the container's insets!		
		Insets insets = parent.getInsets();
		dim.width = minWidth + insets.left + insets.right + (vgap * 2);
		dim.height = minHeight + insets.top + insets.bottom + (vgap * 2);
		return dim;
	}

	/* Required by LayoutManager. */
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right + (vgap * 2 );
		dim.height = preferredHeight + insets.top + insets.bottom + (hgap * 2 );
		return dim;
	}

	/* Required by LayoutManager. */
	public void removeLayoutComponent(Component comp) {
		row = 0; col = 0; curCol = 0; compIndex = 0; height=0; width = 0;
		minWidth = 0; minHeight = 0; preferredWidth = 0; preferredHeight = 0;
		cellSizes = new Hashtable<Dimension, Dimension>();
		componentTable = new HashMap<String, Component>();
		constraintsTable = new HashMap<Object, ComponentConstraint>();
	}

	private void solveCell(Component component,
								ComponentConstraint componentConstraint, double w, double h) {
		int i = componentConstraint.getIndex();
		boolean notComplete;
		do {
			notComplete = false;
			i++;
			Component nextComponent = (Component) componentTable.get(Integer
					.toString(i));
			if (nextComponent != null) {
				ComponentConstraint nextComponentConstraint = (ComponentConstraint) constraintsTable
						.get(nextComponent);
				if (nextComponentConstraint.getCellLocation().getHeight() == componentConstraint
						.getCellLocation().getHeight()
						&& nextComponentConstraint.getCellLocation().getWidth() == componentConstraint
								.getCellLocation().getWidth()) {
					Dimension offset = (Dimension) nextComponentConstraint.getOffset();
					double newW = offset.getWidth() + w;
					double newH = offset.getHeight();
					notComplete = true;
					offset.setSize(newW, newH);
					nextComponentConstraint.setOffset(offset);
					constraintsTable.remove(nextComponent);
					constraintsTable.put(nextComponent, nextComponentConstraint);
				}
			}
		} while (notComplete);
	}

	private void solveGrid(Container parent) {
		Component comps[] = parent.getComponents();
			for (int i = 0; i < comps.length; i++) {
				Component component = comps[i];
				ComponentConstraint componentConstraint = (ComponentConstraint) constraintsTable.get(component);
				Dimension c;
				if(component.isVisible()) 
					c = component.getPreferredSize();
				else
					c = new Dimension(0 ,0);
				
				if (componentConstraint != null && c != null  ) {
					Dimension o = componentConstraint.getCurrentSize();
					double w = (c.getWidth()) - o.getWidth();
					double h = (c.getHeight()) - o.getHeight();
	 			
				if (w != 0 || h != 0 ) {
						Dimension d = (Dimension) cellSizes.get(componentConstraint.getCellLocation());
						double newW = d.getWidth() + w;
						double newH = d.getHeight() + h;																	
						d.setSize(newW + vgap, newH + hgap);
						componentConstraint.setCurrentSize(c);
							  				
						if(component.isVisible()) {
  							cellSizes.put(componentConstraint.getCellLocation(), d);
  							solveCell(component, componentConstraint, w, h);
  						} else {
  							cellSizes.put(componentConstraint.getCellLocation(), c);
  						}
				}	
			}	
		}
	}
	
	public String toString() {
		return getClass().getName() + "[vgap=" + vgap + " hgap=" + hgap + "/]";
	}
}

class ComponentConstraint implements java.io.Serializable {
	private static final long serialVersionUID = -4345026393179834599L;

	private Dimension cellLocation = new Dimension(),
			cellOffset = new Dimension(), currentSize = new Dimension();

	private boolean fillCell = false;
	private int  compIndex = 0;

	ComponentConstraint(Component component) {	
	}

	public Dimension getCellLocation() {
		return this.cellLocation;
	}

	public Dimension getCurrentSize() {
		return this.currentSize;
	}

	public int getIndex() {
		return this.compIndex;
	}

	public boolean getFillCell() {
		return this.fillCell;
	}

	
	public Dimension getOffset() {
		return this.cellOffset;
	}

	public void setCellLocation(Dimension cellLocation) {
		this.cellLocation = cellLocation;
	}

	public void setCellLocation(int col, int row) {
		this.cellLocation.setSize(col, row);
	}

	public void setCurrentSize(Dimension Current) {
		this.currentSize = Current;
	}

	public void setFillCell(boolean fillCell) {
		this.fillCell = fillCell;
	}
	
	public void setCurrentSize(double p0, double p1) {
		this.currentSize.setSize(p0, p1);
	}

	public void setIndex(int compIndex) {
		this.compIndex = compIndex;
	}

	public void setOffset(Dimension cellOffset) {
		this.cellOffset = cellOffset;
	}

	public void setOffset(double p0, double p1) {
		this.cellOffset.setSize(p0, p1);
	}

}