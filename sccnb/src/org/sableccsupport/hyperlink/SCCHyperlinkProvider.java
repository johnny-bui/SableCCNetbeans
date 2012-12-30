/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sableccsupport.hyperlink;

import java.util.EnumSet;
import java.util.Set;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProvider;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProviderExt;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkType;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.cookies.LineCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.text.Line;
import org.openide.util.Exceptions;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.parser.ast.SCCOutlineParser;

/**
 *
 * @author phucluoi
 * @version Dec 30, 2012
 */
@MimeRegistration(mimeType = "text/x-sablecc", service = HyperlinkProviderExt.class)
public class SCCHyperlinkProvider implements HyperlinkProviderExt {

	private int startOffset;
	private int endOffset;
	private String identifier;
	private int lineNumber;
	
	@Override
    public Set<HyperlinkType> getSupportedHyperlinkTypes() {
        return EnumSet.of(HyperlinkType.GO_TO_DECLARATION);
    }
	
	@Override
	public boolean isHyperlinkPoint(Document dcmnt, int offset, HyperlinkType type) {
		return getHyperlinkSpan(dcmnt, offset) != null;
	}
	
	@Override
	public int[] getHyperlinkSpan(Document dcmnt, int offset, HyperlinkType type) {
		return getHyperlinkSpan(dcmnt, offset);
	}

	@Override
	public void performClickAction(Document doc, int offset, HyperlinkType type) {
		//TODO delegiert es in eine thread
		try {
            int targetOffset = 1; //TODO: obtain the value from token
			// sequence, now allways jump to the first line
            Line line =  NbEditorUtilities.getLine(doc, targetOffset,true);
            line.show(Line.ShowOpenType.OPEN, Line.ShowVisibilityType.FRONT);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
	}
	
	@Override
    public String getTooltipText(Document doc, int offset, HyperlinkType type) {
        String text = null;
        try {
            text = doc.getText(startOffset, endOffset - startOffset);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
        return "Click to jump to declaration of: " + text;
    }
	
	private int[] getHyperlinkSpan(Document doc, int offset) {

		TokenHierarchy hi = TokenHierarchy.get(doc);
		TokenSequence<SCCLexerTokenId> ts = hi.tokenSequence(SCCLexerTokenId.getLanguage());

		ts.move(offset);
		if (ts.moveNext()) {
			Token<SCCLexerTokenId> testToken = ts.token();
			startOffset = ts.offset();
			if (testToken.id() == SCCLexerTokenId.ID) {
				Token<SCCLexerTokenId> nextToken = SCCOutlineParser.getNextToken(ts, offset);
				if (!SCCOutlineParser.isOneOf(nextToken.id(),
						SCCLexerTokenId.EQUAL,
						SCCLexerTokenId.R_BKT)) {
					endOffset = startOffset + testToken.text().length();
					int[] span = {startOffset, endOffset};
					return span;
				}
				if (nextToken.id() == SCCLexerTokenId.R_BRACE) {
					Token<SCCLexerTokenId> previousToken = SCCOutlineParser.getPreviousToken(ts, offset);
					if (previousToken.id() == SCCLexerTokenId.ARROW) {
						endOffset = startOffset + testToken.text().length();
						int[] span = {startOffset, endOffset};
						return span;
					}
				}
			}
		}
		return null;
	}

	private static FileObject getFileObject(Document doc) {
        DataObject od = (DataObject) doc.getProperty(Document.StreamDescriptionProperty);
        return od != null ? od.getPrimaryFile() : null;
    }
	
}
