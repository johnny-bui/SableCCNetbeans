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
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.text.Line;
import org.openide.util.Exceptions;
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
	//private String identifier;
	private int lineNumber;

	@Override
	public Set<HyperlinkType> getSupportedHyperlinkTypes() {
		return EnumSet.of(HyperlinkType.GO_TO_DECLARATION);
	}

	@Override
	public boolean isHyperlinkPoint(Document dcmnt, int offset, HyperlinkType type) {
		DeterminateHyperlink dhl = new DeterminateHyperlink();
		getHyperlinkTarget(dcmnt, offset, dhl);
		return dhl.isHyperLink();
	}

	@Override
	public int[] getHyperlinkSpan(Document dcmnt, int offset, HyperlinkType type) {
		CalculateHyperlinkSpan chs = new CalculateHyperlinkSpan();
		getHyperlinkTarget(dcmnt, offset, chs);
		return chs.getSpan();
		//return getHyperlinkSpan(dcmnt, offset);
	}

	@Override
	public void performClickAction(Document doc, int offset, HyperlinkType type) {
		try {
			if (doc == null || offset < 0) {
				return;
			}
			CalculateHyperlinkTarget cht = new CalculateHyperlinkTarget();
			getHyperlinkTarget(doc, offset, cht);
			long targetOffset = cht.getTargetOffset(doc);
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


	private void getHyperlinkTarget(Document doc, int offset, PrepairAction p) {
		//String __identifier = null;
		TokenHierarchy hi = TokenHierarchy.get(doc);
		TokenSequence<SCCLexerTokenId> ts = hi.tokenSequence(SCCLexerTokenId.getLanguage());

		ts.move(offset);
		if (ts.moveNext()) {
			Token<SCCLexerTokenId> testToken = ts.token();
			startOffset = ts.offset();
			endOffset = startOffset + testToken.text().length();
			if (testToken.id() == SCCLexerTokenId.ID) {
				Token<SCCLexerTokenId> nextToken = 
						SCCOutlineParser.getNextToken(ts, startOffset, offset);
				System.out.println("++++++++ nextToken is >>"
									+ nextToken
									+ "<< +++++++++");
				if (nextToken != null) {
					if (!SCCOutlineParser.isOneOf(nextToken.id(),
							SCCLexerTokenId.EQUAL,
							SCCLexerTokenId.R_BRACE,
							SCCLexerTokenId.R_BKT,
							SCCLexerTokenId.L_BRACE)) {
						//int[] span = {startOffset, endOffset};
						//identifier = testToken.text().toString();
						p.setHyperlinkInfo(ts, startOffset, endOffset, testToken);
						return;
					}
					// case of EQUAL is obviously a define of a 
					// helper/token/production so it is never a h<perlink.
					
					// case of R_BRACE
					if (nextToken.id() == SCCLexerTokenId.R_BRACE) {
						Token<SCCLexerTokenId> previousToken =
								SCCOutlineParser.getPreviousToken(
								ts,
								startOffset,
								startOffset );
						if (previousToken.id() != SCCLexerTokenId.L_BRACE) {
							//int[] span = {startOffset, endOffset};
							//identifier = testToken.text().toString();
							p.setHyperlinkInfo(ts, startOffset, endOffset, testToken);
							return;
						} else {
							Token<SCCLexerTokenId> secondPreviousToken = SCCOutlineParser.getPreviousToken(
									ts,
									startOffset,
									startOffset - previousToken.text().length());
							if (secondPreviousToken != null) {
								if (!SCCOutlineParser.isOneOf(secondPreviousToken.id(),
										SCCLexerTokenId.BAR,
										SCCLexerTokenId.EQUAL)) {
									//int[] span = {startOffset, endOffset};
									//identifier = testToken.text().toString();
									p.setHyperlinkInfo(ts, startOffset, endOffset, testToken);
									return;
								}
							}
						}

					}
					// case of R_BKT
					if (nextToken.id() == SCCLexerTokenId.R_BKT) {
						Token<SCCLexerTokenId> secondNextToken =
								SCCOutlineParser.getNextToken(
								ts,
								startOffset,
								endOffset);
						if (secondNextToken.id() != SCCLexerTokenId.COLON) {
							//int[] span = {startOffset, endOffset};
							//__identifier = testToken.text().toString();
							p.setHyperlinkInfo(ts, startOffset, endOffset, testToken);
							return;
						}
					}
					// case of L_BRACE
					if (nextToken.id() == SCCLexerTokenId.L_BRACE){
						Token<SCCLexerTokenId> previousToken = 
								SCCOutlineParser.getPreviousToken(ts, 
								startOffset, 
								startOffset);
						if (previousToken.id() != SCCLexerTokenId.SEMICOLON) {
							// if not the case of definition a production with a transform rule
							p.setHyperlinkInfo(ts, startOffset, endOffset, testToken);
							return;
						}
					}
				}
			}
		}
		p.setVoid();
		return;
	}

	/**
	 * static prevents to accidently access to any member of this class
	 */
	private static interface PrepairAction {

		void setHyperlinkInfo(
				final TokenSequence<SCCLexerTokenId> ts,
				final long startOffset,
				final long endOffset,
				final Token<SCCLexerTokenId> id);

		void setVoid();
	}

	private static final class DeterminateHyperlink implements PrepairAction {

		private boolean isHyperLink = false;

		@Override
		public void setHyperlinkInfo(
				TokenSequence<SCCLexerTokenId> ts,
				long startOffset,
				long endOffset,
				Token<SCCLexerTokenId> id) {
			this.isHyperLink = true;
		}

		@Override
		public void setVoid() {
		}

		private boolean isHyperLink() {
			return isHyperLink;
		}
	}

	private static final class CalculateHyperlinkSpan implements PrepairAction {

		int[] span = {-1, -1};

		@Override
		public void setHyperlinkInfo(
				TokenSequence<SCCLexerTokenId> ts,
				final long startOffset,
				final long endOffset,
				final Token<SCCLexerTokenId> id) {
			span[0] = (int) startOffset;
			span[1] = (int) endOffset;
		}

		@Override
		public void setVoid() {
		}

		private int[] getSpan() {
			return span[0] < 0 ? null : span;
		}
	}

	private static final class CalculateHyperlinkTarget implements PrepairAction {
		private String identifier;

		@Override
		public void setHyperlinkInfo(
				final TokenSequence<SCCLexerTokenId> ts,
				final long startOffset,
				final long endOffset,
				final Token<SCCLexerTokenId> id) {
			identifier = id.text().toString();
		}

		@Override
		public void setVoid() {
		}

		private long getTargetOffset(final Document doc) {
			TokenHierarchy hi = TokenHierarchy.get(doc);
			TokenSequence<SCCLexerTokenId> ts = hi.tokenSequence(SCCLexerTokenId.getLanguage());
			GrammarStructure structure = new SCCOutlineParser().scanStructure(ts);
			long targetOffset = structure.getOffsetFirstOccurence(identifier);
			return targetOffset;
		}
	}
}
