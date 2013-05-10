package edu.ksu.cis.util.graph.visualization.operators;
import java.util.ArrayList;
import java.util.Iterator;
import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.visualization.GraphOperator;
/*!
 * \file GraphBatch.java
 * \author Jeffrey M. Barber
 */
public class GraphBatch implements GraphOperator
{
	private ArrayList	_ops;
	private int			time;
	/*! batch up multiple graph operators 
	 */
	public GraphBatch()
	{
		_ops = new ArrayList();
		time = 0;
	}
	/*! add a graph operator
	 * \param o
	 */
	public void add(GraphOperator o)
	{
		time += o.getTime();
		_ops.add(o);
	}
	/*! apply this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::apply(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph apply(Graph G)
	{
		Graph I = G;
		for (Iterator it = _ops.iterator(); it.hasNext();)
		{
			GraphOperator o = (GraphOperator) (it.next());
			I = o.apply(I);
		}
		return I;
	}
	/*! apply the inverse of this graph operator
	 * \see edu.ksu.cis.util.graph.visualization.GraphOperator::applyInverse(edu.ksu.cis.util.graph.core.Graph)
	 */
	public Graph applyInverse(Graph G)
	{
		Graph I = G;
		for (int k = _ops.size() - 1; k >= 0; k--)
		{
			GraphOperator o = (GraphOperator) _ops.get(k);
			I = o.applyInverse(I);
		}
		return I;
	}
	/*! return the ideal number of frames this operator should encompase
	 * @see edu.ksu.cis.util.graph.visualization.GraphOperator#getTime()
	 */
	public int getTime()
	{
		return time;
	}
}