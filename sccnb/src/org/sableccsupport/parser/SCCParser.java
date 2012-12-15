package org.sableccsupport.parser;

import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import javax.swing.text.Document;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Severity;
import org.sableccsupport.sccparser.lexer.Lexer;
import org.sableccsupport.sccparser.lexer.LexerException;
import org.sableccsupport.sccparser.node.Token;
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
		pw = new SCCParserWrapper(snapshot);
		try{
			pw.parse();
		}catch(Exception ex)
		{
			Logger.getLogger (Parser.class.getName()).log (Level.WARNING, null, ex);
		}
	}

	@Override
	public SCCParserResult getResult(Task task)
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
		private final Document doc;
		
		SCCParserResult(Snapshot snapshot, SCCParserWrapper wrapper) {
			super(snapshot);
			this.wrapper = wrapper;
			doc = getSnapshot().getSource().getDocument(false);
		}
		

		public List<ErrorDescription> getSyntaxErrorDesc(){
			List<ErrorDescription> errors = new ArrayList<ErrorDescription>();
			for(ParserException pex : wrapper.getPExcep())
			{
				Token t = pex.getToken();
				String message = pex.getMessage() + t.getLine();
				int line = pex.getToken().getLine();
				
				ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
						Severity.ERROR,/*standard*/
						message,       /*String*/
						doc,      /*document*/
						line
						);
				errors.add(errorDescription);
			}
			for(LexerException pex : wrapper.getLExcept())
			{
				Token t = pex.getToken();
				String message = pex.getMessage() + t.getLine();
				int line = pex.getToken().getLine();
				
				ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
						Severity.ERROR,/*standard*/
						message,       /*String*/
						doc,      /*document*/
						line
						);
				errors.add(errorDescription);
			}
			return errors;
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
