package edu.ksu.cis.util.graph.visualization.operators;
import java.util.TreeMap;
import edu.ksu.cis.util.graph.visualization.CodePage;
import edu.ksu.cis.util.graph.visualization.CodePageOperator;
/*!
 * \file CodePagePopEnvironment.java
 * \author Jeffrey M. Barber
 */
public class CodePagePopEnvironment implements CodePageOperator
{
	private int		_currentPage;
	private TreeMap	_old;
	/*! pop the code page environment
	 */
	public CodePagePopEnvironment()
	{}
	/*! apply this code page operator
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::apply(edu.ksu.cis.util.graph.visualization.CodePage)
	 */
	public void apply(CodePage CP)
	{
		_currentPage = CP.getIndex();
		_old = CP.pop();
	}
	/*! apply the inverse of this code page operator
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::applyInverse(edu.ksu.cis.util.graph.visualization.CodePage)
	 */
	public void applyInverse(CodePage CP)
	{
		CP.push(_old);
	}
	/*! (non-Javadoc)
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::getCodePage()
	 */
	public int getCodePage()
	{
		return _currentPage;
	}
}