package org.sableccsupport.action;

import java.awt.Color;
import java.io.PrintStream;
import org.sablecc.sablecc.EmbeddedSableCC;
import org.sablecc.sablecc.SableCC;

/**
 *
 * @author verylazyboy
 * @version [too old to known]
 * @version May, 16 2012
 * 		* Remove the private static class to redirect out and err in output 
 * 		windows and
 * 		* use IORedirect to to that.
 */
public class SableCCCaller 
{
	static PrintStream orgOutStream 	= null;
	static PrintStream orgErrStream 	= null;
	
	public static void callSableCC(String filename)
	{
		SableCCHelper h = new SableCCHelper();
		h.setup(filename);
		h.start();
	}
}



class SableCCHelper extends Thread
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
			IORedirect.redirectSystemStreams();
			String msg = "+++++++++++++++++" + filename + "+++++++++++++";
			System.out.println (msg);
			SableCC.processGrammar(filename,null);
			msg = "================= build success  =================" ;
			System.out.println (msg);
		} catch (Exception ex) {
			//Exceptions.printStackTrace(ex);
			System.err.println(ex.getMessage());
		}finally
		{
			IORedirect.setBackOutput();
		}
    }
	
}