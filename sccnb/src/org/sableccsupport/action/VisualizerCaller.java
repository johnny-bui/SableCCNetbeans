package org.sableccsupport.action;

import com.dreamer.outputhandler.OutputHandler;
import java.awt.Color;
import java.io.*;
import javax.swing.JComponent;
import org.sablecc.sablecc.*;
import org.sablecc.sablecc.lexer.Lexer;
import org.sablecc.sablecc.node.Start;
import org.sablecc.sablecc.parser.Parser;
import org.sableccsupport.visual.GrammarVisualizerTopComponent;
import org.sableccsupport.visual.Visualizer;

/**
 *
 * @author hbui
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
				SableCC.processGrammar(filename, null);
			}catch (Exception ex)
			{
				graphDisplay.updateStatus("Parse SableCC file error: " + ex.getMessage());
				throw ex;
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
				//ConDiagnoser conDiagnoser = new ConDiagnoser(tokenReg); 
				//tree.apply(conDiagnoser);
				AstDiagnoser astDiagnoser = new AstDiagnoser(tokenReg);
				tree.apply(astDiagnoser);
				if (astDiagnoser.hasAST())
				{
					graphDisplay.updateStatus("construct graph ok");
					graphDisplay.replaceNewGraph(astDiagnoser.getAstView());
				}else
				{
					graphDisplay.updateStatus("there is no AST to show");
				}
			}catch(Exception ex)
			{
				graphDisplay.updateStatus("Cannot construct graph for AST: " + ex.getMessage());
				throw ex;
			}
			//
			msg = "================= build success  =================" ;
			System.out.println (msg);
		} catch (Exception ex) {
			//Exceptions.printStackTrace(ex);
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}finally
		{
			IORedirect.setBackOutput();
		}
    }
	
}


