package edu.ksu.cis.bnj.gui.tools.undo;
import java.util.ArrayList;
import java.util.Iterator;
import edu.ksu.cis.bnj.gui.tools.UndoContext;
public class UndoBatch implements Undo
{
	public ArrayList	_Batch;
	public UndoBatch()
	{
		_Batch = new ArrayList();
	}
	public void Register(Undo U)
	{
		_Batch.add(U);
	}
	public void apply(UndoContext UC)
	{
		for (Iterator iter = _Batch.iterator(); iter.hasNext();)
		{
			Undo U = (Undo) iter.next();
			U.apply(UC);
		}
	}
}