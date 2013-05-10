package edu.ksu.cis.bnj.ver3.core.values;
import edu.ksu.cis.bnj.ver3.core.Value;
/*!
 * \file ValueZero.java
 * \author Jeffrey M. Barber
 */
public class ValueZero implements Value
{
	public static ValueZero	SingletonZero	= new ValueZero();
	/*! Get the string value of this value
	 * \see edu.ksu.cis.bnj.ver3.core.Value::getExpr()
	 * \return the string expression
	 */
	public String getExpr()
	{
		return "0";
	}
	public double getValue()
	{
		return 0;
	}
}