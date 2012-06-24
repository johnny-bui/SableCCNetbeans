package org.sableccsupport.visual.render;

import de.htwsaarland.astVisual.graphRepresent.GraphContainer;
import javax.swing.JComponent;

/**
 *
 * @author phucluoi
 * @verison May 30, 2012
 */

/** plays the roll like view. It renders just a GraphicContainer, doesn't 
 * matter which construct has the GraphicContainer.
 */
public interface Render 
{
	JComponent renderGraph(GraphContainer gc);
}
