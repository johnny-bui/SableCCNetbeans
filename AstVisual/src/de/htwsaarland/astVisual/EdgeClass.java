
package de.htwsaarland.astVisual;

/**
 *
 * @author phucluoi
 * @version May 6, 2012
 */
public enum EdgeClass {
	T,
	F,
	B,
	C;
	public static String mapToStyle(EdgeClass e)
	{
		switch(e){
			case T: {
				return "color=\"green\"";
			}
			case F: {
				return "color=\"blue\"";
			}
			case B: {
				return "color=\"vilolet\"";
			}
			case C: {
				return "color=\"red\"";
			}
		}
		return "color=\"black\"";
	}
}
