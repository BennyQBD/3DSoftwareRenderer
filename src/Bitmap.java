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

/**
 * Stores a set of pixels in a component-based format.
 * The component-based format stores colors as follows:
 *
 * Byte 0: Alpha
 * Byte 1: Red 
 * Byte 2: Green
 * Byte 3: Blue
 *
 * This format is fast, compact, and ideal for software rendering.
 * It's most notable advantage is that per component operations, such as 
 * lighting, can be performed cheaply without any pixel format converison.
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
	private final char m_components[];

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
		m_components = new char[m_width * m_height * 4];
	}

	/**
	 * Sets every pixel in the bitmap to a specific shade of grey.
	 */
	public void Clear(char shade)
	{
		Arrays.fill(m_components, shade);
	}

	/**
	 * Sets the pixel at (x, y) to the color specified by (a,r,g,b).
	 */
	public void DrawPixel(int x, int y, char a, char r, char g, char b)
	{
		int index = (x + y * m_width) * 4;
		m_components[index    ] = a;
		m_components[index + 1] = r;
		m_components[index + 2] = g;
		m_components[index + 3] = b;
	}

	/**
	 * Copies the entire bitmap into an integer array, where each integer 
	 * represents one ARGB pixel.
	 */
	public void CopyToIntArray(int[] dest)
	{
		for(int i = 0; i < m_width * m_height; i++)
		{
			int a = ((int)m_components[i * 4    ]) << 24;
			int r = ((int)m_components[i * 4 + 1]) << 16;
			int g = ((int)m_components[i * 4 + 2]) << 8;
			int b = ((int)m_components[i * 4 + 3]);

			dest[i] = a | r | g | b;
		}
	}
}
