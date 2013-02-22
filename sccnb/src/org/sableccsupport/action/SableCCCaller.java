package org.sableccsupport.action;

import java.io.File;
import java.io.PrintStream;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.sablecc.sablecc.SableCC;
import org.sablecc.sablecc.Version;

/**
 *
 * @author verylazyboy
 * @version [too old to known]
 * @version May, 16 2012 * Remove the private static class to redirect out and
 * err in output window[s] and * use IORedirect to to that.
 */
final public class SableCCCaller {

	static PrintStream orgOutStream = null;
	static PrintStream orgErrStream = null;

	public static void callSableCC(FileObject f) {
		SableCCHelper h = new SableCCHelper();
		h.setup(f);
		h.start();
	}
}

final class SableCCHelper extends Thread {

	private FileObject file;
	static PrintStream orgOutStream = null;
	static PrintStream orgErrStream = null;
	private ProgressHandle p;

	public void setup(FileObject f) {
		file = f;
	}

	@Override
	public void run() {
		try {
			IORedirect.redirectSystemStreams();
			p = ProgressHandleFactory.createHandle("SableCC");
			p.start();		
			
			SableCCArgument arg = new SableCCArgument();
			arg.setSableCCFile(file);
			String filename = arg.getSableCCFileName();
			
			String msg = "================= The Grammar File is: " + filename + " =============";
			String destination = arg.getDestinationDir();
			File destianationDir = new File(destination);
			if (!destianationDir.exists()){
				destianationDir.mkdirs();
			}
			System.out.println(msg);
			System.out.println("Generated files are saved in: " + destination);
			System.out.println();
			// copy from SableCC source
			System.out.println("SableCC version " + Version.VERSION);
			System.out.println("Copyright (C) 1997-2012 Etienne M. Gagnon <egagnon@j-meg.com> and");
			System.out.println("others.  All rights reserved.");
			System.out.println();
			System.out.println("This software comes with ABSOLUTELY NO WARRANTY.  This is free software,");
			System.out.println("and you are welcome to redistribute it under certain conditions.");
			System.out.println();
			// end of copy
			
			SableCC.processGrammar(filename, destination);
			msg = "BUILD SUCCESS";

			System.out.println(msg);
			StatusDisplayer.getDefault().setStatusText(msg);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			StatusDisplayer.getDefault().setStatusText("BUILD FAIL");
		} finally {
			IORedirect.setBackOutput();
			p.finish();
		}
	}
}