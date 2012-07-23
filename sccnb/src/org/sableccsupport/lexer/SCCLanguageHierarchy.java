package org.sableccsupport.lexer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author phucluoi
 */
public class SCCLanguageHierarchy extends LanguageHierarchy<SCCLexerTokenId> 
{
	private static final List<SCCLexerTokenId> tokens = new ArrayList<SCCLexerTokenId>();
	private static final Map<Integer, SCCLexerTokenId> idToToken = new HashMap<Integer, SCCLexerTokenId>();

	static {
		SCCLexerTokenId[] tokenTypes = SCCLexerTokenId.values();
		for (SCCLexerTokenId tokenType : tokenTypes) {
			tokens.add(
				tokenType
			);
		}
		for (SCCLexerTokenId token : tokens) {
			idToToken.put(token.id, token);
		}
	}

	public static synchronized SCCLexerTokenId getToken(int id) {
		return idToToken.get(id);
	}


	@Override
	protected synchronized Collection<SCCLexerTokenId> createTokenIds() {
		return tokens;
	}

	@Override
	protected synchronized Lexer<SCCLexerTokenId> createLexer(LexerRestartInfo<SCCLexerTokenId> info) {
		return new SCCLexer(info);
	}

	@Override
	protected String mimeType() {
		return "text/x-sablecc";
	}
}
