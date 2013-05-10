package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.inference.Inference;
import edu.ksu.cis.util.graph.algorithms.BuildCliqueTree;
/*!
 * \file MTLS.java
 * \author Jeffrey M. Barber
 */
public class MTLS implements Inference, LSMarginalHook
{
	private CPF[]	marginals	= null;
	/*! Run the Inference
	 * \param[in] bn the Belief Network to run inference on
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::run(edu.ksu.cis.bnj.ver3.core.BeliefNetwork)
	 */
	public void run(BeliefNetwork bn)
	{
		BuildCliqueTree BCT = new BuildCliqueTree();
		BCT.setGraph(bn.getGraph());
		BCT.execute();
		MCliqueTree CT = new MCliqueTree(BCT, bn, this, 2);
	}
	/*! Run the Inference
	 * \param[in] bn the Belief Network to run inference on
	 * \param[in] NumberOfProcessors the Number of processors
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::run(edu.ksu.cis.bnj.ver3.core.BeliefNetwork)
	 */
	public void run(BeliefNetwork bn, int NumberOfProcessors)
	{
		BuildCliqueTree BCT = new BuildCliqueTree();
		BCT.setGraph(bn.getGraph());
		BCT.execute();
		MCliqueTree CT = new MCliqueTree(BCT, bn, this, NumberOfProcessors);
	}
	/*! Get the name of this Inference Algorithm
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::getName()
	 */
	public String getName()
	{
		return "LS Message Passing - Multi Threaded";
	}
	/*! Wait until the marginals arrive
	 */
	public void waitUntilRecieved()
	{
		while (marginals == null)
		{
			try
			{
				Thread.yield();
			}
			catch (Exception e)
			{}
		}
	}
	/*! The marginals have arrived
	 * \see edu.ksu.cis.bnj.ver3.inference.exact.mtls.LSMarginalHook::OnMarginals(edu.ksu.cis.bnj.ver3.core.CPF[])
	 */
	public void OnMarginals(CPF[] Marginals)
	{
		marginals = Marginals;
	}
	/*! Query for a Marginal
	 * \param[in] bnode the Belief Node to query the marginal of
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::queryMarginal()
	 */
	public CPF queryMarginal(BeliefNode bnode)
	{
		if (marginals == null) return null;
		return marginals[bnode.loc()];
	}
}