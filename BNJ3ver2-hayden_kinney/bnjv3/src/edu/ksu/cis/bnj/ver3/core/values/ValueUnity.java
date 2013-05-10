package edu.ksu.cis.bnj.ver3.core.values;
import edu.ksu.cis.bnj.ver3.core.Value;
/*!
 * \file ValueUnity.java
 * \author Jeffrey M. Barber
 */
public class ValueUnity implements Value
{
	public static ValueUnity	SingletonUnity	= new ValueUnity();
	/*! Get the string value of this value
	 * \see edu.ksu.cis.bnj.ver3.core.Value::getExpr()
	 * \return the string expression
	 */
	public String getExpr()
	{
		return "1";
	}
}