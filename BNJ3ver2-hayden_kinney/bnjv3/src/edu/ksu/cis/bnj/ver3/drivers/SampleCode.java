package edu.ksu.cis.bnj.ver3.drivers;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.Discrete;
import edu.ksu.cis.bnj.ver3.core.Value;
import edu.ksu.cis.bnj.ver3.core.values.Field;
/**
 * file: SampleCode.java
 * 
 * @author Jeffrey M. Barber
 */
public class SampleCode
{
	void HowToCreateABayesianNetwork()
	{
		// create the network
		BeliefNetwork bn = new BeliefNetwork("A Sample Network");
		// create a boolean domain
		Discrete Bool = new Discrete(new String[] { "True", "False" });
		// create two nodes
		BeliefNode A = new BeliefNode("A", Bool);
		BeliefNode B = new BeliefNode("B", Bool);
		// insert the nodes into the
		bn.addBeliefNode(A);
		bn.addBeliefNode(B);
		// connect from A to B
		bn.connect(A, B);
	}
	void HowToNavigateABayesianNetwork(BeliefNetwork bn)
	{
		BeliefNode[] Nodes = bn.getNodes();
		for (int i = 0; i < Nodes.length; i++)
		{
			BeliefNode[] Children = bn.getChildren(Nodes[i]);
			BeliefNode[] Parents = bn.getParents(Nodes[i]);
			// do stuff
		}
	}
	boolean[]	Mark	= null;
	void DFS(BeliefNetwork bn, BeliefNode X)
	{
		// already seen this node
		if (Mark[X.loc()]) return;
		// mark node as seen
		Mark[X.loc()] = true;
		// get the Children
		BeliefNode[] Children = bn.getChildren(X);
		// for each child
		for (int i = 0; i < Children.length; i++)
		{
			DFS(bn, Children[i]);
		}
	}
	void DepthFirstSearch(BeliefNetwork bn)
	{
		// get each node
		BeliefNode[] Nodes = bn.getNodes();
		// create the markings
		Mark = new boolean[Nodes.length];
		// initialize the mark
		for (int i = 0; i < Mark.length; i++)
		{
			Mark[i] = false;
		}
		// for each node
		for (int i = 0; i < Nodes.length; i++)
		{
			DFS(bn, Nodes[i]);
		}
	}
	
	
	void OperateOnValues(Value a,Value b)
	{
		Value c;
		// c = a + b
		c = Field.add(a,b);
		// c = a - b
		c = Field.subtract(a,b);
		// c = a * b
		c = Field.mult(a,b);
		// c = a / b
		c = Field.divide(a,b);
	}
	
	void HowToIterateCPF(CPF X)
	{
		// Fastest way!
		for(int i = 0; i < X.size(); i++)
		{
			// get(i)
			// put(i, V)
		}
		
		// Need the logical address?
		// Slow Way
		for(int i = 0; i < X.size(); i++)
		{
			int[] q = X.realaddr2addr(i); // SLOW
			// do stuff with q
			// get(i)
			// put(i, V)
		}
		
		// Fast Way
		int[] q = X.realaddr2addr(0); // SLOW
		for(int i = 0; i < X.size(); i++)
		{
			// do stuff with q
			// get(i)
			// put(i, V)
			X.addOne(q);
		}
	}
	
	void HowToOperateDifferentDomains(CPF Large, CPF Small)
	{
		// The slow way
		int[] q = Large.realaddr2addr(0);
		for(int i = 0; i < Large.size(); i++)
		{
			int[] nQ = CPF.getSubQuery(q, Large.getDomainProduct(), Small.getDomainProduct());
			// nQ is now an address in the Small CPF, which we could use in various ways
			Large.addOne(q);
		}
		
		
		// the faster way
		//int[] q = Large.realaddr2addr(0);
		int[] proj = CPF.getProjectionMapping(Large.getDomainProduct(), Small.getDomainProduct());
		int[] nQ = Small.realaddr2addr(0);
		for(int i = 0; i < Large.size(); i++)
		{
			CPF.applyProjectionMapping(q,proj,nQ);
			// nQ is now an address in the Small CPF, which we could use in various ways
			Large.addOne(q);
		}
	}
}