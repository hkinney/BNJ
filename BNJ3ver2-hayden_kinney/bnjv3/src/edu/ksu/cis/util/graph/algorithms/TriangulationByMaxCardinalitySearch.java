package edu.ksu.cis.util.graph.algorithms;
import edu.ksu.cis.util.graph.core.Vertex;
import edu.ksu.cis.util.graph.visualization.*;
import edu.ksu.cis.util.graph.visualization.operators.Delay;
/**
 * file: Triangulation.java
 * 
 * @author Jeffrey M. Barber
 */
public class TriangulationByMaxCardinalitySearch extends Algorithm
{
	private int[]		alpha		= null;
	private Vertex[]	alphainv	= null;
	public TriangulationByMaxCardinalitySearch()
	{_Name = "Triangulation By Max Cardinality Search";	}
	
	
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ksu.cis.util.graph.visualization.Algorithm#execute()
	 */
	public void execute()
	{
		
		boolean _vis = canVisualize();
		if (_vis) VC().beginTransaction();
		if (_vis) VC().pushAndApplyOperator(new Annotation("Triangulation By Max Cardinality Search"));
		if (_vis) VC().pushAndApplyOperator(new Delay("delay_vis_triangulation_by_mcs",1000));
		
		RemoveDirectionality RD = new RemoveDirectionality();
		Moralization Moral = new Moralization();
		MaximumCardinalitySearch MCS = new MaximumCardinalitySearch();
		// Execute Moralization
		Moral.setGraph(_Graph);
		Moral.setVisualization(VC());
		Moral.execute();
		_Graph = Moral.getGraph();
		// Remove Directionality
		RD.setGraph(_Graph);
		RD.setVisualization(VC());
		RD.execute();
		_Graph = RD.getGraph();
		// Maximum Cardinality Search
		MCS.setGraph(_Graph);
		MCS.setVisualization(VC());
		MCS.execute();
		_Graph = MCS.getGraph();
		
		alpha = MCS.getAlpha();
		alphainv = MCS.getAlphaInverse();
		// Fill-In Computation
		FillIn FIN = new FillIn(alpha, alphainv);
		FIN.setGraph(_Graph);
		FIN.setVisualization(VC());
		FIN.execute();
		_Graph = FIN.getGraph();
		if (_vis) VC().commitTransaction();
	}
}