package de.htwsaarland.astVisual;

/**
 *
 * @author phucluoi
 */
public class AstEdge<V extends AstVertex> 
{
	private V source;
	private V target;
	private EdgeClass c;
			
	public void setNodes(V source, V target)
	{
		this.source = source;
		this.target = target;
	}

	public V getSource()
	{
		return this.source;
	}

	public V getTarget()
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
	
	public String toGraphiz()
	{
		return source.toGraphviz() + " -> " + target.toGraphviz()
			+"[" + EdgeClass.mapToStyle(c) + "]";
	}
}
