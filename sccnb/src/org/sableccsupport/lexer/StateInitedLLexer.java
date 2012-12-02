
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
	}
	public Lexer.State getState()
	{
		return this.state;
	}
	public String getText()
	{
		return this.text.toString();
	}

}
