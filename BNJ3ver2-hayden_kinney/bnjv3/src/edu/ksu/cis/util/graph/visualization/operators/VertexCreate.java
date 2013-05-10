package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.GlobalOptions;
import edu.ksu.cis.util.graph.visualization.*;
import edu.ksu.cis.util.graph.core.*;
/*!
 * \file VertexCreate.java
 * \author Jeffrey M. Barber
 */
public class VertexCreate implements GraphOperator
{
	private Vertex	newVertex;
	/*! create a new vertex
	 * \param nv the new vertex 
	 */
	public VertexCreate(Vertex nv)
	{
		newVertex = nv;
	}
	/*! apply this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::apply(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph apply(Graph G)
	{
		G.addVertex(newVertex);
		return G;
	}
	/*! apply the inverse of this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::applyInverse(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph applyInverse(Graph G)
	{
		G.removeVertex(newVertex);
		return G;
	}
	/*! return the ideal number of frames this operator should encompase
	 * @see edu.ksu.cis.util.graph.visualization.GraphOperator#getTime()
	 */
	public int getTime()
	{
		return GlobalOptions.getInstance().getInteger("time_vertex_create", 100);
	}
}