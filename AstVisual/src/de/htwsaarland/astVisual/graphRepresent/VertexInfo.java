package de.htwsaarland.astVisual.graphRepresent;

/**
 *
 * @author phucluoi
 */
public interface VertexInfo 
{
	/** set the order of detected in DFS */
	public void setDetected(int d);	
	public int getDetected();
	/** set the order of finish in DFS */
	public void setFinished(int f);
	public int getFinished();
	public void setPredName(String v);
	public String getPredName();
	public String getName();

	
	public void setType(VertexType t);
	public VertexType getType();
	
}
