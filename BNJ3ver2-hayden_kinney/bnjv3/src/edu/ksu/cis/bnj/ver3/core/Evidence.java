package edu.ksu.cis.bnj.ver3.core;
/*!
 * \file Evidence.java
 * \author Jeffrey M. Barber
 */
public interface Evidence
{
	/*! Is this evidence correct for this domain?
	 * \param[in] D		the domain to check against
	 * \return the result ot the question [true/false]
	 */
	public boolean inDomain(Domain D);
	/*! Get the value in the domain for this evidence
	 * \return the value of the evivence
	 */
	public Value getEvidenceValue(int q);

	/*! Get the associated name of the value
	 * \return the name
	 */
	public String getName(Domain D);
}