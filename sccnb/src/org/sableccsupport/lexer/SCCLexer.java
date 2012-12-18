package org.sableccsupport.lexer;

import java.io.IOException;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.sableccsupport.scclexer.lexer.Lexer.State;
import org.sableccsupport.scclexer.lexer.LexerException;
import org.sableccsupport.scclexer.node.TBlank;

/**
 *
 * @author phucluoi
 */
public class SCCLexer implements org.netbeans.spi.lexer.Lexer<SCCLexerTokenId> {

	private LexerRestartInfo<SCCLexerTokenId> info;
	//private JavaParserTokenManager javaParserTokenManager;
	private StateInitedLLexer lexer;
	private static ExtendTokenIndex converter = new ExtendTokenIndex();
	public static final int LEXER_BUFFER_SIZE = 1024;

	SCCLexer(LexerRestartInfo<SCCLexerTokenId> info) {
		this.info = info;
		Object startStateObj = info.state();
		State startState = State.NORMAL;
		if (startStateObj != null)/* null ~> normal */ {
			startState = State.PACKAGE;
		}
		lexer = new StateInitedLLexer(
				new NBPushbackReader(
				info.input(), LEXER_BUFFER_SIZE), startState);

	}

	@Override
	public org.netbeans.api.lexer.Token<SCCLexerTokenId> nextToken() {
		org.sableccsupport.scclexer.node.Token token = null;
		org.sableccsupport.scclexer.node.Token lastToken = null;
		SCCLexerTokenId tokenId = SCCLexerTokenId.ERROR;
		try {
			lastToken = token;
			token = lexer.next();
			if (token == null) {
				return null;
			}
			
			if (token.getText().length() == 0) {
				
				int readLength = info.input().readLength();
				if(readLength > 0){
					CharSequence readText = info.input().readText();
					String emptyToken = info.input().readText().toString();
					token = new TBlank(emptyToken, 
							token.getLine(), token.getPos()+emptyToken.length());
				}else{
					return null;
				}
				//return null;
			}
			token.apply(converter);
			tokenId = converter.tokenId;
			org.netbeans.api.lexer.Token<SCCLexerTokenId> sccToken =
					info.tokenFactory().createToken(
					SCCLanguageHierarchy.getToken(tokenId.id), token.getText().length());
			return sccToken;
		} catch (LexerException ex) {
			return info.tokenFactory().createToken(
					SCCLanguageHierarchy.getToken(tokenId.id),
					ex.getToken().getText().length() 
					);
		} catch (IOException ex) {
			throw new RuntimeException("lexer.next() gets IOException");
		} catch (java.lang.IllegalStateException exp){
			throw new RuntimeException("lexer fault!!!", exp);
		}

	}

	@Override
	public Object state() {
		if (lexer.getState() == State.NORMAL) {
			return null;
		} else {
			return State.PACKAGE;
		}
	}

	@Override
	public void release() {
	}
}
