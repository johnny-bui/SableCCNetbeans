package org.sablecc.sablecc;

import org.sablecc.sablecc.analysis.DepthFirstAdapter;
import org.sablecc.sablecc.node.Node;

/**
 *
 * @author hbui
 */
public class GrammarAnalyzer extends DepthFirstAdapter
{

	int depth = 0;
	public void setup(int depth)
	{
		this.depth = depth;
	}
	
	@Override
	public void defaultIn(Node node) 
	{	
		for (int i = 0; i < depth; ++i)
		{
			System.out.print("    ");
		}	
		System.out.println("-> " + node.getClass().getCanonicalName());
		depth = depth+1;
	}

	@Override
	public void defaultOut(Node node) 
	{	
		depth = depth-1;
		for (int i = 0; i < depth; ++i)
		{
			System.out.print("    ");
		}	
		System.out.println("<- " + node.getClass().getCanonicalName());
	}
		
}
