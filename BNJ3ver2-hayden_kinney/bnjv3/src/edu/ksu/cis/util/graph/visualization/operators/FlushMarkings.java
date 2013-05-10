package edu.ksu.cis.util.graph.visualization.operators;
import java.util.ArrayList;
/*!
 * \file FlushMarkings.java
 * \author Jeffrey M. Barber
 */
public class FlushMarkings
{
	private ArrayList	_OldMarkings;
	/*! capture the old markings
	 * \param[in] orig the old markins
	 * \return a new markings list
	 */
	public ArrayList apply(ArrayList orig)
	{
		_OldMarkings = orig;
		return new ArrayList();
	}
	/*! apply the inverse, return the old
	 * \return the old markings
	 */
	public ArrayList applyInverse()
	{
		return _OldMarkings;
	}
}