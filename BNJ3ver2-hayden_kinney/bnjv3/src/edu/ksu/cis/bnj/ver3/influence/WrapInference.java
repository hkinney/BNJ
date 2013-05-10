package edu.ksu.cis.bnj.ver3.influence;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.inference.Inference;

/*!
 * \file WrapInference.java
 * \author Jeffrey M. Barber
 */
public class WrapInference implements Solver
{
	private Inference _Infer;
	public WrapInference(Inference infer)
	{
		_Infer = infer;
	}
	/*! What is the name of this influence diagram solver
	 * \return the name
	 */
	public String getName()
	{
		return "Wrapped:" + _Infer.getName();
	}
	
	/*! Solve this network
	 * 
	 * \param[in] bn the Bayesian Belief Network
	 */
	public void solve(BeliefNetwork bn)
	{
		_Infer.run(bn);
	}

	/*! After solving, query a marginal for a node
	 * 
	 * \param[in] bnode 	A Belief Node we want to query
	 * \return the cpf that contains the marginal
	 */
	public CPF queryMarginal(BeliefNode bnode)
	{
		return _Infer.queryMarginal(bnode);
	}

}
