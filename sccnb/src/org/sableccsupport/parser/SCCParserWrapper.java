
package org.sableccsupport.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.api.Severity;
import org.netbeans.modules.parsing.api.Snapshot;
import org.openide.filesystems.FileObject;
import org.sableccsupport.sccparser.lexer.Lexer;
import org.sableccsupport.sccparser.lexer.LexerException;
import org.sableccsupport.sccparser.node.Start;
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
	PushbackReader text;
	private Start ast;
	SCCParserWrapper(Snapshot sns) 
	{
		text = new PushbackReader(new StringReader(sns.getText().toString()) );
		lex = new StateInitedPLexer(text, Lexer.State.NORMAL);
		parser = new Parser(lex);
		parseError = new ArrayList<ParserException>();
		lexError = new ArrayList<LexerException>();
		tokenIdxConverter = new ExtendTokenIndex();
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
			//text = new PushbackReader(new StringReader(sns.getText().toString()) );
			//lex = new StateInitedPLexer(text, Lexer.State.PACKAGE);
			//parser = new Parser(lex);
			//parse();
			recoverIOException();
		}
	}
	
	public Start getAst(){
		if (ast != null) {
			// if the ast is already constructed 
			// (it means the sablecc grammar ist syntactical OK)
			// just return it
			return ast;
		}else{
			// if not try to parse the doc agains
			Document doc = sns.getSource().getDocument(true);
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
/*	
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
*/ 
}
