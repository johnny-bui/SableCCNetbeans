/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sableccsupport.hyperlink;

import java.util.EnumSet;
import java.util.Set;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProviderExt;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkType;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Source;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.text.Line;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.parser.ast.GrammarStructure;
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
		try {
			
			if (doc == null || offset < 0) {
				return;
			}
			Snapshot sns = Source.create(doc).createSnapshot();
			TokenSequence<SCCLexerTokenId> ts =
					sns.getTokenHierarchy().tokenSequence(SCCLexerTokenId.getLanguage());
			GrammarStructure structure = new SCCOutlineParser().scanStructure(ts);
			long targetOffset = structure.getOffsetFirstOccurence(identifier);
			// sequence, now allways jump to the first line
			if (targetOffset > 0) {
				Line line = NbEditorUtilities.getLine(doc, (int) targetOffset, true);
				line.show(Line.ShowOpenType.OPEN, Line.ShowVisibilityType.FRONT);
			} else {
				// TODO : write to status bar
				System.out.println("Kann nicht springen!!");
			}
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
			endOffset = startOffset + testToken.text().length();
			if (testToken.id() == SCCLexerTokenId.ID) {
				Token<SCCLexerTokenId> nextToken = SCCOutlineParser.getNextToken(ts, startOffset, offset);
				if (nextToken != null) {
					if (!SCCOutlineParser.isOneOf(nextToken.id(),
							SCCLexerTokenId.EQUAL,
							SCCLexerTokenId.R_BRACE,
							SCCLexerTokenId.R_BKT)) {
						int[] span = {startOffset, endOffset};
						identifier = testToken.text().toString();
						return span;
					}
					if (nextToken.id() == SCCLexerTokenId.R_BRACE) {
						Token<SCCLexerTokenId> previousToken = 
								SCCOutlineParser.getPreviousToken(
								ts, 
								offset, 
								offset);
						if (previousToken.id() != SCCLexerTokenId.L_BRACE) {
							int[] span = {startOffset, endOffset};
							identifier = testToken.text().toString();
							return span;
						} else {
							Token<SCCLexerTokenId> secondPreviousToken = SCCOutlineParser.getPreviousToken(
									ts,
									startOffset,
									startOffset - previousToken.text().length() );
							System.out.println("++++++++ secondPreviousToken is >>"+
									secondPreviousToken +
									"<< +++++++++");
							if (secondPreviousToken != null) {
								if (!SCCOutlineParser.isOneOf(secondPreviousToken.id(),
										SCCLexerTokenId.BAR,
										SCCLexerTokenId.EQUAL)) {
									int[] span = {startOffset, endOffset};
									identifier = testToken.text().toString();
									return span;
								}
							}
						}

					}
					if (nextToken.id() == SCCLexerTokenId.R_BKT) {
						Token<SCCLexerTokenId> secondNextToken =
								SCCOutlineParser.getNextToken(
								ts,
								startOffset,
								endOffset);
						if (secondNextToken.id() != SCCLexerTokenId.COLON) {
							int[] span = {startOffset, endOffset};
							identifier = testToken.text().toString();
							return span;
						}
					}
				}
			}
		}
		identifier = null;
		return null;
	}

	private static FileObject getFileObject(Document doc) {
		DataObject od = (DataObject) doc.getProperty(Document.StreamDescriptionProperty);
		return od != null ? od.getPrimaryFile() : null;
	}
}
