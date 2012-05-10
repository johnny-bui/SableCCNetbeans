package de.htwsaarland.astVisual.graphRepresent;

import java.util.Iterator;
import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

/**
 * <code>a -> b<code>, which <code>a.equals(b) == true</code>, is not allow.
 * @author phucluoi
 * @version 05.05.2012
 */

public class GraphContainer<V extends AstVertex> 
{
	ListenableDirectedGraph<V, AstEdge> lgraph;
	DefaultDirectedGraph<V, AstEdge> dgraph;
	V root;
	
	/**
	 * 
	 */
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

	public V getRoot()
	{
		return root;
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

	public synchronized void performDFS()
	{
		lgraph = new ListenableDirectedGraph<V, AstEdge>(dgraph);
		DepthFirstIterator<V, AstEdge> iterator = 
				new DepthFirstIterator<V, AstEdge>(lgraph, root);
		iterator.addTraversalListener(
				new DFMarkerListener<V, AstEdge>()
				);
		while (iterator.hasNext()){iterator.next();}
		Set <AstEdge> allEdges = dgraph.edgeSet();
		Iterator<AstEdge> i = allEdges.iterator();
		while (i.hasNext())
		{
			AstEdge e = i.next();
			V u = (V) e.getSource();
			int d_u = u.getDetected();
			int f_u = u.getFinished();
			
			V v = (V) e.getTarget();
			int d_v = v.getDetected();
			int f_v = v.getFinished();
			/*
			 * (u,v) in T, F <=> d[u] < d[v] < f[u] < f[v]
			 * (u,v) in B    <=> d[v] < d[u] < f[u] < f[v]
			 * (u,v) in C    <=> d[v] < f[v] < d[u] < f[u]
			 */
			if (   d_u < d_v
				&& d_v < f_v
				&& f_v < f_u)
			{
				//TODO set e to T or F
				V pred = (V) v.getPred();
				if (u.equals(pred))
				{
					e.setEdgeClass(EdgeClass.T);
				}else{
					e.setEdgeClass(EdgeClass.F);
				}
			}else if(  d_v < d_u 
					&& d_u < f_u
					&& f_u < f_v)
			{
				e.setEdgeClass(EdgeClass.B);
			}else if(  d_v < f_v
					&& f_v < d_u
					&& d_u < f_u
					)
			{
				e.setEdgeClass(EdgeClass.C);
			}
			
		}
	}

	public String toGraphviz()
	{
		StringBuilder graphvizCode = new StringBuilder();
		Set <AstEdge> e = dgraph.edgeSet();
		Iterator <AstEdge> i = e.iterator();
		graphvizCode.append("digraph ast{\n");
		while (i.hasNext())
		{
			AstEdge ae = i.next();
			graphvizCode.append("\t");
			/*
			graphvizCode.append(ae.getSource().toString());
			graphvizCode.append(" -> ");
			graphvizCode.append(ae.getTarget().toString());
			*/ 
			graphvizCode.append(ae.toGraphiz());
			graphvizCode.append(";\n");
		}
		graphvizCode.append("}\n");
		return graphvizCode.toString();
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

