package de.htwsaarland.astVisual.graphRepresent;

import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author hbui
 * @version May, 14 2012
 * 	new test for new implement
 */

public class GraphContainerTest {
	
	public GraphContainerTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of addRoot method, of class GraphContainer.
	 */
	@Test
	public void testAddRoot() 
	{
		System.out.println("addRoot");
		String root = "root";
		GraphContainer graph = new GraphContainer();
		graph.addRoot(root);
		String rootInGraph = graph.getRoot();
		assertEquals(root, rootInGraph);

		Graph<String,AstEdge> lgraph = graph.getGraph();
		assertEquals(lgraph.containsVertex(root), true);
		Set <String> vertices = lgraph.vertexSet();
		assertEquals("graph must contain only one vertex ",vertices.size() , 1);

	}

	@Test
	public void testDupplicatedRoot()
	{
		System.out.println("testDupplicatedRoot");
		String root = "root";
		String dupRoot = "root";

		GraphContainer graph = new GraphContainer();
		try{
			graph.addRoot(root);
			String rootInGraph = graph.getRoot();
			assertEquals(root, rootInGraph);
			graph.addRoot(dupRoot);
			fail("Expected an Runtime Exception");
		}catch (RuntimeException ex)
		{
			
		}
		Graph<String,AstEdge> lgraph = graph.getGraph();
		assertEquals(lgraph.containsVertex(dupRoot), true);
	}

	/**
	 * Test of changeRoot method, of class GraphContainer.
	 */
	@Test
	public void testChangeRoot() {
		System.out.println("changeRoot");
		String root = "";
		GraphContainer instance = new GraphContainer();
		instance.changeRoot(root);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of addDepend method, of class GraphContainer.
	 */
	@Test
	public void testAddDepend(){
		System.out.println("addDepend");
		GraphContainer graph = new GraphContainer();
		String root = "root";
		String a1 = "a1";
		String a2 = "a2";
		graph.addRoot(root);
		graph.addDepend(root, a1);
		graph.addDepend(root, a1);
		Graph<String,AstEdge> lgraph = graph.getGraph();
		Set<AstEdge> edges = lgraph.edgeSet();
		assertEquals("There must be only one edge in the graph", edges.size(), 1);
		AstEdge e = null;
		for (AstEdge tmpe: edges)
		{
			e = tmpe;
			break;
		}
		
		String source = lgraph.getEdgeSource(e);
		assertEquals("source of the edge is the root ", root, source);
		String target = lgraph.getEdgeTarget(e);
		System.out.println(target);
		assertEquals("target of the edge is the a1 ", a1, target);

	}

	/**
	 * Test of performDFS method, of class GraphContainer.
	 */
	@Test
	public void testPerformDFS() {
		System.out.println("performDFS");
		GraphContainer graph = new GraphContainer();
		/*
		 * root -> a1
		 * root -> a2
		 * a1 -> a3
		 * a1 -> a2
		 */
		graph.addRoot("root");
		graph.addDepend("root", "a1");
		graph.addDepend("root", "a1"); // mit absicht duplication
		graph.addDepend("root", "a2");
		graph.addDepend("a1", "a2");
		graph.addDepend("a1", "a3");
		
		{
			Graph<String,AstEdge> lGraph = graph.getGraph();
			Map<String,VertexInfo> vertexInfoTab = graph.vertexInfoTable;
			Set<AstEdge> edges = lGraph.edgeSet();
			for (AstEdge e : edges)
			{
				System.out.println( lGraph.getEdgeSource(e) + " -> " + lGraph.getEdgeTarget(e) );
			}
		}
		
		graph.performDFS();
		{
			Graph<String,AstEdge> lGraph = graph.getGraph();
			Map<String,VertexInfo> vertexInfoTab = graph.vertexInfoTable;
			Set<AstEdge> edges = lGraph.edgeSet();
			for (AstEdge e : edges)
			{
				System.out.println(vertexInfoTab.get(e.getSource()).toString() 
						+ " -> " + vertexInfoTab.get(e.getTarget()).toString() );
			}
		}
	}
	
}
