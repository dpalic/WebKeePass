package webBoltOns.client.clientUtil;

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
import java.awt.Robot;
import java.awt.event.KeyEvent;

import webBoltOns.client.WindowFrame;

public class AutoType   {
	 
	private static final long serialVersionUID = -3343051243845173451L;

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

	
	private Robot robot;
	private String webSite, altSite, prgm;
	private WindowFrame mFrm;
	
		public AutoType()  {
			try {
				this.robot = new Robot();
			} catch ( AWTException e) {
				e.printStackTrace();	
			}

		}


		public void autoTypeRobort(String i, WindowFrame frame,
								    String b9, String c8, String passwordId, String m8, 
								    String n7, String i2, String p5){			
			
			i = i.replace(UserId,     b9);
 			i = i.replace(Password,   c8);
 			i = i.replace(PasswordId, passwordId);
 			i = i.replace(EmailId,    m8);
 			
 			mFrm = frame;
 			webSite = n7;
 			altSite = i2;
 			prgm = p5;
 			runRobot(i);
		}
		
		
		private void runRobot(String  i) {
			
			final int notFound = 999999999;
			
			int	d = notFound, t = notFound, e = notFound, b = notFound, w = notFound, 
			    a = notFound, p = notFound, r = notFound, h = notFound;;

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
				if(! webSite.startsWith("http"))
					mFrm.getAppletConnector().showWebDocument("http://" + webSite);
				else
					mFrm.getAppletConnector().showWebDocument(webSite);				

				runRobot(i.substring(w+7, i.length()));
				return;	
			}

			
			// do Alt Site
			if(a != notFound && a<p  && a<r  &&   a<h) {
				if(a > 0) type(i.substring(0, a));
				System.out.println(GoAlt);
				if(! altSite.startsWith("http"))
					mFrm.getAppletConnector().showWebDocument("http://" + altSite);
				else
					mFrm.getAppletConnector().showWebDocument(altSite);				

				runRobot(i.substring(a+7, i.length()));
				return;	
			}


			// do Pgm
			if(p != notFound  && p<r  &&  p<h) {
				if(p > 0) type(i.substring(0, p));
				System.out.println(GoPgm);
				
				mFrm.runProgram(prgm);
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

	  
 
