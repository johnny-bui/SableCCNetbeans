package org.sablecc.sablecc;

import de.htwsaarland.astVisual.graphRepresent.GraphContainer;
import de.htwsaarland.astVisual.graphRepresent.VertexType;
import de.htwsaarland.astVisual.graphVisual.AstGraphScene;
import de.htwsaarland.astVisual.testArchiv.SceneSupport;
import java.util.*;
import javax.swing.JComponent;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.Scene;
import org.sablecc.sablecc.analysis.DepthFirstAdapter;
import org.sablecc.sablecc.node.*;

/**
 *
 * @author verylazyboy
 */
public class AstDiagnoser extends DepthFirstAdapter
{

	protected String productName;
	//private boolean elementIdIsProd = true;
		
	private IdSpec idSpec = IdSpec.UN_SPEC;
	
	private HashMap<String,TId> tokenTable;
	protected Map<String,TId> productionTable;
	protected GraphContainer gc;
	
	private boolean regToken = false;
	private boolean grammarHasAst = false;		

	
	protected void setup()
	{
		// to configurate if also reg token
		throw new UnsupportedOperationException("TODO: implement it in future");
	}
	
	private int errorCount;

	public AstDiagnoser(final TokenRegister tokenReg)
	{
		tokenTable = tokenReg.getTokenTable();
		productionTable = tokenReg.getAstProductNameTable();
		gc = new GraphContainer();
	}
	
	@Override
	public void caseAGrammar(AGrammar node) 
	{
		//super.caseAGrammar(node);
		
		if(node.getAst() != null)
		{
			grammarHasAst = true;
			node.getAst().apply(this);
		}
		
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
		productName = node.getId().getText();
		if (gc.getRoot() == null)
		{
			gc.addRoot(productName);
			System.out.println("gc.addRoot(\"" + productName + "\");");
		}
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
		
		{
		  Object temp[] = node.getElems().toArray();
		  for(int i = 0; i < temp.length; i++)
		  {
			((PElem) temp[i]).apply(this);
		  }
		}
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
				//TODO: make a edge Product(productName) -> Token (elementId)
				//System.out.println(productName +  " -> " + "T." + elementId);
				addPT(productName, elementId);
				
			}else
			{
				//TODO: make a edge Product(productName) -> Product (elementId)
				//System.out.println(productName +  " -> " + "P." + elementId);
				addPPdep(productName, elementId);
			}
	    }else
		{
			TId token = tokenTable.get(elementId);
			TId astProd = productionTable.get(elementId);
			if (token == null)
			{
				if (astProd == null)
				{
					// No token and no production -> Error
					errorHandling("["+id.getLine() +":" + id.getPos() + "] " 
							+"Production >>" + id.getText() + "<< and token >>" 
							+ id.getText() 
							+ "<< undefined. If it is a production, "
							+ "it should be defined in AST section.");
				}else
				{
					//TODO: No Token and Production found -> print a edge from production to produciton
					//System.out.println(productName +  " -> " + "P." + elementId);
					addPPdep(productName, elementId);
				}
			}else
			{
				if (astProd == null)
				{
					// TODO: No Production and Token found -> print a edge from production to Token
					// System.out.println(productName +  " -> " + "T." + elementId);
					addPT(productName, elementId);
				}else
				{
					// Found Token and Production -> print error
					errorHandling("[" + id.getLine() + ":" + id.getPos() + "] "
							+ "Ambiguous production and token: >>" 
							+ id.getText() 
							+ "<<, production at ["
							+ astProd.getLine() + ":" + astProd.getPos() 
							+"], token at ["
							+token.getLine()+":"+token.getPos()
							+ "]"
							);
				}
			}
		}
	}


	protected void addPPdep(String parent, String child)
	{
		gc.addDepend(parent, child);
		//System.out.println("gc.addDepend(\"" + parent + "\", \"" + child + "\");");	
	}
	
	protected void addPT(String parent, String child)
	{
		if (regToken)
		{
			gc.addDepend(parent, VertexType.PROD, child, VertexType.TOKEN);
			//System.out.println("gc.addDepend(\"" +parent 
			//				+ "\", VertexType.PROD, \"" + child + "\", VertexType.TOKEN);");	
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

	
	protected String getNameOfNode(Node node)
	{
		String name = node.getClass().getName();
		if (name.lastIndexOf('.') > 0) {
		    name = name.substring(name.lastIndexOf('.')+1);
		}
		return name;
	}

	protected void errorHandling(String msg)
	{
		errorCount = errorCount +1;
		System.err.print(msg);
	}

	public int getError(){return errorCount;}

	/** TODO: move it to somewhere else here. It just cause bugs */
	public JComponent getAstView ()
	{
		gc.performDFS();
		System.out.println(gc);
		
		AstGraphScene ags = new AstGraphScene();
		ags.setup(true);
		ags.portGraph(gc);
		ags.setLayout();

		Scene s = ags.getScene();
		s.getActions ().addAction (ActionFactory.createMouseCenteredZoomAction (1.1));
		s.getActions ().addAction (ActionFactory.createWheelPanAction());
		//SceneSupport.show(s);
		return s.createView();
	}


	public boolean hasAST()
	{
		return this.grammarHasAst;
	}
}


