
package grammarTree;

/**
 *
 * @author phucluoi
 */
public class GraphNode
{
	public String present;
	public boolean nodeType;
	public GraphNode pre;
	public int d;
	public int f;
	public GraphNode(String present, boolean nodeType)
	{
		this.present = present;
		this.nodeType = nodeType;
		pre = null;
		d = 0;
		f = 0;
	}
	public GraphNode(String present, boolean nodeType, GraphNode pre)
	{
		this.present = present;
		this.nodeType = nodeType;
		this.pre = pre;
		d = 0;
		f = 0;
	}
	
	@Override
	public String toString()
	{
		return /*pre + " -> " +*/  present + "[" + d + ":" + f + "]";
	}

}
