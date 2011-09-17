package org.sableccsupport.navigation;

import java.util.Collection;
import javax.swing.JComponent;
import javax.swing.JLabel;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.sableccsupport.SCCDataObject;

/**
 *
 * @author phucluoi
 */
public class SCCNavi implements NavigatorPanel 
{

    /** holds UI of this panel */
    private JComponent panelUI;
    /** template for finding data in given context.
     * Object used as example, replace with your own data source, for example JavaDataObject etc */
    private static final Lookup.Template SABLECC_DATA = new Lookup.Template(SCCDataObject.class);
    /** current context to work on */
    private Lookup.Result curContext;
    /** listener to context changes */
    private LookupListener contextL;
    
    /** public no arg constructor needed for system to instantiate provider well */
    public SCCNavi() {
    }

	@Override
    public String getDisplayHint() {
        return "Basic dummy implementation of NavigatorPanel interface";
    }

	@Override
    public String getDisplayName() {
        return "Dummy View";
    }

	@Override
    public JComponent getComponent() {
        if (panelUI == null) {
            panelUI = new JLabel("Dummy label");
            // You can override requestFocusInWindow() on the component if desired.
        }
        return panelUI;
    }

	@Override
    public void panelActivated(Lookup context) {
        // lookup context and listen to result to get notified about context changes
        curContext = context.lookup(SABLECC_DATA);
        curContext.addLookupListener(getContextListener());
        // get actual data and recompute content
        Collection data = curContext.allInstances();
        setNewContent(data);
    }

	@Override
    public void panelDeactivated() {
        curContext.removeLookupListener(getContextListener());
        curContext = null;
    }
    
	@Override
    public Lookup getLookup () {
        // go with default activated Node strategy
        return null;
    }
    
    /************* non - public part ************/
    
    private void setNewContent (Collection newData) {
        // put your code here that grabs information you need from given
        // collection of data, recompute UI of your panel and show it.
        // Note - be sure to compute the content OUTSIDE event dispatch thread,
        // just final repainting of UI should be done in event dispatch thread.
        // Please use RequestProcessor and Swing.invokeLater to achieve this.
    }
    
    /** Accessor for listener to context */
    private LookupListener getContextListener () {
        if (contextL == null) {
            contextL = new ContextListener();
        }
        return contextL;
    }
    
    /** Listens to changes of context and triggers proper action */
    private class ContextListener implements LookupListener {
        
		@Override
        public void resultChanged(LookupEvent ev) {
            Collection data = ((Lookup.Result)ev.getSource()).allInstances();
            setNewContent(data);
        }
        
    } // end of ContextListener
}	
