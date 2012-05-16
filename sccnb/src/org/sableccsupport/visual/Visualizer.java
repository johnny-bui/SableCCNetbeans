package org.sableccsupport.visual;

import javax.swing.JComponent;

/**
 *
 * @author verylazyboy
 */
public interface Visualizer {
	public void updateStatus(String status);
	//public void appendNewGraph(JComponent graph);
	public void replaceNewGraph(JComponent graph);
}
