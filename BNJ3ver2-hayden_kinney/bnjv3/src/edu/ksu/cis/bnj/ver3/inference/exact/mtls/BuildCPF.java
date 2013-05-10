package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import java.util.LinkedList;
/*!
 * \file BuildCPF.java
 * \author Jeffrey M. Barber
 */
public class BuildCPF extends Message
{
	private LinkedList	_EvidenceNodes;
	/*! Build Clique
	 * \param[in] C the clique
	 * \param[in] evNodes the evidence nodes
	 */
	public BuildCPF(MClique C, LinkedList evNodes)
	{
		_Clique = C;
		_EvidenceNodes = evNodes;
	}
	/*! Run this Message
	 * \see edu.ksu.cis.bnj.ver3.inference.exact.mtls.Message::run()
	 */
	public void run()
	{
		_Clique.localBuildCPF();
		_Clique.localFilterEvidence(_EvidenceNodes);
	}
}