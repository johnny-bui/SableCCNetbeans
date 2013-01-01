/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sableccsupport.parser.ast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phucluoi
 * @version Jan 1, 2013
 */
public class ComposeNode implements SCCNode {
	private final String name;
	private final long offset;
	private final List<SCCNode> children;

	public ComposeNode(String name, long offset) {
		this.name = name;
		this.offset = offset;
		children = new ArrayList<SCCNode>();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public long offset() {
		return offset;
	}

	@Override
	public List<? extends SCCNode> getChildNodes() {
		return children;
	}

	@Override
	public void addChild(SCCNode node) {
		children.add(node);
	}

}
