package edu.ksu.cis.util.graph.visualization.operators;
import java.util.ArrayList;
/*!
 * \file TempEdgeFlush.java
 * \author Jeffrey M. Barber
 */
public class TempEdgeFlush
{
	private ArrayList	_Old;
	/*! keep the old array list
	 * \param[in] old
	 */
	public void setOld(ArrayList old)
	{
		_Old = old;
	}
	/*! get the old temp edges
	 * \return the old temp edges
	 */
	public ArrayList getOld()
	{
		return _Old;
	}
}