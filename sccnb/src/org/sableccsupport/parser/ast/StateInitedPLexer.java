package org.sableccsupport.parser.ast;

import java.io.PushbackReader;
import org.sableccsupport.sccparser.lexer.IPushbackReader;
import org.sableccsupport.sccparser.lexer.Lexer;


/**
 *
 * @author phucluoi
 */
public class StateInitedPLexer extends Lexer
{
	public StateInitedPLexer(PushbackReader reader, Lexer.State state){
		super(reader);
		this.state = state;
		//System.out.println("<<<<<<<<<<<<<<<<"+ " Parser is used " + ">>>>>>>>>>>>>>>>>>>" );
	}
	public StateInitedPLexer(IPushbackReader reader, Lexer.State state)
	{
		super(reader);
		this.state = state;
	}
	public Lexer.State getState()
	{
		return this.state;
	}
}
