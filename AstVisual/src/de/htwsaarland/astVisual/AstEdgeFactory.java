package de.htwsaarland.astVisual;

import java.io.Serializable;
import org.jgrapht.EdgeFactory;

/**
 *
 * @author phucluoi
 * @version 30.04.2012
 */
public class AstEdgeFactory<V extends AstVertex, E extends AstEdge>
    implements EdgeFactory<V, E>,
        Serializable
{

    //~ Instance fields --------------------------------------------------------

    private final Class<? extends E> edgeClass;

    //~ Constructors -----------------------------------------------------------

    public AstEdgeFactory(Class<? extends E> edgeClass)
    {
        this.edgeClass = edgeClass;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * @see EdgeFactory#createEdge(Object, Object)
     */
	@Override
    public E createEdge(V source, V target)
    {
        try {
			E edge = edgeClass.newInstance();
			edge.setNodes(source, target);
			return edge;
        } catch (Exception ex) {
            throw new RuntimeException("Edge factory failed", ex);
        }
    }
}


