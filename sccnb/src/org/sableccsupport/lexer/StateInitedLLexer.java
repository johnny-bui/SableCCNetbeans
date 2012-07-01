
package org.sableccsupport.lexer;

import java.io.PushbackReader;
import org.sableccsupport.scclexer.lexer.Lexer;

/**
 *
 * @author phucluoi
 */
public class StateInitedLLexer extends Lexer
{
	public StateInitedLLexer(PushbackReader reader, State state)
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
