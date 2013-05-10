package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.graph.core.Edge;
/*!
 * \file TempEdgeCreate.java
 * \author Jeffrey M. Barber
 */
public class TempEdgeCreate
{
	public Edge	edgeCreate;
	/*! create a temporary edge
	 * \param[in] E the temporary edge (rendering only)
	 */
	public TempEdgeCreate(Edge E)
	{
		edgeCreate = E;
	}
}