package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import edu.ksu.cis.bnj.ver3.core.CPF;
/*!
 * \file LSMarginalHook.java
 * \author Jeffrey M. Barber
 */
public interface LSMarginalHook
{
	/*! The Interface for getting back all the marginals
	 * @param Marginals the marginals
	 */
	public void OnMarginals(CPF[] Marginals);
}