public class Edge
{
	private float m_x;
	private float m_xStep;
	private int m_yStart;
	private int m_yEnd;
	private float m_texCoordX;
	private float m_texCoordXStep;
	private float m_texCoordY;
	private float m_texCoordYStep;

	public float GetX() { return m_x; }
	public int GetYStart() { return m_yStart; }
	public int GetYEnd() { return m_yEnd; }
	public float GetTexCoordX() { return m_texCoordX; }
	public float GetTexCoordY() { return m_texCoordY; }

	public Edge(Gradients gradients, Vertex minYVert, Vertex maxYVert, int minYVertIndex)
	{
		m_yStart = (int)Math.ceil(minYVert.GetY());
		m_yEnd = (int)Math.ceil(maxYVert.GetY());

		float yDist = maxYVert.GetY() - minYVert.GetY();
		float xDist = maxYVert.GetX() - minYVert.GetX();

		float yPrestep = m_yStart - minYVert.GetY();
		m_xStep = (float)xDist/(float)yDist;
		m_x = minYVert.GetX() + yPrestep * m_xStep;
		float xPrestep = m_x - minYVert.GetX();

		m_texCoordX = gradients.GetTexCoordX(minYVertIndex) +
			gradients.GetTexCoordXXStep() * xPrestep +
			gradients.GetTexCoordXYStep() * yPrestep;
		m_texCoordXStep = gradients.GetTexCoordXYStep() + gradients.GetTexCoordXXStep() * m_xStep;

		m_texCoordY = gradients.GetTexCoordY(minYVertIndex) +
			gradients.GetTexCoordYXStep() * xPrestep +
			gradients.GetTexCoordYYStep() * yPrestep;
		m_texCoordYStep = gradients.GetTexCoordYYStep() + gradients.GetTexCoordYXStep() * m_xStep;
	}

	public void Step()
	{
		m_x += m_xStep;
		m_texCoordX += m_texCoordXStep;
		m_texCoordY += m_texCoordYStep;
	}
}
