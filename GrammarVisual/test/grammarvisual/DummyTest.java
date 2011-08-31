/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grammarvisual;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author phucluoi
 */
public class DummyTest {
	
	public DummyTest() {
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
	 * Test of toString method, of class GraphNode.
	 */
	@Test
	public void testNodeDup() 
	{
		DirectedSparseGraph<GraphNode,GraphLink> 
				testGraph = new DirectedSparseGraph<GraphNode,GraphLink>();
		GraphNode n1 = new GraphNode("n1", true);
		GraphNode n2 = new GraphNode("n2", false);
		
		GraphNode n3 = new GraphNode("n3", true);
		GraphNode n4 = new GraphNode("n4", false);

		GraphLink l1 = new GraphLink();
		GraphLink l2 = new GraphLink();

		boolean added1 = testGraph.addEdge(l1, n1, n2);
		assertTrue(added1);
		boolean added2 = testGraph.addEdge(l2, n3, n4);
		assertTrue(added2);
		/* because l1 and l2 is diffrence */

		GraphLink l3 = new GraphLink();
		
		/* DirectedSparseGraph permits more than one Edge with same endpoints,
		 * but it don't change the graph
		 * therefore this command is not legal.
		 */
		boolean dupNode = testGraph.addVertex(n1);
		assertFalse(dupNode);
		
		/* we create a new GraphNode, which equals the GraphNode n1.  */
		GraphNode n5 = new GraphNode("n1", true);
		dupNode = testGraph.addVertex(n5);
		
		if (n5.equals(n1))
			assertFalse(dupNode);
		else
			assertTrue(dupNode);

		boolean added3 = testGraph.addEdge(l3, n1, n2, EdgeType.DIRECTED);
		assertFalse(added3);
		
		GraphLink equal_l1 = testGraph.findEdge(n1, n2);
		assertEquals(equal_l1, l1);
		assertNotSame(equal_l1, l2);/*event l1 and l2 have same parameters */
		GraphLink n1n2 = testGraph.findEdge(n1, n2);
		assertEquals(l1, n1n2);
		boolean added = testGraph.addVertex(n1);
		assertFalse(added);

		boolean recursiv = testGraph.addEdge(new GraphLink(), n1, n1);
		assertTrue(recursiv);
		System.out.println(testGraph);
	}
}
