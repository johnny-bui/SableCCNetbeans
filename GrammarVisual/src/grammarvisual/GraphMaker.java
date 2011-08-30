package grammarvisual;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.sablecc.sablecc.analysis.DepthFirstAdapter;

import org.sablecc.sablecc.node.*;

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
	private HashMap<String,GraphNode> tokens;
	private GraphNode currentAbstracProduction;
	private boolean drawToken = true;

	public GraphMaker()
	{
		/* we set linkCounter 0 at the begin, because the graph
		 * should empty at it created.
		 */
		linkCounter = 0;
		/* we creat also a new empty directed graph (<code><DirectedSparseGraph</code>),
		 * and a new hash table to remember all tokens (<code>HashMap</code>)
		 */
		dependentGraph = new DirectedSparseGraph<GraphNode, GraphLink>();
		tokens = new HashMap<String, GraphNode>();
		
	}

	/* Question: which method from DepthFirstAdapter must we implement?
	 * This means, which production in the SableCC grammar is relevant 
	 * to generate the dependent graph.
	*/

	/**
	 * Firstly we must remember, 
	 * which <code>id</code> is a token. So we override the method <code>caseATokenDef</code>.
	 */
	@Override
	public void caseATokenDef(ATokenDef node)
	{
		/* 
		 * we create a new </code>GraphNode</code> and try to add it in the set of token
		 * in the grammar.
		 */
		GraphNode token = new GraphNode(node.getId().getText(), true );
		tokens.put(node.getId().getText(), token);
	}

	/**
	 * Then we must process all node in a Abstract Production. Also we override the method
	 * <code>caseAstProd</code>
	 */
	@Override
	public void caseAAstProd(AAstProd node)
	{
		/**
		 * We note the current abstract production by saving it in the Class Felder
		 * currentAbstractProduction then process all alternatives of the production.
		 */
		currentAbstracProduction = new GraphNode(node.getId().getText(), false);
		{
            List<PAstAlt> copy = new ArrayList<PAstAlt>(node.getAlts());
            for(PAstAlt e : copy)
            {
                e.apply(this);
            }
        }
	}

	/**
	 * For all element in a alternative of a production, we check whether it is
	 * a token or a production and make a edge from the currentAbstractProduction
	 * to the element. Also we must override the method <code>caseAAstAlt</code>
	 * and the method <code>caseAElem</code>
	 */
	@Override
	public void caseAAstAlt(AAstAlt node)
	{
		{
            List<PElem> copy = new ArrayList<PElem>(node.getElems());
            for(PElem e : copy)
            {
                e.apply(this);
            }
        }
	}

	/**
	 * If we visit a element of a alternative, we check if it is a token or a 
	 * production by looking up in the hash table tokens.
	 * If we want to draw tokens on the graph, we must create a new GraphNode 
	 * which is corresponding  to the element (token / procedure)
	 * and add it in graph.
	 */
	@Override
	public void caseAElem(AElem node)
	{
		if(node.getSpecifier() != null)
        {
            node.getSpecifier().apply(this);
        }
		
	}
	
	public DirectedGraph getDependentGraph()
	{
		return 	dependentGraph;
	}

	public void notDrawToken()
	{
		drawToken = false;
	}
}
