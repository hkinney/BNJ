package edu.ksu.cis.bnj.ver3.inference.exact;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.inference.Inference;
import edu.ksu.cis.util.graph.algorithms.BuildCliqueTree;
import edu.ksu.cis.util.graph.core.Graph;
/*!
 * \file LSonline.java
 * \author Jeffrey M. Barber
 */
public class LSonline implements Inference
{
	private CPF[]			marginals	= null;
	private BeliefNode[]	beliefnodes;
	private BuildCliqueTree	_BCT;
	private BeliefNetwork	_builtOn	= null;
	/*! Run the Inference
	 * \param[in] bn the Belief Network to catch the Clique Tree On, assuming structure stays same
	 */
	private void Cache(BeliefNetwork bn)
	{
		if (_builtOn != bn)
		{
			Graph g = bn.getGraph().copy();
			_BCT = new BuildCliqueTree();
			_BCT.setGraph(g);
			_BCT.execute();
			_builtOn = bn;
		}
	}
	/*! Run the Inference
	 * \param[in] bn the Belief Network to run inference on
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::run(edu.ksu.cis.bnj.ver3.core.BeliefNetwork)
	 */
	public void run(BeliefNetwork bn)
	{
		if (bn.getNodes().length <= 1) return;
		Cache(bn);
		CliqueTree CT = new CliqueTree(_BCT, _builtOn);
		CT.begin();
		marginals = CT.Marginalize();
		beliefnodes = CT.getNodes();
	}
	/*! Query for a Marginal
	 * \param[in] bnode the Belief Node to query the marginal of
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::queryMarginal()
	 */
	public CPF queryMarginal(BeliefNode bnode)
	{
		for (int i = 0; i < beliefnodes.length; i++)
		{
			if (bnode.getName().equals(beliefnodes[i].getName()))
			{
				return marginals[i];
			}
		}
		return null;
	}
	/*! Get the name of this Inference Algorithm
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::getName()
	 */
	public String getName()
	{
		return "LSonline";
	}
}