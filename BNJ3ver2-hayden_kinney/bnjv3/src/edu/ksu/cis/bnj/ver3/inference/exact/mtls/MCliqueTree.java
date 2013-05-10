package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import java.util.LinkedList;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.util.graph.algorithms.BuildCliqueTree;
import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.core.Vertex;
/*!
 * \file MCliqueTree.java
 * \author Jeffrey M. Barber
 */
public class MCliqueTree
{
	private BeliefNode[]		_BeliefNodes		= null;
	private Graph				_CliqueTree			= null;
	private MessageCoordinator	_MessageCoordinator	= null;
	/*! Construct a Clique Tree, Build Message List, Run Inference
	 * \param[in] BCT the precomputation Clique Tree
	 * \param[in] bn the belief network
	 * \param[in] lsmh the marginal hook
	 * \param[in] NumProc Number processors to build from
	 */
	public MCliqueTree(BuildCliqueTree BCT, BeliefNetwork bn, LSMarginalHook lsmh, int NumProc)
	{
		// build the evidence cache
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
		// create the clique tree
		_CliqueTree = new Graph();
		int N = BCT.getNumberOfCliques();
		_MessageCoordinator = new MessageCoordinator(bn.getNodes().length, lsmh, N, NumProc);
		// create the vertex cache mapping (fix 7/5, JMB)
		int[] vcache = new int[N];
		for (int i = 0; i < N; i++)
		{
			MClique C = new MClique(BCT.getCliqueSet(i), BCT.getCliqueBases(i), BCT.getCliqueS(i), _BeliefNodes);
			_MessageCoordinator.queueBuildCPF(C, evNodes);
			// build the graph
			Vertex Z = new Vertex(BCT.getClique(i).getName());
			Z.setObject(C);
			_CliqueTree.addVertex(Z);
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
		// let the clique's cpfs be BUILT
		// inform the cliques of their connectivity
		for (int i = 0; i < N; i++)
		{
			MClique C = (MClique) nodes[i].getObject();
			Vertex[] CC = _CliqueTree.getChildren(nodes[i]);
			Vertex[] PP = _CliqueTree.getParents(nodes[i]);
			C.setupConnectivity(PP, CC);
			if (CC == null || CC.length == 0)
			{
				_MessageCoordinator.queueLambdaPropigation(C);
				// post LambdaPropigation
			}
		}
		_MessageCoordinator.run();
	}
}