package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.GlobalOptions;
import edu.ksu.cis.util.graph.visualization.*;
import edu.ksu.cis.util.graph.core.*;
/*!
 * \file VertexColor.java
 * \author Jeffrey M. Barber
 */
public class VertexColor implements GraphOperator
{
	private Vertex	cVertex;
	int				oldColorIndex;
	int				newColorIndex;
	/*! Color a vertex
	 * \param[in] V the vertex
	 * \param[in] idx the new color
	 */
	public VertexColor(Vertex V, int idx)
	{
		cVertex = V;
		oldColorIndex = V.getAttrib(2);
		newColorIndex = idx;
	}
	/*! apply this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::apply(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph apply(Graph G)
	{
		cVertex.setAttrib(2, newColorIndex);
		return G;
	}
	/*! apply the inverse of this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::applyInverse(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph applyInverse(Graph G)
	{
		cVertex.setAttrib(2, oldColorIndex);
		return G;
	}
	/*! return the ideal number of frames this operator should encompase
	 * @see edu.ksu.cis.util.graph.visualization.GraphOperator#getTime()
	 */
	public int getTime()
	{
		return GlobalOptions.getInstance().getInteger("time_vertex_color", 25);
	}
}