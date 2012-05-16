
package org.sablecc.sablecc;

import org.netbeans.api.lexer.TokenId;
import org.sablecc.sablecc.analysis.AnalysisAdapter;
import org.sablecc.sablecc.analysis.DepthFirstAdapter;
import org.sablecc.sablecc.node.AGrammar;
import org.sablecc.sablecc.node.ATokenDef;
import org.sablecc.sablecc.node.ATokens;
import org.sablecc.sablecc.node.Token;

/**
 *
 * @author verylazyboy
 * @version May 15, 2012
 * This is just an experiment. It will be used to generate some file to 
 * make supporting for new language easier. (something like the project
 * Schliemann (???) {@link http://wiki.netbeans.org/Schliemann} )
 */

public class TokenEnumGenerator extends DepthFirstAdapter
{
	StringBuffer buff;
	public static String PRE = 
		"/*generated automatically*/\n"+
		"import org.netbeans.api.lexer.Language;\n" +
		"import org.netbeans.api.lexer.TokenId;\n"  +			
		"public enum <<>> implements TokenId\n"     +
		"{\n";
	public static String POST = "\t;\n}";
	private int idx;
	
	public TokenEnumGenerator ()
	{
		buff = new StringBuffer();
		idx = 0;
	}

	@Override
	public void caseAGrammar(AGrammar node) 
	{
		if (node.getTokens() != null)
		{
			node.getTokens().apply(this);
		}
		if (node.getIgnTokens() != null)
		{
			node.getIgnTokens().apply(this);
		}
		buff.append(constructEntryForEOF());
		idx += 1;
		buff.append(constructEntryForError());
	}

	/*
	@Override
	public void caseATokens(ATokens node) {
		super.caseATokens(node);
	}
	*/
	
	
	@Override
	public void caseATokenDef(ATokenDef node) 
	{
		String entry = constructEntryForToken(node);
		idx = idx + 1;
		buff.append(entry);		
		super.caseATokenDef(node);
	}

	public static String transform(String token) {
		StringBuffer tmp = new StringBuffer(token);
		String firstChar = ("" +tmp.charAt(0)).toUpperCase() ;
		tmp.replace(0, 1, firstChar);
		int _idx_ = tmp.indexOf("_");
		String transform = null;
		while (_idx_ != -1)
		{
			transform = tmp.substring(_idx_ + 1, _idx_ +2).toUpperCase();
			tmp.replace(_idx_, _idx_ + 2, transform);
			_idx_ = tmp.indexOf("_");
		}
		return tmp.toString();
	}
	
	public String getTokenLst()
	{
		return PRE + buff.toString() + POST;
	}

	protected String constructEntryForToken(ATokenDef node)
	{
		String token = node.getId().getText();
		String entry = "\t"+token.toUpperCase() + 
				" (" + idx + ",\"" + transform(token) + "\"),\n";
		return entry;
	}

	protected String constructEntryForEOF()
	{
		return "\tEOF(" + idx + "," + "\"EOF\"),\n";
	}

	protected String constructEntryForError()
	{
		return "\t_ERROR_(" + idx + "," + "\"_ERROR_\"),\n";
	}
}
