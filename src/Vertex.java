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
public class Vertex
{
	/** Location in X */
	private float m_x;
	/** Location in Y */
	private float m_y;

	/** Basic Getter */
	public float GetX() { return m_x; }
	/** Basic Getter */
	public float GetY() { return m_y; }

	/** Basic Setter */
	public void SetX(float x) { m_x = x; }
	/** Basic Setter */
	public void SetY(float y) { m_y = y; }

	/**
	 * Creates a new Vertex in a usable state.
	 *
	 * @param x Location on X
	 * @param y Location on Y
	 */
	public Vertex(float x, float y)
	{
		m_x = x;
		m_y = y;
	}

	public float TriangleArea(Vertex b, Vertex c)
	{
		float x1 = b.GetX() - m_x;
		float y1 = b.GetY() - m_y;

		float x2 = c.GetX() - m_x;
		float y2 = c.GetY() - m_y;

		return (x1 * y2 - x2 * y1);
	}
}
