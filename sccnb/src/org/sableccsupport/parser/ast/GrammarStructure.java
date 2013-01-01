package org.sableccsupport.parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author phucluoi
 * @version Dec 31, 2012
 */
public class GrammarStructure {
	private ComposeNode packages;
	private ComposeNode helpers;
	private ComposeNode states;
	private ComposeNode tokens;
	private ComposeNode ignoredTokens;
	private ComposeNode products;
	private ComposeNode ast;

	/*package section*/
	public void createPackageSection(long offset){
		packages = new ComposeNode("Packages", offset);
	}

	public List<? extends SCCNode> getPackageSection(){
		return packages == null ? Collections.EMPTY_LIST : packages.getChildNodes();
	}
	
	public SCCNode getPackage(){
		return packages;
	}
	
	/*helper section*/
	public void createHelperSection(long offset){
		helpers = new ComposeNode("Helper", offset);
	}

	public List<? extends SCCNode> getHelperSection(){
		return helpers == null ? Collections.EMPTY_LIST : helpers.getChildNodes();
	}

	public void addNewHelper(SCCNode helper){
		helpers.addChild(helper);
	}
	
	public SCCNode getHelpers(){
		return helpers;
	}
	/*state section*/
	public void createStateSection(long offset){
		states = new ComposeNode("States", offset);
	}

	public List<? extends SCCNode> getStateSection(){
		return states == null ? Collections.EMPTY_LIST : states.getChildNodes();
	}
	
	public void addNewState(SCCNode state){
		states.addChild(state);
	}

	public SCCNode getStates(){
		return states;
	}
	/*token section*/
	public void createTokenSection(long offset){
		tokens = new ComposeNode("Tokens", offset);
	}

	public List<? extends SCCNode> getTokenSection(){
		return tokens == null ? Collections.EMPTY_LIST : tokens.getChildNodes();
	}

	public void addNewToken(SCCNode token){
		tokens.addChild(token);
	}
	
	public SCCNode getTokens(){
		return tokens;
	}
	
	/* ignored token section*/
	public void createIgnoredTokenSection(long offset){
		ignoredTokens = new ComposeNode("Ignored Token", offset);
	}

	public List<? extends SCCNode> getIgnoredTokenSection(){
		return ignoredTokens == null ? Collections.EMPTY_LIST : ignoredTokens.getChildNodes();
	}
	public void addNewIgnoredToken(SCCNode ignoredToken){
		ignoredTokens.addChild(ignoredToken);
			
	}
	public SCCNode getIgnoredTokens(){
		return ignoredTokens;
	}
	/*production*/
	public void createProductSection(long offset){
		products = new ComposeNode("Products", offset);
	}

	public List<SCCNode> getProductSection(){
		return products == null ? Collections.EMPTY_LIST : products.getChildNodes();
	}

	public void addNewProduct(SCCNode prod){
		products.addChild(prod);
	}
	public SCCNode getProducts(){
		return products;
	}
	/*ast section*/
	public void creatASTSection(long offset){
		ast = new ComposeNode("Abstract Syntax Tree", offset);
	}

	public List<? extends SCCNode> getASTSection(){
		return ast == null ? Collections.EMPTY_LIST : ast.getChildNodes();
	}

	public void addNewAST(SCCNode a){
		ast.addChild(a);
	}

	public SCCNode getAST(){
		return ast;
	}
	
	public static SCCNode newLeafNode(String name, long offset){
		return new LeafNode(name, offset);
	}

	public static SCCNode newComposedNode(String name, long offset){
		return new ComposeNode(name, offset);
	}
/*
	public long getPackageSectionOffset(){
		return packages == null ? -1 : packages.offset();
	}

	public long getHelperSectionOffset(){
		return helpers == null ? -1 : helpers.offset();
	}

	public long getStateSectionOfffset(){
		return states == null ? -1 : states.offset();
	}

	public long getTokenSectionOffset(){
		return tokens == null ? -1 : tokens.offset();
	}

	public long getProductSectionOffset(){
		return tokens == null ? -1 : products.offset();
	}

	public long getASTSectionOffset(){
		return ast == null ? -1 : ast.offset();
	}
*/

	public long getOffsetFirstOccurence(String identifier) {
		for (SCCNode n : tokens.getChildNodes()){
			if (n.name().equals(identifier)){
				return n.offset();
			}
		}
		for (SCCNode n : products.getChildNodes()){
			if (n.name().equals(identifier)){
				return n.offset();
			}
		}
		for (SCCNode n : ast.getChildNodes()){
			if (n.name().equals(identifier)){
				return n.offset();
			}
		}
		return -1;
	}
}
