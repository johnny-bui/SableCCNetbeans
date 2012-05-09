/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htwsaarland.astVisual;

import java.util.Collection;
import org.junit.*;
import static org.junit.Assert.*;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author hbui
 */
public class AstGraphSceneTest {
	
	public AstGraphSceneTest() {
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

	@Test
	public void testAddNode()
	{
		AstGraphScene<NameDistinctVertex,AstEdge<NameDistinctVertex>> gs = 
				new AstGraphScene<NameDistinctVertex, AstEdge<NameDistinctVertex>>() ;
		
		NameDistinctVertex v1 = new NameDistinctVertex("v1");
		NameDistinctVertex v2 = new NameDistinctVertex("v2");
		NameDistinctVertex v3 = new NameDistinctVertex("v1");
		NameDistinctVertex v4 = new NameDistinctVertex("v2");
		gs.addNode(v1);
		try{
			gs.addNode(v3);
		}catch(Error ex)
		{
			System.out.println("catched :" + ex.getMessage());
			assertTrue(ex instanceof Error);
		}
		gs.addNode(v2);
		try{
			gs.addNode(v4);
		}catch(Error ex)
		{
			System.out.println("catched :" + ex.getMessage());
			assertTrue(ex instanceof Error);
		}
		
		Collection<NameDistinctVertex> c = gs.getNodes();
		assertEquals(2, c.size());
		
		AstGraphScene<NameDistinctVertex,AstEdge<DefaultAstVertex>> gsc = 
				new AstGraphScene<NameDistinctVertex, AstEdge<DefaultAstVertex>>() ;

		DefaultAstVertex a1 = new DefaultAstVertex("a1");
		DefaultAstVertex a2 = new DefaultAstVertex("a2");
		DefaultAstVertex a3 = new DefaultAstVertex("a3");
		DefaultAstVertex a4 = new DefaultAstVertex("a4");

	}

	@Test
	public void trivialTest()
	{
		GraphScene <String,String> gs = new DummyGraphScene();
		//gs.addNode("a1");
		//gs.addNode("a1");
	}
}
