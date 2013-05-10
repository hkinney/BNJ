/*
 * Created on Jul 12, 2004
 *
 */
package edu.ksu.cis.bnj.ver3.inference.approximate.ptreduction;

import edu.ksu.cis.bnj.ver3.core.*;
import edu.ksu.cis.util.graph.algorithms.*;
import java.util.*;


/**
 * @author Julie Thornton
 *
 */
public class PartialPolytree {
	private double score;
	private BeliefNetwork network;
	public String deleted = "";
	public Vector deletedVector = new Vector();
	
	public PartialPolytree(BeliefNetwork origNetwork) {
		network = origNetwork.copy();
		score = 0;
	}
	
	public PartialPolytree(BeliefNetwork curNetwork, double curScore) {
		network = curNetwork.copy();
		score = curScore;
	}
	
	//boolean is whether edge deletion results in a connected graph
	public boolean deleteEdge(BeliefNode sourceNode, BeliefNode sinkNode) {
		if (deleted.length() > 0) {
			deleted += ", ";
		}
		deleted += sourceNode.getName() + "->" + sinkNode.getName();
		
		BeliefNode[] nodes = network.getNodes();
		if(	!nodes[sourceNode.loc()].getName().equals(sourceNode.getName()) ||
			!nodes[sinkNode.loc()].getName().equals(sinkNode.getName()))
		{
			System.out.println("a bug jeff needs to fix in the copy");
		}
		
		CPF oldCPF = sinkNode.getCPF();
		BeliefNode[] domain = oldCPF.getDomainProduct();
		network.disconnect(nodes[sourceNode.loc()], nodes[sinkNode.loc()]);
		CPF newCPF = sinkNode.getCPF();
		updateScore(domain, oldCPF, newCPF, sourceNode.getName());
		
		if (!isConnected()) {
			return false;
		}
		
		return true;
	}
	
	private void updateScore(BeliefNode[] domain, CPF oldCPF, CPF newCPF, String delParent) {
		//find index of delParent in domain
		//find number of values delParent takes
		int delIndex = 0;
		int valCount = 0;
		for (int i = 0; i < domain.length; i++) {
			if (delParent.equals(domain[i].getName())) {
				delIndex = i;
				valCount = domain[i].getDomain().getOrder();
				break;
			}
		}
		
		//loop through all CPF entries, update score
		double tempScore = 0;
		for(int i = 0; i < oldCPF.size(); i++)
		{
			int[] q = oldCPF.realaddr2addr(i);
			int[] newQ = newCPF.getSubQuery(q, domain);
			
			double oldEntry = Double.parseDouble(oldCPF.get(i).getExpr());
			double newEntry = Double.parseDouble(newCPF.get(newQ).getExpr());

			tempScore += Math.abs(oldEntry - newEntry);
		}
		
		tempScore /= valCount;
		score += tempScore;
	}
	
	private void print(BeliefNode[] nodes) {
		for (int i = 0; i < nodes.length; i++) {
			System.out.print(nodes[i].getName() + " ");
		}
		System.out.println();
	}
	
	private boolean isConnected() {
		IsConnected conTest = new IsConnected();
		conTest.setGraph(network.getGraph());
		conTest.execute();
		
		return conTest.connected;
	}
	
	public double getScore() {
		return score;
	}
	
	public BeliefNetwork getNetwork() {
		//return network.copy();
		return network;
	}
	
	public Object clone() {
		BeliefNetwork graph = network.copy();
		PartialPolytree pt = new PartialPolytree(graph, score);
		pt.deleted = deleted;
		pt.deletedVector = (Vector) deletedVector.clone();
		
		return pt;
	}

}
