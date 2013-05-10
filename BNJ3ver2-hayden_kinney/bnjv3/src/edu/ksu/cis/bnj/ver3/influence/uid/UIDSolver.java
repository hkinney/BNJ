package edu.ksu.cis.bnj.ver3.influence.uid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import edu.ksu.cis.bnj.ver3.core.*;



public class UIDSolver {
	private class NodeSet {
		BeliefNode[] label;
		BeliefNode[] futureminuslabel;
		NodeSet[] children;
		public NodeSet(BeliefNode[] l, BeliefNode[] f) {
			if (l != null)
				label = Arrays.copyOf(l, l.length);
			if (f != null)
				futureminuslabel = Arrays.copyOf(f, f.length);
		}
	}
	private BeliefNetwork _OriginalNetwork;
	private BeliefNetwork _TransformedNetwork;
	
	
	public UIDSolver() {
		BeliefNode[] nodes = _OriginalNetwork.getNodes();
		HashSet<BeliefNode> decisionNodesSet = new HashSet<BeliefNode>();
		HashSet<BeliefNode> chanceNodesSet = new HashSet<BeliefNode>();		
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType() == nodes[i].NODE_DECISION)
				decisionNodesSet.add(nodes[i]);
			if (nodes[i].getType() == nodes[i].NODE_CHANCE)
				chanceNodesSet.add(nodes[i]);
		}
	}
	
	public void GSskeletonGen(HashSet<BeliefNode> decisionNodes, HashSet<BeliefNode> chanceNodes) {
		NodeSet G;
		ArrayList<NodeSet> process = new ArrayList<NodeSet> ();
		HashSet<BeliefNode> free = new HashSet<BeliefNode>();
		
		mainloop:
		for (BeliefNode n: decisionNodes) {
			BeliefNode[] children = _OriginalNetwork.getChildren(n);
			for (int j = 0; j < children.length; j++) {
				if (children[j].getType() == children[j].NODE_CHANCE)
					continue mainloop;
			}
			free.add(n);
		}
		
		BeliefNode[] freeList = new BeliefNode[free.size()];
		free.toArray(freeList);
		G = new NodeSet(freeList, null);
		process.add(new NodeSet(freeList, null));
		
		while (!process.isEmpty()) {
			NodeSet pNode = process.remove(0);
			
		}
	}
		
	
	
}