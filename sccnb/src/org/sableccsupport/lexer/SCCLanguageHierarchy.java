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
public class SCCLanguageHierarchy extends LanguageHierarchy<SCCTokenId> 
{
	private static final List<SCCTokenId> tokens = new ArrayList<SCCTokenId>();
	private static final Map<Integer, SCCTokenId> idToToken = new HashMap<Integer, SCCTokenId>();

	static {
		SCCTokenId[] tokenTypes = SCCTokenId.values();
		for (SCCTokenId tokenType : tokenTypes) {
			tokens.add(
				tokenType
			);
		}
		for (SCCTokenId token : tokens) {
			idToToken.put(token.id, token);
		}
	}

	public static synchronized SCCTokenId getToken(int id) {
		return idToToken.get(id);
	}


	@Override
	protected synchronized Collection<SCCTokenId> createTokenIds() {
		return tokens;
	}

	@Override
	protected synchronized Lexer<SCCTokenId> createLexer(LexerRestartInfo<SCCTokenId> info) {
		return new SCCLexer(info);
	}

	@Override
	protected String mimeType() {
		return "text/x-sablecc";
	}
}
