package org.sableccsupport.parser;

import org.sableccsupport.parser.ast.SCCOutlineParser;
import org.sableccsupport.parser.ast.SCCErrorParser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

/**
 *
 * @author phucluoi
 */
public class SCCParserFasader extends Parser {

	private Snapshot snapshot;
	private SCCErrorParser pw;
	private boolean cancelled = false;
	private List<StructureItem> structure;
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
			structure = outlineScanner.scanStructure(snapshot);
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
