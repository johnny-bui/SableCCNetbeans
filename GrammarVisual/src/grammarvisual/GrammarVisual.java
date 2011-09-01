package grammarvisual;

import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.PolarPoint;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;
import org.sablecc.sablecc.lexer.Lexer;
import org.sablecc.sablecc.lexer.LexerException;
import org.sablecc.sablecc.node.Start;
import org.sablecc.sablecc.parser.Parser;
import org.sablecc.sablecc.parser.ParserException;

/**
 *
 * @author phucluoi
 */
public class GrammarVisual {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) 
			throws FileNotFoundException, ParserException, LexerException, IOException 
	{
		System.out.println(System.getProperty("user.dir"));
		String smallPascal = "./src/PostFix_1.scc";

		Parser p =
				new Parser(
				new Lexer(
				new PushbackReader(
				new FileReader(smallPascal), 1024)));
		Start s = p.parse();
		GraphMaker gk = new GraphMaker();
		s.apply(gk);
		Graph dep = gk.getDependentGraph();

		System.out.println(dep);

		Transformer<GraphNode, Point2D> locationTransformer = 
				new Transformer<GraphNode, Point2D>() 
		{
            @Override
            public Point2D transform(GraphNode vertex) {
                int value =  20;
                return new Point2D.Double((double) value, (double) value);
            }
        };

		
		CircleLayout<GraphNode,GraphLink> staticLayout = 
				new CircleLayout<GraphNode, GraphLink>(dep/*,locationTransformer*/);
		staticLayout.setSize(new Dimension(450, 450));

		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<GraphNode, GraphLink> layout = 
				new FRLayout<GraphNode, GraphLink> (dep);
		layout.setInitializer(staticLayout);
		
		//layout.setSize(new Dimension(450, 450)); // sets the initial size of the space

		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<GraphNode, GraphLink> vv =
				new BasicVisualizationServer<GraphNode, GraphLink>(layout);
		vv.setPreferredSize(new Dimension(500, 500)); //Sets the viewing area size


		Transformer<GraphNode, Paint> vertexPaint = new Transformer<GraphNode, Paint>() 
		{
			@Override
			public Paint transform(GraphNode i) 
			{
				if (i.isToken)
					return Color.GREEN;
				else
					return Color.BLUE;
			}
		};
		// Set up a new stroke Transformer for the edges
		float dash[] = {10.0f};
		
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		
		Transformer<GraphLink, Stroke> edgeStrokeTransformer =
				new Transformer<GraphLink, Stroke>() 
		{
			@Override
			public Stroke transform(GraphLink s)
			{// TODO: specify edges here
				return edgeStroke;
			}
		};
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);


		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}
}
