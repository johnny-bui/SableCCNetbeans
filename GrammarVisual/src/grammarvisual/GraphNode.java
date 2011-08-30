package grammarvisual;


/**
 *
 * @author phucluoi
 */
public class GraphNode
{
	public String present;
	public boolean nodeType;
	public int d;
	public int f;
	public GraphNode(String present, boolean nodeType)
	{
		this.present = present;
		this.nodeType = nodeType;
		d = 0;
		f = 0;
	}
	public GraphNode(String present, boolean nodeType, GraphNode pre)
	{
		this.present = present;
		this.nodeType = nodeType;
		d = 0;
		f = 0;
	}
	
	@Override
	public String toString()
	{
		return /*pre + " -> " +*/  present + "[" + d + ":" + f + "]";
	}

	@Override
	public boolean equals(Object other)
	{
		// Not strictly necessary, but often a good optimization
		if (this == other)
			return true;
		if (!(other instanceof GraphNode))
			return false;
		GraphNode otherA = (GraphNode) other;
		return 
		  (present.equals(otherA.present))
			&& (nodeType == otherA.nodeType);
	}

	@Override
	public int hashCode()
	{
		String hash = present + nodeType;
		return hash.hashCode();
	}
}
