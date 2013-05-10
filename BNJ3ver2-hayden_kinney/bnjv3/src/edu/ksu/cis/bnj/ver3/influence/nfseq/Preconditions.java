package edu.ksu.cis.bnj.ver3.influence.nfseq;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.util.graph.algorithms.TopologicalSort;

/*!
 * \file NonForgettingCheck.java
 * \author Jeffrey M. Barber
 */
public class Preconditions
{
	public boolean passed;
//	public BeliefNode[] _Decisions;
	public int _NumberOfDecisions;
	public int[] order;
	
	public Preconditions(BeliefNetwork bn)
	{
		TopologicalSort ts = new TopologicalSort();
		ts.execute(bn.getGraph());
		order = ts.alpha;
		
		// count the decisions
		int numDecisions = 0;
		BeliefNode[] nodes = bn.getNodes(); 
		for(int i = 0; i < nodes.length; i++)
			if(nodes[i].getType() == BeliefNode.NODE_DECISION && !nodes[i].hasEvidence() )
				numDecisions++;
		
		// number of decisions not good
		if(numDecisions == 0)
		{
			passed = false;
			return;
		}
		BeliefNode[] _Decisions;
		_Decisions = new BeliefNode[numDecisions];
		
		_NumberOfDecisions = _Decisions.length;
		
		numDecisions = 0;
		for(int i = 0; i < nodes.length; i++)
		{
			int j = order[i];
			if(nodes[j].getType() == BeliefNode.NODE_DECISION && ! nodes[j].hasEvidence())
			{
				_Decisions[numDecisions] = nodes[j];
				numDecisions++;
			}
		}
		
		passed = true;
		for(int i = _Decisions.length - 1; i > 0 && passed; i--)
		{
			for(int j = i-1; j >= 0 && passed; j--)
			{
				passed = bn.getGraph().getConnectedness(_Decisions[j].getOwner(), _Decisions[i].getOwner()) == 1; 
			}
		}
	}
}
