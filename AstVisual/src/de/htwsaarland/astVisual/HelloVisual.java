package de.htwsaarland.astVisual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.border.RoundedBorder;
import org.openide.util.ImageUtilities;
import org.openide.util.Utilities;

/**
 *
 * @author phucluoi
 * @version May 7, 2012
 */
public class HelloVisual {
	public static void main(String[] arg)
	{
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
		
		
		/*
		JDialog dia = new JDialog (new JDialog (), true);
		dia.add (panel, BorderLayout.CENTER);
		dia.setSize (800, 600);
		dia.setVisible (true);
		dia.dispose ();
		// verdammt wichtig
		dia.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		*/ 
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
