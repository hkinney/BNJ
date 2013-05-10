package edu.ksu.cis.util.graph.visualization.operators;
import java.util.ArrayList;
/*!
 * \file NewColorLegend.java
 * \author Jeffrey M. Barber
 */
public class NewColorLegend
{
	private ArrayList	_Old;
	/*! set the old legend
	 * \param[in] old the old legend
	 */
	public void setOld(ArrayList old)
	{
		_Old = old;
	}
	/*! get the old legend
	 * @return the old legen
	 */
	public ArrayList getOld()
	{
		return _Old;
	}
}