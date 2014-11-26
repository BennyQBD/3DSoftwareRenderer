public class Gradients
{
	private float[] m_texCoordX;
	private float[] m_texCoordY;
	private float[] m_oneOverZ;
	private float[] m_depth;
	private float[] m_lightAmt;

	private float m_texCoordXXStep;
	private float m_texCoordXYStep;
	private float m_texCoordYXStep;
	private float m_texCoordYYStep;
	private float m_oneOverZXStep;
	private float m_oneOverZYStep;
	private float m_depthXStep;
	private float m_depthYStep;
	private float m_lightAmtXStep;
	private float m_lightAmtYStep;

	public float GetTexCoordX(int loc) { return m_texCoordX[loc]; }
	public float GetTexCoordY(int loc) { return m_texCoordY[loc]; }
	public float GetOneOverZ(int loc) { return m_oneOverZ[loc]; }
	public float GetDepth(int loc) { return m_depth[loc]; }
	public float GetLightAmt(int loc) { return m_lightAmt[loc]; }

	public float GetTexCoordXXStep() { return m_texCoordXXStep; }
	public float GetTexCoordXYStep() { return m_texCoordXYStep; }
	public float GetTexCoordYXStep() { return m_texCoordYXStep; }
	public float GetTexCoordYYStep() { return m_texCoordYYStep; }
	public float GetOneOverZXStep() { return m_oneOverZXStep; }
	public float GetOneOverZYStep() { return m_oneOverZYStep; }
	public float GetDepthXStep() { return m_depthXStep; }
	public float GetDepthYStep() { return m_depthYStep; }
	public float GetLightAmtXStep() { return m_lightAmtXStep; }
	public float GetLightAmtYStep() { return m_lightAmtYStep; }

	private float CalcXStep(float[] values, Vertex minYVert, Vertex midYVert,
			Vertex maxYVert, float oneOverdX)
	{
		return
			(((values[1] - values[2]) *
			(minYVert.GetY() - maxYVert.GetY())) -
			((values[0] - values[2]) *
			(midYVert.GetY() - maxYVert.GetY()))) * oneOverdX;
	}

	private float CalcYStep(float[] values, Vertex minYVert, Vertex midYVert,
			Vertex maxYVert, float oneOverdY)
	{
		return
			(((values[1] - values[2]) *
			(minYVert.GetX() - maxYVert.GetX())) -
			((values[0] - values[2]) *
			(midYVert.GetX() - maxYVert.GetX()))) * oneOverdY;
	}

	private float Saturate(float val)
	{
		if(val > 1.0f)
		{
			return 1.0f;
		}
		if(val < 0.0f)
		{
			return 0.0f;
		}
		return val;
	}

	public Gradients(Vertex minYVert, Vertex midYVert, Vertex maxYVert)
	{
		float oneOverdX = 1.0f /
			(((midYVert.GetX() - maxYVert.GetX()) *
			(minYVert.GetY() - maxYVert.GetY())) -
			((minYVert.GetX() - maxYVert.GetX()) *
			(midYVert.GetY() - maxYVert.GetY())));

		float oneOverdY = -oneOverdX;

		m_oneOverZ = new float[3];
		m_texCoordX = new float[3];
		m_texCoordY = new float[3];
		m_depth = new float[3];
		m_lightAmt = new float[3];

		m_depth[0] = minYVert.GetPosition().GetZ();
		m_depth[1] = midYVert.GetPosition().GetZ();
		m_depth[2] = maxYVert.GetPosition().GetZ();

		Vector4f lightDir = new Vector4f(0,0,1);
		m_lightAmt[0] = Saturate(minYVert.GetNormal().Dot(lightDir)) * 0.9f + 0.1f;
		m_lightAmt[1] = Saturate(midYVert.GetNormal().Dot(lightDir)) * 0.9f + 0.1f;
		m_lightAmt[2] = Saturate(maxYVert.GetNormal().Dot(lightDir)) * 0.9f + 0.1f;

		// Note that the W component is the perspective Z value;
		// The Z component is the occlusion Z value
		m_oneOverZ[0] = 1.0f/minYVert.GetPosition().GetW();
		m_oneOverZ[1] = 1.0f/midYVert.GetPosition().GetW();
		m_oneOverZ[2] = 1.0f/maxYVert.GetPosition().GetW();

		m_texCoordX[0] = minYVert.GetTexCoords().GetX() * m_oneOverZ[0];
		m_texCoordX[1] = midYVert.GetTexCoords().GetX() * m_oneOverZ[1];
		m_texCoordX[2] = maxYVert.GetTexCoords().GetX() * m_oneOverZ[2];

		m_texCoordY[0] = minYVert.GetTexCoords().GetY() * m_oneOverZ[0];
		m_texCoordY[1] = midYVert.GetTexCoords().GetY() * m_oneOverZ[1];
		m_texCoordY[2] = maxYVert.GetTexCoords().GetY() * m_oneOverZ[2];

		m_texCoordXXStep = CalcXStep(m_texCoordX, minYVert, midYVert, maxYVert, oneOverdX);
		m_texCoordXYStep = CalcYStep(m_texCoordX, minYVert, midYVert, maxYVert, oneOverdY);
		m_texCoordYXStep = CalcXStep(m_texCoordY, minYVert, midYVert, maxYVert, oneOverdX);
		m_texCoordYYStep = CalcYStep(m_texCoordY, minYVert, midYVert, maxYVert, oneOverdY);
		m_oneOverZXStep = CalcXStep(m_oneOverZ, minYVert, midYVert, maxYVert, oneOverdX);
		m_oneOverZYStep = CalcYStep(m_oneOverZ, minYVert, midYVert, maxYVert, oneOverdY);
		m_depthXStep = CalcXStep(m_depth, minYVert, midYVert, maxYVert, oneOverdX);
		m_depthYStep = CalcYStep(m_depth, minYVert, midYVert, maxYVert, oneOverdY);
		m_lightAmtXStep = CalcXStep(m_lightAmt, minYVert, midYVert, maxYVert, oneOverdX);
		m_lightAmtYStep = CalcYStep(m_lightAmt, minYVert, midYVert, maxYVert, oneOverdY);
	}
}
