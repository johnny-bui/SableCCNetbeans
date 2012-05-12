package de.htwsaarland.astVisual.graphRepresent;

import java.awt.Color;

/**
 *
 * @author hbui
 * @version May 12. 2012
 */

public enum VertexType {
	PROD,
	TOKEN;

	public static Color mapToColor(VertexType t)
	{
		switch(t){
			case PROD:
			{
				return Color.decode("0x00CCEE");// 		
			}
			case TOKEN:
			{
				return Color.decode("0xFFFF22");
			}
		}
		return Color.CYAN;
	}
}
