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

/**
 * Represents a 3D Star field that can be rendered into an image.
 */
public class Stars3D
{
	/** How much the stars are spread out in 3D space, on average. */
	private final float m_spread;
	/** How quickly the stars move towards the camera */
	private final float m_speed;

	/** The star positions on the X axis */
	private final float m_starX[];
	/** The star positions on the Y axis */
	private final float m_starY[];
	/** The star positions on the Z axis */
	private final float m_starZ[];

	/**
	 * Creates a new 3D star field in a usable state.
	 *
	 * @param numStars The number of stars in the star field
	 * @param spread   How much the stars spread out, on average.
	 * @param speed    How quickly the stars move towards the camera
	 */
	public Stars3D(int numStars, float spread, float speed)
	{
		m_spread = spread;
		m_speed = speed;

		m_starX = new float[numStars];
		m_starY = new float[numStars];
		m_starZ = new float[numStars];

		for(int i = 0; i < m_starX.length; i++)
		{
			InitStar(i);
		}
	}

	/**
	 * Initializes a star to a new pseudo-random location in 3D space.
	 *
	 * @param i The index of the star to initialize.
	 */
	private void InitStar(int i)
	{
		//The random values have 0.5 subtracted from them and are multiplied
		//by 2 to remap them from the range (0, 1) to (-1, 1).
		m_starX[i] = 2 * ((float)Math.random() - 0.5f) * m_spread;
		m_starY[i] = 2 * ((float)Math.random() - 0.5f) * m_spread;
		//For Z, the random value is only adjusted by a small amount to stop
		//a star from being generated at 0 on Z.
		m_starZ[i] = ((float)Math.random() + 0.00001f) * m_spread;
	}

	/**
	 * Updates every star to a new position, and draws the starfield in a
	 * bitmap.
	 *
	 * @param target The bitmap to render to.
	 * @param delta  How much time has passed since the last update.
	 */
	public void UpdateAndRender(Bitmap target, float delta)
	{
		final float tanHalfFOV = (float)Math.tan(Math.toRadians(90.0/2.0));
		//Stars are drawn on a black background
		target.Clear((byte)0x00);

		float halfWidth  = target.GetWidth()/2.0f;
		float halfHeight = target.GetHeight()/2.0f;
		for(int i = 0; i < m_starX.length; i++)
		{
			//Update the Star.

			//Move the star towards the camera which is at 0 on Z.
			m_starZ[i] -= delta * m_speed;

			//If star is at or behind the camera, generate a new position for
			//it.
			if(m_starZ[i] <= 0)
			{
				InitStar(i);
			}

			//Render the Star.

			//Multiplying the position by (size/2) and then adding (size/2)
			//remaps the positions from range (-1, 1) to (0, size)

			//Division by z*tanHalfFOV moves things in to create a perspective effect.
			int x = (int)((m_starX[i]/(m_starZ[i] * tanHalfFOV)) * halfWidth + halfWidth);
			int y = (int)((m_starY[i]/(m_starZ[i] * tanHalfFOV)) * halfHeight + halfHeight);
//
//			int x = (int)((m_starX[i]) * halfWidth + halfWidth);
//			int y = (int)((m_starY[i]) * halfHeight + halfHeight);


			//If the star is not within range of the screen, then generate a
			//new position for it.
			if(x < 0 || x >= target.GetWidth() ||
				(y < 0 || y >= target.GetHeight()))
			{
				InitStar(i);
			}
			else
			{
				//Otherwise, it is safe to draw this star to the screen.
				target.DrawPixel(x, y, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
			}
		}
	}
}
