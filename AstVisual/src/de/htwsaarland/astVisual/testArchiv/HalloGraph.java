package de.htwsaarland.astVisual.testArchiv;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 *
 * @author phucluoi
 * @version May 8, 2012
 */
public class HalloGraph 
{
	public static void main(String[] arg)
	{
		GraphScene gs = new MyGraphScene();
		String v1 = "v1";
		String v2 = "v1";
		String v3 = "v1";
		String v4 = "v2";

		gs.addNode(v1);
		gs.addNode(v2);
		gs.addNode(v3);
		gs.addNode(v4);

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

class MyGraphScene extends GraphScene<String, String> {

    private Widget mainLayer;
    private Widget connectionLayer;

    private WidgetAction moveAction = ActionFactory.createMoveAction ();

    public MyGraphScene () {
        mainLayer = new Widget (this);
        addChild (mainLayer);
        connectionLayer = new Widget (this);
        addChild (connectionLayer);
    }

    protected Widget attachNodeWidget (String node) {
        IconNodeWidget widget = new IconNodeWidget (this);
        widget.setLabel ("Node: " + node);

        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());
        actions.addAction (moveAction);

        mainLayer.addChild (widget);
        return widget;
    }

    protected Widget attachEdgeWidget (String edge) {
        ConnectionWidget widget = new ConnectionWidget (this);
        widget.setTargetAnchorShape (AnchorShape.TRIANGLE_FILLED);

        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());

        connectionLayer.addChild (widget);
        return widget;
    }

    protected void attachEdgeSourceAnchor (String edge, String oldSourceNode, String sourceNode) {
        ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget sourceNodeWidget = findWidget (sourceNode);
        Anchor sourceAnchor = AnchorFactory.createRectangularAnchor (sourceNodeWidget);
        edgeWidget.setSourceAnchor (sourceAnchor);
    }

    protected void attachEdgeTargetAnchor (String edge, String oldTargetNode, String targetNode) {
        ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget targetNodeWidget = findWidget (targetNode);
        Anchor targetAnchor = AnchorFactory.createRectangularAnchor (targetNodeWidget);
        edgeWidget.setTargetAnchor (targetAnchor);
    }

}
