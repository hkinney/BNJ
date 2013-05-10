package edu.ksu.cis.util.graph.core;
/*!
 * \file Edge.java
 * \author Jeffrey M. Barber
 */
public class Edge
{
	private Vertex	_Source;
	private Vertex	_Destination;
	private boolean	_IsDirected;
	/*! Construct an edge for communication
	 * \param s - the source vertex
	 * \param d - the destination vertex
	 */
	public Edge(Vertex s, Vertex d)
	{
		_Source = s;
		_Destination = d;
		_IsDirected = true;
	}
	/*! Slight of hand accessor for src
	 * \return the source vertex
	 */
	public Vertex s()
	{
		return _Source;
	}
	/*! Slight of hand accessor for dest
	 * \return the destination vertex
	 */
	public Vertex d()
	{
		return _Destination;
	}
	/*! Set how the edge is connected
	 *\param dir
	 */
	public void setDirected(boolean dir)
	{
		_IsDirected = dir;
	}
	/*!
	 * \return how the edge is connected, true => (src,dst) | false => (src,dst) and (dst,src)
	 */
	public boolean isDirected()
	{
		return _IsDirected;
	}
	/*! Invert this Edge
	 */
	public void invert()
	{
		Vertex temp = _Source;
		_Source = _Destination;
		_Destination = temp;
	}
	/*! Get the oppposite vertex of A
	 * \param[in] A the vertex to hold onto
	 * \return the opposite vertex of A
	 */
	public Vertex opposite(Vertex A)
	{
		if (A == _Source) return _Destination;
		if (A == _Destination) return _Source;
		return null;
	}
	/*! Get the inversion of this edge
	 * \return the inverted edge
	 */
	public Edge getInvertedEdge()
	{
		return new Edge(_Destination, _Source);
	}
	/*! copy() this edge
	 * \return a copy of the edge
	 */
	public Edge copy()
	{
		Edge E = new Edge(_Source, _Destination);
		E.setDirected(_IsDirected);
		return E;
	}
}