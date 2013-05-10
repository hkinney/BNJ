/**
 * 
 */
package edu.ksu.cis.bnj.ver3.inference.approximate.sampling;

import edu.ksu.cis.bnj.ver3.core.*;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.inference.Inference;
import edu.ksu.cis.util.graph.core.*;
/**
 * @author Andrew King
 *
 */
public class AISBN implements Inference {
	
	private BeliefNetwork network;
	private Graph graph;
	private Vertex[] graph_vertices;
	private BeliefNode[] nodes;
	private double[][] ICPT;

	/* (non-Javadoc)
	 * @see edu.ksu.cis.bnj.ver3.inference.Inference#getName()
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return "AIS-BN";
	}

	/* (non-Javadoc)
	 * @see edu.ksu.cis.bnj.ver3.inference.Inference#run(edu.ksu.cis.bnj.ver3.core.BeliefNetwork)
	 */
	public void run(BeliefNetwork bn) {
		network = bn;
		graph = bn.getGraph();
		nodes = bn.getNodes();
		
	}

	/* (non-Javadoc)
	 * @see edu.ksu.cis.bnj.ver3.inference.Inference#queryMarginal(edu.ksu.cis.bnj.ver3.core.BeliefNode)
	 */
	public CPF queryMarginal(BeliefNode bnode) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * builds an ICPT table for all of the nodes
	 */
	private void initializeICPT(){
		ICPT = new double[nodes.length][];
		for(int i = 0; i < nodes.length; i++){
			ICPT[i] = new double[nodes[i].getCPF().getDomainProduct().length];
			for(int c = 0; c < ICPT[i].length; c++){
				ICPT[i][c] = Double.parseDouble(nodes[i].getCPF().get(c).getExpr());
			}
		}
	}
	/* (non-Javadoc)
	 *  This method is our implementation of the frist Heuristic initializtion function 
	 */
	private void initialize1(){
		for(int i = 0; i < nodes.length; i++){
			if(nodes[i].hasEvidence()){
				//get the parent nodes
				//for each parent node, get the number of outcomes of those nodes
				//and normalize prob distribution
				BeliefNode[] parents = network.getParents(nodes[i]);
				int outcomes = nodes[i].getDomain().getOrder(); //get the number of outcomes for the evidence node
				int cpf_size = nodes[i].getCPF().size();
				int interval = outcomes / cpf_size; 
				int ev_index = ((DiscreteEvidence)(nodes[i].getEvidence())).getDirectValue();
				double threshold = 1 / (2 * outcomes);
				double ev_prob = 0.0;
				
				for(int c = 0; c < parents.length; c++){
					
				}
			}
		}	
	}
	

}
