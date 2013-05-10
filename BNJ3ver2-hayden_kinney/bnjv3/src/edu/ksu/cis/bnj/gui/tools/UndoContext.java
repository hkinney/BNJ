package edu.ksu.cis.bnj.gui.tools;
import java.util.ArrayList;
import edu.ksu.cis.bnj.gui.rendering.RenderNetwork;
import edu.ksu.cis.bnj.gui.tools.undo.Undo;
public class UndoContext
{
	public RenderNetwork	Network;
	private ArrayList		UndoList;
	public UndoContext()
	{
		UndoList = new ArrayList();
	}
	public void register(Undo U)
	{
		UndoList.add(U);
	}
	public void undo()
	{
		if (UndoList.size() >= 1)
		{
			Undo U = (Undo) UndoList.remove(UndoList.size() - 1);
			U.apply(this);
		}
	}
}