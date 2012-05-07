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
public class AstGraphScene<V extends AstVertex,E extends AstEdge<V>>
	extends GraphScene<V,E>
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
	protected Widget attachNodeWidget(V n) 
	{	//TODO: improve this
		IconNodeWidget widget = new IconNodeWidget (this);
        widget.setLabel ("Node: " + n.getName());

        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());
        actions.addAction (moveAction);

        mainLayer.addChild (widget);
        return widget; 
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected Widget attachEdgeWidget(E e) 
	{	// TODO: use information in e to set style of edge (widget)
		ConnectionWidget widget = new ConnectionWidget (this);
        widget.setTargetAnchorShape (AnchorShape.TRIANGLE_FILLED);

        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());

        connectionLayer.addChild (widget);
        return widget;
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected void attachEdgeSourceAnchor(
			AstEdge edge, AstVertex oldSourceNode, AstVertex sourceNode)
	{
		ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget sourceNodeWidget = findWidget (sourceNode);
        Anchor sourceAnchor = AnchorFactory.createRectangularAnchor (sourceNodeWidget);
        edgeWidget.setSourceAnchor (sourceAnchor);
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected void attachEdgeTargetAnchor(
			AstEdge edge, AstVertex oldTargetNode, AstVertex targetNode)
	{	
		ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget targetNodeWidget = findWidget (targetNode);
        Anchor targetAnchor = AnchorFactory.createRectangularAnchor (targetNodeWidget);
        edgeWidget.setTargetAnchor (targetAnchor);
	}

}
