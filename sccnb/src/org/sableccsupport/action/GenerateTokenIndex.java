/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sableccsupport.action;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.loaders.DataObject;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle.Messages;
import org.sableccsupport.SCCDataObject;

@ActionID(category = "Tools",
id = "org.sableccsupport.action.GenerateTokenIndex")
@ActionRegistration(displayName = "#CTL_GenerateTokenIndex")
@ActionReferences({
	@ActionReference(path = "Menu/Tools", position = 0, separatorBefore = -50, separatorAfter = 50),
	@ActionReference(path = "Loaders/text/x-sablecc/Actions", position = 190),
	@ActionReference(path = "Editors/text/x-sablecc/Popup", position = 5225)
})
@Messages("CTL_GenerateTokenIndex=Generate Token Index")
public final class GenerateTokenIndex implements ActionListener {

	private final SCCDataObject context;

	public GenerateTokenIndex(SCCDataObject context) {
		this.context = context;
	}

	public void actionPerformed(ActionEvent ev) 
	{
		FileObject f = context.getPrimaryFile();
		String displayName = FileUtil.getFileDisplayName(f);
		GeneratorCaller.callGenerator(displayName);
	}
}



