
package de.htwsaarland.mathcoach.astVisual;

/**
 *
 * @author phucluoi
 * @version May 3, 2012
 */
public class NameDistincVertex extends DummyVertex
{
	public NameDistincVertex(String name)
	{
		super(name);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (this == other){return true;}
    	if (!(other instanceof NameDistincVertex)){return false;}
	    NameDistincVertex otherA = (NameDistincVertex) other;
    	return (name.equals(otherA.name));
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = hash * 31 + name.hashCode();
		return hash;
	}

	@Override
	public String toString()
	{
		return this.name + ":" + this.d + ":" + this.f;
	}
}
