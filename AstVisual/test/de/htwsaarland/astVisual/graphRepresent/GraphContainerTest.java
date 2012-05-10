package de.htwsaarland.astVisual.graphRepresent;

import de.htwsaarland.astVisual.graphRepresent.DefaultAstVertex;
import de.htwsaarland.astVisual.graphRepresent.NameDistinctVertex;
import de.htwsaarland.astVisual.graphRepresent.GraphContainer;
import de.htwsaarland.astVisual.graphRepresent.AstEdge;
import de.htwsaarland.astVisual.graphRepresent.AstVertex;
import java.util.Iterator;
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
	public void setUp() 
	{
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
		AstVertex root = new DefaultAstVertex("root");
		gc.addRoot(root);
		// create node a, b
		AstVertex a = new DefaultAstVertex("a");
		AstVertex b = new DefaultAstVertex("a");
		
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
		AstVertex root = new DefaultAstVertex("root");
		g.addRoot(root);
		// create node a, b, c, d
		AstVertex a = new DefaultAstVertex("a");
		AstVertex b = new DefaultAstVertex("b");
		AstVertex c = new DefaultAstVertex("c");
		AstVertex d = new DefaultAstVertex("d");

		g.addDepend(root, a);
		g.addDepend(a, d);
		g.addDepend(a, c);
		g.addDepend(root, b);
		g.addDepend(a, b); // make a cirular
		
		g.performDFS();
		DefaultDirectedGraph<DefaultAstVertex,AstEdge> dgraph = g.getDgraph();
		Set <AstEdge> allEdge = dgraph.edgeSet();
		Iterator<AstEdge> i = allEdge.iterator();
		{
			while(i.hasNext())
			{
				System.out.println(i.next());
			}
		}
	}


	@Test
	public void testLoop()
	{
		System.out.println("testLoop");
		GraphContainer<NameDistinctVertex> gc = 
				new GraphContainer<NameDistinctVertex>();
		NameDistinctVertex root = new NameDistinctVertex("root");
		gc.addRoot(root);
		NameDistinctVertex a = new NameDistinctVertex("a");
		gc.addDepend(root, a);
		gc.addDepend(a, root);
		gc.performDFS();
		DefaultDirectedGraph<NameDistinctVertex,AstEdge> g = gc.getDgraph();
		Set <AstEdge> root_to_a = g.getAllEdges(root, a);
		assertEquals(1, root_to_a.size());
		Set <AstEdge> a_to_root = g.getAllEdges(a, root);
		assertEquals(1, a_to_root.size());
		Set <AstEdge> all_edge = g.edgeSet();
		assertEquals(2, all_edge.size());
	}
	@Test
	public void testEdge()
	{
		System.out.println("testEgde");
		GraphContainer<NameDistinctVertex> gc = 
				new GraphContainer<NameDistinctVertex>();
		NameDistinctVertex root = new NameDistinctVertex("root");
		gc.addRoot(root);
		NameDistinctVertex a = new NameDistinctVertex("a");
		gc.addDepend(root, a);
		gc.addDepend(a, root);
		gc.performDFS();
		DefaultDirectedGraph<NameDistinctVertex,AstEdge> g = gc.getDgraph();
		Set<AstEdge> allEdges = g.edgeSet();
		Iterator <AstEdge> i =  allEdges.iterator();
		while (i.hasNext())
		{
			AstEdge e = i.next();
			NameDistinctVertex s = (NameDistinctVertex) e.getSource();
			System.out.println(s);
			assertTrue(0 < s.getDetected());
			assertTrue(0 < s.getFinished());

			NameDistinctVertex t = (NameDistinctVertex) e.getTarget();
			System.out.println(t);
			assertTrue(0 < t.getDetected());
			assertTrue(0 < t.getFinished());
		}
	}
	
	@Test
	public void testToGraphviz()
	{
		GraphContainer<NameDistinctVertex> gc = new GraphContainer<NameDistinctVertex>();
		NameDistinctVertex root = new NameDistinctVertex("root");
		NameDistinctVertex a = new NameDistinctVertex("a");
		NameDistinctVertex b = new NameDistinctVertex("b");
		NameDistinctVertex c = new NameDistinctVertex("c");
		NameDistinctVertex d = new NameDistinctVertex("d");

		gc.addRoot(root);
		gc.addDepend(root, a);
		gc.addDepend(root, b);
		gc.addDepend(a, b);
		gc.addDepend(b, d);
		gc.performDFS();
		System.out.println("==================================");
		System.out.print(gc.toGraphviz());
		System.out.println("==================================");
	}
}
