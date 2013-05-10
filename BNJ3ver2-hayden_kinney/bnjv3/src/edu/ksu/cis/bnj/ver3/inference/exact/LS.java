package edu.ksu.cis.bnj.ver3.inference.exact;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.lazy.CacheManager;
import edu.ksu.cis.bnj.ver3.inference.Inference;
import edu.ksu.cis.util.graph.layout.LayPolyTree;
import edu.ksu.cis.util.graph.visualization.Annotation;
import edu.ksu.cis.util.graph.visualization.VisualizationController;
import edu.ksu.cis.util.graph.visualization.operators.CallForLayoutPolyTree;
import edu.ksu.cis.util.graph.visualization.operators.CodePageSelect;
import edu.ksu.cis.util.graph.visualization.operators.ColorLegendMap;
import edu.ksu.cis.util.graph.visualization.operators.Delay;
import edu.ksu.cis.util.graph.visualization.operators.NewColorLegend;
/*!
 * \file LS.java
 * \author Jeffrey M. Barber
 */
public class LS implements Inference
{
	private CPF[]			marginals	= null;
	private BeliefNode[]	beliefnodes;
	/*! Run the Inference
	 * \param[in] bn the Belief Network to run inference on
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::run(edu.ksu.cis.bnj.ver3.core.BeliefNetwork)
	 */
	public void run(BeliefNetwork bn)
	{
		CacheManager.getInstance().reset();
		CliqueTree CT = new CliqueTree(bn, null);
		CT.begin();
		marginals = CT.Marginalize();
		beliefnodes = CT.getNodes();
	}
	/*! Run the Inference with a Visualization Controller
	 * \param[in] bn the Belief Network to run inference on
	 * \param[in] VC the visualization controller
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::run(edu.ksu.cis.bnj.ver3.core.BeliefNetwork)
	 */
	public void run(BeliefNetwork bn, VisualizationController VC)
	{
		CacheManager.getInstance().reset();
		VC.beginTransaction();
		VC.pushAndApplyOperator(new Annotation("LS : Pre Processing"));
		VC.pushAndApplyOperator(new Delay("delay_ls_introduction", 500));
		CliqueTree CT = new CliqueTree(bn, VC);
		VC.pushAndApplyOperator(new Annotation("Organizing"));
		VC.pushAndApplyOperator(new Delay("delay_organize", 50));
		VC.pushAndApplyOperator(new CallForLayoutPolyTree());
		VC.pushAndApplyOperator(new CodePageSelect(-1));
		VC.pushAndApplyOperator(new Annotation("LS : Lambda & Pi Message Passing"));
		VC.pushAndApplyOperator(new CodePageSelect(6));
		VC.pushAndApplyOperator(new NewColorLegend());
		VC.pushAndApplyOperator(new ColorLegendMap(0,"No activity"));
		VC.pushAndApplyOperator(new ColorLegendMap(14,"lambdaprop"));
		VC.pushAndApplyOperator(new ColorLegendMap(15,"setlambdamessage"));
		VC.pushAndApplyOperator(new ColorLegendMap(16,"pipropigation"));
		VC.pushAndApplyOperator(new ColorLegendMap(17,"setpimessage"));
		VC.pushAndApplyOperator(new Delay("delay_ls_lambdapi", 500));
		CT.begin();
		VC.pushAndApplyOperator(new CodePageSelect(-1));
		VC.pushAndApplyOperator(new Annotation("LS : Marginalization"));
		VC.pushAndApplyOperator(new NewColorLegend());
		VC.pushAndApplyOperator(new ColorLegendMap(18,"marginalizing"));
		VC.pushAndApplyOperator(new ColorLegendMap(19,"done"));
		VC.pushAndApplyOperator(new Delay("delay_ls_marginalization", 500));
		marginals = CT.Marginalize();
		beliefnodes = CT.getNodes();
		VC.pushAndApplyOperator(new NewColorLegend());
		VC.pushAndApplyOperator(new Annotation("LS : Done"));
		VC.commitTransaction();
	}
	/*! Query for a Marginal
	 * \param[in] bnode the Belief Node to query the marginal of
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::queryMarginal()
	 */
	public CPF queryMarginal(BeliefNode bnode)
	{
		for (int i = 0; i < beliefnodes.length; i++)
		{
			if (bnode.getName().equals(beliefnodes[i].getName()))
			{
				return marginals[i];
			}
		}
		return null;
	}
	/*! Get the name of this Inference Algorithm
	 * \see edu.ksu.cis.bnj.ver3.inference.Inference::getName()
	 */
	public String getName()
	{
		return "LS";
	}
}