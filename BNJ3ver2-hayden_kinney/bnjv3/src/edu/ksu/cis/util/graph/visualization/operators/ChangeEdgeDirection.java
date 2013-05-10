package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.GlobalOptions;
import edu.ksu.cis.util.graph.visualization.*;
import edu.ksu.cis.util.graph.core.*;
/*!
 * \file ChangeEdgeDirection.java
 * \author Jeffrey M. Barber
 */
public class ChangeEdgeDirection implements GraphOperator
{
	private Edge	edgeOld;
	private Edge	edgeNew;
	/*! the edge, and the new direction
	 * \param E the edge
	 * \param newDir the new direction
	 */
	public ChangeEdgeDirection(Edge E, boolean newDir)
	{
		edgeOld = E;
		edgeNew = E.copy();
		edgeNew.setDirected(newDir);
	}
	/*! apply this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::apply(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph apply(Graph G)
	{
		G.removeEdge(edgeOld);
		G.addEdge(edgeNew);
		return G;
	}
	/*! apply the inverse of this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::applyInverse(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph applyInverse(Graph G)
	{
		G.removeEdge(edgeNew);
		G.addEdge(edgeOld);
		return G;
	}
	/*! return the ideal number of frames this operator should encompase
	 * @see edu.ksu.cis.util.graph.visualization.GraphOperator#getTime()
	 */
	public int getTime()
	{
		return GlobalOptions.getInstance().getInteger("time_change_direction", 5);
	}
}