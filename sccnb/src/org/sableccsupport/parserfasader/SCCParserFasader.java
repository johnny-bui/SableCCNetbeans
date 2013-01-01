package org.sableccsupport.parserfasader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.parser.ast.SCCErrorParser;
import org.sableccsupport.parser.ast.SCCOutlineParser;

/**
 *
 * @author phucluoi
 */
public class SCCParserFasader extends Parser {

	private Snapshot snapshot;
	private SCCErrorParser pw;
	private boolean cancelled = false;
	private List<? extends StructureItem> structure;
	private SCCOutlineParser outlineScanner;

	public SCCParserFasader() {
		outlineScanner = new SCCOutlineParser();
	}
	
	@Override
	public void parse(
			Snapshot snapshot, Task task,SourceModificationEvent event)
	{
		this.snapshot = snapshot;
		pw = new SCCErrorParser(snapshot);
		try{
			pw.checkSyntaxErr();
			TokenSequence<SCCLexerTokenId> ts = 
					snapshot.getTokenHierarchy().tokenSequence(SCCLexerTokenId.getLanguage());
			structure = outlineScanner._scanStructure(ts);
		}catch(Exception ex)
		{
			Logger.getLogger (Parser.class.getName()).log (Level.WARNING, null, ex);
		}
	}

	@Override
	public SCCParserResult getResult(Task task)
	{
		SCCParserResult result = new SCCParserResult(snapshot, pw);
		result.setStructure(this.structure);
		return result;
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
}
