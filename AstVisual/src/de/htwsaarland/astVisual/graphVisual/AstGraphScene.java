package de.htwsaarland.astVisual.graphVisual;

import de.htwsaarland.astVisual.graphRepresent.*;
import java.awt.Color;
import java.util.Map;
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
public class AstGraphScene
	extends GraphScene <String, AstEdge>
{
	//private Widget mainLayer;
    private Widget connectionLayer;
	private LayerWidget mainLayer;
    //private LayerWidget connectionLayer;
	private String root = null;
	
    private WidgetAction moveAction = ActionFactory.createMoveAction ();
	private Map<String,VertexInfo> vertexInforTable;
	
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
	protected Widget attachNodeWidget(String node) {
		// add root if neccessary
		if (root == null)
		{
			root = node;
		}
		
		IconNodeWidget nodeWidget = new IconNodeWidget (this);
        nodeWidget.setLabel (node);
		
		/** styling the node. */
		if (vertexInforTable != null)
		{	
			VertexInfo info = vertexInforTable.get(node);
			
			int d = info.getDetected();
			int f = info.getFinished();
			LabelWidget dfsinfo = new LabelWidget(this, d + ":" + f);
			nodeWidget.addChild(dfsinfo);
			
			Color bg = VertexType.mapToColor(info.getType());
			nodeWidget.setBorder(
				BorderFactory.createRoundedBorder(10, 10, 6, 6, bg, Color.BLUE) );
		}else
		{
			nodeWidget.setBorder(
				BorderFactory.createRoundedBorder(10, 10, 6, 6, Color.LIGHT_GRAY, Color.BLUE) );
		}
		
		/** set some action. */
        WidgetAction.Chain actions = nodeWidget.getActions ();
        actions.addAction (createObjectHoverAction ()); /** <- hover action.*/
        actions.addAction (createSelectAction ());/** <- select action */
        actions.addAction (moveAction);/** <- move action */
		

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
			AstEdge edge, String oldSource, String source) 
	{
		ConnectionWidget edgeWidget = (ConnectionWidget) findWidget (edge);
        Widget sourceNodeWidget = findWidget (source);
        Anchor sourceAnchor = AnchorFactory.createRectangularAnchor (sourceNodeWidget);
        edgeWidget.setSourceAnchor (sourceAnchor);
	}

	@Override
	protected void attachEdgeTargetAnchor(
			AstEdge edge, String oldTargetNode, String targetNode)
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
		GraphLayout<String, AstEdge> graphLayout 
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

	
	public void portGraph(GraphContainer gc)
	{
		vertexInforTable = gc.getVertexInforTable();
		gc.performDFS();
		String root = gc.getRoot();
		addNode(gc.getRoot());
		Set<AstEdge> set = gc.getGraph().edgeSet();	
		for (AstEdge e : set)
		{
			String s =  e.getSource();
			String t =  e.getTarget();
			if (! isNode(s))
			{
				addNode(s);
			}
			if (! isNode(t))
			{
				addNode(t);
			}
			if (!isEdge(e))
			{
				if ( !e.getEdgeClass().equals(EdgeClass.C) )
				{
					addEdge(e);
					setEdgeSource(e, s);
					setEdgeTarget(e, t);
				}
			}
		}
	}
	
	
}

