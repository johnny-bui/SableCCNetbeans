package org.sableccsupport.parser;

import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.HintsController;
import org.sableccsupport.parser.SCCParser.SCCParserResult;

/**
 *
 * @author phucluoi
 */
public class SyntaxErrorsHighlightingTask extends ParserResultTask
{
	public SyntaxErrorsHighlightingTask()
	{
	}

	@Override
	public void run(Result result, SchedulerEvent event)
	{
			SCCParserResult sccParserResult =
					(SCCParserResult) result;
			Document document = result.getSnapshot().getSource().getDocument(false);
		 	List<ErrorDescription> syntaxErr = sccParserResult.getSyntaxErrorDesc();
			
//			List<ParserException> excepts = sccParserResult.getPError();
//			for(ParserException pex : excepts)
//			{
//				Token t = pex.getToken();
//				String message = pex.getMessage() + t.getLine();
//				int line = pex.getToken().getLine();
//				
//				ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
//						Severity.ERROR,/*standard*/
//						message,       /*String*/
//						document,      /*document*/
//						line
//						);
//				errors.add(errorDescription);
//			}
			HintsController.setErrors(document, "sablecc grammar", syntaxErr);

	}

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public Class<? extends Scheduler> getSchedulerClass() {
		return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
	}

	@Override
	public void cancel() {
	}
}
