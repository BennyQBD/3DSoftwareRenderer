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
	private Vector4f m_texCoords;
	private Vector4f m_normal;

	/** Basic Getter */
	public float GetX() { return m_pos.GetX(); }
	/** Basic Getter */
	public float GetY() { return m_pos.GetY(); }

	public Vector4f GetPosition() { return m_pos; }
	public Vector4f GetTexCoords() { return m_texCoords; }
	public Vector4f GetNormal() { return m_normal; }

	/**
	 * Creates a new Vertex in a usable state.
	 */
	public Vertex(Vector4f pos, Vector4f texCoords, Vector4f normal)
	{
		m_pos = pos;
		m_texCoords = texCoords;
		m_normal = normal;
	}

	public Vertex Transform(Matrix4f transform, Matrix4f normalTransform)
	{
		// The normalized here is important if you're doing scaling.
		return new Vertex(transform.Transform(m_pos), m_texCoords, 
				normalTransform.Transform(m_normal).Normalized());
	}

	public Vertex PerspectiveDivide()
	{
		return new Vertex(new Vector4f(m_pos.GetX()/m_pos.GetW(), m_pos.GetY()/m_pos.GetW(), 
						m_pos.GetZ()/m_pos.GetW(), m_pos.GetW()),	
				m_texCoords, m_normal);
	}

	public float TriangleAreaTimesTwo(Vertex b, Vertex c)
	{
		float x1 = b.GetX() - m_pos.GetX();
		float y1 = b.GetY() - m_pos.GetY();

		float x2 = c.GetX() - m_pos.GetX();
		float y2 = c.GetY() - m_pos.GetY();

		return (x1 * y2 - x2 * y1);
	}

	public Vertex Lerp(Vertex other, float lerpAmt)
	{
		return new Vertex(
				m_pos.Lerp(other.GetPosition(), lerpAmt),
				m_texCoords.Lerp(other.GetTexCoords(), lerpAmt),
				m_normal.Lerp(other.GetNormal(), lerpAmt)
				);
	}

	public boolean IsInsideViewFrustum()
	{
		return 
			Math.abs(m_pos.GetX()) <= Math.abs(m_pos.GetW()) &&
			Math.abs(m_pos.GetY()) <= Math.abs(m_pos.GetW()) &&
			Math.abs(m_pos.GetZ()) <= Math.abs(m_pos.GetW());
	}

	public float Get(int index)
	{
		switch(index)
		{
			case 0:
				return m_pos.GetX();
			case 1:
				return m_pos.GetY();
			case 2:
				return m_pos.GetZ();
			case 3:
				return m_pos.GetW();
			default:
				throw new IndexOutOfBoundsException();
		}
	}
}
