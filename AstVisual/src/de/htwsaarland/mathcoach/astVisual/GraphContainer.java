package de.htwsaarland.mathcoach.astVisual;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

/**
 *
 * @author phucluoi
 */
public class GraphContainer 
{
	ListenableDirectedGraph<AstVertex, AstEdge> lgraph;
	DefaultDirectedGraph<AstVertex, AstEdge> dgraph;
	AstVertex root;
	
	public GraphContainer()
	{
		dgraph = new DefaultDirectedGraph<AstVertex, AstEdge>
				(new AstEdgeFactory<AstVertex, AstEdge>(AstEdge.class));
	}
	
	public void addRoot(AstVertex root)
	{
		this.root = root;
		this.dgraph.addVertex(root);
	}

	
	public void addDepend(AstVertex parent, AstVertex child)
	{
		this.dgraph.addVertex(parent);
		this.dgraph.addVertex(child);
		this.dgraph.addEdge(parent, child);
	}

	public void performDFS()
	{
		lgraph = new ListenableDirectedGraph<AstVertex, AstEdge>(dgraph);
		DepthFirstIterator<AstVertex, AstEdge> iterator = 
				new DepthFirstIterator<AstVertex, AstEdge>(lgraph, root);
		iterator.addTraversalListener(new DFMarkerListener<AstVertex, AstEdge>());
		
		while (iterator.hasNext()){iterator.next();}
	}

	public String toGraphviz()
	{
		return "";
	}

	public DefaultDirectedGraph<AstVertex,AstEdge> getDgraph()
	{
		return this.dgraph;
	}

	public ListenableDirectedGraph<AstVertex, AstEdge> getLgraph()
	{
		return this.lgraph;
	}

	
	@Override
	public String  toString()
	{
		return this.dgraph.toString();
	}
}

