package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.GlobalOptions;
import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.visualization.GraphOperator;
/*!
 * \file Delay.java
 * \author Jeffrey M. Barber
 */
public class Delay implements GraphOperator
{
	private int	_delay;
	/*! direct delay from d
	 * \param[in] d
	 */
	public Delay(int d)
	{
		_delay = d;
	}
	/*! delay by look up on str with default of def
	 * \param[in] str the key for use in GlobalOptions
	 * \param[in] def the delay time default
	 */
	public Delay(String str, int def)
	{
		_delay = GlobalOptions.getInstance().getInteger(str, def);
	}
	/*! apply this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::apply(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph apply(Graph G)
	{
		return G;
	}
	/*! apply the inverse of this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::applyInverse(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph applyInverse(Graph G)
	{
		return G;
	}
	/*! return the ideal number of frames this operator should encompase
	 * @see edu.ksu.cis.util.graph.visualization.GraphOperator#getTime()
	 */
	public int getTime()
	{
		return _delay;
	}
}