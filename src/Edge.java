public class Edge
{
	private float m_x;
	private float m_xStep;
	private int m_yStart;
	private int m_yEnd;

	public float GetX() { return m_x; }
	public int GetYStart() { return m_yStart; }
	public int GetYEnd() { return m_yEnd; }

	public Edge(Vertex minYVert, Vertex maxYVert)
	{
		m_yStart = (int)Math.ceil(minYVert.GetY());
		m_yEnd = (int)Math.ceil(maxYVert.GetY());

		float yDist = maxYVert.GetY() - minYVert.GetY();
		float xDist = maxYVert.GetX() - minYVert.GetX();

		float yPrestep = m_yStart - minYVert.GetY();
		m_xStep = (float)xDist/(float)yDist;
		m_x = minYVert.GetX() + yPrestep * m_xStep;
	}

	public void Step()
	{
		m_x += m_xStep;
	}
}
