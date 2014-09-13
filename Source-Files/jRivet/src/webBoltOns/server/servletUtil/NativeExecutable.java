package webBoltOns.server.servletUtil;

/*
 * $Id: NativeExecutable.java,v 1.1 2007/04/20 19:37:18 paujones2005 Exp $ $Name:  $
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

import java.io.IOException;

public class NativeExecutable {
 

	public  static Process startAction(final String program,
		 	          String parameters, boolean waitForTermination)  {
		Process process = null;
		
		if (parameters.trim().length() > 0)  
			parameters = " " + parameters.trim();	 
		else  
			parameters = "";
	 
	try {	
		if (isWindows()) {
			if (isWindows9X())  
				process = Runtime.getRuntime().exec(
				    "command.com /C start " + program + " "  + parameters);
			else  
				process = Runtime.getRuntime().exec(
					"cmd /c start " + program + " "  + parameters);
			 
		} else if (isMac()) {
			if (parameters.trim().length() == 0)  
				process = Runtime.getRuntime().exec(
					new String[] { "/usr/bin/open", program });
			 else  
				process = Runtime.getRuntime().exec(
						new String[] { "/usr/bin/open", parameters.trim(), program });	 
		}
		
		if (process != null && waitForTermination)
				process.waitFor();
 			
	    } catch (IOException ie) {
 			
		} catch (InterruptedException ie) {

		}
		return process;
	}
	
 	/**
	 * Checks the Operating System.
	 * 
	 * @return true if the current os is Windows
	 */
	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.indexOf("windows") != -1 || os.indexOf("nt") != -1;
	}

	/**
	 * Checks the Operating System.
	 * 
	 * @return true if the current os is Windows
	 */
	public static boolean isWindows9X() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.equals("Windows 95") || os.equals("Windows 98");
	}

	/**
	 * Checks the Operating System.
	 * 
	 * @return true if the current os is Apple
	 */
	public static boolean isMac() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.indexOf("mac") != -1;
	}

	/**
	 * Checks the Operating System.
	 * 
	 * @return true if the current os is Linux
	 */
	public static boolean isLinux() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.indexOf("linux") != -1;
	}
}