package de.htwsaarland.astVisual.graphVisual;

import de.htwsaarland.astVisual.graphRepresent.*;
import java.awt.Color;
import java.util.Set;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.graph.layout.GraphLayoutSupport;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.*;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 *
 * @author phucluoi
 * @version May 7, 2012
 */
public class AstGraphScene<V extends AstVertex,E>
	extends GraphScene <V, AstEdge>
{
	//private Widget mainLayer;
    private Widget connectionLayer;
	private LayerWidget mainLayer;
    //private LayerWidget connectionLayer;
	private V root = null;
	
    private WidgetAction moveAction = ActionFactory.createMoveAction ();
	
	public AstGraphScene()
	{
		//mainLayer = new Widget (this);
		mainLayer = new LayerWidget(this);
        addChild (mainLayer);
        connectionLayer = new Widget (this);
        //connectionLayer = new LayerWidget(this);
        addChild (connectionLayer);
	}
	
	@Override
	protected Widget attachNodeWidget(V node) {
		// add root if neccessary
		if (root == null)
		{
			root = node;
		}
		//LabelWidget nodeLabel = new LabelWidget()
		
		IconNodeWidget nodeWidget = new IconNodeWidget (this);
        nodeWidget.setLabel (node.getName());
		
		Color bg = VertexType.mapToColor(node.getType());
		nodeWidget.setBorder(
			BorderFactory.createRoundedBorder(10, 10, 6, 6, bg, Color.BLUE) );
		
        WidgetAction.Chain actions = nodeWidget.getActions ();
		LabelWidget info = new LabelWidget(this, node.getDetected() + ":" + node.getFinished());
		nodeWidget.addChild(info);
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());
        actions.addAction (moveAction);

        mainLayer.addChild (nodeWidget);
        return nodeWidget;
	}

	@Override
	protected Widget attachEdgeWidget(AstEdge edge) {
		//VMDConnectionWidget nodeWidget = 
		//		new VMDConnectionWidget(this,VMDFactory.getNetBeans60Scheme());
		ConnectionWidget widget = new ConnectionWidget(this);
		//widget.setRouter(RouterFactory.createOrthogonalSearchRouter (mainLayer));
		//ConnectionWidget nodeWidget = new ConnectionWidget (this);
        widget.setTargetAnchorShape (AnchorShape.TRIANGLE_FILLED);
			
		// TODO classsify the edge using a method
		widget.setLineColor(EdgeClass.mapToColor(edge.getEdgeClass()) );// set color of the edge green
        
		
		WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());
		
		
        connectionLayer.addChild (widget);
		/* =================================== */
		/* =========== create router ========= */
		widget.setPaintControlPoints(true);
		widget.setControlPointShape(PointShape.SQUARE_FILLED_SMALL);
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
	

	public void setLayout()
	{
		Scene scene = getScene();
		//scene.setLayout(LayoutFactory.createVerticalFlowLayout());
		//GraphLayout layout = new GridGraphLayout();
		GraphLayout<V, AstEdge> graphLayout 
				= GraphLayoutFactory.createTreeGraphLayout (100, 100, 50, 50, true);
		GraphLayoutSupport.setTreeGraphLayoutRootNode (graphLayout, root);
		SceneLayout sceneLayout = LayoutFactory.createSceneGraphLayout (this, graphLayout);
		// schedules the graph-oriented layout after the scene validation
		// it just schedules it but does not perform immediatelly
		sceneLayout.invokeLayout ();
		// or you can call "sceneLayout.invokeLayoutImmediatelly ();"
		// to schedule the layout and force immediate scene validation
		// so the layout will be performed in the "invokeLayoutImmediatelly" method
	}

	public void portGraph(GraphContainer<V> gc)
	{
		addNode(gc.getRoot());
		Set<AstEdge> set = gc.getDgraph().edgeSet();	
		for (AstEdge e : set)
		{
			V s = (V) e.getSource();
			V t = (V) e.getTarget();
			if (! isNode(s))
			{
				addNode(s);
			}
			if (! isNode(t))
			{
				addNode(t);
			}
			addEdge(e);
			setEdgeSource(e, s);
			setEdgeTarget(e, t);
		}
	}
}

