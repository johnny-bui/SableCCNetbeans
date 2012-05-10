package de.htwsaarland.astVisual.graphRepresent;


import java.util.Stack;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;

public class DFMarkerListener<V extends AstVertex,E extends AstEdge> 
	extends TraversalListenerAdapter<V, E>
{
	int counter ;
	Stack<AstVertex> visit;
	public DFMarkerListener()
	{
		counter = 1;
		visit = new Stack<AstVertex>();
	}
	
	@Override
	public void  vertexTraversed(VertexTraversalEvent<V> e) 
	{
		V v = e.getVertex();
		v.setDetected(counter);
		counter ++;
		if (counter > 2)
		{
			v.setPred(visit.peek());
		}
		visit.push(v);
		
		//System.out.println("visit:  " + v);
		//System.out.println("stack: " + visit);
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<V> e)
	{
		V v = e.getVertex();
		v.setFinished(counter);
		counter ++;
		visit.pop();
		
		//System.out.println("exit:  " + v);
		//System.out.println("stack: " + visit);
		//DepthFirstIterator dfi = (DepthFirstIterator)e.getSource();
	}
}
