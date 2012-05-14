package de.htwsaarland.astVisual.graphRepresent;

import java.io.Serializable;

/**
 *
 * @author phucluoi
 */
public class AstEdge  
	implements Cloneable,
        Serializable
{
	private String source;
	private String target;
	private EdgeClass c = EdgeClass.N;
			
	public void setNodes(String source, String target)
	{
		this.source = source;
		this.target = target;
		
	}

	public String getSource()
	{
		return this.source;
	}

	public String getTarget()
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
		return "(" + source + " -> " + target + "):" + c;
	}
	
	public String toGraphiz()
	{
		return source + " -> " + target
			+"[" + EdgeClass.mapToStyle(c) + "]";
	}
}


