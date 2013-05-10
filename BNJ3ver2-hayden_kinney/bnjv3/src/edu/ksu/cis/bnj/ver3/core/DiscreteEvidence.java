package edu.ksu.cis.bnj.ver3.core;
import edu.ksu.cis.bnj.ver3.core.values.ValueUnity;
import edu.ksu.cis.bnj.ver3.core.values.ValueZero;
/*!
 * \file DiscreteEvidence.java
 * \author Jeffrey M. Barber
 */
public class DiscreteEvidence implements Evidence
{
	private int	_Which;
	/*! Get the direct mapping
	 * \return the direct mapping of evidence
	 */
	public int getDirectValue()
	{
		return _Which;
	}
	/*! Construct a Discrete Evidence value from 
	 * @param[in] Value - direct mapping of evidence
	 */
	public DiscreteEvidence(int Value)
	{
		_Which = Value;
	}
	/*! Helper Construct
	 * \param[in] D		 		the discrete domain to search in
	 * \param[in] Outcome	    the outcome to find
	 */
	public DiscreteEvidence(Discrete D, String Outcome)
	{
		_Which = D.findName(Outcome);
	}
	/*! Is this evidence correct for this domain?
	 * \see edu.ksu.cis.bnj.ver3.core.Evidence::inDomain()
	 * \param[in] D		the domain to check against
	 * \return the result ot the question [true/false]
	 */
	public boolean inDomain(Domain D)
	{
		return (D instanceof Discrete);
	}
	/*! Get the value in the domain for this evidence
	 * \see edu.ksu.cis.bnj.ver3.core.Evidence#getEvidenceValue()
	 * \return the value of the evivence
	 */
	public Value getEvidenceValue(int q)
	{
		if (q == _Which)
		{
			return ValueUnity.SingletonUnity;
		}
		return ValueZero.SingletonZero;
	}
	/*! Get the associated name of the value
	 * \see edu.ksu.cis.bnj.ver3.core.Evidence#getName()
	 * \return the name
	 */	
	public String getName(Domain D)
	{
		if(inDomain(D))
		{
			return ((Discrete)D).getName(_Which);
		}
		return null;
	}
	
}