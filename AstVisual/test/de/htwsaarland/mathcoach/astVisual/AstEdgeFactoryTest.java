/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htwsaarland.mathcoach.astVisual;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

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
