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
 * The sole purpose of this class is to hold the main method.
 *
 * Any other use should be placed in a separate class
 */
public class Main
{
	public static void main(String[] args)
	{
		Display display = new Display(800, 600, "Software Rendering");
		RenderContext target = display.GetFrameBuffer();
		Stars3D stars = new Stars3D(3, 64.0f, 4.0f);

		Vertex minYVert = new Vertex(new Vector4f(-1, -1, 0, 1), 
		                             new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex midYVert = new Vertex(new Vector4f(0, 1, 0, 1), 
		                             new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex maxYVert = new Vertex(new Vector4f(1, -1, 0, 1), 
		                             new Vector4f(0.0f, 0.0f, 1.0f, 0.0f));

		Matrix4f projection = new Matrix4f().InitPerspective((float)Math.toRadians(70.0f),
					   	(float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f);
	 
		float rotCounter = 0.0f;
		long previousTime = System.nanoTime();
		while(true)
		{
			long currentTime = System.nanoTime();
			float delta = (float)((currentTime - previousTime)/1000000000.0);
			previousTime = currentTime;

			//stars.UpdateAndRender(target, delta);

			rotCounter += delta;
			Matrix4f translation = new Matrix4f().InitTranslation(0.0f, 0.0f, 3.0f);
			Matrix4f rotation = new Matrix4f().InitRotation(0.0f, rotCounter, 0.0f);
			Matrix4f transform = projection.Mul(translation.Mul(rotation));

			target.Clear((byte)0x00);
			target.FillTriangle(maxYVert.Transform(transform), 
							midYVert.Transform(transform), minYVert.Transform(transform));

			display.SwapBuffers();
		}
	}
}
