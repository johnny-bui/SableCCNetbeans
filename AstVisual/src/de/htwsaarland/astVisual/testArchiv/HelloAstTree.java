package de.htwsaarland.astVisual.testArchiv;

import de.htwsaarland.astVisual.graphRepresent.GraphContainer;
import de.htwsaarland.astVisual.graphRepresent.VertexType;
import de.htwsaarland.astVisual.graphVisual.AstGraphScene;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author hbui
 */
public class HelloAstTree {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		GraphContainer gc = new GraphContainer();
		
		gc.addRoot("session");
gc.addDepend("session", "statement_list");
gc.addDepend("statement_list", "statement");
gc.addDepend("statement_list", "statement");
gc.addDepend("statement_list", VertexType.PROD, "semi", VertexType.TOKEN);
gc.addDepend("statement_list", "statement_list");
gc.addDepend("statement", "abst_expr");
gc.addDepend("statement", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", "abst_expr");
gc.addDepend("abst_expr", "abst_expr");
gc.addDepend("abst_expr", "abst_expr");
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", "abst_expr");
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", "abst_expr");
gc.addDepend("abst_expr", "abst_expr");
gc.addDepend("abst_expr", "abst_expr");
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", "abst_expr");
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", VertexType.PROD, "number", VertexType.TOKEN);
gc.addDepend("abst_expr", VertexType.PROD, "identifier", VertexType.TOKEN);
gc.addDepend("abst_expr", "arguments");
gc.addDepend("arguments", "abst_expr");
gc.addDepend("arguments", VertexType.PROD, "identifier", VertexType.TOKEN);
				
		//gc.performDFS();
		
		System.out.println(gc);
		
		AstGraphScene ags = new AstGraphScene();
		ags.portGraph(gc);
		ags.setLayout();

		Scene scene = ags.getScene();
		scene.getActions ().addAction (ActionFactory.createMouseCenteredZoomAction (1.1));
		scene.getActions ().addAction (ActionFactory.createWheelPanAction());
		SceneSupport.show(scene);
	}
}
