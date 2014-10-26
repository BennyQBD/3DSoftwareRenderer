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
	private Vector4f m_pos;
	
	/** Basic Getter */
	public float GetX() { return m_pos.GetX(); }
	/** Basic Getter */
	public float GetY() { return m_pos.GetY(); }

	/**
	 * Creates a new Vertex in a usable state.
	 *
	 * @param x Location on X
	 * @param y Location on Y
	 */
	public Vertex(float x, float y, float z)
	{
		m_pos = new Vector4f(x, y, z, 1);
	}

	public Vertex(Vector4f pos)
	{
		m_pos = pos;
	}

	public Vertex Transform(Matrix4f transform)
	{
		return new Vertex(transform.Transform(m_pos));
	}

	public Vertex PerspectiveDivide()
	{
		return new Vertex(new Vector4f(m_pos.GetX()/m_pos.GetW(), m_pos.GetY()/m_pos.GetW(), 
						m_pos.GetZ()/m_pos.GetW(), m_pos.GetW()));
	}

	public float TriangleAreaTimesTwo(Vertex b, Vertex c)
	{
		float x1 = b.GetX() - m_pos.GetX();
		float y1 = b.GetY() - m_pos.GetY();

		float x2 = c.GetX() - m_pos.GetX();
		float y2 = c.GetY() - m_pos.GetY();

		return (x1 * y2 - x2 * y1);
	}
}
