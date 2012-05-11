package org.sablecc.sablecc;

import java.util.HashMap;
import org.sablecc.sablecc.analysis.DepthFirstAdapter;
import org.sablecc.sablecc.node.*;

/**
 *
 * @author hbui
 * @version May 10. 2012
 * 		initial 
 * 
 */
public class TokenRegister  extends DepthFirstAdapter
{
	HashMap<String,TId> tokenReg ;
	HashMap<String,TId> productionReg;
	HashMap<String,TId> astProductionReg;
	int errorCount = 0;
	
	public TokenRegister()
	{
		tokenReg         = new HashMap<String,TId>();
		productionReg    = new HashMap<String, TId>();
		astProductionReg = new HashMap<String, TId>();
	}
	
	@Override
	public void caseAGrammar(AGrammar node) {
		//super.caseAGrammar(node);
		if (node.getTokens() != null)
		{
			node.getTokens().apply(this);
		}
		if (node.getProductions() != null )
		{
			node.getProductions().apply(this);
		}
		if (node.getAst() != null)
		{
			node.getAst().apply(this);
		}
	}

	@Override
	public void caseATokenDef(ATokenDef node) {
		//super.caseATokenDef(node);
		TId token = node.getId();
		
		if (tokenReg.containsKey(token.getText()))
		{
			TId oldToken = tokenReg.get(token.getText());
			errorHandling(
				"[" + token.getLine() + ":"+token.getPos() + "] Redefinition of token >>" + token.getText() + "<< (["
					+ oldToken.getLine() + ":" + oldToken.getPos() + "])");
		}else
		{
			tokenReg.put(token.getText(), token);
		}
	}

	@Override
	public void caseAProd(AProd node) 
	{
		TId token = node.getId();
		
		if (productionReg.containsKey(token.getText()))
		{
			TId oldToken = productionReg.get(token.getText());
			errorHandling(
				"[" + token.getLine() + ":"+token.getPos() + "] Redefinition of production >>" + token.getText() + "<< (["
					+ oldToken.getLine() + ":" + oldToken.getPos() + "])");
		}else
		{
			productionReg.put(token.getText(), token);
		}
	}

	
	@Override
	public void caseAAstProd(AAstProd node) {
		TId token = node.getId();
		
		if (astProductionReg.containsKey(token.getText()))
		{
			TId oldToken = astProductionReg.get(token.getText());
			errorHandling(
				"[" + token.getLine() + ":"+token.getPos() + "] Redefinition of ast production >>" + token.getText() + "<< (["
					+ oldToken.getLine() + ":" + oldToken.getPos() + "])");
		}else
		{
			astProductionReg.put(token.getText(), token);
		}
	}
	
	
	public HashMap<String,TId> getAstProductNameTable()
	{
		return this.astProductionReg;
	}
	
	public HashMap<String,TId> getTokenTable ()
	{
		return this.tokenReg;
	}

	public HashMap<String,TId> getProductionNameTable()
	{
		return this.productionReg;
	}

	
	private void errorHandling(String msg)
	{
		errorCount = errorCount +1;
		System.err.print(msg);
	}

	public int getError(){return errorCount;}
}
