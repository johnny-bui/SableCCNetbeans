package org.sableccsupport.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import javax.swing.event.ChangeListener;

import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.openide.util.Exceptions;
import org.sableccsupport.sccparser.lexer.LexerException;
import org.sableccsupport.sccparser.parser.ParserException;

/**
 *
 * @author phucluoi
 */
public class SCCParser extends Parser {

	private Snapshot snapshot;
	private org.sableccsupport.sccparser.parser.Parser sableccParser;
	private String message = "????";
	private int line = 0;
	private boolean cancelled = false;
	@Override
	public void parse(
			Snapshot                snapshot,
			Task                    task,
			SourceModificationEvent event
	){
		this.snapshot = snapshot;
		ExtendLexerToken  sableccLexer = new
				ExtendLexerToken(
				new PushbackReader(new StringReader(snapshot.getText().toString()) ) );
		sableccParser = new
				org.sableccsupport.sccparser.parser.Parser(sableccLexer);
		try {
			sableccParser.parse();
			if (cancelled)
				return;
		} catch (ParserException ex) {
			//Exceptions.printStackTrace(ex);
			//this.message = ex.getMessage();
			//this.line = ex.getToken().getLine();
			//throw new RuntimeException(ex.getMessage());
		} catch (LexerException ex) {
			//Exceptions.printStackTrace(ex);
			//this.message = ex.getMessage();
			//this.line    = sableccLexer.getToken().getLine();
			//throw new RuntimeException(ex.getMessage());
		} catch (Exception ex) {
			//Exceptions.printStackTrace(ex);
			//throw new RuntimeException(ex.getMessage());
		}
		//throw new RuntimeException("end of parse");
	}

	@Override
	public Result getResult(Task task)
	{
		return new SCCParserResult(snapshot, message, line);
	}

	@Deprecated
	@Override
	public void cancel()
	{
		cancelled = true;
	}

	@Override
	public void addChangeListener(ChangeListener changeListener) {
	}

	@Override
	public void removeChangeListener(ChangeListener changeListener) {
	}

	public static class SCCParserResult extends Result {

		//private OracleParser sqlParser;
		private boolean valid = true;
		private String message = null;
		private int line;
		SCCParserResult(Snapshot snapshot, 
				String message, int line) {
			super(snapshot);
			this.message = message;
			this.line = line;
			throw new RuntimeException("created ParserResult");
		}

		public String getMessage()
			throws org.netbeans.modules.parsing.spi.ParseException
		{
			if (!valid) throw new org.netbeans.modules.parsing.spi.ParseException ();
			return message;
		}

		public int getLine()
			throws org.netbeans.modules.parsing.spi.ParseException
		{
			if (!valid) throw new org.netbeans.modules.parsing.spi.ParseException ();
			return line;
		}

		@Override
		protected void invalidate() {
			valid = false;
		}
		
	}
}
