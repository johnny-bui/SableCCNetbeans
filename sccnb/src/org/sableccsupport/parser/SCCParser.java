package org.sableccsupport.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.navi.SCCStructureItem;

/**
 *
 * @author phucluoi
 */
public class SCCParser extends Parser {

	private Snapshot snapshot;
	private SCCParserWrapper pw;
	private boolean cancelled = false;
	private List<StructureItem> structure;
	
	@Override
	public void parse(
			Snapshot snapshot, Task task,SourceModificationEvent event)
	{
		this.snapshot = snapshot;
		pw = new SCCParserWrapper(snapshot);
		try{
			pw.checkSyntaxErr();
			scanStructure();
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
	
	private void scanStructure(){
		structure = new ArrayList<StructureItem>();
		SCCStructureItem packageItems;
		SCCStructureItem helperItems;
		SCCStructureItem tokenItems;
		SCCStructureItem ignoredItems;
		SCCStructureItem productItems;
		SCCStructureItem abstractItems;
		boolean isInIgnoredTokens = false;
		TokenSequence<SCCLexerTokenId> ts = 
				snapshot.getTokenHierarchy().tokenSequence(SCCLexerTokenId.getLanguage());
		if (ts != null){
			if (!ts.isEmpty()){
				while (ts.moveNext()){
					long offset = ts.offset();
					Token<SCCLexerTokenId> token = ts.token();
					switch(token.id()){
						case PACKAGE:{
							packageItems = SCCStructureItem.createSectionItem(token, offset);
							structure.add(packageItems);
						}break;
						case HELPERS:{
							helperItems = SCCStructureItem.createSectionItem(token, offset);
							structure.add(helperItems);
						}break;
						case TOKENS:{
							if (!isInIgnoredTokens){
								tokenItems = SCCStructureItem.createSectionItem(token, offset);
								structure.add(tokenItems);
							}else{
								break;
							}
						}break;
						case IGNORED:{
							ignoredItems = SCCStructureItem.createSectionItem(token, offset);
							structure.add(ignoredItems);
							isInIgnoredTokens = true;
						}
						case PRODUCTIONS:{
							productItems = SCCStructureItem.createSectionItem(token, offset);
							structure.add(productItems);
						}break;
						case ABSTRACT:
						{
							abstractItems = SCCStructureItem.createSectionItem(token, offset);
							structure.add(abstractItems);
						}break;
						default:{
							break;
						}
					}
				}
			}
		}
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
