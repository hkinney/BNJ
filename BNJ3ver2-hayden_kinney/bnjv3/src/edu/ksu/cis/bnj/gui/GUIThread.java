package edu.ksu.cis.bnj.gui;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;

/*!
 * \file GUIThread.java
 * \author Jeffrey M. Barber
 */
public class GUIThread extends Thread
{
	private GUIWindow _window;
	private BeliefNetwork _bn;
	
	public GUIThread(GUIWindow guiwindow, BeliefNetwork bn)
	{
		_window = guiwindow;
		_bn = bn;
	}
	
	public void run()
	{
		_window.run(_bn);
	}
}
