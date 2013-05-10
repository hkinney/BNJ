package edu.ksu.cis.bnj.ver3.influence.nrar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;

import edu.ksu.cis.bnj.ver3.core.*;
import edu.ksu.cis.bnj.ver3.core.values.Field;
import edu.ksu.cis.bnj.ver3.core.values.ValueDouble;
import edu.ksu.cis.bnj.ver3.core.values.ValueZero;
import edu.ksu.cis.bnj.ver3.influence.Solver;
import edu.ksu.cis.bnj.ver3.plugin.IOPlugInLoader;
import edu.ksu.cis.bnj.ver3.streams.Importer;
import edu.ksu.cis.bnj.ver3.streams.OmniFormatV1_Reader;
import edu.ksu.cis.util.graph.visualization.VisualizationController;
import edu.ksu.cis.util.graph.visualization.operators.*;

public class VariableElimination implements Solver
{
	private BeliefNetwork _OriginalNetwork;
	private BeliefNetwork _TransformedNetwork;
	private VisualizationController _VC;
	
	public VariableElimination(BeliefNetwork bn) {
		_OriginalNetwork = bn;
		_TransformedNetwork  = bn;
		_VC = new VisualizationController(bn.getGraph());
	}
	
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
	
	
	public void utilityFormatChange() {
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
	
	public void cpfUtilityFormatChange(CPF cpf, Value initialValue) {
		for ( int j = 0; j < cpf.size(); j++) {
			Value v = cpf.get(j);
			if ( v instanceof ValueZero)
				v= initialValue;
			ValueUtility d = new ValueUtility(v);
			cpf.put(j, d);
		}		
	}
	
	public void barrenNodesRemoval() {
		boolean flag= true;
		while (flag) {
			flag = false;
			BeliefNode[] OriginalNodes = _TransformedNetwork.getNodes();
			for ( int i = 0; i < OriginalNodes.length; i++) {
				BeliefNode node = OriginalNodes[i];
				if ( node.getType() == node.NODE_CHANCE || node.getType() == node.NODE_DECISION) {
					BeliefNode[] children = _TransformedNetwork.getChildren(node);
					if ( children.length == 0) {
						flag = true;
						System.out.println("delete barren node:"+node.getName());
						//Delete the barren node...
						_VC.pushAndApplyOperator(new NodeDeletion(node));
						//Delete the node from the network.
						_TransformedNetwork.deleteBeliefNode(node);
					}
				}
			}			
		}	
	}
	
	public void chanceNodeRemoval(BeliefNode c, BeliefNode v) {
		BeliefNode[] parents_c = _TransformedNetwork.getParents(c);
		BeliefNode[] parents_v = _TransformedNetwork.getParents(v);
		
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
			// connect all parents of chance node c to utility node v
			
			_TransformedNetwork.connect(parents_c[i], v);
		// delete chance node c
		
		_TransformedNetwork.deleteBeliefNode(c);
		v.setCPF(vnew_cpf);
		
	}
	
	public void decisionNodeRemoval(BeliefNode d, BeliefNode v) {
		BeliefNode[] parents_d = _TransformedNetwork.getParents(d);
		BeliefNode[] parents_v = _TransformedNetwork.getParents(v);
		
		HashSet<BeliefNode> whole_set = new HashSet<BeliefNode>();
		whole_set.addAll(Arrays.asList(parents_v));
		whole_set.add(v);	
		whole_set.remove(d);
		BeliefNode[] vnew = new BeliefNode[whole_set.size()];
		whole_set.toArray(vnew);
		CPF vnew_cpf = new CPF(vnew);
		cpfUtilityFormatChange(vnew_cpf, new ValueDouble(Double.NEGATIVE_INFINITY));
				
		whole_set.addAll(Arrays.asList(parents_d));
		whole_set.add(d);	
		BeliefNode[] whole = new BeliefNode[whole_set.size()];
		whole_set.toArray(whole);
		CPF whole_cpf = new CPF(whole);
		
		CPF v_cpf = v.getCPF();
		int d_index = whole_cpf.getNodeIndex(d);
		int index = v_cpf.getNodeIndex(v);
		
		for (int i = 0; i < whole_cpf.size(); i++) {
			int[] whole_addr = whole_cpf.realaddr2addr(i);
			int[] v_addr = v_cpf.getSubQuery(whole_addr, whole);
			int[] vnew_addr = vnew_cpf.getSubQuery(whole_addr, whole);
			int d_value = whole_addr[d_index];	
			v_addr[index] = 0;	
			
			ValueUtility v_utility = (ValueUtility)v_cpf.get(v_addr);
			ValueUtility vnew_utility = (ValueUtility)vnew_cpf.get(vnew_addr);
			double v_value = ((ValueDouble) v_utility.getUtility()).getValue();	
			double vnew_value = ((ValueDouble) vnew_utility.getUtility()).getValue();	
			if (v_value > vnew_value) {
				vnew_utility.set(v_utility);
				vnew_utility.addDecision(d, d_value);
				vnew_cpf.put(vnew_addr, vnew_utility);
			}
		}
		//delete decision node d
		_TransformedNetwork.deleteBeliefNode(d);
		v.setCPF(vnew_cpf);	
	}
	
	public void arcReversal(BeliefNode from, BeliefNode to) {

		
		HashSet<BeliefNode>  from_parents = new HashSet<BeliefNode>(Arrays.asList(_TransformedNetwork.getParents(from)));
		HashSet<BeliefNode>  to_parents = new HashSet<BeliefNode>(Arrays.asList(_TransformedNetwork.getParents(to)));
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
		
		//disconnect edge from "from" to "to"
		_TransformedNetwork.disconnect(from, to);
		//connect edge from "to" to "from"
		_TransformedNetwork.connect(to, from);
		for ( int i = 0; i < X.length; i++)
			// connect parents of "from" to "to"
			_TransformedNetwork.connect(X[i], to);
		for (int i = 0; i < Z.length; i++)
			// connect parents of "to" to "from"
			_TransformedNetwork.connect(Z[i], from);
		
		from.setCPF(from_cpf_new);
		to.setCPF(to_cpf_new);
	}
	
	private void utilityNodesComb(BeliefNode[] nodes) {
		HashSet<BeliefNode> nodes_whole_set = new HashSet<BeliefNode>(Arrays.asList(nodes));
		String name_sumU = "sum of";
		for ( int i = 0; i < nodes.length; i++) {
			name_sumU += " "+nodes[i].getName();
		}
		BeliefNode sumU = new BeliefNode(name_sumU, nodes[0].getDomain());
		sumU.setType(sumU.NODE_UTILITY);
		// add one utility node of sum of all utility nodes
		_TransformedNetwork.addBeliefNode(sumU);
		for (int i = 0; i < nodes.length; i++) {
			BeliefNode[] parents = _OriginalNetwork.getParents(nodes[i]);	
			nodes_whole_set.addAll(Arrays.asList(parents));
			for (int j = 0; j < parents.length; j++) {
				//connect parents of all utility nodes to this new one
				_TransformedNetwork.connect(parents[j],sumU);
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
			//delete all old utility nodes
			_TransformedNetwork.deleteBeliefNode(nodes[i]);
		}
		//Show();
	}
	
	
	public boolean chanceNodeCheck(BeliefNode node) {
		BeliefNode[] children = _TransformedNetwork.getChildren(node);
		if (children.length != 1)
			return false;
		if (children[0].getType() != node.NODE_UTILITY)
			return false;
				
		System.out.println("delete chance node:"+node.getName());
		chanceNodeRemoval(node, children[0]);
		return true;
	}
	
	public boolean decisionNodeCheck(BeliefNode node) {
		BeliefNode[] ancestors = _TransformedNetwork.ancestors(node);
		HashSet<BeliefNode> ancestors_set = new HashSet<BeliefNode>(Arrays.asList(ancestors));
		
		BeliefNode[] children = _TransformedNetwork.getChildren(node);
		if (children.length != 1)
			return false;
		if (children[0].getType() != node.NODE_UTILITY)
			return false;
		
		BeliefNode[] child_parents = _TransformedNetwork.getParents(children[0]);
		for ( int j = 0; j < child_parents.length; j++) {
			BeliefNode child_parent = child_parents[j];
			if ( child_parent != node && !ancestors_set.contains(child_parent))
				return false;
		}	
		
		System.out.println("delete decision node: "+node.getName());
		decisionNodeRemoval(node, children[0]);		
		return true;		
	}
	
	public boolean arcReverseCheck() {
		BeliefNode[] nodes = _TransformedNetwork.getNodes();
		HashSet<BeliefNode> candidatesSet = new HashSet<BeliefNode> ();
		for (int i = 0; i < nodes.length; i++) {
			BeliefNode node = nodes[i];
			if (node.getType() == node.NODE_UTILITY) {
				BeliefNode[] parents = _TransformedNetwork.getParents(node);
				for ( int j = 0; j < parents.length; j++) {
					BeliefNode parent = parents[j];
					if (parent.getType() == parent.NODE_CHANCE) {
						boolean isCandidate = true;
						BeliefNode[] parent_children = _TransformedNetwork.getChildren(parent);
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
			BeliefNode[] children = _TransformedNetwork.getChildren(from);
			for ( int j = 0; j < children.length; j++) {
				BeliefNode to = children[j];
				if (to.getType() == to.NODE_CHANCE) {
					if (!_TransformedNetwork.isAncestor(to, from)) {
						System.out.println("reverse nodes: "+from.getName()+" "+to.getName());
						arcReversal(from, to);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean utilityNodeCheck() {
		BeliefNode[] nodes =  _TransformedNetwork.getNodes();
		HashSet<BeliefNode> utilities_set = new HashSet<BeliefNode>();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType() == nodes[i].NODE_UTILITY)
				utilities_set.add(nodes[i]);
		}
		if (utilities_set.size() > 1) {
			BeliefNode[] utilities = new BeliefNode[utilities_set.size()];
			utilities_set.toArray(utilities);
			
			System.out.println("combine utility nodes:"+Arrays.toString(utilities));
			utilityNodesComb(utilities);
			return true;
		}
		else return false;
	}
	
	public void process() {
		utilityFormatChange();

		mainloop: 		
		while (true) {

			barrenNodesRemoval();
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
						barrenNodesRemoval();
						continue mainloop;

					}						
				}	
			    
			}
			if (arcReverseCheck()) continue mainloop;	
			if (utilityNodeCheck()) continue mainloop;	
			break;
		}		
	}
	
	public void run() {
		Show();
		BeliefNode[] Nodes = _TransformedNetwork.getNodes();
		for ( int i = 0; i < Nodes.length; i++) {
			System.out.println(Nodes[i].getName());
			Nodes[i].getCPF().Print();
		}
		
		process();
		
		Show();	
		Nodes = _TransformedNetwork.getNodes();
		for ( int i = 0; i < Nodes.length; i++) {
			System.out.println(Nodes[i].getName());
			Nodes[i].getCPF().Print();
		}
		System.out.println("======================================");				
	}
	
	public static void main(String[] args) {
		String f = "Prisoner's Dilemma.xml";
		try {
			FileInputStream FIS = new FileInputStream(f);
			IOPlugInLoader pil = IOPlugInLoader.getInstance();
			Importer IMP = pil.GetImporterByExt(pil.GetExt(f));
			OmniFormatV1_Reader ofv1w = new OmniFormatV1_Reader();
			IMP.load(FIS, ofv1w);
			BeliefNetwork _bn = ofv1w.GetBeliefNetwork(0);
			VariableElimination ve = new VariableElimination(_bn);
			ve.run();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("Successfully execute this function!");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Node Removal and Arc Reversal Algorithm";
	}

	@Override
	public void solve(BeliefNetwork bn) {
		// TODO Auto-generated method stub
		VariableElimination ve = new VariableElimination(bn);
		ve.run();
	}

	@Override
	public CPF queryMarginal(BeliefNode bnode) {
		// TODO Auto-generated method stub
		return bnode.getCPF();
	}
}