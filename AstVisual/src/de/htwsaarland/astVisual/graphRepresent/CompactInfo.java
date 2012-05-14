/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htwsaarland.astVisual.graphRepresent;

import com.sun.jndi.toolkit.dir.SearchFilter;
import java.io.Serializable;

/**
 *
 * @author hbui
 */
public class CompactInfo 
	implements VertexInfo , Serializable, Cloneable
{

	int d = 0;
	int f = 0;
	private final String name;
	private String pred;
	private VertexType t;
	
	public CompactInfo(String name) {
		this.name = name;
		t = VertexType.PROD;
		int d = 0;
		int f = 0;
	}

	public CompactInfo(String name, VertexType type) 
	{
		this.name = name;
		t = type;
		int d = 0;
		int f = 0;
	}
	
	@Override
	public void setDetected(int d) {
		this.d = d;
	}

	@Override
	public int getDetected() {
		return d;
	}

	@Override
	public void setFinished(int f) {
		this.f = f;
	}

	@Override
	public int getFinished() {
		return f;
	}

	@Override
	public void setPredName(String v) {
		this.pred = v;
	}

	@Override
	public String getPredName() {
		return this.pred;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setType(VertexType t) {
		this.t = t;
	}
	
	@Override
	public VertexType getType() {
		return t;
	}

	@Override
	public String toString() {
		return name + ":" + d + ":" + f;
	}

	
}
