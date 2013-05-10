package edu.ksu.cis.bnj.ver3.influence.nrar;

import java.util.ArrayList;

import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.Value;
import edu.ksu.cis.bnj.ver3.core.values.Field;

public class ValueUtility implements Value {
	Value Utility;
	BeliefNode[] DecisionNodes;
	int[] DecisionValues;
	
	public ValueUtility(Value u) {
		Utility = u;
		DecisionNodes = null;
		DecisionValues = null;
		
	}
	public ValueUtility (Value u, BeliefNode[] oldnodes, int[] oldv) {
		Utility = u;
		DecisionNodes =oldnodes;
		DecisionValues  = oldv;
	}	
	public ValueUtility (Value u, BeliefNode[] oldnodes, int[] oldv, BeliefNode node, int v) {
		Utility = u;
		int l = (oldnodes == null ? 1 : oldnodes.length+1);
		DecisionNodes = new BeliefNode[l];
		DecisionValues = new int[l];
		int i = 0;
		for (i = 0; i < l-1; i++) {
			DecisionNodes[i] = oldnodes[i];
			DecisionValues[i] = oldv[i];
		}
		DecisionNodes[i] = node;
		DecisionValues[i] = v;
	}	
	
	
	public void addDecision(BeliefNode n, int v) {
		if (DecisionNodes == null) {
			DecisionNodes = new BeliefNode[1];
			DecisionValues = new int[1];
			DecisionNodes[0] = n;
			DecisionValues[0] = v;
		}
		else {
			BeliefNode[] dn_new = new BeliefNode[DecisionNodes.length+1];
			int[] dv_new = new int[dn_new.length];
			System.arraycopy(DecisionNodes, 0, dn_new, 0,DecisionNodes.length);
			dn_new[dn_new.length-1] = n;
			System.arraycopy(DecisionValues, 0, dv_new, 0,DecisionValues.length);
			dv_new[dv_new.length-1] = v;
			DecisionNodes = dn_new;
			DecisionValues = dv_new;
		}
	}
	
	public void addDecisions(BeliefNode[] nodes, int[] values) {
		if (nodes == null && values == null)
				return;
		if (DecisionNodes == null) {
			DecisionNodes = new BeliefNode[nodes.length];
			DecisionValues = new int[values.length];
			System.arraycopy(nodes, 0, DecisionNodes, 0,nodes.length);
			System.arraycopy(values, 0, DecisionValues, 0,values.length);
		}
		else {
			BeliefNode[] dn_new = new BeliefNode[DecisionNodes.length+nodes.length];
			int[] dv_new = new int[dn_new.length];
			System.arraycopy(DecisionNodes, 0, dn_new, 0,DecisionNodes.length);
			System.arraycopy(nodes, 0, dn_new, DecisionNodes.length, nodes.length);
			System.arraycopy(DecisionValues, 0, dv_new, 0,DecisionValues.length);
			System.arraycopy(values, 0, dv_new, DecisionValues.length,values.length);
			DecisionNodes = dn_new;
			DecisionValues = dv_new;
		}
	}
	
	public void add(ValueUtility v) {
		Utility = Field.add(Utility, v.getUtility());
		addDecisions(v.getNodes(), v.getValues());
	}
	
	public void set(ValueUtility v) {
		Utility = v.getUtility();
		if (v.getNodes() == null) {
			DecisionNodes = null;
			DecisionValues = null;
		}
		else {
			DecisionNodes = v.getNodes().clone();
			DecisionValues = v.getValues().clone();			
		}

	}
	
	public Value getUtility() {
		return Utility;
	}
	public void putUtility(Value v) {
		Utility = v;
	}
	public BeliefNode[] getNodes() {
		return DecisionNodes;
	}
	public int[] getValues() {
		return DecisionValues;
	}
	@Override
	public String getExpr() {
		String str = "";
		if (DecisionNodes != null) {
			for ( int i = 0; i <DecisionNodes.length; i++) {
				str += DecisionNodes[i].getName() + " = " + DecisionValues[i] + ",";
			}
		}

		// TODO Auto-generated method stub
		return ""+Utility.getExpr()+" ( "+ str + " )";
	}

}