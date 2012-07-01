package org.sableccsupport.parser;

import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.sableccsupport.sccparser.lexer.Lexer;
import org.sableccsupport.sccparser.parser.ParserException;

/**
 *
 * @author phucluoi
 */
public class SCCParser extends Parser {

	private Snapshot snapshot;
	private SCCParserWrapper pw;
	private boolean cancelled = false;
	@Override
	public void parse(
			Snapshot                snapshot,
			Task                    task,
			SourceModificationEvent event
	){
		this.snapshot = snapshot;
		Lexer  sableccLexer = new
				Lexer(
				new PushbackReader(new StringReader(snapshot.getText().toString()) ) );
		pw = new SCCParserWrapper(snapshot);
		try{
			pw.parse();
		}catch(Exception ex)
		{
			Logger.getLogger (Parser.class.getName()).log (Level.WARNING, null, ex);
		}
	}

	@Override
	public Result getResult(Task task)
	{
		return new SCCParserResult(snapshot, pw);
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

	public static class SCCParserResult extends ParserResult 
	{

		//private OracleParser sqlParser;
		private boolean valid = true;
		private final SCCParserWrapper wrapper;
		
		SCCParserResult(Snapshot snapshot, SCCParserWrapper wrapper) {
			super(snapshot);
			this.wrapper = wrapper;
		}
		
		public List<ParserException> getPError()
		{
			return wrapper.getPExcep();
		}
		
		@Override
		protected void invalidate() {
			valid = false;
		}

		@Override
		public List<? extends Error> getDiagnostics() {
			return new ArrayList<Error>(0);
		}
		
	}
}
