package org.sableccsupport.action;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PushbackReader;
import org.sablecc.sablecc.AstDiagnoser;
import org.sablecc.sablecc.CstDiagnoser;
import org.sablecc.sablecc.TokenRegister;
import org.sablecc.sablecc.lexer.Lexer;
import org.sablecc.sablecc.lexer.LexerException;
import org.sablecc.sablecc.node.Start;
import org.sablecc.sablecc.parser.Parser;
import org.sablecc.sablecc.parser.ParserException;







/**
 *
 * @author verylazyboy
 * @version May, 10 2012
 * 		initial version
 * 
 */
public class DiagnoserCaller 
{
	public static void callAnalyzer(String filename)
	{
		AnalyzerHelper h = new AnalyzerHelper();
		h.setup(filename);
		h.start();
	}
}


class AnalyzerHelper extends Thread
{
	private String filename;
	static PrintStream orgOutStream 	= null;
	static PrintStream orgErrStream 	= null;
	public void setup(String filename)
	{
		this.filename = filename;
	}

	@Override
	public void run() {
		try {
			IORedirect.redirectSystemStreams();
			String msg = "-----------------" + filename + "-------------";
			System.out.println (msg);
			Parser p = new Parser(
					new Lexer(
						new PushbackReader(
							new FileReader(filename)
							)));
			Start tree = p.parse();
			TokenRegister tokenReg = new TokenRegister();
			tree.apply(tokenReg);
			
			CstDiagnoser conDiagnoser = new CstDiagnoser(tokenReg); 
			tree.apply(conDiagnoser);
			
			AstDiagnoser astDiagnoser = new AstDiagnoser(tokenReg);
			tree.apply(astDiagnoser);

			
			msg = "================= summary  =================" ;
			System.out.println(msg);
			
			/* Token duplication, ect */
			int tokenError = tokenReg.getError();
			if (tokenError == 0)
			{
				System.out.println("No token error found");
			}else
			{
				System.out.println("Found " + tokenError + " token error(s).");
			}
			
			/* con proble, ect */
			int conProductionError = conDiagnoser.getError();
			if (conProductionError == 0)
			{
				System.out.println("No (CST) production error found");
			}else
			{
				System.out.println("Found " + conProductionError + " (CST) production error(s).");
			}

			/*ast problem, ect*/
			int productionError = astDiagnoser.getError();
			if (productionError == 0)
			{
				System.out.println("No (AST) production error found");
			}else
			{
				System.out.println("Found " + productionError + " (AST) production error(s).");
			}

			
		}catch (IOException ex)
		{
			System.err.println(ex.getMessage());
		} catch (LexerException ex) {
			System.err.println(ex.getMessage());
		}catch (ParserException ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
			String msg = "================= end of diagnosis  =================" ;
			System.out.println (msg);
			IORedirect.setBackOutput();
		}
    }
}

