package org.sableccsupport.parser;

/**
 * Class to map token to an integer index.
 * 
 * @author phucluoi
 * @version Jul 1, 2012
 */
public enum ParserTokenId
{
	PKG_ID (0,"PkgId"),
	PACKAGE (1,"Package"),
	STATES (2,"States"),
	HELPERS (3,"Helpers"),
	TOKENS (4,"Tokens"),
	IGNORED (5,"Ignored"),
	PRODUCTIONS (6,"Productions"),
	ABSTRACT (7,"Abstract"),
	SYNTAX (8,"Syntax"),
	TREE (9,"Tree"),
	NEW (10,"New"),
	NULL (11,"Null"),
	TOKEN_SPECIFIER (12,"TokenSpecifier"),
	PRODUCTION_SPECIFIER (13,"ProductionSpecifier"),
	DOT (14,"Dot"),
	D_DOT (15,"DDot"),
	SEMICOLON (16,"Semicolon"),
	EQUAL (17,"Equal"),
	L_BKT (18,"LBkt"),
	R_BKT (19,"RBkt"),
	L_PAR (20,"LPar"),
	R_PAR (21,"RPar"),
	L_BRACE (22,"LBrace"),
	R_BRACE (23,"RBrace"),
	PLUS (24,"Plus"),
	MINUS (25,"Minus"),
	Q_MARK (26,"QMark"),
	STAR (27,"Star"),
	BAR (28,"Bar"),
	COMMA (29,"Comma"),
	SLASH (30,"Slash"),
	ARROW (31,"Arrow"),
	COLON (32,"Colon"),
	ID (33,"Id"),
	CHAR (34,"Char"),
	DEC_CHAR (35,"DecChar"),
	HEX_CHAR (36,"HexChar"),
	STRING (37,"String"),
	BLANK (38,"Blank"),
	COMMENT (39,"Comment"),
	EOF(40,"EOF"),
	_ERROR_(41,"_ERROR_"),
	;
	private String tokenName;
	private int idx;

	private ParserTokenId(int idx, String tokenName)
	{
		this.tokenName = tokenName ;
		this.idx = idx;
	}

	public int idx() {return idx;}
	public String tokenName(){return tokenName;}
}
