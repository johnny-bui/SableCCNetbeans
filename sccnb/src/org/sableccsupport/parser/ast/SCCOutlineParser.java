/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sableccsupport.parser.ast;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.parsing.api.Snapshot;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.navi.SCCStructureItem;
import org.sableccsupport.navi.SectionSortKey;

/**
 *
 * @author phucluoi
 * @version Dec 24, 2012
 */
public class SCCOutlineParser {
	public List<StructureItem> scanStructure(Snapshot snapshot){
		List<StructureItem> docStructure = new ArrayList<StructureItem>();
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
							docStructure.add(packageItems);
						}break;
						case HELPERS:{
							helperItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.HELPER);
							docStructure.add(helperItems);
							scanHelperDef(helperItems, ts);
						}break;
						case STATES:{
							stateItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset, 
										SectionSortKey.STATE);
							docStructure.add(stateItems);
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
								docStructure.add(tokenItems);
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
							docStructure.add(ignoredItems);
							scanIgnoreTokenDef(ignoredItems, ts);
							isInIgnoredTokens = true;
							ts.moveNext();
						}break;
						case PRODUCTIONS:{
							productItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset, 
										SectionSortKey.PRODUCT);
							docStructure.add(productItems);
							scanProductionDef(productItems, ts);
						}break;
						case ABSTRACT:
						{
							abstractItems = SCCStructureItem
									.createSectionItem(
										token, 
										offset,
										SectionSortKey.AST);
							docStructure.add(abstractItems);
							scanAST(abstractItems, ts);
						}break;
						default:{
							break;
						}
					}
				}
			}
		}

		return docStructure;
	}

	/**
	 * partial finish.
	 */
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
			
			if (isOneOf(currentToken.id(), 
				SCCLexerTokenId.STATES, SCCLexerTokenId.TOKENS, SCCLexerTokenId.IGNORED,
				SCCLexerTokenId.PRODUCTIONS, SCCLexerTokenId.ABSTRACT)){
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

	private void scanStateDef(SCCStructureItem stateItems, 
			TokenSequence<SCCLexerTokenId> ts) {
		List<StructureItem> states = new ArrayList<StructureItem>();
		stateItems.setChild(states);
		while (ts.moveNext()){
			if (isOneOf(ts.token().id(), SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT)){
				continue;
			}
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

/**
	 * TODO: subject of change do not repeat yourself
	 */
	private void scanTokenDef(SCCStructureItem tokenItems, TokenSequence<SCCLexerTokenId> ts) {
		List<StructureItem> tokens = new ArrayList<StructureItem>();
		tokenItems.setChild(tokens);

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
					SCCStructureItem item = SCCStructureItem.createTokenItem(lastToken, lastOffset);
					tokens.add(item);
				}
			}
		}
	}
	
	private void scanIgnoreTokenDef(SCCStructureItem stateItems, 
			TokenSequence<SCCLexerTokenId> ts) {
		List<StructureItem> states = new ArrayList<StructureItem>();
		stateItems.setChild(states);
		while (ts.moveNext()){
			if (isOneOf(ts.token().id(), SCCLexerTokenId.BLANK, SCCLexerTokenId.COMMENT)){
				continue;
			}
			Token<SCCLexerTokenId> token = ts.token();
			int offset = ts.offset();
			if (token.id() != SCCLexerTokenId.SEMICOLON){
				if (token.id() == SCCLexerTokenId.ID){
					StructureItem state = SCCStructureItem.createTokenItem(token, offset);
					states.add(state);
				}
			}else {
				break;
			}
		}
	}

	private void scanProductionDef(SCCStructureItem productItems, TokenSequence<SCCLexerTokenId> ts) {
		List<StructureItem> products = new ArrayList<StructureItem>();
		productItems.setChild(products);

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
					// set this variable to false sothat the productToken is not override
					continue;// and continues
				}else{
					System.out.println("!!!! Parsing error expected an id but was " + currentToken.id());
					break;
				}
			}
			if (currentToken.id() == SCCLexerTokenId.EQUAL){
				SCCStructureItem item = SCCStructureItem.createProductItem(productToken, productOffset);
				products.add(item);
				continue;
			}
		}
		
	}
	
	private void scanAST(SCCStructureItem productItems, TokenSequence<SCCLexerTokenId> ts) {
		List<StructureItem> products = new ArrayList<StructureItem>();
		productItems.setChild(products);

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
				SCCStructureItem item = SCCStructureItem.createProductItem(productToken, productOffset);
				products.add(item);
				continue;
			}
		}
		
	}
	
	private boolean isOneOf(SCCLexerTokenId checkedToken, SCCLexerTokenId... tokens){
		for (SCCLexerTokenId t : tokens){
			if (checkedToken == t){
				return true;
			}
		}
		return false;
	}

}
