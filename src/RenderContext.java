public class RenderContext extends Bitmap
{
	public RenderContext(int width, int height)
	{
		super(width, height);
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

		ScanTriangle(minYVert, midYVert, maxYVert, 
				minYVert.TriangleAreaTimesTwo(maxYVert, midYVert) >= 0);
	}

	private void ScanTriangle(Vertex minYVert, Vertex midYVert, 
			Vertex maxYVert, boolean handedness)
	{
		Edge topToBottom    = new Edge(minYVert, maxYVert);
		Edge topToMiddle    = new Edge(minYVert, midYVert);
		Edge middleToBottom = new Edge(midYVert, maxYVert);

		ScanEdges(topToBottom, topToMiddle, handedness);
		ScanEdges(topToBottom, middleToBottom, handedness);
	}

	private void ScanEdges(Edge a, Edge b, boolean handedness)
	{
		Edge left = a;
		Edge right = b;
		if(handedness)
		{
			Edge temp = left;
			left = right;
			right = temp;
		}

		int yStart = b.GetYStart();
		int yEnd   = b.GetYEnd();
		for(int j = yStart; j < yEnd; j++)
		{
			DrawScanLine(left, right, j);
			left.Step();
			right.Step();
		}
	}

	private void DrawScanLine(Edge left, Edge right, int j)
	{
		int xMin = (int)Math.ceil(left.GetX());
		int xMax = (int)Math.ceil(right.GetX());

		for(int i = xMin; i < xMax; i++)
		{
			DrawPixel(i, j, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
		}
	}
}
