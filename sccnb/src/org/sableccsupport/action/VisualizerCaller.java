package org.sableccsupport.action;

import com.dreamer.outputhandler.OutputHandler;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JComponent;
import org.sablecc.sablecc.EmbeddedSableCC;
import org.sablecc.sablecc.SableCC;
import org.sableccsupport.visual.GrammarVisualizerTopComponent;

/**
 *
 * @author hbui
 */
public class VisualizerCaller 
{
	
	public static void callVisualizer(String fileName, GrammarVisualizerTopComponent visualizer)
	{
		// TODO
		visualizer.updateStatus("parsing ......");
	}
}




class VisualizerHelper extends Thread
{
	private String filename;
	static PrintStream orgOutStream 	= null;
	static PrintStream orgErrStream 	= null;
	private static Color errorColor = Color.decode(EmbeddedSableCC.ERROR_COLOR_OUTPUR);
	private GrammarVisualizerTopComponent graphDisplay;
	
	public void setup(String filename, GrammarVisualizerTopComponent graphDisplay)
	{
		this.filename = filename;
		this.graphDisplay = graphDisplay;
	}

	@Override
	public void run() {
		try {
			redirectSystemStreams();
			String msg = "+++++++++++++++++" + filename + "+++++++++++++";
			System.out.println (msg);
			SableCC.processGrammar(filename, null);
			
			msg = "================= build success  =================" ;
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


