/*
 * Created on Jul 22, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package edu.ksu.cis.bnj.ver3.inference.approximate.sampling;

import java.util.Vector;

/**
 * @author aking
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BBNLifted {



	  private String networkName;  // network Name
	  private Vector nodeList;     // Vector of Nodes

	  /**
	   * BBN creates the network and initializes the nodes.
	   */
	  public BBNLifted() {
		networkName = new String("Anonymous Network");
		nodeList = new Vector();
	  }

	  public BBNLifted(BBNLifted all) {
		  networkName = new String("Anonymous Network");
		  nodeList = new Vector();
		  for(int i=0;i<all.size();i++){
			  this.nodeList.addElement(all.getNodeAt(i));
		  }
	  }

	  public void setNetwork(BBNLifted all){
		nodeList = new Vector();
		  for(int i=0;i<all.size();i++){
			  this.nodeList.addElement(all.getNodeAt(i));
		  }
	  }

	  public BBNLifted copy(){
		BBNLifted network = new BBNLifted();
		Vector newNodes = new Vector(nodeList.size());
		network.setName(networkName);
		network.setNodeList(newNodes);
		for (int i = 0; i < nodeList.size(); i++){
			newNodes.add(null);
		}

		for (int i = 0; i < nodeList.size(); i++){
		  NodeLifted node = ((NodeLifted) nodeList.elementAt(i));

		  NodeLifted nodeCopy = network.getNodeAt(i);

		  if (nodeCopy==null){
			nodeCopy = node.copy(network);
			newNodes.setElementAt(node, i);
		  }

		  nodeCopy.setParentList(new Vector(node.numParents()));
		  nodeCopy.setParentNames(new Vector(node.numParents()));
		  nodeCopy.setChildrenList(new Vector(node.numChildren()));

		  for (int p = 0; p < node.numParents(); p++){
			int parentIndex = indexOf(node.getParent(p));

			if (network.getNodeAt(parentIndex) == null){
			  network.setNodeAt(parentIndex, getNodeAt(parentIndex).copy(network));
			}
			nodeCopy.addParent(network.getNodeAt(parentIndex));
		  }

		  for (int c = 0; c < node.numChildren(); c++){
			int childIndex = indexOf(node.getChild(c));

			if (network.getNodeAt(childIndex) == null){
			  network.setNodeAt(childIndex, getNodeAt(childIndex).copy(network));
			}
			nodeCopy.addChild(network.getNodeAt(childIndex));
		  }

		}
		return network;
	  }

	  public BBNLifted copyShell(){
		BBNLifted network = new BBNLifted();
		for (int i = 0; i < size(); i++){
			NodeLifted original = getNodeAt(i);
			NodeLifted node = new NodeLifted(network);
			node.setName(original.getName());
			for (int j = 0; j < original.getStateNames().size(); j++){
				node.addValue(original.getStateName(j));
			}
//			network.addNode(getNodeAt(i).copy(network));

					node.setX(original.getX());
					node.setY(original.getY());
					network.addNode(node);
		}
		return network;
	  }

	  public void setName(String networkName){
		this.networkName = networkName;
	  }

	  public void setNodeAt(int index, NodeLifted node){
		nodeList.setElementAt(node, index);
	  }

	  public void setNodeList(Vector nodeList){
		this.nodeList = nodeList;
	  }

	  public void report(){
		for (int i = 0; i < size(); i++){
			getNodeAt(i).print();
			getNodeAt(i).printStateNames();
			System.out.println("--------");
		}
	  }

	  public void reportVerbose(){
		for (int i = 0; i < size(); i++){
			NodeLifted node = getNodeAt(i);
			System.out.println("Node " + i + ":");
			node.printVerbose();
		}
	  }
	
/**
   * getNodeAt will return a node at the specified index.
   * Throws BBNE_OutOfBounds exception if the index is out of bounds.
   * @param index - the index of the node being requested.
   * @return Node - the node at the specified index.
   *
   */
  public NodeLifted getNodeAt(int index) {
	/*try{
	  return (Node) nodeList.elementAt(index);
	}
	catch(java.lang.IndexOutOfBoundsException e){
	  throw new BBNE_OutOfBounds();
	}*/

	return (NodeLifted) nodeList.elementAt(index);
  }

  /**
   * getNodes returns a vector containing all of the node objects in the
   * network.
   *
   * @return Vector - the vector of Node objects.
   */
  public Vector getNodes(){
	return nodeList;
  }

  /**
   * getNodeByName will return the Node object matching the specified string
   * name.  Throws BBNE_NodeNotFound exception if the node is not found.
   *
   * @param nodeName - the name of the node to be searched for
   * @return Node - the node found
   */
  public NodeLifted getNodeByName(String nodeName) {
	int length = nodeList.size();
	for (int i = 0; i < length; i++){
	  NodeLifted node = (NodeLifted) nodeList.elementAt(i);
	  if (node.getName().equals(nodeName)){
		return node;
	  }
	}
	return null;
  //  throw new BBNE_NodeNotFound();
  }

  public void addNode(NodeLifted node){nodeList.addElement(node);}

  /**
   * removeNodeByName will remove the Node object matching the specified string
   * name from the network.
   *
   * @param name - the name of the node to be removed
   */

  public void removeNodeByName(String name){
	String str = name;

	for(int i = 0; i < this.nodeList.size(); i++) {
		if(this.getNodeAt(i).getName().equals(name))
			this.nodeList.remove(i);
	}
  }
  /**
	 * indexOf will return the index of a Node.  Throws BBNE_NodeNotFound
	 * exception if the node is not in the network.
	 *
	 * @param node - the Node object being searched for.
	 * @return int - the index of the node
	 */

	public int indexOf(NodeLifted node) {
	  int index = nodeList.indexOf(node);
	//  if (index==-1) throw new BBNE_NodeNotFound();
	  return index;
	}

	/**
	 * indexOf will return the index of a Node.  Throws BBNE_NodeNotFound
	 * exception if the node is not in the network.
	 *
	 * @param node - the Node object being searched for.
	 * @return int - the index of the node
	 */
	public int indexOf(String nodeName) {
	  return indexOf(getNodeByName(nodeName));
	}

	public int size(){
	  return nodeList.size();
	}
	

}


 
