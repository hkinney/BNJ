package edu.ksu.cis.bnj.ver3.inference.exact;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.util.graph.algorithms.BuildCliqueTree;
import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.core.Vertex;
import edu.ksu.cis.util.graph.visualization.VisualizationController;
import edu.ksu.cis.util.graph.visualization.operators.Delay;
import edu.ksu.cis.util.graph.visualization.operators.FlushMarkings;
import edu.ksu.cis.util.graph.visualization.operators.SwitchGraph;
import edu.ksu.cis.util.graph.visualization.operators.VertexColor;
/*!
 * \file CliqueTree.java
 * \author Jeffrey M. Barber
 */
public class CliqueTree
{
	private BeliefNode[]	_BeliefNodes	= null;
	private Graph			_CliqueTree		= null;
	private CPF[]			_Marginals		= null;
	private LinkedList		_StartingNodes	= null;
	private VisualizationController _VC		= null;
	/*! access to the belief nodes of the clique tree 
	 * \return the belief nodes associated to this clique tree
	 */
	public BeliefNode[] getNodes()
	{
		return _BeliefNodes;
	}
	/*! Marginalize the Clique Tree
	 * \return the marginals
	 */
	public CPF[] Marginalize()
	{
		_Marginals = new CPF[_BeliefNodes.length];
		Vertex[] Z = _CliqueTree.getVertices();
		for (int i = 0; i < Z.length; i++)
		{
			Clique C = (Clique) Z[i].getObject();
			BeliefNode[] M = C.getBaseNodes();
			if(_VC != null)
			{
				_VC.pushAndApplyOperator(new VertexColor(C._Owner, 18));
				_VC.pushAndApplyOperator(new Delay("delay_marginalize", 100));
			}			
			for (int j = 0; j < M.length; j++)
			{
				BeliefNode[] S = new BeliefNode[1];
				S[0] = M[j];
				_Marginals[M[j].loc()] = CPF.normalize( C.getCPF().extract(S) );
				//_Marginals[M[j].loc()].normalize();
			}
			if(_VC != null)
			{
				_VC.pushAndApplyOperator(new VertexColor(C._Owner, 19));
				_VC.pushAndApplyOperator(new Delay("delay_marginalize", 100));
			}			
		}
		return _Marginals;
	}
	/*! Construct this tree from a prebuilt Clique Tree
	 * \param[in] BCT a previously built clique tree
	 * \param[in] bn the belief network (contains the evidence)
	 */
	public CliqueTree(BuildCliqueTree BCT, BeliefNetwork bn)
	{
		Graph lct = BCT.getCliqueTree();
		LinkedList evNodes = new LinkedList();
		{
			Object[] cache = bn.getGraph().getObjectsAfterCopy();
			_BeliefNodes = new BeliefNode[cache.length];
			// build the list of evidence
			for (int i = 0; i < cache.length; i++)
			{
				_BeliefNodes[i] = (BeliefNode) cache[i];
				if (_BeliefNodes[i].hasEvidence())
				{
					evNodes.add(_BeliefNodes[i]);
				}
			}
			cache = null;
		}
		// create hte clique tree
		_CliqueTree = new Graph();
		int N = BCT.getNumberOfCliques();
		// create the vertex cache mapping (fix 7/5, JMB)
		int[] vcache = new int[N];
		for (int i = 0; i < N; i++)
		{
			Clique C = new Clique(BCT.getCliqueSet(i), BCT.getCliqueBases(i), BCT.getCliqueS(i), _BeliefNodes);
			C.filterEvidenceNodes(evNodes);
			Vertex Z = new Vertex(BCT.getClique(i).getName());
			Z.setObject(C);
			_CliqueTree.addVertex(Z);
			// update the cache mapping
			vcache[BCT.getClique(i).loc()] = Z.loc();
		}
		// connect the cliques
		Vertex[] nodes = _CliqueTree.getVertices();
		for (int i = 0; i < N; i++)
		{
			Vertex[] c = BCT.getChildren(i);
			Vertex p = nodes[vcache[BCT.getClique(i).loc()]];
			for (int z = 0; z < c.length; z++)
			{
				Vertex chi = nodes[vcache[c[z].loc()]];
				_CliqueTree.addDirectedEdge(p, chi);
			}
		}
		// inform the cliques of their connectivity
		_StartingNodes = new LinkedList();
		for (int i = 0; i < N; i++)
		{
			Clique C = (Clique) nodes[i].getObject();
			Vertex[] CC = _CliqueTree.getChildren(nodes[i]);
			Vertex[] PP = _CliqueTree.getParents(nodes[i]);
			if (CC == null || CC.length == 0)
			{
				_StartingNodes.add(C);
			}
			C.setupConnectivity(PP, CC, nodes[i]);
		}
		for (int i = 0; i < N; i++)
		{
			Clique C = (Clique) nodes[i].getObject();
			C.iterate();
		}
	}
	/*! Construct this clique tree with perhaps a VC
	 * \param[in] bn the belief network (contains the evidence)
	 * \param[in] VC a visualization controller
	 */
	public CliqueTree(BeliefNetwork bn, VisualizationController VC)
	{
		_VC = VC;
		if(_VC!=null)
		{
			VC.beginTransaction();
		}
		// copy the graph, so we don't screw up original by triangulation and such
		Graph g = bn.getGraph().copy();
		LinkedList evNodes = new LinkedList();
		{
			Object[] cache = bn.getGraph().getObjectsAfterCopy();
			_BeliefNodes = new BeliefNode[cache.length];
			// build the list of evidence
			for (int i = 0; i < cache.length; i++)
			{
				_BeliefNodes[i] = (BeliefNode) cache[i];
				if (_BeliefNodes[i].hasEvidence())
				{
					evNodes.add(_BeliefNodes[i]);
				}
			}
			cache = null;
		}
		BuildCliqueTree BCT = new BuildCliqueTree();
		BCT.setGraph(g);
		BCT.setVisualization(VC);
		BCT.execute();
		Graph lct = BCT.getCliqueTree();
		
		// create hte clique tree
		_CliqueTree = new Graph();

		int N = BCT.getNumberOfCliques();
		// create the vertex cache mapping (fix 7/5, JMB)
		int[] vcache = new int[N];
		for (int i = 0; i < N; i++)
		{
			Clique C = new Clique(BCT.getCliqueSet(i), BCT.getCliqueBases(i), BCT.getCliqueS(i), _BeliefNodes);
			C.setVC(_VC);
			C.filterEvidenceNodes(evNodes);
			Vertex Z = new Vertex(BCT.getClique(i).getName());
			if(_VC != null)
			{
				Z.setAttrib(0,BCT.getClique(i).getx());
				Z.setAttrib(1,BCT.getClique(i).gety());
			}
			Z.setObject(C);
			_CliqueTree.addVertex(Z);
			// update the cache mapping
			vcache[BCT.getClique(i).loc()] = Z.loc();
		}
		// connect the cliques
		Vertex[] nodes = _CliqueTree.getVertices();
		for (int i = 0; i < N; i++)
		{
			Vertex[] c = BCT.getChildren(i);
			Vertex p = nodes[vcache[BCT.getClique(i).loc()]];
			for (int z = 0; z < c.length; z++)
			{
				Vertex chi = nodes[vcache[c[z].loc()]];
				_CliqueTree.addDirectedEdge(p, chi);
			}
		}
		
		// inform the cliques of their connectivity
		_StartingNodes = new LinkedList();
		for (int i = 0; i < N; i++)
		{
			Clique C = (Clique) nodes[i].getObject();
			Vertex[] CC = _CliqueTree.getChildren(nodes[i]);
			Vertex[] PP = _CliqueTree.getParents(nodes[i]);
			if (CC == null || CC.length == 0)
			{
				_StartingNodes.add(C);
			}
			C.setupConnectivity(PP, CC, nodes[i]);
		}

		if(_VC != null)
		{
			VC.pushAndApplyOperator(new SwitchGraph(VC.getFrame(), _CliqueTree));
			VC.pushAndApplyOperator(new FlushMarkings());
			VC.commitTransaction();
		}
		for (int i = 0; i < N; i++)
		{
			Clique C = (Clique) nodes[i].getObject();
			C.iterate();
		}
		
	}
	
	private Vertex[] toVertex(BeliefNode[] nodes) {
		Vertex[] vertices = new Vertex[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			vertices[i] = nodes[i].getOwner();
		}
		
		return vertices;
	}
	
	/*! Begin the Inference
	 */
	public void begin()
	{
		if(_VC!=null)
		{
			_VC.beginTransaction();
		}
		for (Iterator i = _StartingNodes.iterator(); i.hasNext();)
		{
			Clique C = (Clique) i.next();
			C.lambdaPropigation();
		}
		if(_VC!=null)
		{
			_VC.commitTransaction();
		}
	}
}