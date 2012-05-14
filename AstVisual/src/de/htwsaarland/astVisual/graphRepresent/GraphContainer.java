package de.htwsaarland.astVisual.graphRepresent;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

/**
 * <code>a -> b<code>, which <code>a.equals(b) == true</code>, is not allow.
 * @author phucluoi
 * @version 05.05.2012
 * @version 14.05.2012 New implement, only use string to presents node.
 */

public class GraphContainer 
{
	/** 
	 * represents the graph. Its vertices are @String, 
	 * Its edges  are @AstEdge.
	 */
	ListenableDirectedGraph<String, AstEdge> lgraph;

	/**
	 * represents the "root" of the graph, on which the DFS
	 * first begins.
	 */
	String root;

	/**
	 * maps the vertices to their properties.
	 */
	Map<String, VertexInfo> vertexInfoTable;

	
	/**
	 * default constructor.
	 */
	public GraphContainer()
	{
		lgraph = new ListenableDirectedGraph<String, AstEdge>(AstEdge.class);
		vertexInfoTable = Collections.synchronizedMap( new HashMap<String, VertexInfo>() );
		root = null;
	}
	

	/**
	 * add the root vertex into graph. This method should be called before any
	 * other vertices than root are added into the graph. This method can be
	 * called only once. Use the method changeRoot(root) to change the root.
	 * 
	 * @param root the root vertex.
	 * 
	 * @exception IllegalArgumentException if the argument root is null.
	 * @exception RuntimeException if the root is ready not null.
	 */
	public void addRoot(String root, VertexType t)
	{
		if (root == null)
		{
			throw new IllegalArgumentException("Root cannot be null.");
		}
		if (this.root == null)
		{
			this.root = root;
			this.lgraph.addVertex(root);
			VertexInfo info = new CompactInfo(root, t);
			vertexInfoTable.put(root, info);
		}else
		{
			throw new RuntimeException("The root node can be added only once.");
		}
	}

	public void addRoot(String root)
	{
		addRoot(root, VertexType.PROD);
	}

	/**
	 * changes the root to an other vertex after add the root into graph.
	 * The Implementation should ensure, that the new root is ready in
	 * the graph.
	 * @param newRoot the new root of graph
	 */
	public void changeRoot(String newRoot)
	{
		throw new UnsupportedOperationException("method not yet supported");
	}
	
	/**
	 * return the root vertex
	 */
	public String getRoot()
	{
		return root;
	}
	
	/**
	 * Adds two vertices into the graph and define a edge from the first to the
	 * second. If they are equal, neither of them is added (to avoid loop) and
	 * they are ignored. If there is already an edge from the first to the second,
	 * no edges is added.
	 * 
	 * @param parent the first vertex
	 * @param child  the second vertex.
	 */
	public void addDepend(String parent, VertexType parentType, String child, VertexType childType)
	{
		// loop is not allowed
		if (! parent.equals(child))
		{
			lgraph.addVertex(parent);
			vertexInfoTable.put(parent, new CompactInfo(parent, parentType));
			
			lgraph.addVertex(child);
			vertexInfoTable.put(child, new CompactInfo(child, childType));
			// duplicated edges not allow
			if (! lgraph.containsEdge(parent, child))
			{
				lgraph.addEdge(parent, child);
			}
		}
	}

	public void addDepend(String parent, String child)
	{
		addDepend(parent, VertexType.PROD, child, VertexType.PROD);
	}

	/**
	 * perform the depth-first-search algorithm on the graph, beginning with
	 * the vertex root and classify the edges in the graph in for classes.
	 * The edges-classes are:
	 * <ul>
	 * 	<li><code>Tree</code></li>
	 * 	<li><code>Forward</code></li>
	 * 	<li><code>Backward</code></li>
	 * 	<li><code>Cross</code></li>
	 * </ul>
	 * 
	 */
	public synchronized void performDFS()
	{
		DepthFirstIterator<String, AstEdge> iterator = 
				new DepthFirstIterator<String, AstEdge>(lgraph, root);
		iterator.addTraversalListener(
				new DFMarkerListener()
				);
		while (iterator.hasNext()){iterator.next();}
		Set <AstEdge> allEdges = lgraph.edgeSet();
		
		Iterator<AstEdge> i = allEdges.iterator();
		while (i.hasNext())
		{
			AstEdge e = i.next();
			String u = lgraph.getEdgeSource(e);
			
			VertexInfo uInfo = vertexInfoTable.get(u);
			int d_u = uInfo.getDetected();
			int f_u = uInfo.getFinished();
			
			String v =  lgraph.getEdgeTarget(e);
			
			VertexInfo vInfo = vertexInfoTable.get(v);
			int d_v = vInfo.getDetected();
			int f_v = vInfo.getFinished();
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
				String pred = vInfo.getPredName();
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
			
			e.setNodes(u, v);
		}
	}

	/**
	 * converts the graph to a dot-string.
	 */
	public String toGraphviz()
	{
		StringBuilder graphvizCode = new StringBuilder();
		Set <AstEdge> e = lgraph.edgeSet();
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

	/**
	 * return the JGraphT graph for low-level-client.
	 */
	public Graph<String, AstEdge> getGraph()
	{
		return this.lgraph;
	}

	/**
	 * return the map of vertex and its properties
	 */
	public Map getVertexInforTable()
	{
		return vertexInfoTable;
	}

	
	/**
	 * return a simple presentation of the graph and the properties
	 * of each vertices in the graph.
	 */
	@Override
	public String  toString()
	{
		return lgraph.toString() + vertexInfoTable.toString();
	}


	/**
	 * an implementation of <code>TraversalListenerAdapter</code>. It marks
	 * the detected-time and the finish-time of each vertices in the graph.
	 * 
	 */
	private class DFMarkerListener 
		extends TraversalListenerAdapter<String, AstEdge>
	{
		int counter ;
		Stack<String> visit;
		
		public DFMarkerListener()
		{
			counter = 1;
			visit = new Stack<String>();
		}
		
		@Override
		public void  vertexTraversed(VertexTraversalEvent<String> e) 
		{
			String vertexName = e.getVertex();
			VertexInfo v = vertexInfoTable.get(vertexName);
			
			v.setDetected(counter);
			counter ++;
			if (counter > 2)
			{
				v.setPredName(visit.peek());
			}
			visit.push(vertexName);
			vertexInfoTable.put(vertexName, v);
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<String> e)
		{
			String vertexName = e.getVertex();
			
			VertexInfo v = vertexInfoTable.get(vertexName);
			v.setFinished(counter);
			counter ++;
			visit.pop();
		}
	}

}

