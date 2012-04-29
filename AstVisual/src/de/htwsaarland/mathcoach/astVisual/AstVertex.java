package de.htwsaarland.mathcoach.astVisual;

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
	public String getName();
}
