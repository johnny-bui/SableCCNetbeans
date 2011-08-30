package org.sableccsupport.parser;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.util.Exceptions;
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
		try {
			SCCParserResult sccParserResult =
					(SCCParserResult) result;
			Document document = result.getSnapshot().getSource().getDocument(false);
			List<ErrorDescription> errors = new ArrayList<ErrorDescription>();
			//for (/*SyntaxError syntaxError : syntaxErrors*/;;)
			//{
				String message = sccParserResult.getMessage();

				int line = sccParserResult.getLine();
				/*if (line <= 0) {
					continue;
				}*/
				ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
						Severity.ERROR,/*standard*/
						message,       /*String*/
						document,      /*document*/
						line
						);
				errors.add(errorDescription);
			//}
			HintsController.setErrors(document, "sablecc grammar", errors);
		}/*catch (BadLocationException ex1) {
            Exceptions.printStackTrace (ex1);
        }*/ catch (org.netbeans.modules.parsing.spi.ParseException ex) {
			Exceptions.printStackTrace(ex);
		}
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
