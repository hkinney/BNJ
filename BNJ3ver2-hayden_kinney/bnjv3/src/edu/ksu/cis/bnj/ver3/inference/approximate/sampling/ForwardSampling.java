/*
 * Created on Sep 15, 2004
 *
 * 
 */
package edu.ksu.cis.bnj.ver3.inference.approximate.sampling;
import java.util.*;
//import edu.ksu.cis.bnj.ver3.core.values.*;
import edu.ksu.cis.bnj.ver3.core.*;
import edu.ksu.cis.bnj.ver3.core.values.*;

/**
 * @author Andrew King
 *
 * This class contains utility methods for forward sampling
 */
public class ForwardSampling {
	
	static public int sampleForward(BeliefNode node,  BeliefNetwork network){
		if(node.hasEvidence()){ return(-1); }
		CPF this_cpf = node.getCPF();
		int diff = 0;
		Random generator = new Random();
		BeliefNode[] domain_product = node.getCPF().getDomainProduct();
		//the fist element in the domain_product array is the node we are
		//instantiating (sampling
		int[] logical_query = new int[domain_product.length];
		//construct the logical query to start at the right place
		
		for(int i = 0; i < logical_query.length; i++){
			if(!domain_product[i].hasEvidence()) { logical_query[i] = 0; }
			else{
				DiscreteEvidence e = (DiscreteEvidence)domain_product[i].getEvidence();
				logical_query[i] = e.getDirectValue();
			}
		}
		
		int real_addy0 = this_cpf.addr2realaddr(logical_query);
		logical_query[0] = 1;
		int real_addy1 = this_cpf.addr2realaddr(logical_query);
		diff = real_addy1 - real_addy0;
		//Make a prob array with #outcomes in domain  - 1
		Value[] prob_interval = new Value[domain_product[0].getDomain().getOrder() - 1];
		//walk the CPF and grab the appropiate values, given the parent instantiations
		for(int i = 0; i < prob_interval.length; i++){
			prob_interval[0] = this_cpf.get(real_addy0 + (i * diff));
		}
		//Now we have the interval setup, so we need to gen a random number between 0 and 1, then see where
		//it falls in the interval
		double random = generator.nextDouble();
		Value value_random = new ValueDouble(random);
		int new_ev_index = -1;
		for(int i = 0; i < prob_interval.length; i++){
			if(intervalCollide(prob_interval, i, value_random)) new_ev_index = i;
		}
		return(new_ev_index);
	}

	static private boolean intervalCollide(Value[] interval, int index, Value random){
		boolean ret = false;
		//check the corner cases
		if(index == 0){
			if(((ValueDouble)interval[0]).getValue() > ((ValueDouble)random).getValue()) return(true);
		}
		if(index == interval.length - 1){
			if(((ValueDouble)interval[0]).getValue() < ((ValueDouble)random).getValue()) return(true);
		}
		//done with corner, now the middle
		if(((ValueDouble)interval[index - 1]).getValue() < ((ValueDouble)random).getValue() &  ((ValueDouble)interval[index]).getValue() > ((ValueDouble)random).getValue()){
			return true;
		}
		return false;
	}
}
