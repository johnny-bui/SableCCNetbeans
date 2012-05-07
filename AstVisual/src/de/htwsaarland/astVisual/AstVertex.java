package de.htwsaarland.astVisual;

/**
 *
 * @author phucluoi
 */
public interface AstVertex 
{
	/** set the order of detected in DFS */
	public void setDetected(int d);	
	public int getDetected();
	/** set the order of finish in DFS */
	public void setFinished(int f);
	public int getFinished();
	public void setPred(AstVertex v);
	public AstVertex getPred();
	public String getName();

	public void setX(double x);
	public void setY(double y);
	public double getX();
	public double getY();
	
	public void setWidth(double width);
	public void setHeight(double height);
	public double getWidth();
	public double getHeight();
	public String toGraphviz();
}
