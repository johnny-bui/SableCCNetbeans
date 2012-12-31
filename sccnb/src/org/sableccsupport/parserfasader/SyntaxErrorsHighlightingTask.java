package org.sableccsupport.parserfasader;

import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.HintsController;

/**
 *
 * @author phucluoi
 */
public class SyntaxErrorsHighlightingTask extends ParserResultTask {

	public SyntaxErrorsHighlightingTask() {
	}

	@Override
	public void run(Result result, SchedulerEvent event) {
		try{
			SCCParserResult sccParserResult =
					(SCCParserResult) result; // <-- source of error
			Document document = result.getSnapshot().getSource().getDocument(false);
			List<ErrorDescription> syntaxErr = sccParserResult.getSyntaxErrorDesc();
			HintsController.setErrors(document, "sablecc grammar", syntaxErr);
		}catch (ClassCastException ex){
			// nothing do do here
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
