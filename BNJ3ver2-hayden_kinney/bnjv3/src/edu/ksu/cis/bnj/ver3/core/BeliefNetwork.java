package edu.ksu.cis.bnj.ver3.core;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import edu.ksu.cis.bnj.ver3.streams.OmniFormatV1_Reader;
import edu.ksu.cis.bnj.ver3.streams.OmniFormatV1_Writer;
import edu.ksu.cis.util.graph.core.*;
/*!
 * \file BeliefNetwork.java
 * \author Jeffrey M. Barber
 */
public class BeliefNetwork
{
	private Graph	_Graph;
	private String	_Name;
	/*!
	 * Constructs an empty beliefnetwork
	 */
	public BeliefNetwork()
	{
		_Graph = new Graph();
		_Name = "bn";
	}
	/*! Constructs an empty beliefnetwork with
	 * \param[in] name The name of the new network
	 */
	public BeliefNetwork(String name)
	{
		_Graph = new Graph();
		_Name = name;
	}
	/*!
	 * get the network's backing graph
	 * \note DO NOT USE unless you
	 * \par
	 * 	   plan to copy() it and change the structure from there
	 *  or plan not to modify it
	 * \return The graph associated to the belief network
	 */
	public Graph getGraph()
	{
		return _Graph;
	}
	/*!
	 * Sets the network's name
	 * \param[in] name The new name of the network
	 */
	public void setName(String name)
	{
		_Name = name;
	}
	/*!
	 * Gets the network's name
	 * \return the name of the network
	 */
	public String getName()
	{
		return _Name;
	}
	/*! Add a belief node to this network
	 * @param[in] bnode	The node to be inserted to the graph
	 */
	public void addBeliefNode(BeliefNode bnode)
	{
		Vertex V = new Vertex(bnode.getName());
		_Graph.addVertex(V);
		V.setObject(bnode);
		bnode.setOwner(V);
	}
	/*! Connect two nodes together
	 * @param[in] parent	 parent which the bnode will be a child of
	 * @param[in] bnode  	node which will be made a child of parent
	 */
	public void connect(BeliefNode parent, BeliefNode bnode)
	{
		_Graph.addDirectedEdge(parent.getOwner(), bnode.getOwner());
		Vertex[] parents = _Graph.getParents(bnode.getOwner());
		BeliefNode[] after = new BeliefNode[parents.length + 1];
		for (int i = 0; i < parents.length; i++)
		{
			after[i + 1] = ((BeliefNode) parents[i].getObject());
		}
		after[0] = bnode;
		CPF beforeCPF = bnode.getCPF();
		bnode.setCPF(beforeCPF.expand(after).hardcopy());
		int pSize2 = bnode.getCPF().size();
	}
	/*! Disconect two nodes, inverse of connect
	 * @param[in] parent	parent which the node will be cut off from
	 * @param[in] bnode		node to be cut from parent
	 */
	public void disconnect(BeliefNode parent, BeliefNode bnode)
	{
		_Graph.removeEdge(parent.getOwner(), bnode.getOwner());
		Vertex[] parents = _Graph.getParents(bnode.getOwner());
		BeliefNode[] after = new BeliefNode[parents.length + 1];
		for (int i = 0; i < parents.length; i++)
		{
			after[i + 1] = ((BeliefNode) parents[i].getObject());
		}
		after[0] = bnode;
		CPF beforeCPF = bnode.getCPF();
		bnode.setCPF(beforeCPF.extract(after));
		bnode.getCPF().normalizeByDomain();
	}
	/*! Delete a belief from network (does disconnects)
	 * 	@param[in] bnode 	the node to be removed
	 */
	public void deleteBeliefNode(BeliefNode bnode)
	{
		BeliefNode[] P = getParents(bnode);
		BeliefNode[] C = getChildren(bnode);
		for (int i = 0; i < P.length; i++)
			disconnect(P[0], bnode);
		for (int i = 0; i < C.length; i++)
			disconnect(bnode, C[i]);
		_Graph.removeVertex(bnode.getOwner());
	}
	/*! Change a belief's nodes domain (update cpf)
	 * @param[in] bnode		the node to update
	 * @param[in] domNew 	the new domain
	 */
	public void changeBeliefNodeDomain(BeliefNode bnode, Domain domNew)
	{
		int[] domainMap = new int[bnode.getDomain().getOrder() + domNew.getOrder()];
		for (int i = 0; i < bnode.getDomain().getOrder(); i++)
		{
			domainMap[i] = -1;
			for (int k = 0; k < domNew.getOrder(); k++)
			{
				if (domNew.getName(k).equals(bnode.getDomain().getName(i)))
				{
					domainMap[i] = k;
				}
			}
		}
		for (int i = bnode.getDomain().getOrder(); i < bnode.getDomain().getOrder() + domNew.getOrder(); i++)
		{
			domainMap[i] = i;
		}
		BeliefNode[] children = getChildren(bnode);
		for (int i = 0; i < children.length; i++)
		{
			CPF mod = CPF.changeDomain(children[i].getCPF(), bnode, domNew, domainMap);
			children[i].setCPF(mod);
		}
		bnode.setCPF(CPF.changeDomain(bnode.getCPF(), bnode, domNew, domainMap));
		bnode.setDomain(domNew);
	}
	/*! Get the nodes
	 * @return An array of all the nodes in the network [such that BeliefNode::loc() = position in array]
	 */
	public BeliefNode[] getNodes()
	{
		Vertex[] V = _Graph.getVertices();
		BeliefNode[] nodes = new BeliefNode[V.length];
		for (int i = 0; i < V.length; i++)
		{
			nodes[i] = (BeliefNode) V[i].getObject();
		}
		return nodes;
	}
	/*! Get the parents of the node
	 * 
	 * @param[in] bnode The node for who we request the parents of
	 * @return	An array of all the parents of bnode
	 */
	public BeliefNode[] getParents(BeliefNode bnode)
	{
		Vertex[] vParents = _Graph.getParents(bnode.getOwner());
		if (vParents == null) return null;
		BeliefNode[] parents = new BeliefNode[vParents.length];
		for (int i = 0; i < vParents.length; i++)
		{
			parents[i] = (BeliefNode) vParents[i].getObject();
		}
		return parents;
	}
	/*! Get the children of a node
	 * 
	 * @param[in] bnode The node for who we request the children of
	 * @return	An array of all the children of bnode
	 */
	public BeliefNode[] getChildren(BeliefNode bnode)
	{
		Vertex[] vChildren = _Graph.getChildren(bnode.getOwner());
		if (vChildren == null) return null;
		BeliefNode[] children = new BeliefNode[vChildren.length];
		for (int i = 0; i < vChildren.length; i++)
		{
			children[i] = (BeliefNode) vChildren[i].getObject();
		}
		return children;
	}
	/*! Apply an ordering to the noders
	 * param[in] ordering 	The order the nodes, ordering is a permutation of [0,N-1] where N = ordering.length
	 */
	public void applyOrder(int[] ordering)
	{
		_Graph.applyOrdering(ordering);
	}
	/*! Clone this BeliefNetwork, (a structure and data clone)
	 * @return a copy of the network
	 */
	public BeliefNetwork copy()
	{
		OmniFormatV1_Reader cheat = new OmniFormatV1_Reader();
		OmniFormatV1_Writer.Write(this, cheat);
		return cheat.GetBeliefNetwork(0);
	}
	/*!
	 * \return the answer to the question
	 */
	public boolean isInfluenceDiagram()
	{
		BeliefNode[] nodes = getNodes();
		for(int i = 0; i < nodes.length; i++)
		{
			if(nodes[i].getType() != BeliefNode.NODE_CHANCE)
				return true;
		}
		return false;
	}
	
	// check whether a is ancestor of b
	public boolean isAncestor(BeliefNode a, BeliefNode b) {
		HashSet<BeliefNode> ancestors = new HashSet<BeliefNode>();
		ArrayList<BeliefNode> queue = new ArrayList<BeliefNode>();
		queue.add(b);
		while (!queue.isEmpty()) {
			
			BeliefNode head = queue.remove(0);
			BeliefNode[] parents = getParents(head);
			for ( int i = 0; i < parents.length; i++) {
				BeliefNode parent = parents[i];
				if ( parent == a) return true;
				if (!ancestors.contains(parent)) {
					queue.add(parent);
					ancestors.add(parent);
				}
			}
			
		}
		return false;
	}
	
	public BeliefNode[] ancestors(BeliefNode n) {
		HashSet<BeliefNode> ancestors = new HashSet<BeliefNode>();	
		ArrayList<BeliefNode> queue = new ArrayList<BeliefNode>();
		queue.add(n);
		while (!queue.isEmpty()) {
			BeliefNode head = queue.remove(0);
			BeliefNode[] parents = getParents(head);
			for ( int i = 0; i < parents.length; i++) {
				BeliefNode parent = parents[i];
				if (!ancestors.contains(parent)) {
					queue.add(parent);
					ancestors.add(parent);
				}
			}
		}
		BeliefNode[] ancestors_array = new BeliefNode[ancestors.size()];
		ancestors.toArray(ancestors_array);
		return ancestors_array;
	}
	
	public BeliefNode[] descendants(BeliefNode n) {
		HashSet<BeliefNode> descendants = new HashSet<BeliefNode>();	
		ArrayList<BeliefNode> queue = new ArrayList<BeliefNode>();
		queue.add(n);
		while (!queue.isEmpty()) {
			BeliefNode head = queue.remove(0);
			BeliefNode[] children = getChildren(head);
			for ( int i = 0; i < children.length; i++) {
				BeliefNode parent = children[i];
				if (!descendants.contains(parent)) {
					queue.add(parent);
					descendants.add(parent);
				}
			}
		}
		BeliefNode[] descendants_array = new BeliefNode[descendants.size()];
		descendants.toArray(descendants_array);
		return descendants_array;
	}
	
	
	public boolean isConnected(BeliefNode from, BeliefNode to) {
		BeliefNode[] to_parents = getParents(to);
		for ( int i = 0; i < to_parents.length; i++)
			if (to_parents[i] == from)
				return true;
		return false;
	}
}