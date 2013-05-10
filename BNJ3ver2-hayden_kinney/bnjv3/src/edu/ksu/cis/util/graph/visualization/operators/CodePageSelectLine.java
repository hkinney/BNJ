package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.graph.visualization.CodePage;
import edu.ksu.cis.util.graph.visualization.CodePageOperator;
/*!
 * \file CodePageSelectLine.java
 * \author Jeffrey M. Barber
 */
public class CodePageSelectLine implements CodePageOperator
{
	private int	_newindex;
	private int	_oldindex;
	private int	_currentPage;
	/*! select in the current code page, newLine
	 * \param[in] newLine new line to highlight
	 */
	public CodePageSelectLine(int newLine)
	{
		_newindex = newLine;
	}
	/*! apply this code page operator
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::apply(edu.ksu.cis.util.graph.visualization.CodePage)
	 */
	public void apply(CodePage CP)
	{
		_currentPage = CP.getIndex();
		_oldindex = CP.getActive();
		CP.setActve(_newindex);
	}
	/*! apply the inverse of this code page operator
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::applyInverse(edu.ksu.cis.util.graph.visualization.CodePage)
	 */
	public void applyInverse(CodePage CP)
	{
		CP.setActve(_oldindex);
	}
	/*! (non-Javadoc)
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::getCodePage()
	 */
	public int getCodePage()
	{
		return _currentPage;
	}
}