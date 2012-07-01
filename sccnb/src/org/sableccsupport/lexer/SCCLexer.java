package org.sableccsupport.lexer;

import java.io.IOException;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.sableccsupport.scclexer.lexer.Lexer.State;
import org.sableccsupport.scclexer.lexer.LexerException;

/**
 *
 * @author phucluoi
 */
public class SCCLexer implements org.netbeans.spi.lexer.Lexer<SCCLexerTokenId> {

	private LexerRestartInfo<SCCLexerTokenId> info;
	//private JavaParserTokenManager javaParserTokenManager;
	private  StateInitedLLexer lexer;
	private static ExtendTokenIndex converter = new ExtendTokenIndex();
	public static final int LEXER_BUFFER_SIZE = 1024;	
	
	SCCLexer(LexerRestartInfo<SCCLexerTokenId> info) {
		this.info = info;
		//JavaCharStream stream = new JavaCharStream(info.input());
		//javaParserTokenManager = new JavaParserTokenManager(stream);
		//LexerInput input = info.input();
		Object startStateObj = info.state();
		State startState = State.NORMAL;
		if (startStateObj != null)/* null ~> normal */
		{
			startState = State.PACKAGE;
		}
		lexer = new StateInitedLLexer(
			new NBPushbackReader(
				 info.input() , LEXER_BUFFER_SIZE), startState);
		
	}

	@Override
	public org.netbeans.api.lexer.Token<SCCLexerTokenId> nextToken()
	{
		//Token token = javaParserTokenManager.getNextToken();
		org.sableccsupport.scclexer.node.Token token = null;
		SCCLexerTokenId tokenId = SCCLexerTokenId.ERROR;
		try {
			token = lexer.next();
			if (token == null)
			{
				throw new RuntimeException("lexer.next() returns null");
			}
			if (token.getText().length() ==0 )
			{
				//throw new RuntimeException("lexer.next() returns empty token:>>" +
				// token.getClass().getSimpleName() + "<<");
				return null;
			}
		} catch (LexerException ex) {
			//throw new RuntimeException("lexer.next() gets Lexer Error, token:>>" +
			//		lexer.getText() + "<<" );
			return info.tokenFactory().createToken(
				SCCLanguageHierarchy.getToken(tokenId.id), 
				lexer.getText().length()
			);
		} catch (IOException ex) {
			throw new RuntimeException("lexer.next() gets IOException");
		}
		token.apply(converter);
		tokenId = converter.tokenId;
	
		//if (tokenId == null)
		//return info.tokenFactory().createToken(
		//		SCCLanguageHierarchy.getToken(-1));
		//throw new RuntimeException(token.getClass().getSimpleName() 
		//+ ":"  +token.getText());
		org.netbeans.api.lexer.Token<SCCLexerTokenId> sccToken =
		info.tokenFactory().createToken(
				SCCLanguageHierarchy.getToken(tokenId.id),token.getText().length());
		return sccToken;
	}

	@Override
	public Object state() 
	{
		if (lexer.getState() == State.NORMAL)
		{
			return null;
		}else{
			return State.PACKAGE;
		}
	}

	@Override
	public void release() {
	}
}
