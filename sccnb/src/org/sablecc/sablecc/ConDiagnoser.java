package org.sablecc.sablecc;

import java.util.*;
import org.sablecc.sablecc.analysis.DepthFirstAdapter;
import org.sablecc.sablecc.node.*;

/**
 *
 * @author hbui
 */
public class ConDiagnoser extends AstDiagnoser
{

	public ConDiagnoser(final TokenRegister tokenReg)
	{
		super(tokenReg);
		productionTable = tokenReg.getProductionNameTable();
	}
	
	@Override
	public void caseAGrammar(AGrammar node) 
	{
		
		if (node.getProductions() != null)
		{
			node.getProductions().apply(this);
		}
		
	}

	

	@Override
	public void caseAProd(AProd node) 
	{
		productName = node.getId().getText();
		{
			Object temp[] = node.getAlts().toArray();
			for(int i = 0; i < temp.length; i++)
			{
				((PAlt) temp[i]).apply(this);
			}
		}
	}
	
	

	@Override
	public void caseAAlt(AAlt node) {
		{
			Object temp[] = node.getElems().toArray();
			for(int i = 0; i < temp.length; i++)
			{
				((PElem) temp[i]).apply(this);
			}
		}
	}

}


