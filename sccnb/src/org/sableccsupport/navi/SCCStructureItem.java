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
public class SCCStructureItem implements StructureItem {

	private final Token<SCCLexerTokenId> token;
	private final long offset;
	private final List childItems;

	public SCCStructureItem(Token<SCCLexerTokenId> token, 
			long offset, List<? extends SCCStructureItem> childItems) {
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
	public String getSortText() {
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
		return ElementKind.PROPERTY;
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

	public static SCCStructureItem createSectionItem (Token<SCCLexerTokenId> token, 
		long offset) {
		 return new SCCStructureItem(token, offset, null);
	}

	
}
