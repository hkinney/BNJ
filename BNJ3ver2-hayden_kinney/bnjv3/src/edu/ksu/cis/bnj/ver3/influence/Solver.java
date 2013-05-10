package edu.ksu.cis.bnj.ver3.influence;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;

/*!
 * \file Solver.java
 * \author Jeffrey M. Barber
 */
public interface Solver
{
	/*! What is the name of this influence diagram solver
	 * \return the name
	 */
	public String getName();
	
	/*! Solve this network
	 * 
	 * \param[in] bn the Bayesian Belief Network
	 */
	void solve(BeliefNetwork bn);

	/*! After solving, query a marginal for a node
	 * 
	 * \param[in] bnode 	A Belief Node we want to query
	 * \return the cpf that contains the marginal
	 */
	CPF queryMarginal(BeliefNode bnode);
	
}
