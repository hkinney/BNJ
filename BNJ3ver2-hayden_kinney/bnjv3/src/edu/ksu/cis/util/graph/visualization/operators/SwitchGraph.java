package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.GlobalOptions;
import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.visualization.GraphOperator;
/*!
 * \file SwitchGraph.java
 * \author Jeffrey M. Barber
 */
public class SwitchGraph implements GraphOperator
{
	Graph	_original;
	Graph	_new;
	/*! switch graphs
	 * \param[in] orig original graph
	 * \param[in] thenew new graph
	 */
	public SwitchGraph(Graph orig, Graph thenew)
	{
		_original = orig;
		_new = thenew;
	}
	/*! apply this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::apply(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph apply(Graph G)
	{
		return _new;
	}
	/*! apply the inverse of this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::applyInverse(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph applyInverse(Graph G)
	{
		return _original;
	}
	/*! return the ideal number of frames this operator should encompase
	 * @see edu.ksu.cis.util.graph.visualization.GraphOperator#getTime()
	 */
	public int getTime()
	{
		return GlobalOptions.getInstance().getInteger("time_switch_graph", 10);
	}
}