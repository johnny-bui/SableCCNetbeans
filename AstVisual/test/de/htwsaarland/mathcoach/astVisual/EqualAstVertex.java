
package de.htwsaarland.mathcoach.astVisual;

/**
 *
 * @author phucluoi
 * @version May 3, 2012
 */
public class EqualAstVertex extends DummyVertex
{
	public EqualAstVertex(String name)
	{
		super(name);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (this == other){return true;}
    	if (!(other instanceof EqualAstVertex)){return false;}
	    EqualAstVertex otherA = (EqualAstVertex) other;
    	return (name.equals(otherA.name));
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = hash * 31 + name.hashCode();
		return hash;
	}
}
