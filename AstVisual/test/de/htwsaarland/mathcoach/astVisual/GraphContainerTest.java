/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htwsaarland.mathcoach.astVisual;

import java.util.Set;
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
	 * Test of performDFS method, of class GraphContainer.
	 */
	@Test
	public void testPerformDFS() {
		System.out.println("performDFS");
		GraphContainer g = new GraphContainer();
		// add a root for g
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






class DummyVertex implements AstVertex
{
	int d; 
	int f;
	String name;
	private AstVertex pred;

	public DummyVertex(String name)
	{
		this.name = name;
		d = 0;
		f = 0;
	}
	
	@Override
	public void setDetected(int d) {
		this.d = d;
	}

	@Override
	public int getDetected() {
		return d;
	}

	@Override
	public void setFinished(int f) {
		this.f = f;
	}

	@Override
	public int getFinished() {
		return f;
	}

	@Override
	public String toString()
	{
		if (pred != null)
		{
			return name + " d="+ d + " f=" + f + "(pred:"+ pred.getName() +")";
		}
		else
		{
			return name + " d="+ d + " f=" + f + "(pred: __null__)";
		}
	}

	@Override
	public void setPred(AstVertex v)
	{
		this.pred = v;
	}

	@Override
	public String getName()
	{
		return name;
	}
}

class DummyEdge extends AstEdge
{
	
}