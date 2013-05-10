/*
 * Created on Jul 31, 2004
 *
 * 
 */
package edu.ksu.cis.util;

import edu.ksu.cis.bnj.ver3.core.*;
import edu.ksu.cis.bnj.ver3.core.values.*;

/**
 * @author Andrew King
 *
 * This class contains a static method to compute the RMSE of an array of approximate
 * and exact CPF values.
 */
public class RMSE {
	
	public static Value computeRMSE(CPF[] exact_probs, CPF[] approx_probs){
		
		Value rmse = new ValueDouble(0.0);
		long n = 0;
		
		for(int i = 0; i < exact_probs.length; i++){
			for(int c = 0; c < exact_probs[i].size(); c++){
				Value exact_val = exact_probs[i].get(c);
				Value approx_val = approx_probs[i].get(c);
				Value difference = Field.subtract(exact_val, approx_val);
				rmse = Field.add(rmse, Field.mult( difference, difference));
				n++;
			}
		}
		rmse = Field.divide(rmse, new ValueDouble(n));
		double rmse_d = Double.parseDouble(rmse.getExpr());
		rmse = new ValueDouble(Math.sqrt(rmse_d));
		return rmse;
	}

}
