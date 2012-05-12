package de.htwsaarland.astVisual.graphRepresent;

/**
 * @author Hong Phuc Bui
 * @version  06.05.2012
 */

public class DefaultAstVertex implements AstVertex
{
	int d; 
	int f;
	String name;
	private AstVertex pred;
	private double x;
	private double y;
	private double height;
	private double width;
	VertexType type;

	/**
	 * @param name Name of the Vertex
	 */
	public DefaultAstVertex(String name)
	{
		this.name = name;
		d = 0;
		f = 0;
		type = VertexType.PROD;
	}
	
	public DefaultAstVertex(String name, VertexType type)
	{
		this.name = name;
		d = 0;
		f = 0;
		this.type = type;
	}

	
	/**
	 * sets the detected time of the vertex by performing DFS
	 * @param d the detected time
	 */
	@Override
	public void setDetected(int d) {
		this.d = d;
	}

	@Override
	public int getDetected() {
		return d;
	}

	@Override
	public void setFinished(int f) {
		this.f = f;
	}

	@Override
	public int getFinished() {
		return f;
	}

	@Override
	public String toString()
	{
		if (pred != null)
		{
			return name + " d="+ d + " f=" + f + "(pred:"+ pred.getName() +")";
		}
		else
		{
			return name + " d="+ d + " f=" + f + "(pred: __null__)";
		}
	}

	@Override
	public void setPred(AstVertex v)
	{
		this.pred = v;
	}

	@Override
	public String getName()
	{
		return name;
	}

	
	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public AstVertex getPred() {
		return this.pred;
	}

	@Override
	public String toGraphviz()
	{
		return "\"" + name + ":" + d + ":" + f + "\"";
	}

	@Override
	public VertexType getType() {
		return this.type;
	}

	
}

