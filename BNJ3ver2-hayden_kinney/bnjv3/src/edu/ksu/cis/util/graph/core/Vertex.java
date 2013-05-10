package edu.ksu.cis.util.graph.core;
/*!
 * \file Vertex.java
 * \author Jeffrey M. Barber
 */
public class Vertex
{
	private String	_Name;
	private int[]	_VisualAttrib;
	private int		_Location;
	private Object	_Object;
	/*! Get the data object
	 *  \param[in] o the data object
	 */
	public void setObject(Object o)
	{
		_Object = o;
	}
	/*! Get the data object
	 * \return the data object
	 */
	public Object getObject()
	{
		return _Object;
	}
	/*! Construct a vertex with a name
	 * \param[in] name - the name of vertex
	 */
	public Vertex(String name)
	{
		_Name = name;
		_VisualAttrib = new int[6];
		_VisualAttrib[0] = 0;
		_VisualAttrib[1] = 0;
		_VisualAttrib[2] = 0;
		_VisualAttrib[3] = 0;
		_VisualAttrib[4] = 0;
		_VisualAttrib[5] = 0;
		_Location = -1;
		_Object = null;
	}
	/*! Get the X Coordinate
	 * \return the x position
	 */
	public int getx()
	{
		return _VisualAttrib[0];
	}
	/*! Get the Y Coordinate
	 * \return the y position
	 */
	public int gety()
	{
		return _VisualAttrib[1];
	}
	/*! Translate the vertex
	 * \param[in] x the x offset
	 * \param[in] y the y offset
	 */
	public void translate(int x, int y)
	{
		_VisualAttrib[0] += x;
		_VisualAttrib[1] += y;
	}
	/*! Get the name
	 * \return the name of the vertex
	 */
	public String getName()
	{
		return _Name;
	}
	/*! Change the Vertex's name
	 * \param name new name of the vertex
	 */
	public void setName(String name)
	{
		_Name = name;
	}
	/*! Change a Vertex's attribute register 
	 * \param[in] idx The index of the vertex register
	 * \param[in] val The register's new value
	 */
	public void setAttrib(int idx, int val)
	{
		_VisualAttrib[idx] = val;
	}
	/*! Query a Vertex' attribute register 
	 * \param[in] idx The index of the vertex register
	 * \return the value of the register
	 */
	public int getAttrib(int idx)
	{
		return _VisualAttrib[idx];
	}
	/*! copy the vertex
	 * \return  a copy of the vertex
	 */
	public Vertex copy()
	{
		Vertex V = new Vertex(_Name);
		V.translate(_VisualAttrib[0], _VisualAttrib[1]);
		V.setAttrib(2, _VisualAttrib[2]);
		V.setAttrib(3, _VisualAttrib[3]);
		V.setAttrib(4, _VisualAttrib[4]);
		V.setAttrib(5, _VisualAttrib[5]);
		V.setLOC(-1);
		V.setObject(_Object);
		return V;
	}
	/*! the location in the store of the vertex, also denotes if bound to a graph
	 * \return the location in the store
	 */
	public int loc()
	{
		return _Location;
	}
	/*! sets the store location
	 * \note
	 * \par
	 * 	DO NOT USE
	 * \param[in] x the new location store
	 */
	public void setLOC(int x)
	{
		_Location = x;
	}
}