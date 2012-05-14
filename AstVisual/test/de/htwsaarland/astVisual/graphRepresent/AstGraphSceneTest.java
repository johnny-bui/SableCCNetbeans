/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htwsaarland.astVisual.graphRepresent;

import de.htwsaarland.astVisual.graphVisual.DummyGraphScene;
import de.htwsaarland.astVisual.graphRepresent.AstEdge;
import de.htwsaarland.astVisual.graphVisual.AstGraphScene;
import java.util.Collection;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Scene;
import de.htwsaarland.astVisual.testArchiv.SceneSupport;

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
		GraphContainer gc = new GraphContainer();
		gc.addRoot("session");
		gc.addDepend("session", "statement_list");
		gc.addDepend("statement_list", "statement");
		gc.addDepend("statement_list", "statement");
		gc.addDepend("statement", "expr_sttm");
		gc.addDepend("statement", "assign_sttm");
		gc.addDepend("expr_sttm", "add_expr");
		gc.addDepend("expr_sttm", "add_expr");
		gc.addDepend("expr_sttm", "mul_expr");
		
		gc.performDFS();

		AstGraphScene ags = new AstGraphScene();
		ags.portGraph(gc);
		ags.setLayout();

		Scene s = ags.getScene();
		
		SceneSupport.show(s);
	}
	
}
