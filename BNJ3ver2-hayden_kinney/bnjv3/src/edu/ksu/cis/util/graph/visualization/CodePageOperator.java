package edu.ksu.cis.util.graph.visualization;
/*!
 * \file CodePageOperator.java
 * \author Jeffrey M. Barber
 */
public interface CodePageOperator
{
	/*! apply this to a codepage
	 * \param[in] CP 
	 * \return
	 */
	public void apply(CodePage CP);
	/*! apply the inverse
	 * \param[in] CP the codepage to operate on
	 * \return
	 */
	public void applyInverse(CodePage CP);
	/*! get the current code page
	 * \return
	 */
	public int getCodePage();
}