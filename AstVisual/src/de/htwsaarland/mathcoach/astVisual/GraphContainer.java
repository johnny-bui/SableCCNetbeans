package de.htwsaarland.mathcoach.astVisual;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

/**
 *
 * @author phucluoi
 * @version 05.05.2012
 * a -> a is not allow
 */
public class GraphContainer<V extends AstVertex> 
{
	ListenableDirectedGraph<V, AstEdge> lgraph;
	DefaultDirectedGraph<V, AstEdge> dgraph;
	V root;
	
	public GraphContainer()
	{
		dgraph = new DefaultDirectedGraph<V, AstEdge>
				(new AstEdgeFactory<V, AstEdge>(AstEdge.class));
	}
	
	public void addRoot(V root)
	{
		this.root = root;
		this.dgraph.addVertex(root);
	}

	
	public void addDepend(V parent, V child)
	{
		if (! parent.equals(child))
		{
			this.dgraph.addVertex(parent);
			this.dgraph.addVertex(child);
			this.dgraph.addEdge(parent, child);
		}
	}

	public void performDFS()
	{
		lgraph = new ListenableDirectedGraph<V, AstEdge>(dgraph);
		DepthFirstIterator<V, AstEdge> iterator = 
				new DepthFirstIterator<V, AstEdge>(lgraph, root);
		iterator.addTraversalListener(
				new DFMarkerListener<V, AstEdge>()
				);
		
		while (iterator.hasNext()){iterator.next();}
	}

	public String toGraphviz()
	{
		return "";
	}

	public DefaultDirectedGraph<V, AstEdge> getDgraph()
	{
		return this.dgraph;
	}

	public ListenableDirectedGraph<V, AstEdge> getLgraph()
	{
		return this.lgraph;
	}

	
	@Override
	public String  toString()
	{
		return this.dgraph.toString();
	}
}

