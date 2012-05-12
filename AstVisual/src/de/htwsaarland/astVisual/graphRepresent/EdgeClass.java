
package de.htwsaarland.astVisual.graphRepresent;

import java.awt.Color;

/**
 *
 * @author phucluoi
 * @version May 6, 2012
 */
public enum EdgeClass {
	T, // tree
	F, // forward
	B, // backward
	C, // cross
	N; // neutral
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

	public static Color mapToColor(EdgeClass e)
	{
		switch(e){
			case T: {
				return Color.decode("0x006600");// dark green
			}
			case F: {
				return Color.decode("0x3300aa");// blue
			}
			case B: {
				return Color.decode("0x9900DD");// violet
			}
			case C: {
				return Color.decode("0xFF0044");// red
			}
			case N:{
				return Color.BLACK;
			}
		}
		return Color.decode("0x000000");// black
	}
}
