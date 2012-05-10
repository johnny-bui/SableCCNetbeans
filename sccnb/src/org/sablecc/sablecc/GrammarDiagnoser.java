package org.sablecc.sablecc;

import java.util.*;
import org.sablecc.sablecc.analysis.DepthFirstAdapter;
import org.sablecc.sablecc.node.*;

/**
 *
 * @author hbui
 */
public class GrammarDiagnoser extends DepthFirstAdapter
{

	int depth = 0;
	private String productName;
	//private boolean elementIdIsProd = true;
		
	private IdSpec idSpec = IdSpec.UN_SPEC;
	
	private HashMap<String,TId> tokenTable;
	private HashMap<String,TId> productTable;
	private HashMap<String,TId> astProductTable;
	
	private boolean isProcessingAst = false;
	private int errorCount;
	private TokenRegister tokenReg;

	@Override
	public void caseAGrammar(AGrammar node) 
	{
		//super.caseAGrammar(node);
		tokenReg = new TokenRegister();
		node.apply(tokenReg);
		tokenTable = tokenReg.getTokenTable();
		productTable = tokenReg.getProductionNameTable();
		astProductTable = tokenReg.getAstProductNameTable();
		
		if(node.getAst() != null)
		{
			node.getAst().apply(this);
		}
		
	}

	@Override
	public void inAAst(AAst node) {
		//super.inAAst(node);
		this.isProcessingAst = true;
	}

	@Override
	public void outAAst(AAst node) {
		//super.outAAst(node);
		this.isProcessingAst = false;
	}

		
	
	@Override
	public void caseAAst(AAst node) 
	{
		inAAst(node);
		LinkedList l = node.getProds();
		for (int i = 0; i< l.size(); ++i)
		{
			 ((PAstProd) l.get(i)).apply(this);
		}
		outAAst(node);
	}

	@Override
	public void caseAAstProd(AAstProd node) 
	{
		//inAAstProd(node);
		productName = node.getId().getText();
		// TODO: make a production Node hier
		/*
		if(node.getId() != null)
		{
		  node.getId().apply(this);
		}
		*/
		
		{
		  Object temp[] = node.getAlts().toArray();
		  for(int i = 0; i < temp.length; i++)
		  {
			((PAstAlt) temp[i]).apply(this);
		  }
		}
		outAAstProd(node);
	}

	@Override
	public void caseAAstAlt(AAstAlt node) 
	{
		//super.caseAAstAlt(node);
		//inAAstAlt(node);
		//if(node.getAltName() != null)
		//{
		//  node.getAltName().apply(this);
		//}
		{
		  Object temp[] = node.getElems().toArray();
		  for(int i = 0; i < temp.length; i++)
		  {
			((PElem) temp[i]).apply(this);
		  }
		}
		//outAAstAlt(node);
	}

	@Override
	public void caseAElem(AElem node) 
	{
		inAElem(node);
		TId id = node.getId();
		String elementId = node.getId().getText();
		if(node.getSpecifier() != null)
    	{
      		node.getSpecifier().apply(this);
			if (idSpec == IdSpec.TOKEN)
			{
				System.out.println(productName +  " -> " + "T." + elementId);
			}else
			{
				System.out.println(productName +  " -> " + "P." + elementId);
			}
	    }else
		{
			TId token = tokenTable.get(elementId);
			TId astProd = astProductTable.get(elementId);
			if (token == null)
			{
				if (astProd == null)
				{
					// No token and no production -> Error
					errorHandling("["+id.getLine() +":" + id.getPos() + "] " 
							+"Production >>" + id.getText() + "<< and token >>" 
							+ id.getText() + "<< undefined. If it is a production, It should be defined in AST section");
				}else
				{
					// No Token and Production found -> print a edge from production to produciton
					System.out.println(productName +  " -> " + "P." + elementId);
				}
			}else
			{
				if (astProd == null)
				{
					// No Production and Token found -> print a edge from production to Token
					System.out.println(productName +  " -> " + "T." + elementId);
				}else
				{
					// Found Token and Production -> print error
					errorHandling("[" + id.getLine() + ":" + id.getPos() + "] "
							+ "Ambiguous production and token: >>" + id.getText() + "<< ["+token.getLine()+":"+token.getPos()+"]");
				}
			}
		}
	}

	@Override
	public void caseATokenSpecifier(ATokenSpecifier node) 
	{
		this.idSpec = IdSpec.TOKEN;
	}

	@Override
	public void caseAProductionSpecifier(AProductionSpecifier node) 
	{
		this.idSpec = IdSpec.PROCDUCTION;
	}

	@Override
	public void inAElem(AElem node) {
		idSpec = IdSpec.UN_SPEC;
	}

	@Override
	public void outAElem(AElem node) {
		idSpec = IdSpec.UN_SPEC;
	}

	
	private String getNameOfNode(Node node)
	{
		String name = node.getClass().getName();
		if (name.lastIndexOf('.') > 0) {
		    name = name.substring(name.lastIndexOf('.')+1);
		}
		return name;
	}

	private void errorHandling(String msg)
	{
		errorCount = errorCount +1;
		System.err.print(msg);
	}

	public int getError(){return errorCount;}

	/*helper method to get error in highter layer*/
	public TokenRegister getTokenRegister()
	{
		return tokenReg;
	}
}


