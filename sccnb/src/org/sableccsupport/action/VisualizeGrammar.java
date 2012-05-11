/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sableccsupport.action;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.cookies.EditorCookie;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Debug",
id = "org.sableccsupport.action.VisualizeGrammar")
@ActionRegistration(iconBase = "org/sableccsupport/img/depGraph.png",
displayName = "#CTL_VisualizeGrammar")
@ActionReferences({
	@ActionReference(path = "Menu/RunProject", position = 433, separatorBefore = 349),
	@ActionReference(path = "Toolbars/Debug", position = 1060),
	@ActionReference(path = "Loaders/text/x-sablecc/Actions", position = 0),
	@ActionReference(path = "Editors/text/x-sablecc/Popup", position = 5200,separatorAfter=5250)
})
@Messages("CTL_VisualizeGrammar=Visualize Grammar")
public final class VisualizeGrammar implements ActionListener 
{

	private final EditorCookie context;

	public VisualizeGrammar(EditorCookie context) {
		this.context = context;
	}

	public void actionPerformed(ActionEvent ev) {
		// TODO use context
	}
}
