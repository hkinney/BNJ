package edu.ksu.cis.bnj.ver3.core;
/*!
 * \file Domain.java
 * \author Jeffrey M. Barber
 */
public abstract class Domain
{
	/*! Get the number of disjoint sets in the domain
	 * \return the number of disjoint sets
	 */
	public abstract int getOrder();
	/*! Get the o'th element in the domain
	 * \see edu.ksu.cis.bnj.ver3.core.Domain::getName()
	 * \param[in] o the o'th element in the domain
	 */
	public abstract String getName(int o);
}