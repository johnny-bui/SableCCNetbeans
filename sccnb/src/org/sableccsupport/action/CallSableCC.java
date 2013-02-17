package org.sableccsupport.action;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;
import org.sableccsupport.SCCDataObject;
import org.sableccsupport.SableCCPanel;

/**
 * @author verylazyboy
 * @version [too old to know]
 */
@ActionID(category = "Build",
id = "org.sableccsupport.action.CallSableCC")
@ActionRegistration(iconBase = "org/sableccsupport/img/sableccBuild.png",
displayName = "#CTL_CallSableCC")
@ActionReferences({
	@ActionReference(path = "Menu/BuildProject", position = 247),
	@ActionReference(path = "Loaders/text/x-sablecc/Actions", position = 150),
	@ActionReference(path = "Editors/text/x-sablecc/Popup", position = 5000, separatorBefore=4900)
})
@Messages("CTL_CallSableCC=Call SableCC")
public final class CallSableCC implements ActionListener 
{

	SCCDataObject context;

	public CallSableCC(SCCDataObject context) {
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent ev) 
	{
		// determinate the file name 
		FileObject f = context.getPrimaryFile();
		SableCCCaller.callSableCC(f);
	}
}
