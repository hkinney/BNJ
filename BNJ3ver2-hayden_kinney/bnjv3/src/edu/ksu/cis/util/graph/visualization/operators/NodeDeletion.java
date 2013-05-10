package edu.ksu.cis.util.graph.visualization.operators;

import edu.ksu.cis.bnj.ver3.core.*;
import edu.ksu.cis.util.GlobalOptions;
import edu.ksu.cis.util.graph.core.Graph;

/*
 * File: NodeDeletion.java
 * File Author: Hayden Kinney
 */

public class NodeDeletion{
	// Variables for BeliefNode and Network.
	private BeliefNode beliefNode;
	private BeliefNetwork beliefNetwork;
	
	//This will create a new NodeDeletion Operator.
	public NodeDeletion(BeliefNode BelNode)
	{
		beliefNode = BelNode;
	}
	
	//Apply a NodeDeletion operator to the beliefNetwork.
	public BeliefNetwork apply(BeliefNetwork BelNet)
	{
		beliefNetwork = BelNet;
		BelNet.deleteBeliefNode(beliefNode);
		return beliefNetwork;
	}

	//Undo a NodeDeletion operator to the beliefNetwork.
	public BeliefNetwork applyInverse(BeliefNetwork BelNet)
	{
		beliefNetwork = BelNet;
		BelNet.addBeliefNode(beliefNode);
		return beliefNetwork;
	}
	
	//This will pause for the Visualization.
	public int getTime()
	{
		return GlobalOptions.getInstance().getInteger("time_node_deletion", 500);
	}
}
