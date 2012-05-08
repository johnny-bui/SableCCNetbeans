package de.htwsaarland.astVisual;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 *
 * @author phucluoi
 * @version May 7, 2012
 */
public class AstGraphScene<V extends AstVertex,E>
	extends GraphScene <V,AstEdge<AstVertex>>
{
	private Widget mainLayer;
    private Widget connectionLayer;

    private WidgetAction moveAction = ActionFactory.createMoveAction ();
	
	public AstGraphScene()
	{
		mainLayer = new Widget (this);
        addChild (mainLayer);
        connectionLayer = new Widget (this);
        addChild (connectionLayer);
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
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected Widget attachEdgeWidget(AstEdge<AstVertex> edge) {
		ConnectionWidget widget = new ConnectionWidget (this);
        widget.setTargetAnchorShape (AnchorShape.TRIANGLE_FILLED);

        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());

        connectionLayer.addChild (widget);
        return widget;
	}

	@Override
	protected void attachEdgeSourceAnchor(
			AstEdge<AstVertex> edge, V oldSource, V source) 
	{
		ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget sourceNodeWidget = findWidget (source);
        Anchor sourceAnchor = AnchorFactory.createRectangularAnchor (sourceNodeWidget);
        edgeWidget.setSourceAnchor (sourceAnchor);
	}

	@Override
	protected void attachEdgeTargetAnchor(
			AstEdge<AstVertex> edge, V oldTargetNode, V targetNode)
	{
		ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget targetNodeWidget = findWidget (targetNode);
        Anchor targetAnchor = AnchorFactory.createRectangularAnchor (targetNodeWidget);
        edgeWidget.setTargetAnchor (targetAnchor);
	}
}

