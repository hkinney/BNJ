package edu.ksu.cis.util.graph.visualization.operators;
/*!
 * \file ColorLegendMap.java
 * \author Jeffrey M. Barber
 */
public class ColorLegendMap
{
	public int		ColorIdx;
	public String	Name;
	/*! color the legend
	 * \param[in] idx the index of the new color
	 * \param[in] name
	 */
	public ColorLegendMap(int idx, String name)
	{
		ColorIdx = idx;
		Name = name;
	}
}