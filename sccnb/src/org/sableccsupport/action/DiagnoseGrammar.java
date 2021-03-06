package org.sableccsupport.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle.Messages;
import org.sableccsupport.SCCDataObject;

/**
 * @author verylazyboy
 * @version May, 10 2012
 * 		initial diagnose feature
 */


@ActionID(category = "Debug",
id = "org.sableccsupport.action.DiagnoseGrammar")
@ActionRegistration(iconBase = "org/sableccsupport/img/diagnose.png",
displayName = "#CTL_DiagnoseGrammar")
@ActionReferences({
	@ActionReference(path = "Menu/RunProject", position = 430,  separatorAfter = 516),
	@ActionReference(path = "Toolbars/Debug", position = 1050),
	@ActionReference(path = "Loaders/text/x-sablecc/Actions", position = 175),
	@ActionReference(path = "Editors/text/x-sablecc/Popup", position = 5200,separatorAfter=5250)
})
@Messages("CTL_DiagnoseGrammar=Diagnose Grammar")
public final class DiagnoseGrammar implements ActionListener {

	SCCDataObject context;

	public DiagnoseGrammar (SCCDataObject context) {
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent ev) 
	{
		FileObject f = context.getPrimaryFile();
		String displayName = FileUtil.getFileDisplayName(f);
		DiagnoserCaller.callAnalyzer(displayName);
	}
}
