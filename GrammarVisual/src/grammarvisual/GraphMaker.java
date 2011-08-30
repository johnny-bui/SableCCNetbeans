package grammarvisual;


import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import org.sablecc.sablecc.analysis.DepthFirstAdapter;

/**
 *
 * @author phucluoi
 * @version 29.08.2011
 */
public class GraphMaker extends DepthFirstAdapter
{
	private DirectedGraph<GraphNode, GraphLink> dependentGraph;
	

	/**
	 * linkCounter is used to indicate a GraphLink in the graph.
	 */
	int linkCounter = 0;
	public GraphMaker()
	{
		/* we set linkCounter 0 at the begin, because the graph
		 * should empty at it created.
		 */
		linkCounter = 0;
		/* we creat also a new empty directed graph:
		 */
		dependentGraph = new DirectedSparseGraph<GraphNode, GraphLink>();
	}

	/* Question: which method from DepthFirstAdapter must we implement?
	 * This means, which production in the SableCC grammar is relevant 
	 * to generate the dependent graph.
	 */
	

	
	public DirectedGraph getDependentGraph()
	{
		return 	dependentGraph;
	}
}
