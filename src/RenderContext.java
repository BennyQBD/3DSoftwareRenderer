public class RenderContext extends Bitmap
{
	private final int m_scanBuffer[];

	public RenderContext(int width, int height)
	{
		super(width, height);
		m_scanBuffer = new int[height * 2];
	}

	public void DrawScanBuffer(int yCoord, int xMin, int xMax)
	{
		m_scanBuffer[yCoord * 2    ] = xMin;
		m_scanBuffer[yCoord * 2 + 1] = xMax;
	}
	
	public void FillShape(int yMin, int yMax)
	{
		for(int j = yMin; j < yMax; j++)
		{
			int xMin = m_scanBuffer[j * 2];
			int xMax = m_scanBuffer[j * 2 + 1];

			for(int i = xMin; i < xMax; i++)
			{
				DrawPixel(i, j, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
			}
		}
	}

	public void FillTriangle(Vertex v1, Vertex v2, Vertex v3)
	{
		Matrix4f screenSpaceTransform = 
				new Matrix4f().InitScreenSpaceTransform(GetWidth()/2, GetHeight()/2);
		Vertex minYVert = v1.Transform(screenSpaceTransform).PerspectiveDivide();
		Vertex midYVert = v2.Transform(screenSpaceTransform).PerspectiveDivide();
		Vertex maxYVert = v3.Transform(screenSpaceTransform).PerspectiveDivide();

		if(maxYVert.GetY() < midYVert.GetY())
		{
			Vertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}

		if(midYVert.GetY() < minYVert.GetY())
		{
			Vertex temp = midYVert;
			midYVert = minYVert;
			minYVert = temp;
		}

		if(maxYVert.GetY() < midYVert.GetY())
		{
			Vertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}

		float area = minYVert.TriangleAreaTimesTwo(maxYVert, midYVert);
		int handedness = area >= 0 ? 1 : 0;

		ScanConvertTriangle(minYVert, midYVert, maxYVert, handedness);
		FillShape((int)Math.ceil(minYVert.GetY()), (int)Math.ceil(maxYVert.GetY()));
	}

	public void ScanConvertTriangle(Vertex minYVert, Vertex midYVert, 
			Vertex maxYVert, int handedness)
	{
		ScanConvertLine(minYVert, maxYVert, 0 + handedness);
		ScanConvertLine(minYVert, midYVert, 1 - handedness);
		ScanConvertLine(midYVert, maxYVert, 1 - handedness);
	}

	private void ScanConvertLine(Vertex minYVert, Vertex maxYVert, int whichSide)
	{
		int yStart = (int)Math.ceil(minYVert.GetY());
		int yEnd   = (int)Math.ceil(maxYVert.GetY());
		int xStart = (int)Math.ceil(minYVert.GetX());
		int xEnd   = (int)Math.ceil(maxYVert.GetX());

		float yDist = maxYVert.GetY() - minYVert.GetY();
		float xDist = maxYVert.GetX() - minYVert.GetX();

		if(yDist <= 0)
		{
			return;
		}

		float xStep = (float)xDist/(float)yDist;
		float yPrestep = yStart - minYVert.GetY();
		float curX = minYVert.GetX() + yPrestep * xStep;

		for(int j = yStart; j < yEnd; j++)
		{
			m_scanBuffer[j * 2 + whichSide] = (int)Math.ceil(curX);
			curX += xStep;
		}	
	}
}
