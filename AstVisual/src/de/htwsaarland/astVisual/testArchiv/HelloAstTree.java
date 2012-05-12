package de.htwsaarland.astVisual.testArchiv;

import de.htwsaarland.astVisual.graphRepresent.AstVertex;
import de.htwsaarland.astVisual.graphRepresent.GraphContainer;
import de.htwsaarland.astVisual.graphRepresent.NameDistinctVertex;
import de.htwsaarland.astVisual.graphRepresent.VertexType;
import de.htwsaarland.astVisual.graphVisual.AstGraphScene;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author hbui
 * @version May 12. 2012
 */

public class HelloAstTree {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) 
	{
		GraphContainer<NameDistinctVertex> gc =
				new GraphContainer<NameDistinctVertex>();
		NameDistinctVertex root = new NameDistinctVertex("root");
		NameDistinctVertex a1 = new NameDistinctVertex("a1");
		NameDistinctVertex a2 = new NameDistinctVertex("a2");
		NameDistinctVertex a3 = new NameDistinctVertex("a3",VertexType.TOKEN);
		NameDistinctVertex a4 = new NameDistinctVertex("a4",VertexType.TOKEN);

		gc.addRoot(root);
		gc.addDepend(root, a1);
		gc.addDepend(root, a2);
		gc.addDepend(a1, a3);
		gc.addDepend(a1, a2);
		gc.addDepend(a2, a4);
		gc.performDFS();
		
		AstGraphScene<NameDistinctVertex,AstVertex> ags = new AstGraphScene<NameDistinctVertex, AstVertex>();
		ags.portGraph(gc);


		ags.setLayout();
		Scene scene = ags.getScene();
		JComponent sceneView = scene.createView ();
		
		JScrollPane panel = new JScrollPane (sceneView);
		JFrame frame = new JFrame("Hello Ast Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().setSize(800, 600);
		frame.getContentPane().add(panel);
		//Display the window.
        //frame.pack();
		frame.setVisible(true);
	}
}
