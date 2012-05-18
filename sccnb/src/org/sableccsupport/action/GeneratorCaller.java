
package org.sableccsupport.action;

import java.io.FileReader;
import java.io.PushbackReader;
import org.sablecc.sablecc.FontAndColorGenerator;
import org.sablecc.sablecc.TokenEnumGenerator;
import org.sablecc.sablecc.lexer.Lexer;
import org.sablecc.sablecc.node.Start;
import org.sablecc.sablecc.parser.Parser;

/**
 *
 * @author hbui
 * @version May 18, 2012
 */
public class GeneratorCaller 
{
	public static void callGenerator(String filename)
	{
		GeneratorHelper h = new GeneratorHelper();
		h.setup(filename);
		h.start();
	}
}


class GeneratorHelper  extends Thread
{
	private String filename;
	
	public void setup(String filename)
	{
		this.filename = filename;
	}

	@Override
	public void run() 
	{
		try
		{
			IORedirect.redirectSystemStreams();	
			String msg = "-----------------" + filename + "-------------";
			System.out.println (msg);
			Parser p = new Parser(
					new Lexer(
						new PushbackReader(
							new FileReader(filename)
							)));
			Start tree = p.parse();
			TokenEnumGenerator tokenGenerator = new TokenEnumGenerator();
			tree.apply(tokenGenerator);
			FontAndColorGenerator facGenerator = new FontAndColorGenerator();
			tree.apply(facGenerator);
			msg = "/* ------------ TOKEN ENUMERATE ------------------------ */";
			System.out.println(tokenGenerator.getTokenLst());
			msg = "/* ----------------------------------------------------- */";
			System.out.println(facGenerator.getTokenLst());
			
		}catch(Exception ex)
		{
				
		}finally
		{
			IORedirect.setBackOutput();
		}
		
	}

	
}