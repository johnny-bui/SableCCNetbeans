package de.htwsaarland.astVisual;

/**
 *
 * @author phucluoi
 * @version May 3, 2012
 */
public class NameDistinctVertex extends DefaultAstVertex
{
	public NameDistinctVertex(String name)
	{
		super(name);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (this == other){return true;}
    	if (!(other instanceof NameDistinctVertex)){return false;}
	    NameDistinctVertex otherA = (NameDistinctVertex) other;
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
