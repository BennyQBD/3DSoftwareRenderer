public class Gradients
{
	private Vector4f[] m_color;
	private Vector4f m_colorXStep;
	private Vector4f m_colorYStep;

	public Vector4f GetColor(int loc) { return m_color[loc]; }
	public Vector4f GetColorXStep() { return m_colorXStep; }
	public Vector4f GetColorYStep() { return m_colorYStep; }

	public Gradients(Vertex minYVert, Vertex midYVert, Vertex maxYVert)
	{
		m_color = new Vector4f[3];

		m_color[0] = minYVert.GetColor();
		m_color[1] = midYVert.GetColor();
		m_color[2] = maxYVert.GetColor();

		float oneOverdX = 1.0f /
			(((midYVert.GetX() - maxYVert.GetX()) *
			(minYVert.GetY() - maxYVert.GetY())) -
			((minYVert.GetX() - maxYVert.GetX()) *
			(midYVert.GetY() - maxYVert.GetY())));

		float oneOverdY = -oneOverdX;

		m_colorXStep =  
			(((m_color[1].Sub(m_color[2])).Mul(
			(minYVert.GetY() - maxYVert.GetY()))).Sub(
			((m_color[0].Sub(m_color[2])).Mul(
			(midYVert.GetY() - maxYVert.GetY()))))).Mul(oneOverdX);

		m_colorYStep =  
			(((m_color[1].Sub(m_color[2])).Mul(
			(minYVert.GetX() - maxYVert.GetX()))).Sub(
			((m_color[0].Sub(m_color[2])).Mul(
			(midYVert.GetX() - maxYVert.GetX()))))).Mul(oneOverdY);
	}
}
