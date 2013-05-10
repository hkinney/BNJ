package edu.ksu.cis.bnj.gui.tools.undo;
import edu.ksu.cis.bnj.gui.tools.UndoContext;
public interface Undo
{
	public void apply(UndoContext UC);
}