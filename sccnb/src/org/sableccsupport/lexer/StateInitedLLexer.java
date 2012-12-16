
package org.sableccsupport.lexer;

import org.sableccsupport.scclexer.lexer.IPushbackReader;
import org.sableccsupport.scclexer.lexer.Lexer;

/**
 *
 * @author phucluoi
 */
public class StateInitedLLexer extends Lexer
{
	public StateInitedLLexer(IPushbackReader reader, State state)
	{
		super(reader);
		this.state = state;
		//System.out.println(">>>>>>>>>>>>>>>>>>>" + " Lexer is used " + "<<<<<<<<<<<<<<<<");
	}
	public Lexer.State getState()
	{
		return this.state;
	}
}
