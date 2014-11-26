/**
@file
@author Benny Bobaganoosh <thebennybox@gmail.com>
@section LICENSE

Copyright (c) 2014, Benny Bobaganoosh
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * Stores a set of pixels in a component-based format.
 * The component-based format stores colors as follows:
 *
 * Byte 0: Alpha
 * Byte 1: Blue 
 * Byte 2: Green
 * Byte 3: Red
 *
 * This format is fast, compact, and ideal for software rendering.
 * It has the following key advantages:
 * - Entire images can be copied to the screen with a single call to
 * System.arrayCopy. (If the screen is not in ABGR pixel format, it requires
 * some conversion. However, the conversion is typically quick and simple).
 * - Per component operations, such as  lighting, can be performed cheaply without any 
 * pixel format converison.
 *
 * This class is primarily intended to be a high-performance image storing 
 * facility for software rendering. As such, there are points where ease of 
 * use is compromised for the sake of performance. If you need to store and 
 * use images outside of a software renderer, it is recommended that you use 
 * Java's standard image classes instead.
 */
public class Bitmap
{
	/** The width, in pixels, of the image */
	private final int  m_width;
	/** The height, in pixels, of the image */
	private final int  m_height;
	/** Every pixel component in the image */
	private final byte m_components[];

	/** Basic getter */
	public int GetWidth() { return m_width; }
	/** Basic getter */
	public int GetHeight() { return m_height; }

	public byte GetComponent(int index) { return m_components[index]; }

	/**
	 * Creates and initializes a Bitmap.
	 *
	 * @param width The width, in pixels, of the image.
	 * @param height The height, in pixels, of the image.
	 */
	public Bitmap(int width, int height)
	{
		m_width      = width;
		m_height     = height;
		m_components = new byte[m_width * m_height * 4];
	}

	public Bitmap(String fileName) throws IOException
	{
		int width = 0;
		int height = 0;
		byte[] components = null;

		BufferedImage image = ImageIO.read(new File(fileName));

		width = image.getWidth();
		height = image.getHeight();

		int imgPixels[] = new int[width * height];
		image.getRGB(0, 0, width, height, imgPixels, 0, width);
		components = new byte[width * height * 4];

		for(int i = 0; i < width * height; i++)
		{
			int pixel = imgPixels[i];

			components[i * 4]     = (byte)((pixel >> 24) & 0xFF); // A
			components[i * 4 + 1] = (byte)((pixel      ) & 0xFF); // B
			components[i * 4 + 2] = (byte)((pixel >> 8 ) & 0xFF); // G
			components[i * 4 + 3] = (byte)((pixel >> 16) & 0xFF); // R
		}

		m_width = width;
		m_height = height;
		m_components = components;
	}

	/**
	 * Sets every pixel in the bitmap to a specific shade of grey.
	 *
	 * @param shade The shade of grey to use. 0 is black, 255 is white.
	 */
	public void Clear(byte shade)
	{
		Arrays.fill(m_components, shade);
	}

	/**
	 * Sets the pixel at (x, y) to the color specified by (a,b,g,r).
	 *
	 * @param x Pixel location in X
	 * @param y Pixel location in Y
	 * @param a Alpha component
	 * @param b Blue component
	 * @param g Green component
	 * @param r Red component
	 */
	public void DrawPixel(int x, int y, byte a, byte b, byte g, byte r)
	{
		int index = (x + y * m_width) * 4;
		m_components[index    ] = a;
		m_components[index + 1] = b;
		m_components[index + 2] = g;
		m_components[index + 3] = r;
	}

	public void CopyPixel(int destX, int destY, int srcX, int srcY, Bitmap src, float lightAmt)
	{
		int destIndex = (destX + destY * m_width) * 4;
		int srcIndex = (srcX + srcY * src.GetWidth()) * 4;
		
		m_components[destIndex    ] = (byte)((src.GetComponent(srcIndex) & 0xFF) * lightAmt);
		m_components[destIndex + 1] = (byte)((src.GetComponent(srcIndex + 1) & 0xFF) * lightAmt);
		m_components[destIndex + 2] = (byte)((src.GetComponent(srcIndex + 2) & 0xFF) * lightAmt);
		m_components[destIndex + 3] = (byte)((src.GetComponent(srcIndex + 3) & 0xFF) * lightAmt);
	}

	/**
	 * Copies the Bitmap into a BGR byte array.
	 *
	 * @param dest The byte array to copy into.
	 */
	public void CopyToByteArray(byte[] dest)
	{
		for(int i = 0; i < m_width * m_height; i++)
		{
			dest[i * 3    ] = m_components[i * 4 + 1];
			dest[i * 3 + 1] = m_components[i * 4 + 2];
			dest[i * 3 + 2] = m_components[i * 4 + 3];
		}
	}
}
