
package webBoltOns.client.components.layoutManagers;

/*
 * $Id: GridFlowLayoutParameter.java,v 1.1 2007/04/20 19:37:30 paujones2005 Exp $ $Name:  $
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

public class GridFlowParm {
	
	public static final boolean NEXT_ROW = true;
	public static final boolean CURRENT_ROW = false;
	public static final boolean FILL_CELL = true;
	public static final boolean STACK_CELL = false;
	public static final int COLUMN_1 = 0,
							COLUMN_2 = 1,
							COLUMN_3 = 2,
							COLUMN_4 = 3,
							COLUMN_5 = 4,
							COLUMN_6 = 5,
							COLUMN_7 = 6,
							COLUMN_8 = 7,
							COLUMN_9 = 8,
							COLUMN_10 = 9;
	
	private  boolean moveToNextRow, fillCell;
	private int tabToColumn;
	public GridFlowParm(boolean moveToNextRow, int tabToColumn) {
		this.moveToNextRow = moveToNextRow;
		this.tabToColumn = tabToColumn;
		this.fillCell = false;
	}
	public GridFlowParm(boolean moveToNextRow, int tabToColumn, boolean fillCell) {
		this.moveToNextRow = moveToNextRow;
		this.tabToColumn = tabToColumn;
		this.fillCell = fillCell;
	}
	protected boolean getRow() {
			return moveToNextRow; 
    }
	protected int getTabs () {
    	return tabToColumn;
    }
	
	protected boolean getFillCell() {
	return fillCell;
	}
}