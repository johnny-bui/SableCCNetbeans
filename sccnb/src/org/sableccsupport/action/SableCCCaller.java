package org.sableccsupport.action;

import java.io.PrintStream;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.awt.StatusDisplayer;
import org.sablecc.sablecc.SableCC;
import org.sablecc.sablecc.Version;

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



final class SableCCHelper extends Thread 
{
	private String filename;
	static PrintStream orgOutStream 	= null;
	static PrintStream orgErrStream 	= null;
	private ProgressHandle p;
	public void setup(String filename)
	{
		this.filename = filename;
		p = ProgressHandleFactory.createHandle("sablecc "+filename);
	}

	@Override
	public void run() {
		try {
			p.start();
			IORedirect.redirectSystemStreams();
			String msg = "+++++++++++++++++" + filename + "+++++++++++++";
			System.out.println (msg);
			//String arg[] = {};
			//try{// print version and copyright of SableCC
			System.out.println();
			System.out.println("SableCC version " + Version.VERSION);
			System.out.println("Copyright (C) 1997-2012 Etienne M. Gagnon <egagnon@j-meg.com> and");
			System.out.println("others.  All rights reserved.");
			System.out.println();
			System.out.println("This software comes with ABSOLUTELY NO WARRANTY.  This is free software,");
			System.out.println("and you are welcome to redistribute it under certain conditions.");
			System.out.println();
			//}catch(Exception ex){}// Nothing to do
			SableCC.processGrammar(filename,null);
			msg = "BUILD SUCCESS";
			
			System.out.println (msg);
			StatusDisplayer.getDefault().setStatusText(msg);
		} catch (Exception ex) {
			//Exceptions.printStackTrace(ex);
			System.err.println(ex.getMessage());
			StatusDisplayer.getDefault().setStatusText("BUILD FAIL");
		}finally
		{
			IORedirect.setBackOutput();
			p.finish();
		}
    }
	
}