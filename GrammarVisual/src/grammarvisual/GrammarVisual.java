package grammarvisual;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
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
	public static void main(String[] args) throws FileNotFoundException, ParserException, LexerException, IOException {

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

		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<GraphNode, GraphLink> layout = new CircleLayout(dep);
		layout.setSize(new Dimension(300, 300)); // sets the initial size of the space

		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<GraphNode, GraphLink> vv =
				new BasicVisualizationServer<GraphNode, GraphLink>(layout);
		vv.setPreferredSize(new Dimension(350, 350)); //Sets the viewing area size


		Transformer<GraphNode, Paint> vertexPaint = new Transformer<GraphNode, Paint>() 
		{
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
