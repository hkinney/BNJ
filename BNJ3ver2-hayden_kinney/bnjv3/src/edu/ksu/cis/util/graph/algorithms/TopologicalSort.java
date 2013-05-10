package edu.ksu.cis.util.graph.algorithms;
import edu.ksu.cis.util.graph.core.Vertex;
import edu.ksu.cis.util.graph.visualization.Algorithm;
import edu.ksu.cis.util.graph.visualization.Annotation;
import edu.ksu.cis.util.graph.visualization.operators.VertexChangeOrderIndex;
/**
 * file: TopologicalSort.java
 * 
 * @author Jeffrey M. Barber
 */
public class TopologicalSort extends Algorithm
{
	private boolean[]	_mark;
	public int[]		alpha;
	private int			_pos;
	private boolean		_vis;

	public TopologicalSort()
	{_Name = "Topological Sort";	}

	private void InitMark()
	{
		for (int i = 0; i < _mark.length; i++)
		{
			_mark[i] = false;
		}
	}
	private void search(Vertex V)
	{
		if (_mark[V.loc()]) return;
		_mark[V.loc()] = true;
		Vertex[] Children = _Graph.getChildren(V);
		for (int i = 0; i < Children.length; i++)
		{
			search(Children[i]);
		}
		if(_vis) VC().pushAndApplyOperator(new VertexChangeOrderIndex(V,alpha.length - _pos));
		alpha[_pos] = V.loc();
		_pos--;
	}
	public void execute()
	{
		_vis = false;
		if (_Graph == null) return;
		if(VC()!=null) _vis = true;
		if(_vis) VC().beginTransaction();
		if (_vis) VC().pushAndApplyOperator(new Annotation("Topological Sort"));
		Vertex[] V = _Graph.getVertices();
		_mark = new boolean[V.length];
		InitMark();
		alpha = new int[V.length];
		_pos = V.length - 1;
		for (int i = 0; i < V.length; i++)
		{
			if (!_mark[i])
			{
				search(V[i]);
			}
		}
		if(_vis) VC().commitTransaction();
	}
}