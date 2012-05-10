/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

@ActionID(category = "Debug",
id = "org.sableccsupport.action.ConstructDepGraph")
@ActionRegistration(iconBase = "org/sableccsupport/img/depGraph.png",
displayName = "#CTL_ConstructDepGraph")
@ActionReferences({
	@ActionReference(path = "Menu/RunProject", position = 433, separatorBefore = 349, separatorAfter = 516),
	@ActionReference(path = "Toolbars/Debug", position = 1050),
	@ActionReference(path = "Loaders/text/x-sablecc/Actions", position = 175)
})
@Messages("CTL_ConstructDepGraph=Construct Dependent Graph")
public final class ConstructDepGraph implements ActionListener {

	SCCDataObject context;

	public ConstructDepGraph (SCCDataObject context) {
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent ev) 
	{
		FileObject f = context.getPrimaryFile();
		String displayName = FileUtil.getFileDisplayName(f);
		GrammarAnalyzerCaller.callAnalyzer(displayName);
	}
}
