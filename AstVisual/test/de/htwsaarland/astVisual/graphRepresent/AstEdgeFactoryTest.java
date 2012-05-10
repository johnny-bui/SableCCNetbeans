package de.htwsaarland.astVisual.graphRepresent;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author phucluoi
 * @version 03.05.2012
 */
public class AstEdgeFactoryTest {
	
	public AstEdgeFactoryTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	/**
	 * Test of createEdge method, of class AstEdgeFactory.
	 */
	@Test
	public void testCreateEdge() {
		System.out.println("createEdge");
		AstEdgeFactory factory = new AstEdgeFactory(AstEdge.class);
		DefaultAstVertex src = new DefaultAstVertex("a");
		DefaultAstVertex target = new DefaultAstVertex("b");
		AstEdge edge = factory.createEdge(src, target);
		assertEquals(src, edge.getSource());
		assertEquals(target, edge.getTarget());
	}
}
