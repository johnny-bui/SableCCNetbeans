package org.sableccsupport.lexer;
import org.sableccsupport.scclexer.node.*;
import org.sableccsupport.scclexer.analysis.AnalysisAdapter;

/**
 *
 * @author phucluoi
 */
public class ExtendTokenIndex extends AnalysisAdapter
{
    public SCCLexerTokenId tokenId = SCCLexerTokenId.ERROR;

    @Override
    public void caseTPkgId(@SuppressWarnings("unused") TPkgId node)
    {
        this.tokenId = SCCLexerTokenId.PKG_ID;
    }

    @Override
    public void caseTPackage(@SuppressWarnings("unused") TPackage node)
    {
        this.tokenId = SCCLexerTokenId.PACKAGE;
    }

    @Override
    public void caseTStates(@SuppressWarnings("unused") TStates node)
    {
        this.tokenId = SCCLexerTokenId.STATES;
    }

    @Override
    public void caseTHelpers(@SuppressWarnings("unused") THelpers node)
    {
        this.tokenId = SCCLexerTokenId.HELPERS;
    }

    @Override
    public void caseTTokens(@SuppressWarnings("unused") TTokens node)
    {
        this.tokenId = SCCLexerTokenId.TOKENS;
    }

    @Override
    public void caseTIgnored(@SuppressWarnings("unused") TIgnored node)
    {
        this.tokenId = SCCLexerTokenId.IGNORED;
    }

    @Override
    public void caseTProductions(@SuppressWarnings("unused") TProductions node)
    {
        this.tokenId = SCCLexerTokenId.PRODUCTIONS;
    }

    @Override
    public void caseTAbstract(@SuppressWarnings("unused") TAbstract node)
    {
        this.tokenId = SCCLexerTokenId.ABSTRACT;
    }

    @Override
    public void caseTSyntax(@SuppressWarnings("unused") TSyntax node)
    {
        this.tokenId = SCCLexerTokenId.SYNTAX;
    }

    @Override
    public void caseTTree(@SuppressWarnings("unused") TTree node)
    {
        this.tokenId = SCCLexerTokenId.TREE;
    }

    @Override
    public void caseTNew(@SuppressWarnings("unused") TNew node)
    {
        this.tokenId = SCCLexerTokenId.NEW;
    }

    @Override
    public void caseTNull(@SuppressWarnings("unused") TNull node)
    {
        this.tokenId = SCCLexerTokenId.NULL;
    }

    @Override
    public void caseTTokenSpecifier(@SuppressWarnings("unused") TTokenSpecifier node)
    {
        this.tokenId = SCCLexerTokenId.TOKEN_SPECIFIER;
    }

    @Override
    public void caseTProductionSpecifier(@SuppressWarnings("unused") TProductionSpecifier node)
    {
        this.tokenId = SCCLexerTokenId.PRODUCTION_SPECIFIER;
    }

    @Override
    public void caseTDot(@SuppressWarnings("unused") TDot node)
    {
        this.tokenId = SCCLexerTokenId.DOT;
    }

    @Override
    public void caseTDDot(@SuppressWarnings("unused") TDDot node)
    {
        this.tokenId = SCCLexerTokenId.DDOT;
    }

    @Override
    public void caseTSemicolon(@SuppressWarnings("unused") TSemicolon node)
    {
        this.tokenId = SCCLexerTokenId.SEMICOLON;
    }

    @Override
    public void caseTEqual(@SuppressWarnings("unused") TEqual node)
    {
        this.tokenId = SCCLexerTokenId.EQUAL;
    }

    @Override
    public void caseTLBkt(@SuppressWarnings("unused") TLBkt node)
    {
        this.tokenId = SCCLexerTokenId.L_BKT;
    }

    @Override
    public void caseTRBkt(@SuppressWarnings("unused") TRBkt node)
    {
        this.tokenId = SCCLexerTokenId.R_BKT;
    }

    @Override
    public void caseTLPar(@SuppressWarnings("unused") TLPar node)
    {
        this.tokenId = SCCLexerTokenId.L_PAR;
    }

    @Override
    public void caseTRPar(@SuppressWarnings("unused") TRPar node)
    {
        this.tokenId = SCCLexerTokenId.R_PAR;
    }

    @Override
    public void caseTLBrace(@SuppressWarnings("unused") TLBrace node)
    {
        this.tokenId = SCCLexerTokenId.L_BRACE;
    }

    @Override
    public void caseTRBrace(@SuppressWarnings("unused") TRBrace node)
    {
        this.tokenId = SCCLexerTokenId.R_BRACE;
    }

    @Override
    public void caseTPlus(@SuppressWarnings("unused") TPlus node)
    {
        this.tokenId = SCCLexerTokenId.PLUS;
    }

    @Override
    public void caseTMinus(@SuppressWarnings("unused") TMinus node)
    {
        this.tokenId = SCCLexerTokenId.MINUS;
    }

    @Override
    public void caseTQMark(@SuppressWarnings("unused") TQMark node)
    {
        this.tokenId = SCCLexerTokenId.Q_MARK;
    }

    @Override
    public void caseTStar(@SuppressWarnings("unused") TStar node)
    {
        this.tokenId = SCCLexerTokenId.STAR;
    }

    @Override
    public void caseTBar(@SuppressWarnings("unused") TBar node)
    {
        this.tokenId = SCCLexerTokenId.BAR;
    }

    @Override
    public void caseTComma(@SuppressWarnings("unused") TComma node)
    {
        this.tokenId = SCCLexerTokenId.COMMA;
    }

    @Override
    public void caseTSlash(@SuppressWarnings("unused") TSlash node)
    {
        this.tokenId = SCCLexerTokenId.SLASH;
    }

    @Override
    public void caseTArrow(@SuppressWarnings("unused") TArrow node)
    {
        this.tokenId = SCCLexerTokenId.ARROW;
    }

    @Override
    public void caseTColon(@SuppressWarnings("unused") TColon node)
    {
        this.tokenId = SCCLexerTokenId.COLON;
    }

    @Override
    public void caseTId(@SuppressWarnings("unused") TId node)
    {
        this.tokenId = SCCLexerTokenId.ID;
    }

    @Override
    public void caseTChar(@SuppressWarnings("unused") TChar node)
    {
        this.tokenId = SCCLexerTokenId.CHAR;
    }

    @Override
    public void caseTDecChar(@SuppressWarnings("unused") TDecChar node)
    {
        this.tokenId = SCCLexerTokenId.DEC_CHAR;
    }

    @Override
    public void caseTHexChar(@SuppressWarnings("unused") THexChar node)
    {
        this.tokenId = SCCLexerTokenId.HEX_CHAR;
    }

    @Override
    public void caseTString(@SuppressWarnings("unused") TString node)
    {
        this.tokenId = SCCLexerTokenId.STRING;
    }

    @Override
    public void caseTBlank(@SuppressWarnings("unused") TBlank node)
    {
        this.tokenId = SCCLexerTokenId.BLANK;
    }

    @Override
    public void caseTComment(@SuppressWarnings("unused") TComment node)
    {
        this.tokenId = SCCLexerTokenId.COMMENT;
    }

    @Override
    public void caseEOF(@SuppressWarnings("unused") EOF node)
    {
        this.tokenId = SCCLexerTokenId.EOF;
    }
}
