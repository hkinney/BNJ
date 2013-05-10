package edu.ksu.cis.util.graph.algorithms;
import edu.ksu.cis.util.data.OrderedList;
import edu.ksu.cis.util.graph.visualization.*;
import edu.ksu.cis.util.graph.visualization.operators.*;
import edu.ksu.cis.util.graph.core.*;

/**
 * file: MaximumCardinalitySearch.java
 * 
 * @author Jeffrey M. Barber
 */
public class MaximumCardinalitySearch extends Algorithm
{
	private int[]		alpha		= null;
	private Vertex[]	alphainv	= null;
	public MaximumCardinalitySearch()
	{_Name = "Maximum Cardinality Search";	}		
	/**
	 * @return
	 */
	public int[] getAlpha()
	{
		return alpha;
	}
	/**
	 * @return
	 */
	public Vertex[] getAlphaInverse()
	{
		return alphainv;
	}
	/**
	 * @param set
	 * @return
	 */
	public Vertex PickVertex(OrderedList set)
	{
		int dMax = -1;
		Vertex vMax = null;
		if (set.size() > 0)
		{
			for (int i = 0; i < set.size(); i++)
			{
				Vertex V = (Vertex) set.get(i);
				if (dMax < _Graph.getDegree(V))
				{
					dMax = _Graph.getDegree(V);
					vMax = V;
				}
			}
		}
		return vMax;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ksu.cis.util.graph.visualization.Algorithm#execute()
	 */
	public void execute()
	{
		boolean _vis = canVisualize();
		if (_vis)
			{
			VC().beginTransaction();
			VC().pushAndApplyOperator(new Annotation("Maximum Cardinality Search"));
			VC().pushAndApplyOperator(new CodePageSelect(2));
			VC().pushAndApplyOperator(new NewColorLegend());
			VC().pushAndApplyOperator(new ColorLegendMap(0,"No activity"));
			VC().pushAndApplyOperator(new ColorLegendMap(5,"v"));
			VC().pushAndApplyOperator(new ColorLegendMap(6,"w"));

			}
		// variables, yeah!
		int n = _Graph.getNumberOfVertices();

		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(3));
		int i, j;
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(4));
		Vertex v, w;
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(5));
		OrderedList[] set = new OrderedList[n+1];
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(6));
		int size[] = new int[n+1];
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(7));
		alpha = new int[n];
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(8));
		alphainv = new Vertex[n + 1];
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(9));
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(10));
		// init the sets
		for (i = 0; i <= n; i++)
		{
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(11));
			set[i] = new OrderedList();
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(10));
		}
		// for each vertex, add to set[0] and set the size[v] = 0
		for (i = _Graph.begin(); _Graph.end(i); i++)
		{
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(12));
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(13));
			v = _Graph.get(i);
			if (_vis) VC().pushAndApplyOperator(new CodePageUpdateEnvironment("v",v.getName()));
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(14));
			size[v.loc()] = 0;
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(15));
			set[0].add(v);
			/*
			if (_vis)
			{
				//TODO update
				//super.VC().pushAndApplyOperator(new VertexColor(_Graph.get(i), new Color(200, 255, 200)));
			}
			*/
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(16));
		}
		// the meat
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(17));
		i = 1;
		j = 0;
		if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(18));
		while (i <= n)
		{
			if (_vis) VC().pushAndApplyOperator(new CodePageUpdateEnvironment("i","" + i));
			if (_vis) VC().pushAndApplyOperator(new CodePageUpdateEnvironment("j","" + j));
			// v = delete any from set[j]
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(19));
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(20));
			v = PickVertex(set[j]);
			if (_vis) VC().pushAndApplyOperator(new CodePageUpdateEnvironment("v",v.getName()));
			//TODO update
			//if (_vis) super.VC().pushAndApplyOperator(new VertexColor(v, new Color(200, 200, 255)));
			if(_vis) super.VC().pushAndApplyOperator(new VertexColor(v,5));
			set[j].remove(v);
			// a[v] = i, ainv[i] = v
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(21));
			alpha[v.loc()] = i;
			if(_vis) super.VC().pushAndApplyOperator(new VertexChangeOrderIndex(v,i));
			alphainv[i] = v;
			// size[v] = -1;
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(22));
			size[v.loc()] = -1;
			// for ever vertex
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(23));
			for (int k = _Graph.begin(); _Graph.end(k); k++)
			{
				w = _Graph.get(k);

				// that isn't v
				if (w.loc() != v.loc())
				{
					if (_vis) VC().pushAndApplyOperator(new CodePageUpdateEnvironment("w",w.getName()));
					if(_vis) super.VC().pushAndApplyOperator(new VertexColor(w,6));
					if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(24));
					// if they are connected and set[size[w]] >= 0
					if (_Graph.getConnectedness(v, w) != 0 && size[w.loc()] >= 0)
					{
						if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(25));
						// TODO update
						//if (_vis) super.VC().pushAndApplyOperator(new VertexColor(w, new Color(255, 200, 200)));
						// remove w from set[size[w]]
						if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(26));
						set[size[w.loc()]].remove(w);
						// set[size[w]]++
						if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(27));
						size[w.loc()]++;
						// add w to set[size[w]]
						if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(28));
						set[size[w.loc()]].add(w);
					}
				}
				if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(29));
				
				if(_vis) super.VC().pushAndApplyOperator(new VertexColor(w,0));
			}
			// i = i + 1
			// j = j + 1
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(30));
			i++;
			j++;
			if (_vis) VC().pushAndApplyOperator(new CodePageUpdateEnvironment("i","" + i));
			if (_vis) VC().pushAndApplyOperator(new CodePageUpdateEnvironment("j","" + j));
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(31));
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(32));
			// while j >= 0 and set[j] != empty set then j = j - 1
			while (j >= 0 && set[j].size() == 0)
			{
				if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(33));
				j--;
				if (_vis) VC().pushAndApplyOperator(new CodePageUpdateEnvironment("j","" + j));
			}
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(34));
			if(_vis) super.VC().pushAndApplyOperator(new VertexColor(v,0));
			if (_vis) VC().pushAndApplyOperator(new CodePageSelectLine(35));

		}
		
		
		if (_vis) VC().commitTransaction();
	}
}