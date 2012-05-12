package de.htwsaarland.astVisual.testArchiv;

import de.htwsaarland.astVisual.graphVisual.AstGraphScene;
import de.htwsaarland.astVisual.graphRepresent.AstEdge;
import de.htwsaarland.astVisual.graphRepresent.GraphContainer;
import de.htwsaarland.astVisual.graphRepresent.NameDistinctVertex;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.graph.layout.GraphLayoutSupport;
import org.netbeans.api.visual.graph.layout.GridGraphLayout;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author phucluoi
 * @version May 8, 2012
 */
public class HelloAst 
{
	public static void main(String[	] argv)
	{
		GraphContainer<NameDistinctVertex> 
				gc = new GraphContainer<NameDistinctVertex>();
		NameDistinctVertex root = new NameDistinctVertex("root");
		NameDistinctVertex a1 = new NameDistinctVertex("a11111111111111111");
		NameDistinctVertex a2 = new NameDistinctVertex("a2");
		NameDistinctVertex a3 = new NameDistinctVertex("a3");
		
		gc.addRoot(root);
		gc.addDepend(root, a1);
		gc.addDepend(root, a2);
		gc.addDepend(a1, a3);	
		gc.addDepend(a1, a2);
		gc.addDepend(a2, a1);

		AstGraphScene<NameDistinctVertex,AstEdge<NameDistinctVertex>>
				ags = new AstGraphScene<NameDistinctVertex, AstEdge<NameDistinctVertex>>();
		Set <AstEdge> s = gc.getDgraph().edgeSet();
		Iterator i = s.iterator();
		while(i.hasNext()) {
			AstEdge<NameDistinctVertex> e = (AstEdge<NameDistinctVertex>) i.next();
			
			if (!ags.isNode(e.getSource()))
			{
				ags.addNode(e.getSource());
			}
			if (!ags.isNode(e.getTarget()))
			{
				ags.addNode(e.getTarget());
			}
			if (!ags.isEdge(e))
			{
				ags.addEdge(e);
			}
			ags.setEdgeSource(e, e.getSource());
			ags.setEdgeTarget(e, e.getTarget());
		}

		/*
		Scene scene = ags.getScene();
		//scene.setLayout(LayoutFactory.createVerticalFlowLayout());
		//GraphLayout layout = new GridGraphLayout();
		GraphLayout<NameDistinctVertex,AstEdge> graphLayout 
				= GraphLayoutFactory.createTreeGraphLayout (100, 100, 50, 50, true);
		GraphLayoutSupport.setTreeGraphLayoutRootNode (graphLayout, gc.getRoot());
		SceneLayout sceneLayout = LayoutFactory.createSceneGraphLayout (ags, graphLayout);
		// schedules the graph-oriented layout after the scene validation
		// it just schedules it but does not perform immediatelly
		sceneLayout.invokeLayout ();
		// or you can call "sceneLayout.invokeLayoutImmediatelly ();"
		// to schedule the layout and force immediate scene validation
		// so the layout will be performed in the "invokeLayoutImmediatelly" method
		*/
		ags.setLayout();
		Scene scene = ags.getScene();
		JComponent sceneView = scene.createView ();
		
		JScrollPane panel = new JScrollPane (sceneView);
		JFrame frame = new JFrame("Hello Ast");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().setSize(800, 600);
		frame.getContentPane().add(panel);
		//Display the window.
        //frame.pack();
		frame.setVisible(true);
		
	}
}
