package edu.ksu.cis.bnj.ver3.inference;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
/*!
 * \file Inference.java
 * \author Jeffrey M. Barber
 */
public interface Inference
{
	/*! What is the name of this inference algorithm
	 * \return the name
	 */
	public String getName();
	/*! Run this algorithm on the belief network
	 * 
	 * \param[in] bn the Bayesian Belief Network
	 */
	public void run(BeliefNetwork bn);
	/*! After running, query a marginal for a node
	 * 
	 * \param[in] bnode 	A Belief Node we want to query
	 * \return the cpf that contains the marginal
	 */
	public CPF queryMarginal(BeliefNode bnode);
}