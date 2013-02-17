package org.sableccsupport.action;

import java.io.File;
import java.io.PrintStream;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.sablecc.sablecc.SableCC;
import org.sablecc.sablecc.Version;
import org.sableccsupport.SableCCPanel;

/**
 *
 * @author verylazyboy
 * @version [too old to known]
 * @version May, 16 2012 * Remove the private static class to redirect out and
 * err in output windows and * use IORedirect to to that.
 */
public class SableCCCaller {

	static PrintStream orgOutStream = null;
	static PrintStream orgErrStream = null;

	public static void callSableCC(FileObject f/*SableCCArgument arg*/) {
		String displayName = FileUtil.getFileDisplayName(f);
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
			
			String msg = "+++++++++++++++++" + filename + "+++++++++++++";
			System.out.println(msg);
			System.out.println();
			System.out.println("SableCC version " + Version.VERSION);
			System.out.println("Copyright (C) 1997-2012 Etienne M. Gagnon <egagnon@j-meg.com> and");
			System.out.println("others.  All rights reserved.");
			System.out.println();
			System.out.println("This software comes with ABSOLUTELY NO WARRANTY.  This is free software,");
			System.out.println("and you are welcome to redistribute it under certain conditions.");
			System.out.println();
			//}catch(Exception ex){}// Nothing to do
			String destination = arg.getDestinationDir();
			File destianationDir = new File(destination);
			if (!destianationDir.exists()){
				destianationDir.mkdirs();
			}
			SableCC.processGrammar(filename, arg.getDestinationDir());
			msg = "BUILD SUCCESS";

			System.out.println(msg);
			StatusDisplayer.getDefault().setStatusText(msg);
		} catch (Exception ex) {
			//Exceptions.printStackTrace(ex);
			System.err.println(ex.getMessage());
			StatusDisplayer.getDefault().setStatusText("BUILD FAIL");
		} finally {
			IORedirect.setBackOutput();
			p.finish();
		}
	}
}