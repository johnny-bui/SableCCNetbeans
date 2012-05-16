package org.sableccsupport.action;

import java.awt.Color;
import java.io.*;
import javax.swing.JComponent;
import org.sablecc.sablecc.*;
import org.sablecc.sablecc.lexer.Lexer;
import org.sablecc.sablecc.lexer.LexerException;
import org.sablecc.sablecc.node.Start;
import org.sablecc.sablecc.parser.Parser;
import org.sablecc.sablecc.parser.ParserException;
import org.sableccsupport.visual.GrammarVisualizerTopComponent;
import org.sableccsupport.visual.Visualizer;

/**
 *
 * @author verylazyboy
 * @version May, 11 2012
 * 
 */
public class VisualizerCaller 
{
	
	public static void callVisualizer(String fileName, GrammarVisualizerTopComponent visualizer)
	{
		VisualizerHelper h = new VisualizerHelper();
		h.setup(fileName, visualizer);
		h.start();
	}
}




class VisualizerHelper extends Thread
{
	private String filename;
	static PrintStream orgOutStream 	= null;
	static PrintStream orgErrStream 	= null;
	private static Color errorColor = Color.decode(EmbeddedSableCC.ERROR_COLOR_OUTPUR);
	private Visualizer graphDisplay;
	
	public void setup(String filename, Visualizer graphDisplay)
	{
		this.filename = filename;
		this.graphDisplay = graphDisplay;
	}

	@Override
	public void run() {
		try {
			IORedirect.redirectSystemStreams();
			String msg = "+++++++++++++++++" + filename + "+++++++++++++";
			System.out.println (msg);
			try{
				// ensure that the "real" sablecc can compile the 
				// grammar and create parser, lexer etc.
				SableCC.processGrammar(filename, null);
			}catch (Exception ex)
			{
				graphDisplay.updateStatus("Parse SableCC file error: " + ex.getMessage());
				throw ex;// so the output window can show the exception, too
			}
			// create a Parser chain
			Start tree = null;
			try{
				Parser p = new Parser(
						new Lexer(
							new PushbackReader(
								new FileReader(filename)
								)));
				tree = p.parse();
				graphDisplay.updateStatus("parse ok");
			}catch(Exception ex)
			{
				graphDisplay.updateStatus("Cannot create AST: " + ex.getMessage());
				throw ex;
			}
			try{
				TokenRegister tokenReg = new TokenRegister();
				tree.apply(tokenReg);
				//ConDiagnoser conDiagnoser = new CstDiagnoser(tokenReg); 
				//tree.apply(conDiagnoser);
				AstDiagnoser astDiagnoser = new AstDiagnoser(tokenReg);
				tree.apply(astDiagnoser);
				if (astDiagnoser.hasAST())
				{
					graphDisplay.updateStatus("construct AST depenedent graph ok");
					graphDisplay.replaceNewGraph(astDiagnoser.getAstView());
				}else
				{
					graphDisplay.updateStatus("there is no AST to show -> constructing CST");
					CstDiagnoser conDiagnoser = new CstDiagnoser(tokenReg);
					tree.apply(conDiagnoser);
					JComponent conVisual = conDiagnoser.getAstView();
					graphDisplay.updateStatus("construct CST depenedent graph ok");
					graphDisplay.replaceNewGraph(conVisual);
					
				}
			}catch(Exception ex)
			{
				graphDisplay.updateStatus("Cannot construct graph for AST: " + ex.getMessage());
				throw ex;
			}
		}catch (IOException ex)
		{
			System.err.println(ex.getMessage());
		}catch (LexerException ex)
		{
			System.err.println(ex.getMessage());
		}catch (ParserException ex)
		{
			System.err.println(ex.getMessage());
		}catch (Exception ex) 
		{
			// unexpected exceptions go here, show them in output window.
			// (with stack trace)
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}finally
		{
			String msg = "================= end of visualization =================" ;
			System.out.println (msg);
			IORedirect.setBackOutput();
		}
    }
	
}


