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

	public void ScanConvertTriangle(Vertex minYVert, Vertex midYVert, 
			Vertex maxYVert, int handedness)
	{
		ScanConvertLine(minYVert, maxYVert, 0 + handedness);
		ScanConvertLine(minYVert, midYVert, 1 - handedness);
		ScanConvertLine(midYVert, maxYVert, 1 - handedness);
	}

	private void ScanConvertLine(Vertex minYVert, Vertex maxYVert, int whichSide)
	{
		int yStart = (int)minYVert.GetY();
		int yEnd   = (int)maxYVert.GetY();
		int xStart = (int)minYVert.GetX();
		int xEnd   = (int)maxYVert.GetX();

		int yDist = yEnd - yStart;
		int xDist = xEnd - xStart;

		if(yDist <= 0)
		{
			return;
		}

		float xStep = (float)xDist/(float)yDist;
		float curX = (float)xStart;

		for(int j = yStart; j < yEnd; j++)
		{
			m_scanBuffer[j * 2 + whichSide] = (int)curX;
			curX += xStep;
		}
	}
}
