
package grammarTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import org.sablecc.sablecc.analysis.DepthFirstAdapter;
import org.sablecc.sablecc.node.*;


/**
 *
 * @author phucluoi
 */
public class ProductionTree extends DepthFirstAdapter
{
	/** A graph is a LinkedList, and its element is a linked list
	 *  of node
	 */
	private LinkedList<LinkedList<GraphNode>> graph;
	private boolean inProd;
	private Stack<LinkedList<GraphNode>> currentProd;
	private HashMap<String,GraphNode> tokens;

	private LinkedList<GraphNode> graphNode;
	public boolean captureToken = true;
	public boolean drawT = true;
	public boolean drawF = true;
	public boolean drawB = true;
	public boolean drawC = true;

	private HashMap<String, GraphNode> visited;
	private int t;

	private static String treeColor = "[color=black]";
	private static String forwardColor= "[color=coral4]";
	private static String backColor = "[color=blueviolet]";
	private static String crossColor = "[color=red]";
	private static String cmdTerminal = ";\n";


	public ProductionTree(String edgeConf)
	{
		graph = new LinkedList<LinkedList<GraphNode>>();
		currentProd = new Stack<LinkedList<GraphNode>>();
		tokens = new HashMap<String,GraphNode>();

		graphNode = new LinkedList<GraphNode>();
		inProd = false;
		Properties defaultProps = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(edgeConf);
			defaultProps.load(in);
			in.close();
		} catch (IOException ex)
		{
			System.err.println(ex.toString());
		}
		// properties for tree
		if (defaultProps.getProperty("graph.t") != null )
		{
			treeColor = defaultProps.getProperty("graph.t");
		}
		// properties for cross 
		if (defaultProps.getProperty("graph.c") != null )
		{
			crossColor = defaultProps.getProperty("graph.c");
		}
		// backward edges
		if (defaultProps.getProperty("graph.b") != null )
		{
			backColor = defaultProps.getProperty("graph.b");
		}
		// fordward edges
		if (defaultProps.getProperty("graph.f") != null )
		{
			forwardColor = defaultProps.getProperty("graph.f");
		}
	}
	

	/**
	 * because sometimes we would like to draw both tokens and productions
	 * on graph, we must capture tokens. This method will capture token if we visit
	 * it and want to draw it on graph.
	 * @param node 
	 */
	@Override
	public void caseATokenDef(ATokenDef node)
	{
		GraphNode tokenNode = new GraphNode(node.getId().getText(),true);

		for (int i = 0; i < graphNode.size(); ++i)
		{
			GraphNode tmp = graphNode.get(i);
			if (tmp.present.equals(node.getId().getText()))
			{
				return;
			}
		}
		
		if (captureToken)
		{
			graphNode.add(tokenNode);
			LinkedList<GraphNode> singelLine = new LinkedList<GraphNode>();
			singelLine.add(tokenNode);
			graph.add(singelLine);
		}
		tokens.put(node.getId().getText(), tokenNode);
	}

	@Override
	public void caseAAstProd(AAstProd node)
	{
		inProd = true;
		GraphNode firstElementOfLine = null;
		boolean re_use = false;
		for (int i = 0; i < graphNode.size(); ++i)
		{
			GraphNode tmp = graphNode.get(i);
			if (tmp.present.equals(node.getId().getText()))
			{
				re_use = true;
				firstElementOfLine = tmp;
				break ;
			}
		}
		if (re_use)
		{
			graphNode.add(firstElementOfLine);
		}else
		{
			firstElementOfLine = new GraphNode(node.getId().getText(), false);
			graphNode.add(firstElementOfLine);
		}

		LinkedList<GraphNode> production = new LinkedList<GraphNode>();
		production.add(firstElementOfLine);
		graph.add(production);

		currentProd.push(production);
		for (int i = 0; i < node.getAlts().size(); ++i)
		{
			node.getAlts().get(i).apply(this);
		}
		currentProd.pop();
		inProd = false;
	}

	@Override
	public void caseAAstAlt(AAstAlt node)
	{
		LinkedList<PElem> items = node.getElems();
		for (int i=0; i < items.size(); ++i)
		{
			items.get(i).apply(this);
		}
	}

	@Override
	public void caseAElem(AElem node)
	{
		if (inProd)
		{
			if (!captureToken)
			{
				if (tokens.containsKey(node.getId().getText()))
				{
					return;
				}
			}

			GraphNode gn = null ;
			for (int i = 0; i < graphNode.size(); ++i)
			{
				GraphNode tmp = graphNode.get(i);
				if (tmp.present.equals(node.getId().getText()))
				{
					gn = tmp;
					break;
				}
			}
			if (gn == null)
			{
				gn  = new GraphNode(node.getId().getText(), false);
				graphNode.add(gn);
			}
			LinkedList<GraphNode> prod = currentProd.peek();

			for (int i = 0; i < prod.size(); ++i)
			{
				GraphNode tmp = prod.get(i);
				if (tmp.present.equals(gn.present))
				{
					return;
				}
			}
			prod.add(gn);
		}
	}

	private void dfsMain(GraphNode gnode)
	{
		String nodeName = gnode.present;
		visited = new HashMap<String, GraphNode>();
		t = 0;
		dfs(gnode);
	}

	private void dfs(GraphNode u)
	{
		visited.put(u.present, u);
		u.d = t;
		t = t +1;
		LinkedList<GraphNode> neighbor = null;
		for (int i = 0; i < graph.size(); i ++)
		{
			if (graph.get(i).get(0).present.equals(u.present))
			{
				neighbor = graph.get(i);
				break;
			}
		}
		for (int j = 0; j <neighbor.size(); ++j)
		{
			GraphNode v = neighbor.get(j);
			if (!visited.containsKey(v.present))
			{
				dfs(v);
				v.pre = u;
			}
		}
		u.f = t;
		t = t +1;
	}


	public void depthFirstSearch(String from)
	{
		GraphNode root = null;
		for (int i = 0; i < graph.size(); ++i)
		{
			LinkedList<GraphNode> tmp = graph.get(i);
			for (int j = 0; j< tmp.size(); ++j)
			{
				GraphNode test = tmp.get(j);
				if (test.present.equals(from))
				{
					root = test;
					break;
				}
			}
		}
		if (root != null)
		{
			dfsMain(root);
		}else
		{
			System.out.println("not found " + from);
		}

	}


	public String constructGraph()
	{
		StringBuilder graphRumpf = new StringBuilder();
		for (int i = 0; i < graph.size(); ++i)
		{
			LinkedList<GraphNode> line = graph.get(i);
			GraphNode head = line.get(0);
			int d_u = head.d;
			int f_u = head.f;
			String edge;
			for (int j = 1; j < line.size(); ++j)
			{
				GraphNode v = line.get(j);
				int d_v = v.d;
				int f_v = v.f;
				if (d_u<d_v && d_v < f_v && f_v < f_u)
				{
					if (v.pre.present.equals(head.present))
					{
						if (drawT)
						{
							edge = constructTreeEdge(head,v);
							graphRumpf.append(edge);
						}
						continue;
					}else
					{
						if (drawF)
						{
							edge = constructForwardEdge(head, v);
							graphRumpf.append(edge);
						}
						continue;
					}

				}
				if (d_v < d_u && d_u < f_u && f_u < f_v)
				{
					if (drawB)
					{
						edge = constructBackEdge(head,v);
						graphRumpf.append(edge);
					}
					continue;
				}
				if (d_v < f_v && f_v < d_u && d_u < f_u)
				{
					if (drawC)
					{
						edge = constructCrossEdge(head,v);
						graphRumpf.append(edge);
					}
					continue;
				}
			}
		}
		return graphRumpf.toString();
	}

	private String constructTreeEdge(GraphNode u, GraphNode v)
	{
		return u.present + " -> " + v.present + treeColor + cmdTerminal;
	}

	private String constructForwardEdge(GraphNode u, GraphNode v)
	{
		return u.present + " -> " + v.present + forwardColor + cmdTerminal;
	}

	private String constructBackEdge(GraphNode u, GraphNode v)
	{
		return u.present + " -> " + v.present + backColor + cmdTerminal;
	}

	private String constructCrossEdge(GraphNode u, GraphNode v)
	{
		return u.present + " -> " + v.present + crossColor + cmdTerminal;
	}

	public void print()
	{
		for (int i = 0; i < graph.size(); ++i)
		{
			System.out.println(graph.get(i));
		}
	}
}

