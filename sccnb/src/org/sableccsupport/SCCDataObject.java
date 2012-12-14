package org.sableccsupport;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject.Registration;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.Children;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.text.DataEditorSupport;
import org.openide.util.Lookup;

public class SCCDataObject extends MultiDataObject {

	public SCCDataObject(FileObject pf, MultiFileLoader loader) 
			throws DataObjectExistsException, IOException 
	{
		super(pf, loader);
		CookieSet cookies = getCookieSet();
		cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
		// registerEditor("text/x-sablecc", true);
	}

	@Override
	protected Node createNodeDelegate() {
		return new DataNode(this, Children.LEAF, getLookup());
	}

	@Override
	public Lookup getLookup() {
		return getCookieSet().getLookup();
	}

/*
	@MultiViewElement.Registration(displayName = "#LBL_SCC_EDITOR",
	iconBase = "org/sableccsupport/img/sccicon16.png",
	mimeType = "text/x-sablecc",
	persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
	preferredID = "SCC",
	position = 1000)
	@NbBundle.Messages("LBL_SCC_EDITOR=Source")
	public static MultiViewEditorElement createEditor(Lookup lkp) {
		return new MultiViewEditorElement(lkp);
	}
*/
	 
}
