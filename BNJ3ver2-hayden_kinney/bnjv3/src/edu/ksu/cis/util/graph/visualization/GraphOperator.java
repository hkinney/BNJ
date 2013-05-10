package edu.ksu.cis.util.graph.visualization;
import edu.ksu.cis.util.graph.core.*;
/*!
 * \file GraphOperator.java
 * \author Jeffrey M. Barber
 */
public interface GraphOperator
{
	/*!apply the graph operator to G, returning a new Graph potentially 
	 * \param[in] G
	 * \return the new graph
	 */
	public Graph apply(Graph G);
	/*! apply the inverse graph operator to G, returning a new Graph potentially 
	 * \param[in] G
	 * \return
	 */
	public Graph applyInverse(Graph G);
	/*! get the time
	 * \return the time for this frame
	 */
	public int getTime();
}