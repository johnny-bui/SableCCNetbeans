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
import org.sableccsupport.navi.SectionSortKey;
import sun.awt.image.OffScreenImage;

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
		SCCStructureItem stateItems;
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
							packageItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.PACKAGE);
							structure.add(packageItems);
						}break;
						case HELPERS:{
							helperItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.HELPER);
							structure.add(helperItems);
							scanHelperDef(helperItems, ts);
						}break;
						case STATES:{
							stateItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset, 
										SectionSortKey.STATE);
							structure.add(stateItems);
							scanStateDef(stateItems, ts);
						}break;
						case TOKENS:{
							System.out.println("token is " + token.id());
							if (!isInIgnoredTokens){
								tokenItems = SCCStructureItem
										.createSectionItem(
											token, 
											offset,
											SectionSortKey.TOKEN);
								System.out.println("add " + token.id() + " in item");
								structure.add(tokenItems);
								scanTokenDef(tokenItems, ts);
							}else{
								System.out.println("!!! " + token.id() + " in IGNORE");
							}
						}break;
						case IGNORED:{
							System.out.println("token is " + token.id());
							ignoredItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.IGNORED);
							System.out.println("add " + token.id() + " in item");
							structure.add(ignoredItems);
							isInIgnoredTokens = true;
							ts.moveNext();
						}break;
						case PRODUCTIONS:{
							productItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset, 
										SectionSortKey.PRODUCT);
							structure.add(productItems);
						}break;
						case ABSTRACT:
						{
							abstractItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.AST);
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

	private void scanHelperDef(SCCStructureItem helperItems, TokenSequence<SCCLexerTokenId> ts){
		List<StructureItem> helper = new ArrayList<StructureItem>();
		helperItems.setChild(helper);
		
		Token<SCCLexerTokenId> currentToken = ts.token();
		int currentOffset = ts.offset();
		
		Token<SCCLexerTokenId> lastToken;
		int lastOffset = 0;
		
		while (ts.moveNext()){
			if (ts.token().id() == SCCLexerTokenId.BLANK){
				continue;
			}
			lastToken = currentToken;
			lastOffset = currentOffset;
			
			currentToken = ts.token();
			currentOffset = ts.offset();

			if (currentToken.id() == SCCLexerTokenId.STATES || 
					currentToken.id() == SCCLexerTokenId.TOKENS ||
					currentToken.id() == SCCLexerTokenId.IGNORED ||
					currentToken.id() == SCCLexerTokenId.PRODUCTIONS ||
					currentToken.id() == SCCLexerTokenId.ABSTRACT){
				ts.movePrevious();
				break;
			}else{
				if (currentToken.id() == SCCLexerTokenId.EQUAL){
					SCCStructureItem item = SCCStructureItem.createHelperItem(lastToken, lastOffset);
					helper.add(item);
				}
			}
		}
	
	}
	
	private void scanTokenDef(SCCStructureItem tokenItems, TokenSequence<SCCLexerTokenId> ts) {
		/*while(ts.moveNext()){
			
		}*/
	}

	private void scanStateDef(SCCStructureItem stateItems, 
			TokenSequence<SCCLexerTokenId> ts) {
		List<StructureItem> states = new ArrayList<StructureItem>();
		stateItems.setChild(states);
		while (ts.moveNext()){
			Token<SCCLexerTokenId> token = ts.token();
			int offset = ts.offset();
			if (token.id() != SCCLexerTokenId.SEMICOLON){
				if (token.id() == SCCLexerTokenId.ID){
					StructureItem state = SCCStructureItem.createStateItem(token, offset);
					states.add(state);
				}
			}else {
				break;
			}
		}
	}
}
