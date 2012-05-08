package de.htwsaarland.astVisual.testArchiv;

import de.htwsaarland.astVisual.AstEdge;
import de.htwsaarland.astVisual.AstVertex;
import java.util.Iterator;
import java.util.List;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 *
 * @author phucluoi
 * @version May 7, 2012
 */
public class AstGraphScene<V extends AstVertex,E>
	extends GraphScene <V,AstEdge>
{
	//private Widget mainLayer;
    private Widget connectionLayer;
	private LayerWidget mainLayer;
    //private LayerWidget connectionLayer;
	private Router router;
	
    private WidgetAction moveAction = ActionFactory.createMoveAction ();
	
	public AstGraphScene()
	{
		//mainLayer = new Widget (this);
		mainLayer = new LayerWidget(this);
        addChild (mainLayer);
        connectionLayer = new Widget (this);
        //connectionLayer = new LayerWidget(this);
        addChild (connectionLayer);
		router = RouterFactory.createOrthogonalSearchRouter(mainLayer);	
	}
	
	@Override
	protected Widget attachNodeWidget(V node) {
		IconNodeWidget widget = new IconNodeWidget (this);
        widget.setLabel ("Node: " + node.getName());

        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());
        actions.addAction (moveAction);

        mainLayer.addChild (widget);
        return widget;
	}

	@Override
	protected Widget attachEdgeWidget(AstEdge edge) {
		ConnectionWidget widget = new ConnectionWidget (this);
        widget.setTargetAnchorShape (AnchorShape.TRIANGLE_FILLED);

        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());

        connectionLayer.addChild (widget);
		/* =================================== */
		widget.setPaintControlPoints(true);
		widget.setControlPointShape(PointShape.SQUARE_FILLED_SMALL);
		widget.setRouter (RouterFactory.createOrthogonalSearchRouter (mainLayer));
		widget.getActions ().addAction (ActionFactory.createAddRemoveControlPointAction ());
        widget.getActions ().addAction (ActionFactory.createFreeMoveControlPointAction ());
        return widget;
	}

	@Override
	protected void attachEdgeSourceAnchor(
			AstEdge edge, V oldSource, V source) 
	{
		ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget sourceNodeWidget = findWidget (source);
        Anchor sourceAnchor = AnchorFactory.createRectangularAnchor (sourceNodeWidget);
        edgeWidget.setSourceAnchor (sourceAnchor);
	}

	@Override
	protected void attachEdgeTargetAnchor(
			AstEdge edge, V oldTargetNode, V targetNode)
	{
		ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget targetNodeWidget = findWidget (targetNode);
        Anchor targetAnchor = AnchorFactory.createRectangularAnchor (targetNodeWidget);
        edgeWidget.setTargetAnchor (targetAnchor);
	}

	
}

