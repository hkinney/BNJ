package edu.ksu.cis.util.graph.visualization;
import edu.ksu.cis.util.graph.core.*;
/*!
 * \file Algorithm.java
 * \author Jeffrey M. Barber
 */
public abstract class Algorithm
{
	private VisualizationController	_VisualControl	= null;
	protected Graph					_Graph			= null;
	protected String				_Name;
	/*! set the visualization controller for this algorithm
	 * @param VC the visualization controller
	 */
	public void setVisualization(VisualizationController VC)
	{
		_VisualControl = VC;
	}
	/*! can this graph visualize
	 * @return
	 */
	public boolean canVisualize()
	{
		return _VisualControl != null;
	}
	/*! query the visualization controller 
	 * @return
	 */
	public VisualizationController VC()
	{
		return _VisualControl;
	}
	/*! set the graph
	 * \param[in] G
	 */
	public void setGraph(Graph G)
	{
		_Graph = G;
	}
	/*! return this graph
	 * \return
	 */
	public Graph getGraph()
	{
		return _Graph;
	}
	/*! Execute the algorithm
	 */
	public abstract void execute();
	/*! execute the algorithm with graph G
	 * \param[in] G
	 */
	public void execute(Graph G)
	{
		setGraph(G);
		execute();
	}
	/*! execute the algorithm
	 * \param[in] G the graph
	 * \param[in] VC the visualization controller
	 */
	public void execute(Graph G, VisualizationController VC)
	{
		setGraph(G);
		setVisualization(VC);
		execute();
	}
	/*! get the name of said such forth algorithm
	 * \return
	 */
	public String getName()
	{
		return _Name;
	}
}