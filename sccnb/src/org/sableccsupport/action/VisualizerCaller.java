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
import org.sableccsupport.visual.render.Render;
import org.sableccsupport.visual.render.SimpleRender;

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
	private Visualizer graphDisplay;
	private Render graphRender;
	
	public void setup(String filename, Visualizer graphDisplay)
	{
		this.filename = filename;
		this.graphDisplay = graphDisplay;
		graphRender = new SimpleRender();
	}

	@Override
	public void run() {
		try {
			IORedirect.redirectSystemStreams();
			String msg = "+++++++++++++++++" + filename + "+++++++++++++";
			System.out.println (msg);
			
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
			}
			catch(IOException ex)
			{
				graphDisplay.updateStatus("Cannot create AST: " + ex.getMessage());
				throw ex;// throw them to leave the block, not nice, to expensive but it works
			}catch (LexerException ex)
			{
				graphDisplay.updateStatus("Cannot create AST: " + ex.getMessage());
				throw ex;
			}catch (ParserException ex)
			{
				graphDisplay.updateStatus("Cannot create AST: " + ex.getMessage());
				throw ex;
			}
			
			try{
				TokenRegister tokenReg = new TokenRegister();
				tree.apply(tokenReg);
				//CstDiagnoser conDiagnoser = new CstDiagnoser(tokenReg); 
				//tree.apply(conDiagnoser);
				AstDiagnoser astDiagnoser = new AstDiagnoser(tokenReg);
				tree.apply(astDiagnoser);
				if (astDiagnoser.hasAST())
				{
					graphDisplay.updateStatus("construct AST depenedent graph ok");
					graphDisplay.replaceNewGraph(
						graphRender.renderGraph( astDiagnoser.getGraphContainer() ));
				}else
				{
					graphDisplay.updateStatus("there is no AST to show -> constructing CST");
					CstDiagnoser conDiagnoser = new CstDiagnoser(tokenReg);
					tree.apply(conDiagnoser);
					graphDisplay.updateStatus("construct CST depenedent graph ok");
					graphDisplay.replaceNewGraph(
						graphRender.renderGraph(conDiagnoser.getGraphContainer())
							);
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

