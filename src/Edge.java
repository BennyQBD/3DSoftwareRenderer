public class Edge
{
	private float m_x;
	private float m_xStep;
	private int m_yStart;
	private int m_yEnd;
	private Vector4f m_color;
	private Vector4f m_colorStep;

	public float GetX() { return m_x; }
	public int GetYStart() { return m_yStart; }
	public int GetYEnd() { return m_yEnd; }
	public Vector4f GetColor() { return m_color; }

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

		m_color = gradients.GetColor(minYVertIndex).Add(
				gradients.GetColorYStep().Mul(yPrestep)).Add(
				gradients.GetColorXStep().Mul(xPrestep));
		m_colorStep = gradients.GetColorYStep().Add(gradients.GetColorXStep().Mul(m_xStep));
	}

	public void Step()
	{
		m_x += m_xStep;
		m_color = m_color.Add(m_colorStep);
	}
}
