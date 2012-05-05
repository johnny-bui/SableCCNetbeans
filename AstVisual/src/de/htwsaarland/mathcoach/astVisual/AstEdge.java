package de.htwsaarland.mathcoach.astVisual;

/**
 *
 * @author phucluoi
 */
public class AstEdge {
	private AstVertex source;
	private AstVertex target;
	
	public void setNodes(AstVertex source, AstVertex target)
	{
		this.source = source;
		this.target = target;
	}

	public AstVertex getSource()
	{
		return this.source;
	}

	public AstVertex getTarget()
	{
		return this.target;
	}
	
	@Override
	public String toString()
	{
		return "(" + source.getName()+ " -> " + target.getName() + ")";
	}
}
