package edu.ksu.cis.util.graph.algorithms;
import edu.ksu.cis.util.data.OrderedList;
import edu.ksu.cis.util.graph.visualization.*;
import edu.ksu.cis.util.graph.visualization.operators.*;
import edu.ksu.cis.util.graph.core.*;
/**
 * file: RemoveDirectionality.java
 * 
 * @author Jeffrey M. Barber
 */
public class RemoveDirectionality extends Algorithm implements Visitor
{
	private boolean		_vis;
	private OrderedList	_Edges;

	
	public RemoveDirectionality()
	{_Name = "Remove Directionality";	}	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ksu.cis.util.graph.visualization.Algorithm#execute()
	 */
	public void execute()
	{
		_vis = super.canVisualize();
		_Edges = new OrderedList();
		if (_vis){
			VC().beginTransaction();
			VC().pushAndApplyOperator(new CodePageSelect(1));
		}
		_Graph.apply(this, false, true);
		if (_vis)
		{
			//for(Iterator i = _Edges.iterator(); i.hasNext(); )
			VC().pushAndApplyOperator(new Annotation("Remove Directionality"));
			VC().pushAndApplyOperator(new CodePageSelectLine(0));
			VC().pushAndApplyOperator(new NewColorLegend());
			VC().pushAndApplyOperator(new ColorLegendMap(0,"No activity"));
			VC().pushAndApplyOperator(new ColorLegendMap(4,"active"));

			for (int i = 0; i < _Edges.size(); i++)
			{
				VC().pushAndApplyOperator(new CodePageSelectLine(1));
				Edge E = (Edge) _Edges.get(i);
				VC().pushAndApplyOperator(new CodePageUpdateEnvironment("s",E.s().getName()));
				VC().pushAndApplyOperator(new CodePageUpdateEnvironment("d",E.d().getName()));
				VC().pushAndApplyOperator(new CodePageSelectLine(2));
				VC().pushAndApplyOperator(new CodePageSelectLine(3));
				if (E.isDirected())
				{
					GraphBatch B = new GraphBatch();
					B.add(new VertexColor(E.s(), 4));
					B.add(new VertexColor(E.d(), 4));
					super.VC().pushAndApplyOperator(B);
					super.VC().pushAndApplyOperator(new ChangeEdgeDirection(E, false));
					B = new GraphBatch();
					B.add(new VertexColor(E.s(), 0));
					B.add(new VertexColor(E.d(), 0));
					super.VC().pushAndApplyOperator(B);
				
				}
				VC().pushAndApplyOperator(new CodePageSelectLine(4));
			}
			VC().pushAndApplyOperator(new CodePageSelectLine(-1));
			
		}
		else
		{
			for (int i = 0; i < _Edges.size(); i++)
			{
				Edge E = (Edge) _Edges.get(i);
				E.setDirected(false);
				_Graph.addEdge(E);
			}
		}
		if (_vis) VC().commitTransaction();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ksu.cis.util.graph.viistors.Visitor#onVertex(edu.ksu.cis.util.graph.core.Vertex)
	 */
	public boolean onVertex(Vertex V)
	{
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ksu.cis.util.graph.viistors.Visitor#onEdge(edu.ksu.cis.util.graph.core.Edge)
	 */
	public boolean onEdge(Edge E)
	{
		_Edges.add(E);
		return true;
	}
}