package org.sableccsupport.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author phucluoi
 * @version Dec 31, 2012
 */
public class LeafNode implements SCCNode{
	private String name;
	private long offset;

	LeafNode(String name, long offset) {
		this.name = name;
		this.offset = offset;
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
		return Collections.EMPTY_LIST;
	}

	@Override
	public void addChild(SCCNode node) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
