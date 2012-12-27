
package org.sableccsupport.parser.ast;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.parsing.api.Snapshot;
import org.sableccsupport.sccparser.lexer.Lexer;
import org.sableccsupport.sccparser.lexer.LexerException;
import org.sableccsupport.sccparser.node.Start;
import org.sableccsupport.sccparser.parser.Parser;
import org.sableccsupport.sccparser.parser.ParserException;
/**
 *
 * @author phucluoi
 * @version Jul 1, 2012
 */
public class SCCErrorParser {
	private StateInitedPLexer lex;
	private Parser parser;
	private List<ParserException> parseError;
	private List<LexerException> lexError;
	private final Snapshot sns;
	// TODO: make switch state here
	PushbackReader text;
	private Start ast;
	public SCCErrorParser(Snapshot sns) 
	{
		text = new PushbackReader(new StringReader(sns.getText().toString()) );
		lex = new StateInitedPLexer(text, Lexer.State.NORMAL);
		parser = new Parser(lex);
		parseError = new ArrayList<ParserException>();
		lexError = new ArrayList<LexerException>();
		this.sns = sns;
	}
	
	public void checkSyntaxErr() 
	{
		try {
			ast = parser.parse();
		} catch (ParserException ex) {
			recoverParseErr(ex);
		} catch (LexerException ex) {
			recoverLexErr(ex);
		} catch (IOException ex1)
		{
			recoverIOException();
		}
	}

	
	public Start getAst() throws ParserException, LexerException, IOException {
		if (ast != null) {
			return ast;
		}else{
			String t = sns.getText().toString();
			ast = (new Parser(new Lexer(new PushbackReader(new StringReader(t))))).parse();
			t = null;
			return ast;	
		}
	}
	
	
	private void recoverParseErr(ParserException ex) 
	{
		parseError.add(ex);
	}
	
	private void recoverLexErr(LexerException ex) 
	{
		lexError.add(ex);
		try {
			int length = ex.getToken().getText().length();
			while (length > 0) {
				--length;
				text.read();
			}
			checkSyntaxErr();
		} catch (IOException ex1) {
			recoverIOException();
		}
	}
	
	private void recoverIOException(){
		text = new PushbackReader(new StringReader(sns.getText().toString()) );
		lex = new StateInitedPLexer(text, Lexer.State.PACKAGE);
		parser = new Parser(lex);
		checkSyntaxErr();
	}
	
	public List<ParserException> getPExcep()
	{
		return parseError;
	}
	public List<LexerException> getLExcept()
	{
		return lexError;
	}
}
