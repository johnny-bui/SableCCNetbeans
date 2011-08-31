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
	private boolean isTokenSpecifier = false;
	private boolean isProductionSpecifier = false;

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
		inATokenDef(node);
		GraphNode token = new GraphNode(node.getId().getText(), true );
		tokens.put(node.getId().getText(), token);
		outATokenDef(node);
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
		GraphNode production = new GraphNode(node.getId().getText(), false);
		dependentGraph.addEdge(new GraphLink(), currentAbstracProduction, production);
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
	 * If we visit an element of an alternative, we check if it is a token of a 
	 * production by looking up in the hash table tokens.
	 * If we want to draw tokens on the graph, we must create a new <code>GraphNode</code> 
	 * which is corresponding  to the element (token / procedure)
	 * and add it in graph. We should also check whether an element is specified by a
	 * Specifier.
	 */
	@Override
	public void caseAElem(AElem node)
	{
		/** checking of specifier  */
		if(node.getSpecifier() != null)
        {
            node.getSpecifier().apply(this);
        }
		if (node.getId() != null)
		{
			GraphNode element;
			String nodeId = node.getId().getText();
			if (isTokenSpecifier)
			{
				element = tokens.get(nodeId);
				element.present = "T." + element.present;
				// set the isTokenSpecifier back to false is important
				isTokenSpecifier =  false;
			}else if (isProductionSpecifier)
			{
				element = new GraphNode(nodeId,false);
				// element.present += "P.";
				// even for isProductionSpecifier
				isProductionSpecifier = false;
			}else
			{
				if(tokens.containsKey(nodeId))
				{
					element = tokens.get(nodeId);
				}else
				{
					element = new GraphNode(nodeId,false);
				}
			}// if the element is not a token, so it is a prodution. We add it in dependentGraph.
			if (!element.isToken)
			{
				dependentGraph.addEdge(new GraphLink(), currentAbstracProduction, element);
				element.setPre(currentAbstracProduction);
			}else if (drawToken)
			{	// else if it is a token, and if we want to draw it, we add it in dependentGraph.
				dependentGraph.addEdge(new GraphLink(), currentAbstracProduction, element);
				element.setPre(currentAbstracProduction);
			}
		}
	}
	
	@Override
	public void caseAProductionSpecifier(AProductionSpecifier node)
	{
		isProductionSpecifier = true;	
	}
	
	@Override
	public void caseATokenSpecifier(ATokenSpecifier node)
	{
		isTokenSpecifier = true;
	}

	/** 
	 * we need to override this method to avoid the processing of element in a 
	 * production, which is not relevant to abstract production.
	 */
	@Override
	public void caseAProd(AProd node)
	{
		inAProd(node);
		outAProd(node);
	}
	
	/**
	 * We also need to set the Start Production as the "root".
	 */
	
	@Override
	public void inAAst(AAst node)
	{
		System.out.println("inAAst");
		currentAbstracProduction = new GraphNode("Root", false);
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
