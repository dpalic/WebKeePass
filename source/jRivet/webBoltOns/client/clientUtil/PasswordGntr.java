package webBoltOns.client.clientUtil;

/*
 * $Id: PasswordGntr.java,v 1.1 2007/04/20 19:37:17 paujones2005 Exp $ $Name:  $
 *
 * Copyright  2004, 2005, 2006,2007  www.jrivet.com
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

import java.security.SecureRandom;
import java.util.Vector;

public class PasswordGntr {


	private static final int DEFAULT_LENGTH = 8;

	public static final char[] NUMBERS_LETTERS = { 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', };

	public static final char[] SYMBOLS = { '!', '\"', '#', '$', '%',
			'&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<',
			'?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~', };

	public static final char[] PRINTABLE = { '!', '\"', '#', '$', '%',
			'&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '?', '@', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[',
			'\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', };

	public static final char[] LOWERCASE_LETTERS = { 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', };

	public static final char[] LOWERCASE_LETTERS_NUMBERS = { 'a',
			'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0',
			'1', '2', '3', '4', '5', '6', '7', '8', '9', };

	public static final char[] LETTERS = { 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', };

	public static final char[] UPPERCASE_LETTERS = { 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', };

	public static final char[] DEFAULT_LETTERS_NUMBERS = { 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
			'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
			'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z', '2',
			'3', '4', '5', '6', '7', '8', '9','1' };

	public static final char[] UPPERCASE_LETTERS_NUMBERS = { 'A', 'B', 'C',
		'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
		'3', '4', '5', '6', '7', '8', '9', }; 
	
	
	protected SecureRandom rand;

	protected int repetition = -1;

	protected char[] alphabet;

	protected char[] firstAlphabet;

	protected char[] lastAlphabet;

	public PasswordGntr() {
		this(new SecureRandom(), DEFAULT_LETTERS_NUMBERS);
	}

	public PasswordGntr(SecureRandom rand) {
		this(rand, DEFAULT_LETTERS_NUMBERS);
	}

	public PasswordGntr(char[] alphabet) {
		this(new SecureRandom(), alphabet);
	}

	public PasswordGntr(SecureRandom rand, char[] alphabet) {
		this.rand = rand;
		this.alphabet = alphabet;
	}

	private class Requirement {
		public Requirement(char[] alphabet, int num) {
			this.alphabet = alphabet;
			this.num = num;
		}

		public char[] alphabet;

		public int num;
	}

	private Vector<Requirement> requirements = null;

	public void addRequirement(char[] alphabet, int num) {
		if (requirements == null)
			requirements = new Vector<Requirement>();
		requirements.add(new Requirement(alphabet, num));
	}

	public void setAlphabet(char[] alphabet) {
		if (alphabet == null)
			throw new NullPointerException("Null alphabet");
		if (alphabet.length == 0)
			throw new ArrayIndexOutOfBoundsException(
					"No characters in alphabet");
		this.alphabet = alphabet;
	}

	public void setRandomGenerator(SecureRandom rand) {
		this.rand = rand;
	}

	public void setFirstAlphabet(char[] alphabet) {
		if (alphabet == null || alphabet.length == 0) {
			this.firstAlphabet = null;
		} else {
			this.firstAlphabet = alphabet;
		}
	}

	public void setLastAlphabet(char[] alphabet) {
		if (alphabet == null || alphabet.length == 0) {
			this.lastAlphabet = null;
		} else {
			this.lastAlphabet = alphabet;
		}
	}

	public void setMaxRepetition(int rep) {
		this.repetition = rep - 1;
	}

	public char[] getPassChars(char[] pass) {
		boolean verified = false;
		while (!verified) {
			int length = pass.length;
			for (int i = 0; i < length; i++) {
				char[] useAlph = alphabet;
				if (i == 0 && firstAlphabet != null) {
					useAlph = firstAlphabet;
				} else if (i == length - 1 && lastAlphabet != null) {
					useAlph = lastAlphabet;
				}
				int size = avoidRepetition(useAlph, pass, i, repetition,
						useAlph.length);
				pass[i] = useAlph[rand.nextInt(size)];
			}
			if (requirements != null)
				applyRequirements(pass);
			verified = true;
			for (int i = 0; verified && verifiers != null
					&& i < verifiers.size(); i++) {
				verified = (verifiers.elementAt(i)).verify(pass);
			}
		}
		return (pass);
	}

	private Vector<PasswordVerifier> verifiers = null;

	public void addVerifier(PasswordVerifier verifier) {
		if (verifiers == null)
			verifiers = new Vector<PasswordVerifier>();
		verifiers.add(verifier);
	}

	private boolean[] touched = null;

	private int[] available = null;

	private void applyRequirements(char[] pass) {
		int size = requirements.size();
		if (size > 0) {
			int length = pass.length;
			if (touched == null || touched.length < length)
				touched = new boolean[length];
			if (available == null || available.length < length)
				available = new int[length];
			for (int i = 0; i < length; i++) {
				touched[i] = false;
			}
			for (int reqNum = 0; reqNum < size; reqNum++) {
				Requirement req = requirements.elementAt(reqNum);

				int reqUsedInd = req.alphabet.length;

				int fufilledInd = 0;
				int availableInd = 0;
				for (int i = 0; i < length; i++) {
					if (arrayContains(req.alphabet, pass[i])
							&& fufilledInd < req.num) {
						fufilledInd++;
						touched[i] = true;
						if (repetition >= 0) {

							reqUsedInd -= moveto(req.alphabet, reqUsedInd,
									pass[i]);

							if (reqUsedInd < 0)
								reqUsedInd = req.alphabet.length;
						}
					} else if (!touched[i]) {
						available[availableInd] = i;
						availableInd++;
					}
				}

				int toDo = req.num - fufilledInd;
				for (int i = 0; i < toDo && availableInd > 0; i++) {

					int slot = rand.nextInt(availableInd);
					char passChar = req.alphabet[rand.nextInt(reqUsedInd)];
					pass[available[slot]] = passChar;
					touched[available[slot]] = true;

					availableInd--;
					available[slot] = available[availableInd];
					if (repetition >= 0) {

						reqUsedInd -= moveto(req.alphabet, reqUsedInd, passChar);

						if (reqUsedInd < 0)
							reqUsedInd = req.alphabet.length;
					}
				}
			}
		}
	}

	private static boolean arrayContains(char[] alph, char c) {
		for (int i = 0; i < alph.length; i++) {
			if (alph[i] == c)
				return true;
		}
		return false;
	}

	private static int avoidRepetition(char[] alph, char[] pass, int passSize,
			int repetition, int alphSize) {
		if (repetition > -1) {

			int repPos = 0;
			while ((repPos = findRep(pass, repPos, passSize, repetition)) != -1) {

				alphSize -= moveto(alph, alphSize, pass[repPos + repetition]);
				repPos++;
			}
			if (alphSize == 0)
				alphSize = alph.length;
		}
		return alphSize;
	}

	private static int findRep(char[] pass, int start, int end, int length) {
		for (int i = start; i < end - length; i++) {
			boolean onTrack = true;
			for (int j = 0; onTrack && j < length; j++) {
				if (pass[i + j] != pass[end - length + j])
					onTrack = false;
			}
			if (onTrack)
				return i;
		}
		return -1;
	}

	private static int moveto(char[] alph, int numGood, char c) {
		int count = 0;
		for (int i = 0; i < numGood; i++) {
			if (alph[i] == c) {
				numGood--;
				char temp = alph[numGood];
				alph[numGood] = alph[i];
				alph[i] = temp;
				count++;
			}
		}
		return count;
	}

	public char[] getPassChars(int length) {
		return (getPassChars(new char[length]));
	}

	public char[] getPassChars() {
		return (getPassChars(DEFAULT_LENGTH));
	}

	public String getPass(int length) {
		return (new String(getPassChars(new char[length])));
	}

	public String getPass() {
		return (getPass(DEFAULT_LENGTH));
	}
}
