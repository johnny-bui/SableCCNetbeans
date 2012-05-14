package org.sableccsupport.visual;

import javax.swing.JComponent;

/**
 *
 * @author hbui
 */
public interface Visualizer {
	public void updateStatus(String status);
	//public void appendNewGraph(JComponent graph);
	public void replaceNewGraph(JComponent graph);
}
