package org.sableccsupport.action;

import com.dreamer.outputhandler.OutputHandler;
import java.awt.Color;
import java.io.*;
import org.sablecc.sablecc.EmbeddedSableCC;
import org.sablecc.sablecc.AstDiagnoser;
import org.sablecc.sablecc.ConDiagnoser;
import org.sablecc.sablecc.TokenRegister;
import org.sablecc.sablecc.lexer.Lexer;
import org.sablecc.sablecc.node.Start;
import org.sablecc.sablecc.parser.Parser;







/**
 *
 * @author hbui
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
	private static Color errorColor = Color.decode(EmbeddedSableCC.ERROR_COLOR_OUTPUR);
	public void setup(String filename)
	{
		this.filename = filename;
	}

	@Override
	public void run() {
		try {
			redirectSystemStreams();
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
			
			ConDiagnoser conDiagnoser = new ConDiagnoser(tokenReg); 
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
				System.out.println("No (con) production error found");
			}else
			{
				System.out.println("Found " + conProductionError + " (con) production error(s).");
			}

			/*ast problem, ect*/
			int productionError = astDiagnoser.getError();
			if (productionError == 0)
			{
				System.out.println("No (ast) production error found");
			}else
			{
				System.out.println("Found " + productionError + " (ast) production error(s).");
			}

			
			msg = "================= end of diagnosis  =================" ;
			System.out.println (msg);
		} catch (Exception ex) {
			//Exceptions.printStackTrace(ex);
			System.err.println(ex.getMessage());
		}finally
		{
			setBackOutput();
		}
    }



	private static void redirectSystemStreams() 
	{
        OutputStream out = new OutputStream() {

            @Override
            public void write(int i) throws IOException {
                OutputHandler.output(EmbeddedSableCC.SABLE_CC_OUTPUT_TITLE ,
						String.valueOf((char) i));
            }

            @Override
            public void write(byte[] bytes) throws IOException {
                OutputHandler.output(EmbeddedSableCC.SABLE_CC_OUTPUT_TITLE, 
						new String(bytes));
            }

            @Override
            public void write(byte[] bytes, int off, int len) throws IOException {
                OutputHandler.output(EmbeddedSableCC.SABLE_CC_OUTPUT_TITLE, 
						new String(bytes, off, len));
            }
        };

		OutputStream err = new OutputStream() {

            @Override
            public void write(int i) throws IOException {
                OutputHandler.output(EmbeddedSableCC.SABLE_CC_OUTPUT_TITLE ,
						String.valueOf((char) i), errorColor);
            }

            @Override
            public void write(byte[] bytes) throws IOException {
                OutputHandler.output(EmbeddedSableCC.SABLE_CC_OUTPUT_TITLE, 
						new String(bytes), errorColor);
            }

            @Override
            public void write(byte[] bytes, int off, int len) throws IOException {
                OutputHandler.output(EmbeddedSableCC.SABLE_CC_OUTPUT_TITLE, 
						new String(bytes, off, len), errorColor);
            }
        };
		orgOutStream = System.out;
		orgErrStream = System.err;
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(err, true));
    }

	private static void setBackOutput()
	{
		System.setErr(orgErrStream);
		System.setOut(orgOutStream);
	}
}

