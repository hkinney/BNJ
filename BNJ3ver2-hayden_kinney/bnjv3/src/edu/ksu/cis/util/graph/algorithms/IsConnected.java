package edu.ksu.cis.util.graph.algorithms;

import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.core.Vertex;
import edu.ksu.cis.util.graph.visualization.Algorithm;

/**
 * file: IsConnected.java
 * @author Jeffrey M. Barber
 */
public class IsConnected  extends Algorithm
{
	private boolean[]	Mark;
	public boolean connected;

	public IsConnected()
	{_Name = "Graph Connected?";	}		
	private void InitMark()
	{
		for (int i = 0; i < Mark.length; i++)
		{
			Mark[i] = false;
		}
	}
	private void search(Graph G, Vertex V)
	{
		if (Mark[V.loc()]) return;
		Mark[V.loc()] = true;
		Vertex[] Children = G.getChildren(V);
		for (int i = 0; i < Children.length; i++)
		{
			search(G, Children[i]);
		}
	}
	public void execute()
	{
		if (_Graph == null) return;
		Graph hack = _Graph.copy();
		RemoveDirectionality RD = new RemoveDirectionality();
		RD.setGraph(hack);
		RD.execute();
		Vertex[] V = hack.getVertices();
		Mark = new boolean[V.length];
		InitMark();
		search(hack, V[0]);
		connected = true;
		for (int i = 0; i < Mark.length && connected; i++)
		{
			connected = Mark[i] && connected;
		}		
	}
}
