/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sableccsupport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Build",
id = "org.sableccsupport.CallSableCCButton")
@ActionRegistration(iconBase = "org/sableccsupport/sableccBuild.png",
displayName = "#CTL_CallSableCCButton")
@ActionReferences({
	@ActionReference(path = "Menu/BuildProject", position = 247)
})
@Messages("CTL_CallSableCCButton=Call SableCC")
public final class CallSableCCButton implements ActionListener {

	private final SCCDataObject context;

	public CallSableCCButton(SCCDataObject context) {
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		FileObject f = context.getPrimaryFile();
		String displayName = FileUtil.getFileDisplayName(f);
		SableCCCaller.callSableCC(displayName);
	}
}
