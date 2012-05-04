package de.htwsaarland.mathcoach.astVisual;

import java.util.Iterator;
import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author phucluoi
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
	 * Test of duplicate Node.
	 */
	@Test
	public void testDuplicateNode()
	{
		System.out.println("duplocateNode");
		GraphContainer gc = new GraphContainer();
		// add a root for gc
		AstVertex root = new DummyVertex("root");
		gc.addRoot(root);
		// create node a, b
		AstVertex a = new DummyVertex("a");
		AstVertex b = new DummyVertex("a");
		
		gc.addDepend(root, a);
		gc.addDepend(a, b);		
		gc.performDFS();
		
		DefaultDirectedGraph<AstVertex,AstEdge> g = gc.getDgraph();
		Set<AstEdge> edges = g.getAllEdges(a, b);
		assertEquals(1, edges.size()); // assert that only one edge from a to b
		
		AstEdge a_b = edges.iterator().next();
		assertTrue(a_b.getSource() == a);// assert that source and target point to a and b
		assertTrue(a_b.getTarget() == b);
		
		assertTrue(a_b.getSource().getDetected() < a_b.getTarget().getFinished());
		assertTrue(a_b.getSource().getDetected() < a_b.getTarget().getDetected());
		assertTrue(a_b.getSource().getFinished() > a_b.getTarget().getFinished());
		
		gc = new GraphContainer();
		EqualAstVertex eroot = new EqualAstVertex("root");
		EqualAstVertex ea = new EqualAstVertex("a");
		EqualAstVertex eb = new EqualAstVertex("a");

		gc.addRoot(eroot);
		gc.addDepend(eroot, ea);
		gc.addDepend(ea, eb);
		gc.performDFS();
		
		edges = gc.getDgraph().getAllEdges(ea, eb);
		assertEquals(1, edges.size());// assert that only one edge from a to a

		AstEdge a_a = edges.iterator().next();
		assertEquals(a_a.getSource(), a_a.getTarget());
		// assert that source and target points to only one node
		//assertTrue(a_a.getSource() == a_a.getTarget());
		// assert that source and target points to only one node
		DefaultDirectedGraph graph = gc.getDgraph();
		Iterator i = graph.vertexSet().iterator();
		while (i.hasNext())
		{
			System.out.println(i.next());
		}
		System.out.println(">>>>>" + gc.toString());
	}

	/**
	 * Test of performDFS method, of class GraphContainer.
	 */
	/*
	@Test
	public void testPerformDFS() {
		System.out.println("performDFS");
		GraphContainer g = new GraphContainer();
		// add a root for gc
		AstVertex root = new DummyVertex("root");
		g.addRoot(root);
		// create node a, b, c, d
		AstVertex a = new DummyVertex("a");
		AstVertex b = new DummyVertex("b");
		AstVertex c = new DummyVertex("c");
		AstVertex d = new DummyVertex("d");

		g.addDepend(root, a);
		g.addDepend(a, d);
		g.addDepend(a, c);
		g.addDepend(root, b);
		g.addDepend(a, b); // make a cirular
		
		g.performDFS();
	}
	*/
	
}
