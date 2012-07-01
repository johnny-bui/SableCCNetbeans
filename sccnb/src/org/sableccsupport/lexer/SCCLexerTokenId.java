/*
 * NOTE: diese klasse ist OK
 */

package org.sableccsupport.lexer;

/**
 *
 * @author phucluoi
 */
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

public enum SCCLexerTokenId implements TokenId
{
	
	PKG_ID(0,"PkgId"),
	PACKAGE(1,"Package"),
	STATES(2,"States"),
	HELPERS(3,"Helpers"),
	TOKENS(4,"Tokens"),
	IGNORED(5,"Ignored"),
	PRODUCTIONS(6,"Productions"),
	ABSTRACT(7,"Abstract"),
	SYNTAX(8,"Syntax"),
	TREE(9,"Tree"),
	NEW(10,"New"),
	NULL(11,"Null"),
	TOKEN_SPECIFIER(12,"TokenSpecifier"),
	PRODUCTION_SPECIFIER(13,"ProductionSpecifier"),
	DOT(14,"Dot"),
	DDOT(15,"DDot"),
	SEMICOLON(16,"Semicolon"),
	EQUAL(17,"Equal"),
	L_BKT(18,"LBkt"),
	R_BKT(19,"RBkt"),
	L_PAR(20,"LPar"),
	R_PAR(21,"RPar"),
	L_BRACE(22,"LBrace"),
	R_BRACE(23,"RBrace"),
	PLUS(24,"Plus"),
	MINUS(25,"Minus"),
	Q_MARK(26,"QMark"),
	STAR(27,"Star"),
	BAR(28,"Bar"),
	COMMA(29,"Comma"),
	SLASH(30,"Slash"),
	ARROW(31,"Arrow"),
	COLON(32,"Colon"),
	ID(33,"Id"),
	CHAR(34,"Char"),
	DEC_CHAR(35,"DecChar"),
	HEX_CHAR(36,"HexChar"),
	STRING(37,"String"),
	BLANK(38,"Blank"),
	COMMENT(39,"Comment"),
	EOF(40,"EOF"),
	ERROR(-1,"ERROR"),
	;

	public int id;
    public String primaryCategory;

	private static final Language<SCCLexerTokenId> language = new SCCLanguageHierarchy().language();

    public static Language<SCCLexerTokenId> getLanguage() {
        return language;
    }
	
    SCCLexerTokenId(int id, String primaryCategory)
    {
        this.primaryCategory = primaryCategory;
		this.id = id;
    }
	

    @Override
    public String primaryCategory() {
        return primaryCategory;
    }


}

