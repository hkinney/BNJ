package edu.ksu.cis.bnj.ver3.core;
/*!
 * \file Continuous.java
 * \author Jeffrey M. Barber
 */
public class Continuous extends Domain
{
	String	_Variable;
	/*! Construct a continuous domain
	 * \param variable - the name of the variable
	 */
	public Continuous(String variable)
	{
		_Variable = variable;
	}
	/*! Get the o'th element in the domain
	 * \see edu.ksu.cis.bnj.ver3.core.Domain::getName()
	 * \param[in] o the o'th element in the domain
	 */
	public String getName(int o)
	{
		return _Variable;
	}
	/*! Get the number of disjoint sets in the domain
	 * \see edu.ksu.cis.bnj.ver3.core.Domain::getOrder()
	 * \return the number of disjoint sets
	 */
	public int getOrder()
	{
		return 1;
	}
}