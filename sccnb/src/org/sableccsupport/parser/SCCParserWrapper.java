/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sableccsupport.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.api.Severity;
import org.netbeans.modules.parsing.api.Snapshot;
import org.openide.filesystems.FileObject;
import org.sableccsupport.sccparser.lexer.Lexer;
import org.sableccsupport.sccparser.lexer.LexerException;
import org.sableccsupport.sccparser.node.Token;
import org.sableccsupport.sccparser.parser.Parser;
import org.sableccsupport.sccparser.parser.ParserException;
/**
 *
 * @author phucluoi
 * @version Jul 1, 2012
 */
public class SCCParserWrapper {
	private StateInitedPLexer lex;
	private Parser parser;
	private List<ParserException> parseError;
	private List<LexerException> lexError;
	private final ExtendTokenIndex tokenIdxConverter;
	private final Snapshot sns;
	// TODO: make switch state here
	SCCParserWrapper(Snapshot sns) 
	{
		PushbackReader text = new PushbackReader(new StringReader(sns.getText().toString()) );
		lex = new StateInitedPLexer(text, Lexer.State.NORMAL);
		parser = new Parser(lex);
		parseError = new ArrayList<ParserException>();
		lexError = new ArrayList<LexerException>();
		tokenIdxConverter = new ExtendTokenIndex();
		this.sns = sns;
	}
	
	private void recoverParseErr(ParserException ex) throws IOException
	{
		parseError.add(ex);
		try {
			Token t = lex.next();
			int idx = tokenIdxConverter.indexOf(t);
			while (idx < ParserTokenId.EOF.idx() // skip to next semicolon
					&& idx != ParserTokenId.SEMICOLON.idx()
					 )
			{
				idx=  tokenIdxConverter.indexOf(lex.next());
			}
			parser.parse();
		}catch (ParserException ex1)
		{
			recoverParseErr(ex1);
		}catch (LexerException ex1) {
			recoverLexErr(ex1);
		}catch (IOException ex1)
		{
			PushbackReader text = new PushbackReader(new StringReader(sns.getText().toString()) );
			lex = new StateInitedPLexer(text, Lexer.State.PACKAGE);
			parser = new Parser(lex);
			parse();
		}
	}
	
	private void recoverLexErr(LexerException ex) throws IOException
	{
		lexError.add(ex);
		try {
			Token t = lex.next();
			int idx = tokenIdxConverter.indexOf(t);
			while (idx < ParserTokenId.EOF.idx() // skip to next semicolon
					&& idx != ParserTokenId.SEMICOLON.idx()
					)
			{
				idx=  tokenIdxConverter.indexOf(lex.next());
			}
			parser.parse();
		} catch (LexerException ex1) {
			recoverLexErr(ex1);// call itself !!!
		} catch (ParserException ex1)
		{
			recoverParseErr(ex1);
		}catch (IOException ex1)
		{
			PushbackReader text = new PushbackReader(new StringReader(sns.getText().toString()) );
			lex = new StateInitedPLexer(text, Lexer.State.PACKAGE);
			parser = new Parser(lex);
			parse();
		}
	}
	
	public void parse() throws IOException
	{
		try {
			parser.parse();
		} catch (ParserException ex) {
			recoverParseErr(ex);
		} catch (LexerException ex) {
			recoverLexErr(ex);
		} catch (IOException ex1)
		{
			PushbackReader text = new PushbackReader(new StringReader(sns.getText().toString()) );
			lex = new StateInitedPLexer(text, Lexer.State.PACKAGE);
			parser = new Parser(lex);
			parse();
		}
	}

	
	public List<ParserException> getPExcep()
	{
		return parseError;
	}
	public List<LexerException> getLExcept()
	{
		return lexError;
	}
	
	public class SCCParserError implements Error
	{
		private final String displayName;
		private final String description;
		private final String key;
		private final int line;
		private final Snapshot snapshot;
		
		public SCCParserError(ParserException ex, final Snapshot snapshot)
		{
			Token token = ex.getToken();
			this.displayName = token.getText() + token.getLine();
			this.description = ex.getToken().getText();
			this.key = "SableCC parse Error";
			
			this.line = ex.getToken().getLine();
			this.snapshot = snapshot;
		}
		@Override
		public String getDisplayName() {
			return displayName;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public String getKey() {
			return key;
		}

		// TODO: error
		@Override
		public FileObject getFile() {
			return this.snapshot.getSource().getFileObject();
		}

		@Override
		public int getStartPosition() {
			return this.line;
		}

		@Override
		public int getEndPosition() {
			return this.line;
		}

		@Override
		public boolean isLineError() {
			return false;
		}

		@Override
		public Severity getSeverity() {
			return Severity.ERROR;
		}

		@Override
		public Object[] getParameters() {
			return null;
		}
		
	}
}
