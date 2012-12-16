
package org.sableccsupport.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.text.Document;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Severity;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.sccparser.lexer.LexerException;
import org.sableccsupport.sccparser.node.Token;
import org.sableccsupport.sccparser.parser.ParserException;

/**
 * Represent the result after the real parser parses the document in the 
 * way that other NB Classes can directly use the result without converting
 * or adapting something. It can interacts with the real parser (here the
 * SCCParserWarpper) to get information and to prepare the info.
 * 
 * @author phucluoi
 * @version Dec 16, 2012
 */
public class SCCParserResult extends ParserResult {
	private boolean valid = true;
	private final SCCParserWrapper wrapper;
	private final Document doc;
	
	/**
	 * 
	 */
	SCCParserResult(Snapshot snapshot, SCCParserWrapper wrapper) {
		super(snapshot);
		this.wrapper = wrapper;
		doc = getSnapshot().getSource().getDocument(false);
	}

	public List<ErrorDescription> getSyntaxErrorDesc() {
		List<ErrorDescription> errors = new ArrayList<ErrorDescription>();
		for (Iterator<ParserException> it = wrapper.getPExcep().iterator(); it.hasNext();) {
			ParserException pex = it.next();
			ErrorDescription errorDescription = 
					makeErrDescr(pex, pex.getToken());
			errors.add(errorDescription);
		}
		for (Iterator<LexerException> it = wrapper.getLExcept().iterator(); it.hasNext();) {
			LexerException pex = it.next();
			ErrorDescription errorDescription = 
				makeErrDescr(pex, pex.getToken());
			errors.add(errorDescription);
		}
		return errors;
	}
	
	private ErrorDescription makeErrDescr(Exception pex, Token t){
		String message = pex.getMessage() + " in line: " + t.getLine();
		int line = t.getLine();
		ErrorDescription errorDescription = 
		ErrorDescriptionFactory.createErrorDescription(Severity.ERROR, message, doc, line);
		return errorDescription;	
	}

	public List<? extends StructureItem> getStructure(){
		List<? extends StructureItem> items = new ArrayList<StructureItem>();
		return items;
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
