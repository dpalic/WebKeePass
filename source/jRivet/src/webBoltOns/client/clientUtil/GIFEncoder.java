package webBoltOns.client.clientUtil;

/*
 * $Id: GIFEncoder.java,v 1.1 2007/04/20 19:37:17 paujones2005 Exp $ $Name:  $
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
import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GIFEncoder {

	public static boolean encode(OutputStream out, JLabel imageLabel) {
		return encode(out, ((ImageIcon) imageLabel.getIcon()).getImage());
	}

	
	public static boolean encode(OutputStream out, Image image) {
		short imageWidth = (short) image.getWidth(null);
		short imageHeight = (short) image.getHeight(null);
		int numberOfColors = 0;
		byte[] allPixels = null;
		byte[] allColors = null;

		int values[] = new int[imageWidth * imageHeight];
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, imageWidth,
				imageHeight, values, 0, imageWidth);

		try {
			if (grabber.grabPixels() != true) {
			
				return false;
			}
		} catch (InterruptedException ie) {
			 
			return false;
		}

		byte[][] r = new byte[imageWidth][imageHeight];
		byte[][] g = new byte[imageWidth][imageHeight];
		byte[][] b = new byte[imageWidth][imageHeight];
		int index = 0;

		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++, index++) {
				r[x][y] = (byte) ((values[index] >> 16) & 0xFF);
				g[x][y] = (byte) ((values[index] >> 8) & 0xFF);
				b[x][y] = (byte) ((values[index]) & 0xFF);
			}
		}

		try {
			allPixels = new byte[imageWidth * imageHeight];
			allColors = new byte[256 * 3];
			int colornum = 0;
			for (int x = 0; x < imageWidth; x++) {
				for (int y = 0; y < imageHeight; y++) {
					int search;
					for (search = 0; search < colornum; search++) {
						if (allColors[search * 3] == r[x][y]
								&& allColors[search * 3 + 1] == g[x][y]
								&& allColors[search * 3 + 2] == b[x][y]) {
							break;
						}
					}

					if (search > 255)
						throw new AWTException("Too many colors.");
					// row major order y=row x=col
					allPixels[y * imageWidth + x] = (byte) search;

					if (search == colornum) {
						allColors[search * 3] = r[x][y]; // [col][row]
						allColors[search * 3 + 1] = g[x][y];
						allColors[search * 3 + 2] = b[x][y];
						colornum++;
					}
				}
			}

			numberOfColors = 1 << BitUtils.BitsNeeded(colornum);
			byte copy[] = new byte[numberOfColors * 3];
			System.arraycopy(allColors, 0, copy, 0, numberOfColors * 3);
			allColors = copy;
		} catch (AWTException e) {
			e.printStackTrace();
			return false;
		}

		try {
			BitUtils.writeString(out, "GIF87a");

			ScreenDescriptor sd = new ScreenDescriptor(imageWidth, imageHeight,
					numberOfColors);
			sd.write(out);
			out.write(allColors, 0, allColors.length);
			ImageDescriptor id = new ImageDescriptor(imageWidth, imageHeight,
					',');
			id.write(out);

			byte codesize = BitUtils.BitsNeeded(numberOfColors);
			if (codesize == 1)
				codesize++;

			out.write(codesize);

			LZWCompressor.LZWCompress(out, codesize, allPixels);
			out.write(0);

			id = new ImageDescriptor((byte) 0, (byte) 0, ';');
			id.write(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}

/**
 * Used to write the bits composing the GIF image.
 */
class BitFile  {

	OutputStream output = null;

	byte[] buffer;

	int indexIntoOutputStream, bitsLeft;

	public BitFile(OutputStream output) {
		this.output = output;
		this.buffer = new byte[256];
		this.indexIntoOutputStream = 0;
		this.bitsLeft = 8;
	}  

	public void flush() throws IOException {
		int numBytes = this.indexIntoOutputStream
				+ ((this.bitsLeft == 8) ? 0 : 1);
		if (numBytes > 0) {
			this.output.write(numBytes);
			this.output.write(this.buffer, 0, numBytes);
			this.buffer[0] = 0;
			this.indexIntoOutputStream = 0;
			this.bitsLeft = 8;
		}
	}

	public void writeBits(int bits, int numbits) throws IOException {
		int bitsWritten = 0;
		int numBytes = 255;
		do {
			if ((this.indexIntoOutputStream == 254 && this.bitsLeft == 0)
					|| this.indexIntoOutputStream > 254) {
				this.output.write(numBytes);
				this.output.write(this.buffer, 0, numBytes);

				this.buffer[0] = 0;
				this.indexIntoOutputStream = 0;
				this.bitsLeft = 8;
			}
			if (numbits <= this.bitsLeft) {
				this.buffer[this.indexIntoOutputStream] |= (bits & ((1 << numbits) - 1)) << (8 - this.bitsLeft);
				bitsWritten += numbits;
				this.bitsLeft -= numbits;
				numbits = 0;
			}

			else {
				this.buffer[this.indexIntoOutputStream] |= (bits & ((1 << this.bitsLeft) - 1)) << (8 - this.bitsLeft);
				bitsWritten += this.bitsLeft;
				bits >>= this.bitsLeft;
				numbits -= this.bitsLeft;
				this.buffer[++this.indexIntoOutputStream] = 0;
				this.bitsLeft = 8;
			}

		} while (numbits != 0);

	}

}

class LZWStringTable  {
	private final static int RES_CODES = 2;

	private final static short HASH_FREE = (short) 0xFFFF;

	private final static short NEXT_FIRST = (short) 0xFFFF;

	private final static int MAXBITS = 12;

	private final static int MAXSTR = (1 << MAXBITS);

	private final static short HASHSIZE = 9973;

	private final static short HASHSTEP = 2039;

	byte strChr_[];

	short strNxt_[];

	short strHsh_[];

	short numStrings_;

	public LZWStringTable() {
		strChr_ = new byte[MAXSTR];
		strNxt_ = new short[MAXSTR];
		strHsh_ = new short[HASHSIZE];
	}  

	public int addCharString(short index, byte b) {
		int hshidx;
		if (numStrings_ >= MAXSTR)
			return 0xFFFF;

		hshidx = Hash(index, b);
		while (strHsh_[hshidx] != HASH_FREE)
			hshidx = (hshidx + HASHSTEP) % HASHSIZE;

		strHsh_[hshidx] = numStrings_;
		strChr_[numStrings_] = b;
		strNxt_[numStrings_] = (index != HASH_FREE) ? index : NEXT_FIRST;

		return numStrings_++;
	}  

	public short findCharString(short index, byte b) {
		int hshidx, nxtidx;

		if (index == HASH_FREE)
			return b;

		hshidx = Hash(index, b);
		while ((nxtidx = strHsh_[hshidx]) != HASH_FREE) {
			if (strNxt_[nxtidx] == index && strChr_[nxtidx] == b)
				return (short) nxtidx;
			hshidx = (hshidx + HASHSTEP) % HASHSIZE;
		}  

		return (short) 0xFFFF;
	}  

	public void ClearTable(int codesize) {
		numStrings_ = 0;

		for (int q = 0; q < HASHSIZE; q++)
			strHsh_[q] = HASH_FREE;

		int w = (1 << codesize) + RES_CODES;
		for (int q = 0; q < w; q++)
			this.addCharString((short) 0xFFFF, (byte) q);
	}  

	public static int Hash(short index, byte lastbyte) {
		return ((int) ((short) (lastbyte << 8) ^ index) & 0xFFFF) % HASHSIZE;
	}

}  

 
class LZWCompressor  {
	public static void LZWCompress(OutputStream output, int codesize,
			byte toCompress[]) throws IOException {
		byte c;
		short index;
		int clearcode, endofinfo, numbits, limit;
		short prefix = (short) 0xFFFF;

		BitFile bitFile = new BitFile(output);
		LZWStringTable strings = new LZWStringTable();

		clearcode = 1 << codesize;
		endofinfo = clearcode + 1;

		numbits = codesize + 1;
		limit = (1 << numbits) - 1;

		strings.ClearTable(codesize);
		bitFile.writeBits(clearcode, numbits);

		for (int loop = 0; loop < toCompress.length; loop++) {
			c = toCompress[loop];
			if ((index = strings.findCharString(prefix, c)) != -1)
				prefix = index;
			else {
				bitFile.writeBits(prefix, numbits);
				if (strings.addCharString(prefix, c) > limit) {
					if (++numbits > 12) {
						bitFile.writeBits(clearcode, numbits - 1);
						strings.ClearTable(codesize);
						numbits = codesize + 1;
					}  

					limit = (1 << numbits) - 1;
				}  

				prefix = (short) ((short) c & 0xFF);
			}  

		} 

		if (prefix != -1)
			bitFile.writeBits(prefix, numbits);

		bitFile.writeBits(endofinfo, numbits);
		bitFile.flush();
	}  

}  

 
class ScreenDescriptor   {
	public short localScreenWidth, localScreenHeight;

	private byte currentByte;

	public byte backgroundColorIndex, pixelAspectRatio;

	/**
	 * tool for dumping current screen image??
	 * 
	 * @param width
	 * @param height
	 * @param numColors
	 */
	public ScreenDescriptor(short width, short height, int numColors) {
		this.localScreenWidth = width;
		this.localScreenHeight = height;
		SetGlobalColorTableSize((byte) (BitUtils.BitsNeeded(numColors) - 1));
		SetGlobalColorTableFlag((byte) 1);
		SetSortFlag((byte) 0);
		SetColorResolution((byte) 7);
		this.backgroundColorIndex = 0;
		this.pixelAspectRatio = 0;
	}  

	public void write(OutputStream output) throws IOException {
		BitUtils.writeWord(output, this.localScreenWidth);
		BitUtils.writeWord(output, this.localScreenHeight);
		output.write(this.currentByte);
		output.write(this.backgroundColorIndex);
		output.write(this.pixelAspectRatio);
	}  

	public void SetGlobalColorTableSize(byte num) {
		this.currentByte |= (num & 7);
	}

	public void SetSortFlag(byte num) {
		this.currentByte |= (num & 1) << 3;
	}

	public void SetColorResolution(byte num) {
		this.currentByte |= (num & 7) << 4;
	}

	public void SetGlobalColorTableFlag(byte num) {
		this.currentByte |= (num & 1) << 7;
	}

} 

 
class ImageDescriptor extends Object {
	public byte separator_;

	public short leftPosition, topPosition, imageWidth, imageHeight;

	private byte currentByte;

	/**
	 * ???
	 * 
	 * @param width
	 * @param height
	 * @param separator
	 */
	public ImageDescriptor(short width, short height, char separator) {
		separator_ = (byte) separator;
		this.leftPosition = 0;
		this.topPosition = 0;
		this.imageWidth = width;
		this.imageHeight = height;
		SetLocalColorTableSize((byte) 0);
		SetReserved((byte) 0);
		SetSortFlag((byte) 0);
		SetInterlaceFlag((byte) 0);
		SetLocalColorTableFlag((byte) 0);
	}  

	public void write(OutputStream output) throws IOException {
		output.write(separator_);
		BitUtils.writeWord(output, this.leftPosition);
		BitUtils.writeWord(output, this.topPosition);
		BitUtils.writeWord(output, this.imageWidth);
		BitUtils.writeWord(output, this.imageHeight);
		output.write(this.currentByte);
	}  

	public void SetLocalColorTableSize(byte num) {
		this.currentByte |= (num & 7);
	}

	public void SetReserved(byte num) {
		this.currentByte |= (num & 3) << 3;
	}

	public void SetSortFlag(byte num) {
		this.currentByte |= (num & 1) << 5;
	}

	public void SetInterlaceFlag(byte num) {
		this.currentByte |= (num & 1) << 6;
	}

	public void SetLocalColorTableFlag(byte num) {
		this.currentByte |= (num & 1) << 7;
	}

}  
	class BitUtils extends Object {
 
	public static byte BitsNeeded(int n) {
		byte ret = 1;

		if (n-- == 0)
			return 0;

		while ((n >>= 1) != 0)
			ret++;

		return ret;
	}  

	public static void writeWord(OutputStream output, short w)
			throws IOException {
		output.write(w & 0xFF);
		output.write((w >> 8) & 0xFF);
	}  

	static void writeString(OutputStream output, String string)
			throws IOException {
		for (int loop = 0; loop < string.length(); loop++) {
			output.write((byte) (string.charAt(loop)));
		}
	}  

	}  

