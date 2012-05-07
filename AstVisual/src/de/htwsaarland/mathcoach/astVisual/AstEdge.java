package de.htwsaarland.mathcoach.astVisual;

/**
 *
 * @author phucluoi
 */
public class AstEdge {
	private AstVertex source;
	private AstVertex target;
	private EdgeClass c;
			
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
	
	public void setEdgeClass(EdgeClass c)
	{
		this.c = c;	
	}
	
	
	public EdgeClass getEdgeClass()
	{
		return c;
	}
	
	@Override
	public String toString()
	{
		return "(" + source.getName()+ " -> " + target.getName() + "):" + c;
	}
	
}
