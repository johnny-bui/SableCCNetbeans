package org.sableccsupport.parser;

import java.io.PushbackReader;
import org.sableccsupport.sccparser.lexer.Lexer;
import org.sableccsupport.sccparser.node.Token;

/**
 *
 * @author phucluoi
 */
public class ExtendLexerToken extends Lexer
{
	public ExtendLexerToken(PushbackReader reader)
	{
		super(reader);
	}
	public Lexer.State getState()
	{
		return this.state;
	}
	
	@Override
	public Token getToken()
	{
		return this.token;
	}

}
