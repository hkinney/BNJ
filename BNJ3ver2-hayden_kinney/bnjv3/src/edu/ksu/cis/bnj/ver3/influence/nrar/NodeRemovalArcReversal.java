package edu.ksu.cis.bnj.ver3.influence.nrar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.plugin.IOPlugInLoader;
import edu.ksu.cis.bnj.ver3.streams.Importer;
import edu.ksu.cis.bnj.ver3.streams.OmniFormatV1_Reader;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.DiscreteEvidence;
import edu.ksu.cis.bnj.ver3.core.Value;
import edu.ksu.cis.bnj.ver3.core.values.Field;
import edu.ksu.cis.bnj.ver3.core.values.ValueDouble;
import edu.ksu.cis.bnj.ver3.core.values.ValueUnity;
import edu.ksu.cis.bnj.ver3.core.values.ValueZero;
import edu.ksu.cis.bnj.ver3.inference.Inference;
import edu.ksu.cis.bnj.ver3.inference.exact.LSonline;
import edu.ksu.cis.bnj.ver3.influence.Solver;
import edu.ksu.cis.bnj.ver3.influence.nfseq.Preconditions;
import edu.ksu.cis.bnj.ver3.plugin.IOPlugInLoader;
import edu.ksu.cis.bnj.ver3.streams.Importer;
import edu.ksu.cis.bnj.ver3.streams.OmniFormatV1_Reader;
import edu.ksu.cis.util.graph.core.*;

public class NodeRemovalArcReversal {
	private BeliefNetwork	_TransformedNetwork;
	private BeliefNetwork	_OriginalNetwork;
	
	public void Show() {
		System.out.println("======================================");
		BeliefNode[] OriginalNodes = _OriginalNetwork.getNodes();
		for (int i = 0; i < OriginalNodes.length; i++) {
			BeliefNode node = OriginalNodes[i];
			System.out.println(node.getName()+ "  Type: "+node.getType());
			System.out.println("children:");
			BeliefNode[] children = _OriginalNetwork.getChildren(node);
			for (int j = 0; j < children.length; j++) {
				System.out.println(children[j].getName()+ "  Type: "+children[j].getType());
			}
			System.out.println();
			
		}
		System.out.println("======================================");
	}
	

	
	
	public void UtilityFormatChange() {
		BeliefNode[] nodes = _TransformedNetwork.getNodes();
		for ( int i = 0; i < nodes.length; i++) {
			BeliefNode node = nodes[i];
			if (node.getType() == node.NODE_UTILITY) {
				CPF cpf = node.getCPF();
				for ( int j = 0; j < cpf.size(); j++) {
					Value v = cpf.get(j);
					ValueUtility d = new ValueUtility(v);
					cpf.put(j, d);
				}
				
			}
		}
	}
	
	public void cpfUtilityFormatChange(CPF cpf) {
		for ( int j = 0; j < cpf.size(); j++) {
			Value v = cpf.get(j);
			if ( v instanceof ValueZero)
				v= new ValueDouble(0.0);
			ValueUtility d = new ValueUtility(v);
			cpf.put(j, d);
		}		
	}
	public void BarrenNodesRemoval() {

		boolean flag= true;
		while (flag) {
			flag = false;
			BeliefNode[] OriginalNodes = _OriginalNetwork.getNodes();
			for ( int i = 0; i < OriginalNodes.length; i++) {
				BeliefNode node = OriginalNodes[i];
				if ( node.getType() == node.NODE_CHANCE || node.getType() == node.NODE_DECISION) {
					BeliefNode[] children = _OriginalNetwork.getChildren(node);
					if ( children.length == 0) {
						flag = true;
						System.out.println("delete barren node:"+node.getName());
						_OriginalNetwork.deleteBeliefNode(node);
					}
				}
		
			}			
		}
		
	}
	
	
	private void utilityNodeRemove(BeliefNode[] nodes) {
		HashSet<BeliefNode> nodes_whole_set = new HashSet<BeliefNode>(Arrays.asList(nodes));
		String name_sumU = "sum of";
		for ( int i = 0; i < nodes.length; i++) {
			name_sumU += " "+nodes[i].getName();
		}
		BeliefNode sumU = new BeliefNode(name_sumU, nodes[0].getDomain());
		sumU.setType(sumU.NODE_UTILITY);
		_OriginalNetwork.addBeliefNode(sumU);
		for (int i = 0; i < nodes.length; i++) {
			BeliefNode[] parents = _OriginalNetwork.getParents(nodes[i]);	
			nodes_whole_set.addAll(Arrays.asList(parents));
			for (int j = 0; j < parents.length; j++) {
				_OriginalNetwork.connect(parents[j],sumU);
			}
		}
		nodes_whole_set.add(sumU);
		CPF cpf_sumU = sumU.getCPF();
		cpfUtilityFormatChange(cpf_sumU);
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
			_OriginalNetwork.deleteBeliefNode(nodes[i]);
		}
		Show();
	}
	
	private void chanceNodeRemove(BeliefNode node) {
		BeliefNode[] parents = _OriginalNetwork.getParents(node);
		BeliefNode[] children = _OriginalNetwork.getChildren(node);
		HashSet<BeliefNode>  parents_sumU_set = new HashSet<BeliefNode>(Arrays.asList(parents));
		String name_sumU = "sum of";
		for (int i = 0; i < children.length; i++) {
			parents_sumU_set.addAll(Arrays.asList(_OriginalNetwork.getParents(children[i])));
			name_sumU += " "+children[i].getName();
		}
		parents_sumU_set.remove(node);
		BeliefNode[] parents_U = new BeliefNode[parents_sumU_set.size()];
		parents_sumU_set.toArray(parents_U);
		
		
		BeliefNode sumU = new BeliefNode(name_sumU, children[0].getDomain());
		sumU.setType(sumU.NODE_UTILITY);
		_OriginalNetwork.addBeliefNode(sumU);
		for ( int i = 0; i < parents_U.length; i++) {
			_OriginalNetwork.connect(parents_U[i], sumU);			
		}
		
		CPF cpf_sumU = sumU.getCPF();
		cpfUtilityFormatChange(cpf_sumU);
		BeliefNode[] nodes_whole = new BeliefNode[cpf_sumU.getDomainProduct().length+1];
		System.arraycopy(cpf_sumU.getDomainProduct(), 0, nodes_whole, 0, cpf_sumU.getDomainProduct().length);
		nodes_whole[nodes_whole.length-1] = node;
		CPF cpf_whole = new CPF(nodes_whole);
		for (int i = 0; i < cpf_whole.size(); i++) {
			int[] addr_whole = cpf_whole.realaddr2addr(i);
			int[] addr_node = node.getCPF().getSubQuery(addr_whole, nodes_whole);
			int[] addr_sumU = cpf_sumU.getSubQuery(addr_whole, nodes_whole);
			Value pr_c = node.query(addr_node);

			for (int j = 0; j < children.length; j++) {
				int[] addr_childU = children[j].getCPF().getSubQuery(addr_whole, nodes_whole);
				Value childU = children[j].query(addr_childU);
				ValueUtility sumU_u = (ValueUtility)(cpf_sumU.get(addr_sumU));
				Value result = Field.add(sumU_u.getUtility(), Field.mult(pr_c, childU));
				sumU_u.putUtility(result);
				if (childU instanceof ValueUtility)
					sumU_u.addDecisions(((ValueUtility) childU).getNodes(), ((ValueUtility) childU).getValues());
			}
		}
		
		//cpf_sumU.Print();
		for ( int i = 0; i < children.length; i++) {
			_OriginalNetwork.deleteBeliefNode(children[i]);
		}
		_OriginalNetwork.deleteBeliefNode(node);
		cpf_sumU.Print();
		
	}
	
	public boolean utilityNodeCheck() {
		BeliefNode[] nodes = _OriginalNetwork.getNodes();
		HashSet<BeliefNode> utilities_set = new HashSet<BeliefNode>();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType() == nodes[i].NODE_UTILITY)
				utilities_set.add(nodes[i]);
		}
		if (utilities_set.size() > 1) {
			BeliefNode[] utilities = new BeliefNode[utilities_set.size()];
			utilities_set.toArray(utilities);
			
			System.out.println("delete utility nodes!");
			utilityNodeRemove(utilities);
			return true;
		}
		else return false;
	}
	
	
	public boolean chanceNodeCheck(BeliefNode node) {

		boolean flag;
		BeliefNode[] children = _OriginalNetwork.getChildren(node);
		flag = true;
		for (int j = 0; j < children.length; j++) {
			if (children[j].getType() != node.NODE_UTILITY) 
				flag = false;						
		}

		if (!flag) return false;
		
		System.out.println("delete chance node:"+node.getName());
		chanceNodeRemove(node);
		return true;

	}
	
	
	public void decisionNodeRemove(BeliefNode node) {
		BeliefNode[] parents = _OriginalNetwork.getParents(node);
		BeliefNode[] children = _OriginalNetwork.getChildren(node);
		HashSet<BeliefNode>  parents_sumU_set = new HashSet<BeliefNode>();
		String name_sumU = "sum of";
		for (int i = 0; i < children.length; i++) {
			parents_sumU_set.addAll(Arrays.asList(_OriginalNetwork.getParents(children[i])));
			name_sumU += " "+children[i].getName();
		}
		parents_sumU_set.remove(node);
		BeliefNode[] parents_U = new BeliefNode[parents_sumU_set.size()];
		parents_sumU_set.toArray(parents_U);
		
		
		BeliefNode sumU = new BeliefNode(name_sumU, children[0].getDomain());
		sumU.setType(sumU.NODE_UTILITY);
		_OriginalNetwork.addBeliefNode(sumU);
		for ( int i = 0; i < parents_U.length; i++) {
			_OriginalNetwork.connect(parents_U[i], sumU);			
		}
		
		CPF cpf_sumU = sumU.getCPF();
		cpfUtilityFormatChange(cpf_sumU);	
		BeliefNode[] nodes_whole = new BeliefNode[cpf_sumU.getDomainProduct().length+1];
		System.arraycopy(cpf_sumU.getDomainProduct(), 0, nodes_whole, 0, cpf_sumU.getDomainProduct().length);
		nodes_whole[nodes_whole.length-1] = node;
		CPF cpf_whole = new CPF(nodes_whole);
		int d_index = cpf_whole.getNodeIndex(node);
		
		
		for ( int i = 0; i < cpf_whole.size(); i++) {
			int[] addr_whole = cpf_whole.realaddr2addr(i);
			int[] addr_sumU = cpf_sumU.getSubQuery(addr_whole, nodes_whole);			
			int d_value = addr_whole[d_index];	

			ValueUtility v_sumU = (ValueUtility) cpf_sumU.get(addr_sumU);
			double vold = ((ValueDouble) v_sumU.getUtility()).getValue();	
			ValueUtility vnew_U = new ValueUtility(new ValueDouble(0.0));
			vnew_U.addDecision(node, d_value);			
			
			for ( int j = 0; j < children.length; j++) {
			
				int[] addr_childU = children[j].getCPF().getSubQuery(addr_whole, nodes_whole);
				int index = children[j].getCPF().getNodeIndex(children[j]);
				addr_childU[index] = 0;				
				
				ValueUtility childU = (ValueUtility) children[j].query(addr_childU);
				vnew_U.putUtility(Field.add(vnew_U, childU.getUtility()));
				vnew_U.addDecisions(childU.getNodes(), childU.getValues());
			}
			
			double vnew = ((ValueDouble)vnew_U.getUtility()).getValue();
			if (vold < vnew) {
				cpf_sumU.put(addr_sumU, vnew_U);
			}
			
			
		}		

		
		//cpf_sumU.Print();
		for ( int i = 0; i < children.length; i++) {
			_OriginalNetwork.deleteBeliefNode(children[i]);
		}
		_OriginalNetwork.deleteBeliefNode(node);	
		cpf_sumU.Print();
		
	}
	
	public boolean  decisionDisconnect(BeliefNode from, BeliefNode to) {
		if (from.getType() != from.NODE_DECISION || to.getType() != to.NODE_UTILITY)
				return false;
		HashSet<BeliefNode>  from_parents = new HashSet<BeliefNode>(Arrays.asList(_OriginalNetwork.getParents(from)));
		HashSet<BeliefNode>  to_parents = new HashSet<BeliefNode>(Arrays.asList(_OriginalNetwork.getParents(to)));
		HashSet<BeliefNode> intersect = new HashSet<BeliefNode>(from_parents);
		intersect.retainAll(to_parents);
		from_parents.removeAll(intersect);
		BeliefNode[] X = new BeliefNode[from_parents.size()];
		BeliefNode[] Y = new BeliefNode[intersect.size()];		
		BeliefNode[] Yi = new BeliefNode[to_parents.size()];
		from_parents.toArray(X);
		intersect.toArray(Y);
		to_parents.toArray(Yi);
		
		CPF to_cpf = to.getCPF();
		CPF cpf = new CPF(Y);
		cpfUtilityFormatChange(cpf);
		int index_from = to_cpf.getNodeIndex(from);
		for ( int i = 0; i < to_cpf.size(); i++) {
			int[] addr = to_cpf.realaddr2addr(i);
			int v_from = addr[index_from];
			ValueUtility v = (ValueUtility)to_cpf.get(i);
			int[] Yaddr = cpf.getSubQuery(addr, Yi);
			Value vDecision = cpf.get(Yaddr);
			if (vDecision instanceof ValueZero) {
				vDecision = new ValueUtility(v.getUtility(), null, null, from, v_from);
				cpf.put(Yaddr, vDecision);
			}
			else {
				ValueUtility voldDecision = (ValueUtility) vDecision;
				double vold = ((ValueDouble) voldDecision.getUtility()).getValue();
				double vcurr = ((ValueDouble) v.getUtility()).getValue();
				if (vold < vcurr) {
					vDecision = new ValueUtility(v.getUtility(), v.getNodes(), v.getValues(), from, v_from);
					cpf.put(Yaddr, vDecision);
				}
				
			}
			
		}
		cpf.Print();
		_OriginalNetwork.disconnect(from, to);
		return true;

	}
	
	
	
	public void ArcReversal(BeliefNode from, BeliefNode to) {

		
		HashSet<BeliefNode>  from_parents = new HashSet<BeliefNode>(Arrays.asList(_OriginalNetwork.getParents(from)));
		HashSet<BeliefNode>  to_parents = new HashSet<BeliefNode>(Arrays.asList(_OriginalNetwork.getParents(to)));
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
		
		_OriginalNetwork.disconnect(from, to);
		_OriginalNetwork.connect(to, from);
		for ( int i = 0; i < X.length; i++)
			_OriginalNetwork.connect(X[i], to);
		for (int i = 0; i < Z.length; i++)
			_OriginalNetwork.connect(Z[i], from);
		
		from.setCPF(from_cpf_new);
		to.setCPF(to_cpf_new);
	}
	
	
	public void NRAR_process() {

		UtilityFormatChange();

		mainloop: 		
		while (true) {

			BarrenNodesRemoval();
			BeliefNode[] nodes = _OriginalNetwork.getNodes();
		
			if (nodes.length == 1)
				if (nodes[0].getType() == nodes[0].NODE_UTILITY)
					break;
			
			for (int i = 0; i < nodes.length; i++) {
				BeliefNode node = nodes[i];
				if (node.getType() == node.NODE_CHANCE) {
					if (chanceNodeCheck(node))
						continue mainloop;
				}
			    if (node.getType() == node.NODE_DECISION) {
					if (decisionNodeCheck(node)) {
						BarrenNodesRemoval();
						continue mainloop;

					}						
				}	
			    
			}
			if (arcReverseCheck()) continue mainloop;	
			if (utilityNodeCheck()) continue mainloop;	
			break;
		}
		

		
	}

	
	public boolean arcReverseCheck() {
		BeliefNode[] nodes = _OriginalNetwork.getNodes();
		HashSet<BeliefNode> candidatesSet = new HashSet<BeliefNode> ();
		for (int i = 0; i < nodes.length; i++) {
			BeliefNode node = nodes[i];
			if (node.getType() == node.NODE_UTILITY) {
				BeliefNode[] parents = _OriginalNetwork.getParents(node);
				for ( int j = 0; j < parents.length; j++) {
					BeliefNode parent = parents[j];
					if (parent.getType() == parent.NODE_CHANCE) {
						boolean isCandidate = true;
						BeliefNode[] parent_children = _OriginalNetwork.getChildren(parent);
						for (int k = 0; k < parent_children.length; k++) {
							if (parent_children[k].getType() == node.NODE_DECISION ) {
								isCandidate = false;
								break;
							}
								
						}
						if (isCandidate)
							candidatesSet.add(parent);
					}
				}
			}
		}
		
		if (candidatesSet.isEmpty())
			return false;
		BeliefNode[] candidates = new BeliefNode[candidatesSet.size()];
		candidatesSet.toArray(candidates);
		for ( int i = 0; i < candidates.length; i++) {
			BeliefNode from = candidates[i];
			BeliefNode[] children = _OriginalNetwork.getChildren(from);
			for ( int j = 0; j < children.length; j++) {
				BeliefNode to = children[j];
				if (to.getType() == to.NODE_CHANCE) {
					if (!_OriginalNetwork.isAncestor(to, from)) {
						System.out.println("reverse nodes: "+from.getName()+" "+to.getName());
						ArcReversal(from, to);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean decisionNodeCheck(BeliefNode node) {
		BeliefNode[] ancestors = _OriginalNetwork.ancestors(node);
		HashSet<BeliefNode> ancestors_set = new HashSet<BeliefNode>(Arrays.asList(ancestors));
		
		BeliefNode[] children = _OriginalNetwork.getChildren(node);
		for ( int i = 0; i < children.length; i++ ) {
			BeliefNode child = children[i];
			if (child.getType() != node.NODE_UTILITY) 
				return false;
			BeliefNode[] child_parents = _OriginalNetwork.getParents(child);
			for ( int j = 0; j < child_parents.length; j++) {
				BeliefNode child_parent = child_parents[j];
				if ( child_parent != node && !ancestors_set.contains(child_parent))
					return false;
			}
			
		}
		System.out.println("delete decision node: "+node.getName());
		decisionNodeRemove(node);
		
		return true;
		
	}
	
	
	
	

	
	public void nodeDisconnect(BeliefNode from, BeliefNode to) {
		CPF from_cpf = from.getCPF();
		CPF to_cpf = to.getCPF();
		HashSet<BeliefNode>  from_parents = new HashSet<BeliefNode>(Arrays.asList(_OriginalNetwork.getParents(from)));
		HashSet<BeliefNode>  to_parents = new HashSet<BeliefNode>(Arrays.asList(_OriginalNetwork.getParents(to)));
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
		BeliefNode[] Whole = new BeliefNode[X.length+Y.length+Z.length+1];
		Whole[0] = to;
		System.arraycopy(X, 0, Whole, 1, X.length);
		System.arraycopy(Y, 0, Whole, X.length+1, Y.length);
		System.arraycopy(Z, 0, Whole, X.length+Y.length+1, Z.length);

		BeliefNode[] Whole_iter = new BeliefNode[Whole.length+1];
		System.arraycopy(Whole, 0, Whole_iter, 0, Whole.length);
		Whole_iter[Whole.length] = from;
		
		CPF cpf = new CPF(Whole);
		cpfUtilityFormatChange(cpf);
		CPF cpf_iter = new CPF(Whole_iter);
		for ( int i = 0; i < cpf_iter.size(); i++) {
			int[] addr = cpf_iter.realaddr2addr(i);
			int[] XYi = from_cpf.getSubQuery(addr, Whole_iter);
			Value pr = from_cpf.get(XYi);
			int[] YZiv = to_cpf.getSubQuery(addr, Whole_iter);
			Value v = (ValueDouble) ((ValueUtility)to_cpf.get(YZiv)).getUtility();
			int[] XYZv = cpf.getSubQuery(addr, Whole_iter);
			ValueUtility vv_u = (ValueUtility)cpf.get(XYZv);
			Value vv = (ValueDouble) vv_u.getUtility();
			Value result = Field.add(vv, Field.mult(pr, v));
			ValueUtility vnew = new ValueUtility(result, vv_u.getNodes(), vv_u.getValues());
			cpf.put(XYZv, vnew);

		}
		cpf.Print();
		_OriginalNetwork.disconnect(from, to);
		BeliefNode[] parents = _OriginalNetwork.getParents(from);
		for ( int i = 0; i < parents.length; i++) {
			_OriginalNetwork.connect(parents[i], to);
		}
		to.setCPF(cpf);
	}
	
	
	
	
	public void run(BeliefNetwork _bn) {
	
		_OriginalNetwork = _bn;
		Show();
		BeliefNode[] Nodes = _OriginalNetwork.getNodes();
		for ( int i = 0; i < Nodes.length; i++) {
			System.out.println(Nodes[i].getName());
			Nodes[i].getCPF().Print();
		}
		
		
		
		
		NRAR_process();
		Show();
		
		Nodes = _OriginalNetwork.getNodes();
		for ( int i = 0; i < Nodes.length; i++) {
			System.out.println(Nodes[i].getName());
			Nodes[i].getCPF().Print();
		}
		/*		
		BeliefNode[] Nodes = _OriginalNetwork.getNodes();
		
		UtilityFormatChange();
		chanceNodeRemove(Nodes[0]);
		Show();
		
		Nodes = _OriginalNetwork.getNodes();
		System.out.println(Nodes[0].getName());
		decisionNodeRemove(Nodes[0]);
		Show();
		Nodes = _OriginalNetwork.getNodes();
		System.out.println(Nodes[0].getName());
		decisionNodeRemove(Nodes[0]);
		Show();

		UtilityFormatChange();
		BarrenNodesRemoval();
		//ChanceNodeRemoval();
		BeliefNode[] Nodes = _OriginalNetwork.getNodes();
		decisionDisconnect(Nodes[0], Nodes[1]);
		Show();
		decisionDisconnect(Nodes[2], Nodes[1]);
		Show();
		/*
		BeliefNode[] Nodes = _OriginalNetwork.getNodes();
		nodeDisconnect(Nodes[0], Nodes[2]);
		Nodes[2].getCPF().Print();
		Show();
		*/
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		String f = "/home/ming/git/BNJ3ver2/bnjv3/thinkboxx.xml";
		try {
			FileInputStream FIS = new FileInputStream(f);
			IOPlugInLoader pil = IOPlugInLoader.getInstance();
			Importer IMP = pil.GetImporterByExt(pil.GetExt(f));
			OmniFormatV1_Reader ofv1w = new OmniFormatV1_Reader();
			IMP.load(FIS, ofv1w);
			BeliefNetwork _bn = ofv1w.GetBeliefNetwork(0);
			NodeRemovalArcReversal nrar = new NodeRemovalArcReversal();
			nrar.run(_bn);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("Successfully execute this function!");
	}
}