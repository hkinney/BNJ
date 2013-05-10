package edu.ksu.cis.bnj.ver3.core;
import java.util.Vector;
/*!
 * \file Discrete.java
 * \author Jeffrey M. Barber
 */
public class Discrete extends Domain
{
	private Vector	_Values;
	private int		_Order	= 0;
	/*! Construct a new discrete domain containing 0 elements
	 */
	public Discrete()
	{
		_Values = new Vector();
	}
	/*! Construct a Discrete with an array of Outcomes
	 * \param[in] outcomes the names of the outcomes
	 */
	public Discrete(String[] outcomes)
	{
		_Values = new Vector();
		for (int i = 0; i < outcomes.length; i++)
		{
			addName(outcomes[i]);
		}
		_Order = _Values.size();
	}
	/*! Find a name in the discrete and get its value
	 * \param[in] outcome the name of an set
	 * \return the index
	 */
	public int findName(String outcome)
	{
		return _Values.indexOf(outcome);
	}
	/*! Add an outcome
	 * \param[in] outcome the name of the new outcome
	 */
	public void addName(String outcome)
	{
		_Values.add(outcome);
		_Order = _Values.size();
	}
	/*! Get the number of disjoint sets in the domain
	 * \see edu.ksu.cis.bnj.ver3.core.Domain::getOrder()
	 * \return the number of disjoint sets
	 */
	public int getOrder()
	{
		return _Order;
	}
	/*! Get the o'th element in the domain
	 * \see edu.ksu.cis.bnj.ver3.core.Domain::getName()
	 * \param[in] o the o'th element in the domain
	 */
	public String getName(int o)
	{
		return (String) _Values.get(o);
	}
}