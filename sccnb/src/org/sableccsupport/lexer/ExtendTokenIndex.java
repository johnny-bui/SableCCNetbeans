package org.sableccsupport.lexer;
import org.sableccsupport.scclexer.node.*;
import org.sableccsupport.scclexer.analysis.AnalysisAdapter;

/**
 *
 * @author phucluoi
 */
public class ExtendTokenIndex extends AnalysisAdapter
{
    public SCCTokenId tokenId = SCCTokenId.ERROR;

    @Override
    public void caseTPkgId(@SuppressWarnings("unused") TPkgId node)
    {
        this.tokenId = SCCTokenId.PKG_ID;
    }

    @Override
    public void caseTPackage(@SuppressWarnings("unused") TPackage node)
    {
        this.tokenId = SCCTokenId.PACKAGE;
    }

    @Override
    public void caseTStates(@SuppressWarnings("unused") TStates node)
    {
        this.tokenId = SCCTokenId.STATES;
    }

    @Override
    public void caseTHelpers(@SuppressWarnings("unused") THelpers node)
    {
        this.tokenId = SCCTokenId.HELPERS;
    }

    @Override
    public void caseTTokens(@SuppressWarnings("unused") TTokens node)
    {
        this.tokenId = SCCTokenId.TOKENS;
    }

    @Override
    public void caseTIgnored(@SuppressWarnings("unused") TIgnored node)
    {
        this.tokenId = SCCTokenId.IGNORED;
    }

    @Override
    public void caseTProductions(@SuppressWarnings("unused") TProductions node)
    {
        this.tokenId = SCCTokenId.PRODUCTIONS;
    }

    @Override
    public void caseTAbstract(@SuppressWarnings("unused") TAbstract node)
    {
        this.tokenId = SCCTokenId.ABSTRACT;
    }

    @Override
    public void caseTSyntax(@SuppressWarnings("unused") TSyntax node)
    {
        this.tokenId = SCCTokenId.SYNTAX;
    }

    @Override
    public void caseTTree(@SuppressWarnings("unused") TTree node)
    {
        this.tokenId = SCCTokenId.TREE;
    }

    @Override
    public void caseTNew(@SuppressWarnings("unused") TNew node)
    {
        this.tokenId = SCCTokenId.NEW;
    }

    @Override
    public void caseTNull(@SuppressWarnings("unused") TNull node)
    {
        this.tokenId = SCCTokenId.NULL;
    }

    @Override
    public void caseTTokenSpecifier(@SuppressWarnings("unused") TTokenSpecifier node)
    {
        this.tokenId = SCCTokenId.TOKEN_SPECIFIER;
    }

    @Override
    public void caseTProductionSpecifier(@SuppressWarnings("unused") TProductionSpecifier node)
    {
        this.tokenId = SCCTokenId.PRODUCTION_SPECIFIER;
    }

    @Override
    public void caseTDot(@SuppressWarnings("unused") TDot node)
    {
        this.tokenId = SCCTokenId.DOT;
    }

    @Override
    public void caseTDDot(@SuppressWarnings("unused") TDDot node)
    {
        this.tokenId = SCCTokenId.DDOT;
    }

    @Override
    public void caseTSemicolon(@SuppressWarnings("unused") TSemicolon node)
    {
        this.tokenId = SCCTokenId.SEMICOLON;
    }

    @Override
    public void caseTEqual(@SuppressWarnings("unused") TEqual node)
    {
        this.tokenId = SCCTokenId.EQUAL;
    }

    @Override
    public void caseTLBkt(@SuppressWarnings("unused") TLBkt node)
    {
        this.tokenId = SCCTokenId.L_BKT;
    }

    @Override
    public void caseTRBkt(@SuppressWarnings("unused") TRBkt node)
    {
        this.tokenId = SCCTokenId.R_BKT;
    }

    @Override
    public void caseTLPar(@SuppressWarnings("unused") TLPar node)
    {
        this.tokenId = SCCTokenId.L_PAR;
    }

    @Override
    public void caseTRPar(@SuppressWarnings("unused") TRPar node)
    {
        this.tokenId = SCCTokenId.R_PAR;
    }

    @Override
    public void caseTLBrace(@SuppressWarnings("unused") TLBrace node)
    {
        this.tokenId = SCCTokenId.L_BRACE;
    }

    @Override
    public void caseTRBrace(@SuppressWarnings("unused") TRBrace node)
    {
        this.tokenId = SCCTokenId.R_BRACE;
    }

    @Override
    public void caseTPlus(@SuppressWarnings("unused") TPlus node)
    {
        this.tokenId = SCCTokenId.PLUS;
    }

    @Override
    public void caseTMinus(@SuppressWarnings("unused") TMinus node)
    {
        this.tokenId = SCCTokenId.MINUS;
    }

    @Override
    public void caseTQMark(@SuppressWarnings("unused") TQMark node)
    {
        this.tokenId = SCCTokenId.Q_MARK;
    }

    @Override
    public void caseTStar(@SuppressWarnings("unused") TStar node)
    {
        this.tokenId = SCCTokenId.STAR;
    }

    @Override
    public void caseTBar(@SuppressWarnings("unused") TBar node)
    {
        this.tokenId = SCCTokenId.BAR;
    }

    @Override
    public void caseTComma(@SuppressWarnings("unused") TComma node)
    {
        this.tokenId = SCCTokenId.COMMA;
    }

    @Override
    public void caseTSlash(@SuppressWarnings("unused") TSlash node)
    {
        this.tokenId = SCCTokenId.SLASH;
    }

    @Override
    public void caseTArrow(@SuppressWarnings("unused") TArrow node)
    {
        this.tokenId = SCCTokenId.ARROW;
    }

    @Override
    public void caseTColon(@SuppressWarnings("unused") TColon node)
    {
        this.tokenId = SCCTokenId.COLON;
    }

    @Override
    public void caseTId(@SuppressWarnings("unused") TId node)
    {
        this.tokenId = SCCTokenId.ID;
    }

    @Override
    public void caseTChar(@SuppressWarnings("unused") TChar node)
    {
        this.tokenId = SCCTokenId.CHAR;
    }

    @Override
    public void caseTDecChar(@SuppressWarnings("unused") TDecChar node)
    {
        this.tokenId = SCCTokenId.DEC_CHAR;
    }

    @Override
    public void caseTHexChar(@SuppressWarnings("unused") THexChar node)
    {
        this.tokenId = SCCTokenId.HEX_CHAR;
    }

    @Override
    public void caseTString(@SuppressWarnings("unused") TString node)
    {
        this.tokenId = SCCTokenId.STRING;
    }

    @Override
    public void caseTBlank(@SuppressWarnings("unused") TBlank node)
    {
        this.tokenId = SCCTokenId.BLANK;
    }

    @Override
    public void caseTComment(@SuppressWarnings("unused") TComment node)
    {
        this.tokenId = SCCTokenId.COMMENT;
    }

    @Override
    public void caseEOF(@SuppressWarnings("unused") EOF node)
    {
        this.tokenId = SCCTokenId.EOF;
    }
}
