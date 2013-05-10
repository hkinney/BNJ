package edu.ksu.cis.bnj.gui.tools.undo;

import edu.ksu.cis.bnj.gui.tools.UndoContext;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;

/*!
 * \file UndoWhole.java
 * \author Jeffrey M. Barber
 */
public class UndoWhole implements Undo
{
	BeliefNetwork _old;
	BeliefNetwork _new;
	public UndoWhole(BeliefNetwork old, BeliefNetwork n)
	{
		_old = old;
		_new = n;
	}
	public void apply(UndoContext uc)
	{
		uc.Network.construct(_old);
	}
}
