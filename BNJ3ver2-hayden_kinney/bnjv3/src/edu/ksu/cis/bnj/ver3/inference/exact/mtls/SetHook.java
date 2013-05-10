package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import edu.ksu.cis.bnj.ver3.core.CPF;
/*!
 * \file SetHook.java
 * \author Jeffrey M. Barber
 */
public class SetHook extends Message
{
	public LSMarginalHook	_Hook;
	public CPF[]			_Marginals;
	/*! Set hook for termination
	 * \param[in] C
	 * \param[in] hook
	 * \param[in] marginals
	 */
	public SetHook(MClique C, LSMarginalHook hook, CPF[] marginals)
	{
		_Clique = C;
		_Hook = hook;
		_Marginals = marginals;
	}
	/*! Run this Message
	 * \see edu.ksu.cis.bnj.ver3.inference.exact.mtls.Message::run()
	 */
	public void run()
	{
		_Hook.OnMarginals(_Marginals);
	}
}