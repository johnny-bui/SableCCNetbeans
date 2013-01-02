/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sableccsupport.parser.ast;

import java.util.List;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.navi.SCCStructureItem;

/**
 *
 * @author phucluoi
 * @version Dec 24, 2012
 */
public class SCCOutlineParser {
	
	private List<? extends SCCStructureItem> _structure;
	
	public GrammarStructure scanStructure(TokenSequence<SCCLexerTokenId> ts){
		GrammarStructure structure;
		structure = new GrammarStructure();
		boolean isInIgnoredTokens = false;
		ts.moveStart();
		if (ts != null){
			if (!ts.isEmpty()){
				while (ts.moveNext()){
					long offset = ts.offset();
					Token<SCCLexerTokenId> token = ts.token();
					switch(token.id()){
						case PACKAGE:{/*
							packageItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.PACKAGE);
							docStructure.add(packageItems);*/
							structure.createPackageSection(offset);
						}break;
						case HELPERS:{/*
							helperItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.HELPER);
							docStructure.add(helperItems);
							_scanHelperDef(helperItems, ts);*/
							structure.createHelperSection(offset);
							scanHelperDef(structure, ts);
						}break;
						case STATES:{/*
							stateItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset, 
										SectionSortKey.STATE);
							docStructure.add(stateItems);
							_scanStateDef(stateItems, ts);*/
							structure.createStateSection(offset);
							scanStateDef(structure, ts);
						}break;
						case TOKENS:{
							if (!isInIgnoredTokens){/*
								tokenItems = SCCStructureItem
										.createSectionItem(
											token, 
											offset,
											SectionSortKey.TOKEN);
								System.out.println("add " + token.id() + " in item");
								docStructure.add(tokenItems);
								_scanTokenDef(tokenItems, ts);*/
								structure.createTokenSection(offset);
								scanTokenDef(structure, ts);
							}else{
							}
						}break;
						case IGNORED:{
							/*ignoredItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.IGNORED);*/
							/* docStructure.add(ignoredItems);
							_scanIgnoreTokenDef(ignoredItems, ts); */
							structure.createIgnoredTokenSection(offset);
							scanIgnoreTokenDef(structure, ts);
							isInIgnoredTokens = true;
							ts.moveNext();
						}break;
						case PRODUCTIONS:{/*
							productItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset, 
										SectionSortKey.PRODUCT);
							docStructure.add(productItems);
							_scanProductionDef(productItems, ts);*/
							structure.createProductSection(offset);
							scanProductionDef(structure, ts);
						}break;
						case ABSTRACT:{/*
							abstractItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.AST);
							docStructure.add(abstractItems);
							_scanAST(abstractItems, ts);*/
							structure.creatASTSection(offset);
							scanAST(structure, ts);
						}break;
						default:{
							break;
						}
					}
				}
			}
		}
		return structure;
	}
	
	
	/**
	 * partial finish.
	 */
	private void scanHelperDef(GrammarStructure structure, TokenSequence<SCCLexerTokenId> ts){
		//List<StructureItem> helper = new ArrayList<StructureItem>();
		//helperItems.setChild(helper);
		
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
			
			if (isOneOf(currentToken.id(), 
				SCCLexerTokenId.STATES, SCCLexerTokenId.TOKENS, SCCLexerTokenId.IGNORED,
				SCCLexerTokenId.PRODUCTIONS, SCCLexerTokenId.ABSTRACT)){
				ts.movePrevious();
				break;
			}else{
				if (currentToken.id() == SCCLexerTokenId.EQUAL){
					//SCCStructureItem item = SCCStructureItem.createHelperItem(lastToken, lastOffset);
					//helper.add(item);
					structure.addNewHelper(
							GrammarStructure.newLeafNode(
								lastToken.text().toString(), lastOffset));
				}
			}
		}
	}
	
	private void scanStateDef(GrammarStructure structure, 
			TokenSequence<SCCLexerTokenId> ts) {
		//List<StructureItem> states = new ArrayList<StructureItem>();
		//stateItems.setChild(states);
		
		while (ts.moveNext()){
			if (isOneOf(ts.token().id(), SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT)){
				continue;
			}
			Token<SCCLexerTokenId> token = ts.token();
			int offset = ts.offset();
			if (token.id() != SCCLexerTokenId.SEMICOLON){
				if (token.id() == SCCLexerTokenId.ID){
					//StructureItem state = SCCStructureItem.createStateItem(token, offset);
					//states.add(state);
					structure.addNewState(
							GrammarStructure.newLeafNode(
								token.text().toString(),
								offset));
				}
			}else {
				break;
			}
		}
	}
	

	/**
	 * TODO: subject of change do not repeat yourself
	 */
	private void scanTokenDef(GrammarStructure structure, TokenSequence<SCCLexerTokenId> ts) {
		//List<StructureItem> tokens = new ArrayList<StructureItem>();
		//tokenItems.setChild(tokens);

		Token<SCCLexerTokenId> currentToken = ts.token();
		int currentOffset = ts.offset();
		
		Token<SCCLexerTokenId> lastToken;
		int lastOffset = 0;

		while (ts.moveNext()){
			if (isOneOf(ts.token().id(), 
					SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT )){
				continue;
			}
			lastToken = currentToken;
			lastOffset = currentOffset;
			
			currentToken = ts.token();
			currentOffset = ts.offset();
			
			if (isOneOf(currentToken.id(), 
				SCCLexerTokenId.IGNORED,
				SCCLexerTokenId.PRODUCTIONS, SCCLexerTokenId.ABSTRACT)){
				ts.movePrevious();
				break;
			}else{
				if (currentToken.id() == SCCLexerTokenId.EQUAL){
					//SCCStructureItem item = SCCStructureItem.createTokenItem(lastToken, lastOffset);
					//tokens.add(item);
					structure.addNewToken(
							GrammarStructure.newLeafNode(
								lastToken.text().toString(), 
								lastOffset));
				}
			}
		}
	}

	
	private void scanIgnoreTokenDef(GrammarStructure structure, 
			TokenSequence<SCCLexerTokenId> ts) {
		//List<StructureItem> states = new ArrayList<StructureItem>();
		//stateItems.setChild(states);
		while (ts.moveNext()){
			if (isOneOf(ts.token().id(), SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT)){
				continue;
			}
			Token<SCCLexerTokenId> token = ts.token();
			int offset = ts.offset();
			if (token.id() != SCCLexerTokenId.SEMICOLON){
				if (token.id() == SCCLexerTokenId.ID){
					//StructureItem state = SCCStructureItem.createTokenItem(token, offset);
					//states.add(state);
					structure.addNewIgnoredToken(
							GrammarStructure.newLeafNode(
								token.text().toString(), 
								offset));
				}
			}else {
				break;
			}
		}
	}
	
	
	
	private void scanProductionDef(GrammarStructure structure, TokenSequence<SCCLexerTokenId> ts) {
		//List<StructureItem> products = new ArrayList<StructureItem>();
		//productItems.setChild(products);

		Token<SCCLexerTokenId> currentToken = ts.token();
		int currentOffset = ts.offset();
		
		Token<SCCLexerTokenId> productToken = ts.token();
		int productOffset = 0;

		boolean beginNewProduct = true;
		while (ts.moveNext()){
			// ignore blank and comments
			currentToken = ts.token();
			currentOffset = ts.offset();
					
			if (isOneOf(currentToken.id(), 
					SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT )){
				continue;
			}
			if (currentToken.id() == SCCLexerTokenId.ABSTRACT){
				ts.movePrevious();
				break;
			}
			if (currentToken.id() == SCCLexerTokenId.SEMICOLON){
				beginNewProduct = true;
				continue;
			}
			if (beginNewProduct){// if begin new product definition, mark it in the vairable productToken
				if (currentToken.id() == SCCLexerTokenId.ID){
					productToken = currentToken;
					productOffset = currentOffset;
					beginNewProduct = false; 
					continue;// and continues
				}else{
					System.out.println("!!!! Parsing error expected an id but was " + currentToken.id());
					break;
				}
			}
			if (currentToken.id() == SCCLexerTokenId.EQUAL){
				ComposeNode production 
					= GrammarStructure.newComposedNode(
						productToken.text().toString(), 
						productOffset);
				structure.addNewProduct(production);
				scanAlt(production, ts);
				continue;
			}
		}
	}
	
	private void scanAST(GrammarStructure structure, TokenSequence<SCCLexerTokenId> ts) {
		//List<StructureItem> products = new ArrayList<StructureItem>();
		//productItems.setChild(products);

		Token<SCCLexerTokenId> currentToken = ts.token();
		int currentOffset = ts.offset();
		
		Token<SCCLexerTokenId> productToken = ts.token();
		int productOffset = 0;

		boolean beginNewProduct = true;
		while (ts.moveNext()){
			// ignore blank and comments
			currentToken = ts.token();
			currentOffset = ts.offset();
					
			if (isOneOf(currentToken.id(), 
					SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT,
					SCCLexerTokenId.SYNTAX, SCCLexerTokenId.TREE)){
				continue;
			}
			if (currentToken.id() == SCCLexerTokenId.EOF ){
				break;
			}
			if (currentToken.id() == SCCLexerTokenId.SEMICOLON){
				beginNewProduct = true;
				continue;
			}
			if (beginNewProduct){// if begin new product definition, mark it in the vairable productToken
				if (currentToken.id() == SCCLexerTokenId.ID){
					productToken = currentToken;
					productOffset = currentOffset;
					beginNewProduct = false; 
					// set this variable to false sothat the productToken is not override
					continue;// and continues
				}else{
					System.out.println("!!!! Parsing error expected an id but was " + currentToken.id());
					break;
				}
			}
			if (currentToken.id() == SCCLexerTokenId.EQUAL){
				//SCCStructureItem item = SCCStructureItem.createProductItem(productToken, productOffset);
				//products.add(item);
				ComposeNode alt = GrammarStructure.newComposedNode(
							productToken.text().toString(), 
							productOffset);
				structure.addNewAST(alt);
				scanAlt(alt, ts);
				continue;
			}
		}
		
	}
	
	private void scanAlt(ComposeNode production, TokenSequence<SCCLexerTokenId> ts){
		assert production != null;
		boolean beginAlternative = false;
		while(ts.moveNext()){
			Token<SCCLexerTokenId> currentToken = ts.token();
			int currentOffset = ts.offset();
			if (isOneOf(currentToken.id(), 
					SCCLexerTokenId.BLANK,
					SCCLexerTokenId.COMMENT)){
				continue;
			}
			if (currentToken.id() == SCCLexerTokenId.SEMICOLON){// end of product
				ts.movePrevious();// move back to semicolon, so the caller can even see the semicolon
				break;
			}
			if (currentToken.id() == SCCLexerTokenId.L_BRACE){// if a "{" is saw, mark begin a alt name
				beginAlternative = true;
				continue;
			}
			if (currentToken.id() == SCCLexerTokenId.ID){
				if (beginAlternative){// if a identifier is found directly after a "{"
					SCCNode alt = GrammarStructure.newLeafNode(
							currentToken.text().toString(), currentOffset);
					production.addChild(alt);
				}
			}
			// finally we must set beginAlternative to false to prevent the case of transition rule : 
			// {-> ... } an other cases, which we don't expected
			beginAlternative = false;
		}
	}
	
	public static boolean isOneOf(SCCLexerTokenId checkedToken, SCCLexerTokenId... tokens){
		for (SCCLexerTokenId t : tokens){
			if (checkedToken == t){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * move to the nearest token after the given offset.
	 */
	public static Token<SCCLexerTokenId> getNextToken(
		final TokenSequence<SCCLexerTokenId> ts, final int offset){
		
		final int oldOffset = ts.offset();
		ts.move(offset);
		Token<SCCLexerTokenId> nextToken = null;
		if(ts.moveNext()){
			while(ts.moveNext()){
				nextToken = ts.token();
				if (! isOneOf(nextToken.id(), SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT)){
					break;
				}
			}
			ts.move(oldOffset);// move back to the origin position
		}
		return nextToken;
	}
	
	public static Token<SCCLexerTokenId> getNextToken(
		final TokenSequence<SCCLexerTokenId> ts, final int oldOffset, final int offset){
		
		ts.move(offset);
		Token<SCCLexerTokenId> nextToken = null;
		if(ts.moveNext()){
			while(ts.moveNext()){
				nextToken = ts.token();
				if (! isOneOf(nextToken.id(), SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT)){
					break;
				}
			}
			ts.move(oldOffset);// move back to the origin position
		}
		return nextToken;
	}
	

	/**
	 * get the nearest Token before the given offset and jump back to 
	 * original position. 
	 */
	public static Token<SCCLexerTokenId> getPreviousToken(
		final TokenSequence<SCCLexerTokenId> ts,final int oldOffset, final int offset){
		
		ts.move(offset);
		Token<SCCLexerTokenId> previousToken = null;
		if(ts.moveNext()){
			while(ts.movePrevious()){
				previousToken = ts.token();
				if (! isOneOf(previousToken.id(), SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT)){
					break;
				}
			}
			ts.move(oldOffset);// move back to the origin position
		}
		return previousToken;
	}
	
	
}
