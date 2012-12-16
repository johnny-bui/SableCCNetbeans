package org.sableccsupport.parser;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.sableccsupport.lexer.SCCLexerTokenId;

/**
 *
 * @author phucluoi
 */
public class SCCParser extends Parser {

	private Snapshot snapshot;
	private SCCParserWrapper pw;
	private boolean cancelled = false;
	
	@Override
	public void parse(
			Snapshot snapshot, Task task,SourceModificationEvent event)
	{
		this.snapshot = snapshot;
		pw = new SCCParserWrapper(snapshot);
		try{
			pw.checkSyntaxErr();
		}catch(Exception ex)
		{
			Logger.getLogger (Parser.class.getName()).log (Level.WARNING, null, ex);
		}
		/*
		TokenSequence<SCCLexerTokenId> ts = 
				snapshot.getTokenHierarchy().tokenSequence(SCCLexerTokenId.getLanguage());
		if (ts != null){
			if (!ts.isEmpty()){
				while (ts.moveNext()){
					Token<SCCLexerTokenId> token = ts.token();
					if (token != null){
						System.out.println(">>>>>>>>>>>>>>> Token:" + token.text() + "<<<<<<<<<<<<<<<<<<");
						System.out.println(">>>>>>>>>>>>>> Offset:" + ts.offset() + "<<<<<<<<<<<<<<<<<<");
					}else{
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + null + "<<<<<<<<<<<<<<<<<<");
					}
				}
			}
		}*/
	}

	@Override
	public SCCParserResult getResult(Task task)
	{
		return new SCCParserResult(snapshot, pw);
	}

	@Deprecated
	@Override
	public void cancel()
	{
		cancelled = true;
	}

	@Override
	public void addChangeListener(ChangeListener changeListener) {
	}

	@Override
	public void removeChangeListener(ChangeListener changeListener) {
	}
}
