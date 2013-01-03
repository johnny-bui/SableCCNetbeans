
package org.sableccsupport.parser.ast;

import java.util.List;

/**
 * presents the abstract structure node
 * @author phucluoi
 * @version Dec 17, 2012
 */
public interface SCCNode {
	String name();
	long offset();
	List<? extends SCCNode> getChildNodes();
	void addChild(SCCNode node);
}
