package edu.ksu.cis.util.graph.coloring;

import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.core.Vertex;

/*!
 * \file Clear.java
 * \author Jeffrey M. Barber
 */
public class Clear
{
	/*!
	 * \param[in] G
	 */
	public static void apply(Graph G)
	{
		Vertex[] V = G.getVertices();
		for(int i = 0; i < V.length; i++)
		{
			V[i].setAttrib(2,0);
		}
	}
}
