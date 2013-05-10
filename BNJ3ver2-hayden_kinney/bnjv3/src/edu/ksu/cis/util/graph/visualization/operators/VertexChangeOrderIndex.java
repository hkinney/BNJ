package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.GlobalOptions;
import edu.ksu.cis.util.graph.visualization.*;
import edu.ksu.cis.util.graph.core.*;
/*!
 * \file VertexChangeOrderIndex.java
 * \author Jeffrey M. Barber
 */
public class VertexChangeOrderIndex implements GraphOperator
{
	private Vertex	cVertex;
	int				oldAttrib;
	int				newAttrib;
	/*! Change the order (attribute 3) of a vertex
	 * \param V  the vertex
	 * \param idx the new order
	 */
	public VertexChangeOrderIndex(Vertex V, int idx)
	{
		cVertex = V;
		oldAttrib = V.getAttrib(3);
		newAttrib = idx;
	}
	/*! apply this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::apply(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph apply(Graph G)
	{
		cVertex.setAttrib(3, newAttrib);
		return G;
	}
	/*! apply the inverse of this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::applyInverse(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph applyInverse(Graph G)
	{
		cVertex.setAttrib(3, oldAttrib);
		return G;
	}
	/*! return the ideal number of frames this operator should encompase
	 * @see edu.ksu.cis.util.graph.visualization.GraphOperator#getTime()
	 */
	public int getTime()
	{
		return GlobalOptions.getInstance().getInteger("time_vertex_change_order_index", 50);
	}
}