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

	public static SCCStructureItem createSectionItem (
			Token<SCCLexerTokenId> token, 
			long offset,
			SectionSortKey prefix) {
		 return new SectionItem(token, offset, null, prefix);
	}

	public static SCCStructureItem createStateItem(
			Token<SCCLexerTokenId> token,
			long offset
			){
		return new StatesItem(token, offset);
	}

	public static SCCStructureItem createHelperItem(
			Token<SCCLexerTokenId> token,
			long offset
			)
	{
		return new HelperItem(token, offset);
	}
}




class HelperItem extends StatesItem{
	HelperItem(Token<SCCLexerTokenId> token, long offset){
		super(token, offset);
	}
}

class StatesItem extends SCCStructureItem{
	
	StatesItem(Token<SCCLexerTokenId> token,
			long offset)
	{
		super(token, offset, null);
	}		
	
	@Override
	public String getSortText() {
		return getName();
	}
}

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