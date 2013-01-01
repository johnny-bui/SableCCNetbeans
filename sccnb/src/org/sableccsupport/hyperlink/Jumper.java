
package org.sableccsupport.hyperlink;

import javax.swing.text.Document;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.editor.StatusBar;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Source;
import org.openide.text.Line;
import org.sablecc.sablecc.Grammar;
import org.sableccsupport.lexer.SCCLexerTokenId;
import org.sableccsupport.parser.ast.GrammarStructure;
import org.sableccsupport.parser.ast.SCCOutlineParser;

/**
 *
 * @author phucluoi
 * @version Jan 1, 2013
 */
public class Jumper implements Runnable {
	private Document doc;
	private int offset = -1;
	private String identifier;

	public void assign(Document doc, int offset, String identifier){
		this.doc = doc;
		this.offset = offset;
		this.identifier = identifier;
	}
	
	@Override
	public void run() {
		// have nothing to do now
		if (doc == null || offset < 0) {
			return;
		}
		Snapshot sns = Source.create(doc).createSnapshot();
		TokenSequence<SCCLexerTokenId> ts = 
				sns.getTokenHierarchy().tokenSequence(SCCLexerTokenId.getLanguage());
		GrammarStructure structure = new SCCOutlineParser().scanStructure(ts);
		long targetOffset = structure.getOffsetFirstOccurence(identifier);
		// sequence, now allways jump to the first line
		if (targetOffset > 0){
	        Line line =  NbEditorUtilities.getLine(doc, (int)targetOffset,true);
    	    line.show(Line.ShowOpenType.OPEN, Line.ShowVisibilityType.FRONT);
		}else{
			System.out.println("Kann nicht springen!!");
		}
	}
}
