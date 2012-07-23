package org.sableccsupport.parser;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.sableccsupport.lexer.SCCLexerTokenId;

/**
 *
 * @author phucluoi
 * @version Jul 1, 2012
 */
@LanguageRegistration(mimeType = "text/x-sablecc")
public class SCCLanguage extends DefaultLanguageConfig
{

	@Override
	public Language getLexerLanguage() {
		return SCCLexerTokenId.getLanguage();
	}

	@Override
	public String getDisplayName() {
		return "SableCC";
	}

}
