/*
 * Created on Jul 26, 2004
 *
 * 
 */
package edu.ksu.cis.bnj.ver3.inference.approximate.sampling;


import edu.ksu.cis.bnj.ver3.core.*;
import edu.ksu.cis.util.driver.*;
import edu.ksu.cis.bnj.ver3.inference.exact.*;
import edu.ksu.cis.bnj.ver3.core.values.*;
import java.util.*;
/**
 * @author Andrew King
 *
 * This class is for running AIS w/o going through the main BNJ entry point (drivers.MASTER)
 * Don't run AIS from here unless you really need to, know what you are doing.
 */
public class AISTestBed {
	
	
	public static void main(String[] args){
		Vector appro_probs = new Vector();
		Vector exact_probs = new Vector();
		int samples = new Integer(args[1]).intValue();
		int interval = new Integer(args[2]).intValue();
		Options options = new Options();

		BeliefNetwork bn = Options.load(args[0]);
		BeliefNode[] nodes = bn.getNodes();

	

		AIS ais = new AIS();
		LS ls  = new LS();
		System.out.println("LOADED: " + args[0]);
		System.out.println("NET NAME: " + bn.getName());
		System.out.println("NUM NODES: " + bn.getNodes().length);

		ais.setNumSamples(samples);
		ais.setInterval(interval);

		//ais.setSamples(samples)
		//ais.setInterval(interval)

		ais.run(bn);
		ls.run(bn);
		
		//get probs from LS marginalize
		for(int i = 0; i < nodes.length; i++){
			CPF this_cpf = ls.queryMarginal(nodes[i]);
			Vector v = new Vector();
			for(int c = 0; c < this_cpf.size(); c++ ){
				ValueDouble val_doub = (ValueDouble)this_cpf.get(c);
				double d = val_doub.getValue();
				Double d_obj = new Double(d);
				v.add(d_obj);
			}
			exact_probs.add(v);
		}
		
		appro_probs = ais.probabilityArray;
		
		double d = ais.eval.computeRMSE_DforOneFromTwoProbs(exact_probs, appro_probs);
		System.out.println("Samples: " + samples + " Interval: " + interval + " RMSE: " + d);
	}
	

}
