package edu.ksu.cis.bnj.ver3.learning.data;
/*!
 * \file SampleDataTest.java
 * \author Jeffrey M. Barber
 */
public class SampleDataTest
{
	public static void main(String[] args)
	{
		try
		{
			DiscreteDataSourceARFF data = new DiscreteDataSourceARFF("asia1000data.arff");
			System.out.println("data loaded\n");
			int[] q = data.getQueryRegister();
			
			// a Simple Query
			// P( Cancer = Present | Smoking = Smoker ) = P ( Cancer = Present AND Smoking = Smoker ) / P ( Smoking = Smoker )

			// need to access the columns from console
			int cCancer  = data.findColumn("Cancer");
			int cSmoking = data.findColumn("Smoking");

			// P ( Cancer = Present AND Smoking = Smoker )
			data.reset(q); // fill with -1
			q[cCancer] = data.getDiscrete(cCancer).findName("Present");
			q[cSmoking] = data.getDiscrete(cSmoking).findName("Smoker");

			System.out.println("calculating P ( Cancer = Present AND Smoking = Smoker )");
			int pCPSS = data.query(q);
			System.out.println(".. " + pCPSS);
			// P ( Cancer = Present AND Smoking = Smoker )

			data.reset(q); // fill with -1
			q[cSmoking] = data.getDiscrete(cSmoking).findName("Smoker");

			System.out.println("calculating P ( Smoking = Smoker )");
			int pSS = data.query(q);
			System.out.println(".. " + pSS);
			
			System.out.println(" " + pCPSS + " / " + pSS);
			System.out.println(" Result = " + ((double) pCPSS / (double) pSS));
			
		} catch (Exception e)
		{
			
		}
	}
}
