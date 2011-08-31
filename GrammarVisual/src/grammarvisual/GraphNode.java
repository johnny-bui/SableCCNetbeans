package grammarvisual;


/**
 *
 * @author phucluoi
 */
public class GraphNode
{
	public String present;
	public boolean isToken;
	public int d;
	public int f;

	private GraphNode pre;
	
	public GraphNode(String present, boolean nodeType)
	{
		this.present = present;
		this.isToken = nodeType;
		d = 0;
		f = 0;
	}
	
	public void setPre(GraphNode pre)
	{
		this.pre = pre;
	}
	
	@Override
	public String toString()
	{
		/*
		if (pre != null)
			return pre.present + " -> " +  present + "[" + d + ":" + f + "]\n";
		else
			return "null" + " -> " + present + "[" + d + ":" + f + "]\n";
		*/
		return this.present;
	}

	/**
	 * Two GraphNode are equality iff they have the same name and the same type.
	 * Therefore we must re-implement the method <code>equals</code> and <code>hashCode</code>.
	 */
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
			&& (isToken == otherA.isToken);
	}

	@Override
	public int hashCode()
	{
		String hash = present + isToken;
		return hash.hashCode();
	}
}
