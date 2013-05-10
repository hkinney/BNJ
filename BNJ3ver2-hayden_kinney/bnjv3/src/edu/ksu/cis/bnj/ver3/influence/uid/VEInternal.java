package edu.ksu.cis.bnj.ver3.influence.uid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.Value;
import edu.ksu.cis.bnj.ver3.core.values.Field;
import edu.ksu.cis.bnj.ver3.core.values.ValueZero;
import edu.ksu.cis.bnj.ver3.influence.nrar.ValueUtility;

public class VEInternal 
{
	public void cpfUtilityFormatChange(CPF cpf, Value initialValue) {
		for ( int j = 0; j < cpf.size(); j++) {
			Value v = cpf.get(j);
			if ( v instanceof ValueZero)
				v= initialValue;
			ValueUtility d = new ValueUtility(v);
			cpf.put(j, d);
		}		
	}
	
	public void chanceNodeRemoval(BeliefNetwork network, BeliefNode c, BeliefNode v) {
		BeliefNode[] parents_c = network.getParents(c);
		BeliefNode[] parents_v = network.getParents(v);
		
		HashSet<BeliefNode> whole_set = new HashSet<BeliefNode>();	
		whole_set.addAll(Arrays.asList(parents_c));
		whole_set.addAll(Arrays.asList(parents_v));
		whole_set.add(v);	
		whole_set.remove(c);
		BeliefNode[] vnew = new BeliefNode[whole_set.size()];
		whole_set.toArray(vnew);
		CPF vnew_cpf = new CPF(vnew);
		cpfUtilityFormatChange(vnew_cpf, new ValueZero());
		
		whole_set.add(c);		
		BeliefNode[] whole = new BeliefNode[whole_set.size()];
		whole_set.toArray(whole);
		CPF whole_cpf = new CPF(whole);		
		CPF v_cpf = v.getCPF();
		CPF c_cpf = c.getCPF();	
		
		for (int i = 0; i < whole_cpf.size(); i++) {
			int[] whole_addr = whole_cpf.realaddr2addr(i);
			int[] c_addr = c_cpf.getSubQuery(whole_addr, whole);
			int[] v_addr = v_cpf.getSubQuery(whole_addr, whole);
			int[] vnew_addr = vnew_cpf.getSubQuery(whole_addr, whole);		
			
			Value pr = c_cpf.get(c_addr);
			ValueUtility vv = (ValueUtility)v_cpf.get(v_addr);
			ValueUtility vvnew = (ValueUtility)vnew_cpf.get(vnew_addr);
			Value result = Field.add(vvnew, Field.mult(pr, vv));
			vvnew.putUtility(result);
			if (vvnew.getUtility() instanceof ValueZero) {
				vvnew.addDecisions(vv.getNodes(), vv.getValues());
			}		
		}
		
		for (int i = 0; i < parents_c.length; i++)
			network.connect(parents_c[i], v);
		network.deleteBeliefNode(c);
		v.setCPF(vnew_cpf);
		
	}
	
public void arcReversal(BeliefNetwork network, BeliefNode from, BeliefNode to) {

		
		HashSet<BeliefNode>  from_parents = new HashSet<BeliefNode>(Arrays.asList(network.getParents(from)));
		HashSet<BeliefNode>  to_parents = new HashSet<BeliefNode>(Arrays.asList(network.getParents(to)));
		HashSet<BeliefNode> intersect = new HashSet<BeliefNode>(from_parents);
		intersect.retainAll(to_parents);
		from_parents.removeAll(intersect);
		to_parents.removeAll(intersect);
		to_parents.remove(from);
		

		BeliefNode[] X = new BeliefNode[from_parents.size()];
		BeliefNode[] Y = new BeliefNode[intersect.size()];
		BeliefNode[] Z = new BeliefNode[to_parents.size()];
		from_parents.toArray(X);
		intersect.toArray(Y);
		to_parents.toArray(Z);
		
		CPF from_cpf = from.getCPF();
		CPF to_cpf = to.getCPF();
		
		BeliefNode[] XYZj = new BeliefNode[X.length+Y.length+Z.length+1] ;
		BeliefNode[] XYZij = new BeliefNode[X.length+Y.length+Z.length+2];
		
		XYZj[0] = to;
		XYZij[0] = from; XYZij[1] = to;
		System.arraycopy(X, 0, XYZj, 1, X.length);
		System.arraycopy(Y, 0, XYZj, X.length+1, Y.length);
		System.arraycopy(Z, 0, XYZj, X.length+Y.length+1, Z.length);
		System.arraycopy(X, 0, XYZij, 2, X.length);
		System.arraycopy(Y, 0, XYZij, X.length+2, Y.length);
		System.arraycopy(Z, 0, XYZij, X.length+Y.length+2, Z.length);	
		
		CPF to_cpf_new = new CPF(XYZj);
		CPF from_cpf_new = new CPF(XYZij);
		for ( int i = 0; i < from_cpf_new.size(); i++ ) {
			int[] addr = from_cpf_new.realaddr2addr(i);
			int[] addr_jXYZ = to_cpf_new.getSubQuery(addr, XYZij);
			Value v_old = to_cpf_new.get(addr_jXYZ);
			int[] addr_jiYZ = to_cpf.getSubQuery(addr, XYZij);
			Value pr_jiYZ = to_cpf.get(addr_jiYZ);
			int[] addr_iXY = from_cpf.getSubQuery(addr, XYZij);
			Value pr_iXY = from_cpf.get(addr_iXY);
			Value v_new = Field.add(v_old, Field.mult(pr_jiYZ, pr_iXY));
			to_cpf_new.put(addr_jXYZ, v_new);
		}
		
		
		for ( int i = 0; i < from_cpf_new.size(); i++ ) {
			int[] addr = from_cpf_new.realaddr2addr(i);
			int[] addr_jiYZ = to_cpf.getSubQuery(addr, XYZij);
			Value pr_jiYZ = to_cpf.get(addr_jiYZ);			
			int[] addr_iXY = from_cpf.getSubQuery(addr, XYZij);
			Value pr_iXY = from_cpf.get(addr_iXY);			
			int[] addr_jXYZ = to_cpf_new.getSubQuery(addr, XYZij);
			Value pr_jXYZ = to_cpf_new.get(addr_jXYZ);			
			Value v_new = Field.divide(Field.mult(pr_jiYZ, pr_iXY), pr_jXYZ);
			from_cpf_new.put(addr, v_new);
			
		}
		
		network.disconnect(from, to);
		network.connect(to, from);
		for ( int i = 0; i < X.length; i++)
			network.connect(X[i], to);
		for (int i = 0; i < Z.length; i++)
			network.connect(Z[i], from);
		
		from.setCPF(from_cpf_new);
		to.setCPF(to_cpf_new);
	}

	private void utilityNodesComb(BeliefNetwork network, BeliefNode[] nodes) {
		HashSet<BeliefNode> nodes_whole_set = new HashSet<BeliefNode>(Arrays.asList(nodes));
		String name_sumU = "sum of";
		for ( int i = 0; i < nodes.length; i++) {
			name_sumU += " "+nodes[i].getName();
		}
		BeliefNode sumU = new BeliefNode(name_sumU, nodes[0].getDomain());
		sumU.setType(sumU.NODE_UTILITY);
		network.addBeliefNode(sumU);
		for (int i = 0; i < nodes.length; i++) {
			BeliefNode[] parents = network.getParents(nodes[i]);	
			nodes_whole_set.addAll(Arrays.asList(parents));
			for (int j = 0; j < parents.length; j++) {
				network.connect(parents[j],sumU);
			}
		}
		nodes_whole_set.add(sumU);
		CPF cpf_sumU = sumU.getCPF();
		cpfUtilityFormatChange(cpf_sumU, new ValueZero());
		BeliefNode[] nodes_whole = new BeliefNode[nodes_whole_set.size()];
		nodes_whole_set.toArray(nodes_whole);
		CPF cpf_whole = new CPF(nodes_whole);
		for (int i = 0; i < cpf_whole.size(); i++) {
			int[] addr_whole = cpf_whole.realaddr2addr(i);
			int[] addr_sumU = cpf_sumU.getSubQuery(addr_whole, nodes_whole);
			ValueUtility sumU_u = (ValueUtility)cpf_sumU.get(addr_sumU);
			
			
			for (int j = 0; j < nodes.length; j++) {
				BeliefNode node = nodes[j];
				int[] addr_node = node.getCPF().getSubQuery(addr_whole, nodes_whole);
				ValueUtility v = (ValueUtility)node.query(addr_node);
				sumU_u.add(v);
			}
		}
		
		for ( int i = 0; i < nodes.length; i++) {
			network.deleteBeliefNode(nodes[i]);
		}
		//Show();
	}


	public void nonObNodeRemove(BeliefNetwork network, BeliefNode node) {
		BeliefNode[] children = network.getChildren(node);
		if (children.length == 0) {
			network.deleteBeliefNode(node);
			return;
		}
		HashSet<BeliefNode> decision = new HashSet<BeliefNode>();
		HashSet<BeliefNode> chance = new HashSet<BeliefNode>();
		HashSet<BeliefNode> utility = new HashSet<BeliefNode>();
		for(int i = 0; i < children.length; i++) {
			if (children[i].getType() == children[i].NODE_CHANCE)
				chance.add(children[i]);
			if (children[i].getType() == children[i].NODE_DECISION)
				decision.add(children[i]);
			if (children[i].getType() == children[i].NODE_UTILITY)
				utility.add(children[i]);
		}
		
		if (!decision.isEmpty()) {
			System.out.println("Nonobservable node should not have decision node as its child!");
			throw new NoSuchElementException();
		}
		if (!chance.isEmpty()) {
			for (BeliefNode n: chance) 
				arcReversal(network, node, n);
		}
		if (utility.size() == 1) {
			assert (children.length == 1);
			chanceNodeRemoval(network, node, children[0]) ;
			return;
		}
		else {
			BeliefNode[] u_nodes = new BeliefNode[utility.size()];
			utility.toArray(u_nodes);
			utilityNodesComb(network, u_nodes);
			children = network.getChildren(node);
			assert (children.length == 1);
			chanceNodeRemoval(network, node, children[0]);
			return;
		}
		
	}

	
}