package edu.ksu.cis.util.graph.core;
/*!
 * \file Visitor.java
 * \author Jeffrey M. Barber
 */
public interface Visitor
{
	/*! visiting a vertex
	 * \param[in] V the incoming vertex
	 * \return true -> keep going, false -> stop
	 */
	public boolean onVertex(Vertex V);
	/*! visiting an edge
	 * \param[in] E the incoming edge
	 * \return true -> keep going, false -> stop
	 */
	public boolean onEdge(Edge E);
}