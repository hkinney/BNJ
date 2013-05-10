package edu.ksu.cis.bnj.ver3.influence.uid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

import edu.ksu.cis.bnj.ver3.core.*;
import edu.ksu.cis.bnj.ver3.influence.nrar.NodeRemovalArcReversal;
import edu.ksu.cis.bnj.ver3.influence.nrar.VariableElimination;
import edu.ksu.cis.bnj.ver3.plugin.IOPlugInLoader;
import edu.ksu.cis.bnj.ver3.streams.Importer;
import edu.ksu.cis.bnj.ver3.streams.OmniFormatV1_Reader;


public class UIDSimpleSolver {
	private class GSNode {
		HashSet<BeliefNode> decisionNodes;
		HashSet<BeliefNode> future;
		HashSet<GSNode> parents;
		public GSNode(HashSet<BeliefNode> d, HashSet<BeliefNode> f) {
			decisionNodes = (HashSet<BeliefNode>)d.clone();
			future = (HashSet<BeliefNode>)f.clone();
			parents = new HashSet<GSNode>();
		}
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (! (o instanceof GSNode))
				return false;
			if (!((GSNode)o).decisionNodes.equals(this.decisionNodes))
				return false;
			if (!((GSNode)o).future.equals(this.future))
				return false;
			return true;
		}
		
		public GSNode clone() {
			return new GSNode(decisionNodes, future);
		}
		
		public void printOut() {
			System.out.println("decision nodes:");
			if ( decisionNodes != null)
				for (BeliefNode n : decisionNodes)
					System.out.print(n.getName()+" ");
			else
				System.out.println("sink");
			System.out.println("\n==============================");
			
			System.out.println("parent nodes: ");
			for (GSNode n :parents)
				n.printOut();
			
				
		}

	}
	
	private class GSGraph {
		ArrayList<GSNode> nodes;
		GSNode free;
		
		public GSGraph(GSNode f) {
			free = f.clone();
			nodes = new ArrayList<GSNode> ();
			nodes.add(f);
		}
		public void printOut() {
			nodes.get(0).printOut();
		}
		
	}
	
	private BeliefNetwork _OriginalNetwork;
	private BeliefNetwork _TransformedNetwork;
	private GSGraph g;
	HashSet<BeliefNode> decisionNodesSet;
	HashSet<BeliefNode> obchanceNodesSet;
	HashSet<BeliefNode> nonobchanceNodesSet;	
	HashSet<BeliefNode> utilityNodesSet;	
	
	public HashSet<GSNode> findParents(GSNode n, HashSet<BeliefNode> future) {
		Hashtable<BeliefNode, HashSet<BeliefNode>> table = new Hashtable<BeliefNode, HashSet<BeliefNode>>();
		
		for (BeliefNode decision: decisionNodesSet) {
			BeliefNode[] descendants = _OriginalNetwork.descendants(decision);
			HashSet<BeliefNode> de = new HashSet<BeliefNode> (Arrays.asList(descendants));
			de.removeAll(future);
			de.removeAll(nonobchanceNodesSet);
			de.removeAll(utilityNodesSet);
			table.put(decision, de);
		}
		
		ArrayList<HashSet<BeliefNode>> parents = new ArrayList<HashSet<BeliefNode>>();
		ArrayList<HashSet<BeliefNode>> min_de = new ArrayList<HashSet<BeliefNode>>();
		
		mainloop:
		for (BeliefNode decision: table.keySet()) {
			if (parents.isEmpty()) {
				HashSet<BeliefNode> dset = new HashSet<BeliefNode>();
				dset.add(decision);
				parents.add(dset);
				HashSet<BeliefNode> deset = table.get(decision);
				min_de.add(deset);
			}
			else {
				for (int i = 0; i < min_de.size(); i++) {
					if (table.get(decision).equals(min_de.get(i))) {
						parents.get(i).add(decision);
						continue mainloop;
					}
					if (min_de.get(i).containsAll(table.get(decision))) {
						continue mainloop;
					}
				}
				
				HashSet<BeliefNode> dset = new HashSet<BeliefNode>();
				dset.add(decision);
				parents.add(dset);
				HashSet<BeliefNode> deset = table.get(decision);
				min_de.add(deset);				
			}
		}
		
		HashSet<GSNode> list = new HashSet<GSNode> ();
		for (int i = 0; i < min_de.size(); i++) {
			HashSet<BeliefNode> f = (HashSet<BeliefNode>)future.clone();
			f.addAll(min_de.get(i));
			list.add(new GSNode(parents.get(i), f));
		}
		return list;
		
	}
	public UIDSimpleSolver(BeliefNetwork _bn, HashSet<BeliefNode> nonObservable) {
		_OriginalNetwork = _bn;
		BeliefNode[] nodes = _OriginalNetwork.getNodes();
		decisionNodesSet = new HashSet<BeliefNode>();
		obchanceNodesSet = new HashSet<BeliefNode>();
		utilityNodesSet = new HashSet<BeliefNode>();
		nonobchanceNodesSet = (HashSet<BeliefNode>)nonObservable.clone();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType() == nodes[i].NODE_DECISION)
				decisionNodesSet.add(nodes[i]);
			if (nodes[i].getType() == nodes[i].NODE_CHANCE)
				if (!nonObservable.contains(nodes[i]))
					obchanceNodesSet.add(nodes[i]);
			if (nodes[i].getType() == nodes[i].NODE_UTILITY)
				utilityNodesSet.add(nodes[i]);
		}
		
		HashSet<BeliefNode> lastDecisions = new HashSet<BeliefNode>();
		
		mainloop:
		for (BeliefNode n : decisionNodesSet) {
			BeliefNode[] children = _OriginalNetwork.getChildren(n);
			for (int i = 0; i < children.length; i++)
				if(children[i].getType() == children[i].NODE_DECISION || obchanceNodesSet.contains(children[i]))
					continue mainloop;
			lastDecisions.add(n);
		}
		
		GSNode free = new GSNode(lastDecisions, new HashSet<BeliefNode>());
		g = new GSGraph(free);
		ArrayList<GSNode> process = new ArrayList<GSNode> ();
		process.add(free);
		
		while (!process.isEmpty()) {
			GSNode n = process.remove(0);
			HashSet<BeliefNode> future = new HashSet<BeliefNode>();
			future.addAll(n.decisionNodes);
			future.addAll(n.future);
			
			HashSet<BeliefNode> d = (HashSet<BeliefNode>) decisionNodesSet.clone();
			d.removeAll(future);
			if (!d.isEmpty()) {
				HashSet<GSNode> parents = findParents(n, future);
				for (GSNode p : parents) {
					if (! g.nodes.contains(p)) {
						g.nodes.add(p);
						n.parents.add(p);
						process.add(p);
					}
					else {
						n.parents.add(g.nodes.get(g.nodes.indexOf(p)));
					}
				}
			}
		}

		g.printOut();
		//nonObRemove(nonObservable);
		
		

	}
	
	public void nonObRemove(HashSet<BeliefNode> nonObservable) {
		VEInternal ve = new VEInternal();
		for (BeliefNode n : nonObservable)
		ve.nonObNodeRemove(_OriginalNetwork, n);
	}
	public void solution() {

	}
	
	/*
	public void SDAGGenRec(HashSet<BeliefNode> future, SDAG current) {
		
		if (current.decisionNode != null) {
			BeliefNode[] children = _OriginalNetwork.getChildren(current.decisionNode);
			for (int i = 0; i < children.length; i++)
				if (obchanceNodesSet.contains(children[i]))
					current.chanceNodes.add(children[i]);
			future.addAll(current.chanceNodes);
		}
		
		HashSet<BeliefNode> candidates = (HashSet<BeliefNode>)decisionNodesSet.clone();
		candidates.removeAll(future);
		Hashtable<BeliefNode, HashSet<BeliefNode>> table = new Hashtable<BeliefNode, HashSet<BeliefNode>>();

		for (BeliefNode n: candidates) {
			BeliefNode[] descendants = _OriginalNetwork.descendants(n);
			HashSet<BeliefNode> de = new HashSet<BeliefNode> (Arrays.asList(descendants));
			de.removeAll(future);
			table.put(n, de);
		}
		HashSet<BeliefNode> parents = new HashSet<BeliefNode>();
		HashSet<BeliefNode> min_de = new HashSet<BeliefNode>();
		for (BeliefNode n: table.keySet()) {
			if (parents.isEmpty()) {
				parents.add(n);
				min_de = table.get(n);
			}
			else {
				if (table.get(n).equals(min_de))
					parents.add(n);
				else 
					if (min_de.containsAll(table.get(n))) {
						parents.clear();
						parents.add(n);
						min_de = table.get(n);
					}
						
			}
		}
		
		current.parents = new SDAG[parents.size()];
		if (current.decisionNode != null)
			System.out.println(current.decisionNode.getName());
		System.out.println("parents: "+parents.toString());
		System.out.println("future: "+future.toString());
		int i = 0;
		for (BeliefNode n: parents) {
			SDAG p = new SDAG(n);
			current.parents[i] = p;
			i++;
			HashSet<BeliefNode> futurenew = (HashSet<BeliefNode> )future.clone();
			futurenew.add(n);

			SDAGGenRec(futurenew, p);
		}
		
		
	}
	
	*/
	
	
	public static void main(String[] args) {
		String f = "/home/ming/git/BNJ3ver2/bnjv3/thinkboxx.xml";
		try {
			FileInputStream FIS = new FileInputStream(f);
			IOPlugInLoader pil = IOPlugInLoader.getInstance();
			Importer IMP = pil.GetImporterByExt(pil.GetExt(f));
			OmniFormatV1_Reader ofv1w = new OmniFormatV1_Reader();
			IMP.load(FIS, ofv1w);
			BeliefNetwork _bn = ofv1w.GetBeliefNetwork(0);
			HashSet<BeliefNode> nonObserverable = new HashSet<BeliefNode>();
			UIDSimpleSolver uid = new UIDSimpleSolver(_bn,nonObserverable);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("Successfully execute this function!");
	}
	
	
	
}