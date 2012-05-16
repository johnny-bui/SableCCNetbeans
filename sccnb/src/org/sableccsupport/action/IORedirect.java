package org.sableccsupport.action;

import com.dreamer.outputhandler.OutputHandler;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.sablecc.sablecc.EmbeddedSableCC;

/**
 *
 * @author verylazyboy
 * @version May, 14 2012
 * 		initial version, move this class from a private class in SableCCCaller.
 */
public class IORedirect 
{
	public static final Color ERROR_COLOR = Color.decode(EmbeddedSableCC.ERROR_COLOR_OUTPUR);
	static PrintStream orgOutStream 	= null;
	static PrintStream orgErrStream 	= null;
	
	public static void redirectSystemStreams() 
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
						String.valueOf((char) i), ERROR_COLOR);
            }

            @Override
            public void write(byte[] bytes) throws IOException {
                OutputHandler.output(EmbeddedSableCC.SABLE_CC_OUTPUT_TITLE, 
						new String(bytes), ERROR_COLOR);
            }

            @Override
            public void write(byte[] bytes, int off, int len) throws IOException {
                OutputHandler.output(EmbeddedSableCC.SABLE_CC_OUTPUT_TITLE, 
						new String(bytes, off, len), ERROR_COLOR);
            }
        };
		orgOutStream = System.out;
		orgErrStream = System.err;
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(err, true));
    }

	public static void setBackOutput()
	{
		System.setErr(orgErrStream);
		System.setOut(orgOutStream);
	}
	
}
