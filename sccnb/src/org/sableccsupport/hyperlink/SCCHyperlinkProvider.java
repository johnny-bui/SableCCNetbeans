/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sableccsupport.hyperlink;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProvider;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.parser.ast.SCCOutlineParser;

/**
 *
 * @author phucluoi
 * @version Dec 30, 2012
 */
@MimeRegistration(mimeType = "text/x-sablecc", service = HyperlinkProvider.class)
public class SCCHyperlinkProvider implements HyperlinkProvider{
	private int startOffset;
	private int endOffset;

	@Override
	public boolean isHyperlinkPoint(Document doc, int offset) {
		
		JTextComponent target = EditorRegistry.lastFocusedComponent();
        final StyledDocument styledDoc = (StyledDocument) target.getDocument();
        if (styledDoc == null) {
            return false;
        }

        // Work only with the open editor 
        //and the editor has to be the active component:
        if ((target == null) || (target.getDocument() != doc)) {
            return false;
        }

        TokenHierarchy hi = TokenHierarchy.get(doc);
        TokenSequence<SCCLexerTokenId> ts = hi.tokenSequence(SCCLexerTokenId.getLanguage());
        
		ts.move(offset);
		if (ts.moveNext()){
			Token<SCCLexerTokenId> testToken = ts.token();
			startOffset = ts.offset();
			if (testToken.id() == SCCLexerTokenId.ID){
				Token<SCCLexerTokenId> nextToken = SCCOutlineParser.getNextToken(ts, offset);
				if (! SCCOutlineParser.isOneOf(nextToken.id(), 
						SCCLexerTokenId.EQUAL, 
						SCCLexerTokenId.R_BKT)){
					endOffset = startOffset + testToken.text().length();
					return true;
				}
				if (nextToken.id() == SCCLexerTokenId.R_BRACE){
					Token<SCCLexerTokenId> previousToken = SCCOutlineParser.getPreviousToken(ts, offset);
					if (previousToken.id() == SCCLexerTokenId.ARROW){
						endOffset = startOffset + testToken.text().length();
						return true;
					}
				}
			}
		}
        return false;
	}

	@Override
	public int[] getHyperlinkSpan(Document dcmnt, int offset) {
		//throw new UnsupportedOperationException("Not supported yet.");
		int[] a = {startOffset, endOffset};
		return a;
	}

	@Override
	public void performClickAction(Document dcmnt, int offset) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}

}
