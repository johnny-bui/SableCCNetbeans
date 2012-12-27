/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sableccsupport.navi;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import org.netbeans.api.lexer.Token;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.StructureItem;
import org.openide.util.ImageUtilities;
import org.sableccsupport.lexer.SCCLexerTokenId;


/**
 *
 * @author phucluoi
 * @version Dec 17, 2012
 */
public abstract class SCCStructureItem implements StructureItem {

	protected final Token<SCCLexerTokenId> token;
	private final long offset;
	protected List childItems;
			
	public SCCStructureItem(Token<SCCLexerTokenId> token, 
			long offset, 
			List<? extends SCCStructureItem> childItems)
	{
		this.token = token;
		this.offset = offset;
		if (childItems == null) {
			this.childItems = Collections.EMPTY_LIST;
		} else {
			this.childItems = childItems;
		}
	}

	

	@Override
	public String getName() {
		return token.text().toString();
	}

	@Override
	public String getHtml(HtmlFormatter hf) {
		hf.reset();
		hf.appendText(getName());
		return hf.getText();
	}

	@Override
	public ElementHandle getElementHandle() {
		return null;
	}

	@Override
	public ElementKind getKind() {
		return ElementKind.OTHER;
	}

	@Override
	public Set<Modifier> getModifiers() {
		return Collections.EMPTY_SET;
	}

	@Override
	public boolean isLeaf() {
		return childItems.isEmpty();// empty = leaf, else not leaf
	}

	@Override
	public List<? extends StructureItem> getNestedItems() {
		return this.childItems;
	}

	@Override
	public long getPosition() {
		return offset;
	}

	@Override
	public long getEndPosition() {
		return offset + token.length();
	}

	@Override
	public ImageIcon getCustomIcon() {
		return new ImageIcon(ImageUtilities.loadImage("org/sableccsupport/img/value.png"));
	}

	public void setChild(List<StructureItem> childItems){
		if (childItems != null){
			this.childItems = childItems;
		}
	}
	
	/** create a section item. */
	public static SCCStructureItem createSectionItem (
			Token<SCCLexerTokenId> token, 
			long offset,
			SectionSortKey prefix) {
		 return new SectionItem(token, offset, null, prefix);
	}

	/** create a state item. */
	public static SCCStructureItem createStateItem(
			Token<SCCLexerTokenId> token,
			long offset
			){
		return new StateItem(token, offset);
	}

	/** create a helper item. */
	public static SCCStructureItem createHelperItem(
			Token<SCCLexerTokenId> token,
			long offset){
		return new HelperItem(token, offset);
	}
	
	/** create token item. */
	public static SCCStructureItem createTokenItem(
			Token<SCCLexerTokenId> token,
			long offset){
		return new TokenItem(token, offset);
	}

	public static SCCStructureItem createProductItem(
			Token<SCCLexerTokenId> token,
			long offset
			){
		return new ProductItem(token, offset);
	}
}



class SimpleItem extends SCCStructureItem{
	
	SimpleItem(Token<SCCLexerTokenId> token,
			long offset)
	{
		super(token, offset, null);
	}		
	
	@Override
	public String getSortText() {
		return getName();
	}
}

/** represents the helpers in Helper Section.
 * TODO: make a new Icon for it
 */
class HelperItem extends SimpleItem{
	HelperItem(Token<SCCLexerTokenId> token, long offset){
		super(token, offset);
	}
	@Override
	public ElementKind getKind() {
		return ElementKind.FIELD;
	}

	@Override
	public ImageIcon getCustomIcon() {
		return new ImageIcon(ImageUtilities.loadImage("org/sableccsupport/img/helper.png"));
	}
}

/**
 * represents the states in States Section.
 * TODO: make new Icons
 */
class StateItem extends SimpleItem{
	public StateItem(Token<SCCLexerTokenId> token, long offset) {
		super(token, offset);
	}

	@Override
	public ImageIcon getCustomIcon() {
		return new ImageIcon(ImageUtilities.loadImage("org/sableccsupport/img/state.png"));
	}
}

/**
 * represents the product definition in the Products section.
 * Object to change
 */
class ProductItem extends SimpleItem{
	public ProductItem(Token<SCCLexerTokenId> token, long offset) {
		super(token, offset);
	}
}

/**
 * represents the token in tokens Section.
 * TODO: s.o.
 */
class TokenItem extends SimpleItem{
	public TokenItem(Token<SCCLexerTokenId> token, long offset) {
		super(token, offset);
	}

	@Override
	public ImageIcon getCustomIcon() {
		return new ImageIcon(ImageUtilities.loadImage("org/sableccsupport/img/token.png"));
	}
}

/**
 * represents the sections in a sablecc file
 */
class SectionItem extends SCCStructureItem{
	private final SectionSortKey prefix;
	public SectionItem(Token<SCCLexerTokenId> token, 
			long offset, 
			List<? extends SCCStructureItem> childItems,
			SectionSortKey prefix)
	{
		super(token, offset, childItems);
		this.prefix = prefix;	
	}

	@Override
	public String getName() {
		return prefix.getSectionName();
	}
	
	@Override
	public ImageIcon getCustomIcon() {
		return new ImageIcon(ImageUtilities.loadImage("org/sableccsupport/img/section.png"));
	}

	@Override
	public String getSortText() {
		return ""+ prefix.ordinal();// +  token.text().toString();
	}
}