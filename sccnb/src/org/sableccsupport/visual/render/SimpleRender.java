package org.sableccsupport.visual.render;

import de.htwsaarland.astVisual.graphRepresent.GraphContainer;
import de.htwsaarland.astVisual.graphVisual.AstGraphScene;
import javax.swing.JComponent;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author phucluoi
 * @version May 30, 2012
 */
public class SimpleRender implements Render
{
	@Override
	public JComponent renderGraph(GraphContainer gc) {
		gc.performDFS();
		System.out.println(gc);
		
		AstGraphScene ags = new AstGraphScene();
		ags.setup(true);
		ags.portGraph(gc);
		ags.setLayout();

		Scene s = ags.getScene();
		s.getActions ().addAction (ActionFactory.createMouseCenteredZoomAction (1.1));
		s.getActions ().addAction (ActionFactory.createWheelPanAction());
		//SceneSupport.show(s);
		return s.createView();
	}

}
