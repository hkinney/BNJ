package edu.ksu.cis.bnj.gui.tools.undo;
import edu.ksu.cis.bnj.gui.tools.UndoContext;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.util.graph.core.Vertex;
public class UndoEdgeCreate implements Undo
{
	private Vertex		_A;
	private Vertex		_B;
	private BeliefNode	_bA;
	private BeliefNode	_bB;
	public UndoEdgeCreate(Vertex A, Vertex B, BeliefNode bA, BeliefNode bB)
	{
		_A = A;
		_B = B;
		_bA = bA;
		_bB = bB;
	}
	public void apply(UndoContext uc)
	{
		uc.Network.ForceDisconnect(_A, _B, _bA, _bB);
	}
}