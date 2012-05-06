package de.htwsaarland.mathcoach.astVisual;

import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;

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
		System.out.println("duplicateNode");
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

	}
	
	@Test
	public void testDuplicate2() 
	{
		System.out.println("testDuplicate2");
		GraphContainer<NameDistinctVertex> egc = new GraphContainer<NameDistinctVertex>();
		NameDistinctVertex eroot = new NameDistinctVertex("root");
		NameDistinctVertex ea = new NameDistinctVertex("a");
		NameDistinctVertex eb = new NameDistinctVertex("a");
		assertTrue(ea.equals(eb));
		
		egc.addRoot(eroot);
		egc.addDepend(eroot, ea);
		egc.addDepend(ea, eb);
		egc.performDFS();
		
		Set<AstEdge> edges = egc.getDgraph().getAllEdges(ea, eb);
		assertEquals(0, edges.size());// assert that no edge if equal vertex
		
		
		DefaultDirectedGraph<NameDistinctVertex,AstEdge>  graph = egc.getDgraph();
		Set<AstEdge> alledges = graph.edgeSet();
		assertEquals(1, alledges.size());
	}

	/**
	 * Test of performDFS method, of class GraphContainer.
	 */
	
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
}
