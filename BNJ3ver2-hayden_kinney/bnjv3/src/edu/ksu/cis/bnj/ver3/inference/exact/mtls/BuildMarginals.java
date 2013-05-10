package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
/*!
 * \file BuildMarginals.java
 * \author Jeffrey M. Barber
 */
public class BuildMarginals extends Message
{
	private MessageCoordinator	_MessageCoordinator;
	private CPF[]				_Marginals;
	/*! Build Marginals
	 * \param[in] C the clique
	 * \param[out] MarginalPad the output reciever for the marginaliation
	 */
	public BuildMarginals(MClique C, CPF[] MarginalPad)
	{
		_Clique = C;
		_Marginals = MarginalPad;
	}
	/*! Run this Message
	 * \see edu.ksu.cis.bnj.ver3.inference.exact.mtls.Message::run()
	 */
	public void run()
	{
		BeliefNode[] M = _Clique.getBaseNodes();
		for (int j = 0; j < M.length; j++)
		{
			BeliefNode[] S = new BeliefNode[1];
			S[0] = M[j];
			_Marginals[M[j].loc()] = _Clique.getCPF().extract(S);
			_Marginals[M[j].loc()].normalize();
		}
	}
}