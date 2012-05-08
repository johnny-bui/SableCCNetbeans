package de.htwsaarland.astVisual.testArchiv;

import de.htwsaarland.astVisual.AstEdge;
import de.htwsaarland.astVisual.NameDistinctVertex;
import java.awt.Color;
import java.util.Collection;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.border.RoundedBorder;
import org.openide.util.ImageUtilities;

/**
 *
 * @author phucluoi
 * @version May 7, 2012
 */
public class HelloVisual {
	public static void main(String[] arg)
	{
		/*
		Scene scene = new Scene ();
		scene.setLayout(LayoutFactory.createHorizontalFlowLayout());
		JComponent sceneView = scene.createView ();
		JScrollPane panel = new JScrollPane (sceneView);


		// create the icon node widget
		Widget tools = new Widget (scene);
		// use a vertical layout
		tools.setLayout (
				LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.CENTER, 4));
		// add a image child widget
		tools.addChild (new ImageWidget (scene, ImageUtilities.loadImage("./img/Tools.png")));
		// add a label child widget
		tools.addChild (new LabelWidget (scene, "Tools"));
		// set border for tools
		tools.setBorder(new RoundedBorder(10, 10, 5, 5, Color.yellow, Color.gray));
		// set tooltip
		tools.setToolTipText("Werkzeug zum Bauen");
		// add it to the scene
		scene.addChild (tools);
		
		
		// create the icon node widget
		Widget home = new Widget (scene);
		// use a vertical layout
		home.setLayout (
				LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.CENTER, 4));
		// add a image child widget
		home.addChild (new ImageWidget (scene, ImageUtilities.loadImage("./img/Home.png")));
		// add a label child widget
		home.addChild (new LabelWidget (scene, "Home"));
		// set border for tools
		home.setBorder(new RoundedBorder(10, 10, 5, 5, Color.yellow, Color.gray));
		// set tooltip
		home.setToolTipText("Mein Zuhaus");
		// add it to the scene
		scene.addChild (home);
		
		
		
		JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().setSize(800, 600);
		frame.getContentPane().add(panel);
		//Display the window.
        //frame.pack();
        frame.setVisible(true);
		*/
		
		AstGraphScene<NameDistinctVertex,AstEdge<NameDistinctVertex>> gs = 
				new AstGraphScene<NameDistinctVertex, AstEdge<NameDistinctVertex>>() ;
		
		NameDistinctVertex v1 = new NameDistinctVertex("v1");
		NameDistinctVertex v2 = new NameDistinctVertex("v2");
		NameDistinctVertex v3 = new NameDistinctVertex("v1");
		NameDistinctVertex v4 = new NameDistinctVertex("v2");
		gs.addNode(v1);
		gs.addNode(v2);
		
		try{
			if (!gs.isNode(v3))
			{
				gs.addNode(v3);
			}
			if (!gs.isNode(v4))
			{
				gs.addNode(v4);
			}
		}catch(Error ex)
		{
			System.out.println("catched :P" + ex);
			throw  new RuntimeException(ex.toString());
		}finally
		{
			
		}
		
		Collection<NameDistinctVertex> c = gs.getNodes();
		System.out.println(c.size());

		Scene scene = gs.getScene();
		scene.setLayout(LayoutFactory.createVerticalFlowLayout());
		JComponent sceneView = scene.createView ();
		
		JScrollPane panel = new JScrollPane (sceneView);
		JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().setSize(800, 600);
		frame.getContentPane().add(panel);
		//Display the window.
        //frame.pack();
        frame.setVisible(true);
	}
}
