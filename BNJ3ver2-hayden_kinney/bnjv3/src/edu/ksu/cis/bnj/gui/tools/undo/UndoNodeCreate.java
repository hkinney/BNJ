package edu.ksu.cis.bnj.gui.tools.undo;
import edu.ksu.cis.bnj.gui.tools.UndoContext;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.util.graph.core.Vertex;
public class UndoNodeCreate implements Undo
{
	Vertex		_X;
	BeliefNode	_node;
	public UndoNodeCreate(Vertex X, BeliefNode bnode)
	{
		_X = X;
		_node = bnode;
	}
	public void apply(UndoContext uc)
	{
		uc.Network.ForceDeleteNode(_X, _node);
	}
}