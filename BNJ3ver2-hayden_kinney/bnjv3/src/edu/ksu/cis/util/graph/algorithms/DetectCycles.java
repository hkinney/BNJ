package edu.ksu.cis.util.graph.algorithms;
import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.core.Vertex;
import edu.ksu.cis.util.graph.visualization.Algorithm;
/**
 * file: DetectCycles.java
 * 
 * @author Jeffrey M. Barber
 */
public class DetectCycles extends Algorithm
{
	public boolean		hasCycles;
	private boolean[]	Mark;
	private Vertex		Goal;
	public DetectCycles()
	{_Name = "Detect Graph Cycles";	}		
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
			if (Children[i].loc() == Goal.loc())
			{
				hasCycles = true;
				return;
			}
			else
			{
				search(G, Children[i]);
			}
		}
	}
	public void execute()
	{
		hasCycles = false;
		if (_Graph == null) return;
		Graph hack = _Graph.copy();
		RemoveDirectionality RD = new RemoveDirectionality();
		RD.setGraph(hack);
		RD.execute();
		hack = RD.getGraph();
		Vertex[] V = hack.getVertices();
		Mark = new boolean[V.length];
		for (int i = 0; i < V.length; i++)
		{
			InitMark();
			Goal = V[i];
			search(hack, V[i]);
			;
			if (hasCycles) return;
		}
	}
}