package org.sableccsupport.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.sableccsupport.SCCDataObject;
import org.sableccsupport.visual.GrammarVisualizerTopComponent;

@ActionID(category = "Debug",
id = "org.sableccsupport.action.VisualizeGrammar")
@ActionRegistration(iconBase = "org/sableccsupport/img/depGraph.png",
displayName = "#CTL_VisualizeGrammar")
@ActionReferences({
	@ActionReference(path = "Menu/RunProject", position = 433, separatorBefore = 349),
	@ActionReference(path = "Toolbars/Debug", position = 1060),
	@ActionReference(path = "Loaders/text/x-sablecc/Actions", position = 180),
	@ActionReference(path = "Editors/text/x-sablecc/Popup", position = 5200,separatorAfter=5250)
})
@Messages("CTL_VisualizeGrammar=Visualize Grammar")
public final class VisualizeGrammar implements ActionListener 
{
	SCCDataObject context;
	//private final EditorCookie context;
	private GrammarVisualizerTopComponent visualizer;
	public VisualizeGrammar(SCCDataObject context) {
		this.context = context;
	}
		
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		System.out.println("*************************************************");
		// TODO use context (to parse grammar file and co)
		//GrammarVisualizerTopComponent win = GrammarVisualizerTopComponent./*findInstance();  */
		//win.getScene().setASTNode(pm.getAST());  
		//win.open();  
		//win.requestActive();  
		System.out.println(ev.getSource().getClass().getName());
		if (visualizer == null)
		{
			visualizer = new GrammarVisualizerTopComponent();
			visualizer.open();
			visualizer.requestActive();
			System.out.println(visualizer.getName());
		}
		VisualizerCaller.callVisualizer(this.context.getPrimaryFile().getPath(), 
			visualizer);
	}
}
