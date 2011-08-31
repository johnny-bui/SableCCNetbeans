package grammarvisual;

import edu.uci.ics.jung.graph.Graph;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
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
	public static void main(String[] args) throws FileNotFoundException, ParserException, LexerException, IOException
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
	}
}
